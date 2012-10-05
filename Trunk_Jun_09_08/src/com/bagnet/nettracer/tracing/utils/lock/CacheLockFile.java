package com.bagnet.nettracer.tracing.utils.lock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;
import org.jboss.cache.Cache;
import org.jboss.cache.CacheManager;
import org.jboss.cache.CacheStatus;
import org.jboss.cache.Fqn;
import org.jboss.ha.framework.server.CacheManagerLocator;

import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class CacheLockFile implements LockFile{

	public static final String CLUSTER_NAME = "lockfile-cache";
	
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
		
		if(agent == null || inc == null){
			return false;
		}
		
		Fqn fqn = Fqn.fromString( CacheLockFile.class.getName()+"/"+inc.getIncident_ID() );
		CacheManager cacheManager = CacheManagerLocator.getCacheManagerLocator().getCacheManager( null );

		Cache<Object, Object> myCache = null;
		try {
			myCache = cacheManager.getCache( PropertyBMO.getValue(PropertyBMO.LOCK_CACHE_CLUSTER), true );
			if(myCache.getCacheStatus() != CacheStatus.STARTED){
				myCache.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Object [] payload = {inc.getIncident_ID(),
				  TracerDateTime.getGMTDate(),
				  agent.getAgent_ID(),
				  agent.getUsername(),
				  agent.getFirstname(),
				  agent.getLastname(),
				  agent.getStation().getStationcode()};
		myCache.put(fqn, agent.getAgent_ID(), payload);
		return true;
	}
	
	@Override
	public List<Object[]> getIncidentAccessLogs(String incidentId) {
		Fqn fqn = Fqn.fromString( CacheLockFile.class.getName()+"/"+incidentId );
		CacheManager cacheManager = CacheManagerLocator.getCacheManagerLocator().getCacheManager( null );
		Map<Object,Object> incidentMap = null;
		Cache<Object, Object> myCache = null;
		try {
			myCache = cacheManager.getCache( PropertyBMO.getValue(PropertyBMO.LOCK_CACHE_CLUSTER), true );
			if(myCache.getCacheStatus() != CacheStatus.STARTED){
				myCache.start();
			}
			incidentMap = myCache.getData(fqn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		Date now = TracerDateTime.getGMTDate();
		long lock_interval = getLockInterval();
		if(incidentMap != null){
			for(Object log:incidentMap.values()){
				Object[]t = (Object[])log;
				if(now.getTime() - ((Date)t[LOAD_TIMESTAMP]).getTime() < lock_interval){
					list.add(t);
				}
			}
		}
		return list;
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
		for(Object[] log:logs){
			if(agent.getAgent_ID() != (Integer)log[AGENT_ID]){
				DateFormat df = new SimpleDateFormat(TracingConstants.DB_TIMEFORMAT);
//				df.setTimeZone(TimeZone.getTimeZone("GMT"));
				Date completedate = DateUtils.convertToDate(df.format(((Date)log[LOAD_TIMESTAMP])), TracingConstants.DB_TIMEFORMAT, null);
				String d = DateUtils.formatDate(completedate, agent.getTimeformat().getFormat(), null, TimeZone.getTimeZone(agent.getCurrenttimezone()));
				String[] vars = {messages.getMessage(new Locale(agent.getCurrentlocale()), "incident"),
						log[AGENT_FIRSTNAME] + " " + log[AGENT_LASTNAME] +" (" + (String)log[AGENT_USERNAME] + ")",
						(String)log[AGENT_STATION],
						d};
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
