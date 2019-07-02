package win.hgfdodo;

import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
//        最久未被访问元素的淘汰
        LinkedHashMap<String, String> access = new LinkedHashMap<String, String>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > 4;
            }
        };
        access.put("1", "project1");
        access.put("2", "project2");
        access.put("3", "project3");
        access.put("4", "project4");

        access.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });

        access.get("2");
        access.get("2");
        access.get("3");
        System.out.println("After access:");
        access.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });

        access.put("6", "Project 6");
        System.out.println("insert one more:");
        access.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
    }
}
