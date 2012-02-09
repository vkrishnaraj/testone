package com.bagnet.clients.defaul;

import java.util.List;

import org.hibernate.Session;

import aero.nettracer.serviceprovider.wt_1_0.common.ForwardMessage;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardOhd;

import com.bagnet.nettracer.integrations.events.BeornDTO;
import com.bagnet.nettracer.integrations.events.ClientEventHandler;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.ProactiveNotification;

public class ClientEventHandlerImpl implements ClientEventHandler {

	@Override
	public void doEventOnBeornWS(BeornDTO dto) {
		// Do nothing
	}

	@Override
	public void doPcn(OHD_Log ohd_log) {
		// Do nothing
		
	}

	@Override
	public void doPcn(OHD_Log ohd_log, boolean async){
		// Do nothing
		
	}
	
	@Override
	public void printPcn(String address, ProactiveNotification pcn) {
		// Do nothing
		
	}

	@Override
	public void doEventOnForward(List<OHD_Log> logs) {
		// Do nothing
		
	}

	@Override
	public void doEventOnForward(ForwardMessage fw) {
		// Do nothing
		
	}

	@Override
	public void doEventOnForward(ForwardOhd fw) {
		// Do nothing
		
	}

	@Override
	public Incident sendCrm(String i, Session sess) {
		//  Do nothing
		return null;
	}


}
