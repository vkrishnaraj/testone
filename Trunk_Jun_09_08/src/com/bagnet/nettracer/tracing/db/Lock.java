package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "GLOBAL_LOCK", 
		uniqueConstraints = @UniqueConstraint(columnNames={"lockType", "lockKey"}))
public class Lock implements Serializable {
	
	public static final long DEFAULT_DURATION = 5000L;
	
	private long id;
	private LockType lockType;
	private String lockKey;
	private String owner;
	private Date createDate;
	private Date expirationDate;
	
	public Lock() {}

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	private void setId(long id) {
		this.id = id;
	}

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	public LockType getLockType() {
		return lockType;
	}

	public void setLockType(LockType lockType) {
		this.lockType = lockType;
	}

	@Column(length = 20)
	public String getLockKey() {
		return lockKey;
	}

	public void setLockKey(String lockKey) {
		this.lockKey = lockKey;
	}

	@Column(length = 20)
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	public static enum LockType{
		AF_COUNT, AF_SUMMARY, AF_DETAIL, TM_INCIDENT, LF_FOUND, DISPUTE, INCIDENT_ACTIVITY
	};
}
