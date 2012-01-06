package com.bagnet.nettracer.tracing.actions.lfc;

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
import com.bagnet.nettracer.tracing.forms.lfc.EnterItemsForm;
import com.bagnet.nettracer.tracing.history.FoundHistoryObject;
import com.bagnet.nettracer.tracing.history.HistoryContainer;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class EnterItemsAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(EnterItemsAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		EnterItemsForm eiForm = (EnterItemsForm) form;
		eiForm.setDateFormat(user.getDateformat().getFormat());
		
		LFUtils.getLists(user, session);
		if (eiForm.getFound() != null) {
			if (LFUtils.actionChangeSubCategory(eiForm.getFound().getItem().getCategory(), request)) {
				request.setAttribute("formName", "enterItemsForm");
				return mapping.findForward(TracingConstants.AJAX_SUBCATEGORY);
			}
		}
		
		LFFound found = eiForm.getFound();
		if (found == null) {
			eiForm.setFound(LFUtils.createLFFound(user));
			eiForm.getFound().setCompanyId(TracingConstants.LF_SWA_COMPANY_ID);
		} else if (request.getParameter("save") != null) {
			LFServiceWrapper.getInstance().saveOrUpdateFoundItem(found, user);
			
			// 1. create the history object
			FoundHistoryObject fho = new FoundHistoryObject();
			fho.setFound(found);
			
			// 2. add the history object to the history container
			HistoryContainer history = (HistoryContainer) session.getAttribute("historyContainer");
			history.put(fho.getUniqueId(), fho);
			
			// 3. submit the history object to the trace handler
			
			// 4. clone the found and set it on the form
			eiForm.setFound(duplicateFound(found, user));
		}

		return mapping.findForward(TracingConstants.LFC_ENTER_ITEMS);
		
	}
	
	private LFFound duplicateFound(LFFound found, Agent agent) {
		LFFound toReturn = LFUtils.createLFFound(agent);
		toReturn.setFoundDate(found.getFoundDate());
		toReturn.getItem().setValue(found.getItem().getValue());
		toReturn.setCompanyId(found.getCompanyId());
		return toReturn;
	}
	
}