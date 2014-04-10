/*
 * Created on Feb 14, 2007
 *
 * matt
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.constant.TracingConstants.SortParam;
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
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ViewIncomingIncidents.class);

	@SuppressWarnings("unchecked")
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
		
		String sort = StringUtils.stripToNull(request.getParameter("sort"));
		if (SortParam.isValid(sort)) {
			request.setAttribute("sort", sort);
		}		 
		
		daform.setStationassigned_ID(agent_station.getStation_ID());
		int[] statuses = new int[3];
		statuses[0] = TracingConstants.MBR_STATUS_OPEN;
		statuses[1] = TracingConstants.MBR_STATUS_PENDING;
		statuses[2] = TracingConstants.MBR_STATUS_TEMP;
		daform.setStatus_IDs(statuses);
		
		if (request.getParameter("search") == null && request.getParameter("update") == null && request.getParameter("generateReport") == null
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
		@SuppressWarnings("rawtypes")
		ArrayList resultlist = null;
		int rowcount = -1;

		//set a trap to test the different itemType_ID: 0-all, 1-lostdelay, 2-damaged, 3-pilferage
		String strIncidentType = request.getParameter("type");
		String myItemTypeId = request.getParameter("itemType_ID");
		
		int myIncidentType = 0;
		if (strIncidentType != null) {
			if (strIncidentType.equalsIgnoreCase("1")) {
				myIncidentType = 1;
			} else if (strIncidentType.equalsIgnoreCase("2")) {
				myIncidentType = 2;
			} else {
				myIncidentType = 3;
			}
		} else if (myItemTypeId != null) {
			if (myItemTypeId.equalsIgnoreCase("1")) {
				myIncidentType = 1;
			} else if (myItemTypeId.equalsIgnoreCase("2")) {
				myIncidentType = 2;
			} else if (myItemTypeId.equalsIgnoreCase("3")) {
				myIncidentType = 3;
			}
		} 
		daform.setItemType_ID(myIncidentType);
		
		//assigned to station within last 24 hours feature for westjet
		String strLast24hours = request.getParameter("withinLast") + "";
		if (strLast24hours.equalsIgnoreCase("24")) {
			daform.setAssigned2StationWithin24hrs(1);  //magic number 1 signifies yes 
		}
		
		// Create report if necessary
		ActionMessages errors = new ActionMessages();
		
		// get number of records found
		if ((resultlist = bs.findIncident(daform, user, 0, 0, true, true, sort)) == null || resultlist.size() <= 0) {
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

			resultlist = bs.findIncident(daform, user, rowsperpage, currpage, false, true, sort);

			if (currpage + 1 == totalpages)
				request.setAttribute("end", "1");
			if (totalpages > 1) {
				@SuppressWarnings("rawtypes")
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
			//Generate a Report based on current parameters if requested
			if (request.getParameter("generateReport") != null && 
					request.getParameter("outputtype") != null) {
				daform.setDjReport(true);
				ServletContext sc = getServlet().getServletContext();
				String reportpath = sc.getRealPath("/");
				String reportfile = null;
				ReportBMO rBMO = new ReportBMO(request);
				int outputtype=Integer.valueOf(request.getParameter("outputtype"));
				
				reportfile = rBMO.createIncomingIncidentReport(daform,
					request, outputtype, user.getCurrentlocale(),
					reportpath, sort);
				
				if (reportfile == null || reportfile.equals("")) {
					//no data to report
					if (rBMO.getErrormsg() != null && rBMO.getErrormsg().length() > 0) {
						ActionMessage error = new ActionMessage(rBMO.getErrormsg());
						errors.add(ActionMessages.GLOBAL_MESSAGE,error);
					} else {
						ActionMessage error = new ActionMessage("message.nodata");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					}
					
					saveMessages(request, errors);
				} else {
					request.setAttribute("reportfile", reportfile);
					if (request.getAttribute("outputtype") == null) {
						request.setAttribute("outputtype", request.getParameter("outputtype"));
					}
				}
			}

		}

		return (mapping.findForward(TracingConstants.VIEW_INCOMING_INCIDENTS));

	}
}