package com.bagnet.nettracer.cronjob.tracing;

import com.bagnet.nettracer.cronjob.tracing.PassiveTrace.PTMode;

public class PassiveTracingWrapper {
	
	String companyCode;
	String instanceName;
	PTMode mode;
	
	public PassiveTracingWrapper(String companyCode, String instanceName, PTMode mode) {
		this.companyCode = companyCode;
		this.instanceName = "Passive Tracer " + instanceName;
		this.mode = mode;
	}
	
	public void startTracing() {
		PassiveTrace.startPassiveTracing(companyCode, instanceName, mode);
	}
}
