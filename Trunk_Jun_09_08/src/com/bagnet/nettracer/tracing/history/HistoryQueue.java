package com.bagnet.nettracer.tracing.history;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class HistoryQueue<K,V> extends LinkedHashMap<K,V> {
	
	private int MAX_QUEUE_SIZE;
	
	public HistoryQueue(int size) {
		super();
		MAX_QUEUE_SIZE = size;
	}
	
	@Override
	protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
		return size() > MAX_QUEUE_SIZE; 
	}

}
