package event;

import java.io.Serializable;

public class DeviceDate implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int DevKey;
    private String DevName;
    private String DevTempName;
    private String DevTempValue;
    private String DevHumiName;
    private String DevHumiValue;
    private Boolean DevStatus;
    private int TempStatus;
    private int HumiStatus;
    private int SomgStatus;
    private int Type; 

    public DeviceDate() {
		super();
	}

	public DeviceDate(int devKey, String devName, String devTempName, String devTempValue, String devHumiName,
			String devHumiValue, Boolean devStatus, int tempStatus, int humiStatus, int somgStatus, int type) {
		super();
		DevKey = devKey;
		DevName = devName;
		DevTempName = devTempName;
		DevTempValue = devTempValue;
		DevHumiName = devHumiName;
		DevHumiValue = devHumiValue;
		DevStatus = devStatus;
		TempStatus = tempStatus;
		HumiStatus = humiStatus;
		SomgStatus = somgStatus;
		Type = type;
	}

	@Override
	public String toString() {
		return "DeviceDate [DevKey=" + DevKey + ", DevName=" + DevName + ", DevTempName=" + DevTempName
				+ ", DevTempValue=" + DevTempValue + ", DevHumiName=" + DevHumiName + ", DevHumiValue=" + DevHumiValue
				+ ", DevStatus=" + DevStatus + ", TempStatus=" + TempStatus + ", HumiStatus=" + HumiStatus
				+ ", SomgStatus=" + SomgStatus + ", Type=" + Type + "]";
	}

	public int getSomgStatus() {
		return SomgStatus;
	}

	public void setSomgStatus(int somgStatus) {
		SomgStatus = somgStatus;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public int getDevKey() {
        return DevKey;
    }

    public void setDevKey(int DevKey) {
        this.DevKey = DevKey;
    }

    public String getDevName() {
        return DevName;
    }

    public void setDevName(String DevName) {
        this.DevName = DevName;
    }

    public String getDevTempName() {
        return DevTempName;
    }

    public void setDevTempName(String DevTempName) {
        this.DevTempName = DevTempName;
    }

    public String getDevTempValue() {
        return DevTempValue;
    }

    public void setDevTempValue(String DevTempValue) {
        this.DevTempValue = DevTempValue;
    }

    public String getDevHumiName() {
        return DevHumiName;
    }

    public void setDevHumiName(String DevHumiName) {
        this.DevHumiName = DevHumiName;
    }

    public String getDevHumiValue() {
        return DevHumiValue;
    }

    public void setDevHumiValue(String DevHumiValue) {
        this.DevHumiValue = DevHumiValue;
    }

    public Boolean getDevStatus() {
        return DevStatus;
    }

    public void setDevStatus(Boolean DevStatus) {
        this.DevStatus = DevStatus;
    }

    public int getTempStatus() {
        return TempStatus;
    }

    public void setTempStatus(int TempStatus) {
        this.TempStatus = TempStatus;
    }

    public int getHumiStatus() {
        return HumiStatus;
    }

    public void setHumiStatus(int HumiStatus) {
        this.HumiStatus = HumiStatus;
    }
}
