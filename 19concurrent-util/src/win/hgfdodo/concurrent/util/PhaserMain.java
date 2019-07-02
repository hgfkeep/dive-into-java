package win.hgfdodo.concurrent.util;

import java.util.concurrent.Phaser;

public class PhaserMain {
    public static void main(String[] args) {
        Phaser phaser = new Phaser();
        PhaserWorker[] phaserWorkers = new PhaserWorker[6];
        for (int i = 1; i < 6; i++) {
            phaserWorkers[i] = new PhaserWorker(String.valueOf(i), phaser);
            phaser.register();
        }
        phaserWorkers[0] = new SpecialWorker(String.valueOf(0), phaser);

        Thread[] threads = new Thread[6];
        for (int i = 0; i < 6; i++) {
            threads[i] = new Thread(phaserWorkers[i]);
            threads[i].start();
        }


    }
}

class SpecialWorker extends PhaserWorker {

    SpecialWorker(String name, Phaser phaser) {
        super(name, phaser);
    }

    @Override
    public void run() {
        //如果phase不是Phaser当前的phase，则会立即返回。
        int phase = phaser.awaitAdvance(2);
        log("arrived @ phase " + phase);

    }
}

class PhaserWorker implements Runnable {
    private String name;
    protected Phaser phaser;

    PhaserWorker(String name, Phaser phaser) {
        this.name = name;
        this.phaser = phaser;
    }

    @Override
    public void run() {
        log("starting");
        int phase = phaser.arriveAndAwaitAdvance();
        log("phase " + phase);
        phase1();
        phase = phaser.arriveAndAwaitAdvance();
        log("phase " + phase);
        phase2();
        phase = phaser.arriveAndAwaitAdvance();
        log("phase " + phase);
    }

    protected void log(String msg) {
        if (name == null) {
            name = Thread.currentThread().getName();
        }
        System.out.println(name + " : " + msg);
    }

    private void phase1() {
        log("system status in phase1!");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void phase2() {
        log("system status in phase2!");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
