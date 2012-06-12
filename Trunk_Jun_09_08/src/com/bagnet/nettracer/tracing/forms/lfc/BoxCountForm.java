package com.bagnet.nettracer.tracing.forms.lfc;

import org.apache.struts.action.ActionForm;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.bagnet.nettracer.tracing.db.lf.LFBoxCount;
import com.bagnet.nettracer.tracing.db.lf.LFBoxContainer;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public final class BoxCountForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	private Date dateCount;
	private LFBoxContainer container=new LFBoxContainer();
	private String addStation;
	private String remStation;
	private String dateString;
	
	public String getAddStation() {
		return addStation;
	}
	
	public void setAddStation(String addStation) {
		this.addStation = addStation;
	}
	
	public Date getDateCount() {
		return dateCount;
	}
	
	public void setDateCount(Date dateCount) {
		this.dateCount = dateCount;
	}
	
	public String getDisDateCount(Agent a) {
		return DateUtils.formatDate(dateCount, a.getDateformat().getFormat(), a.getCurrenttimezone(), null);
	}

	public String getDateString() {
		
		return dateString;
	}
	
	public void setDateString(String dateString) {
		
		this.dateString=dateString;
	}
	
	public LFBoxContainer getContainer() {
		return container;
	}
	
	public void setContainer(LFBoxContainer container) {
		this.container=container;
	}
	
}