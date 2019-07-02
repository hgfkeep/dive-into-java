package win.hgfdodo.dynamic;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.util.Arrays;

public class JavaFileManagerMain {
    public static void main(String[] args) {
        String fullQuanlifiedFileName = "win.hgfdodo.dynamic.".replaceAll("\\.", java.io.File.separator) + "Calculator.java";
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager =
                compiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> files =
                fileManager.getJavaFileObjectsFromStrings(
                        Arrays.asList(fullQuanlifiedFileName));
        JavaCompiler.CompilationTask task = compiler.getTask(
                null, fileManager, null, null, null, files);

        Boolean result = task.call();
        if (result == true) {
            System.out.println("Succeeded");
        }
    }
}
