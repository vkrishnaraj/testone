package com.bagnet.nettracer.tracing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.integrations.events.ClientEventHandler;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class PushToCrmAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);
		Agent user = (Agent) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		// if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1)
		// + ".do", user)) return (mapping
		// .findForward(TracingConstants.NO_PERMISSION));

		String incidentId = request.getParameter("incident_id");
		IncidentForm iForm = (IncidentForm) session.getAttribute("incidentForm");

		try {
			Session sess = HibernateWrapper.getSession().openSession();
			Incident i = (Incident) sess.load(Incident.class, incidentId);
			int itemType = i.getItemtype_ID();
			switch (itemType) {
			case TracingConstants.LOST_DELAY:
				if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PUSH_LD, user)) {
					return mapping.findForward(TracingConstants.AJAX_PUSH_TO_CRM);
				}
				break;
			case TracingConstants.DAMAGED_BAG:
				if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PUSH_DAM, user)) {
					return mapping.findForward(TracingConstants.AJAX_PUSH_TO_CRM);
				}
				break;
			case TracingConstants.MISSING_ARTICLES:
				if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PUSH_PIL, user)) {
					return mapping.findForward(TracingConstants.AJAX_PUSH_TO_CRM);
				}
				break;
			}

			sess.close();
		} catch (Exception e) {
			return mapping.findForward(TracingConstants.AJAX_PUSH_TO_CRM);
		}

		if (incidentId != null && incidentId.equals(iForm.getIncident_ID())) {
			ClientEventHandler e = (ClientEventHandler) SpringUtils.getBean(SpringUtils.EVENT_HANDLER);

			try {
				Incident i = e.sendCrm(incidentId, null);
				if (i != null) {

					iForm.setCrmFile(i.getCrmFile());
				}
			} catch (Exception ex) {

			}
		}

		return mapping.findForward(TracingConstants.AJAX_PUSH_TO_CRM);
	}

}