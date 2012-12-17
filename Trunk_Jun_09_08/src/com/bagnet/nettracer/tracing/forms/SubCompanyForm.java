package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

public class SubCompanyForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	
	private String subcompanyCode;
	private String companyCode;
	private String subCompSearchName;

	private String name;
	private String email_Subject;
	private String email_Path;
	private int email_Notice_1;
	private int email_Notice_2;
	private int auto_Close_Low;
	private int auto_Close_High;
	private String delete1;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSubcompanyCode() {
		return subcompanyCode;
	}

	public void setSubcompanyCode(String subcompanyCode) {
		this.subcompanyCode = subcompanyCode;
	}

	public void setSubCompSearchName(String subCompSearchName) {
		this.subCompSearchName = subCompSearchName;
	}
	
	public String getSubCompSearchName() {
		return subCompSearchName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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

	public void setDelete1(String delete1) {
		this.delete1 = delete1;
	}

	public String getDelete1() {
		return delete1;
	}
}

