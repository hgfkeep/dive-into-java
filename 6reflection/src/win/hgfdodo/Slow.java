package win.hgfdodo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

public class Slow {
    private Random random = new Random();

    public int hello(Integer i) {
        return random.nextInt()+i;
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        System.out.println("test a");
        testA();
        System.out.println("\r\n\r\ntestb");
        testB();
    }

    public static void testA() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Slow s = new Slow();
        long start = System.currentTimeMillis();
        for(int i = 0; i < 1000000; i++){
            s.hello(i);
        }
        System.out.println((System.currentTimeMillis() - start));

        Class c = Slow.class;
        Object rs = c.newInstance();
        Method method = c.getMethod("hello", Integer.class);
        start = System.currentTimeMillis();
        for(int i = 0; i < 1000000; i++){
            method.invoke(rs, i);
        }
        System.out.println((System.currentTimeMillis() - start));
    }

    public static void testB() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        long start = System.currentTimeMillis();
        for(int i = 0; i < 1000000; i++){
            new Slow(   ).hello(i);
        }
        System.out.println((System.currentTimeMillis() - start));


        start = System.currentTimeMillis();
        for(int i = 0; i < 1000000; i++){
            Object rs = Slow.class.newInstance();
            Method method = Slow.class.getMethod("hello", Integer.class);
            method.invoke(rs, i);
        }
        System.out.println((System.currentTimeMillis() - start));
    }
}
