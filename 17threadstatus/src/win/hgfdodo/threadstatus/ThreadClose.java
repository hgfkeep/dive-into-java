package win.hgfdodo.threadstatus;

public class ThreadClose {
    public static void main(String[] args) {
        Task task = new Task();
        task.start();
        System.out.println("thread started...");
        task.interrupt();
        System.out.println("thread interrupted!");

    }

}


class Task extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println("here!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted!: " + e);
            }
        }
    }
}