package win.hgfdodo;

class A {
    private int[] x;

    private A() {
    }

    private A(int l) {
        x = new int[l];
    }

    public static A get() {
        return AHolder.a;
    }

    private static class AHolder {
        private static final A a = new A(2048 * 2048 * 50);
    }
}
