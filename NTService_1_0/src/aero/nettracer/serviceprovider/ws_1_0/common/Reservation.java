package aero.nettracer.serviceprovider.ws_1_0.common;

public class Reservation {

	private Passenger[] passengers = null;
	private Itinerary[] passengerItinerary = null;
	private Itinerary[] bagItinerary = null;

	// To be used only if the reservation system can not provide data
	// broken down at a per-passenger level which is preferred.
	private ClaimCheck[] claimChecks = null;

	private String pnr = null;
	private int numberChecked = 0;
	private int paxAffected = 0;
	private int checkedLocation = 0;
	private String osi = null;
	private boolean nonrev = false;

	public Passenger[] getPassengers() {
		return passengers;
	}

	public void setPassengers(Passenger[] passengers) {
		this.passengers = passengers;
	}

	public Itinerary[] getPassengerItinerary() {
		return passengerItinerary;
	}

	public void setPassengerItinerary(Itinerary[] passengerItinerary) {
		this.passengerItinerary = passengerItinerary;
	}

	public Itinerary[] getBagItinerary() {
		return bagItinerary;
	}

	public void setBagItinerary(Itinerary[] bagItinerary) {
		this.bagItinerary = bagItinerary;
	}

	public ClaimCheck[] getClaimChecks() {
		return claimChecks;
	}

	public void setClaimChecks(ClaimCheck[] claimChecks) {
		this.claimChecks = claimChecks;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public int getNumberChecked() {
		return numberChecked;
	}

	public void setNumberChecked(int numberChecked) {
		this.numberChecked = numberChecked;
	}

	public int getPaxAffected() {
		return paxAffected;
	}

	public void setPaxAffected(int paxAffected) {
		this.paxAffected = paxAffected;
	}

	public int getCheckedLocation() {
		return checkedLocation;
	}

	public void setCheckedLocation(int checkedLocation) {
		this.checkedLocation = checkedLocation;
	}

	public String getOsi() {
		return osi;
	}

	public void setOsi(String osi) {
		this.osi = osi;
	}

	public boolean isNonrev() {
		return nonrev;
	}

	public void setNonrev(boolean nonrev) {
		this.nonrev = nonrev;
	}

}
