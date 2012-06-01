/**
 * BaggageDeliveryStatusTrackerServiceSkeleton.java
 *
 */
package org.tempuri;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import aero.nettracer.serviceprovider.common.dao.UserDao;
import aero.nettracer.serviceprovider.common.db.ParameterType;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;
import aero.nettracer.utilities.TracerProperties;

import com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument;
import com.bagnet.nettracer.ws.core.NTCoreServiceStub;
import com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.UpdateBdoDelivery;
import com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument;
import com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate;


public class BaggageDeliveryStatusTrackerServiceImpl extends BaggageDeliveryStatusTrackerServiceSkeleton {

	private static Logger logger = Logger.getLogger(BaggageDeliveryStatusTrackerServiceImpl.class);

	public org.tempuri.MassUpdateBaggageDeliveryStatusResponseDocument massUpdateBaggageDeliveryStatus(org.tempuri.MassUpdateBaggageDeliveryStatusDocument massUpdateBaggageDeliveryStatus) {

		throw new java.lang.UnsupportedOperationException("Operation not supported...");
	}

	
	
	private static String getBDOStatus(String code){
		if(code != null){
			if(code.equalsIgnoreCase("OD")){
				return "DELIVERED";
			}
			if(code.equalsIgnoreCase("UP")){
				return "PICKED_UP";
			}
			if(code.equalsIgnoreCase("OF")){
				return "ON_TRUCK";
			}
			if(code.equalsIgnoreCase("UN")){
				return "UNABLE_DELIVER";
			}
			if(code.equalsIgnoreCase("AD")){
				return "PICKED_UP";//AD-assigned driver
			}
			if(code.equalsIgnoreCase("BD")){
				return "ON_TRUCK";//BD-out for delivery
			}
		}
		return "";
	}
	
	public org.tempuri.UpdateBaggageDeliveryStatusResponseDocument updateBaggageDeliveryStatus(org.tempuri.UpdateBaggageDeliveryStatusDocument updateBaggageDeliveryStatus) {

		logger.info(updateBaggageDeliveryStatus);
		
		BaggageDeliveryStatusTrackerRequest req = updateBaggageDeliveryStatus.getUpdateBaggageDeliveryStatus().getRequest();
		
		UpdateBaggageDeliveryStatusResponseDocument res = UpdateBaggageDeliveryStatusResponseDocument.Factory.newInstance();

		BaggageDeliveryStatusTrackerResponse res2 = res.addNewUpdateBaggageDeliveryStatusResponse().addNewUpdateBaggageDeliveryStatusReturn();
		res2.setTransactionSuccessful(true);

		// TODO : fill this with the necessary business logic
		
		String endpoint = null;
		String companycode = null;
		String ntusername = null;
		String ntpassword = null;
		String username = null;
		
		if(req != null && req.getBdoNumber() != null){
			companycode = req.getBdoNumber().substring(3, 5);
			if("US".equalsIgnoreCase(companycode)){
				username = TracerProperties.get(TracerProperties.NTCORE_PROFILE_US);
			} else if ("WS".equalsIgnoreCase(companycode)){
				username = TracerProperties.get(TracerProperties.NTCORE_PROFILE_WS);
			} else {
				logger.error("UpdateBaggageDeliveryStatus unsupported company: " + companycode);
				return res;//not supported, bail
			}
		} else {
			logger.error("UpdateBaggageDelieveryStatus no BDO number provided");
			return res;
		}
		
		Session sess = HibernateWrapper.getSession().openSession();
		User user = UserDao.getByUsername(sess, username);
		sess.close();
		endpoint = user.getProfile().getParameters().get(ParameterType.NTCORE_ENDPOINT);
		ntusername = user.getProfile().getParameters().get(ParameterType.NTCORE_USER);
		ntpassword = user.getProfile().getParameters().get(ParameterType.NTCORE_PASS);
		
		try {
			
			
			NTCoreServiceStub ntcore = new NTCoreServiceStub(endpoint);

			UpdateBdoDeliveryDocument ntreq = UpdateBdoDeliveryDocument.Factory.newInstance();
			UpdateBdoDelivery ubd = ntreq.addNewUpdateBdoDelivery();
			ubd.setUsername(ntusername);
			ubd.setPassword(ntpassword);
			ubd.setCompanycode(companycode);
			
			BdoStatusUpdate update = ubd.addNewUpdates();
			update.setStatus(getBDOStatus(req.getDeliveryStatus()));
			update.setStatusDateTime(req.getStatusDateTime());
			
			Integer bdo_id = new Integer(req.getBdoNumber().substring(5));
			update.setBdo(bdo_id);
			
			logger.info(ntreq);
			UpdateBdoDeliveryResponseDocument ntres = ntcore.UpdateBdoDelivery(ntreq);
			logger.info(ntres);
			
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return res;
	}
	
}
