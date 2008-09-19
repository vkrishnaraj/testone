package com.bagnet.nettracer.cronjob.tracing;

public class PassiveTracingWrapper {
	
	public PassiveTracingWrapper(String companyCode, String instanceName) {
		PassiveTrace.startPassiveTracing(companyCode, instanceName);
	}
}
