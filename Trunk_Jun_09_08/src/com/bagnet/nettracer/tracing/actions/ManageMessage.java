/*
 * Created on Jul 9, 2004
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
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Message;
import com.bagnet.nettracer.tracing.db.MessageCopy;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Recipient;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.forms.ComposeForm;
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
 * all the message exchanges between stations. It allows compose, delete, view,
 * reply of messages.
 * 
 * @author Ankur Gupta
 */
public class ManageMessage extends Action {
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

		//		 menu highlite
		request.setAttribute("highlite", TracingConstants.SYSTEM_COMPONENT_NAME_MY_INBOX);

		ComposeForm theForm = (ComposeForm) form;
		if (request.getParameter("message_copy_id") != null) {
			MessageCopy message = MessageUtils.getMessageCopy(request.getParameter("message_copy_id"));
			message.set_DATEFORMAT(user.getDateformat().getFormat());
			message.set_TIMEFORMAT(user.getTimeformat().getFormat());
			theForm.setRecp_list(new ArrayList(message.getParent_message().getRecipients()));
			theForm.setMessage_id("" + message.getMessage_copy_id());
			theForm.setDate(message.getDisp_send_date());
			theForm.setBody(message.getBody());
			theForm.setAgentName(message.getAgent().getUsername());
			theForm.setFile_ref_number(message.getParent_message().getFile_ref_number());
			theForm.setFile_type(Integer.toString(message.getParent_message().getFile_type()));
			
			//update the \n with "<br>" so that it could be displayed correctly as
			// html.
			//theForm.setBody(StringEscapeUtils.escapeHtml(theForm.getBody()));
			//theForm.setBody(theForm.getBody().replaceAll("\n", "<br>"));
			theForm.setSubject(message.getSubject());
			theForm.setCompanyCode(message.getParent_message().getSend_station().getCompany()
					.getCompanyCode_ID());
			theForm.setStationCode(message.getParent_message().getSend_station().getStationcode());

			//Update the status of the message to be seen .
			if (message.getStatus().getStatus_ID() == TracingConstants.MESSAGE_STATUS_NEW) {
				Status s = new Status();
				s.setStatus_ID(TracingConstants.MESSAGE_STATUS_SEEN);
				message.setStatus(s);
				//message.setAgent(user);
				HibernateUtils.save(message);
			}
			return mapping.findForward(TracingConstants.VIEW_MESSAGE);
		}

		//Setup the reply of a message
		if (request.getParameter("reply") != null) {
			MessageCopy message = MessageUtils.getMessageCopy(theForm.getMessage_id());
			Recipient rcpt = new Recipient();
			rcpt.setCompany_code(theForm.getCompanyCode());
			rcpt.setStation(message.getParent_message().getSend_station());
			rcpt.setStation_id(message.getParent_message().getSend_station().getStation_ID());
			theForm.getRecp_list().clear();
			theForm.getRecp_list().add(rcpt);
			theForm.setAgentName(message.getAgent().getUsername());
			theForm.setSubject("Re:" + message.getSubject());
			String date = DateUtils.formatDate(TracerDateTime.getGMTDate(), user.getDateformat()
					.getFormat()
					+ " " + user.getTimeformat().getFormat(), null, TimeZone.getTimeZone(AdminUtils
					.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			theForm.setDate(date);
			theForm.setBody("\n\n\n\n-------- Sender: "
					+ message.getParent_message().getSend_station().getCompany().getCompanyCode_ID() + " "
					+ message.getParent_message().getSend_station().getStationcode() + " --------\n"
					+ message.getBody());
		}

		//Delete the message
		if (request.getParameter("delete") != null) {

			MessageCopy message = MessageUtils.getMessageCopy(theForm.getMessage_id());
			Status s = new Status();
			s.setStatus_ID(TracingConstants.MESSAGE_STATUS_DELETED);
			message.setStatus(s);
			HibernateUtils.save(message);

			//HibernateUtils.delete(message);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.delete.success"));
		}

		if (request.getParameter("delete1") != null && request.getParameter("message_ids") != null
				&& request.getParameter("message_ids").length() > 0) {

			String messages = (String) request.getParameter("message_ids");
			StringTokenizer st = new StringTokenizer(messages, ",");
			while (st.hasMoreTokens()) {
				MessageCopy message = MessageUtils.getMessageCopy(st.nextToken());
				Status s = new Status();
				s.setStatus_ID(TracingConstants.MESSAGE_STATUS_DELETED);
				message.setStatus(s);
				HibernateUtils.save(message);
			}

			//HibernateUtils.delete(message);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.delete.success"));
		}

		//Perform the send of the message.
		boolean isSendingMessage = request.getParameter("send") != null && request.getParameter("send").length() > 0;
		if (isSendingMessage) {
			//Put in the send box.
			Message msg = new Message();
			msg.setSend_date(TracerDateTime.getGMTDate());
			msg.setSend_station(user.getStation());
			msg.setMessage(theForm.getBody());
			msg.setSubject(theForm.getSubject());
			msg.setFile_ref_number(theForm.getFile_ref_number());
			msg.setFile_type(Integer.parseInt(theForm.getFile_type()));
			msg.setAgent(user);

			//if the file_type

			if (msg.getFile_ref_number() != null && !msg.getFile_ref_number().equals("")
					&& msg.getFile_type() == -1) {
				//error should select a file type
				ActionMessage error = new ActionMessage("error.missing.filetype");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return mapping.findForward(TracingConstants.COMPOSE_MESSAGE);
			}

			if (msg.getFile_ref_number().equals("")) msg.setFile_type(-1);

			//check if the file is on hand or incident
			if (msg.getFile_type() == 0) {
				//check if the file is on hand number.
				OHD ohd = OHDUtils.getOHD(msg.getFile_ref_number());
				if (ohd == null) {
					ActionMessage error = new ActionMessage("error.incorrect.filetype");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
					return mapping.findForward(TracingConstants.COMPOSE_MESSAGE);
				}
			}

			//check if the file is on hand or incident
			if (msg.getFile_type() == 1) {
				//check if the file is on hand number.
				IncidentBMO bmo = new IncidentBMO();
				Incident incident = bmo.findIncidentByID(msg.getFile_ref_number());
				if (incident == null) {
					ActionMessage error = new ActionMessage("error.incorrect.filetype");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
					return mapping.findForward(TracingConstants.COMPOSE_MESSAGE);
				}
			}

			//Make sure subject exists.
			if (msg.getSubject() == null || msg.getSubject().trim().equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.enter.subject"));
				saveMessages(request, errors);
				return mapping.findForward(TracingConstants.COMPOSE_MESSAGE);
			}

			//		Make sure message text exists.
			if (msg.getMessage() == null || msg.getMessage().trim().equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.enter.text"));
				saveMessages(request, errors);
				return mapping.findForward(TracingConstants.COMPOSE_MESSAGE);
			}

			List recpList = theForm.getRecp_list();
			List newRecpList = new ArrayList();
			if (recpList != null) {
				for (Iterator i = recpList.iterator(); i.hasNext();) {
					Recipient recp = (Recipient) i.next();
					recp.setMessage(msg);
					if (recp.getStation_id() > 0) {
						recp.setStation(StationBMO.getStation("" + recp.getStation_id()));
						newRecpList.add(recp);
					} else {
						if (recp.getStation_id() == -1) {
							List stations = TracerUtils.getStationList(recp
									.getCompany_code());
							if (stations != null && stations.size() > 0) {
								for (Iterator j = stations.iterator(); j.hasNext();) {
									Station rStation = (Station) j.next();
									Recipient allRecpt = new Recipient();

									allRecpt.setCompany_code(recp.getCompany_code());
									allRecpt.setMessage(recp.getMessage());
									allRecpt.setRecipient_id(recp.getRecipient_id());
									allRecpt.setStation(rStation);
									allRecpt.setStation_id(rStation.getStation_ID());
									allRecpt.setStationList(recp.getStationList());

									newRecpList.add(allRecpt);
								}
							}
						}
					}
				}
			}
			//Make sure the recipient exists.
			if (newRecpList.size() < 1) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.select.recipient"));
				saveMessages(request, errors);
				return mapping.findForward(TracingConstants.COMPOSE_MESSAGE);
			}
			msg.setRecipients(new HashSet(newRecpList));
			HibernateUtils.save(msg);

			//save each repient's copy
			if (msg.getMessage_id() > 0) {
				for (Iterator i = newRecpList.iterator(); i.hasNext();) {
					Recipient recpt = (Recipient) i.next();
					MessageCopy copy = new MessageCopy();
					copy.setParent_message(msg);
					copy.setBody(msg.getMessage());
					copy.setSubject(msg.getSubject());
					copy.setAgent(user);
					copy.setReceiving_station(recpt.getStation());
					Status s = new Status();
					s.setStatus_ID(TracingConstants.MESSAGE_STATUS_NEW);
					copy.setStatus(s);
	
					HibernateUtils.save(copy);
				}
			}

			if (theForm.getMessage_id() != null && !theForm.getMessage_id().equals("")) {
				//Update the status of the old if reply or not..
				MessageCopy message = MessageUtils.getMessageCopy(theForm.getMessage_id());
				if (message != null) {
					Status s = new Status();
					s.setStatus_ID(TracingConstants.MESSAGE_STATUS_REPLIED);
					message.setStatus(s);
					message.setAgent(user);
					HibernateUtils.save(message);
				}
			}

			//set the sent box copy..
			if (msg != null && msg.getMessage_id() > 0) {
				MessageCopy copy = new MessageCopy();
				copy.setParent_message(msg);
				copy.setBody(msg.getMessage());
				copy.setSubject(msg.getSubject());
				copy.setAgent(user);
				copy.setReceiving_station(msg.getSend_station());
				Status s1 = new Status();
				s1.setStatus_ID(TracingConstants.MESSAGE_STATUS_SENT);
				copy.setStatus(s1);
				HibernateUtils.save(copy);
			}

			//Not really an error..Just to denote that the message is successfully
			// sent.
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.send.success"));
		}

		//show agent's inbox
		if (request.getParameter("search") != null || isSendingMessage
				|| request.getParameter("delete") != null
				|| (request.getParameter("new") == null && request.getParameter("inbox") != null)) {
			int messageCount = 0;
			int status_id = -1;
			if (request.getParameter("message_status") != null) status_id = Integer.parseInt(request
					.getParameter("message_status"));

			messageCount = MessageUtils.getMessagesCount("" + agent_station.getStation_ID(), status_id,
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

				//retrieve paginated users.
				List messages = MessageUtils
						.getMessages("" + agent_station.getStation_ID(), rowsperpage, currpage, status_id,
								request.getParameter("s_time"), request.getParameter("e_time"), request
										.getParameter("search_sub"), request.getParameter("search_file_ref"), user,
								sort);

				if (messages != null) {
					for (Iterator i = messages.iterator(); i.hasNext();) {
						MessageCopy m = (MessageCopy) i.next();
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
			return mapping.findForward(TracingConstants.MESSAGE_LIST);
		}

		//A new recipient is selected.
		if (request.getParameter("add") != null) {
			Recipient rcpt = new Recipient();
			theForm.getRecp_list().add(rcpt);
		} else {
			//New message is composed.
			if (request.getParameter("new") != null) {
				theForm = new ComposeForm();
				String date = DateUtils.formatDate(TracerDateTime.getGMTDate(), user.getDateformat()
						.getFormat()
						+ " " + user.getTimeformat().getFormat(), null, TimeZone.getTimeZone(AdminUtils
						.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
				theForm.setDate(date);
				theForm.getRecp_list().clear();

				Recipient r = new Recipient();
				//TODO: default to Please select
				//r.setStation_id(-2);
				r.setCompany_code(user.getCompanycode_ID());
				theForm.getRecp_list().add(r);
				theForm.setBody("");
				theForm.setSubject("");
				theForm.setAgentName(user.getUsername());
				session.setAttribute("composeForm", theForm);
			}
		}

		//Take the recipient list and setup the recipients in the form for correct
		// display.
		List recpList = theForm.getRecp_list();
		for (Iterator i = recpList.iterator(); i.hasNext();) {
			Recipient recpt = (Recipient) i.next();
			if (recpt.getCompany_code().length() > 0) {
				recpt.setStationList(TracerUtils.getStationList(recpt
						.getCompany_code()));
				if (recpt.getStationList() != null && recpt.getStationList().size() > 0) {
					boolean exists_station_id = false;
					for (Iterator j = recpt.getStationList().iterator(); j.hasNext();) {
						Station station = (Station) j.next();
						if (station.getStation_ID() == recpt.getStation_id()) {
							exists_station_id = true;
							break;
						}
					}
					if (!exists_station_id) {
						//TODO: lines below removed to default to Please Select option
						//recpt.setStation((Station) recpt.getStationList().get(0));
						//recpt.setStation_id(recpt.getStation().getStation_ID());
					}
				} else {
					recpt.setStation(null);
					recpt.setStation_id(0);
				}
			} else {
				recpt.setStation(null);
				recpt.setStation_id(0);
			}
		}
		return mapping.findForward(TracingConstants.COMPOSE_MESSAGE);
	}
}