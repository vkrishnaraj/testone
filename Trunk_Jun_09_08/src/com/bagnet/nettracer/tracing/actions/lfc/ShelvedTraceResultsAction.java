package com.bagnet.nettracer.tracing.actions.lfc;

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

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.taskmanager.GeneralTask;
import com.bagnet.nettracer.tracing.db.taskmanager.ItemTraceResultsTask;
import com.bagnet.nettracer.tracing.forms.lfc.ShelvedTraceResultsForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.taskmanager.ShelvedTraceResultsTaskUtil;
import com.bagnet.nettracer.tracing.utils.taskmanager.TaskManagerUtil;

public class ShelvedTraceResultsAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(EnterItemsAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		TracerUtils.checkSession(session);
		ActionMessages errors = new ActionMessages();

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		LFServiceBean serviceBean = new LFServiceBean();
		ShelvedTraceResultsForm strForm = (ShelvedTraceResultsForm) form;
		
		if (request.getParameter("getNextItem") != null) {
			GeneralTask task = null;

			// Make 3 attempts to get an item to work
			for (int i = 0; i < 3; ++i) {
				System.out.println("getting task attempt: " + (i + 1));
				task = ShelvedTraceResultsTaskUtil.getNextTask(user, strForm.getValue());
				if (task != null) {
					response.sendRedirect("create_found_item.do?foundId=" + ((ItemTraceResultsTask) task).getFoundItem().getId());
					return null;
				}
			}
			
			// We couldn't get an item, so display an error message
			ActionMessage error = new ActionMessage("error.lf.no.items.available");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
		}
		
		int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
		int currpage = 0;
		
		long rowcount = serviceBean.getShelvedTraceResultsCount(user.getStation(), strForm.getValue());
		
		currpage = strForm.getCurrpage() != null ? Integer.parseInt(strForm.getCurrpage()) : 0;
		if (strForm.getNextpage() != null && strForm.getNextpage().equals("1"))
			currpage++;
		if (strForm.getPrevpage() != null && strForm.getPrevpage().equals("1"))
			currpage--;
		
		int totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

		List<LFFound> founds = serviceBean.getShelvedTraceResultsPaginated(user.getStation(), strForm.getValue(), (currpage * rowsperpage), rowsperpage, user.getSubcompany());
		
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

		request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
		request.setAttribute("currpage", Integer.toString(currpage));		
		
		strForm.setFoundList(new ArrayList<LFFound>(founds));
		
		return mapping.findForward(TracingConstants.LFC_SHELVED_TRACE_RESULTS);
	}
	
}