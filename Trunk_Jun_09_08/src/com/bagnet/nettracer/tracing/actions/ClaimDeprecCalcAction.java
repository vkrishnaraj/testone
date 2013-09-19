/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.CategoryBMO;
import com.bagnet.nettracer.tracing.bmo.ClaimBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.Claim_Depreciation;
import com.bagnet.nettracer.tracing.db.Claim_Type;
import com.bagnet.nettracer.tracing.db.Depreciation_Category;
import com.bagnet.nettracer.tracing.db.Depreciation_Item;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.forms.ClaimDeprecCalcForm;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * @author Matt
 * 
 */
public class ClaimDeprecCalcAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		// check session
		TracerUtils.checkSession(session);

		if (session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		Agent user = (Agent) session.getAttribute("user");

		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1)
				+ ".do", user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		

		ClaimDeprecCalcForm theform = (ClaimDeprecCalcForm) form;
		ActionMessages errors = new ActionMessages();


		TracerUtils.populateLists(session);
		
		if(request.getParameter("save")!=null){
			if(theform.getClaim_id()!=0){
			ClaimBMO cBMO=new ClaimBMO();
			Claim c=cBMO.findClaimByID(theform.getClaim_id());
			theform.getClaimDeprec().setClaim(c);
			cBMO.saveClaimDepreciation(theform.getClaimDeprec());
			} else {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.noclaim"));
				saveMessages(request, errors);
				request.setAttribute("noclaim", "1");
			}
		}
		
		
		String claim = request.getParameter("claim_id");
		if (claim == null) {
			claim = (String) request.getAttribute("claim_id");
		}
		
		if (claim != null ) {
			ClaimBMO cBMO = new ClaimBMO();
			Claim_Depreciation CD=cBMO.getClaimDeprec(claim);
			if(CD!=null && CD.getItemlist()!=null){
				CD.set_DATEFORMAT(user.getDateformat().getFormat());
				for(Depreciation_Item di:CD.getItemlist()){
					di.set_DATEFORMAT(user.getDateformat().getFormat());
				}
				theform.setClaimDeprec(CD);
			} else if (CD == null) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.load.claim.deprec"));
				saveMessages(request, errors);
			}
		} else if (request.getParameter("clear") != null) {
			// came here from claim menu, need to show form to enter incident id
			request.setAttribute("noclaim", "1");
		}
		
		List<Claim_Type> ctypes = ClaimBMO.getClaimTypes();
			if (ctypes != null)
				request.setAttribute("claimtypes", ctypes);

		List<Depreciation_Category> dcats = CategoryBMO.getDepreciationCategories(user.getCompanycode_ID());
			if (dcats != null)
				request.setAttribute("categories", dcats);
		
		if(request.getParameter("addItems")!=null){
			int addnum=theform.getAddNum();
			for(int i=0;i<addnum;i++){
				Depreciation_Item di = theform.getDeprecItem(theform.getClaimDeprec().getItemlist().size());
				di.set_DATEFORMAT(user.getDateformat().getFormat());
				request.setAttribute("newItem", Integer.toString(theform.getClaimDeprec().getItemlist().size() - 1));
			}
		}
		boolean deleteCategory = false;

		String index = "0";
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String parameter = (String) e.nextElement();
			if (parameter.indexOf("[") != -1) {
				index = parameter.substring(parameter.indexOf("[") + 1, parameter.indexOf("]"));
					if (parameter.indexOf("deleteItem") != -1) {
						deleteCategory = true;
					break;
				}
			}
		}
		
		if (deleteCategory) {
			List<Depreciation_Item> itemList = theform.getClaimDeprec().getItemlist();
			int indexToRemove = Integer.parseInt(index);
			if (itemList != null && !itemList.isEmpty() && itemList.size() > indexToRemove) {
				int i = 0;
				Iterator<Depreciation_Item> iterator = itemList.iterator();
				while (iterator.hasNext()) {
					Depreciation_Item item = iterator.next();
					if (i == indexToRemove) {
//						remark.setFound(null);
						itemList.remove(item);
						if(item.getId()!=0)
							HibernateUtils.delete(item);
						break;
					}
					++i;
				}
			}
		}
		
		if(request.getParameter("printDeprecSum")!=null && request.getParameter("claimDeprecId")!=null){
			MessageResources messages = MessageResources
					.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
			StatReportDTO dsDTO=new StatReportDTO();
			ReportBMO rBMO=new ReportBMO(request);
			String claimDeprec=request.getParameter("claimDeprecId");
			dsDTO.setClaimId(Long.valueOf(claimDeprec));
			dsDTO.setReportnum(ReportingConstants.RPT_11);
			dsDTO.setOutputtype(TracingConstants.REPORT_OUTPUT_PDF);
			dsDTO.setCompanyCode(user.getCompanycode_ID());
			dsDTO.setDateFormat(user.getDateformat().getFormat());
			ServletContext sc = getServlet().getServletContext();
			String reportpath = sc.getRealPath("/");

			String reportfile = null;
			reportfile= rBMO.createReport(reportpath, dsDTO, user);
			
			if (reportfile == null || reportfile.equals("")) {
				//no data to report
				if (rBMO.getErrormsg() != null && rBMO.getErrormsg().length() > 0) {
					ActionMessage error = new ActionMessage(rBMO.getErrormsg());
					errors.add(ActionMessages.GLOBAL_MESSAGE,error);
				} else {
					ActionMessage error = new ActionMessage("message.nodata");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				}
				
				saveMessages(request, errors);
			} else {

				response.sendRedirect("reporting?outputtype=0&reportfile=" + reportfile);
			}
		}
		
			

		return (mapping.findForward(TracingConstants.CLAIM_DEPREC_CALC));
	}
	
}