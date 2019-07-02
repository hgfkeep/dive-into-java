package win.hgfdodo;

public class DeadLock {
    public static void main(String[] args) {
        String x = "1";
        String y = "2";
        Thread t1 = new Thread(new MyTask(x, y));
        Thread t2 = new Thread(new MyTask(y, x));

        t1.start();
        t2.start();

    }
}


class MyTask implements Runnable {

    private final String a;
    private final String b;

    MyTask(String a, String b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        synchronized (a) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (b) {
                System.out.println("entered!");
            }
        }
    }
}