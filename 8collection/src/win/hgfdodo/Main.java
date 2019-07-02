package win.hgfdodo;

import com.sun.tools.javac.util.List;

import java.util.LinkedHashSet;

public class Main {
    public static void main(String[] args) {
        LinkedHashSet set = new LinkedHashSet();
        List<String> s = List.of("a", "b");
        s = s.append("c");
        s = s.append("d");
        System.out.println(s);
    }
}
