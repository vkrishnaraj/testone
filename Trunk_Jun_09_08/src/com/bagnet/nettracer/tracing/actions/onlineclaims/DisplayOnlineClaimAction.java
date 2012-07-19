package com.bagnet.nettracer.tracing.actions.onlineclaims;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.OnlineClaimsDao;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class DisplayOnlineClaimAction extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		if (session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		Agent user = (Agent) session.getAttribute("user");

		if (!(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CLERICAL_CLAIMS_FEATURES, user)
				|| UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CENTRAL_BAGGAGE_CLAIMS_FEATURES, user))) {
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		}
		
		DynaActionForm dynaForm = (DynaActionForm) form;
		Long claimId = (Long) dynaForm.get("claimId");
		
		OnlineClaimsDao d = new OnlineClaimsDao();
		OnlineClaim c = d.getOnlineClaim(claimId);
		request.setAttribute("claim", c);
		
		if (request.getParameter("ajax") != null) {
			return mapping.findForward(TracingConstants.FORWARD_DISPLAY_OC_AJAX);
		}
		return mapping.findForward(TracingConstants.FORWARD_DISPLAY_OC);
	}
}
