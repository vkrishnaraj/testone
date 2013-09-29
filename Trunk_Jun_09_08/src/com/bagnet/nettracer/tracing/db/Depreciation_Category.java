package com.bagnet.nettracer.tracing.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;
/**
 * @author Sean Fine
 * Class representing Depreciation Category object
 */
@Entity
@Table(name = "depreciation_category")
@Proxy(lazy = false)
public class Depreciation_Category {

	private int id;
	private String name;
	private int calcMethod;
	private boolean notCoveredCoc;
	private double flatRate;
	private double firstYear;
	private double secondYear;
	private double thirdYear;
	private double eachYear;
	private double maxDeprec;

	private String companyCode;

	@Id @GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCalcMethod() {
		return calcMethod;
	}

	public void setCalcMethod(int calcMethod) {
		this.calcMethod = calcMethod;
	}

	public boolean isNotCoveredCoc() {
		return notCoveredCoc;
	}

	public void setNotCoveredCoc(boolean notCoveredCoc) {
		this.notCoveredCoc = notCoveredCoc;
	}

	public double getFlatRate() {
		return flatRate;
	}

	public void setFlatRate(double flatRate) {
		this.flatRate = flatRate;
	}

	public double getFirstYear() {
		return firstYear;
	}

	public void setFirstYear(double firstYear) {
		this.firstYear = firstYear;
	}

	public double getSecondYear() {
		return secondYear;
	}

	public void setSecondYear(double secondYear) {
		this.secondYear = secondYear;
	}

	public double getThirdYear() {
		return thirdYear;
	}

	public void setThirdYear(double thirdYear) {
		this.thirdYear = thirdYear;
	}

	public double getEachYear() {
		return eachYear;
	}

	public void setEachYear(double eachYear) {
		this.eachYear = eachYear;
	}

	public double getMaxDeprec() {
		return maxDeprec;
	}

	public void setMaxDeprec(double maxDeprec) {
		this.maxDeprec = maxDeprec;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}


}
