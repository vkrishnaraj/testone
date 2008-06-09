package com.bagnet.nettracer.tracing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.WorldTracerUtils;

/**
 * Implementation of <strong>Action </strong> that is responsible for handling
 * forward on-hand bags task. It would forward the bag to the desired location
 * or LZ.
 * 
 * @author Ankur Gupta
 */
public class WorldTracerROHAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		// check session and user validity
		TracerUtils.checkSession(session);
		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_ROH, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));

		ActionMessages errors = new ActionMessages();

		//Obtain a handle on the resources directory.
		MessageResources messages = MessageResources
				.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");

		BagService bs = new BagService();
		DynaValidatorForm dForm = (DynaValidatorForm) form;
	

		if (request.getParameter("save") != null && request.getParameter("save").length() > 0) {
			String ahl_id = dForm.getString("wt_ahl_id");
			if (ahl_id == null || ahl_id.trim().length() != 10) {
				ActionMessage error = new ActionMessage("error.wt_err_enter_ahl_id");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				// check to see if this ahl is in current user's station
				String as = ahl_id.substring(0,3).toUpperCase();
				String aa = ahl_id.substring(3,5).toUpperCase();
				if (!aa.equals(user.getCompanycode_ID()) || !as.equals(user.getStation().getStationcode())) {
					ActionMessage error = new ActionMessage("error.wt_err_invalid_ahl_id_same_station");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				} else {
					String ohd_id = dForm.getString("wt_ohd_id");
					if (ohd_id == null || ohd_id.trim().length() != 10) {
						ActionMessage error = new ActionMessage("error.wt_err_enter_ohd_id");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
					} else {
						String result = postWTROH(dForm, user);
						request.setAttribute("result", result);
					}
					
				}
			}
		} else {
			// new form, check to see if form has ohd wt_id with it.
			if (request.getParameter("wt_id") != null && request.getParameter("wt_id").length() == 10) {
				OHD foundohd = WorldTracerUtils.findOHDByWTID(request.getParameter("wt_id"));
				if (foundohd != null) {
					dForm.set("wt_ohd_id", foundohd.getWt_id());
					
				}
			}
			
			dForm.set("ag", user.getUsername() + "/" + user.getCompanycode_ID());
		}


		//Allow modifications to the forward.
		return mapping.findForward(TracingConstants.VIEW_WORLDTRACER_ROH);
	}
	
	public String postWTROH(DynaValidatorForm dForm, Agent user) {
		// make the request
		String company = user.getCompanycode_ID();
		String ahl_id = dForm.getString("wt_ahl_id");
		StringBuffer ohd_string = new StringBuffer();
		
		String ohd_id = dForm.getString("wt_ohd_id").toUpperCase();
		String fi = dForm.getString("fi").toUpperCase();
		String ag = dForm.getString("ag").toUpperCase();
		String tx1 = dForm.getString("teletype_address1").toUpperCase();
		String tx2 = dForm.getString("teletype_address2").toUpperCase();
		String tx3 = dForm.getString("teletype_address3").toUpperCase();
		String tx4 = dForm.getString("teletype_address4").toUpperCase();
		String name = dForm.getString("lname").toUpperCase();
		
		ohd_string.append(".OHD " + ohd_id);
		if (fi.length() > 0) ohd_string.append(".FI " + fi);
		if (ag.length() > 0) ohd_string.append(".AG " + ag);
		if (tx1.length() > 0) ohd_string.append(".TX " + tx1);
		if (tx2.length() > 0) ohd_string.append(".TX " + tx2);
		if (tx3.length() > 0) ohd_string.append(".TX " + tx3);
		if (tx4.length() > 0) ohd_string.append(".TX " + tx4);
		if (name.length() > 0) ohd_string.append(".NM " + name);
		
		HttpClient client = WorldTracerUtils.connectWT(WorldTracerUtils.wt_suffix_airline + "/",company);
		String test = WorldTracerUtils.postROH(client, company, ahl_id,ohd_string.toString());
		
		// for now return ohd_string
		test = ohd_string.toString();
		
		return test;
	}
}