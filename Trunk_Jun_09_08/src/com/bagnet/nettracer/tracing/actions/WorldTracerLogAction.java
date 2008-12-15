/**
 * 
 */
package com.bagnet.nettracer.tracing.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerTransaction;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerTransaction.Result;
import com.bagnet.nettracer.tracing.forms.WorldTracerLogsForm;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.bmo.WtTransactionBmo;
import com.bagnet.nettracer.wt.svc.WorldTracerService.TxType;

/**
 * @author Noah
 *
 */
public class WorldTracerLogAction extends Action {

	/**
	 * 
	 */
	public WorldTracerLogAction() {
		super();
	}
	
	private static WtTransactionBmo wttBmo = SpringUtils.getWtTxBmo();

	@Override
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
		
		request.setAttribute("wt_txtypes", EnumSet.allOf(TxType.class));	
		request.setAttribute("statuslist", EnumSet.allOf(Result.class));
		
		if(request.getParameter("reset") != null) {
			request.setAttribute("searchWtLogsForm", new WorldTracerLogsForm());
			
		}
		else if(request.getParameter("search") != null || request.getParameter("update") != null || request.getParameter("pagination") != null) {
			WorldTracerLogsForm wtForm = (WorldTracerLogsForm) form;
			
			SimpleDateFormat sdf = new SimpleDateFormat(user.getDateformat().getFormat());
			Date startDate = wtForm.getStartDate() == null || wtForm.getStartDate().trim().length() < 1 ? null : sdf.parse(wtForm.getStartDate());
			Date endDate = wtForm.getEndDate() == null || wtForm.getEndDate().trim().length() < 1? null : sdf.parse(wtForm.getEndDate());
			Calendar temp = new GregorianCalendar();
			if(endDate != null) {
				temp.setTime(endDate);
				temp.add(Calendar.DATE, 1);
				endDate = temp.getTime();
			}
			
			int rowsperpage = request.getParameter("rowsperpage") != null ? Integer.parseInt(request.getParameter("rowsperpage"))
					: TracingConstants.ROWS_PER_PAGE;
			if (rowsperpage < 1)
				rowsperpage = TracingConstants.ROWS_PER_PAGE;
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			int totalpages = 0;

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request.getParameter("currpage")) : 0;
			if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1"))
				currpage++;
			if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1"))
				currpage--;

			request.setAttribute("currpage", Integer.toString(currpage));

			// find out total pages
			
			int totalRows = wttBmo.getTransactionCount(wtForm.getTxType(), wtForm.getResult(), startDate, endDate, wtForm.getIncident_id(), wtForm.getOhd_id());
			totalpages = (int) Math.ceil((double) totalRows / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}
			
			if (currpage + 1 == totalpages)
				request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList al = new ArrayList();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}
			
			List<WorldTracerTransaction> results =  wttBmo.findTransactions(wtForm.getTxType(),
					wtForm.getResult(), startDate, endDate,
					wtForm.getIncident_id(), wtForm.getOhd_id(), currpage * rowsperpage, rowsperpage);
			
			request.setAttribute("resultlist", results);
		}
		
		return mapping.findForward("success");
	}
}
