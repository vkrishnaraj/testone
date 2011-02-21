package com.bagnet.nettracer.tracing.actions.salvage;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.SalvageDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.salvage.Salvage;
import com.bagnet.nettracer.tracing.forms.salvage.SalvageSearchForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class SalvageSearchAction extends CheckedAction {

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		TracerUtils.checkSession(session);

		if (session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		Agent agent = (Agent) session.getAttribute("user");
		Set<Salvage> resultSet = SalvageDAO.getSalvagesFromSearchForm((SalvageSearchForm) form, agent);

		if (resultSet == null) {
			resultSet = new LinkedHashSet<Salvage>();
		} else {
			request.setAttribute("resultList", resultSet);

		}

		return mapping.findForward(TracingConstants.SALVAGE_SEARCH);
	}

}
