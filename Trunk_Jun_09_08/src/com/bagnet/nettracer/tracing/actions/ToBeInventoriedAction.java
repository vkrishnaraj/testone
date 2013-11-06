/**
 * 
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.constant.TracingConstants.SortParam;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * Implementation of <strong>Action</strong> that is responsible for generating
 * a list of all the bags that needs to be inventoried.
 */
public class ToBeInventoriedAction extends Action {
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

		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)) {
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		}

		Station agent_station = null;
		if (session.getAttribute("cbroStationID") != null) {
			agent_station = StationBMO.getStation((String) session.getAttribute("cbroStationID"));
		} else {
			agent_station = user.getStation();
		}

		//menu highlite
		request.setAttribute("highlite", TracingConstants.SYSTEM_COMPONENT_NAME_TO_BE_INVENTORIED);

		BagService bs = new BagService();
		SearchIncidentForm daform = new SearchIncidentForm();
		daform.setStatus_ID(TracingConstants.OHD_STATUS_TO_BE_INVENTORIED);
		daform.setCompanycode_ID(agent_station.getCompany().getCompanyCode_ID());
		daform.setStationassigned_ID(agent_station.getStation_ID());

		boolean dirtyRead = true;
		List<?> searchList = bs.findOnHandBagsBySearchCriteria(daform, user, 0, 0, true, false, dirtyRead, null);
		if (searchList == null || searchList.isEmpty()) {
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request.getParameter("currpage")) : 0;
			request.setAttribute("currpage", Integer.toString(currpage));

			ActionMessages errors = new ActionMessages();
			ActionMessage error = new ActionMessage("error.no.onhandreport");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
			
			return (mapping.findForward(TracingConstants.TO_BE_INVENTORIED_LIST));
		} 
		
		//Getting the sort logic
		String sort=StringUtils.stripToNull(request.getParameter((new ParamEncoder("ohd")).encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		if(sort==null) {
			sort=StringUtils.stripToNull((request.getParameter("sortBy")!=null && request.getParameter("sortBy").length() > 0)?request.getParameter("sortBy").toString():TracingConstants.SortParam.OHD_NUM.getParamString());
		}
		
		if (SortParam.isValid(sort)) {
			request.setAttribute("sortNum", sort);
		}		

		int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
		request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

		int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request.getParameter("currpage")) : 0;
		if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1")) {
			currpage++;
		}
		if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1")) {
			currpage--;
		}
		request.setAttribute("currpage", Integer.toString(currpage));
		
		// get row count
		int rowcount = ((Long) searchList.get(0)).intValue();
		if(rowcount!=0){
			request.setAttribute("rowcount", Integer.toString(rowcount));
		} else {
			request.setAttribute("rowcount", "0");
		}
		// find out total pages
		int totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

		if (totalpages <= currpage) {
			currpage = 0;
			request.setAttribute("currpage", "0");
		}

		//find the paginated on hand bags
		searchList = bs.findOnHandBagsBySearchCriteria(daform, user, rowsperpage, currpage, false, false, dirtyRead, sort);
		if (searchList == null) {
			searchList=ListUtils.EMPTY_LIST;
		}

		if (currpage + 1 == totalpages) {
			request.setAttribute("end", "1");
		}
		if (totalpages > 1) {
			ArrayList<String> al = new ArrayList<String>();
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
			ic.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
		}
		request.setAttribute("onhandlist", searchList);
		
		return (mapping.findForward(TracingConstants.TO_BE_INVENTORIED_LIST));
	}
}
