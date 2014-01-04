package aero.nettracer.portal.model;

import java.util.Calendar;

public class PaxViewItem {

	private String address1;
	private String address2;
	private String bagstatus;
	private String claimchecknum;
	private String city;
	private String stateID;
	private String zip;
	private Calendar lastDeliveryUpdate;
	private String deliveryStatus;
	private String incidentStatus;
	private boolean claimchecknumavail;

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getBagstatus() {
		if (incidentStatus != null && incidentStatus.equals("Closed")) {
			return "Closed";
		}
		return bagstatus;
	}

	public void setBagstatus(String bagstatus) {
		this.bagstatus = bagstatus;
	}

	public String getClaimchecknum() {
		return claimchecknum;
	}

	public void setClaimchecknum(String claimchecknum) {
		this.claimchecknum = claimchecknum;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateID() {
		return stateID;
	}

	public void setStateID(String stateID) {
		this.stateID = stateID;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Calendar getLastDeliveryUpdate() {
		return lastDeliveryUpdate;
	}

	public void setLastDeliveryUpdate(Calendar lastDeliveryUpdate) {
		this.lastDeliveryUpdate = lastDeliveryUpdate;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getIncidentStatus() {
		return incidentStatus;
	}

	public void setIncidentStatus(String incidentStatus) {
		this.incidentStatus = incidentStatus;
	}

	public boolean isClaimchecknumavail() {
		return (claimchecknum != null && claimchecknum.trim().length() > 0);
	}

	public void setClaimchecknumavail(boolean claimchecknumavail) {
		this.claimchecknumavail = claimchecknumavail;
	}

}
