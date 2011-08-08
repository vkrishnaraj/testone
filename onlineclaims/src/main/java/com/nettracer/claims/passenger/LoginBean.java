package com.nettracer.claims.passenger;

/**
 * @author Utpal Description: This is needed for binding the user credential
 *         with the front end
 */

public class LoginBean {
	private String claimNumber;
	private String lastName;
	private String firstName;

	public LoginBean() {
	}

	public LoginBean(String claimNumber, String lastName) {
		this.claimNumber = claimNumber;
		this.lastName = lastName;
	}

	public LoginBean(String claimNumber, String lastName, String firstName) {
		this.claimNumber = claimNumber;
		this.lastName = lastName;
		this.firstName = firstName;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
