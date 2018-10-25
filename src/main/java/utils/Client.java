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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import com.demo.common.model.Tequipment;
import com.demo.index.TEquipmentService;
import com.demo.index.TSetService;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

import event.ByteUtils;
import event.DeviceDate;

public class Client extends Thread {

    //定义一个Socket对象
    Socket socket = null;
    
    private static TEquipmentService equipmentS = new TEquipmentService();
	private static TSetService setS = new TSetService();
	public static List<DeviceDate> result = new ArrayList<DeviceDate>();
	public static Cache tempRedis = Redis.use("temp");
	String data1 = "010300000002C40B";
	String data2 = "020300000002C438";
	String data3 = "030300000002C5E9"; 
	String data4 = "040300000002C45E";
	String data5 = "050300000002C58F"; 
	String data6 = "060300000002C5BC"; 
	String data7 = "070300000002C46D";
	String data8 = "080300000002C492";
	String data9 = "090300000002C543";
	String data10= "100300000002C74A";
	
	
	
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
            // 读Sock里面的数据
            InputStream s = socket.getInputStream();
            byte[] buf = new byte[9];
            while (s.read(buf) != -1) {
                String dataString = ByteUtils.byteArrayToHexString(buf);
				String temSb= dataString.substring(10, 14);
				String humSb = dataString.substring(6, 10);
				int SlaveId = Integer.parseInt(dataString.substring(0, 2));
				int hum = Integer.parseInt(humSb.toString(), 16);
				int tem = Integer.parseInt(temSb.toString(), 16);
				DecimalFormat df = new DecimalFormat("0.0");
				double temDoub = Double.parseDouble(df.format((float) tem / 10));
				double humDoub = Double.parseDouble(df.format((float) hum / 10));
				Tequipment tequipment = equipmentS.queryBySlaveId(SlaveId);
				DeviceDate deviceDate = new DeviceDate();
				deviceDate.setDevHumiValue(humDoub + "");
				deviceDate.setDevTempValue(temDoub + "");
				deviceDate.setDevKey(tequipment.getDeviceKey());
				deviceDate.setDevName(tequipment.getDevName());
				deviceDate.setDevStatus(true);
				deviceDate.setType(tequipment.getType());
					// 0表示不报警，1表示超上限，2表示超下限
					if (humDoub >= setS.getSetting().getAlarmHumMax()) {
						deviceDate.setHumiStatus(1);
					} else if (humDoub <= setS.getSetting().getAlarmHumMin()) {
						deviceDate.setHumiStatus(2);
					} else {
						deviceDate.setHumiStatus(0);
					}
					if (temDoub >= setS.getSetting().getAlarmTemMax()) {
						deviceDate.setTempStatus(1);
					} else if (temDoub <= setS.getSetting().getAlarmTemMin()) {
						deviceDate.setTempStatus(2);
					} else {
						deviceDate.setTempStatus(0);
					}
						deviceDate.setSomgStatus(0);
					tempRedis.set(deviceDate.getDevKey(),deviceDate);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /** 
     * 字节数组转16进制 
     * @param bytes 需要转换的byte数组 
     * @return  转换后的Hex字符串 
     */  
    public static String bytesToHex(byte[] bytes) {  
        StringBuffer sb = new StringBuffer();  
        for(int i = 0; i < bytes.length; i++) {  
            String hex = Integer.toHexString(bytes[i] & 0xFF);  
            if(hex.length() < 2){  
                sb.append(0);  
            }  
            sb.append(hex);  
        }  
        return sb.toString();  
    }
    
    /**
     * 将16进制字符串转换为byte[]
     * 
     * @param str
     * @return
     */
    public static byte[] toBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
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
                    List<Tequipment> list = equipmentS.queryByType(0);
                	if(list.size()>0){
            			for(Tequipment tequipment :list){
            				switch (tequipment.getSlaveId()) {
        					case 1:
        	                    os.write(toBytes(data1));
        						break;
        					case 2:
        						os.write(toBytes(data2));
        						break;
        					case 3:
        						os.write(toBytes(data3));
        						break;
        					case 4:
        						os.write(toBytes(data4));
        						break;
        					case 5:
        						os.write(toBytes(data5));
        						break;
        					case 6:
        						os.write(toBytes(data6));
        						break;
        					case 7:
        						os.write(toBytes(data7));
        						break;
        					case 8:
        						os.write(toBytes(data8));
        						break;
        					case 9:
        						os.write(toBytes(data9));
        						break;
        					case 10:
        						os.write(toBytes(data10));
        						break;
        					default:
        						break;
        					}
            				try {
            					Thread.sleep(300);
            				} catch (InterruptedException e) {
            					// TODO Auto-generated catch block
            					e.printStackTrace();
            				}
            			}
                        os.flush();
        				try {
        					Thread.sleep(1000);
        				} catch (InterruptedException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
            		}
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
