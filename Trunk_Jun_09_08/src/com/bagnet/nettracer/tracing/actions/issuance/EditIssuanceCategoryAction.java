package com.bagnet.nettracer.tracing.actions.issuance;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

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
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceCategory;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItem;
import com.bagnet.nettracer.tracing.forms.issuance.EditIssuanceCategoryForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * 
 * @author Tyrillius
 *
 */
public class EditIssuanceCategoryAction extends Action {
	
	private static final Logger logger = Logger.getLogger(EditIssuanceCategoryAction.class);
	
	/**
	 * This action runs before loading editIssuanceCategory.do. JSP located at pages/issuance/edit_issuance_category.jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
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

		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ISSUANCE_ITEMS_GLOBAL_ADMIN, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		
		ActionMessages errors = new ActionMessages();

		EditIssuanceCategoryForm fform = (EditIssuanceCategoryForm) form;
		
		// HANDLE ANY PARAMETERS FIRST IN CASE THIS IS AN ACTION ON THE PAGE.
		
		if (request.getParameter("addcategory") != null) {
			return (mapping.findForward(getCategory(fform, null)));
		}
		
		if (request.getParameter("editcategory") != null) {
			return (mapping.findForward(getCategory(fform, request.getParameter("editcategory"))));
		}
		
		if (request.getParameter("id") != null) {
			return (mapping.findForward(getCategory(fform, request.getParameter("id"))));
		}
		
		if (request.getParameter("add_category_item") != null) {
			IssuanceItem newItem = new IssuanceItem();
			newItem.setCategory(fform.getCategory());
			fform.getCategory().getItems().add(newItem);
			return (mapping.findForward(TracingConstants.EDIT_ISSUANCE_CATEGORY));
		}
		//Get template from Database and set documentlist bean
		List<Template> templateResults = IssuanceItemBMO.getTemplate();
		
		if (templateResults != null) 
			fform.setDocumentList(templateResults);

		if (request.getParameter("save_category") != null) {
			IssuanceItemBMO.saveCategory(fform.getCategory(), user);
			return (mapping.findForward(TracingConstants.EDIT_ISSUANCE_CATEGORY));
		}
		
		// AFTER THIS POINT MEANS LOADING PAGE FOR THE FIRST TIME, SO GO GET CATEGORY LIST TO DISPLAY.
		
		List<IssuanceCategory> itemCategoryResults = IssuanceItemBMO.getItemCategories(user.getCompanycode_ID());
		
		if (itemCategoryResults != null) {
			fform.setItemCategories(itemCategoryResults);
			request.setAttribute("item_category_resultList", fform.getItemCategories());
		} else {
			request.setAttribute("item_category_resultList", new ArrayList<IssuanceCategory>());
		}
		
		return (mapping.findForward(TracingConstants.EDIT_ISSUANCE_CATEGORY));
	}
	
	/**
	 * Gets a new category or loads one from the database and sets it on the form.
	 * 
	 * @param fform
	 * @param id
	 * @return
	 */
	private String getCategory(EditIssuanceCategoryForm fform, String id) {
		IssuanceCategory cat = new IssuanceCategory();
		if (id != null) {
			cat = IssuanceItemBMO.getItemCategory(id);
		}
		createItemList(cat);
		fform.setCategory(cat);
		return TracingConstants.EDIT_ISSUANCE_CATEGORY;
	}
	
	/**
	 * If the category has no items this creates the list and adds a blank item to it.
	 * 
	 * @param cat
	 */
	private void createItemList(IssuanceCategory cat) {
		if (cat.getItems() == null) {
			cat.setItems(new LinkedHashSet<IssuanceItem>());
			IssuanceItem newItem = new IssuanceItem();
			newItem.setCategory(cat);
			cat.getItems().add(newItem);
		}
	}
	
}