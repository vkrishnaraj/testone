package com.bagnet.nettracer.tracing.actions.expense;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Passenger;
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
			if (PropertyBMO.isTrue(PropertyBMO.PASSENGER_DATA_IN_EXPENSE)) {
				preparePassengerlist(incForm.getPassengerlist(), epform);
			}
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
	
	private void preparePassengerlist(List<Passenger> passengerlist, ExpensePayoutForm epform) {
        if (passengerlist != null && passengerlist.size() > 0) {
	        Passenger pa = passengerlist.get(0);
	        if (pa != null) {
	        	epform.setLastname(StringUtils.trimToEmpty(pa.getLastname()));
	        	epform.setFirstname(StringUtils.trimToEmpty(pa.getFirstname()));
	        	epform.setMiddlename(StringUtils.trimToEmpty(pa.getMiddlename()));
	        	if (pa.getAddresses() != null && pa.getAddresses().size() > 0) {
			       	Address ad = pa.getAddress(0);
			       	if (ad != null) {
			       		epform.setAddress1(StringUtils.trimToEmpty(ad.getAddress1()));
			       		epform.setAddress2(StringUtils.trimToEmpty(ad.getAddress2()));
			       		epform.setCity(StringUtils.trimToEmpty(ad.getCity()));
			       		epform.setState_ID(StringUtils.trimToEmpty(ad.getState_ID()));
			       		epform.setProvince(StringUtils.trimToEmpty(ad.getProvince()));
			       		epform.setHomephone(StringUtils.trimToEmpty(ad.getHomephone_norm()));
			       		epform.setWorkphone(StringUtils.trimToEmpty(ad.getWorkphone_norm()));
			       		epform.setMobile(StringUtils.trimToEmpty(ad.getMobile_norm()));
			       		epform.setZip(StringUtils.trimToEmpty(ad.getZip()));
			       		epform.setCountrycode_ID(StringUtils.trimToEmpty(ad.getCountrycode_ID()));
			       		epform.setEmail(StringUtils.trimToEmpty(ad.getEmail()));
			       	} // End address null check
	        	} // End addresses list check
	        } // End passenger null check
		} // End passengerlist list check
	}
}
