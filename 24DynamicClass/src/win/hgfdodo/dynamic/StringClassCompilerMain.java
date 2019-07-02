package win.hgfdodo.dynamic;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class StringClassCompilerMain {
    public static void main(String[] args) throws IOException, URISyntaxException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String contents = new String(
                "package win.hgfdodo.dynamic;" +
                        "class CalculatorTest {\n" +
                        "  public void testMultiply() {\n" +
                        "    Calculator c = new Calculator();\n" +
                        "    System.out.println(c.multiply(2, 4));\n" +
                        "  }\n" +
                        "  public static void main(String[] args) {\n" +
                        "    CalculatorTest ct = new CalculatorTest();\n" +
                        "    ct.testMultiply();\n" +
                        "  }\n" +
                        "}\n");
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
        StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(null, null, null);
        SimpleJavaFileManager fileManager = new SimpleJavaFileManager(standardJavaFileManager);
        JavaFileObject testFile = new StringJavaFileObject("win.hgfdodo.dynamic.CalculatorTest", contents);
        Iterable<? extends JavaFileObject> classes = Arrays.asList(testFile);
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, fileManager, collector, null, null, classes);
        if (task.call()) {
            System.out.println("success");
        } else {
            System.out.println("failure!");
        }

        List<Diagnostic<? extends JavaFileObject>> diagnostics = collector.getDiagnostics();
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics) {
            System.out.println("line:" + diagnostic.getLineNumber());
            System.out.println("msg:" + diagnostic.getMessage(Locale.ENGLISH));
            System.out.println("source:" + diagnostic.getSource());

        }
        List<ClassJavaFileObject> classJavaFileObjects = fileManager.getClassJavaFileObjects();
        fileManager.close();


        CompiledClassLoader compiledClassLoader = new CompiledClassLoader(classJavaFileObjects);
        Class clz = compiledClassLoader.loadClass("win.hgfdodo.dynamic.CalculatorTest");
        Method method = clz.getMethod("main", String[].class);
        method.invoke(null, new Object[]{null});
    }

}

class StringJavaFileObject extends SimpleJavaFileObject {
    private String code;

    protected StringJavaFileObject(String name, String code) {
        super(URI.create("string:///" + name.replaceAll("\\.", "/") + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return code;
    }
}

class ClassJavaFileObject extends SimpleJavaFileObject {
    private ByteArrayOutputStream byteArrayOutputStream;
    private final String className;

    protected ClassJavaFileObject(String className, Kind kind) {
        super(URI.create("mem:///" + className.replaceAll("\\.", "/") + kind.extension), kind);
        this.className = className;
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return this.byteArrayOutputStream;
    }

    //TODO: byteArrayOutputStream怎么绑定的？
    public byte[] getBytes() {
        return this.byteArrayOutputStream.toByteArray();
    }

    public String getClassName() {
        return this.className;
    }
}

class SimpleJavaFileManager extends ForwardingJavaFileManager {

    private final List<ClassJavaFileObject> classJavaFileObjects;

    protected SimpleJavaFileManager(JavaFileManager fileManager) {
        super(fileManager);
        classJavaFileObjects = new ArrayList<>();
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        ClassJavaFileObject classJavaFileObject = new ClassJavaFileObject(className, kind);
        classJavaFileObjects.add(classJavaFileObject);
        return classJavaFileObject;
    }

    public List<ClassJavaFileObject> getClassJavaFileObjects() {
        return this.classJavaFileObjects;
    }
}


class CompiledClassLoader extends ClassLoader {
    private final List<ClassJavaFileObject> fileObjects;

    CompiledClassLoader(List<ClassJavaFileObject> fileObjects) {
        this.fileObjects = fileObjects;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Iterator<ClassJavaFileObject> it = fileObjects.iterator();
        while (it.hasNext()) {
            ClassJavaFileObject fileObject = it.next();
            if (fileObject.getName().equals(name)) {
                it.remove();
                byte[] bytes = fileObject.getBytes();
                return super.defineClass(name, bytes, 0, bytes.length);
            }
        }

        return super.findClass(name);
    }
}