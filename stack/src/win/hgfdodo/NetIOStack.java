package win.hgfdodo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.net.URL;

/**
 * 测试网络IO时，堆栈信息中线程状态。
 */
public class NetIOStack {
    public static void main(String[] args) throws IOException {
        String url = "https://github.com/atom/atom/releases/download/v1.38.2/atom-amd64.tar.gz";
        Thread t = new Thread(new Download(url), "mythread");
        t.start();
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println(threadInfo);
        }
    }
}

class Download implements Runnable {
    private final String url;

    Download(String url) {
        this.url = url;
    }

    @Override
    public void run() {

        InputStream in = null;
        FileOutputStream fileOutputStream = null;
        try {
            URL url = new URL(this.url);
            in = url.openStream();
            fileOutputStream = new FileOutputStream(new File("saved"));
            byte[] buffer = new byte[2014];
            int read = -1;
            while ((read = in.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, read);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}