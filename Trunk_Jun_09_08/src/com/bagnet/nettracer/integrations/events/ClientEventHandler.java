package com.bagnet.nettracer.integrations.events;

import java.util.List;

import org.hibernate.Session;

import aero.nettracer.serviceprovider.wt_1_0.common.ForwardMessage;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardOhd;

import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.ProactiveNotification;


public interface ClientEventHandler {
	
	public void doEventOnForward(List<OHD_Log> logs);
	
	public void doEventOnBeornWS(BeornDTO map);
	
	public void doPcn(OHD_Log ohd_log);
	
	public void printPcn(String address, ProactiveNotification pcn);

	public void doEventOnForward(ForwardMessage fw);

	public void doEventOnForward(ForwardOhd fw);
	
	public Incident sendCrm(String i, Session sess);
	
}
