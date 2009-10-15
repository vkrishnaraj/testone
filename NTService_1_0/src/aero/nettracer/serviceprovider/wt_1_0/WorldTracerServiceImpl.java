package aero.nettracer.serviceprovider.wt_1_0;

import aero.nettracer.serviceprovider.common.ServiceConstants;
import aero.nettracer.serviceprovider.common.db.PermissionType;
import aero.nettracer.serviceprovider.common.exceptions.ConfigurationException;
import aero.nettracer.serviceprovider.common.exceptions.UserNotAuthorizedException;
import aero.nettracer.serviceprovider.common.utils.ServiceUtilities;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError;
import aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.services.ServicesManager;

public class WorldTracerServiceImpl extends WorldTracerServiceSkeleton {
    /**
     * Auto generated method signature
     * @param closeOhd
     */
    public aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument closeOhd(
        aero.nettracer.serviceprovider.wt_1_0.CloseOhdDocument closeOhd) {
    	
    	RequestHeader header = closeOhd.getCloseOhd().getHeader();
    	String username = header.getUsername();
    	String password = header.getUsername();
    	boolean userAuthorized = true;
    	
    	try {
			ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		} catch (UserNotAuthorizedException e) {
			userAuthorized = false;
		}
		
		WorldTracerResponse xreturn = WorldTracerResponse.Factory.newInstance();

		if (userAuthorized) {
    		WorldTracerActionDTO dto = new WorldTracerActionDTO();
    		try {
				aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse sResponse = ServicesManager.getInstance().performAction(dto);
				convertToWebServiceResponse(sResponse, xreturn);
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


	/**
     * Auto generated method signature
     * @param forwardOhd
     */
    public aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument forwardOhd(
        aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument forwardOhd) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#forwardOhd");
    }

    /**
     * Auto generated method signature
     * @param getAhl
     */
    public aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument getAhl(
        aero.nettracer.serviceprovider.wt_1_0.GetAhlDocument getAhl) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#getAhl");
    }

    /**
     * Auto generated method signature
     * @param createAhl
     */
    public aero.nettracer.serviceprovider.wt_1_0.CreateAhlResponseDocument createAhl(
        aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument createAhl) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#createAhl");
    }

    /**
     * Auto generated method signature
     * @param eraseActionFile
     */
    public aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument eraseActionFile(
        aero.nettracer.serviceprovider.wt_1_0.EraseActionFileDocument eraseActionFile) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#eraseActionFile");
    }

    /**
     * Auto generated method signature
     * @param amendOhd
     */
    public aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument amendOhd(
        aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument amendOhd) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#amendOhd");
    }

    /**
     * Auto generated method signature
     * @param getActionFileSummary
     */
    public aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument getActionFileSummary(
        aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryDocument getActionFileSummary) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#getActionFileSummary");
    }

    /**
     * Auto generated method signature
     * @param getOhd
     */
    public aero.nettracer.serviceprovider.wt_1_0.GetOhdResponseDocument getOhd(
        aero.nettracer.serviceprovider.wt_1_0.GetOhdDocument getOhd) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#getOhd");
    }

    /**
     * Auto generated method signature
     * @param getActionFileCounts
     */
    public aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsResponseDocument getActionFileCounts(
        aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument getActionFileCounts) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#getActionFileCounts");
    }

    /**
     * Auto generated method signature
     * @param requestOhd
     */
    public aero.nettracer.serviceprovider.wt_1_0.RequestOhdResponseDocument requestOhd(
        aero.nettracer.serviceprovider.wt_1_0.RequestOhdDocument requestOhd) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#requestOhd");
    }

    /**
     * Auto generated method signature
     * @param amendAhl
     */
    public aero.nettracer.serviceprovider.wt_1_0.AmendAhlResponseDocument amendAhl(
        aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument amendAhl) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#amendAhl");
    }

    /**
     * Auto generated method signature
     * @param createBdo
     */
    public aero.nettracer.serviceprovider.wt_1_0.CreateBdoResponseDocument createBdo(
        aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument createBdo) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#createBdo");
    }

    /**
     * Auto generated method signature
     * @param requestQuickOhd
     */
    public aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdResponseDocument requestQuickOhd(
        aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument requestQuickOhd) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#requestQuickOhd");
    }

    /**
     * Auto generated method signature
     * @param getActionFileDetails
     */
    public aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsResponseDocument getActionFileDetails(
        aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument getActionFileDetails) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#getActionFileDetails");
    }

    /**
     * Auto generated method signature
     * @param sendForwardMessage
     */
    public aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageResponseDocument sendForwardMessage(
        aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument sendForwardMessage) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#sendForwardMessage");
    }

    /**
     * Auto generated method signature
     * @param placeActionFile
     */
    public aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileResponseDocument placeActionFile(
        aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument placeActionFile) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#placeActionFile");
    }

    /**
     * Auto generated method signature
     * @param createOhd
     */
    public aero.nettracer.serviceprovider.wt_1_0.CreateOhdResponseDocument createOhd(
        aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument createOhd) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#createOhd");
    }

    /**
     * Auto generated method signature
     * @param reinstanteAhl
     */
    public aero.nettracer.serviceprovider.wt_1_0.ReinstanteAhlResponseDocument reinstanteAhl(
        aero.nettracer.serviceprovider.wt_1_0.ReinstanteAhlDocument reinstanteAhl) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#reinstanteAhl");
    }

    /**
     * Auto generated method signature
     * @param closeAhl
     */
    public aero.nettracer.serviceprovider.wt_1_0.CloseAhlResponseDocument closeAhl(
        aero.nettracer.serviceprovider.wt_1_0.CloseAhlDocument closeAhl) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#closeAhl");
    }

    /**
     * Auto generated method signature
     * @param suspendAhl
     */
    public aero.nettracer.serviceprovider.wt_1_0.SuspendAhlResponseDocument suspendAhl(
        aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument suspendAhl) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#suspendAhl");
    }
    
	private void convertToWebServiceResponse(
			aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse response,
			WorldTracerResponse xreturn) {
		
		
		response.getActionFiles();
		response.getCounts();
		response.getResponseData();
		response.getError();
		response.getAhl();
		response.getOhd();
		
	}
}
