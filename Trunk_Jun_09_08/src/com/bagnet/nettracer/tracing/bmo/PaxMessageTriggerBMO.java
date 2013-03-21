package com.bagnet.nettracer.tracing.bmo;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.PaxMessageTrigger;



public class PaxMessageTriggerBMO {
	
	private static Logger logger = Logger.getLogger(PaxMessageTriggerBMO.class);
	
	private static Session session;

	private static PaxMessageTrigger getPaxMessageTriggerByTriggerKey(String triggerKey) {
		PaxMessageTrigger result = null;
		boolean sessionNull = (session == null);
		
		try {
			if (sessionNull) {
				session = HibernateWrapper.getSession().openSession();
			}
			result = (PaxMessageTrigger) session.get(PaxMessageTrigger.class, triggerKey);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			if (sessionNull) {
				session.close();
			}
		}
		return result;
	}
	
	public static PaxMessageTrigger getPaxMessageTrigger(String triggerKey, String preferredLanguage) {
		PaxMessageTrigger result = null;
		//PaxMessageTrigger - dao.method(key, language); - preferred language otherwise english
		//if neither, then throw exception called runtime exception
		result = getPaxMessageTriggerByTriggerKey(triggerKey);
		if ((!preferredLanguage.equalsIgnoreCase("en")) && result == null) {
			//convert the key to its english equivalent
			String myTriggerKey = triggerKey.substring(0,(triggerKey.length() - 3));
			myTriggerKey += "_EN";
			result = getPaxMessageTriggerByTriggerKey(myTriggerKey);
		}
		
		return result;
	}
}
