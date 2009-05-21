/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions.admin;

import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.UserGroup;
import com.bagnet.nettracer.tracing.db.audit.Audit_UserGroup;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.audit.AuditGroupUtils;

/**
 * Implementation of <strong>Action </strong> that is responsible for modifying
 * permissions
 * 
 * @author Ankur Gupta
 */
public final class ManagePermissions extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		//if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) +
		// ".do",user))
		//	return (mapping.findForward(TracingConstants.LOGON));

		ActionMessages errors = new ActionMessages();
		DynaValidatorForm dForm = (DynaValidatorForm) form;

		String groupID = "";
		if (request.getParameter("groupId") != null) groupID = request.getParameter("groupId");
		else groupID = "" + user.getUsergroup_id();

		UserGroup group = AdminUtils.getGroup(groupID);

		if (request.getParameter("save") != null) {
			//Retrieve list of all the system component IDs and go through 1-1.
			//delete all the existing one's and add the parent group.
			try {

				Audit_UserGroup audit_group = null;

				//Obtain the group id of
				if (user.getStation().getCompany().getVariable().getAudit_group() == 1) {
					audit_group = AuditGroupUtils.getAuditGroup(group, user);
					if (audit_group != null) {
						HibernateUtils.saveNew(audit_group);
					}
				}

				int audit_id = -1;

				if (audit_group != null) audit_id = audit_group.getAudit_id();

				AdminUtils.saveComponents(groupID, audit_id, request, user);

			} catch (Exception ex) {
				ActionMessage error = new ActionMessage("error.creating.component");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
			if (errors.size() == 0) response.sendRedirect("createGroup.do?companyCode="
					+ group.getCompanycode_ID());
		}
		//Obtain the treemap of all components.
		TreeMap sysComponentMap = AdminUtils.getComponentTreeMap(groupID, user);

		//Get group based on ID.
		request.setAttribute("companyCode", group.getCompanycode_ID());
		request.setAttribute("groupName", group.getDescription());
		request.setAttribute("groupId", groupID);
		request.setAttribute("systemMap", sysComponentMap);
		return mapping.findForward(TracingConstants.VIEW_COMPONENT);
	}
}