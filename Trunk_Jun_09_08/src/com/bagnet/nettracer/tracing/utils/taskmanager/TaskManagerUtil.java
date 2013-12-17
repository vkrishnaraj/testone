package com.bagnet.nettracer.tracing.utils.taskmanager;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.bmo.LockBMO;
import com.bagnet.nettracer.tracing.db.Lock;
import com.bagnet.nettracer.tracing.db.Lock.LockType;
import com.bagnet.nettracer.tracing.db.taskmanager.GeneralTask;
import com.bagnet.nettracer.tracing.db.taskmanager.IncidentActivityTask;
import com.bagnet.nettracer.tracing.db.taskmanager.ItemTraceResultsTask;
import com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;

public abstract class TaskManagerUtil {
	private static Logger logger = Logger.getLogger(HibernateUtils.class);
	
	public static GeneralTask lockTask(GeneralTask task){
		String key = getKey(task);		
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
					lock = lockBmo.createLock(getLockType(task), key, 300000L);
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
				throw new Exception("unable to lock task " + key);
			}
			task.setLock(lock);
			return task;
		} catch (Exception e) {
			return null;
		} 
	}
	
	public static void unlockTaskIncident(Lock lock, MorningDutiesTask task){
		try {
			if(lock != null){
				SpringUtils.getLockBmo().releaseLock(lock);
			} else {
				if(task != null && task.getIncident() != null){
					lock = SpringUtils.getLockBmo().loadLock(getLockType(task), getKey(task));
					if(lock != null){
						SpringUtils.getLockBmo().releaseLock(lock);
					}
				}
			}
		} catch (Exception e) {
			// Ignore any exceptions release the lock.
		}
	}
	
	public static void unlockTaskIncident(Lock lock){
		unlockTaskIncident(lock, null);
	}
	
	private static String getKey(GeneralTask task) {
		String key = null;
		if (task instanceof MorningDutiesTask){
			if(((MorningDutiesTask)task).getIncident() != null && ((MorningDutiesTask)task).getIncident().getIncident_ID() != null
					&& ((MorningDutiesTask)task).getIncident().getIncident_ID().trim().length() > 0){
//				key = ((MorningDutiesTask)task).getKey() + ((MorningDutiesTask)task).getIncident().getIncident_ID();
				key = ((MorningDutiesTask)task).getIncident().getIncident_ID();
			}
		} else if (task instanceof ItemTraceResultsTask) {
			if (((ItemTraceResultsTask) task).getFoundItem() != null && ((ItemTraceResultsTask) task).getFoundItem().getBarcode() != null
					&& !((ItemTraceResultsTask) task).getFoundItem().getBarcode().isEmpty()) {
				key = ((ItemTraceResultsTask) task).getFoundItem().getBarcode();
			}
		} else if (task instanceof IncidentActivityTask) {
			key = String.valueOf(((IncidentActivityTask) task).getTask_id());
		}
		return key;
	}
	
	private static LockType getLockType(GeneralTask task) {
		LockType type = null;
		if (task instanceof MorningDutiesTask) {
			type = LockType.TM_INCIDENT;
		} else if (task instanceof ItemTraceResultsTask) {
			type = LockType.LF_FOUND;
		} else if (task instanceof IncidentActivityTask) {
			type = LockType.INCIDENT_ACTIVITY;
		}
		return type;
	}
	
}
