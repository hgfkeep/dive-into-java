package win.hgfdodo.vs;

import java.time.Instant;

public class MyObject {
    private int[] bits = new int[1000];
    private final String name;

    public MyObject(String name) {
        this.name = name;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.printf(Instant.now() + " Finalize: %s\n", name);
    }
}
