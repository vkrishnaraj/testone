package com.bagnet.nettracer.tracing.exceptions;

public class InsufficientInformationException extends Exception {

	private static final long serialVersionUID = 1L;

	public InsufficientInformationException(Class<?> clazz) {
		super("Missing required information: " + clazz != null ? clazz.getSimpleName() : "null class");
	}
	
	public InsufficientInformationException(String message) {
		super("Missing required information: " + message != null ? message : "null message");
	}
}
