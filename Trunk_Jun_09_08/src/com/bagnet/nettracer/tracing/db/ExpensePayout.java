/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;
import java.util.ListIterator;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.NumberUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.cci.utils.parser.ElementNode;

/**
 * @author Administrator
 * 
 * @hibernate.class table = "ExpensePayout"
 */
public class ExpensePayout implements Serializable {
	private Claim claim;
	private int Expensepayout_ID;
	private Date createdate;
	private String paycode;
	private String draft;
	private Date draftreqdate;
	private Date draftpaiddate;
	private double checkamt;
	private double voucheramt;
	private int mileageamt;
	private String comments;
	private ExpenseType expensetype;
	private Station expenselocation;
	private String currency_ID;
	private Status status;
	private double incidentalAmountAuth;
	private double incidentalAmountClaimed;
	private Date voucherExpirationDate;
	private double creditCardRefund;
	
	private String approval_date;

	private Agent agent;
	private Station station;

	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	
	private TimeZone _TIMEZONE;

	public String toXML() {
		StringBuffer sb = new StringBuffer();

		sb.append("<expensepayout>");
		sb.append("<Expensepayout_ID>" + + Expensepayout_ID + "</Expensepayout_ID>");
		sb.append("<createdate>" + (createdate != null ? createdate.toString() : "") + "</createdate>");
		sb.append("<paycode>" + paycode + "</paycode>");
		sb.append("<draft>" + draft + "</draft>");
		sb.append("<draftreqdate>" + (draftreqdate != null ? draftreqdate.toString() : "")
				+ "</draftreqdate>");
		sb.append("<draftpaiddate>" + (draftpaiddate != null ? draftpaiddate.toString() : "")
				+ "</draftpaiddate>");
		sb.append("<checkamt>" + checkamt + "</checkamt>");
		sb.append("<voucheramt>" + voucheramt + "</voucheramt>");
		sb.append("<mileageamt>" + mileageamt + "</mileageamt>");
		sb.append("<expensetype_ID>" + expensetype.getExpensetype_ID() + "</expensetype_ID>");
		sb.append("<expensetype>" + expensetype.getDescription() + "</expensetype>");
		sb.append("<expenselocation_ID>" + expenselocation.getStation_ID() + "</expenselocation_ID>");
		sb.append("<expenselocation>" + expenselocation.getStationcode() + "</expenselocation>");
		sb.append("<currency>" + currency_ID + "</currency>");
		sb.append("<status_ID>" + (status == null ? "" : Integer.toString(status.getStatus_ID())) + "</status_ID>");
		sb.append("<status>" + (status == null ? "" : status.getDescription()) + "</status>");
		sb.append("<agent_ID>" + agent.getAgent_ID() + "</agent_ID>");
		sb.append("<agent>" + agent.getInitial() + "</agent>");
		sb.append("<station_ID>" + station.getStation_ID() + "</station_ID>");
		sb.append("<station>" + station.getStationcode() + "</station>");
		sb.append("<approval_date>" + approval_date + "</approval_date>");
		sb.append("</expensepayout>");

		return sb.toString();
	}

	public static ExpensePayout XMLtoObject(ElementNode root) {
		ExpensePayout obj = new ExpensePayout();

		ElementNode child = null, grandchild = null, ggrandchild = null, gggrandchild = null;

		for (ListIterator i = root.get_children().listIterator(); i.hasNext();) {
			child = (ElementNode) i.next();
			if (child.getType().equals("Expensepayout_ID")) {
				obj.setExpensepayout_ID(NumberUtils.parseInt(child.getTextContents()));
			} else if (child.getType().equals("createdate")) {
				obj.setCreatedate(DateUtils.convertToDate(child.getTextContents(),
						TracingConstants.DB_DATEFORMAT, null));
			} else if (child.getType().equals("paycode")) {
				obj.setPaycode(child.getTextContents());
			} else if (child.getType().equals("draft")) {
				obj.setDraft(child.getTextContents());
			} else if (child.getType().equals("draftreqdate")) {
				obj.setCreatedate(DateUtils.convertToDate(child.getTextContents(),
						TracingConstants.DB_DATEFORMAT, null));
			} else if (child.getType().equals("draftpaiddate")) {
				obj.setCreatedate(DateUtils.convertToDate(child.getTextContents(),
						TracingConstants.DB_DATEFORMAT, null));
			} else if (child.getType().equals("checkamt")) {
				obj.setCheckamt(NumberUtils.parseDouble(child.getTextContents()));
			} else if (child.getType().equals("voucheramt")) {
				obj.setVoucheramt(NumberUtils.parseDouble(child.getTextContents()));
			} else if (child.getType().equals("mileageamt")) {
				obj.setMileageamt(NumberUtils.parseInt(child.getTextContents()));
			} else if (child.getType().equals("expensetype_ID")) {
				ExpenseType et = new ExpenseType();
				et.setExpensetype_ID(NumberUtils.parseInt(child.getTextContents()));
				obj.setExpensetype(et);
			} else if (child.getType().equals("expenselocation_ID")) {
				Station el = new Station();
				el.setStation_ID(NumberUtils.parseInt(child.getTextContents()));
				obj.setExpenselocation(el);
			} else if (child.getType().equals("currency")) {
				obj.setCurrency_ID(child.getTextContents());
			} else if (child.getType().equals("status_ID")) {
				Status status = new Status();
				status.setStatus_ID(NumberUtils.parseInt(child.getTextContents()));
				obj.setStatus(status);			
			} else if (child.getType().equals("agent_ID")) {
				Agent agent = new Agent();
				agent.setAgent_ID(NumberUtils.parseInt(child.getTextContents()));
				obj.setAgent(agent);	
			} else if (child.getType().equals("station_ID")) {
				Station sta = new Station();
				sta.setStation_ID(NumberUtils.parseInt(child.getTextContents()));
				obj.setStation(sta);
			} else if (child.getType().equals("approval_date")) {
				obj.setApproval_date(child.getTextContents());
			}
			

		}
		
		
		
		return obj;
	}
	
	/**
	 * @return Returns the claim.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Claim" column="claim_ID" not-null="true"
	 */
	public Claim getClaim() {
		return claim;
	}

	/**
	 * @return Returns the approval_date.
	 * 
	 * @hibernate.property type="string" column="approval_date"
	 */
	public String getApproval_date() {
		return approval_date;
	}
	/**
	 * @param approval_date The approval_date to set.
	 */
	public void setApproval_date(String approval_date) {
		this.approval_date = approval_date;
	}
	
	public String getDispapproval_date() {
		return DateUtils.formatDate(getApproval_date(), TracingConstants.DB_DATETIMEFORMAT,
				get_DATEFORMAT() + " " + get_TIMEFORMAT(), null, get_TIMEZONE());
	}
	
	/**
	 * @param claim
	 *          The claim to set.
	 */
	public void setClaim(Claim claim) {
		this.claim = claim;
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
	 * @return Returns the expensetype.
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
	 * @return Returns the comments.
	 * 
	 * @hibernate.property type="string" length="255"
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *          The comments to set.
	 */
	public void setComments(String comments) {
		this.comments = comments;
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
	 * 
	 * @return 
	 * @hibernate.property type="date"
	 *  
	 */
	public Date getDraftpaiddate() {
		return draftpaiddate;
	}

	public void setDraftpaiddate(Date draftpaiddate) {
		this.draftpaiddate = draftpaiddate;
	}

	public void setDisdraftpaiddate(String s) {
		setDraftpaiddate(DateUtils.convertToDate(s, get_DATEFORMAT(), null));
	}

	public String getDisdraftpaiddate() {
		return DateUtils.formatDate(getDraftpaiddate(), get_DATEFORMAT(), null, null);
	}

	/**
	 * 
	 * @return 
	 * @hibernate.property type="date"
	 */
	public Date getDraftreqdate() {
		return draftreqdate;
	}

	public void setDraftreqdate(Date draftreqdate) {
		this.draftreqdate = draftreqdate;
	}

	public void setDisdraftreqdate(String s) {
		setDraftreqdate(DateUtils.convertToDate(s, get_DATEFORMAT(), null));
	}

	public String getDisdraftreqdate() {
		return DateUtils.formatDate(getDraftreqdate(), get_DATEFORMAT(), null, null);
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
	 * @return Returns the _DATEFORMAT.
	 */
	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}

	/**
	 * @param _dateformat
	 *          The _DATEFORMAT to set.
	 */
	public void set_TIMEFORMAT(String _timeformat) {
		_TIMEFORMAT = _timeformat;
	}

	public TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}

	public void set_TIMEZONE(TimeZone _timezone) {

		_TIMEZONE = _timezone;
	}

	/**
	 * @return Returns the expensepayout_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer"
	 *               column="Expensepayout_ID" unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="expensepayout_0"
	 * 
	 *  
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

	public void setDischeckamt(String s) {
		setCheckamt(TracerUtils.convertToDouble(s));
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

	public void setDisvoucheramt(String s) {
		setVoucheramt(TracerUtils.convertToDouble(s));
	}

	/** ** following code is for reporting ** */

	public String getLocdesc() {
		return expenselocation.getStationcode();
	}

	public String getTypedesc() {
		return expensetype.getDescription();
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
	 * @hibernate.property type="double" column="creditcard_refund"
	 */
	public double getCreditCardRefund() {
		return creditCardRefund;
	}

	public void setCreditCardRefund(double creditCardRefund) {
		this.creditCardRefund = creditCardRefund;
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
	
	

}