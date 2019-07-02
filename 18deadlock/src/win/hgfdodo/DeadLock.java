package win.hgfdodo;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DeadLock extends Thread {
    private String first;
    private String second;

    public DeadLock(String first, String second, String name) {
        super(name);
        this.first = first;
        this.second = second;
    }

    @Override
    public void run() {
        synchronized (first) {
            System.out.println(this.getName() + " botain:" + first);
            try {
                sleep(1000);

                synchronized (second) {
                    System.out.println(this.getName() + " obtain: " + second);
                }
            } catch (Exception e) {

            }

        }
    }

        public static void main(String[] args) throws InterruptedException {
            String f = "lockA";
            String s = "lockB";
            DeadLock deadLockA = new DeadLock(f, s, "A");
            DeadLock deadLockB = new DeadLock(s, f, "B");

            ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
            Runnable dlCheck = new Runnable() {
                @Override
                public void run() {
                    long[] threadIds = threadMXBean.findDeadlockedThreads();
                    if (threadIds != null) {
                        ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadIds);
                        System.out.println("Dead Lock detected: ");
                        for (ThreadInfo threadInfo : threadInfos) {
                            System.out.println(threadInfo);
                        }
                    }
                }
            };

            ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
            scheduledExecutorService.scheduleAtFixedRate(dlCheck, 0, 5, TimeUnit.SECONDS);

            deadLockA.start();
            deadLockB.start();
            deadLockA.join();
            deadLockB.join();
        }
}