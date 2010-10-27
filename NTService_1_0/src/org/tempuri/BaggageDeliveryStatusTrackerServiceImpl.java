/**
 * BaggageDeliveryStatusTrackerServiceSkeleton.java
 *
 */
package org.tempuri;

import org.apache.log4j.Logger;

public class BaggageDeliveryStatusTrackerServiceImpl extends BaggageDeliveryStatusTrackerServiceSkeleton {

	private static Logger logger = Logger.getLogger(BaggageDeliveryStatusTrackerServiceImpl.class);

	public org.tempuri.MassUpdateBaggageDeliveryStatusResponseDocument massUpdateBaggageDeliveryStatus(org.tempuri.MassUpdateBaggageDeliveryStatusDocument massUpdateBaggageDeliveryStatus) {

		throw new java.lang.UnsupportedOperationException("Operation not supported...");
	}

	public org.tempuri.UpdateBaggageDeliveryStatusResponseDocument updateBaggageDeliveryStatus(org.tempuri.UpdateBaggageDeliveryStatusDocument updateBaggageDeliveryStatus) {

		logger.info(updateBaggageDeliveryStatus);
		
		BaggageDeliveryStatusTrackerRequest req = updateBaggageDeliveryStatus.getUpdateBaggageDeliveryStatus().getRequest();
		req.getBdoNumber();
		req.getDeliveryStatus();
		req.getStatusDateTime();
		
		
		UpdateBaggageDeliveryStatusResponseDocument res = UpdateBaggageDeliveryStatusResponseDocument.Factory.newInstance();

		BaggageDeliveryStatusTrackerResponse res2 = res.addNewUpdateBaggageDeliveryStatusResponse().addNewUpdateBaggageDeliveryStatusReturn();
		res2.setTransactionSuccessful(true);

		// TODO : fill this with the necessary business logic

		return res;
	}

}
