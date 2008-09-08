package com.bagnet.clients.defaul;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class ReservationIntegrationImpl implements
		com.bagnet.nettracer.integrations.reservation.ReservationIntegration {

	protected static Logger logger = Logger.getLogger(ReservationIntegrationImpl.class);
	
	protected void addError(ArrayList<String> errors, String error) {
		if (errors == null) {
			errors = new ArrayList<String>();
		}
		errors.add(error);
	}
	
	public boolean isPopulateIncidentFormOn() {
		String property = TracerProperties.get("booking.is_on");
		if (property == null) return false;
		else return property.equals("1");
	}

	public boolean isWriteCommentToPnrOn() {
		String property = TracerProperties.get("updatecomment.is_on");
		if (property == null) return false;
		else return property.equals("1");

	}

	public ArrayList<String> populateIncidentForm(HttpServletRequest request, IncidentForm form,
			int incidentType) {
		return null;
	}

	public ArrayList<String> writeCommentToPNR(String comment, String recordLocator) {
		return null;
	}

}
