package com.bagnet.nettracer.tracing.history;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.lf.LFLost;

public class LostItemHistoryObject extends HistoryObject {

	private LFLost lost;
	private boolean hasTraceResults;
	
	public LostItemHistoryObject() {
		super();
	}
	
	public LFLost getLostItem() {
		return lost;
	}

	public void setLostItem(LFLost lost) {
		this.lost = lost;
	}

	public boolean isHasTraceResults() {
		return hasTraceResults;
	}

	public void setHasTraceResults(boolean hasTraceResults) {
		this.hasTraceResults = hasTraceResults;
	}
	
	@Override
	public String getUniqueId() {
		return date.getTime() + ":" + getType() + ":" + lost.getId();
	}

	@Override
	public int getType() {
		return TracingConstants.HISTORY_OBJ_TYPE_LOST;
	}

}
