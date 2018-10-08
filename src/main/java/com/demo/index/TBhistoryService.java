package com.demo.index;

import java.util.Date;
import java.util.List;

import com.demo.common.model.Tbhistory;
import com.demo.common.model.Tchistory;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;


@Before(Tx.class)
public class TBhistoryService {
	private static final Tbhistory dao = new Tbhistory().dao();
	
	public List<Tbhistory> getTempHumiById(String id,int type){
		String sql ;
		switch (type) {
		case 0:
			sql = "SELECT Tem,Hum,RecordTime FROM tbhistory where DeviceKey = ? and RecordTime>DATE_SUB(CURDATE(), INTERVAL 1 DAY) GROUP BY RecordTime";
			break;
		case 1:
			sql = "SELECT Tem,Hum,RecordTime FROM tbhistory where DeviceKey = ? and RecordTime>DATE_SUB(CURDATE(), INTERVAL 3 DAY) GROUP BY RecordTime";
			break;
		case 2:
			sql = "SELECT Tem,Hum,RecordTime FROM tbhistory where DeviceKey = ? and RecordTime>DATE_SUB(CURDATE(), INTERVAL 7 DAY) GROUP BY RecordTime";
			break;
		case 3:
			sql = "SELECT Tem,Hum,RecordTime FROM tbhistory where DeviceKey = ? and RecordTime>DATE_SUB(CURDATE(), INTERVAL 1 MONTH) GROUP BY RecordTime";
			break;
		case 4:
			sql = "SELECT Tem,Hum,RecordTime FROM tbhistory where DeviceKey = ? and RecordTime>DATE_SUB(CURDATE(), INTERVAL 3 MONTH) GROUP BY RecordTime";
			break;
		default:
			sql = "SELECT Tem,Hum,RecordTime FROM tbhistory where DeviceKey = ?  GROUP BY RecordTime";
			break;
		}
		//String sql = "SELECT Tem,Hum,RecordTime FROM tbhistory where DeviceKey = ? and RecordTime>DATE_SUB(CURDATE(), INTERVAL 7 DAY) GROUP BY RecordTime";
		return dao.find(sql,id);
	}
	
	
	public List<Tbhistory> getTempHumiToday(){
		String 
			sql = "SELECT * FROM tbhistory where RecordTime>DATE_SUB(CURDATE(), INTERVAL 1 DAY) GROUP BY RecordTime";
		
		return dao.find(sql);
	}
	
	public List<Tbhistory> getTempHumiByTime(String id,Date start,Date end){
		String sql ;
			sql = " SELECT  Tem,Hum,RecordTime FROM tbhistory where DeviceKey = ?  and RecordTime >= ? and RecordTime <=? GROUP BY RecordTime";
		return dao.find(sql,id,start,end);
	}
	
	public List<Tbhistory> queryLast(){
		String sql = "select * from tbhistory where RecordTime = max(Time)";
		return dao.find(sql);
	}
	
	
	public Page<Tbhistory> paginate(int pageNumber, int pageSize) {
		return dao.paginate(pageNumber, pageSize, "select *", "from tbhistory  order by id desc");
	}
}
