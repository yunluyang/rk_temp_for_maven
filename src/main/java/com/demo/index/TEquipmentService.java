package com.demo.index;


import java.util.List;

import com.demo.common.model.Tequipment;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;


@Before(Tx.class)
public class TEquipmentService {
	
private static final Tequipment dao = new Tequipment().dao();
	
	public Tequipment getTequipmentByDeviceKey(int deviceKey){
		String sql = " SELECT  * FROM tequipment where deviceKey = ?";
		return dao.findFirst(sql,deviceKey);
	}
	
	
	public Page<Tequipment> paginate(int pageNumber, int pageSize) {
		return dao.paginate(pageNumber, pageSize, "select *", "from tequipment  order by deviceKey desc");
	}
	
	public List<Tequipment> queryAll(){
		String sql = "select * from tequipment order by deviceKey desc";
		return dao.find(sql);
	}
	
	public void deleteById(int id) {
		dao.deleteById(id);
	}
}
