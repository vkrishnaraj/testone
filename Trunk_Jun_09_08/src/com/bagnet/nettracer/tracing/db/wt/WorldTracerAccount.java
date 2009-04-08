package com.bagnet.nettracer.tracing.db.wt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "world_tracer_account")
public class WorldTracerAccount implements Serializable {

	private static final long serialVersionUID = 4856969474327661943L;

	@Id
	@GeneratedValue
	private long id;

	@Column(length = 2)
	private String companyCode;

	@Column(length = 20)
	private String instanceName;

	@Column(length = 20)
	private String username;

	@Column(length = 20)
	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastReset;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastReset() {
		return lastReset;
	}

	public void setLastReset(Date lastReset) {
		this.lastReset = lastReset;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
}
