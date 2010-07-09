package com.bagnet.nettracer.tracing.db.onlineclaims.audit;

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
@Table(name = "oc_audit_file")
@Proxy(lazy = false)
public class AOCFile {
	
	@Id
	@GeneratedValue
	private long aid;
	
	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim.class)
	@JoinColumn(name = "auditClaimId", nullable = false)
	private AOCClaim claim;
	
	@Column(length = 50)
	private String filename;
	
	@Column(length = 100)
	private String path;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUploaded;
	
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
	public AOCClaim getClaim() {
		return claim;
	}
	public void setClaim(AOCClaim claim) {
		this.claim = claim;
	}
	public long getAid() {
		return aid;
	}
	public void setAid(long aid) {
		this.aid = aid;
	}
  public String getPath() {
  	return path;
  }
	public void setPath(String path) {
  	this.path = path;
  }
}
