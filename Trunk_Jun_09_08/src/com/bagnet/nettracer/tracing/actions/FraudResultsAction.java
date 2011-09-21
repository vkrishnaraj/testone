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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.detection.AccessRequest.RequestStatus;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.fs.model.detection.TraceResponse;
import aero.nettracer.selfservice.fraud.ClaimRemote;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.ClaimDAO;
import com.bagnet.nettracer.tracing.dao.FileDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.forms.FraudResultsForm;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.ntfs.ConnectionUtil;

public class FraudResultsAction extends CheckedAction {
	
	private final int DELETE = 1;
	private final int REQUEST = 2;
	private static final Logger logger = Logger.getLogger(FraudResultsAction.class);
	private Agent user;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);
		ActionMessages errors = new ActionMessages();

		user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		FraudResultsForm resultsForm = (FraudResultsForm) form;
		String claimIdString = request.getParameter("claimId");
		String incidentIdString = request.getParameter("incident");
		if(incidentIdString == null){
			incidentIdString = (String) request.getAttribute("incident");
		}
		
		File file = null;
		FsClaim claim = null;
		if (incidentIdString != null) {
			file = FileDAO.loadFile(incidentIdString);
			request.setAttribute("claimId", claimIdString);
			request.setAttribute("incident", incidentIdString);
		} else if (claimIdString != null) {
			claim = ClaimDAO.loadClaim(Long.parseLong(claimIdString));
			resultsForm.setClaimId(claim.getId());
			request.setAttribute("claimId", claimIdString);
			if (claim.getNtIncidentId() != null) {
				request.setAttribute("incident", claim.getNtIncidentId());
			}
		} else {
//			claim = ClaimDAO.loadClaim(resultsForm.getClaimId());
		}
		
		Set<MatchHistory> results = null;
		TraceResponse traceResponse = null;
		if (session.getAttribute("results") != null) {
			results = (Set<MatchHistory>) session.getAttribute("results");
			traceResponse = (TraceResponse) session.getAttribute("traceResults");
			session.removeAttribute("results");
			session.removeAttribute("traceResults");
		} else if (request.getParameter("matchId") != null 
					|| request.getParameter("requestInfo") != null
						|| request.getParameter("delete") != null) { 
			claim = ClaimDAO.loadClaim(resultsForm.getClaimId());
			results = getResultsFromForm(resultsForm);
		} 

		if (results == null){
			long id = 0;
			if (file != null) {
				id = file.getSwapId();
			} else if (claim != null && claim.getFile() != null) {
				id = claim.getFile().getSwapId();
			}
			
			if (id > 0) {
				Context ctx = null;
				ClaimRemote remote = null;
				try {
					ctx = ConnectionUtil.getInitialContext();
					remote = (ClaimRemote) ConnectionUtil.getRemoteEjb(ctx, PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME));
					if (remote == null) {
						ActionMessage error = new ActionMessage("error.fs.could.not.communicate");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
					} else {
						traceResponse = remote.getFileMatches(id); 
						results = traceResponse.getMatchHistory();
					}
				} catch (Exception e) {
					logger.error(e);
				} finally {
					if (ctx != null) {
						ctx.close();
					}
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
//			request.setAttribute("claimId", resultsForm.getClaimId());
			ClaimUtils.enterAuditClaimEntry(user.getAgent_ID(), TracingConstants.FS_AUDIT_ITEM_TYPE_MATCH_HISTORY, matchId, TracingConstants.FS_ACTION_LOAD);
			return (mapping.findForward(TracingConstants.CLAIM_MATCH_DETAILS));
		} else if (request.getParameter("requestInfo") != null) {
			List<MatchHistory> requestList = getSelectedMatches(resultsForm, REQUEST);
			
			String myCompany = user.getCompanycode_ID();
			ArrayList<MatchHistory> toRemove = new ArrayList<MatchHistory>();
			for (MatchHistory m: requestList) {
				String matchedAirline = m.getFile2().getValidatingCompanycode();
				RequestStatus requestStatus = m.getFile2().getRequestStatus();
				if (matchedAirline.equals(myCompany) || requestStatus != null) {
					toRemove.add(m);
				}
			}
			requestList.removeAll(toRemove);
			if (requestList.size() != 0) {
				session.setAttribute("requestList", requestList);
				if(claim != null){
				response.sendRedirect("request_info.do?claimId=" + claim.getId());
				} else if(incidentIdString != null){
					response.sendRedirect("request_info.do?incident=" + incidentIdString);
				}
				return null;
			}
		} else if (request.getParameter("delete") != null) {
			List<MatchHistory> requestedList = getSelectedMatches(resultsForm, DELETE);
			if (requestedList.size() > 0) {
				LinkedHashSet<Long> ids = new LinkedHashSet<Long>();
				int agentId = user.getAgent_ID();
				long itemId;
				for (MatchHistory m: requestedList) {
					results = removeMatchHistory(results, m.getId());
					itemId = m.getId();
					ClaimUtils.enterAuditClaimEntry(agentId, TracingConstants.FS_AUDIT_ITEM_TYPE_MATCH_HISTORY, itemId, TracingConstants.FS_ACTION_DELETE);
					ids.add(itemId);
				}
				
				Context ctx = ConnectionUtil.getInitialContext();
				ClaimRemote remote = (ClaimRemote) ctx.lookup("NTServices_1_0/ClaimBean/remote");
				if (remote != null) {
					remote.deleteMatch(ids);
				}
			}
		}

		resultsForm = separateResults(results, resultsForm);
		resultsForm.setTraceResponse(traceResponse);
		
		return (mapping.findForward(TracingConstants.CLAIM_FRAUD_RESULTS));
	}
	
	private static Set<MatchHistory> removeMatchHistory(Set<MatchHistory> results, long id) {
		for (MatchHistory m: results) {
			if (m.getId() == id) {
				results.remove(m);
				break;
			}
		}
		return results;
	}
	
	private FraudResultsForm separateResults(Set<MatchHistory> results, FraudResultsForm form) {
		ArrayList<MatchHistory> primaryResults = new ArrayList<MatchHistory>();
		ArrayList<MatchHistory> secondaryResults = new ArrayList<MatchHistory>();

//		if (form.getClaimId() > 0) {
			if (results != null && !results.isEmpty()) {
				for (MatchHistory match: results) {
					if (match.isPrimarymatch()) {
						primaryResults.add(match);
					} else {
						secondaryResults.add(match);
					}
				}
			}
//		}
	
		form.setPrimaryResults(primaryResults);
		form.setSecondaryResults(secondaryResults);
		return form;
	}

	private List<MatchHistory> getSelectedMatches(FraudResultsForm form, int selectedType) {
		ArrayList<MatchHistory> matches = new ArrayList<MatchHistory>();
		matches.addAll(form.getPrimaryResults());
		matches.addAll(form.getSecondaryResults());
		ArrayList<MatchHistory> toReturn = new ArrayList<MatchHistory>();
		
		for (MatchHistory m: matches) {
			switch(selectedType) {
				case DELETE:
					if (m.isDeleteSelected()) {
						toReturn.add(m);
					}
					break;
				case REQUEST:
					if (m.isRequestSelected()) {
						toReturn.add(m);
					}
					break;
				default:
					break;
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
	
	private Set<MatchHistory> getResultsFromForm(FraudResultsForm form) {
		LinkedHashSet<MatchHistory> results = new LinkedHashSet<MatchHistory>();
		results.addAll(form.getPrimaryResults());
		results.addAll(form.getSecondaryResults());
		return results;
	}
	
}