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
	public String getAgentLastName();
	public String getAgentInitials();

	// Claim data
	public String getClaimId();
	public String getClaimType();
	public String getClaimFirstName();
	public String getClaimLastName();
	public String getClaimFullAddress();
	public String getClaimAddress1();
	public String getClaimAddress2();
	public String getClaimCity();
	public String getClaimState();
	public String getClaimProvince();
	public String getClaimZip();
	public String getClaimCountry();
	public String getClaimHomePhone();
	public String getClaimBusinessPhone();
	public String getClaimMobilePhone();
	
	// Found Item data
	public String getFoundItemId();
	public String getFoundItemType();
	public String getFoundItemColor();
	public String getFoundItemItem();
	public String getFoundItemDescription();
	public String getFoundItemCaseColor();
	public String getFoundItemFirstName();
	public String getFoundItemLastName();
	public String getFoundItemFullAddress();
	public String getFoundItemAddress1();
	public String getFoundItemAddress2();
	public String getFoundItemCity();
	public String getFoundItemState();
	public String getFoundItemProvince();
	public String getFoundItemCountry();
	public String getFoundItemZip();
	public String getFoundItemHomePhone();
	public String getFoundItemBusinessPhone();
	public String getFoundItemMobilePhone();
	
	// Incident data
	public String getIncidentId();
	public String getIncidentType();
	public String getIncidentFirstName();
	public String getIncidentLastName();
	public String getIncidentFullAddress();
	public String getIncidentAddress1();
	public String getIncidentAddress2();
	public String getIncidentCity();
	public String getIncidentState();
	public String getIncidentProvince();
	public String getIncidentCountry();
	public String getIncidentZip();
	public String getIncidentHomePhone();
	public String getIncidentBusinessPhone();
	public String getIncidentMobilePhone();
	public String getIncidentSalutation();
	
	// Issuance Item Data
	public String getIssuanceItemBarcode();
	
	// Expense Data
	public String getExpenseTotalAmount();
	
	// Generic data
	public String getDateToday();
}
