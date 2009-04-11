package com.bagnet.nettracer.tracing.actions.wt;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.db.wt.ActionFileStation;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.svc.ActionFileManager;

public class ActionFileSummaryAction extends Action {
	
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
		ActionFileType aft = ActionFileType.valueOf(catName);
		List<Worldtracer_Actionfiles> result= afm.getSummary(companyCode, wtStation, aft, day, user);
		
		List<ActionData> displayList = null;
		if (result  != null) {
			displayList = createDisplayList(result);
		}
		request.setAttribute("afList", displayList);
		request.setAttribute("afType", aft);
		request.setAttribute("day", day);
		
		return mapping.findForward("success");
	}
	
	private List<ActionData> createDisplayList(List<Worldtracer_Actionfiles> tempList) {
		List<ActionData> result = new ArrayList<ActionData>();
		IncidentBMO ibmo = new IncidentBMO();
		
		for(Worldtracer_Actionfiles af : tempList) {
			ActionData ad = new ActionData();
			ad.setAf_id(af.getId());
			if(af.getAction_file_text() != null) 
				ad.setAf_text(StringEscapeUtils.escapeHtml(af.getAction_file_text()).replaceAll("(\\n|\\r)+", "<br />"));
			if(af.getAction_file_summary() != null)
				ad.setAf_summary(StringEscapeUtils.escapeHtml(af.getAction_file_summary()).replaceAll("\\n", "<br />"));
			ad.setWt_incident_id(af.getWt_incident_id());
			if(af.getWt_incident_id() != null && af.getWt_incident_id().trim().length() > 0) {
				Incident inc = ibmo.findIncidentByWtId(af.getWt_incident_id().trim().toUpperCase());
				if (inc != null) {
					ad.setIncident_id(inc.getIncident_ID());
				}
			}
			ad.setWt_ohd_id(af.getWt_ohd_id());
			if(af.getWt_ohd_id() != null && af.getWt_ohd_id().trim().length() > 0) {
				OHD ohd = OhdBMO.findOhdByWtId(af.getWt_ohd_id().trim().toUpperCase());
				if (ohd != null) {
					ad.setOhd_id(ohd.getOHD_ID());
				}
			}
			ad.setItem_number(af.getItem_number());
			result.add(ad);
		}
		return result;
	}

	public class ActionData {
		private long af_id;
		private String wt_incident_id;
		private String wt_ohd_id;
		private String incident_id;
		private String ohd_id;
		private String af_text;
		private String af_summary;
		private int item_number;

		public long getAf_id() {
			return af_id;
		}
		public void setAf_id(long af_id) {
			this.af_id = af_id;
			}

		public String getWt_incident_id() {
			return wt_incident_id;
		}
		public void setWt_incident_id(String wt_incident_id) {
			this.wt_incident_id = wt_incident_id;
		}
		public String getWt_ohd_id() {
			return wt_ohd_id;
		}
		public void setWt_ohd_id(String wt_ohd_id) {
			this.wt_ohd_id = wt_ohd_id;
		}
		public String getIncident_id() {
			return incident_id;
		}
		public void setIncident_id(String incident_id) {
			this.incident_id = incident_id;
		}
		public String getOhd_id() {
			return ohd_id;
		}
		public void setOhd_id(String ohd_id) {
			this.ohd_id = ohd_id;
		}
		public String getAf_text() {
			return af_text;
		}
		public void setAf_text(String af_text) {
			this.af_text = af_text;
		}
		public String getAf_summary() {
			return af_summary;
		}
		public void setAf_summary(String af_summary) {
			this.af_summary = af_summary;
		}
		public int getItem_number() {
			return item_number;
		}
		public void setItem_number(int item_number) {
			this.item_number = item_number;
		}
	}
}
