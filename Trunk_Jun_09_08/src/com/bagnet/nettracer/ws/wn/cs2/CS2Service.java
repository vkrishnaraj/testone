package com.bagnet.nettracer.ws.wn.cs2;

import com.bagnet.nettracer.ws.wn.cs2.pojo.IssueLuvVoucherResponse;
import com.bagnet.nettracer.ws.wn.cs2.pojo.RetrieveIncidentResponse;
import com.bagnet.nettracer.ws.wn.pojo.Authentication;

public class CS2Service {

	/*
	 * Issue Luv Voucher to Incident
	 */
	public IssueLuvVoucherResponse issueLuvVoucher(Authentication authentication, String reportNumber, float amount, String firstName, String lastName, String agentLocation) {
		return null;
	}
	
	/*
	 * Retrieve Incident Information
	 */
	public RetrieveIncidentResponse retrieveIncident(Authentication authentication, String reportNumber) {
		return null;
	}
}