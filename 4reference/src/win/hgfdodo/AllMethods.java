package win.hgfdodo;

import java.lang.reflect.Method;
import java.util.List;

public class AllMethods {
    public static void main(String[] args) {
       Method[] methods =  List.class.getMethods();
       methods[0].getParameters();

    }
}
