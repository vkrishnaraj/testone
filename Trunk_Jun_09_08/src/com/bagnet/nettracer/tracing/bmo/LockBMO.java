package com.bagnet.nettracer.tracing.bmo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.bagnet.nettracer.tracing.db.Incident_Assoc;
import com.bagnet.nettracer.tracing.db.Lock;
import com.bagnet.nettracer.tracing.db.Lock.LockType;

public class LockBMO extends HibernateDaoSupport {
	
	@Transactional
	public Lock createLock(LockType type, String key, Long durationMillis) {
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
		
		Session sess = getSession(false);
		sess.save(tmp);
		return tmp;
	}
	
	@Transactional
	public Lock loadLock(LockType type, String key) {
		if(type == null || key == null) return null;
		
		
		
		Session sess = getSession(false);
		Lock lock = new Lock();
		lock.setLockKey(key);
		lock.setLockType(type);
		List list = sess.createCriteria(Lock.class).add(Example.create(lock)).list();
		if(list != null && list.size() > 0){
			return (Lock)list.get(0);
		}
		
		return null;
	}
	
	@Transactional
	public void releaseLock(Lock lock) {
		Session sess = getSession(false);
		sess.delete(lock);
	}

}
