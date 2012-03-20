package com.bagnet.nettracer.tracing.history;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;

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
			if(numItems==10){
				Collections.reverse(temp);
			}
			return temp;
		}
		toReturn = new ArrayList<HistoryObject>(temp.subList(tempSize - numItems, tempSize));
		System.out.println("toReturn before reversal: "+toReturn);
		if(numItems==10){
			Collections.reverse(toReturn);
		}
		System.out.println("toReturn after reversal: "+toReturn);
		return toReturn;
	}
	
}
