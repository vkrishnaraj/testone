package com.bagnet.nettracer.tracing.actions;

import java.util.Arrays;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import aero.nettracer.serviceprovider.common.db.PrivacyPermissions;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissionsKey;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.forms.PrivacyPermissionsForm;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.ntfs.PrivacyPermissionsUtil;

public class PrivacyPermissionsAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		
		Agent user = (Agent) session.getAttribute("user");
		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1)
				+ ".do", user)
				&& !UserPermissions
						.hasPermission(
								TracingConstants.SYSTEM_COMPONENT_NAME_PRIVACY_PERMISSIONS,
								user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));

		PrivacyPermissionsForm theForm = (PrivacyPermissionsForm)form;
		
		if(request.getParameter("view") != null){
			request.setAttribute("components", Arrays.asList(theForm.getComponentList()));
			PrivacyPermissions def = null;
			PrivacyPermissions req = null;
			try{
				def = PrivacyPermissionsUtil.getPrivacyPermissions(user.getCompanycode_ID(), PrivacyPermissions.AccessLevelType.def);
			    req = PrivacyPermissionsUtil.getPrivacyPermissions(user.getCompanycode_ID(), PrivacyPermissions.AccessLevelType.req);
			} catch (NamingException e){
				ActionMessages errors = new ActionMessages();
				ActionMessage error = new ActionMessage("error.database.connection");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return (mapping.findForward(TracingConstants.ERROR_MAIN));
			}
			if(def == null){
				System.out.println("def is null");
				def = new PrivacyPermissions();
				def.setKey(new PrivacyPermissionsKey(user.getCompanycode_ID(), PrivacyPermissions.AccessLevelType.def));
			}
			if(req == null){
				System.out.println("req is null");
				req = new PrivacyPermissions();
				req.setKey(new PrivacyPermissionsKey(user.getCompanycode_ID(), PrivacyPermissions.AccessLevelType.req));
			}
			
			theForm.setDef(def);
			theForm.setReq(req);
			request.setAttribute("privacyPermissionsForm", theForm);
			return mapping.findForward(TracingConstants.VIEW_PRIVACY_PERMISSIONS);
		}
		
		if(request.getParameter("save") != null){
			boolean success = true;
			if(theForm.getDef()!=null && theForm.getReq() != null){
				PrivacyPermissionsUtil.setPrivacyPermissions(theForm.getDef());
				PrivacyPermissionsUtil.setPrivacyPermissions(theForm.getReq());
			} else {
				success = false;
			}
			
			System.out.println("save");
			if(success){
				request.setAttribute("success", 1);
			} else {
				request.setAttribute("success", 0);
			}
			request.setAttribute("components", Arrays.asList(theForm.getComponentList()));
			return mapping.findForward(TracingConstants.VIEW_PRIVACY_PERMISSIONS);
		}

		return null;
	} 
}
