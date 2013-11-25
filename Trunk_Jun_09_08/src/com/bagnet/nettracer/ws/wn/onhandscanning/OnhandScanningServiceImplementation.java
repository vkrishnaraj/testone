package com.bagnet.nettracer.ws.wn.onhandscanning;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.ws.core.WSCoreOHDUtil;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD;
import com.bagnet.nettracer.ws.wn.onhandscanning.AddBagForLZResponseDocument.AddBagForLZResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.CreateUpdateOnhandResponseDocument.CreateUpdateOnhandResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.EchoResponseDocument.EchoResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.IsValidUserResponseDocument.IsValidUserResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.LookupOnhandLZResponseDocument.LookupOnhandLZResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.LookupOnhandReturnResponseDocument.LookupOnhandReturnResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.ReturnOnhandResponseDocument.ReturnOnhandResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.SaveBagDropTimeResponseDocument.SaveBagDropTimeResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.pojo.xsd.ServiceResponse;

public class OnhandScanningServiceImplementation extends OnhandScanningServiceSkeleton{
	
	private static Logger logger = Logger.getLogger(OnhandScanningServiceImplementation.class);
	
	public static String STATUS_CREATE = "CREATE";
	public static String STATUS_UPDATE = "UPDATE";
	public static String STATUS_RETURN_ALLOWED = "Return Allowed";
	public static String STATUS_RETURN_ASSOCIATED_REPORT = "Cannot Return, associated report";
	public static String STATUS_RETURN_NO_OHD = "Cannot Return, does not exist";
	public static String STATUS_RETURN_LZ_UPDATE_REQUIRED = "LZ Update Required";
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
	
	public static String REMARK_SCANNED = "Item Scanned";
	public static String REMARK_IMPROPER_FOWARD = "Item Improperly Forwarded";
	
	/**
	 * returns agent if properly authorized as by the following criteria:
	 *   Has valid username, password and companycode
	 *   Is a webservice user
	 *   Ignores account locks
	 * 
	 * @param auth
	 * @return
	 * @throws Exception
	 */
	protected Agent getAgent(com.bagnet.nettracer.ws.wn.pojo.xsd.Authentication auth) throws Exception{
		if(auth == null || auth.getSystemName() == null || auth.getSystemName().length() == 0 ||
				auth.getSystemPassword() == null || auth.getSystemPassword().length() == 0 ||
				auth.getAirlineCode() == null ||  auth.getAirlineCode().length() == 0){
			throw new Exception("Must provide username, password and airline code");
		} else {
			Agent agent = null;
			ActionMessages errors = new ActionMessages();
			agent = SecurityUtils.authUser(auth.getSystemName(), auth.getSystemPassword(), auth.getAirlineCode(), 0, errors);
			if(!errors.isEmpty()){
				@SuppressWarnings("unchecked")
				Iterator<ActionMessage> i = errors.get();
				while(i.hasNext()){
					ActionMessage message = (ActionMessage)i.next();
					if("error.user.lockedout".equals(message.getKey())){
						//As per Southwest requirements, account locks are not to be considered, skip and continue
						continue;
					} else {
						throw new Exception("Invalid username/password");
					}
				}
			}
			
			if(agent == null){
				throw new Exception("unable to authenticate user, please contact NetTracer");
			} else {
				if(agent.isWs_enabled()){
					return agent;
				} else {
					throw new Exception("user is not authorized for web services");
				}
			}
		}
	}
	
	
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
			getAgent(auth);
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
			agent = getAgent(auth);
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
	 * BagDrop feature to be implemented in later iteration
	 * 
	 * @param saveBagDropTime
	 */

	public com.bagnet.nettracer.ws.wn.onhandscanning.SaveBagDropTimeResponseDocument saveBagDropTime(com.bagnet.nettracer.ws.wn.onhandscanning.SaveBagDropTimeDocument saveBagDropTime)
	{
		logger.info(saveBagDropTime);
		SaveBagDropTimeResponseDocument resDoc = SaveBagDropTimeResponseDocument.Factory.newInstance();
		SaveBagDropTimeResponse res = resDoc.addNewSaveBagDropTimeResponse();
		com.bagnet.nettracer.ws.wn.onhandscanning.pojo.xsd.ServiceResponse serviceResponse = res.addNewReturn();
		
		if(saveBagDropTime == null || saveBagDropTime.getSaveBagDropTime() == null || saveBagDropTime.getSaveBagDropTime().getBagDrop() == null){
			serviceResponse.setSuccess(false);
			serviceResponse.setValidUser(false);
			serviceResponse.addError("bagdrop request empty");
			logger.info(resDoc);
			return resDoc;
		}

		com.bagnet.nettracer.ws.wn.pojo.xsd.Authentication auth = saveBagDropTime.getSaveBagDropTime().getAuthentication();
		try {
			getAgent(auth);
		} catch (Exception e) {
			serviceResponse.setSuccess(false);
			serviceResponse.setValidUser(false);
			serviceResponse.addError(e.getMessage());
			logger.info(resDoc);
			return resDoc;
		}
		serviceResponse.setValidUser(true);
		
		//TODO BagDrop is to be implemented in a later iteration.  In meantime, echo back request
		serviceResponse.setBagDrop(saveBagDropTime.getSaveBagDropTime().getBagDrop());
		serviceResponse.setSuccess(true);
		
		logger.info(resDoc);
		return resDoc;
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
			agent = getAgent(auth);
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
	 * Checks the required field for creating an OHD.  The set of required fields will differ based on the presents an itinerary.
	 * 
	 * @param wsohd
	 * @param response
	 * @return
	 */
	private boolean addOhdReqFields(WSOHD wsohd, ServiceResponse response){
		boolean success = true;
		if(wsohd != null && wsohd.getItinerariesArray() != null && wsohd.getItinerariesArray().length > 0){
			if(wsohd.getBagtagnum() == null || wsohd.getBagtagnum().length() == 0){
				response.addError(ERROR_REQ_BAGTAG);
				success = false;
			}
			if(wsohd.getHoldingStation() == null || wsohd.getHoldingStation().length() == 0){
				response.addError(ERROR_REQ_HOLDINGSTATION);
				success = false;
			}
			if(wsohd.getFirstname() == null || wsohd.getFirstname().length() == 0){
				response.addError(ERROR_REQ_FIRSTNAME);
				success = false;
			}
			if(wsohd.getLastname() == null || wsohd.getLastname().length() == 0){
				response.addError(ERROR_REQ_LASTNAME);
				success = false;
			}
		} else if (wsohd != null){
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
	 *   itinerary
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
				oldohd.setItinerary(ohd.getItinerary());
				Iterator<OHD_Itinerary> i = oldohd.getItinerary().iterator();
				while(i.hasNext()){
					OHD_Itinerary itin = (OHD_Itinerary) i.next();
					itin.setOhd(oldohd);
				}
			}
			if(updateLateCheck){
				oldohd.setLateCheckInd(ohd.getLateCheckInd());
			}
			
			if(ohd.getPosId() != null && ohd.getPosId().length() > 0){
				oldohd.setPosId(ohd.getPosId());
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
			agent = getAgent(auth);
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
		
		
		WSCoreOHDUtil util = new WSCoreOHDUtil();
		
		//Handle incoming OHD
		OHD incomingOHD = OHDUtils.getBagTagNumberIncomingToStation(lookupOnhandLZ.getLookupOnhandLZ().getTagNumber(),holdingstation);
		if(incomingOHD != null){
			//receive OHD
			util.properlyHandleForwardedOnHand(incomingOHD, agent, holdingstation);
			try{
				OhdBMO obmo = new OhdBMO();
				serviceResponse = ohdToWSOHD(obmo.findOHDByID(incomingOHD.getOHD_ID()),serviceResponse);
			} catch (Exception e) {
				e.printStackTrace();
				serviceResponse.setSuccess(false);
				serviceResponse.addError("Failed to load OHD, please contact NetTracer");
				logger.info(resDoc);
				return resDoc;
			}
			serviceResponse.setSuccess(true);
			serviceResponse.setCreateUpdateIndicator(STATUS_UPDATE);
			serviceResponse.setReturnStatus(STATUS_RETURN_LZ_UPDATE_REQUIRED);
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
			serviceResponse.setSuccess(true);
			serviceResponse.setReturnStatus(STATUS_RETURN_CREATE_OHD);
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
		
		if(inc.getItemlist() != null && inc.getItemlist().size() > 0){
			ohd.setColor(inc.getItemlist().get(0).getColor());
			ohd.setType(inc.getItemlist().get(0).getBagtype());
			ohd.setClaimnum(inc.getItemlist().get(0).getClaimchecknum());
		}
		if(inc.getPassengers() != null && inc.getPassengers().size() > 0){
			ohd.setFirstname(inc.getPassengers().iterator().next().getFirstname());
			ohd.setLastname(inc.getPassengers().iterator().next().getLastname());
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
			agent = getAgent(auth);
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
			agent = getAgent(auth);
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
