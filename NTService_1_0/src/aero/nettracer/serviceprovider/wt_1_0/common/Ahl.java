package aero.nettracer.serviceprovider.wt_1_0.common;

import java.util.Calendar;

public class Ahl {
	private String ahlId;
	private String pnrLocator;
	private Calendar createDate;
	private int numberPaxAffected;
	private int numberBagsChecked;
	private String stationCode;
	private Passenger[] pax;
	private Itinerary[] paxItinerary;
	private Itinerary[] bagItinerary;
	private Item[] item;
	private ClaimCheck[] claimCheck;
	private Agent agent;
	private String airlineCode;
	private Expenses[] expenses;
	private String faultReasonDescription;
	private String faultStation;
	private int faultReason;
	private boolean tracingFinalized;

	public String getFaultReasonDescription() {
		return faultReasonDescription;
	}

	public void setFaultReasonDescription(String faultReasonDescription) {
		this.faultReasonDescription = faultReasonDescription;
	}

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

	public Calendar getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

	public int getNumberBagsChecked() {
		return numberBagsChecked;
	}

	public void setNumberBagsChecked(int numberBagsChecked) {
		this.numberBagsChecked = numberBagsChecked;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public String getPnrLocator() {
		return pnrLocator;
	}

	public void setPnrLocator(String pnrLocator) {
		this.pnrLocator = pnrLocator;
	}

	public String getAirlineCode() {
		return airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	public Expenses[] getExpenses() {
		return expenses;
	}

	public void setExpenses(Expenses[] expenses) {
		this.expenses = expenses;
	}

	public boolean isTracingFinalized() {
		return tracingFinalized;
	}

	public void setTracingFinalized(boolean tracingFinalized) {
		this.tracingFinalized = tracingFinalized;
	}

}
