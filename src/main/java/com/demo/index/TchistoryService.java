package com.demo.index;



import com.demo.common.model.Tchistory;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;


@Before(Tx.class)
public class TchistoryService {
	private static final Tchistory dao = new Tchistory().dao();
	
	public Tchistory getUserById(String id){
		String sql = " SELECT  * FROM tuser where username = ?";
		return dao.findFirst(sql,id);
	}
	
	public Page<Tchistory> paginate(int pageNumber, int pageSize) {
		return dao.paginate(pageNumber, pageSize, "select *", "from tchistory  order by id desc");
	}
}
