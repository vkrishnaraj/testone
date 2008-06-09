/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Work_Shift;
import com.bagnet.nettracer.tracing.db.audit.Audit_Work_Shift;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.audit.AuditWorkshiftUtils;

/**
 * Implementation of <strong>Action </strong> that is responsible for adding,
 * modifying, and deleting shift data.
 * 
 * @author Ankur Gupta
 */
public final class ManageShift extends Action {
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
		if (request.getParameter("edit") != null) {
			String shiftId = request.getParameter("shiftId");
			Work_Shift shift = AdminUtils.getShift(shiftId);
			dForm.set("shift_code", shift.getShift_code());
			dForm.set("shift_description", shift.getShift_description());
			dForm.set("companycode_ID", shift.getCompany().getCompanyCode_ID());
			dForm.set("locale", shift.getLocale());
			return mapping.findForward(TracingConstants.EDIT_SHIFT);
		}

		if (request.getParameter("addNew") != null) {
			return mapping.findForward(TracingConstants.EDIT_SHIFT);
		}

		if (request.getParameter("delete1") != null && !request.getParameter("delete1").equals("")) {
			String shiftId = request.getParameter("shiftId");
			Work_Shift shift = AdminUtils.getShift(shiftId);

			ActionMessage error = null;
			if (shift.getAgents().size() > 0) error = new ActionMessage("error.deleting.shift.agent");

			if (error != null) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				companyCode = shift.getCompany().getCompanyCode_ID();
				HibernateUtils.delete(shift);

				if (user.getStation().getCompany().getVariable().getAudit_shift() == 1) {
					Audit_Work_Shift audit_shift = AuditWorkshiftUtils.getAuditShift(shift, user);
					audit_shift.setReason_modified("Deleted");
					if (audit_shift != null) {
						HibernateUtils.saveNew(audit_shift);
					}
				}
			}
		}
		if (request.getParameter("save") != null) {
			Work_Shift s = new Work_Shift();
			s.setLocale((String) dForm.get("locale"));
			s.setShift_code((String) dForm.get("shift_code"));
			s.setShift_description((String) dForm.get("shift_description"));

			Company c = new Company();
			c.setCompanyCode_ID(companyCode);
			s.setCompany(c);
			try {
				HibernateUtils.saveShift(s, user);
			} catch (Exception e) {
				ActionMessage error = new ActionMessage("error.creating.shift");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
		}
		List shiftList = AdminUtils.getShifts(dForm, companyCode, 0, 0);

		if (shiftList != null && shiftList.size() > 0) {
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
			rowcount = shiftList.size();

			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}

			shiftList = AdminUtils.getShifts(dForm, companyCode, rowsperpage, currpage);

			if (currpage + 1 == totalpages) request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList al = new ArrayList();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}

			/** ************ end of pagination ************* */

			request.setAttribute("shiftList", shiftList);
		} else {
			int rowsperpage = request.getParameter("rowsperpage") != null ? Integer.parseInt(request
					.getParameter("rowsperpage")) : TracingConstants.ROWS_PER_PAGE;
			if (rowsperpage < 1) rowsperpage = TracingConstants.ROWS_PER_PAGE;
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			request.setAttribute("currpage", Integer.toString(currpage));
		}

		return mapping.findForward(TracingConstants.VIEW_SHIFTS);
	}
}