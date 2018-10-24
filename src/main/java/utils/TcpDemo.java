package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import event.ByteUtils;

class TcpDemo {
    private static String SERVER_ADDR = "192.168.1.176";
    private static int PORT = 26;
    private static String EXIT = "exit";


    static class TcpClientThread extends Thread {
        private Socket socket;
        private BufferedReader br;
        private BufferedWriter bw;
        private Scanner scanner;

        @Override
        public void run() {
            try {
                socket = new Socket(SERVER_ADDR, PORT);
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                scanner = new Scanner(System.in);
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            String line;
                            while ((line = br.readLine()) != null) {
                                System.out.println("client received: " + line);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            closeSocket();
                        }
                    }

                }).start();

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

                String line;
                while (scanner.hasNext()) {
                    line = scanner.nextLine();
                    bw.write(line + "\n");
                    // 注意：刷新是关键，否则会等到缓冲区满才将数据实际地发送出去
                    bw.flush();
				
                    if (EXIT.equals(line)) {
                        break;
                    }
                }

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeSocket();
            }
        }

        private void closeSocket() {
            if (scanner != null) {
                scanner.close();
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();                                                  
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 16进制直接转换成为字符串(无需Unicode解码)
     * @param hexStr
     * @return
     */
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }
    /**
     * 16进制转换成为string类型字符串
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }
    
    public static void main(String[] args) {
   //   new TcpServerThread().start();
     new TcpClientThread().start();
    }
}