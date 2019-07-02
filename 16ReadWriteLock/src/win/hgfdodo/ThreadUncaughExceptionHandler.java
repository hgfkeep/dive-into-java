package win.hgfdodo;

public class ThreadUncaughExceptionHandler {
    public static void main(String[] args) {
//        oneThreadUncaughtExceptionHandler();
        defaultThreadUncaughtExceptionHandler();
    }

    public static void oneThreadUncaughtExceptionHandler() {
        Thread t1 = new Thread(() -> {
            throw new RuntimeException(" t1 runtime exception");
        }, "t1");
        t1.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(Thread.currentThread() + "trigger uncaugh exception handler");
            }
        });

        t1.start();
        Thread t2 = new Thread(() -> {
            throw new RuntimeException(" t2 runtime exception");
        }, "t2");
        t2.start();
    }

    public static void defaultThreadUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(Thread.currentThread() + "trigger uncaugh exception handler");
            }
        });
        new Thread(() -> {
            throw new RuntimeException(" t1 runtime exception");
        }, "t1").start();
        new Thread(() -> {
            throw new RuntimeException(" t2 runtime exception");
        }, "t2").start();
    }
}


//class MyUncaughExceptionHandler implements Thread.UncaughtExceptionHandler {
//    private Thread.UncaughtExceptionHandler defaultHandler;
//
//    public MyUncaughExceptionHandler() {
//        this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
//    }
//
//    @Override
//    public void uncaughtException(Thread t, Throwable e) {
//        System.out.println("trigger uncaugh exception handler");
//    }
//}