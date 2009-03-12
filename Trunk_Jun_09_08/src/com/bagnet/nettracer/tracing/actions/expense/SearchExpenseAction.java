package com.bagnet.nettracer.tracing.actions.expense;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.bmo.ExpensePayoutBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.forms.SearchExpenseForm;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class SearchExpenseAction extends BaseExpenseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward fwd = super.execute(mapping, form, request, response);
		if (fwd != mapping.findForward(SUCCESS)) {
			return fwd;
		}
		if(request.getParameter("clear") != null) {
			form.reset(mapping, request);
			return mapping.findForward(SUCCESS);
		}
		Agent user = (Agent) request.getSession().getAttribute("user");
		
		if("1".equals(request.getParameter("approve1"))) {
			//batch approve
			if(!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_APPROVE_EXPENSE, user)) {
				return mapping.findForward(TracingConstants.NO_PERMISSION);
			}
			String temp = request.getParameter("payout_ID");
			String[] payouts = temp.split(",");
			for(String payout_id : payouts) {
				ExpensePayoutBMO.approveExpense(payout_id, user);
			}
			setupPending(request);
			return mapping.findForward(SUCCESS);
		}
		
		if("1".equals(request.getParameter("deny1"))) {
			//batch deny
			if(!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_APPROVE_EXPENSE, user)) {
				return mapping.findForward(TracingConstants.NO_PERMISSION);
			}
			String temp = request.getParameter("payout_ID");
			String[] payouts = temp.split(",");
			for(String payout_id : payouts) {
				ExpensePayoutBMO.denyExpense(payout_id, user);
			}
			setupPending(request);
			return mapping.findForward(SUCCESS);
		}
		
		SearchExpenseForm sef = (SearchExpenseForm) form;
		
		sef.setDateFormat(user.getDateformat().getFormat());
		sef.setTimeZone(user.getCurrenttimezone());
		int rowcount = ExpensePayoutBMO.countExpenses(sef, user);
		if(rowcount > 0) {
			int rowsperpage = request.getParameter("rowsperpage") != null ? Integer.parseInt(request
					.getParameter("rowsperpage")) : TracingConstants.ROWS_PER_PAGE;
			if (rowsperpage < 1) {
				rowsperpage = TracingConstants.ROWS_PER_PAGE;
			}
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
		return mapping.findForward(SUCCESS);
	}
}
