/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.constant.TracingConstants.SortParam;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.db.wtq.WtqAmendOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqCloseOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqOhdAction;
import com.bagnet.nettracer.tracing.forms.CloseOnHandForm;
import com.bagnet.nettracer.tracing.forms.OnHandForm;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;

/**
 * Implementation of <strong>Action </strong> that is responsible for generating
 * a list of all the bags that needs to be delivered to the customer.
 * 
 * @author Ankur Gupta
 */
public class ViewOnhands extends CheckedAction {
	private static Logger logger = Logger.getLogger(ViewOnhands.class);
	private String closedOhd_ID = null;
	private String notClosedOhd_ID = null;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
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

		Station agent_station = null;
		if (session.getAttribute("cbroStationID") != null) {
			agent_station = StationBMO.getStation((String) session.getAttribute("cbroStationID"));
		} else {
			agent_station = user.getStation();
		}
		
		if (request.getParameter("cancelFwd") != null && request.getParameter("ohd_ID") != null) {
			OHDUtils.cancelForward(request.getParameter("ohd_ID"), user);
		}
		
		//		 menu highlite
		request.setAttribute("highlite", TracingConstants.SYSTEM_COMPONENT_NAME_BAGS_IN_STATION);

		BagService bs = new BagService();
		SearchIncidentForm daform = new SearchIncidentForm();

		daform.setCompanycode_ID(agent_station.getCompany().getCompanyCode_ID());
		daform.setStationassigned_ID(agent_station.getStation_ID());

		List resultlist = null;
		
		// get number of records found
		/* 
		 * Getting the sort logic 
		 */
		String sort=request.getParameter((new ParamEncoder("ohd")).encodeParameterName(TableTagParameters.PARAMETER_SORT));
		boolean runReport = true;
		if(sort==null){
			sort=(request.getParameter("sortBy")!=null && request.getParameter("sortBy").length() > 0)?request.getParameter("sortBy").toString():TracingConstants.SortParam.OHD_NUM.getParamString();
		} else {
			runReport = false;
		}
		
		if (sort != null && sort.length() > 0 && SortParam.isValid(sort)) request.setAttribute("sortNum", sort);
		
		if ((resultlist = bs.findOnHandBagsBySearchCriteria(daform, user, 0, 0, true, true, true,sort)) == null
				|| resultlist.size() <= 0) {
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
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
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			int totalpages = 0;

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1")) currpage++;
			if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1")) currpage--;
			request.setAttribute("currpage", Integer.toString(currpage));
			// get row count
			rowcount = ((Long) resultlist.get(0)).intValue();
			if(rowcount!=0){
				request.setAttribute("rowcount", rowcount);
			} else {
				request.setAttribute("rowcount", 0);
			}
			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}
			//find the paginated on hand bags
			List searchList = bs.findOnHandBagsBySearchCriteria(daform, user, rowsperpage, currpage,
					false, true, true,sort);

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

		// Generate report
		if (runReport && request.getParameter("generateReport") != null && 
				request.getParameter("outputtype") != null) {
			
			try {
				String reportPath = getServlet().getServletContext().getRealPath("/");
				int outputType = new Integer(request.getParameter("outputtype")).intValue();
				String reportFile = null;
				int rc = ((Long) resultlist.get(0)).intValue();
				int maxRc = TracerProperties.getMaxReportRows(user.getStation().getCompany().getCompanyCode_ID()); 
									
				if (rc < maxRc) {
					List resultArray =  bs.findOnHandBagsBySearchCriteria(daform, user, 0, 0, false, true, true, sort);
					ReportBMO rbmo = new ReportBMO(request);

					reportFile = ReportBMO.createStationOnhandReport(resultArray, request, outputType, user.getCurrentlocale(), reportPath, rbmo);
					
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
		return (mapping.findForward(TracingConstants.ONHAND_LIST));
	}
}