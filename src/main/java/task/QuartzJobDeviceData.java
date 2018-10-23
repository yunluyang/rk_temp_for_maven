package task;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.demo.common.model.Tequipment;
import com.demo.index.TEquipmentService;
import com.demo.index.TSetService;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

import event.ByteUtils;
import event.DeviceDate;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import utils.SerialPortManager;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 */
public class QuartzJobDeviceData implements Job {
	
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
	
	// 串口对象
	private static SerialPort mSerialport;
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    	List<Tequipment> list = equipmentS.queryByType(0);
    	tempRedis.set("result",null);
    	try {
    		if(list.size()>0){
    			openSerialPort(list.get(0));
    			
    			for(Tequipment tequipment :list){
    				switch (tequipment.getSlaveId()) {
					case 1:
						sendData(ByteUtils.hex2byte(data1));
						break;
					case 2:
						sendData(ByteUtils.hex2byte(data2));
						break;
					case 3:
						sendData(ByteUtils.hex2byte(data3));
						break;
					case 4:
						sendData(ByteUtils.hex2byte(data4));
						break;
					case 5:
						sendData(ByteUtils.hex2byte(data5));
						break;
					case 6:
						sendData(ByteUtils.hex2byte(data6));
						break;
					case 7:
						sendData(ByteUtils.hex2byte(data7));
						break;
					case 8:
						sendData(ByteUtils.hex2byte(data8));
						break;
					case 9:
						sendData(ByteUtils.hex2byte(data9));
						break;
					case 10:
						sendData(ByteUtils.hex2byte(data10));
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

				try {
					Thread.sleep(500);
					SerialPortManager.closePort(mSerialport);
					tempRedis.set("result",result);
					System.out.println("result:"+tempRedis.get("result"));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("QuartzJobOneDeviceData:" + new Date());
    }
    
    

	/**
	 * 打开串口
	 * 
	 * @param evt
	 *            点击事件
	 * @throws PortInUseException
	 */
	private static void openSerialPort(Tequipment equipment) throws PortInUseException {
		// 获取串口名称
		String commName = equipment.getComm();
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
						deviceDate.setDevKey(equipment.getDeviceKey());
						deviceDate.setDevName(equipment.getDevName());
						deviceDate.setDevStatus(true);
						deviceDate.setType(equipment.getType());
						
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
								
								result.add(deviceDate);
					/*	System.out.println("设备地址："+tequipment.getSlaveId());
						System.out.println("设备名称："+tequipment.getDevName());
						System.out.println("湿度："+humDoub+"%RH");
						System.out.println("温度："+temDoub+"°");*/
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