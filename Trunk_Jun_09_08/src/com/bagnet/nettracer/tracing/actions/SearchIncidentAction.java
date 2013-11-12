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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.LossCodeBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.DeliveryInstructions;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.db.wtq.WtqAmendAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqCloseAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqIncidentAction;
import com.bagnet.nettracer.tracing.db.wtq.WtqReinstateAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqSuspendAhl;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.history.HistoryContainer;
import com.bagnet.nettracer.tracing.history.IncidentHistoryObject;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.HistoryUtils;
import com.bagnet.nettracer.tracing.utils.MBRActionUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;
import com.bagnet.nettracer.wt.WorldTracerRecordNotFoundException;
import com.bagnet.nettracer.wt.WorldTracerUtils;
import com.bagnet.nettracer.wt.connector.WorldTracerWebService;
import com.bagnet.nettracer.wt.svc.WorldTracerService;

/**
 * @author Matt
 */
public class SearchIncidentAction extends Action {
	private static Logger logger = Logger.getLogger(SearchIncidentAction.class);

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
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
		if (incident != null) {
			incident = incident.toUpperCase();
		}
		
		BagService bs = new BagService();
		IncidentForm theform = new IncidentForm();
		Incident foundinc = null;
		
		// Getting the AssistDeviceType List for Incidents
		List list = new ArrayList();
		if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_DISABLED_BAG, user) 
				&& session.getAttribute("assistDeviceList") == null) {
			list=new ArrayList(MBRActionUtils.getAssistDeviceTypes());
			if(list!=null)
				session.setAttribute("assistDeviceList", list);
		}
		
		// user passed in worldtracer id, so find it in db or retrieve it from worldtracer
		if (request.getParameter("wt_id") != null && request.getParameter("wt_id").length() == 10) {
			foundinc = WorldTracerUtils.findIncidentByWTID(request.getParameter("wt_id"));
			if (foundinc == null) {
				WorldTracerService wts = SpringUtils.getWorldTracerService();
				try {
					wts.getWtConnector().initialize();
					foundinc = wts.getIncidentForAHL( user, request.getParameter("wt_id"), WTStatus.ACTIVE, WorldTracerWebService.getBasicDto(session));
				}
				catch(WorldTracerRecordNotFoundException ex) {
					ActionMessages errors = new ActionMessages();
					ActionMessage error = new ActionMessage("error.wt.no.ahl");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				}
				catch (WorldTracerException e) {
					logger.error("Unable to import incident:", e);
					ActionMessages errors = new ActionMessages();
					ActionMessage error = new ActionMessage("error.wt_nostation");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				} finally {
					wts.getWtConnector().logout();
				}
			} else {
				incident = foundinc.getIncident_ID();
			}
		}

		// just display the search screen if incident parameter is not passed in
		if ((incident == null || incident.length() == 0) && foundinc == null) {
			

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
					
					
					ArrayList countArray = bs.findIncident(daform, user, 0, 0, true, true);
					int rc = ((Long) countArray.get(0)).intValue();
					int maxRc = TracerProperties.getMaxReportRows(user.getStation().getCompany().getCompanyCode_ID()); 
						

					if (rc < maxRc) {
						ArrayList<Incident> incidentArray = bs.findIncident(daform, user, 0, 0, false, true);
						ReportBMO rbmo = new ReportBMO(request);
						String reportFile = ReportBMO.createSearchIncidentReport(incidentArray, request, outputType, user.getCurrentlocale(), reportPath, rbmo);
						
						if (rbmo.getErrormsg() != null) {
							ActionMessages errors = new ActionMessages();
							ActionMessage error = new ActionMessage(rbmo.getErrormsg());
							errors.add(ActionMessages.GLOBAL_MESSAGE, error);
							saveMessages(request, errors);
						} else {
							request.setAttribute("reportfile", reportFile);
							request.setAttribute("outputtype", outputType);
						}
					} else {
						ActionMessages errors = new ActionMessages();
						ActionMessage error = new ActionMessage("error.maxdata");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
					}
				} catch (Exception e) {
					logger.error(e.getStackTrace());
				} 
			}


			int rowcount = -1;

			// get number of records found
			
			if ((resultlist = bs.findIncident(daform, user, 0, 0, true, true)) == null || resultlist.size() <= 0) {
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
					int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
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

					resultlist = bs.findIncident(daform, user, rowsperpage, currpage, false, true);

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
		if ((incident != null && incident.length() > 0) || foundinc != null) {
			Incident inc = foundinc;

			//special case for prompt for print receipt
			if (request.getParameter("receipt") != null) {
				//forward to prompt receipt parameters.
				if (request.getParameter("new") != null) {
					inc = bs.findIncidentByID(incident, theform, user, TracingConstants.MISSING_ARTICLES);
					bs.populateIncidentFormFromIncidentObj(null, theform, user, TracingConstants.LOST_DELAY, new IncidentBMO(), inc, true);
					session.setAttribute("incidentForm", theform);
				}
				return (mapping.findForward(TracingConstants.RECEIPT_PARAMS));
			}

			if (foundinc == null) {
				inc = bs.findIncidentByID(incident, theform, user, TracingConstants.MISSING_ARTICLES);
				session.setAttribute("incidentObj", inc);
			} else {
				IncidentBMO iBMO = new IncidentBMO();
				TracerUtils.populateIncident(theform, request, TracingConstants.LOST_DELAY);
				theform = (IncidentForm)session.getAttribute("incidentForm");
				theform.setFaultcompany_id(user.getCompanycode_ID());
				bs.populateIncidentFormFromIncidentObj(null, theform, user, TracingConstants.LOST_DELAY, iBMO, foundinc, true);
				theform.setCreatedate(TracerDateTime.getGMTDate());
				theform.setCreatetime(TracerDateTime.getGMTDate());
			}
			
			if (inc == null) {
				ActionMessages errors = new ActionMessages();
				ActionMessage error = new ActionMessage("error.noincident");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return (mapping.findForward(TracingConstants.SEARCH_INCIDENT));
			} else {
				// populate session
				int itemType = inc.getItemtype_ID();
				List<ActionMessage> lockErrors = SpringUtils.getLockFile().getLockActionMessages(inc.getIncident_ID(), user);
				ActionMessages errors = new ActionMessages();
				if(lockErrors != null){
					for(ActionMessage lockError:lockErrors){
						errors.add(ActionMessages.GLOBAL_MESSAGE, lockError);
						saveMessages(request, errors);
					}
				}
				request.setAttribute("incident", incident);
				session.setAttribute("incidentForm", theform);
				List agentassignedlist = TracerUtils.getAgentlist(theform.getStationassigned_ID());

				if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_WT_OTHER_CARRIER,user)){
					TracerUtils.populateWtCompanyLists(session, user.getCompanycode_ID());
				}
				request.setAttribute("agentassignedlist", agentassignedlist);

				if (user.getStation().getStation_ID() != theform.getStationassigned_ID()) {
					request.setAttribute("cantmatch", "1");
				}

				request.setAttribute("LOST_DELAY_RECEIPT", Integer.toString(ReportingConstants.LOST_RECEIPT_RPT));

				//the company specific codes..
				List codes = LossCodeBMO.getCompanyCodes(user.getStation().getCompany().getCompanyCode_ID(), itemType, false, user);
				//add to the loss codes
				request.setAttribute("losscodes", codes);
				
				
				List faultstationlist = null;
				List faultCompanyList = null;

				//Populate incident form with existing information
				if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LOSS_CODES_BAG_LEVEL, user)){
					IncidentBMO.fillFormWithExistingData(inc, theform);
				}
				
				// find out what kind of incident this is
				IncidentHistoryObject IHO=new IncidentHistoryObject();
				IHO.setIncident(inc);
				IHO.setObjectID(inc.getIncident_ID());
				IHO.setLinkURL("searchIncident.do?incident=");
				
				HistoryContainer HCL;
				switch (itemType) {
				case TracingConstants.LOST_DELAY:
					if(UserPermissions.hasLimitedSavePermission(user, inc)) {
						faultstationlist = UserPermissions.getLimitedSaveStations(user, theform.getIncident_ID());
						faultCompanyList = new ArrayList();
						faultCompanyList.add(user.getStation().getCompany());
					} else if (UserPermissions.hasLimitedFaultAirlinesByType(user, inc.getItemtype().getItemType_ID())) {
						faultstationlist = TracerUtils.getStationList(theform.getFaultcompany_id());
						faultCompanyList = new ArrayList();
						faultCompanyList.add(user.getStation().getCompany());
					} else {
						faultstationlist = TracerUtils.getStationList(theform.getFaultcompany_id());
						faultCompanyList = (List) request.getSession().getAttribute("companylistByName");
					}
					request.setAttribute("faultstationlist", faultstationlist);
					request.setAttribute("faultCompanyList", faultCompanyList);
					if(!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_MISHANDLED_BAG, user))
						theform.setReadonly(1);
					if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_LD, user))
						theform.setAllow_remark_update(1);
					request.setAttribute("lostdelay", "1");
					if(inc.getDeliveryInstructions()==null)
					{
						DeliveryInstructions DI=new DeliveryInstructions();
						DI.setInstructions("");
						HibernateUtils.saveNew(DI);
						theform.setDeliveryInstructions(DI);
					}
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
					saveToken(request);
					IHO.setObjectType(TracingConstants.HIST_DESCRIPTION_DELAYED+" "+TracingConstants.HIST_DESCRIPTION_INCIDENT);
					IHO.setStatusDesc(TracingConstants.HIST_DESCRIPTION_LOAD+" "+TracingConstants.HIST_DESCRIPTION_DELAYED+" "+TracingConstants.HIST_DESCRIPTION_INCIDENT);
					HistoryUtils.AddToHistoryContainer(session, IHO, null);

					MBRActionUtils.createIssuanceLists(request, inc.getStationassigned(), TracingConstants.LOST_DELAY, inc.getIssuanceItemIncidents());
					
					return (mapping.findForward(TracingConstants.LD_MAIN));
				case TracingConstants.DAMAGED_BAG:
					if(UserPermissions.hasLimitedSavePermission(user, inc)) {
						faultstationlist = UserPermissions.getLimitedSaveStations(user, theform.getIncident_ID());
						faultCompanyList = new ArrayList();
						faultCompanyList.add(user.getStation().getCompany());
					} else if (UserPermissions.hasLimitedFaultAirlinesByType(user, inc.getItemtype().getItemType_ID())) {
						faultstationlist = TracerUtils.getStationList(theform.getFaultcompany_id());
						faultCompanyList = new ArrayList();
						faultCompanyList.add(user.getStation().getCompany());
					} else {
						faultstationlist = TracerUtils.getStationList(theform.getFaultcompany_id());
						faultCompanyList = (List) request.getSession().getAttribute("companylistByName");
					}
					request.setAttribute("faultstationlist", faultstationlist);
					request.setAttribute("faultCompanyList", faultCompanyList);
					if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_DAMAGED_BAG, user))
						theform.setReadonly(1);
					if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_DA, user))
						theform.setAllow_remark_update(1);
					request.setAttribute("damaged", "1");
					saveToken(request);

					IHO.setObjectType(TracingConstants.HIST_DESCRIPTION_DAMAGED+" "+TracingConstants.HIST_DESCRIPTION_INCIDENT);
					IHO.setStatusDesc(TracingConstants.HIST_DESCRIPTION_LOAD+" "+TracingConstants.HIST_DESCRIPTION_DAMAGED+" "+TracingConstants.HIST_DESCRIPTION_INCIDENT);
					HistoryUtils.AddToHistoryContainer(session, IHO, null);

					MBRActionUtils.createIssuanceLists(request, inc.getStationassigned(), TracingConstants.DAMAGED_BAG, inc.getIssuanceItemIncidents());
					
					return (mapping.findForward(TracingConstants.DAMAGED_MAIN));
				case TracingConstants.MISSING_ARTICLES:
					if(UserPermissions.hasLimitedSavePermission(user, inc)) {
						faultstationlist = UserPermissions.getLimitedSaveStations(user, theform.getIncident_ID());
						faultCompanyList = new ArrayList();
						faultCompanyList.add(user.getStation().getCompany());
					} else if (UserPermissions.hasLimitedFaultAirlinesByType(user, inc.getItemtype().getItemType_ID())) {
						faultstationlist = TracerUtils.getStationList(theform.getFaultcompany_id());
						faultCompanyList = new ArrayList();
						faultCompanyList.add(user.getStation().getCompany());
					} else {
						faultstationlist = TracerUtils.getStationList(theform.getFaultcompany_id());
						faultCompanyList = (List) request.getSession().getAttribute("companylistByName");
					}
					request.setAttribute("faultstationlist", faultstationlist);
					request.setAttribute("faultCompanyList", faultCompanyList);
					if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_MISSING_ARTICLES, user))
						theform.setReadonly(1);
					if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_MS, user))
						theform.setAllow_remark_update(1);
					request.setAttribute("missing", "1");
					saveToken(request);

					IHO.setObjectType(TracingConstants.HIST_DESCRIPTION_MISSING+" "+TracingConstants.HIST_DESCRIPTION_INCIDENT);
					IHO.setStatusDesc(TracingConstants.HIST_DESCRIPTION_LOAD+" "+TracingConstants.HIST_DESCRIPTION_MISSING+" "+TracingConstants.HIST_DESCRIPTION_INCIDENT);
					HistoryUtils.AddToHistoryContainer(session, IHO, null);

					MBRActionUtils.createIssuanceLists(request, inc.getStationassigned(), TracingConstants.MISSING_ARTICLES, inc.getIssuanceItemIncidents());
					
					return (mapping.findForward(TracingConstants.MISSING_MAIN));
				default:
					return (mapping.findForward(TracingConstants.SEARCH_INCIDENT));
				}
			}
		}

		return (mapping.findForward(TracingConstants.SEARCH_INCIDENT));

	}
}