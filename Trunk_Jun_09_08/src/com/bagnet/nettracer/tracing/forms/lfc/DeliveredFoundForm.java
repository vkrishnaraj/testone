package com.bagnet.nettracer.tracing.forms.lfc;

import java.util.List;

import org.apache.struts.action.ActionForm;

public final class DeliveredFoundForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	private List deliveredFound;
	
	public List getDeliveredFound() {
		return deliveredFound;
	}
	public void setDeliveredFound(List deliveredFound) {
		this.deliveredFound = deliveredFound;
	}
	
	
}