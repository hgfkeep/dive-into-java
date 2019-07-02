package win.hgfdodo.init;

public class Son extends Father {
    private int i = test();
    private static int j = method();

    static {
        System.out.println("(6)");
    }

    public Son() {
        System.out.println("(7)");
    }

    {
        System.out.println("(8)");
    }

    public int test() {
        System.out.println("(9)");
        return 1;
    }

    public static int method() {
        System.out.println("(10)");
        return 1;
    }

    public static void main(String[] args) {
        Son s1 = new Son();
        /**
         * 1. Load Father
         *  (5)(1)
         * 2. Load Son
         *  (10)(6)
         *
         *  now Father.class and Son.class in JVM
         *
         * 3. init Father
         *  (4)(3)(2)
         * 4. init Son
         *  (9)(8)(7)
         *
         */


        System.out.println();
        Son s2 = new Son();

    }
}
