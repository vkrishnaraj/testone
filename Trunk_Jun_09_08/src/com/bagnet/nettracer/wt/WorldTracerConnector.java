package com.bagnet.nettracer.wt;

import java.util.List;
import java.util.Map;

import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.wt.DefaultWorldTracerService.WorldTracerField;

public interface WorldTracerConnector {

	String insertIncident(Map<WorldTracerField, List<String>> fieldMap, String companyCode, String stationCode)
			throws WorldTracerException;

	String insertOhd(Map<WorldTracerField, List<String>> fieldMap, String companyCode, String stationCode)
			throws WorldTracerException;

	String closeIncident(Map<WorldTracerField, List<String>> fieldMap, String wtId, String stationCode)
			throws WorldTracerException;

	String closeOhd(Map<WorldTracerField, List<String>> fieldMap, String wt_id, String wt_stationcode)
			throws WorldTracerException;

	void sendFwd(Map<WorldTracerField, List<String>> fieldMap, String wt_CompanyCode) throws WorldTracerException;

	void eraseActionFile(String station_id, String companyCode, ActionFileType area, int day, int itemNum)
			throws WorldTracerException;

	List<Worldtracer_Actionfiles> getActionFiles(String airline, String station, ActionFileType actionFileType, int day) throws WorldTracerException;
	
	Worldtracer_Actionfiles getActionFile(String airline, String station, ActionFileType actionFileType, int day, int itemNum) throws WorldTracerException;

	String findAHL(String parameter) throws WorldTracerException;

	String findOHD(String parameter) throws WorldTracerException;
	

}
