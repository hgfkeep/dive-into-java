package win.hgfdodo.cas;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class AQSAndSynchronizedBenchmark {
    public static void main(String[] args) throws InterruptedException {
        int cpus = Runtime.getRuntime().availableProcessors();
        int n = 10000000;
        CountDownLatch countDownLatch = new CountDownLatch(n);
//        SychronizedCounter counter = new SychronizedCounter(countDownLatch);
        ReentrantLockCounter counter = new ReentrantLockCounter(countDownLatch, false);
        ExecutorService executorService = Executors.newFixedThreadPool(cpus);
        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            executorService.submit(counter);
        }
        countDownLatch.await();
        System.out.println("over: " + (System.nanoTime() - start) / 1000000);
        System.out.println("final x:" + counter.getX());
        executorService.shutdown();
    }
}

class SychronizedCounter implements Runnable {
    private int x;
    private final CountDownLatch countDownLatch;

    SychronizedCounter(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public synchronized void add() {
        x = x + 1;
    }

    @Override
    public void run() {
        add();
        countDownLatch.countDown();
    }

    public int getX() {
        return x;
    }
}

class ReentrantLockCounter implements Runnable {
    private int x;
    private final CountDownLatch countDownLatch;
    private final ReentrantLock lock;

    ReentrantLockCounter(CountDownLatch countDownLatch, boolean fair) {
        this.countDownLatch = countDownLatch;
        this.lock = new ReentrantLock(fair);
    }

    public void add() {
        try {
            lock.lock();
            x = x + 1;
        } finally {
            lock.unlock();
        }
    }


    @Override
    public void run() {
        add();
        countDownLatch.countDown();
    }

    public int getX() {
        return x;
    }
}