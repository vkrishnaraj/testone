package aero.nettracer.vendor.delivery.data;

import java.util.Date;

public class Bdo {

	private String incidentReference;
	private String vendorReference;
	private int bdoNumber;
	private String airportCode;
	private String airlineCode;
	private String lastName;
	private String firstName;
	private String emailAddress;
	private String localPhone;
	private String permanentPhone;
	private String mobilePhone;
	private String faxPhone;
	private String textMsgPhone;
	private String address_line1;
	private String address_line2;
	private String county;
	private String city;
	private String stateProvince;
	private String postalCode;
	private int numberOfPassangers;
	private float estimatedCost;
	private String deliveryRemarks;
	private Date createdDate;
	private Date pickUpDate;
	private Item[] itemData;
	private String[] tagNumber;
	private String vendorImportCode;
	private String serviceLevel;

	public String getIncidentReference() {
		return incidentReference;
	}

	public void setIncidentReference(String incidentReference) {
		this.incidentReference = incidentReference;
	}

	public int getBdoNumber() {
		return bdoNumber;
	}

	public void setBdoNumber(int bdoNumber) {
		this.bdoNumber = bdoNumber;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getAirlineCode() {
		return airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
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

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getLocalPhone() {
		return localPhone;
	}

	public void setLocalPhone(String localPhone) {
		this.localPhone = localPhone;
	}

	public String getPermanentPhone() {
		return permanentPhone;
	}

	public void setPermanentPhone(String permanentPhone) {
		this.permanentPhone = permanentPhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getFaxPhone() {
		return faxPhone;
	}

	public void setFaxPhone(String faxPhone) {
		this.faxPhone = faxPhone;
	}

	public String getTextMsgPhone() {
		return textMsgPhone;
	}

	public void setTextMsgPhone(String textMsgPhone) {
		this.textMsgPhone = textMsgPhone;
	}

	public String getAddress_line1() {
		return address_line1;
	}

	public void setAddress_line1(String address_line1) {
		this.address_line1 = address_line1;
	}

	public String getAddress_line2() {
		return address_line2;
	}

	public void setAddress_line2(String address_line2) {
		this.address_line2 = address_line2;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public int getNumberOfPassangers() {
		return numberOfPassangers;
	}

	public void setNumberOfPassangers(int numberOfPassangers) {
		this.numberOfPassangers = numberOfPassangers;
	}

	public String getDeliveryRemarks() {
		return deliveryRemarks;
	}

	public void setDeliveryRemarks(String deliveryRemarks) {
		this.deliveryRemarks = deliveryRemarks;
	}

	public Item[] getItemData() {
		return itemData;
	}

	public void setItemData(Item[] itemData) {
		this.itemData = itemData;
	}

	public String[] getTagNumber() {
		return tagNumber;
	}

	public void setTagNumber(String[] tagNumber) {
		this.tagNumber = tagNumber;
	}

	public String getVendorImportCode() {
		return vendorImportCode;
	}

	public void setVendorImportCode(String vendorImportCode) {
		this.vendorImportCode = vendorImportCode;
	}

	public String getVendorReference() {
		return vendorReference;
	}

	public void setVendorReference(String vendorReference) {
		this.vendorReference = vendorReference;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getPickUpDate() {
		return pickUpDate;
	}

	public void setPickUpDate(Date pickUpDate) {
		this.pickUpDate = pickUpDate;
	}

	public void setEstimatedCost(float estimatedCost) {
		this.estimatedCost = estimatedCost;
	}

	public float getEstimatedCost() {
		return estimatedCost;
	}

	public String getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}
}
