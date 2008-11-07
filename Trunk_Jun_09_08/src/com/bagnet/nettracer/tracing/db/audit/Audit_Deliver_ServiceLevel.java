package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.DeliverCompany;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="audit_deliver_servicelevel"
 */
public class Audit_Deliver_ServiceLevel implements Serializable {
	
	private int audit_servicelevel_id;
	
	private int servicelevel_ID;
	private String description;
	private DeliverCompany delivercompany;
	private boolean active;

	private Agent modifying_agent;
	private Date time_modified;
	private String reason_modified;
	private String service_code;
	
	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE; // timezone
	
		 
	/**
	 * @return Returns the audit_servicelevel_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer" column="audit_servicelevel_id"
	 * @hibernate.generator-param name="sequence" value="audit_servicelevel_0"
	 * @return Returns the audit_servicelevel_ID.
	 */
	public int getAudit_servicelevel_id() {
		return audit_servicelevel_id;
	}

	/**
	 * @param audit_servicelevel_ID
	 *          The audit_servicelevel_ID to set.
	 */
	public void setAudit_servicelevel_id(int audit_servicelevel_id) {
		this.audit_servicelevel_id = audit_servicelevel_id;
	}
	
	
	/**
	 * @return Returns the description.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *          The description to set.
	 */
	public void setDescription(String desc) {
		this.description = desc;
	}

	
	
	/**
	 * @hibernate.property type="integer"
	 * 
	 * @return Returns the servicelevel_ID.
	 */
	public int getServicelevel_ID() {
		return servicelevel_ID;
	}

	/**
	 * @param servicelevel_ID
	 *          The servicelevel_ID to set.
	 */
	public void setServicelevel_ID(int servicelevel_ID) {
		this.servicelevel_ID = servicelevel_ID;
	}
	
	
	/**
	 * @return Returns the delivercompany.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.DeliverCompany"
	 *                        column="delivercompany_ID"
	 */
	public DeliverCompany getDelivercompany() {
		return delivercompany;
	}
	/**
	 * @param delivercompany The delivercompany to set.
	 */
	public void setDelivercompany(DeliverCompany delivercompany) {
		this.delivercompany = delivercompany;
	}
	
	/**
	 * @return Returns the active.
	 * 
	 * @hibernate.property type="boolean"
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active
	 *          The active to set.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * @return Returns the _DATEFORMAT.
	 */
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	/**
	 * @param _dateformat
	 *          The _DATEFORMAT to set.
	 */
	public void set_DATEFORMAT(String _dateformat) {
		_DATEFORMAT = _dateformat;
	}

	/**
	 * @return Returns the _TIMEFORMAT.
	 */
	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}

	/**
	 * @param _timeformat
	 *          The _TIMEFORMAT to set.
	 */
	public void set_TIMEFORMAT(String _timeformat) {
		_TIMEFORMAT = _timeformat;
	}

	/**
	 * @return Returns the _TIMEZONE.
	 */
	public TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}

	/**
	 * @param _timezone
	 *          The _TIMEZONE to set.
	 */
	public void set_TIMEZONE(TimeZone _timezone) {
		_TIMEZONE = _timezone;
	}

	public String getDisplaytime_modified() {
		Date completedate = DateUtils.convertToDate(this.getTime_modified().toString(),
				TracingConstants.DB_DATETIMEFORMAT, null);
		return DateUtils.formatDate(completedate, _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="modifying_agent_ID"
	 * @return Returns the modifying_agent.
	 */
	public Agent getModifying_agent() {
		return modifying_agent;
	}
	
	/**
	 * @param modifying_agent
	 *          The modifying_agent to set.
	 */
	public void setModifying_agent(Agent modifying_agent) {
		this.modifying_agent = modifying_agent;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the reason_modified.
	 */
	public String getReason_modified() {
		return reason_modified;
	}

	/**
	 * @param reason_modified
	 *          The reason_modified to set.
	 */
	public void setReason_modified(String reason_modified) {
		this.reason_modified = reason_modified;
	}

	/**
	 * @hibernate.property type="timestamp"
	 * @return Returns the time_modified.
	 */
	public Date getTime_modified() {
		return time_modified;
	}

	/**
	 * @param time_modified
	 *          The time_modified to set.
	 */
	public void setTime_modified(Date time_modified) {
		this.time_modified = time_modified;
	}

	/**
	 * @return the service_code
	 */
	public String getService_code() {
		return service_code;
	}

	/**
	 * @param service_code the service_code to set
	 */
	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	
}