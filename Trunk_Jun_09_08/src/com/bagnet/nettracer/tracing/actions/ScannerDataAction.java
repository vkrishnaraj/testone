/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.bagnet.nettracer.datasources.ScannerDataSource;
import com.bagnet.nettracer.exceptions.BagtagException;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.dto.ScannerDTO;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;

/**
 * @author Byron
 * 
 */
public class ScannerDataAction extends Action {
	
	private static Logger logger = Logger.getLogger(ScannerDataAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		if (session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		Agent user = (Agent) session.getAttribute("user");
		Date startDate = null;
		Date endDate = null;

		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)) {
			return (mapping.findForward(TracingConstants.LOGON));
		}		

		DynaActionForm dynaForm = (DynaActionForm) form;
		ActionMessages errors = new ActionMessages();
		boolean informationProvided = false;
		
		// If an on-hand or incident was passed in
		if (!((String) dynaForm.get("incidentId")).equals("") || !((String) dynaForm.get("ohdId")).equals("")) {
			
			informationProvided = true;
			
			// The default date parameters will be based on the create date
			Date temporaryDate = null;
			
			if (!((String) dynaForm.get("incidentId")).equals("")) {
				String incidentId = (String) dynaForm.get("incidentId");
				IncidentBMO iBMO = new IncidentBMO();
				Incident incident = iBMO.findIncidentByID(incidentId);
				
				temporaryDate = incident.getCreatedate();
				
				// If the departure date is set, we will use it as the basis for date parameters 
				if (incident.getItinerary_list().size() > 0) {
					if (((Itinerary)incident.getItinerary_list().get(0)).getDepartdate() != null) {
						temporaryDate = ((Itinerary)incident.getItinerary_list().get(0)).getDepartdate();
					}
				}
				
			} else if (!((String) dynaForm.get("ohdId")).equals("")) {
				
				informationProvided = true;
				
				String ohdId = (String) dynaForm.get("ohdId");
				OhdBMO oBMO = new OhdBMO();
				OHD ohd = oBMO.findOHDByID(ohdId);
				
				temporaryDate = ohd.getFounddate();
				
				// If the departure date is set, we will use it as the basis for date parameters 
				if (ohd.getItinerary().size() > 0) {
					ArrayList<OHD_Itinerary> list = new ArrayList<OHD_Itinerary>(ohd.getItinerary());
					if (list.get(0).getDepartdate() != null) {
						temporaryDate = list.get(0).getDepartdate();
					}
				}
			}
			
			// Convert the temporary date into the default date ranges. 
			Calendar cal = new GregorianCalendar();
			
			int numberDaysBefore = user.getStation().getCompany().getVariable().getScannerDefaultBack();
			int numberDaysAfter = user.getStation().getCompany().getVariable().getScannerDefaultForward();
			
			cal.setTime(temporaryDate);
			cal.add(Calendar.DAY_OF_MONTH, -numberDaysBefore);
			startDate = cal.getTime();
			
			
			cal.setTime(temporaryDate);
			cal.add(Calendar.DAY_OF_MONTH, numberDaysAfter);
			endDate = cal.getTime();

			// Store the dates in the form
			dynaForm.set("startDate", DateUtils.formatDate(startDate, user.getDateformat().getFormat(), null, null));
			dynaForm.set("endDate", DateUtils.formatDate(endDate, user.getDateformat().getFormat(), null, null));
		}
		
		
		if (request.getParameter("search") != null || request.getParameter("generateReport") != null || informationProvided) {

			// Get form values
			String bagTagNumber = (String) dynaForm.get("bagTagNumber");
			startDate = DateUtils.convertToDate((String) dynaForm.get("startDate"), user.getDateformat().getFormat(), null);
			endDate = DateUtils.convertToDate((String) dynaForm.get("endDate"), user.getDateformat().getFormat(), null);
			
			if (startDate == null) {
				ActionMessage error = new ActionMessage("scanner.error.endDate");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return (mapping.findForward(TracingConstants.FORWARD_SCANNER_DATA));
			} else if (endDate == null) {
				ActionMessage error = new ActionMessage("scanner.error.endDate");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return (mapping.findForward(TracingConstants.FORWARD_SCANNER_DATA));
			}
			
			try {
				bagTagNumber = (StringUtils.equalsIgnoreCase("b6", user.getCompanycode_ID())) ? LookupAirlineCodes.getFullBagTag(bagTagNumber) : LookupAirlineCodes.getTwoCharacterBagTag(bagTagNumber);				
			} catch (BagtagException e) {
			}
			dynaForm.set("bagTagNumber", bagTagNumber);
			
			// Obtain the appropriate scannerDataSource
			ScannerDataSource scannerDataSource =	(ScannerDataSource) SpringUtils.getBean(SpringUtils.SCANNER_DATA_SOURCE);
			ScannerDTO dto = null;
			try{
				dto = scannerDataSource.getScannerData(startDate, endDate, bagTagNumber);
			}catch(Exception e){
				logger.error("Exception thrown with ScannerDTO: " + bagTagNumber, e);
				
				ActionMessage error = new ActionMessage("scanner.communicationError.unknownerror");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				
				return mapping.findForward(TracingConstants.FORWARD_SCANNER_DATA);
			}
			
			request.setAttribute("resultList", dto.getScannerDataDTOs());
			
			if (request.getParameter("generateReport") != null && 
					request.getParameter("outputtype") != null) {
				
				try {
					String reportPath = getServlet().getServletContext().getRealPath("/");
					int outputType = new Integer(request.getParameter("outputtype")).intValue();
					String reportFile = null;
					
					reportFile = ReportBMO.createSearchScandataReport(dto, request, outputType, user.getCurrentlocale(), reportPath);
					
					request.setAttribute("reportfile", reportFile);
					request.setAttribute("outputtype", outputType);
					
				} catch (Exception e) {
					logger.error(e.getStackTrace());
				} 
			}
			
			// Add errors if any issues were detected in populating the list.
			if (dto.getErrorResponse() != null) {
				ActionMessage error = new ActionMessage(dto.getErrorResponse());
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
			
			return (mapping.findForward(TracingConstants.FORWARD_SCANNER_DATA));
		}
			
		// All else fails, set date values to yesterday and today
		
		GregorianCalendar a = new GregorianCalendar();
		Date today = a.getTime();
		a.add(Calendar.DATE, -1);
		Date yesterday = a.getTime();
		
		dynaForm.set("startDate", DateUtils.formatDate(yesterday, user.getDateformat().getFormat(), null, null));
		dynaForm.set("endDate", DateUtils.formatDate(today, user.getDateformat().getFormat(), null, null));
		
		return (mapping.findForward(TracingConstants.FORWARD_SCANNER_DATA));
	}
}