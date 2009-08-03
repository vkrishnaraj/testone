package com.bagnet.nettracer.integrations.events;

import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.ProactiveNotification;


public interface ClientEventHandler {

	public void doEventOnBeornWS(BeornDTO map);
	
	public void doPcn(OHD_Log ohd_log);
	
	public void printPcn(String address, ProactiveNotification pcn);
	
}
