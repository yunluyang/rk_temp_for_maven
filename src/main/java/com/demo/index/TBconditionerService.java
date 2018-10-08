package com.demo.index;

import java.util.Date;
import java.util.List;

import com.demo.common.model.Tconditioner;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;


@Before(Tx.class)
public class TBconditionerService {
	private static final Tconditioner dao = new Tconditioner().dao();
	
	
	public Tconditioner getByDeviceKey(int DeviceKey){
		String sql = " SELECT * FROM tconditioner where DeviceKey = ? ";
		return dao.findFirst(sql,DeviceKey);
	}
	
	public List<Tconditioner> getTempHumiByTime(String id,Date start,Date end){
		String sql ;
			sql = " SELECT  Tem,Hum,RecordTime FROM tbhistory where DeviceKey = ?  and RecordTime >= ? and RecordTime <=? GROUP BY RecordTime";
		System.out.println("sql:"+sql);	
			return dao.find(sql,id,start,end);
	}
}
