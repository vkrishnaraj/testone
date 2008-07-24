package com.bagnet.nettracer.ws.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.Articles;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.ItemType;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSItem;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger;

public class WSCoreIncidentUtil {


	/**
	 * this method modularize the method in service skeleton
	 * @param getOHD
	 * @return
	 */
  public com.bagnet.nettracer.ws.core.GetIncidentResponseDocument getIncident(
      com.bagnet.nettracer.ws.core.GetIncidentDocument getIncident) {
    String incident_id = getIncident.getGetIncident().getIncidentId();
    GetIncidentResponseDocument resDoc = GetIncidentResponseDocument.Factory.newInstance();
    GetIncidentResponseDocument.GetIncidentResponse res = resDoc.addNewGetIncidentResponse();
    
    com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident si = null;
    
    // reauth
  	String session_id = getIncident.getGetIncident().getSessionId();
  	
  	// get the item type
  	String comp_id = TracingConstants.SYSTEM_COMPONENT_NAME_MISHANDLED_BAG;
  	if (getIncident.getGetIncident().getIncType() == null) {
  		si = com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident.Factory.newInstance();
  		si.setErrorcode("get incident failed: invalid incident type");
  		res.setReturn(si);
  		return resDoc;
  	} else {
  		ItemType itype = IncidentUtils.retrieveItemTypeWithDesc(getIncident.getGetIncident().getIncType());
  		if (itype.getItemType_ID() == TracingConstants.LOST_DELAY) {
  			comp_id = TracingConstants.SYSTEM_COMPONENT_NAME_MISHANDLED_BAG;
  		} else if (itype.getItemType_ID() == TracingConstants.MISSING_ARTICLES) {
  			comp_id = TracingConstants.SYSTEM_COMPONENT_NAME_MISSING_ARTICLES;
  		} else if (itype.getItemType_ID() == TracingConstants.DAMAGED_BAG) {
  			comp_id = TracingConstants.SYSTEM_COMPONENT_NAME_DAMAGED_BAG;
  		}
  	}
  	
  	String result = WSCoreUtil.reauth(session_id,comp_id);
  	if (result != null) {
  		si = com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident.Factory.newInstance();
  		si.setErrorcode("get incident failed: " + result);
  	} else {
  		si = findIncident(incident_id);
  	}
    res.setReturn(si);
    return resDoc;
  }
  

  /**
   * insert incident into nt from outside
   * @param insertIncident
   * @return
   */
  public com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument insertIncident(
      com.bagnet.nettracer.ws.core.InsertIncidentDocument insertIncident) {
      //TODO : fill this with the necessary business logic
  	WSIncident ws = insertIncident.getInsertIncident().getSi();
  	InsertIncidentResponseDocument resDoc = InsertIncidentResponseDocument.Factory.newInstance();
  	InsertIncidentResponseDocument.InsertIncidentResponse res = resDoc.addNewInsertIncidentResponse();
    
  	
  	String session_id = ws.getWebserviceSessionID();
  	
  	// get the item type
  	String comp_id = TracingConstants.SYSTEM_COMPONENT_NAME_ADD_MISHANDLED_BAG;
  	if (ws.getItemtype() == null) {
  		res.setReturn("insert incident failed: invalid incident type");
  		return resDoc;
  	} else {
  		ItemType itype = IncidentUtils.retrieveItemTypeWithDesc(ws.getItemtype());
  		if (itype.getItemType_ID() == TracingConstants.LOST_DELAY) {
  			comp_id = TracingConstants.SYSTEM_COMPONENT_NAME_ADD_MISHANDLED_BAG;
  		} else if (itype.getItemType_ID() == TracingConstants.MISSING_ARTICLES) {
  			comp_id = TracingConstants.SYSTEM_COMPONENT_NAME_ADD_MISSING_ARTICLES;
  		} else if (itype.getItemType_ID() == TracingConstants.DAMAGED_BAG) {
  			comp_id = TracingConstants.SYSTEM_COMPONENT_NAME_ADD_DAMAGED_BAG;
  		}
  	}
  	
  	String result = WSCoreUtil.reauth(session_id,comp_id);
  	if (result != null) {
  		res.setReturn("insert incident failed: " + result);
  		return resDoc;
  	}
  	// passed auth, now insert
  	
  	String incident_id = insertIncident(ws);
    if (incident_id == null) incident_id = "insert failed: " + ws.getErrorcode();
    res.setReturn(incident_id);
    return resDoc;
  }
	
 	public com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident findIncident(String incident_id) {
 		
 		try {
			if (incident_id == null || incident_id.length() == 0)
				return null;

			IncidentBMO iBMO = new IncidentBMO();
			Incident iDTO = iBMO.findIncidentByID(incident_id.trim().toUpperCase());
			if (iDTO == null)
				return null;

			return IncidenttoWS_Mapping(iDTO);
		} catch (Exception e) {
			return null;
		}
	}
 	/**
 	 * insert incident into nettracer
 	 * @param ws
 	 * @return
 	 */
 	public String insertIncident(WSIncident ws) {
 		
 		try {
			if (ws == null)
				return null;

			return WStoIncident_Mapping(ws);
		} catch (Exception e) {
			ws.setErrorcode("error inserting incident into nt, error: " + e.toString());
			return null;
		}
	}
 	
 	/**
 	 * converts incident object from hibernate into WSincident for webservices
 	 * @param iDTO
 	 * @return
 	 * @throws Exception
 	 */
 	public com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident IncidenttoWS_Mapping(Incident iDTO) throws Exception {
 		
		com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident si = com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident.Factory.newInstance();
		si.setIncidentID(iDTO.getIncident_ID());
		si.setCompanycodeID(iDTO.getStationcreated().getCompany().getCompanyCode_ID());
		si.setStationcreated(iDTO.getStationcreated().getStationcode());
		si.setStationassigned(iDTO.getStationassigned().getStationcode());
		if (iDTO.getFaultstation() != null) {
			si.setFaultairline(iDTO.getFaultstation().getCompany().getCompanyCode_ID());
			si.setFaultstation(iDTO.getFaultstation().getStationcode());
		}
		si.setAgent(iDTO.getAgent().getUsername());
		if (iDTO.getAgentassigned() != null) {
			si.setAgentassigned(iDTO.getAgentassigned().getUsername());
		}
		si.setCreatedate(WSCoreUtil.formatDatetoString(iDTO.getCreatedate(),null));
		si.setCreatetime(WSCoreUtil.formatDatetoString(null,iDTO.getCreatetime()));
		si.setClosedate(iDTO.getClosedate());
		si.setRecordlocator(iDTO.getRecordlocator());
		si.setTicketnumber(iDTO.getTicketnumber());
		si.setReportmethod(iDTO.getReportMethodString(iDTO.getReportmethod()));
		si.setCheckedlocation(iDTO.getCheckedlocation());
		si.setNumbagchecked(iDTO.getNumpassengers());
		si.setNumbagchecked(iDTO.getNumbagchecked());
		si.setNumbagreceived(iDTO.getNumbagreceived());
		si.setVoluntaryseparation(iDTO.getVoluntaryseparation());
		si.setCourtesyreport(iDTO.getCourtesyreport());
		si.setTsachecked(iDTO.getTsachecked());
		si.setCustomcleared(iDTO.getCustomcleared());
		si.setNonrevenue(iDTO.getNonrevenue());
		si.setItemtype(iDTO.getItemtype().getDescription());
		si.setStatus(iDTO.getStatusdesc());
		si.setLossCode(iDTO.getLoss_code());
		
		
		// passengers
		int c = 0;
		Passenger pas = null;
		Address addr = null;
		
		if (iDTO.getPassengers() != null) {
			com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger paarr = null;
			
			for (Iterator i = iDTO.getPassengers().iterator(); i.hasNext();) {

				paarr = si.addNewPassengers();
				pas = (Passenger) i.next();
				paarr.setPassengerID(pas.getPassenger_ID());
				paarr.setJobtitle(pas.getJobtitle());
				paarr.setSalutation(pas.getSalutationdesc());
				paarr.setFirstname(pas.getFirstname());
				paarr.setMiddlename(pas.getMiddlename());
				paarr.setLastname(pas.getLastname());
				paarr.setDriverslicense(pas.getDriverslicense());
				paarr.setDlstate(pas.getDlstate());
				paarr.setCommonnum(pas.getCommonnum());
				paarr.setCountryofissue(pas.getCountryofissue());
				paarr.setIsprimary(pas.getIsprimary());
				if (pas.getMembership() != null) {
					paarr.setMembershipairline(pas.getMembership().getCompanycode_ID());
					paarr.setMembershipnum(pas.getMembership().getMembershipnum());
					paarr.setMembershipstatus(pas.getMembership().getMembershipstatus());
				}
				// address
				addr = (Address) pas.getAddress(0);
				if (addr != null) {
					paarr.setAddress1(addr.getAddress1());
					paarr.setAddress2(addr.getAddress2());
					paarr.setCity(addr.getCity());
					paarr.setZip(addr.getZip());
					paarr.setHotel(addr.getHotel());
					paarr.setHomephone(addr.getHomephone());
					paarr.setWorkphone(addr.getWorkphone());
					paarr.setMobile(addr.getMobile());
					paarr.setPager(addr.getPager());
					paarr.setAltphone(addr.getAltphone());
					paarr.setEmail(addr.getEmail());
					paarr.setIsPermanent(addr.getIs_permanent());
					paarr.setStateID(addr.getState_ID());
					paarr.setProvince(addr.getProvince());
					paarr.setCountrycodeID(addr.getCountrycode_ID());
					paarr.setValidBdate(WSCoreUtil.formatDatetoString(addr.getValid_bdate(),null));
					paarr.setValidEdate(WSCoreUtil.formatDatetoString(addr.getValid_edate(),null));
				}
				
				si.setPassengersArray(c, paarr);
				c++;
			}

		}
		
		// items
		
		c = 0;
		Item item = null;
		if (iDTO.getItemlist() != null) {
			com.bagnet.nettracer.ws.core.pojo.xsd.WSItem itemarr = null;
			for (Iterator i = iDTO.getItemlist().iterator(); i.hasNext();) {
				itemarr = si.addNewItems();
				item = (Item) i.next();
				
				itemarr.setItemtype(iDTO.getItemtype().getDescription());
				
				itemarr.setBagstatus(item.getStatus() != null ? item.getStatus().getDescription() : null);
				itemarr.setClaimchecknum(item.getClaimchecknum());
				itemarr.setColor(item.getColor());
				itemarr.setBagtype(item.getBagtype());
				
				if (item.getXdescelement_ID_1() > 0)
					itemarr.setXdescelement1(item.getXdescelement1());
				if (item.getXdescelement_ID_2() > 0)
					itemarr.setXdescelement2(item.getXdescelement2());
				if (item.getXdescelement_ID_3() > 0)
					itemarr.setXdescelement3(item.getXdescelement3());
				itemarr.setManufacturer(item.getManufacturer());
				
				itemarr.setLvlofdamage(item.getLvlofdamage());
				itemarr.setDamage(item.getDamage());
				itemarr.setResolutiondesc(item.getResolutiondesc());
				if (item.getResolution() != null) 
					itemarr.setResolutionstatus(item.getResolution().getStatus());
				itemarr.setCost(item.getCost());
				itemarr.setDrafts(item.getDrafts());
				itemarr.setCurrencyID(item.getCurrency_ID());
				
				itemarr.setFnameonbag(item.getFnameonbag());
				itemarr.setMnameonbag(item.getMnameonbag());
				itemarr.setLnameonbag(item.getLnameonbag());
				itemarr.setArrivedonairlineID(item.getArrivedonairline_ID());
				itemarr.setArrivedondate(WSCoreUtil.formatDatetoString(item.getArrivedondate(),null));
				itemarr.setArrivedonflightnum(item.getArrivedonflightnum());
				
				itemarr.setMatchedOhd(item.getOHD_ID());
				
				int d = 0;
				
				Item_Inventory inv = null;
				if (item.getInventory() != null) {
					com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory oiarr = null;
					
					for (Iterator j = item.getInventory().iterator(); j.hasNext();) {
						oiarr = itemarr.addNewInventories();
						inv = (Item_Inventory) j.next();
						oiarr.setCategory(inv.getCategory());
						oiarr.setDescription(inv.getDescription());
						itemarr.setInventoriesArray(d, oiarr);
						d++;
					}

				}
				
				si.setItemsArray(c, itemarr);
				c++;
			}

		}
		
		// for missing articles only
		c = 0;
		Articles art = null;
		if (iDTO.getArticles() != null) {
			com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle artarr = null;
			for (Iterator i = iDTO.getArticles().iterator(); i.hasNext();) {
				artarr = si.addNewArticles();
				art = (Articles) i.next();
				artarr.setArticle(art.getArticle());
				artarr.setDescription(art.getDescription());
				artarr.setPurchasedate(WSCoreUtil.formatDatetoString(art.getPurchasedate(),null));
				artarr.setCost(art.getCost());
				artarr.setCurrencyID(art.getCurrency_ID());

				si.setArticlesArray(c, artarr);
				c++;
			}

		}

		//itineraries
		c = 0;
		Itinerary iti = null;
		if (iDTO.getItinerary() != null) {
			com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary itiarr = null;
			for (Iterator i = iDTO.getItinerary().iterator(); i.hasNext();) {
				itiarr = si.addNewItineraries();
				iti = (Itinerary) i.next();
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

				si.setItinerariesArray(c, itiarr);
				c++;
			}

		}
		
		// claim check
		c = 0;
		Incident_Claimcheck ic = null;
		if (iDTO.getClaimchecks() != null) {
			com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck icarr = null;
			for (Iterator i = iDTO.getClaimchecks().iterator(); i.hasNext();) {
				icarr = si.addNewClaimchecks();
				ic = (Incident_Claimcheck) i.next();
				icarr.setClaimchecknum(ic.getClaimchecknum());
				icarr.setMatchedOhd(ic.getOHD_ID());
				si.setClaimchecksArray(c, icarr);
				c++;
			}
		}

		return si;
 	}


 	/**
 	 * map wsohd object into ohd object
 	 * @param ws
 	 * @return
 	 * @throws Exception
 	 */
 	public String WStoIncident_Mapping(WSIncident ws) throws Exception {
 		IncidentBMO obmo = new IncidentBMO();
 		Incident inc = null;
 		
 		if (ws.getIncidentID() == null || ws.getIncidentID().length() == 0) inc = new Incident();
 		else {
 			inc = obmo.findIncidentByID(ws.getIncidentID());
 			if (inc == null) {
 				ws.setErrorcode("incident id specified does not exist in NetTracer, please remove it from xml if you want to insert new Incident.");
 				return null;
 			}
 		}
 		// status
 		Status status = WSCoreUtil.getStatus(ws.getStatus(),TracingConstants.TABLE_INCIDENT);
 		if (status == null) {
 			ws.setErrorcode("invalid status for incident, please consult NetTracer Webservices help for statuses.");
 			return null;
 		}
 		inc.setStatus(status);
 		
 		// create station
 		Station createandassigns = TracerUtils.getStationByCode(ws.getStationcreated(), ws.getCompanycodeID());
 		if (createandassigns == null) {
 			ws.setErrorcode("invalid create station code, please add the station into NetTracer first");
 			return null;
 		}
 		inc.setStationcreated(createandassigns);
 		
 		// assigned station
 		createandassigns = TracerUtils.getStationByCode(ws.getStationassigned(), ws.getCompanycodeID());
 		if (createandassigns == null) {
 			ws.setErrorcode("invalid assigned station code, please add the station into NetTracer first");
 			return null;
 		}
 		inc.setStationassigned(createandassigns);
 		
 		// fault airline and fault station
 		if (ws.getFaultstation() != null && ws.getFaultstation().length() > 0) {
	 		createandassigns = TracerUtils.getStationByCode(ws.getFaultstation(), ws.getFaultairline());
	 		if (createandassigns == null) {
	 			ws.setErrorcode("invalid fault station code, please add the fault airline's station into NetTracer first");
	 			return null;
	 		}
	 		inc.setFaultstation(createandassigns);
 		}

 		// agent created
 		if (ws.getAgent() == null || ws.getAgent().length() == 0) {
 			ws.setErrorcode("invalid agent username, please add the agent into NetTracer first");
 			return null;
 		}
 		Agent agent = AdminUtils.getAgentBasedOnUsername(ws.getAgent(), ws.getCompanycodeID());
 		if (agent == null) {
 			ws.setErrorcode("invalid agent username, please add the agent into NetTracer first");
 			return null;
 		}
 		inc.setAgent(agent);
 		
 		// assigned agent
 		if (ws.getAgentassigned() != null && ws.getAgentassigned().length() > 0) {
	 		agent = AdminUtils.getAgentBasedOnUsername(ws.getAgentassigned(), ws.getCompanycodeID());
	 		if (agent == null) {
	 			ws.setErrorcode("invalid assigned agent username, please add the agent into NetTracer first");
	 			return null;
	 		}
	 		inc.setAgentassigned(agent);
 		}

 		// create date time
 		String datetimestr = ws.getCreatedate();
		Date thedate = DateUtils.convertToDate(datetimestr, WSCoreUtil.WS_DATEFORMAT, null);
		if (thedate == null) {
			ws.setErrorcode("invalid date format for createdate, please use NT standard: " + WSCoreUtil.WS_DATEFORMAT);
 			return null;
		}
		inc.setCreatedate(thedate);
		
		datetimestr = ws.getCreatetime();
		thedate = DateUtils.convertToDate(datetimestr, WSCoreUtil.WS_TIMEFORMAT, null);
		if (thedate == null) {
			ws.setErrorcode("invalid time format for createtime, please use NT standard: " + WSCoreUtil.WS_TIMEFORMAT);
 			return null;
		}
		inc.setCreatetime(thedate);
		
		inc.setClosedate(ws.getClosedate());
		inc.setRecordlocator(ws.getRecordlocator());
		inc.setTicketnumber(ws.getTicketnumber());
		inc.setReportmethod(IncidentUtils.getReportMethod(ws.getReportmethod()));
		inc.setCheckedlocation(ws.getCheckedlocation());
		inc.setNumpassengers(ws.getNumpassengers());
		inc.setNumbagchecked(ws.getNumbagchecked());
		inc.setNumbagreceived(ws.getNumbagreceived());
		inc.setVoluntaryseparation(ws.getVoluntaryseparation());
		inc.setCourtesyreport(ws.getCourtesyreport());
		inc.setTsachecked(ws.getTsachecked());
		inc.setCustomcleared(ws.getCustomcleared());
		inc.setNonrevenue(ws.getNonrevenue());
		inc.setItemtype(IncidentUtils.retrieveItemTypeWithDesc(ws.getItemtype()));
		if (inc.getItemtype() == null) {
			ws.setErrorcode("invalid incident type, please follow NT standard on item types");
 			return null;
		}

		inc.setLoss_code(ws.getLossCode());	
		

 		// passengers
		HashSet<Passenger> pahash = new HashSet<Passenger>();
		HashSet<Address> addrhash = null;
		
		WSPassenger wp = null;
		Address addr = null;
		Passenger pa = null;
		
		if (ws.getPassengersArray() != null) {
			for (int i=0;i<ws.getPassengersArray().length;i++) {
				wp = ws.getPassengersArray(i);
				if (wp != null) {
					pa = new Passenger();
					pa.setIncident(inc);
					pa.setSalutation(IncidentUtils.getSalutationid(wp.getSalutation()));
					pa.setFirstname(wp.getFirstname());
					pa.setIsprimary(wp.getIsprimary());
					pa.setLastname(wp.getLastname());
					pa.setMiddlename(wp.getMiddlename());
					pa.setCommonnum(wp.getCommonnum());
					pa.setCountryofissue(wp.getCountryofissue());
					pa.setDlstate(wp.getDlstate());
					pa.setDriverslicense(wp.getDriverslicense());
					pa.setIsprimary(wp.getIsprimary());
					pa.setJobtitle(wp.getJobtitle());
			
					if (wp.getMembershipnum() != null && wp.getMembershipnum().length() > 0) {
			 			AirlineMembership am = new AirlineMembership();
			 			am.setMembershipstatus(wp.getMembershipstatus());
			 			am.setMembershipnum(wp.getMembershipnum());
			 			am.setCompanycode_ID(wp.getMembershipairline());
			 			pa.setMembership(am);
			 		}
					
					addrhash = new HashSet<Address>();
					addr = new Address();
					addr.setPassenger(pa);
	
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
					
					datetimestr = wp.getValidBdate();
					thedate = DateUtils.convertToDate(datetimestr, WSCoreUtil.WS_DATEFORMAT, null);
					if (thedate != null) addr.setValid_bdate(thedate);
					
					datetimestr = wp.getValidEdate();
					thedate = DateUtils.convertToDate(datetimestr, WSCoreUtil.WS_DATEFORMAT, null);
					if (thedate != null) addr.setValid_edate(thedate);
					
					addrhash.add(addr);
					pa.setAddresses(addrhash);
					pahash.add(pa);
					
				}
			}
			inc.setPassengers(pahash);
		}
		
		// incident claimcheck
		HashSet<Incident_Claimcheck> ic_set = new HashSet<Incident_Claimcheck>();
		
		Incident_Claimcheck ic = null;
		WSIncidentClaimCheck wic = null;
		if (ws.getClaimchecksArray() != null) {
			for (int i=0;i<ws.getClaimchecksArray().length;i++) {
				wic = ws.getClaimchecksArray(i);
				if (wic != null) {
					ic = new Incident_Claimcheck();
					ic.setIncident(inc);
					ic.setClaimchecknum(wic.getClaimchecknum());

					ic_set.add(ic);
				}
			}
			inc.setClaimchecks(ic_set);
		}
		
		// item
		List<Item> ii_list = new ArrayList<Item>();
		HashSet<Item_Inventory> ii_set = new HashSet<Item_Inventory>();
		Item item = null;
		Item_Inventory iinv = null;
		WSItem wi = null;
		WSInventory wii = null;
		
		if (ws.getItemsArray() != null) {
			for (int i=0;i<ws.getItemsArray().length;i++) {
				wi = ws.getItemsArray(i);
				if (wi != null) {
					item = new Item();
					item.setIncident(inc);
					item.setArrivedonairline_ID(wi.getArrivedonairlineID());
					
					datetimestr = wi.getArrivedondate();
					thedate = DateUtils.convertToDate(datetimestr, WSCoreUtil.WS_DATEFORMAT, null);
					if (thedate != null) item.setArrivedondate(thedate);
					
					item.setArrivedonflightnum(wi.getArrivedonflightnum());
					item.setBagnumber(i);
					item.setItemtype_ID(inc.getItemtype_ID());
					
					item.setBagtype(wi.getBagtype());
					item.setClaimchecknum(wi.getClaimchecknum());
					item.setColor(wi.getColor());
					item.setCost(wi.getCost());
					item.setCurrency_ID(wi.getCurrencyID());
					item.setDamage(wi.getDamage());
					item.setDrafts(wi.getDrafts());
					item.setFnameonbag(wi.getFnameonbag());
					item.setLnameonbag(wi.getLnameonbag());
					item.setMnameonbag(wi.getMnameonbag());
					item.setLvlofdamage(wi.getLvlofdamage());
					item.setManufacturer_ID(TracingConstants.MANUFACTURER_OTHER_ID);
					item.setManufacturer_other(wi.getManufacturer());
					item.setResolutiondesc(wi.getResolutiondesc());
					item.setXdescelement_ID_1(TracerUtils.getXdescelementid(wi.getXdescelement1()));
					item.setXdescelement_ID_2(TracerUtils.getXdescelementid(wi.getXdescelement2()));
					item.setXdescelement_ID_3(TracerUtils.getXdescelementid(wi.getXdescelement3()));

					// inventories
					if (wi.getInventoriesArray() != null) {
						ii_set = new HashSet<Item_Inventory>();
						for (int j=0;j<wi.getInventoriesArray().length;j++) {
							wii = wi.getInventoriesArray(j);
							iinv = new Item_Inventory();
							iinv.setItem(item);
							iinv.setCategorytype_ID(WSCoreUtil.getContentCategory(wii.getCategory()));
							iinv.setDescription(wii.getDescription());
							ii_set.add(iinv);
						}
						item.setInventory(ii_set);
					}
					ii_list.add(item);
				}
			}
			inc.setItemlist(ii_list);
		}

		
 		// itinerary
		HashSet<Itinerary> oi_set = new HashSet<Itinerary>();
		
		Itinerary oi = null;
		WSItinerary wit = null;
		if (ws.getItinerariesArray() != null) {
			for (int i=0;i<ws.getItinerariesArray().length;i++) {
				wit = ws.getItinerariesArray(i);
				if (wit != null) {
					oi = new Itinerary();
					oi.setIncident(inc);
					
					oi.setItinerarytype(wit.getItinerarytype());
					
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
			inc.setItinerary(oi_set);
		}
		
		// articles
		HashSet<Articles> art_set = new HashSet<Articles>();
		
		Articles art = null;
		WSArticle wart = null;
		if (ws.getArticlesArray() != null) {
			for (int i=0;i<ws.getArticlesArray().length;i++) {
				wart = ws.getArticlesArray(i);
				if (wart != null) {
					art = new Articles();
					art.setIncident(inc);
					art.setArticle(wart.getArticle());
					art.setCost(wart.getCost());
					art.setCurrency_ID(wart.getCurrencyID());
					art.setDescription(wart.getDescription());
					
					datetimestr = wart.getPurchasedate();
					thedate = DateUtils.convertToDate(datetimestr, WSCoreUtil.WS_DATEFORMAT, null);
					if (thedate != null) art.setPurchasedate(thedate);

					art_set.add(art);
				}
			}
			inc.setArticles(art_set);
		}
		
		// insert into our db
		int result = obmo.insertIncident(inc, null, inc.getAgent());
		if (result < 1) {
			ws.setErrorcode("unable to insert incident into NT, please contact NT support");
			return null;
		}
 		return inc.getIncident_ID();
 
 	}
 	
}