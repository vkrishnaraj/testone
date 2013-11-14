/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

@Entity
@Table(name = "ExpensePayout")
@Proxy(lazy = false)
public class ExpensePayout implements Serializable {

	private Incident incident;
	private int expensepayout_ID;
	private Date createdate;
	private String paycode;
	private String draft;
	private Date draftreqdate;
	private Date draftpaiddate;
	private double checkamt;
	private double voucheramt;
	private int mileageamt;
	private Set<Comment> comments = new HashSet<Comment>();
	private ExpenseType expensetype;
	private Station expenselocation;
	private java.util.Currency currency;
	private Status status;
	private double incidentalAmountAuth;
	private double incidentalAmountClaimed;
	private Date voucherExpirationDate;
	private double creditCardRefund;
	private Date approval_date;
	private Agent agent;
	private Station station;
	private BDO bdo;
	private OHD ohd;
	
	private String distributemethod;
	

	//not part of the model
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private TimeZone _TIMEZONE;

	@ManyToOne
	@JoinColumn(name = "incident_ID", nullable = true)
	@Fetch(FetchMode.SELECT)
	public Incident getIncident() {
		return incident;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getApproval_date() {
		return approval_date;
	}

	public void setApproval_date(Date approval_date) {
		this.approval_date = approval_date;
	}

	@Transient
	public String getDispapproval_date() {
		return DateUtils.formatDate(approval_date, get_DATEFORMAT() + " " + get_TIMEFORMAT(), null, get_TIMEZONE());
	}

	@ManyToOne
	@JoinColumn(name = "expenseType_ID", nullable = false)
	@Fetch(FetchMode.SELECT)
	public ExpenseType getExpensetype() {
		return expensetype;
	}

	public void setExpensetype(ExpenseType expensetype) {
		this.expensetype = expensetype;
	}

	@ManyToOne
	@JoinColumn(name = "expenselocation_ID")
	public Station getExpenselocation() {
		return expenselocation;
	}

	public void setExpenselocation(Station expenselocation) {
		this.expenselocation = expenselocation;
	}


	@ManyToOne
	@JoinColumn(name = "agent_ID", nullable = false)
	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@ManyToOne
	@JoinColumn(name = "station_ID", nullable = false)
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}



	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "expense_comment", joinColumns = @JoinColumn(name = "expensepayout_ID"))
	@org.hibernate.annotations.OrderBy(clause = "createDate asc")
	@Fetch(FetchMode.SELECT)
	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedate() {
		return createdate;
	}

	@Transient
	public String getDiscreatedate() {
		return DateUtils.formatDate(getCreatedate(), get_DATEFORMAT(), null, get_TIMEZONE());
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@Column(length = 30)
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

	@Temporal(TemporalType.DATE)
	public Date getDraftpaiddate() {
		return draftpaiddate;
	}

	public void setDraftpaiddate(Date draftpaiddate) {
		this.draftpaiddate = draftpaiddate;
	}

	
	public void setDisdraftpaiddate(String s) {
		setDraftpaiddate(DateUtils.convertToDate(s, get_DATEFORMAT(), null));
	}

	@Transient
	public String getDisdraftpaiddate() {
		return DateUtils.formatDate(getDraftpaiddate(), get_DATEFORMAT(), null, null);
	}

	@Temporal(TemporalType.DATE)
	public Date getDraftreqdate() {
		return draftreqdate;
	}

	public void setDraftreqdate(Date draftreqdate) {
		this.draftreqdate = draftreqdate;
	}

	public void setDisdraftreqdate(String s) {
		setDraftreqdate(DateUtils.convertToDate(s, get_DATEFORMAT(), null));
	}

	@Transient
	public String getDisdraftreqdate() {
		return DateUtils.formatDate(getDraftreqdate(), get_DATEFORMAT(), null, null);
	}

	@Transient
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	public void set_DATEFORMAT(String _dateformat) {
		_DATEFORMAT = _dateformat;
	}

	@Transient
	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}

	public void set_TIMEFORMAT(String _timeformat) {
		_TIMEFORMAT = _timeformat;
	}

	@Transient
	public TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}

	public void set_TIMEZONE(TimeZone _timezone) {

		_TIMEZONE = _timezone;
	}

	@Id
	@GeneratedValue
	public int getExpensepayout_ID() {
		return expensepayout_ID;
	}
	public void setExpensepayout_ID(int expensepayout_ID) {
		this.expensepayout_ID = expensepayout_ID;
	}

	@Column(name = "currency_ID")
	public java.util.Currency getCurrency() {
		return currency;
	}

	public void setCurrency(java.util.Currency currency) {
		this.currency = currency;
	}
	
	@Transient
	public String getCurrency_ID() {
		return currency.getCurrencyCode();
	}

	@Column(length = 5)
	public String getPaycode() {
		return paycode;
	}

	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}
	
	@Column(length = 5)	
	public String getDistributemethod() {
		return distributemethod;
	}

	public void setDistributemethod(String distributemethod) {
		this.distributemethod = distributemethod;
	}


	@Basic
	public double getCheckamt() {
		return checkamt;
	}

	public void setCheckamt(double checkamt) {
		this.checkamt = checkamt;
	}

	@Transient
	public String getDischeckamt() {
		return TracingConstants.DECIMALFORMAT.format(getCheckamt());
	}

	public void setDischeckamt(String s) {
		setCheckamt(TracerUtils.convertToDouble(s));
	}

	@Basic
	public int getMileageamt() {
		return mileageamt;
	}
	
	public void setMileageamt(int mileageamt) {
		this.mileageamt = mileageamt;
	}

	@Basic
	public double getVoucheramt() {
		return voucheramt;
	}

	public void setVoucheramt(double voucheramt) {
		this.voucheramt = voucheramt;
	}

	@Transient
	public String getDisvoucheramt() {
		return TracingConstants.DECIMALFORMAT.format(getVoucheramt());
	}

	public void setDisvoucheramt(String s) {
		setVoucheramt(TracerUtils.convertToDouble(s));
	}

	@Transient
	public String getLocdesc() {
		return expenselocation.getStationcode();
	}

	@Transient
	public String getTypedesc() {
		return expensetype.getDescription();
	}

	@ManyToOne
	@JoinColumn(name = "status_ID")
	@Fetch(FetchMode.SELECT)
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Column(name = "incidental_amount_auth")
	public double getIncidentalAmountAuth() {
		return incidentalAmountAuth;
	}

	public void setIncidentalAmountAuth(double incidentalAmount) {
		this.incidentalAmountAuth = incidentalAmount;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "voucher_exp")
	public Date getVoucherExpirationDate() {
		return voucherExpirationDate;
	}

	public void setVoucherExpirationDate(Date voucherExpirationDate) {
		this.voucherExpirationDate = voucherExpirationDate;
	}

	@Column(name = "creditcard_refund")
	public double getCreditCardRefund() {
		return creditCardRefund;
	}

	public void setCreditCardRefund(double creditCardRefund) {
		this.creditCardRefund = creditCardRefund;
	}

	@Column(name = "incidental_amount_claim")
	public double getIncidentalAmountClaimed() {
		return incidentalAmountClaimed;
	}

	public void setIncidentalAmountClaimed(double incidentalAmountClaimed) {
		this.incidentalAmountClaimed = incidentalAmountClaimed;
	}

	@ManyToOne
	@JoinColumn(name = "bdo_id", nullable = true)
	@Fetch(FetchMode.SELECT)
	public BDO getBdo() {
		return bdo;
	}

	public void setBdo(BDO bdo) {
		this.bdo = bdo;
	}

	@ManyToOne
	@JoinColumn(name = "ohd_id", nullable = true)
	@Fetch(FetchMode.SELECT)
	public OHD getOhd() {
		return ohd;
	}

	public void setOhd(OHD ohd) {
		this.ohd = ohd;
	}

	@Transient
	public String getDispComments() {
		String result = "";
		
		DateFormat myFormat = new SimpleDateFormat(TracingConstants.DISPLAY_DATEFORMAT);
		
		Set<Comment> comments = getComments();
		
		for (Comment myComment : comments) {
			result += myComment.getAgent().getUsername() + " ";
			result += myFormat.format(myComment.getCreateDate()) + "\n";
			result += myComment.getContent()+ "\n\n";
		}
		
		return result;
	}

}