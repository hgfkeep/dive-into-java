package win.hgfdodo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        MyClassLoader loader = new MyClassLoader();
        try {
            Class clz = loader.loadClass("System");
            clz.getPackage();
            Object system = clz.getConstructor().newInstance();
            Method method = clz.getMethod("getX");
            Object o = method.invoke(system, method);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

class MyClassLoader extends ClassLoader {
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        String root = "/Users/guangfuhe/Projects/learn/java/into-java/loadsystem/resources";
        String path = root+File.separator+name.replaceAll("\\.", "/")+".java";
        try {
            FileInputStream fin = new FileInputStream(new File(path));
            int len = fin.available();
            byte[] bytes = new byte[len];
            fin.read(bytes);
            return super.defineClass(name, bytes, 0, bytes.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}