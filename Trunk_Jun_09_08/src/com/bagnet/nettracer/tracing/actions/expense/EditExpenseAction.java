package com.bagnet.nettracer.tracing.actions.expense;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.bmo.ExpensePayoutBMO;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.UsergroupBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.UserGroup;
import com.bagnet.nettracer.tracing.forms.ExpensePayoutForm;
import com.bagnet.nettracer.tracing.forms.IncidentForm;

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
		
		if(PropertyBMO.getValue(PropertyBMO.BSO_EXPENSE_PROCESS)!=null){
			UserGroup group=UsergroupBMO.getUsergroup(user.getUsergroup_id());
			if(group!=null && group.getBsoLimit()>0){
				request.setAttribute("bsoLimit", group.getBsoLimit());
			}
		}
		populateForm(epform, ep, user, request);
		//Added to remark for cancelled status
		if (epform.getStatus_id() == TracingConstants.EXPENSEPAYOUT_STATUS_CANCEL) {
			IncidentBMO ibmo = new IncidentBMO();
			String incidentId = ((IncidentForm) request.getSession().getAttribute("incidentForm")).getIncident_ID();
			String contents= "The Southwest LUV Voucher has been cancelled. Order Number: " + ep.getOrdernum();
			ibmo.insertRemark(contents,incidentId, user, TracingConstants.REMARK_REGULAR, true);			
		}

		if (epform.getToremark() == null && "yes".equals((String) request.getSession().getAttribute("printToremark"))) {
			epform.setToremark("yes");
			request.getSession().setAttribute("printToremark", null);	
		}
		
		request.getSession().setAttribute("expensepayoutform", epform);	
		return mapping.findForward(EDIT_SUCCESS);
	}

	
}
