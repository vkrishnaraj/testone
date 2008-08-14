package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.OHDRequest;
import com.bagnet.nettracer.tracing.db.OHD_Log_Itinerary;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log_Itinerary;
import com.bagnet.nettracer.tracing.db.WT_Queue;
import com.bagnet.nettracer.tracing.forms.WorldTracerFWDForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.WTOHD;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;
import com.bagnet.nettracer.wt.WorldTracerUtils;

import org.apache.commons.httpclient.HttpClient;

/**
 * Implementation of <strong>Action </strong> that is responsible for handling
 * forward on-hand bags task. It would forward the bag to the desired location
 * or LZ.
 * 
 * @author Ankur Gupta
 */
public class WorldTracerFWDAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();

		// check session and user validity
		TracerUtils.checkSession(session);
		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_FWD, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		
		ActionMessages errors = new ActionMessages();

		// Obtain a handle on the resources direcory.
		MessageResources messages = MessageResources
				.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");

		BagService bs = new BagService();
		WorldTracerFWDForm theform = (WorldTracerFWDForm) form;

		String companyCode = null;
		if (theform.getCompanyCode() != null
				&& !theform.getCompanyCode().equals(""))
			companyCode = theform.getCompanyCode();
		else
			companyCode = user.getCompanycode_ID();

		theform.setCompanyCode(companyCode);

		// get stationList
		List stationList = null;
		if (theform.getCompanyCode() != null
				&& !theform.getCompanyCode().equals(""))
			stationList = TracerUtils.getStationList(user.getCurrentlocale(),
					theform.getCompanyCode());
		else
			stationList = TracerUtils.getStationList(user.getCurrentlocale(),
					user.getCompanycode_ID());

		request.setAttribute("stationList", stationList);

		// get faultstationlist
		List faultstationlist = null;
		if (theform.getCompanyCode() != null
				&& !theform.getCompanyCode().equals(""))
			faultstationlist = TracerUtils.getStationList(user
					.getCurrentlocale(), theform.getCompanyCode());
		else
			faultstationlist = TracerUtils.getStationList(user
					.getCurrentlocale(), user.getCompanycode_ID());
		request.setAttribute("faultstationlist", faultstationlist);

		// the company specific codes..
		List codes = AdminUtils.getLocaleCompanyCodes(user.getStation()
				.getCompany().getCompanyCode_ID(), TracingConstants.LOST_DELAY,
				user.getCurrentlocale());
		// add to the loss codes
		request.setAttribute("losscodes", codes);
		/*
		 * if (request.getParameter("showForward") != null) { String forward_ID =
		 * request.getParameter("forward_id");
		 * 
		 * //Retrieve the on hand log based on the forward id WT_FWD_Log log =
		 * OHDUtils.getForwardLog(forward_ID);
		 * 
		 * theform.setOhd_ID(log.getOhd().getOHD_ID()); if
		 * (log.getOhd_request_id() > 0) theform.setBag_request_id("" +
		 * log.getOhd_request_id());
		 * theform.setExpediteNumber(log.getExpeditenum());
		 * 
		 * //Update the itinerary list in the form List itineraryList = new
		 * ArrayList(log.getItinerary()); if (itineraryList != null) { for
		 * (Iterator i = itineraryList.iterator(); i.hasNext();) {
		 * WT_FWD_Log_Itinerary itinerary = (WT_FWD_Log_Itinerary) i.next();
		 * itinerary.set_DATEFORMAT(user.getDateformat().getFormat());
		 * itinerary.set_TIMEFORMAT(user.getTimeformat().getFormat()); }
		 * theform.setItinerarylist(itineraryList); } //the message that
		 * accompanies the forward theform.setMessage(log.getMessage());
		 * 
		 * return mapping.findForward(TracingConstants.VIEW_FORWARD_DETAILS); }
		 */
		OHD ohd_id = OHDUtils.getOHD(theform.getOhd_ID());
		if (ohd_id == null) {
			theform.setWt_id("");
			theform.setBagtag("");
			theform.setPassenger1("");
			theform.setPassenger2("");
			theform.setPassenger3("");
		} else {
			theform.setWt_id("");
			theform.setBagtag("");
			theform.setPassenger1("");
			theform.setPassenger2("");
			theform.setPassenger3("");
			OHD ohd = OHDUtils.getOHD(theform.getOhd_ID());
			if(ohd.getWt_id()!=null)
			
			theform.setWt_id(ohd.getWt_id());
			if(ohd.getClaimnum()!=null)
			
			theform.setBagtag(ohd.getClaimnum());
			Set passengers = ohd.getPassengers();

			OHD_Passenger op = null;

			for (Iterator it = passengers.iterator(); it.hasNext();) {
				// if (it.hasNext()) {
				op = (OHD_Passenger) it.next();
				int i = 0;
				if (op != null && op.getLastname() != null) {
					if (i == 0)
						theform.setPassenger1(op.getLastname());
					if (i == 1 )
						theform.setPassenger2(op.getLastname());
					if (i == 2)
						theform.setPassenger3(op.getLastname());
					i++;
					if (i == 3)
						break;
				}
				// }
			}

		}

		if (theform == null || request.getParameter("clear") != null) {
			this.clear(mapping, user, session, request);
		}
		// Add itinerary item is clicked.
		if (request.getParameter("additinerary") != null) {
			WT_FWD_Log_Itinerary itinerary = theform.getItinerary(theform
					.getItinerarylist().size());
			itinerary.set_DATEFORMAT(user.getDateformat().getFormat());
			itinerary.set_TIMEFORMAT(user.getTimeformat().getFormat());
		} else if (request.getParameter("save") != null) {
			// String ohd_id = theform.getOhd_ID();
			if (ohd_id == null) {
				ActionMessage error = new ActionMessage("error.match_noonhand");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return (mapping
						.findForward(TracingConstants.VIEW_WORLDTRACER_FWD));
			} else {

				// Invalid or no destination is selected.

				if (theform.getDestStation() == null) {
					ActionMessage error = new ActionMessage(
							"error.noStationcode");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				} else {
					// Do the insert wt forward and wt_queue into database

					if (this.InsertWtFwd(theform, user) ){
						return (mapping
								.findForward(TracingConstants.FORWARD_WT_BAG_SUCCESS));
					}
					// this.InsertTest(theform);
			
					// Do the forward
					// WTOHD wtfwd = new WTOHD();
					// HttpClient client = WorldTracerUtils.connectWT(WorldTracerUtils.wt_suffix_airline + "/");
					// wtfwd.forwardOHD(client, theform, user);
					/*
					 * 
					 * if (bs.forwardOnHand(theform, user, messages)) { return
					 * (mapping.findForward(TracingConstants.FORWARD_ON_HAND_SUCCESS)); }
					 */
				}
			}
			return (mapping.findForward(TracingConstants.VIEW_WORLDTRACER_FWD));
		}

		else {
			boolean deleteBagItin = false;

			// This technique is employed to get the []['nd] reference
			String index = "0";
			Enumeration e = request.getParameterNames();
			while (e.hasMoreElements()) {
				String parameter = (String) e.nextElement();
				if (parameter.indexOf("[") != -1) {
					index = parameter.substring(parameter.indexOf("[") + 1,
							parameter.indexOf("]"));
					if (parameter.indexOf("deleteBag") != -1) {
						deleteBagItin = true;
						break;
					}// delete bag item is clicked.
				}
			}
			if (deleteBagItin) {
				List itnList = theform.getItinerarylist();
				if (itnList != null)
					itnList.remove(Integer.parseInt(index));

				return (mapping
						.findForward(TracingConstants.VIEW_WORLDTRACER_FWD));
			}// delete bag-item
		}

		if (request.getParameter("log_ID") != null) {
			String log_id = request.getParameter("log_ID");
		}
		// String ohd_ID = request.getParameter("ohd_ID");

		// reset the forward form
		WT_FWD_Log_Itinerary itinerary = theform.getItinerary(0);
		itinerary.set_DATEFORMAT(user.getDateformat().getFormat());
		itinerary.set_TIMEFORMAT(user.getTimeformat().getFormat());
		// theform.setCompanyCode(user.getCompanycode_ID());

		// Allow modifications to the forward.
		return mapping.findForward(TracingConstants.VIEW_WORLDTRACER_FWD);
	}

	// clear
	private ActionForward clear(ActionMapping mapping, Agent user,
			HttpSession session, HttpServletRequest request) {
		WorldTracerFWDForm theform = new WorldTracerFWDForm();

		WT_FWD_Log_Itinerary itinerary = theform.getItinerary(0);
		itinerary.set_DATEFORMAT(user.getDateformat().getFormat());
		itinerary.set_TIMEFORMAT(user.getTimeformat().getFormat());
		theform.setCompanyCode(user.getCompanycode_ID());
		session.setAttribute("worldTracerFWDForm", theform);
		return (mapping.findForward(TracingConstants.VIEW_WORLDTRACER_FWD));

	}



	// Insert worldtracer forward to database
	private boolean InsertWtFwd(WorldTracerFWDForm theform, Agent user) {
		WT_FWD_Log wtfwdlog = new WT_FWD_Log();
		wtfwdlog.setOhd(OHDUtils.getOHD(theform.getOhd_ID()));
		wtfwdlog.setPlace_in_file(theform.getPlaced_in_file());
		wtfwdlog.setBagtag(theform.getBagtag());
		System.out.println(theform.getBagtag());
		wtfwdlog.setEbagtag(theform.getEbagtag());
		System.out.println(theform.getEbagtag());
		wtfwdlog.setExpeditenum(theform.getExpeditenum());
		wtfwdlog.setPassenger1(theform.getPassenger1());
		wtfwdlog.setPassenger2(theform.getPassenger2());
		wtfwdlog.setPassenger3(theform.getPassenger3());
		wtfwdlog.setFwd_station_id(Integer.parseInt(theform.getDestStation()));
		wtfwdlog.setItinerary(new HashSet(theform.getItinerarylist()));
		WT_FWD_Log_Itinerary wtfwdlogi = null;
		if (wtfwdlog.getItinerary() != null) {
			for (Iterator i = wtfwdlog.getItinerary().iterator(); i.hasNext();) {
				wtfwdlogi = (WT_FWD_Log_Itinerary) i.next();
				wtfwdlogi.setLog(wtfwdlog);
			}
		}
		wtfwdlog.setLoss_code(theform.getLoss_code());
		wtfwdlog.setFault_station(theform.getFault_station());
		wtfwdlog.setFault_terminal(theform.getFault_terminal());
		wtfwdlog.setReason_for_loss(theform.getReason_for_loss());
		wtfwdlog.setSupplementary_information(theform
				.getSupplementary_information());
		wtfwdlog.setTeletype_address1(theform.getTeletype_address1());
		wtfwdlog.setTeletype_address2(theform.getTeletype_address2());
		wtfwdlog.setTeletype_address3(theform.getTeletype_address3());
		wtfwdlog.setTeletype_address4(theform.getTeletype_address4());
		wtfwdlog.setForwarding_agent(user);
		wtfwdlog.setForward_date(TracerDateTime.getGMTDate());

		wtfwdlog.setFwd_status(TracingConstants.WTFWD_LOG_NOT_RECEIVED);

		HibernateUtils.save(wtfwdlog);
		return true;

	}
}