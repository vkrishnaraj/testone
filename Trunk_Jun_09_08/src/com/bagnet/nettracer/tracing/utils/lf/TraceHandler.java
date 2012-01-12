package com.bagnet.nettracer.tracing.utils.lf;

import java.util.Date;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;


import org.apache.log4j.Logger;



import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.history.FoundHistoryObject;
import com.bagnet.nettracer.tracing.utils.general.ThreadContainer;
import com.bagnet.nettracer.tracing.utils.general.ThreadMonitor;

public class TraceHandler {
	
	private static final Logger logger = Logger.getLogger(TraceHandler.class);

	private static ArrayBlockingQueue<FoundHistoryObject> queue;
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
	
	
	private static synchronized void startThreads() throws RemoteConnectionException{
		if(queue == null){
			v = new Vector<ThreadContainer>();
			queue = new ArrayBlockingQueue<FoundHistoryObject>(100);
			ThreadMonitor tm = new ThreadMonitor(v);
			Thread t = new Thread(tm, "TraceHandlerMonitorThread");
			t.start();
		}
		while(threadCount() < CONNECTION_THREADS && v.size() < MAX_CONNECTION_THREADS){
			String id = "TraceThread " + v.size();
			ThreadContainer container = new ThreadContainer();
			container.setId(id);
			container.setStartTime(new Date());
			TraceThread thread = new TraceThread(queue, container);
			Thread t = new Thread(thread, id);
			t.start();
			container.setThread(t);
			v.add(container);
		}
		if(threadCount() == 0){
			throw new RemoteConnectionException("no connection threads");
		}
	}
	
	public static void trace(FoundHistoryObject f) throws RemoteConnectionException{
		startThreads();
		queue.add(f);
	}
	
	public static void testTrace(FoundHistoryObject f, int result, long wait){
		TestThread test = new TestThread(f,result,wait);
		Thread t = new Thread(test, "testthread");
		t.start();
	}
	
	public static void main(String [] args){
		FoundHistoryObject f = new FoundHistoryObject();
		LFFound found = new LFFound();
		found.setId(2);
		f.setFound(found);
		
//		TraceHandler.testTrace(f, 1, 5000);
		
		while(true){
		try {
			TraceHandler.trace(f);
		} catch (RemoteConnectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int i = 0;
		while(i < 2){
			System.out.println(f.isHasTraceResults());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			i++;
		}
		}
	}
}
