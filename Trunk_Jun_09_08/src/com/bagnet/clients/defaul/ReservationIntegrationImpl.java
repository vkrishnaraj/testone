package com.bagnet.clients.defaul;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.db.BagDrop;
import com.bagnet.nettracer.tracing.forms.ExpensePayoutForm;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.forms.OnHandForm;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Incident;

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
		return TracerProperties.isTrue(TracerProperties.get("wt.company.code"),TracerProperties.RESERVATION_POPULATE_INCIDENT_ON);
	}

	public boolean isWriteCommentToPnrOn() {
		return TracerProperties.isTrue(TracerProperties.get("wt.company.code"),TracerProperties.RESERVATION_UPDATE_COMMENT_ON);
	}
	
	public boolean isWriteExpensesToPnrOn() {
		return TracerProperties.isTrue(TracerProperties.get("wt.company.code"),TracerProperties.RESERVATION_UPDATE_EXPENSES_ON);
	}
	
	public boolean isPopulateOhdFormOn() {
		return TracerProperties.isTrue(TracerProperties.get("wt.company.code"),TracerProperties.RESERVATION_POPULATE_OHD_ON);
	}

	public ArrayList<String> populateIncidentForm(HttpServletRequest request, IncidentForm form,
			int incidentType) {
		return null;
	}

	public ArrayList<String> writeCommentToPNR(String comment, String recordLocator) {
		return null;
	}

	public ArrayList<String> populateOhdForm(HttpServletRequest request,
			OnHandForm form) {
		return null;
	}
	
	public Incident populateIncidentForWS(Incident incident, int passIndex) {
		return null;
	}

	public void doEventOnBeornWS(HashMap<String, String> map) {
		// Do nothing
	}

	@Override
	public ArrayList<BagDrop> getFlightInfo(String stationcode, Calendar date) {
		return null;
	}
	
	public ArrayList<String> submitVoucher(com.bagnet.nettracer.tracing.db.Incident inc, String status, ExpensePayoutForm epf){
		return null;
	}
}
