package com.bagnet.nettracer.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bagnet.nettracer.tracing.db.datafeed.DataFeedLog;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataFeedTx {
	DataFeedLog.DataType type();
}
