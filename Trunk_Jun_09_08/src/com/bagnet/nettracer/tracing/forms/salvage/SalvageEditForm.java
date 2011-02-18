package com.bagnet.nettracer.tracing.forms.salvage;

import java.util.TimeZone;

import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.db.salvage.Salvage;
import com.bagnet.nettracer.tracing.utils.DateUtils;

@SuppressWarnings("serial")
public class SalvageEditForm extends ValidatorForm {
	
	private Salvage salvage;
	private String dateFormat;
	private String timeFormat;
	private TimeZone timeZone;

	public Salvage getSalvage() {
		return salvage;
	}

	public void setSalvage(Salvage salvage) {
		this.salvage = salvage;
	}
	
	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public String getDisSalvageDate() {
		return DateUtils.formatDate(salvage.getSalvageDate(), dateFormat, "", timeZone);
	}
	
	

}
