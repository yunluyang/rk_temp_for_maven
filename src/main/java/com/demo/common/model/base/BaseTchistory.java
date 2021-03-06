package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseTchistory<M extends BaseTchistory<M>> extends Model<M> implements IBean {

	public M setDeviceKey(java.lang.Integer DeviceKey) {
		set("DeviceKey", DeviceKey);
		return (M)this;
	}
	
	public java.lang.Integer getDeviceKey() {
		return getInt("DeviceKey");
	}

	public M setDevName(java.lang.String DevName) {
		set("DevName", DevName);
		return (M)this;
	}
	
	public java.lang.String getDevName() {
		return getStr("DevName");
	}

	public M setType(java.lang.String type) {
		set("type", type);
		return (M)this;
	}
	
	public java.lang.String getType() {
		return getStr("type");
	}

	public M setInfo(java.lang.String info) {
		set("info", info);
		return (M)this;
	}
	
	public java.lang.String getInfo() {
		return getStr("info");
	}

	public M setRecordTime(java.util.Date RecordTime) {
		set("RecordTime", RecordTime);
		return (M)this;
	}
	
	public java.util.Date getRecordTime() {
		return get("RecordTime");
	}

	public M setConditionerName(java.lang.String ConditionerName) {
		set("ConditionerName", ConditionerName);
		return (M)this;
	}
	
	public java.lang.String getConditionerName() {
		return getStr("ConditionerName");
	}

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

}
