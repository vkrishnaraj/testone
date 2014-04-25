package com.bagnet.clients.b6;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.bagnet.clients.us.ScannerComparator;
import com.bagnet.nettracer.datasources.ScannerDataSource;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dto.ScannerDTO;
import com.bagnet.nettracer.tracing.dto.ScannerDataDTO;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.brocksolutions.www.ArrayOfBagTransactionResult;
import com.brocksolutions.www.BagLookupResponseDocument;
import com.brocksolutions.www.BagTransactionResult;
import com.brocksolutions.www.BaggageServiceStub;
import com.brocksolutions.www.BagLookupDocument;
import com.brocksolutions.www.LookupRequest;

import edu.emory.mathcs.backport.java.util.Collections;

public class ScannerDataSourceImpl implements ScannerDataSource {
	private static final Logger logger = Logger.getLogger(ScannerDataSourceImpl.class);

	public ScannerDTO getScannerData(Date startDate, Date endDate, String bagTagNumber) {
		return getScannerData(startDate, endDate, bagTagNumber, 120);
	}

	public ScannerDTO getScannerData(Date startDate, Date endDate, String bagTagNumber, int timeout) {
		BaggageServiceStub stub = null;
		ScannerDTO newDto = new ScannerDTO();
		String endpoint = PropertyBMO.getValue(PropertyBMO.PROPERTY_SCAN_HISTORY_ENDPOINT);
		try {
			logger.debug("Endpoint: " + endpoint);
			stub = new BaggageServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error("AxisFault thrown with Bag Request: endpoint = " + endpoint, e);
			return newDto;
		}
		
		Options options = stub._getServiceClient().getOptions();
		options.setProperty(HTTPConstants.CHUNKED, Boolean.FALSE);
		options.setProperty(HTTPConstants.SO_TIMEOUT, new Integer(timeout*1000));
		options.setProperty(HTTPConstants.CONNECTION_TIMEOUT, new Integer(timeout*1000));		

		BagLookupDocument scanDoc = BagLookupDocument.Factory.newInstance();
		BagLookupDocument.BagLookup bagLookup = scanDoc.addNewBagLookup();
		
		LookupRequest request = bagLookup.addNewRequest();
		request.setBagTagNumber(StringUtils.stripToEmpty(bagTagNumber).toUpperCase());
		
		DateFormat dateFormatRequest = new SimpleDateFormat(TracingConstants.DISPLAY_DATEFORMAT, Locale.US);
		request.setStartDate(dateFormatRequest.format(startDate));
		request.setEndDate(dateFormatRequest.format(endDate));
				
		BagLookupResponseDocument responseDoc = null; 
		try {
			logger.debug("Scanner request: \r" + scanDoc); 
			responseDoc = stub.bagLookup(scanDoc);
		} catch (RemoteException e) {
			logger.error("RemoteException thrown with Bag Request: bagTagNumber = " + bagTagNumber, e);
			newDto.setErrorResponse("scanner.communicationError");
			return newDto;
		} catch (Exception e){
			logger.error("Exception thrown with Bag Request: bagTagNumber = " + bagTagNumber, e);
			newDto.setErrorResponse("scanner.communicationError.unknownerror");
			return newDto;
		}

		ArrayOfBagTransactionResult bagTransactionResults = responseDoc.getBagLookupResponse().getBagLookupResult().getTransactions();
		if (bagTransactionResults == null) {
			logger.error("No bag transaction result : bagTagNumber = " + bagTagNumber + ", startDate = " + dateFormatRequest.format(startDate) //
					+ ", endDate = " + dateFormatRequest.format(endDate)+ " and timeout = " + timeout);
			return newDto;
		}
		
		logger.debug("Results: " + bagTransactionResults.sizeOfBagTransactionResultArray());

		List<ScannerDataDTO> scannerDataList = new ArrayList<ScannerDataDTO>();
		DateFormat dateFormatResult = new SimpleDateFormat(TracingConstants.DISPLAY_DATETIMEFORMAT, Locale.US);
		for (int i = 0; i < bagTransactionResults.sizeOfBagTransactionResultArray(); ++i) {
			BagTransactionResult bagTransactionResult = bagTransactionResults.getBagTransactionResultArray(i);
			logger.debug("bagTransactionResult: \r" + bagTransactionResult);
			if (bagTransactionResult == null) {
				continue;
			}

			String ohdId = bagTransactionResult.getOHDId();
			String scanType = bagTransactionResult.getDescription(); 
			
			Calendar time = bagTransactionResult.getInsertTimestamp();
			Date newdate = DateUtils.convertSystemCalendarToGMTDate(time); 
			String timeStamp = dateFormatResult.format(newdate);
			
			StringBuilder sbLocation = new StringBuilder(0);
			if (bagTransactionResult.getAirportCode() != null) {
				sbLocation.append(bagTransactionResult.getAirportCode()).append("<br/>");
			}
			if (bagTransactionResult.getArea() != null) {
				sbLocation.append(bagTransactionResult.getArea()).append("<br/>");
			}
			if (bagTransactionResult.getLocation() != null) {
				sbLocation.append(bagTransactionResult.getLocation());
			}
			
			StringBuilder sbOtherInfo = new StringBuilder(0);
			if (bagTransactionResult.getUser() != null) {
				sbOtherInfo.append(bagTransactionResult.getUser()).append(" - ");
			}
			if (bagTransactionResult.getWorkstation() != null) {
				sbOtherInfo.append(bagTransactionResult.getWorkstation()).append("<br/>");
			}
			if (bagTransactionResult.getCarrierCode() != null) {
				sbOtherInfo.append(bagTransactionResult.getCarrierCode()).append(" ");
				if (bagTransactionResult.getFlightDate() != null) {
					String flightDate = dateFormatResult.format(bagTransactionResult.getFlightDate().getTime());
					sbOtherInfo.append(flightDate).append("<br/>");
				}
				if (bagTransactionResult.getFlightNumber() != null) {
					sbOtherInfo.append(bagTransactionResult.getFlightNumber());
				}
				sbOtherInfo.append("<br/>");
			}
			if (bagTransactionResult.getContainerName() != null) {
				sbOtherInfo.append(bagTransactionResult.getContainerName()).append("<br/>");
			}
			if (bagTransactionResult.getFromContainerName() != null) {
				sbOtherInfo.append(bagTransactionResult.getFromContainerName()).append("<br/>");
			}
			if (bagTransactionResult.getBumSource() != null) {
				sbOtherInfo.append(bagTransactionResult.getBumSource()).append("<br/>");
			}
			if (bagTransactionResult.getToContainerName() != null) {
				sbOtherInfo.append(bagTransactionResult.getToContainerName()).append("<br/>");
			}
			if (bagTransactionResult.getToContainerName() != null) {
				sbOtherInfo.append(bagTransactionResult.getToContainerName());
			}
			
			ScannerDataDTO dtoItem = new ScannerDataDTO(bagTagNumber, timeStamp, sbLocation.toString(), scanType, sbOtherInfo.toString(), ohdId, time);
			scannerDataList.add(dtoItem);
		}

		newDto.setScannerDataDTOs(scannerDataList);
		Collections.sort(newDto.getScannerDataDTOs(), new ScannerComparator());
		
		return newDto;
	}	
}
