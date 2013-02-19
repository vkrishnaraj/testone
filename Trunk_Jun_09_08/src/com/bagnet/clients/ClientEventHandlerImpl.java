package com.bagnet.clients;

import java.util.List;

import org.hibernate.Session;

import aero.nettracer.serviceprovider.wt_1_0.common.ForwardMessage;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardOhd;

import com.bagnet.nettracer.datasources.ScannerDataSource;
import com.bagnet.nettracer.integrations.events.BeornDTO;
import com.bagnet.nettracer.integrations.events.ClientEventHandler;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.ProactiveNotification;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class ClientEventHandlerImpl implements ClientEventHandler {

	private ClientEventHandler target=new com.bagnet.clients.defaul.ClientEventHandlerImpl();
	

	public ClientEventHandlerImpl() {
		String companyCode = TracerProperties.get("wt.company.code");
		String path = TracerProperties.get(companyCode, "event.class.path");
		try {
			Class cls = Class.forName("com.bagnet.clients." + path
					+ ".ClientEventHandlerImpl");
			ClientEventHandler res = (ClientEventHandler) cls.newInstance();
			target = res;
		} catch (Exception x) {

		}

	}


	@Override
	public void doEventOnForward(List<OHD_Log> logs) {
		target.doEventOnForward(logs);
		
	}


	@Override
	public void doEventOnBeornWS(BeornDTO map) {
		target.doEventOnBeornWS(map);
		
	}


	@Override
	public void doPcn(OHD_Log ohd_log) {
		target.doPcn(ohd_log);
		
	}


	@Override
	public void doPcn(OHD_Log ohd_log, boolean async) {
		target.doPcn(ohd_log, async);
		
	}


	@Override
	public void printPcn(String address, ProactiveNotification pcn) {
		target.printPcn(address, pcn);
		
	}


	@Override
	public void doEventOnForward(ForwardMessage fw) {
		target.doEventOnForward(fw);
		
	}


	@Override
	public void doEventOnForward(ForwardOhd fw) {
		target.doEventOnForward(fw);
		
	}


	@Override
	public Incident sendCrm(String i, Session sess) {
		return target.sendCrm(i, sess);
	}


}
