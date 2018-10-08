package task;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.demo.common.model.Tbalarm;
import com.demo.common.model.Tbhistory;
import com.demo.common.model.Tequipment;
import com.demo.common.model.Tset;
import com.demo.index.TBalarmService;
import com.demo.index.TBconditionerService;
import com.demo.index.TBhistoryService;
import com.demo.index.TEquipmentService;
import com.demo.index.TSetService;
import com.demo.index.TchistoryService;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

import event.DeviceDate;

import java.util.Date;
import java.util.List;

/**
 */
public class QuartzJobHistory implements Job {
	
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    	TSetService setS = new TSetService();
    	TEquipmentService equipmentS = new TEquipmentService();
    	Tset set = setS.getSetting();
		String temRange = set.getAlarmTemMin()+"~"+set.getAlarmTemMax();
		String humRange = set.getAlarmHumMin()+"~"+set.getAlarmHumMin();
		List<Tequipment> list = equipmentS.queryAll();
		List<DeviceDate> result = Redis.use("temp").get("result");
		System.out.println("QuartzJobHistory:"+result.get(0).toString());
		int i=0;
		for(Tequipment equipment :list ){
			if(equipment.getType()==0) {
			if(result!=null&&result.size()>0){
				DeviceDate device = result.get(i);
				if(device.getType()==0) {
					Double huim = Double.parseDouble(device.getDevHumiValue());
					Double temp = Double.parseDouble(device.getDevTempValue());
					if(device.getDevHumiValue()!=null&&!(device.getDevHumiValue().equals(""))){
						Tbhistory history = new Tbhistory();
						history.setDeviceKey(equipment.getDeviceKey());
						history.setDeviceName(equipment.getDevName());
						history.setHum(huim);
						history.setTem(temp);
						history.setRecordTime(new Date());
						history.save();
						//0表示不报警，1表示超上限，2表示超下限
						if(huim>=set.getAlarmHumMax()){
							Tbalarm alarm = new Tbalarm();
							alarm.setDeviceKey(equipment.getDeviceKey());
							alarm.setDeviceName(equipment.getDevName());
							alarm.setAlarmRange(humRange);
							alarm.setAlarmType("湿度(%RH)");
							alarm.setAlarmMessage("超过湿度上线");
							alarm.setDataValue(huim);
							alarm.setRecordTime(new Date());
							alarm.save();
						}else if(huim<=set.getAlarmHumMin()){
							Tbalarm alarm = new Tbalarm();
							alarm.setDeviceKey(equipment.getDeviceKey());
							alarm.setDeviceName(equipment.getDevName());
							alarm.setAlarmRange(humRange);
							alarm.setAlarmType("湿度(%RH)");
							alarm.setAlarmMessage("超过湿度下线");
							alarm.setDataValue(huim);
							alarm.setRecordTime(new Date());
							alarm.save();
						}
						if(temp>=set.getAlarmTemMax()){
							Tbalarm alarm = new Tbalarm();
							alarm.setDeviceKey(equipment.getDeviceKey());
							alarm.setDeviceName(equipment.getDevName());
							alarm.setAlarmRange(temRange);
							alarm.setAlarmType("温度(℃)");
							alarm.setAlarmMessage("超过温度上线");
							alarm.setDataValue(temp);
							alarm.setRecordTime(new Date());
							alarm.save();
						}else if(temp<=set.getAlarmTemMin()){
							Tbalarm alarm = new Tbalarm();
							alarm.setDeviceKey(equipment.getDeviceKey());
							alarm.setDeviceName(equipment.getDevName());
							alarm.setAlarmRange(temRange);
							alarm.setAlarmType("温度(℃)");
							alarm.setAlarmMessage("超过温度下线");
							alarm.setDataValue(temp);
							alarm.setRecordTime(new Date());
							alarm.save();
						}
					}
				}
				
			}
			i++;
			}
		}
        System.out.println("QuartzJobHistory:" + new Date());
    }
}