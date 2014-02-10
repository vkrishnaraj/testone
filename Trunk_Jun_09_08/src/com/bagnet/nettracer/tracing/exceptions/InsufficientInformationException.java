package com.bagnet.nettracer.tracing.exceptions;

import aero.nettracer.fs.model.FsClaim;

public class InsufficientInformationException extends Exception {

	private static final long serialVersionUID = 1L;
	private String missingInfo;
	
	public InsufficientInformationException(Class<?> clazz) {
		super("Missing required information: " + clazz != null ? clazz.getSimpleName() : "null class");
		if (clazz != null) {
			missingInfo = clazz.getSimpleName();
			if (missingInfo.equals(FsClaim.class.getSimpleName())) {
				missingInfo = "Claim";
			}
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
