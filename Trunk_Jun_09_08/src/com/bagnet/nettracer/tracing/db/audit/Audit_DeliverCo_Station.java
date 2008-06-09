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
 * @hibernate.class table="audit_deliverco_station"
 */
public class Audit_DeliverCo_Station implements Serializable {
	
	private int audit_deliverco_station_ID;
	
	private int deliverco_station_ID;
	private int station_ID;
	private DeliverCompany delivercompany;
	
	private Agent modifying_agent;
	private Date time_modified;
	private String reason_modified;
	
	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE; // timezone


	/**
	 * @return Returns the deliverco_station_ID.
	 * 
	 * @hibernate.id generator-class = "native" type="integer"
	 * @hibernate.generator-param name="sequence" value="audit_deliverco_station_0"
	 */
	public int getAudit_deliverco_station_ID() {
		return audit_deliverco_station_ID;
	}

	/**
	 * @param audit_deliverco_station_ID
	 *          The audit_deliverco_station_ID to set.
	 */
	public void setAudit_deliverco_station_ID(int audit_deliverco_station_ID) {
		this.audit_deliverco_station_ID = audit_deliverco_station_ID;
	}
	
	

	
	
	/**
	 * @hibernate.property type="integer"
	 * 
	 * @return Returns the deliverco_station_ID.
	 */
	public int getDeliverco_station_ID() {
		return deliverco_station_ID;
	}

	/**
	 * @param deliverco_station_ID
	 *          The deliverco_station_ID to set.
	 */
	public void setDeliverco_station_ID(int deliverco_station_ID) {
		this.deliverco_station_ID = deliverco_station_ID;
	}

	/**
	 * @return Returns the station.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getStation_ID() {
		return station_ID;
	}
	/**
	 * @param station_ID The station_ID to set.
	 */
	public void setStation_ID(int station_ID) {
		this.station_ID = station_ID;
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
	 * @param delivercompany
	 *          The delivercompany to set.
	 */
	public void setDelivercompany(DeliverCompany delivercompany) {
		this.delivercompany = delivercompany;
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
}