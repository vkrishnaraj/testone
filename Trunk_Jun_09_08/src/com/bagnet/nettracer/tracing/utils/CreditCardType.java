package com.bagnet.nettracer.tracing.utils;

public enum CreditCardType {
	
	VI("Visa"),	
	CA("Mastercard"),	
	DS("Discover"),
	AX("American Express"),	
	DC("Diners Club");
	
	private final String name;
	
	private CreditCardType(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
	
}
