package com.bagnet.nettracer.tracing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class ToggleAction extends Action {


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(); // check session/user
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		Integer toggleType = (Integer) PropertyUtils.getSimpleProperty(form, "toggleType");
		switch (toggleType) {
			case 0:
				// FONT TOGGLE
				String size = (String) PropertyUtils.getSimpleProperty(form, "fontSize");
				session.setAttribute("bodyFontSize", size);
				Integer idx = (Integer) PropertyUtils.getSimpleProperty(form, "fsarIdx");
				session.setAttribute("fsarIdx", idx);
				break;
			case 1:
				// SCANNER SEARCH TOGGLE
				boolean toggleTagSearch = false;
				if (session.getAttribute("toggleTagSearch") != null) {  
					toggleTagSearch = (Boolean) session.getAttribute("toggleTagSearch");
				}
				session.setAttribute("toggleTagSearch", new Boolean(!toggleTagSearch));
				break;
		}

		return (mapping.findForward("success"));
	}
}
