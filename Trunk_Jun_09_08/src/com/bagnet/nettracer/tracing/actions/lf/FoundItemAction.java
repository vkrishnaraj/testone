package com.bagnet.nettracer.tracing.actions.lf;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
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
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFRemark;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchDetail;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;
import com.bagnet.nettracer.tracing.forms.lf.FoundItemForm;
import com.bagnet.nettracer.tracing.forms.lf.TraceResultsFilter;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class FoundItemAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(FoundItemAction.class);
	
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
		
		LFUtils.getLists(user, session);
		LFServiceBean serviceBean = new LFServiceBean();
		FoundItemForm fiForm = (FoundItemForm) form;
		fiForm.setDateFormat(user.getDateformat().getFormat());
		
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
			List remarkList = fiForm.getRemarklist();
			if (remarkList != null)
				remarkList.remove(Integer.parseInt(index));
			request.setAttribute("remark", Integer.toString(fiForm.getRemarklist().size() - 1));
		}
		
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
		}else {
			fiForm.populateRemarks();
			found = fiForm.getFound();
		}
		
		if (request.getParameter("save") != null) {
			try {
				if(found.getItem() != null && found.getItem().getTrackingNumber() != null && found.getItem().getTrackingNumber().trim().length() > 0){
					//we have a manual delivery
					found.getItem().setDispositionId(TracingConstants.LF_DISPOSITION_DELIVERED);
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
					LFServiceWrapper.getInstance().traceFoundItem(found.getId());
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
			LFRemark r = fiForm.getRemark(fiForm.getRemarklist().size());
			r.getRemark().setRemarkdate(TracerDateTime.getGMTDate());
			r.getRemark().setAgent(user);
			r.getRemark().setRemarktext("");
			r.getRemark().set_DATEFORMAT(user.getDateformat().getFormat());
			r.getRemark().set_TIMEFORMAT(user.getTimeformat().getFormat());
			r.getRemark().set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			request.setAttribute("remark", Integer.toString(fiForm.getRemarklist().size() - 1));
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
				}
			} catch (NumberFormatException nfe) {
				logger.error(nfe);
			}
		} else if (request.getParameter("pickup") != null){
			if(found.getItem() != null){
				found.getItem().setDispositionId(TracingConstants.LF_DISPOSITION_PICKED_UP);
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
			}
		} else if (request.getParameter("matchId") != null) {
			try {
				long matchId = Long.valueOf(request.getParameter("matchId"));
				if (request.getParameter("reject") != null) {
					serviceBean.rejectMatch(matchId);
				} else if (request.getParameter("unreject") != null) {
					serviceBean.unrejectMatch(matchId);
				} else if (request.getParameter("confirm") != null) {
					serviceBean.confirmMatch(matchId);
				} else if (request.getParameter("unconfirm") != null) {
					serviceBean.undoMatch(matchId);
				}
			} catch (NumberFormatException nfe) {
				logger.error(nfe, nfe);
			}
		}

		fiForm.setFound(found);
		if (TracingConstants.LF_LF_COMPANY_ID.equalsIgnoreCase(user.getCompanycode_ID()!=null?user.getCompanycode_ID():"") && found.getId() != 0) {
			TraceResultsFilter filter = new TraceResultsFilter();
			filter.setRejected(true);
			fiForm.setRejectedResults(serviceBean.getFilteredTraceResultsPaginatedList(user.getStation(), filter, 0, 5000));
			
			filter.setBarcode(found.getBarcode());
			filter.setOpen(true);
			filter.setConfirmed(true);
			filter.setRejected(false);
			fiForm.setTraceResults(serviceBean.getFilteredTraceResultsPaginatedList(user.getStation(), filter, 0, 5000));
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
	
}