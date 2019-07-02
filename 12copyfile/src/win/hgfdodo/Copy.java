package win.hgfdodo;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Copy {
    public static void copy(File src, File dest) {
        try (FileInputStream fi = new FileInputStream(src);
             FileOutputStream fo = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fi.read(buffer)) > 0) {
                fo.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyNio2(File src, File dest) {
        try (FileChannel srcChannel = new FileInputStream(src).getChannel();
             FileChannel destChannel = new FileOutputStream(dest).getChannel()) {
            for (long size = srcChannel.size(); size > 0; ) {
                long transfered = srcChannel.transferTo(srcChannel.position(), size, destChannel);
                size -= transfered;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyNioDirect(File src, File dest) {
        try (FileChannel srcChannel = new FileInputStream(src).getChannel();
             FileChannel destChannel = new FileOutputStream(dest).getChannel()) {
            long size = srcChannel.size();
            long unit = 1024;
            long pos = 0;
            while (pos < size) {
                MappedByteBuffer buffer = srcChannel.map(FileChannel.MapMode.READ_ONLY, pos, unit);
                pos += unit;
                destChannel.write(buffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String src = "";
        String dest = "";
        int n = 1;
        if (args.length >= 2) {
            src = args[0];
            dest = args[1];
            if (args.length == 3) {
                n = Integer.parseInt(args[2]);
            }
        }
        File s = new File(src);

        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            copy(s, new File(dest + i));
        }
        long end = System.nanoTime();

        System.out.println("avg:\t" + ((end - start) / n));

        start = System.nanoTime();
        for (int i = n; i < 2 * n; i++) {
            copy(s, new File(dest + i));
        }
        end = System.nanoTime();
        System.out.println("nio avg:\t" + ((end - start) / n));

        start = System.nanoTime();
        for (int i = n; i < 2 * n; i++) {
            copyNioDirect(s, new File(dest + i));
        }
        end = System.nanoTime();
        System.out.println("nio avg:\t" + ((end - start) / n));
    }
}
