package com.bagnet.nettracer.tracing.bmo;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Lock;
import com.bagnet.nettracer.tracing.db.Lock.LockType;

public class LockBMO {
	
	private static Logger logger = Logger.getLogger(LockBMO.class);
	
	public Lock createLock(LockType type, String key, Long durationMillis, Agent user) {
		if(type == null || key == null) return null;
		
		Lock tmp = new Lock();
		Date createDate = new Date();
		Date expirationDate = new Date();
		if(durationMillis <= 0) {
			durationMillis = Lock.DEFAULT_DURATION;
		}
		expirationDate.setTime(createDate.getTime() + durationMillis);
		
		tmp.setCreateDate(createDate);
		tmp.setExpirationDate(expirationDate);
		tmp.setLockType(type);
		tmp.setLockKey(key);
		if(user!=null){
			tmp.setOwner(String.valueOf(user.getAgent_ID()));
		}
		Session sess = null;
		Transaction tx = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			tx = sess.beginTransaction();
			sess.save(tmp);
			tx.commit();
		} catch (Exception ex) {
			logger.error("LOCK FAILED TO CREATE: " + ex);
			try {
				tx.rollback();
			} catch (Exception etx) {
				etx.printStackTrace();
			}
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
		return tmp;
	}
	
	public Lock createLock(LockType type, String key, Long durationMillis) {
		return createLock(type, key, durationMillis, null);
	}
	
	public Lock loadLock(LockType type, String key) {
		if(type == null || key == null) return null;
		
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Lock lock = new Lock();
			lock.setLockKey(key);
			lock.setLockType(type);
			List list = sess.createCriteria(Lock.class).add(Example.create(lock)).list();
			if(list != null && list.size() > 0){
				return (Lock)list.get(0);
			}
		} catch (Exception ex) {
			logger.error("LOCK FAILED TO LOAD: " + ex);
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
		
		return null;
	}
	
	public void releaseLock(Lock lock) {
		Session sess = null;
		Transaction tx = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			tx = sess.beginTransaction();
			sess.delete(lock);
			tx.commit();
		} catch (Exception ex) {
			logger.error("LOCK FAILED TO DELETE: " + ex);
			try {
				tx.rollback();
			} catch (Exception etx) {
				etx.printStackTrace();
			}
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
	}

}
