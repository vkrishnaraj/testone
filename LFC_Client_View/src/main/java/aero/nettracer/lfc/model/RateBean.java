package aero.nettracer.lfc.model;

import java.io.Serializable;

public class RateBean implements Serializable{

	private static final long serialVersionUID = -7301854773110494378L;
	
	private String rateType;
	private String rateKey;
	private String rateAmount;
	
	
	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	
	public String getRateKey() {
		return rateKey;
	}

	public void setRateKey(String rateKey) {
		this.rateKey = rateKey;
	}
	
	public String getRateAmount() {
		return rateAmount;
	}

	public void setRateAmount(String rateAmount) {
		this.rateAmount = rateAmount;
	}
}
