package aero.nettracer.serviceprovider.wt_1_0.common;

public class Passenger {

	private int salutation;
	private String firstname;
	private String middlename;
	private String lastname;
	private String ffAirline;
	private String ffNumber;
	private String ffStatus;
	private Address address;
	private String initials;
	private String languageFreeFlow;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFfAirline() {
		return ffAirline;
	}

	public void setFfAirline(String ffAirline) {
		this.ffAirline = ffAirline;
	}

	public String getFfNumber() {
		return ffNumber;
	}

	public void setFfNumber(String ffNumber) {
		this.ffNumber = ffNumber;
	}

	public String getFfStatus() {
		return ffStatus;
	}

	public void setFfStatus(String ffStatus) {
		this.ffStatus = ffStatus;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public Address getAddress() {
  	return address;
  }

	public void setAddress(Address address) {
  	this.address = address;
  }

	public int getSalutation() {
  	return salutation;
  }

	public void setSalutation(int salutation) {
  	this.salutation = salutation;
  }
	
	public String getSalutationDescription() {
		switch (salutation) {
		case 10: return null;
			
		}
		return null;
	}

	public String getLanguageFreeFlow() {
		return languageFreeFlow;
	}

	public void setLanguageFreeFlow(String languageFreeFlow) {
		this.languageFreeFlow = languageFreeFlow;
	}

}
