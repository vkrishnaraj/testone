package com.bagnet.nettracer.tracing.utils.taskmanager;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.bmo.LockBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Lock;
import com.bagnet.nettracer.tracing.db.Lock.LockType;
import com.bagnet.nettracer.tracing.db.taskmanager.GeneralTask;
import com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;

public abstract class TaskManagerUtil {
	private static Logger logger = Logger.getLogger(HibernateUtils.class);
	
	public static GeneralTask lockTask(GeneralTask task){
		String key = null;
		if (task instanceof MorningDutiesTask){
			if(((MorningDutiesTask)task).getIncident() != null && ((MorningDutiesTask)task).getIncident().getIncident_ID() != null
					&& ((MorningDutiesTask)task).getIncident().getIncident_ID().trim().length() > 0){
//				key = ((MorningDutiesTask)task).getKey() + ((MorningDutiesTask)task).getIncident().getIncident_ID();
				key = ((MorningDutiesTask)task).getIncident().getIncident_ID();
			}
		}
		if(key == null){
			return null;
		}
		LockBMO lockBmo = SpringUtils.getLockBmo();
		Lock lock = null;
		int i = 0;
		try {
			do {
				i++;
				try {
					lock = lockBmo.createLock(LockType.TM_INCIDENT, key, 300000L);
				} catch (Exception ex) {
					logger.info("GeneralTask for " + key
							+ " alreaedy locked, waiting..");
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						throw new Exception(
								"unable to acquire lock " + key, e);
					}
				}
			} while (lock == null && i < 2);
			if(lock == null) {
				throw new Exception("unable to lock incident " + key);
			}
			task.setLock(lock);
			return task;
		} catch (Exception e) {
			return null;
		} 
	}
	
	public static void unlockTaskIncident(Lock lock, MorningDutiesTask task){
		if(lock != null){
			SpringUtils.getLockBmo().releaseLock(lock);
		} else {
			if(task != null && task.getIncident() != null){
				lock = SpringUtils.getLockBmo().loadLock(LockType.TM_INCIDENT, task.getIncident().getIncident_ID());
				if(lock != null){
					SpringUtils.getLockBmo().releaseLock(lock);
				}
			}
		}
	}
	
	public static void unlockTaskIncident(Lock lock){
		unlockTaskIncident(lock, null);
	}
	
}
