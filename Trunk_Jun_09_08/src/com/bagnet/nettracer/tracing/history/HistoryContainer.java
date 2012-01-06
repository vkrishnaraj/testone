package com.bagnet.nettracer.tracing.history;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;

public class HistoryContainer {

	private LinkedHashMap<String, HistoryObject> queue;
	
	public HistoryContainer() {
		queue = new HistoryQueue(PropertyBMO.getValueAsInt("history.queue.size"));
	}
	
	public HistoryObject get(String key) {
		return queue.get(key);
	}
	
	public HistoryObject put(String key, HistoryObject value) {
		return queue.put(key, value);
	}
	
	public ArrayList<HistoryObject> getNewestItems(int numItems) {
		ArrayList<HistoryObject> toReturn = new ArrayList<HistoryObject>();
		if (numItems <= 0) {
			return toReturn;
		}
		Collection<HistoryObject> values = queue.values();
		Iterator<HistoryObject> it = values.iterator();
		while (it.hasNext() && toReturn.size() < numItems) {
			toReturn.add(it.next());
		}
		return toReturn;
	}
	
}
