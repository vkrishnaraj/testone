package aero.nettracer.lfc.model;

import java.io.Serializable;

public class CCBean implements Serializable{

	private static final long serialVersionUID = 6759537869024990873L;
	
	private String ccnumber;
	private String ccname;
	private String ccvendor;
	private String ccexpirationmonth;
	private String ccexpirationyear;
	private String ccsecurity;
	
	public String getCcnumber() {
		return ccnumber;
	}
	public void setCcnumber(String ccnumber) {
		this.ccnumber = ccnumber;
	}
	
	public String getCcname() {
		return ccname;
	}
	public void setCcname(String ccname) {
		this.ccname = ccname;
	}
	
	public String getCcvendor() {
		return ccvendor;
	}
	public void setCcvendor(String ccvendor) {
		this.ccvendor = ccvendor;
	}
	
	public String getCcexpirationyear() {
		return ccexpirationyear;
	}
	public void setCcexpirationyear(String ccexpirationyear) {
		this.ccexpirationyear = ccexpirationyear;
	}
	
	public String getCcsecurity() {
		return ccsecurity;
	}
	public void setCcsecurity(String ccsecurity) {
		this.ccsecurity = ccsecurity;
	}
	
	public String getCcexp() {
		return ccexpirationyear+ccexpirationmonth;
	}
	
	public String getCcexpirationmonth() {
		return ccexpirationmonth;
	}
	public void setCcexpirationmonth(String ccexpirationmonth) {
		this.ccexpirationmonth = ccexpirationmonth;
	}
	public String getCc4number() {
		if (ccnumber == null || ccnumber.length() < 4) {
			return "";
		}
		return "************"+ccnumber.substring(ccnumber.length()-4,ccnumber.length());
	}
}
