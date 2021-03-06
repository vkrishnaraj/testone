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
import aero.nettracer.lf.services.LFUtils;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.dto.LFSearchDTO;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class SearchLostFoundAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(SearchLostFoundAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		LFSearchDTO searchDto = (LFSearchDTO) form;
		LFUtils.getLists(user, session);
		if (LFUtils.actionChangeSubCategory(searchDto.getCategory(), request)) {
			request.setAttribute("formName", "searchLostFoundForm");
			return mapping.findForward(TracingConstants.AJAX_SUBCATEGORY);
		}
		
		int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
		int currpage = 0;

		searchDto.setAgent(user);
		int type = searchDto.getType();
		if (TracingConstants.LF_LF_COMPANY_ID.equalsIgnoreCase(user.getCompanycode_ID()!=null?user.getCompanycode_ID():"")) {
			if (request.getParameter("found") != null) {
				type = TracingConstants.LF_TYPE_FOUND;
			} else if (request.getParameter("lost") != null) {
				type = TracingConstants.LF_TYPE_LOST;
			}
			searchDto.setType(type);
		}
		
		List resultSet = new ArrayList();
		if (request.getParameter("clear") == null) {
			LFServiceBean serviceBean = new LFServiceBean();
			long rowcount;
			if (type == TracingConstants.LF_TYPE_LOST) {
				rowcount = serviceBean.searchLostCount(searchDto);
			} else {
				rowcount = serviceBean.searchFoundCount(searchDto);
			}
	
			currpage = searchDto.getCurrpage() != null ? Integer.parseInt(searchDto.getCurrpage()) : 0;
			if (searchDto.getNextpage() != null && searchDto.getNextpage().equals("1"))
				currpage++;
			if (searchDto.getPrevpage() != null && searchDto.getPrevpage().equals("1"))
				currpage--;
			
			
			int totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);
			
			if (type == TracingConstants.LF_TYPE_LOST) {
				resultSet = serviceBean.searchLost(searchDto, (currpage * rowsperpage), rowsperpage);
			} else {
				resultSet = serviceBean.searchFound(searchDto, (currpage * rowsperpage), rowsperpage);
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

		}
		
		request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
		request.setAttribute("currpage", Integer.toString(currpage));		
		
		request.setAttribute("resultList", resultSet);
		
		if (searchDto.getStatus() == null) {
			Status lfStatus = new Status();
			lfStatus.setStatus_ID(TracingConstants.LF_STATUS_ALL);
			searchDto.setStatus(lfStatus);
		}
	
		if (searchDto.getDisposition() == null) {
			Status lfDisposition = new Status();
			lfDisposition.setStatus_ID(TracingConstants.LF_STATUS_ALL);
			searchDto.setDisposition(lfDisposition);
		}
		
		if(searchDto.getStationId()!=-1){
			request.setAttribute("selectStation",searchDto.getStationId());
		}
		
		return mapping.findForward(TracingConstants.LF_SEARCH_LOST_FOUND);
		
	}
	
}