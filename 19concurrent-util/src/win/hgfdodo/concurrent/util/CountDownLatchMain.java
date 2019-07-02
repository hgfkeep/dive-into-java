package win.hgfdodo.concurrent.util;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchMain {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            new Thread(new ArriveOne(countDownLatch)).start();
        }
        try {
            countDownLatch.await();
            System.out.println("all arrived, go!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class ArriveOne implements Runnable {
    private CountDownLatch countDownLatch;

    public ArriveOne(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("arrived one");
        countDownLatch.countDown();
        System.out.println("do something after arrive");
    }
}