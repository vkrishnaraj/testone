package aero.nettracer.serviceprovider.wt_1_0.services;

public class WorldTracerRecordNotFoundException extends WorldTracerException{
	public WorldTracerRecordNotFoundException() {
	}

	public WorldTracerRecordNotFoundException(String message) {
		super(message);
	}

	public WorldTracerRecordNotFoundException(Throwable cause) {
		super(cause);
	}

	public WorldTracerRecordNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
