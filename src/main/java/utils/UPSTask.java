package utils;



import com.demo.index.TEquipmentService;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

import event.ByteUtils;
import event.Q6;
import event.WA;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

public class UPSTask implements Runnable{
		// 串口对象
	private static SerialPort mSerialport;
	
	private static String type;
	TEquipmentService equipmentS = new TEquipmentService();
	/**
	 * 打开串口
	 * 
	 * @param evt
	 *            点击事件
	 * @throws PortInUseException 
	 */
	private static void openSerialPort() throws PortInUseException {
		// 获取串口名称
		String commName = "COM3";
		// 获取波特率，默认为9600
		int baudrate = 2400;
		// 检查串口名称是否获取正确
		mSerialport = SerialPortManager.openPort(commName, baudrate);
		// 添加串口监听
		SerialPortManager.addListener(mSerialport, new SerialPortManager.DataAvailableListener() {
			@Override
			public void dataAvailable() {
				byte[] data = null;
				try {
					if (mSerialport == null) {
						System.out.println("串口对象为空，监听失败！");
					} else {
						System.out.println("串口打开成功！");
						// 读取串口数据
						data = SerialPortManager.readFromPort(mSerialport);
						System.out.println(ByteUtils.byteArrayToHexString(data));
						if(type.equals("Q6")) {
							Q6 q6 =  UpsUtils.getQ6Data(ByteUtils.byteArrayToHexString(data));
							System.out.println("q6:"+q6.toString());
							Cache tempRedis = Redis.use("temp");
							if(q6!=null&&tempRedis!=null) {
								tempRedis.set("Q6",q6);
							}
						}else if(type.equals("WA")){
							WA wa = UpsUtils.getWAData(ByteUtils.byteArrayToHexString(data));
							System.out.println("wa:"+wa.toString());
							Cache tempRedis = Redis.use("temp");
							if(wa!=null&&tempRedis!=null) {
							tempRedis.set("WA",wa);
							}
						}
					}
				} catch (Exception e) {
					System.out.println(e.toString());
					// 发生读取错误时显示错误信息后退出系统
				}
			}
		});
	}
	
	
	private static void sendData(String data) {
		type=data;
		// 待发送数据
		data = data+"\r\n";
		if (mSerialport == null) {
			System.out.println("请先打开串口！");
			return;
		}

		if ("".equals(data) || data == null) {
			System.out.println("请输入要发送的数据！");
			return;
		}
		// 以字符串的形式发送数据
		SerialPortManager.sendToPort(mSerialport, data.getBytes());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			openSerialPort();
			sendData("Q6");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendData("WA");
			try {
				Thread.sleep(5000);
				SerialPortManager.closePort(mSerialport);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (PortInUseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
