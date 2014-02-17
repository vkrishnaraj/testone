package com.bagnet.nettracer.tracing.exceptions;


public class InsufficientInformationException extends Exception {

	private static final long serialVersionUID = 1L;
	private String missingInfo;
	
	public InsufficientInformationException(Class<?> clazz) {
		super("Missing required information: " + clazz != null ? clazz.getSimpleName() : "null class");
		if (clazz != null) {
			missingInfo = clazz.getSimpleName();
		}
	}
	
	public InsufficientInformationException(String message) {
		super("Missing required information: " + message != null ? message : "null message");
		missingInfo = message;
	}
	
	public String getMissingInfo() {
		return missingInfo;
	}
}
