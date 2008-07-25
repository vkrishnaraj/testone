package com.bagnet.nettracer.custom.us;

import com.bagnet.nettracer.custom.abstractTypes.AbstractAutoCoding;
import com.bagnet.nettracer.custom.datatypes.AutoCodeResult;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.forms.IncidentForm;



public class AutoCoding implements AbstractAutoCoding {

	public AutoCodeResult autoCodeIncident(Incident incident,
			IncidentForm form) {
		
		// If was previously closed, exit without modification.
 		
		// Rule 1: If self disclosed, use those values.
		if (form.getLoss_code() != 0 && form.getFaultstation_id() != 0) {
			return new AutoCodeResult(incident, form.getLoss_code(), form.getFaultstation_id(), null);
		}

		/* Rule 2: Check for RC (comments on loss) non-compliance - This is a free-flow
		 * Per Beorn this is the flight & date on which the bag was found. 
		 * Currently we do not store this information unless it is the last segment
		 * In the bag's itinerary.  
		 * 
		 * TODO What should we do?
		 * 
		 * FS: L/D Create Station
		 * RL: 16
		 */
		
		if (true) {
			
		}
		
		/*
		 *  Rule 3: Check to see if there is an OHD tag number match prior to L/D time.
		 *  Check to see if the associated OHD create time occurred during the 24 hours prior to
		 *  L/D create time.
		 *  
		 *  FS: Create Station
		 *  RL: 66
		 */
		
		if (true) {
			
		}
		
		/*
		 * Rule 4: Check to see if RC matches last flight in passenger itinerary.
		 * If the matched OHD last itinerary segment matches the last flight in the passenger
		 * itinerary then  
		 * 
		 * FS: Create Station
		 * RL: 38
		 */

		if (true) {
			
		}
		
		
		// If problem occurs, throw errors of type:
			// Required values not submitted

		
		
		return null;
	}
	
//	private boolean validNotNullInt(String value) {
//		try {
//			int a = new Integer(value).intValue();
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}
	
}
