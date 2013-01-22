package com.bagnet.nettracer.tracing.actions;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;

public class ResetPasswordAction extends Action {


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// Extract attributes we will need
		Locale locale = getLocale(request);
		Agent agent = null;
		HttpSession session = request.getSession();

		// user must be logged in first
		if (session.getAttribute("usertemp") == null) {
			response.sendRedirect("logoff.do");
			return null;
		} else {
			agent = (Agent) session.getAttribute("usertemp");
		}

		Company comp = CompanyBMO.getCompany(agent.getCompanycode_ID());
		
		if (request.getParameter("resetpass") != null) {

			// Validate the request parameters specified by the user
			ActionMessages errors = new ActionMessages();
			String password1 = (String) PropertyUtils.getSimpleProperty(form, "password1");
			String password2 = (String) PropertyUtils.getSimpleProperty(form, "password2");
			String password1sha1 = StringUtils.sha1_256(password1.trim(),true);
			
			
			if (password1.trim().length() == 0) {
				ActionMessage error = new ActionMessage("error.password.required");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return mapping.findForward(TracingConstants.PASS_RESET);
			} else if (password2.trim().length() == 0) {
				ActionMessage error = new ActionMessage("error.password2.required");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return mapping.findForward(TracingConstants.PASS_RESET);
			} else if (!password1.equals(password2)) {
				ActionMessage error = new ActionMessage("error.password.match");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return mapping.findForward(TracingConstants.PASS_RESET);
			} else if (SecurityUtils.lastXPasswords(agent.getAgent_ID(),
					comp!=null?comp.getVariable().getPass_x_history():20, 
					password1sha1)){
				ActionMessage error = new ActionMessage("error.password.sameasold");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return mapping.findForward(TracingConstants.PASS_RESET);
			} else if (!SecurityUtils.isPolicyAcceptablePassword(agent.getCompanycode_ID(), password1.trim(), agent.getUsername(), request, false)) {
				return mapping.findForward(TracingConstants.PASS_RESET);
			} 
			
			// change pass and send user back to login
			agent.setPassword(password1sha1);
			SecurityUtils.insertPasswordHistory(agent.getAgent_ID(), password1sha1);
			agent.setLast_pass_reset_date(new Date());
			agent.setReset_password(false);
			HibernateUtils.save(agent);
			ActionMessage error = new ActionMessage("result.passwordchanged");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
			session.invalidate();
			return mapping.findForward(TracingConstants.LOGON);
		}
		
		response.addHeader("Pragma", "No-cache");
		response.addHeader("Cache-Control", "no-cache");
		response.addDateHeader("Expires", 1);
		return mapping.findForward(TracingConstants.PASS_RESET);
	}
}