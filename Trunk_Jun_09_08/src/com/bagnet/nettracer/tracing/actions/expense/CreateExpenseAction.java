package com.bagnet.nettracer.tracing.actions.expense;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.PassengerExp;
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
			epform.setPassengerlist(preparePassengerlist(incForm.getPassengerlist()));
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
	
	private List<PassengerExp> preparePassengerlist(List<Passenger> passengerlist) {
        List<PassengerExp> passengerexp= new ArrayList<PassengerExp>();
		if (passengerlist != null && passengerlist.size() > 0) {
//			for (int i=0;i<passengerlist.size();i++) { 
	        	Passenger pa = passengerlist.get(0);
	        	PassengerExp paexp = new PassengerExp();
        		paexp.setLastname((pa.getLastname() != null) ? pa.getLastname() : "");
        		paexp.setFirstname((pa.getFirstname() != null) ? pa.getFirstname() : "");
        		paexp.setMiddlename((pa.getMiddlename() != null) ? pa.getMiddlename() : "");
        		paexp.setAddress1((pa.getAddress(0).getAddress1() != null) ? pa.getAddress(0).getAddress1() : "");
        		paexp.setAddress2((pa.getAddress(0).getAddress2() != null) ? pa.getAddress(0).getAddress2() : "");
        		paexp.setCity((pa.getAddress(0).getCity() != null) ? pa.getAddress(0).getCity() : "");
        		paexp.setState_ID((pa.getAddress(0).getState_ID() != null) ? pa.getAddress(0).getState_ID() : "");
        		paexp.setProvince((pa.getAddress(0).getProvince()!= null) ? pa.getAddress(0).getProvince() : "");
        		paexp.setHomephone((pa.getAddress(0).getHomephone_norm()!= null) ? pa.getAddress(0).getHomephone_norm() : "");
        		paexp.setWorkphone((pa.getAddress(0).getWorkphone_norm()!= null) ? pa.getAddress(0).getWorkphone_norm() : "");
        		paexp.setMobile((pa.getAddress(0).getMobile_norm()!= null) ? pa.getAddress(0).getMobile_norm() : "");
        		paexp.setZip((pa.getAddress(0).getZip()!= null) ? pa.getAddress(0).getZip() : "");
        		paexp.setCountrycode_ID((pa.getAddress(0).getCountrycode_ID()!= null) ? pa.getAddress(0).getCountrycode_ID() : "");
        		paexp.setEmail((pa.getAddress(0).getEmail()!= null) ? pa.getAddress(0).getEmail() : "");
        		passengerexp.add(0, paexp);
//	        }
		}
		return passengerexp;
	}
}
