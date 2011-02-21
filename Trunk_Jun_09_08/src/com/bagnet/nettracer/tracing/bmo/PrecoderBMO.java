package com.bagnet.nettracer.tracing.bmo;

import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Precoder;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.PrecoderResult;

public class PrecoderBMO {
	private static Logger logger = Logger.getLogger(HibernateUtils.class);
	
	public static void insert(String incident_id, PrecoderResult pcr){
		Precoder precoder = new Precoder();
		Incident inc = new Incident();
		inc.setIncident_ID(incident_id);
		
		precoder.setIncident(inc);
		precoder.setCreatedate(new Date());
		precoder.setFaultStation(pcr.getFaultStation());
		precoder.setLosscode(pcr.getLossCode());
		precoder.setRemark(pcr.getRemark());
		
		Session sess = HibernateWrapper.getSession().openSession();
		try{
			HibernateUtils.save(precoder, sess);
		}
		catch (Exception e){
			logger.error("Error Saving: ", e);
			e.printStackTrace();
			precoder = null;
		}
		finally{
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
