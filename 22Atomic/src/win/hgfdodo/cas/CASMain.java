package win.hgfdodo.cas;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class CASMain {

    public static void main(String[] args) throws Throwable {
        AtomicReference<AtomicBTreePartition> atomicReference = new AtomicReference<>();
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(AtomicBTreePartition.class, Long.class, "lock");
        MethodHandle methodHandle = MethodHandles.lookup().findSetter(AtomicBTreePartition.class, "lock", Long.class);
        AtomicBTreePartition partition = new AtomicBTreePartition();
        methodHandle = methodHandle.bindTo(partition);
        methodHandle.invoke(10L);

        System.out.println(partition);
    }
}