package com.bagnet.nettracer.cronjob.tracing;

public class PassiveTracingWrapper {
	
	String companyCode;
	String instanceName;
	
	public PassiveTracingWrapper(String companyCode, String instanceName) {
		this.companyCode = companyCode;
		this.instanceName = instanceName;
	}
	
	public void startTracing() {
		PassiveTrace.startPassiveTracing(companyCode, instanceName);
	}
}
