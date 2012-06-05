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
import org.apache.struts.action.ActionMessages;

import aero.nettracer.lf.services.LFServiceBean;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFSalvage;
import com.bagnet.nettracer.tracing.forms.lfc.SalvageSearchForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.dto.SalvageDTO;

public class SalvageSearchAction extends CheckedAction {
	
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
		SalvageSearchForm ssForm = (SalvageSearchForm) form;
		ssForm.setDateFormat(user.getDateformat().getFormat());
		if (request.getParameter("clear") == null) {
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
			int currpage = 0;
			
			long rowcount = serviceBean.getSalvageCount(user.getStation(), ssForm);
			
			currpage = ssForm.getCurrpage() != null ? Integer.parseInt(ssForm.getCurrpage()) : 0;
			if (ssForm.getNextpage() != null && ssForm.getNextpage().equals("1"))
				currpage++;
			if (ssForm.getPrevpage() != null && ssForm.getPrevpage().equals("1"))
				currpage--;
			
			int totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);
			List<SalvageDTO> salvages = new ArrayList();
			//List<LFSalvage>
			salvages = serviceBean.getSalvageSearch(user.getStation(), ssForm, (currpage * rowsperpage), rowsperpage);
					//.getSalvagesPaginated(user.getStation(), ssForm, (currpage * rowsperpage), rowsperpage);
			
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
			
//			ssForm.setSalvageList(salvages);
			request.setAttribute("salvages", salvages);
		}
		
		return mapping.findForward(TracingConstants.LFC_SEARCH_SALVAGE);
	}
	
}