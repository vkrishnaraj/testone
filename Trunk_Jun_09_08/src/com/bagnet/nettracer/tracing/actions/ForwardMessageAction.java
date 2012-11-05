package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
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

import com.bagnet.nettracer.integrations.events.BeornDTO;
import com.bagnet.nettracer.tracing.bmo.LossCodeBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Log_Itinerary;
import com.bagnet.nettracer.tracing.db.salvage.SalvageBox;
import com.bagnet.nettracer.tracing.db.salvage.SalvageItem;
import com.bagnet.nettracer.tracing.forms.ForwardMessageForm;
import com.bagnet.nettracer.tracing.forms.ForwardMessageForm.TagNumber;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

import edu.emory.mathcs.backport.java.util.Collections;

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
		stationList = TracerUtils.getStationList(companyCode);

		if (stationList == null || stationList.size() == 0) {
			theform.setDestStation("");
		}
		request.setAttribute("stationList", stationList);
		
		List codes = LossCodeBMO.getCompanyCodes(user.getStation().getCompany().getCompanyCode_ID(), TracingConstants.LOST_DELAY, false, user);
		//add to the loss codes
		request.setAttribute("losscodes", codes);
		
		List faultstationlist = null;
		faultstationlist = TracerUtils.getStationList(user.getStation().getCompany().getCompanyCode_ID());
		request.setAttribute("faultstationlist", faultstationlist);
		
		if(request.getParameter("delete_these_elements")!=null && request.getParameter("delete_these_elements").length()>0) {
			String deleteString=request.getParameter("delete_these_elements");
			String[] deleteList=deleteString.split(",");
			List indexList=new ArrayList();
			for (String delete : deleteList) {
				int ind=Integer.valueOf(delete.substring(delete.length()-1, delete.length()));
				indexList.add(ind);
			}
			if(indexList.size()>0){
				Collections.sort(indexList);
				for(int i=indexList.size()-1; i>=0;i--)
					theform.deleteTagNumber((Integer)indexList.get(i));
			}
		}

		if (request.getParameter("additinerary") != null) {
			OHD_Log_Itinerary itinerary = theform.getItinerary(theform.getForwarditinerarylist().size());
			itinerary.set_DATEFORMAT(user.getDateformat().getFormat());
			itinerary.set_TIMEFORMAT(user.getTimeformat().getFormat());
		} else if (request.getParameter("addbagitinerary") != null) {
			OHD_Itinerary itinerary = theform.getBagItinerary(theform.getBagitinerarylist().size());
			itinerary.set_DATEFORMAT(user.getDateformat().getFormat());
			itinerary.set_TIMEFORMAT(user.getTimeformat().getFormat());
		} else if (request.getParameter("addBags") != null) {
			int numberToAdd = TracerUtils.getNumberToAdd(request, "addBagNum");
			for (int i = 0; i < numberToAdd; ++i) {
				theform.getTaglist().add(theform.new TagNumber());
			}
		} else if (request.getParameter("save") != null && request.getParameter("save").length() > 0) {
			//Invalid or no destination is selected.
			if (theform.getDestStation() == null || theform.getDestStation().equals("")) {
				ActionMessage error = new ActionMessage("error.noStationcode");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				// Do the forward
				String ohd_id=null;
				List ohdidlist=new ArrayList();
				for(int i=0; i<theform.getTaglist().size(); i++){
					ohd_id=null;
					TagNumber TN=theform.getTagNumber(i);
					theform.setBag_tag(TN.getBagTagNumber());
					theform.setExpediteNumber(TN.getExpediteNumber());
					theform.setExpediteStick(TN.getExpediteSticker());
					 ohd_id = bs.forwardMessage(theform, user, messages);
					 if(ohd_id!=null){
						 ohdidlist.add(ohd_id);
					 }
				}
				if (ohdidlist != null) {
					request.setAttribute("ohdidlist", ohdidlist);
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
			else { //new Message
				theform.setTaglist(new ArrayList());
				
			}
		}

		if (theform.getTaglist().size()==0) {
			//setup 1 default OHD_itinerary..
			OHD_Itinerary itinerary = theform.getBagItinerary(0);
			itinerary.set_DATEFORMAT(user.getDateformat().getFormat());
			itinerary.set_TIMEFORMAT(user.getTimeformat().getFormat());

			OHD_Log_Itinerary logitinerary = theform.getItinerary(0);
			logitinerary.set_DATEFORMAT(user.getDateformat().getFormat());
			logitinerary.set_TIMEFORMAT(user.getTimeformat().getFormat());
			
			TagNumber TN=theform.getTagNumber(0);
		}

		//Allow modifications to the forward.
		return mapping.findForward(TracingConstants.ENTER_FORWARD_MESSAGE);
	}
}