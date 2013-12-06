package com.bagnet.nettracer.tracing.actions.expense;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.UserGroup;
import com.bagnet.nettracer.tracing.forms.ExpensePayoutForm;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.SpringUtils;

public class EditExpenseAction extends BaseExpenseAction {
	private Logger logger = Logger.getLogger(EditExpenseAction.class);
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
		//payment type is LUV Voucher
		if (epform.getPaymentType().equals(TracingConstants.ENUM_VOUCHER) && epform.getCancelcount() == 0 && epform.getCancelreason() != null && epform.getCancelreason().length() > 0 ) {
			IncidentBMO ibmo = new IncidentBMO();
			String incidentId = ((IncidentForm) request.getSession().getAttribute("incidentForm")).getIncident_ID();
			Status st = new Status();			
			//WS submit
			com.bagnet.nettracer.tracing.db.Incident inc = (com.bagnet.nettracer.tracing.db.Incident)ibmo.findIncidentByID(incidentId);
			ArrayList<String> ret= null;
			try{
				ret = SpringUtils.getReservationIntegration().submitVoucher(inc, "cancel",epform);
			}catch(Exception e){
				logger.error("Failed to submit Voucher!!! " +  e);
			}
			boolean ws_submit_ok = (ret != null && ret.get(0).equals("true")) ? true : false ;
			if (ws_submit_ok) {
//				request.getSession().setAttribute("ordernum", ret.get(1));
//				ep.setOrdernum(ret.get(1));
//				request.getSession().setAttribute("slvnum", ret.get(2));
//				request.getSession().setAttribute("seccode", ret.get(3));
//				request.getSession().setAttribute("wssubmit", "yes");
				epform.setCancelcount(1);
				String contents= "The Southwest LUV Voucher has been cancelled. Order Number: " + ret.get(1);
				ibmo.insertRemark(contents,incidentId, user, TracingConstants.REMARK_REGULAR);
				
//				st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_PAID);
			} else {
				epform.setWssubmit("no");
				epform.setErrormsg((ret != null) ? ret.get(4) : "NO CONNECTION!!");
				epform.setCancelreason("");
				epform.setCancelcount(0);
//				epform.setIncident_ID(incidentId);
//				epform.setCreateStation(ep.getStation().getStationcode());
//				epform.setCreateUser(ep.getAgent().getUsername());
//				epform.setCreatedate(ep.getCreatedate());
//				epform.setPaymentType(ep.getPaytype());
//				request.getSession().setAttribute("expensepayoutform", epform);	
//				return mapping.findForward(UPDATE_SUCCESS);
			}				
		}
		request.getSession().setAttribute("expensepayoutform", epform);	
		return mapping.findForward(EDIT_SUCCESS);
	}

	
}
