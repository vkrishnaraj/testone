package com.bagnet.nettracer.tracing.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;

import aero.nettracer.general.services.GeneralServiceBean;

import com.bagnet.nettracer.tracing.bmo.BagDropBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BagDrop;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.audit.Audit_BagDrop;
import com.bagnet.nettracer.tracing.dto.BagDropDTO;
import com.bagnet.nettracer.exceptions.InvalidDateRangeException;
import com.bagnet.nettracer.exceptions.InvalidStationException;
import com.bagnet.nettracer.exceptions.MissingRequiredFieldsException;

/**
 * @author Loupas
 *
 */
public class BagDropUtils {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(BagDropUtils.class);
	
	private static MessageResources messages = null;
	
	static {
		try {
			messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		} catch (Exception e) {
			// Ignore
		}
	}
	
	private static BagDropBMO bdbmo = null;
	
	/**
	 * If bdbmo is null, create new BagDropBMO.  Otherwise use existing bdbmo.
	 * This is to allow unit test to explicitly set a mock BagDropBMO object.
	 * 
	 * @return
	 */
	protected static BagDropBMO getBagDropBMO(){
		if(bdbmo == null){
			bdbmo = new BagDropBMO();
		}
		return bdbmo;
	}
	
	/**
	 * @param bdbmo
	 */
	protected void setBagDropBMO(BagDropBMO bmo){
		bdbmo = bmo;
	}

	/**
	 * Determines if a bag drop exists for the given airline, flight number, arrival station and scheduled arrival date
	 * 
	 * The schedule arrival date range is defined as the beginning of the specified day to the beginning of the next day
	 * Assumes that the date provided has already been converted to GMT (currently the only two methods for creating a new
	 * BagDrop is the scanner implementation and reservation implementation, both of which converts to GMT before invoking 
	 * this method)
	 * 
	 * @param companycode
	 * @param flightNum
	 * @param stationcode
	 * @param date
	 * @return
	 */
	public static long bagdropExists(String companycode, String flightNum, String stationcode, Date date){
		if(companycode == null || flightNum == null || date == null){
			throw new MissingRequiredFieldsException();
		}
		
		GregorianCalendar start = new GregorianCalendar();
		start.setTime(date);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);
		
		GregorianCalendar end = new GregorianCalendar();
		end.setTime(date);
		end.add(Calendar.DATE, 1);
		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		end.set(Calendar.MILLISECOND, 0);
		
		return getBagDropBMO().getBagDropID(companycode, flightNum, stationcode, start.getTime(), end.getTime());
	}
	
	/**
	 * Updates an existing bag drop if it exists, otherwise insert new bag drop
	 * 
	 * @param agent
	 * @param bagdrop
	 * @return
	 */
	public static long saveOrUpdateBagDrop(Agent agent, BagDrop bagdrop) {
		if(bagdrop == null){
			throw new MissingRequiredFieldsException();
		}
		long id = 0;
		if(bagdrop.getId() > 0){
			return updateBagDrop(agent, bagdrop);
		} else if ((id = bagdropExists(
				bagdrop.getAirline(), 
				bagdrop.getFlight(), 
				bagdrop.getArrivalStationCode(), 
				bagdrop.getSchArrivalDate())) > 0){
			bagdrop.setId(id);
			return updateBagDrop(agent, bagdrop);	
		} else {
			return saveNewBagDrop(agent, bagdrop);
		}
	}
	
	/**
	 * Saves a new BagDrop
	 * 
	 * @param agent
	 * @param bagdrop
	 * @return
	 */
	public static long saveNewBagDrop(Agent agent, BagDrop bagdrop){
		bagdrop.setCreateDate(DateUtils.convertToGMTDate(new Date()));
		bagdrop.setCreateAgent(agent);
		return getBagDropBMO().insertBagDrop(bagdrop, agent);
	}
	
	/**
	 * Update a BagDrop if allowed (currently only a date restriction applies)
	 * 
	 * @param agent
	 * @param bagdrop
	 * @return
	 * @throws InvalidDateRangeException
	 */
	public static long updateBagDrop(Agent agent, BagDrop bagdrop) throws InvalidDateRangeException, InvalidStationException{
		if(agent == null || bagdrop == null){
			throw new MissingRequiredFieldsException();
		}
		if(!canUpdateByDate(agent, bagdrop.getSchArrivalDate())){
			throw new InvalidDateRangeException();
		}
		if(!canUpdateByStation(agent, bagdrop.getArrivalStationCode())){
			throw new InvalidStationException();
		}

		/**Do not overwrite original createAgent and createDate**/
		BagDrop old = getBagDropBMO().getBagDropByID(bagdrop.getId());
		bagdrop.setCreateAgent(old.getCreateAgent());
		bagdrop.setCreateDate(old.getCreateDate());
		
		/**Do not overwrite bagdrop time with null when refreshing flight info**/
		if(bagdrop.getBagDropTime() == null){
			bagdrop.setBagDropTime(old.getBagDropTime());
		}

		return getBagDropBMO().insertBagDrop(bagdrop, agent);
	}
	
	/**
	 * Determines if a BagDrop falls within the eligible date range allowed for updating
	 * 
	 * @param date
	 * @return
	 */
	protected static boolean canUpdateByDate(Agent agent, Date date){
		int daysBack = getModifyRange(agent);
		long day = 24 * 60 * 60 * 1000;
		Date now = DateUtils.convertToGMTDate(new Date());
		return now.getTime() - date.getTime() < daysBack * day;
	}
	
	/**
	 * Determines if a BagDrop can be update based on CBRO permissions
	 * 
	 * @param agent
	 * @param station
	 * @return
	 */
	protected static boolean canUpdateByStation(Agent agent, String station){
		if(station == null || agent == null){
			return false;
		} 
		
		if(isCbroManager(agent)){
			//if the agent is a manager, they have permission to update regardless of station
			return true;
		} else {
			//if the agent is not a manager, can only update if the station matches the agent station
			if(agent.getStation().getStationcode().equalsIgnoreCase(station)){
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * Returns the date range in days for which a given agent is allowed to modify a BagDrop
	 * 
	 * Currently for SWA, 30 days for BagDropAdmin users, otherwise 1 day.
	 * 
	 * @param agent
	 * @return
	 */
	public static int getModifyRange(Agent agent){
		return isBagDropAdmin(agent)?30:1;//consider making this configurable in property table or company specific vars
	}
	
	/**
	 * Fetches flight information from third party reservation system.
	 * Store all dates in GMT
	 * 
	 * @param agent
	 * @param station
	 * @param date
	 * @return
	 * @throws InvalidStationException, Exception 
	 * @throws InvalidDateRangeException
	 */
	public static void refreshFlightInfo(Agent agent, String station, Date date) throws InvalidStationException,MissingRequiredFieldsException {
		if(agent == null || station == null || date == null){
			throw new MissingRequiredFieldsException();
		}
		if(!canUpdateByStation(agent,station)){
			throw new InvalidStationException();
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		
		List<BagDrop> bagDropList = SpringUtils.getReservationIntegration().getFlightInfo(station, cal);
		if(bagDropList != null){
			for(BagDrop bagDrop:bagDropList){
				bagDrop.setEntryMethod(TracingConstants.BAGDROP_ENTRY_METHOD_RESERVATION);
				if(bagDrop.getSchArrivalDate()!=null){
					bagDrop.setSchArrivalDate(DateUtils.convertToGMTDate(bagDrop.getSchArrivalDate()));
				}
				if(bagDrop.getActArrivalDate()!=null){
					bagDrop.setActArrivalDate(DateUtils.convertToGMTDate(bagDrop.getActArrivalDate()));
				}
				BagDropUtils.saveOrUpdateBagDrop(agent, bagDrop);
			}
		} 
	}
	
	/**
	 * Refreshes all flight info for a given date.  Primarily intended for use by daily crons
	 * 
	 * @param agent
	 * @param date
	 * @return
	 */
	public static boolean refreshFlightInfo(Agent agent, Date date){
		if(agent == null || date == null){
			throw new MissingRequiredFieldsException();
		}
		GeneralServiceBean utils = new GeneralServiceBean();
		utils.getStations(agent.getCompanycode_ID());
		List<Station> stationList = utils.getStations(agent.getCompanycode_ID());
		for(Station station:stationList){
			try {
				refreshFlightInfo(agent, station.getStationcode(), date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	/**
	 * Returns a List of BagDrop elements based on the criteria specified in the BagDropDTO
	 * 
	 * Sets the appropriate TimeZone and Date format information for each element before returning
	 * 
	 * @param agent
	 * @param dto
	 * @return
	 */
	public static List<BagDrop> searchBagdrop(Agent agent, BagDropDTO dto){
		List<BagDrop> returnList = getBagDropBMO().getBagDrop(dto);
		if(returnList != null && agent != null){
			TimeZone timeZone = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(agent.getDefaulttimezone()).getTimezone());
			for(BagDrop bagDrop:returnList){
				bagDrop.set_DATEFORMAT(agent.getDateformat().getFormat());
				bagDrop.set_TIMEFORMAT(agent.getTimeformat().getFormat());
				bagDrop.set_TIMEZONE(timeZone);
			}
		}
		return returnList;
	}
	
	/**
	 * Returns the number of BagDrop elements based on the criteria specified in the BagDropDTO
	 * 
	 * @param dto
	 * @return
	 */
	public static long searchBagdropCount(BagDropDTO dto){
		return getBagDropBMO().getBagDropCount(dto);
	}
	
	/**
	 * Returns the BagDrop element based on the BagDrop ID
	 * 
	 * Sets the appropriate TimeZone and Date format information for each element before returning
	 * 
	 * @param agent
	 * @param id
	 * @return
	 */
	public static BagDrop getBagDropById(Agent agent, long id){
		BagDrop bagDrop = getBagDropBMO().getBagDropByID(id);
		if(bagDrop != null && agent != null){
			TimeZone timeZone = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(agent.getDefaulttimezone()).getTimezone());
			bagDrop.set_DATEFORMAT(agent.getDateformat().getFormat());
			bagDrop.set_TIMEFORMAT(agent.getTimeformat().getFormat());
			bagDrop.set_TIMEZONE(timeZone);
		}
		return bagDrop;
	}
	
	/**
	 * Returns the audit history for the given bagdrop ID provided that the agent is a BagDropAdmin
	 * 
	 * @param agent
	 * @param bagdropId
	 * @return
	 */
	public static List<Audit_BagDrop> getAuditBagDropList(Agent agent, long bagdropId){
		if(!isBagDropAdmin(agent)){
			return null;
		}
		
		List<Audit_BagDrop> returnList = getBagDropBMO().getAudit_BagDropList(bagdropId);
		if(returnList != null && agent != null){
			String reservation = messages.getMessage(new Locale(agent.getCurrentlocale()), "bagdrop.entrymethod.type.res");
			String web = messages.getMessage(new Locale(agent.getCurrentlocale()), "bagdrop.entrymethod.type.web");
			String scanner = messages.getMessage(new Locale(agent.getCurrentlocale()), "bagdrop.entrymethod.type.scanner");
			
			TimeZone timeZone = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(agent.getDefaulttimezone()).getTimezone());
			
			for(Audit_BagDrop audit:returnList){
				audit.set_DATEFORMAT(agent.getDateformat().getFormat());
				audit.set_TIMEFORMAT(agent.getTimeformat().getFormat());
				audit.set_TIMEZONE(timeZone);
				switch(audit.getEntryMethod()){
				case TracingConstants.BAGDROP_ENTRY_METHOD_RESERVATION:
					audit.setDispEntryMethod(reservation);
					break;
				case TracingConstants.BAGDROP_ENTRY_METHOD_WEB:
					audit.setDispEntryMethod(web);
					break;
				case TracingConstants.BAGDROP_ENTRY_METHOD_SCANNER:
					audit.setDispEntryMethod(scanner);
					break;
				default:audit.setDispEntryMethod("");
				};
			}
		}
		return returnList;
	}
	
	/**
	 * Returns whether the agent is a BagDropAdmin
	 * 
	 * @param agent
	 * @return
	 */
	public static boolean isBagDropAdmin(Agent agent){
		if(agent != null){
			return UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BAG_DROP_ADMIN,agent);
		} else {
			return false;
		}
	}
	
	/**
	 * Returns whether the agent is a CBRO manager
	 * 
	 * @param agent
	 * @return
	 */
	public static boolean isCbroManager(Agent agent){
		if(agent != null){
			return UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CBRO_MGMT,agent);
		} else {
			return false;
		}
	}
	
	/**
	 * Auto fetches the flight information for a given company and station if needed.
	 * Will only update if the agent has permission to update bagdrop for the given station.
	 * 
	 * @param agent
	 * @param station
	 * @param company
	 * @throws InvalidStationException
	 */
	public static void autoRefreshFlightInfo(Agent agent, String station, Company company)  throws InvalidStationException {
		if(needsFlightInfoRefresh(agent,station,company)){
			refreshFlightInfo(agent, station, new Date());
		}
	}
	
	/**
	 * Determines if flight information needs to be fetch for a given station based on the last time flight information was updated
	 * and the length of staleness that is allowed as specified in company specific variables
	 * 
	 * @param agent
	 * @param station
	 * @param company
	 * @return
	 */
	protected static boolean needsFlightInfoRefresh(Agent agent, String station, Company company){
		if(agent == null || company == null || company.getVariable() == null || station == null){
			return false;
		}
		
		if(company.getVariable().getBagdrop_autorefresh_mins() > 0){
			int autorefreshMins = company.getVariable().getBagdrop_autorefresh_mins();
			int minsSinceLastUpdate = minsSinceLastUpdate(station,company.getCompanyCode_ID(),TracingConstants.BAGDROP_ENTRY_METHOD_RESERVATION);
			if(minsSinceLastUpdate > autorefreshMins){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the number of mins since the most recent update for a given company, station and entryMethod (Reservation,Scanner,Web)
	 * If no entry is found, return Max int to signal that no entry exists
	 * 
	 * @param station
	 * @param companycode
	 * @param entryMethod
	 * @return
	 */
	protected static int minsSinceLastUpdate(String station, String companycode, int entryMethod){
		Date lastUpdated = getBagDropBMO().getLastUpdateDate(station, companycode, entryMethod);
		if(lastUpdated == null){
			return Integer.MAX_VALUE;
		} else {
			Date now = DateUtils.convertToGMTDate(new Date());
			return (int) ((now.getTime() - lastUpdated.getTime()) / 60000);
		}
	}
}
