package com.bagnet.nettracer.tracing.bmo;

import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.cronjob.wt.WorldTracerQueueWorker;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.SpecialFlag;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

public class SpecialFlagBMO {

	private static Logger logger = Logger.getLogger(WorldTracerQueueWorker.class);
	
	
	
	public static int getSpecialFlagCount(String keyStr) {
		return getSpecialFlagCount(keyStr, false);
	}
	
	public static int getSpecialFlagCount(String keyStr, boolean dirtyRead) {
		Session sess = null;
		int result = -1;
		try {
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}
			
			Criteria cri = sess.createCriteria(SpecialFlag.class).add(
					Expression.eq("keyStr", keyStr));
			SpecialFlag myCaptchaFlag = (SpecialFlag) cri.list().get(0);
			if (myCaptchaFlag != null) {
				result = Integer.parseInt(myCaptchaFlag.getValueStr()) ;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
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
	
	public static void setSpecialFlagResetTimestampByCron(String keyStr) {
		Session sess = null;
		int result = 0;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String sql = "update special_flag " +
					"set valueStr=:newValue, flagResetTimestamp=:date " +
					"where valueStr=:oldValue and keyStr=:keyStr and flagResetTimestamp <= :dateMinus1";

			SQLQuery q = sess.createSQLQuery(sql);
			q.setString("newValue", "1");
			q.setString("oldValue", "0");
			q.setTimestamp("date", TracerDateTime.getGMTDate());
			Date d = TracerDateTime.getGMTDate();
			int ONE_MINUTE = 1000 * 60 * 1;
			
			d.setTime(d.getTime() - ONE_MINUTE);
			
			q.setTimestamp("dateMinus1", d);
			q.setString("keyStr", keyStr);
			logger.error("Time: " + d);
			logger.error("Query: " + q.getQueryString());
			t = sess.beginTransaction();
			result = q.executeUpdate();
			t.commit();
			
			
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			e.printStackTrace();
			result = -1;
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
	
//	public static boolean setSpecialFlagResetTimestampByAgentOld(String keyStr) {
//		boolean flag=true;
//		
//		// update the special_flag table for captcha, where valueStr is not zero,
//		// setting valueStr to one, and flagResetTimestamp to now
//		SpecialFlag mySpecialFlag = getSpecialFlagByKey(keyStr);
//		if(mySpecialFlag != null) {
//			String myValueStr = mySpecialFlag.getValueStr();
//			if( !myValueStr.equals("0") ) {
//				Session session = null;
//				try {
//					session=HibernateWrapper.getSession().openSession();
//					Transaction tx=session.beginTransaction();
//					mySpecialFlag.setValueStr("0");
//					//set the flagResetTimestamp
//					java.util.Date myDate = TracerDateTime.getGMTDate();
//					mySpecialFlag.setFlagResetTimestamp(myDate);
//					session.update(mySpecialFlag);
//					tx.commit();
//					session.close();
//				} catch (HibernateException e) {
//					flag=false;
//					e.printStackTrace();
//				} finally {
//					if (session != null) {
//						try {
//							session.close();
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				}		
//			}
//		}
//			
//		return flag;
//	}
//	
//	private static SpecialFlag getSpecialFlagByKey(String keyStr) {
//		SpecialFlag result = null;
//		Session sess = null;
//		try {
//			sess = HibernateWrapper.getSession().openSession();
//			Criteria cri = sess.createCriteria(SpecialFlag.class).add(
//					Expression.eq("keyStr", keyStr));
//			result = (SpecialFlag) cri.list().get(0);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (sess != null) {
//				try {
//					sess.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}		
//		
//		return result;
//	}
//	
	public static int setSpecialFlagResetTimestampByAgent(String keyStr) {
		Session sess = null;
		int result = 0;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String sql = "update special_flag " +
					"set valueStr=:value, flagResetTimestamp=:date " +
					"where valueStr!=:value and keyStr=:keyStr";

			SQLQuery q = sess.createSQLQuery(sql);
			q.setString("value", "0");
			q.setTimestamp("date", TracerDateTime.getGMTDate());
			q.setString("keyStr", keyStr);
			
			t = sess.beginTransaction();
			result = q.executeUpdate();
			t.commit();
			
			
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			e.printStackTrace();
			result = -1;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
}
