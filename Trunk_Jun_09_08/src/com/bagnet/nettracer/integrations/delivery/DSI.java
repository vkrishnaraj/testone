package com.bagnet.nettracer.integrations.delivery;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.bmo.DelivercompanyBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.XDescElementsBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.BDO_Passenger;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.dsii.bdoservice.schemas._2008.bdo_2_0_xsd.AuthType;
import com.dsii.bdoservice.schemas._2008.bdo_2_0_xsd.BdoSubmitRequestType;
import com.dsii.bdoservice.schemas._2008.bdo_2_0_xsd.BdoType;
import com.dsii.bdoservice.schemas._2008.bdo_2_0_xsd.BdoUpdateRequestType;
import com.dsii.bdoservice.schemas._2008.bdo_2_0_xsd.BdoUpdateType;
import com.dsii.bdoservice.schemas._2008.bdo_2_0_xsd.ClaimInfoType;
import com.dsii.bdoservice.schemas._2008.bdo_2_0_xsd.ClaimInfoUpdateType;
import com.dsii.bdoservice.schemas._2008.bdo_2_0_xsd.ConsigneeNameType;
import com.dsii.bdoservice.schemas._2008.bdo_2_0_xsd.DeliveryInfoType;
import com.dsii.bdoservice.schemas._2008.bdo_2_0_xsd.DeliveryInfoUpdateType;
import com.dsii.bdoservice.schemas._2008.bdo_2_0_xsd.HeaderType;
import com.dsii.bdoservice.schemas._2008.bdo_2_0_xsd.HeaderUpdateType;
import com.dsii.bdoservice.schemas._2008.bdo_2_0_xsd.JobIdType;
import com.dsii.bdoservice.schemas._2008.bdo_2_0_xsd.LuggageItemType;
import com.dsii.bdoservice.schemas._2008.bdo_2_0_xsd.LuggageItemUpdateType;
import com.dsii.bdoservice.schemas._2008.bdo_2_0_xsd.ServiceLevelType;
import com.dsii.bdoservice.schemas._2008.bdo_2_0_xsd.StatusType;
import com.dsii.bdoservice.schemas._2008.bdo_2_0_xsd.SubmitBdoJobRequestDocument.SubmitBdoJobRequest;
import com.dsii.bdoservice.schemas._2008.bdo_2_0_xsd.UpdateBdoJobRequestDocument.UpdateBdoJobRequest;
import com.dsii.bdoservice.BdoServiceStub;
import com.dsii.bdoservice.SubmitBdoJobDocument;
import com.dsii.bdoservice.SubmitBdoJobResponseDocument;
import com.dsii.bdoservice.SubmitBdoUpdateDocument;
import com.dsii.bdoservice.SubmitBdoUpdateResponseDocument;
import com.dsii.bdoservice.SubmitBdoJobDocument.SubmitBdoJob;
import com.dsii.bdoservice.SubmitBdoUpdateDocument.SubmitBdoUpdate;

public class DSI implements BDOIntegration {
	
	private static final String PROPERTY_DSI_ENDPOINT = "dsi.endpoint";
	private static final String PROPERTY_DSI_CLIENT_ID = "dsi.client.id";
	private static final String PROPERTY_DSI_PASS_CODE = "dsi.client.passcode";
	private static final String PROPERTY_DSI_PROFILE_NAME = "dsi.client.profilename";
	
	Logger logger = Logger.getLogger(DSI.class);

	public DeliveryIntegrationResponse integrate(BDO bdo, Agent agent) {
		DeliveryIntegrationResponse response = new DeliveryIntegrationResponse();
		String responseText = TracerUtils.getText("delivercompany.integration.dsi.success", agent);
		String integrationId = null;
		boolean success = true;
		
		String endpoint= PropertyBMO.getValue(PROPERTY_DSI_ENDPOINT);
		
		if (endpoint == null || endpoint.trim().length() == 0) {
			success = false;
			responseText = TracerUtils.getText("delivercompany.integration.dsi.notconfigured", agent);
		} else {

			
			/*****WEB SERVICE INTEGRATION*****/
		
			try {
				
				BdoServiceStub stub = new BdoServiceStub(null, endpoint);

				// Already have submitted BDO need to send update
				if (bdo.getDelivery_integration_id() != null && bdo.getDelivery_integration_id().trim().length() > 0) {

					// Updated
				
					String fullId = bdo.getDelivery_integration_id().trim();
					
					SubmitBdoUpdateDocument doc = SubmitBdoUpdateDocument.Factory.newInstance();
					SubmitBdoUpdate update = doc.addNewSubmitBdoUpdate();
					UpdateBdoJobRequest request = update.addNewUpdateBdoJobRequest();
					BdoUpdateRequestType req = request.addNewRequest();
					
					request.setSubmitId("1");
					
					AuthType auth = request.addNewAuthenticate();
					auth.setClientId(PropertyBMO.getValue(PROPERTY_DSI_CLIENT_ID));
					auth.setPassCode(PropertyBMO.getValue(PROPERTY_DSI_PASS_CODE));
					auth.setProfileName(PropertyBMO.getValue(PROPERTY_DSI_PROFILE_NAME));
					
					String[] fullIdArray = fullId.split("/");
					
					String pst = fullIdArray[0];
					String rno = fullIdArray[1];
					
					JobIdType jobId = req.addNewJobId();
					jobId.setPST(pst);
					jobId.setRNO(new BigInteger(rno));
					
					BdoUpdateType bdoType = req.addNewBdo();
					
					HeaderUpdateType ht = bdoType.addNewHeader();
					ServiceLevelType.Enum serviceLevel = getServiceLevel(bdo);
					ht.setServiceLevel(serviceLevel);

					StringBuffer remarks = new StringBuffer();
					if (bdo.getDeliverydate() != null) {
						String date = DateUtils.formatDate(bdo.getDeliverydate(), TracingConstants.DISPLAY_DATEFORMAT, null, null);
						remarks.append("Please deliver on: " + date + "\n");
					}

					ht.setDeliveryNote(remarks.toString());
					
					ClaimInfoUpdateType claim = bdoType.addNewClaimInfo();
					claim.setFieldOfficeName(bdo.getDelivercompany().getName());
					claim.setStationAgent(agent.getUsername());
					claim.setFlightDetail("");
					claim.setRoute("");
					
					
					DeliveryInfoUpdateType del = bdoType.addNewDeliveryInfo();
					BDO_Passenger pax = bdo.getPassenger(0);
					ConsigneeNameType con = del.addNewContactName();
					con.setFirstName(pax.getFirstname());
					con.setMiddleName(pax.getMiddlename());
					con.setLastName(pax.getLastname());
					
					del.setCompany(pax.getFirstname() + " " + pax.getLastname());
					
					String phone = pax.getMobile();
					
					if (phone == null) {
						phone = pax.getHomephone();
					} else if (phone == null) {
						phone = pax.getWorkphone();
					} else if (phone == null) {
						phone = pax.getHotel();
					}
					
					del.setContactPhone(phone);
					del.setAddress(pax.getAddress1());
					del.setAddress2(pax.getAddress2());
					del.setCity(pax.getCity());
					del.setState(pax.getState_ID());
					del.setZip(pax.getZip());
					
					
					for (Item item: (Set<Item>)bdo.getItems()) {
						LuggageItemUpdateType lug = bdoType.addNewLuggageItem();
						
						if (item.getClaimchecknum() != null && item.getClaimchecknum().trim().length() > 0) {
							lug.setTagNumber(item.getClaimchecknum());	
						} else {
							lug.setTagNumber("");
						}
						
						StringBuffer sb = new StringBuffer();
						sb.append(item.getBagtype());
						sb.append(getElementDescriptor(item.getXdescelement_ID_1()));
						sb.append(getElementDescriptor(item.getXdescelement_ID_2()));
						sb.append(getElementDescriptor(item.getXdescelement_ID_3()));
		
						lug.setType(sb.toString());
						lug.setColor(item.getColor());
					}
					
					SubmitBdoUpdateResponseDocument resDoc = stub.SubmitBdoUpdate(doc);
					
					// Success or failure
					BigInteger deliveredOrNot = resDoc.getSubmitBdoUpdateResponse().getSubmitBdoUpdateResult().getStatus().getCode();
					
					StatusType status = resDoc.getSubmitBdoUpdateResponse().getSubmitBdoUpdateResult().getStatus();
					// logger.info("DSI BDO Submission Status: " + status.getCode().toString());
					if (deliveredOrNot.equals(new BigInteger("0003"))) {
						// Already delivered
						responseText = TracerUtils.getText("delivercompany.integration.dsi.bdoClosed", agent);
						integrationId = fullId;
					} else if (status.getMessage().equals("OK")) {
						//Success
						// PST/RNO
						
						integrationId = resDoc.getSubmitBdoUpdateResponse().getSubmitBdoUpdateResult().getResult().getJobId().getPST();
						integrationId += "/";
						integrationId += resDoc.getSubmitBdoUpdateResponse().getSubmitBdoUpdateResult().getResult().getJobId().getRNO();
						
						//int orderId = 0;
		
					} else {
						success = false;
						responseText = TracerUtils.getText("delivercompany.integration.dsi.failure", agent);
						integrationId = fullId;
					}
					
				} else {
				
					// IF NEW
				  SubmitBdoJobDocument doc = SubmitBdoJobDocument.Factory.newInstance();
				  
				  SubmitBdoJob job = doc.addNewSubmitBdoJob();
					SubmitBdoJobRequest request = job.addNewSubmitBdoJobRequest();
					
					// Must pass in a submit ID or results in failure.
					request.setSubmitId("1");
					
					// Authentication
					AuthType auth = request.addNewAuthenticate();
					auth.setClientId(PropertyBMO.getValue(PROPERTY_DSI_CLIENT_ID));
					auth.setPassCode(PropertyBMO.getValue(PROPERTY_DSI_PASS_CODE));
					auth.setProfileName(PropertyBMO.getValue(PROPERTY_DSI_PROFILE_NAME));
					
					BdoSubmitRequestType req = request.addNewRequest();
					BdoType bdoType = req.addNewBdo();
					
					// Header
					HeaderType ht = bdoType.addNewHeader();
					ht.setBDONumber(bdo.getBDO_ID_ref().substring(3));
					
					ServiceLevelType.Enum serviceLevel = getServiceLevel(bdo);
					
					ht.setServiceLevel(serviceLevel);
					ht.setOrderType("Luggage");
					ht.setServiceType("Delivery");
					ht.setOrderStatus("New");
					
					String bigIntString = null;
					if (bdo.getItems() == null || bdo.getItems().size() <= 1) {
						bigIntString = "1";
					} else {
						bigIntString = "" + bdo.getItems().size();
					}
					BigInteger bigInt = new BigInteger(bigIntString);
					ht.setNumberOfItems(bigInt);
					
					if (bdo.getIncident() != null) {
						ht.setClientReferenceNumber(bdo.getIncident().getIncident_ID());
					} else if (bdo.getOhd().getOHD_ID() != null) {
						ht.setClientReferenceNumber(bdo.getOhd().getOHD_ID());
					}
					
					StringBuffer remarks = new StringBuffer();
					if (bdo.getDeliverydate() != null) {
						String date = DateUtils.formatDate(bdo.getDeliverydate(), TracingConstants.DISPLAY_DATEFORMAT, null, null);
						remarks.append("Please deliver on: " + date + "\n");
					}
					remarks.append(bdo.getDelivery_comments());
					
					ht.setDeliveryNote(remarks.toString());
					
					// Claim info
					ClaimInfoType claim = bdoType.addNewClaimInfo();
					
					Calendar claimTime = new GregorianCalendar();
					Calendar nowDateTime = new GregorianCalendar();
					
					if (bdo.getIncident() != null) {
						ht.setClientReferenceNumber(bdo.getIncident().getIncident_ID());
						claimTime.setTime(bdo.getIncident().getFullCreateDate());
					} else if (bdo.getOhd().getOHD_ID() != null) {
						ht.setClientReferenceNumber(bdo.getOhd().getOHD_ID());
					}
					
					claim.setClaimDateTime(claimTime);
					claim.setSubmissionDateTime(nowDateTime);
					claim.setBDODateTime(nowDateTime);
					claim.setAirportCode(agent.getStation().getStationcode());
					claim.setAirlineCode(agent.getStation().getCompany().getCompanyCode_ID());
					claim.setResponsibleAirlineCode(agent.getStation().getCompany().getCompanyCode_ID());
					claim.setFieldOfficeName(bdo.getDelivercompany().getName());
					claim.setStationAgent(agent.getUsername());
					claim.setFlightDetail("");
					claim.setRoute("");
					
					// Delivery info
					DeliveryInfoType del = bdoType.addNewDeliveryInfo();
					BDO_Passenger pax = bdo.getPassenger(0);
					ConsigneeNameType con = del.addNewContactName();
					con.setFirstName(pax.getFirstname());
					con.setMiddleName(pax.getMiddlename());
					con.setLastName(pax.getLastname());
					
					del.setCompany(pax.getFirstname() + " " + pax.getLastname());
					
					String phone = pax.getMobile();
					
					if (phone == null) {
						phone = pax.getHomephone();
					} else if (phone == null) {
						phone = pax.getWorkphone();
					} else if (phone == null) {
						phone = pax.getHotel();
					}
					
					del.setContactPhone(phone);
					del.setAddress(pax.getAddress1());
					del.setAddress2(pax.getAddress2());
					del.setCity(pax.getCity());
					del.setState(pax.getState_ID());
					del.setZip(pax.getZip());
					del.setCountryCode(pax.getCountrycode_ID());
					
					
					// Items
					for (Item item: (Set<Item>)bdo.getItems()) {
						LuggageItemType lug = bdoType.addNewLuggageItem();
						
						if (item.getClaimchecknum() != null && item.getClaimchecknum().trim().length() > 0) {
							lug.setTagNumber(item.getClaimchecknum());	
						} else {
							lug.setTagNumber("");
						}
						
						StringBuffer sb = new StringBuffer();
						sb.append(item.getBagtype());
						sb.append(getElementDescriptor(item.getXdescelement_ID_1()));
						sb.append(getElementDescriptor(item.getXdescelement_ID_2()));
						sb.append(getElementDescriptor(item.getXdescelement_ID_3()));
		
						lug.setType(sb.toString());
						lug.setColor(item.getColor());
					}
					
					/********* HANDLING THE RESPONSE ************/
					SubmitBdoJobResponseDocument resDoc = stub.SubmitBdoJob(doc);
		
					// Success or failure
					StatusType status = resDoc.getSubmitBdoJobResponse().getSubmitBdoJobResult().getStatus();
					// logger.info("DSI BDO Submission Status: " + status.getCode().toString());
					if (status.getMessage().equals("OK")) {
						//Success
						// PST/RNO
						
						integrationId = resDoc.getSubmitBdoJobResponse().getSubmitBdoJobResult().getResult().getJobId().getPST();
						integrationId += "/";
						integrationId += resDoc.getSubmitBdoJobResponse().getSubmitBdoJobResult().getResult().getJobId().getRNO();
						
						//int orderId = 0;
		
					} else {
						success = false;
						responseText = TracerUtils.getText("delivercompany.integration.dsi.failure", agent);
					}
				}
	
			} catch (Exception e) {
				e.printStackTrace();
				success = false;
				responseText = TracerUtils.getText("delivercompany.integration.dsi.failure", agent);
			}
		}
		
		response.setResponse(responseText);
		response.setUniqueIntegrationId(integrationId);
		response.setSuccess(success);
		return response;
	}

	private String getElementDescriptor(int xdescelement_ID) {
		String code = XDescElementsBMO.getXdescelementcode(xdescelement_ID);
		if (code == null) {
			return "X";
		}
		return code;
	}

	private ServiceLevelType.Enum getServiceLevel(BDO bdo) {
		
		String code = DelivercompanyBMO.getServiceLevel(bdo.getServicelevel().getServicelevel_ID()).getService_code();
		
		if (code.equals("SPC")) {
			return ServiceLevelType.SPC;
		} else if (code.equals("ECO")) {
			return ServiceLevelType.ECO;
		} else if (code.equals("NEX")) {
			return ServiceLevelType.NEX;
		} else if (code.equals("IME")) {
			return ServiceLevelType.IME;
		} else {
			return ServiceLevelType.STD;
		}
		
	}

	
}
