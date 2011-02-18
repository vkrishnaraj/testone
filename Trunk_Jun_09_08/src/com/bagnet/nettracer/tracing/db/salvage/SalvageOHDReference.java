package com.bagnet.nettracer.tracing.db.salvage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "salvage_ohd_reference")
@Proxy(lazy = false)
public class SalvageOHDReference {
	
	private int ohdRefId;
	private String ohdId;
	private Salvage salvage;

	@Id
	@GeneratedValue
	@Column(name="ohd_ref_id")
	public int getOhdRefId() {
		return this.ohdRefId;
	}
	
	public void setOhdRefId(int ohdRefId) {
		this.ohdRefId = ohdRefId;
	}
	
	@Column(name="ohd_id", length=13, nullable=false)
	public String getOhdId() {
		return ohdId;
	}
	
	public void setOhdId(String ohdId) {
		this.ohdId = ohdId;
	}
	
	@ManyToOne
	@JoinColumn(name = "salvage_id", nullable = true)
	@Fetch(FetchMode.SELECT)
	public Salvage getSalvage() {
		return salvage;
	}
	
	public void setSalvage(Salvage salvage) {
		this.salvage = salvage;
	}
	

}
