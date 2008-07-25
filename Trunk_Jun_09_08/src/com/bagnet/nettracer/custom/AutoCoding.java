package com.bagnet.nettracer.custom;

import com.bagnet.nettracer.custom.abstractTypes.AbstractAutoCoding;
import com.bagnet.nettracer.custom.abstractTypes.IntegrationType;
import com.bagnet.nettracer.custom.datatypes.AutoCodeResult;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.forms.IncidentForm;

public class AutoCoding extends IntegrationType {

	private static AbstractAutoCoding integrationPoint = null;
	
	static {
		integrationName = "AutoCoding";
		
		try {
			Class specificInterface = Class.forName(packageName + "." + airlineCode.toLowerCase() + "." + integrationName);
			integrationPoint = (AbstractAutoCoding) specificInterface.newInstance();
			implementationObject = integrationPoint;
		} catch (ClassNotFoundException e) {
			try {
				Class specificInterface = Class.forName(packageName + ".default" + "." + integrationName);
				integrationPoint = (AbstractAutoCoding) specificInterface.newInstance();
				implementationObject = integrationPoint;
			} catch (Exception ex) {
				e.printStackTrace();
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static AutoCodeResult autoCodeIncident(Incident incident, IncidentForm incidentForm) {
		return integrationPoint.autoCodeIncident(incident, incidentForm);
	}
}
