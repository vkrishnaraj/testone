/*
 * Created on Jul 19, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.utils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;
import org.hibernate.Session;

import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.integrations.events.BeornDTO;
import com.bagnet.nettracer.reporting.DamageReceipt;
import com.bagnet.nettracer.reporting.LostDelayReceipt;
import com.bagnet.nettracer.reporting.MissingReceipt;
import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.ClaimBMO;
import com.bagnet.nettracer.tracing.bmo.ForwardNoticeBMO;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.LossCodeBMO;
import com.bagnet.nettracer.tracing.bmo.LostFoundBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.bmo.exception.StaleStateException;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.FileDAO;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.Articles;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.BDO_Passenger;
import com.bagnet.nettracer.tracing.db.Billing;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.ClaimProrate;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.ControlLog;
import com.bagnet.nettracer.tracing.db.DeliveryInstructions;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Assoc;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.ItemType;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.LostAndFoundIncident;
import com.bagnet.nettracer.tracing.db.LostAndFound_Photo;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHDRequest;
import com.bagnet.nettracer.tracing.db.OHD_Inventory;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.OHD_Log_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.OHD_Photo;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Prorate_Itinerary;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.Task;
import com.bagnet.nettracer.tracing.db.WorldTracerFile;
import com.bagnet.nettracer.tracing.db.audit.Audit_Claim;
import com.bagnet.nettracer.tracing.db.audit.Audit_ClaimProrate;
import com.bagnet.nettracer.tracing.db.audit.Audit_Prorate_Itinerary;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItemIncident;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCFile;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCMessage;
import com.bagnet.nettracer.tracing.db.wtq.WtqCloseOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqOhdAction;
import com.bagnet.nettracer.tracing.dto.FileDTO;
import com.bagnet.nettracer.tracing.dto.IncidentActivityDTO;
import com.bagnet.nettracer.tracing.dto.MessageDTO;
import com.bagnet.nettracer.tracing.dto.Ohd_DTO;
import com.bagnet.nettracer.tracing.dto.SearchIncident_DTO;
import com.bagnet.nettracer.tracing.forms.ClaimForm;
import com.bagnet.nettracer.tracing.forms.ClaimProrateForm;
import com.bagnet.nettracer.tracing.forms.ForwardMessageForm;
import com.bagnet.nettracer.tracing.forms.ForwardOnHandForm;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.forms.LostFoundIncidentForm;
import com.bagnet.nettracer.tracing.forms.OnHandForm;
import com.bagnet.nettracer.tracing.forms.RequestOnHandForm;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.forms.SearchLostFoundForm;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;

/**
 * @author Matt
 * 
 */
public class BagService {
	private static Logger logger = Logger.getLogger(BagService.class);
	
	private static MessageResources messages = null;
	
	static {
		try {
			messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		} catch (Exception e) {
			// Ignore
		}
	}
	
	/**
	 *  
	 */
	public BagService() {
		super();
	}

	public OHDRequest findOHDRequestById(String Id) {
		OhdBMO obmo = new OhdBMO();
		return obmo.findOHDRequestById(Id);
	}

	public boolean forwardOnHand(ForwardOnHandForm form, Agent user, MessageResources messages) {
		boolean escape = false;
		List<LabelValueBean> ohds = form.getOhdList();
		//String expedite_num = form.getExpediteNumber();
		ArrayList<OHD_Log> logs = new ArrayList<OHD_Log>();
		
		for (LabelValueBean ohdBean: ohds) {
			String onhandnum = ohdBean.getLabel();
			String expeditenum = ohdBean.getValue();
	
			OhdBMO obmo = new OhdBMO();
			OHD ohd = obmo.findOHDByID(onhandnum);
			if(ohd == null)
				break;

			OHD_Log log = new OHD_Log();
			logs.add(log);
			log.setOhd(ohd);
			log.setForwarding_agent(user);
			log.setExpeditenum(expeditenum);
			log.setForward_time(TracerDateTime.getGMTDate());
			log.setMessage(form.getMessage());
			log.setDestStationCode(Integer.parseInt(form.getDestStation()));
			
			LinkedHashSet<OHD_Log_Itinerary> newItinSet = new LinkedHashSet<OHD_Log_Itinerary>();
			
			for (OHD_Log_Itinerary i: (List<OHD_Log_Itinerary>) form.getItinerarylist()) {
				OHD_Log_Itinerary ii = new OHD_Log_Itinerary();
				try {
					BeanUtils.copyProperties(ii, i);
					ii.setLog(log);
					newItinSet.add(ii);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			
			log.setItinerary(newItinSet);
			log.setLog_status(TracingConstants.LOG_NOT_RECEIVED);
			OHD_Log_Itinerary oli = null;
			
			ArrayList<String> notifyList = new ArrayList<String>();
			Set<OHD_Log_Itinerary> ItinLogList=new HashSet<OHD_Log_Itinerary>();
			if(log.getItinerary() != null) {
				for(Iterator<OHD_Log_Itinerary> i = log.getItinerary().iterator(); i.hasNext();) {
					oli=new OHD_Log_Itinerary();
					try {
						BeanUtils.copyProperties(oli, (OHD_Log_Itinerary) i.next());
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					oli.setLog(log);
					
					if (oli.isNotify() && oli.getLegto() != null) {
						notifyList.add(oli.getLegto());
					}
					ItinLogList.add(oli);
				}
				log.setItinerary(ItinLogList);
			}

			// Update the status of OHD as well.`

			Remark r = new Remark();
			r.setAgent(user);
			r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
			Station station = StationBMO.getStation("" + log.getDestStationCode());
			r.setRemarktext(messages.getMessage(new Locale(user.getCurrentlocale()), "bagforwardMessage") + " "
					+ station.getCompany().getCompanyCode_ID()
					+ messages.getMessage(new Locale(user.getCurrentlocale()), "aposS") + " "
					+ station.getStationcode() + " station.");
			r.setOhd(ohd);
			// Add a remark to the OHD saying the bag is forwarded.
			Set<Remark> remarks = ohd.getRemarks();
			remarks.add(r);

			// Change the status to be Matched in transit in case of a matching
			// forward
			// Change the status to be In-transit in case of a regular forward.

			String bagRequestId = form.getBag_request_id();
			OHDRequest forRequest = null;
			if(bagRequestId != null && !bagRequestId.equals("")) {
				log.setOhd_request_id(Integer.parseInt(bagRequestId));
				// update the request object
				forRequest = OHDUtils.getRequest(bagRequestId);
			}
			Status s = new Status();
			if(forRequest != null && forRequest.getMatch_id() > 0) {
				s.setStatus_ID(TracingConstants.OHD_STATUS_MATCH_IN_TRANSIT);

			}
			else {
				s.setStatus_ID(TracingConstants.OHD_STATUS_IN_TRANSIT);
			}
			ohd.setStatus(s);

			if(!obmo.forwardOHD(log, ohd, user))
				return false;

			// only do if this forward corresponds to a match.
			if(forRequest != null) {

				// Add a remark to the incident file if the forward is from
				// initiate
				// ROH.
				if(forRequest.getIncident_ID() != null && !forRequest.getIncident_ID().equals("")) {
					IncidentBMO bmo = new IncidentBMO();
					try {
						Incident inc = bmo.findIncidentByID(forRequest.getIncident_ID());
						remarks = inc.getRemarks();
						r.setOhd(null);
						r.setIncident(inc);
						remarks.add(r);
						// save the incident
						bmo.insertIncident(false, inc, null, user);

					}
					catch (StaleStateException sse){
						//loupas - should never reach here
					}
					catch (Exception e) {
						logger.error("unable to find incident: " + e);
					}
				}

				s = new Status();
				s.setStatus_ID(TracingConstants.OHD_REQUEST_STATUS_FORWARDED);
				forRequest.setStatus(s);
				forRequest.setForward_id(log.getOHDLog_ID());
				HibernateUtils.save(forRequest);
				// set the destination code for the forward
				log.setDestStationCode(forRequest.getRequestForStation().getStation_ID());
			}
			else {
				// Update all requests that could potentially have this bag id
				log.setDestStationCode(Integer.parseInt(form.getDestStation()));

				List<OHDRequest> requests = OHDUtils.getRequests(log.getOhd().getOHD_ID(), 0, 0);
				if(requests != null) {
					for(Iterator<OHDRequest> i = requests.iterator(); i.hasNext();) {
						OHDRequest request = (OHDRequest) i.next();
						s = new Status();
						if(request.getRequestForStation().getStation_ID() == log.getDestStationCode())
							s.setStatus_ID(TracingConstants.OHD_REQUEST_STATUS_FORWARDED);
						else
							s.setStatus_ID(TracingConstants.OHD_STATUS_CLOSED);

						if(s.getStatus_ID() == TracingConstants.OHD_REQUEST_STATUS_FORWARDED) {
							request.setForward_id(log.getOHDLog_ID());
						}
						request.setStatus(s);
						HibernateUtils.save(request);
					}
				}
			}
			HibernateUtils.save(log);
			ForwardNoticeBMO.createForwardNotice(log, notifyList, user);
			
			

			// update l/d bag status to in transit as well if forwarding station
			// is the same as l/d assigned station
			@SuppressWarnings("deprecation")
			Item item = BDOUtils.findOHDfromMatchedLD(onhandnum); /* Not sure what to replace this with*/
			if(item != null) {
				Status s2 = new Status();
				s2.setStatus_ID(TracingConstants.ITEM_STATUS_IN_TRANSIT);
				item.setStatus(s2);
				HibernateUtils.save(item);
			}
			if(escape)
				break;
		}
		
		try {
			SpringUtils.getClientEventHandler().doEventOnForward(logs);
		} catch (Exception e) {
			logger.error("Error performing client-specific BEORN Action...");
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * This function performs the BEORN functionality by creating an on-hand
	 * object and then forwarding it.
	 */
	public String forwardMessage(ForwardMessageForm form, Agent user, MessageResources messages) {

		Date start = new Date();
		
		// create an on-hand entry
		OHD oDTO = new OHD();

		String finalFlightNumber = "";
		String finalFlightAirline = "";
		Date finalFlightDate = new Date();
		String faultStationName = "";
		
		oDTO.setAgent(user);
		Status s = new Status();
		s.setStatus_ID(TracingConstants.OHD_STATUS_IN_TRANSIT);
		oDTO.setStatus(s);
		oDTO.setOhd_type(TracingConstants.MASS_OHD_TYPE);
		oDTO.setClaimnum(TracerUtils.removeSpaces(form.getBag_tag()).toUpperCase());

		Date x = TracerDateTime.getGMTDate();

		oDTO.setFoundtime(x);
		oDTO.setFounddate(x);
		oDTO.setFoundAtStation(oDTO.getAgent().getStation());
		oDTO.setHoldingStation(oDTO.getAgent().getStation());
		oDTO.setItinerary(new HashSet<OHD_Itinerary>(form.getBagitinerarylist()));


		if(form.getFaultStation() != null && !form.getFaultStation().equals("")) {
			Station faultStation = StationBMO.getStation(form.getFaultStation());
			oDTO.setFaultstation_ID(faultStation.getStation_ID());
			faultStationName = faultStation.getStationcode();
		}

		if(!form.getLossCode().equals("0")) {
			oDTO.setLoss_code(new Integer(form.getLossCode()).intValue());
		}
		Set<OHD_Itinerary> ItinList=new HashSet<OHD_Itinerary>();
		for(Iterator<OHD_Itinerary> i = oDTO.getItinerary().iterator(); i.hasNext();) {
			
			OHD_Itinerary oo = new OHD_Itinerary();
			try {
				BeanUtils.copyProperties(oo, (OHD_Itinerary) i.next());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			oo.setOhd(oDTO);
			ItinList.add(oo);
		}
		oDTO.setItinerary(ItinList);
		
		Set<Remark> RemList=new HashSet<Remark>();
		Remark rem = new Remark();
		if(form.getMessage()!=null && form.getMessage().length()>0){
			rem.setRemarktext(form.getMessage());
			rem.setOhd(oDTO);
			rem.setCreatetime(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT).format(TracerDateTime.getGMTDate()));
			rem.setAgent(user);
			RemList.add(rem);
			rem=new Remark();
		}
		Station destStation = StationBMO.getStation(form.getDestStation());
		String destString = form.getDestStation();
		if (destStation != null) {
			destString = destStation.getStationcode();
		}
		rem.setRemarktext(messages.getMessage(new Locale(user.getCurrentlocale()),
				"bagforwardMessage")
				+ " "
				+ form.getCompanyCode()
				+ messages.getMessage(new Locale(user.getCurrentlocale()), "aposS")
				+ " "
				+ destString + " station."); //bagforwardMessage
		rem.setOhd(oDTO);
		rem.setCreatetime(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT).format(TracerDateTime.getGMTDate()));
		rem.setAgent(user);
		RemList.add(rem);
		oDTO.setRemarks(RemList);
		OhdBMO oBMO = new OhdBMO();
		
		boolean result = oBMO.insertOHD(oDTO, user);
		if(!result) {
			com.bagnet.nettracer.tracing.utils.general.Logger.logForward(oDTO.getOHD_ID(), "BEORN: NO RESULTS", start);
			return null;
		}
		

		OHD_Log log = new OHD_Log();
		log.setOhd(oDTO);
		log.setForwarding_agent(user);
		log.setExpeditenum(form.getExpediteNumber());
		log.setForward_time(TracerDateTime.getGMTDate());
		log.setMessage(form.getMessage());
		log.setDestStationCode(Integer.parseInt(form.getDestStation()));
		log.setItinerary(new HashSet<OHD_Log_Itinerary>(form.getForwarditinerarylist()));
		log.setLog_status(TracingConstants.LOG_NOT_RECEIVED);
		OHD_Log_Itinerary oli = null;
		
		ArrayList<String> notifyList = new ArrayList<String>();

		Set<OHD_Log_Itinerary> ItinLogList=new HashSet<OHD_Log_Itinerary>();
		if(log.getItinerary() != null) {
			List<OHD_Log_Itinerary> itinList = log.getItinerarylist();
			for (int i = 0; itinList != null && i<itinList.size(); ++i) {
				oli=new OHD_Log_Itinerary();
				try {
					BeanUtils.copyProperties(oli, (OHD_Log_Itinerary) itinList.get(i));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				oli.setLog(log);
				
				if (oli.isNotify() && oli.getLegto() != null) {
					notifyList.add(oli.getLegto());
				}
				
  			if (i == itinList.size() -1) {
  				finalFlightAirline = oli.getAirline();
  				finalFlightNumber = oli.getFlightnum();
  				finalFlightDate = oli.getDepartdate();
  				
  			}
  			ItinLogList.add(oli);
			}
		}
		log.setItinerary(ItinLogList);
		HibernateUtils.save(log);
		
		ForwardNoticeBMO.createForwardNotice(log, notifyList, user);	
			// Fields for Special Instructions Implementation
		BeornDTO bdto = new BeornDTO();
		bdto.setSpecialInstructions(form.getSpecialInstructions());
		bdto.setExpediteNumber(form.getExpediteNumber());
		bdto.setFinalFlightNumber(finalFlightNumber);
		bdto.setFaultStation(faultStationName);
		bdto.setFinalDestination(StationBMO.getStation(form.getDestStation()).getStationcode());
		bdto.setFinalFlightDepartureDate(finalFlightDate);
		bdto.setFinalFlightAirline(finalFlightAirline);
		if (!form.getLossCode().equals("0")) {
			bdto.setReasonForLoss(Integer.toString(LossCodeBMO.getCode(form.getLossCode()).getLoss_code()));
		} else {
			bdto.setReasonForLoss("Not Provided");
		}
		bdto.setTagNumber(form.getBag_tag());
		bdto.setOnhand(oDTO.getOHD_ID());
		bdto.setLog(log);
		
		try {
			SpringUtils.getClientEventHandler().doEventOnBeornWS(bdto);
		} catch (Exception e) {
			logger.error("Error performing client-specific BEORN Action...");
			e.printStackTrace();
		}

		try {
			SpringUtils.getClientEventHandler().doPcn(log, true);
		} catch (Exception e) {
			logger.error("Error performing PCN lookup...");
			e.printStackTrace();
		}
		com.bagnet.nettracer.tracing.utils.general.Logger.logForward(oDTO.getOHD_ID(), "BEORN", start);
		return oDTO.getOHD_ID();
	}

	public boolean requestOnHand(RequestOnHandForm form, Agent user, MessageResources messages) {
		OHDRequest request = new OHDRequest();

		if(form.getIncident_ID() == null || form.getIncident_ID().equals(""))
			request.setIncident_ID(null);
		else
			request.setIncident_ID(form.getIncident_ID());

		OhdBMO obmo = new OhdBMO();
		OHD ohd = obmo.findOHDByID(form.getOhd_ID());
		request.setOhd(ohd);
		if(form.getMatch_ID() == null || form.getMatch_ID().equals(""))
			request.setMatch_id(0);
		else
			request.setMatch_id(Integer.parseInt(form.getMatch_ID()));
		request.setReason(form.getReason());
		request.setRequestForStation(user.getStation());
		request.setRequestingAgent(user);

		Remark r = new Remark();
		r.setAgent(user);
		r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
		r.setRemarktext(messages.getMessage(new Locale(user.getCurrentlocale()), "bagrequestMessage") + " "
				+ user.getStation().getCompany().getCompanyCode_ID()
				+ messages.getMessage(new Locale(user.getCurrentlocale()), "aposS") + " "
				+ user.getStation().getStationcode() + " station.");
		r.setOhd(ohd);

		// Add a remark to the OHD saying the bag is forwarded.
		Set<Remark> remarks = ohd.getRemarks();
		remarks.add(r);

		Status s = new Status();
		s.setStatus_ID(TracingConstants.OHD_STATUS_OPEN); // make the request
		request.setStatus(s);
		// open
		request.setRequestTime(TracerDateTime.getGMTDate());

		boolean ret = true;
		if(!obmo.requestOHD(request, ohd, user))
			ret = false;

		return ret;
	}

	public ActionMessage insertIncident(Incident iDTO, IncidentForm theform, int itemtype, ServletContext sc,
			HttpServletRequest request) {
		return insertIncident(iDTO, theform, itemtype, sc, request, false);
	}

	@SuppressWarnings("unchecked")
	public ActionMessage insertIncident(Incident iDTO, IncidentForm theform, int itemtype, ServletContext sc,
			HttpServletRequest request, boolean checkClosedStatus) {
		try {

			HttpSession session = request.getSession();
			Agent mod_agent = (Agent) session.getAttribute("user");
			boolean setToClosedStatus = false;
			IncidentBMO iBMO = new IncidentBMO(); // init lostdelay pojo or ejb
			Incident oldInc=null;
			if(theform.getIncident_ID()!=null && !theform.getIncident_ID().isEmpty())
				oldInc=IncidentBMO.getIncidentByID(theform.getIncident_ID(), null);
				
			// copy into incident bean
			BeanUtils.copyProperties(iDTO, theform);
			
			if(iDTO.getAgentassigned() == null || iDTO.getAgentassigned().getAgent_ID() == 0)
				iDTO.setAgentassigned(null);

			String realpath = sc.getRealPath("/");
			String configpath = realpath + "/WEB-INF/classes/";
			String imagepath = realpath + "/deployment/main/images/nettracer/";

			
			
			// fault station
			if(theform.getFaultstation_id() == 0) {
				// default to assigned station
				iDTO.setFaultstation(new Station());
				
				if(iDTO.getAgent().getStation().getCompany().getVariable().getDefault_station_code() > 0) {
					iDTO.getFaultstation().setStation_ID(iDTO.getAgent().getStation().getCompany().getVariable().getDefault_station_code());
				}	else {
					iDTO.getFaultstation().setStation_ID(iDTO.getStationassigned().getStation_ID());
				}
				
				// set losscode
				if(iDTO.getAgent().getStation().getCompany().getVariable().getDefault_loss_code() > 0)
					iDTO.setLoss_code(iDTO.getAgent().getStation().getCompany().getVariable().getDefault_loss_code());
			}
			else {
				iDTO.setFaultstation(new Station());
				iDTO.getFaultstation().setStation_ID(theform.getFaultstation_id());
			}

			// closedate
			if(theform.getStatus_ID() == TracingConstants.MBR_STATUS_CLOSED) {
				if(iDTO.getClosedate() == null) {
					iDTO.setClosedate(TracerDateTime.getGMTDate());
					setToClosedStatus = true;
				}

			}
			else {
				// remove close date
				iDTO.setClosedate(null);
			}
			
			if (theform.getWtFile() != null) {
				iDTO.setWtFile(theform.getWtFile());
			}

			// set itemtype to incident
			ItemType it = new ItemType();
			it.setItemType_ID(itemtype);
			iDTO.setItemtype(it);

			if(itemtype == TracingConstants.MISSING_ARTICLES) {
				iDTO.setArticles(new LinkedHashSet<Articles>(theform.getArticlelist()));
				Articles art = null;
				for(int i = 0; i < theform.getArticlelist().size(); i++) {
					art = (Articles) theform.getArticlelist().get(i);
					art.setIncident(iDTO);
				}
			}
			
			

			// remove spaces between claimcheck numbers for missing articles and
			// damaged or remove the bag from item list if it is lostdelay and
			// nothing
			// is entered
			Item item = null;
			Item oldItem=null;
			Item_Inventory ii = null;
			if(iDTO.getItemlist() != null) {
				for(int i = iDTO.getItemlist().size() - 1; i >= 0; i--) {
					item = (Item) iDTO.getItemlist().get(i);
					item.setIncident(iDTO);

					if(oldInc!=null && oldInc.getItemlist()!=null && i<oldInc.getItemlist().size() && oldInc.getItemlist().get(i)!=null){
						oldItem=(Item) oldInc.getItemlist().get(i);
					}

					// remove the item from list if nothing is entered for lost
					// delay only
					// since it is traced
					if(item.getItemtype_ID() == TracingConstants.LOST_DELAY
							&& (item.getBagtype() == null || item.getBagtype().length() == 0)
							&& (item.getColor() == null || item.getColor().length() == 0)
							&& (item.getFnameonbag() == null || item.getFnameonbag().trim().length() == 0)
							&& (item.getLnameonbag() == null || item.getLnameonbag().trim().length() == 0)
							&& (item.getMnameonbag() == null || item.getMnameonbag().trim().length() == 0)
							&& item.getManufacturer_ID() == 0
							&& item.getXdescelement_ID_1() == TracingConstants.XDESC_TYPE_X
							&& item.getXdescelement_ID_2() == TracingConstants.XDESC_TYPE_X
							&& item.getXdescelement_ID_3() == TracingConstants.XDESC_TYPE_X
							&& item.getBag_weight() == 0
							&& item.getLossCode() == 0
							&& item.getFaultStation_id() == 0) {
						boolean t_rem = true;
						for(int j = item.getInventory().size() - 1; j >= 0; j--) {
							ii = (Item_Inventory) item.getInventorylist().get(j);
							if(ii.getCategorytype_ID() > 0
									|| (ii.getDescription() != null && ii.getDescription().trim().length() > 0)) {
								t_rem = false;
							}
							else {
								item.getInventorylist().remove(j);
							}
						}
						if(t_rem)
							iDTO.getItemlist().remove(i);
					}
					else {
						if(item.getClaimchecknum() != null && item.getClaimchecknum().length() > 0) {
							item.setClaimchecknum(TracerUtils.removeSpaces(item.getClaimchecknum().toUpperCase()));
						}
						
						OHD o=null;

						boolean hasPPU=(((UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PASSENGER_PICK_UP_LOSTDELAY, mod_agent) && item.getItemtype_ID()==TracingConstants.LOST_DELAY))
								|| ((UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PASSENGER_PICK_UP_MISSING, mod_agent) && item.getItemtype_ID()==TracingConstants.MISSING_ARTICLES))
								|| ((UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PASSENGER_PICK_UP_DAMAGE, mod_agent) && item.getItemtype_ID()==TracingConstants.DAMAGED_BAG)));
						if(hasPPU) {
							if(item.getStatus()!=null && item.getStatus().getStatus_ID()!=TracingConstants.ITEM_STATUS_PASSENGER_PICKED_UP
									&& oldItem!=null && oldItem.getStatus()!=null && oldItem.getStatus().getStatus_ID()==TracingConstants.ITEM_STATUS_PASSENGER_PICKED_UP){
								if(item.getOHD_ID()!=null && !item.getOHD_ID().isEmpty()){
									OhdBMO obmo=new OhdBMO();
									o=OhdBMO.getOHDByID(item.getOHD_ID(), null);
									if(o!=null){
										Remark rem=new Remark();
										rem.setRemarktype(TracingConstants.REMARK_REGULAR);
										rem.setOhd(o);
										rem.setAgent(mod_agent);
										rem.setCreatetime(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT).format(TracerDateTime.getGMTDate()));
										rem.setRemarktext(messages.getMessage(new Locale(mod_agent.getCurrentlocale()), "ohd.ppu.not.returned"));
										o.getRemarks().add(rem);
										Status s=null;
										if(o.getHoldingStation()!=null && item.getIncident()!=null && item.getIncident().getStationassigned()!=null &&
												o.getHoldingStation().getStation_ID()==item.getIncident().getStationassigned().getStation_ID()){
											s=StatusBMO.getStatus(TracingConstants.OHD_STATUS_TO_BE_DELIVERED);
										} else {
											s=StatusBMO.getStatus(TracingConstants.OHD_STATUS_OPEN);
										}
										o.setStatus(s);
										o.setDisposal_status(null);
										obmo.simpleSaveAndAuditOhd(o, mod_agent, o.getFoundAtStation(), null);
									}
								}
	
								Remark rem=new Remark();
								rem.setRemarktype(TracingConstants.REMARK_REGULAR);
								rem.setIncident(iDTO);
								rem.setAgent(mod_agent);
								rem.setCreatetime(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT).format(TracerDateTime.getGMTDate()));
								rem.setRemarktext(messages.getMessage(new Locale(mod_agent.getCurrentlocale()), "ppu.item")+" "+(i+1)+" "+messages.getMessage(new Locale(mod_agent.getCurrentlocale()), "inc.ppu.not.returned"));
								theform.getRemarklist().add(rem);
								
							} else if(item.getStatus().getStatus_ID()==TracingConstants.ITEM_STATUS_PASSENGER_PICKED_UP
									&& (oldItem==null || (oldItem!=null && oldItem.getStatus().getStatus_ID()!=TracingConstants.ITEM_STATUS_PASSENGER_PICKED_UP))){
								if(item.getOHD_ID()!=null && !item.getOHD_ID().isEmpty()){
									OhdBMO obmo=new OhdBMO();
									o=OhdBMO.getOHDByID(item.getOHD_ID(), null);
									if(o!=null){
										Status s=StatusBMO.getStatus(TracingConstants.OHD_STATUS_OWNER_PICKED_UP);
										Status s2=StatusBMO.getStatus(TracingConstants.OHD_STATUS_CLOSED);
										o.setStatus(s2);
										o.setDisposal_status(s);
										obmo.simpleSaveAndAuditOhd(o, mod_agent, o.getFoundAtStation(), null);
										
									}
									
								}
							}
						}
					}
				}
			}

			// set claimchecks to incident
			// remove all spaces within claimcheck for incident
			// if (itemtype == TracingConstants.LOST_DELAY) {
			Incident_Claimcheck ic = null;

			if(theform.getClaimchecklist() != null) {
				for(int i = theform.getClaimchecklist().size() - 1; i >= 0; i--) {
					ic = (Incident_Claimcheck) theform.getClaimchecklist().get(i);
					if(ic.getClaimchecknum() != null) {
						ic.setClaimchecknum(TracerUtils.removeSpaces(ic.getClaimchecknum().toUpperCase()));
						ic.setIncident(iDTO);
						if(ic.getClaimchecknum().length() == 0) {
							// nothing entered, remove it from claimcheck list
							theform.getClaimchecklist().remove(i);
						}
					}
					else {
						// claim checknum is null, remove it from the list
						theform.getClaimchecklist().remove(i);
					}

				}
			}
			
			//For Existing Incidents
			//If Property (and Lost delay){
			if(PropertyBMO.isTrue(PropertyBMO.PROPERTY_DELIVERY_INSTRUCTIONS) && iDTO.getItemtype().getItemType_ID()==TracingConstants.LOST_DELAY ) {
				if(theform.getDeliveryInstructions()!=null){
					DeliveryInstructions DI=theform.getDeliveryInstructions();
					DI.setInstructions(theform.getDeliveryInstructions().getInstructions());
					HibernateUtils.save(DI);
				}
			}

			Remark re = null;
			if(theform.getRemarklist() != null) {
				for(int i = 0; i < theform.getRemarklist().size(); i++) {
					re = (Remark) theform.getRemarklist().get(i);

					// If agent_ID = 0, agent is transient. To prevent exception
					// from being thrown, we get the non-transient version.
					// Was unable to determine what causes object to become
					// transient.
					if(re.getAgent() != null && re.getAgent().getAgent_ID() == 0) {
						try {
							Agent tmpAgent = TracerUtils.getAgent(re.getAgent().getUsername(), re.getAgent()
									.getCompanycode_ID());
							re.setAgent(tmpAgent);
						}
						catch (Exception e) {
							logger.error("Could not correct transient agent: " + e.getMessage());
							e.printStackTrace();
						}
					}

					re.setIncident(iDTO);
				}
			}
			Itinerary iti = null;
			if(theform.getItinerarylist() != null) {
				for(int i = 0; i < theform.getItinerarylist().size(); i++) {
					iti = (Itinerary) theform.getItinerarylist().get(i);
					iti.setIncident(iDTO);
				}
			}

			iDTO.setClaimchecks(new LinkedHashSet<Incident_Claimcheck>(theform.getClaimchecklist()));
			iDTO.setRemarks(new LinkedHashSet<Remark>(theform.getRemarklist()));
			iDTO.setItinerary(new LinkedHashSet<Itinerary>(theform.getItinerarylist()));

			iDTO.setPassengers(new LinkedHashSet<Passenger>(theform.getPassengerlist()));
			Passenger pa = null;
			if(iDTO.getPassengers() != null) {
				for(Iterator<Passenger> i = iDTO.getPassengers().iterator(); i.hasNext();) {
					pa = (Passenger) i.next();
					pa.setIncident(iDTO);
					if(pa.getMembership() != null) {
						if(pa.getMembership().getCompanycode_ID() == null
								|| pa.getMembership().getCompanycode_ID().length() <= 0) {
							pa.setMembership(null);
						}
					}
				}
			}
			
			for(ExpensePayout ep : iDTO.getExpenses()) {
				ep.setIncident(iDTO);
				ep.setExpensepayout_ID(0);
			}
			
			if(iDTO.getActivities()!=null){
				for(IncidentActivity ia: iDTO.getActivities()){
					ia.setId(0); 
					ia.setIncident(iDTO);
				}
			}

			// everytime when user update mbr, reset the ohd lasttraced date so
			// tracer
			// will pick it up.
			iDTO.setOhd_lasttraced(null);
			
			// set incident for all issuance items just before saving.
			for (IssuanceItemIncident iItem : iDTO.getIssuanceItemIncidents()) {
				iItem.setIncident(iDTO);
			}

			int result = -1;
			// if it is readonly and update remark then only update remark
			if(theform.getReadonly() == 1) {
				// the agent may have permission to save the loss code even though this is read only. This boolean will return true if that is the case.
				boolean stationLock=(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LIMITED_LOSS_CODES, mod_agent) 
						&& iDTO.getStatus().getStatus_ID()==13 && mod_agent.getStation().getStation_ID() == theform.getFaultstation_id() );
				if (stationLock) { // If true save remarks and loss code. Otherwise just save remarks.
					result = iBMO.updateRemarksAndLossCodeOnly(iDTO.getIncident_ID(), iDTO.getRemarks(), mod_agent, iDTO.getLoss_code());
				} else if(theform.getAllow_remark_update() == 1) {
					result = iBMO.updateRemarksOnly(iDTO.getIncident_ID(), iDTO.getRemarks(), mod_agent);
				}
			}
			else {
				try{
					if(setToClosedStatus && checkClosedStatus) {
						result = iBMO.insertIncident(true, iDTO, theform.getAssoc_ID(), mod_agent, true);
					}
					else {
						result = iBMO.insertIncident(true, iDTO, theform.getAssoc_ID(), mod_agent, false);
					}
				} catch (StaleStateException e){
					for(int i = 0; i < theform.getPassengerlist().size(); i++) {
						pa = (Passenger) theform.getPassenger(i);
						if(pa.getMembership() == null) {
							pa.setMembership(new AirlineMembership());
						}
					}
					logger.error("unable to insert incident due to bean stale state error: " + e);
					return new ActionMessage("error.unable_to_insert_incident_stale_state", messages.getMessage(new Locale(mod_agent.getCurrentlocale()), "incident"),iDTO.getIncident_ID());
				}
			}

			if(result <= 0) {
				for(int i = 0; i < theform.getPassengerlist().size(); i++) {
					pa = (Passenger) theform.getPassenger(i);
					if(pa.getMembership() == null) {
						pa.setMembership(new AirlineMembership());
					}
				}
				if(result == -1)
					return new ActionMessage("error.version.unable_to_insert_incident");
				else
					return new ActionMessage("error.unable_to_insert_incident");
			}
			else {

				// first set report verion to sync with inserted version
				theform.setVersion(iDTO.getVersion());

				// check the status of the incident and if it was set to open,
				// possibly
				// add to incidents.

				Billing bill = BillingUtils.reportExists(iDTO.getIncident_ID());

				if(bill == null) {

					bill = new Billing();
					bill.setAgent_id(iDTO.getAgent().getAgent_ID());
					bill.setCompanyCode(iDTO.getStationcreated().getCompany().getCompanyCode_ID());
					bill.setStation_id(iDTO.getStationcreated().getStation_ID());
					bill.setIncident(iDTO);
					bill.setCreate_date_time(DateUtils.convertToDate(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT).format(iDTO
							.getCreatedate()), TracingConstants.DB_DATETIMEFORMAT, null));
					if(iDTO.getStatus().getStatus_ID() != TracingConstants.MBR_STATUS_TEMP) {
						bill.setStatus_change_time(iDTO.getCreatedate());
					}
					else {
						bill.setStatus_change_time(null);
					}
					HibernateUtils.saveNew(bill);
				}
				else {
					// Was temp before?
					if(bill.getStatus_change_time() == null) {
						// Is new status "Open"
						if(iDTO.getStatus().getStatus_ID() != TracingConstants.MBR_STATUS_TEMP) {
							bill.setStatus_change_time(TracerDateTime.getGMTDate());
							HibernateUtils.save(bill);
						}
					}
				}

				// set passenger membership back to not null
				String toemail = null;
				String passname = null;
				String languageKey = null;
				for(int i = 0; i < theform.getPassengerlist().size(); i++) {
					pa = (Passenger) theform.getPassenger(i);
					if(i == 0) {
						Address adr = pa.getAddress(0);
						toemail = adr.getEmail();
						passname = pa.getFirstname() + " " + pa.getLastname();
						languageKey = StringUtils.trim(pa.getLanguageKey());
					}
					if(pa.getMembership() == null) {
						pa.setMembership(new AirlineMembership());
					}
				}

				// send email to customer if sending email is set to 1
				if(theform.getEmail_customer() > 0) {
					// get email
					if(toemail != null && toemail.length() > 0) {

						// send email
						try {
							HtmlEmail he = new HtmlEmail();
							he.setHostName(theform.getAgent().getStation().getCompany().getVariable().getEmail_host());
							he.setSmtpPort(theform.getAgent().getStation().getCompany().getVariable().getEmail_port());
							he.setFrom(theform.getAgent().getStation().getCompany().getVariable().getEmail_from());
						
							//prefered language has higher precedence
							String currentLocale = null;
							if (languageKey != null && session.getAttribute("receiptLocaleList") != null) {
								ArrayList<LabelValueBean> receiptLocaleList = (ArrayList<LabelValueBean>) session.getAttribute("receiptLocaleList");
								for (LabelValueBean labelValueBean : receiptLocaleList) {
									if (StringUtils.equalsIgnoreCase(languageKey, labelValueBean.getLabel())) {
										currentLocale = labelValueBean.getValue();
										break;
									}
								}
							}
							
							if(currentLocale == null) {
								currentLocale = StringUtils.trimToNull(theform.getLanguage());
							}		
							if(currentLocale == null) {
								currentLocale = iDTO.getAgent().getCurrentlocale();
							}

							ArrayList<InternetAddress> al = new ArrayList<InternetAddress>();
							al.add(new InternetAddress(toemail));
							he.setTo(al);
							String bcc = theform.getAgent().getStation().getCompany().getVariable().getBlindEmail();
							if(bcc != null && bcc.trim().length() > 0) {
								List<InternetAddress> bccList = new ArrayList<InternetAddress>();
								bccList.add(new InternetAddress(bcc));
								he.setBcc(bccList);
							}

							MessageResources messages = MessageResources
									.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");

							String htmlFileName = "report_email.html";
							String tmpHtmlFileName = null;
							boolean embedImage = true;
							
							HashMap<String,String> h = new HashMap<String,String>();
							h.put("PASS_NAME", passname);

							File file = null;
							String myEmailSubjectLine = null;
							if(iDTO.getItemtype_ID() == TracingConstants.LOST_DELAY) {
								h.put("REPORT_TYPE", messages.getMessage(
										new Locale(currentLocale), "email.mishandled"));
								
								tmpHtmlFileName = TracerProperties.get(theform.getAgent().getStation().getCompany().getCompanyCode_ID(),TracerProperties.EMAIL_REPORT_LD);
								embedImage = !TracerProperties.isTrue(theform.getAgent().getStation().getCompany().getCompanyCode_ID(),TracerProperties.EMAIL_REPORT_LD_DISABLE_IMAGE);
								h.putAll(LostDelayReceipt.getParameters(theform, null, null, theform.getAgent(), "lostdelay.email.title"));
								
								myEmailSubjectLine = StringUtils.trimToNull(TracerProperties.get(theform.getAgent().getStation().getCompany().getCompanyCode_ID(),"email.subjectline.lostdelay.key"));
								myEmailSubjectLine = (myEmailSubjectLine == null) ? myEmailSubjectLine : StringUtils.trimToNull(messages.getMessage(new Locale(currentLocale), myEmailSubjectLine));
								myEmailSubjectLine = (myEmailSubjectLine != null) ? myEmailSubjectLine : messages.getMessage(new Locale(currentLocale), "email.subject", messages.getMessage(new Locale(currentLocale), "email.mishandled"));
								
								h.put("EMAIL_APOLOGIZE_FOR_TEXT", messages.getMessage(
										new Locale(currentLocale), "email.mishandled.content.apologize.text"));
								
								if (StringUtils.trimToNull(theform.getIncident_ID()) == null && PropertyBMO.isTrue(PropertyBMO.PROPERTY_MISHANDLING_ATTACHMENT_AT_CREATION)) {
									theform.setIncident_ID(iDTO.getIncident_ID());
									request.setAttribute(TracingConstants.COMMAND_PRINT, ReportingConstants.LOST_RECEIPT_RPT);									
									file = FileShareUtils.getFile(LostDelayReceipt.createReport(theform, sc, request, TracingConstants.REPORT_OUTPUT_PDF, currentLocale), sc);
									theform.setIncident_ID(null);//reset form incident Id
								}
							}
							else if(iDTO.getItemtype_ID() == TracingConstants.DAMAGED_BAG) {
								h.put("REPORT_TYPE", messages.getMessage(
										new Locale(currentLocale), "email.damaged"));
								tmpHtmlFileName = TracerProperties.get(theform.getAgent().getStation().getCompany().getCompanyCode_ID(),TracerProperties.EMAIL_REPORT_DAM);
								embedImage = !TracerProperties.isTrue(theform.getAgent().getStation().getCompany().getCompanyCode_ID(),TracerProperties.EMAIL_REPORT_DAM_DISABLE_IMAGE);
								h.putAll(LostDelayReceipt.getParameters(theform, null, null, theform.getAgent(), "damage.email.title"));
								
								myEmailSubjectLine = StringUtils.trimToNull(TracerProperties.get(theform.getAgent().getStation().getCompany().getCompanyCode_ID(),"email.subjectline.damaged.key"));
								myEmailSubjectLine = (myEmailSubjectLine == null) ? myEmailSubjectLine : StringUtils.trimToNull(messages.getMessage(new Locale(currentLocale), myEmailSubjectLine));
								myEmailSubjectLine = (myEmailSubjectLine != null) ? myEmailSubjectLine : messages.getMessage(new Locale(currentLocale), "email.subject", messages.getMessage(new Locale(currentLocale), "email.damaged"));
								
								h.put("EMAIL_APOLOGIZE_FOR_TEXT", messages.getMessage(
										new Locale(currentLocale), "email.damaged.content.apologize.text"));
								
								if (StringUtils.trimToNull(theform.getIncident_ID()) == null && PropertyBMO.isTrue(PropertyBMO.PROPERTY_MISHANDLING_ATTACHMENT_AT_CREATION)) {
									theform.setIncident_ID(iDTO.getIncident_ID());
									request.setAttribute(TracingConstants.COMMAND_PRINT, ReportingConstants.DAMAGE_RECEPIT_RPT);									
									file = FileShareUtils.getFile(DamageReceipt.createReport(theform, sc, request, TracingConstants.REPORT_OUTPUT_PDF, currentLocale), sc);
									theform.setIncident_ID(null);//reset form incident Id
								}
							} 
							else if(iDTO.getItemtype_ID() == TracingConstants.MISSING_ARTICLES) {
								h.put("REPORT_TYPE", messages.getMessage(
										new Locale(currentLocale), "email.missing"));
								tmpHtmlFileName = TracerProperties.get(theform.getAgent().getStation().getCompany().getCompanyCode_ID(),TracerProperties.EMAIL_REPORT_PIL);
								embedImage = !TracerProperties.isTrue(theform.getAgent().getStation().getCompany().getCompanyCode_ID(),TracerProperties.EMAIL_REPORT_PIL_DISABLE_IMAGE);
								h.putAll(LostDelayReceipt.getParameters(theform, null, null, theform.getAgent(), "missing.email.title"));
								
								myEmailSubjectLine = StringUtils.trimToNull(TracerProperties.get(theform.getAgent().getStation().getCompany().getCompanyCode_ID(),"email.subjectline.pilfered.key"));
								myEmailSubjectLine = (myEmailSubjectLine == null) ? myEmailSubjectLine : StringUtils.trimToNull(messages.getMessage(new Locale(currentLocale), myEmailSubjectLine));
								myEmailSubjectLine = (myEmailSubjectLine != null) ? myEmailSubjectLine : messages.getMessage(new Locale(currentLocale), "email.subject", messages.getMessage(new Locale(currentLocale), "email.missing"));
								
								h.put("EMAIL_APOLOGIZE_FOR_TEXT", messages.getMessage(
										new Locale(currentLocale), "email.missing.content.apologize.text"));
								
								if (StringUtils.trimToNull(theform.getIncident_ID()) == null && PropertyBMO.isTrue(PropertyBMO.PROPERTY_MISHANDLING_ATTACHMENT_AT_CREATION)) {
									theform.setIncident_ID(iDTO.getIncident_ID());
									request.setAttribute(TracingConstants.COMMAND_PRINT, ReportingConstants.MISSING_RECEPIT_RPT);									
									file = FileShareUtils.getFile(MissingReceipt.createReport(theform, sc, request, TracingConstants.REPORT_OUTPUT_PDF, currentLocale), sc);
									theform.setIncident_ID(null);//reset form incident Id
								}
							}
							
						
							if (tmpHtmlFileName != null) {
								htmlFileName = tmpHtmlFileName;
							}
							if(currentLocale != null && !currentLocale.equalsIgnoreCase("en")) {
								htmlFileName = htmlFileName.replaceFirst("\\.html$", "_" + currentLocale.toLowerCase() + ".html");
							}

							h.put("REPORT_NUMBER", iDTO.getIncident_ID());
							h.put("AIRLINE", iDTO.getStationcreated().getCompany().getCompanydesc());

							// claim checks
							StringBuffer sb = new StringBuffer();
							if(iDTO.getItemtype_ID() == TracingConstants.LOST_DELAY) {
								for(int i = 0; i < iDTO.getClaimcheck_list().size(); i++) {
									if(iDTO.getClaimcheck_list().get(i) != null) {
										sb.append(((Incident_Claimcheck) iDTO.getClaimcheck_list().get(i))
												.getClaimchecknum());
										sb.append("<BR>");
									}
								}
							}
							else {
								Item item2 = null;
								for(int i = 0; i < iDTO.getItemlist().size(); i++) {
									item2 = (Item) iDTO.getItemlist().get(i);
									if(item2.getClaimchecknum() != null) {
										sb.append(item2.getClaimchecknum());
										sb.append("<BR>");
									}
								}
							}
							h.put("CLAIM_CHECKS", sb.toString());

							// set embedded images
							String myEmailHeaderImage = TracingConstants.BANNER_IMAGE;
							String strImageLogoFromPropertiesFile = 
								messages.getMessage(new Locale(currentLocale), "email.to.pax.header.image.file");
							if (strImageLogoFromPropertiesFile != null && strImageLogoFromPropertiesFile.equalsIgnoreCase("")) {
								myEmailHeaderImage = strImageLogoFromPropertiesFile;
							}
							
							if (embedImage) {
								String img1 = he.embed(new URL("file:/" + imagepath + myEmailHeaderImage),
										myEmailHeaderImage);
								h.put("BANNER_IMAGE", img1);
							}
								
							String msg = EmailParser.parse(configpath + htmlFileName, h, currentLocale);
							
							//logger.error("entire html email:" + msg);
							
							if(msg != null) {
								he.setSubject(myEmailSubjectLine);
								he.setHtmlMsg(msg);
								he.setCharset("UTF-8");
								
								if (file != null && file.exists()) {
									logger.debug("Attaching file : " + file.getPath());
									he.attach(new FileDataSource(file), file.getPath(), "");
									he.setBoolHasAttachments(false); //appear to have a bug when there is an attachment
								}
								
								he.send();
							}
							else {
								logger.warn("Unable to send email because email HTML file was not parsed.");
							}

						}
						catch (Exception maile) {
							logger.error("Unable to send mail due to smtp error. " + maile);
						}

					}

				}
				if(result == 2)
					return new ActionMessage("error.unable_to_close_incident");
				
				/*
				 * If the file is set to closed status AND the file is a L/D incident
				 * we need to close the matched OHDs when saving.
				 */
				if (mod_agent.getStation().getCompany().getVariable().isAutoCloseOhd() && setToClosedStatus) {
					
					HashSet<String> ohdIds = new HashSet<String>();
					
					
					for (Incident_Claimcheck tmp: (ArrayList<Incident_Claimcheck>)iDTO.getClaimcheck_list()) {
						ohdIds.add(tmp.getOHD_ID());
					}
					
					for (Item tmp: (List<Item>) iDTO.getItemlist()) {
						ohdIds.add(tmp.getOHD_ID());
					}
					
					for (String ohdId: ohdIds) {
						if (ohdId != null && ohdId.trim().length() > 0) {
							Session sess = null;
							try {
								sess = HibernateWrapper.getSession().openSession();
								
								OHD ohd = OhdBMO.getOHDByID(ohdId, sess);
								if (ohd == null) {
									logger.error("BagService.java line 859: Should not have null ohd for: " + ohdId);
								} else {
									Status newStatus = new Status();
									newStatus.setStatus_ID(TracingConstants.OHD_STATUS_CLOSED);
									ohd.setStatus(newStatus);
									OhdBMO.updateOHD(ohd, mod_agent, sess);
									
									if (ohd.getWtFile() != null) {
										WtqOhdAction wtq = new WtqCloseOhd();
										wtq.setAgent(mod_agent);
										wtq.setCreatedate(TracerDateTime.getGMTDate());
										wtq.setOhd(ohd);
										WorldTracerQueueUtils.createOrReplaceQueue(wtq);
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								if (sess != null) sess.close();
							}
						}
					}
					
				}

				return null;
			}

		}
		catch (Exception e) {
			logger.error("unable to insert incident due to bean copyproperties error: " + e);
			e.printStackTrace();
			return new ActionMessage("error.unable_to_insert_incident");
		}
	}
	
	public ActionMessage saveItems(Incident iDTO, IncidentForm theform, int itemtype, String realpath,
			Agent mod_agent, boolean checkClosedStatus) {
		try {
			IncidentBMO iBMO = new IncidentBMO(); // init lostdelay pojo or ejb
			// copy into incident bean
			BeanUtils.copyProperties(iDTO, theform);
			Incident oInc=IncidentBMO.getIncidentByID(iDTO.getIncident_ID(), null);
			
			
			if(iDTO.getAgentassigned() == null || iDTO.getAgentassigned().getAgent_ID() == 0)
				iDTO.setAgentassigned(null);

			// set itemtype to incident
			ItemType it = new ItemType();
			it.setItemType_ID(itemtype);
			iDTO.setItemtype(it);

			if(itemtype == TracingConstants.MISSING_ARTICLES) {
				iDTO.setArticles(new LinkedHashSet<Articles>(theform.getArticlelist()));
				Articles art = null;
				for(int i = 0; i < theform.getArticlelist().size(); i++) {
					art = (Articles) theform.getArticlelist().get(i);
					art.setIncident(iDTO);
				}
			}
			
			
			
			// remove spaces between claimcheck numbers for missing articles and
			// damaged or remove the bag from item list if it is lostdelay and
			// nothing
			// is entered
			Item item = null;
			Item_Inventory ii = null;
			
			if(iDTO.getItemlist() != null) {
				List<Item> ilist=iDTO.getItemlist();
				for(int i = ilist.size() - 1; i >= 0; i--) {
					item = (Item)ilist.get(i);
					item.setIncident(iDTO);

					// remove the item from list if nothing is entered for lost
					// delay only
					// since it is traced
					if(item.getItemtype_ID() == TracingConstants.LOST_DELAY
							&& (item.getBagtype() == null || item.getBagtype().length() == 0)
							&& (item.getColor() == null || item.getColor().length() == 0)
							&& (item.getFnameonbag() == null || item.getFnameonbag().trim().length() == 0)
							&& (item.getLnameonbag() == null || item.getLnameonbag().trim().length() == 0)
							&& (item.getMnameonbag() == null || item.getMnameonbag().trim().length() == 0)
							&& item.getManufacturer_ID() == 0
							&& item.getXdescelement_ID_1() == TracingConstants.XDESC_TYPE_X
							&& item.getXdescelement_ID_2() == TracingConstants.XDESC_TYPE_X
							&& item.getXdescelement_ID_3() == TracingConstants.XDESC_TYPE_X
							&& item.getBag_weight() == 0) {
						boolean t_rem = true;
						for(int j = item.getInventory().size() - 1; j >= 0; j--) {
							ii = (Item_Inventory) item.getInventorylist().get(j);
							if(ii.getCategorytype_ID() > 0
									|| (ii.getDescription() != null && ii.getDescription().trim().length() > 0)) {
								t_rem = false;
							}
							else {
								item.getInventorylist().remove(j);
							}
						}
						if(t_rem)
							iDTO.getItemlist().remove(i);
					}
					else {
						if(item.getClaimchecknum() != null && item.getClaimchecknum().length() > 0) {
							item.setClaimchecknum(TracerUtils.removeSpaces(item.getClaimchecknum().toUpperCase()));
						}
					}
				}
				
				//Passenger, Itinerary, and Baginformation set for Audit purposes
				if(oInc!=null){
				// fault station
				if(oInc.getFaultstation()!=null && oInc.getFaultstation().getStation_ID()== 0) {
					// default to assigned station
					iDTO.setFaultstation(new Station());
					
					if(iDTO.getAgent().getStation().getCompany().getVariable().getDefault_station_code() > 0) {
						iDTO.getFaultstation().setStation_ID(iDTO.getAgent().getStation().getCompany().getVariable().getDefault_station_code());
					}	else {
						iDTO.getFaultstation().setStation_ID(iDTO.getStationassigned().getStation_ID());
					}
					
					// set losscode
					if(iDTO.getAgent().getStation().getCompany().getVariable().getDefault_loss_code() > 0)
						iDTO.setLoss_code(iDTO.getAgent().getStation().getCompany().getVariable().getDefault_loss_code());
				}
				else {
					iDTO.setFaultstation(new Station());
					iDTO.getFaultstation().setStation_ID(oInc.getFaultstation().getStation_ID());
				}
				
				if (oInc.getWtFile() != null) {
					iDTO.setWtFile(oInc.getWtFile());
				}

				// set claimchecks to incident
				// remove all spaces within claimcheck for incident
				// if (itemtype == TracingConstants.LOST_DELAY) {
				Incident_Claimcheck ic = null;

				if(oInc.getClaimcheck_list() != null) {
					for(int i = oInc.getClaimcheck_list().size() - 1; i >= 0; i--) {
						ic = (Incident_Claimcheck) oInc.getClaimcheck_list().get(i);
						if(ic.getClaimchecknum() != null) {
							ic.setClaimchecknum(TracerUtils.removeSpaces(ic.getClaimchecknum().toUpperCase()));
							ic.setIncident(iDTO);
							if(ic.getClaimchecknum().length() == 0) {
								// nothing entered, remove it from claimcheck list
								oInc.getClaimcheck_list().remove(i);
							}
						}
						else {
							// claim checknum is null, remove it from the list
							oInc.getClaimcheck_list().remove(i);
						}

					}
				}
				
				//For Existing Incidents
				//If Property (and Lost delay){
				if(PropertyBMO.isTrue(PropertyBMO.PROPERTY_DELIVERY_INSTRUCTIONS) && iDTO.getItemtype().getItemType_ID()==TracingConstants.LOST_DELAY ) {
					if(oInc.getDeliveryInstructions()!=null){
						DeliveryInstructions DI=oInc.getDeliveryInstructions();
						DI.setInstructions(oInc.getDeliveryInstructions().getInstructions());
						HibernateUtils.save(DI);
					}
				}

				if(oInc.getRemarks() != null) {
					for(Remark re:oInc.getRemarks()) {
						//re = (Remark) oInc.getRemarks().get(i);

						// If agent_ID = 0, agent is transient. To prevent exception
						// from being thrown, we get the non-transient version.
						// Was unable to determine what causes object to become
						// transient.
						if(re.getAgent() != null && re.getAgent().getAgent_ID() == 0) {
							try {
								Agent tmpAgent = TracerUtils.getAgent(re.getAgent().getUsername(), re.getAgent()
										.getCompanycode_ID());
								re.setAgent(tmpAgent);
							}
							catch (Exception e) {
								logger.error("Could not correct transient agent: " + e.getMessage());
								e.printStackTrace();
							}
						}

						re.setIncident(iDTO);
					}
				}
				Itinerary iti = null;
				if(oInc.getItinerary_list() != null) {
					for(int i = 0; i < oInc.getItinerary_list().size(); i++) {
						iti = (Itinerary) oInc.getItinerary_list().get(i);
						iti.setIncident(iDTO);
					}
				}

				iDTO.setClaimchecks(new LinkedHashSet<Incident_Claimcheck>(oInc.getClaimcheck_list()));
				iDTO.setRemarks(oInc.getRemarks());
				iDTO.setItinerary(new LinkedHashSet<Itinerary>(oInc.getItinerary_list()));

				iDTO.setPassengers(new LinkedHashSet<Passenger> (oInc.getPassenger_list()));
				Passenger pa = null;
				if(iDTO.getPassengers() != null) {
					for(Iterator<Passenger> i = iDTO.getPassengers().iterator(); i.hasNext();) {
						pa = (Passenger) i.next();
						pa.setIncident(iDTO);
						if(pa.getMembership() != null) {
							if(pa.getMembership().getCompanycode_ID() == null
									|| pa.getMembership().getCompanycode_ID().length() <= 0) {
								pa.setMembership(null);
							}
						}
					}
				}
				
				for(ExpensePayout ep : iDTO.getExpenses()) {
					ep.setIncident(iDTO);
					ep.setExpensepayout_ID(0);
				}

				if(iDTO.getActivities()!=null){
					for(IncidentActivity ia: iDTO.getActivities()){
						ia.setId(0); 
						ia.setIncident(iDTO);
					}
				}
				// everytime when user update mbr, reset the ohd lasttraced date so
				// tracer
				// will pick it up.
				iDTO.setOhd_lasttraced(null);
				}
				//End Audit code
				
				int result = -1;
				try{
					result=iBMO.insertItemContents(true, ilist,mod_agent, iDTO);
				} catch(Exception e){
					e.printStackTrace();
				}
				if(result <= 0) {
					if(result == -1)
						return new ActionMessage("error.version.unable_to_insert_incident");
					else
						return new ActionMessage("error.unable_to_insert_incident");
				}
				
			}
			return null;

		}
		catch (Exception e) {
			logger.error("unable to insert incident due to bean copyproperties error: " + e);
			e.printStackTrace();
			return new ActionMessage("error.unable_to_save_items");
		}
	}

	/** for use with html rewrite find by incident_ID * */
	public Incident findIncidentByID(String incident_ID, IncidentForm theform, Agent user, int itemtype) {
		try {
			IncidentBMO iBMO = new IncidentBMO();
			Incident iDTO = iBMO.findIncidentByID(incident_ID);
			if(iDTO == null)
				return null;

			populateIncidentFormFromIncidentObj(incident_ID, theform, user, itemtype, iBMO, iDTO, false);
			SpringUtils.getLockFile().lockIncident(user, iDTO);
			return iDTO;
		}
		catch (Exception e) {
			logger.error("unable to find incident due to bean copyproperties error: " + e);
			return null;
		}
	}

	public void populateIncidentFormFromIncidentObj(String incident_ID, IncidentForm theform, Agent user,
			int itemtype, IncidentBMO iBMO, Incident iDTO, boolean doNotEmail)
			throws IllegalAccessException, InvocationTargetException {
		BeanUtils.copyProperties(theform, iDTO);
		
		// make sure separate stationcreate and stationassigned and
		// faultstation
		// into different station object
		// hibernate makes all stations into one object for some unknown
		// reason
		// todo later
		Station stationcreated = new Station();
		stationcreated.setStation_ID(iDTO.getStationcreated().getStation_ID());
		stationcreated.setStationcode(iDTO.getStationcreated().getStationcode());
		theform.setStationassigned(new Station());
		theform.setStationassigned_ID(iDTO.getStationassigned().getStation_ID());
		theform.getStationassigned().setCompany(iDTO.getStationassigned().getCompany());
		theform.getStationassigned().setCity(iDTO.getStationassigned().getCity());
		theform.getStationassigned().setPhone(iDTO.getStationassigned().getPhone());

		// make sure agentassigned <> agentcreated
		if(theform.getAgentassigned() != null && theform.getAgentassigned_ID() == theform.getAgent().getAgent_ID()) {
			Agent newagentassigned = new Agent();
			BeanUtils.copyProperties(newagentassigned, theform.getAgentassigned());
			theform.setAgentassigned(null);
			theform.setAgentassigned(newagentassigned);
		}

		theform.setFaultstation(new Station());
		if(iDTO.getFaultstation() != null) {
			theform.setFaultstation_id(iDTO.getFaultstation().getStation_ID());
			theform.setFaultcompany_id(iDTO.getFaultstation().getCompany().getCompanyCode_ID());
		}

		Item item = null;
		// in case there are no bags, put one there anyways
		if(theform.getItemlist().size() == 0) {
			item = (Item) theform.getItem(0, iDTO.getItemtype_ID());
			// item.setItem_ID(0); // for hibernate insert only
			item.setXdescelement_ID_1(TracingConstants.XDESC_TYPE_X);
			item.setXdescelement_ID_2(TracingConstants.XDESC_TYPE_X);
			item.setXdescelement_ID_3(TracingConstants.XDESC_TYPE_X);
			item.set_DATEFORMAT(user.getDateformat().getFormat());
			item.setCurrency_ID(user.getDefaultcurrency());
			item.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_OPEN));
			Item_Inventory ii = new Item_Inventory();
			ii.setItem(item);
			ii.set_DATEFORMAT(user.getDateformat().getFormat());
			ii.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			ii.setEnteredDate(DateUtils.convertToGMTDate(new Date()));
			ii.setInvItemCurrency(user.getDefaultcurrency());
			item.getInventorylist().add(ii);
		}
		else {
			int bagnumber = 0;
			for(int i = 0; i < theform.getItemlist().size(); i++) {
				item = (Item) theform.getItemlist().get(i);
				if(item == null) {
					theform.getItemlist().remove(i);
					i--;
				}
				else {
					if(item.getItemtype_ID() <= 0)
						item.setItemtype_ID(iDTO.getItemtype_ID());
					if(item.getStatus() == null)
						item.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_OPEN));
					item.set_DATEFORMAT(user.getDateformat().getFormat());
					item.setBagnumber(bagnumber);
					bagnumber++;
					if(item.getInventorylist().size() == 0) {
						Item_Inventory ii = new Item_Inventory();
						ii.setItem(item);
						ii.set_DATEFORMAT(user.getDateformat().getFormat());
						ii.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
						item.getInventorylist().add(ii);
					} else {
						Iterator<?> itemIterator = item.getInventorylist().iterator();
						while(itemIterator.hasNext()) {
							Item_Inventory theItem = (Item_Inventory) itemIterator.next();
							theItem.set_DATEFORMAT(user.getDateformat().getFormat());
							theItem.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
						}
					}
				}
			}
		}

		if(itemtype == TracingConstants.MISSING_ARTICLES) {
			ArrayList<Articles> al = new ArrayList<Articles>(iDTO.getArticles());
			if(al.size() <= 0) {
				theform.getArticle(0);
			}
			else {
				//to iterate through list to set DATEFORMAT -
				for (Articles myArticle: al) {
					myArticle.set_DATEFORMAT(user.getDateformat().getFormat());
				}
				theform.setArticlelist(al);
			}
				
		}
		theform.setClaimchecklist(new ArrayList<Incident_Claimcheck>(iDTO.getClaimchecks()));
		if(theform.getClaimchecklist().size() == 0) {
			theform.getClaimcheck(0);
		}
		
		if(iDTO.getDeliveryInstructions()!=null){
			theform.setDeliveryInstructions(iDTO.getDeliveryInstructions());
		}

		theform.setPassengerlist(new ArrayList<Passenger>(iDTO.getPassengers()));
		Passenger p = null;
		Address addr = null;
		for(int i = 0; i < theform.getPassengerlist().size(); i++) {
			p = theform.getPassenger(i);
			if(p.getMembership() == null) {
				p.setMembership(new AirlineMembership());
			}
			for(int j = 0; j < p.getAddresses().size(); j++) {
				addr = (Address) p.getAddress(j);
				addr.set_DATEFORMAT(user.getDateformat().getFormat());
			}
		}

		// set datetime format
		if (iDTO.getRemarks() == null) {
			iDTO.setRemarks(new HashSet<Remark>());
		}
		theform.setRemarklist(new ArrayList<Remark>(iDTO.getRemarks()));
		Remark remark = null;
		for(int i = 0; i < theform.getRemarklist().size(); i++) {
			remark = (Remark) theform.getRemarklist().get(i);
			remark.set_DATEFORMAT(user.getDateformat().getFormat());
			remark.set_TIMEFORMAT(user.getTimeformat().getFormat());
			remark.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
					.getTimezone()));
			if(remark.getRemarktype() <= 0)
				remark.setRemarktype(TracingConstants.REMARK_REGULAR);

		}

		if (iDTO.getItinerary() == null) {
			iDTO.setItinerary(new HashSet<Itinerary>());
		}
		theform.setItinerarylist(new ArrayList<Itinerary>(iDTO.getItinerary()));
		Itinerary iti = null;

		for(int i = 0; i < theform.getItinerarylist().size(); i++) {
			iti = (Itinerary) theform.getItinerarylist().get(i);
			iti.set_DATEFORMAT(user.getDateformat().getFormat());
			iti.set_TIMEFORMAT(user.getTimeformat().getFormat());
		}
		
		List<IncidentActivityDTO> activities = new ArrayList<IncidentActivityDTO>();
		for (IncidentActivity activity: iDTO.getActivities()) {
			IncidentActivityDTO dto = new IncidentActivityDTO();
			dto.set_DATEFORMAT(user.getDateformat().getFormat());
			dto.set_TIMEFORMAT(user.getTimeformat().getFormat());
			dto.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			dto.setId(activity.getId());
			dto.setCreateDate(activity.getCreateDate());
			dto.setPublishedDate(activity.getPublishedDate());
			if (activity.getLastStatus() != null) {
				dto.setStatusId(activity.getLastStatus().getStatus_ID());
			}
			dto.setAgent(activity.getAgent().getUsername());
			dto.setDescription(activity.getDescription());
			dto.setCustCommId(activity.getCustCommId());
			if (activity.getActivity() != null) {
				dto.setActivityCode(activity.getActivity().getCode());
			}

			if (activity.getDocument() != null) {
				dto.setFileName(activity.getDocument().getFileName());
			}
			boolean published = false;
			boolean toBeAcknowledged = false;
			if (activity.getMessages() != null && activity.getMessages().size() > 0) {
				List<MessageDTO> mdtolist = DomainUtils.fromMessages(activity.getMessages(), user);
				if (mdtolist != null) {
					dto.setMessages(mdtolist);
				}
				for (OCMessage message : activity.getMessages()) {
					if (message.isPublish()) {
						published = true;
					}
					if (message.getDateReviewed() == null) {
						toBeAcknowledged = true;
					}
				}

			}

			if (activity.getFiles() != null && activity.getFiles().size() > 0) {
				List<FileDTO> fdtolist = DomainUtils.fromFiles(activity.getFiles(), user);
				if (fdtolist != null) {
					dto.setFiles(fdtolist);
				}

				for (OCFile file : activity.getFiles()) {
					if (file.isPublish()) {
						published = true;
					}
					if (file.getDateViewed() == null) {
						toBeAcknowledged = true;
					}
				}
			}

			dto.setPublished(published);
			dto.setToBeAcknowledged(toBeAcknowledged);

			dto.setCustomerCommunication(activity.getActivity().getCode().equals(TracingConstants.ACTIVITY_CUSTOMER_COMMUNICATION)
					|| activity.getActivity().getCode().equals(TracingConstants.CREATE_SETTLEMENT_ACTIVITY));

			// mark if activity is portal correspondence
			dto.setCorrespondence(activity.getActivity().getCode().equals(TracingConstants.INBOUND_CORRESPONDANCE)
					|| activity.getActivity().getCode().equals(TracingConstants.OUTBOUND_CORRESPONDANCE));
			activities.add(dto);
		}
		theform.setActivityDtos(activities);

		theform.set_DATEFORMAT(user.getDateformat().getFormat());
		theform.set_TIMEFORMAT(user.getTimeformat().getFormat());
		theform.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
				.getTimezone()));

		// convert to complete date + time for formatting purposes
		Date completedate = DateUtils.convertToDate(iDTO.getCreatedate().toString() + " "
				+ iDTO.getCreatetime().toString(), TracingConstants.DB_DATETIMEFORMAT, null);
		theform.setCreatedate(completedate);
		theform.setCreatetime(completedate);

		// determine readonly or not, if current user is from a different
		// company,
		// readonly
		if(!user.getStation().getCompany().getCompanyCode_ID().equals(
				iDTO.getStationcreated().getCompany().getCompanyCode_ID())) {
			theform.setReadonly(1);
		}
		
		if (!UserPermissions.hasIncidentSavePermission(user, iDTO)) {
			theform.setReadonly(1);
			theform.setAllow_remark_update(1);
		}

		if (incident_ID != null) {
			@SuppressWarnings("rawtypes")
			ArrayList al = iBMO.getAssocReports(incident_ID);
			Incident_Assoc ia = null;
			if(al != null && al.size() > 0) {
				for(int i = 0; i < al.size(); i++) {
					ia = (Incident_Assoc) al.get(i);
					if(ia.getItemtype_ID() == TracingConstants.LOST_DELAY)
						theform.setLd_inc_ID(ia.getIncident_ID());
					if(ia.getItemtype_ID() == TracingConstants.DAMAGED_BAG)
						theform.setDamage_inc_ID(ia.getIncident_ID());
					if(ia.getItemtype_ID() == TracingConstants.MISSING_ARTICLES)
						theform.setMa_inc_ID(ia.getIncident_ID());
					if(i == 0)
						theform.setAssoc_ID(ia.getAssoc_ID());
				}
			}
		}

		Company_Specific_Variable csv = AdminUtils.getCompVariable(user.getCompanycode_ID());
		if (!doNotEmail) {
			theform.setEmail_customer(csv.isEmail_customer() ? 1 : 0);
		}
	}

	/**
	 * find incident for customer view only page
	 * 
	 * @param incident_ID
	 * @param name
	 * @param theform
	 * @return
	 */
	public boolean findIncidentForPVO(String incident_ID, String name, IncidentForm theform, HttpServletRequest request) {
		try {
			if(name == null || name.length() == 0 || incident_ID == null || incident_ID.length() == 0)
				return false;
			if(name.indexOf("%") >= 0 || incident_ID.indexOf("%") >= 0)
				return false;

			IncidentBMO iBMO = new IncidentBMO();
			Incident iDTO = iBMO.findIncidentForPVO(incident_ID, name);
			if(iDTO == null)
				return false;

			BeanUtils.copyProperties(theform, iDTO);
			// make sure separate stationcreate and stationassigned and
			// faultstation
			// into different station object
			// hibernate makes all stations into one object for some unknown
			// reason
			// todo later
			Station stationcreated = new Station();
			stationcreated.setStation_ID(iDTO.getStationcreated().getStation_ID());
			stationcreated.setStationcode(iDTO.getStationcreated().getStationcode());
			theform.setStationassigned(new Station());
			theform.setStationassigned_ID(iDTO.getStationassigned().getStation_ID());
			theform.getStationassigned().setCompany(iDTO.getStationassigned().getCompany());
			theform.setFaultstation(new Station());
			if(iDTO.getFaultstation() != null) {
				theform.setFaultstation_id(iDTO.getFaultstation().getStation_ID());
				theform.setFaultcompany_id(iDTO.getFaultstation().getCompany().getCompanyCode_ID());
			}

			ArrayList<Articles> al = new ArrayList<Articles>(iDTO.getArticles());
			if(al.size() <= 0)
				theform.getArticle(0);
			else
				theform.setArticlelist(al);

			Agent user = iDTO.getAgent();

			Item item = null;
			// incase there are no bags, put one there anyways
			if(theform.getItemlist().size() == 0) {

				item = (Item) theform.getItem(0, iDTO.getItemtype_ID());
				// item.setItem_ID(0); // for hibernate insert only
				item.setXdescelement_ID_1(TracingConstants.XDESC_TYPE_X);
				item.setXdescelement_ID_2(TracingConstants.XDESC_TYPE_X);
				item.setXdescelement_ID_3(TracingConstants.XDESC_TYPE_X);
				item.set_DATEFORMAT(user.getDateformat().getFormat());
				item.setCurrency_ID(user.getDefaultcurrency());
				item.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_OPEN));
				Item_Inventory ii = new Item_Inventory();
				ii.setItem(item);
				ii.set_DATEFORMAT(user.getDateformat().getFormat());
				ii.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
				ii.setEnteredDate(DateUtils.convertToGMTDate(new Date()));
				ii.setInvItemCurrency(user.getDefaultcurrency());
				item.getInventorylist().add(ii);
			}
			else {

				for(int i = 0; i < theform.getItemlist().size(); i++) {
					item = (Item) theform.getItemlist().get(i);
					item.set_DATEFORMAT(user.getDateformat().getFormat());
					if(item.getInventorylist().size() == 0) {
						Item_Inventory ii = new Item_Inventory();
						ii.setItem(item);
						item.getInventorylist().add(ii);
					}
				}
			}

			theform.setClaimchecklist(new ArrayList<Incident_Claimcheck>(iDTO.getClaimchecks()));

			theform.setPassengerlist(new ArrayList<Passenger>(iDTO.getPassengers()));
			
			List<IncidentActivityDTO> activities = new ArrayList<IncidentActivityDTO>();
			for (IncidentActivity activity : iDTO.getActivities()) {
				IncidentActivityDTO dto = new IncidentActivityDTO();
				dto.set_DATEFORMAT(user.getDateformat().getFormat());
				dto.set_TIMEFORMAT(user.getTimeformat().getFormat());
				dto.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
				dto.setId(activity.getId());
				dto.setCreateDate(activity.getCreateDate());
				dto.setPublishedDate(activity.getPublishedDate());
				if (activity.getLastStatus() != null) {
					dto.setStatusId(activity.getLastStatus().getStatus_ID());
				}
				dto.setAgent(activity.getAgent().getUsername());
				dto.setDescription(activity.getDescription());
				dto.setCustCommId(activity.getCustCommId());
				if (activity.getActivity() != null) {
					dto.setActivityCode(activity.getActivity().getCode());
				}

				if (activity.getDocument() != null) {
					dto.setFileName(activity.getDocument().getFileName());
				}

				boolean published = false;
				if (activity.getMessages() != null && activity.getMessages().size() > 0) {
					List<MessageDTO> mdtolist = DomainUtils.fromMessages(activity.getMessages(), user);
					if (mdtolist != null) {
						dto.setMessages(mdtolist);
					}
					for (OCMessage message : activity.getMessages()) {
						if (message.isPublish()) {
							published = true;
						}
					}
				}

				if (activity.getFiles() != null && activity.getFiles().size() > 0) {
					List<FileDTO> fdtolist = DomainUtils.fromFiles(activity.getFiles(), user);
					if (fdtolist != null) {
						dto.setFiles(fdtolist);
					}

					for (OCFile file : activity.getFiles()) {
						if (file.isPublish()) {
							published = true;
						}
					}
				}

				dto.setPublished(published);
				dto.setCustomerCommunication(activity.getActivity().getCode().equals(TracingConstants.ACTIVITY_CUSTOMER_COMMUNICATION)
						|| activity.getActivity().getCode().equals(TracingConstants.CREATE_SETTLEMENT_ACTIVITY));

				dto.setCorrespondence(activity.getActivity().getCode().equals(TracingConstants.INBOUND_CORRESPONDANCE)
						|| activity.getActivity().getCode().equals(TracingConstants.OUTBOUND_CORRESPONDANCE));
				activities.add(dto);
			}
			theform.setActivityDtos(activities);

			Passenger p = null;
			Address addr = null;
			for(int i = 0; i < theform.getPassengerlist().size(); i++) {
				p = theform.getPassenger(i);
				if(p.getMembership() == null) {
					p.setMembership(new AirlineMembership());
				}
				for(int j = 0; j < p.getAddresses().size(); j++) {
					addr = (Address) p.getAddress(j);
					addr.set_DATEFORMAT(TracingConstants.DB_DATEFORMAT);
				}
			}

			// get bdo delivery addresses
			ArrayList<BDO_Passenger> bp_list = new ArrayList<BDO_Passenger>();
			@SuppressWarnings("rawtypes")
			ArrayList bdolist = (ArrayList) iBMO.findBDOList(iDTO.getIncident_ID());
			BDO bdo = null;
			if(bdolist != null && bdolist.size() > 0) {
				for(int i = 0; i < bdolist.size(); i++) {
					bdo = (BDO) bdolist.get(i);
					bp_list.add(bdo.getPassenger(0));
				}
			}
			theform.setBdo_passengerlist(bp_list);

			// set datetime format
			theform.setRemarklist(new ArrayList<Remark>(iDTO.getRemarks()));
			Remark remark = null;
			for(int i = 0; i < theform.getRemarklist().size(); i++) {
				remark = (Remark) theform.getRemarklist().get(i);
				if(remark.getRemarktype() <= 0)
					remark.setRemarktype(TracingConstants.REMARK_REGULAR);

			}

			theform.setItinerarylist(new ArrayList<Itinerary>(iDTO.getItinerary()));

			Itinerary iti = null;

			for(int i = 0; i < theform.getItinerarylist().size(); i++) {
				iti = (Itinerary) theform.getItinerarylist().get(i);
				iti.set_DATEFORMAT(TracingConstants.DB_DATEFORMAT);
				iti.set_TIMEFORMAT(TracingConstants.DB_TIMEFORMAT);
			}

			theform.set_DATEFORMAT(TracingConstants.DB_DATEFORMAT);
			theform.set_TIMEFORMAT(TracingConstants.DB_TIMEFORMAT);
			theform.set_TIMEZONE(TimeZone.getTimeZone("Etc/GMT"));

			// convert to complete date + time for formatting purposes
			Date completedate = DateUtils.convertToDate(iDTO.getCreatedate().toString() + " "
					+ iDTO.getCreatetime().toString(), TracingConstants.DB_DATETIMEFORMAT, null);
			theform.setCreatedate(completedate);
			theform.setCreatetime(completedate);
			theform.setReadonly(1);

			// get associating reports
			@SuppressWarnings("unchecked")
			ArrayList<Incident_Assoc> al2 = iBMO.getAssocReports(incident_ID);
			Incident_Assoc ia = null;
			if(al2 != null && al2.size() > 0) {
				for(int i = 0; i < al2.size(); i++) {
					ia = (Incident_Assoc) al2.get(i);
					if(ia.getItemtype_ID() == TracingConstants.LOST_DELAY)
						theform.setLd_inc_ID(ia.getIncident_ID());
					if(ia.getItemtype_ID() == TracingConstants.DAMAGED_BAG)
						theform.setDamage_inc_ID(ia.getIncident_ID());
					if(ia.getItemtype_ID() == TracingConstants.MISSING_ARTICLES)
						theform.setMa_inc_ID(ia.getIncident_ID());
					if(i == 0)
						theform.setAssoc_ID(ia.getAssoc_ID());
				}
			}

			return true;
		}
		catch (Exception e) {
			logger.error("unable to find incident due to bean copyproperties error: " + e);
			return false;
		}
	}

	
	@SuppressWarnings("rawtypes")
	public ArrayList findIncident(SearchIncidentForm daform, Agent user, int rowsperpage, int currpage, boolean iscount) {
		return findIncident(daform, user, rowsperpage, currpage, iscount, false);
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList findIncident(SearchIncidentForm daform, Agent user, int rowsperpage, int currpage, boolean iscount, boolean dirtyRead) { 
		return findIncident(daform, user, rowsperpage, currpage, iscount, false, null);
	}
	/**
	 * 
	 * @param daform
	 * 
	 *            used to search incident by different criterias
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList findIncident(SearchIncidentForm daform, Agent user, int rowsperpage, int currpage, boolean iscount, boolean dirtyRead, String sort) {
		try {
			IncidentBMO iBMO = new IncidentBMO();
			SearchIncident_DTO siDTO = new SearchIncident_DTO();
			BeanUtils.copyProperties(siDTO, daform);
			siDTO.setIntelligentTagSearch(true);
			return (ArrayList) iBMO.findIncident(siDTO, user, rowsperpage, currpage, iscount, dirtyRead, sort);
		}
		catch (Exception e) {
			logger.error("unable to find incident due to bean copyproperties error: " + e);
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList customQuery(SearchIncidentForm daform, Agent user, int rowsperpage, int currpage, boolean iscount,
			String searchtype) {
		return customQuery(daform, user, rowsperpage, currpage, iscount, searchtype, false);
	}

	@SuppressWarnings("rawtypes")
	public ArrayList customQuery(SearchIncidentForm daform, Agent user, int rowsperpage, int currpage, boolean iscount,
			String searchtype, boolean dirtyRead) {
		daform.setIntelligentTagSearch(true);
		try {

			if(searchtype.equals("1") || searchtype.equals("2") || searchtype.equals("3") || searchtype.equals("4")) {
				IncidentBMO iBMO = new IncidentBMO();
				return (ArrayList) iBMO.customQuery(daform, user, rowsperpage, currpage, iscount, searchtype, dirtyRead);
			}
			else {
				OhdBMO oBMO = new OhdBMO();
				return (ArrayList) oBMO.customQuery(daform, user, rowsperpage, currpage, iscount, dirtyRead);
			}
		}
		catch (Exception e) {
			logger.error("unable to find report due to bean copyproperties error: " + e);
			return null;
		}
	}

	/**
	 * 
	 * @param oDTO
	 * @param theform
	 * @return @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public boolean insertLostAndFound(LostFoundIncidentForm theform, Agent user) {

		try {

			LostAndFoundIncident lost = new LostAndFoundIncident();

			if(theform.getReport_status().getStatus_ID() == TracingConstants.LOST_FOUND_CLOSED) {
				if(theform.getClose_date() == null) {
					lost.setClose_date(TracerDateTime.getGMTDate());
					lost.setClosing_agent(user);
				}
			}
			else {
				// open the report back up.
				lost.setClose_date(null);
				lost.setClosing_agent(null);
				lost.setDisposal_status(null); // no disposal status for open
												// lost/found
			}
			lost.setCreate_date(theform.getCreate_date());
			if(theform.getDispCloseDateTime() != null && theform.getDispDateFoundLost().length() > 0) {
				lost.setDateFoundLost(DateUtils.convertToDate(theform.getDispDateFoundLost(), user
						.getDateformat().getFormat(), theform.getFiling_agent().getCurrentlocale()));
			}
			else {
				lost.setDateFoundLost(lost.getCreate_date());
			}
			lost.setCreate_station(theform.getCreate_station());
			lost.setCustomer_address1(theform.getCustomer_address1());
			lost.setCustomer_address2(theform.getCustomer_address2());
			lost.setCustomer_city(theform.getCustomer_city());
			lost.setCustomer_email(theform.getCustomer_email());
			lost.setCustomer_province(theform.getCustomer_province());
			lost.setCustomer_state_ID(theform.getCustomer_state_ID());
			lost.setCustomer_countrycode_ID(theform.getCustomer_countrycode_ID());
			lost.setCustomer_tel(theform.getCustomer_tel());
			lost.setCustomer_zip(theform.getCustomer_zip());
			lost.setCustomer_firstname(theform.getCustomer_firstname());
			lost.setCustomer_mname(theform.getCustomer_mname());
			lost.setCustomer_lastname(theform.getCustomer_lastname());
			lost.setDisposal_status(theform.getDisposal_status());
			lost.setFile_ref_number(theform.getFile_ref_number());
			lost.setFiling_agent(theform.getFiling_agent());
			lost.setFinding_agent_name(theform.getFinding_agent_name());
			lost.setItem_description(theform.getItem_description());
			lost.setLocation(theform.getLocation());
			lost.setRemark(theform.getRemark());
			lost.setReport_status(theform.getReport_status());
			lost.setReport_type(theform.getReport_type());
			lost.setPhotos(new LinkedHashSet<LostAndFound_Photo>(theform.getPhotoList()));
			lost.setCategory_id(theform.getCategory_id());
			lost.setLanguageKey(theform.getLanguageKey());
			lost.setLanguageFreeFlow(theform.getLanguageFreeFlow());
			
			// prevent status id of 0
			if(lost.getDisposal_status() == null || lost.getDisposal_status().getStatus_ID() == 0)
				lost.setDisposal_status(null);

			for(Iterator<LostAndFound_Photo> i = lost.getPhotos().iterator(); i.hasNext();) {
				LostAndFound_Photo oo = (LostAndFound_Photo) i.next();
				oo.setLostandfoundincident(lost);
			}

			LostFoundBMO lBMO = new LostFoundBMO(); 

			boolean result = lBMO.insertLostFound(lost, user);
			if(result)
				theform.setFile_ref_number(lost.getFile_ref_number());
			return result;
		}
		catch (Exception e) {
			logger.error("unable to insert lost/found: " + e);
			return false;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public boolean insertOnHand(OHD oDTO, OnHandForm theform, ArrayList list, Agent mod_agent) {
		return insertOnHand(oDTO, theform, list, mod_agent, null, null, null);
	}

	/**
	 * 
	 * @param oDTO
	 * @param theform
	 * @return @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean insertOnHand(OHD oDTO, OnHandForm theform, ArrayList list, Agent mod_agent, String foundStation, String foundCompany, WorldTracerFile file) {
		try {
			boolean escape = false;
			boolean mass = false;

			if(list.size() > 0) {
				list.clear();
				mass = true;
			}
			String bag_tag = theform.getBagTagNumber();
			while (true) {
				String bagtag = "";
				if(bag_tag.indexOf(",") != -1) {
					int index = bag_tag.indexOf(",");
					bagtag = bag_tag.substring(0, index);
					bag_tag = bag_tag.substring(index + 1);
				}
				else {
					bagtag = bag_tag;
					escape = true;
				}

				if(mass)
					oDTO.setOhd_type(TracingConstants.MASS_OHD_TYPE);
				else
					oDTO.setOhd_type(TracingConstants.NOT_MASS_OHD_TYPE);

				oDTO.setClaimnum(TracerUtils.removeSpaces(bagtag).toUpperCase());
				
				oDTO.setModifiedBy(mod_agent.getUsername());
				oDTO.setModifiedDate(DateUtils.convertToGMTDate(new Date()));
				oDTO.setPosId(theform.getPosId());
				oDTO.setLateCheckInd(theform.getLateCheck());

				oDTO.setFoundtime(theform.getFoundTime());
				oDTO.setFounddate(theform.getFoundDate());
				
				if(oDTO.getFoundAtStation() == null || oDTO.getFoundAtStation().getStation_ID() == 0) {
					if (foundStation == null || foundCompany == null) {
						oDTO.setFoundAtStation(oDTO.getAgent().getStation());
					} else {
						Station locatedAt = StationBMO.getStationByCode(foundStation, foundCompany);
						oDTO.setFoundAtStation(locatedAt);
						oDTO.setHoldingStation(locatedAt);
					}
				}
				
				if (file != null) {
					oDTO.setWtFile(file);
				}
				

				if(oDTO.getHoldingStation() == null || oDTO.getHoldingStation().getStation_ID() == 0) {
					oDTO.setHoldingStation(oDTO.getAgent().getStation());
				}
				oDTO.setStorage_location(theform.getStorage_location());
				if(theform.getDispBagArriveDate() != null) {
					oDTO.setBagarrivedate(DateUtils.convertToDate(theform.getDispBagArriveDate(), theform.getAgent()
							.getDateformat().getFormat(), theform.getAgent().getCurrentlocale()));
				}
				if(theform.getDispWarehouseSentDate() != null) {
					oDTO.setWarehouseSentDate(DateUtils.convertToDate(theform.getDispWarehouseSentDate(), theform.getAgent()
							.getDateformat().getFormat(), theform.getAgent().getCurrentlocale()));
				}
				if(theform.getDispWarehouseReceivedDate() != null) {
					oDTO.setWarehouseReceivedDate(DateUtils.convertToDate(theform.getDispWarehouseReceivedDate(), theform.getAgent()
							.getDateformat().getFormat(), theform.getAgent().getCurrentlocale()));
				}

				if(oDTO.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_CLOSED) {
					oDTO.setClose_date(TracerDateTime.getGMTDate());
				}
				else {
					oDTO.setClose_date(null);
				}

				if(!mass) {
					oDTO.setDisposal_status(theform.getDisposal_status());
					if(oDTO.getDisposal_status() == null || oDTO.getDisposal_status().getStatus_ID() == 0)
						oDTO.setDisposal_status(null);
					oDTO.setFirstname(theform.getFirstname());
					oDTO.setMiddlename(theform.getMiddlename());
					oDTO.setLastname(theform.getLastname());

					oDTO.setStorage_location(theform.getStorage_location());
					oDTO.setColor(theform.getBagColor());
					oDTO.setType(theform.getBagType());
					oDTO.setXdescelement_ID_1(theform.getXDesc1());
					oDTO.setXdescelement_ID_2(theform.getXDesc2());
					oDTO.setXdescelement_ID_3(theform.getXDesc3());
					oDTO.setExternaldesc(theform.getExternaldesc());
					oDTO.setManufacturer_ID(theform.getManufacturer_ID());
					oDTO.setManufacturer_other(theform.getManufacturer_other());
					if(theform.getCompanycode_ID() == null || theform.getCompanycode_ID().equals(""))
						oDTO.setMembership(null);
					else {
						AirlineMembership amsDTO = new AirlineMembership();
						BeanUtils.copyProperties(amsDTO, theform);
						oDTO.setMembership(amsDTO);
					}

					oDTO.setRecord_locator(theform.getPnr());
					oDTO.setItems(new LinkedHashSet(theform.getItemlist()));
					oDTO.setRemarks(new LinkedHashSet(theform.getRemarklist()));
					oDTO.setPhotos(new LinkedHashSet(theform.getPhotoList()));
					oDTO.setTasks(new LinkedHashSet(theform.getTaskList()));
					oDTO.setControlLog(new LinkedHashSet(theform.getControlList()));
					oDTO.setItinerary(new LinkedHashSet(theform.getItinerarylist()));
					oDTO.setPassengers(new LinkedHashSet(theform.getPassengerList()));
					oDTO.setFaultstation_ID(theform.getFaultstation_ID());
					oDTO.setLoss_code(theform.getLoss_code());

					// set the ohd objects into each hibernate object
					for(Iterator i = oDTO.getItems().iterator(); i.hasNext();) {
						OHD_Inventory oo = (OHD_Inventory) i.next();
						oo.setOhd(oDTO);
					}
					for(Iterator i = oDTO.getRemarks().iterator(); i.hasNext();) {
						Remark oo = (Remark) i.next();
						oo.setOhd(oDTO);
					}
					for(Iterator i = oDTO.getPhotos().iterator(); i.hasNext();) {
						OHD_Photo oo = (OHD_Photo) i.next();
						oo.setOhd(oDTO);
					}
					for(Iterator i = oDTO.getTasks().iterator(); i.hasNext();) {
						Task oo = (Task) i.next();
						oo.setFile_ref_number(oDTO.getOHD_ID());
					}
					for(Iterator i = oDTO.getControlLog().iterator(); i.hasNext();) {
						ControlLog oo = (ControlLog) i.next();
						oo.setOhd(oDTO);
					}
					for(Iterator i = oDTO.getItinerary().iterator(); i.hasNext();) {
						OHD_Itinerary oo = (OHD_Itinerary) i.next();
						oo.setOhd(oDTO);
					}
					for(Iterator i = oDTO.getPassengers().iterator(); i.hasNext();) {
						OHD_Passenger oo = (OHD_Passenger) i.next();
						oo.setOhd(oDTO);
					}

				} else {
					oDTO.setXdescelement_ID_1(theform.getXDesc1());
					oDTO.setXdescelement_ID_2(theform.getXDesc2());
					oDTO.setXdescelement_ID_3(theform.getXDesc3());
				}
				
				OhdBMO oBMO = new OhdBMO();
				boolean hasPPULD=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PASSENGER_PICK_UP_LOSTDELAY, mod_agent);
				boolean hasPPUMA=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PASSENGER_PICK_UP_MISSING, mod_agent);
				boolean hasPPUDM=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PASSENGER_PICK_UP_DAMAGE, mod_agent);
				
				if(hasPPULD || hasPPUMA || hasPPUDM) {
					OHD oldohd=null;
					if(theform.getOhd_id()!=null && !theform.getOhd_id().isEmpty())
						oldohd=OhdBMO.getOHDByID(theform.getOhd_id(), null);

					if(oDTO.getDisposal_status()!=null && oDTO.getDisposal_status().getStatus_ID()!=TracingConstants.OHD_STATUS_OWNER_PICKED_UP &&
						oldohd!=null && oldohd.getDisposal_status()!=null && oldohd.getDisposal_status().getStatus_ID()==TracingConstants.OHD_STATUS_OWNER_PICKED_UP){
	
						Remark rem=new Remark();
						rem.setRemarktype(TracingConstants.REMARK_REGULAR);
						rem.setOhd(oDTO);
						rem.setAgent(mod_agent);
						rem.setCreatetime(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT).format(TracerDateTime.getGMTDate()));
						rem.setRemarktext(messages.getMessage(new Locale(mod_agent.getCurrentlocale()), "ohd.ppu.not.returned"));
						oDTO.getRemarks().add(rem);
						
						if(oDTO.getMatched_incident()!=null && !oDTO.getMatched_incident().isEmpty()){
							IncidentBMO iBMO=new IncidentBMO();
							Incident inc=IncidentBMO.getIncidentByID(oDTO.getMatched_incident(), null);
							if(inc!=null && inc.getItemlist()!=null){
								Remark rem2=null;
								int i=1;
								for(Item item:inc.getItemlist()){
									if(item.getOHD_ID()!=null && item.getOHD_ID().equals(oDTO.getOHD_ID())
											&& ((item.getItemtype_ID()==TracingConstants.LOST_DELAY && hasPPULD)
											|| (item.getItemtype_ID()==TracingConstants.MISSING_ARTICLES && hasPPUMA)
											|| (item.getItemtype_ID()==TracingConstants.DAMAGED_BAG && hasPPUDM))){
										rem2=new Remark();
										rem2.setRemarktype(TracingConstants.REMARK_REGULAR);
										rem2.setIncident(inc);
										rem2.setAgent(mod_agent);
										rem2.setCreatetime(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT).format(TracerDateTime.getGMTDate()));
										rem2.setRemarktext(messages.getMessage(new Locale(mod_agent.getCurrentlocale()), "ppu.item")+" "+i+" "+messages.getMessage(new Locale(mod_agent.getCurrentlocale()), "inc.ppu.not.returned"));
										inc.getRemarks().add(rem2);
									}
									i++;
								}
								iBMO.updateRemarksOnly(oDTO.getMatched_incident(), inc.getRemarks(), mod_agent);
							}
						}
					}
				}


				boolean result = false;
				// if it is readonly and update remark then only update remark
				if(theform.getReadonly() == 1) {
					if(theform.getAllow_remark_update() == 1) {
						result = oBMO.updateRemarksOnly(oDTO.getOHD_ID(), oDTO.getRemarks(), mod_agent);
					}
				}
				else {
					result = oBMO.insertOHD(oDTO, mod_agent);
				}
				


				if(!result) {
					return false;
				}
				else {
					list.add(oDTO.getOHD_ID());
				}
				if(escape)
					break;
				else {
					OHD oDTO2 = new OHD();
					oDTO2.setAgent(oDTO.getAgent());
					oDTO2.setStatus(oDTO.getStatus());
					oDTO = oDTO2;
				}
			}
			return true;
		}
		catch (Exception e) {
			logger.error("unable to insert onhand: " + e);
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	public List findOnHandBagsBySearchCriteria(SearchIncidentForm daform, Agent user, int rowsperpage, int currpage,
			boolean isCount, boolean notClosed) {
		return findOnHandBagsBySearchCriteria(daform, user, rowsperpage, currpage, isCount, notClosed, false, null);
		
	}
	@SuppressWarnings("rawtypes")
	public List findOnHandBagsBySearchCriteria(SearchIncidentForm daform, Agent user, int rowsperpage, int currpage,
			boolean isCount, boolean notClosed, boolean dirtyRead) {
		return findOnHandBagsBySearchCriteria(daform, user, rowsperpage, currpage, isCount, notClosed, dirtyRead, null);
		
	}
	@SuppressWarnings("rawtypes")
	public List findOnHandBagsBySearchCriteria(SearchIncidentForm daform, Agent user, int rowsperpage, int currpage,
			boolean isCount, boolean notClosed, boolean dirtyRead, String sort) {
		try {
			OhdBMO oBMO = new OhdBMO();
			Ohd_DTO oDTO = new Ohd_DTO();
			BeanUtils.copyProperties(oDTO, daform);
			oDTO.setOhd_ID(daform.getIncident_ID());
			oDTO.setOHD_categorytype_ID("" + daform.getCategory_ID());
			oDTO.setFoundCompany(daform.getCompanycreated_ID());
			oDTO.setHeldCompany(daform.getCompanycode_ID());
			oDTO.setIntelligentTagSearch(true);
			if(daform.getStationcreated_ID() > 0) {
				oDTO.setFoundStation(StationBMO.getStation(daform.getStationcreated_ID()).getStationcode());
			}

			if(daform.getStationassigned_ID() > 0) {
				oDTO.setHeldStation(StationBMO.getStation(daform.getStationassigned_ID()).getStationcode());
			}

			return oBMO.findOnHandBagsBySearchCriteria(oDTO, user, rowsperpage, currpage, isCount, notClosed, dirtyRead, sort);

		}
		catch (Exception e) {
			logger.error("unable to find on-hands due to bean copyproperties error: " + e);
			e.printStackTrace();
			return null;
		}
	}

	public OHD findOHDByID(String ohd_ID) {
		OhdBMO oBMO = new OhdBMO();
		return oBMO.findOHDByID(ohd_ID);
	}

	@SuppressWarnings("rawtypes")
	public ArrayList findLostFound(SearchLostFoundForm daform, Agent user, int rowsperpage, int currpage,
			boolean iscount) {
		try {
			LostFoundBMO lBMO = new LostFoundBMO();
			return (ArrayList) lBMO.findLostFound(daform, user, rowsperpage, currpage, iscount);
		}
		catch (Exception e) {
			logger.error("unable to find lostfound: " + e);
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean findLostFoundByID(String file_ref_number, LostFoundIncidentForm theform, Agent user, boolean found,
			HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		theform = new LostFoundIncidentForm();

		session.setAttribute("LostAndFoundForm", theform);
		LostFoundBMO lBMO = new LostFoundBMO();

		LostAndFoundIncident lost = lBMO.getLostAndFoundIncident(file_ref_number);
		if(lost == null)
			return false;

		BeanUtils.copyProperties(theform, lost);
		if(theform.getDisposal_status() == null)
			theform.setDisposal_status(new Status());
		theform.setPhotoList(new ArrayList(lost.getPhotos()));

		theform.set_DATEFORMAT(user.getDateformat().getFormat());
		theform.set_TIMEFORMAT(user.getTimeformat().getFormat());
		if(user.getDefaulttimezone() != null)
			theform.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
					.getTimezone()));
		return true;
	}

	public boolean populateLostLostFoundForm(String file_ref_number, LostFoundIncidentForm theform, Agent user,
			boolean found) throws Exception {

		LostFoundBMO lBMO = new LostFoundBMO();
		LostAndFoundIncident lost = lBMO.getLostAndFoundIncident(file_ref_number);
		if(lost == null)
			return false;

		BeanUtils.copyProperties(theform, lost);

		theform.set_DATEFORMAT(user.getDateformat().getFormat());
		theform.set_TIMEFORMAT(user.getTimeformat().getFormat());
		if(user.getDefaulttimezone() != null)
			theform.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
					.getTimezone()));

		return true;
	}

	public OHD findOnHand(String ohd_ID, OnHandForm theform, Agent user, HttpServletRequest request) throws Exception {
		OhdBMO oBMO = new OhdBMO();
		OHD iDTO = oBMO.findOHDByID(ohd_ID);
		if(iDTO == null)
			return null;
		theform.setOhd_id(iDTO.getOHD_ID());
		fillTheOhdForm(iDTO, theform, user, request);
		return iDTO;
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void fillTheOhdForm(OHD iDTO, OnHandForm theform, Agent user, HttpServletRequest request	)
			throws IllegalAccessException, InvocationTargetException {
		
		HttpSession session = request.getSession();
		theform.setAgent_initials(iDTO.getAgent().getUsername());
		theform.setBagColor(iDTO.getColor());
		theform.setBagTagNumber(iDTO.getClaimnum());
		theform.setBagType(iDTO.getType());
		theform.setXDesc1(iDTO.getXdescelement_ID_1());
		theform.setXDesc2(iDTO.getXdescelement_ID_2());
		theform.setLastname(iDTO.getLastname());
		theform.setFirstname(iDTO.getFirstname());
		theform.setMiddlename(iDTO.getMiddlename());
		theform.setXDesc3(iDTO.getXdescelement_ID_3());
		theform.setManufacturer_ID(iDTO.getManufacturer_ID());
		theform.setPnr(iDTO.getRecord_locator());
		theform.setStorage_location(iDTO.getStorage_location());
		theform.setDispBagArriveDate(null);
		theform.setBagarrivedate(iDTO.getBagarrivedate());
		theform.setClose_date(iDTO.getClose_date());
		theform.setOhd_type(iDTO.getOhd_type());
		theform.setWtFile(iDTO.getWtFile());
		theform.setEarlyBag(iDTO.isEarlyBag());
		theform.setWarehouseReceivedDate(iDTO.getWarehouseReceivedDate());
		theform.setWarehouseSentDate(iDTO.getWarehouseSentDate());
		theform.setDispWarehouseReceivedDate(DateUtils.formatDate(iDTO.getWarehouseReceivedDate(), iDTO.getAgent().getDateformat().getFormat(), 
				iDTO.getAgent().getCurrentlocale(), null ));
		theform.setDispWarehouseSentDate(DateUtils.formatDate(iDTO.getWarehouseSentDate(), iDTO.getAgent().getDateformat().getFormat(), 
				iDTO.getAgent().getCurrentlocale(), null ));
		
		theform.setModifiedAgent(iDTO.getModifiedBy());
		theform.setModifiedDate(iDTO.getModifiedDate());
		theform.setPosId(iDTO.getPosId());
		theform.setLateCheck(iDTO.getLateCheckInd());

		BeanUtils.copyProperties(theform, iDTO);
		if(iDTO.getMembership() == null) {
			theform.setCompany(null);
			theform.setMembershipnum("");
		}
		else
			BeanUtils.copyProperties(theform, iDTO.getMembership());

		theform.setFound_company(iDTO.getFoundAtStation().getCompany().getCompanyCode_ID());
		theform.setFound_station(iDTO.getFoundAtStation().getStationcode());

		theform.setHolding_company(iDTO.getHoldingStation().getCompany().getCompanyCode_ID());
		theform.setHolding_station(iDTO.getHoldingStation().getStationcode());

		theform.set_DATEFORMAT(user.getDateformat().getFormat());
		theform.set_TIMEFORMAT(user.getTimeformat().getFormat());
		if(user.getDefaulttimezone() != null)
			theform.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
					.getTimezone()));

		Date completedate = DateUtils.convertToDate(iDTO.getFounddate().toString() + " "
				+ iDTO.getFoundtime().toString(), TracingConstants.DB_DATETIMEFORMAT, null);
		theform.setFoundTime(completedate);
		theform.setFoundDate(completedate);
		theform.setStatus(iDTO.getStatus());
		
		// Setting OHD Status in Session
		
		String Intial_OHD_StatusVal = Integer.toString(iDTO.getStatus().getStatus_ID());
		session.setAttribute("Intial_OHD_StatusVal", Intial_OHD_StatusVal);
		session.setAttribute("Initial_OHD_DisStatusVal","0");
		if(iDTO.getDisposal_status()!=null){
			String Initial_OHD_DisStatusVal = Integer.toString(iDTO.getDisposal_status().getStatus_ID());
			session.setAttribute("Initial_OHD_DisStatusVal", Initial_OHD_DisStatusVal);
		}
		theform.setDisposal_status((iDTO.getDisposal_status() == null ? (new Status()) : iDTO.getDisposal_status()));
		theform.setPassengerList(new ArrayList(iDTO.getPassengers()));

		if(theform.getPassengerList().size() < 1) {
			theform.getPassenger(theform.getPassengerList().size());
		}

		// TODO: HERE
		if (iDTO.getPhotos() != null) {
			theform.setPhotoList(new ArrayList(iDTO.getPhotos()));
		} else {
			theform.setPhotoList(new ArrayList());
		}
		
		if (iDTO.getTasks() != null) {
			theform.setTaskList(new ArrayList(iDTO.getTasks()));
		} else {
			theform.setTaskList(new ArrayList());
		}
		
		if (iDTO.getControlLog() != null) {
			theform.setControlList(new ArrayList(iDTO.getControlLog()));
		} else {
			theform.setControlList(new ArrayList());
		}

		theform.setItemlist(new ArrayList(iDTO.getItems()));
		if(theform.getItemlist().size() < 1) {
			theform.getItem(theform.getItemlist().size());
		}

		// set datetime format
		theform.setRemarklist(new ArrayList(iDTO.getRemarks()));
		if(theform.getRemarklist().size() < 1) {
			// set new remark with current time and current agent
			Remark r = theform.getRemark(theform.getRemarklist().size());
			r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
			r.setAgent(user);
			r.set_DATEFORMAT(user.getDateformat().getFormat());
			r.set_TIMEFORMAT(user.getTimeformat().getFormat());
			r.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));

		}

		for(int i = 0; i < theform.getRemarklist().size(); i++) {
			Remark remark = (Remark) theform.getRemarklist().get(i);
			remark.set_DATEFORMAT(user.getDateformat().getFormat());
			remark.set_TIMEFORMAT(user.getTimeformat().getFormat());
			remark.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
					.getTimezone()));
			if(remark.getRemarktype() <= 0)
				remark.setRemarktype(TracingConstants.REMARK_REGULAR);
		}

		if (theform.getItinerarylist() != null) {
			theform.setItinerarylist(new ArrayList(iDTO.getItinerary()));
		} else {
			theform.setItinerarylist(new ArrayList());
		}

		if(theform.getItinerarylist().size() < 1) {
			theform.getItinerary(theform.getItinerarylist().size());
		}

		for(int i = 0; i < theform.getItinerarylist().size(); i++) {
			OHD_Itinerary iti = (OHD_Itinerary) theform.getItinerarylist().get(i);
			iti.set_DATEFORMAT(user.getDateformat().getFormat());
			iti.set_TIMEFORMAT(user.getTimeformat().getFormat());
		}

		// get forward log
		if (iDTO.getOHD_ID() != null) {
			OHD_Log ol = OHDUtils.getLastLog(iDTO.getOHD_ID());
			if (ol != null) {
				theform.setForwarded_agent(ol.getForwarding_agent().getUsername());
				theform.setForwarded_date(ol.getForward_time());
				theform.setForwarded_station(ol.getDestCompany() + " "
						+ ol.getDestStation());
			} else {
				theform.setForwarded_agent(null);
				theform.setForwarded_date(null);
				theform.setForwarded_station(null);
			}
		}
	}

	/**
	 * insert claims
	 * 
	 * @author Administrator
	 * 
	 *         create date - Aug 3, 2004
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean insertClaim(Claim cDTO, ClaimForm cform, HttpSession session, boolean isinterim, String incident_ID) {
		try {
			Agent user = (Agent) session.getAttribute("user");
			ClaimBMO cBMO = new ClaimBMO(); // init claim pojo or ejb
			Audit_Claim acDTO = null;

			/** ******* update claim amount for main claim ******** */
			BeanUtils.copyProperties(cDTO, cform);

			// AUDIT: copy into audit claim bean
			acDTO = new Audit_Claim();
			BeanUtils.copyProperties(acDTO, cform);

			// AUDIT: copy prorate into audit claim bean
			ClaimProrate cp = cDTO.getClaimprorate();
			if(cp != null) {
				Audit_ClaimProrate a_cp = new Audit_ClaimProrate();
				BeanUtils.copyProperties(a_cp, cp);
				Prorate_Itinerary pi = null;
				Audit_Prorate_Itinerary a_pi = null;
				ArrayList pilist = new ArrayList();
				if(cp.getProrate_itineraries() != null) {
					for(int i = 0; i < cp.getPi_list().size(); i++) {
						pi = (Prorate_Itinerary) cp.getPi_list().get(i);
						a_pi = new Audit_Prorate_Itinerary();
						BeanUtils.copyProperties(a_pi, pi);
						pi.setClaimprorate(cp);
						a_pi.setAudit_claimprorate(a_cp);
						pilist.add(a_pi);
					}
					a_cp.setProrate_itineraries(new LinkedHashSet(pilist));
				}
				acDTO.setAudit_claimprorate(a_cp);
			}

			acDTO.setModify_time(TracerDateTime.getGMTDate());
			acDTO.setModify_agent(user);
			boolean result = cBMO.insertClaim(cDTO, acDTO, incident_ID);
			cform.setClaim(cDTO);
			if(!result)
				return false;
			else
				return true;
		}
		catch (Exception e) {
			logger.error("unable to insert claim due to bean copyproperties error: " + e);
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean insertClaimProrate(Set<Claim> cDTO, ClaimProrateForm cpform, HttpSession session, String incident_ID) {
		try {
			ClaimProrate cp = new ClaimProrate();
			BeanUtils.copyProperties(cp, cpform);
			cp.setProrate_itineraries(new LinkedHashSet(cpform.getItinerarylist()));
			
			if (cDTO != null) {
				for (Claim c: cDTO) {
					c.setClaimprorate(cp);
				}
			}
			
			Prorate_Itinerary pi = null;
			if(cp.getProrate_itineraries() != null) {
				for(int i = 0; i < cp.getPi_list().size(); i++) {
					pi = (Prorate_Itinerary) cp.getPi_list().get(i);
					pi.setClaimprorate(cp);
				}
			}	
			return FileDAO.saveFile(cDTO.iterator().next().getFile(), false);
		}
		catch (Exception e) {
			logger.error("unable to insert claimprorate due to bean copyproperties error: " + e);
			e.printStackTrace();
			return false;
		}
	}


}
