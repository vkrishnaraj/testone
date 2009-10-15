package aero.nettracer.serviceprovider.wt_1_0.common;

public class Ahl {
	private String ahlId;
	private int numberPaxAffected;
	private String stationCode;
	private String faultStation;
	private int faultReason;
	private Passenger[] pax;
	private Itinerary[] paxItinerary;
	private Itinerary[] bagItinerary;
	private Item[] item;
	private ClaimCheck[] claimCheck;

	public String getAhlId() {
		return ahlId;
	}

	public void setAhlId(String ahlId) {
		this.ahlId = ahlId;
	}

	public int getNumberPaxAffected() {
		return numberPaxAffected;
	}

	public void setNumberPaxAffected(int numberPaxAffected) {
		this.numberPaxAffected = numberPaxAffected;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getFaultStation() {
		return faultStation;
	}

	public void setFaultStation(String faultStation) {
		this.faultStation = faultStation;
	}

	public int getFaultReason() {
		return faultReason;
	}

	public void setFaultReason(int faultReason) {
		this.faultReason = faultReason;
	}

	public Passenger[] getPax() {
		return pax;
	}

	public void setPax(Passenger[] pax) {
		this.pax = pax;
	}

	public Itinerary[] getPaxItinerary() {
		return paxItinerary;
	}

	public void setPaxItinerary(Itinerary[] paxItinerary) {
		this.paxItinerary = paxItinerary;
	}

	public Itinerary[] getBagItinerary() {
		return bagItinerary;
	}

	public void setBagItinerary(Itinerary[] bagItinerary) {
		this.bagItinerary = bagItinerary;
	}

	public Item[] getItem() {
		return item;
	}

	public void setItem(Item[] item) {
		this.item = item;
	}

	public ClaimCheck[] getClaimCheck() {
		return claimCheck;
	}

	public void setClaimCheck(ClaimCheck[] claimCheck) {
		this.claimCheck = claimCheck;
	}

}
