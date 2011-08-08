package com.bagnet.nettracer.tracing.actions.onlineclaims;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.OnlineClaimsDao;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class DisplayClaimsListAction extends Action {
	private static final int ONE_DAY = 86400000;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		DynaActionForm dynaForm = (DynaActionForm) form;
		
		if (session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		Agent user = (Agent) session.getAttribute("user");

		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CENTRAL_BAGGAGE_CLAIMS_FEATURES, user)) {
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		}
		
		request.setAttribute("highlite", TracingConstants.SYSTEM_COMPONENT_NAME_CENTRAL_BAGGAGE_CLAIMS_FEATURES);

		// BEGIN BUSINESS LOGIC

		String status = (String) dynaForm.get("claimStatus");
		Date startDate = DateUtils.convertToDate((String) dynaForm.get("startDate"), user.getDateformat().getFormat(), null);
		Date endDate = DateUtils.convertToDate((String) dynaForm.get("endDate"), user.getDateformat().getFormat(), null);
		if (endDate != null) {
			endDate = new Date(endDate.getTime() + ONE_DAY);
		}
		int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
		request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
		int totalpages = 0;

		int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request.getParameter("currpage")) : 0;
		if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1"))
			currpage++;
		if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1"))
			currpage--;

		request.setAttribute("currpage", Integer.toString(currpage));

		// find out total pages
		int rowcount = -1;
		
		OnlineClaimsDao d = new OnlineClaimsDao();
		
		rowcount = d.getClaimsListCount(status, startDate, endDate, rowsperpage, currpage, user.getStation().getLz_ID(), user.getStation().getLz_ID());
		
		totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);
		System.out.println("\n\n\n STATS FOR OC SEARCH: \n\n TOTAL PAGES: " + totalpages + "\n ROW COUNT: " + rowcount + "\n ROWS PER: "
				+ rowsperpage + "\n\n\n");
		if (totalpages <= currpage) {
			currpage = 0;
			request.setAttribute("currpage", "0");
		}
		
		if (currpage + 1 == totalpages) request.setAttribute("end", "1");
		if (totalpages > 1) {
			ArrayList al = new ArrayList();
			for (int i = 0; i < totalpages; i++) {
				al.add(Integer.toString(i));
			}
			request.setAttribute("pages", al);
		}

		List<OnlineClaim> list = d.getClaimsList(status, startDate, endDate, rowsperpage, currpage, user.getStation().getLz_ID(), user.getStation().getLz_ID());
		
		request.setAttribute("resultlist", list);
		
		return mapping.findForward(TracingConstants.FORWARD_OC_LIST);
	}
}
