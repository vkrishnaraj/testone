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
import java.util.Locale;
import java.util.ResourceBundle;
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

	private static final long serialVersionUID = 3312395392352503765L;
	private Incident incident;
	private int expensepayout_ID;
	private Date createdate;
	private String paycode;
	private String draft;
	private Date draftreqdate;
	private Date draftpaiddate;
	private Date maildate;
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
	private String paytype;
	private int printcount;
	private String ordernum;
	private String slvnum;
	private String seccode;
	private String cancelreason;
	private int cancelcount;
	private int issuanceItem;
	
	// Passenger Expense Data
	private String firstname;
	private String middlename;
	private String lastname;
	private String address1;
	private String address2;
	private String city;
	private String zip;
	private String homephone;
	private String workphone;
	private String mobile;
	private String email;
	private String state_ID;
	private String countrycode_ID;
	private String province;

	//not part of the model
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private TimeZone _TIMEZONE;

	private String locale = TracingConstants.DEFAULT_LOCALE;
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

	public void setDismaildate(String s) {
		setMaildate(DateUtils.convertToDate(s, get_DATEFORMAT(), null));
	}

	@Transient
	public String getDismaildate() {
		return DateUtils.formatDate(getMaildate(), get_DATEFORMAT(), null, null);
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
	
	@Column(length = 10)
	public String getCancelreason() {
		return cancelreason;
	}

	public void setCancelreason(String cancelreason) {
		this.cancelreason = cancelreason;
	}
		
	@Column(length = 10)	
	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	
	@Column(length = 10)	
	public String getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	
	@Column(length = 20)
	public String getSlvnum() {
		return slvnum;
	}

	public void setSlvnum(String slvnum) {
		this.slvnum = slvnum;
	}
	
	@Column(length = 10)
	public String getSeccode() {
		return seccode;
	}

	public void setSeccode(String seccode) {
		this.seccode = seccode;
	}
	
	@Basic
	public int getPrintcount() {
		return printcount;
	}
	
	@Basic
	public void setPrintcount(int printcount) {
		this.printcount = printcount;
	}

	public int getIssuanceItem() {
		return issuanceItem;
	}

	public void setIssuanceItem(int issuanceItem) {
		this.issuanceItem = issuanceItem;
	}
	
	@Basic
	public int getCancelcount() {
		return cancelcount;
	}

	public void setCancelcount(int cancelcount) {
		this.cancelcount = cancelcount;
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
	
	@Transient
	public String getResourceValue() {
		ResourceBundle myResources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(locale));
		return myResources.getString(getStatus().getKey());
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

	@Temporal(TemporalType.DATE)
	public Date getMaildate() {
		return maildate;
	}

	public void setMaildate(Date maildate) {
		this.maildate = maildate;
	}
	
	@Column(length = 20)
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	@Column(length = 20)
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	
	@Column(length = 20)
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	@Column(length = 50)
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	@Column(length = 50)
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	@Column(length = 50)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(length = 11)
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	@Column(length = 50)	
	public String getHomephone() {
		return homephone;
	}
	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}
	
	@Column(length = 50)	
	public String getWorkphone() {
		return workphone;
	}
	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}
	
	@Column(length = 50)	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(length = 100)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(length = 2)
	public String getState_ID() {
		return state_ID;
	}
	public void setState_ID(String state_ID) {
		this.state_ID = state_ID;
	}
	
	@Transient
	public String getState() {
		if (state_ID != null && state_ID.length() > 0) {
			return TracerUtils.getState(state_ID).getState();
		}
		return "";
	}
	
	@Column(length = 3)
	public String getCountrycode_ID() {
		return countrycode_ID;
	}
	public void setCountrycode_ID(String countrycode_ID) {
		this.countrycode_ID = countrycode_ID;
	}
	
	@Column(length = 100)
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}

}