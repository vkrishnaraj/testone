package com.bagnet.nettracer.ws.passengerview;

import java.util.Iterator;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.BDO_Passenger;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.utils.StringUtils;

public class PassengerViewUtil {
	private com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident si;
	
	/**
	 * in the skeleton file, just write the following
	 * 
	 PassengerViewUtil pv = new PassengerViewUtil();
   return pv.getIncidentPV(getIncidentPV);
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

		si = findIncidentForPVO(incident_id, name);
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
			Incident iDTO = iBMO.findIncidentForPVO(incident_ID.trim().toUpperCase(), name);
			if (iDTO == null)
				return null;

			si = com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident.Factory.newInstance();

			Passenger p = null;
			Address addr = null;
			for (Iterator i = iDTO.getPassengers().iterator(); i.hasNext();) {
				p = (Passenger) i.next();
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

			si.setIncidentID(iDTO.getIncident_ID());
			si.setDispcreatetime(iDTO.getDisplaydate());
			si.setIncidentStatus(iDTO.getStatus().getDescription());

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
					siarr.setBagstatus(item.getStatus().getDescription());
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
 	
}