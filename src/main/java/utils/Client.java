package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Client extends Thread {

    //定义一个Socket对象
    Socket socket = null;
    private BufferedReader br;
    private BufferedWriter bw;
    public Client(String host, int port) {
        try {
            //需要服务器的IP地址和端口号，才能获得正确的Socket对象
            socket = new Socket(host, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        //客户端一连接就可以写数据个服务器了
        new sendMessThread().start();
        super.run();
        try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                try {
                    System.out.println("client write");
                    //010300000002C40B
                   /* bw.write(0X01);
                    bw.write(0X03);
                    bw.write(0X00);
                    bw.write(0X00);
                    bw.write(0X00);
                    bw.write(0X02);
                    bw.write(0xC4);
                    bw.write(0X0B);*/
                    bw.write("010300000002C40B");
                    // 注意：刷新是关键，否则会等到缓冲区满才将数据实际地发送出去
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    // 发生错误立刻取消定时器
                    this.cancel();
                }
            }

        }, 0, 5000);

        
        try {
            // 读Sock里面的数据
            InputStream s = socket.getInputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = s.read(buf)) != -1) {
                System.out.println(new String(buf, 0, len));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //往Socket里面写数据，需要新开一个线程
    class sendMessThread extends Thread{
        @Override
        public void run() {
            super.run();
            //写操作
            Scanner scanner=null;
            OutputStream os= null;
            try {
                scanner=new Scanner(System.in);
                os= socket.getOutputStream();
                String in="";
                do {
                    in=scanner.next();
                    os.write((""+in).getBytes());
                    os.flush();
                } while (!in.equals("bye"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            scanner.close();
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //函数入口
    public static void main(String[] args) {
        //需要服务器的正确的IP地址和端口号
        Client clientTest=new Client("192.168.1.7", 26);
        clientTest.start();
    }
}
