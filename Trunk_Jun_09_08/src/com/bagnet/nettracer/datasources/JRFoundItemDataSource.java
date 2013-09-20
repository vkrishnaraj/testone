/**
 * 
 */
package com.bagnet.nettracer.datasources;

import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperReport;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.lf.LFFound;

/**
 * @author Byron
 * This class implements the JRDataSource interface for use
 * in OHD and Custom Query reports of OHDs.
 */
public class JRFoundItemDataSource implements JRDataSource {

	private String ID_NAME = "id";
	private String DATE_NAME = "date";
	private String STATUS_NAME = "status";
	private String STATION_NAME = "station";
	private String CLIENT_NAME = "clientName";
	
	private String dateFormat = null;
	
	private List<LFFound> FoundItemQueue = null;
	private HashMap<String, JRField> fieldMap = new HashMap<String, JRField>();
	private LFFound currentObject = null;

	private int currentElement = 0;
	private Agent currentUser = null;
	
	public JRFoundItemDataSource(JasperReport jasperReport, List<LFFound> FoundItems, Agent user) {
		super();
		this.currentUser = user;
		// Instantiate OHD Queue
		this.FoundItemQueue = FoundItems;
		
		// Get user for formatting dates
		this.dateFormat = user.getDateformat().getFormat();
		
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
			if (fieldName.equals(ID_NAME)) {
				return currentObject.getId();
			} else if (fieldName.equals(STATION_NAME)) {
				return currentObject.getLocation().getStationcode();
			} else if (fieldName.equals(DATE_NAME)) {
				return currentObject.getDisplayDate(dateFormat);
			} else if (fieldName.equals(STATUS_NAME)) {
				return currentObject.getStatus().getTextDescription(currentUser);
			} else if (fieldName.equals(CLIENT_NAME)) {
				return currentObject.getClientName();
			}
		}
		return null;
	}

	public boolean next() throws JRException {
		
		if (FoundItemQueue != null && FoundItemQueue.size() > 0) {
			// If there are any more Found Items, use next one.
			currentObject = FoundItemQueue.remove(0);
			currentElement = 0;
			return true;
		} else {
			// There are no remaining Found Items to return.
			return false;
		}
	}
}
