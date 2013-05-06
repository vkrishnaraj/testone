package aero.nettracer.lfc.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RateBean implements Serializable{

	private static final long serialVersionUID = -7301854773110494378L;
	
	private String rateType;
	private String rateKey;
	private String rateAmount;
	private String rateTax;
	private String estDeliveryDate;
	
	
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

	public String getRateTax() {
		return rateTax;
	}

	public void setRateTax(String rateTax) {
		this.rateTax = rateTax;
	}

	public String getEstDeliveryDate() {
		return estDeliveryDate;
	}
	
	public void setEstDeliveryDate(String estDeliveryDate) {
		this.estDeliveryDate = estDeliveryDate;
	}
}
