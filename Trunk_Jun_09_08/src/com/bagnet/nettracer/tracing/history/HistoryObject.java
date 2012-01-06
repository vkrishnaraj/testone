package com.bagnet.nettracer.tracing.history;

import java.util.Date;

import com.bagnet.nettracer.tracing.utils.DateUtils;

public abstract class HistoryObject {
	
	protected String statusDesc;
	protected Date date;
	
	protected HistoryObject() {
		date = DateUtils.convertToGMTDate(new Date());
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
