package com.bagnet.nettracer.tracing.actions.lfc;

import java.util.concurrent.ArrayBlockingQueue;

public class SalvageManager {
	
	private static final int NUM_THREADS = 5;
	private static final int CAPACITY = 250;
	
	private static ArrayBlockingQueue<SalvageItemContainer> queue = new ArrayBlockingQueue<SalvageItemContainer>(CAPACITY);
	private static ThreadGroup group = new ThreadGroup("LFSalvage Thread Group");
	private static Thread[] threads = new Thread[NUM_THREADS];
	
	static {
		for (int i = 0; i < NUM_THREADS; ++i) {
			threads[i] = new Thread(group, new SalvageItemRunnable(), String.valueOf(i));
			threads[i].start();
		}
	}
	
	public static ArrayBlockingQueue<SalvageItemContainer> getQueue() {
		return queue;
	}

}
