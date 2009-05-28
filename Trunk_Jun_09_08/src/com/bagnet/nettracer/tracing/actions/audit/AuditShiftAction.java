/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions.audit;

import java.util.ArrayList;
import java.util.List;
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

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.audit.Audit_Work_Shift;
import com.bagnet.nettracer.tracing.forms.audit.AuditWorkShiftForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.audit.AuditWorkshiftUtils;

/**
 * Implementation of <strong>Action </strong> that is responsible for providing
 * search capabilities to on-hands. It returns a paginated results.
 * 
 * @author Ankur Gupta
 */
public class AuditShiftAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		// check session and user validity
		TracerUtils.checkSession(session);

		if (session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		Agent user = (Agent) session.getAttribute("user");

		AuditWorkShiftForm daform = (AuditWorkShiftForm) form;
		if (request.getParameter("detail") != null) {
			String shift_id = request.getParameter("shift_id");
			request.setAttribute("shift_id", shift_id);

			String audit_shift_id = request.getParameter("audit_id");
			if (audit_shift_id != null && audit_shift_id.length() > 0) {
				List compareList = AuditWorkshiftUtils.getAuditsForComparison(audit_shift_id);
				for (int i = 0; i < compareList.size(); i++) {
					Audit_Work_Shift ic = (Audit_Work_Shift) compareList.get(i);
					ic.set_DATEFORMAT(user.getDateformat().getFormat());
					ic.set_TIMEFORMAT(user.getTimeformat().getFormat());
					ic.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils
							.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
				}
				request.setAttribute("compareList", compareList);

				return (mapping.findForward(TracingConstants.AUDIT_SHIFT_COMPARE_DETAIL));
			}

			//Obtain list of all audit's made to this report.
			List resultlist = null;

			// get number of records found
			if ((resultlist = AuditWorkshiftUtils.getAudits(shift_id, 0, 0, true)) == null
					|| resultlist.size() <= 0) {
				int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_AUDIT_PAGES, session);
				request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

				int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
						.getParameter("currpage")) : 0;
				request.setAttribute("currpage", Integer.toString(currpage));

				ActionMessages errors = new ActionMessages();
				ActionMessage error = new ActionMessage("error.no.shift");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				int rowcount = -1;
				int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_AUDIT_PAGES, session);
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
				rowcount = ((Integer) resultlist.get(0)).intValue();
				// find out total pages
				totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

				if (totalpages <= currpage) {
					currpage = 0;
					request.setAttribute("currpage", "0");
				}

				//find the paginated on hand bags
				List searchList = AuditWorkshiftUtils.getAudits(shift_id, rowsperpage, currpage, false);

				if (currpage + 1 == totalpages) request.setAttribute("end", "1");
				if (totalpages > 1) {
					ArrayList al = new ArrayList();
					for (int i = 0; i < totalpages; i++) {
						al.add(Integer.toString(i));
					}
					request.setAttribute("pages", al);
				}

				/** ************ end of pagination ************* */
				for (int i = 0; i < searchList.size(); i++) {
					Audit_Work_Shift ic = (Audit_Work_Shift) searchList.get(i);
					ic.set_DATEFORMAT(user.getDateformat().getFormat());
					ic.set_TIMEFORMAT(user.getTimeformat().getFormat());
					ic.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils
							.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
				}
				request.setAttribute("auditworkshiftlist", searchList);
				return (mapping.findForward(TracingConstants.AUDIT_SHIFT_DETAIL));
			}
		}

		if (request.getParameter("search") == null) {
			return (mapping.findForward(TracingConstants.AUDIT_SHIFT));
		}

		List resultlist = null;
		// get number of records found
		if ((resultlist = AuditWorkshiftUtils.findShiftsByAuditSearchCriteria(daform, user, 0, 0, true)) == null
				|| resultlist.size() <= 0) {
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_AUDIT_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			request.setAttribute("currpage", Integer.toString(currpage));

			ActionMessages errors = new ActionMessages();
			ActionMessage error = new ActionMessage("error.no.shift");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
		} else {
			int rowcount = -1;
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_AUDIT_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			int totalpages = 0;

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1")) currpage++;
			if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1")) currpage--;
			request.setAttribute("currpage", Integer.toString(currpage));
			// get row count
			rowcount = ((Long) resultlist.get(0)).intValue();
			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}

			//find the paginated on hand bags
			List searchList = AuditWorkshiftUtils.findShiftsByAuditSearchCriteria(daform, user,
					rowsperpage, currpage, false);

			if (currpage + 1 == totalpages) request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList al = new ArrayList();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}
			request.setAttribute("shiftList", searchList);
		}
		return (mapping.findForward(TracingConstants.AUDIT_SHIFT));
	}
}