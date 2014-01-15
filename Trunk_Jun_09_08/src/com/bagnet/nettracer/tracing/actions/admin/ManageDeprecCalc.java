/*
 * Created on Jul 9, 2004
 *
 */
package com.bagnet.nettracer.tracing.actions.admin;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.bmo.CategoryBMO;
import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Depreciation_Category;
import com.bagnet.nettracer.tracing.db.Depreciation_Item;
import com.bagnet.nettracer.tracing.db.GeneralDepreciationRules;
import com.bagnet.nettracer.tracing.forms.DeprecCalcAdminForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * Implementation of <strong>Action </strong> that is responsible for managing depreciation calculator information.
 * 
 * @author Sean Fine
 */
public final class ManageDeprecCalc extends Action {
		
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		// check session
		TracerUtils.checkSession(session);
		
		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)) return (mapping
				.findForward(TracingConstants.NO_PERMISSION));

		ActionMessages errors = new ActionMessages();
		DeprecCalcAdminForm theform = (DeprecCalcAdminForm) form;
		String companyCode = user.getCompanycode_ID();


		//retrieve all timezones and populate the list.
		request.setAttribute("timezones", AdminUtils.getTimeZones());
		
		List<Depreciation_Category> deprecCats;
		
		if (request.getParameter("save") != null || request.getParameter("addcategory")!=null) {
			GeneralDepreciationRules rules=CompanyBMO.getDeprecRules(companyCode);

			if(rules==null){
				rules=new GeneralDepreciationRules();
				rules.setCompanyCode(companyCode);
			}

			rules.setTwentyOnefiftyDeprec(theform.getTwentyOnefiftyDeprec());
			rules.setLessTwentyDeprec(theform.getLessTwentyDeprec());
			rules.setOnefiftyDeprec(theform.getOnefiftyDeprec());
			rules.setNoDates(theform.getNoDates());
			try {
				HibernateUtils.saveDepreciationRules(rules, user);
			} catch (Exception ex) {
				ActionMessage error = new ActionMessage("error.creating.deprec.rules");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
			for(Depreciation_Category dc:theform.getCategories()){
				
				dc.setCompanyCode(companyCode);
							
				try {
					HibernateUtils.saveDepreciationCategory(dc, user);
					request.setAttribute("saved", 1);
				} catch (Exception ex) {
					ActionMessage error = new ActionMessage("error.creating.deprec.cat");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				}
			}

		}

		
		GeneralDepreciationRules rules=CompanyBMO.getDeprecRules(companyCode);
		if(rules!=null){
			theform.setTwentyOnefiftyDeprec(rules.getTwentyOnefiftyDeprec());
			theform.setLessTwentyDeprec(rules.getLessTwentyDeprec());
			theform.setOnefiftyDeprec(rules.getOnefiftyDeprec());
			theform.setNoDates(rules.getNoDates());
		}
		deprecCats = CategoryBMO.getDepreciationCategories(companyCode);
		if (deprecCats != null){
			theform.setCategories(deprecCats);
		} else {

			ActionMessage error = new ActionMessage("error.loading.cats");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
		}
		
		if (request.getParameter("addcategory") != null) {
			theform.getCategory(theform.getCategories().size());
			request.setAttribute("newCategory", Integer.toString(theform.getCategories().size() - 1));
		}


		boolean deleteCategory = false;

		String index = "0";
		@SuppressWarnings("rawtypes")
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String parameter = (String) e.nextElement();
			if (parameter.indexOf("[") != -1) {
				index = parameter.substring(parameter.indexOf("[") + 1, parameter.indexOf("]"));
					if (parameter.indexOf("deleteCategory") != -1) {
						deleteCategory = true;
					break;
				}
			}
		}
		if (deleteCategory) {
			List<Depreciation_Category> catList = theform.getCategories();
			int indexToRemove = Integer.parseInt(index);
			if (catList != null && !catList.isEmpty() && catList.size() > indexToRemove) {
				int i = 0;
				Iterator<Depreciation_Category> iterator = catList.iterator();
				while (iterator.hasNext()) {
					Depreciation_Category category = iterator.next();
					if (i == indexToRemove) {
						List<Depreciation_Item> ilist=CategoryBMO.getDeprecItemsByCategory(category.getId());
						if(ilist!=null && ilist.size()==0){
							catList.remove(category);
							if(category.getId()!=0)
								HibernateUtils.delete(category);
							request.setAttribute("catDeleted", 1);
							break;
						} else {
							ActionMessage error = new ActionMessage("error.delete.used.cat");
							errors.add(ActionMessages.GLOBAL_MESSAGE, error);
							saveMessages(request, errors);
						}
					}
					++i;
				}
			}
		}

		return mapping.findForward(TracingConstants.DEPREC_CALC_ADMIN);
	}
}