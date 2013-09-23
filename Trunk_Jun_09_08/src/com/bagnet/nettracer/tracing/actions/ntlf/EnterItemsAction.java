package com.bagnet.nettracer.tracing.actions.ntlf;

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
import aero.nettracer.lf.services.exception.UpdateException;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.forms.lfc.EnterItemsForm;
import com.bagnet.nettracer.tracing.history.FoundHistoryObject;
import com.bagnet.nettracer.tracing.history.HistoryContainer;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

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

		if (session.getAttribute("stationList") == null) {
			session.setAttribute("stationList", AdminUtils.getStations(null, user.getCompanycode_ID(), 0, 0));
		}
		
		LFUtils.getLists(user, session);
		
		if (eiForm.getFound() != null) {
			if (LFUtils.actionChangeSubCategory(eiForm.getFound().getItem().getCategory(), request) || LFUtils.actionChangeSubCompany(eiForm.getFound().getItem().getCategory(), request)) {
				request.setAttribute("formName", "enterItemsForm");
				return mapping.findForward(TracingConstants.AJAX_SUBCATEGORY);
			}
		}
		
		LFFound found = eiForm.getFound();
		if (found == null) {
			eiForm.setFound(LFUtils.createLFFound(user));
			eiForm.getFound().setCompanyId(user.getCompanycode_ID());
			eiForm.getFound().getItem().setValue(TracingConstants.LFC_ITEM_HIGH_VALUE);
		} else if (request.getParameter("save") != null) {
			try {
				
				LFServiceWrapper.getInstance().saveOrUpdateFoundItem(found, user);
				ActionMessage error = new ActionMessage("message.found.save.success");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				
				// 1. create the history object for quick history popup.
				FoundHistoryObject fho = new FoundHistoryObject();
				found.setLocation(StationBMO.getStation(found.getLocationId()));
				fho.setFound(found);
				fho.setDate(Calendar.getInstance().getTime()); 
				fho.setStatusDesc(TracingConstants.HIST_DESCRIPTION_ADD + " " + TracingConstants.HIST_DESCRIPTION_FOUNDITEM);
				fho.setObjectType("Found Item");
				fho.setObjectID(found.getId() + "");
				fho.setLinkURL("ntlf_create_found_item.do?foundId=");
				// 2. add the history object to the quick history container
				HistoryContainer history = (HistoryContainer) session.getAttribute("historyContainer");
				history.put(fho.getUniqueId(), fho);				
				// 3. clone the found and set it on the form
				eiForm.setFound(duplicateFound(found, user));

				request.setAttribute("stationID", eiForm.getFound().getLocation().getStation_ID());
			} catch (NonUniqueBarcodeException nube) {
				logger.error(nube, nube);
				ActionMessage error = new ActionMessage("error.failed.to.save");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} catch (UpdateException ue) {
				logger.error(ue, ue);
				ActionMessage error = new ActionMessage("error.failed.to.save");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} catch (Exception e) {
				logger.error(e, e);
				ActionMessage error = new ActionMessage("error.failed.to.save");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
		}

		return mapping.findForward(TracingConstants.NTLF_ENTER_ITEMS);
		
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