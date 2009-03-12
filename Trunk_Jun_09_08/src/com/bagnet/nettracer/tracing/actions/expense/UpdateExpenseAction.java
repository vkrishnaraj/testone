package com.bagnet.nettracer.tracing.actions.expense;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.bmo.ExpensePayoutBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.forms.ExpensePayoutForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;

public class UpdateExpenseAction extends BaseExpenseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward fwd = super.execute(mapping, form, request, response);
		if(fwd != mapping.findForward(SUCCESS)) {
			return fwd;
		}
		
		ExpensePayoutForm expenseForm = (ExpensePayoutForm) form;
		Agent user = (Agent) request.getSession().getAttribute("user");
		ExpensePayout ep = getUpdatedPayout(expenseForm, user);

		// set status to pending or approved
		Status st = new Status();
		
		if(expenseForm.getUpdateExpense() != null) {
			st.setStatus_ID(expenseForm.getStatus_id());
			if(expenseForm.getStatus_id() == TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED) {
				st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_PENDING);
			}
			addComment(ep, user, "expense.comment.updated", expenseForm.getNewComment());
		}
		else if(expenseForm.getApproveExpense() != null) {
			st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED);
			addComment(ep, user, "expense.comment.approved", expenseForm.getNewComment());
		}
		else if(expenseForm.getDenyExpense() != null) {
			st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_DENIED);
			addComment(ep, user, "expense.comment.denied", expenseForm.getNewComment());
		}
		else if(expenseForm.getPayExpense() != null) {
			st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_PAID);
			addComment(ep, user, "expense.comment.paid", expenseForm.getNewComment());
		}
		
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
