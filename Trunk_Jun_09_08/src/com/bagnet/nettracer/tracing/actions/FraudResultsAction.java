/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.selfservice.fraud.ClaimRemote;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.ClaimDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.forms.FraudResultsForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.ntfs.ConnectionUtil;

public class FraudResultsAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(FraudResultsAction.class);
	private Agent user;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		FraudResultsForm resultsForm = (FraudResultsForm) form;
		String claimIdString = request.getParameter("claimId");
		FsClaim claim = null;
		if (claimIdString != null) {
			claim = ClaimDAO.loadClaim(Long.parseLong(claimIdString));
			resultsForm.setClaimId(claim.getId());
		} else {
			claim = ClaimDAO.loadClaim(resultsForm.getClaimId());
		}
		
		Set<MatchHistory> results = null;
		if (request.getParameter("results") != null) {
			results = (Set<MatchHistory>) session.getAttribute("results");
			session.removeAttribute("results");
		} else {
			if (claim != null) {
				try {
					Context ctx = ConnectionUtil.getInitialContext();
					ClaimRemote remote = (ClaimRemote) ctx.lookup("NTServices_1_0/ClaimBean/remote");
					results = remote.getFileMatches(claim.getFile().getSwapId());
					ctx.close();
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}
		
		if (request.getParameter("matchId") != null) {
			long matchId = Long.parseLong(request.getParameter("matchId"));
			MatchHistory match = getMatchHistory(matchId, resultsForm);
			request.setAttribute("match", match);
			int status = match.getFile2().getStatusId();
			if (status == TracingConstants.STATUS_SUSPECTED_FRAUD || status == TracingConstants.STATUS_KNOWN_FRAUD) {
				request.setAttribute("status", status);
			}
			request.setAttribute("claimId", resultsForm.getClaimId());
			return (mapping.findForward(TracingConstants.CLAIM_MATCH_DETAILS));
		} else if (request.getParameter("requestInfo") != null) {
			List<MatchHistory> requestList = getRequestedMatches(resultsForm);
			if (requestList.size() != 0) {
				session.setAttribute("requestList", requestList);
				response.sendRedirect("request_info.do?claimId=" + claim.getId());
				return null;
			}
		} 

		resultsForm = separateResults(results, resultsForm);
		
		return (mapping.findForward(TracingConstants.CLAIM_FRAUD_RESULTS));
	}
	
	private FraudResultsForm separateResults(Set<MatchHistory> results, FraudResultsForm form) {
		ArrayList<MatchHistory> primaryResults = new ArrayList<MatchHistory>();
		ArrayList<MatchHistory> secondaryResults = new ArrayList<MatchHistory>();

		if (form.getClaimId() > 0) {

			if (results != null && !results.isEmpty()) {
			
				for (MatchHistory match: results) {
					if (match.isPrimarymatch()) {
						primaryResults.add(match);
					} else {
						secondaryResults.add(match);
					}
				}
				
			}
			
		}
		
		form.setPrimaryResults(primaryResults);
		form.setSecondaryResults(secondaryResults);
		return form;
	}

	private List<MatchHistory> getRequestedMatches(FraudResultsForm form) {
		ArrayList<MatchHistory> matches = new ArrayList<MatchHistory>();
		matches.addAll(form.getPrimaryResults());
		matches.addAll(form.getSecondaryResults());
		ArrayList<MatchHistory> toReturn = new ArrayList<MatchHistory>();
		long originalId = form.getClaimId();
		
		for (MatchHistory m: matches) {
			if (m.isSelected()) {
				logger.info("Info requested for match: " + m.getId());
				if (m.getFile1().getClaim().getId() == originalId) {
					toReturn.add(m);
				} else {
					toReturn.add(m);
				}
			}
		}
		
		return toReturn;  
	}
	
	private MatchHistory getMatchHistory(long matchId, FraudResultsForm form) {
		LinkedHashSet<MatchHistory> matches = new LinkedHashSet<MatchHistory>();
		if (form.getPrimaryResults() != null) {
			matches.addAll(form.getPrimaryResults());
		}

		if (form.getSecondaryResults() != null) {
			matches.addAll(form.getSecondaryResults());
		}
		
		if (!matches.isEmpty()) {
			for (MatchHistory match: matches) {
				if (match.getId() == matchId) {
					return match;
				}
			}
		}
		return null;
	}
	
}