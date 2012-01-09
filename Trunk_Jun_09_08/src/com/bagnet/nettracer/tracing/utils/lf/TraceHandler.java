package com.bagnet.nettracer.tracing.utils.lf;

import java.util.concurrent.ArrayBlockingQueue;


import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.history.FoundHistoryObject;

public class TraceHandler {
	
	private static final Logger logger = Logger.getLogger(TraceHandler.class);

	private static ArrayBlockingQueue<FoundHistoryObject> queue;
	private static final int MAX_CONNECTION_THREADS = 1;
	
	public static void trace(FoundHistoryObject f){
		if(queue == null){
			queue = new ArrayBlockingQueue<FoundHistoryObject>(100);
			for(int i = 0; i < MAX_CONNECTION_THREADS; i++){
				TraceThread thread = new TraceThread(queue);
				Thread t = new Thread(thread, "TraceThread " + i);
				t.start();
			}
		}
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
		
		TraceHandler.testTrace(f, 1, 5000);
		
//		TraceHandler.trace(f);
		while(true){
			System.out.println(f.isHasTraceResults());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}
}
