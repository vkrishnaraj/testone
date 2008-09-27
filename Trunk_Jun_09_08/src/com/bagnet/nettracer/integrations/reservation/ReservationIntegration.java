package com.bagnet.nettracer.integrations.reservation;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.forms.OnHandForm;

public interface ReservationIntegration {

	public boolean isWriteCommentToPnrOn();
	public boolean isPopulateIncidentFormOn();	
	public boolean isPopulateOhdFormOn();
	
	public ArrayList<String> populateIncidentForm(HttpServletRequest request, IncidentForm form, int incidentType);
	public ArrayList<String> writeCommentToPNR(String comment, String recordLocator);
	public ArrayList<String> populateOhdForm(HttpServletRequest request, OnHandForm form);

}
