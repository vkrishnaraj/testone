package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Administrator
 * 
 * @hibernate.typedef name="worldTracerStatus" class="com.bagnet.nettracer.tracing.utils.StringEnumUserType"
 * @hibernate.typedef-param typedef-name="worldTracerStatus" name="enumClassname"
 * 			value="com.bagnet.nettracer.tracing.db.WorldTracerFile$WTStatus"
 */
public class WorldTracerFile implements Serializable{

	
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
	 * 
	 * @hibernate.property type="worldTracerStatus"
	 */
	public WTStatus getWt_status() {
		return status;
	}
	public void setWt_status(WTStatus status) {
		this.status = status;
	}
	
	/**
	 * @return Returns the numpassengers.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getWt_id() {
		return wt_id;
	}
	public void setWt_id(String wt_id) {
		this.wt_id = wt_id;
	}
	
	@Override
	public boolean equals(Object otherObject) {
		// TODO Auto-generated method stub
		if(this == otherObject) return true;
		if(otherObject == null) return false;
		if(!(otherObject instanceof WorldTracerFile)) return false;
		
		if(wt_id == null || status == null) return false;
		
		WorldTracerFile o = (WorldTracerFile) otherObject;
		return (wt_id == null ? o.wt_id == null : wt_id.equals(o.wt_id))
				&& (status == null ? o.status == null : status.equals(o.status));
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + (status == null ? 0 : status.hashCode());
		result = 37 * result + (wt_id == null ? 0 : wt_id.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s:%s", wt_id, status);
	}
	
	
	public static enum WTStatus {
		ACTIVE, SUSPENDED, CLOSED
	}
}
