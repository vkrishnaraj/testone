package com.bagnet.nettracer.ws.core;

import com.bagnet.nettracer.ws.core.pojo.WS_BEORN;
import com.bagnet.nettracer.ws.core.pojo.WS_Incident;
import com.bagnet.nettracer.ws.core.pojo.WS_OHD;
import com.bagnet.nettracer.ws.core.pojo.WS_OhdResponse;
import com.bagnet.nettracer.ws.core.pojo.WS_QOHD;


public class NTCoreService {


	/**
	 * return authenticated session id
	 * @param username
	 * @param password
	 * @param companycode
	 * @return
	 */
	public String authenticate(String username, String password, String companycode) {
		/*
		WSCoreUtil coreutil = new WSCoreUtil();
		return coreutil.authenticate(authenticate);
		*/
		return null;
	}
	
	/**
	 * remove session from webservice table
	 * @param session_id
	 * @return
	 */
	public boolean logoff(String session_id) {
		/*
		WSCoreUtil coreutil = new WSCoreUtil();
	  	return coreutil.logoff(logoff);
		*/
		return true;
	}
		
	/**
	 * return a single ohd based on ohd number
	 * @param ohd_id
	 * @return
	 */
	public WS_OHD getOHD(String session_id, String ohd_id) {
		/*
			WSCoreOHDUtil coreutil = new WSCoreOHDUtil();
    	return coreutil.getOHD(getOHD);
		*/
		WS_OHD so = null;
		return so;
	}
	
	
	/**
	 * return a single incident based on incident number
	 * @param incident_id
	 * @return
	 */
	public WS_Incident getIncident(String session_id, String incident_id, String inc_type) {
		/*
    	WSCoreIncidentUtil coreutil = new WSCoreIncidentUtil();
    	return coreutil.getIncident(getIncident);
		*/
		WS_Incident si = null;
		return si;
	}
	
	/**
	 * insert incident
	 * @param incident_id
	 * @return
	 */
	public String insertIncident(String session_id, WS_Incident si) {
		/*
    	WSCoreIncidentUtil coreutil = new WSCoreIncidentUtil();
    	return coreutil.insertIncident(insertIncident);
		*/
		return null;
	}
	
	/**
	 * insert ohd
	 * @param incident_id
	 * @return
	 */
	public WS_OhdResponse insertOHD(String session_id, WS_OHD si) {
		/*
    	WSCoreOHDUtil coreutil = new WSCoreOHDUtil();
    	return coreutil.insertOHD(insertOHD);
		*/
		WS_OhdResponse so = null;
		return so;
	}
	
	/**
	 * insert ohd
	 * @param incident_id
	 * @return
	 */
	public WS_OhdResponse insertQuickOHD(String session_id, WS_QOHD si) {
		/*
    	return new WSCoreOHDUtil().insertQOHD(insertQuickOHD);
		*/
		WS_OhdResponse so = null;
		return so;
	}
	
	/**
	 * BEORN
	 * @param sf WS_FWD
	 * @return
	 */
	public WS_OhdResponse beornOHD(String session_id, WS_BEORN si) {
		/*
    	WSCoreIncidentUtil coreutil = new WSCoreForwardUtil();
    	return coreutil.forwardOHD(forwardOHD);
		*/
		WS_OhdResponse so = null;
		return so;
	}
}
