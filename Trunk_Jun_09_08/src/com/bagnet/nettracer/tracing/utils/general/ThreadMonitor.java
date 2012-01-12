package com.bagnet.nettracer.tracing.utils.general;

import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;


public class ThreadMonitor implements Runnable{
	private static final Logger logger = Logger.getLogger(ThreadMonitor.class);
	
	private Vector<ThreadContainer> container;
	private static final long TIMEOUT = 120000;//2min
	private static final long POLL_INTERVAL = 30000;//30sec
	private static final long EMAIL_INTERVAL = 300000;//5min
	
	public ThreadMonitor(Vector<ThreadContainer> container){
		this.container = container;
	}
	
	@Override
	public void run() {
		while(true){
			try{
				Thread.sleep(POLL_INTERVAL);
				for(ThreadContainer tc:container){
					Date now = new Date();
					if(!tc.isDead() && (tc.isWaiting() == false || !tc.getThread().isAlive())){
						if(!tc.getThread().isAlive() || now.getTime() - tc.getStartTime().getTime() > TIMEOUT){
							try{
								logger.error("Thread Failure: " + tc.getId() + "appears to have failed");
								AlertEmail.sendAlertEmail("Thread Failure","Thread: " + tc.getId() + "appears to have failed");
								tc.setDead(true);
							}
							catch(Exception e){
								e.printStackTrace();
							}
						}
					}
					if(!tc.isDead() && tc.isConnectError()){
						logger.error("Thread Failure: " + tc.getId() + "has a connection error");
						if(tc.getConnectErrorEmailDate() == null || now.getTime() - tc.getConnectErrorEmailDate().getTime() > EMAIL_INTERVAL){
							AlertEmail.sendAlertEmail("EBJ connection error", "Thread: " + tc.getId() + " has a connection error");
							tc.setConnectErrorEmailDate(now);
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
