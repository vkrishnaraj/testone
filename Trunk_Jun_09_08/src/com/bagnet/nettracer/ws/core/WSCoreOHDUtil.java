package com.bagnet.nettracer.ws.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_Inventory;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger;

public class WSCoreOHDUtil {

	
	/**
	 * this method modularize the method in service skeleton
	 * @param getOHD
	 * @return
	 */
  public com.bagnet.nettracer.ws.core.GetOHDResponseDocument getOHD(
      com.bagnet.nettracer.ws.core.GetOHDDocument getOHD) {
    String ohd_id = getOHD.getGetOHD().getOhdId();
    GetOHDResponseDocument resDoc = GetOHDResponseDocument.Factory.newInstance();
    GetOHDResponseDocument.GetOHDResponse res = resDoc.addNewGetOHDResponse();
    
    com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD so = null;
    
    // reauth
  	String session_id = getOHD.getGetOHD().getSessionId();
  	String result = WSCoreUtil.reauth(session_id,TracingConstants.SYSTEM_COMPONENT_NAME_ON_HAND_BAG);
  	if (result != null) {
  		so = com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD.Factory.newInstance();
  		so.setErrorcode("get ohd failed: " + result);
  	} else {
  		so = findOHD(ohd_id);
  	}
    res.setReturn(so);
    return resDoc;
  }
  
	/**
	 * this method modularize the method in service skeleton
	 * @param getOHD
	 * @return
	 */
  public com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument getOHDsForWT(
      com.bagnet.nettracer.ws.core.GetOHDsForWTDocument getOHDsForWT) {
    String companycode = getOHDsForWT.getGetOHDsForWT().getCompanycode();
    GetOHDsForWTResponseDocument resDoc = GetOHDsForWTResponseDocument.Factory.newInstance();
    GetOHDsForWTResponseDocument.GetOHDsForWTResponse res = resDoc.addNewGetOHDsForWTResponse();

    
    // reauth
  	String session_id = getOHDsForWT.getGetOHDsForWT().getSessionId();
  	String result = WSCoreUtil.reauth(session_id,TracingConstants.SYSTEM_COMPONENT_NAME_ON_HAND_BAG);
  	if (result != null) {
  		com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD so = com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD.Factory.newInstance();
  		so.setErrorcode("get ohd for wt failed: " + result);
  		res.setReturnArray(0, so);
  	} else {
  		com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD[] so = findOHDsForWT(companycode);
      res.setReturnArray(so);
  	}
    return resDoc;
  }
  
  /**
   * insert ohd into nt from outside
   * @param insertOHD
   * @return
   */
  public com.bagnet.nettracer.ws.core.InsertOHDResponseDocument insertOHD(
      com.bagnet.nettracer.ws.core.InsertOHDDocument insertOHD) {

  	InsertOHDResponseDocument resDoc = InsertOHDResponseDocument.Factory.newInstance();
  	InsertOHDResponseDocument.InsertOHDResponse res = resDoc.addNewInsertOHDResponse();
  	
  	// reauth
  	WSOHD ws = insertOHD.getInsertOHD().getSo();
  	String session_id = ws.getWebserviceSessionID();
  	String result = WSCoreUtil.reauth(session_id,TracingConstants.SYSTEM_COMPONENT_NAME_ADD_ON_HAND_BAG);
  	if (result != null) {
  		res.setReturn("insert ohd failed: " + result);
  		return resDoc;
  	}
  	// passed auth, now insert
  	
    String ohd_id = insertOHD(ws);
    if (ohd_id == null) ohd_id = "insert ohd failed: " + ws.getErrorcode();
    res.setReturn(ohd_id);
    return resDoc;
  }
  

 	public com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD findOHD(String ohd_id) {
 		
 		try {
			if (ohd_id == null || ohd_id.length() == 0)
				return null;

			OhdBMO oBMO = new OhdBMO();
			OHD oDTO = oBMO.findOHDByID(ohd_id.trim().toUpperCase());
			if (oDTO == null)
				return null;

			return OHDtoWS_Mapping(oDTO);
		} catch (Exception e) {
			return null;
		}
	}
 	
 	/**
 	 * insert ohd into nettracer
 	 * @param ws
 	 * @return
 	 */
 	public String insertOHD(WSOHD ws) {
 		
 		try {
			if (ws == null)
				return null;

			return WStoOHD_Mapping(ws);
		} catch (Exception e) {
			ws.setErrorcode("error inserting ohd into nt, error: " + e.toString());
			return null;
		}
	}
	
 	/**
 	 * return a list of WSOHDs that has passed N days specified in company specific variable
 	 * 
 	 * @param companycode
 	 * @return
 	 */
 	public com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD[] findOHDsForWT(String companycode) {
 		
 		try {
			if (companycode == null || companycode.length() == 0)
				return null;
			Company_Specific_Variable csv = AdminUtils.getCompany(companycode).getVariable();
			// default to 5 days to send to WT
			int numdays = 5;
			//numdays = csv.getdaysToWT();
			OhdBMO oBMO = new OhdBMO();
			ArrayList al = (ArrayList)oBMO.findOHDforWT(numdays, companycode);
			if (al != null && al.size() > 0) {
				int numohds = al.size();
				com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD[] ohds = new com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD[numohds];
				for (int i=0;i<al.size();i++) {
					ohds[i] = OHDtoWS_Mapping((OHD)al.get(i));
				}
				return ohds;
			}

			return null;

			
		} catch (Exception e) {
			return null;
		}
	}
 	
 	/**
 	 * convert NT ohd object into Webservices WSOHD object
 	 * @param oDTO
 	 * @return
 	 * @throws Exception
 	 */
 	public com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD OHDtoWS_Mapping(OHD oDTO) throws Exception {
 		
		com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD so = com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD.Factory.newInstance();
		
		so.setOHDID(oDTO.getOHD_ID());
		so.setStatus(oDTO.getStatus().getDescription());
		so.setFoundAtStation(oDTO.getFoundAtStation().getStationcode());
		so.setHoldingStation(oDTO.getHoldingStation().getStationcode());
		so.setStorageLocation(oDTO.getStorage_location());
		so.setAgent(oDTO.getAgent().getUsername());
		so.setFounddatetime(WSCoreUtil.formatDatetoString(oDTO.getFounddate(),oDTO.getFoundtime()));
		so.setBagarrivedate(WSCoreUtil.formatDatetoString(oDTO.getBagarrivedate(),null));
		so.setBagtagnum(oDTO.getClaimnum());
		so.setColor(oDTO.getColor());
		so.setType(oDTO.getType());
		so.setLastname(oDTO.getLastname());
		so.setFirstname(oDTO.getFirstname());
		so.setMiddlename(oDTO.getMiddlename());
		if (oDTO.getMembership() != null)
			so.setMembership(oDTO.getMembership().getMembershipnum());
		so.setRecordLocator(oDTO.getRecord_locator());
		if (oDTO.getXdescelement_ID_1() > 0)
			so.setXdescelement1(TracerUtils.getXdescelement(oDTO.getXdescelement_ID_1()).getCode());
		if (oDTO.getXdescelement_ID_2() > 0)
			so.setXdescelement2(TracerUtils.getXdescelement(oDTO.getXdescelement_ID_2()).getCode());
		if (oDTO.getXdescelement_ID_3() > 0)
			so.setXdescelement3(TracerUtils.getXdescelement(oDTO.getXdescelement_ID_3()).getCode());
		so.setManufacturer(oDTO.getManufacturer());
		if (oDTO.getDisposal_status() != null) so.setDisposalStatus(oDTO.getDisposal_status().getDescription());
		so.setCloseDate(WSCoreUtil.formatDatetoString(oDTO.getClose_date(),null));
		int c = 0;
		
		OHD_Inventory inv = null;
		if (oDTO.getItems() != null) {
			com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory oiarr = null;
			
			for (Iterator i = oDTO.getItems().iterator(); i.hasNext();) {

				oiarr = so.addNewInventories();
				inv = (OHD_Inventory) i.next();
				oiarr.setCategory(inv.getCategory());
				oiarr.setDescription(inv.getDescription());
				so.setInventoriesArray(c, oiarr);
				c++;
			}

		}
		
		OHD_Itinerary iti = null;
		if (oDTO.getItinerary() != null) {
			com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary itiarr = null;
			c = 0;
			for (Iterator i = oDTO.getItinerary().iterator(); i.hasNext();) {
				itiarr = so.addNewItineraries();
				iti = (OHD_Itinerary) i.next();
				itiarr.setItineraryID(iti.getItinerary_ID());
				itiarr.setActarrivetime(WSCoreUtil.formatDatetoString(null,iti.getActarrivetime()));
				itiarr.setActdeparttime(WSCoreUtil.formatDatetoString(null,iti.getActdeparttime()));
				itiarr.setAirline(iti.getAirline());
				itiarr.setArrivedate(WSCoreUtil.formatDatetoString(iti.getArrivedate(),null));
				itiarr.setDepartdate(WSCoreUtil.formatDatetoString(iti.getDepartdate(),null));
				itiarr.setFlightnum(iti.getFlightnum());
				itiarr.setItinerarytype(iti.getItinerarytype());
				itiarr.setLegfrom(iti.getLegfrom());
				itiarr.setLegto(iti.getLegto());
				itiarr.setLegfromType(iti.getLegfrom_type());
				itiarr.setLegtoType(iti.getLegto_type());
				itiarr.setScharrivetime(WSCoreUtil.formatDatetoString(null,iti.getScharrivetime()));
				itiarr.setSchdeparttime(WSCoreUtil.formatDatetoString(null,iti.getSchdeparttime()));
				
				so.setItinerariesArray(c, itiarr);
				c++;
			}

		}

		return so;
 	}


 	/**
 	 * map wsohd object into ohd object
 	 * @param ws
 	 * @return
 	 * @throws Exception
 	 */
 	public String WStoOHD_Mapping(WSOHD ws) throws Exception {
 		OhdBMO obmo = new OhdBMO();
 		OHD ohd = null;
 		
 		if (ws.getOHDID() == null || ws.getOHDID().length() == 0) ohd = new OHD();
 		else {
 			ohd = obmo.findOHDByID(ws.getOHDID());
 			if (ohd == null) {
 				ws.setErrorcode("ohd id specified does not exist in NetTracer, please remove it from xml if you want to insert new OHD.");
 				return null;
 			}
 		}
 		// status
 		Status status = WSCoreUtil.getStatus(ws.getStatus(),TracingConstants.TABLE_ON_HAND);
 		if (status == null) {
 			ws.setErrorcode("invalid status for ohd, please consult NetTracer Webservices help for statuses.");
 			return null;
 		}
 		ohd.setStatus(status);
 		
 		// found at station
 		Station foundandholds = TracerUtils.getStationByCode(ws.getFoundAtStation(), ws.getCompanycodeId());
 		if (foundandholds == null) {
 			ws.setErrorcode("invalid found station code, please add the station into NetTracer first");
 			return null;
 		}
 		ohd.setFoundAtStation(foundandholds);
 		
 		// holding station
 		foundandholds = TracerUtils.getStationByCode(ws.getHoldingStation(), ws.getCompanycodeId());
 		if (foundandholds == null) {
 			ws.setErrorcode("invalid holding station code, please add the station into NetTracer first");
 			return null;
 		}
 		ohd.setHoldingStation(foundandholds);
 		
 		ohd.setStorage_location(ws.getStorageLocation());
 		
 		// agent
 		if (ws.getAgent() == null || ws.getAgent().length() == 0) {
 			ws.setErrorcode("invalid agent username, please add the agent into NetTracer first");
 			return null;
 		}
 		Agent agent = AdminUtils.getAgentBasedOnUsername(ws.getAgent(), ws.getCompanycodeId());
 		if (agent == null) {
 			ws.setErrorcode("invalid agent username, please add the agent into NetTracer first");
 			return null;
 		}
 		ohd.setAgent(agent);
 		
 		// found date time
 		String datetimestr = ws.getFounddatetime();
		Date thedate = DateUtils.convertToDate(datetimestr, WSCoreUtil.WS_DATETIMEFORMAT, null);
		if (thedate == null) {
			ws.setErrorcode("invalid datetime format for found datetime, please use NT standard: " + WSCoreUtil.WS_DATETIMEFORMAT);
 			return null;
		}
		ohd.setFounddate(thedate);
		ohd.setFoundtime(thedate);
		
		// bag arrive date
		if (ws.getBagarrivedate() != null && ws.getBagarrivedate().length() > 0) {
			datetimestr = ws.getBagarrivedate();
			thedate = DateUtils.convertToDate(datetimestr, WSCoreUtil.WS_DATEFORMAT, null);
			if (thedate == null) {
				ws.setErrorcode("invalid date format for bag arrive date, please use NT standard: " + WSCoreUtil.WS_DATEFORMAT);
	 			return null;
			}
	 		ohd.setBagarrivedate(thedate);
		}
		
 		ohd.setClaimnum(ws.getBagtagnum());
 		ohd.setColor(ws.getColor());
 		ohd.setType(ws.getType());
 		ohd.setLastname(ws.getLastname());
 		ohd.setFirstname(ws.getFirstname());
 		ohd.setMiddlename(ws.getMiddlename());
 		if (ws.getMembership() != null && ws.getMembership().length() > 0) {
 			AirlineMembership am = new AirlineMembership();
 			am.setMembershipnum(ws.getMembership());
 			ohd.setMembership(am);
 		}
 		
 		ohd.setRecord_locator(ws.getRecordLocator());
 		ohd.setXdescelement_ID_1(TracerUtils.getXdescelementid(ws.getXdescelement1()));
 		ohd.setXdescelement_ID_2(TracerUtils.getXdescelementid(ws.getXdescelement2()));
 		ohd.setXdescelement_ID_3(TracerUtils.getXdescelementid(ws.getXdescelement3()));
 		
 		ohd.setManufacturer_ID(TracingConstants.MANUFACTURER_OTHER_ID);
 		ohd.setManufacturer_other(ws.getManufacturer());
 		
 		if (ws.getDisposalStatus() != null && ws.getDisposalStatus().length() > 0) {
 			Status dstatus = WSCoreUtil.getStatus(ws.getDisposalStatus(),TracingConstants.TABLE_DISPOSAL_LOST_FOUND);
 			ohd.setDisposal_status(dstatus);
 		}
 		
 		if (ws.getCloseDate() != null && ws.getCloseDate().length() > 0) {
	 		datetimestr = ws.getCloseDate();
			thedate = DateUtils.convertToDate(datetimestr, WSCoreUtil.WS_DATEFORMAT, null);
			if (thedate == null) {
				ws.setErrorcode("invalid date format for close date, please use NT standard: " + WSCoreUtil.WS_DATEFORMAT);
	 			return null;
			}
 		}
 		ohd.setClose_date(thedate);
 		
 		// passengers
		HashSet<OHD_Passenger> pahash = new HashSet<OHD_Passenger>();
		HashSet<OHD_Address> addrhash = null;
		
		WSPassenger wp = null;
		OHD_Address addr = null;
		OHD_Passenger pa = null;
		
		if (ws.getPassengersArray() != null) {
			for (int i=0;i<ws.getPassengersArray().length;i++) {
				wp = ws.getPassengersArray(i);
				if (wp != null) {
					pa = new OHD_Passenger();
					pa.setOhd(ohd);
					pa.setFirstname(wp.getFirstname());
					pa.setIsprimary(wp.getIsprimary());
					pa.setLastname(wp.getLastname());
					pa.setMiddlename(wp.getMiddlename());
					
					addrhash = new HashSet<OHD_Address>();
					addr = new OHD_Address();
					addr.setOhd_passenger(pa);
					addr.setAddress1(wp.getAddress1());
					addr.setAddress2(wp.getAddress2());
					addr.setAltphone(wp.getAltphone());
					addr.setCity(wp.getCity());
					addr.setCountrycode_ID(wp.getCountrycodeID());
					addr.setEmail(wp.getEmail());
					addr.setHomephone(wp.getHomephone());
					addr.setMobile(wp.getMobile());
					addr.setPager(wp.getPager());
					addr.setProvince(wp.getProvince());
					addr.setState_ID(wp.getStateID());
					addr.setWorkphone(wp.getWorkphone());
					addr.setZip(wp.getZip());
					addrhash.add(addr);
					pa.setAddresses(addrhash);
					pahash.add(pa);
				}
			}
			ohd.setPassengers(pahash);
		}
		
 		// contents
		
		HashSet<OHD_Inventory> ii_set = new HashSet<OHD_Inventory>();
		
		OHD_Inventory ii = null;
		WSInventory wi = null;
		if (ws.getInventoriesArray() != null) {
			for (int i=0;i<ws.getInventoriesArray().length;i++) {
				wi = ws.getInventoriesArray(i);
				if (wi != null) {
					ii = new OHD_Inventory();
					ii.setOhd(ohd);
					ii.setDescription(wi.getDescription());
					ii.setOHD_categorytype_ID(WSCoreUtil.getContentCategory(wi.getCategory()));
					ii_set.add(ii);
				}
			}
			ohd.setItems(ii_set);
		}
		
 		// itinerary
		HashSet<OHD_Itinerary> oi_set = new HashSet<OHD_Itinerary>();
		
		OHD_Itinerary oi = null;
		WSItinerary wit = null;
		if (ws.getItinerariesArray() != null) {
			for (int i=0;i<ws.getItinerariesArray().length;i++) {
				wit = ws.getItinerariesArray(i);
				if (wit != null) {
					oi = new OHD_Itinerary();
					oi.setOhd(ohd);
					
					// actual arrive time
					datetimestr = wit.getActarrivetime();
					thedate = DateUtils.convertToDate(datetimestr, WSCoreUtil.WS_TIMEFORMAT, null);
					if (thedate == null) {
						ws.setErrorcode("invalid time format for actual arrive time, please use NT standard: " + WSCoreUtil.WS_TIMEFORMAT);
			 			return null;
					}
					oi.setActarrivetime(thedate);
					
					// actual depart time
					datetimestr = wit.getActdeparttime();
					thedate = DateUtils.convertToDate(datetimestr, WSCoreUtil.WS_TIMEFORMAT, null);
					if (thedate == null) {
						ws.setErrorcode("invalid time format for actual depart time, please use NT standard: " + WSCoreUtil.WS_TIMEFORMAT);
			 			return null;
					}
					oi.setActdeparttime(thedate);
					
					oi.setAirline(wit.getAirline());
					
					// arrive date
					datetimestr = wit.getArrivedate();
					thedate = DateUtils.convertToDate(datetimestr, WSCoreUtil.WS_DATEFORMAT, null);
					if (thedate == null) {
						ws.setErrorcode("invalid time format for arrive date, please use NT standard: " + WSCoreUtil.WS_DATEFORMAT);
			 			return null;
					}
					oi.setArrivedate(thedate);
					
					// depart date
					datetimestr = wit.getDepartdate();
					thedate = DateUtils.convertToDate(datetimestr, WSCoreUtil.WS_DATEFORMAT, null);
					if (thedate == null) {
						ws.setErrorcode("invalid time format for depart date, please use NT standard: " + WSCoreUtil.WS_DATEFORMAT);
			 			return null;
					}
					oi.setDepartdate(thedate);
					
					oi.setFlightnum(wit.getFlightnum());
					oi.setLegfrom(wit.getLegfrom());
					oi.setLegto(wit.getLegto());

					// scheduled arrive time
					datetimestr = wit.getScharrivetime();
					thedate = DateUtils.convertToDate(datetimestr, WSCoreUtil.WS_TIMEFORMAT, null);
					if (thedate == null) {
						ws.setErrorcode("invalid time format for scheduled arrive time, please use NT standard: " + WSCoreUtil.WS_TIMEFORMAT);
			 			return null;
					}
					oi.setScharrivetime(thedate);
					
					// scheduled depart time
					datetimestr = wit.getSchdeparttime();
					thedate = DateUtils.convertToDate(datetimestr, WSCoreUtil.WS_TIMEFORMAT, null);
					if (thedate == null) {
						ws.setErrorcode("invalid time format for scheduled depart time, please use NT standard: " + WSCoreUtil.WS_TIMEFORMAT);
			 			return null;
					}
					oi.setSchdeparttime(thedate);
					oi_set.add(oi);
				}
			}
			ohd.setItinerary(oi_set);
		}
		
		// insert into our db
		boolean result = obmo.insertOHD(ohd, ohd.getAgent());
		if (!result) {
			ws.setErrorcode("unable to insert ohd into NT, please contact NT support");
			return null;
		}
 		return ohd.getOHD_ID();
 
 	}

 	
}