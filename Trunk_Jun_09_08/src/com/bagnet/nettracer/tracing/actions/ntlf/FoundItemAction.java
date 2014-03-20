package com.bagnet.nettracer.tracing.actions.ntlf;

import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
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
import aero.nettracer.lf.services.exception.NonUniqueBarcodeException;
import aero.nettracer.lf.services.exception.UpdateException;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.actions.templates.DocumentTemplateResult;
import com.bagnet.nettracer.tracing.adapter.TemplateAdapter;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.documents.Document;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFRemark;
import com.bagnet.nettracer.tracing.dto.TemplateAdapterDTO;
import com.bagnet.nettracer.tracing.factory.TemplateAdapterFactory;
import com.bagnet.nettracer.tracing.forms.lf.FoundItemForm;
import com.bagnet.nettracer.tracing.history.FoundHistoryObject;
import com.bagnet.nettracer.tracing.service.DocumentService;
import com.bagnet.nettracer.tracing.service.TemplateService;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.HistoryUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.DomainUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class FoundItemAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(FoundItemAction.class);
	
	private DocumentService documentService = (DocumentService) SpringUtils.getBean(TracingConstants.DOCUMENT_SERVICE_BEAN);
	private TemplateService templateService = (TemplateService) SpringUtils.getBean(TracingConstants.TEMPLATE_SERVICE_BEAN);
	
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

		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_NTLF_FOUND_ITEM, user)) {
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		}
		
		if (session.getAttribute("stationList") == null) {
			session.setAttribute("stationList", AdminUtils.getStations(null, user.getCompanycode_ID(), 0, 0));
		}
		
		LFUtils.getLists(user, session);
		FoundItemForm fiForm = (FoundItemForm) form;
		fiForm.setDateFormat(user.getDateformat().getFormat());
		
		LFFound found = null;
		if (request.getParameter("createNew") != null) {
			found = LFUtils.createLFFound(user);
			found.setCompanyId(user.getCompanycode_ID());
			fiForm.setTraceResults(null);
			fiForm.setRejectedResults(null);
		} else if (request.getParameter("foundId") != null) {
			long id = Long.parseLong(request.getParameter("foundId"));
			found = LFServiceWrapper.getInstance().getFoundItem(id);
		} else {
			found = fiForm.getFound();
		}
		if (found == null) {
			found = LFUtils.createLFFound(user);
			found.setCompanyId(user.getCompanycode_ID());
		}
		if(found!=null){
			if (request.getParameter("displayReceipt") != null) {
				displayReceipt(request, response, user, found);
				return null;
			}
			request.setAttribute("stationID", found.getLocation().getStation_ID());
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
					int dispId = found.getItem().getDispositionId();
					if (dispId != TracingConstants.LF_DISPOSITION_DELIVERED && dispId != TracingConstants.LF_DISPOSITION_SENT_TO_LFC) {
						found.getItem().setDispositionId(TracingConstants.LF_DISPOSITION_SENT_TO_LFC);
						found.setDeliveredDate(new Date());
						found.setStatusId(TracingConstants.LF_STATUS_CLOSED);
					}
				}
				if(found.getItem() != null && found.getItem().getRemovalReason() != null && found.getItem().getRemovalReason().trim().length() > 0){
					int dispId = found.getItem().getDispositionId();
					if (dispId != TracingConstants.LF_DISPOSITION_REMOVED) {
						found.getItem().setRemovalReason(null);
					}
				}
				
				LFServiceWrapper.getInstance().saveOrUpdateFoundItem(found, user);
				ActionMessage error = new ActionMessage("message.found.save.success");
				request.setAttribute("success", true);
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				
				// Create FHO and add to History Container for Quick History Popup.
				FoundHistoryObject FHO=new FoundHistoryObject();
				FHO.setFound(found);
				FHO.setObjectID(found.getId() + "");
				FHO.setLinkURL("create_found_item.do?foundId=");
				FHO.setStatusDesc(TracingConstants.HIST_DESCRIPTION_SAVE+" "+TracingConstants.HIST_DESCRIPTION_FOUNDITEM);
				FHO.setObjectType(TracingConstants.HIST_DESCRIPTION_FOUNDITEM);
				HistoryUtils.AddToHistoryContainer(session, FHO, null);
			} catch (NonUniqueBarcodeException nube) {
				logger.error(nube, nube);
				ActionMessage error = new ActionMessage("error.failed.to.save");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				request.setAttribute("enableIdField", "true");
			} catch (UpdateException ue) {
				logger.error(ue, ue);
				ActionMessage error = new ActionMessage("error.failed.to.save");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} catch (Exception ex) {
				logger.error(ex, ex);
				ActionMessage error = new ActionMessage("error.failed.to.save");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
		} else if (request.getParameter("addremark") != null) {
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
				disposition = TracingConstants.LF_DISPOSITION_OTHER;
				
				item.setDispositionId(disposition);
				item.setTrackingNumber(null);
				item.setRemovalReason(null);
			}
			found.setDeliveredDate(null);
			found.setStatusId(TracingConstants.LF_STATUS_OPEN);
			LFServiceWrapper.getInstance().saveOrUpdateFoundItem(found, user);
			ActionMessage error = new ActionMessage("message.found.save.success");
			request.setAttribute("success", true);
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
		} else if (request.getParameter("pickup") != null){
			if(found.getItem() != null){
				found.getItem().setDispositionId(TracingConstants.LF_DISPOSITION_PICKED_UP);
				found.setDeliveredDate(new Date());
				found.setStatusId(TracingConstants.LF_STATUS_CLOSED);
				
				DocumentTemplateResult result = generateFoundItemReceipt(user, found);
				if (!result.isSuccess()) {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(result.getMessageKey()));
				} else {
					found.setReceiptFile((Document) result.getPayload());
				}
				
				LFServiceWrapper.getInstance().saveOrUpdateFoundItem(found, user);
				ActionMessage error = new ActionMessage("message.found.save.success");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				request.setAttribute("success", result.isSuccess());
			}
		} else if (request.getParameter("deliver") != null){
			if(found.getItem() != null){
				found.getItem().setDispositionId(TracingConstants.LF_DISPOSITION_DELIVERED);
				found.setDeliveredDate(new Date());
				found.setStatusId(TracingConstants.LF_STATUS_CLOSED);
				LFServiceWrapper.getInstance().saveOrUpdateFoundItem(found, user);
				ActionMessage error = new ActionMessage("message.found.save.success");
				request.setAttribute("success", true);
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
		} else if (request.getParameter("remove") != null){
			if(found.getItem() != null){
				found.getItem().setDispositionId(TracingConstants.LF_DISPOSITION_REMOVED);
				found.setDeliveredDate(new Date());
				found.setStatusId(TracingConstants.LF_STATUS_CLOSED);
				LFServiceWrapper.getInstance().saveOrUpdateFoundItem(found, user);
				ActionMessage error = new ActionMessage("message.found.save.success");
				request.setAttribute("success", true);
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
		} else if (request.getParameter("sendToLFC") != null){
			if(found.getItem() != null){
				found.getItem().setDispositionId(TracingConstants.LF_DISPOSITION_SENT_TO_LFC);
				found.setDeliveredDate(new Date());
				found.setStatusId(TracingConstants.LF_STATUS_CLOSED);
				LFServiceWrapper.getInstance().saveOrUpdateFoundItem(found, user);
				ActionMessage error = new ActionMessage("message.found.save.success");
				request.setAttribute("success", true);
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
		}

		fiForm.setFound(found);
		if (!errors.isEmpty()) {
			saveMessages(request, errors);
		}
		
		return mapping.findForward(TracingConstants.NTLF_CREATE_FOUND_ITEM);
		
	}

	private void displayReceipt(HttpServletRequest request, HttpServletResponse response, Agent user, LFFound found) {
		try {
			int outputType = Integer.parseInt((String) request.getParameter(TracingConstants.OUTPUT_TYPE));
			long foundId = Long.parseLong((String) request.getParameter("foundId"));
			found = LFServiceWrapper.getInstance().getFoundItem(foundId);
			if (found != null && found.getReceiptFile() != null) {
				Document document = found.getReceiptFile();
				if (outputType == TracingConstants.REPORT_OUTPUT_PDF && document.getFileName() == null) {
					if (!documentService.generatePdf(user, document, PropertyBMO.getValue(PropertyBMO.DOCUMENT_LOCATION_RECEIPTS)).isSuccess()) {
						logger.error("Failed to generate a pdf receipt for found item with id: " + found.getId());
					}
				}
				byte[] toOutput = documentService.getByteArrayForDocument(document, user.getCompanycode_ID(), PropertyBMO.getValue(PropertyBMO.DOCUMENT_LOCATION_RECEIPTS), outputType);
				outputToResponse(response, toOutput, outputType);
			}
		} catch (Exception e) {
			logger.error("Failed to display passenger pickup receipt", e);
		}
	}
	
	private LFItem getItemById(Set<LFItem> items, long id) {
		for (LFItem item: items) {
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
	
	private DocumentTemplateResult generateFoundItemReceipt(Agent user, LFFound found) {
		DocumentTemplateResult result = new DocumentTemplateResult();
		// 1. load the PPU receipt template
		long templateId = 0;
		String templateIdStr = PropertyBMO.getValue(PropertyBMO.FOUND_ITEM_RECEIPT_TEMPLATE);
		try {
			templateId = Long.valueOf(templateIdStr);
		} catch (NumberFormatException nfe) {
			logger.error("Invalid value for found receipt template id: " + templateIdStr , nfe);
			result.setMessageKey("no.found.receipt.template.defined");
			return result;
		}
		
		Template template = templateService.load(templateId);
		if (template == null) {
			result.setMessageKey("no.found.receipt.template.defined");
			return result;
		}
		
		// 2. get the template adapter
		TemplateAdapterDTO dto = DomainUtils.getTemplateAdapterDTO(user, template);
		dto.setFound(found);
		
		try {
			TemplateAdapter adapter = TemplateAdapterFactory.getTemplateAdapter(dto);
			Document document = new Document(template);
			
			// 3. merge the template and adapter
			result = documentService.mergeDocumentToPrint(document, adapter);
			if (!result.isSuccess()) return result;
			
			if (documentService.save(document) == 0) {
				result.setSuccess(false);
				result.setMessageKey("ppu.receipt.failed");
			} else {
				result.setPayload(document);
			}
			
		} catch (Exception e) {
			logger.error("Failed to generate the found item receipt for LFFound with id: " + found.getId(), e);
		}
		
		return result;
	}
	
}
