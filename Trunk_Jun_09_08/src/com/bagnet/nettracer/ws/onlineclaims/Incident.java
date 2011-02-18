package com.bagnet.nettracer.ws.onlineclaims;

public class Incident {
	private String[] claimCheck;
	private String firstName;
	private String lastName;
	private String pnr;
	private IncidentBag[] bag;
	private String email;
	private IncidentPhone[] phone;
	private int deliveryType;
	private boolean deliverWithoutSignature;
	private IncidentAddress deliveryAddress;
	private IncidentItinerary[] itinerary;
	private int authStatus;
	private int authID;
	private String osi;
	
	public static final int AUTH_FAILURE = 0;
	public static final int AUTH_COMPLETE = 1;
	public static final int AUTH_EXPIRED = 2;
	public static final int AUTH_SUCCESS = 3;
	
	

	public int getAuthID() {
		return authID;
	}

	public void setAuthID(int authID) {
		this.authID = authID;
	}

	public String getOsi() {
		return osi;
	}

	public void setOsi(String osi) {
		this.osi = osi;
	}

	public int getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(int authStatus) {
		this.authStatus = authStatus;
	}

	public IncidentItinerary[] getItinerary() {
		return itinerary;
	}

	public void setItinerary(IncidentItinerary[] itinerary) {
		this.itinerary = itinerary;
	}

	public String[] getClaimCheck() {
		return claimCheck;
	}

	public void setClaimCheck(String[] claimCheck) {
		this.claimCheck = claimCheck;
	}

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

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public IncidentBag[] getBag() {
		return bag;
	}

	public void setBag(IncidentBag[] bag) {
		this.bag = bag;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public IncidentPhone[] getPhone() {
		return phone;
	}

	public void setPhone(IncidentPhone[] phone) {
		this.phone = phone;
	}

	public int getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(int deliveryType) {
		this.deliveryType = deliveryType;
	}

	public boolean isDeliverWithoutSignature() {
		return deliverWithoutSignature;
	}

	public void setDeliverWithoutSignature(boolean deliverWithoutSignature) {
		this.deliverWithoutSignature = deliverWithoutSignature;
	}

	public IncidentAddress getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(IncidentAddress deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

}
