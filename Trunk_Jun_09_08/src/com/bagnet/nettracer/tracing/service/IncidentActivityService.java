package com.bagnet.nettracer.tracing.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bagnet.nettracer.tracing.db.communications.Activity;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.dto.OptionDTO;

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
	
	public List<OptionDTO> getActivityOptions();
	public Activity getActivity(String code);
	public void writeOptionsList(List<OptionDTO> options, HttpServletResponse response);
	public boolean activityBelongsToIncident(long incidentActivityId, String incidentId);
}
