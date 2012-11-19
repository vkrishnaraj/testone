package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.apache.taglibs.standard.resources.Resources;

import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.selfservice.fraud.ClaimRemote;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.forms.RequestInfoForm;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.ntfs.ConnectionUtil;

public class RequestInfoAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(RequestInfoAction.class);
	private static ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale("US"));
	
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
			boolean success = sendRequests(requestForm, user);
			ActionMessages messages = new ActionMessages();
			if (!success) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.request.info"));
				saveErrors(session, messages);
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.sent"));
				saveMessages(session, messages);
			}
			
			String claimId = (String) request.getAttribute("claimId");
			if (claimId == null) {
				claimId = requestForm.getOriginalClaimId();
			}
			String incidentId = (String) request.getAttribute("incident");
			if(incidentId == null){
				incidentId = (String) request.getParameter("incident");
			}
			
			if (claimId != null) {
				response.sendRedirect("fraud_results.do?claimId=" + claimId);
			} else if (incidentId != null){
				response.sendRedirect("fraud_results.do?incident=" + incidentId);				
			}
			return null;
		}
		
		requestForm.setOriginalClaimId((String) request.getParameter("claimId"));
		requestForm.setRequestedMatches((ArrayList<MatchHistory>) session.getAttribute("requestList"));
		requestForm.setContactName(user.getFirstname() + " " + user.getLastname());
		requestForm.setContactEmail("");
		requestForm.setContactPhone("");
		
		return mapping.findForward(TracingConstants.CLAIM_REQUEST_INFO);
		
	}
	
	private static boolean sendRequests(RequestInfoForm form, Agent user) {
		boolean success = true;
		try {
			Context ctx = ConnectionUtil.getInitialContext();
			ClaimRemote remote = (ClaimRemote) ctx
					.lookup("NTServices_1_0/ClaimBean/remote");
			String message = form.getMessage();

			if (remote != null) {
				int agentId = user.getAgent_ID();
				long itemId;
				for (MatchHistory m: form.getRequestedMatches()) {
					itemId = m.getId();
					ClaimUtils.enterAuditClaimEntry(agentId, TracingConstants.FS_AUDIT_ITEM_TYPE_MATCH_HISTORY, itemId, TracingConstants.FS_ACTION_REQUEST_INFO);
					remote.requestAccess(m.getFile2().getId(), itemId, user.getFirstname() + " " + user.getLastname(), user.getCompanycode_ID(), resources.getString("request.message")+": "+message, form.getContactName(), form.getContactEmail(),form.getContactPhone());
				}
			}
			ctx.close();
		} catch (NamingException e) {
			logger.error(e);
			success = false;
		}
		return success;
	}
}