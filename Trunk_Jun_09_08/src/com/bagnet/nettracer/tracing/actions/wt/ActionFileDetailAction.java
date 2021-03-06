package com.bagnet.nettracer.tracing.actions.wt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionRedirect;

import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.db.wt.ActionFileStation;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerTransaction.Result;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.connector.CaptchaException;
import com.bagnet.nettracer.wt.connector.WorldTracerWebService;
import com.bagnet.nettracer.wt.svc.ActionFileManager;
import com.bagnet.nettracer.wt.svc.WorldTracerService;

public class ActionFileDetailAction extends Action {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);
		if(session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		Agent user = (Agent) session.getAttribute("user");

		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1)
				+ ".do", user)
				&& !UserPermissions
						.hasPermission(
								TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_ACTION_FILES,
								user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		
		Station agentStation = null;
		if (UserPermissions.hasPermission(
				TracingConstants.SYSTEM_COMPONENT_NAME_CBRO_VIEW, user)) {
			if (request.getParameter("cbroStation") != null
					&& !((String) request.getParameter("cbroStation"))
							.equals("")) {
				Station station = StationBMO.getStation((String) request
						.getParameter("cbroStation"));
				if (station.getCompany().getCompanyCode_ID().equals(
						user.getCompanycode_ID())) {
					session.setAttribute("cbroStationID", request
							.getParameter("cbroStation"));
					if (UserPermissions.hasPermission(
							TracingConstants.SYSTEM_COMPONENT_NAME_CBRO_MGMT,
							user)) {
						user.setStation(StationBMO.getStationById(Integer
								.parseInt(request.getParameter("cbroStation")),
								user.getCompanycode_ID()));
					}
				}
			}
			if (session.getAttribute("cbroStationID") != null) {
				agentStation = StationBMO.getStation((String) session
						.getAttribute("cbroStationID"));
			} else {
				agentStation = user.getStation();
			}
		} else {
			agentStation = user.getStation();
		}
		

		ActionMessages errors = new ActionMessages();
		
		String companyCode = user.getCompanycode_ID();
		ActionFileManager afm = SpringUtils.getActionFileManager();
		String wtStation = agentStation.getWt_stationcode();
		ActionFileStation afStation = null;

		if (wtStation == null || wtStation.trim().length() < 1) {
			ActionMessage error = new ActionMessage("message.no.wt.station");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
			mapping.findForward("success");
		}
		
		String catName = request.getParameter("category");
		int day = Integer.parseInt(request.getParameter("day"));
		int itemNum = Integer.parseInt(request.getParameter("itemNum"));
		ActionFileType aft = ActionFileType.valueOf(catName);
		String seq = request.getParameter("seq");
		try {
			afm.updateDetails(companyCode, wtStation, aft, seq, day, itemNum, user, WorldTracerWebService.getBasicDto(session));
		} catch (CaptchaException e) {
			session.setAttribute("REDIRECT_REQUEST_URL", request.getRequestURL().toString());
			response.sendRedirect("wtCaptcha.do");
			return null;
		}
		
		ActionRedirect redirect = new ActionRedirect(mapping.findForward("success"));
		redirect.addParameter("category", catName);
		redirect.addParameter("day", Integer.toString(day));
		redirect.addParameter("currpage", request.getParameter("currpage"));
		redirect.addParameter("rowsperpage", request.getParameter("rowsperpage"));
		return redirect;
	}
}
