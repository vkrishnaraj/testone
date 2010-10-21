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

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.BDOUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * @author Matt
 * 
 */
public class CancelBdoAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		if (session.getAttribute("user") == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		Agent user = (Agent) session.getAttribute("user");
		
		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CANCEL_BDO, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));

		int bdo_id = 0;
		boolean needToCancelWholeBdo = false;

		bdo_id = Integer.parseInt(request.getParameter("bdo_id"));
		
		if (bdo_id > 0) {
			if (request.getParameter("item_id") != null) {
				int item_id = Integer.parseInt(request.getParameter("item_id"));
				BDOUtils.cancelBdo(bdo_id, item_id, user);
			} else {
				BDOUtils.cancelBdo(bdo_id, -1, user);	
			}
			
			
			
			
		}
		
		response.sendRedirect("bdo.do?bdo_id=" + bdo_id);
		return null;
	}
}