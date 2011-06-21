package com.bagnet.nettracer.tracing.actions.lf;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import aero.nettracer.lf.services.LFServiceBean;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.forms.lf.CreateDeliveryForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class CreateDeliveryAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(CreateDeliveryAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		CreateDeliveryForm cdForm = (CreateDeliveryForm) form;
		LFServiceBean serviceBean = new LFServiceBean();
		if (request.getParameter("itemId") != null) {
			long itemId = Long.valueOf(request.getParameter("itemId"));
			LFItem item = serviceBean.getItem(itemId);
			if (request.getParameter("pickedUp") != null) {
				List<LFItem> items = serviceBean.getItemsByLostFoundId(item.getFound().getId(), TracingConstants.LF_TYPE_FOUND);
				for (LFItem i: items) {
					i.setTrackingNumber(null);
					i.setDispositionId(TracingConstants.LF_DISPOSITION_PICKED_UP);
					i.getLost().setStatusId(TracingConstants.LF_STATUS_CLOSED);
					i.getFound().setStatusId(TracingConstants.LF_STATUS_CLOSED);
					
					serviceBean.saveOrUpdateLostReport(i.getLost());
					serviceBean.saveOrUpdateFoundItem(i.getFound());
				}
				
				response.sendRedirect("view_items_deliver.do");
				return null;
			}
			cdForm.setItem(item);
		} else if (request.getParameter("save") != null) {
			LFItem item = cdForm.getItem();
			List<LFItem> items = serviceBean.getItemsByLostFoundId(item.getFound().getId(), TracingConstants.LF_TYPE_FOUND);
			for (LFItem i: items) {
				i.setDispositionId(TracingConstants.LF_DISPOSITION_DELIVERED);
				i.setTrackingNumber(item.getTrackingNumber());
				i.getLost().setStatusId(TracingConstants.LF_STATUS_CLOSED);
				i.getFound().setStatusId(TracingConstants.LF_STATUS_CLOSED);

				serviceBean.saveOrUpdateLostReport(i.getLost());
				serviceBean.saveOrUpdateFoundItem(i.getFound());
			}
			response.sendRedirect("view_items_deliver.do");
			return null;
		}

		return mapping.findForward(TracingConstants.LF_CREATE_DELIVERY);
		
	}
	
}