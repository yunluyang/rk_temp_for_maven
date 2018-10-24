package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
public class TcpServer extends Thread{
    Socket clientSocket;
    public TcpServer(Socket clientSocket) {
        super();
        this.clientSocket = clientSocket;
    }
    @Override
      public void run() {
        try {
            //获得客户端的ip地址和主机名
            String clientAddress = clientSocket.getInetAddress().getHostAddress();
            String clientHostName = clientSocket.getInetAddress().getHostName();
            System.out.println(clientHostName + "(" + clientAddress + ")" + " 连接成功!");
            System.out.println("Now, 传输图片数据...........");
            long startTime = System.currentTimeMillis();
            //获取客户端的OutputStream
            OutputStream out = clientSocket.getOutputStream();
            //传出图片数据
            FileInputStream in = new FileInputStream(new File("/home/gavinzhou/test.jpg"));
            byte[] data = new byte[4096];
            int length = 0;
            while((length = in.read(data)) != -1){
                out.write(data, 0, length);
                //写出数据
            }
            long endTime = System.currentTimeMillis();
            //提示信息
            System.out.println(clientHostName + "(" + clientAddress + ")" + " 图片传输成功," + "用时:" + ((endTime-startTime)) + "ms");
            //关闭资源
            in.close();
            clientSocket.close();
            System.out.println("连接关闭!");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        //建立TCP连接服务,绑定端口
        ServerSocket tcpServer = new ServerSocket(8899);
        //接受连接,传图片给连接的客户端,每个TCP连接都是一个java线程
        while(true){
            Socket clientSocket = tcpServer.accept();
            new TcpServer(clientSocket).start();
        }
    	
    	
    }
}