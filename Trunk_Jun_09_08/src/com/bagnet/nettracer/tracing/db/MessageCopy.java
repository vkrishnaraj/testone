/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="message_copies"
 */
public class MessageCopy implements Serializable {
	private int message_copy_id;
	private Message parent_message;
	private Station receiving_station;
	private String body;
	private String subject;
	private Status status;
	private Agent agent;

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Status"
	 *                        column="status_id"
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

	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private TimeZone _TIMEZONE;

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

	/**
	 * @hibernate.property type="string"
	 * @return Returns the body.
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 *          The body to set.
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @hibernate.id generator-class="native" type="integer"
	 *               column="message_copy_id" unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="Message_Copies_0"
	 * 
	 * @return Returns the message_copy_id.
	 */
	public int getMessage_copy_id() {
		return message_copy_id;
	}

	/**
	 * @param message_copy_id
	 *          The message_copy_id to set.
	 */
	public void setMessage_copy_id(int message_copy_id) {
		this.message_copy_id = message_copy_id;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Message"
	 *                        column="message_id"
	 * @return Returns the parent_message.
	 */
	public Message getParent_message() {
		return parent_message;
	}

	/**
	 * @param parent_message
	 *          The parent_message to set.
	 */
	public void setParent_message(Message parent_message) {
		this.parent_message = parent_message;
	}

	/**
	 * *
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Station"
	 *                        column="station_id"
	 * @return Returns the receiving_station.
	 */
	public Station getReceiving_station() {
		return receiving_station;
	}

	/**
	 * @param receiving_station
	 *          The receiving_station to set.
	 */
	public void setReceiving_station(Station receiving_station) {
		this.receiving_station = receiving_station;
	}

	/**
	 * *
	 * 
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
	 * 
	 * @return the createtime for display only
	 */
	public String getDisp_send_date() {
		if (parent_message != null) return DateUtils.formatDate(parent_message.getSend_date(),
				_DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);

		return null;
	}
}