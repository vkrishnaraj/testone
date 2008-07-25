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

import org.apache.commons.httpclient.HttpClient;
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
import com.bagnet.nettracer.tracing.forms.SearchOnHandForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.wt.WTIncident;
import com.bagnet.nettracer.wt.WTOHD;
import com.bagnet.nettracer.wt.WorldTracerUtils;

/**
 * Implementation of <strong>Action </strong> that is responsible for providing
 * search capabilities to on-hands. It returns a paginated results.
 * 
 * @author Ankur Gupta
 */
public class SearchOnHandAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		// check session and user validity
		TracerUtils.checkSession(session);

		if (session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		Agent user = (Agent) session.getAttribute("user");
		
		List oStatusList = OHDUtils.getOhdStatusList(user.getCurrentlocale());
		request.setAttribute("oStatusList", oStatusList);
		
		SearchOnHandForm daform = (SearchOnHandForm) form;
		
		// user passed in worldtracer id, so find it in db or retrieve it from worldtracer
		if (request.getParameter("wt_id") != null && request.getParameter("wt_id").length() == 10) {
			OHD foundohd = WorldTracerUtils.findOHDByWTID(request.getParameter("wt_id"));
			if (foundohd == null) {
				HttpClient client = WorldTracerUtils.connectWT(user.getStation().getCompany().getVariable().getWt_url() + "/",user.getCompanycode_ID());
				String wt_http = WorldTracerUtils.getWt_url(user.getCompanycode_ID());
				String wt_url = "http://" + wt_http + "/";
				String result = WorldTracerUtils.getROF(client, request.getParameter("wt_id"),wt_url);
				WTOHD wi = new WTOHD();
				// for now show all as active
				foundohd = wi.parseWTOHD(result,true,WorldTracerUtils.status_active);
				if (foundohd != null) response.sendRedirect("addOnHandBag.do?ohd_ID=" + foundohd.getOHD_ID());
				else {
					ActionMessages errors = new ActionMessages();
					ActionMessage error = new ActionMessage("error.wt_nostation");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				}
				
			} else {
				response.sendRedirect("addOnHandBag.do?ohd_ID=" + foundohd.getOHD_ID());
				
			}
		}
		
		
		if (request.getParameter("search") == null) {
			
			//daform.setAirline(user.getStation().getCompany().getCompanyCode_ID());
			daform.setFoundCompany(user.getStation().getCompany().getCompanyCode_ID());
			daform.setHeldCompany(user.getStation().getCompany().getCompanyCode_ID());
			daform.setStatus_ID(Integer.toString(TracingConstants.OHD_STATUS_OPEN));
			return (mapping.findForward(TracingConstants.SEARCH_ONHAND));
		}

		BagService bs = new BagService();
		

		List resultlist = null;

		// get number of records found
		if ((resultlist = bs.findOnHandBagsBySearchCriteria(daform, user, 0, 0, true, false)) == null
				|| resultlist.size() <= 0) {
			int rowsperpage = request.getParameter("rowsperpage") != null ? Integer.parseInt(request
					.getParameter("rowsperpage")) : TracingConstants.ROWS_PER_PAGE;
			if (rowsperpage < 1) rowsperpage = TracingConstants.ROWS_PER_PAGE;
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			request.setAttribute("currpage", Integer.toString(currpage));

			ActionMessages errors = new ActionMessages();
			ActionMessage error = new ActionMessage("error.no.onhandreport");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
		} else {
			int rowcount = -1;
			int rowsperpage = request.getParameter("rowsperpage") != null ? Integer.parseInt(request
					.getParameter("rowsperpage")) : TracingConstants.ROWS_PER_PAGE;
			if (rowsperpage < 1) rowsperpage = TracingConstants.ROWS_PER_PAGE;
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			int totalpages = 0;

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1")) currpage++;
			if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1")) currpage--;
			request.setAttribute("currpage", Integer.toString(currpage));
			// get row count
			rowcount = ((Long) resultlist.get(0)).intValue();
			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}

			//find the paginated on hand bags
			List searchList = bs.findOnHandBagsBySearchCriteria(daform, user, rowsperpage, currpage,
					false, false);

			if (currpage + 1 == totalpages) request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList al = new ArrayList();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}

			/** ************ end of pagination ************* */
			for (int i = 0; i < searchList.size(); i++) {
				OHD ic = (OHD) searchList.get(i);
				ic.set_DATEFORMAT(user.getDateformat().getFormat());
				ic.set_TIMEFORMAT(user.getTimeformat().getFormat());
				ic.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
						.getTimezone()));
			}
			request.setAttribute("onhandlist", searchList);
		}
		return (mapping.findForward(TracingConstants.SEARCH_ONHAND));
	}
}