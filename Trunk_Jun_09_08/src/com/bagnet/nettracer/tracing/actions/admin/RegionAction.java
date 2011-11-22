package com.bagnet.nettracer.tracing.actions.admin;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.bmo.RegionBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Region;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.forms.RegionForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class RegionAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		
		Agent user = (Agent) session.getAttribute("user");
		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1)
				+ ".do", user)
				&& !UserPermissions
						.hasPermission(
								TracingConstants.SYSTEM_COMPONENT_NAME_REGION,
								user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		
		RegionForm theForm = (RegionForm)form;
		session.setAttribute("form", theForm);
		
		if(request.getParameter("view") != null){
			
			ArrayList<Region> regions = new ArrayList<Region>();
			Region noRegion = new Region();
			noRegion.setId(0);
			noRegion.setName("Not assigned");
			regions.add(noRegion);
			regions.addAll(RegionBMO.getRegions(user.getCompanycode_ID()));

			ArrayList <Station>stations = (ArrayList<Station>) AdminUtils.getCustomStations(null, user.getCompanycode_ID(), 0, 0, TracingConstants.AgentActiveStatus.ALL);
			
			for(Station station:stations){
				if(station.getRegion() == null){
					Region r = new Region();
					station.setRegion(r);
				} else {
					Region r = new Region();
					r.setId(station.getRegion().getId());
					station.setRegion(r);
				}
				station.setCurrentRegionId(station.getRegion().getId());
				station.setCurrentGoal(station.getGoal());
			}
			
			theForm.setStations(stations);
			theForm.setRegions(regions);
			return(mapping.findForward("viewregion"));
		}
		
		if(request.getParameter("update_stations") != null){
			System.out.println("update stations");
			if(RegionBMO.updateStationRegion(theForm.getStations(), user)){
				request.setAttribute("success", 1);
				return(mapping.findForward("editregionsuccess"));
			}else{
				request.setAttribute("error", 1);
				return(mapping.findForward("editregionsuccess"));
			}
		}

		if(request.getParameter("new_region") != null){
			System.out.println("new region");
			Region r = new Region();
			Company c = new Company();
			c.setCompanyCode_ID(user.getCompanycode_ID());
			r.setCompany(c);
			theForm.setEditRegion(r);
			return(mapping.findForward("editregion"));
		}
		
		if(request.getParameter("edit_region") != null){
			long id = new Long(request.getParameter("edit_region"));
			Region r = RegionBMO.getRegion(id);
			if(r != null){
				theForm.setEditRegion(r);
				return(mapping.findForward("editregion"));
			} else {
				request.setAttribute("error", 1);
				return(mapping.findForward("editregionsuccess"));
			}
		}
		
		if(request.getParameter("save_region") != null){
			long id = RegionBMO.saveRegion(theForm.getEditRegion(), user);
			if(id > 0){
				request.setAttribute("success", 1);
				return(mapping.findForward("editregionsuccess"));
			}else{
				request.setAttribute("error", 1);
				return(mapping.findForward("editregionsuccess"));
			}
		}
		
		if(request.getParameter("delete_region") != null){
			if(RegionBMO.deleteRegion(new Long(request.getParameter("delete_region")), user)){
				request.setAttribute("success", 1);
				return(mapping.findForward("editregionsuccess"));
			}else{
				request.setAttribute("error", 1);
				return(mapping.findForward("editregionsuccess"));
			}
		}
		request.setAttribute("error", 1);
		return(mapping.findForward("editregionsuccess"));
	}
	
}
