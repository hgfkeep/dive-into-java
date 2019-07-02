package win.hgfdodo;

import java.util.ArrayList;
import java.util.List;

public class AutoBox {
    public static void main(String[] args) {
        Integer i = 1;
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.remove(2);
        System.out.println(list);
    }

}
