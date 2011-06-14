package com.bagnet.nettracer.tracing.actions.lf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import aero.nettracer.lf.services.LFServiceWrapper;
import aero.nettracer.lf.services.LFUtils;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.forms.lf.LostReportForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class LostReportAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(LostReportAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_LOST_REPORT, user)) {
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		}
		
		LFUtils.getLists(user, session);
		
		LostReportForm lrForm = (LostReportForm) form;
		lrForm.setDateFormat(user.getDateformat().getFormat());
		
		LFLost lostReport = null;
		if (request.getParameter("lostId") != null) {
			long id = Long.parseLong(request.getParameter("lostId"));
			lostReport = LFServiceWrapper.getInstance().getLostReport(id);
		} else if (request.getParameter("save") != null) {
			lostReport = lrForm.getLost();
			LFServiceWrapper.getInstance().saveOrUpdateLostReport(lostReport);
		} else {
			lostReport = LFUtils.createLFLost(user);
		}

		lrForm.setLost(lostReport);
		return mapping.findForward(TracingConstants.LF_CREATE_LOST_REPORT);
		
	}
	
}