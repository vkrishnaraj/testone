package com.nettracer.claims.incident;


/**
 * @author Utpal Description: This is needed for binding the user credential
 *         with the front end
 */

public class LoginBean {
	private String confNumber;
	private String lastName;
	private String firstName;
	
	
	public LoginBean() {
	}
	public LoginBean(String confNumber,String lastName, String firstName) {
		this.confNumber = confNumber;
		this.lastName = lastName;
		this.firstName = firstName;
	}
	public String getConfNumber() {
		return confNumber;
	}
	public void setConfNumber(String confNumber) {
		this.confNumber = confNumber;
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
