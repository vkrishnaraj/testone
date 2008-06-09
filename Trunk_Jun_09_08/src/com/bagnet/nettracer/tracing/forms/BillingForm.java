package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Ankur Gupta
 * 
 * This class represents the form that is used for viewing statistical reports.
 */
public final class BillingForm extends ValidatorForm {

	private int itemType_ID = 0;
	private String starttime;
	private String endtime;
	private int outputtype = 0;
	private String companycode_ID;
	private String tran_value = "1.0";

	/**
	 * @return Returns the tran_value.
	 */
	public String getTran_value() {
		return tran_value;
	}

	/**
	 * @param tran_value
	 *          The tran_value to set.
	 */
	public void setTran_value(String tran_value) {
		this.tran_value = tran_value;
	}

	/**
	 * @return Returns the companycode_ID.
	 */
	public String getCompanycode_ID() {
		return companycode_ID;
	}

	/**
	 * @param companycode_ID
	 *          The companycode_ID to set.
	 */
	public void setCompanycode_ID(String companycode_ID) {
		this.companycode_ID = companycode_ID;
	}

	/**
	 * @return Returns the endtime.
	 */
	public String getEndtime() {
		return endtime;
	}

	/**
	 * @param endtime
	 *          The endtime to set.
	 */
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	/**
	 * @return Returns the itemType_ID.
	 */
	public int getItemType_ID() {
		return itemType_ID;
	}

	/**
	 * @param itemType_ID
	 *          The itemType_ID to set.
	 */
	public void setItemType_ID(int itemType_ID) {
		this.itemType_ID = itemType_ID;
	}

	/**
	 * @return Returns the outputtype.
	 */
	public int getOutputtype() {
		return outputtype;
	}

	/**
	 * @param outputtype
	 *          The outputtype to set.
	 */
	public void setOutputtype(int outputtype) {
		this.outputtype = outputtype;
	}

	/**
	 * @return Returns the starttime.
	 */
	public String getStarttime() {
		return starttime;
	}

	/**
	 * @param starttime
	 *          The starttime to set.
	 */
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
}