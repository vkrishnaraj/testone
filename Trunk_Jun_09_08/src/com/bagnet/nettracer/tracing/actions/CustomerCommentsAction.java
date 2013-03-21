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
import com.bagnet.nettracer.tracing.bmo.CustomerViewableCommentBMO;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.CustomerViewableComment;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.audit.Audit_CustomerViewableComment;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class CustomerCommentsAction extends Action {
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
		try {
			Incident inc = IncidentBMO.getIncidentByID(incident_ID, sess);
			
			CustomerViewableComment comment = CustomerViewableCommentBMO.getComment(incident_ID, sess);
			
			if(!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)
					&& !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUSTOMER_COMMENTS, user))
				return (mapping.findForward(TracingConstants.NO_PERMISSION));
			
			// SAVE OSI
			if (request.getParameter("save") != null) {
				if (comment == null) {
					comment = new CustomerViewableComment();
				}
			
				String text = (String) theForm.get("text");
				 		
				comment.setIncident(inc);
				comment.setComment(text);
				
				HibernateUtils.save(comment, sess);
				
	//			Audit_CustomerViewableComment auditComment = new Audit_CustomerViewableComment();
	//			BeanUtils.copyProperties(auditComment, comment);
	//			auditComment.setModifying_agent(user);
	//			auditComment.setTime_modified(TracerDateTime.getGMTDate());
	//			HibernateUtils.save(auditComment, sess);
	
				
				//OtherSystemInformationBMO.saveOsi(inc, osi);
			} else {
				// GET OSI OR CREATE NEW OSI
				if (comment == null) {
					comment = new CustomerViewableComment();
					comment.setIncident(inc);
				}
			}
		
			// POPULATE THE FORM
			theForm.set("id", Integer.toString(comment.getId()));
			theForm.set("incident_id", comment.getIncident().getIncident_ID());
			theForm.set("text", comment.getComment());
			
			
			if (!UserPermissions.hasIncidentSavePermission(user, inc)) {
				theForm.set("readOnly", "true");
			}
			
			return (mapping.findForward(TracingConstants.FORWARD_CUSTOMER_COMMENTS));
		} finally {
		
			sess.close();
		}
	}
}