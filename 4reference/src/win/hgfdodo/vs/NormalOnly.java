package win.hgfdodo.vs;

import java.lang.ref.Reference;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NormalOnly {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            MyObject myObject = new MyObject("normal " + i);
            Thread.sleep(10);
        }
        printReferences(null);
    }

    public static void printReferences(List<Reference<MyObject>> references) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("---print references---");
            if (references != null) {
                references.stream().forEach(NormalOnly::printReference);
            }
        });
    }

    private static void printReference(Reference<MyObject> myObjectReference) {
        System.out.printf("Reference: %s [%s]\n", myObjectReference.get(), myObjectReference.getClass().getSimpleName());
    }


}

