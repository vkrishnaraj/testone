package com.bagnet.nettracer.tracing.db.taskmanager;

import com.bagnet.nettracer.tracing.db.lf.LFFound;

public class ItemTraceResultsTask extends GeneralTask {
	
	private LFFound foundItem;

	public LFFound getFoundItem() {
		return foundItem;
	}

	public void setFoundItem(LFFound foundItem) {
		this.foundItem = foundItem;
	}
	
}
