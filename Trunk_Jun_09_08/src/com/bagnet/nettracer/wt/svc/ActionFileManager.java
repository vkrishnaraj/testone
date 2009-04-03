package com.bagnet.nettracer.wt.svc;

import java.util.List;

import com.bagnet.nettracer.exceptions.WorldTracerDisabledException;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.db.wt.ActionFileStation;
import com.bagnet.nettracer.wt.WorldTracerException;

public interface ActionFileManager {

	ActionFileStation getCounts(String companyCode, String wtStation) throws WorldTracerDisabledException, WorldTracerException;
	
	List<Worldtracer_Actionfiles> getSummary(String companyCode, String wtStation, ActionFileType category, int day);
	
	Worldtracer_Actionfiles getDetails(String companyCode, String wtStation, ActionFileType category, int day, int fileNum);
	
	boolean eraseActionFile(String companyCode, String wtStation, ActionFileType category, int day, int fileNum);

}
