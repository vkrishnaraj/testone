package com.bagnet.nettracer.tracing.db.salvage;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "salvage")
@Proxy(lazy = false)
public class Salvage {
	
	private int salvageId;
	private String companyCodeId;
	private Date salvageDate;
	private String pickedUpByLName;
	private String pickedUpByFName;
	private int status;
	private Set<SalvageBox> salvageBoxes;
	private Set<SalvageOHDReference> ohdReferences;
	
	@Id
	@GeneratedValue
	@Column(name="salvage_id")
	public int getSalvageId() {
		return salvageId;
	}
	
	public void setSalvageId(int salvageId) {
		this.salvageId = salvageId;
	}
	
	@Column(name="companycode_id", length=2)
	public String getCompanyCodeId() {
		return companyCodeId;
	}
	
	public void setCompanyCodeId(String companyCodeId) {
		this.companyCodeId = companyCodeId;
	}
	
	@Column(name="salvage_date")
	public Date getSalvageDate() {
		return salvageDate;
	}
	
	public void setSalvageDate(Date salvageDate) {
		this.salvageDate = salvageDate;
	}
	
	@Column(name="pickedupby_lname", length=30)
	public String getPickedUpByLName() {
		return pickedUpByLName;
	}
	
	public void setPickedUpByLName(String pickedUpByLName) {
		this.pickedUpByLName = pickedUpByLName;
	}
	
	@Column(name="pickedupby_fname", length=30)
	public String getPickedUpByFName() {
		return pickedUpByFName;
	}
	
	public void setPickedUpByFName(String pickedUpByFName) {
		this.pickedUpByFName = pickedUpByFName;
	}
	
	@Column(name="status")
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	@OneToMany(mappedBy = "salvage", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@OrderBy(clause = "display_id")
	@Fetch(FetchMode.SELECT)
	public Set<SalvageBox> getSalvageBoxes() {
		return salvageBoxes;
	}
	
	public void setSalvageBoxes(Set<SalvageBox> salvageBoxes) {
		this.salvageBoxes = salvageBoxes;
	}
	
	@OneToMany(mappedBy = "salvage", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@OrderBy(clause = "ohd_id")
	@Fetch(FetchMode.SELECT)
	public Set<SalvageOHDReference> getOhdReferences() {
		return ohdReferences;
	}
	
	public void setOhdReferences(Set<SalvageOHDReference> ohdReferences) {
		this.ohdReferences = ohdReferences;
	}
	
}