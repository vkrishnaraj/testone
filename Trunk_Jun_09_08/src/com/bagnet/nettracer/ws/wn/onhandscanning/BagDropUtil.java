package com.bagnet.nettracer.ws.wn.onhandscanning;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.BagDropUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.ws.wn.onhandscanning.SaveBagDropTimeResponseDocument.SaveBagDropTimeResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.pojo.xsd.BagDrop;

public class BagDropUtil {
	
	private static Logger logger = Logger.getLogger(BagDropUtil.class);
	
	/**
	 * Web Service Util method for creating or updating a BagDrop.  Determining an existing bag drop to update is a function of companycode, flight number and arrival time.
	 * 
	 * On successful update, return whether the bag drop was created or updated along with a copy of the bagdrop as it exists in NetTracer
	 * 
	 * @param saveBagDropTime
	 * @return
	 */
	public static com.bagnet.nettracer.ws.wn.onhandscanning.SaveBagDropTimeResponseDocument saveBagDropTime(com.bagnet.nettracer.ws.wn.onhandscanning.SaveBagDropTimeDocument saveBagDropTime)
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
			agent = OnhandScanningServiceUtil.getAgent(auth);
		} catch (Exception e) {
			serviceResponse.setSuccess(false);
			serviceResponse.setValidUser(false);
			serviceResponse.addError(e.getMessage());
			logger.info(resDoc);
			return resDoc;
		}
		serviceResponse.setValidUser(true);
		
		BagDrop wsbagdrop = null;
		com.bagnet.nettracer.tracing.db.BagDrop ntbagdrop = null;
		boolean previouslyEntered = false;
		
		if(saveBagDropTime != null && saveBagDropTime.getSaveBagDropTime() != null && saveBagDropTime.getSaveBagDropTime().getBagDrop() != null
				&& saveBagDropTime.getSaveBagDropTime().getBagDrop().getBagDropDatetime() != null){
			
			wsbagdrop = saveBagDropTime.getSaveBagDropTime().getBagDrop();			
			long bagDropId = BagDropUtils.bagdropExists(
					wsbagdrop.getAirlineCode(), 
					wsbagdrop.getFlightNumber(), 
					wsbagdrop.getArrivalStationCode(), 
					wsbagdrop.getScheduleArrivalDatetime()!=null?wsbagdrop.getScheduleArrivalDatetime().getTime():null);
			if(bagDropId > 0){
				//update existing bagdrop
				ntbagdrop = BagDropUtils.getBagDropById(agent, bagDropId);
				ntbagdrop.setEntryMethod(TracingConstants.BAGDROP_ENTRY_METHOD_SCANNER);
				ntbagdrop.setBagDropTime(DateUtils.convertToGMTDate(wsbagdrop.getBagDropDatetime().getTime()));
				if(BagDropUtils.saveOrUpdateBagDrop(agent, ntbagdrop) > 0){
					serviceResponse.setCreateUpdateIndicator(OnhandScanningServiceImplementation.STATUS_UPDATE);
					previouslyEntered = true;
				} else {
					serviceResponse.setSuccess(false);
					serviceResponse.addError("Error updating bagdrop");
					logger.info(resDoc);
					return resDoc;
				}
			} else {
				//create new bagdrop
				ntbagdrop = wsToNtBagDrop(wsbagdrop);
				ntbagdrop.setCreateAgent(agent);
				ntbagdrop.setEntryMethod(TracingConstants.BAGDROP_ENTRY_METHOD_SCANNER);
				if(BagDropUtils.saveOrUpdateBagDrop(agent, ntbagdrop) > 0){
					serviceResponse.setCreateUpdateIndicator(OnhandScanningServiceImplementation.STATUS_CREATE);
				} else {
					serviceResponse.setSuccess(false);
					serviceResponse.addError("Error updating bagdrop");
					logger.info(resDoc);
					return resDoc;
				}
			}
		} else {
			serviceResponse.setSuccess(false);
			serviceResponse.addError("Please provide bagdrop time");
			logger.info(resDoc);
			return resDoc;
		}
		
		//Return copy of ntbagdrop
		serviceResponse.setBagDrop(ntToWsBagDrop(BagDropUtils.getBagDropById(agent, ntbagdrop.getId())));
		serviceResponse.getBagDrop().setPreviouslyEnteredFlag(previouslyEntered);
		serviceResponse.setSuccess(true);
		logger.info(resDoc);
		return resDoc;
	}
	
	/**
	 * Maps the web service bagdrop to a newly created NetTracer BagDrop.  All timestamps are to be converted to GMT
	 * 
	 * @param wsbagdrop
	 * @return
	 */
	private static com.bagnet.nettracer.tracing.db.BagDrop wsToNtBagDrop(BagDrop wsbagdrop){
		com.bagnet.nettracer.tracing.db.BagDrop ntbagdrop = new com.bagnet.nettracer.tracing.db.BagDrop();
		if(wsbagdrop != null){
			ntbagdrop.setAirline(wsbagdrop.getAirlineCode());
			ntbagdrop.setFlight(wsbagdrop.getFlightNumber());
			if(wsbagdrop.getBagDropDatetime() != null){
				ntbagdrop.setBagDropTime(DateUtils.convertToGMTDate(wsbagdrop.getBagDropDatetime().getTime()));
			}
			if(wsbagdrop.getScheduleArrivalDatetime() != null){
				ntbagdrop.setSchArrivalDate(DateUtils.convertToGMTDate(wsbagdrop.getScheduleArrivalDatetime().getTime()));
			}
			ntbagdrop.setArrivalStationCode(wsbagdrop.getArrivalStationCode());
			ntbagdrop.setCreateDate(DateUtils.convertToGMTDate(new Date()));
		}
		return ntbagdrop;
	}

	/**
	 * Maps a NetTracer BagDrop to a newly created web service bagdrop
	 * 
	 * The timestamps retrieved from NetTracer are in GMT, however, when loaded from the database is treated as local server time due to a jboss setting; must manually set offset to GMT
	 * 
	 * @param ntbagdrop
	 * @return
	 */
	private static BagDrop ntToWsBagDrop(com.bagnet.nettracer.tracing.db.BagDrop ntbagdrop){
		BagDrop wsbagdrop = BagDrop.Factory.newInstance();
		if(ntbagdrop != null){
			wsbagdrop.setAirlineCode(ntbagdrop.getAirline());
			wsbagdrop.setArrivalStationCode(ntbagdrop.getArrivalStationCode());
			wsbagdrop.setFlightNumber(ntbagdrop.getFlight());

			Calendar bagDropTime = GregorianCalendar.getInstance();
			bagDropTime.setTime(ntbagdrop.getBagDropTime());
			wsbagdrop.setBagDropDatetime(setCalendarGMT(bagDropTime));

			Calendar scheduleArrivalTime = GregorianCalendar.getInstance();
			scheduleArrivalTime.setTime(ntbagdrop.getSchArrivalDate());
			wsbagdrop.setScheduleArrivalDatetime(setCalendarGMT(scheduleArrivalTime));
		}
		return wsbagdrop;
	}
	
	/**
	 * Manually sets the offset to GMT
	 * 
	 * @param cal
	 * @return
	 */
	private static Calendar setCalendarGMT(Calendar cal){
		Calendar ret = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		ret.set(
				cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH),
				cal.get(Calendar.HOUR_OF_DAY),
				cal.get(Calendar.MINUTE),
				cal.get(Calendar.SECOND)
		);
		return ret;
	}
}
