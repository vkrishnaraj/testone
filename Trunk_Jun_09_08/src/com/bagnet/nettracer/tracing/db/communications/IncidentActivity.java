package com.bagnet.nettracer.tracing.db.communications;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.documents.Document;

@Entity
@Table(name = "incident_activity")
public class IncidentActivity {

	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "incident", nullable = false)
	@Fetch(FetchMode.SELECT)
	private Incident incident;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createDate;

	@Temporal(value = TemporalType.TIMESTAMP)
	private Date publishedDate;
	
	@ManyToOne
	@JoinColumn(name = "agent")
	private Agent agent;
	
	@ManyToOne
	@JoinColumn(name = "approvalAgent")
	private Agent approvalAgent;
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.documents.Document.class)
	@JoinColumn(name = "document")
	@Fetch(FetchMode.SELECT)
	private Document document;
	
	@ManyToOne
	@JoinColumn(name = "status")
	private Status status;
	
	@ManyToOne
	@JoinColumn(name = "activity")
	private Activity activity;
	
	@OneToMany(mappedBy = "incidentActivity", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause="createDate")
	@Fetch(FetchMode.SELECT)
	private List<IncidentActivityRemark> remarks;
	
	private String description;
	
	private long custCommId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Incident getIncident() {
		return incident;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Agent getApprovalAgent() {
		return approvalAgent;
	}

	public void setApprovalAgent(Agent approvalAgent) {
		this.approvalAgent = approvalAgent;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getCustCommId() {
		return custCommId;
	}

	public void setCustCommId(long custCommId) {
		this.custCommId = custCommId;
	}

	public List<IncidentActivityRemark> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<IncidentActivityRemark> remarks) {
		this.remarks = remarks;
	}
	
}
