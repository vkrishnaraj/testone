package com.bagnet.nettracer.tracing.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.bmo.CategoryBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Category;
import com.bagnet.nettracer.tracing.db.bagbuzz.BagBuzz;
import com.bagnet.nettracer.tracing.db.bagbuzz.Utils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class BagBuzzEditorAction extends Action{

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		Agent user = (Agent) session.getAttribute("user");
		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BAGBUZZ,user)) {
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		}
		
		boolean admin = (request.getParameter("admin_view") != null && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BAGBUZZ_ADMIN, user));
		
		//Category Actions
		if (admin && request.getParameter("addcategory") != null) {
			String newDescription = request.getParameter("newCategory");
			Utils.addCategory(newDescription);
			return navigateToAdmin(request, mapping);
		}
		if (admin && request.getParameter("editcategory") != null) {
			int editIndex = Integer.parseInt(request.getParameter("editcategory"));
			String editDescription = request.getParameter("bb_cat[" + editIndex + "].description");
			Utils.editCategory(editIndex, editDescription);
			return navigateToAdmin(request, mapping);
		}
		if (admin && request.getParameter("deletecategory") != null) {
			int deleteIndex = Integer.parseInt(request.getParameter("deletecategory"));
			Utils.deleteCategory(deleteIndex);
			return navigateToAdmin(request, mapping);
		}
		
		if (request.getParameter("bb_id") != null){
			BagBuzz bagbuzz = Utils.getBagBuzz(Long.parseLong(request.getParameter("bb_id")));
			if(bagbuzz != null && bagbuzz.getData() != null){
				request.setAttribute("bagbuzz", bagbuzz);
			}
		} else {
			BagBuzz bagbuzz = new BagBuzz();
			bagbuzz.setCategory(CategoryBMO.getCategory(TracingConstants.BAG_BUZZ_DEFAULT_CATEGORY));
			request.setAttribute("bagbuzz", bagbuzz);
		}
		
		request.setAttribute("bb_category_list", Utils.getBagBuzzCategoryValues());
		
		return (mapping.findForward(TracingConstants.VIEW_BAGBUZZ_EDITOR));
		
	}
	
	private ActionForward navigateToAdmin(HttpServletRequest request, ActionMapping mapping) {
		List<Category> catlist = Utils.getBagBuzzCategories();
		request.setAttribute("bb_cat_list", catlist);
		if (catlist.size() < PropertyBMO.getValueAsInt(PropertyBMO.BAGBUZZ_MAX_CATEGORIES)) {
			request.setAttribute("can_add_category", "1");
		}
		List <BagBuzz> bblist = Utils.getBagBuzzList(null);
		request.setAttribute("bb_list", bblist);
		request.setAttribute("admin", "1");
		return mapping.findForward(TracingConstants.VIEW_BAGBUZZ_SEARCH);
	}

}
