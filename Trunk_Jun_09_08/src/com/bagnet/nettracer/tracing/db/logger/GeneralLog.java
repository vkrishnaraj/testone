package com.bagnet.nettracer.tracing.db.logger;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;


@Entity
@Table(name="generallog")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="trans", discriminatorType = DiscriminatorType.STRING, length=16)
@DiscriminatorValue("GENERAL")
@Proxy(lazy = true)
public class GeneralLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4770369543842583765L;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getEntrydate() {
		return entrydate;
	}
	public void setEntrydate(Date entrydate) {
		this.entrydate = entrydate;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String desc) {
		this.description = desc;
	}
	public long getElapseTime() {
		return elapseTime;
	}
	public void setElapseTime(long elapseTime) {
		this.elapseTime = elapseTime;
	}
	
	@Id
	@GeneratedValue
	long id;
	Date entrydate;
	String refId;
	String description;
	long elapseTime;
}
