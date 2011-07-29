package aero.nettracer.fs.service.objects;

public class Reservation {
	private Person[] claimants;
	private Segment[] segments;
	private String recordLocator;
	private String formOfPayment;
	private String ccType;
	private String ccNumber;
	private String ccNumLastFour;
	private int ccExpMonth;
	private int ccExpYear;

	public Person[] getClaimants() {
		return claimants;
	}

	public void setClaimants(Person[] claimants) {
		this.claimants = claimants;
	}

	public String getRecordLocator() {
		return recordLocator;
	}

	public void setRecordLocator(String recordLocator) {
		this.recordLocator = recordLocator;
	}

	public String getFormOfPayment() {
		return formOfPayment;
	}

	public void setFormOfPayment(String formOfPayment) {
		this.formOfPayment = formOfPayment;
	}

	public String getCcType() {
		return ccType;
	}

	public void setCcType(String ccType) {
		this.ccType = ccType;
	}

	public String getCcNumber() {
		return ccNumber;
	}

	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}

	public String getCcNumLastFour() {
		return ccNumLastFour;
	}

	public void setCcNumLastFour(String ccNumLastFour) {
		this.ccNumLastFour = ccNumLastFour;
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

	public Segment[] getSegments() {
		return segments;
	}

	public void setSegments(Segment[] segments) {
		this.segments = segments;
	}

}
