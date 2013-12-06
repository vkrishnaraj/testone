package com.bagnet.nettracer.tracing.db.communications;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bagnet.nettracer.tracing.db.Agent;

@Entity
@Table(name = "incident_activity_remark")
public class IncidentActivityRemark {
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "agent", nullable = false)
	private Agent agent;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createDate;

	@ManyToOne
	@JoinColumn(name = "incidentActivity", nullable = false)
	private IncidentActivity incidentActivity;
	
	@Column(name = "remarkText", nullable = false, length = 255)
	private String remarkText;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public IncidentActivity getIncidentActivity() {
		return incidentActivity;
	}

	public void setIncidentActivity(IncidentActivity incidentActivity) {
		this.incidentActivity = incidentActivity;
	}

	public String getRemarkText() {
		return remarkText;
	}

	public void setRemarkText(String remarkText) {
		this.remarkText = remarkText;
	}
	
}
