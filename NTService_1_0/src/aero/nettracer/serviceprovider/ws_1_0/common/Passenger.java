package aero.nettracer.serviceprovider.ws_1_0.common;

public class Passenger {
	private String firstname;
	private String middlename;
	private String lastname;
	private String ffAirline;
	private String ffNumber;
	private String ffStatus;
	private Address addresses;
	private int salutation;

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

	public Address getAddresses() {
		return addresses;
	}

	public void setAddresses(Address addresses) {
		this.addresses = addresses;
	}

	public int getSalutation() {
		return salutation;
	}

	public void setSalutation(int salutation) {
		this.salutation = salutation;
	}
}
