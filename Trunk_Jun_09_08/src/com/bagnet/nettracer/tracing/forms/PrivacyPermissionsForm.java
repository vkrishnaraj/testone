package com.bagnet.nettracer.tracing.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import aero.nettracer.serviceprovider.common.db.PrivacyPermissions;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissionsKey;


public class PrivacyPermissionsForm extends ValidatorForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3676047698635316893L;
	String[] componentList = {"autosend","showallclaiminfo","name","address","phonenumber","email","traveldate","claimtype",
			"claimdate","amountclaimed","amountpaid","fraudstatus","denied","denialreason",
			"cc","drivers","ffn","ssn","passport","pnrloc","pnrdata","dob","itin","ticketamount",
			"bag","bdo","incdate","incremarks"};
	

	public String[] getComponentList() {
		return componentList;
	}
	public PrivacyPermissions getDef() {
		return def;
	}
	public void setDef(PrivacyPermissions def) {
		this.def = def;
	}
	public PrivacyPermissions getReq() {
		return req;
	}
	public void setReq(PrivacyPermissions req) {
		this.req = req;
	}

	public void reset(ActionMapping mapping, 
			HttpServletRequest request) {

		//Initialize the property 
		PrivacyPermissionsKey defKey = null;
		PrivacyPermissionsKey reqKey = null;
		if(this.def != null) defKey = this.def.getKey();
		if(this.req != null) reqKey = this.req.getKey();
		def = new PrivacyPermissions();
		req = new PrivacyPermissions();
		def.setKey(defKey);
		req.setKey(reqKey);
	}

	PrivacyPermissions def;
	PrivacyPermissions req;
	int retention;


	public int getRetention() {
		return retention;
	}
	public void setRetention(int retention) {
		this.retention = retention;
	}
}
