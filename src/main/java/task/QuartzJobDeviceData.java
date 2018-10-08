package task;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.demo.index.TEquipmentService;
import com.demo.index.TSetService;

import utils.Modbus4jUtils;

import java.util.Date;

/**
 */
public class QuartzJobDeviceData implements Job {
	
	TSetService setS = new TSetService();
	TEquipmentService equipmentS = new TEquipmentService();
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    	Modbus4jUtils.modbusTCP(equipmentS.queryAll(), setS.getSetting());
        System.out.println("QuartzJobOneDeviceData:" + new Date());
    }
}