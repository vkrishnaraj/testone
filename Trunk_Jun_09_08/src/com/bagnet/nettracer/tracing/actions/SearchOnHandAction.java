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

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerUtils;
import com.bagnet.nettracer.wt.svc.WorldTracerService;

/**
 * Implementation of <strong>Action </strong> that is responsible for providing
 * search capabilities to on-hands. It returns a paginated results.
 * 
 * @author Ankur Gupta
 */
public class SearchOnHandAction extends Action {
	
	private static Logger logger = Logger.getLogger(SearchOnHandAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		// check session and user validity
		TracerUtils.checkSession(session);
		
		boolean notClosed = false;

		if (session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		Agent user = (Agent) session.getAttribute("user");
		
		SearchIncidentForm daform = (SearchIncidentForm) form;
		
		
		if (request.getParameter("search") == null && request.getParameter("generateReport") == null) {
			
			//daform.setAirline(user.getStation().getCompany().getCompanyCode_ID());
			daform.setCompanycreated_ID(user.getStation().getCompany().getCompanyCode_ID());
			daform.setCompanycode_ID(user.getStation().getCompany().getCompanyCode_ID());
			String tmp = PropertyBMO.getValue(PropertyBMO.PROPERTY_DEFAULT_OHD_SEARCH_STATUS);
			if(tmp == null) {
				daform.setStatus_ID(TracingConstants.OHD_STATUS_OPEN);
			}
			else {
				daform.setStatus_ID(Integer.parseInt(tmp));
			}
			return (mapping.findForward(TracingConstants.SEARCH_ONHAND));
		}

		BagService bs = new BagService();

		if (request.getParameter("generateReport") != null && 
				request.getParameter("outputtype") != null) {
			
			try {
				String reportPath = getServlet().getServletContext().getRealPath("/");
				int outputType = new Integer(request.getParameter("outputtype")).intValue();
				String reportFile = null;
				
				if(daform.getStatus_ID() == TracingConstants.OHD_STATUS_ACTIVE) {
					notClosed = true;
				}
				
				List countArray =  bs.findOnHandBagsBySearchCriteria(daform, user, 0, 0, true, notClosed, true);
				int rc = ((Long) countArray.get(0)).intValue();
				int maxRc = TracerProperties.getMaxReportRows(user.getStation().getCompany().getCompanyCode_ID()); 
									
				if (rc < maxRc) {
					List resultArray =  bs.findOnHandBagsBySearchCriteria(daform, user, 0, 0, false, notClosed, true);
					ReportBMO rbmo = new ReportBMO(request);

					reportFile = ReportBMO.createSearchOnhandReport(resultArray, request, outputType, user.getCurrentlocale(), reportPath, rbmo);
					
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

		

		List resultlist = null;

		if(daform.getStatus_ID() == TracingConstants.OHD_STATUS_ACTIVE) {
			notClosed = true;
		}
		// get number of records found
		if ((resultlist = bs.findOnHandBagsBySearchCriteria(daform, user, 0, 0, true, notClosed, true)) == null
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
			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}

			//find the paginated on hand bags
			List searchList = bs.findOnHandBagsBySearchCriteria(daform, user, rowsperpage, currpage,
					false, notClosed, true);

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