package com.bagnet.nettracer.tracing.db.communications;

import java.util.ArrayList;
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
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.documents.Document;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCFile;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCMessage;
import com.bagnet.nettracer.tracing.db.taskmanager.IncidentActivityTask;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expensepayout_id")
	@Fetch(FetchMode.SELECT)
	private ExpensePayout expensePayout;

	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastPrinted;
	
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
	@JoinColumn(name = "activity")
	private Activity activity;
	
	@OneToMany(mappedBy = "incAct", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause="dateCreated")
	@Fetch(FetchMode.SELECT)
	private List<OCMessage> messages;
	
	@OneToMany(mappedBy = "incAct", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause="id")
	@Fetch(FetchMode.SELECT)
	private List<OCFile> files;
	
	@OneToMany(mappedBy = "incidentActivity", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause="createDate")
	@Fetch(FetchMode.SELECT)
	private List<IncidentActivityRemark> remarks;
	
	@OneToMany(mappedBy = "incidentActivity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause="opened_timestamp")
	@Fetch(FetchMode.SELECT)
	private List<IncidentActivityTask> tasks;
	
	private String description;
	
	private int custCommId;

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
	
	public ExpensePayout getExpensePayout() {
		return expensePayout;
	}

	public void setExpensePayout(ExpensePayout expensePayout) {
		this.expensePayout = expensePayout;
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

	public int getCustCommId() {
		return custCommId;
	}

	public void setCustCommId(int custCommId) {
		this.custCommId = custCommId;
	}

	public List<IncidentActivityRemark> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<IncidentActivityRemark> remarks) {
		this.remarks = remarks;
	}

	public List<IncidentActivityTask> getTasks() {
		if (tasks == null) {
			tasks = new ArrayList<IncidentActivityTask>();
		}
		return tasks;
	}

	public void setTasks(List<IncidentActivityTask> tasks) {
		this.tasks = tasks;
	}

	public List<OCMessage> getMessages() {
		if (messages == null) {
			messages = new ArrayList<OCMessage>();
		}
		return messages;
	}

	public void setMessages(List<OCMessage> messages) {
		this.messages = messages;
	}

	public List<OCFile> getFiles() {
		if (files == null) {
			files = new ArrayList<OCFile>();
		}
		return files;
	}

	public void setFiles(List<OCFile> files) {
		this.files = files;
	}
	
	public Status getLastStatus() {
		if (tasks == null || tasks.isEmpty()) return null;
		return tasks.get(tasks.size() - 1).getStatus();
	}
	
	public Date getLastPrinted() {
		return lastPrinted;
	}

	public void setLastPrinted(Date lastPrinted) {
		this.lastPrinted = lastPrinted;
	}
	
}
