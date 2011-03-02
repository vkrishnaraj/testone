package com.nettracer.claims.core.model;

import java.util.ArrayList;
import java.util.List;

public class IncidentBean {
	private String firstName;
	private String lastName;
	private String pnr;
	private List<String> claimCheck;
	private List<IncidentBagBean> bag;
	private String email;
	private List<IncidentPhoneBean> phone;
	private int deliveryType;
	private boolean deliverWithoutSignature;
	private IncidentAddressBean deliveryAddress;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(int deliveryType) {
		this.deliveryType = deliveryType;
	}

	public boolean isDeliverWithoutSignature() {
		return deliverWithoutSignature;
	}

	public void setDeliverWithoutSignature(boolean deliverWithoutSignature) {
		this.deliverWithoutSignature = deliverWithoutSignature;
	}

	public IncidentAddressBean getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(IncidentAddressBean deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public List<String> getClaimCheck() {
		return claimCheck;
	}

	public void setClaimCheck(List<String> claimCheck) {
		this.claimCheck = claimCheck;
	}

	public List<IncidentBagBean> getBag() {
		return bag;
	}

	public void setBag(List<IncidentBagBean> bag) {
		this.bag = bag;
	}

	public List<IncidentPhoneBean> getPhone() {
		return phone;
	}

	public void setPhone(List<IncidentPhoneBean> phone) {
		this.phone = phone;
	}

}
