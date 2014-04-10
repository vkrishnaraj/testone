/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions.admin;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.constant.TracingConstants.SortParam;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Lz;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.audit.Audit_Agent;
import com.bagnet.nettracer.tracing.db.audit.Audit_Station;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.LzUtils;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.audit.AuditAgentUtils;
import com.bagnet.nettracer.tracing.utils.audit.AuditStationUtils;

/**
 * Implementation of <strong>Action </strong> that is responsible for adding,
 * modifying, and deleting station data.
 * 
 * @author Ankur Gupta
 */
public final class ManageStation extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

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

		if (sort != null && sort.length() > 0 && SortParam.isValid(sort)) request.setAttribute("sort", sort);

		if ((request.getParameter("edit") != null &&  !request.getParameter("edit").equals(""))|| request.getParameter("addAgents") != null) {
			String stationId = request.getParameter("stationId");
			Station station = StationBMO.getStation(stationId);
			dForm.set("stationId", "" + station.getStation_ID());
			dForm.set("stationCode", station.getStationcode());
			dForm.set("companyCode", station.getCompany().getCompanyCode_ID());
			dForm.set("stationDesc", station.getStationdesc());
			dForm.set("addr1", station.getAddress1());
			dForm.set("addr2", station.getAddress2());
			dForm.set("city", station.getCity());
			dForm.set("state_ID", station.getState_ID());
			dForm.set("countrycode_ID", station.getCountrycode_ID());
			dForm.set("zip", station.getZip());
			dForm.set("phone", station.getPhone());
			dForm.set("operation_hours", station.getOperation_hours());
			dForm.set("airport_location", station.getAirport_location());
			dForm.set("associated_airport", station.getAssociated_airport() == null ? "" : station
					.getAssociated_airport());
			dForm.set("station_region", station.getStation_region());
			dForm.set("station_region_mgr", station.getStation_region_mgr());
			dForm.set("goal", Double.toString(station.getGoal()));
			dForm.set("lz_id", Integer.toString(station.getLz_ID()));
			dForm.set("active", "" + station.isActive());
			dForm.set("wt_stationcode", station.getWt_stationcode());
			
			if (station.getCompany().getVariable().getLz_mode() == 
				TracingConstants.MOVETOLZ_MODE_ASSIGNMENT) {
				request.setAttribute("lzStations", LzUtils.getIncidentLzStationsBeans(station.getCompany().getCompanyCode_ID()));
			}

			//check if adding agents to this group
			if (request.getParameter("addAgents") != null) {
				HashMap<String,String> selectedAgents = new HashMap<String,String>();

				String[] agentsSelected = request.getParameterValues("agent_ID");
				if (agentsSelected != null) {
					for (int i = 0; i < agentsSelected.length; i++) {
						Agent a = AdminUtils.getAgent(agentsSelected[i]);
						selectedAgents.put("" + a.getAgent_ID(), "1");
						a.setStation(station);

						//change association for the agent.
						HibernateUtils.save(a);
						
						//Make an entry into the audit table.
						if (a.getStation().getCompany().getVariable().getAudit_agent() == 1) {
							Audit_Agent audit_agent = AuditAgentUtils.getAuditAgent(a, user);
							if (audit_agent != null) {
								audit_agent.setReason_modified("Maintain Station: Station Assignment Changed");
								HibernateUtils.saveNew(audit_agent);
							}
						}
					}
				}

				
				// IF AGENT IS NOT SELECTED AND AGENT'S EXISTING STATION == STATION
				// OTHER AGENTS ARE BEING ASSIGNED TO, THE AGENT IN QUESTION IS
				// INACTIVATED.

				String station_id = null;

				if (request.getParameter("station_id") != null) station_id = request
						.getParameter("station_id");

				int rowsperpage = request.getParameter("rowsperpage") != null ? Integer.parseInt(request
						.getParameter("rowsperpage")) : TracingConstants.ROWS_PER_PAGE;
				int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
						.getParameter("currpage")) : 0;
				
				List<Agent> agents = null;
				if (!station_id.equals("-1")) agents = AdminUtils.getAgentsByStation(station_id, sort,
						null, rowsperpage, currpage);
				else agents = AdminUtils.getAgents(station.getCompany().getCompanyCode_ID(), sort, null, rowsperpage,
						currpage);

				for (Iterator<Agent> i = agents.iterator(); i.hasNext();) {
					Agent a = (Agent) i.next();
					if (a.getStation().getStation_ID() == station.getStation_ID()) {
						// If agent was selected, leave it alone.
						if (selectedAgents.get("" + a.getAgent_ID()) == null) {
							// If agent was displayed but not selected, deactivate it.
							a.setActive(false);
							HibernateUtils.save(a);
							
							//Make an entry into the audit table.
							if (a.getStation().getCompany().getVariable().getAudit_agent() == 1) {
								Audit_Agent audit_agent = AuditAgentUtils.getAuditAgent(a, user);
								if (audit_agent != null) {
									audit_agent.setReason_modified("Maintain Station: Deactivated User");
									HibernateUtils.saveNew(audit_agent);
								}
							}
						}
					}
				}
			}

			List<Agent> agents = AdminUtils.getAgentsByStation(stationId, sort, null, 0, 0);
			if (agents != null && agents.size() > 0) {
				/** ************ pagination ************* */
				int rowcount = -1;
				int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_ADMIN_PAGES, session);
				request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
				int totalpages = 0;

				int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
						.getParameter("currpage")) : 0;
				if (request.getParameter("nextpage") != null
						&& request.getParameter("nextpage").equals("1")) currpage++;
				if (request.getParameter("prevpage") != null
						&& request.getParameter("prevpage").equals("1")) currpage--;

				request.setAttribute("currpage", Integer.toString(currpage));

				// get row count
				rowcount = agents.size();

				// find out total pages
				totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

				if (totalpages <= currpage) {
					currpage = 0;
					request.setAttribute("currpage", "0");
				}

				agents = AdminUtils.getAgentsByStation(stationId, sort, null, rowsperpage, currpage);

				if (currpage + 1 == totalpages) request.setAttribute("end", "1");
				if (totalpages > 1) {
					ArrayList<String> al = new ArrayList<String>();
					for (int i = 0; i < totalpages; i++) {
						al.add(Integer.toString(i));
					}
					request.setAttribute("pages", al);
				}

				/** ************ end of pagination ************* */

				request.setAttribute("agentList", agents);
			} else {
				int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_ADMIN_PAGES, session);
				request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

				int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
						.getParameter("currpage")) : 0;
				request.setAttribute("currpage", Integer.toString(currpage));
			}

			return mapping.findForward(TracingConstants.EDIT_STATION);
		}

		if (request.getParameter("companyCode") != null) companyCode = request
				.getParameter("companyCode");
		else {
			if (dForm.get("companyCode") != null && ((String) dForm.get("companyCode")).length() > 0) companyCode = (String) dForm
					.get("companyCode");
			else companyCode = user.getStation().getCompany().getCompanyCode_ID();
		}
		dForm.set("companyCode", companyCode);

		if (request.getParameter("addNew") != null) {
			List<Lz> lzList = LzUtils.getIncidentLzStations(companyCode);
			dForm.set("lz_id", "" + LzUtils.getDefaultLz(lzList));		
			request.setAttribute("lzStations", LzUtils.getIncidentLzStationsBeans(companyCode));
			return mapping.findForward(TracingConstants.EDIT_STATION);
		}

		if (request.getParameter("addNewAgent") != null) {
			Station station = StationBMO.getStation(request.getParameter("stationId"));

			//Get the station List for the company.
			List<Station> stationList = AdminUtils.getStations(dForm, station.getCompany().getCompanyCode_ID(), 0,
					0);
			if (stationList != null) {
				List<LabelValueBean> x2 = new ArrayList<LabelValueBean>();
				Station first = null;
				for (Iterator<Station> i = stationList.iterator(); i.hasNext();) {
					Station station2 = (Station) i.next();
					if (first == null) first = station2;
					x2.add(new LabelValueBean(station2.getStationcode(), "" + station2.getStation_ID()));
				}
				request.setAttribute("stationList", stationList);

				String station_id = null;

				//get the station id from the request
				if (request.getParameter("station_id") != null) {
					station_id = request.getParameter("station_id");
				} else {
					if (request.getParameter("stationId") != null) {
						station_id = request.getParameter("stationId");
					} else {
						station_id = "" + first.getStation_ID();
					}
				}

				if (station_id != null) {
					List<Agent> agents = null;

					int path = -1;

					if (!station_id.equals("-1")) {
						path = 0;
						//station = AdminUtils.getStation(station_id);
						agents = AdminUtils.getAgentsByStation(station_id, sort, null, 0, 0);
					} else {
						path = 1;
						//use the root station id to retrieve the company.
						agents = AdminUtils.getAgents(station.getCompany().getCompanyCode_ID(), "", null, 0, 0);
					}

					if (agents != null && agents.size() > 0) {
						/** ************ pagination ************* */
						int rowcount = -1;
						int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_ADMIN_PAGES, session);
						request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
						int totalpages = 0;

						int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
								.getParameter("currpage")) : 0;
						if (request.getParameter("nextpage") != null
								&& request.getParameter("nextpage").equals("1")) currpage++;
						if (request.getParameter("prevpage") != null
								&& request.getParameter("prevpage").equals("1")) currpage--;

						request.setAttribute("currpage", Integer.toString(currpage));

						// get row count
						rowcount = agents.size();

						// find out total pages
						totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

						if (totalpages <= currpage) {
							currpage = 0;
							request.setAttribute("currpage", "0");
						}

						if (path == 0) agents = AdminUtils.getAgentsByStation(station_id, sort, null,
								rowsperpage, currpage);
						else agents = AdminUtils.getAgents(station.getCompany().getCompanyCode_ID(), "", null,
								rowsperpage, currpage);

						if (currpage + 1 == totalpages) request.setAttribute("end", "1");
						if (totalpages > 1) {
							ArrayList<String> al = new ArrayList<String>();
							for (int i = 0; i < totalpages; i++) {
								al.add(Integer.toString(i));
							}
							request.setAttribute("pages", al);
						}

						/** ************ end of pagination ************* */

						request.setAttribute("agentList", agents);
					} else {
						int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_ADMIN_PAGES, session);
						request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

						int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
								.getParameter("currpage")) : 0;
						request.setAttribute("currpage", Integer.toString(currpage));
					}

					dForm.set("station_id", station_id);
				}

				if (station != null) {
					dForm.set("stationId", "" + station.getStation_ID());
					dForm.set("stationCode", station.getStationcode());
					dForm.set("companyCode", station.getCompany().getCompanyCode_ID());
					dForm.set("stationDesc", station.getStationdesc());
					dForm.set("addr1", station.getAddress1());
					dForm.set("addr2", station.getAddress2());
					dForm.set("city", station.getCity());
					dForm.set("state_ID", station.getState_ID());
					dForm.set("countrycode_ID", station.getCountrycode_ID());
					dForm.set("zip", station.getZip());
					dForm.set("phone", station.getPhone());
					dForm.set("operation_hours", station.getOperation_hours());
					dForm.set("airport_location", station.getAirport_location());
					dForm.set("associated_airport", station.getAssociated_airport() == null ? "" : station
							.getAssociated_airport());
					dForm.set("station_region", station.getStation_region());
					dForm.set("station_region_mgr", station.getStation_region_mgr());
					dForm.set("goal", Double.toString(station.getGoal()));
					dForm.set("lz_id", "" + station.getLz_ID());
					dForm.set("wt_stationcode", station.getWt_stationcode());
				}

			}
			return mapping.findForward(TracingConstants.EDIT_AGENT_STATION);
		}

		if (request.getParameter("delete1") != null && !request.getParameter("delete1").equals("")) {
			String stationId = request.getParameter("stationId");
			Station station = StationBMO.getStation(stationId);

			ActionMessage error = null;
			if (TracerUtils.getAgentlist(station.getStation_ID()).size() > 0) error = new ActionMessage("error.deleting.station.agent");
			List<Incident> a = IncidentUtils.getStationAssignedList(station.getStation_ID(), false);
			if (a.size() > 0)  error = new ActionMessage("error.deleting.station.incidentcreated");
			if (OHDUtils.getHoldingStationList(station.getStation_ID(), false).size() > 0)  error = new ActionMessage("error.deleting.station.onhandcreated");
			if (OHDUtils.getDestStationList(station.getStation_ID(), false).size() > 0)  error = new ActionMessage("error.deleting.station.onhandcreated");
			
			if (error != null) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				companyCode = station.getCompany().getCompanyCode_ID();
				if (!HibernateUtils.delete(station)) {
					error = new ActionMessage("error.deleting.station");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				} else {

					if (user.getStation().getCompany().getVariable().getAudit_station() == 1) {
						Audit_Station audit_station = AuditStationUtils.getAuditStation(station, user);
						if (audit_station != null) {
							audit_station.setReason_modified("Deleted");
							HibernateUtils.saveNew(audit_station);
						}
					}
				}
			}
		}

		if (request.getParameter("save") != null) {
			String stationId = request.getParameter("stationId");
			Station s;
			if (stationId != null && !stationId.equals("")) {
				s = StationBMO.getStation(stationId);
			} else {
				s = new Station();	
			}
			String stationCode=(String) dForm.get("stationCode");
			stationCode=stationCode.replace(" ","");
			s.setStationcode(stationCode);
			s.setStationdesc((String) dForm.get("stationDesc"));
			s.setAddress1((String) dForm.get("addr1"));
			s.setAddress2((String) dForm.get("addr2"));
			s.setCity((String) dForm.get("city"));
			s.setState_ID((String) dForm.get("state_ID"));
			s.setCountrycode_ID((String) dForm.get("countrycode_ID"));
			s.setZip((String) dForm.get("zip"));
			s.setPhone((String) dForm.get("phone"));
			s.setOperation_hours((String) dForm.get("operation_hours"));
			s.setAirport_location((String) dForm.get("airport_location"));
			s.setAssociated_airport((String) dForm.get("associated_airport"));
			s.setStation_region((String) dForm.get("station_region"));
			s.setStation_region_mgr((String) dForm.get("station_region_mgr"));
			if ((String) dForm.get("lz_id") != null && ((String) dForm.get("lz_id")).trim().length() > 0)
				s.setLz_ID(Integer.parseInt((String) dForm.get("lz_id")));
			s.setWt_stationcode((String) dForm.get("wt_stationcode"));
			
			try {
				double thegoal = Double.parseDouble((String)dForm.get("goal"));
				s.setGoal(thegoal);
			} catch (Exception e) {
				s.setGoal(0);
			}

			Company c = new Company();
			c.setCompanyCode_ID(companyCode);
			s.setCompany(c);
			
			boolean active = dForm.get("active").equals("true") ? true : false;

			if (!active) {
				ActionMessage error = null;
				if (TracerUtils.getAgentlist(s.getStation_ID()).size() > 0) error =	new ActionMessage("error.disable.station.agent");
				if (IncidentUtils.getStationAssignedList(s.getStation_ID(), true).size() > 0)  error = new ActionMessage("error.disable.station.incidentcreated");
				if (OHDUtils.getHoldingStationList(s.getStation_ID(), true).size() > 0)  error = new ActionMessage("error.disable.station.onhandcreated");
				if (OHDUtils.getDestStationList(s.getStation_ID(), true).size() > 0) error = new ActionMessage("error.disable.station.forwarded");
				
				if (error != null) {
					active = true;
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				}
			}
			s.setActive(active);
			
			try {
				HibernateUtils.saveStation(s, user);
			} catch (Exception e) {
				ActionMessage error = new ActionMessage("error.creating.station");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return mapping.findForward(TracingConstants.EDIT_AGENT_STATION);
			}
		}
		
		List<Station> stationList = null;
		TracingConstants.AgentActiveStatus status;
		if (request.getParameter("active") == null || request.getParameter("active").equals("-1") || request.getParameter("save") != null) {
			dForm.set("active", null);
			status = TracingConstants.AgentActiveStatus.ALL;
			stationList = AdminUtils.getCustomStations(dForm, companyCode, 0, 0, TracingConstants.AgentActiveStatus.ALL);
		} else if (request.getParameter("active").equals("true")) {
			status = TracingConstants.AgentActiveStatus.ACTIVE;
			stationList = AdminUtils.getCustomStations(dForm, companyCode, 0, 0, TracingConstants.AgentActiveStatus.ACTIVE);
		} else {
			status = TracingConstants.AgentActiveStatus.INACTIVE;
			stationList = AdminUtils.getCustomStations(dForm, companyCode, 0, 0, TracingConstants.AgentActiveStatus.INACTIVE);
		}

		if (stationList != null && stationList.size() > 0) {
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
			rowcount = stationList.size();

			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}

			stationList = AdminUtils.getCustomStations(dForm, companyCode, rowsperpage, currpage, status);

			if (currpage + 1 == totalpages) request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList<String> al = new ArrayList<String>();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}

			/** ************ end of pagination ************* */

			request.setAttribute("stationList", stationList);
		} else {
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_ADMIN_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			request.setAttribute("currpage", Integer.toString(currpage));
		}
		return mapping.findForward(TracingConstants.VIEW_STATIONS);
	}
}