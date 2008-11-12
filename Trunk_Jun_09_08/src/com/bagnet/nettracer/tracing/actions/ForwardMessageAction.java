package com.bagnet.nettracer.tracing.actions;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;

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

import com.bagnet.nettracer.integrations.events.BeornDTO;
import com.bagnet.nettracer.tracing.bmo.LossCodeBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Log_Itinerary;
import com.bagnet.nettracer.tracing.forms.ForwardMessageForm;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * Implementation of <strong>Action </strong> that is responsible for handling
 * forward on-hand bags task. It would forward the bag to the desired location
 * or LZ.
 * 
 * @author Ankur Gupta
 */
public class ForwardMessageAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		// check session and user validity
		TracerUtils.checkSession(session);
		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		ActionMessages errors = new ActionMessages();

		//Obtain a handle on the resources direcory.
		MessageResources messages = MessageResources
				.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");

		BagService bs = new BagService();
		ForwardMessageForm theform = (ForwardMessageForm) form;

		List stationList = null;

		String companyCode = null;
		if (theform.getCompanyCode() != null && !theform.getCompanyCode().equals("")) companyCode = theform
				.getCompanyCode();
		else companyCode = user.getCompanycode_ID();

		theform.setCompanyCode(companyCode);
		stationList = TracerUtils.getStationList(user.getCurrentlocale(), companyCode);

		if (stationList == null || stationList.size() == 0) {
			theform.setDestStation("");
		}
		request.setAttribute("stationList", stationList);
		
		List codes = LossCodeBMO.getLocaleCompanyCodes(user.getStation().getCompany().getCompanyCode_ID(), TracingConstants.LOST_DELAY, user
				.getCurrentlocale(), false, user);
		//add to the loss codes
		request.setAttribute("losscodes", codes);
		
		List faultstationlist = null;
		faultstationlist = TracerUtils.getStationList(user.getCurrentlocale(), user.getStation().getCompany().getCompanyCode_ID());
		request.setAttribute("faultstationlist", faultstationlist);
		
		

		if (request.getParameter("additinerary") != null) {
			OHD_Log_Itinerary itinerary = theform.getItinerary(theform.getForwarditinerarylist().size());
			itinerary.set_DATEFORMAT(user.getDateformat().getFormat());
			itinerary.set_TIMEFORMAT(user.getTimeformat().getFormat());
		} else if (request.getParameter("addbagitinerary") != null) {
			OHD_Itinerary itinerary = theform.getBagItinerary(theform.getBagitinerarylist().size());
			itinerary.set_DATEFORMAT(user.getDateformat().getFormat());
			itinerary.set_TIMEFORMAT(user.getTimeformat().getFormat());
		} else if (request.getParameter("save") != null) {
			//Invalid or no destination is selected.
			if (theform.getDestStation() == null || theform.getDestStation().equals("")) {
				ActionMessage error = new ActionMessage("error.noStationcode");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				// Do the forward
				String ohd_id = bs.forwardMessage(theform, user, messages);
				if (ohd_id != null) {
					request.setAttribute("ohd_ID", ohd_id);
					// remove forward form from session
					session.removeAttribute("forwardMessageForm");
					return (mapping.findForward(TracingConstants.FORWARD_ON_HAND_SUCCESS));
				}
			}

			return (mapping.findForward(TracingConstants.ENTER_FORWARD_MESSAGE));
		} else {
			boolean deleteBagItin = false;

			boolean deleteforwardItin = false;

			//This technique is employed to get the []['nd] reference
			String index = "0";
			Enumeration e = request.getParameterNames();
			while (e.hasMoreElements()) {
				String parameter = (String) e.nextElement();
				if (parameter.indexOf("[") != -1) {
					index = parameter.substring(parameter.indexOf("[") + 1, parameter.indexOf("]"));
					if (parameter.indexOf("deleteBag") != -1) {
						deleteBagItin = true;
						break;
					}//delete bag itin is clicked.
					else if (parameter.indexOf("deleteForward") != -1) {
						deleteforwardItin = true;
						break;
					}//delete forward itin is clicked.
				}
			}
			if (deleteBagItin) {
				List itnList = theform.getBagitinerarylist();
				if (itnList != null) itnList.remove(Integer.parseInt(index));

				return (mapping.findForward(TracingConstants.ENTER_FORWARD_MESSAGE));
			}//delete bag-itin
			else if (deleteforwardItin) {
				List itnList = theform.getForwarditinerarylist();
				if (itnList != null) itnList.remove(Integer.parseInt(index));

				return (mapping.findForward(TracingConstants.ENTER_FORWARD_MESSAGE));
			}//delete forward
		}

		if (theform.getExpediteNumber() == null || theform.getExpediteNumber().length() < 1) {
			//setup 1 default OHD_itinerary..
			OHD_Itinerary itinerary = theform.getBagItinerary(0);
			itinerary.set_DATEFORMAT(user.getDateformat().getFormat());
			itinerary.set_TIMEFORMAT(user.getTimeformat().getFormat());

			OHD_Log_Itinerary logitinerary = theform.getItinerary(0);
			logitinerary.set_DATEFORMAT(user.getDateformat().getFormat());
			logitinerary.set_TIMEFORMAT(user.getTimeformat().getFormat());
		}

		//Allow modifications to the forward.
		return mapping.findForward(TracingConstants.ENTER_FORWARD_MESSAGE);
	}
}