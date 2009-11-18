package com.bagnet.nettracer.ws.passengerview;

import java.util.Iterator;

import com.bagnet.nettracer.tracing.bmo.CustomerViewableCommentBMO;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.BDO_Passenger;
import com.bagnet.nettracer.tracing.db.CustomerViewableComment;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger;

public class PassengerViewUtil {
	
	/**
	 *
	 * @param getIncidentPV
	 * @return
	 */
  public com.bagnet.nettracer.ws.passengerview.GetIncidentPVResponseDocument getIncidentPV(
      com.bagnet.nettracer.ws.passengerview.GetIncidentPVDocument getIncidentPV) {
		String incident_id = getIncidentPV.getGetIncidentPV().getIncidentId();
		String name = getIncidentPV.getGetIncidentPV().getLastname();
		
		
		GetIncidentPVResponseDocument resDoc = GetIncidentPVResponseDocument.Factory
				.newInstance();
		GetIncidentPVResponseDocument.GetIncidentPVResponse res = resDoc
				.addNewGetIncidentPVResponse();

		
		com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident si = findIncidentForPVO(incident_id, name);
		res.setReturn(si);
		return resDoc;
  }
  
	/**
  *
	 * @param getIncidentPV
	 * @return
	 */
 public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVResponseDocument getAdvancedIncidentPV(
     com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument getIncidentPV) {
		String incident_id = getIncidentPV.getGetAdvancedIncidentPV().getIncidentId();
		String name = getIncidentPV.getGetAdvancedIncidentPV().getLastname();
		
		boolean authorizeName = true;
		if (getIncidentPV.getGetAdvancedIncidentPV().isSetDoNotAuthorize() == true)
			authorizeName = !getIncidentPV.getGetAdvancedIncidentPV().getDoNotAuthorize();

		
		GetAdvancedIncidentPVResponseDocument resDoc = GetAdvancedIncidentPVResponseDocument.Factory
				.newInstance();
		GetAdvancedIncidentPVResponseDocument.GetAdvancedIncidentPVResponse res = resDoc
				.addNewGetAdvancedIncidentPVResponse();

		
		com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident si = findAdvancedIncidentForPVO(incident_id, name, authorizeName);
		res.setReturn(si);
		return resDoc;
 }
  
 	/**
   * 
   * @param incident_ID
   * @param name
   * @return
   */
 	public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident findIncidentForPVO(String incident_ID, String name) {
		
 		try {
 			
			if (name == null || name.length() == 0 || incident_ID == null
					|| incident_ID.length() == 0)
				return null;
			if (name.indexOf("%") >= 0 || incident_ID.indexOf("%") >= 0)
				return null;
			
			incident_ID = StringUtils.fillzero(incident_ID);

			IncidentBMO iBMO = new IncidentBMO();
			
			Incident iDTO = null;
			
			iDTO = iBMO.findIncidentForPVO(incident_ID.trim().toUpperCase(), name);

			if (iDTO == null)
				return null;

			com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident si = com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident.Factory.newInstance();

			Passenger p = null;
			Address addr = null;
			for (Iterator i = iDTO.getPassengers().iterator(); i.hasNext();) {
				p = (Passenger) i.next();
				
				if (p.getLastname().equalsIgnoreCase(name)) {
					si.setFirstname(p.getFirstname());
					si.setMiddlename(p.getMiddlename());
					si.setLastname(p.getLastname());
					
					
					for (int j = 0; j < p.getAddresses().size(); j++) {
						addr = (Address) p.getAddress(j);
						si.setHomephone(addr.getHomephone());
						si.setWorkphone(addr.getWorkphone());
						si.setMobile(addr.getMobile());
						si.setHotel(addr.getHotel());
						si.setEmail(addr.getEmail());
					}
					break;
				}
			}

			si.setIncidentID(iDTO.getIncident_ID());
			si.setDispcreatetime(iDTO.getDisplaydate());
			si.setIncidentStatus(iDTO.getStatus().getTextDescription(null));

			Item item = null;
			if (iDTO.getItemlist() != null) {
				com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem siarr = null;
				BDO bdo = null;
				BDO_Passenger bdo_p = null;
				for (int i = 0; i < iDTO.getItemlist().size(); i++) {
					bdo = null;
					bdo_p = null;
					siarr = si.addNewItems();

					item = (Item) iDTO.getItemlist().get(i);
					siarr.setBagstatus(item.getStatus().getTextDescription(null));
					siarr.setClaimchecknum(item.getClaimchecknum());

					bdo = item.getBdo();
					if (bdo != null)
						bdo_p = bdo.getPassenger(0);
					if (bdo_p != null) {
						siarr.setAddress1(bdo_p.getAddress1());
						siarr.setAddress2(bdo_p.getAddress2());
						siarr.setCity(bdo_p.getCity());
						siarr.setStateID(bdo_p.getState_ID());
						siarr.setZip(bdo_p.getZip());
					}
					si.setItemsArray(i, siarr);
				}

			}

			return si;
		} catch (Exception e) {
			return null;
		}
	}
 	
  /**
   * 
   * @param incident_ID
   * @param name
   * @return
   */
 	public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident findAdvancedIncidentForPVO(String incident_ID, String name, boolean authorizeName) {
		
 		try {
 			
 			if (authorizeName == true) {
				if (name == null || name.length() == 0 || incident_ID == null
						|| incident_ID.length() == 0)
					return null;
				if (name.indexOf("%") >= 0 || incident_ID.indexOf("%") >= 0)
					return null;
 			}
			
			incident_ID = StringUtils.fillzero(incident_ID);

			IncidentBMO iBMO = new IncidentBMO();
			
			Incident iDTO = null;
			
			/*
			 * there are whitespace issues with the way a pax enters a name and an agent enters a name
			 * that cause pax view webservice not to work. it is easier to do white space agnostic name
			 * verification in the Java below than in the query (see isNameMatch(String, String))
			 */
			//if (authorizeName == true)
				//iDTO = iBMO.findIncidentForPVO(incident_ID.trim().toUpperCase(), name);
			//else
				iDTO = iBMO.findIncidentByID(incident_ID.trim().toUpperCase());

			if (iDTO == null)
				return null;

			com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident si = com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident.Factory.newInstance();


			Address addr = null;
			int k = 0;
			for (Iterator i = iDTO.getPassengers().iterator(); i.hasNext();) {
				Passenger p = (Passenger) i.next();

				WSPVPassenger wsp = WSPVPassenger.Factory.newInstance();
				wsp.setFirstname(p.getFirstname());
				wsp.setMiddlename(p.getMiddlename());
				wsp.setLastname(p.getLastname());
				
				for (int j = 0; j < p.getAddresses().size(); j++) {
					addr = (Address) p.getAddress(j);
					wsp.setHomephone(addr.getHomephone());
					wsp.setWorkphone(addr.getWorkphone());
					wsp.setMobile(addr.getMobile());
					wsp.setHotel(addr.getHotel());
					wsp.setEmail(addr.getEmail());
					break;
				}
				
				if (!authorizeName || (authorizeName && isNameMatch(wsp.getLastname(), name))) {
					si.addNewPassengers();
					si.setPassengersArray(k, wsp);
					++k;
				}
			}
			
			if(authorizeName && k == 0) {
				//didn't find name in incident
				return null;
			}

			si.setIncidentID(iDTO.getIncident_ID());
			si.setDispcreatetime(iDTO.getDisplaydate());
			si.setIncidentStatus(iDTO.getStatus().getTextDescription(null));

			Item item = null;
			if (iDTO.getItemlist() != null) {
				com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem siarr = null;
				BDO bdo = null;
				BDO_Passenger bdo_p = null;
				for (int i = 0; i < iDTO.getItemlist().size(); i++) {
					bdo = null;
					bdo_p = null;
					siarr = si.addNewItems();

					item = (Item) iDTO.getItemlist().get(i);
					siarr.setBagstatus(item.getStatus().getTextDescription(null));
					if (item.getClaimchecknum() != null && item.getClaimchecknum().trim().length() > 0)
						siarr.setClaimchecknum(item.getClaimchecknum());

					bdo = item.getBdo();
					if (bdo != null)
						bdo_p = bdo.getPassenger(0);
					if (bdo_p != null) {
						siarr.setAddress1(bdo_p.getAddress1());
						siarr.setAddress2(bdo_p.getAddress2());
						siarr.setCity(bdo_p.getCity());
						siarr.setStateID(bdo_p.getState_ID());
						siarr.setZip(bdo_p.getZip());
					}
					si.setItemsArray(i, siarr);
				}

			}
			
			CustomerViewableComment comment = CustomerViewableCommentBMO.getComment(iDTO.getIncident_ID(), null);
			if (comment != null) {
				si.setComments(comment.getComment());
			}

			return si;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

private boolean isNameMatch(String lastname, String name) {
	if(lastname == null || name == null) { 
		return false;
	}
	lastname = lastname.trim().replaceAll("\\s+", " ");
	name = name.trim().replaceAll("\\s+", " ");
	return lastname.equalsIgnoreCase(name); 
}
 	
}