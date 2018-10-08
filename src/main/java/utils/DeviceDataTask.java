package utils;

import com.demo.index.TEquipmentService;
import com.demo.index.TSetService;

public class DeviceDataTask implements Runnable{
	TSetService setS = new TSetService();
	TEquipmentService equipmentS = new TEquipmentService();
	public void run() {
		// TODO Auto-generated method stub
		Modbus4jUtils.modbusTCP(equipmentS.queryAll(), setS.getSetting());
	}

}
