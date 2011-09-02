package com.bagnet.nettracer.tracing.actions.lf;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import aero.nettracer.lf.services.LFServiceWrapper;
import aero.nettracer.lf.services.LFUtils;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchDetail;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;
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
		
		LFUtils.getLists(user, session);
		
		LostReportForm lrForm = (LostReportForm) form;
		lrForm.setDateFormat(user.getDateformat().getFormat());
		
		LFLost lostReport = null;
		if (request.getParameter("createNew") != null) {
			lostReport = LFUtils.createLFLost(user);
		} else if (request.getParameter("lostId") != null) {
			long id = Long.parseLong(request.getParameter("lostId"));
			lostReport = LFServiceWrapper.getInstance().getLostReport(id);
		} else {
			lostReport = lrForm.getLost();
		}
		
		if (request.getParameter("save") != null) {
			LFServiceWrapper.getInstance().saveOrUpdateLostReport(lostReport, user);
			if (lostReport.getItem().getFound() != null) {
				LFServiceWrapper.getInstance().saveOrUpdateFoundItem(lostReport.getItem().getFound(), user);
			}
			if(lostReport.getStatus().getStatus_ID() == TracingConstants.LF_STATUS_OPEN){
				LFServiceWrapper.getInstance().traceLostItem(lostReport.getId());
			}
		} else if (request.getParameter("undo") != null) {
			long itemId = Long.valueOf((String) request.getParameter("itemId"));
			LFItem item = getItemById(lrForm.getLost().getItems(), itemId);
		
			if (item != null) {
				item.setDispositionId(TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED);
				item.setTrackingNumber(null);
				
				item.getFound().getItem().setDispositionId(TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED);
				item.getFound().getItem().setTrackingNumber(null);
			}
			lostReport.setStatusId(TracingConstants.LF_STATUS_OPEN);
		} else if (request.getParameter("unmatchItem") != null) {
			long itemId = Long.valueOf((String) request.getParameter("itemId"));
			LFItem item = getItemById(lrForm.getLost().getItems(), itemId);
			if (item != null) {
				long matchId = LFServiceWrapper.getInstance().findConfirmedMatch(item.getLost().getId(), item.getFound().getId());
				if(LFServiceWrapper.getInstance().undoMatch(matchId)){
					lostReport = LFServiceWrapper.getInstance().getLostReport(lrForm.getLost().getId());
				} else {
					//TODO handle the failed undo
				}
			}
		} else if (request.getParameter("matchItem") != null) {

			try {
				String foundId = (String) request.getParameter("foundId");
				long id = Long.valueOf(foundId);
				LFFound found = LFServiceWrapper.getInstance().getFoundItem(id);
				long matchId = LFServiceWrapper.getInstance().createManualMatch(lrForm.getLost(), found);
				if(LFServiceWrapper.getInstance().confirmMatch(matchId)){
					lostReport = LFServiceWrapper.getInstance().getLostReport(lrForm.getLost().getId());
				} else {
					//TODO fail to create manual match, do something....
				}
			} catch (NumberFormatException nfe) {
				logger.error(nfe);
			}

		}

		lrForm.setLost(lostReport);
		return mapping.findForward(TracingConstants.LF_CREATE_LOST_REPORT);
		
	}
	
	private LFItem getItemById(Set<LFItem> items, long id) {
		for (LFItem item: items) {
			if (item.getId() == id) {
				return item;
			}
		}
		return null;
	}
	
}