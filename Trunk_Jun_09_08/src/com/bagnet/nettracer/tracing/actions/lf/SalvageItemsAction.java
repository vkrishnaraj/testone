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
import aero.nettracer.lf.services.exception.NonUniqueBarcodeException;
import aero.nettracer.lf.services.exception.UpdateException;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.forms.lf.HandleItemsForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class SalvageItemsAction extends CheckedAction {
	
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
		HandleItemsForm hiForm = (HandleItemsForm) form;
		hiForm.setDateFormat(user.getDateformat().getFormat());
		if (request.getParameter("salvageItems") != null) {
			salvageItems(hiForm.getFoundItems(), serviceBean, user);
		}
		
		long rowcount = 0;
		int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
		int currpage = 0;
		List<LFItem> resultSet = new ArrayList<LFItem>();
		
		if (TracingConstants.LF_LF_COMPANY_ID.equalsIgnoreCase(user.getCompanycode_ID()!=null?user.getCompanycode_ID():"")) {
			rowcount = serviceBean.getLFItemsToSalvageCount(user.getStation(), hiForm, user.getSubcompany());
		} else {
			rowcount = serviceBean.getItemsToSalvageCount(user.getStation(), user.getSubcompany());
		}
		
		currpage = hiForm.getCurrpage() != null ? Integer.parseInt(hiForm.getCurrpage()) : 0;
		if (hiForm.getNextpage() != null && hiForm.getNextpage().equals("1")) {
			currpage++;
		}

		if (hiForm.getPrevpage() != null && hiForm.getPrevpage().equals("1")) {
			currpage--;
		}
		
		int totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);
		
		if (TracingConstants.LF_LF_COMPANY_ID.equalsIgnoreCase(user.getCompanycode_ID()!=null?user.getCompanycode_ID():"")) {
			resultSet = serviceBean.getLFItemsToSalvagePaginatedList(user.getStation(), hiForm, (currpage * rowsperpage), rowsperpage, user.getSubcompany());
		} else {
			resultSet = serviceBean.getItemsToSalvagePaginatedList(user.getStation(), (currpage * rowsperpage), rowsperpage, user.getSubcompany());
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
		
		hiForm.setFoundItems(new ArrayList<LFItem>(resultSet));
		
		request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
		request.setAttribute("currpage", Integer.toString(currpage));		


		return mapping.findForward(TracingConstants.LF_VIEW_ITEMS_TO_SALVAGE);
		
	}
	
	private void salvageItems(ArrayList<LFItem> foundItems, LFServiceBean serviceBean, Agent agent) {
		Status status = new Status();
		status.setStatus_ID(TracingConstants.LF_STATUS_CLOSED);
		Status disposition = new Status();
		disposition.setStatus_ID(TracingConstants.LF_DISPOSITION_SALVAGED);
		if (foundItems != null) {
			for (LFItem item: foundItems) {
				if (item.isSelected()) {
					item.getFound().setStatus(status);
					item.setDisposition(disposition);
					try {
						serviceBean.saveOrUpdateFoundItem(item.getFound(), agent);
					} catch (NonUniqueBarcodeException e) {
						e.printStackTrace();
					} catch (UpdateException ue){
						ue.printStackTrace();
					}
				}
			}
		}
	}
	
}