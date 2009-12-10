package com.bagnet.clients.us;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.log4j.Logger;

import com.bagnet.nettracer.datasources.ScannerDataSource;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dto.ScannerDTO;
import com.bagnet.nettracer.tracing.dto.ScannerDataDTO;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.usairways.cbro.baggage.scanning.history.ScanPointsServiceBeanServiceStub;
import com.usairways.www.cbro.baggage_scanner.wsdl.BulkUnloadType;
import com.usairways.www.cbro.baggage_scanner.wsdl.FlightScanType;
import com.usairways.www.cbro.baggage_scanner.wsdl.FlightTaskType;
import com.usairways.www.cbro.baggage_scanner.wsdl.ForwardLegType;
import com.usairways.www.cbro.baggage_scanner.wsdl.ForwardScanType;
import com.usairways.www.cbro.baggage_scanner.wsdl.GetScanPointsDocument;
import com.usairways.www.cbro.baggage_scanner.wsdl.GetScanPointsResponseDocument;
import com.usairways.www.cbro.baggage_scanner.wsdl.LoadBagScanType;
import com.usairways.www.cbro.baggage_scanner.wsdl.LoadScanType;
import com.usairways.www.cbro.baggage_scanner.wsdl.LoadULDScanType;
import com.usairways.www.cbro.baggage_scanner.wsdl.NetTracerScanType;
import com.usairways.www.cbro.baggage_scanner.wsdl.QohScanType;
import com.usairways.www.cbro.baggage_scanner.wsdl.ScanType;
import com.usairways.www.cbro.baggage_scanner.wsdl.TaskType;
import com.usairways.www.cbro.baggage_scanner.wsdl.TrackingScanType;
import com.usairways.www.cbro.baggage_scanner.wsdl.UldLoadScanType;
import com.usairways.www.cbro.baggage_scanner.wsdl.UldScanType;
import com.usairways.www.cbro.baggage_scanner.wsdl.UldTaskType;
import com.usairways.www.cbro.baggage_scanner.wsdl.UldTransferType;
import com.usairways.www.cbro.baggage_scanner.wsdl.UnloadScanType;
import com.usairways.www.cbro.baggage_scanner.wsdl.UnloadULDScanType;
import com.usairways.www.cbro.baggage_scanner.wsdl.GetScanPointsResponseDocument.GetScanPointsResponse.Out;

import edu.emory.mathcs.backport.java.util.Collections;

public class ScannerDataSourceImpl implements ScannerDataSource {
	
	private static final String PROPERTY_SCAN_HISTORY_ENDPOINT = "scan.history.endpoint";
	private static final Logger logger = Logger.getLogger(ScannerDataSourceImpl.class);

	public ScannerDTO getScannerData(Date startDate, Date endDate, String bagTagNumber) {
		String endpoint = PropertyBMO.getValue(PROPERTY_SCAN_HISTORY_ENDPOINT);
		
		
		ScanPointsServiceBeanServiceStub stub = null;
		try {
			stub = new ScanPointsServiceBeanServiceStub(endpoint);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		stub._getServiceClient().getOptions().setProperty(HTTPConstants.CHUNKED, Boolean.FALSE);
		stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, new Integer(2*60*1000));
		stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, new Integer(2*60*1000));
		
		ScannerDTO newDto = new ScannerDTO();
		ArrayList<ScannerDataDTO> list = new ArrayList<ScannerDataDTO>();
		
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

		GetScanPointsResponseDocument responseDoc = null;
		try {
			System.out.println("Scanner request: " + scanDoc);
			logger.info("Scanner request: " + scanDoc);
			
			responseDoc = stub.getScanPoints(scanDoc);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		Out out = responseDoc.getGetScanPointsResponse().getOut();
		System.out.println("Scanner Response: " + responseDoc);
		logger.info("Scanner response: " + responseDoc);
		
		for (int i = 0; i < 16; ++i) {
			logger.info("Iteration: " + i);
			Object[] arr = null;
			switch (i) {
				case 0: if (out.getBulkUnloadArray() != null) arr = out.getBulkUnloadArray(); break;
				case 1:	if (out.getCancelFlightArray() != null ) arr = out.getCancelFlightArray(); break;
				case 2:	if (out.getForwardArray() != null ) arr = out.getForwardArray(); break;
				case 3:	if (out.getInventoryCountArray() != null ) arr = out.getInventoryCountArray(); break;
				case 4:	if (out.getInventoryInArray() != null ) arr = out.getInventoryInArray(); break;
				case 6: if (out.getInventoryOutArray() != null ) arr = out.getInventoryOutArray(); break;
				case 7:	if (out.getLoadArray() != null ) arr = out.getLoadArray(); break;
				case 8:	if (out.getLoadULDArray() != null ) arr = out.getLoadULDArray(); break;
				case 9:	if (out.getQohArray() != null ) arr = out.getQohArray(); break;
				case 10: if (out.getTrackingArray() != null ) arr = out.getTrackingArray(); break;
				case 11: if (out.getUldAssignToBulkArray() != null ) arr = out.getUldAssignToBulkArray(); break;
				case 12: if (out.getUldLoadArray() != null ) arr = out.getUldLoadArray(); break;
				case 13: if (out.getUldTransferArray() != null ) arr = out.getUldTransferArray(); break;
				case 14: if (out.getUnloadArray() != null ) arr = out.getUnloadArray(); break;
				case 15: if (out.getUnloadULDArray() != null ) arr = out.getUnloadULDArray(); break;
			}
			
			
			if (arr != null && arr.length > 0) {
				
				logger.info(arr);
				for (int j = 0; j < arr.length; ++j) {
					Object obj = arr[j];
//					for (Object obj : arr) {
						logger.info(obj);
						
						String type = "Scan";
						StringBuffer comment = new StringBuffer();
						String ohdId = null;
						Calendar time = null;
						String city = null;
						
						if (obj instanceof ScanType) {
							ScanType o = (ScanType) obj;
							city = o.getCity();
							time = o.getInstant();
						}
						
						if (obj instanceof TaskType) {
							TaskType o = (TaskType) obj;
							city = o.getCity();
							time = o.getInstant();
						}
						
						if (obj instanceof NetTracerScanType) {
							NetTracerScanType o = (NetTracerScanType) obj;
							ohdId = o.getNetTracerId();
						}
						
						if (obj instanceof FlightTaskType) {
							FlightTaskType o = (FlightTaskType) obj;
							comment.append("Flight: " + o.getFlightNumber() + " on " +o.getFlightDate()+ "<br />");
						} 

						if (obj instanceof BulkUnloadType) {
							type = "Bulk Unload";
							BulkUnloadType o = (BulkUnloadType) obj;
							ifNotNull(comment, "Unload Reason: ", o.getUnloadReason(), "<br />");
						} 
						
						if (obj instanceof ForwardScanType) {
							type = "Forward";
							
							// NetTracer Scan Type
							ForwardScanType o = (ForwardScanType) obj;
							comment.append("Tag Status: " + o.getTagStatus() + "<br />");
							comment.append("Fault Reason: " + o.getFaultReason() + "<br />");
							comment.append("Fault Station: " + o.getFaultStation() + "<br />");
							
							ForwardLegType[] legs= o.getLegs().getLegArray();
							if (legs.length > 0) {
								comment.append("Forward Itinerary: <br />");
							}
							
							for (ForwardLegType leg: legs) {
								comment.append("&nbsp;&nbsp;" + leg.getCarrier() + " " + leg.getFlightDate() + " " + leg.getFlightNumber() + " to " + leg.getDestination() + "<br />");
							}
						} 
						
						if (obj instanceof TrackingScanType) {
							type = "Tracking";
							TrackingScanType o = (TrackingScanType) obj;
							comment.append("Location: " + o.getLocation() + "<br />");
						} 

						if (obj instanceof FlightScanType) {
							type = "Tracking";
							FlightScanType o = (FlightScanType) obj;
							comment.append("Flight: " + o.getFlightNumber() + " on " +o.getFlightDate()+ "<br />");							
						} 
						
						if (obj instanceof LoadScanType) {
							type = "Load Scan";
							LoadScanType o = (LoadScanType) obj;
							comment.append("Bin: " + o.getBin() + "<br />");
						} 
						
						if (obj instanceof LoadBagScanType) {
							type = "Load Bag Scan";
							LoadBagScanType o = (LoadBagScanType) obj;
							ohdId = o.getNetTracerId();
							ifNotNull(comment, "Destination: ", o.getForwardDestination(), "<br />");				
						} 

						if (obj instanceof UldTaskType) {
							type = "ULD Task Scan";
							UldTaskType o = (UldTaskType) obj;
							comment.append("ULD: " + o.getUld() + "<br />");
						} 
						
						if (obj instanceof UldScanType) {
							type = "ULD Task Scan";
							UldScanType o = (UldScanType) obj;
							comment.append("ULD: " + o.getUld() + "<br />");
						} 

						
						if (obj instanceof LoadULDScanType) {
							type = "Load ULD Scan";
							LoadULDScanType o = (LoadULDScanType) obj;
							comment.append("ULD: " + o.getUld() + "<br />");
						} 

						if (obj instanceof QohScanType) {
							type = "QOH Scan";
							QohScanType o = (QohScanType) obj;
							comment.append("Comment: " + o.getComment() + "<br />");
						} 
						
						
						if (obj instanceof UldLoadScanType) {
							type = "ULD Load Scan Type";
							UldLoadScanType o = (UldLoadScanType) obj;
							ifNotNull(comment, "Destination: ", o.getForwardDestination(), "<br />");
							ohdId = o.getNetTracerId();
							comment.append("ULD: " + o.getUld() + "<br />");
						} 
						
						if (obj instanceof UldTransferType) {
							type = "ULD Transfer";
							UldTransferType o = (UldTransferType) obj;
							comment.append("Transfer from: " + o.getUldFrom() + " to " + o.getUldTo() + "<br />");
						} 
						
						if (obj instanceof UnloadScanType) {
							type = "Unload Scan";
							UnloadScanType o = (UnloadScanType) obj;
							ifNotNull(comment, "Unload Reason: ", o.getUnloadReason(), "<br />");
						} 
						
						if (obj instanceof UnloadULDScanType) {
							type = "Unload ULD Scan";
							UnloadULDScanType o = (UnloadULDScanType) obj;
							comment.append("ULD: " + o.getUld() + "<br />");
						}
						
						DateFormat sdf = new SimpleDateFormat(TracingConstants.DISPLAY_DATETIMEFORMAT, Locale.US);
						Date newdate = DateUtils.convertSystemCalendarToGMTDate(time); 
						String dateString = sdf.format(newdate);
						ScannerDataDTO dtoItem = new ScannerDataDTO(dateString, city, type, comment.toString(), ohdId, time);
						list.add(dtoItem);

//					}
				}

			}
		}
		newDto.setScannerDataDTOs(list);
		Collections.sort(newDto.getScannerDataDTOs(), new ScannerComparator());
		
		return newDto;
		
	}

	private void ifNotNull(StringBuffer comment, String string1,
			String string2, String string3) {
		if (string2 != null) {
			comment.append(string1 + " " + string2 + " " + string3 );
		}
	}
}
