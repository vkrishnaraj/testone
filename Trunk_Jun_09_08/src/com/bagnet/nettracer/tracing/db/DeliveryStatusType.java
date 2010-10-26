package com.bagnet.nettracer.tracing.db;

/*
OD- Bag Delivered
UP- Bag Picked up from the BSO
OF- Bag on the Truck
UN- Bag unable to Deliver
 */

public enum DeliveryStatusType {
	DELIVERED, PICKED_UP, ON_TRUCK, UNABLE_DELIVER;
	
	public String getDescriptionKey() {
		return "status.delivery." + this.name();
	}
}
