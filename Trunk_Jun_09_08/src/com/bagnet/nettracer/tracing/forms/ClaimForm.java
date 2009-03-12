package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.ClaimProrate;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author Ankur Gupta
 * 
 * This class represents the claim form that is used for accessing claim
 * functionality
 */
public final class ClaimForm extends ActionForm {

	// incident id
	private String incident_ID;

	// incident
	private Incident incident;
	// claim payout
	private int claim_ID;
	private double claimamount;
	private String claimcurrency_ID;
	private Status status;
	private double total;

	// ssn stuff
	private String passengername;
	private String ssn;
	private String driverslicense;
	private String dlstate;
	private String commonnum;
	private String countryofissue;
	
	private List<ExpensePayout> expenselist = new ArrayList<ExpensePayout>();
	private int expensetype_ID;
	private int expenselocation_ID;
	private int expensestatus_ID;

	// audit table fields
	private String mod_claim_reason;
	private String mod_exp_reason;
	// claim prorate
	private ClaimProrate claimprorate;
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private java.util.TimeZone _TIMEZONE;

	public void setExpenselist(List<ExpensePayout> expenselist) {
		this.expenselist = expenselist;
	}

	public List<ExpensePayout> getExpenselist() {
		return expenselist;
	}

	/**
	 * @return Returns the claimprorate.
	 */
	public ClaimProrate getClaimprorate() {
		return claimprorate;
	}

	/**
	 * @param claimprorate
	 *          The claimprorate to set.
	 */
	public void setClaimprorate(ClaimProrate claimprorate) {
		this.claimprorate = claimprorate;
	}


	/**
	 * @return Returns the incident_ID.
	 */
	public String getIncident_ID() {
		return incident_ID;
	}

	/**
	 * @param incident_ID
	 *          The incident_ID to set.
	 */
	public void setIncident_ID(String incident_ID) {
		this.incident_ID = incident_ID;
	}

	/**
	 * @return Returns the claim_ID.
	 */
	public int getClaim_ID() {
		return claim_ID;
	}

	/**
	 * @param claim_ID
	 *          The claim_ID to set.
	 */
	public void setClaim_ID(int claim_ID) {
		this.claim_ID = claim_ID;
	}

	/**
	 * @return Returns the claimamount.
	 */
	public double getClaimamount() {
		return claimamount;
	}

	/**
	 * @return Returns the claimcurrency_ID.
	 */
	public String getClaimcurrency_ID() {
		return claimcurrency_ID;
	}

	/**
	 * @param claimcurrency_ID
	 *          The claimcurrency_ID to set.
	 */
	public void setClaimcurrency_ID(String claimcurrency_ID) {
		this.claimcurrency_ID = claimcurrency_ID;
	}

	/**
	 * @param claimamount
	 *          The claimamount to set.
	 */
	public void setClaimamount(double claimamount) {
		this.claimamount = claimamount;
	}

	public String getDisclaimamount() {
		return TracingConstants.DECIMALFORMAT.format(getClaimamount());
	}

	public void setDisclaimamount(String s) {
		setClaimamount(TracerUtils.convertToDouble(s));
	}

	/**
	 * @return Returns the passengername.
	 */
	public String getPassengername() {
		return passengername;
	}

	/**
	 * @param passengername
	 *          The passengername to set.
	 */
	public void setPassengername(String passengername) {
		this.passengername = passengername;
	}

	/**
	 * @return Returns the ssn.
	 */
	public String getSsn() {
		return ssn;
	}

	/**
	 * @param ssn
	 *          The ssn to set.
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	
	/**
	 * @return Returns the dlstate.
	 */
	public String getDlstate() {
		return dlstate;
	}
	/**
	 * @param dlstate The dlstate to set.
	 */
	public void setDlstate(String dlstate) {
		this.dlstate = dlstate;
	}

	public String getDispdlstate() {
		if (dlstate != null && dlstate.length() > 0) {
			return TracerUtils.getState(dlstate, "en").getState();
		}
		return "";
	}
	/**
	 * @return Returns the driverslicense.
	 */
	public String getDriverslicense() {
		return driverslicense;
	}
	/**
	 * @param driverslicense The driverslicense to set.
	 */
	public void setDriverslicense(String driverslicense) {
		this.driverslicense = driverslicense;
	}

	/**
	 * @return Returns the commonnum.
	 * 
	 */
	public String getCommonnum() {
		return commonnum;
	}

	/**
	 * @param commonnum
	 *          The commonnum to set.
	 */
	public void setCommonnum(String commonnum) {
		this.commonnum = commonnum;
	}

	/**
	 * @return Returns the countryofissue.
	 * 
	 */
	public String getCountryofissue() {
		return countryofissue;
	}

	/**
	 * @param countryofissue
	 *          The countryofissue to set.
	 */
	public void setCountryofissue(String countryofissue) {
		this.countryofissue = countryofissue;
	}
	
	
	/**
	 * @return Returns the status.
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *          The status to set.
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return Returns the status_ID.
	 */
	public int getStatus_ID() {
		return getStatus().getStatus_ID();
	}

	/**
	 * @param status_ID
	 *          The status_ID to set.
	 */
	public void setStatus_ID(int status_ID) {
		getStatus().setStatus_ID(status_ID);
	}

	/**
	 * @return Returns the total.
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * @param total
	 *          The total to set.
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	public String getDistotal() {
		return TracingConstants.DECIMALFORMAT.format(getTotal());
	}

	public void setDistotal(String s) {
		setTotal(TracerUtils.convertToDouble(s));
	}

	/**
	 * @return Returns the incident.
	 */
	public Incident getIncident() {
		return incident;
	}

	/**
	 * @param incident
	 *          The incident to set.
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	/**
	 * @return Returns the mod_claim_reason.
	 */
	public String getMod_claim_reason() {
		return mod_claim_reason;
	}

	/**
	 * @param mod_claim_reason
	 *          The mod_claim_reason to set.
	 */
	public void setMod_claim_reason(String mod_claim_reason) {
		this.mod_claim_reason = mod_claim_reason;
	}

	/**
	 * @return Returns the mod_exp_reason.
	 */
	public String getMod_exp_reason() {
		return mod_exp_reason;
	}

	/**
	 * @param mod_exp_reason
	 *          The mod_exp_reason to set.
	 */
	public void setMod_exp_reason(String mod_exp_reason) {
		this.mod_exp_reason = mod_exp_reason;
	}

	/**
	 * @return Returns the _DATEFORMAT.
	 */
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	/**
	 * @param _dateformat
	 *          The _DATEFORMAT to set.
	 */
	public void set_DATEFORMAT(String _dateformat) {
		_DATEFORMAT = _dateformat;
	}

	/**
	 * @return Returns the _TIMEFORMAT.
	 */
	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}

	/**
	 * @param _timeformat
	 *          The _TIMEFORMAT to set.
	 */
	public void set_TIMEFORMAT(String _timeformat) {
		_TIMEFORMAT = _timeformat;
	}

	/**
	 * @return Returns the _TIMEZONE.
	 */
	public java.util.TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}

	/**
	 * @param _timezone
	 *          The _TIMEZONE to set.
	 */
	public void set_TIMEZONE(java.util.TimeZone _timezone) {
		_TIMEZONE = _timezone;
	}
}