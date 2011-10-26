package com.bagnet.nettracer.tracing.actions;

import java.util.LinkedHashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import aero.nettracer.fs.model.FsClaim;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.forms.SelectClaimForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class SelectClaimAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(ModifyClaimAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MODIFY_CLAIM, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		
		if(!manageToken(request)) {
			return (mapping.findForward(TracingConstants.INVALID_TOKEN));
		}

		SelectClaimForm scForm = (SelectClaimForm) form;
		scForm.setType(TracingConstants.LINK_TYPE_CLAIM_PAGE);
		
		String incidentId = request.getParameter("incidentId");
		if (incidentId == null || incidentId.isEmpty()) {
			incidentId = scForm.getIncidentId();
		}
		
		// we couldn't get the incident id from the form or the request
		if (incidentId == null || incidentId.isEmpty()) {
			response.sendRedirect("claim_resolution.do?createNew=1");
			return null;
		}
		
		Incident incident = new IncidentBMO().findIncidentByID(incidentId);
		
		if (incident == null) {
			ActionMessages errors = new ActionMessages();
			ActionMessage error = new ActionMessage("error.noincident");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
			return mapping.findForward(TracingConstants.CLAIM_CREATE_NEW);
		} else if (incident.getClaims() == null || incident.getClaims().isEmpty()) {
			// shouldn't be here without a valid incident
		
			// there aren't any claims, so we have to create one in any case
			if (request.getParameter("fraud_results") != null) {
				response.sendRedirect("fraud_results.do?incident=" + incident.getIncident_ID());
			} else {
				response.sendRedirect("claim_resolution.do?createNew=1&populate=1&incidentId=" + incident.getIncident_ID());
			}
			return null;
		} else {
			
			// now we know that we have an incident with at least one claim
			if (PropertyBMO.isTrue("ntfs.support.multiple.claims")) {
				if (request.getParameter("createNew") != null) {
					response.sendRedirect("claim_resolution.do?createNew=1&populate=1&incidentId=" + incident.getIncident_ID());
					return null;
				} else {
					scForm.setIncidentId(incidentId);
					scForm.setClaims(new LinkedHashSet<FsClaim>(incident.getClaims()));
					if (request.getParameter("fraud_results") != null) {
						scForm.setType(TracingConstants.LINK_TYPE_FRAUD_RESULTS_PAGE);
					}
					return mapping.findForward(TracingConstants.CLAIM_SELECT_CLAIM);
				}
			} else {
				long claimId = incident.getClaims().iterator().next().getId();
				if (request.getParameter("fraud_results") != null) {
					response.sendRedirect("fraud_results.do?claimId=" + claimId);
				} else {
					response.sendRedirect("claim_resolution.do?claimId=" + claimId);
				}
				return null;
			}
			
		}
		
	}
}