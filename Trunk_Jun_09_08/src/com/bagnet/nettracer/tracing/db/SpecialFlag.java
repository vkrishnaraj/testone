package com.bagnet.nettracer.tracing.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//for captcha feature
@Entity
@Table(name = "SPECIAL_FLAG")
public class SpecialFlag {

	private String keyStr;
	private String valueStr;
	private Date flagResetTimestamp;
	
	@Id
	public String getKeyStr() {
		return keyStr;
	}
	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}
	
	public String getValueStr() {
		return valueStr;
	}
	public void setValueStr(String valueStr) {
		this.valueStr = valueStr;
	}
	
	public Date getFlagResetTimestamp() {
		return flagResetTimestamp;
	}
	public void setFlagResetTimestamp(Date flagResetTimestamp) {
		this.flagResetTimestamp = flagResetTimestamp;
	}
}
