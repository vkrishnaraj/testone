/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import aero.nettracer.fs.model.detection.AccessRequest;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.fs.utilities.TransportMapper;
import aero.nettracer.selfservice.fraud.client.ClaimClientRemote;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.ntfs.ConnectionUtil;

/**
 * Implementation of <strong>Action </strong> that is responsible for viewing
 * incoming bags and performing actions like receive.
 * 
 * @author Ankur Gupta
 */
public class ViewFraudRequests extends CheckedAction {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ViewFraudRequests.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MessageResources messages = MessageResources
				.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

//		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user))
//			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		
		String approveId = (String) request.getParameter("approveId");
		String denyId = (String) request.getParameter("denyId");
		Context ctx = ConnectionUtil.getInitialContext();
		ClaimClientRemote remote = (ClaimClientRemote) ctx
				.lookup(PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME));

		if (approveId != null) {
			remote.approveRequest(Long.parseLong(approveId), null, user.getFirstname() + " " + user.getLastname());

		}

		else if (denyId != null) {
			remote.denyRequest(Long.parseLong(denyId), null, user.getFirstname() + " " + user.getLastname());
		}
		ctx.close();
		// menu highlite
		request.setAttribute("highlite", TracingConstants.SYSTEM_COMPONENT_NAME_FRAUD_REQUESTS);

		int requestListCount = remote.getOutstandingRequetsCount(user.getCompanycode_ID());
		List<OHD_Log> bagsList = null;
		List requestList = null;
		if (requestListCount > 0) {
			/** ************ pagination ************* */
			int rowcount = -1;
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"),
					TracingConstants.ROWS_SEARCH_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			int totalpages = 0;

			int currpage = request.getParameter("currpage") != null ? Integer
					.parseInt(request.getParameter("currpage")) : 0;
			if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1"))
				currpage++;
			if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1"))
				currpage--;

			request.setAttribute("currpage", Integer.toString(currpage));

			// get row count
			rowcount = requestListCount;

			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}

			// TODO - Get list
			requestList = TransportMapper.map(remote.getOutstandingRequests(user.getCompanycode_ID(), rowsperpage * currpage,
					rowsperpage));

			if (currpage + 1 == totalpages)
				request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList al = new ArrayList();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}

			/** ************ end of pagination ************* */
			request.setAttribute("requestList", requestList);
		} else {
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"),
					TracingConstants.ROWS_SEARCH_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

			int currpage = request.getParameter("currpage") != null ? Integer
					.parseInt(request.getParameter("currpage")) : 0;
			request.setAttribute("currpage", Integer.toString(currpage));
		}
		
		if (request.getParameter("matchId") != null) {
			long matchId = Long.parseLong(request.getParameter("matchId"));
			MatchHistory match = getMatchHistoryFromResults(matchId, requestList);
			request.setAttribute("match", match);
			int status = match.getFile2().getStatusId();
			if (status == TracingConstants.STATUS_SUSPECTED_FRAUD || status == TracingConstants.STATUS_KNOWN_FRAUD) {
				request.setAttribute("status", status);
			}
			
//			if (match.getFile2().getClaim() != null) {
//				request.setAttribute("claimId", match.getFile2().getClaim().getSwapId());
//			} else {
//				request.setAttribute("claimId", match.getFile2().getIncident().getAirlineIncidentId());
//			}
			return (mapping.findForward(TracingConstants.CLAIM_MATCH_DETAILS));
		}

//		ActionMessages errors = new ActionMessages();
//		if (false) {
//			ActionMessage error = new ActionMessage("message.send.teletype.info");
//			saveMessages(request, errors);
//			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
//		}

		return mapping.findForward("viewfraudrequests");
	}
	
	private static MatchHistory getMatchHistoryFromResults(long matchId, List<AccessRequest> requestList) {
		if (requestList == null || requestList.isEmpty()) {
			return null;
		}
		
		MatchHistory toReturn = null;
		for (AccessRequest ar: requestList) {
			if (matchId == ar.getMatchHistory().getId()) {
				toReturn = ar.getMatchHistory();
				break;
			}
		}
		return toReturn;
	}

}