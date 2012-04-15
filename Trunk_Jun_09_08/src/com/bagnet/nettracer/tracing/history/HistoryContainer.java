package com.bagnet.nettracer.tracing.history;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;

import edu.emory.mathcs.backport.java.util.Collections;

public class HistoryContainer {

	private HistoryQueue<String, HistoryObject> queue;
	
	public HistoryContainer() {
		queue = new HistoryQueue<String, HistoryObject>(PropertyBMO.getValueAsInt("history.queue.size"));
	}
	
	public HistoryObject get(String key) {
		return queue.get(key);
	}
	
	public HistoryObject put(String key, HistoryObject value) {
		return queue.put(key, value);
	}
	
	public HistoryQueue<String, HistoryObject> getQueue()
	{
		return queue;
	}
	
	public ArrayList<HistoryObject> getNewestItems(int numItems) {
		ArrayList<HistoryObject> toReturn = new ArrayList<HistoryObject>();
		if (numItems <= 0) {
			return toReturn;
		}
		Collection<HistoryObject> values = queue.values();
		ArrayList<HistoryObject> temp = new ArrayList<HistoryObject>(values);
		int tempSize = temp.size();
		if (tempSize < numItems) {
			return temp;
		}
		toReturn = new ArrayList<HistoryObject>(temp.subList(tempSize - numItems, tempSize));
	
		return toReturn;
	}
	
	public ArrayList<HistoryObject> getRevNewestItems(int numItems) {
		ArrayList<HistoryObject> toReturn = getNewestItems(numItems);
		Collections.reverse(toReturn);
		return toReturn;
	}
	
	public ArrayList<FoundHistoryObject> getNewestFoundHistoryItems(int numItems) {
		ArrayList<FoundHistoryObject> toReturn = new ArrayList<FoundHistoryObject>();
		
		ArrayList<HistoryObject> temp = new ArrayList<HistoryObject>(queue.values());
		int j = 0;
		
		for (int i = temp.size() - 1; i >= 0 && j < numItems; --i) {
			if (FoundHistoryObject.class.isInstance(temp.get(i)) && (TracingConstants.HIST_DESCRIPTION_ADD + " " + TracingConstants.HIST_DESCRIPTION_FOUNDITEM).equals(temp.get(i).getStatusDesc())) {
				toReturn.add((FoundHistoryObject)temp.get(i));
				++j;
			}
		}
		Collections.reverse(toReturn);
		
		return toReturn;
	}
	
}
