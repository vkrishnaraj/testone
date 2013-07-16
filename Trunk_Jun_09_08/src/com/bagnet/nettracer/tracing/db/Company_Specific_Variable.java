/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * @hibernate.class table="company_specific_variable"
 * @hibernate.cache usage="read-write"
 */
public class Company_Specific_Variable implements Serializable {
	private String companyCode_ID;
	private int total_threads;
	private int seconds_wait;
	private double min_match_percent;
	private int mbr_to_lz_days;
	private int damaged_to_lz_days;
	private int miss_to_lz_days;
	private int ohd_to_lz_days;
	private int ohd_lz;
	private int lz_mode;

	private int report_method;
	private int default_station_code;
	private int default_loss_code;
	private boolean email_customer; // send email to customer after report creation or
	private boolean autoCloseOhd;
	private double min_interim_approval_check;
	private double min_interim_approval_miles;
	private double min_interim_approval_voucher;
	private double min_interim_approval_incidental;
	private double min_interim_approval_cc_refund;
    private int bak_nttracer_data_days;
    private int bak_nttracer_ohd_data_days;
    private int bak_nttracer_lostfound_data_days;
	private int mbr_to_wt_days;
	private int ohd_to_wt_hours;
	private int oal_ohd_hours;
	private int oal_inc_hours;
	private boolean auto_wt_amend;
	private String wt_user;
	private String wt_pass;
	private int retrieve_actionfile_interval;
	private int audit_ohd;
	private int audit_lost_found;
	private int audit_lost_delayed;
	private int audit_damaged;
	private int audit_missing_articles;
	private int audit_agent;
	private int audit_group;
	private int audit_company;
	private int audit_shift;
	private int audit_station;
	private int audit_loss_codes;
	private int audit_claims;
	private int audit_airport;
	private int audit_delivery_companies;
	private int max_image_file_size;
	private String email_host;
	private int email_port;
	private String email_from;
	private String email_to;
	private int pass_expire_days;
	private int max_failed_logins;
	private int webs_enabled;
	private int secure_password;
	private String wt_url;
	private String wt_airlinecode;
	private int wt_enabled;
	private int wt_write_enabled;
	private int scannerDefaultBack;
	private int scannerDefaultForward;
	private String blindEmail;
	private int min_pass_size;
	private int pass_x_history;
	/**
	 * Variable to determine the last amount of days to include incidents for a PNR Prepopulation Check. -Sean Fine
	 */
	private int pnr_last_x_days;
	
	//AUTO CLOSE
	private int auto_close_days_back;
	private int auto_close_ld_code;
	private int auto_close_dam_code;
	private int auto_close_pil_code;
	private int auto_close_ld_station;
	private int auto_close_dam_station;
	private int auto_close_pil_station;

	//Incident Lock
	private int incident_lock_mins;
	
	/**
	 * @return the wt_write_enabled
	 * @hibernate.property type="integer"
	 */
	public int getWt_write_enabled() {
		return wt_write_enabled;
	}
	/**
	 * @param wt_write_enabled the wt_write_enabled to set
	 */
	public void setWt_write_enabled(int wt_write_enabled) {
		this.wt_write_enabled = wt_write_enabled;
	}
	/**
	 * @return the wt_url
	 * @hibernate.property type="string"
	 */
	public String getWt_url() {
		return wt_url;
	}
	/**
	 * @param wt_url the wt_url to set
	 */
	public void setWt_url(String wt_url) {
		this.wt_url = wt_url;
	}
	/**
	 * @return the wt_airlinecode
	 * @hibernate.property type="string"
	 */
	public String getWt_airlinecode() {
		return wt_airlinecode;
	}
	/**
	 * @param wt_airlinecode the wt_airlinecode to set
	 */
	public void setWt_airlinecode(String wt_airlinecode) {
		this.wt_airlinecode = wt_airlinecode;
	}
	/**
	 * @return the wt_enabled
	 * @hibernate.property type="integer"
	 */
	public int getWt_enabled() {
		return wt_enabled;
	}
	/**
	 * @param wt_enabled the wt_enabled to set
	 */
	public void setWt_enabled(int wt_enabled) {
		this.wt_enabled = wt_enabled;
	}
	/**
	 * @return Returns the default_loss_code.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getDefault_loss_code() {
		return default_loss_code;
	}
	/**
	 * @param default_loss_code The default_loss_code to set.
	 */
	public void setDefault_loss_code(int default_loss_code) {
		this.default_loss_code = default_loss_code;
	}
	/**
	 * @hibernate.property type="integer"
	 * 
	 * @return Returns the max_image_file_size.
	 */
	public int getMax_image_file_size() {
		return max_image_file_size;
	}

	/**
	 * @param max_image_file_size
	 *          The max_image_file_size to set.
	 */
	public void setMax_image_file_size(int max_image_file_size) {
		this.max_image_file_size = max_image_file_size;
	}

	/**
	 * @hibernate.property type="double"
	 * 
	 * @return Returns the min_interim_approval_check.
	 */
	public double getMin_interim_approval_check() {
		return min_interim_approval_check;
	}

	/**
	 * @param min_interim_approval_check
	 *          The min_interim_approval_check to set.
	 */
	public void setMin_interim_approval_check(double min_interim_approval_check) {
		this.min_interim_approval_check = min_interim_approval_check;
	}

	/**
	 * @hibernate.property type="double"
	 * @return Returns the min_interim_approval_miles.
	 */
	public double getMin_interim_approval_miles() {
		return min_interim_approval_miles;
	}

	/**
	 * @param min_interim_approval_miles
	 *          The min_interim_approval_miles to set.
	 */
	public void setMin_interim_approval_miles(double min_interim_approval_miles) {
		this.min_interim_approval_miles = min_interim_approval_miles;
	}

	/**
	 * @hibernate.property type="double"
	 * 
	 * @return Returns the min_interim_approval_voucher.
	 */
	public double getMin_interim_approval_voucher() {
		return min_interim_approval_voucher;
	}

	/**
	 * @param min_interim_approval_voucher
	 *          The min_interim_approval_voucher to set.
	 */
	public void setMin_interim_approval_voucher(double min_interim_approval_voucher) {
		this.min_interim_approval_voucher = min_interim_approval_voucher;
	}

	/**
	 * @hibernate.property type="double"
	 * 
	 * @return Returns the min_interim_approval_incidental.
	 */
	public double getMin_interim_approval_incidental() {
		return min_interim_approval_incidental;
	}
	
	public void setMin_interim_approval_incidental(double min_interim_approval_incidental) {
		this.min_interim_approval_incidental = min_interim_approval_incidental;
	}
	
	/**
	 * @hibernate.property type="double"
	 * 
	 * @return Returns the min_interim_approval_cc_refund.
	 */
	public double getMin_interim_approval_cc_refund() {
		return min_interim_approval_cc_refund;
	}
	
	public void setMin_interim_approval_cc_refund(double min_interim_approval_cc_refund) {
		this.min_interim_approval_cc_refund = min_interim_approval_cc_refund;
	}
	
	/**
	 * @hibernate.property type="integer"
	 * 
	 * @return Returns the audit_airport.
	 */
	public int getAudit_airport() {
		return audit_airport;
	}

	/**
	 * @param audit_airport
	 *          The audit_airport to set.
	 */
	public void setAudit_airport(int audit_airport) {
		this.audit_airport = audit_airport;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the audit_agent.
	 */
	public int getAudit_agent() {
		return audit_agent;
	}

	/**
	 * @param audit_agent
	 *          The audit_agent to set.
	 */
	public void setAudit_agent(int audit_agent) {
		this.audit_agent = audit_agent;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the audit_company.
	 */
	public int getAudit_company() {
		return audit_company;
	}

	/**
	 * @param audit_company
	 *          The audit_company to set.
	 */
	public void setAudit_company(int audit_company) {
		this.audit_company = audit_company;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the audit_damaged.
	 */
	public int getAudit_damaged() {
		return audit_damaged;
	}

	/**
	 * @param audit_damaged
	 *          The audit_damaged to set.
	 */
	public void setAudit_damaged(int audit_damaged) {
		this.audit_damaged = audit_damaged;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the audit_group.
	 */
	public int getAudit_group() {
		return audit_group;
	}

	/**
	 * @param audit_group
	 *          The audit_group to set.
	 */
	public void setAudit_group(int audit_group) {
		this.audit_group = audit_group;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the audit_loss_codes.
	 */
	public int getAudit_loss_codes() {
		return audit_loss_codes;
	}

	/**
	 * @param audit_loss_codes
	 *          The audit_loss_codes to set.
	 */
	public void setAudit_loss_codes(int audit_loss_codes) {
		this.audit_loss_codes = audit_loss_codes;
	}

	/**
	 * @hibernate.property type="integer"
	 * 
	 * @return Returns the audit_lost_delayed.
	 */
	public int getAudit_lost_delayed() {
		return audit_lost_delayed;
	}

	/**
	 * @param audit_lost_delayed
	 *          The audit_lost_delayed to set.
	 */
	public void setAudit_lost_delayed(int audit_lost_delayed) {
		this.audit_lost_delayed = audit_lost_delayed;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the audit_lost_found.
	 */
	public int getAudit_lost_found() {
		return audit_lost_found;
	}

	/**
	 * @param audit_lost_found
	 *          The audit_lost_found to set.
	 */
	public void setAudit_lost_found(int audit_lost_found) {
		this.audit_lost_found = audit_lost_found;
	}

	/**
	 * @hibernate.property type="integer"
	 * 
	 * @return Returns the audit_missing_articles.
	 */
	public int getAudit_missing_articles() {
		return audit_missing_articles;
	}

	/**
	 * @param audit_missing_articles
	 *          The audit_missing_articles to set.
	 */
	public void setAudit_missing_articles(int audit_missing_articles) {
		this.audit_missing_articles = audit_missing_articles;
	}

	/**
	 * @hibernate.property type="integer"
	 * 
	 * @return Returns the audit_shift.
	 */
	public int getAudit_shift() {
		return audit_shift;
	}

	/**
	 * @param audit_shift
	 *          The audit_shift to set.
	 */
	public void setAudit_shift(int audit_shift) {
		this.audit_shift = audit_shift;
	}

	/**
	 * @hibernate.property type="integer"
	 * 
	 * @return Returns the audit_station.
	 */
	public int getAudit_station() {
		return audit_station;
	}

	/**
	 * @param audit_station
	 *          The audit_station to set.
	 */
	public void setAudit_station(int audit_station) {
		this.audit_station = audit_station;
	}

	/**
	 * @hibernate.property type="integer"
	 * 
	 * @return Returns the audit delivery company.
	 */
	public int getAudit_delivery_companies() {
		return audit_delivery_companies;
	}

	/**
	 * @param audit_station
	 *          The audit_delivery_company to set.
	 */
	public void setAudit_delivery_companies(int audit_delivery_companies) {
		this.audit_delivery_companies = audit_delivery_companies;
	}

	
	/**
	 * @hibernate.property type="integer"
	 * @return Returns the audit_ohd.
	 */
	public int getAudit_ohd() {
		return audit_ohd;
	}

	/**
	 * @param audit_ohd
	 *          The audit_ohd to set.
	 */
	public void setAudit_ohd(int audit_ohd) {
		this.audit_ohd = audit_ohd;
	}

	/**
	 * @return Returns the audit_claims.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getAudit_claims() {
		return audit_claims;
	}

	/**
	 * @param audit_claims
	 *          The audit_claims to set.
	 */
	public void setAudit_claims(int audit_claims) {
		this.audit_claims = audit_claims;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the default_station_code.
	 */
	public int getDefault_station_code() {
		return default_station_code;
	}

	/**
	 * @param default_station_code
	 *          The default_station_code to set.
	 */
	public void setDefault_station_code(int default_station_code) {
		this.default_station_code = default_station_code;
	}

	/**
	 * @return Returns the min_match_percent.
	 * 
	 * @hibernate.property type="double"
	 */
	public double getMin_match_percent() {
		return min_match_percent;
	}

	/**
	 * @param min_match_percent
	 *          The min_match_percent to set.
	 */
	public void setMin_match_percent(double min_match_percent) {
		this.min_match_percent = min_match_percent;
	}

	/**
	 * @return Returns the seconds_wait.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getSeconds_wait() {
		return seconds_wait;
	}

	/**
	 * @param seconds_wait
	 *          The seconds_wait to set.
	 * 
	 *  
	 */
	public void setSeconds_wait(int seconds_wait) {
		this.seconds_wait = seconds_wait;
	}

	/**
	 * @return Returns the total_threads.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getTotal_threads() {
		return total_threads;
	}

	/**
	 * @param total_threads
	 *          The total_threads to set.
	 */
	public void setTotal_threads(int total_threads) {
		this.total_threads = total_threads;
	}

	/**
	 * @return Returns the mbr_to_lz_days.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getMbr_to_lz_days() {
		return mbr_to_lz_days;
	}

	/**
	 * @param mbr_to_lz_days
	 *          The mbr_to_lz_days to set.
	 */
	public void setMbr_to_lz_days(int mbr_to_lz_days) {
		this.mbr_to_lz_days = mbr_to_lz_days;
	}

	/**
	 * @return Returns the damaged_to_lz_days.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getDamaged_to_lz_days() {
		return damaged_to_lz_days;
	}
	/**
	 * @param damaged_to_lz_days The damaged_to_lz_days to set.
	 */
	public void setDamaged_to_lz_days(int damaged_to_lz_days) {
		this.damaged_to_lz_days = damaged_to_lz_days;
	}
	
	
	
	/**
	 * @return Returns the miss_to_lz_days.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getMiss_to_lz_days() {
		return miss_to_lz_days;
	}
	/**
	 * @param miss_to_lz_days The miss_to_lz_days to set.
	 */
	public void setMiss_to_lz_days(int miss_to_lz_days) {
		this.miss_to_lz_days = miss_to_lz_days;
	}
	
	/**
	 * @return Returns the ohd_to_lz_days.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getOhd_to_lz_days() {
		return ohd_to_lz_days;
	}

	/**
	 * @param ohd_to_lz_days
	 *          The ohd_to_lz_days to set.
	 */
	public void setOhd_to_lz_days(int ohd_to_lz_days) {
		this.ohd_to_lz_days = ohd_to_lz_days;
	}

	/**
	 * @hibernate.id generator-class="assigned" type="string"
	 *               column="companyCode_ID"
	 * @return Returns the companyCode_ID.
	 */
	public String getCompanyCode_ID() {
		return companyCode_ID;
	}

	/**
	 * @param companyCode_ID
	 *          The companyCode_ID to set.
	 */
	public void setCompanyCode_ID(String companyCode_ID) {
		this.companyCode_ID = companyCode_ID;
	}

	/**
	 * @return Returns the report_method.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getReport_method() {
		return report_method;
	}

	/**
	 * @param report_method
	 *          The report_method to set.
	 */
	public void setReport_method(int report_method) {
		this.report_method = report_method;
	}

	
	/**
	 * @return Returns the email_from.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getEmail_from() {
		if (email_from == null) return "";
		return email_from;
	}
	/**
	 * @param email_from The email_from to set.
	 */
	public void setEmail_from(String email_from) {
		this.email_from = email_from;
	}
	/**
	 * @return Returns the email_host.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getEmail_host() {
		if (email_host == null) return "localhost";
		return email_host;
	}
	/**
	 * @param email_host The email_host to set.
	 */
	public void setEmail_host(String email_host) {
		this.email_host = email_host;
	}
	/**
	 * @return Returns the email_port.
	 *
	 * @hibernate.property type="integer"
	 */
	public int getEmail_port() {
		return email_port;
	}
	/**
	 * @param email_port The email_port to set.
	 */
	public void setEmail_port(int email_port) {
		this.email_port = email_port;
	}
	/**
	 * @return Returns the email_to.
	 *
	 * @hibernate.property type="string"
	 */
	public String getEmail_to() {
		if (email_to == null) return "";
		return email_to;
	}
	/**
	 * @param email_to The email_to to set.
	 */
	public void setEmail_to(String email_to) {
		this.email_to = email_to;
	}
	
	
	/**
	 * @return Returns the pass_expire_days.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getPass_expire_days() {
		return pass_expire_days;
	}
	/**
	 * @param pass_expire_days The pass_expire_days to set.
	 */
	public void setPass_expire_days(int pass_expire_days) {
		this.pass_expire_days = pass_expire_days;
	}
	
	/**
	 * @return the webs_enabled
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getWebs_enabled() {
		return webs_enabled;
	}
	/**
	 * @param ws_enabled the ws_enabled to set
	 */
	public void setWebs_enabled(int webs_enabled) {
		this.webs_enabled = webs_enabled;
	}
	
	/**
	 * @return Returns the max_failed_logins.
	 *
	 * @hibernate.property type="integer"
	 */
	public int getMax_failed_logins() {
		return max_failed_logins;
	}
	/**
	 * @param max_failed_logins The maximum number of failable logins to set.
	 */
	public void setMax_failed_logins(int max_failed_logins) {
		this.max_failed_logins = max_failed_logins;
	}
	
	/**
	 * @return the secure_password
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getSecure_password() {
		return secure_password;
	}
	/**
	 * @param secure_password the secure_password to set
	 */
	public void setSecure_password(int secure_password) {
		this.secure_password = secure_password;
	}

	/**
	 * @return the mbr_to_wt_days
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getMbr_to_wt_days() {
		return mbr_to_wt_days;
	}
	/**
	 * @param mbr_to_wt_days the mbr_to_wt_days to set
	 */
	public void setMbr_to_wt_days(int mbr_to_wt_days) {
		this.mbr_to_wt_days = mbr_to_wt_days;
	}
	/**
	 * @return the ohd_to_wt_hours
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getOhd_to_wt_hours() {
		return ohd_to_wt_hours;
	}
	/**
	 * @param ohd_to_wt_hours the ohd_to_wt_hours to set
	 */
	public void setOhd_to_wt_hours(int ohd_to_wt_hours) {
		this.ohd_to_wt_hours = ohd_to_wt_hours;
	}


	/**
	 * @return the wt_user
	 * @hibernate.property type="string"
	 */
	public String getWt_user() {
		return wt_user;
	}
	/**
	 * @return the wt_pass
	 * @hibernate.property type="string"
	 */
	public String getWt_pass() {
		return wt_pass;
	}
	/**
	 * @param wt_user the wt_user to set
	 */
	public void setWt_user(String wt_user) {
		this.wt_user = wt_user;
	}
	/**
	 * @param wt_pass the wt_pass to set
	 */
	public void setWt_pass(String wt_pass) {
		this.wt_pass = wt_pass;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the ohd_lz.
	 */
	public int getOhd_lz() {
		return ohd_lz;
	}

	/**
	 * @param ohd_lz
	 *          The ohd_lz to set.
	 */
	public void setOhd_lz(int ohd_lz) {
		this.ohd_lz = ohd_lz;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the ohd_lz.
	 */
	public int getLz_mode() {
		return lz_mode;
	}
	/**
	 * @param lz_mode
	 *          The lz_mode to set.
	 */
	public void setLz_mode(int lz_mode) {
		this.lz_mode = lz_mode;
	}


	/**
	 * @return the bak_nttracer_data_days
	 * @hibernate.property type="integer"
	 */
	public int getBak_nttracer_data_days() {
		return bak_nttracer_data_days;
	}
	/**
	 * @param bak_nttracer_data_days the bak_nttracer_data_days to set
	 */
	public void setBak_nttracer_data_days(int bak_nttracer_data_days) {
		this.bak_nttracer_data_days = bak_nttracer_data_days;
	}
	/**
	 * @return the bak_nttracer_ohd_data_days
	 * @hibernate.property type="integer"
	 */
	public int getBak_nttracer_ohd_data_days() {
		return bak_nttracer_ohd_data_days;
	}
	/**
	 * @param bak_nttracer_ohd_data_days the bak_nttracer_ohd_data_days to set
	 */
	public void setBak_nttracer_ohd_data_days(int bak_nttracer_ohd_data_days) {
		this.bak_nttracer_ohd_data_days = bak_nttracer_ohd_data_days;
	}
	/**
	 * @return the bak_nttracer_lostfound_data_days
	 * @hibernate.property type="integer"
	 */
	public int getBak_nttracer_lostfound_data_days() {
		return bak_nttracer_lostfound_data_days;
	}
	/**
	 * @param bak_nttracer_lostfound_data_days the bak_nttracer_lostfound_data_days to set
	 */
	public void setBak_nttracer_lostfound_data_days(
			int bak_nttracer_lostfound_data_days) {
		this.bak_nttracer_lostfound_data_days = bak_nttracer_lostfound_data_days;
	}
	/**
	 * @return the retrieve_actionfile_interval
	 * @hibernate.property type="integer"
	 */
	public int getRetrieve_actionfile_interval() {
		return retrieve_actionfile_interval;
	}
	/**
	 * @param retrieve_actionfile_interval the retrieve_actionfile_interval to set
	 */
	public void setRetrieve_actionfile_interval(int retrieve_actionfile_interval) {
		this.retrieve_actionfile_interval = retrieve_actionfile_interval;
	}
	/**
	 * @return the scannerDefaultBack
	 * @hibernate.property type="integer"
	 */
	public int getScannerDefaultBack() {
		return scannerDefaultBack;
	}
	/**
	 * @param scannerDefaultBack the scannerDefaultBack to set
	 */
	public void setScannerDefaultBack(int scannerDefaultBack) {
		this.scannerDefaultBack = scannerDefaultBack;
	}
	
	/**
	 * @return the scannerDefaultForward
	 * @hibernate.property type="integer"
	 */
	public int getScannerDefaultForward() {
		return scannerDefaultForward;
	}
	
	/**
	 * @param scannerDefaultForward the scannerDefaultForward to set
	 */
	public void setScannerDefaultForward(int scannerDefaultForward) {
		this.scannerDefaultForward = scannerDefaultForward;
	}
	
	/**
	 * @return the scannerDefaultForward
	 * @hibernate.property type="string" column="blind_cc" length="100"
	 */
	public String getBlindEmail() {
		return blindEmail;
	}
	
	public void setBlindEmail(String blindEmail) {
		this.blindEmail = blindEmail;
	}
	/**
	 * @return
	 * @hibernate.property type="integer"
	 */
	public int getOal_ohd_hours() {
		return oal_ohd_hours;
	}
	/**
	 * @param oal_ohd_hours
	 */
	public void setOal_ohd_hours(int oal_ohd_hours) {
		this.oal_ohd_hours = oal_ohd_hours;
	}
	/**
	 * @return
	 * @hibernate.property type="integer"
	 */
	public int getOal_inc_hours() {
		return oal_inc_hours;
	}
	/**
	 * @param oal_inc_hours
	 *
	 */
	public void setOal_inc_hours(int oal_inc_hours) {
		this.oal_inc_hours = oal_inc_hours;
	}
	
	/**
	 * @return
	 * @hibernate.property type="boolean"
	 */
	public boolean isAuto_wt_amend() {
		return auto_wt_amend;
	}
	/**
	 * @param auto_wt_amend
	 */
	public void setAuto_wt_amend(boolean auto_wt_amend) {
		this.auto_wt_amend = auto_wt_amend;
	}
	
	/**
	 * @return Returns the email_customer.
	 * 
	 * @hibernate.property type="boolean"
	 */
	public boolean isEmail_customer() {
		return email_customer;
	}
	public void setEmail_customer(boolean email_customer) {
		this.email_customer = email_customer;
	}
	
	/**
	 * @return Returns the email_customer.
	 * 
	 * @hibernate.property type="boolean"
	 */
	public boolean isAutoCloseOhd() {
		return autoCloseOhd;
	}
	
	public void setAutoCloseOhd(boolean autoCloseOhd) {
		this.autoCloseOhd = autoCloseOhd;
	}
	
	
	public void setMin_pass_size(int min_pass_size) {
		this.min_pass_size = min_pass_size;
	}
	/**
	 * @return
	 * @hibernate.property type="integer"
	 */
	public int getMin_pass_size() {
		return min_pass_size;
	}
	
	public void setPass_x_history(int pass_x_history) {
		this.pass_x_history = pass_x_history;
	}
	/**
	 * @return
	 * @hibernate.property type="integer"
	 */
	public int getPass_x_history() {
		return pass_x_history;
	}
	
	public void setAuto_close_days_back(int auto_close_days_back) {
		this.auto_close_days_back = auto_close_days_back;
	}
	/**
	 * @return
	 * @hibernate.property type="integer"
	 */
	public int getAuto_close_days_back() {
		return auto_close_days_back;
	}
	
	public void setAuto_close_ld_code(int auto_close_ld_code) {
		this.auto_close_ld_code = auto_close_ld_code;
	}
	/**
	 * @return
	 * @hibernate.property type="integer"
	 */
	public int getAuto_close_ld_code() {
		return auto_close_ld_code;
	}
	
	public void setAuto_close_dam_code(int auto_close_dam_code) {
		this.auto_close_dam_code = auto_close_dam_code;
	}
	/**
	 * @return
	 * @hibernate.property type="integer"
	 */
	public int getAuto_close_dam_code() {
		return auto_close_dam_code;
	}
	
	public void setAuto_close_pil_code(int auto_close_pil_code) {
		this.auto_close_pil_code = auto_close_pil_code;
	}
	/**
	 * @return
	 * @hibernate.property type="integer"
	 */
	public int getAuto_close_pil_code() {
		return auto_close_pil_code;
	}
	
	public void setAuto_close_ld_station(int auto_close_ld_station) {
		this.auto_close_ld_station = auto_close_ld_station;
	}
	/**
	 * @return
	 * @hibernate.property type="integer"
	 */
	public int getAuto_close_ld_station() {
		return auto_close_ld_station;
	}
	
	public void setAuto_close_dam_station(int auto_close_dam_station) {
		this.auto_close_dam_station = auto_close_dam_station;
	}
	/**
	 * @return
	 * @hibernate.property type="integer"
	 */
	public int getAuto_close_dam_station() {
		return auto_close_dam_station;
	}
	
	public void setAuto_close_pil_station(int auto_close_pil_station) {
		this.auto_close_pil_station = auto_close_pil_station;
	}
	/**
	 * @return
	 * @hibernate.property type="integer"
	 */
	public int getAuto_close_pil_station() {
		return auto_close_pil_station;
	}
	
	
	public void setIncident_lock_mins(int incident_lock_mins) {
		this.incident_lock_mins = incident_lock_mins;
	}
	/**
	 * @return
	 * @hibernate.property type="integer"
	 */
	public int getIncident_lock_mins() {
		return incident_lock_mins;
	}

	/**
	 * @return the pnr_last_x_days
	 * @hibernate.property type="integer"
	 */
	public int getPnr_last_x_days() {
		return pnr_last_x_days;
	}
	
	public void setPnr_last_x_days(int pnr_last_x_days) {
		this.pnr_last_x_days = pnr_last_x_days;
	}
	
}