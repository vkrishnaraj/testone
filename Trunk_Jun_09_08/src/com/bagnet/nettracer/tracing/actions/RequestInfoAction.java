package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.selfservice.fraud.ClaimRemote;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.forms.RequestInfoForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.ntfs.ConnectionUtil;

public class RequestInfoAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(RequestInfoAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		RequestInfoForm requestForm = (RequestInfoForm) form;

		if (request.getParameter("send") != null) {
			sendRequests(requestForm, user);
			
			String claimId = (String) request.getAttribute("claimId");
			if (claimId == null) {
				claimId = requestForm.getOriginalClaimId();
			}
			String incidentId = (String) request.getAttribute("incident");
			if (claimId != null) {
				response.sendRedirect("claim_resolution.do?claimId=" + claimId);
			} else if (incidentId != null){
				response.sendRedirect("claim_resolution.do?incident=" + incidentId);				
			}
			return null;
		}
		
		requestForm.setOriginalClaimId((String) request.getParameter("claimId"));
		requestForm.setRequestedMatches((ArrayList<MatchHistory>) session.getAttribute("requestList"));
	
		return mapping.findForward(TracingConstants.CLAIM_REQUEST_INFO);
		
	}
	
	private static void sendRequests(RequestInfoForm form, Agent user) {
		try {
			Context ctx = ConnectionUtil.getInitialContext();
			ClaimRemote remote = (ClaimRemote) ctx
					.lookup("NTServices_1_0/ClaimBean/remote");
			String message = form.getMessage();
			if (remote != null) {
				for (MatchHistory m: form.getRequestedMatches()) {
					remote.requestAccess(m.getFile2().getId(), m.getId(), user.getFirstname() + " " + user.getLastname(), user.getCompanycode_ID(), message);
				}
			}
			ctx.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}