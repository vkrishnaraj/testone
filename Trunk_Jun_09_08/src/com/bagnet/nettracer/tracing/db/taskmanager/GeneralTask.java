package com.bagnet.nettracer.tracing.db.taskmanager;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import org.apache.struts.util.MessageResources;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bagnet.nettracer.tracing.db.Lock;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.DateUtils;

@Entity
@Table(name="task")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="task_type", discriminatorType = DiscriminatorType.STRING, length=16)
public abstract class GeneralTask {
	protected static MessageResources messages = null;
	private String locale;
	static {
		try {
			messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		} catch (Exception e) {
			// Ignore
		}
	}
	
	@Id
	@GeneratedValue
	public long getTask_id() {
		return task_id;
	}
	public void setTask_id(long taskId) {
		task_id = taskId;
	}
	public Date getOpened_timestamp() {
		return opened_timestamp;
	}
	public void setOpened_timestamp(Date openedTimestamp) {
		opened_timestamp = openedTimestamp;
	}
	public Date getClosed_timestamp() {
		return closed_timestamp;
	}
	public void setClosed_timestamp(Date closedTimestamp) {
		closed_timestamp = closedTimestamp;
	}
	@ManyToOne
	@JoinColumn(name = "status_ID", nullable = false)
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@ManyToOne
	@JoinColumn(name = "agent_ID")
	public Agent getAssigned_agent() {
		return assigned_agent;
	}
	public void setAssigned_agent(Agent assignedAgent) {
		assigned_agent = assignedAgent;
	}
	
	public Date getGeneric_timestamp() {
		return generic_timestamp;
	}

	public void setGeneric_timestamp(Date generic_timestamp) {
		this.generic_timestamp = generic_timestamp;
	}
	
	long task_id;
	Date opened_timestamp;
	Date closed_timestamp;
	Status status;
	Agent assigned_agent;
	Date generic_timestamp;
	List<TaskActivity> activities;
	Lock lock;
	
	@Transient
	public Date getDeferment_timestamp() {
		return generic_timestamp;
	}

	public void setDeferment_timestamp(Date deferment_timestamp) {
		this.generic_timestamp = deferment_timestamp;
	}
	
	@Transient
	public Date getExpire_timestamp() {
		return generic_timestamp;
	}

	public void setExpire_timestamp(Date expire_timestamp) {
		this.generic_timestamp = expire_timestamp;
	}
	
	@Transient
	public Lock getLock() {
		return lock;
	}
	public void setLock(Lock lock) {
		this.lock = lock;
	}
	
	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
//	@org.hibernate.annotations.IndexColumn(name = "bagnumber")
	@Fetch(FetchMode.SELECT)
	public List<TaskActivity> getActivities() {
		return activities;
	}
	public void setActivities(List<TaskActivity> activities) {
		this.activities = activities;
	}
	
	@Transient
	public String getDescription() {
		return null;
	}
	
	@Transient
	public String getLabel() {
		return null;
	}
	
	@Transient
	public String getKey() {
		return null;
	}
	
	@Transient
	public String getAlert() {
		return "Example";
	}
	
	/**
	 * ************************************************
	 * The following are for date/time display purposes
	 * ************************************************
	 */
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private java.util.TimeZone _TIMEZONE;
	
	@Transient
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	@Transient
	public String getDispOpened_timestamp(){
		String s = DateUtils.formatDate(getOpened_timestamp(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
		return s!=null?s:"";
	}
	@Transient
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}
	public void set_DATEFORMAT(String _DATEFORMAT) {
		this._DATEFORMAT = _DATEFORMAT;
	}
	@Transient
	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}
	public void set_TIMEFORMAT(String _TIMEFORMAT) {
		this._TIMEFORMAT = _TIMEFORMAT;
	}
	@Transient
	public java.util.TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}
	public void set_TIMEZONE(java.util.TimeZone _TIMEZONE) {
		this._TIMEZONE = _TIMEZONE;
	}
	
}
