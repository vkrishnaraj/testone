package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Ankur Gupta
 * 
 * This class represents the form that is used for viewing tasks
 */
public final class TaskForm extends ValidatorForm {

	private String task_status; //status
	private String s_time; //start time
	private String e_time; //end time
	private String file_ref_number; //report reference number if any.
	private String assigned_to_id;

	public String getAssigned_to_id() {
		return assigned_to_id;
	}

	public void setAssigned_to_id(String x) {
		this.assigned_to_id = x;
	}

	/**
	 * @return Returns the e_time.
	 */
	public String getE_time() {
		return e_time;
	}

	/**
	 * @param e_time
	 *          The e_time to set.
	 */
	public void setE_time(String e_time) {
		this.e_time = e_time;
	}

	/**
	 * @return Returns the file_ref_number.
	 */
	public String getFile_ref_number() {
		return file_ref_number;
	}

	/**
	 * @param file_ref_number
	 *          The file_ref_number to set.
	 */
	public void setFile_ref_number(String file_ref_number) {
		this.file_ref_number = file_ref_number;
	}

	/**
	 * @return Returns the s_time.
	 */
	public String getS_time() {
		return s_time;
	}

	/**
	 * @param s_time
	 *          The s_time to set.
	 */
	public void setS_time(String s_time) {
		this.s_time = s_time;
	}

	/**
	 * @return Returns the task_status.
	 */
	public String getTask_status() {
		return task_status;
	}

	/**
	 * @param task_status
	 *          The task_status to set.
	 */
	public void setTask_status(String task_status) {
		this.task_status = task_status;
	}
}