package com.bagnet.nettracer.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {
	
	@Pointcut("within(com.bagnet.nettracer.wt.svc..*)")
	public void inWorldTracerService() {
	}

}
