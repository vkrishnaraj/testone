package com.bagnet.clients.us;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.general.ThreadContainer;

public class SharesIntegrationThread implements Runnable{
	private static final boolean MOCK = false;
	private static final long MOCK_TIME = 30000;
	
	private static final Logger logger = Logger.getLogger(SharesIntegrationThread.class);
	
	private ArrayBlockingQueue<Object[]> queue;
	private ThreadContainer container;
	
	public SharesIntegrationThread(ArrayBlockingQueue<Object[]> queue, ThreadContainer container){
		this.queue = queue;
		this.container = container;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				container.setWaiting(true);
				Object [] args = queue.take();
				container.setStartTime(new Date());
				container.setWaiting(false);
				
				if((Integer)args[0] == SharesIntegrationThreadHandler.SEND_TELEX){
					sendTelex((String[])args[1]);
				} else if ((Integer)args[0] == SharesIntegrationThreadHandler.PCN){
					doPcn((OHD_Log)args[1]);
				} else if ((Integer)args[0] == SharesIntegrationThreadHandler.FM){
					sendForwardMessage((List<OHD_Log>)args[1]);
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private boolean sendTelex(String [] args){
		if(MOCK){
			try {
				System.out.println("Mock sendTelex");
				Thread.sleep(MOCK_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			SharesIntegrationWrapper iw = new SharesIntegrationWrapper();
			iw.sendTelex(args[0], args[1]);
			return true;
		}
	}

	private boolean doPcn(OHD_Log log){
		if(MOCK){
			try {
				System.out.println("Mock doPcn");
				Thread.sleep(MOCK_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			try {
				SpringUtils.getClientEventHandler().doPcn(log);
			} catch (Exception e) {
				logger.error("Error performing PCN lookup...");
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}
	
	private boolean sendForwardMessage(List<OHD_Log> logs){
		if(MOCK){
			try{
				System.out.println("Mock sendForwardMessage");
				Thread.sleep(MOCK_TIME);
			} catch (Exception e){
				e.printStackTrace();
			}
			return true;
		} else {
			try {
				SpringUtils.getClientEventHandler().doEventOnForward(logs);
			} catch (Exception e) {
				logger.error("Error performing forward...");
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}
}
