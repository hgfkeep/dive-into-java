package win.hgfdodo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AIOMain extends Thread {
    @Override
    public void run() {
        AsynchronousChannelGroup group = null;
        try {
            group = AsynchronousChannelGroup.withThreadPool(Executors.newFixedThreadPool(4));
            AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(group).bind(new InetSocketAddress("0.0.0.0", 8013));
            server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                @Override
                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    server.accept(null, this);
                    Future<Integer> future = result.write(Charset.defaultCharset().encode("hello world!"));
                    try {
                        future.get();
                        result.close();
                        ;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println(exc);
                }
            });

            group.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        AsynchronousChannelGroup group = null;
//        try {
//            group = AsynchronousChannelGroup.withThreadPool(Executors.newFixedThreadPool(4));
//            try (AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(group)
//                    .bind(new InetSocketAddress(InetAddress.getLocalHost(), 8013))) {
//                server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
//                    @Override
//                    public void completed(AsynchronousSocketChannel result, Object attachment) {
//                        server.accept(null, this);
//                        try {
//                            sayHelloWorld(result, Charset.defaultCharset().encode("hello world, aio"));
//                        } catch (ExecutionException e) {
//                            e.printStackTrace();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    private void sayHelloWorld(AsynchronousSocketChannel result, ByteBuffer encode) throws ExecutionException, InterruptedException, IOException {
//                        System.out.println("say");
//                        Future<Integer> future = result.write(encode);
//                        future.get();
//                        result.close();
//                    }
//
//                    @Override
//                    public void failed(Throwable exc, Object attachment) {
//
//                    }
//                });
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            //此处不适合在这里阻塞，因为上面使用的是try resources 模式，那么try执行结束后，打开的resource就关闭了，阻塞必须在try中。
//            group.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
//            System.out.println("out:");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public static void main(String[] args) {
        AIOMain aioMain = new AIOMain();
        aioMain.start();

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try (Socket client = new Socket(InetAddress.getLocalHost(), 8013)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            br.lines().forEach(line -> System.out.println(line));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
