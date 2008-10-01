package com.bagnet.nettracer.integrations.delivery;

import java.util.Set;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.tempuri.ArrayOfItemData;
import org.tempuri.BDOAddDocument;
import org.tempuri.BDOAddResponseDocument;
import org.tempuri.ErrorCode;
import org.tempuri.ItemData;
import org.tempuri.NetTracerStub;
import org.tempuri.BDOAddDocument.BDOAdd;

import com.bagnet.nettracer.tracing.bmo.DelivercompanyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.BDO_Passenger;
import com.bagnet.nettracer.tracing.db.DeliverCompany;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class Rynns implements BDOIntegration {
	
	Logger logger = Logger.getLogger(Rynns.class);

	public DeliveryIntegrationResponse integrate(BDO bdo, Agent agent) {
		DeliveryIntegrationResponse response = new DeliveryIntegrationResponse();
		String responseText = TracerUtils.getResourcePropertyText("delivercompany.integration.rynns.success", agent);
		String integrationId = null;
		boolean success = true;
		
		/*****WEB SERVICE INTEGRATION*****/
	
		try {
			String endpoint= TracerProperties.get(TracerProperties.RYNNS_ENDPOINT);
			NetTracerStub stub = new NetTracerStub(null, endpoint);
			BDOAddDocument doc = BDOAddDocument.Factory.newInstance();
			BDOAdd ws = doc.addNewBDOAdd();
			/********* MAPPING FIELDS *******************/
			
			DeliverCompany dc = DelivercompanyBMO.getDeliveryCompany(bdo.getDelivercompany().getDelivercompany_ID() + "");
			ws.setVendorImportCode(dc.getIntegration_key());
			ws.setAirportCode(agent.getStation().getStationcode());
			
			String airlineCode = agent.getCompanycode_ID();
			
			ws.setAirlineCode(airlineCode);
			String bdoNum = bdo.getBDO_ID_ref().substring(3);
			ws.setBDONumber(bdoNum);
			BDO_Passenger pax = bdo.getPassenger(0);
			ws.setLastName(pax.getLastname());
			ws.setFirstName(pax.getFirstname());
			ws.setEmailAddress(pax.getEmail());
			
			ws.setLocalPhone(pax.getHotel());
			ws.setPermanentPhone(pax.getHomephone());
			ws.setMobilePhone(pax.getMobile());
			
			ws.setAddressLine1(pax.getAddress1());
			ws.setAddressLine2(pax.getAddress2());
			ws.setZipCode(pax.getZip());
			ws.setCity(pax.getCity());
			ws.setState(pax.getState_ID());
			
			if (bdo.getIncident() != null) {
				ws.setClaimReferenceNumber(bdo.getIncident().getIncident_ID());
			} else if (bdo.getOhd() != null){
				ws.setClaimReferenceNumber(bdo.getOhd().getOHD_ID());
			}
			
			StringBuffer remarks = new StringBuffer();
			if (bdo.getDeliverydate() != null) {
				String date = DateUtils.formatDate(bdo.getDeliverydate(), TracingConstants.DISPLAY_DATEFORMAT, null, null);
				remarks.append("Please deliver on: " + date + "\n");
			}
			remarks.append(bdo.getDelivery_comments());
			
			ws.setDeliveryRemarks(remarks.toString());

			bdo.set_DATEFORMAT(TracingConstants.DISPLAY_DATEFORMAT);
			bdo.set_TIMEFORMAT(TracingConstants.DISPLAY_TIMEFORMAT_B);
			bdo.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(agent.getCurrenttimezone())
					.getTimezone()));
			ws.setCreatedDate(bdo.getDispcreatetime());
			ws.setPickUpDate(bdo.getDispcreatetime());
			
			ArrayOfItemData array = ws.addNewItemsData();
			
			if (bdo.getItems() != null) {
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
			BDOAddResponseDocument resDoc = stub.BDO_Add(doc);
			
			int orderId = resDoc.getBDOAddResponse().getBDOAddResult().getOrderID();
			
			if (orderId > 0) {
				integrationId = new Integer(orderId).toString();
			} else {
				success = false;
				responseText = TracerUtils.getResourcePropertyText("delivercompany.integration.rynns.failure", agent);
				ErrorCode.Enum[] a = resDoc.getBDOAddResponse().getBDOAddResult().getErrorCodes().getErrorCodeArray();
				for (ErrorCode.Enum enumeration: a) {
					logger.error("Invalid: " + enumeration.toString());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			success = false;
			responseText = TracerUtils.getResourcePropertyText("delivercompany.integration.rynns.failure", agent);
		}
			
		/*********************************/
		
		response.setResponse(responseText);
		response.setUniqueIntegrationId(integrationId);
		response.setSuccess(success);
		return response;
	}
	
}
