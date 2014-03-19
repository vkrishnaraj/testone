package com.bagnet.nettracer.tracing.adapter.impl;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.adapter.TemplateAdapter;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public class TemplateAdapterImpl implements TemplateAdapter {
	
	private Logger logger = Logger.getLogger(TemplateAdapterImpl.class);
	
	private String dateFormat;
	
	// Agent info
	private String agentFirstName;
	private String agentLastName;
	private String agentInitials;
	
	// Claim info
	private String claimId;
	private String claimType;
	private String claimFirstName;
	private String claimLastName;
	private String claimAddress1;
	private String claimAddress2;
	private String claimCity;
	private String claimState;
	private String claimProvince;
	private String claimZip;
	private String claimCountry;
	private String claimHomePhone;
	private String claimBusinessPhone;
	private String claimMobilePhone;
	
	// Found Item info
	private String foundItemId;
	private String foundItemType;
	private String foundItemColor;
	private String foundItemItem;
	private String foundItemDescription;
	private String foundItemCaseColor;
	private String foundItemFirstName;
	private String foundItemLastName;
	private String foundItemAddress1;
	private String foundItemAddress2;
	private String foundItemCity;
	private String foundItemState;
	private String foundItemProvince;
	private String foundItemCountry;
	private String foundItemZip;
	private String foundItemHomePhone;
	private String foundItemBusinessPhone;
	private String foundItemMobilePhone;

	// Incident info
	private String incidentId;
	private String incidentType;
	private String incidentFirstName;
	private String incidentLastName;
	private String incidentAddress1;
	private String incidentAddress2;
	private String incidentCity;
	private String incidentState;
	private String incidentProvince;
	private String incidentCountry;
	private String incidentZip;
	private String incidentHomePhone;
	private String incidentBusinessPhone;
	private String incidentMobilePhone;
	private String incidentSalutation;
	
	// Expense info
	private String expenseTotalAmount;
	
	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getAgentFirstName() {
		return agentFirstName;
	}

	public void setAgentFirstName(String agentFirstName) {
		this.agentFirstName = agentFirstName;
	}

	public String getAgentLastName() {
		return agentLastName;
	}

	public void setAgentLastName(String agentLastName) {
		this.agentLastName = agentLastName;
	}

	public String getAgentInitials() {
		return agentInitials;
	}

	public void setAgentInitials(String agentInitials) {
		this.agentInitials = agentInitials;
	}

	public String getClaimId() {
		return claimId;
	}

	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}

	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public String getClaimFirstName() {
		return claimFirstName;
	}

	public void setClaimFirstName(String claimFirstName) {
		this.claimFirstName = claimFirstName;
	}

	public String getClaimLastName() {
		return claimLastName;
	}

	public void setClaimLastName(String claimLastName) {
		this.claimLastName = claimLastName;
	}

	public String getClaimFullAddress() {
		return getFullAddress("Claim");
	}

	public String getClaimAddress1() {
		return claimAddress1;
	}

	public void setClaimAddress1(String claimAddress1) {
		this.claimAddress1 = claimAddress1;
	}

	public String getClaimAddress2() {
		return claimAddress2;
	}

	public void setClaimAddress2(String claimAddress2) {
		this.claimAddress2 = claimAddress2;
	}

	public String getClaimCity() {
		return claimCity;
	}

	public void setClaimCity(String claimCity) {
		this.claimCity = claimCity;
	}

	public String getClaimState() {
		return claimState;
	}

	public void setClaimState(String claimState) {
		this.claimState = claimState;
	}

	public String getClaimProvince() {
		return claimProvince;
	}

	public void setClaimProvince(String claimProvince) {
		this.claimProvince = claimProvince;
	}

	public String getClaimZip() {
		return claimZip;
	}

	public void setClaimZip(String claimZip) {
		this.claimZip = claimZip;
	}

	public String getClaimCountry() {
		return claimCountry;
	}

	public void setClaimCountry(String claimCountry) {
		this.claimCountry = claimCountry;
	}

	public String getClaimHomePhone() {
		return claimHomePhone;
	}

	public void setClaimHomePhone(String claimHomePhone) {
		this.claimHomePhone = claimHomePhone;
	}

	public String getClaimBusinessPhone() {
		return claimBusinessPhone;
	}

	public void setClaimBusinessPhone(String claimBusinessPhone) {
		this.claimBusinessPhone = claimBusinessPhone;
	}

	public String getClaimMobilePhone() {
		return claimMobilePhone;
	}

	public void setClaimMobilePhone(String claimMobilePhone) {
		this.claimMobilePhone = claimMobilePhone;
	}

	public String getFoundItemId() {
		return foundItemId;
	}

	public void setFoundItemId(String foundItemId) {
		this.foundItemId = foundItemId;
	}

	public String getFoundItemType() {
		return foundItemType;
	}

	public void setFoundItemType(String foundItemType) {
		this.foundItemType = foundItemType;
	}

	public String getFoundItemColor() {
		return foundItemColor;
	}

	public void setFoundItemColor(String foundItemColor) {
		this.foundItemColor = foundItemColor;
	}

	public String getFoundItemItem() {
		return foundItemItem;
	}

	public void setFoundItemItem(String foundItemItem) {
		this.foundItemItem = foundItemItem;
	}

	public String getFoundItemDescription() {
		return foundItemDescription;
	}

	public void setFoundItemDescription(String foundItemDescription) {
		this.foundItemDescription = foundItemDescription;
	}

	public String getFoundItemCaseColor() {
		return foundItemCaseColor;
	}

	public void setFoundItemCaseColor(String foundItemCaseColor) {
		this.foundItemCaseColor = foundItemCaseColor;
	}

	public String getFoundItemFirstName() {
		return foundItemFirstName;
	}

	public void setFoundItemFirstName(String foundItemFirstName) {
		this.foundItemFirstName = foundItemFirstName;
	}

	public String getFoundItemLastName() {
		return foundItemLastName;
	}

	public void setFoundItemLastName(String foundItemLastName) {
		this.foundItemLastName = foundItemLastName;
	}
	
	public String getFoundItemFullAddress(){
		return getFullAddress("FoundItem");
	}

	public String getFoundItemAddress1() {
		return foundItemAddress1;
	}

	public void setFoundItemAddress1(String foundItemAddress1) {
		this.foundItemAddress1 = foundItemAddress1;
	}

	public String getFoundItemAddress2() {
		return foundItemAddress2;
	}

	public void setFoundItemAddress2(String foundItemAddress2) {
		this.foundItemAddress2 = foundItemAddress2;
	}

	public String getFoundItemCity() {
		return foundItemCity;
	}

	public void setFoundItemCity(String foundItemCity) {
		this.foundItemCity = foundItemCity;
	}

	public String getFoundItemState() {
		return foundItemState;
	}

	public void setFoundItemState(String foundItemState) {
		this.foundItemState = foundItemState;
	}

	public String getFoundItemProvince() {
		return foundItemProvince;
	}

	public void setFoundItemProvince(String foundItemProvince) {
		this.foundItemProvince = foundItemProvince;
	}

	public String getFoundItemCountry() {
		return foundItemCountry;
	}

	public void setFoundItemCountry(String foundItemCountry) {
		this.foundItemCountry = foundItemCountry;
	}

	public String getFoundItemZip() {
		return foundItemZip;
	}

	public void setFoundItemZip(String foundItemZip) {
		this.foundItemZip = foundItemZip;
	}

	public String getFoundItemHomePhone() {
		return foundItemHomePhone;
	}

	public void setFoundItemHomePhone(String foundItemHomePhone) {
		this.foundItemHomePhone = foundItemHomePhone;
	}

	public String getFoundItemBusinessPhone() {
		return foundItemBusinessPhone;
	}

	public void setFoundItemBusinessPhone(String foundItemBusinessPhone) {
		this.foundItemBusinessPhone = foundItemBusinessPhone;
	}

	public String getFoundItemMobilePhone() {
		return foundItemMobilePhone;
	}

	public void setFoundItemMobilePhone(String foundItemMobilePhone) {
		this.foundItemMobilePhone = foundItemMobilePhone;
	}

	public String getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}

	public String getIncidentType() {
		return incidentType;
	}

	public void setIncidentType(String incidentType) {
		this.incidentType = incidentType;
	}

	public String getIncidentFirstName() {
		return incidentFirstName;
	}

	public void setIncidentFirstName(String incidentFirstName) {
		this.incidentFirstName = incidentFirstName;
	}

	public String getIncidentLastName() {
		return incidentLastName;
	}

	public void setIncidentLastName(String incidentLastName) {
		this.incidentLastName = incidentLastName;
	}
	
	public String getIncidentFullAddress(){
		return this.getFullAddress("Incident");
	}

	public String getIncidentAddress1() {
		return incidentAddress1;
	}

	public void setIncidentAddress1(String incidentAddress1) {
		this.incidentAddress1 = incidentAddress1;
	}

	public String getIncidentAddress2() {
		return incidentAddress2;
	}

	public void setIncidentAddress2(String incidentAddress2) {
		this.incidentAddress2 = incidentAddress2;
	}

	public String getIncidentCity() {
		return incidentCity;
	}

	public void setIncidentCity(String incidentCity) {
		this.incidentCity = incidentCity;
	}

	public String getIncidentState() {
		return incidentState;
	}

	public void setIncidentState(String incidentState) {
		this.incidentState = incidentState;
	}

	public String getIncidentProvince() {
		return incidentProvince;
	}

	public void setIncidentProvince(String incidentProvince) {
		this.incidentProvince = incidentProvince;
	}

	public String getIncidentCountry() {
		return incidentCountry;
	}

	public void setIncidentCountry(String incidentCountry) {
		this.incidentCountry = incidentCountry;
	}

	public String getIncidentZip() {
		return incidentZip;
	}

	public void setIncidentZip(String incidentZip) {
		this.incidentZip = incidentZip;
	}

	public String getIncidentHomePhone() {
		return incidentHomePhone;
	}

	public void setIncidentHomePhone(String incidentHomePhone) {
		this.incidentHomePhone = incidentHomePhone;
	}

	public String getIncidentBusinessPhone() {
		return incidentBusinessPhone;
	}

	public void setIncidentBusinessPhone(String incidentBusinessPhone) {
		this.incidentBusinessPhone = incidentBusinessPhone;
	}

	public String getIncidentMobilePhone() {
		return incidentMobilePhone;
	}

	public void setIncidentMobilePhone(String incidentMobilePhone) {
		this.incidentMobilePhone = incidentMobilePhone;
	}

	public String getIncidentSalutation() {
		return incidentSalutation;
	}

	public void setIncidentSalutation(String incidentSalutation) {
		this.incidentSalutation = incidentSalutation;
	}

	public String getDateToday() {
		return DateUtils.formatDate(new Date(), this.dateFormat, null, null);
	}

	public String getExpenseTotalAmount() {
		return expenseTotalAmount;
	}

	public void setExpenseTotalAmount(String expenseTotalAmount) {
		this.expenseTotalAmount = expenseTotalAmount;
	}

	private String getFullAddress(String className){
		StringBuilder fullAddress = new StringBuilder();
		
		String value = invokeMethod("get" + className + "Address1");
		if(value != null && !value.isEmpty()){
			fullAddress.append(value+"<br/>");
		}
		value = invokeMethod("get" + className + "Address2");
		if(value != null && !value.isEmpty()){
			fullAddress.append(value+"<br/>");
		}
		value = invokeMethod("get" + className + "City");
		if(value != null && !value.isEmpty()){
			fullAddress.append(value+", ");
		}
		value = invokeMethod("get" + className + "State");
		if(value != null && !value.isEmpty()){
			fullAddress.append(value+". ");
		}
		value = invokeMethod("get" + className + "Zip");
		if(value != null && !value.isEmpty()){
			fullAddress.append(value);
		}
		value = invokeMethod("get" + className + "Country");
		if(value != null && !value.isEmpty()){
			fullAddress.append("<br/>"+value);
		}
		
		return !fullAddress.toString().isEmpty() ? fullAddress.toString() : null;
	}
	
	private String invokeMethod(String methodName) {
		String result = null;
		try {
			Method getter = this.getClass().getDeclaredMethod(methodName, new Class[] { });
			result = (String) getter.invoke(this, new Object[] { });
		} catch (Exception e) {
			logger.error(e);
		}
		return result;
	}

}
