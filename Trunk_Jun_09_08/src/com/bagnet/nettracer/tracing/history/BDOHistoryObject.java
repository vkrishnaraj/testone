package com.bagnet.nettracer.tracing.history;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.BDO;

public class BDOHistoryObject extends HistoryObject {

	private BDO bdo;
	private boolean hasTraceResults;
	
	public BDOHistoryObject() {
		super();
	}
	
	public BDO getBDO() {
		return bdo;
	}

	public void setBDO(BDO bdo) {
		this.bdo = bdo;
	}

	public boolean isHasTraceResults() {
		return hasTraceResults;
	}

	public void setHasTraceResults(boolean hasTraceResults) {
		this.hasTraceResults = hasTraceResults;
	}
	
	@Override
	public String getUniqueId() {
		return date.getTime() + ":" + getType() + ":" + bdo.getBDO_ID();
	}

	@Override
	public int getType() {
		return TracingConstants.HISTORY_OBJ_TYPE_BDO;
	}

}
