package com.bagnet.nettracer.tracing.utils;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.HTTPConstants;
import org.tempuri.ArrayOfBaggageDeliveryStatusInfoResponse;
import org.tempuri.BDOAddDocument;
import org.tempuri.BDOAddDocument.BDOAdd;
import org.tempuri.BDOAddResponseDocument;
import org.tempuri.BDOAddResponseDocument.BDOAddResponse;
import org.tempuri.CalculateDeliveryCostDocument;
import org.tempuri.CalculateDeliveryCostDocument.CalculateDeliveryCost;
import org.tempuri.CalculateDeliveryCostResponseDocument;
import org.tempuri.CalculateDeliveryCostResponseDocument.CalculateDeliveryCostResponse;
import org.tempuri.RetVal;
import org.tempuri.SouthwestAirlinesCalculateDeliveryCostResponse;
import org.tempuri.SouthwestAirlinesStub;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.web.StatusListDisp;

@Stateless
public class CostServiceUtils {

	static private int DEFAULT_RES_WS_TIMEOUT = 30000;
//Begin Logic
	public static void bdoAdd(String bdoid, Agent user){
		SouthwestAirlinesStub stub=null;
		try {
			stub = new SouthwestAirlinesStub(PropertyBMO.getValue(PropertyBMO.SWA_SERVICE_ADDRESS_ENDPOINT));

			int timeout = PropertyBMO.getValueAsInt(PropertyBMO.RES_WS_TIMEOUT);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, timeout==0?DEFAULT_RES_WS_TIMEOUT:timeout);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, timeout==0?DEFAULT_RES_WS_TIMEOUT:timeout);
		} catch (AxisFault e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		BDOAddDocument validate=BDOAddDocument.Factory.newInstance();
		BDOAdd request=validate.addNewBDOAdd();
		//TODO - adding BDO information to send for the request.
		request.setBDONumber(bdoid);
	    try {
	    	BDOAddResponseDocument replyDoc=stub.bDO_Add(validate);
			// Initialize the service
			
			// This is the call to the web service
	    	BDOAddResponse reply = replyDoc.getBDOAddResponse();
			
			RetVal a= reply.getBDOAddResult();
			
			return ;
		} catch (Exception e) {
            e.printStackTrace();
		}

		return ;
	}	

	public static String calculateDeliveryCost(){
		SouthwestAirlinesStub stub=null;
		try {
			stub = new SouthwestAirlinesStub(PropertyBMO.getValue(PropertyBMO.SWA_SERVICE_ADDRESS_ENDPOINT));

			int timeout = PropertyBMO.getValueAsInt(PropertyBMO.RES_WS_TIMEOUT);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, timeout==0?DEFAULT_RES_WS_TIMEOUT:timeout);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, timeout==0?DEFAULT_RES_WS_TIMEOUT:timeout);
		} catch (AxisFault e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		CalculateDeliveryCostDocument validate=CalculateDeliveryCostDocument.Factory.newInstance();
		CalculateDeliveryCost request=validate.addNewCalculateDeliveryCost();
		//TODO - adding Address, Bag, and Service information to send for the request.
		
	    try {
	    	CalculateDeliveryCostResponseDocument replyDoc=stub.calculateDeliveryCost(validate);
			// Initialize the service
			
			// This is the call to the web service
	    	CalculateDeliveryCostResponse reply = replyDoc.getCalculateDeliveryCostResponse();
			
			SouthwestAirlinesCalculateDeliveryCostResponse a= reply.getCalculateDeliveryCostResult();
			
			return null;
		} catch (Exception e) {
            e.printStackTrace();
		}

		return null;
	}
}