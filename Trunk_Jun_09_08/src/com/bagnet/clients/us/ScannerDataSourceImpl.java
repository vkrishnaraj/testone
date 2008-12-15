package com.bagnet.clients.us;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.log4j.Logger;

import com.bagnet.nettracer.datasources.ScannerDataSource;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dto.ScannerDTO;
import com.bagnet.nettracer.tracing.dto.ScannerDataDTO;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.usairways.www.cbro.baggage_scanner.wsdl.ForwardLegType;
import com.usairways.www.cbro.baggage_scanner.wsdl.ForwardType;
import com.usairways.www.cbro.baggage_scanner.wsdl.GetScanPointsDocument;
import com.usairways.www.cbro.baggage_scanner.wsdl.GetScanPointsResponseDocument;
import com.usairways.www.cbro.baggage_scanner.wsdl.InventoryType;
import com.usairways.www.cbro.baggage_scanner.wsdl.LoadType;
import com.usairways.www.cbro.baggage_scanner.wsdl.QohType;
import com.usairways.www.cbro.baggage_scanner.wsdl.ScanPointsStub;
import com.usairways.www.cbro.baggage_scanner.wsdl.ScanType;
import com.usairways.www.cbro.baggage_scanner.wsdl.TrackingType;
import com.usairways.www.cbro.baggage_scanner.wsdl.UnloadType;

public class ScannerDataSourceImpl implements ScannerDataSource {
	
	private static final String PROPERTY_SCAN_HISTORY_ENDPOINT = "scan.history.endpoint";
	private static final Logger logger = Logger.getLogger(ScannerDataSourceImpl.class);

	public ScannerDTO getScannerData(Date startDate, Date endDate, String bagTagNumber) {

		ScannerDTO newDto = new ScannerDTO();
		ArrayList<ScannerDataDTO> list = new ArrayList<ScannerDataDTO>();
		
		
		try {
			String endpoint = PropertyBMO.getValue(PROPERTY_SCAN_HISTORY_ENDPOINT);		
			ScanPointsStub stub = new ScanPointsStub(endpoint);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.CHUNKED, Boolean.FALSE);
			
			GetScanPointsDocument scanDoc = GetScanPointsDocument.Factory.newInstance();
			GetScanPointsDocument.GetScanPoints sp = scanDoc.addNewGetScanPoints();
			
			GregorianCalendar startCal = new GregorianCalendar();
			startCal.setTime(startDate);
			startCal.setTimeZone(TimeZone.getTimeZone("GMT"));
			
			GregorianCalendar endCal = new GregorianCalendar();
			endCal.setTime(endDate);
			endCal.setTimeZone(TimeZone.getTimeZone("GMT"));
			
			sp.setStartTime(startCal);
			sp.setEndTime(endCal);
			sp.setTag(bagTagNumber);

			GetScanPointsResponseDocument responseDoc = stub.getScanPoints(scanDoc);
			ScanType[] array = responseDoc.getGetScanPointsResponse().getScanArray();
			responseDoc.getGetScanPointsResponse().getScanArray();
			

			for (ScanType item: array) {
				
				String type = "Scan";
				StringBuffer comment = new StringBuffer();
				String ohdId = null;
				Calendar time = null;
				
				if (item instanceof QohType) {
					type = "Mass On-Hand";
					QohType t = (QohType) item;
					if (t.getComment() != null) {
						comment.append(t.getComment());
					}
					ohdId = t.getNetTracerId();
					time = t.getScanTime();
					
				} else if (item instanceof ForwardType) {
					type = "Forward";
					ForwardType t = (ForwardType) item;
					comment.append("Carrier: " + t.getCarrier() + "<br />");
					comment.append("Tag Status: " + t.getTagStatus() + "<br />");
					comment.append("Destination: " + t.getDestination() + "<br />");
					comment.append("Fault Reason: " + t.getFaultReason() + "<br />");
					comment.append("Fault Station: " + t.getFaultStation() + "<br />");
					ForwardLegType[] legs= t.getLegs().getLegArray();
					if (legs.length > 0) {
						comment.append("Forward Itinerary: " + t.getFaultStation() + "<br />");
					}
					
					for (ForwardLegType leg: legs) {
						comment.append(" " + leg.getCarrier() + " " + leg.getFlightDate() + " " + leg.getFlightNumber());
					}
					
					ohdId = t.getNetTracerId();
					time = t.getScanTime();
					
				} else if (item instanceof TrackingType) {
					type = "Tracking Type";
					TrackingType t = (TrackingType) item;
					comment.append("Location: " + t.getLocation());
					time = t.getScanTime();
					
				} else if (item instanceof InventoryType) {
					type = "Inventory Type";
					InventoryType t = (InventoryType) item;
					comment.append("Info: " + t.getInfo());
					time = t.getScanTime();
					
				} else if (item instanceof UnloadType) {
					type = "Unload";
					UnloadType t = (UnloadType) item;
					comment.append("Bin: " + t.getBin() + "<br />");
					comment.append("Flight: " + t.getFlightNumber() + " " +t.getFlightDate()+ "<br />");
					if (t.getUnloadReason() != null) {
						comment.append("Unload Reason: " + t.getUnloadReason()+ "<br />");
					}
					time = t.getScanTime();
				} else if (item instanceof LoadType) {
					type = "Load";
					LoadType t = (LoadType) item;
					comment.append("Bin: " + t.getBin()+ "<br />");
					comment.append("Flight: " + t.getFlightNumber() + " " +t.getFlightDate()+ "<br />"); 
					time = t.getScanTime();
				}
				
				DateFormat sdf = new SimpleDateFormat(TracingConstants.DISPLAY_DATETIMEFORMAT, Locale.US);
				Date newdate = DateUtils.convertSystemCalendarToGMTDate(time); 
					
				String dateString = sdf.format(newdate);
				
				ScannerDataDTO dtoItem = new ScannerDataDTO(dateString, item.getCity(), type, comment.toString(), ohdId);
				list.add(dtoItem);
			}

		} catch (Exception e) {
			logger.error("Error calling Scan History webservice.", e);
			newDto.setErrorResponse("Error calling webservice: " + e.toString());
		}
		newDto.setScannerDataDTOs(list);		
		return newDto;
	}

}
