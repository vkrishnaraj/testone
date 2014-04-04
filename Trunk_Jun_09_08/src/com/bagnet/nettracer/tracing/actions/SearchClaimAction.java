/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import aero.nettracer.fs.model.FsClaim;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.ClaimDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.forms.SearchClaimForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class SearchClaimAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(SearchClaimAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MODIFY_CLAIM, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		
		if(!manageToken(request)) {
			return (mapping.findForward(TracingConstants.INVALID_TOKEN));
		}
		
		/***************** begin pagination *****************/
		int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
		int currpage = 0;

		SearchClaimForm theForm = (SearchClaimForm) form;
		Set<FsClaim> resultSet = new LinkedHashSet<FsClaim>();
		if (request.getParameter("clear") == null) {
			ClaimDAO cdao = new ClaimDAO();
			long rowcount = cdao.getClaimCountFromSearchForm(theForm, user);
	
			currpage = theForm.getCurrpage() != null ? Integer.parseInt(theForm.getCurrpage()) : 0;
			if (theForm.getNextpage() != null && theForm.getNextpage().equals("1"))
				currpage++;
			if (theForm.getPrevpage() != null && theForm.getPrevpage().equals("1"))
				currpage--;
			
			
			int totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);
			
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

			resultSet = cdao.getClaimsFromSearchForm(theForm, user, rowsperpage, currpage);
			if (!end && resultSet.size() == 1) {
				long claimId = resultSet.iterator().next().getId();
				
				logger.info("Redirecting -> claim_resolution.do?claimId=" + claimId);
				response.sendRedirect("claim_resolution.do?claimId=" + claimId);
				return null;
			}

		}
		request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
		request.setAttribute("currpage", Integer.toString(currpage));		
		
		request.setAttribute("resultList", resultSet);
		return mapping.findForward(TracingConstants.CLAIM_SEARCH);
	}
	
}