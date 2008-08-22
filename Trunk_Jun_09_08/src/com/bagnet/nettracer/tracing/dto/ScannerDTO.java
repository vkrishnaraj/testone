package com.bagnet.nettracer.tracing.dto;

import java.util.ArrayList;
import java.util.List;

public class ScannerDTO {
	
	private String errorResponse;
	private List<ScannerDataDTO> scannerDataDTOs = new ArrayList<ScannerDataDTO>();
	
	/**
	 * @return the errorResponse
	 */
	public String getErrorResponse() {
		return errorResponse;
	}
	/**
	 * @param errorResponse the errorResponse to set
	 */
	public void setErrorResponse(String errorResponse) {
		this.errorResponse = errorResponse;
	}
	/**
	 * @return the scannerDataDTOs
	 */
	public List<ScannerDataDTO> getScannerDataDTOs() {
		return scannerDataDTOs;
	}
	/**
	 * @param scannerDataDTOs the scannerDataDTOs to set
	 */
	public void setScannerDataDTOs(List<ScannerDataDTO> scannerDataDTOs) {
		this.scannerDataDTOs = scannerDataDTOs;
	}
	
}
