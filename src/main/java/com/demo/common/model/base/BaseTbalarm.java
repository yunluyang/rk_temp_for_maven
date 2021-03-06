package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseTbalarm<M extends BaseTbalarm<M>> extends Model<M> implements IBean {

	public M setID(java.lang.Integer ID) {
		set("ID", ID);
		return (M)this;
	}
	
	public java.lang.Integer getID() {
		return getInt("ID");
	}

	public M setDeviceKey(java.lang.Integer DeviceKey) {
		set("DeviceKey", DeviceKey);
		return (M)this;
	}
	
	public java.lang.Integer getDeviceKey() {
		return getInt("DeviceKey");
	}

	public M setDeviceName(java.lang.String DeviceName) {
		set("DeviceName", DeviceName);
		return (M)this;
	}
	
	public java.lang.String getDeviceName() {
		return getStr("DeviceName");
	}

	public M setAlarmType(java.lang.String AlarmType) {
		set("AlarmType", AlarmType);
		return (M)this;
	}
	
	public java.lang.String getAlarmType() {
		return getStr("AlarmType");
	}

	public M setAlarmMessage(java.lang.String AlarmMessage) {
		set("AlarmMessage", AlarmMessage);
		return (M)this;
	}
	
	public java.lang.String getAlarmMessage() {
		return getStr("AlarmMessage");
	}

	public M setAlarmRange(java.lang.String AlarmRange) {
		set("AlarmRange", AlarmRange);
		return (M)this;
	}
	
	public java.lang.String getAlarmRange() {
		return getStr("AlarmRange");
	}

	public M setDataValue(java.lang.Double DataValue) {
		set("DataValue", DataValue);
		return (M)this;
	}
	
	public java.lang.Double getDataValue() {
		return getDouble("DataValue");
	}

	public M setRecordTime(java.util.Date RecordTime) {
		set("RecordTime", RecordTime);
		return (M)this;
	}
	
	public java.util.Date getRecordTime() {
		return get("RecordTime");
	}

}
