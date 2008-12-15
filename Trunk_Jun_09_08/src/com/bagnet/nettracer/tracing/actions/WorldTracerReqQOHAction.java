package com.bagnet.nettracer.tracing.actions;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestQoh;
import com.bagnet.nettracer.tracing.forms.WorldTracerReqQOHForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;

public class WorldTracerReqQOHAction extends Action {

	@Override
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

		if (!UserPermissions.hasPermission(
				TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_REQ_QOH, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		
		WorldTracerReqQOHForm theform = (WorldTracerReqQOHForm) form;
		
		if (theform == null || request.getParameter("clear") != null) {
			theform = new WorldTracerReqQOHForm();
			session.setAttribute("worldTracerReqQOHForm", theform);
		}
		
		ActionMessages errors = new ActionMessages();
		
		if (request.getParameter("save") != null) {
			if (theform.getFromAirline() == null || theform.getFromAirline().trim().length() < 1) {
				ActionMessage error = new ActionMessage("error.noAirline");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				// Do the insert wt forward and wt_queue into database
				
				ActionMessage error = this.insertWtReqQoh(theform, user);
				if(error == null){
					return (mapping.findForward(TracingConstants.SEND_WT_ROH_SUCCESS));
				}
				else {
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				}
			}

			return (mapping.findForward(TracingConstants.VIEW_WORLDTRACER_REQ_QOH));
		}
		
		return mapping.findForward(TracingConstants.VIEW_WORLDTRACER_REQ_QOH);
	}

	private ActionMessage insertWtReqQoh(WorldTracerReqQOHForm theform, Agent user) {
		WtqRequestQoh wtq = new WtqRequestQoh();
		
		IncidentBMO ibmo = new IncidentBMO();
		Incident inc = ibmo.findIncidentByWtId(theform.getMatchingAhl());
		if(inc == null ) {
			return new ActionMessage("error.wt_err_no_inc_for_ahl");
		}
		wtq.setAgent(user);
		wtq.setBagTagNumber(theform.getBagTag());
		wtq.setFromAirline(theform.getFromAirline());
		wtq.setFromStation(theform.getFromStation());
		wtq.setFurtherInfo(theform.getFurtherInfo());
		wtq.setIncident(inc);
		wtq.setTeletypes(new HashSet<String>(theform.getTeletypes()));
		try {
			WorldTracerQueueUtils.createOrReplaceQueue(wtq);
		} catch (Exception e) {
			return new ActionMessage("error.wt_err_database_queue_error");
		}
		return null;
	}
}
