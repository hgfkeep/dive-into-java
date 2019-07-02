package win.hgfdodo;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class Main {

    public static void main(String[] args) {
//        Integer i = 129;
        Integer i = new Integer(1);
        ReferenceQueue<Integer> q = new ReferenceQueue();
        PhantomReference p = new PhantomReference(i, q);
        i = null;
        System.gc();

        try {
            Reference ref = q.remove(1000);
            if(ref!=null){
                System.out.println(ref.hashCode());
            }else {
                System.out.println("null");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
