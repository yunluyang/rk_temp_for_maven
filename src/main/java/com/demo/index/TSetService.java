package com.demo.index;


import com.demo.common.model.Tset;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;


@Before(Tx.class)
public class TSetService {
	private static final Tset dao = new Tset().dao();
	
	public Tset getSetting(){
		String sql = " SELECT  * FROM tset";
		return dao.findFirst(sql);
	}
}
