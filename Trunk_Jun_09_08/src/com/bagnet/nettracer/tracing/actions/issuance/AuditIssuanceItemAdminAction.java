/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions.issuance;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.bmo.IssuanceItemBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.issuance.AuditIssuanceItemInventory;
import com.bagnet.nettracer.tracing.db.issuance.AuditIssuanceItemQuantity;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItemInventory;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItemQuantity;
import com.bagnet.nettracer.tracing.forms.issuance.AuditIssuanceItemAdminForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class AuditIssuanceItemAdminAction extends Action {
	
	private static final Logger logger = Logger.getLogger(AuditIssuanceItemAdminAction.class);
	
	/**
	 * Action for auditIssuanceItemAdmin.do. JSP located at pages/issuance/auditIssuanceItemAdmin.jsp.
	 */
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
				
		ActionMessages errors = new ActionMessages();

		AuditIssuanceItemAdminForm fform = (AuditIssuanceItemAdminForm) form;
		
		// HANDLE PARAMETERS FOR FIRST PAGE LOAD.
		
		if (request.getParameter("back") != null) {
			response.sendRedirect("issuanceItemAdmin.do");
			return null;
		}
		
		Station searchStation = user.getStation();
		if (request.getParameter("station") != null) {
			String stationCode = request.getParameter("station");
			searchStation = StationBMO.getStationByCode(stationCode, user.getCompanycode_ID());
		} else if (fform.getStationsearch_ID() > 0) {
			searchStation = StationBMO.getStation(fform.getStationsearch_ID());
		}
		
		long id = -1;
		if (request.getParameter("id") != null) {
			String itemID = request.getParameter("id");
			if (itemID.matches("^\\d+$")) {
				id = Long.parseLong(itemID);
			}
		}
		
		String type = "Q";
		if (request.getParameter("type") != null) {
			type = request.getParameter("type");
		}
		
		// DONE WITH FIRST PAGE LOAD. HANDLE IF USER CLICKS VIEW RESULTS BUTTON.
		
		if (request.getParameter("view_quantity") != null) {
			type = "Q";
			id = Long.parseLong(request.getParameter("quantity_id"));
		}
		
		if (request.getParameter("view_inventory") != null) {
			type = "I";
			id = Long.parseLong(request.getParameter("inventory_id"));
		}
		
		// DONE WITH BUTTON HADLING. LOAD PROPER LISTS AND FORWARD TO PAGE.
		
		request.setAttribute("id", id);
		fform.setStationsearch_ID(searchStation.getStation_ID());
		
		fform = createAuditIssuanceItemAdminForm(request, searchStation.getStation_ID());
						
		if ("Q".equals(type)) { // Loading quantity item audit. This means load audit list for station and item list for station.
			List<AuditIssuanceItemQuantity> auditItemQuantityResults = IssuanceItemBMO.getAuditQuantifiedItems(searchStation, id);
			
			if (auditItemQuantityResults != null) {
				fform.setAuditItemQuantities(auditItemQuantityResults);
				request.setAttribute("audit_item_quantity_resultList", fform.getAuditItemQuantities());
			} else {
				request.setAttribute("audit_item_quantity_resultList", new ArrayList<AuditIssuanceItemQuantity>());
			}
			List<IssuanceItemQuantity> itemQuantityResults = IssuanceItemBMO.getQuantifiedItems(searchStation);
			
			if (itemQuantityResults != null) {
				fform.setItemQuantities(itemQuantityResults);
				request.setAttribute("item_quantity_resultList", fform.getItemQuantities());
			} else {
				request.setAttribute("item_quantity_resultList", new ArrayList<IssuanceItemQuantity>());
			}
		}
		
		if ("I".equals(type)) { // Loading inventory item audit. This means load audit list for station and item list for station.
			List<AuditIssuanceItemInventory> auditItemInventoryResults = IssuanceItemBMO.getAuditInventoriedItems(searchStation, id);
			
			if (auditItemInventoryResults != null) {
				fform.setAuditItemInventories(auditItemInventoryResults);
				request.setAttribute("audit_item_inventory_resultList", fform.getAuditItemInventories());
			} else {
				request.setAttribute("audit_item_inventory_resultList", new ArrayList<AuditIssuanceItemInventory>());
			}
			List<IssuanceItemInventory> itemInventoryResults = IssuanceItemBMO.getInventoriedItems(searchStation, 0);
			
			if (itemInventoryResults != null) {
				fform.setItemInventories(itemInventoryResults);
				request.setAttribute("item_inventory_resultList", fform.getItemInventories());
			} else {
				logger.error("ERROR");
				request.setAttribute("item_inventory_resultList", new ArrayList<IssuanceItemInventory>());
			}
		}
		
	    return (mapping.findForward(TracingConstants.AUDIT_ISSUANCE_ITEM_ADMIN));
	}
	
	/**
	 * Sets FORMATS for first load of form.
	 * 
	 * @param request
	 * @param stationID
	 * @return
	 */
	private AuditIssuanceItemAdminForm createAuditIssuanceItemAdminForm(HttpServletRequest request, int stationID) {
		AuditIssuanceItemAdminForm fform = null;
		try {
			HttpSession session = request.getSession();
			fform = (AuditIssuanceItemAdminForm) request.getAttribute("auditIssuanceItemAdminForm");

			if (fform == null) {
				fform = new AuditIssuanceItemAdminForm();
			} 
	
			Agent user = (Agent) session.getAttribute("user");

			fform.set_DATEFORMAT(user.getDateformat().getFormat());
			fform.set_TIMEFORMAT(user.getTimeformat().getFormat());
			fform.set_TIMEZONE(TimeZone
					.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			fform.setStationsearch_ID(stationID);

			request.setAttribute("auditIssuanceItemAdminForm", fform);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return fform;
	}
	
}