package win.hgfdodo;

import java.util.concurrent.locks.ReentrantLock;

public class Lock {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.tryLock();
    }
}
