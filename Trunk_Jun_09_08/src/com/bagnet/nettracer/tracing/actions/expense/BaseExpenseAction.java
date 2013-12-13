package com.bagnet.nettracer.tracing.actions.expense;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.BeanUtils;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.bmo.ExpensePayoutBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Comment;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.ExpenseType;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.forms.ExpensePayoutForm;
import com.bagnet.nettracer.tracing.forms.SearchExpenseForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public abstract class BaseExpenseAction extends CheckedAction {

	protected final static String SUCCESS = "success";
	protected final static String ERROR = "error";
	protected final static String INCIDENT_ID = "incident_id";
	protected final static String CREATE_SUCCESS = "create_success";
	protected final static String EDIT_SUCCESS = "edit_success";
	protected final static String UPDATE_SUCCESS = "update_success";
	protected final static String SAVE_SUCCESS = "save_success";
	protected final static String ERROR_NO_INCIDENT = "error_no_incident";

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		if (session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		Agent user = (Agent) session.getAttribute("user");

		if ("Create".equals(mapping.getParameter())) {
			if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_EXPENSE, user)) {
				return (mapping.findForward(TracingConstants.NO_PERMISSION));
			}
		} else if ("Save".equals(mapping.getParameter())) {
			if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_EXPENSE, user)) {
				return (mapping.findForward(TracingConstants.NO_PERMISSION));
			}
		} else if ("Update".equals(mapping.getParameter())) {
			ExpensePayoutForm epf = (ExpensePayoutForm) form;
			if (epf.getApproveExpense() != null
					&& !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_APPROVE_EXPENSE, user)) {
				return (mapping.findForward(TracingConstants.NO_PERMISSION));
			} else if (epf.getDenyExpense() != null
					&& !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_APPROVE_EXPENSE, user)) {
				return (mapping.findForward(TracingConstants.NO_PERMISSION));
			} else if (epf.getPayExpense() != null
					&& !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PAY_EXPENSE, user)) {
				return (mapping.findForward(TracingConstants.NO_PERMISSION));
			} else if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_EXPENSE, user)) {
				return (mapping.findForward(TracingConstants.NO_PERMISSION));
			}

		} else if ("Edit".equals(mapping.getParameter())) {
			if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_EXPENSE, user)) {
				return (mapping.findForward(TracingConstants.NO_PERMISSION));
			}
		} else if ("BatchApprove".equals(mapping.getParameter())) {
			if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_APPROVE_EXPENSE, user)) {
				return (mapping.findForward(TracingConstants.NO_PERMISSION));
			}
		} else if ("Approve".equals(mapping.getParameter())) {
			if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_APPROVE_EXPENSE, user)) {
				return (mapping.findForward(TracingConstants.NO_PERMISSION));
			}
		} else if ("Deny".equals(mapping.getParameter())) {
			if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_APPROVE_EXPENSE, user)) {
				return (mapping.findForward(TracingConstants.NO_PERMISSION));
			}
		} else if ("Search".equals(mapping.getParameter())) {
			if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MODIFY_CLAIM, user)) {
				return (mapping.findForward(TracingConstants.NO_PERMISSION));
			}
		}else {
			return mapping.findForward(TracingConstants.NO_PERMISSION);
		}

		//confirm token except for searches
		if (!"Search".equals(mapping.getParameter()) && !manageToken(request)) {
			return (mapping.findForward(TracingConstants.INVALID_TOKEN));
		}
		
		if (form instanceof ExpensePayoutForm) {
			ExpensePayoutForm epf = (ExpensePayoutForm) form;
			epf.setTz(user.getCurrenttimezone());
			epf.setDateFormat(user.getDateformat().getFormat());	
		}
		
		
		return mapping.findForward(SUCCESS);
	}

	protected void populateForm(ExpensePayoutForm epform, ExpensePayout ep, Agent user, HttpServletRequest request) {
		BeanUtils.copyProperties(ep, epform);
		if (ep.getBdo() != null) {
			epform.setBdo_ref(ep.getBdo().getBDO_ID_ref());
			epform.setBdo_id(Integer.toString(ep.getBdo().getBDO_ID()));
		}
		epform.setCreateStation(ep.getStation().getStationcode());
		epform.setCreateUser(ep.getAgent().getUsername());
		epform.setDateFormat(user.getDateformat().getFormat());
		epform.setExpenselocation_ID(ep.getExpenselocation().getStation_ID());
		epform.setExpensetype_id(ep.getExpensetype().getExpensetype_ID());
		epform.setIncident_ID(ep.getIncident().getIncident_ID());
		epform.setOldComments(new ArrayList<Comment>(ep.getComments()));
		epform.setStatus_id(ep.getStatus().getStatus_ID());
		epform.setTz(user.getCurrenttimezone());
		epform.setPaymentType(ep.getPaytype());
		epform.setDistributemethod(ep.getDistributemethod());
		epform.setCancelreason(ep.getCancelreason());
		epform.setCancelcount(ep.getCancelcount());
		epform.setOrdernum(ep.getOrdernum());
		epform.setSlvnum(ep.getSlvnum());
		epform.setSeccode(ep.getSeccode());
		epform.setWssubmitp(ep.getWssubmitp());
		epform.setWssubmitc((String)request.getSession().getAttribute("wssubmitc"));
		epform.setErrormsg((String)request.getSession().getAttribute("errormsg"));
	}

	protected ExpensePayout createNewPayout(ExpensePayoutForm expenseForm, Agent user) throws Exception {
		ExpensePayout ep = new ExpensePayout();
		BeanUtils.copyProperties(expenseForm, ep);

		ep.setAgent(user);

		ep.setCurrency(Currency.getInstance(expenseForm.getCurrency_ID()));
		
		ep.setCreatedate(new Date());

		Station loc = new Station();
		loc.setStation_ID(expenseForm.getExpenselocation_ID());
		ep.setExpenselocation(loc);

		ExpenseType et = new ExpenseType();
		et.setExpensetype_ID(expenseForm.getExpensetype_id());
		
		// If limited version of page (DEFAULT)
		if (expenseForm.getPaymentType() != null) {
			ep.setPaytype(expenseForm.getPaymentType());
			if (expenseForm.getPaymentType().equals(TracingConstants.ENUM_VOUCHER)) {
				ep.setVoucheramt(expenseForm.getCheckamt());
				ep.setDistributemethod(expenseForm.getDistributemethod());
				ep.setCheckamt(0);
			} else if (expenseForm.getPaymentType().equals(TracingConstants.ENUM_CCREF)) {
				ep.setCreditCardRefund(expenseForm.getCheckamt());
				ep.setCheckamt(0);
			} else if (expenseForm.getPaymentType().equals(TracingConstants.ENUM_DRAFT)) {
				// Do nothing -- this occurs by default
			} else if (expenseForm.getPaymentType().equals(TracingConstants.ENUM_INC)) {
				ep.setIncidentalAmountClaimed(expenseForm.getCheckamt());
				ep.setCheckamt(0);
			} else if (expenseForm.getPaymentType().equals(TracingConstants.ENUM_MILE)) {
				Double x = new Double(expenseForm.getCheckamt());
				ep.setMileageamt(x.intValue());
				ep.setCheckamt(0);
			}
		} else {
			// If complete version of page (WESTJET)
			ep.setCheckamt(expenseForm.getCheckamt());
			ep.setCreditCardRefund(expenseForm.getCreditCardRefund());
			ep.setDraft(expenseForm.getDraft());
			ep.setDraftpaiddate(expenseForm.getDraftpaiddate());
			ep.setDraftreqdate(expenseForm.getDraftreqdate());
			ep.setIncidentalAmountAuth(expenseForm.getIncidentalAmountAuth());
			ep.setIncidentalAmountClaimed(expenseForm.getIncidentalAmountClaimed());
			ep.setMileageamt(expenseForm.getMileageamt());
			ep.setVoucheramt(expenseForm.getVoucheramt());
			ep.setVoucherExpirationDate(expenseForm.getVoucherExpirationDate());
		}
		
		ep.setExpensetype(et);
		ep.setStation(user.getStation());

		return ep;
	}

	protected ExpensePayout getUpdatedPayout(ExpensePayoutForm epf, Agent user) throws Exception {
		ExpensePayout ep = ExpensePayoutBMO.findExpensePayout(epf.getExpensepayout_ID());
		if (ep == null)
			return null;

		ep.setCheckamt(epf.getCheckamt());
		ep.setCreditCardRefund(epf.getCreditCardRefund());
		ep.setCurrency(Currency.getInstance(epf.getCurrency_ID()));
		ep.setDraft(epf.getDraft());
		ep.setDraftpaiddate(epf.getDraftpaiddate());
		ep.setDraftreqdate(epf.getDraftreqdate());
		ep.setIncidentalAmountAuth(epf.getIncidentalAmountAuth());
		ep.setIncidentalAmountClaimed(epf.getIncidentalAmountClaimed());
		ep.setMileageamt(epf.getMileageamt());
		ep.setVoucheramt(epf.getVoucheramt());
		ep.setVoucherExpirationDate(epf.getVoucherExpirationDate());
		
		ExpenseType et = new ExpenseType();
		et.setExpensetype_ID( epf.getExpensetype_id());
		ep.setExpensetype(et);
		
		ep.setPaycode(epf.getPaycode());
		return ep;
	}

	protected void addComment(ExpensePayout ep, Agent user, String key, String content) {
		// create a comment
		Comment com = new Comment(user);
		String tmp = TracerUtils.getText(key, user);
		tmp += content != null ? content : "";
		com.setContent(tmp);

		if(ep.getComments() == null){
			ep.setComments(new HashSet<Comment>());
		}
		ep.getComments().add(com);
	}
	
	protected void setupPending(HttpServletRequest request) {
		SearchExpenseForm sef = new SearchExpenseForm();
		sef.setStatusId(TracingConstants.EXPENSEPAYOUT_STATUS_PENDING);
		Agent user = (Agent) request.getSession().getAttribute("user");
		sef.setDateFormat(user.getDateformat().getFormat());
		sef.setTimeZone(user.getCurrenttimezone());
		long rowcount = ExpensePayoutBMO.countExpenses(sef, user);
		if(rowcount > 0) {
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, request.getSession());
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			int totalpages = 0;

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1")) currpage++;
			if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1")) currpage--;

			request.setAttribute("currpage", Integer.toString(currpage));
			
			sef.setCurrentPage(currpage);
			sef.setRowsPerPage(rowsperpage);
			List<ExpensePayout> expenseList = ExpensePayoutBMO.searchExpenses(sef, user);
			request.setAttribute("expenseList", expenseList);

			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				request.setAttribute("currpage", "0");
			}
			if (totalpages > 1) {
				ArrayList<String> al = new ArrayList<String>();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}
		}
	}
}
