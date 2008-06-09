/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="message"
 */
public class Message implements Serializable {
	private int message_id;
	private Station send_station;
	private Set recipients;
	private String message;
	private Date send_date;
	private String subject;
	private String file_ref_number;
	private int file_type;
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private TimeZone _TIMEZONE;
	private Agent agent;

	public String getCreatedBy() {
		if (agent != null) return agent.getUsername();
		else return "";
	}

	public String getStationString() {
		if (send_station != null) return send_station.getStationcode();
		else return "";
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="agent_ID"
	 * @return Returns the agent.
	 */
	public Agent getAgent() {
		return agent;
	}

	/**
	 * @param agent
	 *          The agent to set.
	 */
	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	/**
	 * @hibernate.property type="string"
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
	 * 
	 * @return the createtime for display only
	 */
	public String getDisp_send_date() {
		return DateUtils.formatDate(this.getSend_date(), _DATEFORMAT + " " + _TIMEFORMAT, null,
				_TIMEZONE);
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *          The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @hibernate.id generator-class="native" type="integer" column="message_id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="Message_0"
	 * 
	 * @return Returns the message_id.
	 */
	public int getMessage_id() {
		return message_id;
	}

	/**
	 * @param message_id
	 *          The message_id to set.
	 */
	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	/**
	 * @hibernate.set cascade="all" order-by="recipient_id"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Recipient"
	 * @hibernate.key column="message_id"
	 * @return Returns the recipients.
	 */
	public Set getRecipients() {
		return recipients;
	}

	public String getMessageTo() {

		StringBuffer toRet = new StringBuffer("");

		if (recipients != null && recipients.size() > 0) {
			for (Iterator i = recipients.iterator(); i.hasNext();) {
				Recipient rcpt = (Recipient) i.next();

				if (toRet.length() > 0) toRet.append(", ");
				toRet.append(rcpt.getStation().getCompany().getCompanyCode_ID() + " "
						+ rcpt.getStation().getStationcode());
			}
		}

		return toRet.toString();
	}

	/**
	 * @param recipients
	 *          The recipients to set.
	 */
	public void setRecipients(Set recipients) {
		this.recipients = recipients;
	}

	/**
	 * @hibernate.property type="timestamp"
	 * @return Returns the send_date.
	 */
	public Date getSend_date() {
		return send_date;
	}

	/**
	 * @param send_date
	 *          The send_date to set.
	 */
	public void setSend_date(Date send_date) {
		this.send_date = send_date;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Station"
	 *                        column="station_id"
	 * @return Returns the send_station.
	 */
	public Station getSend_station() {
		return send_station;
	}

	/**
	 * @param send_station
	 *          The send_station to set.
	 */
	public void setSend_station(Station send_station) {
		this.send_station = send_station;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *          The subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
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

}