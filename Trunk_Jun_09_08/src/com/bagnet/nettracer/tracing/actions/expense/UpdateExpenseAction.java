package com.bagnet.nettracer.tracing.actions.expense;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.bmo.ExpensePayoutBMO;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.UsergroupBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.UserGroup;
import com.bagnet.nettracer.tracing.forms.ExpensePayoutForm;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class UpdateExpenseAction extends BaseExpenseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward fwd = super.execute(mapping, form, request, response);
		if (fwd != mapping.findForward(SUCCESS)) {
			return fwd;
		}
		ActionMessages errors = new ActionMessages();

		ExpensePayoutForm expenseForm = (ExpensePayoutForm) form;
		Agent user = (Agent) request.getSession().getAttribute("user");
		ExpensePayout ep = getUpdatedPayout(expenseForm, user);
		
		request.getSession().setAttribute("expense_id", ep.getExpensepayout_ID());
		
		boolean cbsProcess=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BSO_PROCESS, user) && !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BSO_ADMIN, user); 
		request.setAttribute("swaCbsProcess", cbsProcess);
		// set status to pending or approved
		Status st = new Status();
		
		if (expenseForm.getUpdateExpense() != null) {
			st.setStatus_ID(expenseForm.getStatus_id());
			if (expenseForm.getStatus_id() == TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED 
					|| expenseForm.getStatus_id() == TracingConstants.EXPENSEPAYOUT_STATUS_PENDING) {

				UserGroup group=UsergroupBMO.getUsergroup(user.getUsergroup_id());
				double bsoLimit=0;
				if(group!=null && group.getBsoLimit()>0){
					bsoLimit=group.getBsoLimit();
				}
				
				if(bsoLimit>0 && ep.getCheckamt()>bsoLimit && cbsProcess){
					ActionMessage error = new ActionMessage("");
					error = new ActionMessage("unable.create.over.bso.limit", new Object[]{bsoLimit});
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					request.setAttribute("passBSOLimit", "1");
					saveMessages(request, errors);
					return mapping.findForward(UPDATE_SUCCESS);
				}
				
				st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED);
				Company_Specific_Variable csv = AdminUtils.getCompVariable(user.getCompanycode_ID());
				if (Math.abs(ep.getCheckamt()) > 0.001) {
					if ((csv.getMin_interim_approval_check() >= -0.001
							&& (csv.getMin_interim_approval_check() - Math.abs(ep.getCheckamt())) < -0.001) 
							&& (bsoLimit==0 || !cbsProcess)) {
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
			}
			addComment(ep, user, "expense.comment.updated", expenseForm.getNewComment());
		} else if (expenseForm.getApproveExpense() != null) {
			st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED);
			ep.setApproval_date(TracerDateTime.getGMTDate());
			addComment(ep, user, "expense.comment.approved", expenseForm.getNewComment());
		} else if (expenseForm.getDenyExpense() != null) {
			st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_DENIED);
			addComment(ep, user, "expense.comment.denied", expenseForm.getNewComment());
			ep.setApproval_date(TracerDateTime.getGMTDate());
		} else if (expenseForm.getPayExpense() != null) {
			st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_PAID);
			addComment(ep, user, "expense.comment.paid", expenseForm.getNewComment());
		} else if (expenseForm.getUpdateRemarkOnly() != null) {
			int epId = ep.getExpensepayout_ID();
			if(ExpensePayoutBMO.addComment(epId, user, "expense.comment.comment", expenseForm.getNewComment())) {
				return mapping.findForward(UPDATE_SUCCESS);
			}
			else {
				return mapping.findForward(ERROR);
			}
		} else if (expenseForm.getStatus_id() == TracingConstants.EXPENSEPAYOUT_STATUS_PAID) {
			st.setStatus_ID(expenseForm.getStatus_id());
			ep.setPrintcount(expenseForm.getPrintcount());
			//Added to remark when print status is "No"
			if (expenseForm.getToremark().equals("yes")) {
				String incidentId = ((IncidentForm) request.getSession().getAttribute("incidentForm")).getIncident_ID();
				IncidentBMO ibmo = new IncidentBMO();
		        SimpleDateFormat df2 = new SimpleDateFormat("MMM dd, yyyy hh:mm");
		        String dateText = df2.format(new Date(System.currentTimeMillis()));
				String contents= "User Name: " + user.getUsername() + "\n" + 
		                 "LUV Voucher Printing Time: " + dateText;
				ibmo.insertRemark(contents,incidentId, user, TracingConstants.REMARK_REGULAR);
			}
		}

		ep.setStatus(st);
		if (ExpensePayoutBMO.updateExpense(ep, user)) {
			request.getSession().setAttribute("getclaimfa", "1");
			request.getSession().setAttribute("incidentid", ep.getIncident().getIncident_ID());
			String formateddatetime = DateUtils.formatDate(TracerDateTime.getGMTDate(),
					TracingConstants.DB_DATETIMEFORMAT, null, TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
							user.getDefaulttimezone()).getTimezone()));
			if (expenseForm.getApproveExpense() != null) {
				if (SpringUtils.getReservationIntegration().isWriteCommentToPnrOn()
						&& SpringUtils.getReservationIntegration().isWriteExpensesToPnrOn()) {
					SpringUtils.getReservationIntegration().writeCommentToPNR(
							TracingConstants.CMT_APPROVED_INTERIM + formateddatetime,
							ep.getIncident().getRecordlocator());
				}
			} else if (expenseForm.getDenyExpense() != null) {
				if (SpringUtils.getReservationIntegration().isWriteCommentToPnrOn()
						&& SpringUtils.getReservationIntegration().isWriteExpensesToPnrOn()) {
					SpringUtils.getReservationIntegration().writeCommentToPNR(
							TracingConstants.CMT_DENIED_INTERIM + formateddatetime,
							ep.getIncident().getRecordlocator());
				}
			}
			return mapping.findForward(UPDATE_SUCCESS);
		} else {
			return mapping.findForward(ERROR);
		}
	}
}
