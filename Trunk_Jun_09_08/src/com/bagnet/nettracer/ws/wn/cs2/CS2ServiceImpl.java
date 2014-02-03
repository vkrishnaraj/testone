/**
 * CS2ServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */
package com.bagnet.nettracer.ws.wn.cs2;

import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Comment;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.ExpenseType;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.ws.wn.cs2.IssueLuvVoucherDocument.IssueLuvVoucher;
import com.bagnet.nettracer.ws.wn.cs2.IssueLuvVoucherResponseDocument.IssueLuvVoucherResponse;
import com.bagnet.nettracer.ws.wn.cs2.RetrieveIncidentDocument.RetrieveIncident;
import com.bagnet.nettracer.ws.wn.cs2.RetrieveIncidentResponseDocument.RetrieveIncidentResponse;
import com.bagnet.nettracer.ws.wn.pojo.xsd.Address;
import com.bagnet.nettracer.ws.wn.pojo.xsd.Authentication;

/**
 * CS2ServiceImpl java implementation for the axisService
 */
public class CS2ServiceImpl extends CS2ServiceSkeleton {

	private static final String FAILED = "Failed";
	private static final String SUCCESS = "Success";
	private static final String AUTH_FAILED = "Authentication Failed";
	private static final String NOT_FOUND = "Incident Number not found";
	private static final String DEFAULT_CODE = "OTH";
	private static final String DEFAULT_CURR = "USD";
	private static final int DEFAULT_STATUS = TracingConstants.EXPENSEPAYOUT_STATUS_PENDING;
	private static final int DEFAULT_TYPE = 8;

	/**
	 * Auto generated method signature
	 * 
	 * @param issueLuvVoucher
	 */
	public com.bagnet.nettracer.ws.wn.cs2.IssueLuvVoucherResponseDocument issueLuvVoucher(
			com.bagnet.nettracer.ws.wn.cs2.IssueLuvVoucherDocument issueLuvVoucher) {
		
		// PREPARE RETURN
		IssueLuvVoucherResponseDocument res = IssueLuvVoucherResponseDocument.Factory.newInstance();
		IssueLuvVoucherResponse res2 = res.addNewIssueLuvVoucherResponse();
		com.bagnet.nettracer.ws.wn.cs2.pojo.xsd.IssueLuvVoucherResponse res3 = res2.addNewReturn();
		
		// AUTHENTICATE
		IssueLuvVoucher doc = issueLuvVoucher.getIssueLuvVoucher();
		Agent agent = null;
		Authentication auth = doc.getAuthentication();
		ActionMessages errors = new ActionMessages();
		agent = SecurityUtils.authUser(auth.getSystemName(), auth.getSystemPassword(), auth.getAirlineCode(), 1, errors);
		if(!errors.isEmpty()){
			res3.setReturnCode(1);
			res3.setMessage(AUTH_FAILED);
			return res;
		}
		
		// ADD VOUCHER
		if (issueLuvVoucher(doc, auth.getAirlineCode(), agent)) {
			res3.setReturnCode(0);
			res3.setMessage(SUCCESS);
		} else {
			res3.setReturnCode(1);
			res3.setMessage(FAILED);
		}
		return res;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param retrieveIncident
	 */
	public com.bagnet.nettracer.ws.wn.cs2.RetrieveIncidentResponseDocument retrieveIncident(
			com.bagnet.nettracer.ws.wn.cs2.RetrieveIncidentDocument retrieveIncident) {
		
		// PREPARE RETURN
		RetrieveIncidentResponseDocument res = RetrieveIncidentResponseDocument.Factory.newInstance();
		RetrieveIncidentResponse res2 = res.addNewRetrieveIncidentResponse();
		com.bagnet.nettracer.ws.wn.cs2.pojo.xsd.RetrieveIncidentResponse res3 = res2.addNewReturn();
		
		// AUTHENTICATE
		RetrieveIncident doc = retrieveIncident.getRetrieveIncident();
		Authentication auth = doc.getAuthentication();
		ActionMessages errors = new ActionMessages();
		SecurityUtils.authUser(auth.getSystemName(), auth.getSystemPassword(), auth.getAirlineCode(), 1, errors);
		if(!errors.isEmpty()){
			res3.setReturnCode(1);
			res3.setMessage(AUTH_FAILED);
			return res;
		}
		
		// RETRIEVE INCIDENT
		if (retrieveIncident(doc, res3)) {
			res3.setReturnCode(0);
			res3.setMessage(SUCCESS);
		} else {
			res3.setReturnCode(1);
			res3.setMessage(NOT_FOUND);
		}
		return res;
	}

	/**
	 * This method issues a voucher to a given incident.
	 * Currently firstName and lastName are unused.
	 * Also payment_code=Cl, type_code=B, and disbursement_status_code=ND are unused
	 * 
	 * @param doc, companyCode, agent
	 */
	private boolean issueLuvVoucher(IssueLuvVoucher doc, String companyCode, Agent agent) {
		if (doc.getReportNumber() == null) {
			return false;
		}
		IncidentBMO bmo = new IncidentBMO();
		Incident inc = bmo.findIncidentByID(doc.getReportNumber());
		if (inc == null) {
			return false;
		}
		
		Station station = StationBMO.getStationByCode(doc.getAgentLocation(), companyCode);
		if (station == null) {
			return false;
		}
		
		ExpensePayout payout = new ExpensePayout();
		payout.setAgent(agent);
		payout.setCreatedate(new Date());
		payout.setStation(station);
		payout.setExpenselocation(station);
		payout.setVoucheramt(doc.getAmount());
		String curr = PropertyBMO.getValue(PropertyBMO.CS2_EXPENSE_CURRENCY);
		curr = (curr == null ? DEFAULT_CURR : curr);
		payout.setCurrency(Currency.getInstance(curr));
		Comment comment = new Comment();
		comment.setAgent(agent);
		comment.setCreateDate(new Date());
		String name = (doc.getFirstName() != null ? doc.getFirstName() + " " : "");
		name += (doc.getLastName() != null ? doc.getLastName() : "");
		comment.setContent("Auto generated voucher for " + name);
		Set<Comment> comments = new LinkedHashSet<Comment>();
		comments.add(comment);
		payout.setComments(comments);
		String code = PropertyBMO.getValue(PropertyBMO.CS2_EXPENSE_PAYCODE);
		code = (code == null ? DEFAULT_CODE : code);
		int stat = PropertyBMO.getValueAsInt(PropertyBMO.CS2_EXPENSE_STATUS);
		stat = (stat == 0 ? DEFAULT_STATUS : stat);
		int typeNum = PropertyBMO.getValueAsInt(PropertyBMO.CS2_EXPENSE_PAYTYPE);
		typeNum = (typeNum == 0 ? DEFAULT_TYPE : typeNum);
		payout.setPaycode(code);
		payout.setStatus(StatusBMO.getStatus(stat));
		ExpenseType type = new ExpenseType();
		type.setExpensetype_ID(typeNum);
		payout.setExpensetype(type);

		ActionMessage error = bmo.saveExpense(payout, inc.getIncident_ID(), agent);
		if (error != null) {
			return false;
		}
		
		Remark remark = new Remark();
		remark.setAgent(agent);
		remark.setIncident(inc);
		remark.setRemarktext("LUV VOUCHER ISSUED FOR $" + doc.getAmount() + " TO " + name);
		remark.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
		remark.setRemarktype(TracingConstants.REMARK_REGULAR);
		Set<Remark> remarks = inc.getRemarks();
		remarks.add(remark);
		
		int check = bmo.updateRemarksOnly(inc.getIncident_ID(), remarks, agent, true);
		if (check < 1) {
			return false;
		}
		
		return true;
	}

	/**
	 * This method inserts some incident info into the response if the incident info is available.
	 * 
	 * @param doc, res3
	 */
	private boolean retrieveIncident(RetrieveIncident doc, com.bagnet.nettracer.ws.wn.cs2.pojo.xsd.RetrieveIncidentResponse res3) {
		boolean toReturn = false;
		if (doc.getReportNumber() == null) {
			return toReturn;
		}
		IncidentBMO bmo = new IncidentBMO();
		Incident inc = bmo.findIncidentByID(doc.getReportNumber());
		if (inc == null) {
			return toReturn;
		}
		toReturn = true;
		if (inc.getPassenger_list() != null && inc.getPassenger_list().size() > 0) {
			if (inc.getPassenger_list().get(0) != null && inc.getPassenger_list().get(0).getAddresses() != null && inc.getPassenger_list().get(0).getAddresses().size() > 0) {
				com.bagnet.nettracer.tracing.db.Address incAddr = inc.getPassenger_list().get(0).getAddress(0);
				res3.setPhone(incAddr.getMobile());
				res3.setEmail(incAddr.getEmail());
				Address addr = res3.addNewAddress();
				addr.setAddress1(incAddr.getAddress1());
				addr.setAddress2(incAddr.getAddress2());
				addr.setCity(incAddr.getCity());
				addr.setState(incAddr.getState());
				addr.setProvince(incAddr.getProvince());
				addr.setZip(incAddr.getZip());
				addr.setCountry(incAddr.getCountry());
			}
			for (Passenger pass : inc.getPassenger_list()) {
				if (pass != null) {
					String name = pass.getLastname() + ", " + pass.getFirstname();
					if (pass.getMiddlename() != null && pass.getMiddlename().length() > 0) {
						name += " " + pass.getMiddlename();
					}
					res3.addNames(name);
				}
			}
		}
		return toReturn;
	}

}
