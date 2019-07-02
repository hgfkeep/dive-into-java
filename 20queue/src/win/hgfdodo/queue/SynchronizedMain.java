package win.hgfdodo.queue;

import java.util.concurrent.SynchronousQueue;

//程序线程接力
public class SynchronizedMain {
    public static void main(String[] args) {
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();
        new Thread(new Logger(synchronousQueue)).start();
        new Thread(new Appender(synchronousQueue)).start();
    }

}


class Logger implements Runnable {
    private SynchronousQueue<String> synchronousQueue;

    Logger(SynchronousQueue<String> synchronousQueue) {
        this.synchronousQueue = synchronousQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                synchronousQueue.put("info " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("add log:" + i + ", ");
        }
        System.out.println("logger out!");
    }
}

class Appender implements Runnable {
    private SynchronousQueue<String> synchronousQueue;

    Appender(SynchronousQueue<String> synchronousQueue) {
        this.synchronousQueue = synchronousQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            String log = null;
            try {
                log = synchronousQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("appender: " + log);
        }
        System.out.println("appender out!");
    }
}