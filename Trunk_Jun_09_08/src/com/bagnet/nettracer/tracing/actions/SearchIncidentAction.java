/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.LossCodeBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.WorldTracerFile;
import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.db.wtq.WtqAmendAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqCloseAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqIncidentAction;
import com.bagnet.nettracer.tracing.db.wtq.WtqReinstateAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqSuspendAhl;
import com.bagnet.nettracer.tracing.dto.SearchIncident_DTO;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.WTIncident;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;
import com.bagnet.nettracer.wt.WorldTracerUtils;
import com.bagnet.nettracer.wt.connector.BetaWtConnector;
import com.bagnet.nettracer.wt.svc.WorldTracerService;

/**
 * @author Matt
 */
public class SearchIncidentAction extends Action {
	private static Logger logger = Logger.getLogger(SearchIncidentAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		// user clicked on a incident after search result has returned
		String incident = request.getParameter("incident");
		
		BagService bs = new BagService();
		IncidentForm theform = new IncidentForm();
		
		// user passed in worldtracer id, so find it in db or retrieve it from worldtracer
		if (request.getParameter("wt_id") != null && request.getParameter("wt_id").length() == 10) {
			Incident foundinc = WorldTracerUtils.findIncidentByWTID(request.getParameter("wt_id"));
			if (foundinc == null) {
				WorldTracerService wts = SpringUtils.getWorldTracerService();
				try {
					foundinc = wts.getIncidentForAHL(request.getParameter("wt_id"), WTStatus.ACTIVE);
				}
				catch (WorldTracerException e) {
					ActionMessages errors = new ActionMessages();
					ActionMessage error = new ActionMessage("error.wt_nostation");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				}
			} else {
				incident = foundinc.getIncident_ID();
			}
		}

		// just display the search screen if incident parameter is not passed in
		if (incident == null || incident.length() == 0) {
			

			if (request.getParameter("generateReport") == null && request.getParameter("search") == null && request.getParameter("update") == null
					&& (request.getParameter("pagination") == null || !request.getParameter("pagination").equals("1"))) {
				SearchIncidentForm newform = new SearchIncidentForm();
				//newform.setAirline(user.getStation().getCompany().getCompanyCode_ID());
				newform.setCompanycreated_ID(user.getStation().getCompany().getCompanyCode_ID());

				if (request.getParameter("ld") != null)
					newform.setItemType_ID(TracingConstants.LOST_DELAY);
				if (request.getParameter("missing") != null)
					newform.setItemType_ID(TracingConstants.MISSING_ARTICLES);
				if (request.getParameter("damage") != null)
					newform.setItemType_ID(TracingConstants.DAMAGED_BAG);
				request.setAttribute("searchIncidentForm", newform);
				
				
				return (mapping.findForward(TracingConstants.SEARCH_INCIDENT));
			}

			// search
			SearchIncidentForm daform = (SearchIncidentForm) form;
			ArrayList resultlist = null;
			
			// Create report if neccessary
			if (request.getParameter("generateReport") != null && 
					request.getParameter("outputtype") != null) {
				
				try {			
					int outputType = new Integer(request.getParameter("outputtype")).intValue();
					String reportPath = getServlet().getServletContext().getRealPath("/");
					ArrayList<Incident> incidentArray = bs.findIncident(daform, user, 0, 0, false);
					String reportFile = ReportBMO.createSearchIncidentReport(incidentArray, request, outputType, user.getCurrentlocale(), reportPath);
					request.setAttribute("reportfile", reportFile);
					request.setAttribute("outputtype", outputType);
				} catch (Exception e) {
					logger.error(e.getStackTrace());
				} 
			}


			int rowcount = -1;

			// get number of records found
			if ((resultlist = bs.findIncident(daform, user, 0, 0, true)) == null || resultlist.size() <= 0) {
				ActionMessages errors = new ActionMessages();
				ActionMessage error = new ActionMessage("error.nosearchresult");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return (mapping.findForward(TracingConstants.SEARCH_INCIDENT));
			} else {
				// get total records
				rowcount = ((Long) resultlist.get(0)).intValue();
				if (rowcount == 1) {
					// only one result returned, so send straight to the screen.
					resultlist = bs.findIncident(daform, user, 0, 0, false);
					Incident inc = (Incident) resultlist.get(0);
					incident = inc.getIncident_ID();
				} else {
					Incident ic = null;

					/** ************ pagination ************* */
					int rowsperpage = request.getParameter("rowsperpage") != null ? Integer.parseInt(request.getParameter("rowsperpage"))
							: TracingConstants.ROWS_PER_PAGE;
					if (rowsperpage < 1)
						rowsperpage = TracingConstants.ROWS_PER_PAGE;
					request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
					int totalpages = 0;

					int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request.getParameter("currpage")) : 0;
					if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1"))
						currpage++;
					if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1"))
						currpage--;

					request.setAttribute("currpage", Integer.toString(currpage));

					// find out total pages
					totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

					if (totalpages <= currpage) {
						currpage = 0;
						request.setAttribute("currpage", "0");
					}

					resultlist = bs.findIncident(daform, user, rowsperpage, currpage, false);

					if (currpage + 1 == totalpages)
						request.setAttribute("end", "1");
					if (totalpages > 1) {
						ArrayList al = new ArrayList();
						for (int i = 0; i < totalpages; i++) {
							al.add(Integer.toString(i));
						}
						request.setAttribute("pages", al);
					}

					/** ************ end of pagination ************* */

					for (int i = 0; i < resultlist.size(); i++) {
						ic = (Incident) resultlist.get(i);
						ic.set_DATEFORMAT(user.getDateformat().getFormat());
						ic.set_TIMEFORMAT(user.getTimeformat().getFormat());
						ic.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
					}

					request.setAttribute("resultlist", resultlist);

				}
			}
		}

		// go to specific MBR report based on the incident id from parameter or from
		// the search result
		if (incident != null && incident.length() > 0) {

			//special case for prompt for print receipt
			if (request.getParameter("receipt") != null) {
				//forward to prompt receipt parameters.
				return (mapping.findForward(TracingConstants.RECEIPT_PARAMS));
			}

			if (bs.findIncidentByID(incident, theform, user, TracingConstants.MISSING_ARTICLES) == null) {
				ActionMessages errors = new ActionMessages();
				ActionMessage error = new ActionMessage("error.noincident");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return (mapping.findForward(TracingConstants.SEARCH_INCIDENT));
			} else {
				// populate session
				request.setAttribute("incident", incident);
				session.setAttribute("incidentForm", theform);
				List agentassignedlist = TracerUtils.getAgentlist(theform.getStationassigned_ID());
				request.setAttribute("agentassignedlist", agentassignedlist);

				if (user.getStation().getStation_ID() != theform.getStationassigned_ID()) {
					request.setAttribute("cantmatch", "1");
				}

				request.setAttribute("LOST_DELAY_RECEIPT", Integer.toString(ReportingConstants.LOST_RECEIPT_RPT));

				Item item = theform.getItem(0, 0);

				//the company specific codes..
				List codes = LossCodeBMO.getLocaleCompanyCodes(user.getStation().getCompany().getCompanyCode_ID(), item.getItemtype_ID(), user
						.getCurrentlocale(), false, user);
				//add to the loss codes
				request.setAttribute("losscodes", codes);

				// find out what kind of incident this is

				switch (item.getItemtype_ID()) {
				case TracingConstants.LOST_DELAY:
					if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_MISHANDLED_BAG, user))
						theform.setReadonly(1);
					if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_LD, user))
						theform.setAllow_remark_update(1);
					request.setAttribute("lostdelay", "1");
					WtqIncidentAction pendingAction = WorldTracerQueueUtils.findPendingIncidentAction(incident);
					if(pendingAction != null) {
						if(pendingAction instanceof WtqCreateAhl) {
							request.setAttribute("pendingWtAction", TracingConstants.WT_PENDING_CREATE);
						}
						else if(pendingAction instanceof WtqAmendAhl) {
							request.setAttribute("pendingWtAction", TracingConstants.WT_PENDING_AMEND);
						}
						else if(pendingAction instanceof WtqSuspendAhl) {
							request.setAttribute("pendingWtAction", TracingConstants.WT_PENDING_SUSPEND);
						}
						else if(pendingAction instanceof WtqReinstateAhl) {
							request.setAttribute("pendingWtAction", TracingConstants.WT_PENDING_REINSTATE);
						}
						else if(pendingAction instanceof WtqCloseAhl) {
							request.setAttribute("pendingWtAction", TracingConstants.WT_PENDING_CLOSE);
						}
					}
					return (mapping.findForward(TracingConstants.LD_MAIN));
				case TracingConstants.DAMAGED_BAG:
					if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_DAMAGED_BAG, user))
						theform.setReadonly(1);
					if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_DA, user))
						theform.setAllow_remark_update(1);
					request.setAttribute("damaged", "1");
					return (mapping.findForward(TracingConstants.DAMAGED_MAIN));
				case TracingConstants.MISSING_ARTICLES:
					if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_MISSING_ARTICLES, user))
						theform.setReadonly(1);
					if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_MS, user))
						theform.setAllow_remark_update(1);
					request.setAttribute("missing", "1");
					return (mapping.findForward(TracingConstants.MISSING_MAIN));
				default:
					return (mapping.findForward(TracingConstants.SEARCH_INCIDENT));
				}
			}
		}

		return (mapping.findForward(TracingConstants.SEARCH_INCIDENT));

	}
}