package com.bagnet.nettracer.ws.wn.onhandscanning;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;
import com.bagnet.nettracer.ws.core.WSCoreOHDUtil;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD;
import com.bagnet.nettracer.ws.wn.onhandscanning.CreateUpdateOnhandResponseDocument.CreateUpdateOnhandResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.EchoResponseDocument.EchoResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.IsValidUserResponseDocument.IsValidUserResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.LookupOnhandLZResponseDocument.LookupOnhandLZResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.LookupOnhandReturnResponseDocument.LookupOnhandReturnResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.ReturnOnhandResponseDocument.ReturnOnhandResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.SaveBagDropTimeResponseDocument.SaveBagDropTimeResponse;

public class OnhandScanningServiceImplementation extends OnhandScanningServiceSkeleton{
	
	private static Logger logger = Logger.getLogger(OnhandScanningServiceImplementation.class);
	
	public static String STATUS_CREATE = "CREATE";
	public static String STATUS_UPDATE = "UPDATE";
	public static String STATUS_RETURN_ALLOWED = "Return allowed";
	public static String STATUS_RETURN_ASSOCIATED_REPORT = "Cannot return, associated report";
	public static String STATUS_RETURN_NO_OHD = "Cannot return, does not exist";
	public static String STATUS_RETURN_LZ_UPDATE_REQUIRED = "LZ Update Required";
	public static String STATUS_RETURN_UPDATE_SUCCESS = "Successful Update";
	public static String STATUS_RETURN_CREATE_OHD = "Create OHD";
	
	
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
				Iterator i = errors.get();
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

		Agent agent = null;
		com.bagnet.nettracer.ws.wn.pojo.xsd.Authentication auth = isValidUser.getIsValidUser().getAuthentication();
		try {
			agent = getAgent(auth);
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
		
		Station foundstation = StationBMO.getStationByCode(returnOnhand.getReturnOnhand().getFoundStation(), agent.getCompanycode_ID());
		if(foundstation == null){
			serviceResponse.setSuccess(false);
			serviceResponse.addError("Please provide a valid foundStation code");
			logger.info(resDoc);
			return resDoc;
		}
		String ohdId = lookupBagtag(returnOnhand.getReturnOnhand().getTagNumber(), foundstation.getStation_ID());
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
		ohd.getRemarks().add(r);
		
		if(obmo.insertOHD(ohd, ohd.getAgent())){
			WSCoreOHDUtil util = new WSCoreOHDUtil();
			try {
				serviceResponse.setOnhand(util.OHDtoWS_Mapping(OhdBMO.getOHDByID(ohd.getOHD_ID(),null)));
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
	 * Auto generated method signature
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

		Agent agent = null;
		com.bagnet.nettracer.ws.wn.pojo.xsd.Authentication auth = saveBagDropTime.getSaveBagDropTime().getAuthentication();
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
		
		//TODO BagDrop is to be implemented in a later iteration.  In meantime, echo back request
		serviceResponse.setBagDrop(saveBagDropTime.getSaveBagDropTime().getBagDrop());
		serviceResponse.setSuccess(true);
		
		logger.info(resDoc);
		return resDoc;
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param createUpdateOnhand
	 */

	public CreateUpdateOnhandResponseDocument createUpdateOnhand(CreateUpdateOnhandDocument createUpdateOnhand)
	{
		logger.info(createUpdateOnhand);
		CreateUpdateOnhandResponseDocument resDoc = CreateUpdateOnhandResponseDocument.Factory.newInstance();
		CreateUpdateOnhandResponse res = resDoc.addNewCreateUpdateOnhandResponse();
		com.bagnet.nettracer.ws.wn.onhandscanning.pojo.xsd.ServiceResponse serviceResponse = res.addNewReturn();

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
		if(wsohd.getStatus() == null){
			wsohd.setStatus("Open");
		}
		wsohd.setAgent(agent.getUsername());
		wsohd.setCompanycodeId(agent.getCompanycode_ID());
		
		Station foundstation = StationBMO.getStationByCode(wsohd.getFoundAtStation(), agent.getCompanycode_ID());
		String ohdId = lookupBagtag(wsohd.getBagtagnum(), foundstation.getStation_ID());
		OHD incomingOHD = OHDUtils.getBagTagNumberIncomingToStation(wsohd.getBagtagnum(),foundstation);
		if(ohdId != null){
			//update OHD
			wsohd.setOHDID(ohdId);
			WSCoreOHDUtil util = new WSCoreOHDUtil();
			try {
				
				OHD ntohd = util.WStoOHD_Mapping(wsohd);
				if(updateExistingOhd(ntohd)){
					serviceResponse.setOnhand(util.OHDtoWS_Mapping(OhdBMO.getOHDByID(ntohd.getOHD_ID(),null)));
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
		} else if(incomingOHD != null){
			//receive bag
			wsohd.setOHDID(incomingOHD.getOHD_ID());
			WSCoreOHDUtil util = new WSCoreOHDUtil();
			try {
				OHD ntohd = util.WStoOHD_Mapping(wsohd);
				if(updateExistingOhd(ntohd)){
					util.properlyHandleForwardedOnHand(incomingOHD, agent, foundstation);
					serviceResponse.setOnhand(util.OHDtoWS_Mapping(OhdBMO.getOHDByID(ntohd.getOHD_ID(),null)));
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
			//create new OHD
			if(wsohd.getFounddatetime() == null){
				GregorianCalendar c = new GregorianCalendar();
				c.setTime(DateUtils.convertToGMTDate(new Date()));
				wsohd.setFounddatetime(c);
			}
			
			WSCoreOHDUtil util = new WSCoreOHDUtil();
			try {
				OHD ntohd = util.WStoOHD_Mapping(wsohd);
			
				ntohd.setXdescelement_ID_1(TracingConstants.TRACING_ELEMENT_X);
				ntohd.setXdescelement_ID_2(TracingConstants.TRACING_ELEMENT_X);
				ntohd.setXdescelement_ID_3(TracingConstants.TRACING_ELEMENT_X);
				
				OhdBMO obmo = new OhdBMO();
				if(obmo.insertOHD(ntohd, ntohd.getAgent())){
					serviceResponse.setOnhand(util.OHDtoWS_Mapping(OhdBMO.getOHDByID(ntohd.getOHD_ID(),null)));
					serviceResponse.setSuccess(true);
					serviceResponse.setCreateUpdateIndicator(STATUS_CREATE);
					logger.info(resDoc);
					return resDoc;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		serviceResponse.setSuccess(false);
		serviceResponse.addError(wsohd.getErrorcode());
		logger.info(resDoc);
		return resDoc;
	}

	private boolean updateExistingOhd(OHD ohd){
		OhdBMO obmo = new OhdBMO();
		OHD oldohd = obmo.findOHDByID(ohd.getOHD_ID());
		if(oldohd != null){
			//These fields are not to be altered during an update
			ohd.setStatus(oldohd.getStatus());
			ohd.setXdescelement_ID_1(oldohd.getXdescelement_ID_1());
			ohd.setXdescelement_ID_2(oldohd.getXdescelement_ID_2());
			ohd.setXdescelement_ID_3(oldohd.getXdescelement_ID_3());
			ohd.setCreationMethod(oldohd.getCreationMethod());
			ohd.setFounddate(oldohd.getFounddate());
			ohd.setFoundtime(oldohd.getFoundtime());
		}
		
		return obmo.insertOHD(ohd, ohd.getAgent());
	}


	/**
	 * Auto generated method signature
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
		
		Station foundstation = StationBMO.getStationByCode(lookupOnhandLZ.getLookupOnhandLZ().getFoundStation(), agent.getCompanycode_ID());
		if(foundstation == null){
			serviceResponse.setSuccess(false);
			serviceResponse.addError("Please provide a valid foundStation code");
			logger.info(resDoc);
			return resDoc;
		}
		
		
		WSCoreOHDUtil util = new WSCoreOHDUtil();
		OHD incomingOHD = OHDUtils.getBagTagNumberIncomingToStation(lookupOnhandLZ.getLookupOnhandLZ().getTagNumber(),foundstation);
		
		if(incomingOHD != null){
			//receive OHD
			util.properlyHandleForwardedOnHand(incomingOHD, agent, foundstation);
			try{
				OhdBMO obmo = new OhdBMO();
				serviceResponse.setOnhand(util.OHDtoWS_Mapping(obmo.findOHDByID(incomingOHD.getOHD_ID())));
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

		OHD ohd = null;
		OhdBMO obmo = new OhdBMO();
		String ohdId = lzDeleteLast5Days(lookupOnhandLZ.getLookupOnhandLZ().getTagNumber());
		if(ohdId != null){
			ohd = obmo.findOHDByID(ohdId);
			//TODO loupas - to be built in a later iteration
		} else {
			ohdId = lzDamagedList(lookupOnhandLZ.getLookupOnhandLZ().getTagNumber());
			if(ohdId != null){
				ohd = obmo.findOHDByID(ohdId);
				//TODO loupas - to be built in a later iteration
			} else {
				ohdId = lzDamagedBagBSO(lookupOnhandLZ.getLookupOnhandLZ().getTagNumber());
				if(ohdId != null){
					ohd = obmo.findOHDByID(ohdId);
					//TODO loupas - to be built in a later iteration
				} else {
					ohdId = lzTBI(lookupOnhandLZ.getLookupOnhandLZ().getTagNumber());
					if(ohdId != null){
						ohd = obmo.findOHDByID(ohdId);
						//TODO loupas - to be built in a later iteration
					} else {
						ohdId = lz45Days(lookupOnhandLZ.getLookupOnhandLZ().getTagNumber());
						if(ohdId != null){
							ohd = obmo.findOHDByID(ohdId);
							//TODO loupas - to be built in a later iteration
						}
					}
				}
			}
		}
		
		if(ohd != null){
			obmo.insertOHD(ohd, ohd.getAgent());
			try{
				serviceResponse.setOnhand(util.OHDtoWS_Mapping(incomingOHD));
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

	private String lzDeleteLast5Days(String bagtag){
		//TODO loupas - to be built in a later iteration
		return null;
	}
	
	private String lzDamagedList(String bagtag){
		//TODO loupas - to be built in a later iteration
		return null;
	}
	
	private String lzDamagedBagBSO(String bagtag){
		//TODO loupas - to be built in a later iteration
		return null;
	}
	
	private String lzTBI(String bagtag){
		//TODO loupas - to be built in a later iteration
		return null;
	}
	
	private String lz45Days(String bagtag){
		//TODO loupas - to be built in a later iteration
		return null;
	}
	
	/**
	 * Auto generated method signature
	 * 
	 * @param lookupOnhandReturn
	 */

	public com.bagnet.nettracer.ws.wn.onhandscanning.LookupOnhandReturnResponseDocument lookupOnhandReturn(com.bagnet.nettracer.ws.wn.onhandscanning.LookupOnhandReturnDocument lookupOnhandReturn)
	{
		
		logger.info(lookupOnhandReturn);
		LookupOnhandReturnResponseDocument resDoc = LookupOnhandReturnResponseDocument.Factory.newInstance();
		LookupOnhandReturnResponse res = resDoc.addNewLookupOnhandReturnResponse();
		com.bagnet.nettracer.ws.wn.onhandscanning.pojo.xsd.ServiceResponse serviceResponse = res.addNewReturn();

		if(lookupOnhandReturn == null || lookupOnhandReturn.getLookupOnhandReturn() == null){
			serviceResponse.setSuccess(false);
			serviceResponse.setValidUser(false);
			serviceResponse.addError("lookupOnhandReturn request empty");
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
			logger.info(resDoc);
			return resDoc;
		}
		serviceResponse.setValidUser(true);
		
		Station foundstation = StationBMO.getStationByCode(lookupOnhandReturn.getLookupOnhandReturn().getFoundStation(), agent.getCompanycode_ID());
		if(foundstation == null){
			serviceResponse.setSuccess(false);
			serviceResponse.addError("Please provide a valid foundStation code");
			logger.info(resDoc);
			return resDoc;
		}
		
		String ohdId = lookupBagtag(lookupOnhandReturn.getLookupOnhandReturn().getTagNumber(), foundstation.getStation_ID());
		OHD incomingOHD = OHDUtils.getBagTagNumberIncomingToStation(lookupOnhandReturn.getLookupOnhandReturn().getTagNumber(),foundstation);
		WSCoreOHDUtil util = new WSCoreOHDUtil();
		OHD ohd = null;
		if(ohdId != null){
			//OHD in current station
			OhdBMO obmo = new OhdBMO();
			ohd = obmo.findOHDByID(ohdId);
		} else if (incomingOHD != null){
			//Incoming OHD, receive
			util.properlyHandleForwardedOnHand(incomingOHD, agent, foundstation);
			OhdBMO obmo = new OhdBMO();
			ohd = obmo.findOHDByID(incomingOHD.getOHD_ID());
		}
		
		if(ohd != null){
			try{
				serviceResponse.setOnhand(util.OHDtoWS_Mapping(ohd));
			} catch (Exception e) {
				e.printStackTrace();
				serviceResponse.setSuccess(false);
				serviceResponse.addError("Failed to load OHD, please contact NetTracer");
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
	 * Search for existing bagtag
	 * 
	 * @param bagtag
	 * @return
	 */
	protected String lookupBagtag(String bagtag, int foundStationID){
		//TODO identify additional critiera
		if(bagtag == null || bagtag.isEmpty() || foundStationID == 0){
			return null;
		}
		
		Session sess = null;
		try {
			String query = "select ohd_id from ohd "
					+ "where (claimnum=:claimnum1 or claimnum=:claimnum2) " +
					"and status_id =:status " +
					"and found_station_ID =:found_station_ID " +
					"order by founddate desc";
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createSQLQuery(query);
			q.setParameter("claimnum1", bagtag);
			String twoCharBagTag = null;
			try{
				twoCharBagTag = LookupAirlineCodes.getTwoCharacterBagTag(bagtag);
			} catch (Exception e){
				twoCharBagTag = bagtag;
			}
			q.setString("claimnum2", twoCharBagTag);
			q.setParameter("status", TracingConstants.OHD_STATUS_OPEN);
			q.setParameter("found_station_ID", foundStationID);
			List list = q.list();
			if (list.size() == 0) {
				logger.debug("unable to find ohd with bagtag: " + bagtag);
				return null;
			}
			return (String) list.get(0);
		} catch (Exception e) {
			logger.error("unable to retrieve ohd: " + e);
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
	}
	
}
