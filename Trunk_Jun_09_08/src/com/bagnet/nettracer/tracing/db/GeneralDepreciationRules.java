package com.bagnet.nettracer.tracing.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;
/**
 * @author Sean Fine
 * Class representing General Depreciation Rules object
 */
@Entity
@Table(name = "generaldepreciationrules")
@Proxy(lazy = false)
public class GeneralDepreciationRules {

	private int id;
	private double lessTwentyDeprec;
	private double twentyOnefiftyDeprec;
	private double onefiftyDeprec;
	private int noDates;

	private String companyCode;

	@Id @GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLessTwentyDeprec() {
		return lessTwentyDeprec;
	}

	public void setLessTwentyDeprec(double lessTwentyDeprec) {
		this.lessTwentyDeprec = lessTwentyDeprec;
	}

	public double getTwentyOnefiftyDeprec() {
		return twentyOnefiftyDeprec;
	}

	public void setTwentyOnefiftyDeprec(double twentyOnefiftyDeprec) {
		this.twentyOnefiftyDeprec = twentyOnefiftyDeprec;
	}

	public double getOnefiftyDeprec() {
		return onefiftyDeprec;
	}

	public void setOnefiftyDeprec(double onefiftyDeprec) {
		this.onefiftyDeprec = onefiftyDeprec;
	}

	public int getNoDates() {
		return noDates;
	}

	public void setNoDates(int noDates) {
		this.noDates = noDates;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

}
