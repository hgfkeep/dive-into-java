package win.hgfdodo.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExecutorMain {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
//        ExecutorService threadPool = Executors.newCachedThreadPool();
        int n = 100;
        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
        }
        Thread.sleep(3000);
        for (int i = 0; i < n; i++) {
            threadPool.submit(new NeverDoneTask());
        }
        System.out.println("wait task:" + n);
    }
}


class NeverDoneTask implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class ThreadLocleProblemTask implements Runnable {
    private ThreadLocal<String> local = new ThreadLocal<>();

    @Override
    public void run() {
        local.set("thread locale task result");
    }
}