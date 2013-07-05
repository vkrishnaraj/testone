package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
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
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHDRequest;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.OHD_Log_Itinerary;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.forms.ForwardOnHandForm;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * Implementation of <strong>Action </strong> that is responsible for handling
 * forward on-hand bags task. It would forward the bag to the desired location
 * or LZ.
 * 
 * @author Ankur Gupta
 */
public class ForwardOnHandAction extends Action {
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
		ForwardOnHandForm theform = (ForwardOnHandForm) form;
		
		//this field is what contains the expedite bagtag which is a freeflow. 
		//must strip invalid characters to prevent reflective xss attacks
		if(theform.getOhdList() != null){
			for(LabelValueBean ohdBean: theform.getOhdList()){
				if(ohdBean != null && ohdBean.getValue() != null){
					ohdBean.setValue(ohdBean.getValue().replaceAll("['\"/<>%()]",""));
				}
			}
		}
		
		String companyCode = null;
		if (theform.getCompanyCode() != null && !theform.getCompanyCode().equals("") && CompanyBMO.getCompany(theform.getCompanyCode())!=null) companyCode = theform
				.getCompanyCode();
		else companyCode = user.getCompanycode_ID();

		theform.setCompanyCode(companyCode);
		
		List stationList = null;
		if (theform.getCompanyCode() != null && !theform.getCompanyCode().equals("") && CompanyBMO.getCompany(theform.getCompanyCode())!=null) stationList = TracerUtils
				.getStationList(theform.getCompanyCode());
		else stationList = TracerUtils
				.getStationList(user.getCompanycode_ID());

		if (stationList == null || stationList.size() == 0) {
			theform.setDestStation("");
		}
		request.setAttribute("stationList", stationList);

		if (request.getParameter("showForward") != null) {
			String forward_ID = request.getParameter("forward_id");

			//Retrieve the on hand log based on the forward id
			OHD_Log log = OHDUtils.getForwardLog(forward_ID);

			theform.setOhd_ID(log.getOhd().getOHD_ID());
			if (log.getOhd_request_id() > 0) theform.setBag_request_id("" + log.getOhd_request_id());
			theform.setExpediteNumber(log.getExpeditenum());

			//Update the itinerary list in the form
			List itineraryList = new ArrayList(log.getItinerary());
			if (itineraryList != null) {
				for (Iterator i = itineraryList.iterator(); i.hasNext();) {
					OHD_Log_Itinerary itinerary = (OHD_Log_Itinerary) i.next();
					itinerary.set_DATEFORMAT(user.getDateformat().getFormat());
					itinerary.set_TIMEFORMAT(user.getTimeformat().getFormat());
				}
				theform.setItinerarylist(itineraryList);
			}
			//the message that accompanies the forward
			theform.setMessage(log.getMessage());

			return mapping.findForward(TracingConstants.VIEW_FORWARD_DETAILS);
		}
		//Add itinerary item is clicked.
		else if (request.getParameter("additinerary") != null) {
			OHD_Log_Itinerary itinerary = theform.getItinerary(theform.getItinerarylist().size());
			itinerary.set_DATEFORMAT(user.getDateformat().getFormat());
			itinerary.set_TIMEFORMAT(user.getTimeformat().getFormat());
			return mapping.findForward(TracingConstants.ENTER_FORWARD_ON_HAND);
		} else if (request.getParameter("save") != null) {
			//Invalid or no destination is selected.
			if (theform.getDestStation() == null || theform.getDestStation().equals("") || StationBMO.getStation(theform.getDestStation())==null) {
				ActionMessage error = new ActionMessage("error.noStationcode");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				// Do the forward
				if (((theform.getOhd_ID()!=null && theform.getOhd_ID().length()>0 && OHDUtils.getOHD(String.valueOf(theform.getOhd_ID()))!=null) || (theform.getOhdList()!=null && theform.getOhdList().size()>0) 
						|| (theform.getBag_request_id()!=null && OHDUtils.getOHD(String.valueOf(theform.getBag_request_id()))!=null)) && bs.forwardOnHand(theform, user, messages)) {
					return (mapping.findForward(TracingConstants.FORWARD_ON_HAND_SUCCESS));
				} else {
					ActionMessage error = new ActionMessage("error.noForwardcode");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				}
			}
			return (mapping.findForward(TracingConstants.ENTER_FORWARD_ON_HAND));
		} else {
			boolean deleteBagItin = false;

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
					}//delete bag item is clicked.
				}
			}
			if (deleteBagItin) {
				List itnList = theform.getItinerarylist();
				if (itnList != null) itnList.remove(Integer.parseInt(index));

				return (mapping.findForward(TracingConstants.ENTER_FORWARD_ON_HAND));
			}//delete bag-item
		}

		if (request.getParameter("log_ID") != null) {
			String log_id = request.getParameter("log_ID");
		}
		String ohd_ID = theform.getOhd_ID();
		
		if(ohd_ID==null || ohd_ID.length()==0){
			request.getParameter("ohd_ID");
			if(ohd_ID!=null && OHDUtils.getOHD(ohd_ID)==null)
				ohd_ID=null;
		}

		if (request.getParameter("batch") != null) {
			ohd_ID = request.getParameter("batch_id");
		}
		
		ArrayList<LabelValueBean> oList = new ArrayList<LabelValueBean>();
		

		if (ohd_ID != null) {
			String[] ohdArray = ohd_ID.split(",");
			
			for (String o: ohdArray) {
				if(OHDUtils.getOHD(o)!=null)
					oList.add(new LabelValueBean(o, ""));
			}		
			//reset the forward form
			theform = new ForwardOnHandForm();
			List list = new ArrayList();
			OHD_Log_Itinerary itinerary = theform.getItinerary(0);
			itinerary.set_DATEFORMAT(user.getDateformat().getFormat());
			itinerary.set_TIMEFORMAT(user.getTimeformat().getFormat());
			list.add(itinerary);
			theform.setOhd_ID(ohd_ID);
			theform.setOhdList(oList);
			if(companyCode!=null && companyCode.length()>0){
				theform.setCompanyCode(companyCode);
			} else {
				theform.setCompanyCode(user.getCompanycode_ID());
			}
			session.setAttribute("forwardOnHandForm", theform);
		}
		String request_ID = request.getParameter("request_ID");
		if (request_ID != null) {
			//set the company code and destination station as well.
			OHDRequest oReq = OHDUtils.getRequest(request_ID);
			theform.setBag_request_id(request_ID);
			theform.setDestStation("" + oReq.getRequestForStation().getStation_ID());
			theform.setCompanyCode(oReq.getRequestForStation().getCompany().getCompanyCode_ID());
		}

		if (request.getParameter("lz") != null) {
			//from lz..update the
			List stations = new ArrayList();
			stations.add(StationBMO.getStation(user.getStation().getCompany().getVariable().getOhd_lz()));
				
			if (stations == null || stations.size() == 0) {
				return (mapping.findForward(TracingConstants.FORWARD_ERROR));
			} else {
				theform.setCompanyCode(user.getStation().getCompany().getCompanyCode_ID());
				theform.setDestStation("" + ((Station) stations.get(0)).getStation_ID());
				theform.setLz(1);
			}
		}

		if (theform.getItinerarylist().size() == 0) {
			OHD_Log_Itinerary itinerary = theform.getItinerary(0);
			itinerary.set_DATEFORMAT(user.getDateformat().getFormat());
			itinerary.set_TIMEFORMAT(user.getTimeformat().getFormat());
		}
		
		//Allow modifications to the forward.
		return mapping.findForward(TracingConstants.ENTER_FORWARD_ON_HAND);
	}
}