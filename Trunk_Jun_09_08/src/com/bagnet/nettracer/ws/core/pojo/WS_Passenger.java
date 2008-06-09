package com.bagnet.nettracer.ws.core.pojo;


public class WS_Passenger {
	private int Passenger_ID;
	private String jobtitle;
	private String salutation;
	private String firstname;
	private String middlename;
	private String lastname;
	private String driverslicense;
	private String dlstate;
	private String commonnum;
	private String countryofissue;
	private int isprimary;

	private String membershipnum;
	private String membershipstatus;
	private String membershipairline;
	

	// since one passenger one address, for now, keep them together
	private String address1;
	private String address2;
	private String city;
	private String zip;
	private String hotel;
	private String homephone;
	private String workphone;
	private String mobile;
	private String pager;
	private String altphone;
	private String email;
	private int is_permanent;
	private String state_ID;
	private String countrycode_ID;
	private String province;

	private String valid_bdate;
	private String valid_edate;
	/**
	 * @return the passenger_ID
	 */
	public int getPassenger_ID() {
		return Passenger_ID;
	}
	/**
	 * @param passenger_ID the passenger_ID to set
	 */
	public void setPassenger_ID(int passenger_ID) {
		Passenger_ID = passenger_ID;
	}
	/**
	 * @return the jobtitle
	 */
	public String getJobtitle() {
		return jobtitle;
	}
	/**
	 * @param jobtitle the jobtitle to set
	 */
	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}
	/**
	 * @return the salutation
	 */
	public String getSalutation() {
		return salutation;
	}
	/**
	 * @param salutation the salutation to set
	 */
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * @return the middlename
	 */
	public String getMiddlename() {
		return middlename;
	}
	/**
	 * @param middlename the middlename to set
	 */
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * @return the driverslicense
	 */
	public String getDriverslicense() {
		return driverslicense;
	}
	/**
	 * @param driverslicense the driverslicense to set
	 */
	public void setDriverslicense(String driverslicense) {
		this.driverslicense = driverslicense;
	}
	/**
	 * @return the dlstate
	 */
	public String getDlstate() {
		return dlstate;
	}
	/**
	 * @param dlstate the dlstate to set
	 */
	public void setDlstate(String dlstate) {
		this.dlstate = dlstate;
	}
	/**
	 * @return the commonnum
	 */
	public String getCommonnum() {
		return commonnum;
	}
	/**
	 * @param commonnum the commonnum to set
	 */
	public void setCommonnum(String commonnum) {
		this.commonnum = commonnum;
	}
	/**
	 * @return the countryofissue
	 */
	public String getCountryofissue() {
		return countryofissue;
	}
	/**
	 * @param countryofissue the countryofissue to set
	 */
	public void setCountryofissue(String countryofissue) {
		this.countryofissue = countryofissue;
	}
	/**
	 * @return the isprimary
	 */
	public int getIsprimary() {
		return isprimary;
	}
	/**
	 * @param isprimary the isprimary to set
	 */
	public void setIsprimary(int isprimary) {
		this.isprimary = isprimary;
	}
	/**
	 * @return the membershipnum
	 */
	public String getMembershipnum() {
		return membershipnum;
	}
	/**
	 * @param membershipnum the membershipnum to set
	 */
	public void setMembershipnum(String membershipnum) {
		this.membershipnum = membershipnum;
	}
	/**
	 * @return the membershipstatus
	 */
	public String getMembershipstatus() {
		return membershipstatus;
	}
	/**
	 * @param membershipstatus the membershipstatus to set
	 */
	public void setMembershipstatus(String membershipstatus) {
		this.membershipstatus = membershipstatus;
	}
	/**
	 * @return the membershipairline
	 */
	public String getMembershipairline() {
		return membershipairline;
	}
	/**
	 * @param membershipairline the membershipairline to set
	 */
	public void setMembershipairline(String membershipairline) {
		this.membershipairline = membershipairline;
	}
	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}
	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}
	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @return the hotel
	 */
	public String getHotel() {
		return hotel;
	}
	/**
	 * @param hotel the hotel to set
	 */
	public void setHotel(String hotel) {
		this.hotel = hotel;
	}
	/**
	 * @return the homephone
	 */
	public String getHomephone() {
		return homephone;
	}
	/**
	 * @param homephone the homephone to set
	 */
	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}
	/**
	 * @return the workphone
	 */
	public String getWorkphone() {
		return workphone;
	}
	/**
	 * @param workphone the workphone to set
	 */
	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the pager
	 */
	public String getPager() {
		return pager;
	}
	/**
	 * @param pager the pager to set
	 */
	public void setPager(String pager) {
		this.pager = pager;
	}
	/**
	 * @return the altphone
	 */
	public String getAltphone() {
		return altphone;
	}
	/**
	 * @param altphone the altphone to set
	 */
	public void setAltphone(String altphone) {
		this.altphone = altphone;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the is_permanent
	 */
	public int getIs_permanent() {
		return is_permanent;
	}
	/**
	 * @param is_permanent the is_permanent to set
	 */
	public void setIs_permanent(int is_permanent) {
		this.is_permanent = is_permanent;
	}
	/**
	 * @return the state_ID
	 */
	public String getState_ID() {
		return state_ID;
	}
	/**
	 * @param state_ID the state_ID to set
	 */
	public void setState_ID(String state_ID) {
		this.state_ID = state_ID;
	}
	/**
	 * @return the countrycode_ID
	 */
	public String getCountrycode_ID() {
		return countrycode_ID;
	}
	/**
	 * @param countrycode_ID the countrycode_ID to set
	 */
	public void setCountrycode_ID(String countrycode_ID) {
		this.countrycode_ID = countrycode_ID;
	}
	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * @return the valid_bdate
	 */
	public String getValid_bdate() {
		return valid_bdate;
	}
	/**
	 * @param valid_bdate the valid_bdate to set
	 */
	public void setValid_bdate(String valid_bdate) {
		this.valid_bdate = valid_bdate;
	}
	/**
	 * @return the valid_edate
	 */
	public String getValid_edate() {
		return valid_edate;
	}
	/**
	 * @param valid_edate the valid_edate to set
	 */
	public void setValid_edate(String valid_edate) {
		this.valid_edate = valid_edate;
	}
	
}
