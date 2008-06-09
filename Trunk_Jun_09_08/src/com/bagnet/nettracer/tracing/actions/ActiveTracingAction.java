/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.forms.ActiveTracingForm;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ActiveTracingAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session to see if timedout
		TracerUtils.checkSession(session);

		if (session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		Agent user = (Agent) session.getAttribute("user");

		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)) return (mapping
				.findForward(TracingConstants.NO_PERMISSION));

		if (request.getParameter("search") == null) {
			return (mapping.findForward(TracingConstants.ACTIVE_TRACING));
		}
		BagService bs = new BagService();
		ActiveTracingForm daform = (ActiveTracingForm) form;
		String incident_ID = daform.getIncident_ID();
		ActionMessages errors = new ActionMessages();

		if (incident_ID == null || incident_ID.length() <= 0) {
			ActionMessage error = new ActionMessage("error.noreportnum");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
			return (mapping.findForward(TracingConstants.ACTIVE_TRACING));
		}

		ServletContext sc = getServlet().getServletContext();
		String configfilepath = sc.getRealPath("/") + "/WEB-INF/classes/";

		boolean result = bs.activeTracing(daform, user, configfilepath);
		//List all the on hand reports.
		if (!result) {
			ActionMessage error = new ActionMessage("error.nomatch");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
		} else {
			request.setAttribute("result", "1");
		}
		return (mapping.findForward(TracingConstants.ACTIVE_TRACING));
	}
}