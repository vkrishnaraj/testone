package com.bagnet.nettracer.tracing.actions.lfc;

import java.util.Calendar;
import java.util.Date;

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

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.forms.lfc.EnterItemsForm;
import com.bagnet.nettracer.tracing.history.FoundHistoryObject;
import com.bagnet.nettracer.tracing.history.HistoryContainer;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.lf.RemoteConnectionException;
import com.bagnet.nettracer.tracing.utils.lf.TraceHandler;

public class EnterItemsAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(EnterItemsAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		TracerUtils.checkSession(session);
		ActionMessages errors = new ActionMessages();

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		EnterItemsForm eiForm = (EnterItemsForm) form;
		eiForm.setDateFormat(user.getDateformat().getFormat());
		
		LFUtils.getLists(user, session);
		if (eiForm.getFound() != null) {
			if (LFUtils.actionChangeSubCategory(eiForm.getFound().getItem().getCategory(), request) || LFUtils.actionChangeSubCompany(eiForm.getFound().getItem().getCategory(), request)) {
				request.setAttribute("formName", "enterItemsForm");
				return mapping.findForward(TracingConstants.AJAX_SUBCATEGORY);
			}
			
//			if (LFUtils.actionChangeSubCompany(eiForm.getFound().getItem().getCategory(), request)) {
//				request.setAttribute("formName", "enterItemsForm");
//				return mapping.findForward(TracingConstants.AJAX_SUBCATEGORY);
//			}
			
			String fhoId = (String) request.getParameter("fhoId");
			if (fhoId != null && !fhoId.isEmpty()) {
				HistoryContainer hc = (HistoryContainer) session.getAttribute("historyContainer");
				FoundHistoryObject fho = (FoundHistoryObject) hc.get(fhoId);
				fho.getFound().setEntryStatus(TracingConstants.LF_STATUS_MOVED);
				LFServiceWrapper.getInstance().saveOrUpdateFoundItem(fho.getFound(), user);
				request.setAttribute("divId", request.getParameter("divId"));
				return mapping.findForward(TracingConstants.AJAX_ITEM_SUMMARY);
			}
		}
		
		LFFound found = eiForm.getFound();
		if (found == null) {
			eiForm.setFound(LFUtils.createLFFound(user));
			eiForm.getFound().setCompanyId(TracingConstants.LF_SWA_COMPANY_ID);
		} else if (request.getParameter("save") != null) {
			try {
				if (found.hasContactInfo()) {
					found.setEntryStatus(TracingConstants.LF_STATUS_VERIFICATION_NEEDED);
				}
				
				LFServiceWrapper.getInstance().saveOrUpdateFoundItem(found, user);
				
				// 1. create the history object
				FoundHistoryObject fho = new FoundHistoryObject();
				fho.setFound(found);
				fho.setDate(Calendar.getInstance().getTime()); 
				fho.setStatusDesc(TracingConstants.HIST_DESCRIPTION_ADD + " " + TracingConstants.HIST_DESCRIPTION_FOUNDITEM);
				fho.setObjectType("Found Item");
				fho.setObjectID(found.getBarcode());
				fho.setLinkURL("create_found_item.do?barcode=");
				// 2. add the history object to the history container
				HistoryContainer history = (HistoryContainer) session.getAttribute("historyContainer");
				history.put(fho.getUniqueId(), fho);
				
				// 3. submit the history object to the trace handler
				try{
					TraceHandler.trace(fho);
				} catch (RemoteConnectionException re){
					//do not prevent item entry even if tracing fails
				}
				
				// 4. clone the found and set it on the form
				eiForm.setFound(duplicateFound(found, user));
			} catch (NonUniqueBarcodeException nube) {
				logger.error(nube, nube);
				ActionMessage error = new ActionMessage("error.non.unique.barcode");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
		}

		return mapping.findForward(TracingConstants.LFC_ENTER_ITEMS);
		
	}
	
	private LFFound duplicateFound(LFFound found, Agent agent) {
		LFFound toReturn = LFUtils.createLFFound(agent);
		toReturn.setReceivedDate(found.getReceivedDate());
		toReturn.setFoundDate(new Date());
		toReturn.setLocation(found.getLocation());
		toReturn.getItem().setValue(found.getItem().getValue());
		toReturn.setCompanyId(found.getCompanyId());
		return toReturn;
	}
	
}