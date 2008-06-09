package com.bagnet.nettracer.tracing.actions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class CustomizedExceptionHandler extends ExceptionHandler {

	public ActionForward execute(Exception ex, ExceptionConfig exConfig,
			ActionMapping mapping, ActionForm formInstance,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
			
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		// Process this user logoff
		if (user == null) {
			session.invalidate();
			// no session log off
			return mapping.findForward(TracingConstants.LOGON);
		}

		return mapping.findForward(TracingConstants.ERROR_MAIN);
	}
}
