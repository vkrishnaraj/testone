package com.bagnet.nettracer.tracing.utils.general;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.logger.GeneralForwardLog;
import com.bagnet.nettracer.tracing.db.logger.GeneralLog;
import com.bagnet.nettracer.tracing.db.logger.GeneralLFLog;
import com.bagnet.nettracer.tracing.db.logger.GeneralPcnLog;
import com.bagnet.nettracer.tracing.db.logger.GeneralScanQueryLog;
import com.bagnet.nettracer.tracing.db.logger.GeneralTelexLog;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public class Logger {
	
	
	private static boolean writeLog(GeneralLog log){
		Session sess = null;
		Transaction t = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			sess.saveOrUpdate(log);
			t.commit();
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			try {
				t.rollback();
			} catch (Exception ex) {
				// Fails
				ex.printStackTrace();
			}
			return false;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static boolean log(GeneralLog log, String refId, String desc, long elapse){
		log.setEntrydate(DateUtils.convertToGMTDate(new Date()));
		log.setDescription(desc);
		log.setRefId(refId);
		log.setElapseTime(elapse);
		return writeLog(log);
	}
	
	public static boolean logGeneral(String refId, String desc, long elapse){
		GeneralLog log = new GeneralLog();
		return log(log, refId, desc, elapse);
	}
	
	public static boolean logLF(String refId, String desc, long elapse){
		GeneralLFLog log = new GeneralLFLog();
		return log(log, refId, desc, elapse);
	}
	
	public static boolean logPcn(String refId, String desc, Date starttime){
		GeneralPcnLog log = new GeneralPcnLog();
		Date endtime = new Date();
		return log(log, refId, desc, starttime!=null?endtime.getTime()-starttime.getTime():0);
	}
	
	public static boolean logTelex(String refId, String desc, Date starttime){
		return logTelex(refId, desc, starttime, new Date());
	}
	
	public static boolean logTelex(String refId, String desc, Date starttime, Date endtime){
		GeneralTelexLog log = new GeneralTelexLog();
		return log(log, refId, desc, starttime!=null?endtime.getTime()-starttime.getTime():0);
	}
	
	public static boolean logForward(String refId, String desc, Date starttime){
		GeneralForwardLog log = new GeneralForwardLog();
		Date endtime = new Date();
		return log(log, refId, desc, starttime!=null?endtime.getTime()-starttime.getTime():0);
	}
	
	public static boolean logScanQuery(String refId, String desc, long elapse){
		GeneralScanQueryLog log = new GeneralScanQueryLog();
		return log(log, refId, desc, elapse);
	}
}
