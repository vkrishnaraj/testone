/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.forms.ViewCreatedRequestForm;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * Implementation of <strong>Action </strong> that is responsible for viewing
 * all created requests by this station
 * 
 * @author Ankur Gupta
 */
public class ViewCreatedRequests extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		// check session and user validity
		TracerUtils.checkSession(session);
		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)) return (mapping
				.findForward(TracingConstants.NO_PERMISSION));

		Station agent_station = null;
		if (session.getAttribute("cbroStationID") != null) {
			agent_station = StationBMO.getStation((String) session.getAttribute("cbroStationID"));
		} else {
			agent_station = user.getStation();
		}

		List ohd_request_status_list = new ArrayList();

		Status s = new Status();
		s.setStatus_ID(TracingConstants.OHD_STATUS_OPEN);
		s.setLocale(user.getCurrentlocale());
		//s.setDescription(OHDUtils.getStatusDesc(TracingConstants.OHD_STATUS_OPEN));
		ohd_request_status_list.add(s);

		s = new Status();
		s.setStatus_ID(TracingConstants.OHD_STATUS_CLOSED);
		s.setLocale(user.getCurrentlocale());
		//s.setDescription(OHDUtils.getStatusDesc(TracingConstants.OHD_STATUS_CLOSED));
		ohd_request_status_list.add(s);

		s = new Status();
		s.setStatus_ID(TracingConstants.OHD_REQUEST_STATUS_FORWARDED);
		s.setLocale(user.getCurrentlocale());
		//s.setDescription(OHDUtils.getStatusDesc(TracingConstants.OHD_REQUEST_STATUS_FORWARDED));
		ohd_request_status_list.add(s);

		s = new Status();
		s.setStatus_ID(TracingConstants.OHD_REQUEST_STATUS_DENIED);
		s.setLocale(user.getCurrentlocale());
		//s.setDescription(OHDUtils.getStatusDesc(TracingConstants.OHD_REQUEST_STATUS_DENIED));
		ohd_request_status_list.add(s);

		request.setAttribute("ohd_request_status_list", ohd_request_status_list);
		
		ViewCreatedRequestForm theform =(ViewCreatedRequestForm) form;
		if (theform != null) {
			if (theform.getStatus() == null || theform.getStatus().length() == 0) {
				theform.setStatus(Integer.toString(TracingConstants.OHD_STATUS_OPEN));
			}
		}
		//		 menu highlite
		request.setAttribute("highlite", TracingConstants.SYSTEM_COMPONENT_NAME_VIEW_CREATED_REQUESTS);

		int requestListCount = OHDUtils.getCreatedRequestsCount(user, (ViewCreatedRequestForm) form,
				agent_station);
		if (requestListCount > 0) {
			/** ************ pagination ************* */
			int rowcount = -1;
			int rowsperpage = request.getParameter("rowsperpage") != null ? Integer.parseInt(request
					.getParameter("rowsperpage")) : TracingConstants.ROWS_PER_PAGE;
			if (rowsperpage < 1) rowsperpage = TracingConstants.ROWS_PER_PAGE;
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			int totalpages = 0;

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1")) currpage++;
			if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1")) currpage--;

			request.setAttribute("currpage", Integer.toString(currpage));

			// get row count
			rowcount = requestListCount;

			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}
			List requestList = OHDUtils.getCreatedRequests(user, rowsperpage, currpage,
					(ViewCreatedRequestForm) form, agent_station);

			if (currpage + 1 == totalpages) request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList al = new ArrayList();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}

			/** ************ end of pagination ************* */
			request.setAttribute("requestList", requestList);
		} else {
			int rowsperpage = request.getParameter("rowsperpage") != null ? Integer.parseInt(request
					.getParameter("rowsperpage")) : TracingConstants.ROWS_PER_PAGE;
			if (rowsperpage < 1) rowsperpage = TracingConstants.ROWS_PER_PAGE;
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			request.setAttribute("currpage", Integer.toString(currpage));
		}

		return mapping.findForward(TracingConstants.VIEW_CREATED_REQUEST_LIST);
	}
}