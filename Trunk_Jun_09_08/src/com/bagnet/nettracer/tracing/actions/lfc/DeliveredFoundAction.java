package com.bagnet.nettracer.tracing.actions.lfc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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
import aero.nettracer.lf.services.LFUtils;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFSalvage;
import com.bagnet.nettracer.tracing.forms.lfc.DeliveredFoundForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class DeliveredFoundAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(SalvageAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		TracerUtils.checkSession(session);
		ActionMessages errors = new ActionMessages();

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		LFUtils.getLists(user, session);

		LFServiceBean serviceBean = new LFServiceBean();
		DeliveredFoundForm dfForm = (DeliveredFoundForm) form;
		List<LFFound> deliveredFounds=dfForm.getDeliveredFound();
		if(deliveredFounds==null){
				deliveredFounds=new ArrayList();
		}
		request.removeAttribute("found");
		String divId = (String) request.getParameter("divId");
		request.setAttribute("divId", divId);
		String addDeliveredItem = (String) request.getParameter("addDeliveredItem");
		String removeItem = (String) request.getParameter("removeItem");
		String prevBoxId = (String) request.getParameter("previousBoxId");
		String updateItem = (String) request.getParameter("boxUpdateFound");
		String newRemark=(String) request.getParameter("newRemark");

		if (addDeliveredItem != null && !addDeliveredItem.isEmpty()) {
			String barcode = "";
			LFFound found = null;
			boolean success = true;
			try {
				barcode = (String) request.getParameter("addDeliveredItem");
				found = serviceBean.getFoundItemByBarcode(barcode);
			} catch (NumberFormatException nfe) {
				logger.error(nfe, nfe);
			}
			
			if (found == null) {
				success = false;
				request.setAttribute("messageerror", resources.getString("error.unable.add.item") + ": " + barcode);
			} else if (found.getItem() != null && found.getItem().getDisposition()!=null && found.getItem().getDisposition().getStatus_ID()==TracingConstants.LF_DISPOSITION_DELIVERED) {
				request.setAttribute("found", found);
				success = false;
				if (found.getItem().getDisposition().getStatus_ID() == TracingConstants.LF_DISPOSITION_DELIVERED) {
					request.setAttribute("messageerror", resources.getString("lf.found.item") + ": " + found.getBarcode() + " " + resources.getString("error.item.already.delivered"));
				} else if (found.getStatusId() == TracingConstants.LF_STATUS_CLOSED) {
					request.setAttribute("messageerror", resources.getString("lf.found.item") + ": " + found.getBarcode() + " " + resources.getString("error.item.already.closed"));
				}
			} else if(found.getItem().getLost()!=null) {
				request.setAttribute("item", found.getItem());
				request.setAttribute("found", found);
				request.setAttribute("lost", found.getItem().getLost());
				found.getItem().setDispositionId(TracingConstants.LF_DISPOSITION_DELIVERED);
				found.setDeliveredDate(new Date());
				found.setStatusId(TracingConstants.LF_STATUS_CLOSED);
				found.getItem().getLost().setStatusId(TracingConstants.LF_STATUS_CLOSED);
				if(newRemark!=null && !newRemark.isEmpty()) {
					found.getItem().setTrackingNumber(newRemark);
					request.setAttribute("lastRemark", newRemark);
				}
				if(found.getItem().getLost().getItem() != null){
					found.getItem().getLost().getItem().setDispositionId(TracingConstants.LF_DISPOSITION_DELIVERED);
					found.getItem().getLost().getItem().setTrackingNumber(found.getItem().getTrackingNumber());
					serviceBean.saveOrUpdateLostReport(found.getItem().getLost(), user);
				}

				serviceBean.saveOrUpdateFoundItem(found, user);
				request.setAttribute("message", resources.getString("lf.found.item") + ": " + found.getBarcode() + ", " + resources.getString("match.with")+" "+resources.getString("lf.lost.report")+": "+found.getItem().getLost().getId()+", "+resources.getString("item.delivered"));
			} else {

				request.setAttribute("item", found.getItem());
				request.setAttribute("found", found);
				found.getItem().getDisposition().setStatus_ID(TracingConstants.LF_DISPOSITION_DELIVERED);
				found.setDeliveredDate(new Date());
				found.setStatusId(TracingConstants.LF_STATUS_CLOSED);
				if(newRemark!=null && !newRemark.isEmpty()) {
					found.getItem().setTrackingNumber(newRemark);
					request.setAttribute("lastRemark", newRemark);
				}
				serviceBean.saveOrUpdateFoundItem(found, user);
				
				Calendar rxDate = Calendar.getInstance();
				rxDate.setTime(found.getReceivedDate());
				deliveredFounds.add(found);

				if (success) {
					request.setAttribute("message", resources.getString("lf.found.item") + ": " + found.getBarcode() + " "+resources.getString("item.delivered"));
				}
			}

			return mapping.findForward(TracingConstants.AJAX_DELIVERED_FOUNDS);
			
		}// else if (request.getParameter("save") != null) {
//			saveSalvage(serviceBean, salvage, request, errors);
//			salvage = serviceBean.loadSalvage(salvage.getId());
//			salvageItems = serviceBean.loadSalvageFound(salvage.getId());
//		} 
//		
//		if (deliveredFounds != null) {
//			ActionMessage error = new ActionMessage("message.save.salvage.to.add.items");
//			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
//			saveMessages(request, errors);
//		}
		
		if(newRemark!=null && !newRemark.isEmpty()) {
			//found.getItem().set
			request.setAttribute("lastRemark", newRemark);
		}
		dfForm.setDeliveredFound(deliveredFounds);
		request.setAttribute("deliveredFounds", deliveredFounds);
		return mapping.findForward(TracingConstants.LFC_DELIVERED_FOUND);
	}
	
	private LFSalvage createSalvage(Agent user) {
		LFSalvage salvage = new LFSalvage();
		salvage.setCreatedDate(new Date());
		salvage.setAgent(user);
		Status status = new Status();
		status.setStatus_ID(TracingConstants.LF_STATUS_OPEN);
		salvage.setStatus(status);
		salvage.setLocation(user.getStation());
		salvage.setItems(new LinkedHashSet<LFFound>());
		return salvage;
	}
	
	private boolean saveSalvage(LFServiceBean serviceBean, LFSalvage salvage, HttpServletRequest request, ActionMessages errors) {
		boolean success = serviceBean.saveSalvage(salvage); 
		ActionMessage message;
		if (success) {
			message = new ActionMessage("message.salvage.saved");
		} else {
			message = new ActionMessage("message.salvage.save.failed");
		}
		errors.add(ActionMessages.GLOBAL_MESSAGE, message);
		saveMessages(request, errors);
		return success;
	}
	
//	private String padBarcode(String barcode) {
//		StringBuilder sb = new StringBuilder(barcode);
//		if (barcode.length() < 8) {
//			sb.append("0");
//		}
//		return sb.toString();
//	}
	
}