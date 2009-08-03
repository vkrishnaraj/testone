package com.bagnet.clients.defaul;

import com.bagnet.nettracer.integrations.events.BeornDTO;
import com.bagnet.nettracer.integrations.events.ClientEventHandler;
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
	public void printPcn(String address, ProactiveNotification pcn) {
		// Do nothing
		
	}


}
