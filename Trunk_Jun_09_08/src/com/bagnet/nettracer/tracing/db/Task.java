/*
 * Created on Aug 28, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Ankur Gupta
 * 
 * @hibernate.class table="tasks"
 *  
 */
public class Task implements Serializable {

	private int task_id = 0;
	private String file_ref_number;
	private String description;
	private Date due_date_time;
	private Date reminder_date_time;
	private Priority priority;
	private Agent assigningAgent;
	private Station station;
	private int file_type;
	private Status status;
	private String remarks;
	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;

	private Agent assignedTo;
	
	public static final int ALL_TASKS = -1;
	public static final int ACTIVE_TASKS = -2;


	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="assigned_agent_ID"
	 * @return Returns the assignedTo.
	 */
	public Agent getAssignedTo() {
		return assignedTo;
	}

	/**
	 * @param assignedTo
	 *          The assignedTo to set.
	 */
	public void setAssignedTo(Agent assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getCreatedBy() {
		if (assigningAgent != null) return assigningAgent.getUsername();
		else return "";
	}

	public String getPriorityString() {
		if (priority != null) return priority.getDescription();
		else return "";
	}

	public String getStationString() {
		if (station != null) return station.getStationcode();
		else return "";
	}

	/**
	 * @param _dateformat
	 *          The _DATEFORMAT to set.
	 */
	public void set_DATEFORMAT(String _dateformat) {
		_DATEFORMAT = _dateformat;
	}

	/**
	 * @param _timeformat
	 *          The _TIMEFORMAT to set.
	 */
	public void set_TIMEFORMAT(String _timeformat) {
		_TIMEFORMAT = _timeformat;
	}

	/**
	 * @param _timezone
	 *          The _TIMEZONE to set.
	 */
	public void set_TIMEZONE(TimeZone _timezone) {
		_TIMEZONE = _timezone;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *          The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Status"
	 *                        column="task_status_id" *
	 * @return Returns the status.
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *          The status to set.
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="assigningAgent"
	 * @return Returns the assigningAgent.
	 */
	public Agent getAssigningAgent() {
		return assigningAgent;
	}

	/**
	 * @param assigningAgent
	 *          The assigningAgent to set.
	 */
	public void setAssigningAgent(Agent assigningAgent) {
		this.assigningAgent = assigningAgent;
	}

	/**
	 * *
	 * 
	 * @hibernate.property type="string"
	 * 
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *          The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @hibernate.property type="string"
	 * 
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
	 * @hibernate.id generator-class="native" type="integer" column="task_id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="tasks_0"
	 * 
	 * @return Returns the task_id.
	 */
	public int getTask_id() {
		return task_id;
	}

	/**
	 * @param task_id
	 *          The task_id to set.
	 */
	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Priority"
	 *                        column="priority_id"
	 * @return Returns the priority.
	 */
	public Priority getPriority() {
		return priority;
	}

	/**
	 * @param priority_ID
	 *          The priority_ID to set.
	 */
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the file_type.
	 */
	public int getFile_type() {
		return file_type;
	}

	/**
	 * @param file_type
	 *          The file_type to set.
	 */
	public void setFile_type(int file_type) {
		this.file_type = file_type;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Station"
	 *                        column="station_id"
	 * @return Returns the station_id.
	 */
	public Station getStation() {
		return station;
	}

	/**
	 * @param station_id
	 *          The station_id to set.
	 */
	public void setStation(Station station) {
		this.station = station;
	}

	/**
	 * @hibernate.property type="timestamp"
	 * @return Returns the due_date_time.
	 */
	public Date getDue_date_time() {
		return due_date_time;
	}

	/**
	 * @param due_date_time
	 *          The due_date_time to set.
	 */
	public void setDue_date_time(Date due_date_time) {
		this.due_date_time = due_date_time;
	}

	/**
	 * @hibernate.property type="timestamp"
	 * @return Returns the reminder_date_time.
	 */
	public Date getReminder_date_time() {
		return reminder_date_time;
	}

	/**
	 * @param reminder_date_time
	 *          The reminder_date_time to set.
	 */
	public void setReminder_date_time(Date reminder_date_time) {
		this.reminder_date_time = reminder_date_time;
	}

	public String getDispreminderdate() {
		return DateUtils.formatDate(this.getReminder_date_time(), _DATEFORMAT, null, _TIMEZONE);
	}

	public String getDispremindertime() {
		return DateUtils.formatDate(this.getReminder_date_time(), _TIMEFORMAT, null, _TIMEZONE);
	}

	public String getDispduedate() {
		return DateUtils.formatDate(this.getDue_date_time(), _DATEFORMAT, null, _TIMEZONE);
	}

	public String getDispduetime() {
		return DateUtils.formatDate(this.getDue_date_time(), _TIMEFORMAT, null, _TIMEZONE);
	}
}