package com.bagnet.nettracer.ws.v1_1;

import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.CustomerViewableCommentBMO;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.PaxCommunicationBMO;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.BDO_Passenger;
import com.bagnet.nettracer.tracing.db.CustomerViewableComment;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.PaxCommunication;
import com.bagnet.nettracer.tracing.db.PaxCommunication.PaxCommunicationStatus;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument.GetPaxView;
import com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument.WritePassengerComment;
import com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks;
import com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident;
import com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem;
import com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger;
import com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication;

public class PaxViewImplementation extends PaxViewSkeleton {

	private static final String USER_NOT_VALIDATED = "INVALID USER";
	private static final String INVALID_REQUEST = "INVALID REQUEST";
	private static final String NO_MATCH_FOR_CRITERIA = "NO MATCH FOR REQUEST CRITERIA";

	/**
	 * Auto generated method signature
	 * 
	 * @param getPaxView
	 */
	public com.bagnet.nettracer.ws.v1_1.GetPaxViewResponseDocument getPaxView(
			com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument getPaxView) {
		
		GetPaxView reqDoc = getPaxView.getGetPaxView();
		
		String username = reqDoc.getUsername();
		String password = reqDoc.getPassword();
		
		GetPaxViewResponseDocument responseDocument = GetPaxViewResponseDocument.Factory.newInstance();
		

		boolean validated = validateUser(username, password);	
		if (!validated) {
			responseDocument.addNewGetPaxViewResponse().addNewReturn().setErrorcode(USER_NOT_VALIDATED);
			return responseDocument;
		} 
		
		String incidentId = reqDoc.getIncidentId();
		String lastName = reqDoc.getLastname();

		
		if (incidentId == null || lastName == null 
				|| incidentId.length() == 0 || lastName.length() == 0 
				|| incidentId.contains("%") || lastName.contains("%")) {
			responseDocument.addNewGetPaxViewResponse().addNewReturn().setErrorcode(INVALID_REQUEST);
			return responseDocument;
		}
		
		incidentId = StringUtils.fillzero(incidentId.trim());
		Incident inc = IncidentBMO.getIncidentByID(incidentId.toUpperCase(), null);
		
		if (inc == null || !doNamesMatch(lastName, inc)) {
			responseDocument.addNewGetPaxViewResponse().addNewReturn().setErrorcode(NO_MATCH_FOR_CRITERIA);
			return responseDocument;
		}

		WSPVIncident i = getPaxViewData(inc, reqDoc.getMsgsReadByUser());
		responseDocument.addNewGetPaxViewResponse().setReturn(i);
		
		return responseDocument;
	}

	private WSPVIncident getPaxViewData(Incident inc, boolean agentMessageReadByPax) {
		WSPVIncident i = WSPVIncident.Factory.newInstance(); 
		
		i.setIncidentID(inc.getIncident_ID());
		i.setDispcreatetime(inc.getDisplaydate());
		i.setIncidentStatus(inc.getStatus().getTextDescription(null));
		i.setItemType("" + inc.getItemtype().getItemType_ID());
		
		for (Passenger p: inc.getPassenger_list()) {
			WSPVPassenger si = i.addNewPassengers();
			si.setFirstname(p.getFirstname());
			si.setMiddlename(p.getMiddlename());
			si.setLastname(p.getLastname());
			
			
			for (int j = 0; j < p.getAddresses().size(); j++) {
				Address addr = (Address) p.getAddress(j);
				si.setEmail(addr.getEmail());
				
				si.setHomephone(addr.getHomephone());
				si.setWorkphone(addr.getWorkphone());
				si.setMobile(addr.getMobile());
				si.setHotel(addr.getHotel());
				si.setEmail(addr.getEmail());
				
				StringBuffer aof = new StringBuffer();
				aofAppend(aof, "", addr.getAddress1(), "");
				aofAppend(aof, " ", addr.getAddress2(), "");
				aofAppend(aof, ", ", addr.getCity(), "");
				aofAppend(aof, " ", addr.getState(), "");
				aofAppend(aof, " ", addr.getProvince(), "");
				aofAppend(aof, " ", addr.getZip(), "");
				aofAppend(aof, " ", addr.getCountry(), "");
				si.setAddressOnFile(aof.toString());
			}
		}
		
		
		
		if (inc.getItemlist() != null) {
			for (Item item: inc.getItemlist()) {
				WSPVItem siarr = i.addNewItems();
				siarr.setType(item.getBagtype());
				siarr.setColor(item.getColor());
				siarr.setBagstatus(item.getStatus().getTextDescription(null));
				siarr.setClaimchecknum(item.getClaimchecknum());
				BDO bdo = item.getBdo();
				BDO_Passenger bdo_p = null;
				if (bdo != null)
					bdo_p = bdo.getPassenger(0);
				if (bdo_p != null) {
					
					StringBuffer da = new StringBuffer();
					aofAppend(da, "", bdo_p.getAddress1(), "");
					aofAppend(da, " ", bdo_p.getAddress2(), "");
					aofAppend(da, ", ", bdo_p.getCity(), "");
					aofAppend(da, " ", bdo_p.getState_ID(), "");
					aofAppend(da, " ", bdo_p.getProvince(), "");
					aofAppend(da, " ", bdo_p.getZip(), "");
					aofAppend(da, " ", bdo_p.getCountrycode_ID(), "");
					siarr.setDeliveryAddress(da.toString());
				}
			}
		}
		
		if (inc.getClaimcheck_list() != null) {
			for (Incident_Claimcheck ic: inc.getClaimcheck_list()) {
				WSPVClaimChecks pvcc = i.addNewPaxClaimchecks();
				pvcc.setClaimcheck(ic.getClaimchecknum());
			}
		}
		

		List<PaxCommunication> paxCommList = PaxCommunicationBMO.getPaxCommunication(inc.getIncident_ID(), null);
		if (paxCommList != null) {
			for (PaxCommunication pc : paxCommList) {
				WSPVPaxCommunication pvComm = i.addNewPaxCommunication();

				
				if (agentMessageReadByPax &&  pc.getStatus().equals(PaxCommunicationStatus.NEW) && pc.getAgent() != null) {
					Session sess = HibernateWrapper.getSession().openSession();
					org.hibernate.Transaction t = sess.beginTransaction();

					pc.setStatus(PaxCommunicationStatus.READ);
					sess.saveOrUpdate(pc);
					t.commit();
					sess.close();
				}
				pvComm.setStatus(pc.getDescription());
				
				
				if (pc.getAgent() != null) {
					pvComm.setAgent(pc.getAgent().getUsername());
				}

				
				//pvComm.setCreateTimestamp(pc.getCreatedate().toString());
				//SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd, yyyy HH:mm:ss zzz");
				//sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
				SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd, yyyy HH:mm:ss");
				String myCreateTimestamp = sdf.format(pc.getCreatedate());
				pvComm.setCreateTimestamp(myCreateTimestamp);

				if (pc.getAcknowledge_agent() != null) {
					pvComm.setAcknowledgedAgent(pc.getAcknowledge_agent()
							.getUsername());
					pvComm.setAcknowledgedAirline(pc.getAcknowledge_agent().getStation().getCompany().getCompanydesc());

				}

				if (pc.getAcknowledge_timestamp() != null) {
					//pvComm.setAcknowledgedTimestamp(pc
					//		.getAcknowledge_timestamp().toString());
					String myAcknowledgedTimestamp = sdf.format(pc.getAcknowledge_timestamp());
					pvComm.setAcknowledgedTimestamp(myAcknowledgedTimestamp);
				}

				pvComm.setComment(pc.getComment());
				pvComm.setStatus(pc.getDescription());
			}
		}
		
		CustomerViewableComment comment = CustomerViewableCommentBMO.getComment(inc.getIncident_ID(), null);
		if (comment != null) {
			i.setComments(comment.getComment());
		}
		
		return i;
	}

	private void aofAppend(StringBuffer aof, String pre, String str, String post) {
		if (str != null) {
			aof.append(pre);
			aof.append(str.trim());
			aof.append(post);
		}
	}

	private boolean validateUser(String username, String password) {
		if (username.equals("PaxViewUser") && password.equals("Password")) {
			return true;
		}
		return false;
	}

	
    /**
     * Auto generated method signature
     * @param writePassengerComment
     */
    public com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument writePassengerComment(
        com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument writePassengerComment) {

    	WritePassengerComment reqDoc = writePassengerComment.getWritePassengerComment();		
		
		String username = reqDoc.getUsername();
		String password = reqDoc.getPassword();
		
		WritePassengerCommentResponseDocument responseDocument = WritePassengerCommentResponseDocument.Factory.newInstance();
		
		boolean validated = validateUser(username, password);	
		if (!validated) {
			responseDocument.addNewWritePassengerCommentResponse().addNewReturn().setErrorcode(USER_NOT_VALIDATED);
			return responseDocument;
		} 
		
		String incidentId = reqDoc.getIncidentId();
		String comment = reqDoc.getComment();
		
		if (incidentId == null || comment == null 
				|| incidentId.length() == 0 || comment.length() == 0 
				|| incidentId.contains("%") || comment.contains("%")) {
			responseDocument.addNewWritePassengerCommentResponse().addNewReturn().setErrorcode(INVALID_REQUEST);
			return responseDocument;
		}
		
		incidentId = StringUtils.fillzero(incidentId.trim());
		Incident inc = IncidentBMO.getIncidentByID(incidentId.toUpperCase(), null);
		
		if (inc == null) {
			responseDocument.addNewWritePassengerCommentResponse().addNewReturn().setErrorcode(NO_MATCH_FOR_CRITERIA);
			return responseDocument;
		}
		
		PaxCommunication newPaxCommunication = new PaxCommunication();
		newPaxCommunication.setComment(comment);
		newPaxCommunication.setIncident(inc);
		newPaxCommunication.setAgent(null);
		newPaxCommunication.setAcknowledge_agent(null);
		newPaxCommunication.setStatus(PaxCommunicationStatus.NEW);
		newPaxCommunication.setCreatedate(TracerDateTime.getGMTDate());
		newPaxCommunication.setAcknowledge_timestamp(null);
		HibernateUtils.save(newPaxCommunication, null);

		WSPVIncident i = getPaxViewData(inc, true);
		responseDocument.addNewWritePassengerCommentResponse().setReturn(i);
		
		return responseDocument;
    }
	
	private boolean doNamesMatch(String lastname, Incident inc) {
		if(lastname == null || inc == null) { 
			return false;
		}
		lastname = lastname.trim().replaceAll("\\s+", " ");
		
		for (Passenger p: inc.getPassenger_list()) {
			String name = p.getLastname().trim().replaceAll("\\s+", " ");
			if (lastname.equalsIgnoreCase(name)) {
				return true;
			}
		}
		
		return false; 
	}
	
	/**
	 * Auto generated method signature
	 * 
	 * @param getPaxViewIvr
	 */
	public com.bagnet.nettracer.ws.v1_1.GetPaxViewIvrResponseDocument getPaxViewIvr(
			com.bagnet.nettracer.ws.v1_1.GetPaxViewIvrDocument getPaxViewIvr) {
		throw new java.lang.UnsupportedOperationException("Please implement "
				+ this.getClass().getName() + "#getPaxViewIvr");
	}

}
