package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.db.lf.LFFound;

import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.fs.model.detection.TraceResponse;

public final class FraudResultsForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private long claimId;
	private TraceResponse traceResponse;
	private ArrayList<MatchHistory> primaryResults;
	private ArrayList<MatchHistory> secondaryResults;

	public long getClaimId() {
		return claimId;
	}

	public void setClaimId(long claimId) {
		this.claimId = claimId;
	}

	public ArrayList<MatchHistory> getPrimaryResults() {
		return primaryResults;
	}

	public void setPrimaryResults(ArrayList<MatchHistory> primaryResults) {
		this.primaryResults = primaryResults;
	}

	public ArrayList<MatchHistory> getSecondaryResults() {
		return secondaryResults;
	}

	public void setSecondaryResults(ArrayList<MatchHistory> secondaryResults) {
		this.secondaryResults = secondaryResults;
	}

	public TraceResponse getTraceResponse() {
		return traceResponse;
	}

	public void setTraceResponse(TraceResponse traceResponse) {
		this.traceResponse = traceResponse;
	}
	 
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// reset boolean variables so that checkboxes will work right.
		if (primaryResults != null) {
			for (MatchHistory match : primaryResults) {
				match.setDeleteSelected(false);
				match.setRequestSelected(false);
			}
		}
		if (secondaryResults != null) {
			for (MatchHistory match : secondaryResults) {
				match.setDeleteSelected(false);
				match.setRequestSelected(false);
			}
		}
	}

}