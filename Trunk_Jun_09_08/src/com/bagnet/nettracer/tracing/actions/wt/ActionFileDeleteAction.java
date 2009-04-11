package com.bagnet.nettracer.tracing.actions.wt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionRedirect;

import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.db.wt.ActionFileStation;
import com.bagnet.nettracer.tracing.db.wtq.WtqEraseActionFile;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;
import com.bagnet.nettracer.wt.WorldTracerUtils;
import com.bagnet.nettracer.wt.svc.ActionFileManager;

public class ActionFileDeleteAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		// check session
		TracerUtils.checkSession(session);
		if (session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		ActionMessages errors = new ActionMessages();

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

		String companyCode = user.getCompanycode_ID();
		String catName = request.getParameter("category");
		int day = Integer.parseInt(request.getParameter("day"));
		int itemNum = Integer.parseInt(request.getParameter("itemNum"));
		ActionFileType aft = ActionFileType.valueOf(catName);
		String wtStation = agentStation.getWt_stationcode();
		
		ActionFileManager afm = SpringUtils.getActionFileManager();
		afm.eraseActionFile(companyCode, wtStation, aft, day, itemNum, user);

		ActionRedirect redirect = new ActionRedirect(mapping.findForward("success"));
		redirect.addParameter("category", catName);
		redirect.addParameter("day", Integer.toString(day));
		redirect.addParameter("deleted", 1);
		return redirect;
	}
}
