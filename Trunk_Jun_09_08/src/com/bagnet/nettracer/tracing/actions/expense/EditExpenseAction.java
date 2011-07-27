package com.bagnet.nettracer.tracing.actions.expense;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.bagnet.nettracer.tracing.bmo.ExpensePayoutBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.forms.ExpensePayoutForm;

public class EditExpenseAction extends BaseExpenseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward fwd = super.execute(mapping, form, request, response);
		if(fwd != mapping.findForward(SUCCESS)) {
			return fwd;
		}
		
		int epId = 0;
		String expenseId = request.getParameter("expense_id");
		if(expenseId != null){
			epId = Integer.parseInt(expenseId);
		} else {
			epId = (Integer)request.getSession().getAttribute("expense_id");
		}
		ExpensePayout ep = ExpensePayoutBMO.findExpensePayout(epId);
		ExpensePayoutForm epform = (ExpensePayoutForm) form;
		Agent user = (Agent)request.getSession().getAttribute("user");
		
		populateForm(epform, ep, user, request);
		return mapping.findForward(EDIT_SUCCESS);
	}

	
}
