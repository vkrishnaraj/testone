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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.DynaValidatorForm;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.constant.TracingConstants.SortParam;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.GroupComponentPolicy;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.UserGroup;
import com.bagnet.nettracer.tracing.db.audit.Audit_Agent;
import com.bagnet.nettracer.tracing.db.audit.Audit_GroupComponentPolicy;
import com.bagnet.nettracer.tracing.db.audit.Audit_UserGroup;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.audit.AuditAgentUtils;
import com.bagnet.nettracer.tracing.utils.audit.AuditGroupUtils;

/**
 * Implementation of <strong>Action </strong> that is responsible for adding,
 * modifying, and deleting group data.
 * 
 * @author Ankur Gupta
 */
public final class ManageGroups extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		String sort = request.getParameter("sort");

		if (sort != null && sort.length() > 0 && SortParam.isValid(sort))
			request.setAttribute("sort", sort);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1)
				+ ".do", user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));

		ActionMessages errors = new ActionMessages();
		DynaValidatorForm dForm = (DynaValidatorForm) form;
		
		String companyCode = "";
		if ((request.getParameter("edit") != null && request.getParameter("edit").trim().length() > 0)
				|| request.getParameter("addAgents") != null) {
			UserGroup group = AdminUtils.getGroup(request
					.getParameter("groupID"));
			dForm.set("groupID", "" + group.getUserGroup_ID());
			dForm.set("companyCode", group.getCompanycode_ID());
			dForm.set("groupName", group.getDescription());
			dForm.set("groupDesc", group.getDescription2());
			dForm.set("bsoLimit", String.valueOf(group.getBsoLimit()));

			// check if adding agents to this group
			if (request.getParameter("addAgents") != null) {
				HashMap<String,String> selectedAgents = new HashMap<String,String>();
				String[] agentsSelected = request
						.getParameterValues("agent_ID");
				if (agentsSelected != null) {
					for (int i = 0; i < agentsSelected.length; i++) {
						Agent a = AdminUtils.getAgent(agentsSelected[i]);
						selectedAgents.put("" + a.getAgent_ID(), "1");
						a.setGroup(group);
						// change association for the agent.
						HibernateUtils.save(a);

						if (AdminUtils.getCompVariable(a.getCompanycode_ID())
								.getAudit_agent() == 1) {
							Audit_Agent audit_agent = AuditAgentUtils
									.getAuditAgent(a, user);
							if (audit_agent != null) {
								HibernateUtils.saveNew(audit_agent);
							}
						}

					}
				}
				// Get all the agents for this station.
				String station_id = null;
				// get the station id from the request
				if (request.getParameter("station_id") != null)
					station_id = request.getParameter("station_id");

				int rowsperpage = request.getParameter("rowsperpage") != null ? Integer
						.parseInt(request.getParameter("rowsperpage"))
						: TracingConstants.ROWS_PER_PAGE;
				int currpage = request.getParameter("currpage") != null ? Integer
						.parseInt(request.getParameter("currpage"))
						: 0;

				List<Agent> agents = null;
				if (!station_id.equals("-1"))
					agents = AdminUtils.getAgentsByStation(station_id, sort,
							null, rowsperpage, currpage);
				else
					agents = AdminUtils.getAgents(group.getCompanycode_ID(),
							sort, null, rowsperpage, currpage);

				UserGroup guest = AdminUtils.getGuestGroup(group
						.getCompanycode_ID());

				for (Iterator<Agent> i = agents.iterator(); i.hasNext();) {
					Agent a = (Agent) i.next();
					if (a.getUsergroup_id() == group
							.getUserGroup_ID()) {
						// check if it has been selected. if so.leave as is
						if (selectedAgents.get("" + a.getAgent_ID()) == null) {
							// not selected..move the user to the guest group.
							a.setGroup(guest);
							HibernateUtils.save(a);

							if (AdminUtils.getCompVariable(
									a.getCompanycode_ID()).getAudit_agent() == 1) {
								Audit_Agent audit_agent = AuditAgentUtils
										.getAuditAgent(a, user);
								if (audit_agent != null) {
									HibernateUtils.saveNew(audit_agent);
								}
							}
						}
					}
				}
			}

			// get the list of the agents for the group.
			List<Agent> agents = AdminUtils.getAgentsByGroup(""
					+ group.getUserGroup_ID(), sort, 0, 0);
			if (agents != null && agents.size() > 0) {
				/** ************ pagination ************* */
				int rowcount = -1;
				int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_ADMIN_PAGES, session);
				request.setAttribute("rowsperpage", Integer
						.toString(rowsperpage));
				int totalpages = 0;

				int currpage = request.getParameter("currpage") != null ? Integer
						.parseInt(request.getParameter("currpage"))
						: 0;
				if (request.getParameter("nextpage") != null
						&& request.getParameter("nextpage").equals("1"))
					currpage++;
				if (request.getParameter("prevpage") != null
						&& request.getParameter("prevpage").equals("1"))
					currpage--;

				request.setAttribute("currpage", Integer.toString(currpage));

				// get row count
				rowcount = agents.size();

				// find out total pages
				totalpages = (int) Math.ceil((double) rowcount
						/ (double) rowsperpage);

				if (totalpages <= currpage) {
					currpage = 0;
					request.setAttribute("currpage", "0");
				}

				agents = AdminUtils.getAgentsByGroup(""
						+ group.getUserGroup_ID(), sort, rowsperpage, currpage);

				if (currpage + 1 == totalpages)
					request.setAttribute("end", "1");
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
				request.setAttribute("rowsperpage", Integer
						.toString(rowsperpage));

				int currpage = request.getParameter("currpage") != null ? Integer
						.parseInt(request.getParameter("currpage"))
						: 0;
				request.setAttribute("currpage", Integer.toString(currpage));
			}
			return mapping.findForward(TracingConstants.EDIT_GROUP);
		}

		if (request.getParameter("companyCode") != null)
			companyCode = request.getParameter("companyCode");
		else {
			if (dForm.get("companyCode") != null
					&& ((String) dForm.get("companyCode")).length() > 0)
				companyCode = (String) dForm.get("companyCode");
			else
				companyCode = user.getStation().getCompany()
						.getCompanyCode_ID();
		}
		dForm.set("companyCode", companyCode);

		if (request.getParameter("addNew") != null) {
			return mapping.findForward(TracingConstants.EDIT_GROUP);
		}

		if (request.getParameter("addNewAgent") != null) {
			UserGroup group = AdminUtils.getGroup(request
					.getParameter("groupID"));
			dForm.set("groupID", "" + group.getUserGroup_ID());
			dForm.set("companyCode", group.getCompanycode_ID());
			dForm.set("groupName", group.getDescription());

			// Get the station List for the company.
			List<Station> stationList = AdminUtils.getStations(null, group
					.getCompanycode_ID(), 0, 0);
			if (stationList != null) {
				List<LabelValueBean> x2 = new ArrayList<LabelValueBean>();
				// Station first = null;
				for (Iterator<Station> i = stationList.iterator(); i.hasNext();) {
					Station station = (Station) i.next();
					// if (first == null)
					// first = station;
					x2.add(new LabelValueBean(station.getStationcode(), ""
							+ station.getStation_ID()));
				}
				request.setAttribute("stationList", stationList);

				String station_id = null;

				// get the station id from the request
				if (request.getParameter("station_id") != null) {
					station_id = request.getParameter("station_id");
				} else {
					station_id = "-1";// + first.getStation_ID();
				}

				if (station_id != null) {
					List<Agent> agents = null;
					int path = -1;
					if (!station_id.equals("-1")) {
						agents = AdminUtils.getAgentsByStation(station_id,
								sort, null, 0, 0);
						path = 0;
					} else {
						path = 1;
						agents = AdminUtils.getAgents(
								group.getCompanycode_ID(), sort, null, 0, 0);
					}
					if (agents != null && agents.size() > 0) {
						/** ************ pagination ************* */
						int rowcount = -1;
						int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_ADMIN_PAGES, session);
						request.setAttribute("rowsperpage", Integer
								.toString(rowsperpage));
						int totalpages = 0;

						int currpage = request.getParameter("currpage") != null ? Integer
								.parseInt(request.getParameter("currpage"))
								: 0;
						if (request.getParameter("nextpage") != null
								&& request.getParameter("nextpage").equals("1"))
							currpage++;
						if (request.getParameter("prevpage") != null
								&& request.getParameter("prevpage").equals("1"))
							currpage--;

						request.setAttribute("currpage", Integer
								.toString(currpage));

						// get row count
						rowcount = agents.size();

						// find out total pages
						totalpages = (int) Math.ceil((double) rowcount
								/ (double) rowsperpage);

						if (totalpages <= currpage) {
							currpage = 0;
							request.setAttribute("currpage", "0");
						}

						if (path == 0)
							agents = AdminUtils.getAgentsByStation(station_id,
									sort, null, rowsperpage, currpage);
						else
							agents = AdminUtils.getAgents(group
									.getCompanycode_ID(), "", null,
									rowsperpage, currpage);

						if (currpage + 1 == totalpages)
							request.setAttribute("end", "1");
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
						request.setAttribute("rowsperpage", Integer
								.toString(rowsperpage));

						int currpage = request.getParameter("currpage") != null ? Integer
								.parseInt(request.getParameter("currpage"))
								: 0;
						request.setAttribute("currpage", Integer
								.toString(currpage));
					}

					dForm.set("station_id", station_id);
				}
			}
			return mapping.findForward(TracingConstants.EDIT_AGENT_GROUP);
		}

		if (request.getParameter("delete1") != null
				&& !request.getParameter("delete1").equals("")) {
			String groupId = request.getParameter("groupID");

			UserGroup grp = AdminUtils.getGroup(groupId);

			// check if agents belong to a group.
			ActionMessage error = null;
			if (AdminUtils.getAgentsByGroup("" + grp.getUserGroup_ID(), "", 0,
					0).size() > 0) {
				error = new ActionMessage("error.deleting.group.agents");
			}
			// else
			// {
			// if (grp.getComponentPolicies().size() > 0)
			// error = new ActionMessage("error.deleting.group.permissions");
			// }

			if (error != null) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				companyCode = grp.getCompanycode_ID();

				/*
				 * //delete the permissions first and then the group.
				 * 
				 * Set permissions = grp.getComponentPolicies(); if (permissions
				 * != null && permissions.size() > 0) { for (Iterator
				 * i=permissions.iterator();i.hasNext();) { GroupComponentPolicy
				 * policy = (GroupComponentPolicy)i.next();
				 * HibernateUtils.delete(policy); } }
				 */
				HibernateUtils.delete(grp);

				if (user.getStation().getCompany().getVariable()
						.getAudit_group() == 1) {
					Audit_UserGroup audit_group = AuditGroupUtils
							.getAuditGroup(grp, user);
					if (audit_group != null) {

						audit_group.setReason_modified("Deleted");
						HibernateUtils.saveNew(audit_group);

						// check to see if there were component policies with
						// this group
						// Component policies need to recopied.
						if (grp.getComponentPolicies() != null
								&& grp.getComponentPolicies().size() > 0) {
							for (Iterator<GroupComponentPolicy> i = grp.getComponentPolicies()
									.iterator(); i.hasNext();) {
								GroupComponentPolicy policy = (GroupComponentPolicy) i
										.next();
								Audit_GroupComponentPolicy plicy2 = new Audit_GroupComponentPolicy();
								BeanUtils.copyProperties(plicy2, policy);
								plicy2.setAudit_usergroup(audit_group);
								HibernateUtils.saveNew(plicy2);
							}
						}
					}
				}

			}
		}

		if (request.getParameter("save") != null) {

			String groupId = (String) request.getParameter("groupID");

			UserGroup g = new UserGroup();
			String groupName = (String) dForm.get("groupName");
			String groupDesc = (String) dForm.get("groupDesc");
			double bsoLimit =0;
			if(dForm.get("bsoLimit")!=null && !((String)dForm.get("bsoLimit")).isEmpty()){
				bsoLimit = Double.parseDouble((String) dForm.get("bsoLimit"));
			}
			
			if(groupName != null){
				groupName = groupName.replaceAll(TracingConstants.FILTER_CHARACTERS, "");
			}
			if(groupDesc != null){
				groupDesc = groupDesc.replaceAll(TracingConstants.FILTER_CHARACTERS, "");
			}
			
			g.setDescription(groupName);
			g.setDescription2(groupDesc);
			g.setCompanycode_ID(companyCode);
			g.setBsoLimit(bsoLimit);
			try {
				HibernateUtils.saveGroup(g, groupId, user);

				UserGroup objRef;
				if (groupId == null || groupId.length() < 1)
					objRef = g;
				else
					objRef = AdminUtils.getGroup(groupId);

				if (user.getStation().getCompany().getVariable()
						.getAudit_group() == 1) {
					Audit_UserGroup audit_group = AuditGroupUtils
							.getAuditGroup(objRef, user);
					if (audit_group != null) {
						HibernateUtils.saveNew(audit_group);
					}

					// check to see if there were component policies with this
					// group
					// Component policies need to recopied.
					if (objRef.getComponentPolicies() != null
							&& objRef.getComponentPolicies().size() > 0) {
						for (Iterator<GroupComponentPolicy> i = objRef.getComponentPolicies()
								.iterator(); i.hasNext();) {
							GroupComponentPolicy policy = (GroupComponentPolicy) i
									.next();
							Audit_GroupComponentPolicy plicy2 = new Audit_GroupComponentPolicy();
							BeanUtils.copyProperties(plicy2, policy);
							plicy2.setAudit_usergroup(audit_group);
							HibernateUtils.saveNew(plicy2);
						}
					}
				}
			} catch (Exception e) {
				ActionMessage error = new ActionMessage("error.creating.group");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
		}
		List<UserGroup> groupList = AdminUtils.getGroups(dForm, companyCode, 0, 0);
		if (groupList != null && groupList.size() > 0) {
			/** ************ pagination ************* */
			int rowcount = -1;
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_ADMIN_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			int totalpages = 0;

			int currpage = request.getParameter("currpage") != null ? Integer
					.parseInt(request.getParameter("currpage")) : 0;
			if (request.getParameter("nextpage") != null
					&& request.getParameter("nextpage").equals("1"))
				currpage++;
			if (request.getParameter("prevpage") != null
					&& request.getParameter("prevpage").equals("1"))
				currpage--;

			request.setAttribute("currpage", Integer.toString(currpage));

			// get row count
			rowcount = groupList.size();

			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount
					/ (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}

			groupList = AdminUtils.getGroups(dForm, companyCode, rowsperpage,
					currpage);

			if (currpage + 1 == totalpages)
				request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList<String> al = new ArrayList<String>();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}

			/** ************ end of pagination ************* */
			request.setAttribute("groupList", groupList);
		} else {
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_ADMIN_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

			int currpage = request.getParameter("currpage") != null ? Integer
					.parseInt(request.getParameter("currpage")) : 0;
			request.setAttribute("currpage", Integer.toString(currpage));
		}

		return mapping.findForward(TracingConstants.VIEW_GROUPS);
	}
}