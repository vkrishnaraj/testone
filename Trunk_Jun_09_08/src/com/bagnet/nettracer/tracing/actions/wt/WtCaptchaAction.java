package com.bagnet.nettracer.tracing.actions.wt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.forms.CaptchaForm;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.wt.svc.WorldTracerService;

public class WtCaptchaAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();

		CaptchaForm thisForm = (CaptchaForm) form;
		// check session
		TracerUtils.checkSession(session);
		if (session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		WorldTracerService service = SpringUtils.getWorldTracerService();
		if (thisForm.getCaptcha_text() != null && thisForm.getCaptcha_text().length() > 0) {
			service.getWtConnector().returnUserCaptcha(thisForm.getCaptcha_text());
			response.sendRedirect((String)session.getAttribute("REDIRECT_REQUEST_URL"));
			return null;
		} 
		
		service.getWtConnector().doesUserNeedToEnterCaptcha(true);
		return mapping.findForward("enterCaptcha");
		
		
	}
}
