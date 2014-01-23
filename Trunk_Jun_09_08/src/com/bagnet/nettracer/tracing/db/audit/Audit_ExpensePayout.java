/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.ExpenseType;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table = "Audit_ExpensePayout"
 */
public class Audit_ExpensePayout implements Serializable {
	private int audit_expensepayout_id;
	private int Expensepayout_ID;
	private Date createdate;
	private String paycode;
	private String draft;
	private Date draftreqdate;
	private Date draftpaiddate;
	private double checkamt;
	private double voucheramt;
	private int mileageamt;
	private String currency_ID;
	private Station expenselocation;
	private ExpenseType expensetype;
	private String auditComments;
	private Status status;
	private double incidentalAmountAuth;
	private double incidentalAmountClaimed;
	private double creditCardRefund;
	private Date voucherExpirationDate;
	
	
	private Date approval_date;


	private Agent agent;
	private Station station;

	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;
	
	private Audit_Claim audit_claim;
	
	private String modify_reason;
	private Date modify_time;
	
	/**
	 * 
	 * @hibernate.property type="string"
	 */
	public String getModify_reason() {
		return modify_reason;
	}
	public void setModify_reason(String modify_reason) {
		this.modify_reason = modify_reason;
	}
	
	/**
	 * @return Returns the modify_time.
	 * 
	 * @hibernate.property type="timestamp"
	 */
	public Date getModify_time() {
		return modify_time;
	}

	/**
	 * @param modify_time
	 *          The modify_time to set.
	 */
	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}
	
	/**
	 * @return Returns the audit_claim.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.audit.Audit_Claim"
	 *                        column="audit_claim_id"
	 */
	public Audit_Claim getAudit_claim() {
		return audit_claim;
	}
	/**
	 * @param audit_claim The audit_claim to set.
	 */
	public void setAudit_claim(Audit_Claim audit_claim) {
		this.audit_claim = audit_claim;
	}

	/**
	 * @return Returns the expensepayout_ID.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getExpensepayout_ID() {
		return Expensepayout_ID;
	}

	/**
	 * @param expensepayout_ID
	 *          The expensepayout_ID to set.
	 */
	public void setExpensepayout_ID(int expensepayout_ID) {
		Expensepayout_ID = expensepayout_ID;
	}

	/**
	 * @return Returns the expenselocation.
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Station"
	 *                        column="expenselocation_ID"
	 */
	public Station getExpenselocation() {
		return expenselocation;
	}

	public void setExpenselocation(Station expenselocation) {
		this.expenselocation = expenselocation;
	}

	/**
	 * @return Returns the expensetype.
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="agent_ID"
	 */
	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	/**
	 * @return Returns the expensetype.
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Station"
	 *                        column="station_ID"
	 */
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	/**
	 * @return Returns the expensetype.
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.ExpenseType"
	 *                        column="expensetype_ID"
	 */
	public ExpenseType getExpensetype() {
		return expensetype;
	}

	/**
	 * @param expensetype
	 *          The expensetype to set.
	 */
	public void setExpensetype(ExpenseType expensetype) {
		this.expensetype = expensetype;
	}

	/**
	 * @return Returns the currency_ID.
	 * 
	 * @hibernate.property type="string" length="3"
	 */
	public String getCurrency_ID() {
		return currency_ID;
	}

	/**
	 * @param currency_ID
	 *          The currency_ID to set.
	 */
	public void setCurrency_ID(String currency_ID) {
		this.currency_ID = currency_ID;
	}

	/**
	 * @return Returns the createdate.
	 * 
	 * @hibernate.property type="date"
	 */
	public Date getCreatedate() {
		return createdate;
	}

	public String getDiscreatedate() {
		return DateUtils.formatDate(getCreatedate(), get_DATEFORMAT(), null, get_TIMEZONE());
	}

	/**
	 * @param createdate
	 *          The createdate to set.
	 *  
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	/**
	 * @return Returns the draft.
	 * 
	 * @hibernate.property type="string" length="30"
	 */
	public String getDraft() {
		return draft;
	}

	/**
	 * @param draft
	 *          The draft to set.
	 */
	public void setDraft(String draft) {
		this.draft = draft;
	}

	/**
	 * @return Returns the draftpaiddate.
	 * 
	 * @hibernate.property type="date"
	 */
	public Date getDraftpaiddate() {
		return draftpaiddate;
	}

	/**
	 * @param draftpaiddate
	 *          The draftpaiddate to set.
	 */
	public void setDraftpaiddate(Date draftpaiddate) {
		this.draftpaiddate = draftpaiddate;
	}

	public String getDisdraftpaiddate() {
		return DateUtils.formatDate(getDraftpaiddate(), get_DATEFORMAT(), null, null);
	}

	/**
	 * @return Returns the draftreqdate.
	 * 
	 * @hibernate.property type="date"
	 */
	public Date getDraftreqdate() {
		return draftreqdate;
	}

	/**
	 * @param draftreqdate
	 *          The draftreqdate to set.
	 */
	public void setDraftreqdate(Date draftreqdate) {
		this.draftreqdate = draftreqdate;
	}

	public String getDisdraftreqdate() {
		return DateUtils.formatDate(getDraftreqdate(), get_DATEFORMAT(), null, null);
	}

	/**
	 * @return Returns the audit_Expensepayout_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 * 
	 * @hibernate.generator-param name="sequence" value="Audit_expensepayout_0"
	 * 
	 *  
	 */
	public int getAudit_expensepayout_id() {
		return audit_expensepayout_id;
	}

	/**
	 * @param audit_Expensepayout_ID
	 *          The audit_Expensepayout_ID to set.
	 */
	public void setAudit_expensepayout_id(int audit_expensepayout_id) {
		this.audit_expensepayout_id = audit_expensepayout_id;
	}

	/**
	 * @return Returns the paycode.
	 * 
	 * @hibernate.property type="string" length="5"
	 */
	public String getPaycode() {
		return paycode;
	}

	/**
	 * @param paycode
	 *          The paycode to set.
	 */
	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}

	/**
	 * @return Returns the checkamt.
	 * 
	 * @hibernate.property type="double"
	 */
	public double getCheckamt() {
		return checkamt;
	}

	/**
	 * @param checkamt
	 *          The checkamt to set.
	 */
	public void setCheckamt(double checkamt) {
		this.checkamt = checkamt;
	}

	public String getDischeckamt() {
		return TracingConstants.DECIMALFORMAT.format(getCheckamt());
	}

	/**
	 * @return Returns the mileageamt.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getMileageamt() {
		return mileageamt;
	}

	/**
	 * @param mileageamt
	 *          The mileageamt to set.
	 */
	public void setMileageamt(int mileageamt) {
		this.mileageamt = mileageamt;
	}

	/**
	 * @return Returns the voucheramt.
	 * 
	 * @hibernate.property type="double"
	 */
	public double getVoucheramt() {
		return voucheramt;
	}

	/**
	 * @param voucheramt
	 *          The voucheramt to set.
	 */
	public void setVoucheramt(double voucheramt) {
		this.voucheramt = voucheramt;
	}

	public String getDisvoucheramt() {
		return TracingConstants.DECIMALFORMAT.format(getVoucheramt());
	}

	/**
	 * @return Returns the comments.
	 * 
	 * @hibernate.property type="string" column="comments"
	 */
	public String getAuditComments() {
		return auditComments;
	}

	/**
	 * @return Returns the status.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Status"
	 *                        column="status_ID"
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
	 * @param comments
	 *          The comments to set.
	 */
	public void setAuditComments(String comments) {
		this.auditComments = comments;
	}

	/**
	 * @return Returns the approval_date.
	 * 
	 * @hibernate.property type="timestamp" column="approval_date"
	 */
	public Date getApproval_date() {
		return approval_date;
	}
	/**
	 * @param approval_date The approval_date to set.
	 */
	public void setApproval_date(Date approval_date) {
		this.approval_date = approval_date;
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
	public TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}

	/**
	 * @param _timezone
	 *          The _TIMEZONE to set.
	 */
	public void set_TIMEZONE(TimeZone _timezone) {
		_TIMEZONE = _timezone;
	}
	
	/**
	 * @return Returns the status.
	 * 
	 * @hibernate.property type="double" column="incidental_amount_auth"
	 */
	public double getIncidentalAmountAuth() {
		return incidentalAmountAuth;
	}

	public void setIncidentalAmountAuth(double incidentalAmount) {
		this.incidentalAmountAuth = incidentalAmount;
	}

	/**
	 * @return Returns the status.
	 * 
	 * @hibernate.property type="date" column="voucher_exp"
	 */
	public Date getVoucherExpirationDate() {
		return voucherExpirationDate;
	}

	public void setVoucherExpirationDate(Date voucherExpirationDate) {
		this.voucherExpirationDate = voucherExpirationDate;
	}
	
	/**
	 * @return Returns the status.
	 * 
	 * @hibernate.property type="double" column="incidental_amount_claim"
	 */
	public double getIncidentalAmountClaimed() {
		return incidentalAmountClaimed;
	}
	
	public void setIncidentalAmountClaimed(double incidentalAmountClaimed) {
		this.incidentalAmountClaimed = incidentalAmountClaimed;
	}
	
	/**
	 * @return Returns the status.
	 * 
	 * @hibernate.property type="double" column="creditcard_refund"
	 */
	public double getCreditCardRefund() {
		return creditCardRefund;
	}
	
	public void setCreditCardRefund(double creditCardRefund) {
		this.creditCardRefund = creditCardRefund;
	}
	
	
}