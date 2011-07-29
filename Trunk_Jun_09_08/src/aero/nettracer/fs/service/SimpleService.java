package aero.nettracer.fs.service;

import aero.nettracer.fs.service.objects.Authentication;
import aero.nettracer.fs.service.objects.File;
import aero.nettracer.fs.service.objects.SimpleResponse;

public class SimpleService {
	public SimpleResponse submitClaim(int version,
			Authentication authentication, File data) {
		return null;
	}

	public SimpleResponse updateClaimStatus(int version,
			Authentication authentication, long fileId, double amountPaid,
			String amountPaidCurrency, int resolutionStatus,
			int resolutionSubstatus) {
		return null;
	}

	public String echo(String s) {
		return "echo: " + s;
	}
}
