package com.bagnet.nettracer.tracing.actions.lf;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

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
import aero.nettracer.lf.services.LFServiceWrapper;
import aero.nettracer.lf.services.LFUtils;
import aero.nettracer.lf.services.exception.NonUniqueBarcodeException;
import aero.nettracer.lf.services.exception.UpdateException;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFRemark;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;
import com.bagnet.nettracer.tracing.forms.lf.FoundItemForm;
import com.bagnet.nettracer.tracing.forms.lf.TraceResultsFilter;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.lf.TraceHandler;

public class FoundItemAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(FoundItemAction.class);
	
	@SuppressWarnings("rawtypes")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		TracerUtils.checkSession(session);
		ActionMessages errors = new ActionMessages();

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_FOUND_ITEM, user)) {
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		}
		
		boolean isLFC = TracingConstants.LF_LF_COMPANY_ID.equalsIgnoreCase(user.getCompanycode_ID()!=null?user.getCompanycode_ID():"");
		
		LFUtils.getLists(user, session);
		LFServiceBean serviceBean = new LFServiceBean();
		FoundItemForm fiForm = (FoundItemForm) form;
		fiForm.setDateFormat(user.getDateformat().getFormat());
		
		LFFound found = null;
		if (request.getParameter("createNew") != null) {
			found = LFUtils.createLFFound(user);
			fiForm.setTraceResults(null);
			fiForm.setRejectedResults(null);
		} else if (request.getParameter("foundId") != null) {
			long id = Long.parseLong(request.getParameter("foundId"));
			found = LFServiceWrapper.getInstance().getFoundItem(id);
		} else if (request.getParameter("barcode") != null) {
			String barcode = (String) request.getParameter("barcode");
			found = LFServiceWrapper.getInstance().getFoundItemByBarcode(barcode);
			if (found == null) {
				found = LFUtils.createLFFound(user);
			}
		} else {
			found = fiForm.getFound();
		}
		
		boolean deleteRemark = false;

		String index = "0";
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String parameter = (String) e.nextElement();
			if (parameter.indexOf("[") != -1) {
				index = parameter.substring(parameter.indexOf("[") + 1, parameter.indexOf("]"));
					if (parameter.indexOf("deleteRemark") != -1) {
					deleteRemark = true;
					break;
				}
			}
		}
		if (deleteRemark) {
			Set<LFRemark> remarkList = found.getAgentRemarks();
			int indexToRemove = Integer.parseInt(index);
			if (remarkList != null && !remarkList.isEmpty() && remarkList.size() > indexToRemove) {
				int i = 0;
				Iterator<LFRemark> iterator = remarkList.iterator();
				while (iterator.hasNext()) {
					LFRemark remark = iterator.next();
					if (i == indexToRemove) {
						remark.setFound(null);
						remarkList.remove(remark);
						break;
					}
					++i;
				}
			}
			request.setAttribute("remark", Integer.toString(fiForm.getFound().getAgentRemarks().size() - 1));
		}
		
		if (request.getParameter("save") != null) {
			
			try {
				populateRemarks(found);
				if(found.getItem() != null && found.getItem().getTrackingNumber() != null && found.getItem().getTrackingNumber().trim().length() > 0){
					//we have a manual delivery
					found.getItem().setDispositionId(TracingConstants.LF_DISPOSITION_DELIVERED);
					found.setDeliveredDate(new Date());
					found.setStatusId(TracingConstants.LF_STATUS_CLOSED);
					if (found.getItem().getLost() != null) {
						found.getItem().getLost().setStatusId(TracingConstants.LF_STATUS_CLOSED);
						if(found.getItem().getLost().getItem() != null){
							found.getItem().getLost().getItem().setDispositionId(TracingConstants.LF_DISPOSITION_DELIVERED);
							found.getItem().getLost().getItem().setTrackingNumber(found.getItem().getTrackingNumber());
						}
					}
				}
				
				LFServiceWrapper.getInstance().saveOrUpdateFoundItem(found, user);
				if (found.getItem().getLost() != null) {
					LFServiceWrapper.getInstance().saveOrUpdateLostReport(found.getItem().getLost(), user);
				}
				if (found.getStatus().getStatus_ID() == TracingConstants.LF_STATUS_OPEN){
					if(isLFC){
						TraceHandler.trace(found);//Async tracing for LFC	
					} else {
						LFServiceWrapper.getInstance().traceFoundItem(found.getId());
					}
				}
				ActionMessage error = new ActionMessage("message.found.save.success");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} catch (NonUniqueBarcodeException nube) {
				logger.error(nube, nube);
				ActionMessage error = new ActionMessage("error.non.unique.barcode");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				request.setAttribute("enableIdField", "true");
			} catch (UpdateException ue) {
				logger.error(ue, ue);
				ActionMessage error = new ActionMessage("error.failed.to.save");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
		} else if (request.getParameter("addremark") != null) {
			//set new remark with current time
//			LFRemark r = fiForm.getRemark(fiForm.getFound().getAgentRemarks().size());
			populateRemarks(found);
			LFRemark r = new LFRemark();
			r.getRemark().setType(TracingConstants.REMARK_REGULAR);
			r.getRemark().setRemarkdate(TracerDateTime.getGMTDate());
			r.getRemark().setAgent(user);
			r.getRemark().setRemarktext("");
			r.getRemark().set_DATEFORMAT(user.getDateformat().getFormat());
			r.getRemark().set_TIMEFORMAT(user.getTimeformat().getFormat());
			r.getRemark().set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			r.setFound(found);
			found.getAgentRemarks().add(r);
			request.setAttribute("remark", Integer.toString(found.getAgentRemarks().size() - 1));
		} else if (request.getParameter("undo") != null) {
			//handles both undoing a delivery and a pickup
			long itemId = Long.valueOf((String) request.getParameter("itemId"));
			LFItem item = getItemById(fiForm.getFound().getItems(), itemId);
		
			if (item != null) {
				int disposition;
				if(item.getFound() != null && item.getLost() != null && 
					LFServiceWrapper.getInstance().findConfirmedMatch(item.getLost().getId(), item.getFound().getId()) > 0){
					disposition = TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED;
				} else {
					disposition = TracingConstants.LF_DISPOSITION_OTHER;
				}
				
				item.setDispositionId(disposition);
				item.setTrackingNumber(null);
				
				if(item.getLost() != null){
					if(item.getLost().getItem() != null){
						item.getLost().getItem().setDispositionId(disposition);
						item.getLost().getItem().setTrackingNumber(null);
					}
					item.getLost().setStatusId(TracingConstants.LF_STATUS_OPEN);
					LFServiceWrapper.getInstance().saveOrUpdateLostReport(item.getLost(), user);
				}
				
				if (item.getDeliveryRejected()) {
					item.setDeliveryRejected(false);
					if (item.getLost() != null) {
						item.getLost().getItem().setDeliveryRejected(false);
					}
				}
			}
			found.setDeliveredDate(null);
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
				if (lost == null) {
					ActionMessage error = new ActionMessage("error.invalid.lost.id");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
					return mapping.findForward(TracingConstants.LF_CREATE_FOUND_ITEM);
				}
				long matchId = LFServiceWrapper.getInstance().createManualMatch(lost, fiForm.getFound());
				if(LFServiceWrapper.getInstance().confirmMatch(matchId)){
					found = LFServiceWrapper.getInstance().getFoundItem(fiForm.getFound().getId());
				} else {
					//TODO fail to create manual match, do something....
					ActionMessage error = new ActionMessage("error.invalid.lostMatched.id");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
					return mapping.findForward(TracingConstants.LF_CREATE_FOUND_ITEM);
				}
			} catch (NumberFormatException nfe) {
				logger.error(nfe);
			}
		} else if (request.getParameter("pickup") != null){
			if(found.getItem() != null && found.getCheckAmount() == 0){
				found.getItem().setDispositionId(TracingConstants.LF_DISPOSITION_PICKED_UP);
				found.setDeliveredDate(new Date());
				found.setStatusId(TracingConstants.LF_STATUS_CLOSED);
				if(found.getItem().getLost() != null){
					found.getItem().getLost().setStatusId(TracingConstants.LF_STATUS_CLOSED);
					if(found.getItem().getLost().getItem() != null){
						//loupas - even though the model supports multiple items for a lost, the way tracing and matching currently works
						//multiple items per lost cannot work unless there is some major refactoring
						found.getItem().getLost().getItem().setDispositionId(TracingConstants.LF_DISPOSITION_PICKED_UP);
					}
					LFServiceWrapper.getInstance().saveOrUpdateLostReport(found.getItem().getLost(), user);
				}
				LFServiceWrapper.getInstance().saveOrUpdateFoundItem(found, user);
			} else {
				ActionMessage error = new ActionMessage("error.message.check.tracking.required");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
		} else if (request.getParameter("matchId") != null) {
			try {
				long matchId = Long.valueOf(request.getParameter("matchId"));
				if (request.getParameter("reject") != null) {
					if (serviceBean.rejectMatch(matchId)) {
						found = serviceBean.getFoundItem(fiForm.getFound().getId());
					}
				} else if (request.getParameter("unreject") != null) {
					if (serviceBean.unrejectMatch(matchId)) {
						found = serviceBean.getFoundItem(fiForm.getFound().getId());
					}
				} else if (request.getParameter("confirm") != null) {
					if (serviceBean.confirmMatch(matchId)) {
						found = serviceBean.getFoundItem(fiForm.getFound().getId());
					}
				} else if (request.getParameter("unconfirm") != null) {
					if(LFServiceWrapper.getInstance().undoMatch(matchId)){
						found = serviceBean.getFoundItem(fiForm.getFound().getId());
					}
				} else if (request.getParameter("email") != null) {
					try {
						LFMatchHistory match = getMatchById(fiForm.getTraceResults(), matchId);
						if (match != null && serviceBean.sendFoundEmail(match.getLost().getId())) {
							found = serviceBean.getFoundItem(fiForm.getFound().getId());
						} else {
							ActionMessage error = new ActionMessage("message.email.error");
							errors.add(ActionMessages.GLOBAL_MESSAGE, error);
							saveMessages(request, errors);
						}
					} catch (Exception ex) {
						logger.error(ex, ex);
						ActionMessage error = new ActionMessage("message.email.error");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
					}
				}
			} catch (NumberFormatException nfe) {
				logger.error(nfe, nfe);
			}
		} else if (request.getParameter("deliveryRejected") != null) {
			if(found.getItem() != null && found.getCheckAmount() == 0){
				found.getItem().setDeliveryRejected(true);
				found.getItem().setDispositionId(TracingConstants.LF_DISPOSITION_OTHER);
				found.setDeliveredDate(new Date());
				found.setStatusId(TracingConstants.LF_STATUS_CLOSED);
				if(found.getItem().getLost() != null){
					found.getItem().getLost().setStatusId(TracingConstants.LF_STATUS_CLOSED);
					if(found.getItem().getLost().getItem() != null){
						//loupas - even though the model supports multiple items for a lost, the way tracing and matching currently works
						//multiple items per lost cannot work unless there is some major refactoring
						found.getItem().getLost().getItem().setDeliveryRejected(true);
						found.getItem().getLost().getItem().setDispositionId(TracingConstants.LF_DISPOSITION_OTHER);
					}
					LFServiceWrapper.getInstance().saveOrUpdateLostReport(found.getItem().getLost(), user);
				}
				LFServiceWrapper.getInstance().saveOrUpdateFoundItem(found, user);
			} else {
				ActionMessage error = new ActionMessage("error.message.check.tracking.required");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
		} 

		fiForm.setFound(found);
		if (isLFC && found.getId() != 0) {
			TraceResultsFilter filter = new TraceResultsFilter();
			filter.setRejected(true);
			filter.setFoundId(found.getId());
			fiForm.setRejectedResults(serviceBean.getFilteredTraceResultsPaginatedList(user.getStation(), filter, 0, 5000));
			
			filter.setBarcode(found.getBarcode());
			filter.setOpen(true);
			filter.setConfirmed(true);
			filter.setRejected(false);
			filter.setFoundId(found.getId());
			fiForm.setTraceResults(serviceBean.getFilteredTraceResultsPaginatedList(user.getStation(), filter, 0, 5000));

			if (fiForm.getFound().getStatus().getStatus_ID() == TracingConstants.LF_STATUS_CLOSED) {
				if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LFC_REOPEN_LOST_FOUND, user)) {
					return mapping.findForward(TracingConstants.LF_CREATE_FOUND_ITEM_RO);
				}
			}
		}
		
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

	private LFMatchHistory getMatchById(List<LFMatchHistory> items, long id) {
		for (LFMatchHistory item: items) {
			if (item.getId() == id) {
				return item;
			}
		}
		return null;
	}

	private void populateRemarks(LFFound found) {
		Iterator<LFRemark> iterator = found.getAgentRemarks().iterator();
		while (iterator.hasNext()) {
			LFRemark lfr = iterator.next();
			if (lfr.getRemark().getRemarktext() != null && !lfr.getRemark().getRemarktext().trim().equals("")) {
				lfr.setFound(found);
				if (lfr.getOutcome() != 0) {
					lfr.getRemark().setType(TracingConstants.REMARK_CALL);
				}
			} else {
				found.getAgentRemarks().remove(lfr);
			}
		}
	}
	
}