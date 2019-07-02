package win.hgfdodo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();
        InvocationHandler dynamicHandler = new DynamicSubject(realSubject);
        Subject subject = (Subject) Proxy.newProxyInstance(RealSubject.class.getClassLoader(), RealSubject.class.getInterfaces(), dynamicHandler);
        System.out.println(subject.operations("hgf"));
    }
}

interface Subject {
    public String operations(String args);
}

class RealSubject implements Subject {

    @Override
    public String operations(String args) {
        return System.nanoTime() + " hello, " + args + "!";
    }
}

class DynamicSubject implements InvocationHandler {
    private Subject subject;

    public DynamicSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(System.nanoTime() + " Before say hello");
        Object result = method.invoke(subject, args);
        System.out.println(System.nanoTime() + " After say hello");
        return result;
    }
}