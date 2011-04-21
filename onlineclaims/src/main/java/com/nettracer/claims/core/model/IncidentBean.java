package com.nettracer.claims.core.model;

import java.util.ArrayList;
import java.util.List;

import com.nettracer.claims.utils.IncidentProperties;

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
	private String incidentID;
	
	public String getIncidentID() {
		return incidentID;
	}

	public void setIncidentID(String incidentID) {
		this.incidentID = incidentID;
	}

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
	
	public String addPhone() {
		if (phone == null) {
			phone = new ArrayList<IncidentPhoneBean>();
		}
		phone.add(new IncidentPhoneBean());
		return null;
	}
	
	public String getDeliverWithoutSignatureLabel() {
		return (isDeliverWithoutSignature() ? "Yes" : "No");
	}
	
	public String getDeliveryTypeLabel() {
		return IncidentProperties.get("stepthree_radio_deltype_" + getDeliveryType());
	}

}
