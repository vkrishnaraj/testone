package com.bagnet.nettracer.tracing.utils.general;

import java.util.Date;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.utils.TracerProperties;


public class ThreadMonitor implements Runnable{
	private static final Logger logger = Logger.getLogger(ThreadMonitor.class);
	
	private Vector<ThreadContainer> container;
	private ArrayBlockingQueue queue;
	private static final int DEFAULT_TIMEOUT = 360000;//6min
	private static final int POLL_INTERVAL = 30000;//30sec
	private static final int DEFAULT_EMAIL_INTERVAL = 300000;//5min
	
	private int maxQueueSize;
	
	public ThreadMonitor(Vector<ThreadContainer> container, ArrayBlockingQueue queue){
		this.container = container;
		this.queue = queue;
		this.maxQueueSize=0;
	}
	
	@Override
	public void run() {
		while(true){
			try{
				Thread.sleep(POLL_INTERVAL);
				int size = queue.size();
				if(size > this.maxQueueSize){
					this.maxQueueSize = size;
				}
				for(ThreadContainer tc:container){
					Date now = new Date();
					if(!tc.isDead() && (tc.isWaiting() == false || !tc.getThread().isAlive())){
						int timeout = PropertyBMO.getValueAsInt(PropertyBMO.THREAD_MONITOR_THREAD_TIMEOUT);
						if(!tc.getThread().isAlive() || now.getTime() - tc.getStartTime().getTime() > (timeout!=0?timeout:DEFAULT_TIMEOUT)){
							try{
								logger.error("Thread Failure: " + tc.getId() + "appears to have failed");
								AlertEmail.sendAlertEmail("Thread Failure","Thread: " + tc.getId() + "appears to have failed " + TracerProperties.getInstanceLabel());
								tc.setDead(true);
							}
							catch(Exception e){
								e.printStackTrace();
							}
						}
					}
					if(!tc.isDead() && tc.isConnectError()){
						logger.error("Thread Failure: " + tc.getId() + "has a connection error");
						int emailInterval = PropertyBMO.getValueAsInt(PropertyBMO.THREAD_MONITOR_EMAIL_INTERVAL);
						if(tc.getConnectErrorEmailDate() == null || now.getTime() - tc.getConnectErrorEmailDate().getTime() > (emailInterval!=0?emailInterval:DEFAULT_EMAIL_INTERVAL)){
							AlertEmail.sendAlertEmail("EJB connection error", "Thread: " + tc.getId() + " has a connection error " + 
									TracerProperties.getInstanceLabel() +
									" connecting to " + PropertyBMO.getValue(PropertyBMO.LF_EJB_SERVER_LOCATION));
							tc.setConnectErrorEmailDate(now);
							tc.setConnectError(false);
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
