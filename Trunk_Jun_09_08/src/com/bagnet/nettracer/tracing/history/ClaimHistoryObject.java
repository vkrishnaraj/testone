package com.bagnet.nettracer.tracing.history;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Claim;

public class ClaimHistoryObject extends HistoryObject {

	private Claim claim;
	private boolean hasTraceResults;
	
	public ClaimHistoryObject() {
		super();
	}
	
	public Claim getClaim() {
		return claim;
	}

	public void setClaim(Claim claim) {
		this.claim = claim;
	}

	public boolean isHasTraceResults() {
		return hasTraceResults;
	}

	public void setHasTraceResults(boolean hasTraceResults) {
		this.hasTraceResults = hasTraceResults;
	}
	
	@Override
	public String getUniqueId() {
		return date.getTime() + ":" + getType() + ":" + claim.getId();
	}

	@Override
	public int getType() {
		return TracingConstants.HISTORY_OBJ_TYPE_CLAIM;
	}

}
