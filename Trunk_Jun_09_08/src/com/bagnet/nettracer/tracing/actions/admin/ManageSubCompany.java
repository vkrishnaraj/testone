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

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.lf.SubCompanyDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.lf.Subcompany;
import com.bagnet.nettracer.tracing.db.lf.SubcompanyStation;
import com.bagnet.nettracer.tracing.forms.SubCompanyForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.LzUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * Implementation of <strong>Action </strong> that is responsible for adding,
 * modifying, and deleting station data.
 * 
 * @author Ankur Gupta
 */
public final class ManageSubCompany extends Action {
	
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
		SubCompanyForm SCForm = (SubCompanyForm) form;

		String companyCode = "";

		String sort = request.getParameter("sort");

		if (sort != null && sort.length() > 0) request.setAttribute("sort", sort);

		if ((request.getParameter("edit") != null &&  !request.getParameter("edit").equals("")) || request.getParameter("addStations") != null) {
			String subcompId = request.getParameter("id");
			Subcompany subcomp = SubCompanyDAO.loadSubcompany(Long.valueOf(subcompId));
			SCForm.setAuto_Close_High(subcomp.getAuto_Close_High());
			SCForm.setAuto_Close_Low(subcomp.getAuto_Close_Low());
			SCForm.setCompanyCode(subcomp.getCompany().getCompanyCode_ID());
			SCForm.setEmail_Notice_1(subcomp.getEmail_Notice_1());
			SCForm.setEmail_Notice_2(subcomp.getEmail_Notice_2());
			SCForm.setEmail_Notice_3(subcomp.getEmail_Notice_3());
			SCForm.setEmail_Notice_4(subcomp.getEmail_Notice_4());
			SCForm.setEmail_Notice_5(subcomp.getEmail_Notice_5());
			SCForm.setShippingSurcharge(subcomp.getShippingSurcharge());
			SCForm.setName(subcomp.getName());
			SCForm.setSubcompanyCode(subcomp.getSubcompanyCode());
			SCForm.setId(subcomp.getId());
			SCForm.setEmail_Subject(subcomp.getEmail_Subject());
			
			if (subcomp.getCompany().getVariable().getLz_mode() == 
				TracingConstants.MOVETOLZ_MODE_ASSIGNMENT) {
				request.setAttribute("lzStations", LzUtils.getIncidentLzStationsBeans(subcomp.getCompany().getCompanyCode_ID()));
			}
			
			//check if adding agents to this group
			if (request.getParameter("addStations") != null) {
				HashMap<String,String> selectedStations = new HashMap<String,String>();

				String[] stationsSelected = request.getParameterValues("station_ID");
				if (stationsSelected != null) {
					for (int i = 0; i < stationsSelected.length; i++) {
						SubcompanyStation scs = null;
						scs=AdminUtils.getSubcompanyStation(subcomp.getId(),Long.valueOf(stationsSelected[i]));
						Station s=AdminUtils.getStation(stationsSelected[i]);
						if(scs==null){
							scs=new SubcompanyStation();
							scs.setSubcompany(subcomp);
							selectedStations.put("" + s.getStation_ID(), String.valueOf(subcomp.getId()));
							scs.setStation(s);
	
							//change association for the agent.
							HibernateUtils.save(scs);

						} else {
							selectedStations.put("" + s.getStation_ID(), String.valueOf(subcomp.getId()));
						}
					}
				}

				
				// IF AGENT IS NOT SELECTED AND AGENT'S EXISTING STATION == STATION
				// OTHER AGENTS ARE BEING ASSIGNED TO, THE AGENT IN QUESTION IS
				// INACTIVATED.

				String subcomp_id = null;

				if (request.getParameter("id") != null) subcomp_id = request
						.getParameter("id");

				List<SubcompanyStation> scslist = null;
				if (!subcomp_id.equals("-1")) scslist = AdminUtils.getSubcompanyStationsBySubcompany(Long.valueOf(subcomp_id));
						
				if(scslist!=null && scslist.size()>0){
					for (Iterator<SubcompanyStation> i = scslist.iterator(); i.hasNext();) {
						SubcompanyStation scs = (SubcompanyStation) i.next();
						if (scs.getSubcompany().getId() == subcomp.getId()) {
							// If agent was selected, leave it alone.
							if (selectedStations.get("" + scs.getStation().getStation_ID()) == null) {
								// If agent was displayed but not selected, deactivate it.
								HibernateUtils.delete(scs);
							}
						}
					}
				}
			}

			List<Station> stations = AdminUtils.getStationsBySubcompany(subcompId, null, 0, 0);
			
			if (stations != null && stations.size() > 0) {
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
				rowcount = stations.size();

				// find out total pages
				totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

				if (totalpages <= currpage) {
					currpage = 0;
					request.setAttribute("currpage", "0");
				}

				stations = AdminUtils.getStationsBySubcompany(subcompId, null, rowsperpage, currpage);

				if (currpage + 1 == totalpages) request.setAttribute("end", "1");
				if (totalpages > 1) {
					ArrayList<String> al = new ArrayList<String>();
					for (int i = 0; i < totalpages; i++) {
						al.add(Integer.toString(i));
					}
					request.setAttribute("pages", al);
				}

				/** ************ end of pagination ************* */

				request.setAttribute("stationList", stations);
			} else {
				int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_ADMIN_PAGES, session);
				request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

				int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
						.getParameter("currpage")) : 0;
				request.setAttribute("currpage", Integer.toString(currpage));
			}
			

			return mapping.findForward(TracingConstants.EDIT_SUBCOMPANY);
		}

		if (request.getParameter("companyCode") != null) companyCode = request.getParameter("companyCode");
		else {
			companyCode = user.getStation().getCompany().getCompanyCode_ID();
		}
		SCForm.setCompanyCode(companyCode);

		if (request.getParameter("addNew") != null) {	
			request.setAttribute("lzStations", LzUtils.getIncidentLzStationsBeans(companyCode));
			return mapping.findForward(TracingConstants.EDIT_SUBCOMPANY);
		}
		
		if (request.getParameter("addNewStation") != null) {
			Subcompany subcomp = SubCompanyDAO.loadSubcompany(Long.valueOf(request.getParameter("id")));

			//Get the station List for the company.
			List<Subcompany> subCompList = AdminUtils.getSubcompanies(SCForm, subcomp.getCompany().getCompanyCode_ID(), 0,
					0);
			if (subCompList != null) {
				List<LabelValueBean> x2 = new ArrayList<LabelValueBean>();
				Subcompany first = null;
				for (Iterator<Subcompany> i = subCompList.iterator(); i.hasNext();) {
					Subcompany subcomp2 = (Subcompany) i.next();
					if (first == null) first = subcomp2;
					x2.add(new LabelValueBean(subcomp2.getSubcompanyCode(), "" + subcomp2.getId()));
				}
				request.setAttribute("subCompList", subCompList);

				String subcomp_id = null;

				//get the station id from the request
				if (request.getParameter("id") != null) {
					subcomp_id = request.getParameter("id");
				} else {
					subcomp_id = "" + first.getId();
				}
				

				if (subcomp_id != null) {
					List<Station> stations = null;
					List<SubcompanyStation> subcompstations=null;
					
					stations = AdminUtils.getStations(subcomp.getCompany().getCompanyCode_ID(), 0, 0); //sort, SCForm,
					subcompstations=AdminUtils.getSubcompanyStationsBySubcompany(Long.valueOf(subcomp_id));

					HashMap<String,String> SCSMap=new HashMap<String,String>();
					for(SubcompanyStation scs:subcompstations){
						SCSMap.put(String.valueOf(scs.getStation().getStation_ID()), String.valueOf(scs.getSubcompany().getId()));
					}

					request.setAttribute("subCompanyStationMap", SCSMap);

					if (stations != null && stations.size() > 0) {
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
						rowcount = stations.size();

						// find out total pages
						totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

						if (totalpages <= currpage) {
							currpage = 0;
							request.setAttribute("currpage", "0");
						}

						stations = AdminUtils.getStations(subcomp.getCompany().getCompanyCode_ID(),	0, 0);
							
						if (currpage + 1 == totalpages) request.setAttribute("end", "1");
						if (totalpages > 1) {
							ArrayList<String> al = new ArrayList<String>();
							for (int i = 0; i < totalpages; i++) {
								al.add(Integer.toString(i));
							}
							request.setAttribute("pages", al);
						}

						/** ************ end of pagination ************* */

						request.setAttribute("stationList", stations);
					} else {
						int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_ADMIN_PAGES, session);
						request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

						int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
								.getParameter("currpage")) : 0;
						request.setAttribute("currpage", Integer.toString(currpage));
					}

					SCForm.setId(Long.valueOf(subcomp_id));
				}

				if (subcomp != null) {
					SCForm.setId(subcomp.getId());
					SCForm.setSubcompanyCode(subcomp.getSubcompanyCode());
					SCForm.setCompanyCode(subcomp.getCompany().getCompanyCode_ID());
					SCForm.setName(subcomp.getName());
					SCForm.setAuto_Close_High(subcomp.getAuto_Close_High());
					SCForm.setAuto_Close_Low(subcomp.getAuto_Close_Low());
					SCForm.setEmail_Notice_1(subcomp.getEmail_Notice_1());
					SCForm.setEmail_Notice_2(subcomp.getEmail_Notice_2());
					SCForm.setEmail_Notice_3(subcomp.getEmail_Notice_3());
					SCForm.setEmail_Notice_4(subcomp.getEmail_Notice_4());
					SCForm.setEmail_Notice_5(subcomp.getEmail_Notice_5());
					SCForm.setEmail_Subject(subcomp.getEmail_Path());
				}

			}
			return mapping.findForward(TracingConstants.EDIT_STATION_SUBCOMPANY);
		}

		if (request.getParameter("delete1") != null && !request.getParameter("delete1").equals("")) {
			String subCompId = request.getParameter("id");
			Subcompany subComp= SubCompanyDAO.loadSubcompany(Long.valueOf(subCompId));

			ActionMessage error = null;
			
			companyCode = subComp.getCompany().getCompanyCode_ID();
			if (!HibernateUtils.delete(subComp)) {
				error = new ActionMessage("error.deleting.station");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
		}

		if (request.getParameter("save") != null) {
			String subcompId = request.getParameter("id");
			Subcompany sc;
			if (subcompId != null && !subcompId.equals("0")) {
				sc = SubCompanyDAO.loadSubcompany(Integer.valueOf(subcompId));
			} else {
				sc = new Subcompany();	
				
				// Set unchangeable values here so that they will not change on edit. CG
				sc.setSubcompanyCode(SCForm.getSubcompanyCode());
				sc.setName(SCForm.getName());
				sc.setEmail_Path("/" + SCForm.getSubcompanyCode() + "/");
				sc.setSendDataplanEmails(true);
			}
			
			sc.setEmail_Notice_1(SCForm.getEmail_Notice_1());
			sc.setEmail_Notice_2(SCForm.getEmail_Notice_2());
			sc.setEmail_Notice_3(SCForm.getEmail_Notice_3());
			sc.setEmail_Notice_4(SCForm.getEmail_Notice_4());
			sc.setEmail_Notice_5(SCForm.getEmail_Notice_5());
			sc.setEmail_Subject(SCForm.getEmail_Subject());
			sc.setShippingSurcharge(SCForm.getShippingSurcharge());
			sc.setAuto_Close_High(SCForm.getAuto_Close_High());
			sc.setAuto_Close_Low(SCForm.getAuto_Close_Low());
			
			Company c = new Company();
			c.setCompanyCode_ID(companyCode);
			sc.setCompany(c);
						
			
			try {
				HibernateUtils.saveSubCompany(sc, user);
			} catch (Exception e) {
				ActionMessage error = new ActionMessage("error.creating.station");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return mapping.findForward(TracingConstants.EDIT_SUBCOMPANY);
			}
		}
		
		List<Subcompany> subCompList = null;

		subCompList = AdminUtils.getCustomSubCompanies(SCForm, companyCode, 0, 0);
		
		if (subCompList != null && subCompList.size() > 0) {
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
			rowcount = subCompList.size();

			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}

			subCompList = AdminUtils.getCustomSubCompanies(SCForm, companyCode, rowsperpage, currpage);

			if (currpage + 1 == totalpages) request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList<String> al = new ArrayList<String>();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}

			/** ************ end of pagination ************* */

			request.setAttribute("subCompList", subCompList);
		} else {
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_ADMIN_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			request.setAttribute("currpage", Integer.toString(currpage));
		}
		return mapping.findForward(TracingConstants.VIEW_SUBCOMPANIES);
	}
}