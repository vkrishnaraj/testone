/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.ExpenseType;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.forms.ClaimForm;
import com.bagnet.nettracer.tracing.forms.ClaimProrateForm;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class ClaimSettlementAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		String incident_id = null;

		// check session
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
	String number = (String) request.getParameter("screen");
	int num = 0;
	
	try {
		num = Integer.parseInt(number);
	} catch (Exception e) {
		e.printStackTrace();
	}
		
	switch(num) {
		case 1:
			return (mapping.findForward(TracingConstants.CLAIM_SETTLEMENT1));
	
		case 2:
			return (mapping.findForward(TracingConstants.CLAIM_SETTLEMENT2));

		case 3:
			return (mapping.findForward(TracingConstants.CLAIM_SETTLEMENT3));

		case 4:
			return (mapping.findForward(TracingConstants.CLAIM_SETTLEMENT4));
	}


	return (mapping.findForward(TracingConstants.CLAIM_SETTLEMENT));
	}
}