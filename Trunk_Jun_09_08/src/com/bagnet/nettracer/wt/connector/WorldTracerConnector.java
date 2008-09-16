package com.bagnet.nettracer.wt.connector;

import java.util.List;
import java.util.Map;

import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.svc.WorldTracerService;
import com.bagnet.nettracer.wt.svc.WorldTracerService.WorldTracerField;

public interface WorldTracerConnector {

	String insertIncident(Map<WorldTracerService.WorldTracerField, List<String>> fieldMap, String companyCode, String stationCode)
			throws WorldTracerException;

	String insertOhd(Map<WorldTracerService.WorldTracerField, List<String>> fieldMap, String companyCode, String stationCode)
			throws WorldTracerException;

	String closeIncident(Map<WorldTracerService.WorldTracerField, List<String>> fieldMap, String wtId, String stationCode)
			throws WorldTracerException;

	String closeOhd(Map<WorldTracerService.WorldTracerField, List<String>> fieldMap, String wt_id, String wt_stationcode)
			throws WorldTracerException;

	String sendFwd(Map<WorldTracerService.WorldTracerField, List<String>> fieldMap, String stationCode, String companyCode) throws WorldTracerException;

	void eraseActionFile(String station_id, String companyCode, ActionFileType area, int day, int itemNum)
			throws WorldTracerException;

	List<Worldtracer_Actionfiles> getActionFiles(String airline, String station, ActionFileType actionFileType, int day) throws WorldTracerException;
	
	Worldtracer_Actionfiles getActionFile(String airline, String station, ActionFileType actionFileType, int day, int itemNum) throws WorldTracerException;

	String findAHL(String wt_id) throws WorldTracerException;

	String findOHD(String wt_id) throws WorldTracerException;
	
	void suspendOHD(String wt_id, String agent) throws WorldTracerException;
	
	void suspendAHL(String wt_id, String agent) throws WorldTracerException;
	
	void reinstateOHD(String wt_id, String agent) throws WorldTracerException;
	
	void reinstateAHL(String wt_id, String agent) throws WorldTracerException;

	String forwardOhd(Map<WorldTracerField, List<String>> fieldMap, String ohd_id, String ahl_id) throws WorldTracerException ;

	String amendAhl(Map<WorldTracerField, List<String>> fieldMap, String wt_ahl_id) throws WorldTracerException;

	String amendOhd(Map<WorldTracerField, List<String>> fieldMap, String wt_ohd_id) throws WorldTracerException;

	String requestOhd(String wt_ohd_id, String wt_ahl_id, Map<WorldTracerField, List<String>> fieldMap) throws WorldTracerException;

}
