package aero.nettracer.lfc.model;

import java.io.Serializable;

public class LoginBean implements Serializable{

	private static final long serialVersionUID = -3533031594602595371L;
	
	private String lastName;
	private String trackingNumber;
	private String hashKey;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getHashKey() {
		return hashKey;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}


}
