package com.nettracer.claims.core.exception;

public class SimplePersistenceException extends Exception {
	private static final long serialVersionUID = 1L;

	public SimplePersistenceException(String message) {
		super(message);
	}
	
	public SimplePersistenceException(Exception e) {
		super(e);
	}
}
