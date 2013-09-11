package com.bagnet.nettracer.tracing.utils.lock;

import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;

public class CacheLockFileThread implements Runnable{
	private static final Logger logger = Logger.getLogger(AsyncCacheLockFile.class);
	
	private ArrayBlockingQueue<QueueItem> queue;
	private LockFile lockFile;


	public CacheLockFileThread(ArrayBlockingQueue<QueueItem> queue, LockFile lockFile){
		this.queue = queue;
		this.lockFile = lockFile;
	}
	
	@Override
	public void run() {
		while(true){
			try{
				QueueItem item = queue.take();
				if(item.getQueueItemType() == AsyncCacheLockFile.LOCK_INCIDENT){
					item.setLockIncidentReturn(lockFile.lockIncident(item.getAgent(),item.getIncident()));
				} else {
					item.setLockActionMessagesReturn(lockFile.getLockActionMessages(item.getIncidentId(), item.getAgent()));
				}
				item.objectNotification();
			} catch (Exception e){
				if(lockFile == null){
					logger.error("LockFile needs to be injected as a paramenter through Spring if implmenting AsyncCacheLockFile.  Please update spring-beans.xml to inject implementing class");
				}
				e.printStackTrace();
			}
		}
	}

}
