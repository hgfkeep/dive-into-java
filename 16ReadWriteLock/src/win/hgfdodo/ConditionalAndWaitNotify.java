package win.hgfdodo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionalAndWaitNotify {
    volatile int x = 0;
    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void use(int mod) throws InterruptedException {
        while (x < 100) {
            try {
                lock.lock();
                while (x % 2 == mod) {
                    System.out.println(Thread.currentThread() + " before await");
                    condition.await();
                    System.out.println(Thread.currentThread() + " after await");
                }
                x++;
                System.out.println(Thread.currentThread() + ":" + x);
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConditionalAndWaitNotify conditionalAndWaitNotify = new ConditionalAndWaitNotify();
        Thread a = new Thread() {
            @Override
            public void run() {
                try {
                    conditionalAndWaitNotify.use(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread b = new Thread() {
            @Override
            public void run() {
                try {
                    conditionalAndWaitNotify.use(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };


//        Thread c = new Thread() {
//            @Override
//            public void run() {
//                conditionalAndWaitNotify.start();
//            }
//        };

        a.start();
        b.start();
//        c.start();

        a.join();
        b.join();
//        c.join();
    }
}