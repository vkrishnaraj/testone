package com.bagnet.nettracer.ws.wn.onhandscanning;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.communications.Activity;
import com.bagnet.nettracer.tracing.service.IncidentActivityService;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.DomainUtils;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.taskmanager.InboundTasksUtils;
import com.bagnet.nettracer.ws.core.WSCoreOHDUtil;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger;
import com.bagnet.nettracer.ws.wn.onhandscanning.AddBagForLZResponseDocument.AddBagForLZResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.CreateUpdateOnhandResponseDocument.CreateUpdateOnhandResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.EchoResponseDocument.EchoResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.IsValidUserResponseDocument.IsValidUserResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.LookupOnhandLZResponseDocument.LookupOnhandLZResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.LookupOnhandReturnResponseDocument.LookupOnhandReturnResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.ReturnOnhandResponseDocument.ReturnOnhandResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.pojo.xsd.ServiceResponse;

public class OnhandScanningServiceImplementation extends OnhandScanningServiceSkeleton{
	
	private static Logger logger = Logger.getLogger(OnhandScanningServiceImplementation.class);
	
	
	/**
	 * Since IncidentActivityService uses spring to instantiate and currently our junit test suite does not support spring,
	 * we need to provided a setter to allow junit to set a mock IncidentActivityService
	 */
	private IncidentActivityService incidentActivityService;
	
	public IncidentActivityService getIncidentActivityService() {
		if(incidentActivityService == null){
			incidentActivityService = (IncidentActivityService) SpringUtils.getBean(TracingConstants.INCIDENT_ACTIVITY_SERVICE_BEAN);
		}
		return incidentActivityService;
	}


	public void setIncidentActivityService(
			IncidentActivityService incidentActivityService) {
		this.incidentActivityService = incidentActivityService;
	}
	
	
	public static String STATUS_CREATE = "CREATE";
	public static String STATUS_UPDATE = "UPDATE";
	public static String STATUS_RETURN_ALLOWED = "Return Allowed";
	public static String STATUS_RETURN_ASSOCIATED_REPORT = "Cannot Return, associated report";
	public static String STATUS_RETURN_NO_OHD = "Cannot Return, does not exist";
	public static String STATUS_RETURN_LZ_UPDATE_REQUIRED = "LZ Update Required";
	public static String STATUS_RETURN_LZ_UPDATE_ITEM_TYPE_REQUIRED = "Type of Item Update Required";
	public static String STATUS_RETURN_UPDATE_SUCCESS = "Successful Update";
	public static String STATUS_RETURN_CREATE_UPDATE_SUCCESS = "Successful Create/Update";
	public static String STATUS_RETURN_CREATE_OHD = "Create OHD";
	public static String STATUS_MANUAL_ADD_REQUIRED = "Manual Add Required";
	
	
	public static String ERROR_LOAD_OHD_FAIL = "Failed to load OHD, please contact NetTracer";
	public static String ERROR_REQ_HOLDINGSTATION = "Please provide a valid holding station code";
	public static String ERROR_REQ_BAGTAG = "Please provide a valid tag number";
	public static String ERROR_REQ_FIRSTNAME = "Please provide first name";
	public static String ERROR_REQ_LASTNAME = "Please provide last name";
	public static String ERROR_REQ_COLOR = "Please provide color";
	public static String ERROR_REQ_TYPE = "Please provide type";
	public static String ERROR_REQ_ADDBAGLZ = "Color, Type, Type of Item Required";
	
	public static String REMARK_SCANNED = "Item Scanned";
	public static String REMARK_IMPROPER_FOWARD = "Item Improperly Forwarded";
	
	
	/**
	 * returns agent if properly authorized as by the following criteria:
	 *   Has valid username, password and companycode
	 *   Is a webservice user
	 *   Ignores account locks
	 * 
	 * @param isValidUser
	 */

	public IsValidUserResponseDocument isValidUser(IsValidUserDocument isValidUser){
		logger.info(isValidUser);
		IsValidUserResponseDocument resDoc = IsValidUserResponseDocument.Factory.newInstance();
		IsValidUserResponse res = resDoc.addNewIsValidUserResponse();
		com.bagnet.nettracer.ws.wn.onhandscanning.pojo.xsd.ServiceResponse serviceResponse = res.addNewReturn();
		
		if(isValidUser == null || isValidUser.getIsValidUser() == null){
			serviceResponse.setSuccess(false);
			serviceResponse.setValidUser(false);
			serviceResponse.addError("isValidUser request empty");
			logger.info(resDoc);
			return resDoc;
		}

		com.bagnet.nettracer.ws.wn.pojo.xsd.Authentication auth = isValidUser.getIsValidUser().getAuthentication();
		try {
			OnhandScanningServiceUtil.getAgent(auth);
		} catch (Exception e) {
			serviceResponse.setSuccess(false);
			serviceResponse.setValidUser(false);
			serviceResponse.addError(e.getMessage());
			logger.info(resDoc);
			return resDoc;
		}
		serviceResponse.setSuccess(true);
		serviceResponse.setValidUser(true);
		logger.info(resDoc);
		return resDoc;
	}


	/**
	 * Test echo service
	 * 
	 * @param echo
	 */

	public EchoResponseDocument echo(EchoDocument echo)
	{
		EchoResponseDocument resDoc = EchoResponseDocument.Factory.newInstance();
		EchoResponse res = resDoc.addNewEchoResponse();
		res.setReturn("Echo service: " + echo.getEcho().getS());
		return resDoc;
	}


	/**
	 * Searches for an OHD with the given tag number for the given station.
	 * If found, closes the OHD with disposition of "Owner Picked Up"
	 * 
	 * @param returnOnhand
	 */

	public ReturnOnhandResponseDocument returnOnhand(ReturnOnhandDocument returnOnhand)
	{
		logger.info(returnOnhand);
		ReturnOnhandResponseDocument resDoc = ReturnOnhandResponseDocument.Factory.newInstance();
		ReturnOnhandResponse res = resDoc.addNewReturnOnhandResponse();
		com.bagnet.nettracer.ws.wn.onhandscanning.pojo.xsd.ServiceResponse serviceResponse = res.addNewReturn();

		if(returnOnhand == null || returnOnhand.getReturnOnhand() == null){
			serviceResponse.setSuccess(false);
			serviceResponse.setValidUser(false);
			serviceResponse.addError("returnOnhand request empty");
			logger.info(resDoc);
			return resDoc;
		}
		
		Agent agent = null;
		com.bagnet.nettracer.ws.wn.pojo.xsd.Authentication auth = returnOnhand.getReturnOnhand().getAuthentication();
		try {
			agent = OnhandScanningServiceUtil.getAgent(auth);
		} catch (Exception e) {
			serviceResponse.setSuccess(false);
			serviceResponse.setValidUser(false);
			serviceResponse.addError(e.getMessage());
			logger.info(resDoc);
			return resDoc;
		}
		serviceResponse.setValidUser(true);
		
		Station holdingstation = StationBMO.getStationByCode(returnOnhand.getReturnOnhand().getHoldingStation(), agent.getCompanycode_ID());
		if(holdingstation == null){
			serviceResponse.setSuccess(false);
			serviceResponse.addError(ERROR_REQ_HOLDINGSTATION);
			logger.info(resDoc);
			return resDoc;
		}
		String ohdId = OnhandScanningServiceUtil.lookupBagtag(returnOnhand.getReturnOnhand().getTagNumber(), holdingstation.getStation_ID());
		if(ohdId == null){
			serviceResponse.setSuccess(false);
			serviceResponse.addError("No OHD exists with this baggage tag number at this station");
			logger.info(resDoc);
			return resDoc;
		}
		
		OhdBMO obmo = new OhdBMO();
		OHD ohd = obmo.findOHDByID(ohdId);
		if(ohd == null){
			serviceResponse.setSuccess(false);
			serviceResponse.addError("Error load OHD, please contact NetTracer");
			logger.info(resDoc);
			return resDoc;
		}
		
		Status status = new Status();
		status.setStatus_ID(TracingConstants.OHD_STATUS_CLOSED);
		ohd.setStatus(status);
		
		Status disposalStatus = new Status();
		disposalStatus.setStatus_ID(TracingConstants.OHD_STATUS_OWNER_PICKED_UP);
		ohd.setDisposal_status(disposalStatus);
		
		Remark r = new Remark();
		r.setAgent(agent);
		r.setOhd(ohd);
		r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
		r.setRemarktype(TracingConstants.REMARK_CLOSING);
		r.setRemarktext("Onhand has been returned");
		ohd.getRemarks().add(r);//unchecked type to be addressed in NT-1733
		
		if(obmo.insertOHD(ohd, ohd.getAgent())){
			try {
				serviceResponse = ohdToWSOHD(OhdBMO.getOHDByID(ohd.getOHD_ID(),null), serviceResponse);
				serviceResponse.setSuccess(true);
				serviceResponse.setCreateUpdateIndicator(STATUS_UPDATE);
				logger.info(resDoc);
				return resDoc;
			} catch (Exception e) {
				e.printStackTrace();
			}

		} 

		serviceResponse.setSuccess(false);
		serviceResponse.addError("Error updating OHD, please contact NetTracer");
		logger.info(resDoc);
		return resDoc;
	}


	/**
	 * Save/Updates BagDrop
	 * 
	 * @param saveBagDropTime
	 */

	public com.bagnet.nettracer.ws.wn.onhandscanning.SaveBagDropTimeResponseDocument saveBagDropTime(com.bagnet.nettracer.ws.wn.onhandscanning.SaveBagDropTimeDocument saveBagDropTime)
	{
		return BagDropUtil.saveBagDropTime(saveBagDropTime);
	}


	/**
	 * Searches for an existing open OHD for a given bagtag for the given holding station.
	 * If one is found, update the onhand.
	 * 
	 * If there is not an existing OHD fitting the previous criteria, check if there is an incoming OHD
	 * for the given bagtag.  If one exists, receive it and update the OHD.
	 * 
	 * If there is no OHD or incoming OHD is found, create a new OHD provided that all SWA required fields are met.
	 * 
	 * @param createUpdateOnhand
	 */

	public CreateUpdateOnhandResponseDocument createUpdateOnhand(CreateUpdateOnhandDocument createUpdateOnhand)
	{
		//Authentication
		logger.info(createUpdateOnhand);
		CreateUpdateOnhandResponseDocument resDoc = CreateUpdateOnhandResponseDocument.Factory.newInstance();
		CreateUpdateOnhandResponse res = resDoc.addNewCreateUpdateOnhandResponse();
		ServiceResponse serviceResponse = res.addNewReturn();

		if(createUpdateOnhand == null || createUpdateOnhand.getCreateUpdateOnhand() == null || createUpdateOnhand.getCreateUpdateOnhand().getOnhand() == null){
			serviceResponse.setSuccess(false);
			serviceResponse.setValidUser(false);
			serviceResponse.addError("ohd request empty");
			logger.info(resDoc);
			return resDoc;
		}

		Agent agent = null;
		com.bagnet.nettracer.ws.wn.pojo.xsd.Authentication auth = createUpdateOnhand.getCreateUpdateOnhand().getAuthentication();
		try {
			agent = OnhandScanningServiceUtil.getAgent(auth);
			serviceResponse.setValidUser(true);
		} catch (Exception e) {
			serviceResponse.setSuccess(false);
			serviceResponse.setValidUser(false);
			serviceResponse.addError(e.getMessage());
			logger.info(resDoc);
			return resDoc;
		}
		
		WSOHD wsohd = createUpdateOnhand.getCreateUpdateOnhand().getOnhand();
		String posId = createUpdateOnhand.getCreateUpdateOnhand().getPositionId();
		boolean lateCheckInc = createUpdateOnhand.getCreateUpdateOnhand().getLateCheckIndicator();
		boolean hasLateCheckInc = createUpdateOnhand.getCreateUpdateOnhand().isSetLateCheckIndicator();
		
		//Populated default information that is required by NetTracer but not required by SWA
		populateMissingOHDFields(wsohd, agent);
		
		//check bagtag and holding station, these fields are required for looking up an existing OHD
		boolean hasReqFields = true;
		Station holdingstation = StationBMO.getStationByCode(wsohd.getHoldingStation(), agent.getCompanycode_ID());
		if(wsohd.getBagtagnum() == null || wsohd.getBagtagnum().length() == 0){
			serviceResponse.addError(ERROR_REQ_BAGTAG);
			hasReqFields = false;
		}
		if(holdingstation == null){
			serviceResponse.addError(ERROR_REQ_HOLDINGSTATION);
			hasReqFields = false;
		}
		if(!hasReqFields){
			serviceResponse.setSuccess(false);
			serviceResponse.setReturnStatus(STATUS_MANUAL_ADD_REQUIRED);
			logger.info(resDoc);
			return resDoc;
		}
		
		//Find existing OHD
		String ohdId = null;
		OHD incomingOHD = null;
		if((ohdId =  OnhandScanningServiceUtil.lookupBagtag(wsohd.getBagtagnum(), holdingstation.getStation_ID()))!= null){
			//existing OHD found, update
			wsohd.setOHDID(ohdId);
			try {
				OHD ntohd = wsohdToOHD(wsohd, posId, lateCheckInc);
				if(updateExistingOhd(ntohd, hasLateCheckInc)){
					serviceResponse = ohdToWSOHD(OhdBMO.getOHDByID(ntohd.getOHD_ID(),null), serviceResponse);
					serviceResponse.setSuccess(true);
					serviceResponse.setCreateUpdateIndicator(STATUS_UPDATE);
					logger.info(resDoc);
					return resDoc;
				} else {
					serviceResponse.addError("unable to insert ohd into NT, please contact NT support");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if((incomingOHD = OHDUtils.getBagTagNumberIncomingToStation(wsohd.getBagtagnum(),holdingstation)) != null){
			//receive incoming bag
			wsohd.setOHDID(incomingOHD.getOHD_ID());
			WSCoreOHDUtil util = new WSCoreOHDUtil();
			try {
				OHD ntohd = wsohdToOHD(wsohd, posId, lateCheckInc);
				if(updateExistingOhd(ntohd, hasLateCheckInc)){
					util.properlyHandleForwardedOnHand(incomingOHD, agent, holdingstation);
					serviceResponse = ohdToWSOHD(OhdBMO.getOHDByID(ntohd.getOHD_ID(),null), serviceResponse);
					serviceResponse.setSuccess(true);
					serviceResponse.setCreateUpdateIndicator(STATUS_UPDATE);
					logger.info(resDoc);
					return resDoc;
				} else {
					serviceResponse.addError("unable to insert ohd into NT, please contact NT support");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			//Creating new OHD, verify required fields as specified by SWA
			if(!addOhdReqFields(wsohd, serviceResponse)){
				serviceResponse.setSuccess(false);
				serviceResponse.setReturnStatus(STATUS_MANUAL_ADD_REQUIRED);
				logger.info(resDoc);
				return resDoc;
			}
			
			try {
				OHD ntohd = wsohdToOHD(wsohd, posId, lateCheckInc);
				OhdBMO obmo = new OhdBMO();
				if(obmo.insertOHD(ntohd, ntohd.getAgent())){
					serviceResponse = ohdToWSOHD(OhdBMO.getOHDByID(ntohd.getOHD_ID(),null), serviceResponse);
					serviceResponse.setSuccess(true);
					serviceResponse.setCreateUpdateIndicator(STATUS_CREATE);
					logger.info(resDoc);
					return resDoc;
				} else {
					serviceResponse.addError("unable to insert ohd into NT, please contact NT support");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		serviceResponse.setSuccess(false);
		serviceResponse.addError(wsohd.getErrorcode());
		logger.info(resDoc);
		return resDoc;
	}

	
	
	/**
	 * Checks the required field for creating an OHD.  
	 * The set of required fields will differ based on the presents of an itinerary segment with a flight number.
	 * 
	 * As per NT-1822, the passenger name is now part of the first element of the Passenger array as opposed to the name on the bag
	 * 
	 * @param wsohd
	 * @param response
	 * @return
	 */
	protected boolean addOhdReqFields(WSOHD wsohd, ServiceResponse response){
		boolean success = true;
		boolean hasFlightNumber = false;
		if(wsohd != null && wsohd.getItinerariesArray() != null && wsohd.getItinerariesArray().length > 0){
			WSItinerary[] itinArray = wsohd.getItinerariesArray();
			for(int i = 0; i < itinArray.length; i++){
				if(itinArray[i] != null && itinArray[i].getFlightnum() != null && itinArray[i].getFlightnum().length() > 0){
					hasFlightNumber = true;
					break;
				}
			}
		}
		
		if(hasFlightNumber){//SWA defines this as a 2D tag
			if(wsohd.getBagtagnum() == null || wsohd.getBagtagnum().length() == 0){
				response.addError(ERROR_REQ_BAGTAG);
				success = false;
			}
			if(wsohd.getHoldingStation() == null || wsohd.getHoldingStation().length() == 0){
				response.addError(ERROR_REQ_HOLDINGSTATION);
				success = false;
			}
			if(wsohd.getPassengersArray() == null || wsohd.getPassengersArray().length == 0) {
				response.addError(ERROR_REQ_FIRSTNAME);
				response.addError(ERROR_REQ_LASTNAME);
				success = false;
			} else {
				WSPassenger pax = wsohd.getPassengersArray(0);
				if(pax.getFirstname() == null || pax.getFirstname().length() == 0){
					response.addError(ERROR_REQ_FIRSTNAME);
					success = false;
				}
				if(pax.getLastname() == null || pax.getLastname().length() == 0){
					response.addError(ERROR_REQ_LASTNAME);
					success = false;
				}
			}
		} else if (wsohd != null){//SWA defines this as a 1D tag
			if(wsohd.getBagtagnum() == null || wsohd.getBagtagnum().length() == 0){
				response.addError(ERROR_REQ_BAGTAG);
				success = false;
			}
			if(wsohd.getHoldingStation() == null || wsohd.getHoldingStation().length() == 0){
				response.addError(ERROR_REQ_HOLDINGSTATION);
				success = false;
			}
			if(wsohd.getColor() == null || wsohd.getColor().length() == 0){
				response.addError(ERROR_REQ_COLOR);
				success = false;
			}
			if(wsohd.getType() == null || wsohd.getType().length() == 0){
				response.addError(ERROR_REQ_TYPE);
				success = false;
			}
		} else {
			//ohd object is null
			response.addError("ohd request empty");
			success = false;
		}
		return success;
	}
	
	
	/**
	 * Populates default values for the the OHD fields that are required by NetTracer 
	 * that are not required by SWA.
	 * 
	 * @param wsohd
	 * @param agent
	 */
	private void populateMissingOHDFields(WSOHD wsohd, Agent agent){
		if(wsohd.getStatus() == null){
			wsohd.setStatus("Open");
		}
		wsohd.setAgent(agent.getUsername());
		if(wsohd.getCompanycodeId() == null || wsohd.getCompanycodeId().isEmpty()){
			wsohd.setCompanycodeId(agent.getCompanycode_ID());
		}
		if(wsohd.getFoundAtStation() == null || wsohd.getFoundAtStation().length() == 0){
			wsohd.setFoundAtStation(wsohd.getHoldingStation());
		}
		if(wsohd.getXdescelement1() == null || wsohd.getXdescelement1().length() == 0){
			wsohd.setXdescelement1("X");
		}
		if(wsohd.getXdescelement2() == null || wsohd.getXdescelement2().length() == 0){
			wsohd.setXdescelement2("X");
		}
		if(wsohd.getXdescelement3() == null || wsohd.getXdescelement3().length() == 0){
			wsohd.setXdescelement3("X");
		}
		/**
		 * Removed FoundDateTime check due to SimpleValue Calendar value not
		 * being able to factor in time to the date, regardless of actions
		 * against it, resulting in DateTimes of the current day at Midnight,
		 * which when localized can lead to an inaccurate date, such as the
		 * previous day
		 * 
		 * Refer to WStoOHD_Mapping method in WSCoreOHDUtil.java class for
		 * setting Null FoundDates
		 * 
		 * Refactoring ticket to further investigate this issue - NT-1638
		 */
	}
	
	/**
	 * Updates an existing OHD.
	 * 
	 * When updating an existing OHD for SWA, only update the following fields if provided:
	 *   color
	 *   type
	 *   claimnum
	 *   storage location
	 *   first name
	 *   last name
	 *   record locator
	 *   lateCheckInd
	 *   posId
	 *   itinerary - 2D tag is defined as an one or more itinerary segments with a flight number.
	 *   	if the provided itinerary has a flight number (2D tag), replace the existing itinerary with the one provided.
	 *   
	 *   	A 1D tag is defined as single itinerary segment with no flight number (only dest station). 
	 *   	If 1D tag is provided, update the existing itinerary's last segment's dst station with the one provided.  In the case of updating an existing
	 *   	ohd with no segments, create a new segment with destination only. 
	 *   
	 *   passenger name - only a single passenger object with a first and last name is to be provided.  In the event that multiple passengers are provided,
	 *   	only use the first passenger.  In update an existing passenger, only update the first and last name if a first and last name is provided
	 *   	(the service can be provided empty string in which update the existing the existing passenger name to empty string).  If more than one passenger already
	 *   	exists, update the first passenger.  If no passengers exists, create a new passenger
	 *   	
	 * 
	 * @param ohd
	 * @param updateLateCheck - since lateCheckInd is a boolean, 
	 * 							we have to pass in the hasLateCheckIndicator value from the web service
	 * @return
	 */
	private boolean updateExistingOhd(OHD ohd, boolean updateLateCheck){
		OhdBMO obmo = new OhdBMO();
		OHD oldohd = obmo.findOHDByID(ohd.getOHD_ID());
		
		if(oldohd != null && ohd!= null){
			if(ohd.getColor() != null && ohd.getColor().length() > 0){
				oldohd.setColor(ohd.getColor());
			}
			if(ohd.getStorage_location() != null && ohd.getStorage_location().length() > 0){
				oldohd.setStorage_location(ohd.getStorage_location());
			}
			if(ohd.getClaimnum() != null && ohd.getClaimnum().length() > 0){
				oldohd.setClaimnum(ohd.getClaimnum());
			}
			if(ohd.getType() != null && ohd.getType().length() > 0){
				oldohd.setType(ohd.getType());
			}
			if(ohd.getFirstname() != null && ohd.getFirstname().length() > 0){
				oldohd.setFirstname(ohd.getFirstname());
			}
			if(ohd.getLastname() != null && ohd.getLastname().length() > 0){
				oldohd.setLastname(ohd.getLastname());
			}
			if(ohd.getRecord_locator() != null && ohd.getRecord_locator().length() > 0){
				oldohd.setRecord_locator(ohd.getRecord_locator());
			}
			if(ohd.getItinerary() != null && ohd.getItinerary().size() > 0){
				//check if the itinerary provide contains a single segment with no flight number (1D)
				boolean oneDTag = false;
				boolean hasExistingItinerary = oldohd.getItinerary() != null && oldohd.getItinerary().size() > 0;
				OHD_Itinerary segment = null;
				if(ohd.getItinerary().size() == 1){
					segment = ohd.getItinerary().iterator().next();
					if(segment != null && (segment.getFlightnum() == null || segment.getFlightnum().length() == 0)){
						oneDTag = true;
					}
				} 

				if(oneDTag && hasExistingItinerary){
					//has existing itin, update final segment with provided dest
					Iterator<OHD_Itinerary> i = oldohd.getItinerary().iterator();
					while(i.hasNext()){
						OHD_Itinerary itin = (OHD_Itinerary) i.next();
						if(!i.hasNext()){
							//final segment
							itin.setLegto(segment.getLegto());
						}
					} 
				} else {
					//2D tag or 1D tag with no existing itin to update, replace the itinerary with one provided
					oldohd.setItinerary(ohd.getItinerary());
					Iterator<OHD_Itinerary> i = oldohd.getItinerary().iterator();
					while(i.hasNext()){
						OHD_Itinerary itin = (OHD_Itinerary) i.next();
						itin.setOhd(oldohd);
					}
				}
			}
			if(updateLateCheck){
				oldohd.setLateCheckInd(ohd.getLateCheckInd());
			}
			
			if(ohd.getPosId() != null && ohd.getPosId().length() > 0){
				oldohd.setPosId(ohd.getPosId());
			}
			
			if(ohd.getPassengers() != null && ohd.getPassengers().size() > 0){
				//if multiple passengers, only use first one provided
				OHD_Passenger pax = ohd.getPassengers().iterator().next();
				if(pax.getFirstname() != null || pax.getLastname() != null){
					OHD_Passenger oldpax = null;
					if(oldohd.getPassengers() != null && oldohd.getPassengers().size() > 0){
						//passenger set exists, update first passenger
						oldpax = oldohd.getPassengers().iterator().next();
					} else {
						//Passenger set doesn't exist, create one
						LinkedHashSet<OHD_Passenger> oldpaxset = new LinkedHashSet<OHD_Passenger>();
						oldpax = new OHD_Passenger();
						oldpax.setOhd(oldohd);
						oldpaxset.add(oldpax);
						oldohd.setPassengers(oldpaxset);
					}
					
					if(pax.getFirstname() != null){
						oldpax.setFirstname(pax.getFirstname());
					}
					if(pax.getLastname() != null){
						oldpax.setLastname(pax.getLastname());
					}
				}
			}
			
			return obmo.insertOHD(oldohd, ohd.getAgent());
		} else {
			return false;
		}
		

	}


	/**
	 * This service first looks up if there is an incoming bag.  If so, receive it and respond with "UPDATE OHD"
	 * If there is no incoming bag, check if there is a OHD or Incident that already exists, update it and return "Successful Update"
	 * If no ohd or incident can be found, return "CREATE OHD"
	 * 
	 * @param lookupOnhandLZ
	 */

	public com.bagnet.nettracer.ws.wn.onhandscanning.LookupOnhandLZResponseDocument lookupOnhandLZ(com.bagnet.nettracer.ws.wn.onhandscanning.LookupOnhandLZDocument lookupOnhandLZ)
	{
		logger.info(lookupOnhandLZ);
		LookupOnhandLZResponseDocument resDoc = LookupOnhandLZResponseDocument.Factory.newInstance();
		LookupOnhandLZResponse res = resDoc.addNewLookupOnhandLZResponse();
		com.bagnet.nettracer.ws.wn.onhandscanning.pojo.xsd.ServiceResponse serviceResponse = res.addNewReturn();

		if(lookupOnhandLZ == null || lookupOnhandLZ.getLookupOnhandLZ() == null){
			serviceResponse.setSuccess(false);
			serviceResponse.setValidUser(false);
			serviceResponse.addError("lookupOnhandReturn request empty");
			logger.info(resDoc);
			return resDoc;
		}

		Agent agent = null;
		com.bagnet.nettracer.ws.wn.pojo.xsd.Authentication auth = lookupOnhandLZ.getLookupOnhandLZ().getAuthentication();
		try {
			agent = OnhandScanningServiceUtil.getAgent(auth);
			serviceResponse.setValidUser(true);
		} catch (Exception e) {
			serviceResponse.setSuccess(false);
			serviceResponse.setValidUser(false);
			serviceResponse.addError(e.getMessage());
			logger.info(resDoc);
			return resDoc;
		}
		serviceResponse.setValidUser(true);

		//Check required fields
		Station holdingstation = StationBMO.getStationByCode(lookupOnhandLZ.getLookupOnhandLZ().getHoldingStation(), agent.getCompanycode_ID());
		String bagtag = lookupOnhandLZ.getLookupOnhandLZ().getTagNumber();
		boolean hasReqFields = true;
		if(holdingstation == null){
			serviceResponse.addError(ERROR_REQ_HOLDINGSTATION);
		}
		if(bagtag == null || bagtag.length() == 0){
			serviceResponse.addError(ERROR_REQ_HOLDINGSTATION);
		}
		if(!hasReqFields){
			serviceResponse.setSuccess(false);
			logger.info(resDoc);
			return resDoc;
		}
		
		//Handle incoming OHD
		OHD incomingOHD = OHDUtils.getBagTagNumberIncomingToStation(lookupOnhandLZ.getLookupOnhandLZ().getTagNumber(),holdingstation);
		if(incomingOHD != null){
			/** 
			 * per SWA requirement, NetTracer is to not receive the bag, only to acknowledge it by updating the lastModified datetime (NT-1991) 
			 * 
			 * the lastModified is automatically updated whenever the OHD is saved, thus save the OHD we just loaded
			 **/
			OhdBMO obmo = new OhdBMO();
			obmo.insertOHD(incomingOHD, agent);

			try{
				serviceResponse = ohdToWSOHD(obmo.findOHDByID(incomingOHD.getOHD_ID()),serviceResponse);
			} catch (Exception e) {
				e.printStackTrace();
				serviceResponse.setSuccess(false);
				serviceResponse.addError("Failed to load OHD, please contact NetTracer");
				logger.info(resDoc);
				return resDoc;
			}
			serviceResponse.setSuccess(true);
			serviceResponse.setCreateUpdateIndicator("");//as per SWA requirement, returning a empty string for the indicator NT-1991
			serviceResponse.setReturnStatus(STATUS_RETURN_LZ_UPDATE_ITEM_TYPE_REQUIRED);
			logger.info(resDoc);
			return resDoc;
		}
		
		
		//Find OHD or incident at LZ and update appropriately
		String tagNumber = lookupOnhandLZ.getLookupOnhandLZ().getTagNumber();
		String id = null;
		OHD ohd = null;
		if((id = OnhandScanningServiceUtil.lzDeleteLast5Days(tagNumber, agent.getCompanycode_ID())) != null){
			ohd = handleLzDeleteLast5Days(id, agent, holdingstation);
		} else if ((id = OnhandScanningServiceUtil.lzDamagedList(tagNumber, holdingstation.getStation_ID())) != null){
			ohd = handleLzDamagedList(id, agent);
		} else if ((id = OnhandScanningServiceUtil.lzDamagedBagBSO(tagNumber, agent.getCompanycode_ID())) != null){
			ohd = handleLzDamagedBagBSO(id, agent, holdingstation);
		} else if ((id = OnhandScanningServiceUtil.lzTBI(tagNumber, holdingstation.getStation_ID())) != null){
			ohd = handleLzTBI(id, agent);
		} else if ((id = OnhandScanningServiceUtil.lz45Days(tagNumber, holdingstation.getStation_ID())) != null){
			ohd = handleLz45Days(id, agent);
		}
		
		//Send web service response
		if(ohd != null){
			try{
				serviceResponse = ohdToWSOHD(ohd, serviceResponse);
			} catch (Exception e) {
				e.printStackTrace();
				serviceResponse.setSuccess(false);
				serviceResponse.addError("Failed to load OHD, please contact NetTracer");
				logger.info(resDoc);
				return resDoc;
			}
			serviceResponse.setSuccess(true);
			serviceResponse.setCreateUpdateIndicator(STATUS_UPDATE);
			serviceResponse.setReturnStatus(STATUS_RETURN_UPDATE_SUCCESS);
			logger.info(resDoc);
			return resDoc;
		} else {
			serviceResponse.setSuccess(false);
			serviceResponse.setReturnStatus(STATUS_RETURN_CREATE_OHD);
			serviceResponse.addError(ERROR_REQ_ADDBAGLZ);
			logger.info(resDoc);
			return resDoc;
		}
	}

	/**
	 * Updates the OHD status to 'TBI' updates the holding station.
	 * Adds 'Improper Forward' and 'Scanned' remarks
	 * 
	 * @param id
	 * @param agent
	 * @param holdingstation
	 * @return
	 */
	private OHD handleLzDeleteLast5Days(String id, Agent agent, Station holdingstation){
		OhdBMO obmo = new OhdBMO();
		OHD ohd = obmo.findOHDByID(id);
		Status status = new Status();
		status.setStatus_ID(TracingConstants.OHD_STATUS_TO_BE_INVENTORIED);
		ohd.setStatus(status);
		ohd.setHoldingStation(holdingstation);
		addOHDUpdateRemark(ohd, agent, REMARK_IMPROPER_FOWARD);
		addOHDUpdateRemark(ohd, agent, REMARK_SCANNED);
		obmo.insertOHD(ohd, ohd.getAgent());
		return ohd;
	}
	
	/**
	 * Updates damaged incident (PND state and Activity code to be implemented in iteration 2)
	 * Adds 'Scanned' remark
	 * Copies elements of incident into OHD
	 * 
	 * @param id
	 * @param agent
	 * @return
	 */
	private OHD handleLzDamagedList(String id, Agent agent){
		IncidentBMO ibmo = new IncidentBMO();
		Incident inc = IncidentBMO.getIncidentByID(id, null);
		//TODO PND state (to be implemented in a later ticket)
		//TODO Activity code 10 (to be implemented in a later ticket)
		addIncidentUpdateRemark(inc, agent, REMARK_SCANNED);
		try {
			ibmo.saveAndAuditIncident(false, inc, agent, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return copyIncidentToOHD(inc);
	}

	/**
	 * Updates damaged incident assigned station, sets the rxTimestamp and adds 'Improper Forward' and 'Scanned' remarks
	 * Copies elements of incident into OHD
	 * 
	 * @param id
	 * @param agent
	 * @param holdingstation
	 * @return
	 */
	private OHD handleLzDamagedBagBSO(String id, Agent agent, Station holdingstation){
		IncidentBMO ibmo = new IncidentBMO();
		Incident inc = IncidentBMO.getIncidentByID(id, null);

		//Assign to LZ and set damaged receive by LZ timestamp
		inc.setStationassigned(holdingstation);
		inc.setRxTimestamp(DateUtils.convertToGMTDate(new Date()));
		
		//Add remarks
		addIncidentUpdateRemark(inc, agent, REMARK_IMPROPER_FOWARD);
		addIncidentUpdateRemark(inc, agent, REMARK_SCANNED);
		try {
			ibmo.saveAndAuditIncident(false, inc, agent, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		Activity activity = getIncidentActivityService().getActivity(TracingConstants.ACTIVITY_CODE_RECEIVED_DAMAGED_ITEM);
		long activityId = getIncidentActivityService().save(DomainUtils.createIncidentActivity(inc, activity, agent));
		
		if(activityId > 0){
			InboundTasksUtils.createDamagedTask(inc, agent, activity, activityId);
		}
		
		return copyIncidentToOHD(inc);
	}

	/**
	 * Add 'Scanned' remark
	 * 
	 * @param id
	 * @param agent
	 * @return
	 */
	private OHD handleLzTBI(String id, Agent agent){
		OhdBMO obmo = new OhdBMO();
		OHD ohd = obmo.findOHDByID(id);
		addOHDUpdateRemark(ohd, agent, REMARK_SCANNED);
		obmo.insertOHD(ohd, ohd.getAgent());
		return ohd;
	}


	/**
	 * Add 'Scanned' remark
	 * 
	 * @param id
	 * @param agent
	 * @return
	 */
	private OHD handleLz45Days(String id, Agent agent){
		OhdBMO obmo = new OhdBMO();
		OHD ohd = obmo.findOHDByID(id);
		addOHDUpdateRemark(ohd, agent, REMARK_SCANNED);
		obmo.insertOHD(ohd, ohd.getAgent());
		return ohd;
	}
	
	/**
	 * Adds ohd remark
	 * 
	 * @param ohd
	 * @param agent
	 * @param remarkText
	 */
	private void addOHDUpdateRemark(OHD ohd, Agent agent, String remarkText){
		Remark r = new Remark();
		r.setAgent(agent);
		r.setOhd(ohd);
		r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
		r.setRemarktype(TracingConstants.REMARK_REGULAR);
		r.setRemarktext(remarkText);
		ohd.getRemarks().add(r);//unchecked type to be addressed in NT-1733
	}
	
	/**
	 * Adds incident remark
	 * 
	 * @param inc
	 * @param agent
	 * @param remarkText
	 */
	private void addIncidentUpdateRemark(Incident inc, Agent agent, String remarkText){
		Remark r = new Remark();
		r.setAgent(agent);
		r.setIncident(inc);
		r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
		r.setRemarktype(TracingConstants.REMARK_REGULAR);
		r.setRemarktext(remarkText);
		inc.getRemarks().add(r);
	}
	
	
	
	/**
	 * For handling bags at SWA LZ, there are cases where the OHD is associated with a damaged incident.
	 * For those cases, SWA wants certain elements of the incident to be returned in 
	 * the onhand portion of the response
	 * 
	 * @param inc
	 * @return
	 */
	private OHD copyIncidentToOHD(Incident inc){
		if(inc == null){
			return null;
		}
		OHD ohd = new OHD();
		Status status = new Status();
		status.setStatus_ID(TracingConstants.OHD_STATUS_OPEN);
		//These fields are required for the WS mapper
		ohd.setStatus(status);
		ohd.setFoundAtStation(inc.getStationcreated());
		ohd.setHoldingStation(inc.getStationassigned());
		ohd.setAgent(inc.getAgent());
		ohd.setFounddate(inc.getCreatedate());
		ohd.setFoundtime(inc.getCreatetime());
		ohd.setRecord_locator(inc.getRecordlocator());
		
		if(inc.getItemlist() != null && inc.getItemlist().size() > 0){
			ohd.setColor(inc.getItemlist().get(0).getColor());
			ohd.setType(inc.getItemlist().get(0).getBagtype());
			ohd.setClaimnum(inc.getItemlist().get(0).getClaimchecknum());
		}
		if(inc.getPassengers() != null && inc.getPassengers().size() > 0){
			ohd.setFirstname(inc.getPassengers().iterator().next().getFirstname());
			ohd.setLastname(inc.getPassengers().iterator().next().getLastname());
			
			for(Passenger incPax:inc.getPassengers()){
				OHD_Passenger newPax = new OHD_Passenger();
				newPax.setFirstname(incPax.getFirstname());
				newPax.setLastname(incPax.getLastname());
				newPax.setMiddlename(incPax.getMiddlename());

				if(incPax.getAddresses() != null && incPax.getAddresses().size() > 0){
					for(com.bagnet.nettracer.tracing.db.Address incAddr:incPax.getAddresses()){
						OHD_Address newAddr = new OHD_Address();
						newAddr.setAddress1(incAddr.getAddress1());
						newAddr.setAddress2(incAddr.getAddress2());
						newAddr.setCity(incAddr.getCity());
						newAddr.setState_ID(incAddr.getState_ID());
						newAddr.setProvince(incAddr.getProvince());
						newAddr.setZip(incAddr.getZip());
						newAddr.setCountrycode_ID(incAddr.getCountrycode_ID());
						newAddr.setAddresstype(incAddr.getAddresstype());
						
						newAddr.setEmail(incAddr.getEmail());
						newAddr.setAltphone(incAddr.getAltphone());
						newAddr.setHomephone(incAddr.getHomephone());
						newAddr.setMobile(incAddr.getMobile());
						newAddr.setPager(incAddr.getPager());
						newAddr.setWorkphone(incAddr.getWorkphone());
						
						newAddr.setOhd_passenger(newPax);
						newPax.addAddress(newAddr);
					}
				}

				if(ohd.getPassengers() == null){
					ohd.setPassengers(new LinkedHashSet<OHD_Passenger>());
				}
				newPax.setOhd(ohd);
				ohd.getPassengers().add(newPax);
			}
		}
		
		//have to manually copy itin since incidents and ohds uses two different Itinerary objects
		if(inc.getItinerary() != null){
			LinkedHashSet<OHD_Itinerary> s = new LinkedHashSet<OHD_Itinerary>();
			for(Itinerary i: inc.getItinerary()){
				OHD_Itinerary ohdi = new OHD_Itinerary();
				ohdi.setActarrivetime(i.getActarrivetime());
				ohdi.setActdeparttime(i.getActdeparttime());
				ohdi.setAirline(i.getAirline());
				ohdi.setArrivedate(i.getArrivedate());
				ohdi.setDepartdate(i.getDepartdate());
				ohdi.setDisactarrivetime(i.getDisactarrivetime());
				ohdi.setDisactdeparttime(i.getDisactdeparttime());
				ohdi.setDisarrivedate(i.getDisarrivedate());
				ohdi.setDisscharrivetime(i.getDisscharrivetime());
				ohdi.setDisschdeparttime(i.getDisschdeparttime());
				ohdi.setFlightnum(i.getFlightnum());
				ohdi.setItinerarytype(i.getItinerarytype());
				ohdi.setLegfrom(i.getLegfrom());
				ohdi.setLegfrom_type(i.getLegfrom_type());
				ohdi.setLegto(i.getLegto());
				ohdi.setLegto_type(i.getLegto_type());
				ohdi.setScharrivetime(i.getScharrivetime());
				ohdi.setSchdeparttime(i.getSchdeparttime());
				ohdi.setOhd(ohd);
				s.add(ohdi);
			}
			ohd.setItinerary(s);
		}
		
		return ohd;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param addBagForLZ
	 */

	public com.bagnet.nettracer.ws.wn.onhandscanning.AddBagForLZResponseDocument addBagForLZ(com.bagnet.nettracer.ws.wn.onhandscanning.AddBagForLZDocument addBagForLZ)
	{
		logger.info(addBagForLZ);
		AddBagForLZResponseDocument resDoc = AddBagForLZResponseDocument.Factory.newInstance();
		AddBagForLZResponse res = resDoc.addNewAddBagForLZResponse();
		com.bagnet.nettracer.ws.wn.onhandscanning.pojo.xsd.ServiceResponse serviceResponse = res.addNewReturn();

		if(addBagForLZ == null || addBagForLZ.getAddBagForLZ() == null || addBagForLZ.getAddBagForLZ().getOnhand() == null){
			serviceResponse.setSuccess(false);
			serviceResponse.setValidUser(false);
			serviceResponse.addError("lookupOnhandReturn request empty");
			logger.info(resDoc);
			return resDoc;
		}

		Agent agent = null;
		com.bagnet.nettracer.ws.wn.pojo.xsd.Authentication auth = addBagForLZ.getAddBagForLZ().getAuthentication();
		try {
			agent = OnhandScanningServiceUtil.getAgent(auth);
			serviceResponse.setValidUser(true);
		} catch (Exception e) {
			serviceResponse.setSuccess(false);
			serviceResponse.setValidUser(false);
			serviceResponse.addError(e.getMessage());
			logger.info(resDoc);
			return resDoc;
		}
		serviceResponse.setValidUser(true);
		
		WSOHD wsohd = addBagForLZ.getAddBagForLZ().getOnhand();
		boolean TBI = addBagForLZ.getAddBagForLZ().getTBI();
		String posId = addBagForLZ.getAddBagForLZ().getPositionId();
		boolean lateCheckInc = addBagForLZ.getAddBagForLZ().getLateCheckIndicator();
		
		
		
		//check bagtag and holding station, these fields are required for looking up an existing OHD
		boolean hasReqFields = true;
		Station holdingstation = StationBMO.getStationByCode(wsohd.getHoldingStation(), agent.getCompanycode_ID());
		if(wsohd.getBagtagnum() == null || wsohd.getBagtagnum().length() == 0){
			serviceResponse.addError(ERROR_REQ_BAGTAG);
			hasReqFields = false;
		}
		if(holdingstation == null){
			serviceResponse.addError(ERROR_REQ_HOLDINGSTATION);
			hasReqFields = false;
		}
		if(!hasReqFields){
			serviceResponse.setSuccess(false);
			serviceResponse.setReturnStatus(STATUS_RETURN_LZ_UPDATE_REQUIRED);
			logger.info(resDoc);
			return resDoc;
		}

		String ohdId = null;
		OHD incomingOHD = null;
		OhdBMO obmo = new OhdBMO();
		WSCoreOHDUtil util = new WSCoreOHDUtil();
		OHD ohd = null;
		
		//add/update ohd
		if(wsohd.getOHDID() != null && wsohd.getOHDID().length() > 0){
			ohd = OhdBMO.getOHDByID(wsohd.getOHDID(), null);
			updateLzOHD(wsohd, ohd, agent, TBI);
			serviceResponse.setCreateUpdateIndicator(STATUS_UPDATE);
		} else if ((ohdId = OnhandScanningServiceUtil.lookupBagtag(wsohd.getBagtagnum(), holdingstation.getStation_ID())) != null){
			ohd = OhdBMO.getOHDByID(ohdId, null);
			updateLzOHD(wsohd, ohd, agent, TBI);
			serviceResponse.setCreateUpdateIndicator(STATUS_UPDATE);
		} else if ((incomingOHD = OHDUtils.getBagTagNumberIncomingToStation(wsohd.getBagtagnum(),holdingstation)) != null){
			util.properlyHandleForwardedOnHand(incomingOHD, agent, holdingstation);
			ohd = OhdBMO.getOHDByID(incomingOHD.getOHD_ID(), null);
			updateLzOHD(wsohd, ohd, agent, TBI);
			serviceResponse.setCreateUpdateIndicator(STATUS_UPDATE);
		} else {
			populateMissingOHDFields(wsohd, agent);
			if(!addOhdReqFields(wsohd, serviceResponse)){
				serviceResponse.setSuccess(false);
				serviceResponse.setReturnStatus(STATUS_RETURN_LZ_UPDATE_REQUIRED);
				logger.info(resDoc);
				return resDoc;
			}
			try {
				ohd = wsohdToOHD(wsohd, posId, lateCheckInc);
				handleTBI(ohd, TBI);
				obmo.insertOHD(ohd, ohd.getAgent());
				serviceResponse.setCreateUpdateIndicator(STATUS_CREATE);
			} catch (Exception e) {
				e.printStackTrace();
				serviceResponse.setSuccess(false);
				serviceResponse.addError(e.getMessage());
				logger.info(resDoc);
				return resDoc;
			}
		} 
		
		try {
			serviceResponse = ohdToWSOHD(OhdBMO.getOHDByID(ohd.getOHD_ID(),null), serviceResponse);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResponse.setSuccess(false);
			serviceResponse.addError("Failed to load OHD, please contact NetTracer");
			logger.info(resDoc);
			return resDoc;
		}
		serviceResponse.setSuccess(true);
		serviceResponse.setValidUser(true);
		serviceResponse.setReturnStatus(STATUS_RETURN_CREATE_UPDATE_SUCCESS);
		logger.info(resDoc);
		return resDoc;
	}
	
	private void handleTBI(OHD ohd, boolean TBI){
		Status status = new Status();
		if(TBI){
			status.setStatus_ID(TracingConstants.OHD_STATUS_TO_BE_INVENTORIED);
			ohd.setStatus(status);
		} else {
			status.setStatus_ID(TracingConstants.OHD_STATUS_CLOSED);
			ohd.setStatus(status);
		}
	}
	
	/**
	 * Updates OHD for addBagForLZ.  Update itinerary, status and adds 'Scanned' remark
	 * 
	 * @param wsohd
	 * @param ohd
	 * @param agent
	 * @param TBI
	 */
	private void updateLzOHD(WSOHD wsohd, OHD ohd, Agent agent, boolean TBI){
		if(ohd != null){
			OhdBMO obmo = new OhdBMO();
			try {
				if(wsohd.getItinerariesArray() != null && wsohd.getItinerariesArray().length > 0){
					WSCoreOHDUtil util = new WSCoreOHDUtil();
					util.WStoOHDItinMapping(wsohd, ohd);
				}
				handleTBI(ohd, TBI);
				addOHDUpdateRemark(ohd, agent, REMARK_SCANNED);
				obmo.insertOHD(ohd, agent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * Determines if a onhand at a particular station returnable.
	 * 
	 * First checks if the bagtag exists at the station or if there is a incoming bag into that station.  If neither, then there is no onhand in NT to return.
	 * If incoming, receive the OHD.
	 * 
	 * With OHD at current station, check if there is a match incident, if there is, cannot return.  Otherwise the OHD is returnable.
	 * 
	 * @param lookupOnhandReturn
	 */

	public com.bagnet.nettracer.ws.wn.onhandscanning.LookupOnhandReturnResponseDocument lookupOnhandReturn(com.bagnet.nettracer.ws.wn.onhandscanning.LookupOnhandReturnDocument lookupOnhandReturn)
	{
		//Authenticate
		logger.info(lookupOnhandReturn);
		LookupOnhandReturnResponseDocument resDoc = LookupOnhandReturnResponseDocument.Factory.newInstance();
		LookupOnhandReturnResponse res = resDoc.addNewLookupOnhandReturnResponse();
		com.bagnet.nettracer.ws.wn.onhandscanning.pojo.xsd.ServiceResponse serviceResponse = res.addNewReturn();

		if(lookupOnhandReturn == null || lookupOnhandReturn.getLookupOnhandReturn() == null){
			serviceResponse.setSuccess(false);
			serviceResponse.setValidUser(false);
			serviceResponse.addError("lookupOnhandReturn request empty");
			serviceResponse.setReturnStatus(STATUS_MANUAL_ADD_REQUIRED);
			logger.info(resDoc);
			return resDoc;
		}

		Agent agent = null;
		com.bagnet.nettracer.ws.wn.pojo.xsd.Authentication auth = lookupOnhandReturn.getLookupOnhandReturn().getAuthentication();
		try {
			agent = OnhandScanningServiceUtil.getAgent(auth);
			serviceResponse.setValidUser(true);
		} catch (Exception e) {
			serviceResponse.setSuccess(false);
			serviceResponse.setValidUser(false);
			serviceResponse.addError(e.getMessage());
			serviceResponse.setReturnStatus(STATUS_MANUAL_ADD_REQUIRED);
			logger.info(resDoc);
			return resDoc;
		}
		serviceResponse.setValidUser(true);
		
		
		//Check required fields
		Station holdingstation = StationBMO.getStationByCode(lookupOnhandReturn.getLookupOnhandReturn().getHoldingStation(), agent.getCompanycode_ID());
		String tagNum = lookupOnhandReturn.getLookupOnhandReturn().getTagNumber();
		if(holdingstation == null  || tagNum == null){
			serviceResponse.setSuccess(false);
			if(holdingstation == null){
				serviceResponse.addError(ERROR_REQ_HOLDINGSTATION);
			}
			if(tagNum == null){
				serviceResponse.addError(ERROR_REQ_BAGTAG);
			}
			serviceResponse.setReturnStatus(STATUS_MANUAL_ADD_REQUIRED);
			logger.info(resDoc);
			return resDoc;
		}
		
		WSCoreOHDUtil util = new WSCoreOHDUtil();
		
		//Find a current OHD either at station or incoming
		String ohdId = null;
		OHD incomingOHD = null;
		OHD ohd = null;
		if((ohdId = OnhandScanningServiceUtil.lookupBagtag(tagNum, holdingstation.getStation_ID())) != null){
			//OHD in current station
			OhdBMO obmo = new OhdBMO();
			ohd = obmo.findOHDByID(ohdId);
		} else if ((incomingOHD = OHDUtils.getBagTagNumberIncomingToStation(tagNum,holdingstation)) != null){
			//Incoming OHD, receive
			util.properlyHandleForwardedOnHand(incomingOHD, agent, holdingstation);
			OhdBMO obmo = new OhdBMO();
			ohd = obmo.findOHDByID(incomingOHD.getOHD_ID());
		}
		
		//Handle response
		if(ohd != null){
			try{
				serviceResponse = ohdToWSOHD(ohd, serviceResponse);
			} catch (Exception e) {
				e.printStackTrace();
				serviceResponse.setSuccess(false);
				serviceResponse.addError(ERROR_LOAD_OHD_FAIL);
				serviceResponse.setReturnStatus(STATUS_MANUAL_ADD_REQUIRED);
				logger.info(resDoc);
				return resDoc;
			}
			if(ohd.getMatched_incident() != null && !ohd.getMatched_incident().isEmpty()){
				serviceResponse.setAssoicatedIncidentId(ohd.getMatched_incident());
				serviceResponse.setSuccess(true);
				serviceResponse.setReturnStatus(STATUS_RETURN_ASSOCIATED_REPORT);
				logger.info(resDoc);
				return resDoc;
			} else {
				serviceResponse.setSuccess(true);
				serviceResponse.setReturnStatus(STATUS_RETURN_ALLOWED);
				logger.info(resDoc);
				return resDoc;
			}
		} else {
			serviceResponse.setSuccess(true);
			serviceResponse.setReturnStatus(STATUS_RETURN_NO_OHD);
			logger.info(resDoc);
			return resDoc;
		}
	}
	
	/**
	 * Created customer wrapper for core mapper for the Southwest specific fields that we do not want to include in our core model
	 * to prevent the current clients from having to re-consume the core wsdl.
	 * 
	 * 
	 * @param wsohd
	 * @param posId
	 * @param lateCheckInd
	 * @return
	 * @throws Exception
	 */
	private static OHD wsohdToOHD(WSOHD wsohd, String posId, boolean lateCheckInd) throws Exception{
		WSCoreOHDUtil util = new WSCoreOHDUtil();
		OHD ohd = util.WStoOHD_Mapping(wsohd);
		ohd.setPosId(posId);
		ohd.setLateCheckInd(lateCheckInd);
		return ohd;
	}
	
	/**
	 * Created customer wrapper for core mapper for the Southwest specific fields that we do not want to include in our core model
	 * to prevent the current clients from having to re-consume the core wsdl.
	 * 
	 * @param ohd
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	private static ServiceResponse ohdToWSOHD(OHD ohd, ServiceResponse response) throws Exception{
		WSCoreOHDUtil util = new WSCoreOHDUtil();
		response.setOnhand(util.OHDtoWS_Mapping(ohd));
		response.setPositionId(ohd.getPosId());
		response.setLateCheckIndicator(ohd.getLateCheckInd());
		return response;
	}
	
}
