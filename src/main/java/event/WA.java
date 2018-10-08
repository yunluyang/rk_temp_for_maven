package event;

import java.io.Serializable;

public class WA implements Serializable{
	private String outPutPower;
	private String outPutApparentPower;
	private String totalPower;
	private String totalApparentPower;
	private String outputCurrent;
	private String outputLoadPercentage;
	private String upsStatus;
	public String getOutPutPower() {
		return outPutPower;
	}
	public void setOutPutPower(String outPutPower) {
		this.outPutPower = outPutPower;
	}
	public String getOutPutApparentPower() {
		return outPutApparentPower;
	}
	public void setOutPutApparentPower(String outPutApparentPower) {
		this.outPutApparentPower = outPutApparentPower;
	}
	public String getTotalPower() {
		return totalPower;
	}
	public void setTotalPower(String totalPower) {
		this.totalPower = totalPower;
	}
	public String getTotalApparentPower() {
		return totalApparentPower;
	}
	public void setTotalApparentPower(String totalApparentPower) {
		this.totalApparentPower = totalApparentPower;
	}
	public String getOutputCurrent() {
		return outputCurrent;
	}
	public void setOutputCurrent(String outputCurrent) {
		this.outputCurrent = outputCurrent;
	}
	public String getOutputLoadPercentage() {
		return outputLoadPercentage;
	}
	public void setOutputLoadPercentage(String outputLoadPercentage) {
		this.outputLoadPercentage = outputLoadPercentage;
	}
	public String getUpsStatus() {
		return upsStatus;
	}
	public void setUpsStatus(String upsStatus) {
		this.upsStatus = upsStatus;
	}
	@Override
	public String toString() {
		return "WA [outPutPower=" + outPutPower + ", outPutApparentPower=" + outPutApparentPower + ", totalPower="
				+ totalPower + ", totalApparentPower=" + totalApparentPower + ", outputCurrent=" + outputCurrent
				+ ", outputLoadPercentage=" + outputLoadPercentage + ", upsStatus=" + upsStatus + "]";
	}
	
}
