/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.detection.MatchDetail;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.selfservice.fraud.ClaimRemote;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.ClaimDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.forms.FraudResultsForm;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;
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

		if(!manageToken(request)) {
			return (mapping.findForward(TracingConstants.INVALID_TOKEN));
		}
		
		FraudResultsForm resultsForm = (FraudResultsForm) form;
		String claimIdString = request.getParameter("claimId");
		FsClaim claim = null;
		if (claimIdString != null) {
			claim = ClaimDAO.loadClaim(Long.parseLong(claimIdString));
			resultsForm.setClaim(claim);
		} else {
			claim = resultsForm.getClaim();
		}
		
		if (request.getParameter("requestInfo") != null) {
			List<FsClaim> requestList = getRequestedClaims(resultsForm);
			// TODO: get the requested claims and forward to the messaging page.
			logger.info(requestList);
		}
		
		Set<MatchHistory> results = null;
		if (request.getParameter("results") != null) {
			results = (Set<MatchHistory>) session.getAttribute("results");
			session.removeAttribute("results");
		} else {
			if (claim != null) {
				try {
				ClaimRemote remote = ConnectionUtil.getClaimRemote();
				results = remote.getFileMatches(claim.getFile().getSwapId());
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}
		
		resultsForm = separateResults(results, resultsForm);
		
		if (request.getParameter("matchId") != null) {
			long matchId = Long.parseLong(request.getParameter("matchId"));
			MatchHistory match = getMatchHistory(matchId, resultsForm);
			request.setAttribute("match", match);
			request.setAttribute("claimId", resultsForm.getClaim().getId());
			return (mapping.findForward(TracingConstants.CLAIM_MATCH_DETAILS));
		}
		
		
		
//		if (claimIdString != null && !claimIdString.isEmpty()) {
//			long claimId = claim.getId();
//			if (claimId > 0) {
//				// TODO: remove this line when actual is done and replace with the above commented lines
////				Set<MatchHistory> results = getDummyResults(claimId);
//				LinkedHashSet<MatchHistory> primaryResults = new LinkedHashSet<MatchHistory>();
//				LinkedHashSet<MatchHistory> secondaryResults = new LinkedHashSet<MatchHistory>();
//
//				
//				resultsForm.setPrimaryResults(primaryResults);
//				resultsForm.setSecondaryResults(secondaryResults);
//				
//			}
//			
//		}

		return (mapping.findForward(TracingConstants.CLAIM_FRAUD_RESULTS));
	}
	
	private FraudResultsForm separateResults(Set<MatchHistory> results, FraudResultsForm form) {
		LinkedHashSet<MatchHistory> primaryResults = new LinkedHashSet<MatchHistory>();
		LinkedHashSet<MatchHistory> secondaryResults = new LinkedHashSet<MatchHistory>();

		if (form.getClaim() != null) {

			long claimId = form.getClaim().getId();
			if (results != null && !results.isEmpty()) {
			
				for (MatchHistory match: results) {
					if (match.getFile1().getClaim().getSwapId() == claimId) {
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

	private List<FsClaim> getRequestedClaims(FraudResultsForm form) {
		ArrayList<MatchHistory> matches = new ArrayList<MatchHistory>();
		matches.addAll(form.getPrimaryResults());
		matches.addAll(form.getSecondaryResults());
		ArrayList<FsClaim> toReturn = new ArrayList<FsClaim>();
		long originalId = form.getClaim().getId();
		
		for (MatchHistory m: matches) {
			if (m.isSelected()) {
				logger.info("Info requested for match: " + m.getId());
				if (m.getFile1().getClaim().getId() == originalId) {
					toReturn.add(m.getFile2().getClaim());
				} else {
					toReturn.add(m.getFile1().getClaim());
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
	
//	// TODO: remove this method
//	private Set<MatchHistory> getDummyResults(long realClaimId) {
//		LinkedHashSet<MatchHistory> dummyResults = new LinkedHashSet<MatchHistory>();
//		for (int i = 0; i < 5; ++i) {
//			MatchHistory match = getDummyMatch();
//			match.setId(i);
//			match.getClaim1().setId(realClaimId);
//			match.getClaim2().setId(i);
//			dummyResults.add(match);
//		}
//		
//		for (int i = 0; i < 5; ++i) {
//			MatchHistory match = getDummyMatch();
//			match.setId(i+5);
//			match.getClaim1().setId(i);
//			match.getClaim2().setId(realClaimId);
//			dummyResults.add(match);
//		}
//		return dummyResults;
//	}
	
//	// TODO: remove this method
//	private MatchHistory getDummyMatch() {
//		MatchHistory dummyMatch = new MatchHistory();
//		
//		FsClaim claim1 = ClaimUtils.createClaim(user);
//		FsClaim claim2 = ClaimUtils.createClaim(user);
//		claim1.setClaimType(TracingConstants.LOST_DELAY);
//		claim2.setClaimType(TracingConstants.LOST_DELAY);
//		claim1.setClaimDate(new Date());
//		claim2.setClaimDate(new Date());
//		dummyMatch.setClaim1(claim1);
//		dummyMatch.setClaim2(claim2);
//		
//		MatchDetail dummyDetail = new MatchDetail();
//		dummyDetail.setDescription("name");
//		dummyDetail.setContent1("doe,john");
//		dummyDetail.setContent2("doe,john");
//		dummyDetail.setMatch(dummyMatch);
//		LinkedHashSet<MatchDetail> details = new LinkedHashSet<MatchDetail>();
//		details.add(dummyDetail);
//		dummyMatch.setDetails(details);
//		
//		return dummyMatch;
//	}

}