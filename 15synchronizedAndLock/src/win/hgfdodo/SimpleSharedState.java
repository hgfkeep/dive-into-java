package win.hgfdodo;

public class SimpleSharedState {
    public int sharedState;

    public void action() {
        while (sharedState < 100000) {
            synchronized (this) {
                int former = sharedState++;
                int latter = sharedState;
                if (former != latter - 1) {
                    System.out.printf("Observed data race: former: " + former + " latter: " + latter);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleSharedState simpleSharedState = new SimpleSharedState();

        Thread a = new Thread() {
            @Override
            public void run() {
                simpleSharedState.action();
            }
        };

        Thread b = new Thread() {
            @Override
            public void run() {
                simpleSharedState.action();
            }
        };
        a.start();
        b.start();
        a.join();
        b.join();
    }
}
