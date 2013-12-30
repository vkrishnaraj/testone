package com.bagnet.nettracer.tracing.adapter;

/**
 * The TemplateAdapter class is used to supply normalized data from Incidents and FsClaims 
 * to Document objects in order to generate printable files for the user based on system data 
 * and Templates that have been defined in the system.
 * @author Mike
 *
 */
public interface TemplateAdapter {
	// Agent data
	public String getAgentFirstName();
	public void setAgentFirstName(String agentFirstName);
	public String getAgentLastName();
	public void setAgentLastName(String agentLastName);
	public String getAgentInitials();
	public void setAgentInitials(String agentInitials);

	// Claim data
	public String getClaimId();
	public void setClaimId(String claimId);
	public String getClaimType();
	public void setClaimType(String claimType);
	public String getClaimFirstName();
	public void setClaimFirstName(String claimFirstName);
	public String getClaimLastName();
	public void setClaimLastName(String claimLastName);
	public String getClaimAddress1();
	public void setClaimAddress1(String claimAddress1);
	public String getClaimAddress2();
	public void setClaimAddress2(String claimAddress2);
	public String getClaimCity();
	public void setClaimCity(String claimCity);
	public String getClaimState();
	public void setClaimState(String claimState);
	public String getClaimZip();
	public void setClaimZip(String claimZip);
	public String getClaimHomePhone();
	public void setClaimHomePhone(String claimHomePhone);
	public String getClaimBusinessPhone();
	public void setClaimBusinessPhone(String claimBusinessPhone);
	public String getClaimMobilePhone();
	public void setClaimMobilePhone(String claimHomePhone);
	
	// Found Item data
	public String getFoundItemId();
	public void setFoundItemId(String foundItemId);
	public String getFoundItemType();
	public void setFoundItemType(String foundItemType);
	public String getFoundItemColor();
	public void setFoundItemColor(String foundItemColor);
	public String getFoundItemItem();
	public void setFoundItemItem(String foundItemItem);
	public String getFoundItemDescription();
	public void setFoundItemDescription(String foundItemDescription);
	public String getFoundItemCaseColor();
	public void setFoundItemCaseColor(String foundItemCaseColor);
	public String getFoundItemFirstName();
	public void setFoundItemFirstName(String foundItemFirstName);
	public String getFoundItemLastName();
	public void setFoundItemLastName(String foundItemLastName);
	public String getFoundItemAddress1();
	public void setFoundItemAddress1(String foundItemAddress1);
	public String getFoundItemAddress2();
	public void setFoundItemAddress2(String foundItemAddress2);
	public String getFoundItemCity();
	public void setFoundItemCity(String foundItemCity);
	public String getFoundItemState();
	public void setFoundItemState(String foundItemState);
	public String getFoundItemZip();
	public void setFoundItemZip(String foundItemZip);
	public String getFoundItemHomePhone();
	public void setFoundItemHomePhone(String foundItemHomePhone);
	public String getFoundItemBusinessPhone();
	public void setFoundItemBusinessPhone(String foundItemBusinessPhone);
	public String getFoundItemMobilePhone();
	public void setFoundItemMobilePhone(String foundItemHomePhone);
	
	// Incident data
	public String getIncidentId();
	public void setIncidentId(String incidentId);
	public String getIncidentType();
	public void setIncidentType(String incidentType);
	public String getIncidentFirstName();
	public void setIncidentFirstName(String incidentFirstName);
	public String getIncidentLastName();
	public void setIncidentLastName(String incidentLastName);
	public String getIncidentAddress1();
	public void setIncidentAddress1(String incidentAddress1);
	public String getIncidentAddress2();
	public void setIncidentAddress2(String incidentAddress2);
	public String getIncidentCity();
	public void setIncidentCity(String incidentCity);
	public String getIncidentState();
	public void setIncidentState(String incidentState);
	public String getIncidentZip();
	public void setIncidentZip(String incidentZip);
	public String getIncidentHomePhone();
	public void setIncidentHomePhone(String incidentHomePhone);
	public String getIncidentBusinessPhone();
	public void setIncidentBusinessPhone(String incidentBusinessPhone);
	public String getIncidentMobilePhone();
	public void setIncidentMobilePhone(String incidentHomePhone);
	
	// Expense Data
	public String getExpenseTotalAmount();
	public void setExpenseTotalAmount(String expenseTotalAmount);
	
	// Generic data
	public void setDateFormat(String dateFormat);
	public String getDateToday();
}
