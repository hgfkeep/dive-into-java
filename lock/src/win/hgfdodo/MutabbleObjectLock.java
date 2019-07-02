package win.hgfdodo;

import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * 测试可变对象作为锁的安全问题
 * 由于CPU速度太快，循环次数太少可能不会出现问题
 */
public class MutabbleObjectLock {

    private int count = 0;
    private Random random = new Random();

    //锁
    private Integer lock = new Integer(1);

    public void inc() {
        synchronized (lock) {
            //改变下个线程需要获取的锁
            lock = random.nextInt(3);
            count++;
        }

    }

    //锁没有改变
    public void addOne() {
        synchronized (lock) {
            count++;
        }
    }

    public int getCount() {
        return count;
    }

    static class MultiAdder implements Runnable {
        private MutabbleObjectLock mutabbleObjectLock;

        MultiAdder(MutabbleObjectLock mutabbleObjectLock) {
            this.mutabbleObjectLock = mutabbleObjectLock;
        }

        @Override
        public void run() {
            //由于CPU速度太快，循环次数太少可能不会出现问题
            for (int i = 0; i < 10000; i++) {
                mutabbleObjectLock.inc();
            }
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int n = Runtime.getRuntime().availableProcessors();
        MutabbleObjectLock obj = new MutabbleObjectLock();

        Thread a = new Thread(new MultiAdder(obj));
        Thread b = new Thread(new MultiAdder(obj));
        Thread c = new Thread(new MultiAdder(obj));
        a.start();
        b.start();
        c.start();
        a.join();
        b.join();
        c.join();
        System.out.println(obj.getCount());
    }

}
