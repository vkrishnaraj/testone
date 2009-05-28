/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Message;
import com.bagnet.nettracer.tracing.db.MessageCopy;
import com.bagnet.nettracer.tracing.db.Recipient;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.audit.Audit_ExpensePayout;
import com.bagnet.nettracer.tracing.forms.InterimExpenseRequestForm;
import com.bagnet.nettracer.tracing.utils.ExpenseUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * Implementation of <strong>Action </strong> that is responsible for viewing
 * incoming bags and performing actions like receive.
 * 
 * @author Ankur Gupta
 */
public class InterimExpenseAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MessageResources messages = MessageResources
				.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		HttpSession session = request.getSession();

		InterimExpenseRequestForm theform = (InterimExpenseRequestForm) form;
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
		request.setAttribute("highlite",
				TracingConstants.SYSTEM_COMPONENT_NAME_INTERIM_EXPENSE_REQUESTS);

		if (request.getParameter("approve") != null
				|| (request.getParameter("approve1") != null && request.getParameter("approve1").length() > 0)) {
			String payout_ID = request.getParameter("payout_ID");
			StringTokenizer st = new StringTokenizer(payout_ID, ",");

			String incident_id = "";
			while (st.hasMoreTokens()) {
				String token = (String) st.nextToken();
				//Get the corresponding ExpensePayout and update the status.
				ExpensePayout payout = ExpenseUtils.getExpensePayout(token);
				Status s = new Status();
				s.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED);
				payout.setApproval_date(TracerDateTime.getGMTDate());
				payout.setStatus(s);
				HibernateUtils.save(payout);
				
				Audit_ExpensePayout a_ep = new Audit_ExpensePayout();
				BeanUtils.copyProperties(a_ep, payout);
				HibernateUtils.save(a_ep);
				
				if (!incident_id.equals("")) {
					incident_id += ",";
				}

				incident_id += payout.getIncident().getIncident_ID();
			}

			request.setAttribute("incident_id", incident_id);
			request.setAttribute("payout_id", payout_ID);

			return mapping.findForward(TracingConstants.EXPENSE_PAYOUT_APPROVED_SUCCESS);

		} else if (request.getParameter("deny") != null
				|| (request.getParameter("deny1") != null && request.getParameter("deny1").length() > 0)) {

			String payout_ID = request.getParameter("payout_ID");
			StringTokenizer st = new StringTokenizer(payout_ID, ",");

			String incident_id = "";
			while (st.hasMoreTokens()) {
				String token = (String) st.nextToken();
				//Get the corresponding ExpensePayout and update the status.
				ExpensePayout payout = ExpenseUtils.getExpensePayout(token);
				Status s = new Status();
				s.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_DENIED);
				payout.setStatus(s);
				HibernateUtils.save(payout);
				
				Audit_ExpensePayout a_ep = new Audit_ExpensePayout();
				BeanUtils.copyProperties(a_ep, payout);
				HibernateUtils.save(a_ep);

				if (!incident_id.equals("")) {
					incident_id += ",";
				}

				incident_id += payout.getIncident().getIncident_ID();

				//Put in the send box.
				Message msg = new Message();
				msg.setSend_date(TracerDateTime.getGMTDate());
				msg.setSend_station(user.getStation());
				msg.setMessage("Interim Expense Denied");
				msg.setSubject("Interim Expense Request Denied");
				msg.setFile_ref_number(payout.getIncident().getIncident_ID());
				msg.setFile_type(1);
				msg.setAgent(user);

				List newRecpList = new ArrayList();
				Recipient recp = new Recipient();
				recp.setStation(payout.getStation());
				recp.setMessage(msg);
				newRecpList.add(recp);
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
						Status s1 = new Status();
						s1.setStatus_ID(TracingConstants.MESSAGE_STATUS_NEW);
						copy.setStatus(s1);
						HibernateUtils.save(copy);
					}
				}
			}
			request.setAttribute("incident_id", incident_id);
			request.setAttribute("payout_id", payout_ID);

			return mapping.findForward(TracingConstants.EXPENSE_PAYOUT_DENIED_SUCCESS);
		}

		List expenseList = ExpenseUtils.getPendingInterimExpenses(true, agent_station.getCompany()
				.getCompanyCode_ID(), theform, sort, 0, 0);
		if (expenseList != null && ((Long) expenseList.get(0)).intValue() > 0) {
			/** ************ pagination ************* */
			int rowcount = -1;
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			int totalpages = 0;

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1")) currpage++;
			if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1")) currpage--;

			request.setAttribute("currpage", Integer.toString(currpage));

			// get row count
			rowcount = ((Long) expenseList.get(0)).intValue();

			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}

			expenseList = ExpenseUtils.getPendingInterimExpenses(false, agent_station.getCompany()
					.getCompanyCode_ID(), theform, sort, rowsperpage, currpage);

			if (currpage + 1 == totalpages) request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList al = new ArrayList();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}

			/** ************ end of pagination ************* */
			request.setAttribute("expenseList", expenseList);
		} else {
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			request.setAttribute("currpage", Integer.toString(currpage));
		}
		return mapping.findForward(TracingConstants.INTERIM_EXPENSE_REQUEST);
	}
}