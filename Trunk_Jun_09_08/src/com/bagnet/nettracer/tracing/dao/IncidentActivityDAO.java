package com.bagnet.nettracer.tracing.dao;

import java.util.List;

import com.bagnet.nettracer.tracing.db.communications.Activity;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;

public interface IncidentActivityDAO {
	public IncidentActivity load(long incidentActivityId);
	public long save(IncidentActivity incidentActivity);
	public boolean update(IncidentActivity incidentActivity);
	public boolean delete(long incidentActivityId);
	
	public List<Activity> getActivities();
	public Activity getActivity(String code);
}
