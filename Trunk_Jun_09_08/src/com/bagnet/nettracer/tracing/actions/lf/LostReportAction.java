package com.bagnet.nettracer.tracing.actions.lf;

import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashSet;
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

import aero.nettracer.lf.services.LFServiceWrapper;
import aero.nettracer.lf.services.LFUtils;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFRemark;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchDetail;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;
import com.bagnet.nettracer.tracing.forms.lf.LostReportForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
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
			List remarkList = lrForm.getRemarklist();
			if (remarkList != null)
				remarkList.remove(Integer.parseInt(index));
			request.setAttribute("remark", Integer.toString(lrForm.getRemarklist().size() - 1));
		}
		
		LFLost lostReport = null;
		if (request.getParameter("createNew") != null) {
			lostReport = LFUtils.createLFLost(user);
		} else if (request.getParameter("lostId") != null) {
			long id = Long.parseLong(request.getParameter("lostId"));
			lostReport = LFServiceWrapper.getInstance().getLostReport(id);
		} else {
			lrForm.populateRemarks();
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
				LFServiceWrapper.getInstance().saveOrUpdateFoundItem(item.getFound(), user);
			}
			lostReport.setStatusId(TracingConstants.LF_STATUS_OPEN);
			LFServiceWrapper.getInstance().saveOrUpdateLostReport(lostReport, user);
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
		} else if (request.getParameter("addremark") != null) {
			//set new remark with current time
			LFRemark r = lrForm.getRemark(lrForm.getRemarklist().size());
			r.getRemark().setRemarkdate(TracerDateTime.getGMTDate());
			r.getRemark().setAgent(user);
			r.getRemark().setRemarktext("");
			r.getRemark().set_DATEFORMAT(user.getDateformat().getFormat());
			r.getRemark().set_TIMEFORMAT(user.getTimeformat().getFormat());
			r.getRemark().set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			request.setAttribute("remark", Integer.toString(lrForm.getRemarklist().size() - 1));
		} else if (request.getParameter("matchItem") != null) {  // ALWAYS HAVE THIS PARAMETER RUN LAST!!!! CG: 1.9.12

			try {
				String foundId = (String) request.getParameter("foundId");
				if (foundId != null && !foundId.trim().equals("")) {
					long id = Long.valueOf(foundId);
					LFFound found;
					if (TracingConstants.LF_SUBCOMPANIES.get(lostReport.getCompanyId()).equals(TracingConstants.LF_AB_COMPANY_ID)) {
						found = LFServiceWrapper.getInstance().getFoundItem(id);
					} else {
						found = LFServiceWrapper.getInstance().getFoundItemByBarcode(id + "");
					}
					if (found == null) {
						ActionMessages errors = new ActionMessages();
						ActionMessage error = new ActionMessage("error.invalid.found.id");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
						return mapping.findForward(TracingConstants.LF_CREATE_LOST_REPORT);
					}
					long matchId = LFServiceWrapper.getInstance().createManualMatch(lrForm.getLost(), found);
					if(LFServiceWrapper.getInstance().confirmMatch(matchId)){
						lostReport = LFServiceWrapper.getInstance().getLostReport(lrForm.getLost().getId());
					} else {
						//TODO fail to create manual match, do something....
					}
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