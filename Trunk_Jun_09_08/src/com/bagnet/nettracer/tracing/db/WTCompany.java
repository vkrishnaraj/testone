package com.bagnet.nettracer.tracing.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;
/**
 * @author Sean Fine
 * Class representing WTCompany object
 */
@Entity
@Table(name = "wtcompany")
@Proxy(lazy = false)
public class WTCompany {

	private long id;
	private String wtCompanyCode;
	private String company_id;
	private String companyName;
	
	@Id @GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWtCompanyCode() {
		return wtCompanyCode;
	}

	public void setWtCompanyCode(String wtCompanyCode) {
		this.wtCompanyCode = wtCompanyCode;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
