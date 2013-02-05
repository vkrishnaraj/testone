package com.bagnet.nettracer.tracing.utils.lf;

import java.util.Date;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;


import org.apache.log4j.Logger;



import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.history.FoundHistoryObject;
import com.bagnet.nettracer.tracing.utils.general.ThreadContainer;
import com.bagnet.nettracer.tracing.utils.general.ThreadMonitor;

public class TraceHandler {
	
	private static final Logger logger = Logger.getLogger(TraceHandler.class);

	public static final Integer TYPE_LOST = 1;
	public static final Integer TYPE_FOUND = 2;
	public static final Integer TYPE_FOUND_HISTORY_OBJECT = 3;
	
	private static ArrayBlockingQueue<Object[]> primaryqueue;
	private static ArrayBlockingQueue<Object[]> secondaryqueue;
	
//	private static ArrayBlockingQueue<FoundHistoryObject> queue;
	private static Vector<ThreadContainer> v;
	
	private static final int CONNECTION_THREADS = 2;
	private static final int MAX_CONNECTION_THREADS = 10;
	
	private static int threadCount(){
		int ret = 0;
		for(ThreadContainer c:v){
			if(!c.isDead()){
				ret++;
			}
		}
		return ret;
	}
	
	public static int getQueueSize(){
		if(primaryqueue != null){
			return primaryqueue.size();
		} else {
			return 0;
		}
	}
	
	private static synchronized void startThreads() throws RemoteConnectionException{
		if(primaryqueue == null){
			v = new Vector<ThreadContainer>();
			primaryqueue = new ArrayBlockingQueue<Object[]>(20000);
			ThreadMonitor tm = new ThreadMonitor(v,primaryqueue);
			Thread t = new Thread(tm, "TraceHandlerMonitorThread");
			t.start();
		}
		while(threadCount() < CONNECTION_THREADS && v.size() < MAX_CONNECTION_THREADS){
			String id = "LFCTraceThread " + v.size();
			ThreadContainer container = new ThreadContainer();
			container.setId(id);
			container.setStartTime(new Date());
			TraceThread thread = new TraceThread(primaryqueue, container);
			Thread t = new Thread(thread, id);
			t.start();
			container.setThread(t);
			v.add(container);
			if(PropertyBMO.isTrue(PropertyBMO.LF_TRACING_SECONDARY_TRACE)){
				SecondTraceThread thread2 = new SecondTraceThread(secondaryqueue, container);
				Thread t2 = new Thread(thread2, id);
				t2.start();
				container.setThread(t);
				v.add(container);
			}
			
		}
		if(threadCount() == 0){
			throw new RemoteConnectionException("no connection threads");
		}
	}
	
	public static void trace(FoundHistoryObject f) throws RemoteConnectionException{
		startThreads();
		Object[] toAdd = {TYPE_FOUND_HISTORY_OBJECT, f};
		primaryqueue.add(toAdd);

		if(PropertyBMO.isTrue(PropertyBMO.LF_TRACING_SECONDARY_TRACE)){
			secondaryqueue.add(toAdd);
		}
	}
	
	public static void trace(LFLost lost) throws RemoteConnectionException{
		if(lost != null && lost.getId() > 0){
			startThreads();
			Object[] toAdd = {TYPE_LOST, new Long(lost.getId())};
			primaryqueue.add(toAdd);
			if(PropertyBMO.isTrue(PropertyBMO.LF_TRACING_SECONDARY_TRACE)){
				secondaryqueue.add(toAdd);
			}
		}
	}
	
	public static void trace(LFFound found) throws RemoteConnectionException{
		if(found != null && found.getId() > 0){
			startThreads();
			Object[] toAdd = {TYPE_FOUND, new Long(found.getId())};
			primaryqueue.add(toAdd);
			if(PropertyBMO.isTrue(PropertyBMO.LF_TRACING_SECONDARY_TRACE)){
				secondaryqueue.add(toAdd);
			}
		}
	}
	
	public static void testTrace(FoundHistoryObject f, int result, long wait){
		TestThread test = new TestThread(f,result,wait);
		Thread t = new Thread(test, "testthread");
		t.start();
	}
	
	public static void main(String [] args){
		FoundHistoryObject f = new FoundHistoryObject();
		LFFound found = new LFFound();
		LFLost lost = new LFLost();
		
		found.setId(536);
		f.setFound(found);
		lost.setId(546);
		
//		TraceHandler.testTrace(f, 1, 5000);
		
//		while(true){
		try {
			TraceHandler.trace(found);
			TraceHandler.trace(lost);
			TraceHandler.trace(f);
		} catch (RemoteConnectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		int i = 0;
//		while(i < 2){
//			System.out.println(f.isHasTraceResults());
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//			}
//			i++;
//		}
//		}
		System.out.println("done");
	}
}
