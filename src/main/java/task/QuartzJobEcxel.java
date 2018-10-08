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
import utils.WriteExcel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 */
public class QuartzJobEcxel implements Job {
	
	TBhistoryService historyS = new TBhistoryService();
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    	String title[] = {"ID","设备名称","温度","湿度","记录时间"};  
		Date date=new Date();//取时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		String strPath = "D:/动力系统/"+dateString+"/";  
		String fileName = strPath + "温湿度数据.xls";
		File file = new File(strPath);  
		if(!file.exists())
		{  
		    file.mkdirs();  
		} 
		try {
			WriteExcel.createExcel(fileName,"sheet1",title);
	        List<Tbhistory> list= historyS.getTempHumiToday();
	        WriteExcel.writeExcel(list, 3, fileName); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("QuartzJobEcxel:" + new Date());
    }
}