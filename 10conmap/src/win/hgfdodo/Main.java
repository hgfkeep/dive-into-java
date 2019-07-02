package win.hgfdodo;

import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> a = new ConcurrentHashMap();
//        a.put("a", 1);
        a.put("b", 2);
        Integer x = a.putIfAbsent("a", 3);

        System.out.println(x);
        System.out.println(a);

    }
}
