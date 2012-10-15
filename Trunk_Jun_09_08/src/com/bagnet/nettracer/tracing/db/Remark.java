/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.ListIterator;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.NumberUtils;
import com.cci.utils.parser.ElementNode;


/**
 * @author Administrator
 * 
 * @hibernate.class table="Remark" dynamic-update="true"
 */
public class Remark implements Serializable {
	private int Remark_ID;
	private Agent agent;
	private String createtime = "";
	private String remarktext = "";
	private int remarktype;
	
	private Incident incident;
	private OHD ohd;
	private Claim claim;
	//Add Claim field getters and setters

	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private TimeZone _TIMEZONE;

	public String toXML() {
		StringBuffer sb = new StringBuffer();
		sb.append("<remark>");
		sb.append("<Remark_ID>" + Remark_ID + "</Remark_ID>");
		sb.append("<remarktext>" + remarktext + "</remarktext>");
		sb.append("<createtime>" + createtime + "</createtime>");
		sb.append("<remarktype>" + remarktype + "</remarktype>");
		sb.append("<agent_ID>" + agent.getAgent_ID() + "</agent_ID>");
		sb.append("</remark>");
		return sb.toString();
	}
	
	public static Remark XMLtoObject(ElementNode root) {
		Remark obj = new Remark();

		ElementNode child = null, grandchild = null, ggrandchild = null, gggrandchild = null;

		for (ListIterator i = root.get_children().listIterator(); i.hasNext();) {
			child = (ElementNode) i.next();
			if (child.getType().equals("Remark_ID")) {
				obj.setRemark_ID(NumberUtils.parseInt(child.getTextContents()));
			} else if (child.getType().equals("remarktext")) {
				obj.setRemarktext(child.getTextContents());
			} else if (child.getType().equals("remarktype")) {
				obj.setRemarktype(NumberUtils.parseInt(child.getTextContents()));	
			} else if (child.getType().equals("createtime")) {
				obj.setCreatetime(child.getTextContents());
			} else if (child.getType().equals("agent_ID")) {
				Agent agent = new Agent();
				agent.setAgent_ID(NumberUtils.parseInt(child.getTextContents()));
				obj.setAgent(agent);	
			}
	
		}

		return obj;
	}
	
	/**
	 * @return Returns the agent.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="agent_ID"
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
	 * @hibernate.id generator-class="native" type="integer" column="Remark_ID"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="Remark_0"
	 *  
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
	 * @return Returns the incident.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Incident"
	 *                        column="incident_ID" not-null="false" fetch="select"
	 */
	public Incident getIncident() {
		return incident;
	}
	/**
	 * @param incident The incident to set.
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	/**
	 * @return Returns the ohd.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.OHD"
	 *                        column="OHD_ID" not-null="false" fetch="select"
	 */
	public OHD getOhd() {
		return ohd;
	}
	/**
	 * @param ohd The ohd to set.
	 */
	public void setOhd(OHD ohd) {
		this.ohd = ohd;
	}
	
	/**
	 * @return Returns the remarktext.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getRemarktext() {
		return remarktext;
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
	 * @param remarktext
	 *          The remarktext to set.
	 */
	public void setRemarktext(String remarktext) {
		this.remarktext = remarktext;
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
}