/*
 * Created on Aug 24, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.dto;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BillingDTO {

	private String companyCode;
	private String reportType;
	private String count;

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *          The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the count.
	 */
	public String getCount() {
		return count;
	}

	/**
	 * @param count
	 *          The count to set.
	 */
	public void setCount(String count) {
		this.count = count;
	}

	/**
	 * @return Returns the reportType.
	 */
	public String getReportType() {
		return reportType;
	}

	/**
	 * @param reportType
	 *          The reportType to set.
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
}