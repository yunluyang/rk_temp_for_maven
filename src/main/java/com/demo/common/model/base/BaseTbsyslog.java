package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseTbsyslog<M extends BaseTbsyslog<M>> extends Model<M> implements IBean {

	public M setID(java.lang.Long ID) {
		set("ID", ID);
		return (M)this;
	}
	
	public java.lang.Long getID() {
		return getLong("ID");
	}

	public M setUserName(java.lang.String UserName) {
		set("UserName", UserName);
		return (M)this;
	}
	
	public java.lang.String getUserName() {
		return getStr("UserName");
	}

	public M setOperate(java.lang.String Operate) {
		set("Operate", Operate);
		return (M)this;
	}
	
	public java.lang.String getOperate() {
		return getStr("Operate");
	}

	public M setDetails(java.lang.String Details) {
		set("Details", Details);
		return (M)this;
	}
	
	public java.lang.String getDetails() {
		return getStr("Details");
	}

	public M setResult(java.lang.String Result) {
		set("Result", Result);
		return (M)this;
	}
	
	public java.lang.String getResult() {
		return getStr("Result");
	}

	public M setIP(java.lang.String IP) {
		set("IP", IP);
		return (M)this;
	}
	
	public java.lang.String getIP() {
		return getStr("IP");
	}

	public M setRecordTime(java.util.Date RecordTime) {
		set("RecordTime", RecordTime);
		return (M)this;
	}
	
	public java.util.Date getRecordTime() {
		return get("RecordTime");
	}

}