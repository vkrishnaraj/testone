package aero.nettracer.lfc.model;

import java.io.Serializable;
import java.math.BigInteger;

public class AddressBean implements Serializable{

	private static final long serialVersionUID = 6759537869024990873L;
	
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String province;
	private String postal;
	private String country;
	private BigInteger score;

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public BigInteger getScore() {
		return score;
	}

	public void setScore(BigInteger score) {
		this.score = score;
	}

}
