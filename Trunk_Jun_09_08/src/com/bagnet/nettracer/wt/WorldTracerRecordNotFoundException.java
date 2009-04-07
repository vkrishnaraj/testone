package com.bagnet.nettracer.wt;

public class WorldTracerRecordNotFoundException extends WorldTracerException {

	private String wt_id;
	
	public WorldTracerRecordNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WorldTracerRecordNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public WorldTracerRecordNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public WorldTracerRecordNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public String getWt_id() {
		return wt_id;
	}

	public void setWt_id(String wt_id) {
		this.wt_id = wt_id;
	}
	

}
