package com.bagnet.nettracer.tracing.db.onlineclaims;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;

@Entity
@Table(name = "oc_file")
@Proxy(lazy = false)
public class OCFile {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim.class)
	@JoinColumn(name = "claimId", nullable = false)
	private OnlineClaim claim;

	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.communications.IncidentActivity.class)
	@JoinColumn(name = "incActId", nullable = true)
	private IncidentActivity incAct;

	@Column(length = 100)
	private String filename;

	@Column(length = 1000)
	private String path;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUploaded;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateViewed;
	
	@Basic
	private boolean interim;
	
	private boolean publish;
	private int statusId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getDateUploaded() {
		return dateUploaded;
	}

	public void setDateUploaded(Date dateUploaded) {
		this.dateUploaded = dateUploaded;
	}

	public Date getDateViewed() {
		return dateViewed;
	}

	public void setDateViewed(Date dateViewed) {
		this.dateViewed = dateViewed;
	}

	public OnlineClaim getClaim() {
		return claim;
	}

	public void setClaim(OnlineClaim claim) {
		this.claim = claim;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isInterim() {
		return interim;
	}

	public void setInterim(boolean interim) {
		this.interim = interim;
	}
	
	public IncidentActivity getIncAct(){
		return incAct;
	}
	
	public void setIncAct(IncidentActivity incAct){
		this.incAct=incAct;
	}

	public boolean isPublish() {
		return publish;
	}

	public void setPublish(boolean publish) {
		this.publish = publish;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
}
