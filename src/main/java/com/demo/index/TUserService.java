package com.demo.index;


import com.demo.common.model.Tuser;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;


@Before(Tx.class)
public class TUserService {
private static final Tuser dao = new Tuser().dao();
	
	public Tuser getUserById(String userName){
		String sql = " SELECT  * FROM tuser where username = ?";
		return dao.findFirst(sql,userName);
	}
	
}
