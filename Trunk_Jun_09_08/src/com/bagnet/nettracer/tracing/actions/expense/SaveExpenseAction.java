package com.bagnet.nettracer.tracing.actions.expense;

import java.util.Currency;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Comment;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.ExpenseType;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.forms.ExpensePayoutForm;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class SaveExpenseAction extends BaseExpenseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		// BaseExpenseAction does core validations
		ActionForward fwd = super.execute(mapping, form, request, response);
		if (fwd != mapping.findForward(SUCCESS)) {
			return fwd;
		}

		ActionMessages messages = new ActionMessages();

		IncidentBMO ibmo = new IncidentBMO();

		ExpensePayoutForm expenseForm = (ExpensePayoutForm) form;
		Agent user = (Agent) request.getSession().getAttribute("user");
		ExpensePayout ep = createNewPayout(expenseForm, user);
		addComment(ep, user, "expense.comment.new", expenseForm.getNewComment());

		// set status to pending or approved
		Status st = new Status();
		st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED);
		Company_Specific_Variable csv = AdminUtils.getCompVariable(user.getCompanycode_ID());
		if (Math.abs(ep.getCheckamt()) > 0.001) {
			if (csv.getMin_interim_approval_check() >= -0.001
					&& (csv.getMin_interim_approval_check() - Math.abs(ep.getCheckamt())) < -0.001) {
				st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_PENDING);
			}
		}
		if (Math.abs(ep.getMileageamt()) > 0) {
			if (csv.getMin_interim_approval_miles() >= -0.001
					&& (csv.getMin_interim_approval_miles() - Math.abs(ep.getMileageamt())) < -0.001) {
				st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_PENDING);
			}
		}
		if (Math.abs(ep.getVoucheramt()) > 0.001) {
			if (csv.getMin_interim_approval_voucher() >= -0.001
					&& (csv.getMin_interim_approval_voucher() - Math.abs(ep.getVoucheramt())) < -0.001) {
				st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_PENDING);
			}
		}
		if (Math.abs(ep.getCreditCardRefund()) > 0.001) {
			if (csv.getMin_interim_approval_cc_refund() >= -0.001
					&& (csv.getMin_interim_approval_cc_refund() - Math.abs(ep.getCreditCardRefund())) < -0.001) {
				st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_PENDING);
			}
		}

		if (Math.abs(ep.getIncidentalAmountAuth()) > 0.001) {
			if (csv.getMin_interim_approval_incidental() >= -0.001
					&& (csv.getMin_interim_approval_incidental() - Math.abs(ep.getIncidentalAmountAuth())) < -0.001) {
				st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_PENDING);
			}
		}
		ep.setStatus(st);

		String incidentId = ((IncidentForm) request.getSession().getAttribute("incidentForm")).getIncident_ID();
		ibmo.saveExpense(ep, incidentId, user);

		request.getSession().setAttribute("getclaimfa", "1");
		request.getSession().setAttribute("incidentid", ep.getIncident().getIncident_ID());
		return mapping.findForward(SAVE_SUCCESS);

	}

}
