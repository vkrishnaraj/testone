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
			int retention = 0;
			try{
				def = PrivacyPermissionsUtil.getPrivacyPermissions(user.getCompanycode_ID(), PrivacyPermissions.AccessLevelType.def);
			    req = PrivacyPermissionsUtil.getPrivacyPermissions(user.getCompanycode_ID(), PrivacyPermissions.AccessLevelType.req);
			} catch (Exception e){
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
			} else {
				retention = def.getRetention();
			}
			if(req == null){
				System.out.println("req is null");
				req = new PrivacyPermissions();
				req.setKey(new PrivacyPermissionsKey(user.getCompanycode_ID(), PrivacyPermissions.AccessLevelType.req));
			}
			
			theForm.setDef(def);
			theForm.setReq(req);
			theForm.setRetention(retention);
			request.setAttribute("privacyPermissionsForm", theForm);
			return mapping.findForward(TracingConstants.VIEW_PRIVACY_PERMISSIONS);
		}
		
		if(request.getParameter("save") != null){
			boolean success = true;
			if(theForm.getDef()!=null && theForm.getReq() != null){
				//Def permission is automatically applied to Req
				PrivacyPermissions def = theForm.getDef();
				PrivacyPermissions req = theForm.getReq();
				if(def.isAddress())req.setAddress(true);
				if(def.isAmountclaimed())req.setAmountclaimed(true);
				if(def.isAmountpaid())req.setAmountpaid(true);
				if(def.isBag())req.setBag(true);
				if(def.isBdo())req.setBdo(true);
				if(def.isCc())req.setCc(true);
				if(def.isClaimdate())req.setClaimdate(true);
				if(def.isClaimtype())req.setClaimtype(true);
				if(def.isDenialreason())req.setDenialreason(true);
				if(def.isDenied())req.setDenied(true);
				if(def.isDob())req.setDob(true);
				if(def.isDrivers())req.setDrivers(true);
				if(def.isEmail())req.setEmail(true);
				if(def.isFfn())req.setFfn(true);
				if(def.isFraudstatus())req.setFraudstatus(true);
				if(def.isIncdate())req.setIncdate(true);
				if(def.isIncremarks())req.setIncremarks(true);
				if(def.isItin())req.setItin(true);
				if(def.isName())req.setName(true);
				if(def.isPassport())req.setPassport(true);
				if(def.isPhonenumber())req.setPhonenumber(true);
				if(def.isPnrdata())req.setPnrdata(true);
				if(def.isPnrloc())req.setPnrloc(true);
				if(def.isSsn())req.setSsn(true);
				if(def.isTicketamount())req.setTicketamount(true);
				if(def.isTraveldate())req.setTraveldate(true);
				
				def.setRetention(theForm.getRetention());
				req.setRetention(theForm.getRetention());

				if(PrivacyPermissionsUtil.setPrivacyPermissions(def) 
						&& PrivacyPermissionsUtil.setPrivacyPermissions(req)){
					success = true;
				} else {
					success = false;
				}
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
