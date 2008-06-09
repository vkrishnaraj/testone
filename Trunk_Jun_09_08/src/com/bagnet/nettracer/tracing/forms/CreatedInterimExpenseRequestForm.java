package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Ankur Gupta
 * 
 * This class represents the form that is used for viewing incoming requests.
 * There is no data-entry currently, therefore, left blank intentionally.
 */
public final class CreatedInterimExpenseRequestForm extends ValidatorForm {

	private String incident_num;

	private String expense_status = "-1";

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

	/**
	 * @return Returns the expense_status.
	 */
	public String getExpense_status() {
		return expense_status;
	}

	/**
	 * @param expense_status
	 *          The expense_status to set.
	 */
	public void setExpense_status(String expense_status) {
		this.expense_status = expense_status;
	}
}