package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.log4j.Logger;
import org.tempuri.BaggageDeliveryStatusInfoResponse;
import org.tempuri.BaggageDeliveryStatusInfoServiceStub;
import org.tempuri.GetStatusListDocument;
import org.tempuri.GetStatusListDocument.GetStatusList;
import org.tempuri.GetStatusListResponseDocument;
import org.tempuri.GetStatusListResponseDocument.GetStatusListResponse;
import org.tempuri.StatusInfo;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.web.StatusListDisp;

@Stateless
public class BagDelStatusInfoUtils {

	private static Logger logger = Logger.getLogger(BagDelStatusInfoUtils.class);
	
	static private int DEFAULT_RES_WS_TIMEOUT = 30000;

	public static List<StatusListDisp> getStatusList(String bdoid, Agent user){
		BaggageDeliveryStatusInfoServiceStub stub=null;
		try {
			stub = new BaggageDeliveryStatusInfoServiceStub(PropertyBMO.getValue(PropertyBMO.BDSI_ADDRESS_ENDPOINT));

			int timeout = PropertyBMO.getValueAsInt(PropertyBMO.BDO_WS_TIMEOUT);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, timeout==0?DEFAULT_RES_WS_TIMEOUT:timeout);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, timeout==0?DEFAULT_RES_WS_TIMEOUT:timeout);
		} catch (AxisFault e2) {
			e2.printStackTrace();
		}
		GetStatusListDocument validate=GetStatusListDocument.Factory.newInstance();
		GetStatusList request=validate.addNewGetStatusList();
		request.setBdoNumber(bdoid);
	    try {
	    	//submit web service call to HSD
	    	logger.info(validate);
	    	GetStatusListResponseDocument replyDoc=stub.getStatusList(validate);
			logger.info(replyDoc);
			
			//Handle Response
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

}