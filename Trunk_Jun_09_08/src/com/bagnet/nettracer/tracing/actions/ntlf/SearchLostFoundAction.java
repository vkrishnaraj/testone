package com.bagnet.nettracer.tracing.actions.ntlf;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import aero.nettracer.lf.services.LFServiceBean;
import aero.nettracer.lf.services.LFUtils;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.dto.LFSearchDTO;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
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

		if (session.getAttribute("stationList") == null) {
			session.setAttribute("stationList", AdminUtils.getStations(null, user.getCompanycode_ID(), 0, 0));
		}
		
		LFUtils.getLists(user, session);
		if (LFUtils.actionChangeSubCategory(searchDto.getCategory(), request)) {
			request.setAttribute("formName", "searchLostFoundForm");
			return mapping.findForward(TracingConstants.AJAX_SUBCATEGORY);
		}
		
		int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
		int currpage = 0;

		searchDto.setAgent(user);
		int type = searchDto.getType();
		if (type <= 0) {
			type = TracingConstants.LF_TYPE_FOUND;
			searchDto.setType(type);
		}
		
		if (request.getParameter("tmhvSearch") != null) {
			searchDto.setValue(TracingConstants.LFC_ITEM_HIGH_VALUE);
			searchDto.setStationId(user.getStation().getStation_ID());
			searchDto.setStatusId(TracingConstants.LF_STATUS_OPEN);
			searchDto.setDispositionId(-1);
		}

		if (request.getParameter("generateReport") != null && 
				request.getParameter("outputtype") != null) {
			LFServiceBean serviceBean = new LFServiceBean();
			
			try {
				String reportPath = getServlet().getServletContext().getRealPath("/");
				int outputType = new Integer(request.getParameter("outputtype")).intValue();
				String reportFile = null;
								
				int rc = serviceBean.searchFoundCount(searchDto);
				int maxRc = TracerProperties.getMaxReportRows(user.getStation().getCompany().getCompanyCode_ID()); 
									
				if (rc < maxRc) {
					List<LFFound> resultArray =  serviceBean.searchFound(searchDto, -99, -99);
					ReportBMO rbmo = new ReportBMO(request);

					reportFile = ReportBMO.createSearchFoundItemReport(resultArray, request, outputType, user.getCurrentlocale(), reportPath, rbmo);
					
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
		
		List<LFFound> resultSet = new ArrayList<LFFound>();
		if (request.getParameter("clear") == null) {
			LFServiceBean serviceBean = new LFServiceBean();
			long rowcount;
			rowcount = serviceBean.searchFoundCount(searchDto);
	
			currpage = searchDto.getCurrpage() != null ? Integer.parseInt(searchDto.getCurrpage()) : 0;
			if (searchDto.getNextpage() != null && searchDto.getNextpage().equals("1"))
				currpage++;
			if (searchDto.getPrevpage() != null && searchDto.getPrevpage().equals("1"))
				currpage--;
			
			
			int totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);
			
			resultSet = serviceBean.searchFound(searchDto, (currpage * rowsperpage), rowsperpage);
	
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
				long id = ((LFFound) resultSet.iterator().next()).getId();
				response.sendRedirect("ntlf_create_found_item.do?foundId=" + id);
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
		
		return mapping.findForward(TracingConstants.NTLF_SEARCH_LOST_FOUND);
		
	}
	
}