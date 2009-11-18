/*
 * Created on Sept 24, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.PaxCommunicationBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Message;
import com.bagnet.nettracer.tracing.db.MessageCopy;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.PaxCommunication;
import com.bagnet.nettracer.tracing.db.Recipient;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.PaxCommunication.PaxCommunicationStatus;
//import com.bagnet.nettracer.tracing.forms.ComposeForm;
import com.bagnet.nettracer.tracing.forms.ListNewPaxCommunicationForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.MessageUtils;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * Implementation of <strong>Action </strong> that is responsible for managing
 * all new  message by passengers. 
 * 
 * @author Ian
 */
public class ListNewPaxCommunication extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
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

		String sort = request.getParameter("sort");
		if (sort != null && sort.length() > 0) request.setAttribute("sort", sort);

		ActionMessages errors = new ActionMessages();

		List statusList = TracerUtils.getStatusList(TracingConstants.TABLE_MESSAGE, user.getCurrentlocale(), "status_ID") ;
		request.setAttribute("message_status_list", statusList);

		ArrayList ax = new ArrayList();
		ax.add(new LabelValueBean(TracerUtils.getText("ohd.short", user), "0"));
		ax.add(new LabelValueBean(TracerUtils.getText("incident_cap", user), "1"));
		request.setAttribute("typelist", ax);

		//menu highlight
		request.setAttribute("highlite", TracingConstants.SYSTEM_COMPONENT_NAME_PAX_COMMUNICATION);

		ListNewPaxCommunicationForm theForm = (ListNewPaxCommunicationForm) form;

		//Delete the message 
		// remove this block later
		if (request.getParameter("delete") != null) {

			MessageCopy message = MessageUtils.getMessageCopy(theForm.getMessage_id());
			Status s = new Status();
			s.setStatus_ID(TracingConstants.MESSAGE_STATUS_DELETED);
			message.setStatus(s);
			HibernateUtils.save(message);

			//HibernateUtils.delete(message);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.delete.success"));
		}

		//batch acknowledge feature
		if (request.getParameter("delete1") != null && request.getParameter("message_ids") != null
				&& request.getParameter("message_ids").length() > 0) {

			String messages = (String) request.getParameter("message_ids");
			StringTokenizer st = new StringTokenizer(messages, ",");
			while (st.hasMoreTokens()) {
				PaxCommunication message = PaxCommunicationBMO.getPaxCommunication(st.nextToken());
				message.setStatus(PaxCommunicationStatus.ACKNOWLEDGED);
				//set the acknowledge_date and acknowledge agent
				java.util.Date myDate = TracerDateTime.getGMTDate();
				message.setAcknowledge_timestamp(myDate);
				message.setAcknowledge_agent(user);
				HibernateUtils.save(message);
			}

			//HibernateUtils.delete(message);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("pax.comments.batch.acknowledge.success"));
		}
			
		if (request.getParameter("search") != null || request.getParameter("delete") != null
				|| (request.getParameter("new") == null && request.getParameter("inbox") != null)) {
			int messageCount = 0;
			int status_id = -1;
			String strStatus_id = "NEW"; //default display 
//			if (request.getParameter("message_status") != null) status_id = Integer.parseInt(request
//					.getParameter("message_status"));
			
			if (request.getParameter("message_status") != null) {
				strStatus_id = request.getParameter("message_status");
			} 
			
			messageCount = PaxCommunicationBMO.getPaxMessagesCount("" + agent_station.getStation_ID(), strStatus_id,
					request.getParameter("s_time"), request.getParameter("e_time"), request
							.getParameter("search_sub"), request.getParameter("search_file_ref"), user, sort);

			if (messageCount > 0) {
				/** ************ pagination ************* */
				int rowcount = -1;
				int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
				request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
				int totalpages = 0;

				int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
						.getParameter("currpage")) : 0;
				if (request.getParameter("nextpage") != null
						&& request.getParameter("nextpage").equals("1")) currpage++;
				if (request.getParameter("prevpage") != null
						&& request.getParameter("prevpage").equals("1")) currpage--;

				request.setAttribute("currpage", Integer.toString(currpage));

				// get row count
				rowcount = messageCount;

				// find out total pages
				totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

				if (totalpages <= currpage) {
					currpage = 0;
					request.setAttribute("currpage", "0");
				}

				//retrieve paginated messages.
/*				List messages = MessageUtils
						.getMessages("" + agent_station.getStation_ID(), rowsperpage, currpage, status_id,
								request.getParameter("s_time"), request.getParameter("e_time"), request
										.getParameter("search_sub"), request.getParameter("search_file_ref"), user,
								sort);*/
				List messages = PaxCommunicationBMO
						.getPaxMessages("" + agent_station.getStation_ID(), rowsperpage, currpage, strStatus_id,
								request.getParameter("s_time"), request.getParameter("e_time"), request
										.getParameter("search_sub"), request.getParameter("search_file_ref"), user,
								sort);

				if (messages != null) {
					for (Iterator i = messages.iterator(); i.hasNext();) {
						PaxCommunication m = (PaxCommunication) i.next();
						m.set_DATEFORMAT(user.getDateformat().getFormat());
						m.set_TIMEFORMAT(user.getTimeformat().getFormat());
						m.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
								user.getDefaulttimezone()).getTimezone()));
					}
				}

				if (currpage + 1 == totalpages) request.setAttribute("end", "1");
				if (totalpages > 1) {
					ArrayList al = new ArrayList();
					for (int i = 0; i < totalpages; i++) {
						al.add(Integer.toString(i));
					}
					request.setAttribute("pages", al);
				}

				/** ************ end of pagination ************* */
				request.setAttribute("messages", messages);
			} else {
				int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
				request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

				int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
						.getParameter("currpage")) : 0;
				request.setAttribute("currpage", Integer.toString(currpage));
			}
			if (!errors.isEmpty()) saveMessages(request, errors);
		}
		
		//find out if we will display the batch acknowledge buttons
		//if YES, display Batch Acknowledge button
		String isThereAnyNewPaxComment = "NO";
		boolean isThereNewPaxCommunications = PaxCommunicationBMO.isThereNewPaxCommunications(null);
		if(isThereNewPaxCommunications) {
			isThereAnyNewPaxComment = "YES";
		}
		request.setAttribute("isThereAnyNewPaxComment", isThereAnyNewPaxComment);
		
		return mapping.findForward(TracingConstants.LIST_NEW_PAX_COMMUNICATION);
	}
}