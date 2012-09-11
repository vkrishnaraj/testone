package com.bagnet.nettracer.tracing.db.salvage;

import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.DateUtils;

@Entity
@Table(name = "salvage_remark")
@Proxy(lazy = false)
public class SalvageRemark {

	private int id;
	private Agent agent;
	private Date createtime;
	private String remarktext = "";
	private int remarktype;

	@Id
	@GeneratedValue
	@Column(name="remark_id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "agent_ID", nullable = false)
	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@Temporal(value = TemporalType.TIMESTAMP)
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Lob
	public String getRemarktext() {
		return remarktext;
	}

	public void setRemarktext(String remarktext) {
		this.remarktext = remarktext;
	}

	public int getRemarktype() {
		return remarktype;
	}

	public void setRemarktype(int remarktype) {
		this.remarktype = remarktype;
	}	
	
	@Transient
	public String getDispcreatetime() {
		return DateUtils.formatDate(createtime, get_DATEFORMAT() + " " + get_TIMEFORMAT(), null, get_TIMEZONE());
	}

	@Transient
	public String get_DATEFORMAT() {
		if (getAgent() != null) {
			return agent.getDateformat().getFormat();
		}
		return TracingConstants.DISPLAY_DATEFORMAT;
	}

	@Transient
	public String get_TIMEFORMAT() {
		if (getAgent() != null) {
			return agent.getTimeformat().getFormat();
		}
		return TracingConstants.DISPLAY_TIMEFORMAT;
	}

	@Transient
	public TimeZone get_TIMEZONE() {
		if (getAgent() != null) {
			return TimeZone.getTimeZone(agent.getDefaulttimezone());
		}
		return TimeZone.getTimeZone(TracingConstants.DEFAULT_AGENT_TIMEZONE);
	}

	@Transient
	public String getAgentStation() {
		if (agent != null) return agent.getStation().getStationcode();
		else return null;
	}

	@Transient
	public String getAgentUsername() {
		if (agent != null) return agent.getUsername();
		else return null;
	}


}
