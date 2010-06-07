package com.nettracer.claims.passenger;


/**
 * @author Utpal Description: This is needed for binding the user credential
 *         with the front end
 */

public class LoginBean {
	private String claimNumber;
	private String lastName;
	
	
	public LoginBean() {
	}
	public LoginBean(String claimNumber,String lastName) {
		this.claimNumber = claimNumber;
		this.lastName = lastName;
	}
	public String getClaimNumber() {
		return claimNumber;
	}
	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	

	

}
