package com.bagnet.nettracer.tracing.utils.lock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.general.AlertEmail;

public class AsyncCacheLockFile implements LockFile{
	
	private static final Logger logger = Logger.getLogger(AsyncCacheLockFile.class);
	
	public static final int MAX_QUEUE_SIZE = 20;
	public static final long MAX_WAIT_TIME = 5000;
	public static final int CONSUMER_THREAD_COUNT = 2;
	public static final int ALERT_INTERVAL = 24 * 60 * 60 * 1000;
	
	public static final int LOCK_INCIDENT = 0;
	public static final int GET_LOCK_MESSAGE = 1;
	
	private static ArrayBlockingQueue<QueueItem> queue;
	private static List<CacheLockFileThread> consumerThreads;
	private LockFile lockFile;

	private static Date lastAlertSent;
	
	public AsyncCacheLockFile(){
	}
	
	/**
	 * This constructor exists to allow unit tests to mock the underlying LockFile implementation.
	 * The application will specify the LockFile implementation via Spring injection.
	 * 
	 * @param lockFile
	 */
	public AsyncCacheLockFile(LockFile lockFile){
		this.lockFile = lockFile;
	}
	
	
	/**
	 * The under lying LockFile implementation must be injected by Spring before initializing the queue and threads.
	 */
	@PostConstruct
	public void init(){
		logger.debug("PostConstruct Invoked");
		if(queue == null){
			queue = new ArrayBlockingQueue<QueueItem>(MAX_QUEUE_SIZE);
		}
		if(consumerThreads == null){
			startConsumerThreads();
		}
		if(lockFile == null){
			logger.error("LockFile needs to be injected through Spring.  Please update spring-beans.xml to inject implementing class");
		}
	}

	private void startConsumerThreads(){
		consumerThreads = new ArrayList<CacheLockFileThread>();
		for(int i = 0; i < CONSUMER_THREAD_COUNT; i++){
			CacheLockFileThread thread = new CacheLockFileThread(queue, lockFile);
			Thread t = new Thread(thread, "AsyncCacheLockThread " + i);
			t.start();
		}
	}

	/**
	 * Checks if Async queue processing cache requests is full.  If so, send alert (only one alert per interval).
	 * 
	 * @return
	 */
	private boolean cacheAvaililble(){
		if(queue != null && queue.size() < MAX_QUEUE_SIZE){
			return true;
		} else {
			if(lastAlertSent == null || (new Date()).getTime() - lastAlertSent.getTime() > ALERT_INTERVAL){
				lastAlertSent = new Date();
				try{
					AlertEmail.sendAlertEmail("AsyncCacheLockFile",
							"AsyncCacheLockFile has exceed maxium queue size<br/>" +
									"queue size:   " + MAX_QUEUE_SIZE + "<br/>" +
									"Instance Label " + TracerProperties.getInstanceLabel() + 
									"Alert interval " + ALERT_INTERVAL + "<br/>"
							);
				} catch (Exception e){
					e.printStackTrace();
				} catch (Throwable e){
					e.printStackTrace();
				}
			}
			return false;
		}
	}
	
	/**
	 * Creates a QueueItem to invoke the LockFile.lockIncident method.  If the consumer queue does not invoke the method
	 * within the MAX_WAIT_TIME, return false.
	 */
	@Override
	public  boolean lockIncident(Agent agent, Incident inc) {
		try{
		if(cacheAvaililble()){
			QueueItem item = new QueueItem();
			item.setAgent(agent);
			item.setIncident(inc);
			item.setQueueItemType(LOCK_INCIDENT);

			boolean addToQueue = false;
			synchronized(this){
				if(cacheAvaililble()){
					queue.add(item);
					addToQueue = true;
				}
			}
			if(addToQueue){
				item.objectWait(MAX_WAIT_TIME);
			}
			
			return item.isLockIncidentReturn();
		}
		} catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Creates a QueueItem to invoke the LockFile.getLockActionMessages method.  If the consumer queue does not invoke the method
	 * within the MAX_WAIT_TIME, return false.
	 */
	@Override
	public List<ActionMessage> getLockActionMessages(String incidentId,
			Agent agent) {
		try{
			if(cacheAvaililble()){
				QueueItem item = new QueueItem();
				item.setIncidentId(incidentId);
				item.setAgent(agent);
				item.setQueueItemType(GET_LOCK_MESSAGE);

				boolean addToQueue = false;
				synchronized(this){
					if(cacheAvaililble()){
						queue.add(item);
						addToQueue = true;
					}
				}
				if(addToQueue){
					item.objectWait(MAX_WAIT_TIME);
				}
				return item.getLockActionMessagesReturn();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public LockFile getLockFile() {
		return lockFile;
	}

	public void setLockFile(LockFile lockFile) {
		this.lockFile = lockFile;
	}

}
