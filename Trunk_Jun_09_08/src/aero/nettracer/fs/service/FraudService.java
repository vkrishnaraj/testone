package aero.nettracer.fs.service;

import aero.nettracer.fs.service.objects.Authentication;
import aero.nettracer.fs.service.objects.File;
import aero.nettracer.fs.service.objects.ClaimResponse;

public class FraudService {
	public ClaimResponse submitClaim(int version,
			Authentication authentication, File data) {
		return null;
	}

	public ClaimResponse updateClaimStatus(int version,
			Authentication authentication, long fileId, double amountPaid,
			String amountPaidCurrency, int resolutionStatus,
			int resolutionSubstatus){
		return null;
	}
	
	public String echo(String s) {
		return "echo: " + s;
	}
}
