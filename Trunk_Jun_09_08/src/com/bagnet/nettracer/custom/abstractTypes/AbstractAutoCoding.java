package com.bagnet.nettracer.custom.abstractTypes;

import com.bagnet.nettracer.custom.datatypes.AutoCodeResult;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.forms.IncidentForm;

public interface AbstractAutoCoding {

	AutoCodeResult autoCodeIncident(Incident incident, IncidentForm incidentForm);
	
}
