/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;
import java.util.ListIterator;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.NumberUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.cci.utils.parser.ElementNode;
import com.cci.utils.parser.XMLParser;

/**
 * @author Administrator
 * 
 * create date - Jul 14, 2004
 * @hibernate.class table="Address"
 */
public class Address implements Serializable {
	private int Address_ID;

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
	private int addresstype;
	private int is_permanent;
	private String state_ID;
	private String countrycode_ID;
	private String province;

	private Date valid_bdate;
	private Date valid_edate;

	private Passenger passenger;
	
	private String _DATEFORMAT; //for date time format purpose only, not

	public String getState() {
		if (state_ID != null && state_ID.length() > 0) {
			return TracerUtils.getState(state_ID, "en").getState();
		}
		return "";
	}

	public String getCountry() {
		if (countrycode_ID != null && countrycode_ID.length() > 0) {
			return TracerUtils.getCountry(countrycode_ID, "en").getCountry();
		}
		return "";
	}

	/**
	 * @return Returns the passenger.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Passenger"
	 *                        column="passenger_ID" not-null="true"
	 * 
	 */
	public Passenger getPassenger() {
		return passenger;
	}
	/**
	 * @param passenger The passenger to set.
	 */
	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}
	
	/**
	 * @return Returns the countrycode_ID.
	 * 
	 * @hibernate.property type="string" length="3"
	 */
	public String getCountrycode_ID() {
		return countrycode_ID;
	}

	/**
	 * @param country_ID
	 *          The countrycode_ID to set.
	 */
	public void setCountrycode_ID(String countrycode_ID) {
		this.countrycode_ID = countrycode_ID;
	}

	/**
	 * @return Returns the state_ID.
	 * 
	 * @hibernate.property type="string" length="2"
	 */
	public String getState_ID() {
		return state_ID;
	}

	/**
	 * @param state_ID
	 *          The state_ID to set.
	 */
	public void setState_ID(String state_ID) {
		this.state_ID = state_ID;
	}

	/**
	 * @return Returns the addresstype.
	 * 
	 * @hibernate.property type="integer" length="6"
	 */
	public int getAddresstype() {
		return addresstype;
	}

	/**
	 * @param addresstype
	 *          The addresstype to set.
	 */
	public void setAddresstype(int addresstype) {
		this.addresstype = addresstype;
	}

	/**
	 * @return Returns the is_permanent.
	 * 
	 * @hibernate.property type="integer" length="1"
	 */
	public int getIs_permanent() {
		return is_permanent;
	}
	/**
	 * @param is_permanent The is_permanent to set.
	 */
	public void setIs_permanent(int is_permanent) {
		this.is_permanent = is_permanent;
	}
	
	/**
	 * @return Returns the address_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 *               column="Address_ID"
	 * 
	 * @hibernate.generator-param name="sequence" value="ADDRESS_0"
	 */
	public int getAddress_ID() {
		return Address_ID;
	}

	/**
	 * @param address_ID
	 *          The address_ID to set.
	 */
	public void setAddress_ID(int address_ID) {
		Address_ID = address_ID;
	}

	/**
	 * @return Returns the address1.
	 * 
	 * @hibernate.property type="string" length="50"
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1
	 *          The address1 to set.
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return Returns the address2.
	 * 
	 * @hibernate.property type="string" length="50"
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2
	 *          The address2 to set.
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return Returns the altphone.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getAltphone() {
		return altphone;
	}

	/**
	 * @param altphone
	 *          The altphone to set.
	 */
	public void setAltphone(String altphone) {
		this.altphone = altphone;
	}

	/**
	 * @return Returns the city.
	 * 
	 * @hibernate.property type="string" length="50"
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *          The city to set.
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return Returns the email.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *          The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Returns the homephone.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getHomephone() {
		return homephone;
	}

	/**
	 * @param homephone
	 *          The homephone to set.
	 */
	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}

	/**
	 * @return Returns the hotel.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getHotel() {
		return hotel;
	}

	/**
	 * @param hotel
	 *          The hotel to set.
	 */
	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	/**
	 * @return Returns the mobile.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *          The mobile to set.
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return Returns the pager.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getPager() {
		return pager;
	}

	/**
	 * @param pager
	 *          The pager to set.
	 */
	public void setPager(String pager) {
		this.pager = pager;
	}

	/**
	 * @return Returns the workphone.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getWorkphone() {
		return workphone;
	}

	/**
	 * @param workphone
	 *          The workphone to set.
	 */
	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}

	/**
	 * @return Returns the zip.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip
	 *          The zip to set.
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return Returns the province.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province
	 *          The province to set.
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return Returns the valid_bdate.
	 * 
	 * @hibernate.property type="date"
	 */
	public Date getValid_bdate() {
		return valid_bdate;
	}

	/**
	 * @param valid_bdate
	 *          The valid_bdate to set.
	 */
	public void setValid_bdate(Date valid_bdate) {
		this.valid_bdate = valid_bdate;
	}

	public void setDispvalid_bdate(String s) {
		setValid_bdate(DateUtils.convertToDate(s, get_DATEFORMAT(), null));
	}

	public String getDispvalid_bdate() {
		return DateUtils.formatDate(getValid_bdate(), get_DATEFORMAT(), null, null);
	}

	/**
	 * @return Returns the valid_edate.
	 * 
	 * @hibernate.property type="date"
	 */
	public Date getValid_edate() {
		return valid_edate;
	}

	/**
	 * @param valid_edate
	 *          The valid_edate to set.
	 */
	public void setValid_edate(Date valid_edate) {
		this.valid_edate = valid_edate;
	}

	public void setDispvalid_edate(String s) {
		setValid_edate(DateUtils.convertToDate(s, get_DATEFORMAT(), null));
	}

	public String getDispvalid_edate() {
		return DateUtils.formatDate(getValid_edate(), get_DATEFORMAT(), null, null);
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

	public String toXML() {
		StringBuffer sb = new StringBuffer();

		sb.append("<address>");
		sb.append("<Address_ID>" + getAddress_ID() + "</Address_ID>");
		sb.append("<address1>" + getAddress1() + "</address1>");
		sb.append("<address2>" + getAddress2() + "</address2>");
		sb.append("<city>" + getCity() + "</city>");
		sb.append("<state_ID>" + getState_ID() + "</state_ID>");
		sb.append("<province>" + getProvince() + "</province>");
		sb.append("<hotel>" + getHotel() + "</hotel>");
		sb.append("<zip>" + getZip() + "</zip>");
		sb.append("<countrycode_ID>" + getCountrycode_ID() + "</countrycode_ID>");
		sb.append("<homephone>" + getHomephone() + "</homephone>");
		sb.append("<workphone>" + getWorkphone() + "</workphone>");
		sb.append("<mobile>" + getMobile() + "</mobile>");
		sb.append("<pager>" + getPager() + "</pager>");
		sb.append("<altphone>" + getAltphone() + "</altphone>");
		sb.append("<email>" + getEmail() + "</email>");
		sb.append("<valid_bdate>" + (valid_bdate != null ? valid_bdate.toString() : "")
				+ "</valid_bdate>");
		sb.append("<valid_edate>" + (valid_edate != null ? valid_edate.toString() : "")
				+ "</valid_edate>");

		sb.append("</address>");

		return sb.toString();
	}

	public static Address XMLtoObject(ElementNode root) {
		Address obj = new Address();

		XMLParser parser = null;

		ElementNode child = null, grandchild = null, ggrandchild = null, gggrandchild = null;

		boolean break_main = false;

		for (ListIterator i = root.get_children().listIterator(); i.hasNext();) {
			child = (ElementNode) i.next();
			if (child.getType().equals("Address_ID")) {
				obj.setAddress_ID(NumberUtils.parseInt(child.getTextContents()));
			} else if (child.getType().equals("address1")) {
				obj.setAddress1(child.getTextContents());
			} else if (child.getType().equals("address2")) {
				obj.setAddress2(child.getTextContents());
			} else if (child.getType().equals("city")) {
				obj.setCity(child.getTextContents());
			} else if (child.getType().equals("state_ID")) {
				obj.setState_ID(child.getTextContents());
			} else if (child.getType().equals("province")) {
				obj.setProvince(child.getTextContents());
			} else if (child.getType().equals("hotel")) {
				obj.setHotel(child.getTextContents());
			} else if (child.getType().equals("zip")) {
				obj.setZip(child.getTextContents());
			} else if (child.getType().equals("countrycode_ID")) {
				obj.setCountrycode_ID(child.getTextContents());
			} else if (child.getType().equals("homephone")) {
				obj.setHomephone(child.getTextContents());
			} else if (child.getType().equals("workphone")) {
				obj.setWorkphone(child.getTextContents());
			} else if (child.getType().equals("mobile")) {
				obj.setMobile(child.getTextContents());
			} else if (child.getType().equals("pager")) {
				obj.setPager(child.getTextContents());
			} else if (child.getType().equals("altphone")) {
				obj.setAltphone(child.getTextContents());
			} else if (child.getType().equals("email")) {
				obj.setEmail(child.getTextContents());
			} else if (child.getType().equals("valid_bdate")) {
				obj.setValid_bdate(DateUtils.convertToDate(child.getTextContents(),
						TracingConstants.DB_DATEFORMAT, null));
			} else if (child.getType().equals("valid_edate")) {
				obj.setValid_edate(DateUtils.convertToDate(child.getTextContents(),
						TracingConstants.DB_DATEFORMAT, null));
			}

		}

		return obj;
	}

}