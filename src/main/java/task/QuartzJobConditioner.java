package task;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.demo.common.model.Tchistory;
import com.demo.common.model.Tconditioner;
import com.demo.common.model.Tset;
import com.demo.index.TBalarmService;
import com.demo.index.TBconditionerService;
import com.demo.index.TBhistoryService;
import com.demo.index.TEquipmentService;
import com.demo.index.TSetService;
import com.demo.index.TchistoryService;
import com.jfinal.plugin.redis.Redis;

import event.DeviceDate;
import utils.Modbus4jUtils;
import utils.ReadAWriteUtil;
import utils.SendMsg;

import java.util.Date;
import java.util.List;

/**
 */
public class QuartzJobConditioner implements Job {
	
	TBconditionerService conditionerS = new TBconditionerService();
	TBalarmService alarmS = new TBalarmService();
	TBhistoryService historyS = new TBhistoryService();
	TchistoryService cHistoryS = new TchistoryService();
	TSetService setS = new TSetService();
	TEquipmentService equipmentS = new TEquipmentService();
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    	List<DeviceDate> result = Redis.use("temp").get("result");
		System.out.println("ConditionerTask:"+result.toString());
		if(result!=null&&result.size()!=0){
			for (DeviceDate deviceDate : result) {
	    		Tconditioner tconditioner = conditionerS.getByDeviceKey(deviceDate.getDevKey());
	            double devTempValue = Double.parseDouble(deviceDate.getDevTempValue());
	            double devHumiValue = Double.parseDouble(deviceDate.getDevHumiValue());
	            int tempStatus = deviceDate.getTempStatus();
	            int humiStatus = deviceDate.getHumiStatus();
	            int devKey = deviceDate.getDevKey();
	            String devName = deviceDate.getDevName();
	            double devTempValueMax = setS.getSetting().getDevTempValueMax();
	            double devHumiValueMax = setS.getSetting().getDevHumiValueMax();
	            double devTempValueMin = setS.getSetting().getDevTempValueMin();
	            double devHumiValueMin = setS.getSetting().getDevHumiValueMin();
	            Boolean devStatus = deviceDate.getDevStatus();
	    		if(tconditioner!=null){
	    			if(devStatus) {
	        			//空调控制
	    			if(devTempValue>=devTempValueMax){
	    				System.out.println("devTempValue>=devTempValueMax:"+devTempValue);
	                	//温度高于最高值 开空调
	    				ReadAWriteUtil.writeRegisterTest(tconditioner.getCOM(), tconditioner.getBaud(), tconditioner.getSlaveId(), 0,0xffff);
	    				Tchistory tchistory = new Tchistory();
	        			tchistory.setDeviceKey(devKey);
	        			tchistory.setDevName(devName);
	        			tchistory.setType("打开空调制冷");
	        			tchistory.setInfo("成功");
	        			tchistory.setConditionerName(tconditioner.getConditionerName());
	        			tchistory.setRecordTime(new Date());
	        			tchistory.save();
	    				
	    			}
	    			if(devTempValue<=devTempValueMin){
	    				System.out.println("devTempValue<=devTempValueMax):"+devTempValue);
	                	//温度低于最低值  关空调
	                	ReadAWriteUtil.writeRegisterTest(tconditioner.getCOM(), tconditioner.getBaud(), tconditioner.getSlaveId(), 1,0xffff);
	                	Tchistory tchistory = new Tchistory();
	                	tchistory.setDeviceKey(devKey);
	        			tchistory.setDevName(devName);
	        			tchistory.setType("关闭空调除湿");
	        			tchistory.setRecordTime(new Date());
	        			tchistory.setInfo("成功");
	        			tchistory.setConditionerName(tconditioner.getConditionerName());
	        			tchistory.save();
	    			}
	    			if(devHumiValue>=devHumiValueMax){
	    				System.out.println("devHumiValue>=devHumiValueMax):"+devHumiValue);
	    				System.out.println("devHumiValue>=devHumiValueMax):"+devHumiValueMax);
	    				//湿度高于最高值 开空调
	    				ReadAWriteUtil.writeRegisterTest(tconditioner.getCOM(), tconditioner.getBaud(), tconditioner.getSlaveId(), 2,0xffff);
	    				Tchistory tchistory = new Tchistory();
	    				tchistory.setDeviceKey(devKey);
	        			tchistory.setDevName(devName);
	        			tchistory.setRecordTime(new Date());
	        			tchistory.setType("打开空调除湿");
	        			tchistory.setConditionerName(tconditioner.getConditionerName());
	        			tchistory.setInfo("成功");
	        			tchistory.save();
	    			}
	    			if(devHumiValue<=devHumiValueMin){
	    				System.out.println("devHumiValue<=devHumiValueMin:"+devHumiValue);
	    				//湿度低于最低值
	    				ReadAWriteUtil.writeRegisterTest(tconditioner.getCOM(), tconditioner.getBaud(), tconditioner.getSlaveId(), 3,0xffff);
	    				Tchistory tchistory = new Tchistory();
	    				tchistory.setDeviceKey(devKey);
	        			tchistory.setDevName(devName);
	        			tchistory.setConditionerName(tconditioner.getConditionerName());
	        			tchistory.setRecordTime(new Date());
	        			tchistory.setType("关闭空调除湿");
	        			tchistory.setInfo("成功");
	        			tchistory.save();
	    			}
	    			if(tempStatus!=0) {
	    				/*
	    				 * 发送报警短信
	    				 * TempStatus：模拟量一报警状态（0表示不报警，1表示超上限，2表示超下限）
	    				HumiStatus：模拟量二报警状态（0表示不报警，1表示超上限，2表示超下限）
	    				*/
	    				Tset tset = setS.getSetting();
	    				String phone1= tset.getPhone1();
	    				String phone2= tset.getPhone2();
	    				String phone3= tset.getPhone3();
	    				StringBuilder sb = new StringBuilder();
	    				JSONObject object = new JSONObject();
	    				if(phone1 != null && phone1.length() != 0) {
	    					sb.append(phone1+",");
	    				}
	    				if(phone2 != null && phone2.length() != 0) {
	    					sb.append(phone2+",");
	    				}
	    				if(phone3 != null && phone3.length() != 0) {
	    					sb.append(phone3);
	    				}
	    		        SendSmsRequest request = new SendSmsRequest();
	    		        request.setPhoneNumbers(sb.toString());
	    		        request.setSignName("孜晟新能源环境监控");
	    		        request.setTemplateCode("SMS_143719799");
	    		        object.put("devname", devName);
	    		        if(tempStatus==1) {
	    		        	 object.put("status", "温度"+devTempValue+"°高于上线");
	    		        }else {
	    		        	object.put("status", "温度"+devTempValue+"°低于下线");
	    		        }
	    		        request.setTemplateParam(object.toJSONString());

	    		        try {
							SendSmsResponse response = SendMsg.sendSms(request);
							if(response.getCode()=="OK") {
								if(tempStatus==1) {
									new Tchistory().setDeviceKey(devKey).setDevName(devName).setType("温度超高发送短信").setInfo("发送成功").setRecordTime(new Date()).save();
			    		        }else {
			    		        	new Tchistory().setDeviceKey(devKey).setDevName(devName).setType("温度过低发送短信").setInfo("发送成功").setRecordTime(new Date()).save();
			    		        }
								
							}else {
								if(tempStatus==1) {
									new Tchistory().setDeviceKey(devKey).setDevName(devName).setType("温度超高发送短信").setInfo("发送失败").setRecordTime(new Date()).save();
			    		        }else {
			    		        	new Tchistory().setDeviceKey(devKey).setDevName(devName).setType("温度过低发送短信").setInfo("发送失败").setRecordTime(new Date()).save();
			    		        }
							}
						} catch (ClientException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    		        
	    		        
	    			}
	    			
	    			if(humiStatus!=0) {
	    				/*
	    				 * 发送报警短信
	    				 * TempStatus：模拟量一报警状态（0表示不报警，1表示超上限，2表示超下限）
	    				HumiStatus：模拟量二报警状态（0表示不报警，1表示超上限，2表示超下限）
	    				*/
	    				Tset tset = setS.getSetting();
	    				String phone1= tset.getPhone1();
	    				String phone2= tset.getPhone2();
	    				String phone3= tset.getPhone3();
	    				StringBuilder sb = new StringBuilder();
	    				JSONObject object = new JSONObject();
	    				if(phone1 != null && phone1.length() != 0) {
	    					sb.append(phone1+",");
	    				}
	    				if(phone2 != null && phone2.length() != 0) {
	    					sb.append(phone2+",");
	    				}
	    				if(phone3 != null && phone3.length() != 0) {
	    					sb.append(phone3);
	    				}
	    		        SendSmsRequest request = new SendSmsRequest();
	    		        request.setPhoneNumbers(sb.toString());
	    		        request.setSignName("孜晟新能源环境监控");
	    		        request.setTemplateCode("SMS_143719799");
	    		        object.put("devname", devName);
	    		        if(tempStatus==1) {
	    		        	 object.put("status", "湿度"+devHumiValue+"°高于上线");
	    		        }else {
	    		        	object.put("status", "湿度"+devHumiValue+"°低于下线");
	    		        }
	    		        request.setTemplateParam(object.toJSONString());

	    		        try {
							SendSmsResponse response = SendMsg.sendSms(request);
							if(response.getCode()=="OK") {
								if(humiStatus==1) {
									new Tchistory().setDeviceKey(devKey).setDevName(devName).setType("湿度超高发送短信").setInfo("发送成功").setRecordTime(new Date()).save();
			    		        }else {
			    		        	new Tchistory().setDeviceKey(devKey).setDevName(devName).setType("湿度过低发送短信").setInfo("发送成功").setRecordTime(new Date()).save();
			    		        }
								
							}else {
								if(humiStatus==1) {
									new Tchistory().setDeviceKey(devKey).setDevName(devName).setType("湿度超高发送短信").setInfo("发送失败").setRecordTime(new Date()).save();
			    		        }else {
			    		        	new Tchistory().setDeviceKey(devKey).setDevName(devName).setType("湿度过低发送短信").setInfo("发送失败").setRecordTime(new Date()).save();
			    		        }
							}
						} catch (ClientException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    		        
	    		        
	    			}
	    			}
	    		}else{
	    			// 未绑定空调报警
	    			Tchistory tchistory = new Tchistory();
	    			tchistory.setDeviceKey(devKey);
	    			tchistory.setDevName(devName);
	    			tchistory.setRecordTime(new Date());
	    			tchistory.setType("未绑定空调");
	    			tchistory.setInfo("失败");
	    			tchistory.save();
	    		}
	            
	        }
		}
        System.out.println("QuartzJobConditioner:" + new Date());
    }
}