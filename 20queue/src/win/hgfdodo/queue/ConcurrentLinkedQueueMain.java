package win.hgfdodo.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueMain {

    public static void main(String[] args) {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
        new Thread(new ConProducer(queue)).start();
        new Thread(new ConConsumer(queue)).start();
    }
}

class ConProducer implements Runnable {
    private ConcurrentLinkedQueue<String> queue;

    ConProducer(ConcurrentLinkedQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            boolean flag = false;
            while (!flag) {
                flag = queue.offer(String.valueOf(i));
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


class ConConsumer implements Runnable {
    private ConcurrentLinkedQueue<String> queue;

    ConConsumer(ConcurrentLinkedQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            String s = null;
            while (s == null) {
                s = queue.poll();
            }
            System.out.println("Get data: " + s);
        }
    }
}