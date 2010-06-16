package com.nettracer.claims.core.model;

import java.util.List;



/**
 * @author Utpal Description: This is needed for binding the Application Data
 *         with the front end
 */

public class Passenger {
	
	
	//Data binding For Passenger Info Page
	private String firstName;
	private String lastName;
	private String middleInitial;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleInitial() {
		return middleInitial;
	}
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}
	
	
	
}
