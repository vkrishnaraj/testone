package com.bagnet.nettracer.tracing.actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.bmo.ExpensePayoutBMO;
import com.bagnet.nettracer.tracing.bmo.ForwardNoticeBMO;
import com.bagnet.nettracer.tracing.bmo.PaxCommunicationBMO;
import com.bagnet.nettracer.tracing.bmo.ProactiveNotificationBMO;
import com.bagnet.nettracer.tracing.bmo.SpecialFlagBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.OnlineClaimsDao;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.ForwardNotice;
import com.bagnet.nettracer.tracing.db.GroupComponentPolicy;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.ProactiveNotification;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.taskmanager.GeneralTask;
import com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask;
import com.bagnet.nettracer.tracing.db.taskmanager.TwoDayTask;
import com.bagnet.nettracer.tracing.dto.ActivityDTO;
import com.bagnet.nettracer.tracing.dto.PcnSearchDTO;
import com.bagnet.nettracer.tracing.forms.ClaimsToBeProcessedForm;
import com.bagnet.nettracer.tracing.forms.CreatedInterimExpenseRequestForm;
import com.bagnet.nettracer.tracing.forms.InterimExpenseRequestForm;
import com.bagnet.nettracer.tracing.forms.SearchExpenseForm;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.forms.ViewIncomingRequestForm;
import com.bagnet.nettracer.tracing.forms.ViewMassOnHandsForm;
import com.bagnet.nettracer.tracing.forms.ViewRequestForm;
import com.bagnet.nettracer.tracing.forms.ViewTemporaryOnHandsForm;
import com.bagnet.nettracer.tracing.forms.ViewTemporaryReportsForm;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.ExpenseUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.MatchUtils;
import com.bagnet.nettracer.tracing.utils.MessageUtils;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;
import com.bagnet.nettracer.tracing.utils.TaskUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.taskmanager.MorningDutiesUtil;

public class LogonAction extends Action {

	private String foobar;

	/**
	 * Process the specified HTTP request, and create the corresponding HTTP
	 * response (or forward to another web component that will create it). Return
	 * an <code>ActionForward</code> instance describing where and how control
	 * should be forwarded, or <code>null</code> if the response has already
	 * been completed.
	 * 
	 * @param mapping
	 *          The ActionMapping used to select this instance
	 * @param form
	 *          The optional ActionForm bean for this request (if any)
	 * @param request
	 *          The HTTP request we are processing
	 * @param response
	 *          The HTTP response we are creating
	 * 
	 * @exception Exception
	 *              if business logic throws an exception
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// Extract attributes we will need
		Locale locale = getLocale(request);
		Agent agent = null;
		HttpSession session = request.getSession();

		request.setAttribute("maintask", "1");

		if (request.getParameter("taskmanager") != null) {
			if (session.getAttribute("user") == null) {
				response.sendRedirect("logoff.do");
				return null;
			}

			taskManagerSetup(session, request);
			//Forward control to the specified success URI
			return (mapping.findForward("success"));
		}

		// Validate the request parameters specified by the user
		ActionMessages errors = new ActionMessages();
		String username = (String) PropertyUtils.getSimpleProperty(form, "username");
		String password = (String) PropertyUtils.getSimpleProperty(form, "password");
		String companyCode = request.getParameter("companyCode");

		if (username.length() == 0 && password.length() == 0) {
			session.invalidate();
			response.addHeader("Pragma", "No-cache");
			response.addHeader("Cache-Control", "no-cache");
			response.addDateHeader("Expires", -1);
			return mapping.findForward(TracingConstants.LOGON);
		}

		agent = SecurityUtils.authUser(username, password, companyCode, 0, errors);

		// Report any errors we have discovered back to the original form
		if (!errors.isEmpty()) {
			saveMessages(request, errors);
			PropertyUtils.setSimpleProperty(form, "username", foobar);
			response.addHeader("Pragma", "No-cache");
			response.addHeader("Cache-Control", "no-cache");
			response.addDateHeader("Expires", -1);
			return mapping.findForward(TracingConstants.LOGON);
		}

		// check to see if password needs to be reset
		if (agent.isReset_password() || !SecurityUtils.isPolicyAcceptablePassword(companyCode, password, username, request, true)) {
			session.setAttribute("usertemp", agent);
			response.addHeader("Pragma", "No-cache");
			response.addHeader("Cache-Control", "no-cache");
			response.addDateHeader("Expires", -1);
			return mapping.findForward(TracingConstants.PASS_RESET);
		}

		int expiredays = agent.getStation().getCompany().getVariable().getPass_expire_days();
		if (expiredays > 0) {
			Date lastreset = agent.getLast_pass_reset_date();
			if (lastreset == null || DateUtils.formatDate(lastreset, TracingConstants.DB_DATEFORMAT, null, null).equals("0000-00-00")) {
				// first time logon, set it
				agent.setLast_pass_reset_date(new Date());
			} else {
				long nowtime = ((new Date()).getTime() / (3600000 * 24));
				long lastresettime = (lastreset.getTime() / (3600000 * 24));
				if ((nowtime - lastresettime) > expiredays) {
					// forward to reset page
					session.setAttribute("usertemp", agent);
					response.addHeader("Pragma", "No-cache");
					response.addHeader("Cache-Control", "no-cache");
					response.addDateHeader("Expires", -1);
					return mapping.findForward(TracingConstants.PASS_RESET);
				}
			}
		}

		// Save our logged-in user in the session
		session.setAttribute("lastupdate", new Date());
		//session.setMaxInactiveInterval(agent.getTimeout() * 60);
		session.setAttribute("user", agent);

		// write to database
		agent.setLast_logged_on(TracerDateTime.getGMTDate());
		agent.setIs_online(1);
		SecurityUtils.updateAgentLogin(agent, null, 1, true);

		// load drop down ui properties into session
		Properties properties = new Properties();
		try {
			ServletContext sc = getServlet().getServletContext();
			String configfilepath = sc.getRealPath("/") + "/WEB-INF/classes/";
			properties.load(new FileInputStream(configfilepath + "custom_ui.properties"));
			session.setAttribute("first_row_menu_text", properties.getProperty("first_row_menu_text"));
			session.setAttribute("first_row_bg", properties.getProperty("first_row_bg"));
			session.setAttribute("drop_down_text", properties.getProperty("drop_down_text"));
			session.setAttribute("drop_down_text_hilite", properties.getProperty("drop_down_text_hilite"));
			session.setAttribute("drop_down_bg", properties.getProperty("drop_down_bg"));
			session.setAttribute("drop_down_bg_hilite", properties.getProperty("drop_down_bg_hilite"));

		} catch (IOException e) {
			// unable to get config
			response.addHeader("Pragma", "No-cache");
			response.addHeader("Cache-Control", "no-cache");
			response.addDateHeader("Expires", -1);
			return mapping.findForward(TracingConstants.LOGON);
		}

		// Remove the obsolete form bean
		if (mapping.getAttribute() != null) {
			if ("request".equals(mapping.getScope()))
				request.removeAttribute(mapping.getAttribute());
			else
				session.removeAttribute(mapping.getAttribute());
		}

		LinkedHashMap map = UserPermissions.renderApplicationLinks(agent);
		session.setAttribute("menu_links", map);
		//Setup the activity list
		if (map.get("Task Manager") != null) {
			taskManagerSetup(session, request);
		}
		// prepopulate list
		TracerUtils.populateLists(session);

		if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CBRO_VIEW, agent)) {
			//Setup cbroStationid for the first time
			session.setAttribute("cbroStationID", "" + agent.getStation().getStation_ID());
		}

		// Forward control to the specified success URI
		return (mapping.findForward("success"));
	}

	//Task manager stuff
	private void taskManagerSetup(HttpSession session, HttpServletRequest request) {

		Agent agent = (Agent) session.getAttribute("user");

		ArrayList<GroupComponentPolicy> taskList = (ArrayList<GroupComponentPolicy>) session.getAttribute("userTaskList");
		if (taskList == null) {
			taskList = UserPermissions.getTaskManagerComponents(agent);
			session.setAttribute("userTaskList", taskList);
		}

		//	check if the cbro agent
		if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CBRO_VIEW, agent)) {
			if (request.getParameter("cbroStation") != null && !((String) request.getParameter("cbroStation")).equals("")) {
				Station station = StationBMO.getStation((String) request.getParameter("cbroStation"));
				if (station.getCompany().getCompanyCode_ID().equals(agent.getCompanycode_ID())) {
					session.setAttribute("cbroStationID", request.getParameter("cbroStation"));
					if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CBRO_MGMT, agent)) {
						agent.setStation(StationBMO.getStationById(Integer.parseInt(request.getParameter("cbroStation")), agent.getCompanycode_ID()));
					}
				}
			}
		}
		
		//check if the agent has any outstanding MorningDutiesTask
		//TODO this is a US Air specific task, check to see of this can be a General Task function
		
		
		ArrayList <GeneralTask> alertList = new ArrayList<GeneralTask>();
		
		MorningDutiesTask hasTask = (MorningDutiesTask)MorningDutiesUtil.hasAssignedTask(agent);
		if(hasTask != null){
			session.setAttribute("sessionTaskContainer", hasTask);;
			alertList.add(hasTask);
		}
		
		request.setAttribute("taskManagerAlerts", alertList);
		

		if (taskList != null && agent != null) {
			ArrayList list = new ArrayList();

			Station s = null;
			if (session.getAttribute("cbroStationID") != null) {
				s = StationBMO.getStation((String) session.getAttribute("cbroStationID"));
			} else {
				s = agent.getStation();
			}
			for (int i = 0; i < taskList.size(); ++i) {
				GroupComponentPolicy policy = (GroupComponentPolicy) taskList.get(i);
				ActivityDTO dto = new ActivityDTO();

				String key = policy.getComponent().getComponent_Name();
				dto.setActivityinfo(key);
				dto.setActivityloc(policy.getComponent().getComponent_action_link());
				dto.setActivityinfomenu(key);
				dto.setGroup(policy.getComponent().getSort_group());
				dto.setHighPriority(false);

				int entries = 0;
				if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_OTHER_TASKS)) {
					int x = TaskUtils.getActiveTaskCount(s.getStation_ID(), true);
					if (x != -1)
						entries = x;
				} else if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_PCN)) {
					PcnSearchDTO pcnDto = new PcnSearchDTO();
					pcnDto.setDestinationStation(agent.getStation().getStation_ID());
					pcnDto.setStatus_ID(ProactiveNotification.STATUS_OPEN);
					int x = ProactiveNotificationBMO.getCount(pcnDto, agent);
					if (x != -1)
						entries = x;
				} else if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_FORWARD_NOTICES)) {
					int x = ForwardNoticeBMO.getForwardsForStationCount(s, ForwardNotice.OPEN_STATUS);
					if (x != -1)
						entries = x;
				} else {
					if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_FORWARD_BAGS_TO_LZ)) {
						if (!s.isThisOhdLz()) {
							int x = OHDUtils.getBagsToLZedCount(s.getStation_ID(), true);
							if (x != -1)
								entries = x;
						}
					} else {
						if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_INCOMING_BAGS)) {
							int x = OHDUtils.getIncomingBagsCount(s.getStation_ID(), new ViewIncomingRequestForm(), true);
							if (x != -1)
								entries = x;
						} else {
							if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_INCOMING_INCIDENTS)) {
								int x = OHDUtils.getIncomingIncidentsCount(s.getStation_ID(), new SearchIncidentForm(), true);
								if (x != -1)
									entries = x;
							} else {
								if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_VIEW_CREATED_REQUESTS)) {
									int x = OHDUtils.getCreatedRequestsCount(s.getStation_ID(), true);
									if (x != -1)
										entries = x;
								} else {
									if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_VIEW_MATCHES)) {
										entries = MatchUtils.getMatchRowCount(false, s, null, null, null, true);
										if (entries == -1)
											entries = 0;
									} else {
										if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_VIEW_INCOMING_REQUEST)) {
											int x = OHDUtils.getRequestsCount(s.getStation_ID(), new ViewRequestForm(), true);
											if (x != -1)
												entries = x;
										} else {
											if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_MY_INBOX)) {
												int x = MessageUtils.getMessagesCount("" + s.getStation_ID(), -1, null, null, null, null, null, null, true);
												if (x != -1)
													entries = x;
											} else {
												if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_TEMPORARY_ON_HAND)) {
													int x = ((Long) OHDUtils.getOHDs(agent, "", new ViewTemporaryOnHandsForm(), "" + TracingConstants.OHD_STATUS_TEMP, "" + s.getStation_ID(), 0, 0, true, true).get(0))
													    .intValue();
													if (x != -1)
														entries = x;
												} else {
													if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_CLAIMS_TO_BE_PROCESSED)) {
														int x = IncidentUtils.retrieveClaimsListCount(new ClaimsToBeProcessedForm(), agent, true);
														if (x != -1)
															entries = x;
													} else {
														if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_BAGS_TO_BE_DELIVERED)) {
															BagService bs = new BagService();
															SearchIncidentForm daform = new SearchIncidentForm();
															daform.setStatus_ID(TracingConstants.OHD_STATUS_TO_BE_DELIVERED);
															daform.setCompanycode_ID(s.getCompany().getCompanyCode_ID());
															daform.setStationassigned_ID(s.getStation_ID());
															List resultlist = bs.findOnHandBagsBySearchCriteria(daform, agent, 0, 0, true, false, true);
															if (resultlist != null && resultlist.size() > 0)
																entries = ((Long) resultlist.get(0)).intValue();
														} else {
															if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_BAGS_IN_STATION)) {
																BagService bs = new BagService();
																SearchIncidentForm daform = new SearchIncidentForm();
																daform.setCompanycode_ID(s.getCompany().getCompanyCode_ID());
																daform.setStationassigned_ID(s.getStation_ID());
																List resultlist = bs.findOnHandBagsBySearchCriteria(daform, agent, 0, 0, true, true, true);
																if (resultlist != null && resultlist.size() > 0)
																	entries = ((Long) resultlist.get(0)).intValue();

															} else {
																if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_TEMPORARY_REPORTS)) {
																	int x = ((Long) IncidentUtils
																	    .getIncidents(agent, "", new ViewTemporaryReportsForm(), "" + TracingConstants.MBR_STATUS_TEMP, "" + s.getStation_ID(), 0, 0, true, true).get(0)).intValue();
																	;
																	if (x != -1)
																		entries = x;
																} else {
																	if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_MASS_ON_HANDS)) {
																		int x = ((Long) OHDUtils.getOHDsByTypeStatus(agent, "", "" + TracingConstants.MASS_OHD_TYPE, new ViewMassOnHandsForm(), "" + s.getStation_ID(), 0, 0, true, true)
																		    .get(0)).intValue();
																		;
																		if (x != -1)
																			entries = x;
																	} else {
																		if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_INTERIM_EXPENSE_REQUESTS)) {
																			int x = ((Long) ExpenseUtils.getPendingInterimExpenses(true, s.getCompany().getCompanyCode_ID(), new InterimExpenseRequestForm(), "", 0, 0, true).get(0))
																			    .intValue();
																			;
																			if (x != -1)
																				entries = x;
																		} else {
																			if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_CREATED_INTERIM_EXPENSE_REQUESTS)) {
																				int x = ((Long) ExpenseUtils.getCreateInterimExpenses(true, s.getStation_ID(), new CreatedInterimExpenseRequestForm(), "", 0, 0, true).get(0)).intValue();
																				;
																				if (x != -1)
																					entries = x;
																			} else {
																				if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_APPROVED_EXPENSES)) {
																					SearchExpenseForm form = new SearchExpenseForm();
																					form.setStatusId(TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED);
																					int x = ExpensePayoutBMO.countExpenses(form, agent);
																					if (x != -1) {
																						entries = x;
																					}
																				} else if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_PAX_COMMUNICATION)) {
																					int x = PaxCommunicationBMO.getPaxMessagesCount("" + s.getStation_ID(), "NEW", null, null, null, null, null, null, true);
																					int highPriority = PaxCommunicationBMO.getHighPriorityPaxMessagesCount("" + s.getStation_ID(), "NEW");
																					if (highPriority > 0) {
																						dto.setHighPriority(true);
																						dto.setHighPriorityNumber(highPriority);
																					}
																					if (x != -1) {
																						entries = x;
																					} // end if
																				} else if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_CAPTCHAS)) {
																					int x = SpecialFlagBMO.getSpecialFlagCount("captcha", true);
																					if (x > 0) {
																						dto.setDisplayCaptcha(true);
																					}
																					if (x != -1) {
																						entries = x;
																					}
																				} else if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_INCOMING_INCIDENTS_TYPE_DELAYED)) {
																					//itemized 1 for delayed
																					int x = OHDUtils.getIncomingIncidentCount(s.getStation_ID(), 1, true);
																					if (x != -1) {
																						entries = x;
																					}
																				} else if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_INCOMING_INCIDENTS_TYPE_PILFERAGE)) {
																					//itemized 2 for pilferage
																					int x = OHDUtils.getIncomingIncidentCount(s.getStation_ID(), 2, true);
																					if (x != -1) {
																						entries = x;
																					}
																				} else if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_INCOMING_INCIDENTS_TYPE_DAMAGED)) {
																					//itemized 3 for damaged
																					int x = OHDUtils.getIncomingIncidentCount(s.getStation_ID(), 3, true);
																					if (x != -1) {
																						entries = x;
																					}
																				} else if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_INCOMING_INCIDENTS_LAST_24_HOURS)) {
																					//itemized 3 for damaged
																					int x = OHDUtils.getIncomingIncidentInLast24HoursCount(s.getStation_ID(), true);
																					if (x != -1) {
																						entries = x;
																					} 																					
																				} else if(key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_MANAGE_FAULT_DISPUTE)) {														
																					int x = OHDUtils.getDisputeCount(true, agent.getStation().getLz_ID(), agent.getStation().getStation_ID());
																					if (x != -1) {
																						entries = x;
																					} 																					
																				} else if(key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_BAGBUZZ)){																			
																					int x = com.bagnet.nettracer.tracing.db.bagbuzz.Utils.getBagBuzzCount(agent);																		
																					if (x != -1) {
																						entries = x;
																					} 																
																				} else if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_CLERICAL_CLAIMS_FEATURES)) {
																					//itemized 3 for damaged
																					OnlineClaimsDao d = new OnlineClaimsDao();
																					
																					int x = d.getSubmittedCount(agent.getStation().getLz_ID(),agent.getStation().getStation_ID());
																					if (x != -1) {
																						entries = x;
																					}
																				} else if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_NAME_CENTRAL_BAGGAGE_CLAIMS_FEATURES)) {
																					OnlineClaimsDao d = new OnlineClaimsDao();
																					
																					int x = d.getReviewCount(agent.getStation().getLz_ID(), agent.getStation().getStation_ID());
																					if (x != -1) {
																						entries = x;
																					}
																				} else if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_MANAGE_2DAY_CALL)){																				
																					int x = com.bagnet.nettracer.tracing.utils.taskmanager.MorningDutiesUtil.getCount(agent, 
																							com.bagnet.nettracer.tracing.utils.taskmanager.MorningDutiesUtil.TWODAY);
																					if (x != -1) {
																						entries = x;
																					}
																				} else if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_MANAGE_3DAY_CALL)){
																					int x = com.bagnet.nettracer.tracing.utils.taskmanager.MorningDutiesUtil.getCount(agent,
																							com.bagnet.nettracer.tracing.utils.taskmanager.MorningDutiesUtil.THREEDAY);
																					if (x != -1) {
																						entries = x;
																					}
																				} else if (key.equalsIgnoreCase(TracingConstants.SYSTEM_COMPONENT_MANAGE_4DAY_CALL)){
																					int x = com.bagnet.nettracer.tracing.utils.taskmanager.MorningDutiesUtil.getCount(agent,
																							com.bagnet.nettracer.tracing.utils.taskmanager.MorningDutiesUtil.FOURDAY);
																					if (x != -1) {
																						entries = x;
																					}
																				}																			
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
				dto.setEntries("" + entries);
				list.add(dto);
			}
			session.setAttribute("activityList", list);
		}
	}

	public void setFoobar(String foobar) {
		this.foobar = foobar;
	}

}