package aero.nettracer.security;


public class SsoNode {
	private String companycode;
	private String username;
	private boolean validAssertion;
	private String group;
	private String station;
	private String firstname;
	private String lastname;
	public String getCompanycode() {
		return companycode;
	}
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isValidAssertion() {
		return validAssertion;
	}
	public void setValidAssertion(boolean validAssertion) {
		this.validAssertion = validAssertion;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String toString(){
		String s = "";
		s += "username: " 	+ username	+ "\n";
		s += "station: " 	+ station 	+ "\n";
		s += "group: "	 	+ group 	+ "\n";
		s += "firstname: "	+ firstname + "\n";
		s += "lastname: "   + lastname  + "\n";
		s += "isValid: "    + validAssertion  + "\n";
		return s;

	}
}
