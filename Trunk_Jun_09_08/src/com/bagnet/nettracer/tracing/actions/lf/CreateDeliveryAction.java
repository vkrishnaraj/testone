package com.bagnet.nettracer.tracing.actions.lf;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import aero.nettracer.lf.services.LFServiceBean;
import aero.nettracer.lf.services.exception.UpdateException;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.forms.lf.CreateDeliveryForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class CreateDeliveryAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(CreateDeliveryAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		TracerUtils.checkSession(session);
		ActionMessages errors = new ActionMessages();

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		CreateDeliveryForm cdForm = (CreateDeliveryForm) form;
		LFServiceBean serviceBean = new LFServiceBean();
		LFItem item = cdForm.getItem();
		int dispositionToUse = -1;
		if (request.getParameter("itemId") != null) {
			long itemId = Long.valueOf(request.getParameter("itemId"));
			item = serviceBean.getItem(itemId);
			if (request.getParameter("pickedUp") != null) {
//				List<LFItem> items = serviceBean.getItemsByLostFoundId(item.getFound().getId(), TracingConstants.LF_TYPE_FOUND);
//				for (LFItem i: items) {
//					i.setTrackingNumber(null);
//					i.setDispositionId(TracingConstants.LF_DISPOSITION_PICKED_UP);
//					i.getLost().setStatusId(TracingConstants.LF_STATUS_CLOSED);
//					i.getFound().setStatusId(TracingConstants.LF_STATUS_CLOSED);
//					i.getFound().setDeliveredDate(new Date());
//					
//					serviceBean.saveOrUpdateLostReport(i.getLost(), user);
//					serviceBean.saveOrUpdateFoundItem(i.getFound(), user);
//				}
//				response.sendRedirect("view_items_deliver.do");
//				return null;
				dispositionToUse = TracingConstants.LF_DISPOSITION_PICKED_UP;
			} else if (request.getParameter("deliveryRejected") != null) {
				item.setDeliveryRejected(true);
				dispositionToUse = TracingConstants.LF_DISPOSITION_OTHER;
//				List<LFItem> items = serviceBean.getItemsByLostFoundId(item.getFound().getId(), TracingConstants.LF_TYPE_FOUND);
//				for (LFItem i: items) {
//					i.setTrackingNumber(null);
//					i.setDeliveryRejected(true);
//					i.setDispositionId(TracingConstants.LF_DISPOSITION_OTHER);
//					i.getLost().setStatusId(TracingConstants.LF_STATUS_CLOSED);
//					i.getFound().setStatusId(TracingConstants.LF_STATUS_CLOSED);
//					i.getFound().setDeliveredDate(new Date());
//					
//					serviceBean.saveOrUpdateLostReport(i.getLost(), user);
//					serviceBean.saveOrUpdateFoundItem(i.getFound(), user);
//				}
//				response.sendRedirect("view_items_deliver.do");
//				return null;
			}
			
			if (dispositionToUse == -1) {			
				cdForm.setItem(item);
			}
		} else if (request.getParameter("save") != null) {
			item = cdForm.getItem();
			dispositionToUse = TracingConstants.LF_DISPOSITION_DELIVERED;
//			List<LFItem> items = serviceBean.getItemsByLostFoundId(item.getFound().getId(), TracingConstants.LF_TYPE_FOUND);
//			for (LFItem i: items) {
//				i.setDispositionId(TracingConstants.LF_DISPOSITION_DELIVERED);
//				i.setTrackingNumber(item.getTrackingNumber());
//				i.getLost().setStatusId(TracingConstants.LF_STATUS_CLOSED);
//				i.getFound().setStatusId(TracingConstants.LF_STATUS_CLOSED);
//				i.getFound().setDeliveredDate(new Date());
//
//				serviceBean.saveOrUpdateLostReport(i.getLost(), user);
//				serviceBean.saveOrUpdateFoundItem(i.getFound(), user);
//			}
//			response.sendRedirect("view_items_deliver.do");
//			return null;
		}
		
		if (dispositionToUse != -1) {
			if (!updateFoundItemsStatusAndDisposition(item, dispositionToUse, user)) {
				ActionMessage error = new ActionMessage("message.delivery.error");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
			
			response.sendRedirect("view_items_deliver.do");
			return null;
		}

		return mapping.findForward(TracingConstants.LF_CREATE_DELIVERY);
		
	}
	
	private boolean updateFoundItemsStatusAndDisposition(LFItem item, int disposition, Agent user) {
		if (item == null) {
			return false;
		}
		
		LFServiceBean serviceBean = new LFServiceBean();
		boolean success = true;
		
		List<LFItem> items = serviceBean.getItemsByLostFoundId(item.getFound().getId(), TracingConstants.LF_TYPE_FOUND);
		for (LFItem i: items) {
			i.setDispositionId(disposition);
			i.setTrackingNumber(item.getTrackingNumber());
			i.getLost().setStatusId(TracingConstants.LF_STATUS_CLOSED);
			i.getFound().setStatusId(TracingConstants.LF_STATUS_CLOSED);
			i.getFound().setDeliveredDate(new Date());

			try {
				serviceBean.saveOrUpdateLostReport(i.getLost(), user);
				serviceBean.saveOrUpdateFoundItem(i.getFound(), user);
			} catch (UpdateException ue) {
				logger.error(ue, ue);
				success = false;
			}
		}
		
		return success;
	}
	
}