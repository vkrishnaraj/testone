package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Prorate_Itinerary;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author Ankur Gupta
 * 
 * This class represents the form that is used for claim prorate related
 * functionality.
 */
public class ClaimProrateForm extends ActionForm {

	private int Claimprorate_ID;
	private String companycode_ID;
	private Date createdate;
	private String file_reference;
	private int pir_attached;
	private int claim_attached;
	private int confirmpayment_attached;
	private int all_prorate;
	private String all_prorate_reason;
	private int remit;
	private double remit_amount;
	private String currency_ID;
	private String remit_to;
	private int clearing_bill;
	private String prorate_officer;
	private String sita_address;
	private String fax_number;
	private double total_percentage;
	private double total_share;
	private List itinerarylist = new ArrayList();
	private String passname;
	private int claimtype_ID; // for reporting purpose
	private String claimtype;
	private String ticketnumber;
	private List claimchecknumlist = new ArrayList();
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private java.util.TimeZone _TIMEZONE;

	/**
	 * @return Returns the itinerarylist.
	 */
	public List getItinerarylist() {
		return itinerarylist;
	}

	/**
	 * @param itinerarylist
	 *          The itinerarylist to set.
	 */
	public void setItinerarylist(List itinerarylist) {
		this.itinerarylist = itinerarylist;
	}

	public Prorate_Itinerary getItinerary(int index) {
		if (this.itinerarylist.size() <= index) {
			this.itinerarylist.add(new Prorate_Itinerary());
		}
		return (Prorate_Itinerary) this.itinerarylist.get(index);
	}

	/**
	 * @return Returns the all_prorate.
	 */
	public int getAll_prorate() {
		return all_prorate;
	}

	/**
	 * @param all_prorate
	 *          The all_prorate to set.
	 */
	public void setAll_prorate(int all_prorate) {
		this.all_prorate = all_prorate;
	}

	/**
	 * @return Returns the all_prorate_reason.
	 */
	public String getAll_prorate_reason() {
		return all_prorate_reason;
	}

	/**
	 * @param all_prorate_reason
	 *          The all_prorate_reason to set.
	 */
	public void setAll_prorate_reason(String all_prorate_reason) {
		this.all_prorate_reason = all_prorate_reason;
	}

	/**
	 * @return Returns the claim_attached.
	 */
	public int getClaim_attached() {
		return claim_attached;
	}

	/**
	 * @param claim_attached
	 *          The claim_attached to set.
	 */
	public void setClaim_attached(int claim_attached) {
		this.claim_attached = claim_attached;
	}

	/**
	 * @return Returns the claimprorate_ID.
	 */
	public int getClaimprorate_ID() {
		return Claimprorate_ID;
	}

	/**
	 * @param claimprorate_ID
	 *          The claimprorate_ID to set.
	 */
	public void setClaimprorate_ID(int claimprorate_ID) {
		Claimprorate_ID = claimprorate_ID;
	}

	/**
	 * @return Returns the clearing_bill.
	 */
	public int getClearing_bill() {
		return clearing_bill;
	}

	/**
	 * @param clearing_bill
	 *          The clearing_bill to set.
	 */
	public void setClearing_bill(int clearing_bill) {
		this.clearing_bill = clearing_bill;
	}

	/**
	 * @return Returns the confirmpayment_attached.
	 */
	public int getConfirmpayment_attached() {
		return confirmpayment_attached;
	}

	/**
	 * @param confirmpayment_attached
	 *          The confirmpayment_attached to set.
	 */
	public void setConfirmpayment_attached(int confirmpayment_attached) {
		this.confirmpayment_attached = confirmpayment_attached;
	}

	/**
	 * @return Returns the companycode_ID.
	 */
	public String getCompanycode_ID() {
		return companycode_ID;
	}

	/**
	 * @param companycode_ID
	 *          The companycode_ID to set.
	 */
	public void setCompanycode_ID(String companycode_ID) {
		this.companycode_ID = companycode_ID;
	}
	
	public String getCompanyName() {
		return AdminUtils.getCompany(companycode_ID).getCompanydesc();
	}

	/**
	 * @return Returns the createdate.
	 */
	public Date getCreatedate() {
		return createdate;
	}

	public String getDisplaydate() {
		return DateUtils.formatDate(getCreatedate(), _DATEFORMAT, null, _TIMEZONE);
	}

	/**
	 * @param createdate
	 *          The createdate to set.
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	/**
	 * @return Returns the currency_ID.
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
	 * @return Returns the fax_number.
	 */
	public String getFax_number() {
		return fax_number;
	}

	/**
	 * @param fax_number
	 *          The fax_number to set.
	 */
	public void setFax_number(String fax_number) {
		this.fax_number = fax_number;
	}

	/**
	 * @return Returns the file_reference.
	 */
	public String getFile_reference() {
		return file_reference;
	}

	/**
	 * @param file_reference
	 *          The file_reference to set.
	 */
	public void setFile_reference(String file_reference) {
		this.file_reference = file_reference;
	}

	/**
	 * @return Returns the pir_attached.
	 */
	public int getPir_attached() {
		return pir_attached;
	}

	/**
	 * @param pir_attached
	 *          The pir_attached to set.
	 */
	public void setPir_attached(int pir_attached) {
		this.pir_attached = pir_attached;
	}

	/**
	 * @return Returns the prorate_officer.
	 */
	public String getProrate_officer() {
		return prorate_officer;
	}

	/**
	 * @param prorate_officer
	 *          The prorate_officer to set.
	 */
	public void setProrate_officer(String prorate_officer) {
		this.prorate_officer = prorate_officer;
	}

	/**
	 * @return Returns the remit.
	 */
	public int getRemit() {
		return remit;
	}

	/**
	 * @param remit
	 *          The remit to set.
	 */
	public void setRemit(int remit) {
		this.remit = remit;
	}

	/**
	 * @return Returns the remit_amount.
	 */
	public double getRemit_amount() {
		return remit_amount;
	}

	/**
	 * @param remit_amount
	 *          The remit_amount to set.
	 */
	public void setRemit_amount(double remit_amount) {
		this.remit_amount = remit_amount;
	}

	public String getDisremit_amount() {
		return TracingConstants.DECIMALFORMAT.format(getRemit_amount());
	}

	public void setDisremit_amount(String s) {
		setRemit_amount(TracerUtils.convertToDouble(s));
	}

	/**
	 * @return Returns the remit_to.
	 */
	public String getRemit_to() {
		return remit_to;
	}

	/**
	 * @param remit_to
	 *          The remit_to to set.
	 */
	public void setRemit_to(String remit_to) {
		this.remit_to = remit_to;
	}

	/**
	 * @return Returns the sita_address.
	 */
	public String getSita_address() {
		return sita_address;
	}

	/**
	 * @param sita_address
	 *          The sita_address to set.
	 */
	public void setSita_address(String sita_address) {
		this.sita_address = sita_address;
	}

	/**
	 * @return Returns the total_percentage.
	 */
	public double getTotal_percentage() {
		return total_percentage;
	}

	/**
	 * @param total_percentage
	 *          The total_percentage to set.
	 */
	public void setTotal_percentage(double total_percentage) {
		this.total_percentage = total_percentage;
	}

	public String getDistotal_percentage() {
		return TracingConstants.DECIMALFORMAT.format(getTotal_percentage());
	}

	public void setDistotal_percentage(String s) {
		setTotal_percentage(TracerUtils.convertToDouble(s));
	}

	/**
	 * @return Returns the total_share.
	 */
	public double getTotal_share() {
		return total_share;
	}

	/**
	 * @param total_share
	 *          The total_share to set.
	 */
	public void setTotal_share(double total_share) {
		this.total_share = total_share;
	}

	public String getDistotal_share() {
		return TracingConstants.DECIMALFORMAT.format(getTotal_share());
	}

	public void setDistotal_share(String s) {
		setTotal_share(TracerUtils.convertToDouble(s));
	}

	/**
	 * @return Returns the claimtype.
	 */
	public String getClaimtype() {
		return claimtype;
	}

	/**
	 * @param claimtype
	 *          The claimtype to set.
	 */
	public void setClaimtype(String claimtype) {
		this.claimtype = claimtype;
	}

	/**
	 * @return Returns the passname.
	 */
	public String getPassname() {
		return passname;
	}

	/**
	 * @param passname
	 *          The passname to set.
	 */
	public void setPassname(String passname) {
		this.passname = passname;
	}

	/**
	 * @return Returns the claimchecknumlist.
	 */
	public List getClaimchecknumlist() {
		return claimchecknumlist;
	}

	/**
	 * @param claimchecknumlist
	 *          The claimchecknumlist to set.
	 */
	public void setClaimchecknumlist(List claimchecknumlist) {
		this.claimchecknumlist = claimchecknumlist;
	}

	/**
	 * @return Returns the ticketnumber.
	 */
	public String getTicketnumber() {
		return ticketnumber;
	}

	/**
	 * @param ticketnumber
	 *          The ticketnumber to set.
	 */
	public void setTicketnumber(String ticketnumber) {
		this.ticketnumber = ticketnumber;
	}

	public int getClaimtype_ID() {
		return claimtype_ID;
	}

	public void setClaimtype_ID(int claimtype_ID) {
		this.claimtype_ID = claimtype_ID;
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