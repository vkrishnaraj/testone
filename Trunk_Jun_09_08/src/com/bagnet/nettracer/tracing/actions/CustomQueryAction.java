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

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.forms.OnHandForm;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CustomQueryAction extends Action {
	private static Logger logger = Logger.getLogger(CustomQueryAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		if (session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		Agent user = (Agent) session.getAttribute("user");

		String searchtype = request.getParameter("searchtype");
		
		if (searchtype != null && (searchtype.equals("1") || searchtype.equals("2") || searchtype.equals("3") || searchtype.equals("4"))) {
			request.setAttribute("incident", searchtype);
			request.removeAttribute("ohd");
		}
		
		if (searchtype != null && searchtype.equals("5")) {
			request.removeAttribute("incident");
			request.setAttribute("ohd", "1");
		}
		
		List oStatusList = OHDUtils.getOhdStatusList(user.getCurrentlocale());
		request.setAttribute("oStatusList", oStatusList);

		IncidentForm theform = new IncidentForm();
		OnHandForm ohdform = new OnHandForm();

		String report_id = request.getParameter("reportid");

		// if user comes here first, just display the screen
		// auto set search type if user comes to this page
		if (request.getParameter("search") == null
				&& request.getParameter("update") == null
				&& (request.getParameter("pagination") == null || !request.getParameter("pagination")
						.equals("1"))) {
			request.setAttribute("incident", "1");
			// go straight to the page
			if (report_id == null || report_id.length() == 0) {

				SearchIncidentForm newform = new SearchIncidentForm();
				
				if (searchtype != null && (searchtype.equals("1") || searchtype.equals("2") || searchtype.equals("3") || searchtype.equals("4"))) {
					newform.setStatus_ID(TracingConstants.MBR_STATUS_OPEN);
				}
				
				if (searchtype != null && searchtype.equals("5")) {
					newform.setStatus_ID(TracingConstants.OHD_STATUS_OPEN);
				}

				
				//newform.setAirline(user.getStation().getCompany().getCompanyCode_ID());
				request.setAttribute("searchIncidentForm",newform);
				return (mapping.findForward(TracingConstants.CUSTOM_QUERY));
			} else {
				// go straight to the report (mbr or ohd)
				if (searchtype != null && searchtype.equals("1")) {//mbr
					response.sendRedirect("searchIncident.do?incident=" + report_id);
					return null;
				} else if (searchtype != null && searchtype.equals("2")) { // ohd
					response.sendRedirect("addOnHandBag.do?ohd_ID=" + report_id);
					return null;
				} else {
					// throw an error
					return (mapping.findForward(TracingConstants.CUSTOM_QUERY));
				}

			}

		} else {

			// start searching

			SearchIncidentForm daform = (SearchIncidentForm) form;
			
			if (request.getParameter("changeStatuses") != null &&
					request.getParameter("changeStatuses").equals("" + TracingConstants.AJAX_STATUS_INC) ||
					request.getParameter("changeStatuses").equals("" + TracingConstants.AJAX_STATUS_OHD))	{
			
				if (searchtype != null && (searchtype.equals("1") || searchtype.equals("2") || searchtype.equals("3") || searchtype.equals("4"))) {
					daform.setStatus_ID(TracingConstants.MBR_STATUS_OPEN);
				}
				
				if (searchtype != null && searchtype.equals("5")) {
					daform.setStatus_ID(TracingConstants.OHD_STATUS_OPEN);
				}

				
				return (mapping.findForward(TracingConstants.AJAX_STATUSES));
				
			}

			
			ArrayList resultlist = null;

			resultlist = doSearch(daform, user, request, searchtype);

			// returned one result go straight to the page
			if (resultlist != null && resultlist.size() == 1) {

				if (searchtype != null && searchtype.equals("1") || searchtype.equals("2") 
						|| searchtype.equals("3") || searchtype.equals("4")) { // search mbr

					// go straight to the mbr page
					Incident inc = (Incident) resultlist.get(0);
					report_id = inc.getIncident_ID();
					response.sendRedirect("searchIncident.do?incident=" + report_id);
					return null;

				} else if (searchtype != null && searchtype.equals("5")) { // search ohd

					// go straight to the ohd page
					OHD inc = (OHD) resultlist.get(0);
					report_id = inc.getOHD_ID();
					response.sendRedirect("addOnHandBag.do?ohd_ID=" + report_id);
					return null; //return (mapping.findForward(gotoOHD(report_id,
											 // ohdform, user, request)));
				}

			}
		}

		return (mapping.findForward(TracingConstants.CUSTOM_QUERY));

	}

	/**
	 * searching incident or on-hand
	 * 
	 * @param daform
	 * @param user
	 * @param request
	 * @param searchtype
	 * @return
	 */
	private ArrayList doSearch(SearchIncidentForm daform, Agent user, HttpServletRequest request,
			String searchtype) {
		// get number of records found
		ArrayList resultlist = null;
		BagService bs = new BagService();
		int rowcount = -1;
		String report_id = null;

		if ((resultlist = bs.customQuery(daform, user, 0, 0, true, searchtype)) == null
				|| resultlist.size() <= 0) {
			ActionMessages errors = new ActionMessages();
			ActionMessage error = new ActionMessage("error.nosearchresult");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
		} else {
			// get total records
			rowcount = ((Long) resultlist.get(0)).intValue();
			if (rowcount == 1) {
				resultlist = bs.customQuery(daform, user, 0, 0, false, searchtype);
			} else {
				Incident ic = null;
				OHD oc = null;

				/** ************ pagination ************* */
				int rowsperpage = request.getParameter("rowsperpage") != null ? Integer.parseInt(request
						.getParameter("rowsperpage")) : TracingConstants.ROWS_PER_PAGE;
				if (rowsperpage < 1) rowsperpage = TracingConstants.ROWS_PER_PAGE;
				request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
				int totalpages = 0;

				int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
						.getParameter("currpage")) : 0;
				if (request.getParameter("nextpage") != null
						&& request.getParameter("nextpage").equals("1")) currpage++;
				if (request.getParameter("prevpage") != null
						&& request.getParameter("prevpage").equals("1")) currpage--;

				request.setAttribute("currpage", Integer.toString(currpage));

				// find out total pages
				totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

				if (totalpages <= currpage) {
					currpage = 0;
					request.setAttribute("currpage", "0");
				}

				resultlist = bs.customQuery(daform, user, rowsperpage, currpage, false, searchtype);

				if (currpage + 1 == totalpages) request.setAttribute("end", "1");
				if (totalpages > 1) {
					ArrayList al = new ArrayList();
					for (int i = 0; i < totalpages; i++) {
						al.add(Integer.toString(i));
					}
					request.setAttribute("pages", al);
				}

				/** ************ end of pagination ************* */

				for (int i = 0; i < resultlist.size(); i++) {
					if (searchtype.equals("1") || searchtype.equals("2") || searchtype.equals("3") || searchtype.equals("4")) {
						ic = (Incident) resultlist.get(i);
						ic.set_DATEFORMAT(user.getDateformat().getFormat());
						ic.set_TIMEFORMAT(user.getTimeformat().getFormat());
						ic.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
								user.getDefaulttimezone()).getTimezone()));
					} else {
						oc = (OHD) resultlist.get(i);
						oc.set_DATEFORMAT(user.getDateformat().getFormat());
						oc.set_TIMEFORMAT(user.getTimeformat().getFormat());
						oc.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
								user.getDefaulttimezone()).getTimezone()));
					}
				}

				request.setAttribute("resultlist", resultlist);

			}
		}

		return resultlist;

	}

}