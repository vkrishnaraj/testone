/*
 * Created on Jul 9, 2004
 *
 */
package com.bagnet.nettracer.tracing.actions.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.DynaValidatorForm;

import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.lf.SubCompanyDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.NTDateFormat;
import com.bagnet.nettracer.tracing.db.NTTimeFormat;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.UserGroup;
import com.bagnet.nettracer.tracing.db.audit.Audit_Agent;
import com.bagnet.nettracer.tracing.db.lf.Subcompany;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.audit.AuditAgentUtils;

/**
 * Implementation of <strong>Action </strong> that is responsible for adding,
 * modifying, and deleting agent data.
 * 
 * @author Ankur Gupta
 */
public final class ManageAgents extends Action {
		
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		// check session
		TracerUtils.checkSession(session);
		boolean addStation = false;
		boolean addSubcompany = false;
		
		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)) return (mapping
				.findForward(TracingConstants.NO_PERMISSION));

		ActionMessages errors = new ActionMessages();
		DynaValidatorForm dForm = (DynaValidatorForm) form;
		String companyCode = "";

		String sort = request.getParameter("sort");

		if (sort != null && sort.length() > 0 && com.bagnet.nettracer.tracing.constant.TracingConstants.SortParam.isValid(sort)) request.setAttribute("sort", sort);

		//retrieve all timezones and populate the list.
		request.setAttribute("timezones", AdminUtils.getTimeZones());

		//figure out the company to be used.
		if (request.getParameter("companyCode") != null) 
			companyCode = request
				.getParameter("companyCode");
		else {
			if (dForm.get("companyCode") != null && ((String) dForm.get("companyCode")).length() > 0) companyCode = (String) dForm
					.get("companyCode");
			else companyCode = user.getStation().getCompany().getCompanyCode_ID();
		}

		dForm.set("companyCode", companyCode);

		Agent quickSearchAgent = null;
		
		if (request.getParameter("searchAgentUsername") != null) {

				String quickSearchString = request.getParameter("searchAgentUsername");
				quickSearchAgent = AdminUtils.getAgentBasedOnUsername(quickSearchString, companyCode);
		}
		
		if ((request.getParameter("edit") != null && !request.getParameter("edit").equals("")) || request.getParameter("self_edit") != null || quickSearchAgent != null) {
			Agent a = null;
			if (request.getParameter("self_edit") != null) {
				a = user;
			} else {
				if (quickSearchAgent != null) {
					a = quickSearchAgent;
				} else {
					a = AdminUtils.getAgent(request.getParameter("agentId"));
				}
			}
			dForm.set("agent_id", "" + a.getAgent_ID());
			dForm.set("station_id", "" + a.getStation().getStation_ID());
			if(a.getSubcompany()!=null){
				dForm.set("subcompany_id", "" + a.getSubcompany().getId());
			}
			companyCode = a.getStation().getCompany().getCompanyCode_ID();
			dForm.set("companyCode", "" + a.getStation().getCompany().getCompanyCode_ID());
			dForm.set("group_id", "" + a.getUsergroup_id());
			dForm.set("fname", "" + a.getFirstname());
			dForm.set("mname", "" + a.getMname());
			dForm.set("lname", "" + a.getLastname());
			dForm.set("timeout", "" + a.getTimeout());
			dForm.set("username", "" + a.getUsername());
			dForm.set("password", "XXXXXX");
			dForm.set("password2", "XXXXXX");
			dForm.set("resetPassword", "" + a.isReset_password());
			dForm.set("active", "" + a.isActive());
			if (a.getShift() != null) dForm.set("shift_id", "" + a.getShift().getShift_id());
			dForm.set("defLocale", "" + a.getDefaultlocale());
			dForm.set("currLocale", "" + a.getCurrentlocale());
			dForm.set("defCurrency", "" + a.getDefaultcurrency());
			dForm.set("dateFormat", "" + a.getDateformat().getDateformat_ID());
			dForm.set("timeFormat", "" + a.getTimeformat().getTimeformat_ID());
			dForm.set("currentTimezone", a.getCurrenttimezone());
			dForm.set("defaultTimezone", a.getDefaulttimezone());
			dForm.set("web_enabled", "" + a.isWeb_enabled());
			dForm.set("ws_enabled", "" + a.isWs_enabled());
			dForm.set("max_ws_sessions", "" + a.getMax_ws_sessions());
			dForm.set("inboundQueue", "" + a.isInboundQueue());
					
			if (a.isAccount_locked()) {
				ActionMessage error = new ActionMessage("error.user.admin.lockedout");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				dForm.set("accountLocked", "true");
			}

			addStation = true;
			addSubcompany = true;
		}

		List groupList = AdminUtils.getGroups(null, companyCode, 0, 0);
		List x = new ArrayList();
		for (Iterator i = groupList.iterator(); i.hasNext();) {
			UserGroup group = (UserGroup) i.next();
			x.add(new LabelValueBean(group.getDescription(), "" + group.getUserGroup_ID()));
		}

		List subcompList = AdminUtils.getSubcompanies(null, companyCode, 0, 0);
		List x3 = new ArrayList();
		boolean subcompFound = false;
		if (subcompList != null) {
			for (Iterator i = subcompList.iterator(); i.hasNext();) {
				Subcompany subcomp = (Subcompany) i.next();
				x3.add(new LabelValueBean(subcomp.getSubcompanyCode(), "" + subcomp.getId()));
				
			}
			x3.add(0, new LabelValueBean("No Subcompany", ""));
		}
		
		Station tmp = null;
		if (addStation && request.getParameter("agentId") != null && request.getParameter("agentId") != "") {
			Agent tmpAgent = AdminUtils.getAgent(request.getParameter("agentId"));
			tmp = tmpAgent.getStation();
		}
		List stationList = AdminUtils.getStations(null, companyCode, 0, 0);
		List x2 = new ArrayList();
		boolean stationFound = false;
		if (stationList != null) {
			for (Iterator i = stationList.iterator(); i.hasNext();) {
				Station station = (Station) i.next();
				x2.add(new LabelValueBean(station.getStationcode(), "" + station.getStation_ID()));
				if (tmp != null) {
					if (station.getStationcode().equals(tmp.getStationcode()))
						stationFound = true;
				}
			}
			if (addStation && !stationFound)
				x2.add(0, new LabelValueBean("", ""));
		}

		List dateformatList = HibernateUtils.retrieveAll(NTDateFormat.class);
		List timeformatList = HibernateUtils.retrieveAll(NTTimeFormat.class);
		List shiftList = AdminUtils.getShifts(companyCode, user.getCurrentlocale(), 0, 0);

		request.setAttribute("dateformatlist", dateformatList);
		request.setAttribute("timeformatlist", timeformatList);
		request.setAttribute("shiftList", shiftList);
		request.setAttribute("grouplist", x);
		request.setAttribute("statList", x2);
		request.setAttribute("subcompList", x3);

		if (request.getParameter("self_edit") != null) {
			return mapping.findForward(TracingConstants.EDIT_SELF);
		} else if (request.getParameter("addNew") != null || request.getParameter("edit") != null || quickSearchAgent != null) {
			if (request.getParameter("addNew") != null) {
				request.setAttribute("aNew", "1");
				dForm.set("max_ws_sessions", "0");
				
				//set the default values.
				dForm.set("timeout", TracingConstants.DEFAULT_AGENT_TIMEOUT);
				dForm.set("currentTimezone", TracingConstants.DEFAULT_AGENT_TIMEZONE);
				dForm.set("defaultTimezone", TracingConstants.DEFAULT_AGENT_TIMEZONE);
				dForm.set("defCurrency", "" + TracingConstants.DEFAULT_AGENT_CURRENCY);
				if(user.getSubcompany()!=null){
					dForm.set("subcompany_id",""+user.getSubcompany().getId());
				}
			}

			return mapping.findForward(TracingConstants.EDIT_AGENT);
		}
		if (request.getParameter("delete1") != null && !request.getParameter("delete1").equals("")) {
			String agent_id = request.getParameter("agentId");
			Agent agent = AdminUtils.getAgent(agent_id);
			companyCode = agent.getStation().getCompany().getCompanyCode_ID();

			//check if incidents, ohds, tasks, messages, requests,
			//if (IncidentUtils.checkCreatedByAgent(agent_id) ||
			// OHDUtils.checkCreatedByAgent(agent_id)
			//		)

			//agent has done any interaction
			if (AdminUtils.getAgentLogCount(agent) > 0) {
				ActionMessage error = new ActionMessage("error.deleting.agent");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else if (AdminUtils.getAssignedAgent(agent) > 0) {
					ActionMessage error = new ActionMessage("error.deleting.agent");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
			
			} else if (!HibernateUtils.delete(agent)) {
					ActionMessage error = new ActionMessage("error.deleting.agent");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
			} else {

				//Make an entry into the audit table.
				if (agent.getStation().getCompany().getVariable().getAudit_agent() == 1) {
					Audit_Agent audit_agent = AuditAgentUtils.getAuditAgent(agent, user);
					if (audit_agent != null) {
						audit_agent.setReason_modified("Deleted");
						HibernateUtils.saveNew(audit_agent);
					}
				}
			}
		}

		if (request.getParameter("save") != null) {

			String username = (String) dForm.get("username");
			
			 
			if (dForm.get("password") != null && dForm.get("password2") != null
					&& ((String) dForm.get("password")).equals((String) dForm.get("password2"))) {
				Agent agent = null;
				boolean isNew = false;

			if (username != null) {
					agent = AdminUtils.getAgentBasedOnUsername(username, companyCode);
					if (request.getParameter("aNew") != null && agent != null) {
						request.setAttribute("aNew", "1");
						//dForm.set("username", "");

						ActionMessage error = new ActionMessage("error.username.exists");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
						if (request.getParameter("self") != null) {
							return mapping.findForward(TracingConstants.EDIT_SELF);
						} else {
							return mapping.findForward(TracingConstants.EDIT_AGENT);
						}
					}
					if (request.getParameter("aNew") != null) {
						agent = new Agent();
						isNew = true;
					}
				}
					
				Station s = new Station();
				if (request.getParameter("self") == null) {
					s.setStation_ID(Integer.parseInt(((String) dForm.get("station_id"))));
					agent.setStation(s);
				}
				
				if (request.getParameter("self") == null) {
					agent.setInboundQueue(((String)dForm.get("inboundQueue")).equals("true"));
				}
				
				Subcompany sc = new Subcompany();
				if (request.getParameter("self") == null) {
					if((!((String)dForm.get("subcompany_id")).equals(""))){
						sc=SubCompanyDAO.loadSubcompany(Long.parseLong((String) dForm.get("subcompany_id")));
						agent.setSubcompany(sc);
					} else {
						agent.setSubcompany(null);
					}
				}
				
				UserGroup g = new UserGroup();
				if (request.getParameter("self") == null) {
					g.setUserGroup_ID(Integer.parseInt(((String) dForm.get("group_id"))));
					agent.setGroup(g);
				}

				agent.setFirstname((String) dForm.get("fname"));
				agent.setMname((String) dForm.get("mname"));
				agent.setLastname((String) dForm.get("lname"));
				if ((String) dForm.get("timeout") != null) 
					agent.setTimeout(Integer.parseInt((String) dForm
						.get("timeout")));
				else agent.setTimeout(60);

				if (request.getParameter("self") == null) 
					agent.setUsername((String) dForm.get("username"));

				if (dForm.get("resetPassword") != null)
					agent.setReset_password(((String)dForm.get("resetPassword")).equals("true"));
				if (dForm.get("accountLocked") != null) {
					agent.setAccount_locked(((String)dForm.get("accountLocked")).equals("true"));
					agent.setFailed_logins(0);
				}
				if (request.getParameter("self") == null) 
					agent.setActive(((String) dForm.get("active"))
						.equals("true") ? true : false);
				//agent.setTimezone_ID((String)dForm.get("timezone"));
				agent.setDefaultlocale((String) dForm.get("defLocale"));
				agent.setCurrentlocale((String) dForm.get("currLocale"));
				agent.setDefaultcurrency((String) dForm.get("defCurrency"));
				//agent.setVdomain((String)dForm.get("vdomain"));
				agent.setDateformat(AdminUtils.getDateFormat((String) dForm.get("dateFormat")));
				agent.setTimeformat(AdminUtils.getTimeFormat((String) dForm.get("timeFormat")));
				if (request.getParameter("self") == null) {
					if (dForm.get("shift_id") != null && !dForm.get("shift_id").equals("")) 
						agent
							.setShift(AdminUtils.getShift((String) dForm.get("shift_id")));
				}
				agent.setCompanycode_ID(StationBMO.getStation("" + agent.getStation().getStation_ID())
						.getCompany().getCompanyCode_ID());
				agent.setDefaulttimezone((String) dForm.get("defaultTimezone"));
				agent.setCurrenttimezone((String) dForm.get("currentTimezone"));

				if (UserPermissions.hasPermission(
						TracingConstants.SYSTEM_COMPONENT_NAME_MAINTAIN_WEB_SERVICE_AGENTS, user)) {
					if (!((String) dForm.get("web_enabled")).equals("")) {
						agent.setWeb_enabled(((String) dForm.get("web_enabled")).equals("true"));
						agent.setWs_enabled(((String) dForm.get("ws_enabled")).equals("true"));
						try {
							agent.setMax_ws_sessions(Integer.parseInt((String)dForm.get("max_ws_sessions")));
						}
						catch (NumberFormatException ex) {
							ActionMessage error = new ActionMessage("error.invalid_max_ws_sessions");
							errors.add(ActionMessages.GLOBAL_MESSAGE, error);
							saveMessages(request, errors);
							return mapping.findForward(TracingConstants.EDIT_AGENT);
						}
	
						if (!agent.isWeb_enabled() && !agent.isWs_enabled()) {
							ActionMessage error = new ActionMessage("error.agents.mustbeweborws");
							errors.add(ActionMessages.GLOBAL_MESSAGE, error);
							saveMessages(request, errors);
							return mapping.findForward(TracingConstants.EDIT_AGENT);
						}
					}
				} else {
					agent.setWeb_enabled(true);
					agent.setWs_enabled(false);
				}
				
				
				if (dForm.get("passwordChanged") != null && dForm.get("passwordChanged").equals("1")) {
					boolean validPw = SecurityUtils.isPolicyAcceptablePassword(agent.getCompanycode_ID(), (String) dForm.get("password"), agent.getUsername(), request, false);
					String password = StringUtils.sha1_256((String)dForm.get("password"),true);
					Company comp = CompanyBMO.getCompany(agent.getCompanycode_ID());
					boolean passX = SecurityUtils.lastXPasswords(agent.getAgent_ID(), comp!=null?comp.getVariable().getPass_x_history():20, password); 
					if (validPw && !passX) {
						agent.setPassword(password);
						SecurityUtils.insertPasswordHistory(agent.getAgent_ID(), password);
					} else {
						if(passX){
							ActionMessage error = new ActionMessage("error.password.sameasold");
							errors.add(ActionMessages.GLOBAL_MESSAGE, error);
							saveMessages(request, errors);
						}
						if(!validPw){
							request.setAttribute("securePolicy", 1);
						}
						if (request.getParameter("self") != null) {
							return mapping.findForward(TracingConstants.EDIT_SELF);
						} else {
							if (request.getParameter("aNew") != null)
								request.setAttribute("aNew", "1");
							return mapping.findForward(TracingConstants.EDIT_AGENT);
						}
					}
				}
				
				if (isNew) {
					HibernateUtils.saveNew(agent);
					if (request.getParameter("self") != null)
						user = agent;
				} else {
					//Find the mail config xml for this agent.
					HibernateUtils.save(agent);
					if (user.getAgent_ID() == agent.getAgent_ID()) {
						session.setAttribute("user", AdminUtils.getAgent(Integer.toString(agent.getAgent_ID())));
					}
				}
				/*
				 * check to see if auditing is enabled...
				 */
				//check if audit is enabled for this company....
				if (AdminUtils.getCompVariable(agent.getCompanycode_ID()).getAudit_agent() == 1) {
					Audit_Agent audit_agent = AuditAgentUtils.getAuditAgent(agent, user);
					if (audit_agent != null) {
						HibernateUtils.saveNew(audit_agent);
					}
				}
				
				saveMessages(request, errors);
				if (request.getParameter("self") != null)
					return mapping.findForward(TracingConstants.SELF_UPDATE_SUCCESS);
				else {
					dForm.initialize(mapping);
					dForm.set("companyCode", companyCode);
				}

			} else {
				if (request.getParameter("aNew") != null) {
					request.setAttribute("aNew", "1");
					//dForm.set("username", "");
				}
				ActionMessage error = new ActionMessage("error.password.match");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);

				if (request.getParameter("self") != null) {
					return mapping.findForward(TracingConstants.EDIT_SELF);
				} else {
					return mapping.findForward(TracingConstants.EDIT_AGENT);
				}
			}
		}

		if (request.getParameter("self") != null) response.sendRedirect("logon.do?taskmanager=1");

		TracingConstants.AgentActiveStatus status;
		if (request.getParameter("active") == null || request.getParameter("active").equals("-1") || request.getParameter("save") != null) {
			dForm.set("active", null);
			status = TracingConstants.AgentActiveStatus.ALL;
		} else if (request.getParameter("active").equals("true")) {
			status = TracingConstants.AgentActiveStatus.ACTIVE;
		} else {
			status = TracingConstants.AgentActiveStatus.INACTIVE;
		}

		List agentList = null;

		if (((String) dForm.get("station_id")).equals("") || ((String) dForm.get("station_id")).equals("-1")) {
			agentList = AdminUtils.getCustomAgents(null, companyCode, sort, dForm, 0, 0, status);
		} else {
			agentList = AdminUtils.getCustomAgents((String) dForm.get("station_id"), null, sort, dForm, 0, 0, status);
		}
		
		
		if (agentList != null && agentList.size() > 0) {
			/** ************ pagination ************* */
			int rowcount = -1;
			
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_ADMIN_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			
			int totalpages = 0;
			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1")) currpage++;
			if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1")) currpage--;
			request.setAttribute("currpage", Integer.toString(currpage));
			// get row count
			rowcount = agentList.size();
			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);
			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}

			if (((String) dForm.get("station_id")).equals("") || ((String) dForm.get("station_id")).equals("-1")) {
				agentList = AdminUtils.getCustomAgents(null, companyCode, sort, dForm, rowsperpage, currpage, status);
			} else {
				agentList = AdminUtils.getCustomAgents((String) dForm.get("station_id"), null, sort, dForm, rowsperpage, currpage, status);
			}
			
			if (currpage + 1 == totalpages) request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList al = new ArrayList();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}
			/** ************ end of pagination ************* */
			request.setAttribute("agentList", agentList);
		} else {
			ActionMessage error = new ActionMessage("error.agents.notfound");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);

			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_ADMIN_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			request.setAttribute("currpage", Integer.toString(currpage));
		}

		return mapping.findForward(TracingConstants.VIEW_AGENTS);
	}
}