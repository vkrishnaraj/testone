package com.bagnet.nettracer.tracing.actions.expense;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.forms.ExpensePayoutForm;
import com.bagnet.nettracer.tracing.forms.IncidentForm;

public class CreateExpenseAction extends BaseExpenseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionForward fwd = super.execute(mapping, form, request, response);
		if(fwd != mapping.findForward(SUCCESS)) {
			return fwd;
		}
		
		ExpensePayoutForm epform = (ExpensePayoutForm) form;
		Agent user = (Agent)request.getSession().getAttribute("user");
		
		prepareForm(epform, user);
		IncidentForm incForm = (IncidentForm) request.getSession().getAttribute("incidentForm");
		if(incForm != null) {
			epform.setIncident_ID(incForm.getIncident_ID());
		}
		else {
			return mapping.findForward(ERROR_NO_INCIDENT);
		}
		
		return mapping.findForward(CREATE_SUCCESS);
	}
	
	private void prepareForm(ExpensePayoutForm epform, Agent user) {
		epform.setCreatedate(new Date());
		epform.setCreateStation(user.getStation().getStationcode());
		epform.setCreateUser(user.getUsername());
		epform.setCurrency_ID(user.getDefaultcurrency());
		epform.setExpenselocation_ID(user.getStation().getStation_ID());
		epform.setDateFormat(user.getDateformat().getFormat());
		epform.setTz(user.getCurrenttimezone());
		epform.setExpensetype_id(TracingConstants.EXPENSEPAYOUT_INTERIM);
	}
}
