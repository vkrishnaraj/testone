package aero.nettracer.fs.model;

public class CreditCard {
	public String getCcType() {
		return ccType;
	}
	public void setCcType(String ccType) {
		this.ccType = ccType;
	}
	public String getCcNumLastFour() {
		return ccNumLastFour;
	}
	public void setCcNumLastFour(String ccNumLastFour) {
		this.ccNumLastFour = ccNumLastFour;
	}
	public String getCcNumber() {
		return ccNumber;
	}
	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}
	public int getCcExpMonth() {
		return ccExpMonth;
	}
	public void setCcExpMonth(int ccExpMonth) {
		this.ccExpMonth = ccExpMonth;
	}
	public int getCcExpYear() {
		return ccExpYear;
	}
	public void setCcExpYear(int ccExpYear) {
		this.ccExpYear = ccExpYear;
	}
	String ccType;
	String ccNumLastFour;
	String ccNumber;
	int ccExpMonth;
	int ccExpYear;
}
