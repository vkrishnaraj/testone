package com.bagnet.clients.defaul;

import java.util.Date;

import com.bagnet.nettracer.datasources.ScannerDataSource;
import com.bagnet.nettracer.tracing.dto.ScannerDTO;

public class ScannerDataSourceImpl implements ScannerDataSource {

	public ScannerDTO getScannerData(Date startDate, Date endDate, String bagTagNumber, int timeout) {

		ScannerDTO retDto = new ScannerDTO();
		retDto.setErrorResponse("scanner.error.default");
		return retDto;
	}
	
	public ScannerDTO getScannerData(Date startDate, Date endDate, String bagTagNumber) {

		ScannerDTO retDto = new ScannerDTO();
		retDto.setErrorResponse("scanner.error.default");
		return retDto;
	}

}
