package com.bagnet.nettracer.ws.wn.onhandscanning;

import com.bagnet.nettracer.ws.core.pojo.WS_OHD;
import com.bagnet.nettracer.ws.wn.onhandscanning.pojo.BagDrop;
import com.bagnet.nettracer.ws.wn.onhandscanning.pojo.ServiceResponse;
import com.bagnet.nettracer.ws.wn.pojo.Authentication;

public class OnhandScanningService {
	public String echo(String s) {
		return "echo: " + s;
	}
	
	public ServiceResponse isValidUser(Authentication authentication){
		return null;
	}
	
	public ServiceResponse createUpdateOnhand(Authentication authentication, WS_OHD onhand, String positionId, boolean lateCheckIndicator){
		return null;
	}
	
	public ServiceResponse lookupOnhandLZ(Authentication authentication, String tagNumber, String holdingStation){
		return null;
	}
	
	public ServiceResponse addBagForLZ(Authentication authentication, boolean tbi, WS_OHD onhand, String positionId, boolean lateCheckIndicator){
		return null;
	}
	
	public ServiceResponse lookupOnhandReturn(Authentication authentication, String tagNumber, String holdingStation){
		return null;
	}
	
	public ServiceResponse returnOnhand(Authentication authentication,  String tagNumber, String claimCheckInd, String customerSignature, String holdingStation, String positiveIdInd){
		return null;
	}
	
	public ServiceResponse saveBagDropTime(Authentication authentication, BagDrop bagDrop){
		return null;
	}
}
