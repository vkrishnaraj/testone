package com.bagnet.nettracer.tracing.utils;

public enum CreditCardType {
	
	VISA("Visa"),	
	MC("Mastercard"),	
	DISCOVER("Discover"),
	AMEX("American Express"),	
	DC("Diners Club");
	
	private final String name;
	
	private CreditCardType(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
	
}
