package utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo.common.model.Tbhistory;
import com.demo.index.TBhistoryService;

public class EcxelTask implements Runnable{

	TBhistoryService historyS = new TBhistoryService();
	public void run() {
		// TODO Auto-generated method stub
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
        
	}

}
