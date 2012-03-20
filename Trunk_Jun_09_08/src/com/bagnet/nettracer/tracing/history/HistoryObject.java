package com.bagnet.nettracer.tracing.history;

import java.util.Date;

import com.bagnet.nettracer.tracing.utils.DateUtils;

public abstract class HistoryObject {
	
	protected String statusDesc;
	protected Date date;
	protected String linkURL;
	protected String objectID;
	protected String objectType;
	protected String actualId;
	
	protected HistoryObject() {
		date = DateUtils.convertToGMTDate(new Date());
	}
	
	public String getLinkURL() {
		return linkURL;
	}
	
	public void setLinkURL(String LinkURL) {
		if(getObjectType()!=null && getObjectType().equals("Baggage Delivery Order"))
		{
			this.linkURL = LinkURL+getActualID();
		}
		else
		{
			this.linkURL = LinkURL+getObjectID();
		}
	}
	
	public String getObjectID() {
		return objectID;
	}
	
	public void setObjectID(String ObjectID) {
		this.objectID = ObjectID;
	}
	
	public String getActualID() {
		return actualId;
	}
	
	public void setActualID(String actualId) {
		this.actualId = actualId;
	}

	public String getObjectType() {
		return objectType;
	}
	
	public void setObjectType(String ObjectType) {
		this.objectType = ObjectType;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getStatusDesc() {
		return statusDesc;
	}
	
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
	public abstract String getUniqueId();
	public abstract int getType();

}
