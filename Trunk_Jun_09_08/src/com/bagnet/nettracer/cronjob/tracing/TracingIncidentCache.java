package com.bagnet.nettracer.cronjob.tracing;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.TraceIncident;


public class TracingIncidentCache{
	
	private static final String PROPERTY_MAX_INC_CACHE = "tracing.maxinc.cache";
	private static final int MAX_INC_CACHE = Integer.parseInt(PropertyBMO.getValue(PROPERTY_MAX_INC_CACHE));
	
	private Calendar reCacheDate = null;
	private Logger logger = null;
	private ConcurrentHashMap<String, TraceIncident> incMap = new ConcurrentHashMap<String, TraceIncident>(3000);
	private ConcurrentHashMap<String, TraceIncident> newMap = new ConcurrentHashMap<String, TraceIncident>(3000);
	private boolean stopCaching = false;
	
	public void reset() {
		incMap = newMap;
		newMap = new ConcurrentHashMap<String, TraceIncident>(5000);
		if (stopCaching == true && incMap.size() < MAX_INC_CACHE) {
			stopCaching = false;
			logger.info("Incident Caching re-enabled...");
		}
	}
	
	public TracingIncidentCache(String loggerName) {
		logger = Logger.getLogger(loggerName);
		
		Calendar killCacheDate = new GregorianCalendar();
		Calendar nowDate = new GregorianCalendar();
		killCacheDate.set(Calendar.HOUR_OF_DAY, 3);
		killCacheDate.set(Calendar.MINUTE, 0);
		if (nowDate.after(killCacheDate)) {
			killCacheDate.add(Calendar.DATE, 1);
		}
		reCacheDate = killCacheDate;
	}
	

	public TraceIncident loadIncident(String incidentId, String lastTracedDate, Session sess) {
		TraceIncident tmp = null;
		boolean containsKey = incMap.containsKey(incidentId);
		if (lastTracedDate != null && containsKey) {
			tmp = incMap.get(incidentId);
			newMap.put(incidentId, tmp);
			return tmp;
		} else {
			TraceIncident inc = (TraceIncident) sess.get(TraceIncident.class, incidentId);
			sess.evict(inc);
			if (!stopCaching || containsKey) {
				newMap.put(incidentId, inc);
				if (containsKey) {
					logger.debug("Reloading Incident into cache: " + incidentId);
				}
			}
			
			if (stopCaching == false && newMap.size() % 250 == 0) {
				int size = newMap.size();
				logger.info("Current Incident Cache Size: " + size);
				if (size >= MAX_INC_CACHE) {
					stopCaching = true;
					logger.info("Caching capped at " + MAX_INC_CACHE + ".");
				}
			}
			return inc;
		}
	}


	/**
	 * @return the reCacheDate
	 */
	public Calendar getReCacheDate() {
		return reCacheDate;
	}


	/**
	 * @param reCacheDate the reCacheDate to set
	 */
	public void setReCacheDate(Calendar reCacheDate) {
		this.reCacheDate = reCacheDate;
	}
}
