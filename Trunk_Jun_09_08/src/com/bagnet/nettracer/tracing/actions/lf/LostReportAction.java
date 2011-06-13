package com.bagnet.nettracer.tracing.actions.lf;

import java.util.Date;
import java.util.LinkedHashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import aero.nettracer.lf.services.LFServiceBean;
import aero.nettracer.lf.services.LFServiceWrapper;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFAddress;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFPerson;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
import com.bagnet.nettracer.tracing.db.lf.LFReservation;
import com.bagnet.nettracer.tracing.forms.lf.LostReportForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class LostReportAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(LostReportAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_LOST_REPORT, user)) {
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		}
		
		getLists(user, session);
		
		LostReportForm lrForm = (LostReportForm) form;
		lrForm.setDateFormat(user.getDateformat().getFormat());
		
		LFLost lostReport = null;
		if (request.getParameter("lostId") != null) {
			long id = Long.parseLong(request.getParameter("lostId"));
			lostReport = LFServiceWrapper.getInstance().getLostReport(id);
		} else if (request.getParameter("save") != null) {
			lostReport = lrForm.getLost();
			LFServiceWrapper.getInstance().saveOrUpdateLostReport(lostReport);
		} else {
			lostReport = createLFLost(user);
		}

		lrForm.setLost(lostReport);
		return mapping.findForward(TracingConstants.LF_CREATE_LOST_REPORT);
		
	}
	
	private LFLost createLFLost(Agent agent) {
		LFLost lost = new LFLost();
		Status lostStatus = new Status();
		lostStatus.setStatus_ID(TracingConstants.LF_STATUS_OPEN);
		lost.setStatus(lostStatus);
		
		LinkedHashSet<LFItem> items = new LinkedHashSet<LFItem>();
		LFItem item = new LFItem();
		item.setLost(lost);
		Status itemStatus = new Status();
		itemStatus.setStatus_ID(TracingConstants.LF_STATUS_OPEN);
		item.setStatus(itemStatus);
		items.add(item);
		lost.setItems(items);
		
		LFReservation reservation = new LFReservation();
		Station dropoffLocation = new Station();
		Station pickupLocation = new Station();
		dropoffLocation.setStation_ID(agent.getStation().getStation_ID());
		reservation.setPickupLocation(pickupLocation);
		reservation.setDropoffLocation(dropoffLocation);
		lost.setReservation(reservation);
		
		LFPerson client = new LFPerson();
		LFAddress address = new LFAddress();
		client.setAddress(address);
		
		LinkedHashSet<LFPhone> phones = new LinkedHashSet<LFPhone>();
		client.setPhones(phones);
		
		lost.setClient(client);
		lost.setOpenDate(new Date());
		lost.setAgent(agent);
		lost.setLocation(agent.getStation());
		return lost;
	}
	
	private void getLists(Agent user, HttpSession session) {
		
		if (session.getAttribute("lfcolorlist") == null) {
			session.setAttribute("lfcolorlist", new LFServiceBean().getColors());
		}
		
		if (session.getAttribute("lfcategorylist") == null) {
			session.setAttribute("lfcategorylist", new LFServiceBean().getCategories());
		}
		
		session.setAttribute("lfstatuslist", session
				.getAttribute("lfstatuslist") != null ? session
						.getAttribute("lfstatuslist") : TracerUtils.getStatusList(TracingConstants.TABLE_LF_STATUS, user.getCurrentlocale()));

		session.setAttribute("lfdispositionlist", session
				.getAttribute("lfdispositionlist") != null ? session
				.getAttribute("lfdispositionlist") : TracerUtils.getStatusList(TracingConstants.TABLE_LF_DISPOSITION, user.getCurrentlocale()));
	}
	
}