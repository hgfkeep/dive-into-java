package win.hgfdodo.dynamic;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CompileMain {

    public static void main(String[] args) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(null, null, null, "win.hgfdodo.dynamic.test.java");
        System.out.println(result == 0 ? "编译成功" : "编译失败");

        Process process = Runtime.getRuntime().exec("java win.hgfdodo.dynamic.test");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            System.out.println(str);
        }
    }
}
