package win.hgfdodo.concurrent.util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrrierMain {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("GO GO GO!");
            }
        });

        for(int i = 0; i < 5; i++){
            new Thread(new CyclicBarrierWorker(cyclicBarrier)).start();
        }

    }
}

class CyclicBarrierWorker implements Runnable {
    private String name;
    private CyclicBarrier cyclicBarrier;

    CyclicBarrierWorker(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {

        try {
            for (int i = 0; i < 3; i++) {
                log("cyclic barrier await!");

                cyclicBarrier.await();

                log("after await!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private void log(String msg) {
        if (name == null) {
            name = Thread.currentThread().getName();
        }
        System.out.println(name + " : " + msg);
    }
}