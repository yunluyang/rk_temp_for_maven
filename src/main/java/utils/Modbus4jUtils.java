package utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.demo.common.model.Tequipment;
import com.demo.common.model.Tset;
import com.demo.index.TEquipmentService;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.serotonin.io.serial.SerialParameters;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.msg.ModbusRequest;
import com.serotonin.modbus4j.msg.ModbusResponse;
import com.serotonin.modbus4j.msg.ReadDiscreteInputsRequest;
import com.serotonin.modbus4j.msg.ReadDiscreteInputsResponse;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersRequest;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersResponse;
import com.serotonin.modbus4j.msg.WriteRegistersRequest;
import com.serotonin.modbus4j.msg.WriteRegistersResponse;
import com.serotonin.util.queue.ByteQueue;

import event.DeviceDate;

/**
 * 通过串口解析MODBUS协议
 * 
 * @author cl2
 *
 */
public class Modbus4jUtils {
	// 设定MODBUS网络上从站地址
	private final static int SLAVE_ADDRESS = 1;
	// 串行波特率
	private final static int BAUD_RATE = 4800;

	TEquipmentService equipmentS = new TEquipmentService();

	static Cache tempRedis = Redis.use();
	public static void main(String[] args) {
	}

	public void query() throws InterruptedException {
	}

	/**
	 * 读保持寄存器上的内容
	 * 
	 * @param ip
	 *            从站IP
	 * @param port
	 *            modbus端口
	 * @param start
	 *            起始地址偏移量
	 * @param readLenth
	 *            待读寄存器个数
	 * @return
	 */
	public static void modbusTCP(List<Tequipment> list, Tset set) {
			List<DeviceDate> result = new ArrayList<DeviceDate>();
			for (Tequipment equipment : list) {
				if(equipment.getType()!=2) {
					
				DeviceDate deviceDate = new DeviceDate();
				Double temDoub = null;
				Double humDoub = null;
				Double smogDoub = null;
				
				deviceDate.setDevHumiName("湿度(%RH)");
				deviceDate.setDevTempName("温度(℃)");
				SerialParameters serialParameters = new SerialParameters();
				// 设定MODBUS通讯的串行口
				serialParameters.setCommPortId(equipment.getComm());
				// 设定成无奇偶校验
				serialParameters.setParity(0);
				// 设定成数据位是8位
				serialParameters.setDataBits(8);
				// 设定为1个停止位
				serialParameters.setStopBits(1);
				// 设定端口名称
				serialParameters.setPortOwnerName("Numb nuts");
				// 设定端口波特率
				serialParameters.setBaudRate(BAUD_RATE);
				// 创建ModbusFactory工厂实例
				ModbusFactory modbusFactory = new ModbusFactory();
				// 创建ModbusMaster实例
				ModbusMaster master = modbusFactory.createRtuMaster(serialParameters);
				ModbusRequest modbusRequest = null;
				ModbusResponse modbusResponse = null;
				ByteQueue byteQueue = new ByteQueue(1024);
				try {
					master.init();
					// 功能码03 读取保持寄存器的值
					modbusRequest = new ReadHoldingRegistersRequest(equipment.getSlaveId(), 0, 5);
					modbusResponse = master.send(modbusRequest);
					modbusResponse.write(byteQueue);
					String str = byteQueue.toString().replace("[", "");
					str = str.replace("]", "");
					String[] split = str.split(",");
					for (int i = 0; i < split.length; i++) {
						if (split[i].length() == 2) {
						} else {
							split[i] = "0" + split[i];
						}
					}
					StringBuilder humSb = new StringBuilder();
						humSb.append(split[3]);
						humSb.append(split[4]);
					StringBuilder	smogSb = new StringBuilder();
						smogSb.append(split[9]);
						smogSb.append(split[10]);
					StringBuilder temSb = new StringBuilder();
					temSb.append(split[5]);
					temSb.append(split[6]);
					int hum = Integer.parseInt(humSb.toString(), 16);
					int tem = Integer.parseInt(temSb.toString(), 16);
					int smg = Integer.parseInt(smogSb.toString(), 16);
					DecimalFormat df = new DecimalFormat("0.0");
					temDoub = Double.parseDouble(df.format((float) tem / 10));
					humDoub = Double.parseDouble(df.format((float) hum / 10));
					smogDoub = Double.parseDouble(df.format((float) smg / 10));
				} catch (ModbusTransportException e) {

				} catch (ModbusInitException e) {
					// TODO Auto-generated catch block

				} finally {
					master.destroy();
					if (temDoub != null && !(temDoub.equals(""))) {
						deviceDate.setDevHumiValue(humDoub + "");
						deviceDate.setDevTempValue(temDoub + "");
						deviceDate.setDevKey(equipment.getDeviceKey());
						deviceDate.setDevName(equipment.getDevName());
						deviceDate.setDevStatus(true);
						deviceDate.setType(equipment.getType());
							// 0表示不报警，1表示超上限，2表示超下限
							if (humDoub >= set.getAlarmHumMax()) {
								deviceDate.setHumiStatus(1);
							} else if (humDoub <= set.getAlarmHumMin()) {
								deviceDate.setHumiStatus(2);
							} else {
								deviceDate.setHumiStatus(0);
							}
							if (temDoub >= set.getAlarmTemMax()) {
								deviceDate.setTempStatus(1);
							} else if (temDoub <= set.getAlarmTemMin()) {
								deviceDate.setTempStatus(2);
							} else {
								deviceDate.setTempStatus(0);
							}
							if(smogDoub!=0) {
								deviceDate.setSomgStatus(1);
								deviceDate.setHumiStatus(0);
								deviceDate.setTempStatus(0);
							}else {
								deviceDate.setSomgStatus(0);
								deviceDate.setHumiStatus(0);
								deviceDate.setTempStatus(0);
							}
						
						
					} else {
						deviceDate.setType(equipment.getType());
						deviceDate.setDevStatus(false);
						deviceDate.setDevKey(equipment.getDeviceKey());
						deviceDate.setDevName(equipment.getDevName());
						deviceDate.setDevHumiValue("0.00");
						deviceDate.setDevTempValue("0.00");
					}
					result.add(deviceDate);
				}
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
			tempRedis.set("result",result);
	}

	/**
	 * 读开关量型的输入信号
	 * 
	 * @param master
	 *            主站
	 * @param slaveId
	 *            从站地址
	 * @param start
	 *            起始偏移量
	 * @param len
	 *            待读的开关量的个数
	 */
	private static void readDiscreteInputTest(ModbusMaster master, int slaveId, int start, int len) {
		try {
			ReadDiscreteInputsRequest request = new ReadDiscreteInputsRequest(slaveId, start, len);
			ReadDiscreteInputsResponse response = (ReadDiscreteInputsResponse) master.send(request);
			if (response.isException())
				System.out.println("Exception response: message=" + response.getExceptionMessage());
			else
				System.out.println(Arrays.toString(response.getBooleanData()));
		} catch (ModbusTransportException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读保持寄存器上的内容
	 * 
	 * @param master
	 *            主站
	 * @param slaveId
	 *            从站地址
	 * @param start
	 *            起始地址的偏移量
	 * @param len
	 *            待读寄存器的个数
	 */
	private static void readHoldingRegistersTest(ModbusMaster master, int slaveId, int start, int len) {
		try {
			ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, start, len);
			ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) master.send(request);
			if (response.isException()) {
				System.out.println("Exception response: message=" + response.getExceptionMessage());
			} else {
				System.out.println(Arrays.toString(response.getShortData()));
				/*
				 * short[] list = response.getShortData(); for(int i = 0; i <
				 * list.length; i++){ System.out.print(list[i] + " "); }
				 */
			}
		} catch (ModbusTransportException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量写数据到保持寄存器
	 * 
	 * @param master
	 *            主站
	 * @param slaveId
	 *            从站地址
	 * @param start
	 *            起始地址的偏移量
	 * @param values
	 *            待写数据
	 */
	public static void writeRegistersTest(ModbusMaster master, int slaveId, int start, short[] values) {
		try {
			WriteRegistersRequest request = new WriteRegistersRequest(slaveId, start, values);
			WriteRegistersResponse response = (WriteRegistersResponse) master.send(request);
			if (response.isException()) {
				System.out.println("Exception response: message=" + response.getExceptionMessage());
			} else {
				ByteQueue byteQueue = new ByteQueue(1024);
				response.write(byteQueue);
				System.out.println("功能码:" + response.getFunctionCode());
				System.out.println("从站地址:" + response.getSlaveId());
				System.out.println("收到的响应信息大小:" + byteQueue.size());
				System.out.println("收到的响应信息值:" + byteQueue);
			}
		} catch (ModbusTransportException e) {
			e.printStackTrace();
		}
	}
}
