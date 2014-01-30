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

import com.bagnet.nettracer.tracing.bmo.LossCodeBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.db.ItemType;
import com.bagnet.nettracer.tracing.db.audit.Audit_Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.audit.AuditLosscodeUtils;

/**
 * Implementation of <strong>Action </strong> that is responsible for adding,
 * modifying, and deleting codes.
 * 
 * @author Ankur Gupta
 */
public final class ManageCodes extends Action {
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

		List<ItemType> reportTypes = IncidentUtils.retrieveItemTypes();
		request.setAttribute("reportTypes", reportTypes);

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
			String code_id = request.getParameter("code_id");
			Company_specific_irregularity_code code = LossCodeBMO.getCode(code_id);
			dForm.set("code_id", code_id);
			dForm.set("report_type", "" + code.getReport_type());
			dForm.set("loss_code", "" + code.getLoss_code());
			dForm.set("description", code.getDescription());
			dForm.set("companycode_ID", code.getCompany().getCompanyCode_ID());
			String visible = "";
			if (code.isShow_to_limited_users() == true)
				visible = "1";
			dForm.set("visibleToLimited", visible);
			dForm.set("active", code.isActive() ? "1" : "");
			dForm.set("controllable", code.isControllable() ? "1" : "");
			dForm.set("departStation", code.isDepartStation() ? "1" : "");
			dForm.set("transferStation", code.isTransferStation() ? "1" : "");
			dForm.set("destinationStation", code.isDestinationStation() ? "1" : "");
			dForm.set("anyStation", code.isAnyStation() ? "1" : "");
			return mapping.findForward(TracingConstants.EDIT_CODE);
		}

		if (request.getParameter("addNew") != null) {
			dForm.set("visibleToLimited", "on");
			return mapping.findForward(TracingConstants.EDIT_CODE);
		}

		if (request.getParameter("delete1") != null && !request.getParameter("delete1").equals("")) {
			String code_ids = request.getParameter("loss_code");
			boolean escape = false;

			boolean error = false;
			int i = 0;

			@SuppressWarnings("unused")
			String error_ids = "";

			while (true) {
				String code_id = "";
				if (code_ids.indexOf(",") != -1) {
					int index = code_ids.indexOf(",");
					code_id = code_ids.substring(0, index);
					code_ids = code_ids.substring(index + 1);
				} else {
					code_id = code_ids;
					escape = true;
				}

				Company_specific_irregularity_code code = LossCodeBMO.getCode(code_id);

				//Check if there is any incident with this code.
				if (IncidentUtils.incidentWithLossCode("" + code.getLoss_code(), code.getReport_type())) {
					if (i > 0) error_ids += ", ";

					error_ids += code.getLoss_code();
					error = true;
				} else {
					companyCode = code.getCompany().getCompanyCode_ID();
					HibernateUtils.deleteCode(code);
					if (user.getStation().getCompany().getVariable().getAudit_loss_codes() == 1) {
						Audit_Company_specific_irregularity_code audit_code = AuditLosscodeUtils
								.getAuditLossCode(code, user);
						if (audit_code != null) {
							audit_code.setReason_modified("Deleted");
							HibernateUtils.saveNew(audit_code);
						}
					}
				}

				if (escape) break;
			}

			if (error) {
				ActionMessage errorC = new ActionMessage("error.deleting.losscode");
				errors.add(ActionMessages.GLOBAL_MESSAGE, errorC);
				saveMessages(request, errors);
			}
		}

		if (request.getParameter("save") != null) {
			Company_specific_irregularity_code s = null;

			boolean isNew = true;
			String code_id = request.getParameter("code_id");
			if (code_id == null || code_id.length() < 1) {
				Company_specific_irregularity_code code = LossCodeBMO.getLossCode(new Integer( (String)dForm
						.get("loss_code")).intValue(), new Integer( (String) dForm.get("report_type")).intValue(), AdminUtils.getCompany(companyCode));
				if (code != null) {
					ActionMessage error = new ActionMessage("error.creating.losscode.exists");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);

					dForm.set("description", request.getParameter("description"));
					dForm.set("companycode_ID", companyCode);
					dForm.set("report_type", request.getParameter("report_type"));
					dForm.set("locale", request.getParameter("locale"));
					return mapping.findForward(TracingConstants.EDIT_CODE);
				} else {
					s = new Company_specific_irregularity_code();
					String loss_code = request.getParameter("loss_code");
					s.setLoss_code(Integer.parseInt(loss_code));
					s.setReport_type(Integer.parseInt(request.getParameter("report_type")));
					

					Company comp = new Company();
					comp.setCompanyCode_ID(companyCode);
					s.setCompany(comp);
				}
			} else {
				isNew = false;
				s = LossCodeBMO.getCode(code_id);
			}
			
			s.setDescription((String)dForm.get("description"));
			boolean show = false;
			if (((String)dForm.get("visibleToLimited")).equals("1")) {
				show = true;
			}
			s.setShow_to_limited_users(show);
			
			boolean active = false;
			if (((String)dForm.get("active")).equals("1")) {
				active = true;
			}
			s.setActive(active);
			
			boolean controllable = false;
			if (((String)dForm.get("controllable")).equals("1")) {
				controllable = true;
			}
			s.setControllable(controllable);
			
			boolean departStation = false;
			if (((String)dForm.get("departStation")).equals("1")) {
				departStation = true;
			}
			s.setDepartStation(departStation);
			
			boolean transferStation = false;
			if (((String)dForm.get("transferStation")).equals("1")) {
				transferStation = true;
			}
			s.setTransferStation(transferStation);
			
			boolean destinationStation = false;
			if (((String)dForm.get("destinationStation")).equals("1")) {
				destinationStation = true;
			}
			s.setDestinationStation(destinationStation);
			
			boolean anyStation = false;
			if (((String)dForm.get("anyStation")).equals("1")) {
				anyStation = true;
			}
			s.setAnyStation(anyStation);
			
			try {
				HibernateUtils.saveCode(s, isNew);

				if (user.getStation().getCompany().getVariable().getAudit_loss_codes() == 1) {
					Audit_Company_specific_irregularity_code audit_code = AuditLosscodeUtils
							.getAuditLossCode(s, user);
					if (audit_code != null) {
						HibernateUtils.saveNew(audit_code);
					}
				}

			} catch (Exception e) {
				ActionMessage error = new ActionMessage("error.creating.losscode");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}

			if (s != null) request.setAttribute("report_type", "" + s.getReport_type());
		}

		String report_type = request.getParameter("report_type");

		if (report_type == null || report_type.equals("-1")) report_type = "";

		List<Company_specific_irregularity_code> codeList = LossCodeBMO.getCodes(companyCode, report_type, dForm, 0, 0);
		if (codeList != null && codeList.size() > 0) {
			/** ************ pagination ************* */
			int rowcount = -1;
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_ADMIN_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			int totalpages = 0;

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1")) currpage++;
			if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1")) currpage--;

			request.setAttribute("currpage", Integer.toString(currpage));

			// get row count
			rowcount = codeList.size();

			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}
			codeList = LossCodeBMO.getCodes(companyCode, report_type, dForm, rowsperpage, currpage);

			if (currpage + 1 == totalpages) request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList<String> al = new ArrayList<String>();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}

			/** ************ end of pagination ************* */
			request.setAttribute("codeList", codeList);
		} else {
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_ADMIN_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			request.setAttribute("currpage", Integer.toString(currpage));
		}

		return mapping.findForward(TracingConstants.VIEW_CODES);
	}
}