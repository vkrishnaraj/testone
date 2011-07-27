package com.bagnet.nettracer.tracing.actions;

import java.util.LinkedHashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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
		
		// redirect if the airline doesn't support multiple claims
		String incidentId = request.getParameter("incidentId");
		if (!PropertyBMO.isTrue("ntfs.support.multiple.claims")) {
			response.sendRedirect("claim_resolution.do?incidentId=" + incidentId);
			return null;
		}
		
		// redirect to the create claim action with the pre-population trigger set
		if (request.getParameter("createNew") != null) {
			response.sendRedirect("create_claim.do?incidentId=" + scForm.getIncidentId() + "&populate=1");
			return null;
		}
		
		// otherwise load the incident and display the claims
		Incident incident = new IncidentBMO().findIncidentByID(incidentId);
		if (incident.getClaims() == null || incident.getClaims().isEmpty()) {
			response.sendRedirect("claim_resolution.do?incidentId=" + incidentId);
			return null;
		} else {
			scForm.setIncidentId(incidentId);
			scForm.setClaims(new LinkedHashSet<FsClaim>(incident.getClaims()));
		}
		
		return mapping.findForward(TracingConstants.CLAIM_SELECT_CLAIM);

	}
}