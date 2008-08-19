package com.bagnet.nettracer.tracing.db;

/**
 * 
 * @author Noah
 *
 */
public class WorldTracerFile {
	
	private WTStatus status;
	private String wt_id;
	
	public WorldTracerFile() {}
	
	public WorldTracerFile(String id) {
		this.status = WTStatus.ACTIVE;
		this.wt_id = id;
	}
	
	public WorldTracerFile(String wt_id, WTStatus wtStatus) {
		this.wt_id = wt_id;
		this.status = wtStatus;
	}

	/**
	 * @hibernate.property type="worldTracerStatus"
	 */
	public WTStatus getWt_status() {
		return status;
	}
	public void setWt_status(WTStatus status) {
		this.status = status;
	}
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getWt_id() {
		return wt_id;
	}
	public void setWt_id(String wt_id) {
		this.wt_id = wt_id;
	}
	
	public static enum WTStatus {
		ACTIVE, SUSPENDED, CLOSED
	}
}
