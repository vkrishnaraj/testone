package com.bagnet.nettracer.tracing.actions.expense;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.UsergroupBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.UserGroup;
import com.bagnet.nettracer.tracing.forms.ExpensePayoutForm;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class SaveExpenseAction extends BaseExpenseAction {
	private Logger logger = Logger.getLogger(SaveExpenseAction.class);
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
		String incidentId = ((IncidentForm) request.getSession().getAttribute("incidentForm")).getIncident_ID();
		
		boolean cbsProcess=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BSO_PROCESS, user) && !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BSO_ADMIN, user);
		boolean luvProcess=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_IMMEDIATE_FULFILLMENT, user)
				          || UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EMAIL_FULFILLMENT, user)
				          || UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MAIL_FULFILLMENT, user); 
		// set status to pending or approved
		Status st = new Status();
		st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED);
		Company_Specific_Variable csv = AdminUtils.getCompVariable(user.getCompanycode_ID());
		if (Math.abs(ep.getCheckamt()) > 0.001) {
			/**
			 * If the user is part of a usergroup that has a BSO Limit and
			 * is subject to the BSO Expense Process, then verify that the
			 * check amount entered is not greater than the BSO Limit. If it
			 * is then do not save the expense payout
			 */
			UserGroup group=UsergroupBMO.getUsergroup(user.getUsergroup_id());
			double bsoLimit=0;
			if(group!=null && group.getBsoLimit()>0){
				bsoLimit=group.getBsoLimit();
				request.setAttribute("bsoLimit", bsoLimit);
			}
			
			if(bsoLimit>0 && ep.getCheckamt()>bsoLimit && cbsProcess){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("unable.create.over.bso.limit", new Object[]{bsoLimit}));
				saveMessages(request, messages);
				return mapping.findForward(CREATE_SUCCESS);
			}
			
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
			/**
			 * If the user is part of a usergroup that has a LUV Limit and
			 * is subject to the LUV Permission, then verify that the
			 * check amount entered is not greater than the LUV Limit. If it
			 * is then do not save the expense payout
			 */
			UserGroup group=UsergroupBMO.getUsergroup(user.getUsergroup_id());
			double luvLimit=0;
			if(group!=null && group.getLuvLimit()>0){
				luvLimit=group.getLuvLimit();
				request.setAttribute("luvLimit", luvLimit);
			}
			
			if(luvLimit>0 && ep.getVoucheramt()>luvLimit && luvProcess){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("unable.create.over.luv.limit", new Object[]{luvLimit}));
				saveMessages(request, messages);
				return mapping.findForward(CREATE_SUCCESS);
			}			
			if ((csv.getMin_interim_approval_voucher() >= -0.001
					&& (csv.getMin_interim_approval_voucher() - Math.abs(ep.getVoucheramt())) < -0.001) 
					&& (luvLimit==0 || !luvProcess)) {					
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
		//payment type is LUV Voucher
		if (TracingConstants.ENUM_VOUCHER.equals(expenseForm.getPaymentType())) {
			//WS submit
			ArrayList<String> ret= null;
			try {
				Incident inc = (Incident)ibmo.findIncidentByID(incidentId);
				ret = SpringUtils.getReservationIntegration().submitVoucher(inc, "open",expenseForm);
			} catch(Exception e) {
				logger.error("Failed to submit Voucher!!! " +  e);
			}
			
			
			if (ret != null && 3 < ret.size() && "true".equalsIgnoreCase(ret.get(0))) {
				request.getSession().setAttribute("wssubmitp", "yes");
				ep.setOrdernum(ret.get(1));
				ep.setSlvnum(ret.get(2));
				ep.setSeccode(ret.get(3));
				expenseForm.setWssubmitp("yes");
				request.getSession().setAttribute("showsubmit", "1");
				String contents= String.format("Voucher Issue Amount: $%s\nAgent Comments: %s", String.valueOf(expenseForm.getCheckamt()), expenseForm.getNewComment());
				ibmo.insertRemark(contents, incidentId, user, TracingConstants.REMARK_REGULAR, true);
				st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_PAID);
			} else {
				request.getSession().setAttribute("wssubmitp", "no");
				expenseForm.setWssubmitp("no");
				request.getSession().setAttribute("showsubmit", "0");
				expenseForm.setIncident_ID(incidentId);
				expenseForm.setCreateStation(ep.getStation().getStationcode());
				expenseForm.setCreateUser(ep.getAgent().getUsername());
				expenseForm.setCreatedate(ep.getCreatedate());
				expenseForm.setPaymentType(ep.getPaytype());
				expenseForm.setErrormsg((ret != null && 3 < ret.size()) ? ret.get(4) : "NO CONNECTION!!");

				return mapping.findForward(CREATE_SUCCESS);
			}				
		}
		
		//normalize phone number 
		ep.setHomephone(TracerUtils.normalizePhoneNumber(ep.getHomephone()));
		ep.setWorkphone(TracerUtils.normalizePhoneNumber(ep.getWorkphone()));
		ep.setMobile(TracerUtils.normalizePhoneNumber(ep.getMobile()));
		
		ep.setStatus(st);
		ibmo.saveExpense(ep, incidentId, user);
		request.getSession().setAttribute("getclaimfa", "1");
		request.getSession().setAttribute("incidentid", incidentId);
		request.getSession().setAttribute("expense_id", ep.getExpensepayout_ID());
		response.sendRedirect("EditExpense.do");
		return null;
		
	}

}
