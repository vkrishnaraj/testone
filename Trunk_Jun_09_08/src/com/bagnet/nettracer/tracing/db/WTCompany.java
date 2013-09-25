package com.bagnet.nettracer.tracing.db;

import java.util.Date;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.utils.DateUtils;
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
}
