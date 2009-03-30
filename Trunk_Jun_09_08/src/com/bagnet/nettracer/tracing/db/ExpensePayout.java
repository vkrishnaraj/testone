/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

@Entity
@Table(name = "ExpensePayout")
@Proxy(lazy = false)
public class ExpensePayout implements Serializable {

	@ManyToOne
	@JoinColumn(name = "incident_ID", nullable = false)
	private Incident incident;

	@Id
	@GeneratedValue
	private int expensepayout_ID;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdate;
	
	@Column(length = 5)
	private String paycode;
	
	@Column(length = 30)
	private String draft;
	
	@Temporal(TemporalType.DATE)
	private Date draftreqdate;
	
	@Temporal(TemporalType.DATE)
	private Date draftpaiddate;
	
	@Basic
	private double checkamt;
	
	@Basic
	private double voucheramt;
	
	@Basic
	private int mileageamt;

	@org.hibernate.annotations.CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name = "expense_comment", joinColumns = @JoinColumn(name = "expensepayout_ID"))
	@org.hibernate.annotations.OrderBy(clause = "createDate asc")
	@Fetch(FetchMode.SUBSELECT)
	private Set<Comment> comments = new HashSet<Comment>();

	@ManyToOne
	@JoinColumn(name = "expenseType_ID", nullable = false)
	private ExpenseType expensetype;

	@ManyToOne
	@JoinColumn(name = "expenselocation_ID")
	private Station expenselocation;

	@Column(name = "currency_ID")
	private java.util.Currency currency;

	@ManyToOne
	@JoinColumn(name = "status_ID")
	private Status status;
	
	@Column(name = "incidental_amount_auth")
	private double incidentalAmountAuth;
	
	@Column(name = "incidental_amount_claim")
	private double incidentalAmountClaimed;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "voucher_exp")
	private Date voucherExpirationDate;
	
	@Column(name = "creditcard_refund")
	private double creditCardRefund;

	@Temporal(TemporalType.TIMESTAMP)
	private Date approval_date;

	@ManyToOne
	@JoinColumn(name = "agent_ID", nullable = false)
	private Agent agent;

	@ManyToOne
	@JoinColumn(name = "station_ID", nullable = false)
	private Station station;

	@Transient
	private String _DATEFORMAT;
	@Transient
	private String _TIMEFORMAT;

	@Transient
	private TimeZone _TIMEZONE;

	public Incident getIncident() {
		return incident;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	public Date getApproval_date() {
		return approval_date;
	}

	public void setApproval_date(Date approval_date) {
		this.approval_date = approval_date;
	}

	public String getDispapproval_date() {
		return DateUtils.formatDate(approval_date, get_DATEFORMAT() + " " + get_TIMEFORMAT(), null, get_TIMEZONE());
	}

	public ExpenseType getExpensetype() {
		return expensetype;
	}

	public void setExpensetype(ExpenseType expensetype) {
		this.expensetype = expensetype;
	}

	public Station getExpenselocation() {
		return expenselocation;
	}

	public void setExpenselocation(Station expenselocation) {
		this.expenselocation = expenselocation;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public String getCurrency_ID() {
		return currency.getCurrencyCode();
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public String getDiscreatedate() {
		return DateUtils.formatDate(getCreatedate(), get_DATEFORMAT(), null, get_TIMEZONE());
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getDraft() {
		return draft;
	}

	/**
	 * @param draft
	 *            The draft to set.
	 */
	public void setDraft(String draft) {
		this.draft = draft;
	}

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
	 *            The _DATEFORMAT to set.
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
	 *            The _DATEFORMAT to set.
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

	public int getExpensepayout_ID() {
		return expensepayout_ID;
	}
	public void setExpensepayout_ID(int expensepayout_ID) {
		expensepayout_ID = expensepayout_ID;
	}

	public java.util.Currency getCurrency() {
		return currency;
	}

	public void setCurrency(java.util.Currency currency) {
		this.currency = currency;
	}


	public String getPaycode() {
		return paycode;
	}

	/**
	 * @param paycode
	 *            The paycode to set.
	 */
	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}


	public double getCheckamt() {
		return checkamt;
	}


	public void setCheckamt(double checkamt) {
		this.checkamt = checkamt;
	}

	public String getDischeckamt() {
		return TracingConstants.DECIMALFORMAT.format(getCheckamt());
	}

	public void setDischeckamt(String s) {
		setCheckamt(TracerUtils.convertToDouble(s));
	}

	public int getMileageamt() {
		return mileageamt;
	}

	/**
	 * @param mileageamt
	 *            The mileageamt to set.
	 */
	public void setMileageamt(int mileageamt) {
		this.mileageamt = mileageamt;
	}

	public double getVoucheramt() {
		return voucheramt;
	}

	/**
	 * @param voucheramt
	 *            The voucheramt to set.
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
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            The status to set.
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return Returns the status.
	 * 
	 */
	public double getIncidentalAmountAuth() {
		return incidentalAmountAuth;
	}

	public void setIncidentalAmountAuth(double incidentalAmount) {
		this.incidentalAmountAuth = incidentalAmount;
	}

	public Date getVoucherExpirationDate() {
		return voucherExpirationDate;
	}

	public void setVoucherExpirationDate(Date voucherExpirationDate) {
		this.voucherExpirationDate = voucherExpirationDate;
	}


	public double getCreditCardRefund() {
		return creditCardRefund;
	}

	public void setCreditCardRefund(double creditCardRefund) {
		this.creditCardRefund = creditCardRefund;
	}

	public double getIncidentalAmountClaimed() {
		return incidentalAmountClaimed;
	}

	public void setIncidentalAmountClaimed(double incidentalAmountClaimed) {
		this.incidentalAmountClaimed = incidentalAmountClaimed;
	}

}