package com.bagnet.nettracer.tracing.actions.lf;

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
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.forms.lf.FoundItemForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class FoundItemAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(FoundItemAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_FOUND_ITEM, user)) {
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		}
		
		LFUtils.getLists(user, session);
		
		FoundItemForm fiForm = (FoundItemForm) form;
		fiForm.setDateFormat(user.getDateformat().getFormat());
		
		LFFound found = null;
		if (request.getParameter("createNew") != null) {
			found = LFUtils.createLFFound(user);
		} else if (request.getParameter("foundId") != null) {
			long id = Long.parseLong(request.getParameter("foundId"));
			found = LFServiceWrapper.getInstance().getFoundItem(id);
		} else {
			found = fiForm.getFound();
		}
		
		if (request.getParameter("save") != null) {
			LFServiceWrapper.getInstance().saveOrUpdateFoundItem(found, user);
			if (found.getItem().getLost() != null) {
				LFServiceWrapper.getInstance().saveOrUpdateLostReport(found.getItem().getLost(), user);
			}
			if (found.getStatus().getStatus_ID() == TracingConstants.LF_STATUS_OPEN){
				LFServiceWrapper.getInstance().traceFoundItem(found.getId());
			}
		} else if (request.getParameter("undo") != null) {
			long itemId = Long.valueOf((String) request.getParameter("itemId"));
			LFItem item = getItemById(fiForm.getFound().getItems(), itemId);
		
			if (item != null) {
				item.setDispositionId(TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED);
				item.setTrackingNumber(null);
				
				item.getLost().getItem().setDispositionId(TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED);
				item.getLost().getItem().setTrackingNumber(null);
				LFServiceWrapper.getInstance().saveOrUpdateLostReport(item.getLost(), user);
			}
			found.setStatusId(TracingConstants.LF_STATUS_OPEN);
			LFServiceWrapper.getInstance().saveOrUpdateFoundItem(found, user);
		} else if (request.getParameter("unmatchItem") != null) {
			LFItem item = fiForm.getFound().getItem();
			long matchId = LFServiceWrapper.getInstance().findConfirmedMatch(item.getLost().getId(), item.getFound().getId());
			if(LFServiceWrapper.getInstance().undoMatch(matchId)){
				found = LFServiceWrapper.getInstance().getFoundItem(fiForm.getFound().getId());
			} else {
				//TODO handle the failed undo
			}
		} else if (request.getParameter("matchItem") != null) {
			try {
				String foundId = (String) request.getParameter("lostId");
				long id = Long.valueOf(foundId);
				LFLost lost = LFServiceWrapper.getInstance().getLostReport(id);
				long matchId = LFServiceWrapper.getInstance().createManualMatch(lost, fiForm.getFound());
				if(LFServiceWrapper.getInstance().confirmMatch(matchId)){
					found = LFServiceWrapper.getInstance().getFoundItem(fiForm.getFound().getId());
				} else {
					//TODO fail to create manual match, do something....
				}
			} catch (NumberFormatException nfe) {
				logger.error(nfe);
			}
		}

		fiForm.setFound(found);
		return mapping.findForward(TracingConstants.LF_CREATE_FOUND_ITEM);
		
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