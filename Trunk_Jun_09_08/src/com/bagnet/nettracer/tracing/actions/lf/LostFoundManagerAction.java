package com.bagnet.nettracer.tracing.actions.lf;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import aero.nettracer.lf.services.LFServiceBean;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.forms.lf.LostFoundManagerForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class LostFoundManagerAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(LostFoundManagerAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		LFServiceBean serviceBean = new LFServiceBean();
		LostFoundManagerForm lfmForm = (LostFoundManagerForm) form;
		
		
		long rowcount = 0;
		int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
		int currpage = 0;
		List resultSet = new ArrayList();
		int type = 0;
		if (request.getParameter("openLost") != null) {
			type = TracingConstants.LF_TYPE_LOST;
			rowcount = serviceBean.getLostCount(user);
		} else {
			type = TracingConstants.LF_TYPE_FOUND;
			rowcount = serviceBean.getFoundCount(user);
		}
		
		currpage = lfmForm.getCurrpage() != null ? Integer.parseInt(lfmForm.getCurrpage()) : 0;
		if (lfmForm.getNextpage() != null && lfmForm.getNextpage().equals("1")) {
			currpage++;
		}

		if (lfmForm.getPrevpage() != null && lfmForm.getPrevpage().equals("1")) {
			currpage--;
		}
		
		int totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);
		
		if (request.getParameter("openLost") != null) {
			resultSet = serviceBean.getLostPaginatedList(user.getStation(), (currpage * rowsperpage), rowsperpage, user.getSubcompany());
		} else {
			resultSet = serviceBean.getFoundPaginatedList(user.getStation(), (currpage * rowsperpage), rowsperpage, user.getSubcompany());
		}

		if (totalpages <= currpage) {
			currpage = 0;
			request.setAttribute("currpage", "0");
		}
		
		boolean end = currpage + 1 == totalpages && totalpages > 1;
		if (end)
			request.setAttribute("end", "1");
		if (totalpages > 1) {
			ArrayList<String> al = new ArrayList<String>();
			for (int j = 0; j < totalpages; j++) {
				al.add(Integer.toString(j));
			}
			request.setAttribute("pages", al);
		}
		
		/***************** end pagination *****************/
		
		if (!end && resultSet.size() == 1) {
			if (type == TracingConstants.LF_TYPE_LOST) {
				long id = ((LFLost) resultSet.iterator().next()).getId();
				response.sendRedirect("create_lost_report.do?lostId=" + id);
			} else {
				long id = ((LFFound) resultSet.iterator().next()).getId();
				response.sendRedirect("create_found_item.do?foundId=" + id);
			}
			return null;
		}
		
		request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
		request.setAttribute("currpage", Integer.toString(currpage));		

		if (request.getParameter("openLost") != null) {
			request.setAttribute("resultList", resultSet);
			request.setAttribute("type", "lost");
			return mapping.findForward(TracingConstants.LF_VIEW_OPEN_LOST);
		} else if (request.getParameter("openFound") != null) {
			request.setAttribute("resultList", resultSet);
			request.setAttribute("type", "found");
			return mapping.findForward(TracingConstants.LF_VIEW_OPEN_FOUND);
		} else {
			return null;
		}
	
	}
	
}