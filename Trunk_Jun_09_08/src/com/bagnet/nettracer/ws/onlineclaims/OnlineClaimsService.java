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
	
	/*
	 * Image Functions
	 */
	public File uploadFile(String filename, byte[] file, NtAuth auth) {
		return null;
	}
	
//	public byte[] retrieveFile(long claimId, long fileId) {
//		return null;
//	}
	
	public boolean deleteFile(long claimId, long fileId, NtAuth auth) {
		return true;
	}
}