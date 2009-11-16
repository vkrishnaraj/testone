package aero.nettracer.serviceprovider.wt_1_0.common;

import java.util.Calendar;

public class Expenses {
	private String paycode;
	private float checkamt;
	private String currrency;
	private Calendar approvalDate;

	public String getPaycode() {
		return paycode;
	}

	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}

	public float getCheckamt() {
		return checkamt;
	}

	public void setCheckamt(float checkamt) {
		this.checkamt = checkamt;
	}

	public String getCurrrency() {
		return currrency;
	}

	public void setCurrrency(String currrency) {
		this.currrency = currrency;
	}

	public Calendar getApprovalDate() {
  	return approvalDate;
  }

	public void setApprovalDate(Calendar approvalDate) {
  	this.approvalDate = approvalDate;
  }

}
