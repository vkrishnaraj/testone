package com.bagnet.nettracer.tracing.utils.lf;

import com.bagnet.nettracer.tracing.history.FoundHistoryObject;

public class TraceHandler {
	public static void trace(FoundHistoryObject f){
		f.setHasTraceResults(Math.random() > 0.5);
	}
}
