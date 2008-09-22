/*
 * Created on Feb 14, 2007
 *
 * matt
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;

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

import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue;
import com.bagnet.nettracer.tracing.db.wtq.WtqEraseActionFile;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;
import com.bagnet.nettracer.wt.WorldTracerUtils;
import com.bagnet.nettracer.wt.connector.BetaWtConnector;
import com.ctc.wstx.util.StringUtil;

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
		String wt_http = WorldTracerUtils.getWt_url(user.getCompanycode_ID());
		String wt_url = "http://" + wt_http + "/";
		// if (!user.getStation().getCompany().getVariable().isWTEnabled())
		// return (mapping.findForward(TracingConstants.NO_PERMISSION));

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
			Incident foundinc = WorldTracerUtils.findIncidentByWTID(request
					.getParameter("ahl_id"));
			if (foundinc == null) {
				String result = SpringUtils.getWorldTracerConnector().findAHL(request.getParameter("ahl_id"));
//				String result = BetaWtConnector.getInstance(user.getCompanycode_ID()).findAHL(request.getParameter("ahl_id"));
				request.setAttribute("wt_raw", result);
			} else {
				request.setAttribute("wt_raw_hasinc", "1");
			}
			request.setAttribute("wt_raw_incident", request
					.getParameter("ahl_id"));
			return (mapping
					.findForward(TracingConstants.VIEW_WORLDTRACER_AF_VIEW_RAW_INC));

		} else if (request.getParameter("ohd_id") != null
				&& request.getParameter("rawtext") != null) {
			OHD foundinc = WorldTracerUtils.findOHDByWTID(request
					.getParameter("ohd_id"));
			if (foundinc == null) {
				String result = SpringUtils.getWorldTracerConnector().findOHD(request.getParameter("ohd_id"));
				//String result = BetaWtConnector.getInstance(user.getCompanycode_ID()).findAHL(request.getParameter("ohd_id"));
				request.setAttribute("wt_raw", result);
			} else {
				request.setAttribute("wt_raw_hasinc", "1");
			}

			request.setAttribute("wt_raw_ohd", request.getParameter("ohd_id"));
			return (mapping
					.findForward(TracingConstants.VIEW_WORLDTRACER_AF_VIEW_RAW_INC));
		}

		// if got here and still no action, default to viewaction
		/** ***** view action files ********* */
		String wt_type = request.getParameter("viewaction");
		if (wt_type == null || wt_type.equals("") || wt_type.equals("null"))
			wt_type = "FW";
		String day = request.getParameter("d");
		if (day == null || day.equals("") || day.equals("null"))
			day = "1";
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
				ActionMessage error = new ActionMessage("message.nodata");
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
		int rowsperpage = request.getParameter("rowsperpage") != null ? Integer
				.parseInt(request.getParameter("rowsperpage"))
				: TracingConstants.ROWS_PER_PAGE;
		if (rowsperpage < 1)
			rowsperpage = TracingConstants.ROWS_PER_PAGE;
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
			ArrayList al = new ArrayList();
			for (int i = 0; i < totalpages; i++) {
				al.add(Integer.toString(i));
			}
			request.setAttribute("pages", al);
		}

		if ((request.getParameter("ahl_id") != null && request.getParameter(
				"ahl_id").length() > 9)
				|| (request.getParameter("ohd_id") != null && request
						.getParameter("ohd_id").length() > 9)) {
			resultlist = WorldTracerUtils.findActionFilesbyWTId(request
					.getParameter("ahl_id"), request.getParameter("ohd_id"),
					wt_type, rowsperpage, currpage);
			request.setAttribute("wt_id_specific", "1");
		} else {
			resultlist = WorldTracerUtils.findActionFiles(wt_type, day, user
					.getCompanycode_ID(), agent_station.getWt_stationcode(),
					rowsperpage, currpage);
		}

		
		request.setAttribute("resultlist", resultlist);

		/** ************ end of pagination ************* */
		//System.out.println(resultlist.size()+"------------------");
		return (mapping.findForward(TracingConstants.VIEW_WORLDTRACER_ACTION_FILES));

	}

}