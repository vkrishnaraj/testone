package com.bagnet.nettracer.ws.onlineclaims;



public class OnlineClaimsService {

	/*
	 * Authenticate Administrative User
	 */
	public boolean authAdminUser(String username, String password, NtAuth auth) {
		return true;
	}
	
	/*
	 * Authenticate Passenger
	 */
	public PassengerView authPassenger(String passengerLastName, String incidentId, NtAuth auth) {
		return null;
	}
	
	/*
	 * Load Claim
	 */
	public Claim loadClaim(String name, String incidentId, long claimId, NtAuth auth) {
		return null;
	}
	
	/*
	 * Save Claim
	 */
	public boolean saveClaim(String name, String incidentId, Claim claim, NtAuth auth) {
		return true;
	}
}