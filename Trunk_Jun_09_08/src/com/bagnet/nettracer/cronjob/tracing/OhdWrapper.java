package com.bagnet.nettracer.cronjob.tracing;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.bagnet.nettracer.tracing.db.TraceOHD;


public class OhdWrapper{
	
	public Logger logger = null;
	public ConcurrentHashMap<String, TraceOHD> ohdMap = new ConcurrentHashMap<String, TraceOHD>(3000);
	public boolean stopCaching = false;
	
	public OhdWrapper(String loggerName) {
		logger = Logger.getLogger(loggerName);
	}

	public TraceOHD loadOhd(String ohdId, Session sess) {
		if (ohdMap.containsKey(ohdId)) {
			return ohdMap.get(ohdId);
		} else {
			TraceOHD ohd = (TraceOHD) sess.get(TraceOHD.class, ohdId);
			if (!stopCaching) {
				ohdMap.put(ohdId, ohd);
			}
			
			if (ohdMap.size() % 250 == 0) {
				int size = ohdMap.size();
				logger.info("Current OHD Cache Size: " + size);
				if (size > 5000) {
					stopCaching = true;
					logger.info("Caching capped at 5000.");
				}
			}
			return ohd;
		}
	}
}
