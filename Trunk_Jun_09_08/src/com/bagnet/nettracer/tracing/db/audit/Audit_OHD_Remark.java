/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.audit.AuditOHDUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Audit_Remark"
 */
public class Audit_OHD_Remark implements Serializable {

	private int id;

	private int Remark_ID;
	private Agent agent;
	private String createtime;
	private String remarktext = "";
	private int remarktype;

	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private TimeZone _TIMEZONE;
	
	private Audit_OHD audit_ohd;
	
	
	/**
	 * @return Returns the audit_incident.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.audit.Audit_OHD"
	 *                        column="audit_ohd_id"
	 */
	public Audit_OHD getAudit_ohd() {
		return audit_ohd;
	}
	/**
	 * @param audit_ohd The audit_ohd to set.
	 */
	public void setAudit_ohd(Audit_OHD audit_ohd) {
		this.audit_ohd = audit_ohd;
	}
		
	
	/**
	 * @return Returns the agent.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="agent_ID"
	 *  
	 */
	public Agent getAgent() {
		return agent;
	}

	public String getAgentStation() {
		if (agent != null) return agent.getStation().getStationcode();
		else return null;
	}

	public String getAgentUsername() {
		if (agent != null) return agent.getUsername();
		else return null;
	}


	/**
	 * @param agent
	 *          The agent to set.
	 */
	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	/**
	 * @return Returns the createtime.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getCreatetime() {
		return createtime;
	}

	/**
	 * 
	 * @return the createtime for display only
	 */
	public String getDispcreatetime() {
		return DateUtils.formatDate(createtime, TracingConstants.DB_DATETIMEFORMAT, get_DATEFORMAT()
				+ " " + get_TIMEFORMAT(), null, get_TIMEZONE());
	}

	/**
	 * @param createtime
	 *          The createtime to set.
	 */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	/**
	 * @return Returns the remark_ID.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getRemark_ID() {
		return Remark_ID;
	}

	/**
	 * @param remark_ID
	 *          The remark_ID to set.
	 */
	public void setRemark_ID(int remark_ID) {
		Remark_ID = remark_ID;
	}

	/**
	 * @return Returns the remarktext.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getRemarktext() {
		return remarktext;
	}

	/**
	 * @hibernate.id generator-class="native" type="integer" column="id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="audit_remark_0"
	 * 
	 * 
	 * 
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *          The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param remarktext
	 *          The remarktext to set.
	 */
	public void setRemarktext(String remarktext) {
		this.remarktext = remarktext;
	}

	public String getReadonlyremarktext() {
		if(remarktext != null)
		{
			return remarktext.replaceAll("\r\n", "<br>");
		}
		else
		{
			return "";
		}
	}
	
	/**
	 * @return Returns the remarktype.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getRemarktype() {
		return remarktype;
	}

	/**
	 * @param remarktype
	 *          The remarktype to set.
	 */
	public void setRemarktype(int remarktype) {
		this.remarktype = remarktype;
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

	public boolean equals(Object obj) {
		Audit_OHD_Remark aam = (Audit_OHD_Remark) obj;
		boolean ret = true;

		if (AuditOHDUtils.notEqualObjects(aam.getRemarktext(), this.getRemarktext())) {
			ret = false;
		}
		return ret;
	}
}