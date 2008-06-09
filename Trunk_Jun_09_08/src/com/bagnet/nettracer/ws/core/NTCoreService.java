package com.bagnet.nettracer.ws.core;

import com.bagnet.nettracer.ws.core.pojo.WS_Incident;
import com.bagnet.nettracer.ws.core.pojo.WS_OHD;


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
	 * return array of ohds based on company specific variable, 
	 * 
	 * this method is used specifically for world tracer to send worldtracer an array of ohds 
	 * for tracing on other airlines
	 * @param companycode
	 * @return
	 */
	public WS_OHD[] getOHDsForWT(String companycode, String session_id) {
		/*
		 	WSCoreOHDUtil coreutil = new WSCoreOHDUtil();
    	return coreutil.getOHDsForWT(getOHDsForWT);
		*/
		WS_OHD[] wos = null;
		return wos;
	}
	
	/**
	 * return a single ohd based on ohd number
	 * @param ohd_id
	 * @return
	 */
	public WS_OHD getOHD(String ohd_id, String session_id) {
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
	public WS_Incident getIncident(String incident_id, String session_id, String inc_type) {
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
	public String insertIncident(WS_Incident si) {
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
	public String insertOHD(WS_OHD so) {
		/*
    	WSCoreIncidentUtil coreutil = new WSCoreIncidentUtil();
    	return coreutil.insertOHD(insertOHD);
		*/
		return null;
	}
	
}
