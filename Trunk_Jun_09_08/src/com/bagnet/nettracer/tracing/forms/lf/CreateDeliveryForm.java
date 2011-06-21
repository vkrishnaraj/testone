package com.bagnet.nettracer.tracing.forms.lf;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.db.lf.LFItem;

public final class CreateDeliveryForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private LFItem item;

	public LFItem getItem() {
		return item;
	}

	public void setItem(LFItem item) {
		this.item = item;
	}

}