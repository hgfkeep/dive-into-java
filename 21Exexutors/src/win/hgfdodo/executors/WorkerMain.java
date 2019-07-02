package win.hgfdodo.executors;

import java.util.concurrent.*;

public class WorkerMain {
    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(3, 3, 600, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), new MyThreadFactory());
        for (int i = 0; i < 20; i++) {
            final int x = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.print(Thread.currentThread().getName()+":"+x+":");
//                    if(x%3==0){
//                        throw new RuntimeException();
//                    }
                    for (int j = 0; j < x; j++) {
                        System.out.print("a");
                    }
                    System.out.println();
                }
            });
        }



    }
}
