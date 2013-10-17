package com.bagnet.nettracer.tracing.dao;

import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;

public interface IncidentActivityDAO {
	public IncidentActivity load(long incidentActivityId);
	public long save(IncidentActivity incidentActivity);
	public boolean update(IncidentActivity incidentActivity);
	public boolean delete(long incidentActivityId);
}
