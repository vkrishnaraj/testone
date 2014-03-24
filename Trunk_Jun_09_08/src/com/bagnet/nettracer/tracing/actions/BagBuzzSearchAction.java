package com.bagnet.nettracer.tracing.actions;

import java.io.PrintWriter;
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
import com.bagnet.nettracer.tracing.db.taskmanager.BagBuzzTask;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class BagBuzzSearchAction extends Action{

	private static BagBuzz getBagBuzz(HttpServletRequest request){
		BagBuzz bb = new BagBuzz();
		bb.setBagbuzz_id(Long.parseLong(request.getParameter("bb_id")));
		System.out.println(request.getParameter("data"));
		bb.setData(request.getParameter("data"));
		if (request.getParameter("description") != null) {
			bb.setDescription(request.getParameter("description").trim());
		}
		if (request.getParameter("category.id") != null && request.getParameter("category.id").matches("^\\d*$")) {
			bb.setCategory(CategoryBMO.getCategory(Integer.parseInt(request.getParameter("category.id"))));
		}
		return bb;
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		
		Agent user = (Agent) session.getAttribute("user");
		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)
				&& !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BAGBUZZ, user)) {
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		}
		
		boolean admin = (request.getParameter("admin_view") != null && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BAGBUZZ_ADMIN, user));
				
		if(request.getParameter("view") != null){
			request.setAttribute("bb", Utils.getBagBuzz(Long.parseLong(request.getParameter("bb_id"))));
			if (request.getParameter("bbt_id") != null){
				Utils.updateBagBuzzTaskReadStatus(Long.parseLong(request.getParameter("bbt_id")));
			}
			return (mapping.findForward(TracingConstants.VIEW_BAGBUZZ_VIEW));
		}
		if(request.getParameter("save") != null){
			Utils.saveBagBuzz(BagBuzzSearchAction.getBagBuzz(request));
			this.closeWindow(response);
			return null;
		}
		if(request.getParameter("publish") != null){
			Utils.publishBagBuzz(BagBuzzSearchAction.getBagBuzz(request), null);
		}
		if(request.getParameter("unpublish") != null){
			Utils.unpublishBagBuzz(BagBuzzSearchAction.getBagBuzz(request));
		}
		if(request.getParameter("delete") != null){
			Utils.deleteBagBuzz(BagBuzzSearchAction.getBagBuzz(request));
		}
		if(request.getParameter("copy") != null){
			Utils.copyBagBuzz(Long.parseLong(request.getParameter("bb_id")));
		}
		
		if(admin){
			List<Category> catlist = Utils.getBagBuzzCategories();
			request.setAttribute("bb_cat_list", catlist);
			if (catlist.size() < PropertyBMO.getValueAsInt(PropertyBMO.BAGBUZZ_MAX_CATEGORIES)) {
				request.setAttribute("can_add_category", "1");
			}
			List <BagBuzz> bblist = Utils.getBagBuzzList(null);
			request.setAttribute("bb_list", bblist);
			request.setAttribute("admin", "1");
		} else {
			if (request.getParameter("category") != null) {
				long category = Long.parseLong(request.getParameter("category"));
				List <BagBuzzTask> bbt_list = Utils.getBagBuzzTaskList(user, category);
				request.setAttribute("bbt_list", bbt_list);
			}
		}
		return mapping.findForward(TracingConstants.VIEW_BAGBUZZ_SEARCH);
	}
	
	private void closeWindow(HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
	    out.write("<html>");
	    out.write("<script language=\"javascript\">");
	    out.write("self.close();");
	    out.write("</script>");
	    out.write("</html>");
	    response.flushBuffer();
	}

}