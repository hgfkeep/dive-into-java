package win.hgfdodo.concurrent.util;

import java.util.concurrent.Semaphore;

public class AbnormalSemaphoreMain {
    public static int SEMAPHORRE_PERMITS = 5;

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(SEMAPHORRE_PERMITS);
        for (int i = 0; i < 2* SEMAPHORRE_PERMITS; i++) {
            new Thread(new SemaphoreWork2(semaphore)).start();
        }

        System.out.println("GO!!!");
        semaphore.release(SEMAPHORRE_PERMITS);
        while (semaphore.availablePermits() != 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        semaphore.release(SEMAPHORRE_PERMITS);
    }
}


class SemaphoreWork2 implements Runnable {
    private Semaphore semaphore;

    public SemaphoreWork2(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println("acquired permit!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}