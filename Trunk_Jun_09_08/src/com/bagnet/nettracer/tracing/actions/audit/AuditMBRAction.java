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
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.audit.Audit_Incident;
import com.bagnet.nettracer.tracing.forms.audit.AuditMBRForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.audit.AuditIncidentUtils;

/**
 * Implementation of <strong>Action </strong> that is responsible for providing
 * search capabilities to on-hands. It returns a paginated results.
 * 
 * @author Ankur Gupta
 */
public class AuditMBRAction extends Action {
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

		AuditMBRForm daform = (AuditMBRForm) form;
		if (request.getParameter("detail") != null) {
			String incident_ID = request.getParameter("incident_ID");
			request.setAttribute("incident_ID", incident_ID);

			String audit_incident_id = request.getParameter("audit_incident_id");
			if (audit_incident_id != null && audit_incident_id.length() > 0) {
				List compareList = AuditIncidentUtils.getAuditsForComparison(audit_incident_id);
				for (int i = 0; i < compareList.size(); i++) {
					Audit_Incident ic = (Audit_Incident) compareList.get(i);
					ic.set_DATEFORMAT(user.getDateformat().getFormat());
					ic.set_TIMEFORMAT(user.getTimeformat().getFormat());
					ic.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils
							.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
					if (i == 0) {
						if (ic.getItemtype().getItemType_ID() == TracingConstants.LOST_DELAY) {
							request.setAttribute("ld", "1");
						}
						if (ic.getItemtype().getItemType_ID() == TracingConstants.DAMAGED_BAG) {
							request.setAttribute("damaged", "1");
						}
						if (ic.getItemtype().getItemType_ID() == TracingConstants.MISSING_ARTICLES) {
							request.setAttribute("missing", "1");
						}
					}
				}
				request.setAttribute("compareList", compareList);
				return (mapping.findForward(TracingConstants.AUDIT_MBR_COMPARE_DETAIL));
			}

			//Obtain list of all audit's made to this report.
			List resultlist = null;

			// get number of records found
			if ((resultlist = AuditIncidentUtils.getAudits(incident_ID, 0, 0, true)) == null
					|| resultlist.size() <= 0) {
				int rowsperpage = request.getParameter("rowsperpage") != null ? Integer.parseInt(request
						.getParameter("rowsperpage")) : TracingConstants.ROWS_PER_PAGE;
				if (rowsperpage < 1) rowsperpage = TracingConstants.ROWS_PER_PAGE;
				request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

				int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
						.getParameter("currpage")) : 0;
				request.setAttribute("currpage", Integer.toString(currpage));

				ActionMessages errors = new ActionMessages();
				ActionMessage error = new ActionMessage("error.no.incidentreport");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				int rowcount = -1;
				int rowsperpage = request.getParameter("rowsperpage") != null ? Integer.parseInt(request
						.getParameter("rowsperpage")) : TracingConstants.ROWS_PER_PAGE;
				if (rowsperpage < 1) rowsperpage = TracingConstants.ROWS_PER_PAGE;
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
				rowcount = ((Long) resultlist.get(0)).intValue();
				// find out total pages
				totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

				if (totalpages <= currpage) {
					currpage = 0;
					request.setAttribute("currpage", "0");
				}

				//find the paginated mbr
				List searchList = AuditIncidentUtils.getAudits(incident_ID, rowsperpage, currpage, false);

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
					Audit_Incident ic = (Audit_Incident) searchList.get(i);
					ic.set_DATEFORMAT(user.getDateformat().getFormat());
					ic.set_TIMEFORMAT(user.getTimeformat().getFormat());
					ic.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils
							.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
				}
				request.setAttribute("auditmbrlist", searchList);
				return (mapping.findForward(TracingConstants.AUDIT_MBR_DETAIL));
			}
		}

		if (request.getParameter("search") == null) {
			return (mapping.findForward(TracingConstants.AUDIT_MBR));
		}

		List resultlist = null;
		// get number of records found
		if ((resultlist = AuditIncidentUtils
				.findIncidentByAuditSearchCriteria(daform, user, 0, 0, true)) == null
				|| resultlist.size() <= 0) {
			int rowsperpage = request.getParameter("rowsperpage") != null ? Integer.parseInt(request
					.getParameter("rowsperpage")) : TracingConstants.ROWS_PER_PAGE;
			if (rowsperpage < 1) rowsperpage = TracingConstants.ROWS_PER_PAGE;
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			request.setAttribute("currpage", Integer.toString(currpage));

			ActionMessages errors = new ActionMessages();
			ActionMessage error = new ActionMessage("error.no.incidentreport");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
		} else {
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
			rowcount = ((Long) resultlist.get(0)).intValue();
			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}

			//find the paginated mbr report
			List searchList = AuditIncidentUtils.findIncidentByAuditSearchCriteria(daform, user,
					rowsperpage, currpage, false);

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
				Incident ic = (Incident) searchList.get(i);
				ic.set_DATEFORMAT(user.getDateformat().getFormat());
				ic.set_TIMEFORMAT(user.getTimeformat().getFormat());
				ic.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
						.getTimezone()));
			}
			request.setAttribute("mbrlist", searchList);
		}
		return (mapping.findForward(TracingConstants.AUDIT_MBR));
	}
}