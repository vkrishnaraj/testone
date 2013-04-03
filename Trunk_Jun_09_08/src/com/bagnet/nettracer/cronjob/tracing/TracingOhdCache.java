package com.bagnet.nettracer.cronjob.tracing;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.TraceOHD;


public class TracingOhdCache{
	
	private static final String PROPERTY_MAX_OHD_CACHE = "tracing.maxohd.cache";
	private static final int MAX_OHD_CACHE = Integer.parseInt(PropertyBMO.getValue(PROPERTY_MAX_OHD_CACHE));
	
	private Calendar reCacheDate = null;
	private Logger logger = null;
	private ConcurrentHashMap<String, TraceOHD> ohdMap = new ConcurrentHashMap<String, TraceOHD>(3000);
	private boolean stopCaching = false;
	
	public TracingOhdCache(String loggerName) {
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
	
	public void reset(List<String> validOhdList) {
		ConcurrentHashMap<String, TraceOHD> newMap = new ConcurrentHashMap<String, TraceOHD>(3000);
		
		for (String ohdId: validOhdList) {
			if (ohdMap.containsKey(ohdId)) {
				newMap.put(ohdId, ohdMap.get(ohdId));
			}
		}
		if (stopCaching == true && newMap.size() < MAX_OHD_CACHE) {
			stopCaching = false;
			logger.info("OHD Caching re-enabled...");
		}
		ohdMap = newMap;
	}
	

	public TraceOHD loadOhd(String ohdId, Date lastUpdated, Session sess) {
		boolean containsKey = ohdMap.containsKey(ohdId);
		TraceOHD ohd = null;

		if (containsKey) {
			ohd = ohdMap.get(ohdId);
		}
		
		if (containsKey && ohd.getLastupdated().equals(lastUpdated)) {
			return ohd;
		} else {
			if(sess == null || !sess.isOpen()){
				logger.info("Session is closed, failed to load ohd: " + ohdId);
			}
			int limit = 0;
			do {
				if (limit > 0) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// Do nothing.
					}
				}
				ohd = (TraceOHD) sess.get(TraceOHD.class, ohdId);
				++ limit;
			} while (ohd == null && limit < 3);
			
			sess.evict(ohd);

			if (!stopCaching || containsKey) {
				ohdMap.put(ohdId, ohd);
				if (containsKey) {
					logger.debug("Reloading OHD into cache: " + ohdId);
				}
			}
			
			if (stopCaching == false && ohdMap.size() % 250 == 0) {
				int size = ohdMap.size();
				logger.info("Current OHD Cache Size: " + size);
				if (size >= MAX_OHD_CACHE) {
					stopCaching = true;
					logger.info("Caching capped at " + MAX_OHD_CACHE + ".");
				}
			}
			return ohd;
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
