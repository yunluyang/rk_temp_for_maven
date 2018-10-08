package com.demo.index;


import com.demo.common.model.Tbalarm;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;


@Before(Tx.class)
public class TBalarmService {
	private static final Tbalarm dao = new Tbalarm().dao();
	
	public Page<Tbalarm> paginate(int pageNumber, int pageSize,String deviceKey) {
		return dao.paginate(pageNumber, pageSize, "select *", "from tbalarm where DeviceKey = ? order by RecordTime desc",deviceKey);//where DeviceKey = ?  order by RecordTime
	}
	
	
	public Page<Tbalarm> paginates(int pageNumber, int pageSize) {
		return dao.paginate(pageNumber, pageSize, "select *", "from tbalarm order by RecordTime desc");
	}
}
