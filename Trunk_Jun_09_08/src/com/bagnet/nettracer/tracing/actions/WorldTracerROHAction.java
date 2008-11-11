package com.bagnet.nettracer.tracing.actions;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.WT_ROH;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestOhd;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;
import com.bagnet.nettracer.wt.WorldTracerUtils;
import com.bagnet.nettracer.wt.connector.BetaWtConnector;

/**
 * Implementation of <strong>Action </strong> that is responsible for handling
 * forward on-hand bags task. It would forward the bag to the desired location
 * or LZ.
 * 
 * @author Ankur Gupta
 */

public class WorldTracerROHAction extends Action {
	
	private static final Logger logger = Logger.getLogger(WorldTracerROHAction.class);
	
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
						ActionMessage error = this.InsertWtRoh(dForm, user);
						if(error != null) {
							errors.add(ActionMessages.GLOBAL_MESSAGE, error);
							saveMessages(request, errors);
						}
						else {
							return (mapping.findForward(TracingConstants.SEND_WT_ROH_SUCCESS));
						}
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
	private ActionMessage InsertWtRoh(DynaValidatorForm theform,Agent user){
		IncidentBMO ibmo = new IncidentBMO();
		WtqRequestOhd wtq = new WtqRequestOhd();
		wtq.setAgent(user);
		wtq.setFurtherInfo(theform.getString("fi"));
		try {
			Incident inc = ibmo.findIncidentByWtId(theform.getString("wt_ahl_id"));
			if (inc == null) {
				return new ActionMessage("error.wt_err_no_inc_for_ahl");
			}
			wtq.setIncident(inc);
			Set<String> teletypes = new HashSet<String>();
			if(theform.getString("teletype_address1") != null && theform.getString("teletype_address1").trim().length() > 3) {
				teletypes.add(theform.getString("teletype_address1"));
			}
			if(theform.getString("teletype_address2") != null && theform.getString("teletype_address2").trim().length() > 3) {
				teletypes.add(theform.getString("teletype_address2"));
			}
			if(theform.getString("teletype_address3") != null && theform.getString("teletype_address3").trim().length() > 3) {
				teletypes.add(theform.getString("teletype_address3"));
			}
			if(theform.getString("teletype_address4") != null && theform.getString("teletype_address4").trim().length() > 3) {
				teletypes.add(theform.getString("teletype_address4"));
			}
			wtq.setTeletypes(teletypes);
			wtq.setWt_ohd(theform.getString("wt_ohd_id"));
			
			WorldTracerQueueUtils.createOrReplaceQueue(wtq);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.warn("unable to complete Request OHD", e);
			return new ActionMessage("error.wt_err_database_queue_error");
		}
		
		return null;
	}
}