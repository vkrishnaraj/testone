/**
 * 
 */
package com.bagnet.nettracer.tracing.utils.reporting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.utils.AdminUtils;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperReport;

/**
 * @author Byron
 * This class implements the JRDataSource interface for use
 * in Incident and Custom Query reports of Incidents.
 */
public class JRIncidentDataSource implements JRDataSource {

	private String INCIDENT_ID_NAME = "incidentId";
	private String TYPE_NAME = "type";
	private String STATIONCREATED_NAME = "stationCreated";
	private String STATIONASSIGNED_NAME = "stationAssigned";
	private String DATE_NAME = "date";
	private String STATUS_NAME = "status";
	private String TICKETNUMBER_NAME = "ticketNumber";
	private String CLAIMCHECK_NAME = "claimCheck";
	private String PASSENGER_NAME = "passengerName";
	
	private String dateFormat = null;
	private String timeFormat = null;
	private TimeZone timeZone = null;
	
	
	private ArrayList<Incident> incidentQueue = null;
	private HashMap<String, JRField> fieldMap = new HashMap<String, JRField>();
	private Incident currentObject = null;

	private int totalElements = 0;
	private int currentElement = 0;
	
	public JRIncidentDataSource(JasperReport jasperReport, ArrayList<Incident> incidents, Agent user) {
		super();

		// Instantiate Incident Queue
		this.incidentQueue = incidents;
		
		// Get user for formatting dates
		this.dateFormat = user.getDateformat().getFormat();
		this.timeFormat = user.getTimeformat().getFormat();
		this.timeZone = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getCurrenttimezone())
				.getTimezone());
		
		// Map the fields
		JRField[] fields = jasperReport.getFields();
		for (JRField field: fields) {
			fieldMap.put(field.getName(), field);
		}
	}

	public Object getFieldValue(JRField field) throws JRException {
		String fieldName = field.getName();
		
		// Only return these elemnts if primary entry in report.
		if (currentElement == 0) {
			if (fieldName.equals(INCIDENT_ID_NAME)) {
				return currentObject.getIncident_ID();
			} else if (fieldName.equals(TYPE_NAME)) {
				return currentObject.getItemtype().getDescription();
			} else if (fieldName.equals(STATIONCREATED_NAME)) {
				return currentObject.getStationcreated().getStationcode();
			} else if (fieldName.equals(STATIONASSIGNED_NAME)) {
				return currentObject.getStationassigned().getStationcode();
			} else if (fieldName.equals(DATE_NAME)) {
				currentObject.set_DATEFORMAT(dateFormat);
				currentObject.set_TIMEFORMAT(timeFormat);
				currentObject.set_TIMEZONE(timeZone);
				return currentObject.getDisplaydate();
			} else if (fieldName.equals(STATUS_NAME)) {
				return currentObject.getStatus().getDescription();
			} else if (fieldName.equals(TICKETNUMBER_NAME)) {
				return currentObject.getTicketnumber();
			}
		}
			
		if (fieldName.equals(CLAIMCHECK_NAME)) {
			if (currentElement < currentObject.getClaimcheck_list().size()) {
				Incident_Claimcheck tmp = (Incident_Claimcheck) 
					currentObject.getClaimcheck_list().get(currentElement);
				return tmp.getClaimchecknum();
			}
			return null;
		} else if (fieldName.equals(PASSENGER_NAME)) {
			if (currentElement < currentObject.getPassenger_list().size()) {
				Passenger tmp = (Passenger) currentObject.getPassenger_list().get(currentElement);
				return  tmp.getLastname() + ", " + tmp.getFirstname() + " " + tmp.getMiddlename();
			}
			return null;
		} else {
			return null;
		}
	}

	public boolean next() throws JRException {

		if (currentElement + 1 < totalElements) {
			++currentElement;
			return true;
		}
		
		if (incidentQueue.size() > 0) {
			// If there are any more incidents, use next one.
			currentObject = incidentQueue.remove(0);
			currentElement = 0;
			totalElements = java.lang.Math.max(currentObject.getPassenger_list().size(), 
					currentObject.getClaimcheck_list().size());
			
			return true;
		} else {
			// There are no remaining incidents to return.
			return false;
		}
	}
}
