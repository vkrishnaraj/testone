package com.bagnet.nettracer.wt.connector;

import java.util.List;
import java.util.Map;

import com.bagnet.nettracer.tracing.db.DeliverCompany;
import com.bagnet.nettracer.tracing.db.Station;
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
	
	/**
	 * 
	 * @param wt_id
	 * @param agent
	 * @throws WorldTracerException
	 */
	void suspendOHD(String wt_id, String agent) throws WorldTracerException;
	
	/**
	 * 
	 * @param wt_id
	 * @param agent
	 * @throws WorldTracerException
	 */
	void suspendAHL(String wt_id, String agent) throws WorldTracerException;
	
	/**
	 * 
	 * @param wt_id
	 * @param agent
	 * @throws WorldTracerException
	 */
	void reinstateOHD(String wt_id, String agent) throws WorldTracerException;

	/**
	 * 
	 * @param wt_id
	 * @param agent
	 * @throws WorldTracerException
	 */
	void reinstateAHL(String wt_id, String agent) throws WorldTracerException;

	/**
	 * 
	 * @param fieldMap
	 * @param ohd_id
	 * @param ahl_id
	 * @return
	 * @throws WorldTracerException
	 */
	String forwardOhd(Map<WorldTracerField, List<String>> fieldMap, String ohd_id, String ahl_id) throws WorldTracerException ;

	/**
	 * 
	 * @param fieldMap
	 * @param wt_ahl_id
	 * @return
	 * @throws WorldTracerException
	 */
	String amendAhl(Map<WorldTracerField, List<String>> fieldMap, String wt_ahl_id) throws WorldTracerException;

	/**
	 * 
	 * @param fieldMap
	 * @param wt_ohd_id
	 * @return
	 * @throws WorldTracerException
	 */
	String amendOhd(Map<WorldTracerField, List<String>> fieldMap, String wt_ohd_id) throws WorldTracerException;

	/**
	 * 
	 * @param wt_ohd_id
	 * @param wt_ahl_id
	 * @param fieldMap
	 * @return
	 * @throws WorldTracerException
	 */
	String requestOhd(String wt_ohd_id, String wt_ahl_id, Map<WorldTracerField, List<String>> fieldMap) throws WorldTracerException;

	/**
	 * Creates a Worldtracer BDO based on the passed in field map and the delivery
	 * company information. Either ahl_id or ohd_id should be non-null (or both) so that the BDO
	 * reference can be placed in the appropriate WT file(s).  WorldTracer delivery company 1
	 * is always used, but the company specific information for the delivercompany is used
	 * in the request.
	 * 
	 * @param fieldMap - WorldTracer fields to be potentially added. DA (Delivery address) is required
	 * @param ahl_id - WT AHL reference to place bdo
	 * @param ohd_id - WT OHD reference to place BDO
	 * @param delivercompany - delivery company info (name, contacts etc)
	 * @return
	 * @throws WorldTracerException
	 */
	String createBdo(Map<WorldTracerField, List<String>> fieldMap, String ahl_id, String ohd_id,
			DeliverCompany delivercompany, Station station) throws WorldTracerException;

}
