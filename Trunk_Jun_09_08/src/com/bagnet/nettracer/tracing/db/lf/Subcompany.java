package com.bagnet.nettracer.tracing.db.lf;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	private int auto_Close_Low;
	private int auto_Close_High;
	
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
}
