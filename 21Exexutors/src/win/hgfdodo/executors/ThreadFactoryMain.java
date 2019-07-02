package win.hgfdodo.executors;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadFactoryMain {
    public static void main(String[] args) throws InterruptedException {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(Thread.currentThread().hashCode() + "-[uncaught exception]: " + t.getName() + ", " + e);
            }
        });

        ExecutorService executorService = Executors.newFixedThreadPool(3, new MyThreadFactory());
        System.out.println("before task submit!");
//        executorService.execute(new ExceptionTask());
//        Thread.sleep(3000);

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(System.nanoTime() + " normal task!");
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("normal task!");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
//        Thread.sleep(1000);
//
//        executorService.execute(new ExceptionTask());
//        Thread.sleep(1000);
//        executorService.execute(new ExceptionTask());
//        Thread.sleep(1000);
//        executorService.execute(new ExceptionTask());
//        Thread.sleep(1000);
        System.out.println("prepare to shutdown!");
        executorService.shutdown();
        System.out.println(Thread.currentThread().hashCode() + ":shuting down!");
    }
}


class ExceptionTask implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().hashCode() + ":throw exception now!");
        throw new RuntimeException();
    }
}


class MyThreadFactory implements ThreadFactory {
    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable r) {
        int num = count.incrementAndGet();
        Thread x = new Thread(r);
        System.out.println(Thread.currentThread().hashCode() + ":use " + num + ", create thread: " + x.hashCode() + "---" + System.nanoTime());
        return x;
    }
}