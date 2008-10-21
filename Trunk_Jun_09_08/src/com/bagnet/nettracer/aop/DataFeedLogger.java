package com.bagnet.nettracer.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

public class DataFeedLogger {

	@Around("PointCuts.inDataFeedService() &&" + "@annotation(tx)")
	public Object logDataFeed(ProceedingJoinPoint pjp, DataFeedTx tx) throws Throwable {
		//TODO
		return null;
	}
}
