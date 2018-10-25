package com.demo.index;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.demo.common.model.Tbalarm;
import com.demo.common.model.Tbhistory;
import com.demo.common.model.Tchistory;
import com.demo.common.model.Tconditioner;
import com.demo.common.model.Tequipment;
import com.demo.common.model.Tset;
import com.demo.common.model.Tuser;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

import event.DeviceDate;
/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * IndexController
 */

@Before(UserInterceptor.class)
public class IndexController extends Controller {
	TBhistoryService historyS = enhance(TBhistoryService.class);
	TBconditionerService conditionerS = enhance(TBconditionerService.class);
	TBalarmService alarmS = enhance(TBalarmService.class);
	TUserService userS = enhance(TUserService.class);
	TchistoryService cHistoryS = enhance(TchistoryService.class);
	TSetService setS = enhance(TSetService.class);
	TEquipmentService equipmentS = enhance(TEquipmentService.class);
	Tset set = setS.getSetting();
	Cache tempRedis = Redis.use();
	private static final String REGEX_MOBILE = "(134[0-8]\\d{7})" +
            "|(" +
                "((13([0-3]|[5-9]))" +
                    "|149" +
                    "|15([0-3]|[5-9])" +
                    "|166" +
                    "|17(3|[5-8])" +
                    "|18[0-9]" +
                    "|19[8-9]" +
                ")" +
            "\\d{8}" +
            ")";

	public void index() {
		render("/index/index.html");
	}
	
	public void manager() {
		render("manager.html");
	}
	
	
	public void conditioner() {
		render("conditioner.html");
	}
	
	@Clear()
	public void login(){
		Tuser user  =userS.getUserById(getPara("username"));
		if(user!=null&&user.getPassword().equals(getPara("password"))){
			getSession().setMaxInactiveInterval(-1);
			setSessionAttr("loginUser", user.getUsername());
			setAttr("user", user);
			renderJson("result",1);
		}else{
			renderJson("result",2);//如果是2代表没有借成功  
		}
	}

	public void getDeviceData(){
		/*List<Tequipment> list = equipmentS.queryAll();
		Modbus4jUtils.modbusTCP(list,set);*/
		List<DeviceDate> result = new ArrayList<>();
		List<Tequipment> list = equipmentS.queryByType(0);
		for(Tequipment eTequipment :list){
			if(tempRedis.exists(eTequipment.getDeviceKey())){
				DeviceDate deviceDate = tempRedis.get(eTequipment.getDeviceKey());
				result.add(deviceDate);
			}
		}
		setAttr("result", result);
		setAttr("Q6",tempRedis.get("Q6"));
		setAttr("WA",tempRedis.get("WA"));
		renderJson();
	}
	
	/***
	 * type =  1  當天  
	 * type =  2  7天
	 * type =  3  30天
	 */
	public void getWenduData(){
		if(getParaToInt("type")!=null){
			List<Tbhistory> list = historyS.getTempHumiById(getPara("id"),getParaToInt("type"));
			if(list.size()==0){
				renderJson();
			}
			setAttr("list", list);
			renderJson();
		}else{
			List<Tbhistory> list = historyS.getTempHumiById(getPara("id"),0);	
			if(list.size()==0){
				renderJson();
			}
			setAttr("list", list);
			renderJson();
		}
	}
	
	
	public void saveConditioner(){
		Tconditioner tconditioner = conditionerS.getByDeviceKey(getParaToInt("deviceKey"));
		if(tconditioner!=null){
			tconditioner.setCOM(getPara("comm")).setConditionerName(getPara("info")).setDeviceKey(getParaToInt("deviceKey")).setSlaveId(getParaToInt("add")).setBaud(getParaToInt("baud"));
			tconditioner.update();
		}else{
			 tconditioner = new Tconditioner();
			tconditioner.setCOM(getPara("comm")).setConditionerName(getPara("info")).setDeviceKey(getParaToInt("deviceKey")).setSlaveId(getParaToInt("add")).setBaud(getParaToInt("baud"));
			tconditioner.save();
		}
		renderJson("status", "ok");
	}
	
	public void queryConditioner(){
		renderJson(conditionerS.getByDeviceKey(getParaToInt("deviceKey")));
	}
	
	public void queryAlarm(){
		Page<Tbalarm> page = alarmS.paginate(getParaToInt("page"),getParaToInt("rows"),getPara("deviceKey"));
		setAttr("total", page.getTotalRow());
		setAttr("pageSize", page.getPageSize());
		setAttr("rows", page.getList());
		renderJson();
	}
	
	public void queryAllAlarm(){
		Page<Tbalarm> page = alarmS.paginates(getParaToInt("page"),getParaToInt("rows"));
		System.out.println("page:"+page.toString());
		setAttr("total", page.getTotalRow());
		setAttr("pageSize", page.getPageSize());
		setAttr("rows", page.getList());
		renderJson();
	}
	
	public void queryCdtHistory(){
		Page<Tchistory> page = cHistoryS.paginate(getParaToInt("page"),getParaToInt("rows"));
		setAttr("total", page.getTotalRow());
		setAttr("pageSize", page.getPageSize());
		setAttr("rows", page.getList());
		renderJson();
	}
	
	
	public void loginOut(){
		getSession().removeAttribute("loginUser");
		redirect("pages-login.html");
	}
	
	public void querySetting() {
		renderJson(setS.getSetting());
	}
	public void setCndtnerControl(){
		double devTempValueMax = Double.parseDouble(getPara("DevTempValueMax"));
		double devTempValueMin = Double.parseDouble(getPara("DevTempValueMin"));
        double devHumiValueMax = Double.parseDouble(getPara("DevHumiValueMax"));
        double devHumiValueMin = Double.parseDouble(getPara("DevHumiValueMin"));
        double alarmTemMax = Double.parseDouble(getPara("AlarmTemMax"));
        double alarmTemMin = Double.parseDouble(getPara("AlarmTemMin"));
        double alarmHumMax = Double.parseDouble(getPara("AlarmHumMax"));
        double alarmHumMin = Double.parseDouble(getPara("AlarmHumMin"));
        String phone1 = getPara("phone1");
        String phone2 = getPara("phone2");
        String phone3 = getPara("phone3");
        if(phone1!=null&&phone1!=""){
        	if(!Pattern.matches(REGEX_MOBILE,phone1)){
    			renderJson("result",3);
    			}
        }
        if(phone2!=null&&phone2!=""){
        	if(!Pattern.matches(REGEX_MOBILE,phone2))
        			renderJson("result",3);
        }
        if(phone3!=null&&phone3!=""){
        	if(!Pattern.matches(REGEX_MOBILE,phone3))
        			renderJson("result",3);
        }
        if(devTempValueMax<=devTempValueMin||devHumiValueMax<=devHumiValueMin){
            renderJson("result",2);
        }else if(alarmTemMax<=devTempValueMax||alarmTemMin>=devTempValueMin||alarmHumMax<=devHumiValueMax||alarmHumMin>=devHumiValueMin){
        	renderJson("result",4);
        }
        else{
        Tset set = setS.getSetting();
        if(set!=null){
        	set.setDevHumiValueMax(devHumiValueMax);
        	set.setDevHumiValueMin(devHumiValueMin);
        	set.setDevTempValueMax(devTempValueMax);
        	set.setDevTempValueMin(devTempValueMin);
        	set.setAlarmTemMax(alarmTemMax);
        	set.setAlarmTemMin(alarmTemMin);
        	set.setAlarmHumMax(alarmHumMax);
        	set.setAlarmHumMin(alarmHumMin);
        	set.setPhone1(phone1);
        	set.setPhone2(phone2);
        	set.setPhone3(phone3);
        	set.setRecordTime(new Date());
        	set.update();
        }else{
        	Tset set2 =new Tset();
        	set2.setDevHumiValueMax(devHumiValueMax);
        	set2.setDevHumiValueMin(devHumiValueMin);
        	set2.setDevTempValueMax(devTempValueMax);
        	set2.setDevTempValueMin(devTempValueMin);	
        	set2.setAlarmTemMax(alarmTemMax);
        	set2.setAlarmTemMin(alarmTemMin);
        	set2.setAlarmHumMax(alarmHumMax);
        	set2.setAlarmHumMin(alarmHumMin);
        	set2.setRecordTime(new Date());
        	set2.setPhone1(phone1);
        	set2.setPhone2(phone2);
        	set2.setPhone3(phone3);
        	set2.save();
        }
        	renderJson("result",1);
	}
        }
	
	public void modifyPass() {
		Tuser user = userS.getUserById((String)getSession().getAttribute("loginUser"));
		if(getPara("pw1").equals(getPara("pw2"))) {
			if(getPara("pw").equals(user.getPassword())) {
				user.setPassword(getPara("pw1"));
				if(user.update()) {
					renderJson("result",0);
				}else {
					renderJson("result",4);
				}
			}else {
				renderJson("result",2);
			}
		}else{
			renderJson("result",1);
		}
	}
	
	public void queryAllTemHumi(){
		Page<Tbhistory> page = historyS.paginate(getParaToInt("page"),getParaToInt("rows"));
		System.out.println("page:"+page.toString());
		setAttr("total", page.getTotalRow());
		setAttr("pageSize", page.getPageSize());
		setAttr("rows", page.getList());
		renderJson();
	}
	/*
	 * cip = 192.168.1.49
	 * cport = 80
	 * cuser = admin
	 * cpd = ly623698
	 * */
	public void getCameraSet() {
		setAttr("cip", PropKit.get("cip"));
		setAttr("cport", PropKit.get("cport"));
		setAttr("cuser", PropKit.get("cuser"));
		setAttr("cpw", PropKit.get("cpw"));
		renderJson();
	}
	
	
	public void getCamera() {
		String cpw = getPara("cpw");
		if(cpw!=null&&cpw.endsWith(PropKit.get("cpw"))) {
			render("hk_camera.html");
		}else {
			render("lock-screen.html");
		}
	}
	
	public void queryEquipment(){
		Page<Tequipment> page = equipmentS.paginate(getParaToInt("page"),getParaToInt("rows"));
		System.out.println("pages:"+page.toString());
		setAttr("total", page.getTotalRow());
		setAttr("pageSize", page.getPageSize());
		setAttr("rows", page.getList());
		renderJson();
	}
	
	public void addEquipment(){
		Tequipment equipment = getModel(Tequipment.class);
		if(equipment.save())
			{
			renderJson("result",0);
			}else{
				renderJson("result",1);
			}
		
	}
	
	
	public void deletEquipment(){
		int deviceKey = getParaToInt("deviceKey");
		equipmentS.deleteById(deviceKey);
		conditioner();
	}
	
}



