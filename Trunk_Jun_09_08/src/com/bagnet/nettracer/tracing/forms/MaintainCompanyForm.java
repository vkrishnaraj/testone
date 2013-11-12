package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;
import com.bagnet.nettracer.tracing.db.WTCompany;

/**
 * @author Byron Smith
 * 
 * This class represents bdo administration form
 */

public final class MaintainCompanyForm extends ValidatorForm {
	private String pageState;
	private String companySearchName;
	private String companyCode;
	private String companyDesc;
	private String addr1;
	private String addr2;
	private String city;
	private String state_ID;
	private String countrycode_ID;
	private String zip;
	private String phone;
	private String email_address;
	private String total_threads;
	private String seconds_wait;
	private String min_match_percent;
	private String mbr_to_lz_days;
	private String damaged_to_lz_days;
	private String miss_to_lz_days;
	private String ohd_to_lz_days;
	private String ohd_lz;
	private String new_lz;
	private String lz_mode;
	private String default_station_code;
	private String default_loss_code;
	private boolean email_customer;
	private boolean autoCloseOhd;
	private String email_host;
	private String email_port;
	private String email_from;
	private String email_to;
	private String max_image_file_size;
	private String min_interim_approval_check;
	private String min_interim_approval_miles;
	private String min_interim_approval_voucher;
	private String min_interim_approval_cc_refund;
	private String min_interim_approval_incidental;
	private String pass_expire_days;
	private String account_lockout;
	private String mbr_to_wt_days;
	private String ohd_to_wt_hours;
	private String oal_inc_hours;
	private String oal_ohd_hours;
	private String wt_user;
	private String wt_pass;
	private String audit_ohd;
	private String audit_lost_found;
	private String audit_lost_delayed;
	private String audit_damaged;
	private String audit_missing_articles;
	private String audit_agent;
	private String audit_group;
	private String audit_company;
	private String audit_shift;
	private String audit_permission;
	private String audit_station;
	private String audit_loss_codes;
	private String audit_claims;
	private String audit_airport;
	private String audit_delivery_companies;
	private String secure_password;
	private String webs_enabled;
	private List lzStations = new ArrayList();
	private List stations = new ArrayList();
	private List<WTCompany> wtCompanyList = new ArrayList<WTCompany>();
	private Integer defaultLz = 0;
	private String bak_nttracer_data_days;
	private String bak_nttracer_ohd_data_days;
	private String bak_nttracer_lostfound_data_days;
  private String wt_url;
  private String wt_airlinecode;
  private String wt_enabled;
  private String wt_write_enabled;
  private String selectedCarrier;
 	private Integer scannerDefaultBack;
 	private Integer scannerDefaultForward;
 	private String blindEmail;
	private boolean auto_wt_amend;
	private Integer min_pass_size;
	private Integer pass_x_history;
	private Integer pnrlastxdays;
	private Integer issuanceitemxdaysback;
	private String statusMessage;

	//AUTO CLOSE
	private Integer auto_close_days_back;
	private Integer auto_close_ld_code;
	private Integer auto_close_dam_code;
	private Integer auto_close_pil_code;
	private Integer auto_close_ld_station;
	private Integer auto_close_dam_station;
	private Integer auto_close_pil_station;
	private Integer auto_close_ohd_days_back;

	//lock incident
	private Integer incident_lock_mins;

	public String getWt_write_enabled() {
		return wt_write_enabled;
	}

	public void setWt_write_enabled(String wt_write_enabled) {
		this.wt_write_enabled = wt_write_enabled;
	}


	public String getWt_url() {
		return wt_url;
	}


	public void setWt_url(String wt_url) {
		this.wt_url = wt_url;
	}

	public String getWt_airlinecode() {
		return wt_airlinecode;
	}

	public void setWt_airlinecode(String wt_airlinecode) {
		this.wt_airlinecode = wt_airlinecode;
	}


	public String getWt_enabled() {
		return wt_enabled;
	}


	public void setWt_enabled(String wt_enabled) {
		this.wt_enabled = wt_enabled;
	}

	public void setBak_nttracer_lostfound_data_days(String days) {
		this.bak_nttracer_lostfound_data_days = days;
	}
	
	public String getBak_nttracer_lostfound_data_days() {
		return bak_nttracer_lostfound_data_days;
	}
	
	public void setBak_nttracer_ohd_data_days(String days) {
		this.bak_nttracer_ohd_data_days = days;
	}
	
	public String getBak_nttracer_ohd_data_days() {
		return bak_nttracer_ohd_data_days;
	}
	
	public void setBak_nttracer_data_days(String days) {
		this.bak_nttracer_data_days = days;
	}
	
	public String getBak_nttracer_data_days() {
		return bak_nttracer_data_days;
	}
	
	public void setDefaultLz(Integer defaultLz) {
		this.defaultLz = defaultLz;
	}
	
	public Integer getDefaultLz() {
		return defaultLz;
	}

	public List getLzStations() {
		return lzStations;
	}
	
	public void setLzStations(List lzStations) {
		this.lzStations = lzStations;
	}

	public List getStations() {
		return stations;
	}
	
	public void setStations(List stations) {
		this.stations = stations;
	}
	
	public String getPageState() { return this.pageState;}
	public String getCompanySearchName() { return this.companySearchName;}
	public String getCompanyCode() { return this.companyCode;}
	public String getCompanyDesc() { return this.companyDesc;}
	public String getAddr1() { return this.addr1;}
	public String getAddr2() { return this.addr2;}
	public String getCity() { return this.city;}
	public String getState_ID() { return this.state_ID;}
	public String getCountrycode_ID() { return this.countrycode_ID;}
	public String getZip() { return this.zip;}
	public String getPhone() { return this.phone;}
	public String getEmail_address() { return this.email_address;}
	public String getTotal_threads() { return this.total_threads;}
	public String getSeconds_wait() { return this.seconds_wait;}
	public String getMin_match_percent() { return this.min_match_percent;}
	public String getMbr_to_lz_days() { return this.mbr_to_lz_days;}
	public String getDamaged_to_lz_days() { return this.damaged_to_lz_days;}
	public String getMiss_to_lz_days() { return this.miss_to_lz_days;}
	public String getOhd_to_lz_days() { return this.ohd_to_lz_days;}
	public String getOhd_lz() { return this.ohd_lz;}
	public String getNew_lz() { return this.new_lz;}
	public String getLz_mode() { return this.lz_mode;}
	public String getDefault_station_code() { return this.default_station_code;}
	public String getDefault_loss_code() { return this.default_loss_code;}
	public String getEmail_host() { return this.email_host;}
	public String getEmail_port() { return this.email_port;}
	public String getEmail_from() { return this.email_from;}
	public String getEmail_to() { return this.email_to;}
	public String getMax_image_file_size() { return this.max_image_file_size;}
	public String getMin_interim_approval_check() { return this.min_interim_approval_check;}
	public String getMin_interim_approval_miles() { return this.min_interim_approval_miles;}
	public String getMin_interim_approval_voucher() { return this.min_interim_approval_voucher;}
	public String getPass_expire_days() { return this.pass_expire_days;}
	public String getAccount_lockout() { return this.account_lockout;}
	public String getMbr_to_wt_days() { return this.mbr_to_wt_days;}
	public String getOhd_to_wt_hours() { return this.ohd_to_wt_hours;}
	public String getWt_user() { return this.wt_user;}
	public String getWt_pass() { return this.wt_pass;}
	public String getAudit_ohd() { return this.audit_ohd;}
	public String getAudit_lost_found() { return this.audit_lost_found;}
	public String getAudit_lost_delayed() { return this.audit_lost_delayed;}
	public String getAudit_damaged() { return this.audit_damaged;}
	public String getAudit_missing_articles() { return this.audit_missing_articles;}
	public String getAudit_agent() { return this.audit_agent;}
	public String getAudit_group() { return this.audit_group;}
	public String getAudit_company() { return this.audit_company;}
	public String getAudit_shift() { return this.audit_shift;}
	public String getAudit_permission() { return this.audit_permission;}
	public String getAudit_station() { return this.audit_station;}
	public String getAudit_loss_codes() { return this.audit_loss_codes;}
	public String getAudit_claims() { return this.audit_claims;}
	public String getAudit_airport() { return this.audit_airport;}
	public String getAudit_delivery_companies() { return this.audit_delivery_companies;}
	public String getSecure_password() { return this.secure_password;}
	public String getWebs_enabled() { return this.webs_enabled;}
	public void setPageState ( String  pageState ) { this.pageState  =  pageState ; }
	public void setCompanySearchName ( String  companySearchName ) { this.companySearchName  =  companySearchName ; }
	public void setCompanyCode ( String  companyCode ) { this.companyCode  =  companyCode ; }
	public void setCompanyDesc ( String  companyDesc ) { this.companyDesc  =  companyDesc ; }
	public void setAddr1 ( String  addr1 ) { this.addr1  =  addr1 ; }
	public void setAddr2 ( String  addr2 ) { this.addr2  =  addr2 ; }
	public void setCity ( String  city ) { this.city  =  city ; }
	public void setState_ID ( String  state_ID ) { this.state_ID  =  state_ID ; }
	public void setCountrycode_ID ( String  countrycode_ID ) { this.countrycode_ID  =  countrycode_ID ; }
	public void setZip ( String  zip ) { this.zip  =  zip ; }
	public void setPhone ( String  phone ) { this.phone  =  phone ; }
	public void setEmail_address ( String  email_address ) { this.email_address  =  email_address ; }
	public void setTotal_threads ( String  total_threads ) { this.total_threads  =  total_threads ; }
	public void setSeconds_wait ( String  seconds_wait ) { this.seconds_wait  =  seconds_wait ; }
	public void setMin_match_percent ( String  min_match_percent ) { this.min_match_percent  =  min_match_percent ; }
	public void setMbr_to_lz_days ( String  mbr_to_lz_days ) { this.mbr_to_lz_days  =  mbr_to_lz_days ; }
	public void setDamaged_to_lz_days ( String  damaged_to_lz_days ) { this.damaged_to_lz_days  =  damaged_to_lz_days ; }
	public void setMiss_to_lz_days ( String  miss_to_lz_days ) { this.miss_to_lz_days  =  miss_to_lz_days ; }
	public void setOhd_to_lz_days ( String  ohd_to_lz_days ) { this.ohd_to_lz_days  =  ohd_to_lz_days ; }
	public void setOhd_lz ( String  ohd_lz ) { this.ohd_lz  =  ohd_lz ; }
	public void setNew_lz ( String  new_lz ) { this.new_lz  =  new_lz ; }
	public void setLz_mode ( String  lz_mode ) { this.lz_mode  =  lz_mode ; }
	public void setDefault_station_code ( String  default_station_code ) { this.default_station_code  =  default_station_code ; }
	public void setDefault_loss_code ( String  default_loss_code ) { this.default_loss_code  =  default_loss_code ; }
	public void setEmail_host ( String  email_host ) { this.email_host  =  email_host ; }
	public void setEmail_port ( String  email_port ) { this.email_port  =  email_port ; }
	public void setEmail_from ( String  email_from ) { this.email_from  =  email_from ; }
	public void setEmail_to ( String  email_to ) { this.email_to  =  email_to ; }
	public void setMax_image_file_size ( String  max_image_file_size ) { this.max_image_file_size  =  max_image_file_size ; }
	public void setMin_interim_approval_check ( String  min_interim_approval_check ) { this.min_interim_approval_check  =  min_interim_approval_check ; }
	public void setMin_interim_approval_miles ( String  min_interim_approval_miles ) { this.min_interim_approval_miles  =  min_interim_approval_miles ; }
	public void setMin_interim_approval_voucher ( String  min_interim_approval_voucher ) { this.min_interim_approval_voucher  =  min_interim_approval_voucher ; }
	public void setPass_expire_days ( String  pass_expire_days ) { this.pass_expire_days  =  pass_expire_days ; }
	public void setAccount_lockout ( String  account_lockout ) { this.account_lockout  =  account_lockout ; }
	public void setMbr_to_wt_days ( String  mbr_to_wt_days ) { this.mbr_to_wt_days  =  mbr_to_wt_days ; }
	public void setOhd_to_wt_hours ( String  ohd_to_wt_hours ) { this.ohd_to_wt_hours  =  ohd_to_wt_hours ; }
	public void setWt_user ( String  wt_user ) { this.wt_user  =  wt_user ; }
	public void setWt_pass ( String  wt_pass ) { this.wt_pass  =  wt_pass ; }
	public void setAudit_ohd ( String  audit_ohd ) { this.audit_ohd  =  audit_ohd ; }
	public void setAudit_lost_found ( String  audit_lost_found ) { this.audit_lost_found  =  audit_lost_found ; }
	public void setAudit_lost_delayed ( String  audit_lost_delayed ) { this.audit_lost_delayed  =  audit_lost_delayed ; }
	public void setAudit_damaged ( String  audit_damaged ) { this.audit_damaged  =  audit_damaged ; }
	public void setAudit_missing_articles ( String  audit_missing_articles ) { this.audit_missing_articles  =  audit_missing_articles ; }
	public void setAudit_agent ( String  audit_agent ) { this.audit_agent  =  audit_agent ; }
	public void setAudit_group ( String  audit_group ) { this.audit_group  =  audit_group ; }
	public void setAudit_company ( String  audit_company ) { this.audit_company  =  audit_company ; }
	public void setAudit_shift ( String  audit_shift ) { this.audit_shift  =  audit_shift ; }
	public void setAudit_permission ( String  audit_permission ) { this.audit_permission  =  audit_permission ; }
	public void setAudit_station ( String  audit_station ) { this.audit_station  =  audit_station ; }
	public void setAudit_loss_codes ( String  audit_loss_codes ) { this.audit_loss_codes  =  audit_loss_codes ; }
	public void setAudit_claims ( String  audit_claims ) { this.audit_claims  =  audit_claims ; }
	public void setAudit_airport ( String  audit_airport ) { this.audit_airport  =  audit_airport ; }
	public void setAudit_delivery_companies ( String  audit_delivery_companies ) { this.audit_delivery_companies  =  audit_delivery_companies ; }
	public void setSecure_password ( String  secure_password ) { this.secure_password  =  secure_password ; }
	public void setWebs_enabled ( String  webs_enabled ) { this.webs_enabled  =  webs_enabled ; }

	/**
	 * @return the scannerDefaultBack
	 */
	public Integer getScannerDefaultBack() {
		return scannerDefaultBack;
	}

	/**
	 * @param scannerDefaultBack the scannerDefaultBack to set
	 */
	public void setScannerDefaultBack(Integer scannerDefaultBack) {
		this.scannerDefaultBack = scannerDefaultBack;
	}

	/**
	 * @return the scannerDefaultForward
	 */
	public Integer getScannerDefaultForward() {
		return scannerDefaultForward;
	}

	/**
	 * @param scannerDefaultForward the scannerDefaultForward to set
	 */
	public void setScannerDefaultForward(Integer scannerDefaultForward) {
		this.scannerDefaultForward = scannerDefaultForward;
	}

	public String getBlindEmail() {
		return blindEmail;
	}

	public void setBlindEmail(String blindEmail) {
		this.blindEmail = blindEmail;
	}

	public boolean isAuto_wt_amend() {
		return auto_wt_amend;
	}

	public void setAuto_wt_amend(boolean auto_wt_amend) {
		this.auto_wt_amend = auto_wt_amend;
		
	}

	public boolean isEmail_customer() {
		return email_customer;
	}

	public void setEmail_customer(boolean email_customer) {
		this.email_customer = email_customer;
	}

	public String getOal_inc_hours() {
		return oal_inc_hours;
	}

	public void setOal_inc_hours(String oal_inc_hours) {
		this.oal_inc_hours = oal_inc_hours;
	}

	public String getOal_ohd_hours() {
		return oal_ohd_hours;
	}

	public void setOal_ohd_hours(String oal_ohd_hours) {
		this.oal_ohd_hours = oal_ohd_hours;
	}

	public String getMin_interim_approval_cc_refund() {
		return min_interim_approval_cc_refund;
	}

	public void setMin_interim_approval_cc_refund(String min_interim_approval_cc_refund) {
		this.min_interim_approval_cc_refund = min_interim_approval_cc_refund;
	}

	public String getMin_interim_approval_incidental() {
		return min_interim_approval_incidental;
	}

	public void setMin_interim_approval_incidental(String min_interim_approval_incidental) {
		this.min_interim_approval_incidental = min_interim_approval_incidental;
	}

	public boolean isAutoCloseOhd() {
		return autoCloseOhd;
	}

	public void setAutoCloseOhd(boolean autoCloseOhd) {
		this.autoCloseOhd = autoCloseOhd;
	}

	public void setMin_pass_size(Integer min_pass_size) {
		this.min_pass_size = min_pass_size;
	}

	public Integer getMin_pass_size() {
		return min_pass_size;
	}

	public void setPass_x_history(Integer pass_x_history) {
		this.pass_x_history = pass_x_history;
	}

	public Integer getPass_x_history() {
		return pass_x_history;
	}

	public Integer getAuto_close_days_back() {
		return auto_close_days_back;
	}

	public void setAuto_close_days_back(Integer auto_close_days_back) {
		this.auto_close_days_back = auto_close_days_back;
	}

	public Integer getAuto_close_ld_code() {
		return auto_close_ld_code;
	}

	public void setAuto_close_ld_code(Integer auto_close_ld_code) {
		this.auto_close_ld_code = auto_close_ld_code;
	}

	public Integer getAuto_close_dam_code() {
		return auto_close_dam_code;
	}

	public void setAuto_close_dam_code(Integer auto_close_dam_code) {
		this.auto_close_dam_code = auto_close_dam_code;
	}

	public Integer getAuto_close_pil_code() {
		return auto_close_pil_code;
	}

	public void setAuto_close_pil_code(Integer auto_close_pil_code) {
		this.auto_close_pil_code = auto_close_pil_code;
	}

	public Integer getAuto_close_ld_station() {
		return auto_close_ld_station;
	}

	public void setAuto_close_ld_station(Integer auto_close_ld_station) {
		this.auto_close_ld_station = auto_close_ld_station;
	}

	public Integer getAuto_close_dam_station() {
		return auto_close_dam_station;
	}

	public void setAuto_close_dam_station(Integer auto_close_dam_station) {
		this.auto_close_dam_station = auto_close_dam_station;
	}

	public Integer getAuto_close_pil_station() {
		return auto_close_pil_station;
	}

	public void setAuto_close_pil_station(Integer auto_close_pil_station) {
		this.auto_close_pil_station = auto_close_pil_station;
	}

	public void setIncident_lock_mins(Integer incident_lock_mins) {
		this.incident_lock_mins = incident_lock_mins;
	}

	public Integer getIncident_lock_mins() {
		return incident_lock_mins;
	}

	public Integer getPnrlastxdays() {
		return pnrlastxdays;
	}

	public void setPnrlastxdays(Integer pnrlastxdays) {
		this.pnrlastxdays = pnrlastxdays;
	}

	public Integer getIssuanceitemxdaysback() {
		return issuanceitemxdaysback;
	}

	public void setIssuanceitemxdaysback(Integer issuanceitemxdaysback) {
		this.issuanceitemxdaysback = issuanceitemxdaysback;
	}

	public Integer getAuto_close_ohd_days_back() {
		return auto_close_ohd_days_back;
	}

	public void setAuto_close_ohd_days_back(Integer auto_close_ohd_days_back) {
		this.auto_close_ohd_days_back = auto_close_ohd_days_back;
	}
	
	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getSelectedCarrier() {
		return this.selectedCarrier;
	}

	public void setSelectedCarrier(String selectedCarrier) {
		this.selectedCarrier = selectedCarrier;
	}

	public List<WTCompany> getWtCompanyList() {
		return wtCompanyList;
	}

	public void setWtCompanyList(List<WTCompany> wtCompanyList) {
		this.wtCompanyList = wtCompanyList;
	}

}