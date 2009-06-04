package com.bagnet.nettracer.tracing.actions.taskmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.bmo.ForwardNoticeBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.ForwardNotice;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.OHD_Log_Itinerary;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.forms.SearchForwardNoticeForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class SearchForwardNoticeAction extends Action {
	private static Logger logger = Logger.getLogger(SearchForwardNoticeAction.class);

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
		SearchForwardNoticeForm theForm = (SearchForwardNoticeForm) form;
		
		Station agentStation = null;
		if (session.getAttribute("cbroStationID") != null) {
			agentStation = StationBMO.getStation((String) session.getAttribute("cbroStationID"));
		} else {
			agentStation = user.getStation();
		}
		
		// Close
		if (theForm.getSelect() != null && theForm.getSelect().trim().length() != 0) {
			String[] selectedObject = request.getParameter("select").split(",");
			ForwardNoticeBMO.closeForwardNotice(selectedObject);
		}

		int rowcount = ForwardNoticeBMO.getForwardsForStationCount(agentStation, theForm.getStatus());

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

		List<ForwardNotice> resultlist = ForwardNoticeBMO.getForwardsForStation(agentStation, rowsperpage, currpage, theForm.getStatus());

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
		for (int i = 0; i < resultlist.size(); i++) {
			log = ((ForwardNotice) resultlist.get(i)).getForward();
			log.set_DATEFORMAT(user.getDateformat().getFormat());
			log.set_TIMEFORMAT(user.getTimeformat().getFormat());
			log.set_TIMEZONE(tz);
			for (OHD_Log_Itinerary itin: (Set<OHD_Log_Itinerary>) log.getItinerary()) {
				itin.set_DATEFORMAT(user.getDateformat().getFormat());
				itin.set_TIMEFORMAT(user.getTimeformat().getFormat());
			}
		}

		request.setAttribute("resultlist", resultlist);
		
		
		return (mapping.findForward(TracingConstants.SEARCH_FORWARD_NOTICE));
	}

}
