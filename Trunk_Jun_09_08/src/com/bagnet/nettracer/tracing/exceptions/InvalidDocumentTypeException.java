package com.bagnet.nettracer.tracing.exceptions;

public class InvalidDocumentTypeException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidDocumentTypeException() {
		super("Undefined DocumentTemplateType");
	}

}
