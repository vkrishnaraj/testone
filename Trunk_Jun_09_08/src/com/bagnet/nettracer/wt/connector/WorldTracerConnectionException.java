package com.bagnet.nettracer.wt.connector;

public class WorldTracerConnectionException extends RuntimeException {

	public WorldTracerConnectionException() {
	}

	public WorldTracerConnectionException(String message) {
		super(message);
	}

	public WorldTracerConnectionException(Throwable cause) {
		super(cause);
	}

	public WorldTracerConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

}
