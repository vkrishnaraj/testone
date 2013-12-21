package com.bagnet.nettracer.tracing.db.onlineclaims;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;

@Entity
@Table(name = "oc_message")
@Proxy(lazy = false)
public class OCMessage {
	@Id
	@GeneratedValue
	long id;

	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim.class)
	@JoinColumn(name = "claimId", nullable = false)
	private OnlineClaim claim;

	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.communications.IncidentActivity.class)
	@JoinColumn(name = "incActId", nullable = true)
	private IncidentActivity incAct;
	
	private String message;
	private Date dateCreated;
	private Date dateReviewed;
	private String username;
	private boolean publish;
	private int statusId;

	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id=id;
	}
	
	public OnlineClaim getClaim(){
		return claim;
	}
	
	public void setClaim(OnlineClaim claim){
		this.claim=claim;
	}

	
	public IncidentActivity getIncAct(){
		return incAct;
	}
	
	public void setIncAct(IncidentActivity incAct){
		this.incAct=incAct;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateReviewed() {
		return dateReviewed;
	}

	public void setDateReviewed(Date dateReviewed) {
		this.dateReviewed = dateReviewed;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
