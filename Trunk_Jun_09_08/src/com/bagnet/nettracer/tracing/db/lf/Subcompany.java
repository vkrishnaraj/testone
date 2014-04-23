package com.bagnet.nettracer.tracing.db.lf;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.Company;

@Entity
@Proxy(lazy = false)
public class Subcompany implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	@JoinColumn(name = "company_ID", nullable = false)
	private Company company;
	
	@Column(length = 3)
	private String subcompanyCode;

	private String name;
	private String email_Subject;
	private String email_Path;
	private int email_Notice_1;
	private int email_Notice_2;
	private int email_Notice_3;
	private int email_Notice_4;
	private int email_Notice_5;
	private int auto_Close_Low;
	private int auto_Close_High;
	private int shippingSurcharge;
	private boolean sendDataplanEmails;

	private int salvage_Low;
	private int salvage_High;

	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="subcompany")
	private Set<SubcompanyStation> subcompanyStations;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getSubcompanyCode() {
		return subcompanyCode;
	}

	public void setSubcompanyCode(String subcompanyCode) {
		this.subcompanyCode = subcompanyCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail_Subject() {
		return email_Subject;
	}

	public void setEmail_Subject(String email_Subject) {
		this.email_Subject = email_Subject;
	}

	public String getEmail_Path() {
		return email_Path;
	}

	public void setEmail_Path(String email_Path) {
		this.email_Path = email_Path;
	}

	public int getEmail_Notice_1() {
		return email_Notice_1;
	}

	public void setEmail_Notice_1(int email_Notice_1) {
		this.email_Notice_1 = email_Notice_1;
	}

	public int getEmail_Notice_2() {
		return email_Notice_2;
	}

	public void setEmail_Notice_2(int email_Notice_2) {
		this.email_Notice_2 = email_Notice_2;
	}
	
	public int getEmail_Notice_3() {
		return email_Notice_3;
	}

	public void setEmail_Notice_3(int email_Notice_3) {
		this.email_Notice_3 = email_Notice_3;
	}

	public int getEmail_Notice_4() {
		return email_Notice_4;
	}

	public void setEmail_Notice_4(int email_Notice_4) {
		this.email_Notice_4 = email_Notice_4;
	}

	public int getEmail_Notice_5() {
		return email_Notice_5;
	}

	public void setEmail_Notice_5(int email_Notice_5) {
		this.email_Notice_5 = email_Notice_5;
	}

	public int getAuto_Close_Low() {
		return auto_Close_Low;
	}

	public void setAuto_Close_Low(int auto_Close_Low) {
		this.auto_Close_Low = auto_Close_Low;
	}

	public int getAuto_Close_High() {
		return auto_Close_High;
	}

	public void setAuto_Close_High(int auto_Close_High) {
		this.auto_Close_High = auto_Close_High;
	}

	public int getShippingSurcharge() {
		return shippingSurcharge;
	}

	public void setShippingSurcharge(int shippingSurcharge) {
		this.shippingSurcharge = shippingSurcharge;
	}

	public boolean isSendDataplanEmails() {
		return sendDataplanEmails;
	}

	public void setSendDataplanEmails(boolean sendDataplanEmails) {
		this.sendDataplanEmails = sendDataplanEmails;
	}

	public int getSalvage_Low() {
		return salvage_Low;
	}

	public void setSalvage_Low(int salvage_Low) {
		this.salvage_Low = salvage_Low;
	}

	public int getSalvage_High() {
		return salvage_High;
	}

	public void setSalvage_High(int salvage_High) {
		this.salvage_High = salvage_High;
	}
	
}

