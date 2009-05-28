/*
 * Created on Feb 14, 2007
 *
 * matt
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ViewIncomingIncidents extends Action {
	private static Logger logger = Logger.getLogger(ViewIncomingIncidents.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		if (session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		Agent user = (Agent) session.getAttribute("user");
		
		Station agent_station = null;
		if (session.getAttribute("cbroStationID") != null) {
			agent_station = StationBMO.getStation((String) session.getAttribute("cbroStationID"));
		} else {
			agent_station = user.getStation();
		}
		
		request.setAttribute("highlite", TracingConstants.SYSTEM_COMPONENT_NAME_INCOMING_INCIDENTS);


		BagService bs = new BagService();

		SearchIncidentForm daform = (SearchIncidentForm) form;
		daform.setStationassigned_ID(agent_station.getStation_ID());
		int[] statuses = new int[3];
		statuses[0] = TracingConstants.MBR_STATUS_OPEN;
		statuses[1] = TracingConstants.MBR_STATUS_PENDING;
		statuses[2] = TracingConstants.MBR_STATUS_TEMP;
		daform.setStatus_IDs(statuses);
		
		if (request.getParameter("search") == null && request.getParameter("update") == null
				&& (request.getParameter("pagination") == null || !request.getParameter("pagination").equals("1"))) {
			daform = new SearchIncidentForm();
			//newform.setAirline(user.getStation().getCompany().getCompanyCode_ID());
			daform.setCompanycreated_ID(agent_station.getCompany().getCompanyCode_ID());

			// assigned station always current user's station
			daform.setStationassigned_ID(agent_station.getStation_ID());
			daform.setStatus_IDs(statuses);
			
			request.setAttribute("searchIncidentForm", daform);

		}

		// search
		ArrayList resultlist = null;
		int rowcount = -1;

		// get number of records found
		if ((resultlist = bs.findIncident(daform, user, 0, 0, true, true)) == null || resultlist.size() <= 0) {
			ActionMessages errors = new ActionMessages();
			ActionMessage error = new ActionMessage("error.nosearchresult");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
			return (mapping.findForward(TracingConstants.VIEW_INCOMING_INCIDENTS));
		} else {
			// get total records
			rowcount = ((Long) resultlist.get(0)).intValue();

			Incident ic = null;

			/** ************ pagination ************* */
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			int totalpages = 0;

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request.getParameter("currpage")) : 0;
			if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1"))
				currpage++;
			if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1"))
				currpage--;

			request.setAttribute("currpage", Integer.toString(currpage));

			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}

			resultlist = bs.findIncident(daform, user, rowsperpage, currpage, false, true);

			if (currpage + 1 == totalpages)
				request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList al = new ArrayList();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}

			/** ************ end of pagination ************* */

			for (int i = 0; i < resultlist.size(); i++) {
				ic = (Incident) resultlist.get(i);
				ic.set_DATEFORMAT(user.getDateformat().getFormat());
				ic.set_TIMEFORMAT(user.getTimeformat().getFormat());
				ic.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			}

			request.setAttribute("resultlist", resultlist);

		}

		return (mapping.findForward(TracingConstants.VIEW_INCOMING_INCIDENTS));

	}
}