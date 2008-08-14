package com.bagnet.nettracer.wt;

import java.util.List;

import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;

public interface WorldTracerService {

	String insertIncident(Incident incident) throws WorldTracerException;

	String insertOhd(OHD ohd) throws WorldTracerException;

	String closeIncident(Incident incident) throws WorldTracerException;

	String closeOHD(OHD ohd) throws WorldTracerException;

	void sendFwd(WT_FWD_Log fwd) throws WorldTracerException;

	void eraseActionFile(Worldtracer_Actionfiles waf) throws WorldTracerException;

	List<Worldtracer_Actionfiles> getActionFiles(String airline, String station, ActionFileType actionFileType, int day)
			throws WorldTracerException;

}
