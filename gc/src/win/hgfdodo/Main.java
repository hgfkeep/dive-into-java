package win.hgfdodo;

import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("aaa");
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        System.out.println(pid);
        Thread.sleep(10000);
        A a = A.get();
    }

}

