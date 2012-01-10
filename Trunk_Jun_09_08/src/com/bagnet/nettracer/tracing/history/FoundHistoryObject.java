package com.bagnet.nettracer.tracing.history;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.lf.LFFound;

public class FoundHistoryObject extends HistoryObject {

	private LFFound found;
	private boolean hasTraceResults;
	
	public FoundHistoryObject() {
		super();
	}
	
	public LFFound getFound() {
		return found;
	}

	public void setFound(LFFound found) {
		this.found = found;
	}

	public boolean isHasTraceResults() {
		return hasTraceResults;
	}

	public void setHasTraceResults(boolean hasTraceResults) {
		this.hasTraceResults = hasTraceResults;
	}
	
	@Override
	public String getUniqueId() {
		return date.getTime() + ":" + getType() + ":" + found.getId();
	}

	@Override
	public int getType() {
		return TracingConstants.HISTORY_OBJ_TYPE_FOUND;
	}

}
