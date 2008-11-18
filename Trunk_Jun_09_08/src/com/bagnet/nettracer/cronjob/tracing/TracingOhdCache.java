package com.bagnet.nettracer.cronjob.tracing;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.bagnet.nettracer.tracing.db.TraceOHD;


public class TracingOhdCache{
	
	private static final int MAX_OHD_CACHE = 5000;
	
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
			ohd = (TraceOHD) sess.get(TraceOHD.class, ohdId);
			if (!stopCaching || containsKey) {
				ohdMap.put(ohdId, ohd);
				if (containsKey) {
					logger.info("Reloading OHD into cache: " + ohdId);
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
