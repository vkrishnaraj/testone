/*
 * Created on Jul 30, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.dto;

import java.util.ArrayList;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import java.util.Date;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Ankur Gupta
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SalvageDTO {

	private Long salvageID;
	private Date createdDate;
	private Date closedDate;
	private String createdBy;
	private String timeZone;
	private int status;
	private int itemCount;
	
	public Long getSalvageID() {
		return salvageID;
	}

	public void setSalvageID(Long salvageID) {
		this.salvageID = salvageID;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public Date getClosedDate() {
		return createdDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}
	
	public String getDisCreatedDate(String dateFormat) {
		return DateUtils.formatDate(createdDate, dateFormat, timeZone, null);
	}

	public String getDisClosedDate(String dateFormat) {
		if (closedDate != null) {
			return DateUtils.formatDate(closedDate, dateFormat, timeZone, null);
		}
		return "N/A";
	}
	
	public String getAgent() {
		return createdBy;
	}

	public void setAgent(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	
}