package com.bagnet.nettracer.integrations.reservation;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.bagnet.nettracer.tracing.forms.IncidentForm;

public interface ReservationIntegration {

	public boolean isWriteCommentToPnrOn();
	public boolean isPopulateIncidentFormOn();	
	
	public ArrayList<String> populateIncidentForm(HttpServletRequest request, IncidentForm form, int incidentType);
	public ArrayList<String> writeCommentToPNR(String comment, String recordLocator);

}
