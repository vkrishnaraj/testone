package com.bagnet.nettracer.tracing.adapter;

/**
 * The TemplateAdapter class is used to supply normalized data from Incidents and FsClaims 
 * to Document objects in order to generate printable files for the user based on system data 
 * and Templates that have been defined in the system.
 * @author Mike
 *
 */
public interface TemplateAdapter {
	public String getAddressAddress1();
	public void setAddressAddress1(String address1);
	public String getAddressAddress2();
	public void setAddressAddress2(String address2);
	public String getAddressCity();
	public void setAddressCity(String city);
	public String getAddressState();
	public void setAddressState(String state);
	public String getAddressZip();
	public void setAddressZip(String zip);
	public String getAgentFirstName();
	public void setAgentFirstName(String agentFirstName);
	public String getAgentLastName();
	public void setAgentLastName(String agentLastName);
	public String getAgentInitials();
	public void setAgentInitials(String agentInitials);
	public String getClaimId();
	public void setClaimId(String claimId);
	public String getClaimType();
	public void setClaimType(String claimType);
	public String getIncidentId();
	public void setIncidentId(String incidentId);
	public String getIncidentType();
	public void setIncidentType(String incidentType);
	public String getPassengerFirstName();
	public void setPassengerFirstName(String passengerFirstName);
	public String getPassengerLastName();
	public void setPassengerLastName(String passengerLastName);
	public void setDateFormat(String dateFormat);
	public String getDateToday();
}
