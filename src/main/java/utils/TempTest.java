package utils;

import java.text.DecimalFormat;
import event.ByteUtils;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

public class TempTest {

	// 串口对象
	private static SerialPort mSerialport;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//01 03 00 00 00 02 C4 0B
		//02 03 00 00 00 02 C4 38 
		//03 03 00 00 00 02 C5 E9 
		//04 03 00 00 00 02 C4 5E 
		//05 03 00 00 00 02 C5 8F 
		//06 03 00 00 00 02 C5 BC 
		//07 03 00 00 00 02 C4 6D
		//08 03 00 00 00 02 C4 92
		//09 03 00 00 00 02 C5 43
		//10 03 00 00 00 02 C7 4A
		try {
			openSerialPort();
			sendData(ByteUtils.hex2byte("030300000002C5E9"));
			try {
				Thread.sleep(300);
				SerialPortManager.closePort(mSerialport);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 打开串口
	 * 
	 * @param evt
	 *            点击事件
	 * @throws PortInUseException
	 */
	private static void openSerialPort() throws PortInUseException {
		// 获取串口名称
		String commName = "COM6";
		// 获取波特率，默认为9600
		int baudrate = 4800;
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
						String dataString = ByteUtils.byteArrayToHexString(data);
						String temSb= dataString.substring(10, 14);
						String humSb = dataString.substring(6, 10);
						int hum = Integer.parseInt(humSb.toString(), 16);
						int tem = Integer.parseInt(temSb.toString(), 16);
						DecimalFormat df = new DecimalFormat("0.0");
						double temDoub = Double.parseDouble(df.format((float) tem / 10));
						double humDoub = Double.parseDouble(df.format((float) hum / 10));
						System.out.println("湿度："+humDoub+"%RH");
						System.out.println("温度："+temDoub+"°");
					}
				} catch (Exception e) {
					System.out.println(e.toString());
					// 发生读取错误时显示错误信息后退出系统
				}
			}
		});
	}

	private static void sendData(byte[] data) {
		// 待发送数据
		if (mSerialport == null) {
			System.out.println("请先打开串口！");
			return;
		}

		if ("".equals(data) || data == null) {
			System.out.println("请输入要发送的数据！");
			return;
		}
		// 以字符串的形式发送数据
		SerialPortManager.sendToPort(mSerialport, data);
	}
}
