package com.bagnet.clients.demo;

import java.util.ArrayList;
import java.util.Date;

import com.bagnet.nettracer.datasources.ScannerDataSource;
import com.bagnet.nettracer.tracing.dto.ScannerDTO;
import com.bagnet.nettracer.tracing.dto.ScannerDataDTO;

public class ScannerDataSourceImpl implements ScannerDataSource {


	public ScannerDTO getScannerData(Date startDate, Date endDate, String bagTagNumber, int timeout) {

		ScannerDTO newDto = new ScannerDTO();
		ArrayList<ScannerDataDTO> list = new ArrayList<ScannerDataDTO>();
		
		ScannerDataDTO item = new ScannerDataDTO("0123999999", "01/01/2008 10:15AM", "DFW", "Load", "", null);
		list.add(item);
		item = new ScannerDataDTO("0123999999", "01/01/2008 12:15PM", "ATL", "Unload", "", null);
		list.add(item);
		item = new ScannerDataDTO("0123999999", "01/01/2008 12:40PM", "ATL", "On-hand", "This bag was found on the runway having fallen out of a baggage bin prior to being loaded on the next flight.", "ATLDA00000010");
		list.add(item);

		newDto.setScannerDataDTOs(list);
		
		return newDto;
	}
	
	public ScannerDTO getScannerData(Date startDate, Date endDate, String bagTagNumber) {

		ScannerDTO newDto = new ScannerDTO();
		ArrayList<ScannerDataDTO> list = new ArrayList<ScannerDataDTO>();
		
		ScannerDataDTO item = new ScannerDataDTO("0123999999", "01/01/2008 10:15AM", "DFW", "Load", "", null);
		list.add(item);
		item = new ScannerDataDTO("0123999999", "01/01/2008 12:15PM", "ATL", "Unload", "", null);
		list.add(item);
		item = new ScannerDataDTO("0123999999", "01/01/2008 12:40PM", "ATL", "On-hand", "This bag was found on the runway having fallen out of a baggage bin prior to being loaded on the next flight.", "ATLDA00000010");
		list.add(item);

		newDto.setScannerDataDTOs(list);
		
		return newDto;
	}

}
