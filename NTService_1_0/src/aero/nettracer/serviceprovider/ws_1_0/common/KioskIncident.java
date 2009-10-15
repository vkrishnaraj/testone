package aero.nettracer.serviceprovider.ws_1_0.common;

public class KioskIncident {
	private KioskItinerary[] bagItinerary = null;
	private KioskClaimCheck[] claimChecks = null;
	private KioskItem[] items = null;
	private KioskPassenger[] passengers = null;
	private KioskItinerary[] paxItinerary = null;
	private String pnrLocator = null;
	private String stationReported = null;
	private String paxLanguage = null;

	public String getPaxLanguage() {
		return paxLanguage;
	}

	public void setPaxLanguage(String paxLanguage) {
		this.paxLanguage = paxLanguage;
	}

	public KioskItinerary[] getBagItinerary() {
		return bagItinerary;
	}

	public KioskClaimCheck[] getClaimChecks() {
		return claimChecks;
	}
	public KioskItem[] getItems() {
		return items;
	}
	public KioskPassenger[] getPassengers() {
		return passengers;
	}

	public KioskItinerary[] getPaxItinerary() {
		return paxItinerary;
	}

	public String getPnrLocator() {
		return pnrLocator;
	}

	public String getStationReported() {
		return stationReported;
	}

	public void setBagItinerary(KioskItinerary[] bagItinerary) {
		this.bagItinerary = bagItinerary;
	}

	public void setClaimChecks(KioskClaimCheck[] claimChecks) {
		this.claimChecks = claimChecks;
	}

	public void setItems(KioskItem[] items) {
		this.items = items;
	}

	public void setPassengers(KioskPassenger[] passengers) {
		this.passengers = passengers;
	}

	public void setPaxItinerary(KioskItinerary[] paxItinerary) {
		this.paxItinerary = paxItinerary;
	}

	public void setPnrLocator(String pnrLocator) {
		this.pnrLocator = pnrLocator;
	}

	public void setStationReported(String stationReported) {
		this.stationReported = stationReported;
	}
}
