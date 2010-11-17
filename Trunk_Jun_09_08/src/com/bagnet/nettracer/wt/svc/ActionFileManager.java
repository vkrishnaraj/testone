package com.bagnet.nettracer.wt.svc;

import java.util.List;

import com.bagnet.nettracer.exceptions.WorldTracerDisabledException;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.db.wt.ActionFileStation;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerLockException;
import com.bagnet.nettracer.wt.connector.CaptchaException;
import com.bagnet.nettracer.wt.connector.WebServiceDto;

public interface ActionFileManager {

	ActionFileStation getCounts(String companyCode, String wtStation, Agent user, WebServiceDto dto) throws WorldTracerDisabledException, CaptchaException, WorldTracerLockException, WorldTracerException;
	
	List<Worldtracer_Actionfiles> getSummary(String companyCode, String wtStation, ActionFileType category, String seq, int day, Agent user, WebServiceDto dto) throws WorldTracerException, CaptchaException, WorldTracerDisabledException;

	boolean eraseActionFile(String companyCode, String wtStation,
			ActionFileType category, String seq, int day, int fileNum, Agent user, WebServiceDto dto)
			throws WorldTracerException, CaptchaException, WorldTracerDisabledException;

	void updateDetails(String companyCode, String wtStation,
			ActionFileType category, String seq, int day, int fileNum, Agent user, WebServiceDto dto)
			throws WorldTracerException, CaptchaException;
}
