package com.bagnet.nettracer.tracing.constant;

public enum IssuanceItemType {

	INVENTORY("Inventory"),
	QUANTITY("Quantity");
	
	private String defaultName;
	
	private IssuanceItemType(String defaultName) {
		this.defaultName = defaultName;
	}
	
	public String getDisplayName() {
		return this.defaultName;
	}
	
	@Override
	public String toString() {
		return getDisplayName();
	}
	
}
