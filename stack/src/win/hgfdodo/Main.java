package win.hgfdodo;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class Main {
    public static void main(String[] args) {

        Object o = new Object();
        Thread t1 = new Thread(new Task(o), "mythread-a");
        Thread t2 = new Thread(new Task(o), "mythread-b");
        t1.start();
        t2.start();
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println(threadInfo);
        }

    }
}

class Task implements Runnable {
    private final Object o;

    Task(Object o) {
        this.o = o;
    }

    @Override
    public void run() {
        synchronized (o) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}