package com.bagnet.nettracer.tracing.actions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.bagnet.nettracer.cronjob.bmo.WTQueueBmo;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.OHDRequest;
import com.bagnet.nettracer.tracing.db.OHD_Log_Itinerary;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log_Itinerary;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdGeneral;
import com.bagnet.nettracer.tracing.db.wtq.WtqSegment;
import com.bagnet.nettracer.tracing.forms.WorldTracerFWDForm;
import com.bagnet.nettracer.tracing.forms.WorldTracerFOHForm.FwdFormSegment;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
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

		if (!UserPermissions.hasPermission(
				TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_FWD, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));

		ActionMessages errors = new ActionMessages();

		// Obtain a handle on the resources direcory.
		MessageResources messages = MessageResources
				.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");

		BagService bs = new BagService();
		WorldTracerFWDForm theform = (WorldTracerFWDForm) form;

		// get faultstationlist
		List faultstationlist = null;
		faultstationlist = TracerUtils.getStationList(user.getCurrentlocale(),
				user.getCompanycode_ID());
		request.setAttribute("faultstationlist", faultstationlist);

		// the company specific codes..
		List codes = AdminUtils.getLocaleCompanyCodes(user.getStation()
				.getCompany().getCompanyCode_ID(), TracingConstants.LOST_DELAY,
				user.getCurrentlocale());
		// add to the loss codes
		request.setAttribute("losscodes", codes);

		OHD ohd_id = OHDUtils.getOHD(theform.getOhd_ID());
		if (ohd_id != null) {
			theform.setWt_id("");
			theform.setBagtag("");
			theform.setPassenger1("");
			theform.setPassenger2("");
			theform.setPassenger3("");
			OHD ohd = OHDUtils.getOHD(theform.getOhd_ID());
			if (ohd.getWt_id() != null)

				theform.setWt_id(ohd.getWt_id());
			if (ohd.getClaimnum() != null)

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
					if (i == 1)
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
			theform.addSegment();
		} else if (request.getParameter("save") != null) {
			if (theform.getDestinationStation() == null) {
				ActionMessage error = new ActionMessage("error.noStationcode");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				// Do the insert wt forward and wt_queue into database

				if (this.InsertWtFwd(theform, user)) {
					return (mapping.findForward(TracingConstants.FORWARD_WT_BAG_SUCCESS));
				}
				else {
					ActionMessage error = new ActionMessage("error.wt_fwd");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				}
			}

			return (mapping.findForward(TracingConstants.VIEW_WORLDTRACER_FWD));
		}
		else if (request.getParameter("deleteSegment") != null && request.getParameter("deleteSegment").trim().length() > 0) {
			int itin = Integer.parseInt(request.getParameter("deleteSegment"));
			theform.removeSegment(itin);
			return (mapping.findForward(TracingConstants.VIEW_WORLDTRACER_FWD));
		}

		return mapping.findForward(TracingConstants.VIEW_WORLDTRACER_FWD);
	}

	// clear
	private ActionForward clear(ActionMapping mapping, Agent user,
			HttpSession session, HttpServletRequest request) {
		WorldTracerFWDForm theform = new WorldTracerFWDForm();

		theform.addSegment();
		session.setAttribute("worldTracerFWDForm", theform);
		return (mapping.findForward(TracingConstants.VIEW_WORLDTRACER_FWD));
	}

	// Insert worldtracer forward to database
	private boolean InsertWtFwd(WorldTracerFWDForm theform, Agent user) {
		WtqFwdGeneral fwd = new WtqFwdGeneral();
		fwd.setAgent(user);
		fwd.setCreatedate(TracerDateTime.getGMTDate());
		fwd.setMatchingAhl(theform.getMatchingAhl());
		fwd.setSupInfo(theform.getSupplementary_information());
		fwd.setFwdDestinationAirline(theform.getDestinationAirline());
		fwd.setFwdDestinationStation(theform.getDestinationStation());
		for(FwdFormSegment seg : theform.getItinerarylist()) {
			WtqSegment wtqSeg = new WtqSegment();
			wtqSeg.setAirline(seg.getAirline());
			SimpleDateFormat sdf = new SimpleDateFormat(user.getDateformat().getFormat());
			try {
				wtqSeg.setDepartdate(sdf.parse(seg.getDepartdate()));
			} catch (ParseException e) {
				return false;
			}
			wtqSeg.setFlightnum(seg.getFlightnum());
			wtqSeg.setLegfrom(seg.getLegfrom());
			wtqSeg.setLegto(seg.getLegto());
			fwd.getItinerary().add(wtqSeg);
		}
		fwd.setFwdExpediteNum(theform.getExpeditenum());
		fwd.setLossCode(theform.getLoss_code());
		fwd.setLossComments(theform.getReason_for_loss());
		if(theform.getPassenger1() != null && theform.getPassenger1().trim().length() > 0) {
			fwd.getFwdName().add(theform.getPassenger1().trim());
		}
		if(theform.getPassenger2() != null && theform.getPassenger2().trim().length() > 0) {
			fwd.getFwdName().add(theform.getPassenger2().trim());
		}
		if(theform.getPassenger3() != null && theform.getPassenger3().trim().length() > 0) {
			fwd.getFwdName().add(theform.getPassenger3().trim());
		}
		try {
			WorldTracerQueueUtils.createOrReplaceQueue(fwd);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}