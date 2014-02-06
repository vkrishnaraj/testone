/*
 * Created on Feb 14, 2007
 *
 * matt
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import aero.nettracer.serviceprovider.wt_1_0.common.Ahl;
import aero.nettracer.serviceprovider.wt_1_0.common.Ohd;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.wtq.WtqEraseActionFile;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;
import com.bagnet.nettracer.wt.WorldTracerRecordNotFoundException;
import com.bagnet.nettracer.wt.WorldTracerUtils;
import com.bagnet.nettracer.wt.connector.WebServiceDto;
import com.bagnet.nettracer.wt.connector.WorldTracerConnector;
import com.bagnet.nettracer.wt.connector.WorldTracerWebService;

/**
 * @author matt
 * 
 */
public class WorldTracerAFAction extends Action {
	private static Logger logger = Logger.getLogger(WorldTracerAFAction.class);

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

		Station agent_station = null;
		if (session.getAttribute("cbroStationID") != null) {
			agent_station = StationBMO.getStation((String) session
					.getAttribute("cbroStationID"));
		} else {
			agent_station = user.getStation();
		}

		/** ********* view raw ahl / ohd text ************* */
		if (request.getParameter("ahl_id") != null
				&& request.getParameter("rawtext") != null) {
			WorldTracerConnector wtc;
			Ahl result = null;
			wtc = WorldTracerWebService.getInstance();
			try {
				WebServiceDto dto = new WebServiceDto();
				dto.setCronUser(false);
				dto.setUseAvailableConnectionsIfAvailable(true);
				result = wtc.findAHL(request.getParameter("ahl_id"), dto);
			} catch (WorldTracerRecordNotFoundException ex) {
				logger.error("ahl not found");
				request.setAttribute("file_not_found", "1");
			} catch (RuntimeException e) {

				e.printStackTrace();
			} finally {
				wtc.logout();
			}
			if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_IMPORT_AHL, user)){
				Incident inc=WorldTracerUtils.findIncidentByWTID(request.getParameter("ahl_id"));
				if(inc==null){
					request.setAttribute("allowImport",1);
				}
			}
			
			request.setAttribute("wt_raw", result);
			request.setAttribute("wt_raw_incident", request
					.getParameter("ahl_id"));
			return (mapping
					.findForward(TracingConstants.VIEW_WORLDTRACER_AHL));


		} else if (request.getParameter("ohd_id") != null
				&& request.getParameter("rawtext") != null) {
			WorldTracerConnector wtc = WorldTracerWebService.getInstance();
			Ohd result = null;
			try {
				wtc.initialize();
				result = wtc.findOHD(request.getParameter("ohd_id"), wtc.getDto(session));

			} catch (WorldTracerRecordNotFoundException ex) {
				logger.error("ohd not found");
				request.setAttribute("file_not_found", "1");
			}
			catch (RuntimeException e) {
				logger.error("error getting wt ohd", e);
			} finally {
				wtc.logout();
			}

			request.setAttribute("wt_raw", result);
			request.setAttribute("wt_raw_ohd", request.getParameter("ohd_id"));
			return (mapping
					.findForward(TracingConstants.VIEW_WORLDTRACER_OHD));
		}

		// if got here and still no action, default to viewaction
		/** ***** view action files ********* */
		String wt_type = request.getParameter("viewaction");
		if (wt_type == null || wt_type.equals("") || wt_type.equals("null"))
			wt_type = "FW";
		String day = request.getParameter("d");
		if(day == null || day.trim().equals("") || day.trim().equals("null")) {
			if((request.getParameter("ahl_id") != null && request.getParameter("ahl_id").length() > 9)
					|| (request.getParameter("ohd_id") != null && request.getParameter("ohd_id").length() > 9)) {
				day = "-1";
			}
			else {
				day = "1";
			}
		}

		request.setAttribute("af_day", day);

		if (wt_type == null || wt_type.equals(""))
			wt_type = "FW";
		request.setAttribute("wt_type", wt_type.toUpperCase());

		// check if deleting action file
		if (request.getParameter("delete") != null
				&& request.getParameter("delete").length() > 0) {
			Worldtracer_Actionfiles waf = WorldTracerUtils.findActionFileByID(Integer.parseInt(request.getParameter("delete")));
			boolean deleted = false;
			if(waf != null) {
				//queue for WT deletion and delete from our database
				WtqEraseActionFile wq = new WtqEraseActionFile();
				wq.setAgent(user);
				wq.setCreatedate(TracerDateTime.getGMTDate());
				wq.setAf_id(waf.generateId());
				WorldTracerQueueUtils.createOrReplaceQueue(wq);
				deleted = WorldTracerUtils.deleteActionFiles(request
						.getParameter("delete"));
			}

			if (!deleted) {
				ActionMessage error = new ActionMessage("message.no_action_file");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				request.setAttribute("afdeleted", "1");
			}
		}

		int rowcount = -1;
		ArrayList<Worldtracer_Actionfiles> resultlist = new ArrayList<Worldtracer_Actionfiles>();

		// get action file by type and date or by id
		if ((request.getParameter("ahl_id") != null && request.getParameter(
				"ahl_id").length() > 9)
				|| (request.getParameter("ohd_id") != null && request
						.getParameter("ohd_id").length() > 9)) {
			resultlist = WorldTracerUtils.findActionFilesbyWTId(request
					.getParameter("ahl_id"), request.getParameter("ohd_id"),
					wt_type, 0, 0);
			request.setAttribute("wt_id_specific", "1");
		} else {
			if(agent_station.getWt_stationcode() != null && agent_station.getWt_stationcode().trim().length() > 0) {
			resultlist = WorldTracerUtils.findActionFiles(wt_type, day, user
					.getCompanycode_ID(), agent_station.getWt_stationcode(),
					0, 0);
			}
		}
		rowcount = resultlist.size();


		/** ************ pagination ************* */
		int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
		request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
		int totalpages = 0;

		int currpage = request.getParameter("currpage") != null ? Integer
				.parseInt(request.getParameter("currpage")) : 0;
		if (request.getParameter("nextpage") != null
				&& request.getParameter("nextpage").equals("1"))
			currpage++;
		if (request.getParameter("prevpage") != null
				&& request.getParameter("prevpage").equals("1"))
			currpage--;

		request.setAttribute("currpage", Integer.toString(currpage));

		// find out total pages
		totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

		if (totalpages <= currpage) {
			currpage = 0;
			request.setAttribute("currpage", "0");
		}

		if (currpage + 1 == totalpages)
			request.setAttribute("end", "1");
		if (totalpages > 1) {
			ArrayList<String> al = new ArrayList<String>();
			for (int i = 0; i < totalpages; i++) {
				al.add(Integer.toString(i));
			}
			request.setAttribute("pages", al);
		}
		
		List<Worldtracer_Actionfiles> tempList = resultlist.subList(currpage * rowsperpage, ((currpage* rowsperpage) + rowsperpage) > resultlist.size() ? resultlist.size() : ((currpage* rowsperpage) + rowsperpage));
		
		List<ActionData> displayList = createDisplayList(tempList);

		request.setAttribute("resultlist", displayList);

		/** ************ end of pagination ************* */
		//System.out.println(resultlist.size()+"------------------");
		return (mapping.findForward(TracingConstants.VIEW_WORLDTRACER_ACTION_FILES));

	}
	
	private List<ActionData> createDisplayList(List<Worldtracer_Actionfiles> tempList) {
		List<ActionData> result = new ArrayList<ActionData>();
		IncidentBMO ibmo = new IncidentBMO();
		
		for(Worldtracer_Actionfiles af : tempList) {
			ActionData ad = new ActionData();
			ad.setAf_id(af.getId());
			ad.setAf_text(StringEscapeUtils.escapeHtml(af.getAction_file_text()).replaceAll("\\n", "<br />"));
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

		
		
	}

}