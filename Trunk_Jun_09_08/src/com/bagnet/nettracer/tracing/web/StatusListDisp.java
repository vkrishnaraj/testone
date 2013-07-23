/*
 * Created on July 19, 2013
 *
 * 
 */
package com.bagnet.nettracer.tracing.web;

import java.util.Date;

import com.bagnet.nettracer.tracing.utils.DateUtils;

public class StatusListDisp {

	private String delStatusDesc2;
	private String delStatusDesc;
	private String delStatus;
	private Date createDate;
	

	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	
	public String getDelStatusDesc2() {
		return delStatusDesc2;
	}
	public void setDelStatusDesc2(String delStatusDesc2) {
		this.delStatusDesc2 = delStatusDesc2;
	}
	public String getDelStatusDesc() {
		return delStatusDesc;
	}
	public void setDelStatusDesc(String delStatusDesc) {
		this.delStatusDesc = delStatusDesc;
	}

	public String getCreateDateDisp() {
		String createDateDisp = "";
		if (createDate != null) {
			createDateDisp = DateUtils.formatDate(getCreateDate(),  _DATEFORMAT + " " + _TIMEFORMAT, null,	null);
		}
		return createDateDisp;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}
	public void set_DATEFORMAT(String _DATEFORMAT) {
		this._DATEFORMAT = _DATEFORMAT;
	}
	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}
	public void set_TIMEFORMAT(String _TIMEFORMAT) {
		this._TIMEFORMAT = _TIMEFORMAT;
	}
}