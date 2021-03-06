package com.bagnet.nettracer.tracing.actions.lf;

import java.util.ArrayList;
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
import aero.nettracer.lf.services.exception.UpdateException;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.lf.SubCompanyDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFRemark;
import com.bagnet.nettracer.tracing.db.lf.LFSegment;
import com.bagnet.nettracer.tracing.db.lf.Subcompany;
import com.bagnet.nettracer.tracing.forms.lf.LostReportForm;
import com.bagnet.nettracer.tracing.history.LostItemHistoryObject;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.HistoryUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.lf.RemoteConnectionException;
import com.bagnet.nettracer.tracing.utils.lf.TraceHandler;

public class LostReportAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(LostReportAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		TracerUtils.checkSession(session);
		ActionMessages errors = new ActionMessages();

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_LOST_REPORT, user)) {
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		}
		
		boolean isLFC = TracingConstants.LF_LF_COMPANY_ID.equalsIgnoreCase(user.getCompanycode_ID()!=null?user.getCompanycode_ID():"");
		
		LFUtils.getLists(user, session);

		LFServiceBean serviceBean = new LFServiceBean();
		LostReportForm lrForm = (LostReportForm) form;
		lrForm.setDateFormat(user.getDateformat().getFormat());
		
		boolean deleteRemark = false;
		boolean deleteSegment = false;

		String delRemIndex = "0";
		String delSegIndex = "0";
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String parameter = (String) e.nextElement();
			if (parameter.indexOf("[") != -1) {
				if (parameter.indexOf("deleteRemark") != -1) {
					deleteRemark = true;
					delRemIndex = parameter.substring(parameter.indexOf("[") + 1, parameter.indexOf("]"));
					break;
				}
				if (parameter.indexOf("deleteSegment") != -1) {
					deleteSegment = true;
					delSegIndex = parameter.substring(parameter.indexOf("[") + 1, parameter.indexOf("]"));
					break;
				}
			}
		}
		
		LFLost lostReport = null;
		if (request.getParameter("createNew") != null) {
			lostReport = LFUtils.createLFLost(user);
		} else if (request.getParameter("lostId") != null) {
			long id = Long.parseLong(request.getParameter("lostId"));
			lostReport = LFServiceWrapper.getInstance().getLostReport(id);
			LostItemHistoryObject LHO=new LostItemHistoryObject();
			LHO.setLostItem(lostReport);
			LHO.setObjectID(String.valueOf(lostReport.getId()));
			LHO.setLinkURL("create_lost_report.do?lostId=");
			LHO.setStatusDesc(TracingConstants.HIST_DESCRIPTION_LOAD+" "+TracingConstants.HIST_DESCRIPTION_LOSTITEM);
			LHO.setObjectType(TracingConstants.HIST_DESCRIPTION_LOSTITEM);
			HistoryUtils.AddToHistoryContainer(session, LHO, null);
		} else {
			lostReport = lrForm.getLost();
		}
		if(lostReport!=null){
			if(user.getSubcompany()!=null)
			{
				if(!lostReport.getCompanyId().equals(user.getSubcompany().getSubcompanyCode())){
					return (mapping.findForward(TracingConstants.NO_PERMISSION));
				}
			}
		}
		
		if (deleteRemark) {
			Set<LFRemark> remarkList = lostReport.getAgentRemarks();
			int indexToRemove = Integer.parseInt(delRemIndex);
			if (remarkList != null && !remarkList.isEmpty() && remarkList.size() > indexToRemove) {
				int i = 0;
				Iterator<LFRemark> iterator = remarkList.iterator();
				while (iterator.hasNext()) {
					LFRemark remark = iterator.next();
					if (i == indexToRemove) {
						remark.setLost(null);
						remarkList.remove(remark);
						break;
					}
					++i;
				}
			}
			request.setAttribute("remark", Integer.toString(lrForm.getLost().getAgentRemarks().size() - 1));
		}
		
		if (deleteSegment) {
			Set<LFSegment> segments = lostReport.getSegments();
			int indexToRemove = Integer.parseInt(delSegIndex);
			if (segments != null && !segments.isEmpty() && segments.size() > indexToRemove) {
				int i = 0;
				Iterator<LFSegment> iterator = segments.iterator();
				while (iterator.hasNext()) {
					LFSegment segment = iterator.next();
					if (i == indexToRemove) {
						segment.setLost(null);
						segments.remove(segment);
						break;
					}
					++i;
				}
			}
		}
		
		if (request.getParameter("save") != null) {
			try {
				populateRemarks(lostReport);

				if(lostReport.getItem() != null && lostReport.getItem().getTrackingNumber()!=null && lostReport.getItem().getTrackingNumber().length()>0){
					if (lostReport.getItem().getFound() != null && lostReport.getItem().getFound().getItem() != null && lostReport.getItem().getFound().getItem().getLost()==null){
						lostReport.getItem().getFound().getItem().setLost(lostReport);
					} else {
						List<LFItem> itemList=serviceBean.getItemsByLostFoundId(lostReport.getId(), 1);
						for(LFItem i:itemList){
							if(i.getLost().getId()==lostReport.getId() && i.getFound()!=null){
								if(i.getFound().getItem() != null){
									i.getFound().getItem().setLost(lostReport);
								}
								lostReport.getItem().setFound(i.getFound());
							}
						}
					}
				}
				
				LFServiceWrapper.getInstance().saveOrUpdateLostReport(lostReport, user);
				if (lostReport.getItem().getFound() != null) {
					LFServiceWrapper.getInstance().saveOrUpdateFoundItem(lostReport.getItem().getFound(), user);
				}
				if(lostReport.getStatus().getStatus_ID() == TracingConstants.LF_STATUS_OPEN){
					if(isLFC){
						try{
							TraceHandler.trace(lostReport);//Async tracing for LFC
						} catch (RemoteConnectionException re){
							//do not prevent item entry even if tracing fails
						}
					} else {
						LFServiceWrapper.getInstance().traceLostItem(lostReport.getId());
					}
				}
				ActionMessage error = new ActionMessage("message.lost.save.success");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				LostItemHistoryObject LHO=new LostItemHistoryObject();
				LHO.setLostItem(lostReport);
				LHO.setObjectID(String.valueOf(lostReport.getId()));
				LHO.setLinkURL("create_lost_report.do?lostId=");
				LHO.setStatusDesc(TracingConstants.HIST_DESCRIPTION_SAVE+" "+TracingConstants.HIST_DESCRIPTION_LOSTITEM);
				LHO.setObjectType(TracingConstants.HIST_DESCRIPTION_LOSTITEM);
				HistoryUtils.AddToHistoryContainer(session, LHO, null);
				
			} catch (UpdateException ue) {
				logger.error(ue, ue);
				ActionMessage error = new ActionMessage("error.failed.to.save");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
		} else if (request.getParameter("undo") != null) {
			long itemId = Long.valueOf((String) request.getParameter("itemId"));
			LFItem item = getItemById(lrForm.getLost().getItems(), itemId);
		
			if (item != null) {
				item.setDispositionId(TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED);
				item.setTrackingNumber(null);
				item.setDeliveryRejected(false);
				
				if (item.getFound() != null) {
					LFItem foundItem = item.getFound().getItem();
					foundItem.setDispositionId(TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED);
					foundItem.setTrackingNumber(null);
					foundItem.setDeliveryRejected(false);
					LFServiceWrapper.getInstance().saveOrUpdateFoundItem(item.getFound(), user);
				}
			}
			lostReport.setStatusId(TracingConstants.LF_STATUS_OPEN);
			LFServiceWrapper.getInstance().saveOrUpdateLostReport(lostReport, user);
		} else if (request.getParameter("unmatchItem") != null) {
			long itemId = Long.valueOf((String) request.getParameter("itemId"));
			LFItem item = getItemById(lrForm.getLost().getItems(), itemId);
			if (item != null) {
				long matchId = LFServiceWrapper.getInstance().findConfirmedMatch(item.getLost().getId(), item.getFound().getId());
				if(LFServiceWrapper.getInstance().undoMatch(matchId, user)){
					lostReport = LFServiceWrapper.getInstance().getLostReport(lrForm.getLost().getId());
				} else {
					//TODO handle the failed undo
				}
			} 
		} else if (request.getParameter("addremark") != null) {
			//set new remark with current time
//			LFRemark r = lrForm.getRemark(lrForm.getLost().getAgentRemarks().size());
			populateRemarks(lostReport);
			LFRemark r = new LFRemark();
			r.getRemark().setType(TracingConstants.REMARK_REGULAR);
			r.getRemark().setRemarkdate(TracerDateTime.getGMTDate());
			r.getRemark().setAgent(user);
			r.getRemark().setRemarktext("");
			r.getRemark().set_DATEFORMAT(user.getDateformat().getFormat());
			r.getRemark().set_TIMEFORMAT(user.getTimeformat().getFormat());
			r.getRemark().set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			r.setLost(lostReport);
			lostReport.getAgentRemarks().add(r);
			request.setAttribute("remark", Integer.toString(lostReport.getAgentRemarks().size() - 1));
		} else if (request.getParameter("addsegment") != null) {		
			int numberToAdd = TracerUtils.getNumberToAdd(request, "addsegmentnum");
			LFSegment s = null;
			for (int i=0; i<numberToAdd; ++i) {
				s = new LFSegment();
				s.setLost(lostReport);
				lostReport.getSegments().add(s);
			}
		} else if (request.getParameter("matchItem") != null) {  // ALWAYS HAVE THIS PARAMETER RUN LAST!!!! CG: 1.9.12

			try {
				String foundId = (String) request.getParameter("foundId");
				if (foundId != null && !foundId.trim().equals("")) {
					long id = Long.valueOf(foundId);
					LFFound found;
					Subcompany subcomp=SubCompanyDAO.loadSubcompany(lostReport.getCompanyId(), lostReport.getAgent().getCompanycode_ID());
					if (subcomp.getCompany().getCompanyCode_ID().equals(TracingConstants.LF_AB_COMPANY_ID)) {
						found = LFServiceWrapper.getInstance().getFoundItem(id);
					} else if (lostReport.getCompanyId().equals(TracingConstants.LF_DEMO_COMPANY_ID)){
						found = LFServiceWrapper.getInstance().getFoundItem(id);
					} else {
						found = LFServiceWrapper.getInstance().getFoundItemByBarcode(foundId + "");//LFC user, use barcode
					}
					if (found == null) {
						ActionMessage error = new ActionMessage("error.invalid.found.id");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
						return mapping.findForward(TracingConstants.LF_CREATE_LOST_REPORT);
					}
					long matchId = LFServiceWrapper.getInstance().createManualMatch(lrForm.getLost(), found);
					if(LFServiceWrapper.getInstance().confirmMatch(matchId, user)){
						lostReport = LFServiceWrapper.getInstance().getLostReport(lrForm.getLost().getId());
					} else {
						ActionMessage error = new ActionMessage("error.invalid.foundMatched.id");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
						return mapping.findForward(TracingConstants.LF_CREATE_LOST_REPORT);//TODO fail to create manual match, do something....
					}
				}
			} catch (NumberFormatException nfe) {
				logger.error(nfe);
			}

		}

		lrForm.setLost(lostReport);
		
		if (TracingConstants.LF_LF_COMPANY_ID.equalsIgnoreCase(user.getCompanycode_ID()!=null?user.getCompanycode_ID():"")) {
			if (lrForm.getLost().getStatus().getStatus_ID() == TracingConstants.LF_STATUS_CLOSED || user.getSubcompany()!=null) {
				if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LFC_REOPEN_LOST_FOUND, user)) {
					return mapping.findForward(TracingConstants.LF_CREATE_LOST_REPORT_RO);
				}
			}
		}
		
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
	
	private void populateRemarks(LFLost lost) {
		Iterator<LFRemark> iterator = lost.getAgentRemarks().iterator();
		while (iterator.hasNext()) {
			LFRemark lfr = iterator.next();
			if (lfr.getRemark().getRemarktext() != null && !lfr.getRemark().getRemarktext().trim().equals("")) {
				lfr.setLost(lost);
				if (lfr.getOutcome() != 0) {
					lfr.getRemark().setType(TracingConstants.REMARK_CALL);
				}
			} else {
				lost.getAgentRemarks().remove(lfr);
			}
		}
	}
	
}