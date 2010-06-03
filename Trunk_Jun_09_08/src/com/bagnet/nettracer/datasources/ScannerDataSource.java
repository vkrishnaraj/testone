package com.bagnet.nettracer.datasources;

import java.util.Date;

import com.bagnet.nettracer.tracing.dto.ScannerDTO;

public interface ScannerDataSource {

	public ScannerDTO getScannerData(Date startDate, Date endDate, String bagTagNumber);
	public ScannerDTO getScannerData(Date startDate, Date endDate, String bagTagNumber, int timeout);
	
}
