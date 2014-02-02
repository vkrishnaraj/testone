package com.bagnet.nettracer.ws.passengerview;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.datasources.ScannerDataSource;
import com.bagnet.nettracer.tracing.bmo.CustomerViewableCommentBMO;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.BDO_Passenger;
import com.bagnet.nettracer.tracing.db.CustomerViewableComment;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.dto.ScannerDTO;
import com.bagnet.nettracer.tracing.dto.ScannerDataDTO;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger;

public class PassengerViewUtil {
	private static Logger logger = Logger.getLogger(PassengerViewUtil.class);
	
	
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
  * Auto generated method signature
  * @param getIncidentPV
  */
 public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVWithAgentResponseDocument getAdvancedIncidentPVWithAgent
     (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVWithAgentDocument getIncidentPV) {
		String incident_id = getIncidentPV.getGetAdvancedIncidentPVWithAgent().getIncidentId();
		String name = getIncidentPV.getGetAdvancedIncidentPVWithAgent().getLastname();
		String callingAgent = getIncidentPV.getGetAdvancedIncidentPVWithAgent().getCallingAgent();
		
		boolean authorizeName = true;
		if (getIncidentPV.getGetAdvancedIncidentPVWithAgent().isSetDoNotAuthorize() == true)
			authorizeName = !getIncidentPV.getGetAdvancedIncidentPVWithAgent().getDoNotAuthorize();

		
		GetAdvancedIncidentPVWithAgentResponseDocument resDoc = GetAdvancedIncidentPVWithAgentResponseDocument.Factory
				.newInstance();
		GetAdvancedIncidentPVWithAgentResponseDocument.GetAdvancedIncidentPVWithAgentResponse res = resDoc
				.addNewGetAdvancedIncidentPVWithAgentResponse();

		
		com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident si = findAdvancedIncidentForPVO(incident_id, name, authorizeName, callingAgent);
		res.setReturn(si);
		return resDoc;
 	
 }

	/**
	 * Auto generated method signature
	 * 
	 * @param getIncidentPV
	 */
	public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVFrequentFlyerResponseDocument getAdvancedIncidentsPVFrequentFlyer(
			com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVFrequentFlyerDocument getIncidentPV) {
			String freq_flyer = getIncidentPV.getGetAdvancedIncidentsPVFrequentFlyer().getFrequentFlyer();
			String calling_agent = getIncidentPV.getGetAdvancedIncidentsPVFrequentFlyer().getCallingAgent();
			
			GetAdvancedIncidentsPVFrequentFlyerResponseDocument resDoc = GetAdvancedIncidentsPVFrequentFlyerResponseDocument.Factory
					.newInstance();
			GetAdvancedIncidentsPVFrequentFlyerResponseDocument.GetAdvancedIncidentsPVFrequentFlyerResponse res = resDoc
					.addNewGetAdvancedIncidentsPVFrequentFlyerResponse();

			com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident[] si = findAdvancedIncidentForPVOFreqFlyer(freq_flyer, calling_agent);
			res.setReturnArray(si);
			return resDoc;
	 }

	/**
	 * Auto generated method signature
	 * 
	 * @param getIncidentPV
	 */
	public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument getAdvancedIncidentsPVPnr(
			com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument getIncidentPV) {
		String record_locator = getIncidentPV.getGetAdvancedIncidentsPVPnr().getRecordLocator();
		String calling_agent = getIncidentPV.getGetAdvancedIncidentsPVPnr().getCallingAgent();
		
		GetAdvancedIncidentsPVPnrResponseDocument resDoc = GetAdvancedIncidentsPVPnrResponseDocument.Factory
				.newInstance();
		GetAdvancedIncidentsPVPnrResponseDocument.GetAdvancedIncidentsPVPnrResponse res = resDoc
				.addNewGetAdvancedIncidentsPVPnrResponse();

		com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident[] si = findAdvancedIncidentForPVOPnr(record_locator, calling_agent);
		res.setReturnArray(si);
		return resDoc;
		
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param getIncidentPV
	 */
	public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPhoneResponseDocument getAdvancedIncidentsPVPhone(
			com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPhoneDocument getIncidentPV) {
		String phone_number = getIncidentPV.getGetAdvancedIncidentsPVPhone().getPhoneNumber();
		String calling_agent = getIncidentPV.getGetAdvancedIncidentsPVPhone().getCallingAgent();
		
		GetAdvancedIncidentsPVPhoneResponseDocument resDoc = GetAdvancedIncidentsPVPhoneResponseDocument.Factory
				.newInstance();
		GetAdvancedIncidentsPVPhoneResponseDocument.GetAdvancedIncidentsPVPhoneResponse res = resDoc
				.addNewGetAdvancedIncidentsPVPhoneResponse();

		com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident[] si = findAdvancedIncidentForPVOPhone(phone_number, calling_agent);
		res.setReturnArray(si);
		return resDoc;
		
	}

	/**
	 * Auto generated method signature
	 * 
	 */
	public com.bagnet.nettracer.ws.passengerview.GetActivePhoneListResponseDocument getActivePhoneList() {		
		GetActivePhoneListResponseDocument resDoc = GetActivePhoneListResponseDocument.Factory
				.newInstance();
		GetActivePhoneListResponseDocument.GetActivePhoneListResponse res = resDoc
				.addNewGetActivePhoneListResponse();

		String[] si = findActiveNumbers();
		res.setReturnArray(si);
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
 	
 	public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident findAdvancedIncidentForPVO(String incident_ID, String name, boolean authorizeName) {
 		return findAdvancedIncidentForPVO(incident_ID, name, null, authorizeName, null);
 	}
 	
 	public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident findAdvancedIncidentForPVO(String incident_ID, String name, String fName, boolean authorizeName) {
 		return findAdvancedIncidentForPVO(incident_ID, name, fName, authorizeName, null);
 	}
 	
 	public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident findAdvancedIncidentForPVO(String incident_ID, String name, boolean authorizeName, String calling_agent) {
 		return findAdvancedIncidentForPVO(incident_ID, name, null, authorizeName, calling_agent);
 	}
  /**
   * 
   * @param incident_ID
   * @param name
   * @return
   */
 	public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident findAdvancedIncidentForPVO(String incident_ID, String name, String fName, boolean authorizeName, String calling_agent) {
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
				
			iDTO = iBMO.findIncidentByID(incident_ID.trim().toUpperCase());
			
			if (calling_agent != null && iDTO != null) {
				addCallingAgentRemark(iDTO, calling_agent, iBMO, "findByIncidentID");
			}
			
			return populateAdvancedIncident(iDTO, name, fName, authorizeName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
 	
 	public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident[] findAdvancedIncidentForPVOFreqFlyer(String freq_flyer, String calling_agent) {
 		try {

			IncidentBMO iBMO = new IncidentBMO();
			List<Incident> iDTOList = null;
				
			iDTOList = iBMO.findIncidentForPVOFreqFlyer(freq_flyer.trim().toUpperCase());
			if (iDTOList != null) {
				int currentIndex = 0;
				com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident[] returnArray = new com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident[iDTOList.size()];
				for (Incident iDTO : iDTOList) {
					returnArray[currentIndex] = populateAdvancedIncident(iDTO);
					addCallingAgentRemark(iDTO, calling_agent, iBMO, "findByFrequentFlyerNumber");
					currentIndex++;
				}
				return returnArray;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
 	
 	public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident[] findAdvancedIncidentForPVOPnr(String pnr, String calling_agent) {
 		try {

			IncidentBMO iBMO = new IncidentBMO();
			List<Incident> iDTOList = null;
				
			iDTOList = iBMO.findIncidentForPVORecordLocator(pnr.trim().toUpperCase());
			if (iDTOList != null) {
				int currentIndex = 0;
				com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident[] returnArray = new com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident[iDTOList.size()];
				for (Incident iDTO : iDTOList) {
					returnArray[currentIndex] = populateAdvancedIncident(iDTO);
					addCallingAgentRemark(iDTO, calling_agent, iBMO, "findByRecordLocator");
					currentIndex++;
				}
				return returnArray;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
 	
 	public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident[] findAdvancedIncidentForPVOPhone(String phone, String calling_agent) {
 		try {

			IncidentBMO iBMO = new IncidentBMO();
			List<Incident> iDTOList = null;
				
			iDTOList = iBMO.findIncidentForPVOPhoneNumber(phone.trim().toUpperCase());
			if (iDTOList != null) {
				int currentIndex = 0;
				com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident[] returnArray = new com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident[iDTOList.size()];
				for (Incident iDTO : iDTOList) {
					returnArray[currentIndex] = populateAdvancedIncident(iDTO);
					addCallingAgentRemark(iDTO, calling_agent, iBMO, "findByPhoneNumber");
					currentIndex++;
				}
				return returnArray;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
 	
 	private void addCallingAgentRemark(Incident iDTO, String callingAgent, IncidentBMO iBMO, String functionCall) {
 		Remark r = new Remark();
 		ResourceBundle myResources = ResourceBundle.getBundle(
				"com.bagnet.nettracer.tracing.resources.ApplicationResources");
 		Agent agent = AdminUtils.getAgentBasedOnUsername(callingAgent, TracerProperties.get(iDTO.getAgent().getCompanycode_ID(),"wt.company.code"));
 		agent.setUsername(callingAgent);
		r.setAgent(agent);
		r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
		r.setRemarktext(myResources.getString("service_load_remark_text"));
		r.setIncident(iDTO);
		r.setRemarktype(TracingConstants.REMARK_REGULAR);
		
		Set<Remark> remarks = iDTO.getRemarks();
		remarks.add(r);
		iBMO.updateRemarksOnly(iDTO.getIncident_ID(), remarks, agent);
 	}
 	
 	private com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident populateAdvancedIncident(Incident iDTO) {
 		return populateAdvancedIncident(iDTO, null, null, false);
 	}
 	
 	private com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident populateAdvancedIncident(Incident iDTO, String name, boolean authorizeName) {
 		return populateAdvancedIncident(iDTO, name, null, authorizeName);
 	}
 	
 	private com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident populateAdvancedIncident(Incident iDTO, String name, String fName, boolean authorizeName) {
		if (iDTO == null) {
			return null;
		}

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
			
			if (!authorizeName || (authorizeName && isNameMatch(wsp.getLastname(), name, wsp.getFirstname(), fName))) {
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
		Calendar createCal = new GregorianCalendar();
		createCal.setTime(iDTO.getCreatedate());
		si.setCreatedate(createCal);
		if (iDTO.getClosedate() != null) {
			Calendar closeCal = new GregorianCalendar();
			closeCal.setTime(iDTO.getClosedate());
			si.setClosedate(closeCal);
		}
		si.setItemType(iDTO.getItemtype_ID());
		si.setIncidentStatus(iDTO.getStatus().getTextDescription(null));

		int index = 0;
		for (Incident_Claimcheck claimCheck : iDTO.getClaimchecks()) {
			com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck check = null;
			check = si.addNewClaimChecks();
			check.setTag(claimCheck.getClaimchecknum());
			si.setClaimChecksArray(index, check);
			index++;
		}

		Item item = null;
		if (iDTO.getItemlist() != null) {
			com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem siarr = null;
			BDO bdo = null;
			BDO_Passenger bdo_p = null;
			int siarrIndex = 0;
			for (int i = 0; i < iDTO.getItemlist().size(); i++) {
				bdo = null;
				bdo_p = null;
				siarr = si.addNewItems();
				item = (Item) iDTO.getItemlist().get(i);// Obtain the appropriate scannerDataSource
				if (item != null) {
					siarr.setBagstatus(item.getStatus().getTextDescription(null));
					if (item.getClaimchecknum() != null && item.getClaimchecknum().trim().length() > 0) {
						siarr.setClaimchecknum(item.getClaimchecknum());
					}
					bdo = item.getBdo();
					if (bdo != null) {
						bdo_p = bdo.getPassenger(0);
					}
					if (bdo_p != null) {
						siarr.setAddress1(bdo_p.getAddress1());
						siarr.setAddress2(bdo_p.getAddress2());
						siarr.setCity(bdo_p.getCity());
						siarr.setStateID(bdo_p.getState_ID());
						siarr.setZip(bdo_p.getZip());
					}
					si.setItemsArray(siarrIndex, siarr);
					siarrIndex++;
				}
			}

		}
		
		CustomerViewableComment comment = CustomerViewableCommentBMO.getComment(iDTO.getIncident_ID(), null);
		if (comment != null) {
			si.setComments(comment.getComment());
		}

		return si;
 		
 	}

private boolean isNameMatch(String lastname, String name, String firstname, String fname) {
	if(lastname == null || name == null) { 
		return false;
	}
	boolean firstPass = true;
	boolean checkFirst = (fname != null && fname.trim().length() > 0);
	lastname = lastname.trim().replaceAll("\\s+", " ");
	name = name.trim().replaceAll("\\s+", " ");
	if (checkFirst) {
		System.out.println("CHECKING FIRST NAME: " + fname + " :: " + firstname);
		if (firstname == null) {
			return false;
		}
		firstname = firstname.trim().replaceAll("\\s+", " ");
		fname = fname.trim().replaceAll("\\s+", " ");
		firstname = firstname.toUpperCase();
		fname = fname.toUpperCase();
		firstPass = firstname.contains(fname);
	}
	return (lastname.equalsIgnoreCase(name) && firstPass); 
}

private String[] findActiveNumbers() {
	try {
		IncidentBMO iBMO = new IncidentBMO();
		List<Object[]> objectList = null;
			
		objectList = iBMO.findActiveIncidentsPhones();
		if (objectList != null) {
			int currentIndex = 0;
			String[] firstArray = new String[objectList.size() * 5];
			for (Object[] objs : objectList) {
				String homeP = (String) objs[0];
				String workP = (String) objs[1];
				String mobileP = (String) objs[2];
				String pagerP = (String) objs[3];
				String altP = (String) objs[4];
				if (checkPhone(homeP)) {
					firstArray[currentIndex] = homeP;
					currentIndex++;
				}
				if (checkPhone(workP)) {
					firstArray[currentIndex] = workP;
					currentIndex++;
				}
				if (checkPhone(mobileP)) {
					firstArray[currentIndex] = mobileP;
					currentIndex++;
				}
				if (checkPhone(pagerP)) {
					firstArray[currentIndex] = pagerP;
					currentIndex++;
				}
				if (checkPhone(altP)) {
					firstArray[currentIndex] = altP;
					currentIndex++;
				}
			}
			String[] returnArray = new String[currentIndex];
			for (int i = 0; i < currentIndex; i++) {
				returnArray[i] = firstArray[i];
			}
			return returnArray;
		}
		return null;
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}

private boolean checkPhone(String number) {
	if (number != null && number.trim().length() > 0 && number.trim().matches("^[0-9]*$")) {
		return true;
	}
	return false;
}
 	
}