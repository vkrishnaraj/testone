package com.bagnet.nettracer.tracing.actions.expense;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.actions.expense.BaseExpenseAction;
import com.bagnet.nettracer.tracing.bmo.ExpensePayoutBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.forms.ExpensePayoutForm;

public class DenyExpenseAction extends BaseExpenseAction {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward fwd = super.execute(mapping, form, request, response);
		if(fwd != mapping.findForward(SUCCESS)) {
			return fwd;
		}
		
		ExpensePayoutForm expenseForm = (ExpensePayoutForm) form;
		Agent user = (Agent) request.getSession().getAttribute("user");
		
		ExpensePayout ep = ExpensePayoutBMO.findExpensePayout(Integer.parseInt((String)request.getParameter("expense_id")));
		
		if(ep == null){
			return mapping.findForward(ERROR);
		}
		
		Status st = new Status();
		st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_DENIED);
		addComment(ep, user, "expense.comment.denied", expenseForm.getNewComment());
		ep.setStatus(st);
		
		if(ExpensePayoutBMO.updateExpense(ep, user)) {
			request.getSession().setAttribute("getclaimfa", "1");
			request.getSession().setAttribute("incidentid", ep.getIncident().getIncident_ID());
			return mapping.findForward(UPDATE_SUCCESS);
		}
		else {
			return mapping.findForward(ERROR);
		}
	}
}
