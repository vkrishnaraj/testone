package com.bagnet.nettracer.tracing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OtherSystemInformationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OtherSystemInformation;
import com.bagnet.nettracer.tracing.db.audit.Audit_OtherSystemInformation;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class OsiAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(); // check session/user
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if(user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		DynaActionForm theForm = (DynaActionForm) form;
		
		String incident_ID =  (String) theForm.get("incident_id");
		
		Session sess = HibernateWrapper.getSession().openSession();
		Incident inc = IncidentBMO.getIncidentByID(incident_ID, sess);
		OtherSystemInformation osi = OtherSystemInformationBMO.getOsi(incident_ID, sess);
		
		switch(inc.getItemtype_ID()) {
			case TracingConstants.LOST_DELAY: 
				if(!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)
						&& !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_OSI_LD, user))
					return (mapping.findForward(TracingConstants.NO_PERMISSION));
				break;
			case TracingConstants.MISSING_ARTICLES: 
				if(!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)
						&& !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_OSI_PIL, user))
					return (mapping.findForward(TracingConstants.NO_PERMISSION));
				break;
			case TracingConstants.DAMAGED_BAG: 
				if(!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)
						&& !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_OSI_DAM, user))
					return (mapping.findForward(TracingConstants.NO_PERMISSION));
				break;
		}
		
		// SAVE OSI
		if (request.getParameter("save") != null) {
			if (osi == null) {
				osi = new OtherSystemInformation();
			}
		
			String text = (String) theForm.get("text");
			 		
			osi.setIncident(inc);
			osi.setInfo(text);
			
			HibernateUtils.save(osi, sess);
			
//			Audit_OtherSystemInformation auditOsi = new Audit_OtherSystemInformation();
//			BeanUtils.copyProperties(auditOsi, osi);
//			auditOsi.setModifying_agent(user);
//			auditOsi.setTime_modified(TracerDateTime.getGMTDate());
//			HibernateUtils.save(auditOsi, sess);
			
		} else {
			// GET OSI OR CREATE NEW OSI
			if (osi == null) {
				osi = new OtherSystemInformation();
				osi.setIncident(inc);
			}
		}
		
		sess.close();
		
		// POPULATE THE FORM
		theForm.set("id", Integer.toString(osi.getId()));
		theForm.set("incident_id", osi.getIncident().getIncident_ID());
		theForm.set("text", osi.getInfo());
		
		if (!UserPermissions.hasIncidentSavePermission(user, inc)) {
			theForm.set("readOnly", "true");
		}
		
		return (mapping.findForward(TracingConstants.FORWARD_OSI));
	}
}