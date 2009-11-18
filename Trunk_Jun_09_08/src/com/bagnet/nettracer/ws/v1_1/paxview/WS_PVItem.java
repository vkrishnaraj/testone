package com.bagnet.nettracer.ws.v1_1.paxview;

public class WS_PVItem {
	private String bagstatus;
	private String claimchecknum;
	private String color;
	private String type;
	
	// bdo_passenger data
	private String deliveryAddress;

	public String getBagstatus() {
		return bagstatus;
	}

	public String getClaimchecknum() {
		return claimchecknum;
	}

	public String getColor() {
		return color;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public String getType() {
		return type;
	}

	public void setBagstatus(String bagstatus) {
		this.bagstatus = bagstatus;
	}

	public void setClaimchecknum(String claimchecknum) {
		this.claimchecknum = claimchecknum;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public void setType(String type) {
		this.type = type;
	}
}
