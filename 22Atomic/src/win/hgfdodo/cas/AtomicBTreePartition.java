package win.hgfdodo.cas;

public class AtomicBTreePartition {
    private volatile Long lock;
    public void acquireLock(){}
    public void releaseeLock(){}

    @Override
    public String toString() {
        return "AtomicBTreePartition{" +
                "lock=" + lock +
                '}';
    }
}
