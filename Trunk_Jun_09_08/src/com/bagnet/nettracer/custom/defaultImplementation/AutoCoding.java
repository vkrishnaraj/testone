package com.bagnet.nettracer.custom.defaultImplementation;

import com.bagnet.nettracer.custom.abstractTypes.AbstractAutoCoding;
import com.bagnet.nettracer.custom.datatypes.AutoCodeResult;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.forms.IncidentForm;

public class AutoCoding implements AbstractAutoCoding {

	public AutoCodeResult autoCodeIncident(Incident incident,
			IncidentForm incidentForm) {
		return null;
	}
}
