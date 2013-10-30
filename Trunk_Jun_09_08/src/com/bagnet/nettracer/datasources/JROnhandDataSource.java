/**
 * 
 */
package com.bagnet.nettracer.datasources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperReport;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Byron
 * This class implements the JRDataSource interface for use
 * in OHD and Custom Query reports of OHDs.
 */
public class JROnhandDataSource implements JRDataSource {

	private String OHD_ID_NAME = "ohdId";
	private String INCIDENT_ID_NAME = "incidentId";
	private String TYPE_NAME = "type";
	private String COLOR_NAME = "color";
	private String STATIONFOUND_NAME = "stationFound";
	private String STATIONASSIGNED_NAME = "stationAssigned";
	private String DATE_NAME = "date";
	private String STATUS_NAME = "status";
	private String BAGTAGNUMBER_NAME = "bagTagNumber";
	private String PASSENGER_NAME = "passengerName";
	private String MODIFIEDDATE_NAME = "modifiedDate";
	private String DESTINATION_NAME = "destination";
	private String COMMENTS_NAME = "comments";
	private String POS_ID_NAME = "posId";
		
	
	private String dateFormat = null;
	private String timeFormat = null;
	private TimeZone timeZone = null;
	
	
	private List<OHD> OHDQueue = null;
	private HashMap<String, JRField> fieldMap = new HashMap<String, JRField>();
	private OHD currentObject = null;
	
	private ArrayList<OHD_Passenger> passengers = null;

	private int totalElements = 0;
	private int currentElement = 0;
	private Agent currentUser = null;
	
	public JROnhandDataSource(JasperReport jasperReport, List<OHD> OHDs, Agent user) {
		super();
		this.currentUser = user;
		// Instantiate OHD Queue
		this.OHDQueue = OHDs;
		
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
			if (fieldName.equals(OHD_ID_NAME)) {
				return currentObject.getOHD_ID();
			} else if (fieldName.equals(TYPE_NAME)) {
				return currentObject.getType();
			} else if (fieldName.equals(STATIONFOUND_NAME)) {
				return currentObject.getFoundAtStation().getStationcode();
			} else if (fieldName.equals(STATIONASSIGNED_NAME)) {
				return currentObject.getHoldingStation().getStationcode();
			} else if (fieldName.equals(DATE_NAME)) {
				currentObject.set_DATEFORMAT(dateFormat);
				currentObject.set_TIMEFORMAT(timeFormat);
				currentObject.set_TIMEZONE(timeZone);
				return currentObject.getDisplaydate();
			} else if (fieldName.equals(STATUS_NAME)) {
				return currentObject.getStatus().getTextDescription(currentUser);
			} else if (fieldName.equals(COLOR_NAME)) {
				return currentObject.getColor();
			} else if (fieldName.equals(BAGTAGNUMBER_NAME)) {
				return currentObject.getClaimnum();
			} else if (fieldName.equals(INCIDENT_ID_NAME)){
				return currentObject.getMatched_incident();
			} else if (fieldName.equals(MODIFIEDDATE_NAME)){
				return DateUtils.formatDate(currentObject.getModifiedDate(), TracingConstants.DISPLAY_DATEFORMAT + " " + TracingConstants.DISPLAY_TIMEFORMAT_C, null, currentObject.get_TIMEZONE());
			} else if (fieldName.equals(DESTINATION_NAME)){
				return currentObject.getDispDestination();
			} else if (fieldName.equals(COMMENTS_NAME)){
				return currentObject.getStorage_location();
			} else if (fieldName.equals(POS_ID_NAME)){
				return currentObject.getPosId();
			}    
		}
		
		if (fieldName.equals(PASSENGER_NAME)) {
			if (currentElement < passengers.size()) {
				OHD_Passenger tmp = passengers.get(currentElement);
				if (tmp.getLastname() != null && tmp.getFirstname() != null && tmp.getLastname().length() > 0 && tmp.getFirstname().length() > 0)
					return tmp.getLastname() + ", " + tmp.getFirstname()+ " " + tmp.getMiddlename();
				else if (tmp.getLastname() == null && tmp.getFirstname() == null )
					return null;
				else if (tmp.getLastname() != null && tmp.getLastname().length() > 0)
					return  tmp.getLastname();
				else if (tmp.getFirstname() != null && tmp.getFirstname().length() > 0)
					return  ", " + tmp.getFirstname();
				else 
					return null;
			}
			return null;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public boolean next() throws JRException {

		if (currentElement + 1 < totalElements) {
			++currentElement;
			return true;
		}
		
		if (OHDQueue.size() > 0) {
			// If there are any more OHDs, use next one.
			currentObject = OHDQueue.remove(0);
			currentElement = 0;

			totalElements = java.lang.Math.max(currentObject.getPassengers().size(), 1);
			passengers = new ArrayList<OHD_Passenger>(currentObject.getPassengers());
			return true;
		} else {
			// There are no remaining OHDs to return.
			return false;
		}
	}
}
