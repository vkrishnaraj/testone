package com.bagnet.nettracer.tracing.utils.lock;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Locale;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;
import org.jboss.cache.CacheStatus;
import org.jboss.cache.Fqn;

import javax.naming.InitialContext;

import org.infinispan.lifecycle.ComponentStatus;
import org.infinispan.manager.CacheContainer;
import org.infinispan.Cache;

import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class CacheLockFile implements LockFile{

	public static final String CLUSTER_NAME = "java:jboss/infinispan/CacheLockFileContainer";
	
	public static final int INCIDENT_ID = 0;
	public static final int LOAD_TIMESTAMP = 1;
	public static final int AGENT_ID = 2;
	public static final int AGENT_USERNAME = 3;
	public static final int AGENT_FIRSTNAME = 4;
	public static final int AGENT_LASTNAME = 5;
	public static final int AGENT_STATION = 6;
	
	
	private static MessageResources messages = null;
	
	static {
		try {
			messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		} catch (Exception e) {
			// Ignore
		}
	}
	
	public CacheLockFile(){
		
	}
	
	@Override
	public synchronized boolean lockIncident(Agent agent, Incident inc) {
		try{
			String cacheName = PropertyBMO.getValue(PropertyBMO.LOCK_CACHE_CLUSTER);
			if(cacheName == null || cacheName.trim().length() == 0){
				//cache disabled
				return false;
			}	
			
		if(agent == null || inc == null || inc.getIncident_ID() == null){
			return false;
		}
		
		InitialContext ic = new InitialContext();
		CacheContainer cc = (CacheContainer) ic.lookup(CLUSTER_NAME);
		
		Cache<Object, Object> myCache = null;
		Map<Integer, Object[]> incidentMap = null;
		try {
			myCache = cc.getCache(cacheName);

			if(myCache.getStatus() != ComponentStatus.RUNNING){
				myCache.start();
			}
			incidentMap = (Map<Integer, Object[]>) myCache.get(inc.getIncident_ID());
			if(incidentMap == null){
				incidentMap = new HashMap<Integer,Object[]>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Object [] payload = {inc.getIncident_ID(),
				  new Date(),
				  agent.getAgent_ID(),
				  agent.getUsername(),
				  agent.getFirstname(),
				  //By US request, to compile with EU privacy policy, only include first initial of last name
				  (agent.getLastname()!=null && agent.getLastname().trim().length()>0?
						  agent.getLastname().trim().substring(0,1):""),
				  agent.getStation().getStationcode()};
		
		incidentMap.put(agent.getAgent_ID(), payload);
		myCache.put(inc.getIncident_ID(), incidentMap);
		
		return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public List<Object[]> getIncidentAccessLogs(String incidentId) {
		try{
			String cacheName = PropertyBMO.getValue(PropertyBMO.LOCK_CACHE_CLUSTER);
			if(cacheName == null || cacheName.trim().length() == 0){
				//cache disabled
				return null;
			}
			InitialContext ic = new InitialContext();
			CacheContainer cc = (CacheContainer) ic.lookup(CLUSTER_NAME);
			
		Map<Integer, Object[]> incidentMap = null;
		Cache<Object, Object> myCache = null;
		try {
			myCache = cc.getCache(cacheName);
			if(myCache.getStatus() != ComponentStatus.RUNNING){
				myCache.start();
			}
			incidentMap = (Map<Integer, Object[]>) myCache.get(incidentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		Date now = new Date();
		long lock_interval = getLockInterval();
		if(incidentMap != null){
			for(Object[] t:incidentMap.values()){
				if(now.getTime() - ((Date)t[LOAD_TIMESTAMP]).getTime() < lock_interval){
					list.add(t);
				}
			}
		}
		return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public synchronized void cleanIncidentAccessLogs() {
//		if(new Date().getTime() - lastCleaned.getTime() > 600000){//10 mins
//			Date now = TracerDateTime.getGMTDate();
//			long lock_interval = getLockInterval();
//			for(String incidentId: cache.keySet()){
//				for(Integer agentId:cache.get(incidentId).keySet()){
//					if(now.getTime() - cache.get(incidentId).get(agentId).getLoadTimestamp().getTime() > lock_interval){
//						cache.get(incidentId).remove(agentId);
//					}
//				}
//				if(cache.get(incidentId).isEmpty()){
//					cache.remove(incidentId);
//				}
//			}
//			lastCleaned = new Date();
//		}
	}

	@Override
	public List<ActionMessage> getLockActionMessages(String incidentId, Agent agent){
		List<Object[]> logs = getIncidentAccessLogs(incidentId);
		if(logs == null){
			return null;
		}
		ArrayList<ActionMessage> ret = new ArrayList<ActionMessage>();
		Date now = new Date();
		for(Object[] log:logs){
			if(agent.getAgent_ID() != (Integer)log[AGENT_ID]){
				long diff = ((now.getTime() - ((Date)log[LOAD_TIMESTAMP]).getTime())/60000) + 1;//+1 to round up to the nearest minute
				String[] vars = {messages.getMessage(new Locale(agent.getCurrentlocale()), "incident"),
						log[AGENT_FIRSTNAME] + " " + log[AGENT_LASTNAME].toString().substring(0,1) +" (" + (String)log[AGENT_USERNAME] + ")",
						(String)log[AGENT_STATION],
						""+diff};
				ActionMessage toAdd = new ActionMessage("error.incident.lock", vars);
				ret.add(toAdd);
			}
		}
		return ret;
	}
	
	private static long getLockInterval(){
		Company c = CompanyBMO.getCompany(TracerProperties.get("wt.company.code"));
		int lock_ms = 0;
		if(c != null && c.getVariable() != null){
			lock_ms = c.getVariable().getIncident_lock_mins() * 60000;
		} else {
			lock_ms = 15 * 60000;//default to 15 mins
		}
		return lock_ms;
	}
}
