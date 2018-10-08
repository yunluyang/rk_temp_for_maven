package event;

import java.io.Serializable;

public class Q6 implements Serializable{
	private String inputVoltageR;
	private String inputVoltageS;
	public String getWarrnigCode() {
		return warrnigCode;
	}
	public void setWarrnigCode(String warrnigCode) {
		this.warrnigCode = warrnigCode;
	}
	private String inputVoltageT;
	private String inputFrequency;
	private String outputVoltageR;
	private String outputVoltageS;
	private String outputVoltageT;
	private String outputFrequency;
	private String outputCurrent;
	private String batteryPostive;
	private String batteryNegative;
	private String temp;
	private String batteryTime;
	private String batteryPercentage;
	private String KB;
	private String errorCode;
	private String warrnigCode;
	
	
	
	
	@Override
	public String toString() {
		return "Q6 [inputVoltageR=" + inputVoltageR + ", inputVoltageS=" + inputVoltageS + ", inputVoltageT="
				+ inputVoltageT + ", inputFrequency=" + inputFrequency + ", outputVoltageR=" + outputVoltageR
				+ ", outputVoltageS=" + outputVoltageS + ", outputVoltageT=" + outputVoltageT + ", outputFrequency="
				+ outputFrequency + ", outputCurrent=" + outputCurrent + ", batteryPostive=" + batteryPostive
				+ ", batteryNegative=" + batteryNegative + ", temp=" + temp + ", batteryTime=" + batteryTime
				+ ", batteryPercentage=" + batteryPercentage + ", KB=" + KB + ", errorCode=" + errorCode
				+ ", warrnigCode=" + warrnigCode + "]";
	}
	public String getInputVoltageR() {
		return inputVoltageR;
	}
	public void setInputVoltageR(String inputVoltageR) {
		this.inputVoltageR = inputVoltageR;
	}
	public String getInputVoltageS() {
		return inputVoltageS;
	}
	public void setInputVoltageS(String inputVoltageS) {
		this.inputVoltageS = inputVoltageS;
	}
	public String getInputVoltageT() {
		return inputVoltageT;
	}
	public void setInputVoltageT(String inputVoltageT) {
		this.inputVoltageT = inputVoltageT;
	}
	public String getInputFrequency() {
		return inputFrequency;
	}
	public void setInputFrequency(String inputFrequency) {
		this.inputFrequency = inputFrequency;
	}
	public String getOutputVoltageR() {
		return outputVoltageR;
	}
	public void setOutputVoltageR(String outputVoltageR) {
		this.outputVoltageR = outputVoltageR;
	}
	public String getOutputVoltageS() {
		return outputVoltageS;
	}
	public void setOutputVoltageS(String outputVoltageS) {
		this.outputVoltageS = outputVoltageS;
	}
	public String getOutputVoltageT() {
		return outputVoltageT;
	}
	public void setOutputVoltageT(String outputVoltageT) {
		this.outputVoltageT = outputVoltageT;
	}
	public String getOutputFrequency() {
		return outputFrequency;
	}
	public void setOutputFrequency(String outputFrequency) {
		this.outputFrequency = outputFrequency;
	}
	public String getOutputCurrent() {
		return outputCurrent;
	}
	public void setOutputCurrent(String outputCurrent) {
		this.outputCurrent = outputCurrent;
	}
	public String getBatteryPostive() {
		return batteryPostive;
	}
	public void setBatteryPostive(String batteryPostive) {
		this.batteryPostive = batteryPostive;
	}
	public String getBatteryNegative() {
		return batteryNegative;
	}
	public void setBatteryNegative(String batteryNegative) {
		this.batteryNegative = batteryNegative;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getBatteryTime() {
		return batteryTime;
	}
	public void setBatteryTime(String batteryTime) {
		this.batteryTime = batteryTime;
	}
	public String getBatteryPercentage() {
		return batteryPercentage;
	}
	public void setBatteryPercentage(String batteryPercentage) {
		this.batteryPercentage = batteryPercentage;
	}
	public String getKB() {
		return KB;
	}
	public void setKB(String kB) {
		KB = kB;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
}
