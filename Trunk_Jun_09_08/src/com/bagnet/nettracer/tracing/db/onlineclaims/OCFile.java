package com.bagnet.nettracer.tracing.db.onlineclaims;

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

import org.hibernate.annotations.Proxy;

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
	
	@Column(length = 50)
	private String filename;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUploaded;
	
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
	public OnlineClaim getClaim() {
		return claim;
	}
	public void setClaim(OnlineClaim claim) {
		this.claim = claim;
	}
}
