package com.bagnet.nettracer.ws.wn.onhandscanning;

import com.bagnet.nettracer.ws.core.pojo.WS_OHD;
import com.bagnet.nettracer.ws.wn.onhandscanning.pojo.BagDrop;
import com.bagnet.nettracer.ws.wn.onhandscanning.pojo.DeviceUser;
import com.bagnet.nettracer.ws.wn.onhandscanning.pojo.ServiceResponse;
import com.bagnet.nettracer.ws.wn.pojo.Authentication;

public class OnhandScanningService {
	public String echo(String s) {
		return "echo: " + s;
	}
	
	public ServiceResponse isValidUser(Authentication authentication){
		return null;
	}
	
	public ServiceResponse createUpdateOnhand(Authentication authentication, WS_OHD onhand){
		return null;
	}
	
	public ServiceResponse lookupOnhandLZ(Authentication authentication, WS_OHD onhand){
		return null;
	}
	
	public ServiceResponse lookupOnhandReturn(Authentication authentication, String tagNumber){
		return null;
	}
	
	public ServiceResponse returnOnhand(Authentication authentication,  String tagNumber, String claimCheckInd, String customerSignature, String foundStation, String positiveIdInd){
		return null;
	}
	
	public ServiceResponse saveBagDropTime(Authentication authentication, BagDrop bagDrop){
		return null;
	}
}
