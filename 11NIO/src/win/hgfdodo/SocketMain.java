package win.hgfdodo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketMain extends Thread {

    private ServerSocket serverSocket;

    public synchronized int getPort() {
        int port = 0;
        synchronized (this){
            serverSocket.getLocalPort();
        }
        System.out.println("xxx");
        return serverSocket.getLocalPort();
    }

    public  void run() {
        try {
            synchronized (this) {
                System.out.println("before");
                serverSocket = new ServerSocket(0);
                System.out.println("after");
            }
            while (true) {
                Socket socket = serverSocket.accept();
                RequestHandler handler = new RequestHandler(socket);
                handler.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        SocketMain socketMain = new SocketMain();
        socketMain.start();
        try (Socket client = new Socket(InetAddress.getLocalHost(), socketMain.getPort())) {
            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            br.lines().forEach(line -> System.out.println(line));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


class RequestHandler extends Thread{
    private Socket socket;
    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            PrintWriter printWriter = new PrintWriter(this.socket.getOutputStream());
            printWriter.println("Hello World!");
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
