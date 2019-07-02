package win.hgfdodo;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RunException {
    public static void main(String[] args) {

        ExecutorService readerPool = Executors.newFixedThreadPool(3);
        Future future = readerPool.submit(new Runnable() {
            public void run() {
                throw new RuntimeException("异常");
            }
        });

        readerPool.shutdown();
    }
}
