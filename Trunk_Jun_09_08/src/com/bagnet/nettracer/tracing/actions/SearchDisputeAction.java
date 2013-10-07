package com.bagnet.nettracer.tracing.actions;

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

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.dr.Dispute;
import com.bagnet.nettracer.tracing.db.dr.DisputeUtils;
import com.bagnet.nettracer.tracing.forms.SearchDisputeForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class SearchDisputeAction extends CheckedAction {
	private static Logger logger = Logger.getLogger(SearchDisputeAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		String forwardTarget = TracingConstants.VIEW_DISPUTES;

		// check session
		TracerUtils.checkSession(session);

		if(session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		Agent user = (Agent) session.getAttribute("user");
		boolean bagLossCodes=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LOSS_CODES_BAG_LEVEL, user) && PropertyBMO.isTrue(PropertyBMO.PROPERTY_BAG_LEVEL_LOSS_CODES);
		if(bagLossCodes || (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)
				&& !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MANAGE_FAULT_DISPUTE, user)))
			forwardTarget = TracingConstants.NO_PERMISSION;
		
		//find out request type
		String actionType = "" + request.getParameter("actionType");
		
		
		if (actionType.equalsIgnoreCase("manage")) {	// list all disputes limited access
			forwardTarget = TracingConstants.VIEW_DISPUTES;
		}
		
		SearchDisputeForm theform = (SearchDisputeForm) form;
		
		if(request.getParameter("disputeType")!=null)
		{
			theform.setDispute_type(Integer.parseInt(request.getParameter("disputeType")));
		}
		session.setAttribute("disputeType",theform.getDispute_type());
		
		int disputeType=theform.getDispute_type();
		if (request.getParameter("getnext")!=null)
		{
			Dispute d=DisputeUtils.getDispute(user, theform.getDispute_type());
			if(d!=null){
				String nextDispute=d.getIncident().getIncident_ID();
				if(nextDispute!=null){
					response.sendRedirect("disputeResolution.do?id="+nextDispute+"&actionType=viewToResolve");
					return null;
				}
			}
			
		}
		
		// search
		List<Dispute> resultList = new ArrayList<Dispute>();
		long rowcount = -1;
		
		String incidentIdFromSearchForm = theform.getIncident_ID();
		
		
		//if the incident id is specified, then it comes from search button
		//in this case there is at most one dispute returned;
		//if the dispute is not open status, then view only for everyone, no more update
		if (incidentIdFromSearchForm != null & (!incidentIdFromSearchForm.equals(""))) {
			boolean viewOnlyWithNoUpdate = false;
			Dispute searchByIncidentIdResult = DisputeUtils.getDisputeByIncidentId(incidentIdFromSearchForm);

			if (searchByIncidentIdResult != null) {
				resultList.add(searchByIncidentIdResult);
				
				Status myStatus = searchByIncidentIdResult.getStatus();
				if (myStatus != null) {
					if (myStatus.getStatus_ID() != TracingConstants.DISPUTE_RESOLUTION_STATUS_OPEN) {
						viewOnlyWithNoUpdate = true;
					}
				}

				request.setAttribute("viewOnlyWithNoUpdate", viewOnlyWithNoUpdate);
			}
			forwardTarget = TracingConstants.VIEW_DISPUTES;
		} else {
			rowcount = DisputeUtils.getDisputeCount(user, disputeType);
			if (rowcount <= 0) {
				ActionMessages errors = new ActionMessages();
				ActionMessage error = new ActionMessage("error.nosearchresult");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				forwardTarget = TracingConstants.VIEW_DISPUTES;
			} else {
				// get total records
				Dispute dispute = null;

				/** ************ pagination ************* */
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
				totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

				if (totalpages <= currpage) {
					currpage = 0;
					request.setAttribute("currpage", "0");
				}

				resultList = DisputeUtils.getPaginatedDisputeList(user, rowsperpage, currpage, false, true, disputeType, false);

				if (currpage + 1 == totalpages)
					request.setAttribute("end", "1");
				if (totalpages > 1) {
					ArrayList al = new ArrayList();
					for (int i = 0; i < totalpages; i++) {
						al.add(Integer.toString(i));
					}
					request.setAttribute("pages", al);
				}			
				
			}
		}
				
		
		request.setAttribute("resultlist", resultList);
		return mapping.findForward(forwardTarget);
	}
}
