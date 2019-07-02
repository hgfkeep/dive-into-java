package win.hgfdodo.threadstatus;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        System.out.println("jstat -gcutil "+pid+" 100");
        Thread.sleep(15000);


        CountDownLatch countDownLatch = new CountDownLatch(101);

        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new MyTheard(countDownLatch));
            t.start();
            countDownLatch.countDown();
        }


    }
}

class MyTheard implements Runnable {
    private Random random = new Random();
    private final CountDownLatch countDownLatch;

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    MyTheard(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 10000; i++) {
            stringBuffer.append(random.nextInt(1000));
        }
        threadLocal.set(stringBuffer.toString());
        try {
            this.countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
