package win.hgfdodo.classloader;

public class InitMain {

    public static final int b = 10;
    public static int a = 100;
    public static final int INT_CONSTANT = 1000;
    public static final Integer INTEGER_CONSTANT = Integer.valueOf(10000);

    public InitMain() {
        System.out.println("cons:" + a);
        System.out.println("cons:" + b);
        System.out.println("cons:" + INT_CONSTANT);
        System.out.println("cons:" + INTEGER_CONSTANT);
    }

    public static void main(String[] args) {
        System.out.println(new InitMain());
    }
}
