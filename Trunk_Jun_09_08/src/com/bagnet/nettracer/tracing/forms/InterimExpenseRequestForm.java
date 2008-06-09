package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.constant.TracingConstants;

/**
 * @author Ankur Gupta
 * 
 * This class represents the form that is used for viewing incoming requests.
 * There is no data-entry currently, therefore, left blank intentionally.
 */
public final class InterimExpenseRequestForm extends ValidatorForm {

	private String incident_num;
	private String payout_status = "" + TracingConstants.EXPENSEPAYOUT_STATUS_PENDING;

	/**
	 * @return Returns the payout_status.
	 */
	public String getPayout_status() {
		return payout_status;
	}

	/**
	 * @param payout_status
	 *          The payout_status to set.
	 */
	public void setPayout_status(String payout_status) {
		this.payout_status = payout_status;
	}

	/**
	 * @return Returns the incident_num.
	 */
	public String getIncident_num() {
		return incident_num;
	}

	/**
	 * @param incident_num
	 *          The incident_num to set.
	 */
	public void setIncident_num(String incident_num) {
		this.incident_num = incident_num;
	}
}