package win.hgfdodo;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWSample {
    private final static int MAX = 10000;
    private final Map<String, String> m = new TreeMap<>();
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();
    private AtomicBoolean writeLocked = new AtomicBoolean(false);
    private AtomicBoolean readLocked = new AtomicBoolean(false);

    public RWSample() {
        for (int i = 0; i < MAX; i++) {
            m.put(String.valueOf(i), i + "");
        }
    }

    public String get(String key) {
        r.lock();
        if (writeLocked.get()) {
            System.out.println("read lock get when write lock ");
        }
        if (readLocked.getAndSet(true)) {
            System.out.println("read lock can reentrant!");
        }
        System.out.println(System.nanoTime() + "：" + Thread.currentThread() + ":读锁定");
        try {
            return m.get(key);
        } finally {
            readLocked.set(false);
            r.unlock();
        }
    }

    public String put(String key, String entry) {

        w.lock();
        if (writeLocked.getAndSet(true)) {
            System.out.println("write lock can reetrant!");
        }
        System.out.println(System.nanoTime() + "：" + Thread.currentThread() + ":写锁定");
        try {
            return m.put(key, entry);
        } finally {
            writeLocked.set(false);
            w.unlock();
        }
    }

    public static void main(String[] args) {
        int n = Runtime.getRuntime().availableProcessors();
        final Random random = new Random();

        try {
            final RWSample sample = new RWSample();

            Thread read = new Thread() {
                @Override
                public void run() {
                    ExecutorService readerPool = Executors.newFixedThreadPool(n);
                    for (int i = 0; i < MAX; i++) {
                        readerPool.submit(new Runnable() {
                            public void run() {
                                String s = null;
                                s = sample.get(random.nextInt(MAX % 53) + "");
                                System.out.println(random.nextInt(0));
                            }
                        });
                    }

                    readerPool.shutdown();
                }
            };

            Thread write1 = new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        try {
                            sample.put("" + i, "hgf" + i);
                            Thread.sleep(random.nextInt(20));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            Thread write2 = new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        try {
                            sample.put("" + i, "hgf" + i);
                            Thread.sleep(random.nextInt(20));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            read.start();
            write1.start();
            write2.start();
            write1.join();
            write2.join();
            System.out.println("write over!");
            read.join();


        } catch (Exception e) {

        }
    }
}
