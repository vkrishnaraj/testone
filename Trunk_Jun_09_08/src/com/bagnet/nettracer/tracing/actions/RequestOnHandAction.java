/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.text.SimpleDateFormat;
import java.util.Locale;
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

import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.RequestOhdBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHDRequest;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.forms.RequestOnHandForm;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.MessageUtils;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * Implementation of <strong>Action </strong> that is responsible for managing
 * all the tasks related to requesting an on-hand baggage. Allows for denial,
 * view, creation of a request message.
 * 
 * @author Ankur Gupta
 */
public class RequestOnHandAction extends Action {
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

		//		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) +
		// ".do",user))
		//			return (mapping.findForward(TracingConstants.LOGON));

		MessageResources messages = MessageResources
				.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");

		RequestOnHandForm theform = (RequestOnHandForm) form;

		//the agent has selected to deny the request
		if (request.getParameter("deny") != null) {
			String request_ID = request.getParameter("request_ID");
			if (request.getParameter("done_deny") != null) {
				String reason = request.getParameter("denialReason");
				OHDRequest oReq = OHDUtils.getRequest(request_ID);
				if (oReq != null) {
					
					OhdBMO obmo = new OhdBMO();
					OHD ohd = oReq.getOhd();
					
					oReq.setDenied(1);
					oReq.setDenialReason(reason);
					oReq.setDenialDate(TracerDateTime.getGMTDate());
					Status s = new Status();
					s.setStatus_ID(TracingConstants.OHD_REQUEST_STATUS_DENIED);
					oReq.setStatus(s);
					//persist in the DB.
					HibernateUtils.save(oReq);
					
					//Add a remark to the OHD saying the bag is denied.
					Remark r = new Remark();
					r.setAgent(user);
					r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
					String remarkText = messages.getMessage(new Locale(user.getCurrentlocale()), "bagrequestDenyMessage") + " "
					+ user.getStation().getCompany().getCompanyCode_ID() + messages.getMessage(new Locale(user.getCurrentlocale()), "aposS") + " "
					+ user.getStation().getStationcode() + " station.";
					
					if (reason != null && reason.trim().length() > 0) {
						remarkText += "\n\n" + messages.getMessage(new Locale(user.getCurrentlocale()), "bagrequestDenyReason") + " ";
					}
						
					r.setRemarktext(remarkText);
					

					r.setOhd(ohd);
					Set remarks = ohd.getRemarks();
					remarks.add(r);
					obmo.insertOHD(ohd, user);
					
					String msgText = messages.getMessage(new Locale(user.getCurrentlocale()), "bagrequestDenySendMessage1") + " "
					+ oReq.getOhd().getOHD_ID() + " "
					+ messages.getMessage(new Locale(user.getCurrentlocale()), "bagrequestDenySendMessage2") + " "
					+ user.getStation().getCompany().getCompanyCode_ID() + messages.getMessage(new Locale(user.getCurrentlocale()), "aposS") + " "
					+ user.getStation().getStationcode() + " " + TracerUtils.getText("remarkTextStation", user);
					 
					if (msgText != null && reason.trim().length() > 0) {
						remarkText += "\n\n" + messages.getMessage(new Locale(user.getCurrentlocale()), "bagrequestDenyReason") + " ";
					}
					String subject = TracerUtils.getText("bagrequestDenyMsgSubject" + ": " + oReq.getOhd().getOHD_ID(), user);
					
					MessageUtils.sendmessage(oReq.getRequestForStation(), subject, user, msgText, oReq.getIncident_ID(), oReq.getOhd().getOHD_ID());
				}
				response.sendRedirect("viewROH.do");
				return null;
			} else {
				request.setAttribute("request_ID", request_ID);
				OHDRequest oReq = OHDUtils.getRequest(request_ID);
				request.setAttribute("ohd_request", oReq);
				return mapping.findForward(TracingConstants.DENY_REQUEST_ON_HAND);
			}
		} else {
			//show the request
			if (request.getParameter("showRequest") != null) {
				String request_ID = request.getParameter("request_ID");
				OHDRequest oReq = OHDUtils.getRequest(request_ID);
				request.setAttribute("ohd_request", oReq);

				return mapping.findForward(TracingConstants.VIEW_REQUEST_DETAILS);
			} else {
				
				//check and make sure the agents station does not already have a pending request for that bag.
				String ohd_ID = request.getParameter("ohd_ID");
				if(ohd_ID != null) {
					OHDRequest existing = RequestOhdBMO.getOpenRequest(user.getStation(), ohd_ID);
					if(existing != null) {
						request.setAttribute("ohd_request", existing);
						ActionMessage error = new ActionMessage("message.request.ohd.already.exists");
						ActionMessages errors = new ActionMessages();
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
						return mapping.findForward(TracingConstants.VIEW_REQUEST_DETAILS);
					}
				}

				BagService bs = new BagService();

				
				if (request.getParameter("save") != null) {
					if (bs.requestOnHand(theform, user, messages)) {
						return (mapping.findForward(TracingConstants.REQUEST_ON_HAND_SUCCESS));
					} else {
						return (mapping.findForward(TracingConstants.REQUEST_ON_HAND));
					}
				}

				String incident_ID = request.getParameter("mbr_ID");
				String match_ID = request.getParameter("match_ID");

				if (ohd_ID != null) {
					theform.setOhd_ID(ohd_ID);
					theform.setIncident_ID(incident_ID);
					theform.setMatch_ID(match_ID);
				}
				return mapping.findForward(TracingConstants.REQUEST_ON_HAND);
			}
		}
	}
}