package com.bagnet.nettracer.tracing.history;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.OHD;

public class OHDHistoryObject extends HistoryObject {

	private OHD ohd;
	private boolean hasTraceResults;
	
	public OHDHistoryObject() {
		super();
	}
	
	public OHD getOHD() {
		return ohd;
	}

	public void setOHD(OHD ohd) {
		this.ohd = ohd;
	}

	public boolean isHasTraceResults() {
		return hasTraceResults;
	}

	public void setHasTraceResults(boolean hasTraceResults) {
		this.hasTraceResults = hasTraceResults;
	}
	
	@Override
	public String getUniqueId() {
		return date.getTime() + ":" + getType() + ":" + ohd.getOHD_ID();
	}

	@Override
	public int getType() {
		return TracingConstants.HISTORY_OBJ_TYPE_OHD;
	}

}
