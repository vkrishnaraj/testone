/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions.issuance;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.axis2.transport.TransportUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import aero.nettracer.fs.model.forum.FsForumPost;
import aero.nettracer.fs.model.forum.FsForumSearch;
import aero.nettracer.fs.model.forum.FsForumTag;
import aero.nettracer.fs.model.forum.FsForumThread;
import aero.nettracer.fs.model.transport.v3.forum.FsForumSearchResults;
import aero.nettracer.fs.model.transport.v3.forum.FsForumThreadInfo;
import aero.nettracer.fs.utilities.TransportMapper;
import aero.nettracer.selfservice.fraud.client.ClaimClientRemote;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.bmo.IssuanceItemBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItemQuantity;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.forms.ClaimForm;
import com.bagnet.nettracer.tracing.forms.forum.ForumSearchForm;
import com.bagnet.nettracer.tracing.forms.forum.ForumViewForm;
import com.bagnet.nettracer.tracing.forms.issuance.IssuanceItemAdminForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.ntfs.ConnectionUtil;

import org.apache.struts.action.Action;

public class IssuanceItemAdminAction extends Action {
	
	private static final Logger logger = Logger.getLogger(IssuanceItemAdminAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ISSUANCE_ITEMS_STATION_ADMIN, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		
		if (request.getParameter("back") != null) {
			request.setAttribute("back", "1");
		}
		
		ActionMessages errors = new ActionMessages();

		IssuanceItemAdminForm fform = (IssuanceItemAdminForm) form;
		
		if (request.getParameter("quantity_history") != null) {
			response.sendRedirect("auditIssuanceItemAdmin.do");
			return null;
		}
		
		if (request.getParameter("editquantity") != null) {
			String qID = request.getParameter("editquantity");
			if (qID != null && qID.matches("^\\d+$")) {
				String newQuantity = request.getParameter("item_quantity_" + qID);
				String newMinQuantity = request.getParameter("item_minquantity_" + qID);
				if (newQuantity != null && newQuantity.matches("^\\d+$") && newMinQuantity != null && newMinQuantity.matches("^\\d+$")) {
					IssuanceItemBMO.editQuantifiedItem(Long.parseLong(qID), Integer.parseInt(newQuantity), Integer.parseInt(newMinQuantity), user, null);
				}
			}
		}
		
		if (request.getParameter("issuequantity") != null) {
			String qID = request.getParameter("issuequantity");
			if (qID != null && qID.matches("^\\d+$")) {
				String incID = request.getParameter("item_incid_" + qID);
				IssuanceItemBMO.editQuantifiedItem(Long.parseLong(qID), -1, -1, user, incID);
			}
		}
		
		List<IssuanceItemQuantity> itemQuantityResults = IssuanceItemBMO.getQuantifiedItems(user.getStation());
		
		if (itemQuantityResults != null) {
			logger.info("GOT QUANTIFIED ITEM RESULTS");
			
			fform = createIssuanceItemAdminForm(request);
			
			fform.setItemQuantities(itemQuantityResults);

			request.setAttribute("item_quantity_resultList", fform.getItemQuantities());
		} else {
			logger.error("TAG OR THREAD ERROR");
			request.setAttribute("item_quantity_resultList", new ArrayList<IssuanceItemQuantity>());
		}
		
		/***************** end pagination *****************/
		
	    return (mapping.findForward(TracingConstants.ISSUANCE_ITEM_ADMIN));
	}
	
	private IssuanceItemAdminForm createIssuanceItemAdminForm(HttpServletRequest request) {
		IssuanceItemAdminForm fform = null;
		try {
			HttpSession session = request.getSession();
			fform = (IssuanceItemAdminForm) request.getAttribute("issuanceItemAdminForm");

			if (fform == null) {
				fform = new IssuanceItemAdminForm();
			} 
	
			Agent user = (Agent) session.getAttribute("user");

			fform.set_DATEFORMAT(user.getDateformat().getFormat());
			fform.set_TIMEFORMAT(user.getTimeformat().getFormat());
			fform.set_TIMEZONE(TimeZone
					.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));

			request.setAttribute("issuanceItemAdminForm", fform);

		} catch (Exception e) {
			logger.error("bean copy claim form error on populateClaim: " + e);
			e.printStackTrace();
		}
		return fform;
	}
	
}