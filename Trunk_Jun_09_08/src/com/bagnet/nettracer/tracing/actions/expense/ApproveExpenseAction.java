package com.bagnet.nettracer.tracing.actions.expense;


import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.actions.expense.BaseExpenseAction;
import com.bagnet.nettracer.tracing.bmo.ExpensePayoutBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.forms.ExpensePayoutForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class ApproveExpenseAction extends BaseExpenseAction {
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
		
		ActionMessages messages = new ActionMessages();
		
		Status st = new Status();
		st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED);
		if (!addComment(ep, user, "expense.comment.approved", expenseForm.getNewComment())) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.maxlength", new Object[]{TracerUtils.getText("colname.new.comments", user), TracingConstants.EXPENSE_COMMENT_CHAR_LENGTH}));
			saveMessages(request, messages);
			return mapping.findForward(UPDATE_SUCCESS);
		}
		ep.setStatus(st);
		ep.setApproval_date(new Date());
		if(ExpensePayoutBMO.updateExpense(ep, user)) {
			request.getSession().setAttribute("getclaimfa", "1");
			request.getSession().setAttribute("incidentid", ep.getIncident().getIncident_ID());
			if (SpringUtils.getReservationIntegration().isWriteCommentToPnrOn()
					&& SpringUtils.getReservationIntegration().isWriteExpensesToPnrOn()) {
				String formateddatetime = DateUtils.formatDate(TracerDateTime.getGMTDate(),
						TracingConstants.DB_DATETIMEFORMAT, null, TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
								user.getDefaulttimezone()).getTimezone()));
				SpringUtils.getReservationIntegration().writeCommentToPNR(
						TracingConstants.CMT_APPROVED_INTERIM + formateddatetime,
						ep.getIncident().getRecordlocator());
			}	
			return mapping.findForward(UPDATE_SUCCESS);
		}
		else {
			return mapping.findForward(ERROR);
		}
	}
}
