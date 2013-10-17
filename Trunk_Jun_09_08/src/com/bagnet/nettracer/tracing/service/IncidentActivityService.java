package com.bagnet.nettracer.tracing.service;

import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;

/**
 * The IncidentActivityService interface defines methods which provide database and maintenance operations 
 * to the user in regards to activities related to Incidents, Claims, Found Items, etc... 
 * @author Mike
 *
 */
public interface IncidentActivityService {

	public IncidentActivity load(long incidentActivityId);
	public long save(IncidentActivity incidentActivity);
	public boolean update(IncidentActivity incidentActivity);
	public boolean delete(long incidentActivityId);
	
}
