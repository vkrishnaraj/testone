package com.bagnet.nettracer.tracing.utils;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.HTTPConstants;
import org.tempuri.ArrayOfBaggageDeliveryStatusInfoResponse;
import org.tempuri.BaggageDeliveryStatusInfoResponse;
import org.tempuri.BaggageDeliveryStatusInfoServiceStub;
import org.tempuri.GetLastStatusDocument;
import org.tempuri.GetLastStatusDocument.GetLastStatus;
import org.tempuri.GetLastStatusResponseDocument;
import org.tempuri.GetLastStatusResponseDocument.GetLastStatusResponse;
import org.tempuri.GetLastStatusesDocument;
import org.tempuri.GetLastStatusesDocument.GetLastStatuses;
import org.tempuri.GetLastStatusesResponseDocument;
import org.tempuri.GetLastStatusesResponseDocument.GetLastStatusesResponse;
import org.tempuri.GetStatusListDocument;
import org.tempuri.GetStatusListDocument.GetStatusList;
import org.tempuri.GetStatusListResponseDocument;
import org.tempuri.GetStatusListResponseDocument.GetStatusListResponse;
import org.tempuri.GetStatusListsDocument;
import org.tempuri.GetStatusListsDocument.GetStatusLists;
import org.tempuri.GetStatusListsResponseDocument;
import org.tempuri.GetStatusListsResponseDocument.GetStatusListsResponse;
import org.tempuri.StatusInfo;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.web.StatusListDisp;

@Stateless
public class BagDelStatusInfoUtils {

	static private int DEFAULT_RES_WS_TIMEOUT = 30000;
//Begin Logic
	public static List<StatusListDisp> getStatusList(String bdoid, Agent user){
		BaggageDeliveryStatusInfoServiceStub stub=null;
		try {
			stub = new BaggageDeliveryStatusInfoServiceStub(PropertyBMO.getValue(PropertyBMO.BDSI_ADDRESS_ENDPOINT));

			int timeout = PropertyBMO.getValueAsInt(PropertyBMO.BDO_WS_TIMEOUT);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, timeout==0?DEFAULT_RES_WS_TIMEOUT:timeout);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, timeout==0?DEFAULT_RES_WS_TIMEOUT:timeout);
		} catch (AxisFault e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		GetStatusListDocument validate=GetStatusListDocument.Factory.newInstance();
		GetStatusList request=validate.addNewGetStatusList();
		request.setBdoNumber(bdoid);
	    try {
	    	GetStatusListResponseDocument replyDoc=stub.getStatusList(validate);
			// Initialize the service
			
			// This is the call to the web service
	    	GetStatusListResponse reply = replyDoc.getGetStatusListResponse();
			
			BaggageDeliveryStatusInfoResponse a= reply.getGetStatusListResult();
			List<StatusListDisp> sldList=new ArrayList<StatusListDisp>();
			if(a.getBdoExists() && a.getStatusExists() && a.getInfoArray()!=null){
				for(StatusInfo si:a.getInfoArray()){
					StatusListDisp sld=new StatusListDisp();
					sld.setCreateDate(DateUtils.convertSystemCalendarToGMTDate(si.getCreatedDateTime()));
					sld.setDelStatus(si.getDeliveryStatus());
					sld.setDelStatusDesc(si.getDeliveryStatusDescription());
					sld.setDelStatusDesc2(si.getDeliveryStatusDescription2());
					sld.set_DATEFORMAT(user.getDateformat().getFormat());
					sld.set_TIMEFORMAT(user.getTimeformat().getFormat());
					sldList.add(sld);
				}
			}
			return sldList;
		} catch (Exception e) {
            e.printStackTrace();
		}

		return null;
	}	

	public static String getLastStatus(String bdoid){
		BaggageDeliveryStatusInfoServiceStub stub=null;
		try {
			stub = new BaggageDeliveryStatusInfoServiceStub(PropertyBMO.getValue(PropertyBMO.BDSI_ADDRESS_ENDPOINT));
			int timeout = PropertyBMO.getValueAsInt(PropertyBMO.RES_WS_TIMEOUT);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, timeout==0?DEFAULT_RES_WS_TIMEOUT:timeout);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, timeout==0?DEFAULT_RES_WS_TIMEOUT:timeout);
		} catch (AxisFault e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		GetLastStatusDocument validate=GetLastStatusDocument.Factory.newInstance();
		GetLastStatus request=validate.addNewGetLastStatus();
		request.setBdoNumber(bdoid);
	    try {
	    	GetLastStatusResponseDocument replyDoc=stub.getLastStatus(validate);
			// Initialize the service
			
			// This is the call to the web service
	    	GetLastStatusResponse reply = replyDoc.getGetLastStatusResponse();
			
			boolean insufficientData=false;
			BaggageDeliveryStatusInfoResponse a= reply.getGetLastStatusResult();
		} catch (Exception e) {
            e.printStackTrace();
		}

		return null;
	}
	
	public static String getLastStatuses(List<String> bdoids){
		BaggageDeliveryStatusInfoServiceStub stub=null;
		try {
			stub = new BaggageDeliveryStatusInfoServiceStub(PropertyBMO.getValue(PropertyBMO.BDSI_ADDRESS_ENDPOINT));
			int timeout = PropertyBMO.getValueAsInt(PropertyBMO.RES_WS_TIMEOUT);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, timeout==0?DEFAULT_RES_WS_TIMEOUT:timeout);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, timeout==0?DEFAULT_RES_WS_TIMEOUT:timeout);
		} catch (AxisFault e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		GetLastStatusesDocument validate=GetLastStatusesDocument.Factory.newInstance();
		GetLastStatuses request=validate.addNewGetLastStatuses();
		String[] bdolist=new String[bdoids.size()];
		int i=0;
		for(String bdoid:bdoids){
			bdolist[i]=bdoid;
			i++;
		}
		request.setBdoNumbersArray(bdolist);
	    try {
	    	GetLastStatusesResponseDocument replyDoc=stub.getLastStatuses(validate);
			// Initialize the service
			
			// This is the call to the web service
	    	GetLastStatusesResponse reply = replyDoc.getGetLastStatusesResponse();
			
			boolean insufficientData=false;
			ArrayOfBaggageDeliveryStatusInfoResponse a= reply.getGetLastStatusesResult();
		} catch (Exception e) {
            e.printStackTrace();
		}

		return null;
	}

	public static String getStatusLists(List<String> bdoids){
		BaggageDeliveryStatusInfoServiceStub stub=null;
		try {
			stub = new BaggageDeliveryStatusInfoServiceStub(PropertyBMO.getValue(PropertyBMO.BDSI_ADDRESS_ENDPOINT));
			int timeout = PropertyBMO.getValueAsInt(PropertyBMO.RES_WS_TIMEOUT);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, timeout==0?DEFAULT_RES_WS_TIMEOUT:timeout);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, timeout==0?DEFAULT_RES_WS_TIMEOUT:timeout);
		} catch (AxisFault e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		GetStatusListsDocument validate=GetStatusListsDocument.Factory.newInstance();
		GetStatusLists request=validate.addNewGetStatusLists();
		String[] bdolist=new String[bdoids.size()];
		int i=0;
		for(String bdoid:bdoids){
			bdolist[i]=bdoid;
			i++;
		}
		request.setBdoNumbersArray(bdolist);
	    try {
	    	GetStatusListsResponseDocument replyDoc=stub.getStatusLists(validate);
			// Initialize the service
			
			// This is the call to the web service
	    	GetStatusListsResponse reply = replyDoc.getGetStatusListsResponse();
			
			boolean insufficientData=false;
			ArrayOfBaggageDeliveryStatusInfoResponse a= reply.getGetStatusListsResult();
		} catch (Exception e) {
            e.printStackTrace();
		}

		return null;
	}
}