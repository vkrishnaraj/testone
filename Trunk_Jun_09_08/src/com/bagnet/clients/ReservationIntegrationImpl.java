package com.bagnet.clients;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.db.BagDrop;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.forms.OnHandForm;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Incident;

public class ReservationIntegrationImpl implements
		com.bagnet.nettracer.integrations.reservation.ReservationIntegration {

	protected static Logger logger = Logger.getLogger(ReservationIntegrationImpl.class);
	
	private com.bagnet.nettracer.integrations.reservation.ReservationIntegration target=new com.bagnet.clients.defaul.ReservationIntegrationImpl();
        
	public ReservationIntegrationImpl() {
		String companyCode = TracerProperties.get("wt.company.code");
		String path1 = TracerProperties.get(companyCode,
				"reservation.class.path"); // Use new TP get method
		String path2 = TracerProperties.get(companyCode,
				"reservation.version.name"); // Use new TP get method
		if(path2==null){
			path2="";
		}
		try {
			Class cls = Class.forName("com.bagnet.clients." + path1 + "."
					+ path2 + "ReservationIntegrationImpl");
			com.bagnet.nettracer.integrations.reservation.ReservationIntegration res = (com.bagnet.nettracer.integrations.reservation.ReservationIntegration) cls
					.newInstance();
			target = res;
		} catch (Exception x) {

		}

	}
    
	@Override
	public boolean isWriteCommentToPnrOn() {
		return target.isWriteCommentToPnrOn();
	}

	@Override
	public boolean isWriteExpensesToPnrOn() {
		return target.isWriteExpensesToPnrOn();
	}

	@Override
	public boolean isPopulateIncidentFormOn() {
		// TODO Auto-generated method stub
		return target.isPopulateIncidentFormOn();
	}

	@Override
	public boolean isPopulateOhdFormOn() {
		// TODO Auto-generated method stub
		return target.isPopulateOhdFormOn();
	}

	@Override
	public ArrayList<String> populateIncidentForm(HttpServletRequest request,
			IncidentForm form, int incidentType) {
		// TODO Auto-generated method stub
		return target.populateIncidentForm(request, form, incidentType);
	}

	@Override
	public ArrayList<String> writeCommentToPNR(String comment,
			String recordLocator) {
		// TODO Auto-generated method stub
		return target.writeCommentToPNR(comment, recordLocator);
	}

	@Override
	public ArrayList<String> populateOhdForm(HttpServletRequest request,
			OnHandForm form) {
		// TODO Auto-generated method stub
		return target.populateOhdForm(request, form);
	}

	@Override
	public Incident populateIncidentForWS(Incident incident, int passIndex) {
		// TODO Auto-generated method stub
		return target.populateIncidentForWS(incident, passIndex);
	}

	@Override
	public ArrayList<BagDrop> getFlightInfo(String stationcode, Calendar date) {
		// TODO Auto-generated method stub
		return target.getFlightInfo(stationcode, date);
	}
}
