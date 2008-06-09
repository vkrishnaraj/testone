/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Airport;
import com.bagnet.nettracer.tracing.db.audit.Audit_Airport;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.audit.AuditAirportUtils;

/**
 * Implementation of <strong>Action </strong> that is responsible for adding,
 * modifying, and deleting shift data.
 * 
 * @author Ankur Gupta
 */
public final class ManageAirport extends Action {
	
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

		ActionMessages errors = new ActionMessages();
		DynaValidatorForm dForm = (DynaValidatorForm) form;

		String companyCode = "";
		if (request.getParameter("companyCode") != null) companyCode = request
				.getParameter("companyCode");
		else {
			if (dForm.get("companycode_ID") != null
					&& ((String) dForm.get("companycode_ID")).length() > 0) companyCode = (String) dForm
					.get("companycode_ID");
			else companyCode = user.getStation().getCompany().getCompanyCode_ID();
		}
		dForm.set("companycode_ID", companyCode);
		
		if (request.getParameter("addNew") != null || request.getParameter("edit") != null) {
			MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
			
			ArrayList al = new ArrayList();

			al.add(new LabelValueBean(messages.getMessage(new Locale(user.getCurrentlocale()), "airportadmin.country.us"), "0"));
			al.add(new LabelValueBean(messages.getMessage(new Locale(user.getCurrentlocale()), "airportadmin.country.canada"), "1"));
			al.add(new LabelValueBean(messages.getMessage(new Locale(user.getCurrentlocale()), "airportadmin.country.other"), "2"));
			
			session.setAttribute("countryTypeList", al);
		}
		
		if (request.getParameter("edit") != null) {
			
			String id = request.getParameter("id");
			Airport airport = AdminUtils.getAirport(id);
			dForm.set("airport_ID", Integer.toString(airport.getId()));
			dForm.set("airport_code", airport.getAirport_code());
			dForm.set("airport_description", airport.getAirport_desc());
			dForm.set("companycode_ID", airport.getCompanyCode_ID());
			dForm.set("locale", airport.getLocale());
			dForm.set("airport_country", Integer.toString(airport.getCountry()));
			return mapping.findForward(TracingConstants.EDIT_AIRPORT);
		}

		if (request.getParameter("addNew") != null) {
			return mapping.findForward(TracingConstants.EDIT_AIRPORT);
		}

		if (request.getParameter("delete1") != null && !request.getParameter("delete1").equals("")) {
			String id = request.getParameter("id");
			Airport airport = AdminUtils.getAirport(id);

			ActionMessage error = null;
			//if (shift.getAgents().size() > 0)
			//	error = new ActionMessage("error.deleting.shift.agent");

			if (error != null) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				companyCode = airport.getCompanyCode_ID();
				HibernateUtils.delete(airport);

				if (user.getStation().getCompany().getVariable().getAudit_airport() == 1) {
					Audit_Airport audit_airport = AuditAirportUtils.getAuditAirport(airport, user);
					if (audit_airport != null) {
						audit_airport.setReason_modified("Deleted");
						HibernateUtils.saveNew(audit_airport);
					}
				}

			}
		}
		
		if (request.getParameter("save") != null) {
			Airport ap = new Airport();
			String airportId = (String)dForm.get("airport_ID");
			
			ap.setLocale((String) dForm.get("locale"));
			ap.setAirport_code((String) dForm.get("airport_code"));
			ap.setAirport_desc((String) dForm.get("airport_description"));
			ap.setCompanyCode_ID(companyCode);
			ap.setCountry(Integer.parseInt((String) dForm.get("airport_country")));
			
			if (airportId != null && !airportId.equals("")) {
				ap.setId(new Integer(airportId));
			} else {
				// Check to ensure airport does not already exist.
				if (AdminUtils.getAirport(companyCode, ap.getAirport_code(), ap.getLocale()) != null) {
					ActionMessage error = new ActionMessage("error.creating.duplicate.airport");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
					return mapping.findForward(TracingConstants.EDIT_AIRPORT);
				}
			}


			try {
				HibernateUtils.saveAirport(ap, user);
			} catch (Exception e) {
				ActionMessage error = new ActionMessage("error.creating.airport");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
		}
		List airportList = AdminUtils.getAirports(companyCode, dForm, 0, 0);

		if (airportList != null && airportList.size() > 0) {
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
			rowcount = airportList.size();

			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}

			airportList = AdminUtils.getAirports(companyCode, dForm, rowsperpage, currpage);

			if (currpage + 1 == totalpages) request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList al = new ArrayList();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}

			/** ************ end of pagination ************* */

			request.setAttribute("airportsList", airportList);
		} else {
			int rowsperpage = request.getParameter("rowsperpage") != null ? Integer.parseInt(request
					.getParameter("rowsperpage")) : TracingConstants.ROWS_PER_PAGE;
			if (rowsperpage < 1) rowsperpage = TracingConstants.ROWS_PER_PAGE;
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			request.setAttribute("currpage", Integer.toString(currpage));
		}

		return mapping.findForward(TracingConstants.VIEW_AIRPORTS);
	}
}