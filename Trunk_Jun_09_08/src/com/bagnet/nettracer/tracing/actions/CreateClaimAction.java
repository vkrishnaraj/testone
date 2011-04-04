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

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CreateClaimAction extends CheckedAction {
	
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


		String url = "claim_resolution.do?createNew=1";
		boolean isNtUser = PropertyBMO.isTrue("nt.user");

		if (isNtUser) {
			if (request.getParameter("populate") != null) {
				String incidentId = request.getParameter("incidentId");
				if (incidentId != null && !incidentId.isEmpty()) {
					url += "&populate=1&incidentId=" + incidentId;
					response.sendRedirect(url);
					return null;
				} else {
					return (mapping.findForward(TracingConstants.CLAIM_CREATE_NEW));
				}
			} else if (request.getParameter("skipPrepopulate") != null) {
				session.setAttribute("incidentForm", null);
				response.sendRedirect(url);
				return null;
			} else {
				return (mapping.findForward(TracingConstants.CLAIM_CREATE_NEW));
			}
		} else {
			response.sendRedirect(url);
			return null;
		}
	}
}