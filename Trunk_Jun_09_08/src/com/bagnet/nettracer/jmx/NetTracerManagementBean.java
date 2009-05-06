package com.bagnet.nettracer.jmx;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class NetTracerManagementBean {

	public void reloadDatabaseProperties() {
		PropertyBMO.resetCache();
	}
	
	public void reloadTracerFileProperties() {
		TracerProperties.reloadProperties();
	}

}
