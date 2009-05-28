/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
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
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.forms.SearchBDOForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BDOUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SearchBDOAction extends Action {
	private static Logger logger = Logger.getLogger(SearchBDOAction.class);

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


		if (request.getParameter("search") == null
				&& request.getParameter("update") == null
				&& (request.getParameter("pagination") == null || !request.getParameter("pagination")
						.equals("1"))) {
			return (mapping.findForward(TracingConstants.SEARCH_BDO_MAIN));
		}

		// search
		SearchBDOForm daform = (SearchBDOForm) form;
		ArrayList resultlist = null;
		int rowcount = -1;

		// get number of records found
		if ((resultlist = BDOUtils.searchBDOs(daform, user, 0, 0, true)) == null
				|| resultlist.size() <= 0) {
			ActionMessages errors = new ActionMessages();
			ActionMessage error = new ActionMessage("error.nosearchresult");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
			return (mapping.findForward(TracingConstants.SEARCH_BDO_MAIN));
		} else {
			// get total records
			rowcount = ((Long) resultlist.get(0)).intValue();

			/** ************ pagination ************* */
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			int totalpages = 0;

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1")) currpage++;
			if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1")) currpage--;

			request.setAttribute("currpage", Integer.toString(currpage));

			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}

			resultlist = BDOUtils.searchBDOs(daform, user, rowsperpage, currpage, false);

			if (currpage + 1 == totalpages) request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList al = new ArrayList();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}

			/** ************ end of pagination ************* */
			BDO bdo = null;
			for (int i = 0; i < resultlist.size(); i++) {
				bdo = (BDO) resultlist.get(i);
				bdo.set_DATEFORMAT(user.getDateformat().getFormat());
				bdo.set_TIMEFORMAT(user.getTimeformat().getFormat());
				bdo.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
						.getTimezone()));
			}

			request.setAttribute("bdo_list", resultlist);

		}

		return (mapping.findForward(TracingConstants.SEARCH_BDO_MAIN));

	}
}