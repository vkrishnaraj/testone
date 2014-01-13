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

import com.bagnet.nettracer.tracing.bmo.IssuanceItemBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceCategory;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItemInventory;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItemQuantity;
import com.bagnet.nettracer.tracing.forms.issuance.IssuanceItemAdminForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class IssuanceItemAdminAction extends Action {
	
	private static final Logger logger = Logger.getLogger(IssuanceItemAdminAction.class);
	
	/**
	 * Action for issuanceItemAdmin.do. JSP located at pages/issuance/issuance_admin.jsp
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

		IssuanceItemAdminForm fform = (IssuanceItemAdminForm) form;
		
		Station searchStation = user.getStation();
		if (fform.getStationsearch_ID() > 0) {
			searchStation = StationBMO.getStation(fform.getStationsearch_ID());
		}
		
		fform.setStationsearch_ID(searchStation.getStation_ID());
		
		// HANDLE HISTORY ACTIONS
		
		if (request.getParameter("quantity_history") != null) {
			response.sendRedirect("auditIssuanceItemAdmin.do?type=Q&station=" + searchStation.getStationcode());
			return null;
		}
		
		if (request.getParameter("historyquantity") != null) {
			String qID = request.getParameter("historyquantity");
			if (qID.matches("^\\d+$")) {
				response.sendRedirect("auditIssuanceItemAdmin.do?type=Q&id=" + qID + "&station=" + searchStation.getStationcode());
				return null;
			}
		}
		
		if (request.getParameter("inventory_history") != null) {
			response.sendRedirect("auditIssuanceItemAdmin.do?type=I&station=" + searchStation.getStationcode());
			return null;
		}
		
		if (request.getParameter("historyinventory") != null) {
			String qID = request.getParameter("historyinventory");
			if (qID.matches("^\\d+$")) {
				response.sendRedirect("auditIssuanceItemAdmin.do?type=I&id=" + qID + "&station=" + searchStation.getStationcode());
				return null;
			}
		}
		
		// HANDLE QUANTITY ACTIONS
		
		if (request.getParameter("editquantity") != null) {
			String qID = request.getParameter("editquantity");
			if (qID.matches("^\\d+$")) {
				String newQuantity = request.getParameter("item_quantity_" + qID);
				String newMinQuantity = request.getParameter("item_minquantity_" + qID);
				int intMinQuantity = -1;
				if (newMinQuantity != null && newMinQuantity.matches("^\\d+$")) {
					intMinQuantity = Integer.parseInt(newMinQuantity);
				}
				if (newQuantity != null && newQuantity.matches("^\\d+$")) {
					IssuanceItemBMO.editQuantifiedItem(Long.parseLong(qID), Integer.parseInt(newQuantity), intMinQuantity, user, null);
				}
			}
		}
		
		if (request.getParameter("issuequantity") != null) {
			String qID = request.getParameter("issuequantity");
			if (qID.matches("^\\d+$")) {
				String incID = request.getParameter("item_incid_" + qID);
				IssuanceItemBMO.editQuantifiedItem(Long.parseLong(qID), -1, -1, user, incID);
			}
		}
		
		// HANDLE INVENTORY ACTIONS
		
		if (request.getParameter("addinventory") != null) {
			String newType = (String) request.getParameter("item_type");
			String newCost = (String) request.getParameter("item_cost");
			String newDesc = (String) request.getParameter("item_desc");
			String newBarcode = (String) request.getParameter("item_barcode");
			String newTradetype = (String) request.getParameter("item_tradetype");
			if (newTradetype != null && newTradetype.matches("^\\d+$") && newCost != null && newCost.matches("^\\d*\\.?\\d+$")) {
				IssuanceItemBMO.addInventoriedItem(Long.parseLong(newType), newDesc, newBarcode, Integer.parseInt(newTradetype), user, searchStation, Double.parseDouble(newCost));
			}
		}
		
		if (request.getParameter("editinventory") != null) {
			String qID = request.getParameter("editinventory");
			if (qID.matches("^\\d+$")) {
				String newDesc = request.getParameter("item_desc_" + qID);
				String newBarcode = request.getParameter("item_barcode_" + qID);
				String newTradetype = request.getParameter("item_tradetype_" + qID);
				String newCost = request.getParameter("item_cost_" + qID);
				if (newTradetype != null && newTradetype.matches("^\\d+$") && newCost != null && newCost.matches("^\\d*\\.?\\d+$")) {
					IssuanceItemBMO.editInventoriedItem(Long.parseLong(qID), newDesc, newBarcode, Integer.parseInt(newTradetype), user, Double.parseDouble(newCost));
				}
			}
		}
		
		if (request.getParameter("issueinventory") != null) {
			String qID = request.getParameter("issueinventory");
			if (qID.matches("^\\d+$")) {
				String incID = request.getParameter("inv_item_incid_" + qID);
				IssuanceItemBMO.moveInventoriedItem(Long.parseLong(qID), TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_ISSUED, user, incID, "Issued Item");
			}
		}
		
		if (request.getParameter("loaninventory") != null) {
			String qID = request.getParameter("loaninventory");
			if (qID.matches("^\\d+$")) {
				String incID = request.getParameter("inv_item_incid_" + qID);
				IssuanceItemBMO.moveInventoriedItem(Long.parseLong(qID), TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_ONLOAN, user, incID, "Loaned Item");
			}
		}
		
		if (request.getParameter("snloaninventory") != null) {
			String qID = request.getParameter("snloaninventory");
			if (qID.matches("^\\d+$")) {
				String snFName = (String) request.getParameter("snFName");
				String snLName = (String) request.getParameter("snLName");
				String snAddr1 = (String) request.getParameter("snAddr1");
				String snAddr2 = (String) request.getParameter("snAddr2");
				String snCity = (String) request.getParameter("snCity");
				String snState = (String) request.getParameter("snState");
				String snProv = (String) request.getParameter("snProv");
				String snZip = (String) request.getParameter("snZip");
				String snCtry = (String) request.getParameter("snCtry");
				String snPhone = (String) request.getParameter("snPhone");
				String snDesc = (String) request.getParameter("snDesc");
				IssuanceItemBMO.moveInventoriedItem(Long.parseLong(qID), TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_ONLOAN, user, "$SNITEM$", "SN Loaned Item",
						true, snFName, snLName, snAddr1, snAddr2, snCity, snState, snProv, snZip, snCtry, snPhone, snDesc);
			}
		}
		
		if (request.getParameter("returninventory") != null) {
			String qID = request.getParameter("returninventory");
			if (qID.matches("^\\d+$")) {
				IssuanceItemBMO.moveInventoriedItem(Long.parseLong(qID), TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_AVAILABLE, user, null, "Returned Item");
			}
		}
		
		if (request.getParameter("discardinventory") != null) {
			String qID = request.getParameter("discardinventory");
			if (qID.matches("^\\d+$")) {
				String reason = request.getParameter("discardreason");
				IssuanceItemBMO.moveInventoriedItem(Long.parseLong(qID), TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_DISCARDED, user, null, "Discarded Item: " + reason);
			}
		}
		
		if (request.getParameter("convertinventory") != null) {
			String qID = request.getParameter("convertinventory");
			if (qID.matches("^\\d+$")) {
				IssuanceItemBMO.moveInventoriedItem(Long.parseLong(qID), TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_ISSUED, user, null, "Converted Item to Tradeout");
			}
		}

		fform = createIssuanceItemAdminForm(request);
		
		// GET QUANTITY ITEMS
		List<IssuanceItemQuantity> itemQuantityResults = IssuanceItemBMO.getQuantifiedItems(searchStation);
		
		if (itemQuantityResults != null) {
			fform.setItemQuantities(itemQuantityResults);
			request.setAttribute("item_quantity_resultList", fform.getItemQuantities());
		} else {
			request.setAttribute("item_quantity_resultList", new ArrayList<IssuanceItemQuantity>());
		}
		
		// GET INVENTORY ITEMS BY STATUS
		List<IssuanceItemInventory> itemInventoryResults = IssuanceItemBMO.getInventoriedItems(searchStation, TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_AVAILABLE);
		List<IssuanceItemInventory> itemInventoryLoanResults = IssuanceItemBMO.getInventoriedItems(searchStation, TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_ONLOAN);
		List<IssuanceItemInventory> itemInventoryIssueResults = IssuanceItemBMO.getInventoriedItems(searchStation, TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_ISSUED);
		List<IssuanceItemInventory> itemInventoryDiscardResults = IssuanceItemBMO.getInventoriedItems(searchStation, TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_DISCARDED);
		
		if (itemInventoryResults != null) {
			fform.setItemInventories(itemInventoryResults);
			request.setAttribute("item_inventory_resultList", fform.getItemInventories());
		} else {
			request.setAttribute("item_inventory_resultList", new ArrayList<IssuanceItemInventory>());
		}
		
		if (itemInventoryLoanResults != null) {
			fform.setItemLoanInventories(itemInventoryLoanResults);
			request.setAttribute("item_inventory_loan_resultList", fform.getItemLoanInventories());
		} else {
			request.setAttribute("item_inventory_loan_resultList", null);
		}
		
		if (itemInventoryIssueResults != null) {
			fform.setItemIssueInventories(itemInventoryIssueResults);
			request.setAttribute("item_inventory_issue_resultList", fform.getItemIssueInventories());
		} else {
			request.setAttribute("item_inventory_issue_resultList", null);
		}
		
		if (itemInventoryDiscardResults != null) {
			fform.setItemDiscardInventories(itemInventoryDiscardResults);
			request.setAttribute("item_inventory_discard_resultList", fform.getItemDiscardInventories());
		} else {
			request.setAttribute("item_inventory_discard_resultList", null);
		}
		
		// GET CATAGORIES FOR CREATING NEW INVENTORY ITEMS.
		List<IssuanceCategory> itemCategoryResults = IssuanceItemBMO.getItemCategories(user.getCompanycode_ID());
		
		if (itemCategoryResults != null) {
			fform.setItemCategories(itemCategoryResults);
			request.setAttribute("item_category_resultList", fform.getItemCategories());
		} else {
			request.setAttribute("item_category_resultList", new ArrayList<IssuanceCategory>());
		}
		
	    return (mapping.findForward(TracingConstants.ISSUANCE_ITEM_ADMIN));
	}
	
	/**
	 * Set FORMATS on form on first page load.
	 * @param request
	 * @return
	 */
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