package aero.nettracer.serviceprovider.wt_1_0;


import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import aero.nettracer.serviceprovider.common.ServiceConstants;
import aero.nettracer.serviceprovider.common.db.PermissionType;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.exceptions.ConfigurationException;
import aero.nettracer.serviceprovider.common.exceptions.UserNotAuthorizedException;
import aero.nettracer.serviceprovider.common.utils.ServiceUtilities;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileRequestData;
import aero.nettracer.serviceprovider.wt_1_0.common.Ahl;
import aero.nettracer.serviceprovider.wt_1_0.common.Bdo;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardMessage;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.Ohd;
import aero.nettracer.serviceprovider.wt_1_0.common.Pxf;
import aero.nettracer.serviceprovider.wt_1_0.common.Qoh;
import aero.nettracer.serviceprovider.wt_1_0.common.RequestOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionType;
import aero.nettracer.serviceprovider.wt_1_0.services.ServicesManager;

public class WorldTracerServiceImpl extends WorldTracerServiceSkeleton {
	
	private static final Logger logger = Logger.getLogger(WorldTracerServiceImpl.class);
	
    public aero.nettracer.serviceprovider.wt_1_0.EstablishWtrConnectionResponseDocument establishWtrConnection(
			aero.nettracer.serviceprovider.wt_1_0.EstablishWtrConnectionDocument establishWtrConnection) {

    	WorldTracerActionType type = WorldTracerActionType.ESTABLISH;
    	
    	RequestHeader header = establishWtrConnection.getEstablishWtrConnection().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {

			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, null, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	EstablishWtrConnectionResponseDocument response = EstablishWtrConnectionResponseDocument.Factory.newInstance();
		response.addNewEstablishWtrConnectionResponse().setReturn(xreturn);
		
		return response;
	}

	
    public aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument closeOhd(
        aero.nettracer.serviceprovider.wt_1_0.CloseOhdDocument closeOhd) {
    	
    	WorldTracerActionType type = WorldTracerActionType.CLOSE_OHD;
    	
    	RequestHeader header = closeOhd.getCloseOhd().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd input = closeOhd.getCloseOhd().getOhd();
			logger.info(input);
			Ohd payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, Ohd.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
		CloseOhdResponseDocument response = CloseOhdResponseDocument.Factory.newInstance();
		response.addNewCloseOhdResponse().setReturn(xreturn);
		
		return response;
    }


    public aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument forwardOhd(
        aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument forwardOhd) {

    	WorldTracerActionType type = WorldTracerActionType.FORWARD_OHD;
    	
    	RequestHeader header = forwardOhd.getForwardOhd().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd input = forwardOhd.getForwardOhd().getData();
			logger.info(input);
			ForwardOhd payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, ForwardOhd.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	ForwardOhdResponseDocument response = ForwardOhdResponseDocument.Factory.newInstance();
		response.addNewForwardOhdResponse().setReturn(xreturn);
		
		return response;
    }


    public aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument getAhl(
        aero.nettracer.serviceprovider.wt_1_0.GetAhlDocument getAhl) {

    	WorldTracerActionType type = WorldTracerActionType.GET_AHL;
    	
    	RequestHeader header = getAhl.getGetAhl().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl input = getAhl.getGetAhl().getAhl();
    		logger.info(input);
			Ahl payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, Ahl.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	GetAhlResponseDocument response = GetAhlResponseDocument.Factory.newInstance();
		response.addNewGetAhlResponse().setReturn(xreturn);
		
		return response;
    }


    public aero.nettracer.serviceprovider.wt_1_0.CreateAhlResponseDocument createAhl(
        aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument createAhl) {
    	WorldTracerActionType type = WorldTracerActionType.CREATE_AHL;
    	
    	RequestHeader header = createAhl.getCreateAhl().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl input = createAhl.getCreateAhl().getAhl();
			logger.info(input);
			Ahl payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, Ahl.class);
			
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	CreateAhlResponseDocument response = CreateAhlResponseDocument.Factory.newInstance();
		response.addNewCreateAhlResponse().setReturn(xreturn);
		
		return response;
    }


    public aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument eraseActionFile(
        aero.nettracer.serviceprovider.wt_1_0.EraseActionFileDocument eraseActionFile) {
    	WorldTracerActionType type = WorldTracerActionType.ERASE_ACTION_FILE;
    	
    	RequestHeader header = eraseActionFile.getEraseActionFile().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData input = eraseActionFile.getEraseActionFile().getData();
			logger.info(input);
			ActionFileRequestData payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, ActionFileRequestData.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	EraseActionFileResponseDocument response = EraseActionFileResponseDocument.Factory.newInstance();
		response.addNewEraseActionFileResponse().setReturn(xreturn);
		
		return response;
    }


    public aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument amendOhd(
        aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument amendOhd) {
    	WorldTracerActionType type = WorldTracerActionType.AMEND_OHD;
    	
    	RequestHeader header = amendOhd.getAmendOhd().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd input = amendOhd.getAmendOhd().getOhd();
			logger.info(input);
			Ohd payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, Ohd.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	AmendOhdResponseDocument response = AmendOhdResponseDocument.Factory.newInstance();
		response.addNewAmendOhdResponse().setReturn(xreturn);
		
		return response;
    }


    public aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument getActionFileSummary(
        aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryDocument getActionFileSummary) {
    	WorldTracerActionType type = WorldTracerActionType.ACTION_FILE_SUMMARY;
    	
    	RequestHeader header = getActionFileSummary.getGetActionFileSummary().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData input = getActionFileSummary.getGetActionFileSummary().getData();
			logger.info(input);
			ActionFileRequestData payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, ActionFileRequestData.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	GetActionFileSummaryResponseDocument response = GetActionFileSummaryResponseDocument.Factory.newInstance();
		response.addNewGetActionFileSummaryResponse().setReturn(xreturn);
		
		return response;
    }


    public aero.nettracer.serviceprovider.wt_1_0.GetOhdResponseDocument getOhd(
        aero.nettracer.serviceprovider.wt_1_0.GetOhdDocument getOhd) {
    	WorldTracerActionType type = WorldTracerActionType.GET_OHD;
    	
    	RequestHeader header = getOhd.getGetOhd().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd input = getOhd.getGetOhd().getOhd();
			logger.info(input);
			Ohd payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, Ohd.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	GetOhdResponseDocument response = GetOhdResponseDocument.Factory.newInstance();
		response.addNewGetOhdResponse().setReturn(xreturn);
		
		return response;
    }


    public aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsResponseDocument getActionFileCounts(
        aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument getActionFileCounts) {
    	WorldTracerActionType type = WorldTracerActionType.ACTION_FILE_COUNTS;
    	
    	RequestHeader header = getActionFileCounts.getGetActionFileCounts().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();
		
		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData input = getActionFileCounts.getGetActionFileCounts().getData();
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			logger.info(input);
			ActionFileRequestData payload = mapper.map(input, ActionFileRequestData.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	GetActionFileCountsResponseDocument response = GetActionFileCountsResponseDocument.Factory.newInstance();
		response.addNewGetActionFileCountsResponse().setReturn(xreturn);
		
		return response;
    }

    /**
     * Auto generated method signature
     * @param requestOhd
     */
    public aero.nettracer.serviceprovider.wt_1_0.RequestOhdResponseDocument requestOhd(
        aero.nettracer.serviceprovider.wt_1_0.RequestOhdDocument requestOhd) {
    	WorldTracerActionType type = WorldTracerActionType.REQUEST_OHD;
    	
    	RequestHeader header = requestOhd.getRequestOhd().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.RequestOhd input = requestOhd.getRequestOhd().getData();
			logger.info(input);
			RequestOhd payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, RequestOhd.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	RequestOhdResponseDocument response = RequestOhdResponseDocument.Factory.newInstance();
		response.addNewRequestOhdResponse().setReturn(xreturn);
		
		return response;
    }


    public aero.nettracer.serviceprovider.wt_1_0.AmendAhlResponseDocument amendAhl(
        aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument amendAhl) {
    	
    	WorldTracerActionType type = WorldTracerActionType.AMEND_AHL;
    	
    	RequestHeader header = amendAhl.getAmendAhl().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl input = amendAhl.getAmendAhl().getAhl();
			logger.info(input);
			Ahl payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, Ahl.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	AmendAhlResponseDocument response = AmendAhlResponseDocument.Factory.newInstance();
		response.addNewAmendAhlResponse().setReturn(xreturn);
		
		return response;
    }


    public aero.nettracer.serviceprovider.wt_1_0.CreateBdoResponseDocument createBdo(
        aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument createBdo) {
    	WorldTracerActionType type = WorldTracerActionType.CREATE_BDO;
    	
    	RequestHeader header = createBdo.getCreateBdo().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.Bdo input = createBdo.getCreateBdo().getBdo();
			logger.info(input);
			Bdo payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, Bdo.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	CreateBdoResponseDocument response = CreateBdoResponseDocument.Factory.newInstance();
		response.addNewCreateBdoResponse().setReturn(xreturn);
		
		return response;
    }


    public aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdResponseDocument requestQuickOhd(
        aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument requestQuickOhd) {
    	WorldTracerActionType type = WorldTracerActionType.REQUEST_QOHD;
    	
    	RequestHeader header = requestQuickOhd.getRequestQuickOhd().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.RequestOhd input = requestQuickOhd.getRequestQuickOhd().getData();
			logger.info(input);
			RequestOhd payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, RequestOhd.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	RequestQuickOhdResponseDocument response = RequestQuickOhdResponseDocument.Factory.newInstance();
		response.addNewRequestQuickOhdResponse().setReturn(xreturn);
		
		return response;
    }


    public aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsResponseDocument getActionFileDetails(
        aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument getActionFileDetails) {
    	WorldTracerActionType type = WorldTracerActionType.ACTION_FILE_DETAILS;
    	
    	RequestHeader header = getActionFileDetails.getGetActionFileDetails().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData input =  getActionFileDetails.getGetActionFileDetails().getData();
			logger.info(input);
			ActionFileRequestData payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, ActionFileRequestData.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	GetActionFileDetailsResponseDocument response = GetActionFileDetailsResponseDocument.Factory.newInstance();
		response.addNewGetActionFileDetailsResponse().setReturn(xreturn);
		
		return response;
    }


    public aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageResponseDocument sendForwardMessage(
        aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument sendForwardMessage) {
    	WorldTracerActionType type = WorldTracerActionType.SEND_FORWARD_MESSAGE;
    	
    	RequestHeader header = sendForwardMessage.getSendForwardMessage().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardMessage input = sendForwardMessage.getSendForwardMessage().getForwardMessage();
			logger.info(input);
			ForwardMessage payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, ForwardMessage.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	SendForwardMessageResponseDocument response = SendForwardMessageResponseDocument.Factory.newInstance();
		response.addNewSendForwardMessageResponse().setReturn(xreturn);
		
		return response;
    }


    public aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileResponseDocument placeActionFile(
        aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument placeActionFile) {
    	WorldTracerActionType type = WorldTracerActionType.PLACE_ACTION_FILE;
    	
    	RequestHeader header = placeActionFile.getPlaceActionFile().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf input = placeActionFile.getPlaceActionFile().getPxf();
			logger.info(input);
			Pxf payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, Pxf.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	PlaceActionFileResponseDocument response = PlaceActionFileResponseDocument.Factory.newInstance();
		response.addNewPlaceActionFileResponse().setReturn(xreturn);
		
		return response;
    }


    public aero.nettracer.serviceprovider.wt_1_0.CreateOhdResponseDocument createOhd(
        aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument createOhd) {
    	WorldTracerActionType type = WorldTracerActionType.CREATE_OHD;
    	
    	RequestHeader header = createOhd.getCreateOhd().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd input = createOhd.getCreateOhd().getOhd();
			logger.info(input);
			Ohd payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, Ohd.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	CreateOhdResponseDocument response = CreateOhdResponseDocument.Factory.newInstance();
		response.addNewCreateOhdResponse().setReturn(xreturn);
		
		return response;
    }


    public aero.nettracer.serviceprovider.wt_1_0.ReinstateAhlResponseDocument reinstateAhl(
        aero.nettracer.serviceprovider.wt_1_0.ReinstateAhlDocument reinstateAhl) {
    	WorldTracerActionType type = WorldTracerActionType.REINSTATE_AHL;
    	
    	RequestHeader header = reinstateAhl.getReinstateAhl().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl input = reinstateAhl.getReinstateAhl().getAhl();
			logger.info(input);
			Ahl payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, Ahl.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	ReinstateAhlResponseDocument response = ReinstateAhlResponseDocument.Factory.newInstance();
		response.addNewReinstateAhlResponse().setReturn(xreturn);
		
		return response;
    }


    public aero.nettracer.serviceprovider.wt_1_0.CloseAhlResponseDocument closeAhl(
        aero.nettracer.serviceprovider.wt_1_0.CloseAhlDocument closeAhl) {
    	WorldTracerActionType type = WorldTracerActionType.CLOSE_AHL;
    	
    	RequestHeader header = closeAhl.getCloseAhl().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl input = closeAhl.getCloseAhl().getAhl();
			logger.info(input);
			Ahl payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, Ahl.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	CloseAhlResponseDocument response = CloseAhlResponseDocument.Factory.newInstance();
		response.addNewCloseAhlResponse().setReturn(xreturn);
		
		return response;
    }


    public aero.nettracer.serviceprovider.wt_1_0.SuspendAhlResponseDocument suspendAhl(
        aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument suspendAhl) {
    	WorldTracerActionType type = WorldTracerActionType.SUSPEND_AHL;
    	
    	RequestHeader header = suspendAhl.getSuspendAhl().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl input = suspendAhl.getSuspendAhl().getAhl();
			logger.info(input);
			Ahl payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, Ahl.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	SuspendAhlResponseDocument response = SuspendAhlResponseDocument.Factory.newInstance();
		response.addNewSuspendAhlResponse().setReturn(xreturn);
		
		return response;
    }
    
	private WorldTracerResponse convertToWebServiceResponse(
			aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse input) {
		
		return DozerBeanMapperSingletonWrapper.getInstance().map(input, WorldTracerResponse.class);
	}
	
    
    public aero.nettracer.serviceprovider.wt_1_0.SuspendOhdResponseDocument suspendOhd(aero.nettracer.serviceprovider.wt_1_0.SuspendOhdDocument suspendOhd) {
    	WorldTracerActionType type = WorldTracerActionType.SUSPEND_OHD;
    	
    	RequestHeader header = suspendOhd.getSuspendOhd().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd input = suspendOhd.getSuspendOhd().getOhd();
			logger.info(input);
			Ohd payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, Ohd.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	SuspendOhdResponseDocument response = SuspendOhdResponseDocument.Factory.newInstance();
		response.addNewSuspendOhdResponse().setReturn(xreturn);
		
		return response;
	}

	public aero.nettracer.serviceprovider.wt_1_0.ReinstateOhdResponseDocument reinstateOhd(aero.nettracer.serviceprovider.wt_1_0.ReinstateOhdDocument reinstateOhd) {
    	WorldTracerActionType type = WorldTracerActionType.REINSTATE_OHD;
    	
    	RequestHeader header = reinstateOhd.getReinstateOhd().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd input = reinstateOhd.getReinstateOhd().getOhd();
			logger.info(input);
			Ohd payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, Ohd.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	ReinstateOhdResponseDocument response = ReinstateOhdResponseDocument.Factory.newInstance();
		response.addNewReinstateOhdResponse().setReturn(xreturn);
		
		return response;
	}
	
    
    public aero.nettracer.serviceprovider.wt_1_0.SendQohResponseDocument sendQoh(aero.nettracer.serviceprovider.wt_1_0.SendQohDocument sendQoh) {
    	WorldTracerActionType type = WorldTracerActionType.SEND_QOH;
    	
    	RequestHeader header = sendQoh.getSendQoh().getHeader();
    	User user = null;
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
			aero.nettracer.serviceprovider.wt_1_0.common.xsd.Qoh input = sendQoh.getSendQoh().getOhd();
			logger.info(input);
			Qoh payload = DozerBeanMapperSingletonWrapper.getInstance().map(input, Qoh.class);
			WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, header);
    		
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				xreturn = convertToWebServiceResponse(sResponse);
			} catch (UserNotAuthorizedException e) {
				userAuthorized = false;
			} catch (ConfigurationException e) {
				WebServiceError error = xreturn.addNewError();
				error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			}
    	} 

    	if (!userAuthorized){
    		WebServiceError error = xreturn.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
    	}
    	
    	SendQohResponseDocument response = SendQohResponseDocument.Factory.newInstance();
		response.addNewSendQohResponse().setReturn(xreturn);
		
		return response;
	}


}
