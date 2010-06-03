package com.bagnet.nettracer.tracing.db;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


//for the new feature - email and SMS message
//being sent to Pax when certain condition is met
@Entity
@Table(name = "PAX_MESSAGE_TRIGGER")
public class PaxMessageTrigger {
	private String triggerKey;
	private String emailContentText;
	private String smsContentText;
	private String language = "en";
	private String subjectLine;
	
	@Column(name="SUBJECT_LINE", columnDefinition="VARCHAR(65)")
	public String getSubjectLine() {
		return subjectLine;
	}
	public void setSubjectLine(String subjectLine) {
		this.subjectLine = subjectLine;
	}
	
	@Column(name="EMAIL_CONTENT_TEXT")
	public String getEmailContentText() {
		return emailContentText;
	}
	public void setEmailContentText(String emailContentText) {
		this.emailContentText = emailContentText;
	}
	
	@Column(name="SMS_CONTENT_TEXT", columnDefinition="VARCHAR(65)")
	public String getSmsContentText() {
		return smsContentText;
	}
	public void setSmsContentText(String smsContentText) {
		this.smsContentText = smsContentText;
	}
	
	@Column(name="LANGUAGE", columnDefinition="VARCHAR(2) default 'en'")
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	@Id
	@Column(name="TRIGGER_KEY", columnDefinition="VARCHAR(16)")
	public String getTriggerKey() {
		return triggerKey;
	}
	public void setTriggerKey(String triggerKey) {
		this.triggerKey = triggerKey;
	}
	
}
