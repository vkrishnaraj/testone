/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import aero.nettracer.fs.model.Claim;

import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.forms.ClaimProrateForm;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ClaimProrateAction extends CheckedAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)) return (mapping
				.findForward(TracingConstants.NO_PERMISSION));
		
		if(!manageToken(request)) {
			return (mapping.findForward(TracingConstants.INVALID_TOKEN));
		}

		BagService bs = new BagService();
		ClaimProrateForm cpform = (ClaimProrateForm) form;
		IncidentForm theform = (IncidentForm) session.getAttribute("incidentForm");
		request.setAttribute("CLAIM_PRORATE_RPT", Integer
				.toString(ReportingConstants.CLAIM_PRORATE_RPT));
		/** ****************** handle requests ******************** */
		if (request.getParameter("error") != null) {
			if (request.getParameter("error").equals("print")) {
				// printing error
				ActionMessages errors = new ActionMessages();
				ActionMessage error = new ActionMessage("error.print");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				request.setAttribute("closebtn", "1");
				return (mapping.findForward(TracingConstants.ERROR_MAIN));
			}
		}

		if (theform == null || request.getParameter("clear") != null) {
			// came here from claim menu, need to show form to enter incident id
			session.setAttribute("prorate", "1");
			request.setAttribute("noincident", "1");
			return (mapping.findForward(TracingConstants.CLAIM_CREATE_NEW));
//			response.sendRedirect("create_claim.do");
//			return null;
		} else {
			request.setAttribute("incident", theform.getIncident_ID());
		}

		// save claim prorate notice
		if (request.getParameter("save") != null) {
			Claim cDTO = new Claim();
			if (bs.insertClaimProrate(cDTO, cpform, session, theform.getIncident_ID())) {
				theform.setClaim(cDTO);
				request.setAttribute("success", "1");
			} else {
				//TracerUtils.populateClaimProrate(cpform, theform, session);
				request.setAttribute("fail", "1");
			}
			return (mapping.findForward(TracingConstants.CLAIM_PRORATE_MAIN));
		}
		/**
		 * retrieve to modify claim payout
		 */
		TracerUtils.populateClaimProrate(cpform, theform, request);

		return (mapping.findForward(TracingConstants.CLAIM_PRORATE_MAIN));
	}
}