package com.bagnet.nettracer.tracing.actions.wt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.bmo.SpecialFlagBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.forms.CaptchaForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.wt.connector.CaptchaException;
import com.bagnet.nettracer.wt.connector.WebServiceDto;
import com.bagnet.nettracer.wt.connector.WorldTracerWebService;

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
		
		boolean result = false;
		if (thisForm.getCaptcha_text() != null
		    && thisForm.getCaptcha_text().length() > 0) {
			WebServiceDto dto = (WebServiceDto) session
			    .getAttribute(TracingConstants.WEB_SERVICE_DTO);
			dto.setCaptchaText(thisForm.getCaptcha_text());

			if (thisForm.isTaskManagerEntry()) {
				session.setAttribute("REDIRECT_REQUEST_URL", "logon.do?taskmanager=1");
				WorldTracerWebService svc = WorldTracerWebService.getInstance();
				try {
					result = svc.establishWtrConnection(dto);
					if (result) {
						// Get rid of captcha notice
						SpecialFlagBMO.setSpecialFlagResetTimestampByAgent("captcha");
					}
				} catch (CaptchaException e) {
					// Exception is expected....
				}
			}
			
			response.sendRedirect((String) session
			    .getAttribute("REDIRECT_REQUEST_URL"));
			return null;
		} else if (thisForm.isTaskManagerEntry()) {
			session.setAttribute("REDIRECT_REQUEST_URL", "logon.do?taskmanager=1");
			WebServiceDto dto = WorldTracerWebService.getBasicDto(session);
			WorldTracerWebService svc = WorldTracerWebService.getInstance();
			try {
				result = svc.establishWtrConnection(dto);
				if (dto.getCaptcha() == null) {
					// No need to do anything any longer, so redirect
					response.sendRedirect((String) session
						    .getAttribute("REDIRECT_REQUEST_URL"));
					return null;
				}
			} catch (CaptchaException e) {
				// Exception is expected....
			}
			if (result) {
				response.sendRedirect((String) session
					    .getAttribute("REDIRECT_REQUEST_URL"));
				return null;
			} else {
				return mapping.findForward("enterCaptcha");
			}
		}


		return mapping.findForward("enterCaptcha");

	}
}
