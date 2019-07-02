package win.hgfdodo.concurrent.util;

import java.util.concurrent.Semaphore;

public class SemaphoreMain {
    public static int SEMAPHORRE_PERMITS = 5;
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(SEMAPHORRE_PERMITS);
        for (int i = 0; i < 100; i++) {
            new Thread(new SemaphoreWorker(semaphore)).start();
        }
    }
}


class SemaphoreWorker implements Runnable {
    private String name;
    private Semaphore semaphore;

    public SemaphoreWorker(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            log("wait for permit! available: " + semaphore.availablePermits());
            semaphore.acquire();
            doPermitWork();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log("releasing permit");
            semaphore.release();
            log("released permit");
        }
    }

    private void log(String msg) {
        if (name == null) {
            name = Thread.currentThread().getName();
        }
        System.out.println(System.currentTimeMillis() + ":" + name + " : " + msg);
    }

    private void doPermitWork() {
        System.out.println("available permits: "+ semaphore.availablePermits());
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}