/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.ControlLog;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHDRequest;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.audit.Audit_OHD;
import com.bagnet.nettracer.tracing.forms.ViewIncomingRequestForm;
import com.bagnet.nettracer.tracing.utils.BDOUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.audit.AuditOHDUtils;

/**
 * Implementation of <strong>Action </strong> that is responsible for viewing
 * incoming bags and performing actions like receive.
 * 
 * @author Ankur Gupta
 */
public class ViewIncomingBags extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MessageResources messages = MessageResources
				.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		HttpSession session = request.getSession();

		ViewIncomingRequestForm theform = (ViewIncomingRequestForm) form;
		// check session
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)) return (mapping
				.findForward(TracingConstants.NO_PERMISSION));

		String sort = request.getParameter("sort");

		if (sort != null && sort.length() > 0) request.setAttribute("sort", sort);

		Station agent_station = null;
		if (session.getAttribute("cbroStationID") != null) {
			agent_station = StationBMO.getStation((String) session.getAttribute("cbroStationID"));
		} else {
			agent_station = user.getStation();
		}

		//		 menu highlite
		request.setAttribute("highlite", TracingConstants.SYSTEM_COMPONENT_NAME_INCOMING_BAGS);

		if (request.getParameter("close") != null || request.getParameter("close1") != null
				&& request.getParameter("ohd_ID") != null && request.getParameter("ohd_ID").length() > 0) {
			//forward to a success page.
			String ohd_ID = request.getParameter("ohd_ID");

			if (ohd_ID.length() > 13) {
				StringTokenizer st = new StringTokenizer(ohd_ID, ",");
				while (st.hasMoreTokens()) {
					OHD ohd = OHDUtils.getOHD(st.nextToken());
					if (ohd != null) {

						Date gmtDate = TracerDateTime.getGMTDate();
						String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(gmtDate);

						if (user.getStation().isThisOhdLz()) {
							ohd.getStatus().setStatus_ID(TracingConstants.OHD_STATUS_OPEN);
						} else {
							if (ohd.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_MATCH_IN_TRANSIT) {
								ohd.getStatus().setStatus_ID(TracingConstants.OHD_STATUS_TO_BE_DELIVERED);
							} else {
								ohd.getStatus().setStatus_ID(TracingConstants.OHD_STATUS_OPEN);
							}
							//Update the close date on the file as well.
							ohd.setClose_date(gmtDate);
						}

						//Update the holding status for this ohd and also add a remark.
						ControlLog log = ohd.getLastLog();
						if (log != null) {
							log.setEnd_date(date);
						}

						//Update the controlling information on the file.
						ControlLog newLog = new ControlLog();
						newLog.setControlling_station(user.getStation());
						newLog.setStart_date(date);
						newLog.setOhd(ohd);

						ohd.getControlLog().add(newLog);
						ohd.setHoldingStation(user.getStation());
						
						// set ohd_log to received status
						OHDUtils.setLogReceived(ohd.getOHD_ID());

						Remark r = new Remark();
						r.setAgent(user);
						r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime
								.getGMTDate()));
						r.setRemarktext(messages.getMessage(new Locale(user.getCurrentlocale()),
								"bagreceiveMessage")
								+ " "
								+ user.getStation().getCompany().getCompanyCode_ID()
								+ messages.getMessage(new Locale(user.getCurrentlocale()), "aposS")
								+ " "
								+ user.getStation().getStationcode() + " station.");
						r.setRemarktype(TracingConstants.REMARK_REGULAR);
						r.setOhd(ohd);
						
						if (ohd.getRemarks() == null) ohd.setRemarks(new HashSet());
						ohd.getRemarks().add(r);
						
						//HibernateUtils.save(ohd);
						OhdBMO ohdBmo = new OhdBMO();
						ohdBmo.insertOHD(ohd, user);
						
						// update l/d bag status to to be delivered as well if forwarding station is the same as l/d assigned station
						Item item = BDOUtils.findOHDfromMatchedLD(ohd.getOHD_ID());
						if (item != null) {
							if (ohd.getHoldingStation().getStation_ID() == item.getIncident().getStationassigned().getStation_ID()) {
								// only do this if forwarded station = station assigned for incident
								Status s2 = new Status();
								s2.setStatus_ID(TracingConstants.ITEM_STATUS_TOBEDELIVERED);
								item.setStatus(s2);
								HibernateUtils.save(item);
							}
						}
						
						// update request if there was one
						OHDRequest forRequest = null;
						Status s = null;
						ArrayList listofrequest = (ArrayList)OHDUtils.getCreatedRequestsForOHD(agent_station.getStation_ID(),ohd.getOHD_ID());
						if (listofrequest != null && listofrequest.size() > 0) {
							for (int i = 0; i<listofrequest.size();i++) {
								// close all these requests
								s = new Status();
								s.setStatus_ID(TracingConstants.OHD_STATUS_CLOSED);
								forRequest = (OHDRequest) listofrequest.get(i);
								forRequest.setStatus(s);
								HibernateUtils.save(forRequest);
							}
						}
					}
				}
				request.setAttribute("ohd_ID", ohd_ID);
				return mapping.findForward(TracingConstants.CLOSE_ON_HAND_SUCCESS);
			} else {
				OHD ohd = OHDUtils.getOHD(ohd_ID);

				if (ohd != null) {

					Date gmtDate = TracerDateTime.getGMTDate();
					String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(gmtDate);

					if (user.getStation().isThisOhdLz()) {
						ohd.getStatus().setStatus_ID(TracingConstants.OHD_STATUS_OPEN);

					} else {
						if (ohd.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_MATCH_IN_TRANSIT) {
							ohd.getStatus().setStatus_ID(TracingConstants.OHD_STATUS_TO_BE_DELIVERED);
						} else {
							ohd.getStatus().setStatus_ID(TracingConstants.OHD_STATUS_OPEN);
						}
						//Update the close date on the file as well.
						ohd.setClose_date(gmtDate);
					}

					//Update the holding status for this ohd and also add a remark.
					ControlLog log = ohd.getLastLog();
					if (log != null) {
						log.setEnd_date(date);
					}
					
					// set ohd_log to received status
					OHDUtils.setLogReceived(ohd.getOHD_ID());

					//Update the controlling information on the file.
					ControlLog newLog = new ControlLog();
					newLog.setControlling_station(user.getStation());
					newLog.setStart_date(date);
					newLog.setOhd(ohd);
					
					ohd.getControlLog().add(newLog);
					ohd.setHoldingStation(user.getStation());

					Remark r = new Remark();
					r.setAgent(user);
					r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime
							.getGMTDate()));
					r.setRemarktext(messages.getMessage(new Locale(user.getCurrentlocale()),
							"bagreceiveMessage")
							+ " "
							+ user.getStation().getCompany().getCompanyCode_ID()
							+ messages.getMessage(new Locale(user.getCurrentlocale()), "aposS")
							+ " "
							+ user.getStation().getStationcode() + " station.");
					r.setRemarktype(TracingConstants.REMARK_REGULAR);
					r.setOhd(ohd);
					
					if (ohd.getRemarks() == null) ohd.setRemarks(new HashSet());
					ohd.getRemarks().add(r);
					
					// update l/d bag status to to be delivered as well if forwarding station is the same as l/d assigned station
					Item item = BDOUtils.findOHDfromMatchedLD(ohd.getOHD_ID());
					if (item != null) {
						if (ohd.getHoldingStation().getStation_ID() == item.getIncident().getStationassigned().getStation_ID()) {
							// only do this if forwarded station = station assigned for incident
							Status s2 = new Status();
							s2.setStatus_ID(TracingConstants.ITEM_STATUS_TOBEDELIVERED);
							item.setStatus(s2);
							HibernateUtils.save(item);
						}
					}
					
					// update request if there was one
					OHDRequest forRequest = null;
					Status s = null;
					ArrayList listofrequest = (ArrayList)OHDUtils.getCreatedRequestsForOHD(agent_station.getStation_ID(),ohd.getOHD_ID());
					if (listofrequest != null && listofrequest.size() > 0) {
						for (int i = 0; i<listofrequest.size();i++) {
							// close all these requests
							s = new Status();
							s.setStatus_ID(TracingConstants.OHD_STATUS_CLOSED);
							forRequest = (OHDRequest) listofrequest.get(i);
							forRequest.setStatus(s);
							HibernateUtils.save(forRequest);
						}
					}

					//HibernateUtils.save(ohd);
					OhdBMO ohdBmo = new OhdBMO();
					ohdBmo.insertOHD(ohd, user);
					
				}
				request.setAttribute("ohd_ID", ohd_ID);

				return mapping.findForward(TracingConstants.CLOSE_ON_HAND_SUCCESS);
			}
		}

		int requestListCount = OHDUtils.getIncomingBagsCount(agent_station.getStation_ID(), theform);
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

			List requestList = OHDUtils.getIncomingBags(agent_station.getStation_ID(), theform, sort,
					rowsperpage, currpage);

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
		return mapping.findForward(TracingConstants.VIEW_INCOMING_BAG_LIST);
	}
}