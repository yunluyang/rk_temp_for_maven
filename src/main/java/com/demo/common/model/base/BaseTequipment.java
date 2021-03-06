package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseTequipment<M extends BaseTequipment<M>> extends Model<M> implements IBean {

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

	public M setSlaveId(java.lang.Integer SlaveId) {
		set("SlaveId", SlaveId);
		return (M)this;
	}
	
	public java.lang.Integer getSlaveId() {
		return getInt("SlaveId");
	}

	public M setComm(java.lang.String Comm) {
		set("Comm", Comm);
		return (M)this;
	}
	
	public java.lang.String getComm() {
		return getStr("Comm");
	}

	public M setType(java.lang.Integer Type) {
		set("Type", Type);
		return (M)this;
	}
	
	public java.lang.Integer getType() {
		return getInt("Type");
	}

}
