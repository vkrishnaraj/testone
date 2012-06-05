package com.bagnet.nettracer.tracing.forms.lfc;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.db.lf.LFSalvage;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public final class SalvageForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	private LFSalvage salvage;
	
	public LFSalvage getSalvage() {
		return salvage;
	}
	public void setSalvage(LFSalvage salvage) {
		this.salvage = salvage;
	}
	
	public String getDisCreatedDate() {
		return DateUtils.formatDate(salvage.getCreatedDate(), salvage.getAgent().getDateformat().getFormat(), salvage.getAgent().getCurrenttimezone(), null);
	}

	public String getDisClosedDate() {
		if (salvage.getClosedDate() != null) {
			return DateUtils.formatDate(salvage.getClosedDate(), salvage.getAgent().getDateformat().getFormat(), salvage.getAgent().getCurrenttimezone(), null);
		}
		return "";
	}
	
	public String getDisCreatedDate(Agent a) {
		return DateUtils.formatDate(salvage.getCreatedDate(), a.getDateformat().getFormat(), a.getCurrenttimezone(), null);
	}

	public String getDisClosedDate(Agent a) {
		if (salvage.getClosedDate() != null) {
			return DateUtils.formatDate(salvage.getClosedDate(), a.getDateformat().getFormat(), a.getCurrenttimezone(), null);
		}
		return "";
	}
	
	
}