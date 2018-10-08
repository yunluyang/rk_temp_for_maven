package utils;

import java.util.Arrays;

import com.serotonin.io.serial.SerialParameters;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.msg.ModbusRequest;
import com.serotonin.modbus4j.msg.ModbusResponse;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersRequest;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersResponse;
import com.serotonin.modbus4j.msg.ReadInputRegistersRequest;
import com.serotonin.modbus4j.msg.ReadInputRegistersResponse;
import com.serotonin.modbus4j.msg.WriteRegisterRequest;
import com.serotonin.modbus4j.msg.WriteRegisterResponse;
import com.serotonin.modbus4j.msg.WriteRegistersRequest;
import com.serotonin.modbus4j.msg.WriteRegistersResponse;
import com.serotonin.util.queue.ByteQueue;

public class ReadAWriteUtil {

	
	
	public static void main(String[] args) {
		
		
		SerialParameters serialParameters = new SerialParameters();
		// 设定MODBUS通讯的串行口
		serialParameters.setCommPortId("COM6");
		// 设定成无奇偶校验
		serialParameters.setParity(0);
		// 设定成数据位是8位
		serialParameters.setDataBits(8);
		// 设定为1个停止位
		serialParameters.setStopBits(1);
		// 设定端口名称
		serialParameters.setPortOwnerName("Numb nuts");
		// 设定端口波特率
		serialParameters.setBaudRate(4800);
		// 创建ModbusFactory工厂实例
		ModbusFactory modbusFactory = new ModbusFactory();
		// 创建ModbusMaster实例
		ModbusMaster master = modbusFactory.createRtuMaster(serialParameters);
		try {
			master.init();
			readHoldingRegistersTest(master,1,0,5);
		} catch (ModbusInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			master.destroy();
		}
	}
    /**
     * 批量写数据到保持寄存器
     * @param ip 从站IP
     * @param port modbus端口
     * @param slaveId 从站ID
     * @param start 起始地址偏移量
     * @param values 待写数据
     */
    public static void modbusWTCP(ModbusMaster master,int slaveId, int start, short[] values) {  
    
        try {  
            WriteRegistersRequest request = new WriteRegistersRequest(slaveId, start, values);  
            WriteRegistersResponse response = (WriteRegistersResponse) master.send(request);  
            if (response.isException()){
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            }else{
            	System.out.println("Exception response: message="
                    + response.getExceptionMessage());
                ByteQueue byteQueue= new ByteQueue(1024);  
                response.write(byteQueue); 
                System.out.println("功能码:"+response.getFunctionCode());  
                System.out.println("从站地址:"+response.getSlaveId());  
                System.out.println("收到的响应信息大小:"+byteQueue.size());
                System.out.println("收到的响应信息值:"+byteQueue);  
            }     
        } catch (ModbusTransportException e) {  
            e.printStackTrace();  
        } 
    }  
     
    
    /**
     * 读保持寄存器上的内容
     * @param ip 从站IP
     * @param port modbus端口
     * @param start 起始地址偏移量
     * @param readLenth 待读寄存器个数
     * @return
     */
    public static ByteQueue modbusTCP(ModbusMaster master,int slaveId,int start,int readLenth) {  
     
        ModbusRequest modbusRequest=null;  
        try {  
            //功能码03   读取保持寄存器的值
            modbusRequest = new ReadHoldingRegistersRequest(slaveId, start, readLenth);  
        } catch (ModbusTransportException e) {  
            e.printStackTrace();  
        }  
        ModbusResponse modbusResponse=null;  
        try {  
            modbusResponse = master.send(modbusRequest);  
        } catch (ModbusTransportException e) {  
            e.printStackTrace();  
        }  
        ByteQueue byteQueue= new ByteQueue(1024);  
        modbusResponse.write(byteQueue); 
        System.out.println("功能码:"+modbusRequest.getFunctionCode());  
        System.out.println("从站地址:"+modbusRequest.getSlaveId());  
        System.out.println("收到的响应信息大小:"+byteQueue.size());
        System.out.println("收到的响应信息值:"+byteQueue);  
        return byteQueue;  
    }
    /**
     * @Description: 读取保持寄存器数据
     * @param master 主站实例
     * @param slaveId 从站ID
     * @param start 起始位
     * @param len 长度
     */
    public static void readHoldingRegistersTest(ModbusMaster master, int slaveId, int start, int len) {
        try {
            ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, start, len);
            ModbusResponse modbusResponse = null;
			ByteQueue byteQueue = new ByteQueue(1024);
            modbusResponse = master.send(request);
			modbusResponse.write(byteQueue);
            if (modbusResponse.isException())
                System.out.println("Exception response: message=" + modbusResponse.getExceptionMessage());
            else
    			System.out.println("modbusTCP+byteQueue:"+byteQueue.toString());
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * @Description: 读取外围设备输入的数据
     * @param master 主站实例
     * @param slaveId 从站ID
     * @param start 起始位
     * @param len 长度
     */
    public static void readInputRegistersTest(ModbusMaster master, int slaveId, int start, int len) {
        try {
            ReadInputRegistersRequest request = new ReadInputRegistersRequest(slaveId, start, len);
            ReadInputRegistersResponse response = (ReadInputRegistersResponse) master.send(request);
 
            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println("功能码:4--" + Arrays.toString(response.getShortData()));
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @param com  端口
     * @param baud  波特率
     * @param slaveId  从站地址
     * @param offset  偏移量
     * @param value  ff
     * @throws ModbusInitException
     */
    public static void writeRegisterTest(String com,int baud,int slaveId, int offset, int value)  {
    	SerialParameters serialParameters = new SerialParameters();
        // 设定MODBUS通讯的串行口
        serialParameters.setCommPortId(com);
        // 设定成无奇偶校验
        serialParameters.setParity(0);
        // 设定成数据位是8位
        serialParameters.setDataBits(8);
        // 设定为1个停止位
        serialParameters.setStopBits(1);
        // 设定端口名称
        serialParameters.setPortOwnerName("Numb nuts");
        // 设定端口波特率
        serialParameters.setBaudRate(baud);
        // 创建ModbusFactory工厂实例
        ModbusFactory modbusFactory = new ModbusFactory();
        // 创建ModbusMaster实例
        ModbusMaster master = modbusFactory.createRtuMaster(serialParameters);
        try {  
        	master.init();  
            System.out.println("========初始化成功=======");  
            WriteRegisterRequest request = new WriteRegisterRequest(slaveId, offset, value);
            WriteRegisterResponse response = (WriteRegisterResponse) master.send(request);
 
            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println("写入单个模拟量数据成功!");
            ByteQueue byteQueue= new ByteQueue(1024);  
            response.write(byteQueue); 
            System.out.println("功能码:"+response.getFunctionCode());  
            System.out.println("从站地址:"+response.getSlaveId());  
            System.out.println("收到的响应信息大小:"+byteQueue.size());
            System.out.println("收到的响应信息值:"+byteQueue);  
        } catch (ModbusTransportException e) {
        }catch(ModbusInitException ee){
        }finally {
        	master.destroy();
		}
    }

}
