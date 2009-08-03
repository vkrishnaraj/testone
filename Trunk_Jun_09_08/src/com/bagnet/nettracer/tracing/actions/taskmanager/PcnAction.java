package com.bagnet.nettracer.tracing.actions.taskmanager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.bmo.ProactiveNotificationBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.ProactiveNotification;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.dto.PcnSearchDTO;
import com.bagnet.nettracer.tracing.forms.PcnSearchForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class PcnAction extends Action {
	private static Logger logger = Logger.getLogger(PcnAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		if (session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		Agent user = (Agent) session.getAttribute("user");
		PcnSearchForm theForm = (PcnSearchForm) form;
		
		
		if(!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		
		
		Station agentStation = null;
		if (session.getAttribute("cbroStationID") != null) {
			agentStation = StationBMO.getStation((String) session.getAttribute("cbroStationID"));
		} else {
			agentStation = user.getStation();
		}
		
		// Close
		if (theForm.getSelect() != null && theForm.getSelect().trim().length() != 0) {
			String[] selectedObjects = request.getParameter("select").split(",");
			if (request.getParameter("close") != null && ((String)request.getParameter("close")).length() > 0) {
				ProactiveNotificationBMO.closePcns(selectedObjects);
			} else if (request.getParameter("print") != null && ((String)request.getParameter("print")).length() > 0) {
				ProactiveNotificationBMO.printPcns(selectedObjects, theForm.getPrinterAddress());
			}
		}

		PcnSearchDTO dto = new PcnSearchDTO();
		BeanUtils.copyProperties(dto, theForm);
		
		if (theForm.getStatus_ID() == 0) {
			dto.setStatus_ID(ProactiveNotification.STATUS_OPEN);
		}
		
		if (theForm.getDestinationStation() == 0) {
			dto.setDestinationStation(user.getStation().getStation_ID());
		}
			
		if (theForm.getMissedFlightAirline() == null) {
			theForm.setMissedFlightAirline(user.getCompanycode_ID());
			dto.setMissedFlightAirline(user.getCompanycode_ID());
		}
		
		if (theForm.getMissedFlightDate() == null) {
			SimpleDateFormat tmp = new SimpleDateFormat(user.getDateformat().getFormat());
			tmp.setTimeZone(TimeZone.getDefault());
			theForm.setMissedFlightDate(tmp.format(TracerDateTime.getGMTDate()));
		}
	
		
		int rowcount = ProactiveNotificationBMO.getCount(dto, user);

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

		List<ProactiveNotification> resultlist = ProactiveNotificationBMO.get(dto, user);

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
		OHD_Log log = null;
		TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());

		request.setAttribute("resultlist", resultlist);
		
		
		return (mapping.findForward(TracingConstants.SYSTEM_COMPONENT_FORWARD_PCN));
	}

}
