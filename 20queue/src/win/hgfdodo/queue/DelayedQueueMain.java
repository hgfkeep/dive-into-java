package win.hgfdodo.queue;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedQueueMain {
    public static void main(String[] args) throws InterruptedException {
        Comp a = new Comp(5);
        Comp b = new Comp(8);
        Comp c = new Comp(6);
        List x = Arrays.asList(a, b, c);
        Collections.sort(x);
        System.out.println(x);
        System.out.println(a.compareTo(b));


        Comparator comparator = new Comparator<Comp>() {
            @Override
            public int compare(Comp o1, Comp o2) {
                return o1.compareTo(o2);
            }
        };

        System.out.println(comparator.compare(a, b));

        DelayQueue<DelayedTask> delayQueue = new DelayQueue<DelayedTask>();
        delayQueue.put(new DelayedTask(Instant.now(), "task 1"));
        Thread.sleep(100);
        Instant now = Instant.now();
        delayQueue.put(new DelayedTask(now, "task 2"));
        delayQueue.put(new DelayedTask(now, "task 3"));
        delayQueue.put(new DelayedTask(now, "task 4"));
        delayQueue.put(new DelayedTask(Instant.now(), "task 5"));
        Thread.sleep(10);
        delayQueue.put(new DelayedTask(Instant.now(), "task 6"));
        Thread.sleep(10);
        delayQueue.put(new DelayedTask(Instant.now(), "task 7"));
        Thread.sleep(10);
        delayQueue.put(new DelayedTask(Instant.now(), "task 8"));

        while (delayQueue.size() > 0) {
            DelayedTask task = delayQueue.poll();
            if (task != null) {
                System.out.println(Instant.now() + ":" + task.getTask());
            }
        }
    }
}

class Comp implements Comparable<Comp> {
    private final int x;

    Comp(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    @Override
    public int compareTo(Comp o) {
        return this.x - o.getX();
    }

    @Override
    public String toString() {
        return "Comp{" +
                "x=" + x +
                '}';
    }
}

class DelayedTask implements Delayed {

    private final Instant time;
    private final String task;

    DelayedTask(Instant time, String task) {
        this.time = time;
        this.task = task;
    }

    public Instant getTime() {
        return this.time;
    }

    public String getTask() {
        return task;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(time.getEpochSecond() - Instant.now().getEpochSecond(), TimeUnit.SECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (o == this) {
            return 0;
        }
        if (o instanceof DelayedTask) {
            DelayedTask delayedTask = (DelayedTask) o;
            return (this.getTime().getEpochSecond() - delayedTask.getTime().getEpochSecond()) > 0 ? 1 :
                    (this.getTime().getEpochSecond() - delayedTask.getTime().getEpochSecond()) < 0 ? -1 : 0;
        }
        long diff = this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
        return (diff < 0) ? -1 : (diff > 0) ? 1 : 0;
    }
}
