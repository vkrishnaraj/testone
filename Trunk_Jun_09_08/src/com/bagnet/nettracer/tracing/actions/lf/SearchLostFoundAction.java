package com.bagnet.nettracer.tracing.actions.lf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class SearchLostFoundAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(SearchLostFoundAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SEARCH_LOST_FOUND, user)) {
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		}
		
		return mapping.findForward(TracingConstants.LF_SEARCH_LOST_FOUND);
		
	}
	
}