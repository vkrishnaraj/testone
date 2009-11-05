package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service;

public class WorldTracerConnectionException extends RuntimeException {

	private static final long serialVersionUID = -3155435867769385979L;

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
