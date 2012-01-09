package com.bagnet.nettracer.tracing.utils.lf;

import com.bagnet.nettracer.tracing.history.FoundHistoryObject;

public class TestThread implements Runnable{

	private FoundHistoryObject f;
	private int result;
	private long wait;
	
	public TestThread(FoundHistoryObject f, int result, long wait){
		this.f = f;
		this.result = result;
		this.wait = wait;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(result == -1){
			f.setHasTraceResults(Math.random() > 0.5);
		} else {
			f.setHasTraceResults(result == 1);
		}
		
	}
	
}
