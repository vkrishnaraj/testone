package com.bagnet.nettracer.tracing.actions.wt;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.exceptions.WorldTracerDisabledException;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.db.wt.ActionFileCount;
import com.bagnet.nettracer.tracing.db.wt.ActionFileStation;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerLockException;
import com.bagnet.nettracer.wt.connector.CaptchaException;
import com.bagnet.nettracer.wt.connector.WorldTracerWebService;
import com.bagnet.nettracer.wt.svc.ActionFileManager;

public class ActionFileCountAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
		ActionFileManager afm = SpringUtils.getActionFileManager();
		
		
		
		String wtStation = agentStation.getWt_stationcode();
		ActionFileStation afStation = null;

		if (wtStation == null || wtStation.trim().length() < 1) {
			ActionMessage error = new ActionMessage("message.no.wt.station");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
			mapping.findForward("success");
		}

		try {
			afStation = afm.getCounts(companyCode, wtStation, user, WorldTracerWebService.getBasicDto(session));
			request.setAttribute("afStation", afStation);
			if(afStation == null || afStation.getCountList() == null){
				request.setAttribute("afCounts", null);
			} else {
				if(afStation.getCountList().size() == 0){
					request.setAttribute("countListEmpty",1);
				}
				
				java.util.Collection<ActionFileCount> countCollection = afStation.getCountList();
				ArrayList <ActionFileCount> countList = new ArrayList<ActionFileCount>(countCollection);
				
				HashMap <ActionFileType, ActionFileCount> temp = new HashMap<ActionFileType, ActionFileCount>();
				for(ActionFileType type:ActionFileType.values()){
					ActionFileCount afc = new ActionFileCount(type);
					temp.put(type, afc);
				}
				
				for(ActionFileCount afc:countList){
					temp.remove(afc.getAf_type());
				}
				
				countList.addAll(temp.values());
				java.util.Collections.sort(countList);
				
				request.setAttribute("afCounts", countList);
			}
		} catch (CaptchaException e) {
			session.setAttribute("REDIRECT_REQUEST_URL", request.getRequestURL().toString());
			response.sendRedirect("wtCaptcha.do");
			return null;
		} catch (WorldTracerLockException ex) {
			ActionMessage error = new ActionMessage("message.wt.af.lock.error");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
			mapping.findForward("error");
		} catch (WorldTracerDisabledException e) {
			ActionMessage error = new ActionMessage("message.wt.disabled");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
			mapping.findForward("error");
		} catch (WorldTracerException e) {
			ActionMessage error = new ActionMessage("message.wt.error");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
			mapping.findForward("error");
		}

		return mapping.findForward("success");
	}
}
