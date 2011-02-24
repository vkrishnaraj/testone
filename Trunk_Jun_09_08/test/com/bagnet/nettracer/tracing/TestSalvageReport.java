package com.bagnet.nettracer.tracing;

import org.junit.Test;

import com.bagnet.nettracer.tracing.bmo.ReportBMO;


public class TestSalvageReport {
	
	@Test
	public void testCreateSalvageReport() {
		ReportBMO rBmo = new ReportBMO(null);
		rBmo.createSalvageReport("C:\\", 13, null);
	}

}
