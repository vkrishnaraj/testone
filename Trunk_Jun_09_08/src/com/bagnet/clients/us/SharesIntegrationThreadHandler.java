package com.bagnet.clients.us;

import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.utils.general.ThreadContainer;
import com.bagnet.nettracer.tracing.utils.general.ThreadHandler;
import com.bagnet.nettracer.tracing.utils.general.ThreadMonitor;

public class SharesIntegrationThreadHandler implements ThreadHandler{
	private static ArrayBlockingQueue<Object[]> queue;
	private static Vector<ThreadContainer> v;
	private static ThreadMonitor tm;
	
	private static final int DEFAULT_CONNECTION_THREADS = 2;
	private static final int DEFAULT_MAX_CONNECTION_THREADS = 10;
	
	public static final Integer SEND_TELEX = 1;
	public static final Integer PCN = 2;
	public static final Integer FM = 3;
	
	private static int maxItems = 0;
	
	private static int getConnectionThreadCount(){
		int i = PropertyBMO.getValueAsInt(PropertyBMO.PROPERTY_SHARES_THREAD_COUNT);
		return i!=0?i:DEFAULT_CONNECTION_THREADS;
	}
	
	private static int getMaxConnectionThreadCount(){
		int i = PropertyBMO.getValueAsInt(PropertyBMO.PROPERTY_SHARES_MAX_THREAD_COUNT);
		return i!=0?i:DEFAULT_MAX_CONNECTION_THREADS;
	}
	
	private static int threadCount(){
		int ret = 0;
		for(ThreadContainer c:v){
			if(!c.isDead()){
				ret++;
			}
		}
		return ret;
	}
	
	public static ArrayBlockingQueue<Object[]> getQueue(){
		return queue;
	}
	
	public static synchronized void startThreads() throws Exception{
		if(queue == null){
			v = new Vector<ThreadContainer>();
			queue = new ArrayBlockingQueue<Object[]>(10000);
			tm = new ThreadMonitor(v, queue);
			Thread t = new Thread(tm, "SharesIntegrationMonitorThread");
			t.start();
		}
		while(threadCount() < getConnectionThreadCount() && v.size() < getMaxConnectionThreadCount()){
			String id = "SharesIntegrationThread " + v.size();
			ThreadContainer container = new ThreadContainer();
			container.setId(id);
			container.setStartTime(new Date());
			SharesIntegrationThread thread = new SharesIntegrationThread(queue, container);
			Thread t = new Thread(thread, id);
			t.start();
			container.setThread(t);
			v.add(container);
		}
		if(threadCount() == 0){
			throw new Exception();
		}
	}
	
	public static void sendTelex(String message, String address) throws Exception{
		String [] payload = {message,address};
		Object[] toAdd = {SEND_TELEX, payload};
		addToQueue(toAdd);
	}
	
	public static void doPcn(OHD_Log ohd_log) throws Exception{
		Object[] toAdd = {PCN,ohd_log};
		addToQueue(toAdd);
	}
	
	public static void sendForwardMessage(List<OHD_Log> logs) throws Exception{
		Object[] toAdd = {FM,logs};
		addToQueue(toAdd);		
	}
	
	private static void addToQueue(Object[] o) throws Exception{
		startThreads();
		int i = queue.size();
		if(maxItems < i){
			maxItems = i;
		}
		queue.add(o);
	}
	
	public int getCurrentQueueSize(){
		return queue!=null?queue.size():0;
	}
	
	public int getMaxQueueSize(){
		return maxItems;
	}
	
//	public void spawnTest(){
//		//be sure to set thread to mock if running this
//		for(int i = 0; i < 10; i++){
//			try {
//				for(int j = 0; j < Math.random() * 100.0; j++)
//				SharesIntegrationThreadHandler.doPcn(null);
//				for(int j = 0; j < Math.random() * 100.0; j++)
//					SharesIntegrationThreadHandler.sendTelex("","");
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	
}
