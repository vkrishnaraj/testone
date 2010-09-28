package aero.nettracer.serviceprovider.ws_1_0.common;

import java.util.Calendar;

public class BaggageDeliveryInfo {
	
	/*
	   Rynn's Statuses
	   ===============
	   OD- Bag Delivered
	   UP- Bag Picked up from the BSO
       OF- Bag on the Truck
       UN- Bag unable to Deliver
	 */
	
	private String baggageDescription;
	private String bagTagNumber;
	private Calendar deliveredOn;
	private String deliveryStatus;

	public String getBaggageDescription() {
		return baggageDescription;
	}

	public void setBaggageDescription(String baggageDescription) {
		this.baggageDescription = baggageDescription;
	}

	public String getBagTagNumber() {
		return bagTagNumber;
	}

	public void setBagTagNumber(String bagTagNumber) {
		this.bagTagNumber = bagTagNumber;
	}

	public Calendar getDeliveredOn() {
		return deliveredOn;
	}

	public void setDeliveredOn(Calendar deliveredOn) {
		this.deliveredOn = deliveredOn;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
}
