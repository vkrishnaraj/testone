package com.bagnet.nettracer.tracing.bmo;

import java.util.Date;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

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
	public void releaseLock(Lock lock) {
		Session sess = getSession(false);
		sess.delete(lock);
	}

}
