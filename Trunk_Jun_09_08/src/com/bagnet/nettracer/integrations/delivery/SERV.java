package com.bagnet.nettracer.integrations.delivery;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.tempuri.ArrayOfItemData;
import org.tempuri.BDOAddDocument;
import org.tempuri.BDOAddResponseDocument;
import org.tempuri.ErrorCode;
import org.tempuri.ItemData;
import org.tempuri.AirlineServicesStub;
import org.tempuri.BDOAddDocument.BDOAdd;

import com.bagnet.nettracer.tracing.bmo.DelivercompanyBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.BDO_Passenger;
import com.bagnet.nettracer.tracing.db.DeliverCompany;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class SERV implements BDOIntegration {
	
	private static final String PROPERTY_SERV_ENDPOINT = "serv.endpoint";
	
	Logger logger = Logger.getLogger(SERV.class);

	public DeliveryIntegrationResponse integrate(BDO bdo, Agent agent) {
		DeliveryIntegrationResponse response = new DeliveryIntegrationResponse();
		String responseText = TracerUtils.getText("delivercompany.integration.serv.success", agent);
		String integrationId = null;
		boolean success = true;
		
		/*****WEB SERVICE INTEGRATION*****/
	
		try {
			logger.info("POINT 1\n\n");
			String endpoint= PropertyBMO.getValue(PROPERTY_SERV_ENDPOINT);
			AirlineServicesStub stub = new AirlineServicesStub(null, endpoint);
			BDOAddDocument doc = BDOAddDocument.Factory.newInstance();
			BDOAdd ws = doc.addNewBDOAdd();
			/********* MAPPING FIELDS *******************/

			logger.info("POINT 2\n\n");
			DeliverCompany dc = DelivercompanyBMO.getDeliveryCompany(bdo.getDelivercompany().getDelivercompany_ID() + "");
			ws.setVendorImportCode(dc.getIntegration_key());
			ws.setAirportCode(agent.getStation().getStationcode());
			
			String airlineCode = agent.getCompanycode_ID();
			
			ws.setAirlineCode(airlineCode);
			ws.setResponsibleAirlineCode(airlineCode);
			String bdoNum = bdo.getBDO_ID_ref().substring(3);
			ws.setBDONumber(bdoNum);
			BDO_Passenger pax = bdo.getPassenger(0);
			ws.setLastName(pax.getLastname());
			ws.setMiddleName(pax.getMiddlename());
			ws.setFirstName(pax.getFirstname());
			ws.setEmailAddress(pax.getEmail());

			logger.info("POINT 3\n\n");
			ws.setLocalPhone(pax.getHotel());
			ws.setPermanentPhone(pax.getHomephone());
			ws.setMobilePhone(pax.getMobile());
			
			ws.setAddressLine1(pax.getAddress1());
			ws.setAddressLine2(pax.getAddress2());
			ws.setZipCode(pax.getZip());
			ws.setCity(pax.getCity());
			ws.setState(pax.getState_ID());
			
			ws.setFlightNumber("");
			ws.setFlightDateSpecified(false);
			ws.setRoute("");
			ws.setServiceLevel(DelivercompanyBMO.getServiceLevel(bdo.getServicelevel().getServicelevel_ID()).getService_code());

			logger.info("POINT 4\n\n");
			Calendar claimCal = new GregorianCalendar();
			if (bdo.getIncident() != null) {
				ws.setClaimReferenceNumber(bdo.getIncident().getIncident_ID());
				claimCal.setTime(bdo.getIncident().getCreatedate());
				ws.setClaimDate(claimCal);
			} else if (bdo.getOhd() != null){
				ws.setClaimReferenceNumber(bdo.getOhd().getOHD_ID());
				claimCal.setTime(bdo.getOhd().getFounddate());
				ws.setClaimDate(claimCal);
			}
			ws.setClaimDateSpecified(true);

			logger.info("POINT 5\n\n");

			Calendar deliveryCal = new GregorianCalendar();
			if (bdo.getDeliverydate() != null) {
				deliveryCal.setTime(bdo.getDeliverydate());
				ws.setDeliveryDate(deliveryCal);
				ws.setDeliveryDateSpecified(true);
			} else {
				ws.setDeliveryDateSpecified(false);
			}
			
			StringBuffer remarks = new StringBuffer();
			remarks.append("Field Office: " + dc.getName() + "\n");
			remarks.append(bdo.getDelivery_comments());
			
			ws.setDeliveryRemarks(remarks.toString());

			logger.info("POINT 6\n\n");
			bdo.set_DATEFORMAT(TracingConstants.DISPLAY_DATEFORMAT);
			bdo.set_TIMEFORMAT(TracingConstants.DISPLAY_TIMEFORMAT_B);
			bdo.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(agent.getCurrenttimezone())
					.getTimezone()));
			Calendar createCal = new GregorianCalendar(bdo.get_TIMEZONE());
			createCal.setTime(bdo.getCreatedate());
			ws.setCreatedDate(createCal);
			ws.setPickUpDate(createCal);
			
			ArrayOfItemData array = ws.addNewItemsData();

			logger.info("POINT 7\n\n");
			if (bdo.getItems() != null && bdo.getIncident() != null) {
				for (Item item: (Set<Item>)bdo.getItems()) {
					ItemData id = array.addNewItemData();
					id.setColor(item.getColor());
					if (item.getClaimchecknum() != null && item.getClaimchecknum().trim().length() > 0) {
						id.setTag(item.getClaimchecknum());	
					} else {
						id.setTag("notAvailable");
					}
					
					id.setItemType(item.getBagtype());
					id.setBrand(item.getManuname());
				}
			} else if (bdo.getOhd() != null) {
				OHD ohd = bdo.getOhd();
				ItemData id = array.addNewItemData();
				if (ohd.getColor() != null) {
					id.setColor(ohd.getColor());
				}
				if (ohd.getClaimnum() != null && ohd.getClaimnum().trim().length() > 0) {
					id.setTag(ohd.getClaimnum());	
				} else {
					id.setTag("notAvailable");
				}
			}
			
			
			/********* HANDLING THE RESPONSE ************/
			logger.info(doc);
			BDOAddResponseDocument resDoc = stub.BDO_Add(doc);
			logger.info(resDoc);
			int orderId = resDoc.getBDOAddResponse().getBDOAddResult().getOrderID();
			
			if (orderId > 0) {
				integrationId = new Integer(orderId).toString();
			} else {
				success = false;
				responseText = TracerUtils.getText("delivercompany.integration.serv.failure", agent);
				ErrorCode.Enum[] a = resDoc.getBDOAddResponse().getBDOAddResult().getErrorCodes().getErrorCodeArray();
				for (ErrorCode.Enum enumeration: a) {
					logger.error("Invalid: " + enumeration.toString());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			success = false;
			responseText = TracerUtils.getText("delivercompany.integration.serv.failure", agent);
		}
			
		/*********************************/
		
		response.setResponse(responseText);
		response.setUniqueIntegrationId(integrationId);
		response.setSuccess(success);
		return response;
	}
	
}
