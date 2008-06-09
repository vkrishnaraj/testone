package com.bagnet.nettracer.tracing.actions.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.DeliverCo_Station;
import com.bagnet.nettracer.tracing.db.DeliverCompany;
import com.bagnet.nettracer.tracing.db.Deliver_ServiceLevel;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.audit.Audit_DeliverCo_Station;
import com.bagnet.nettracer.tracing.db.audit.Audit_DeliverCompany;
import com.bagnet.nettracer.tracing.db.audit.Audit_Deliver_ServiceLevel;
import com.bagnet.nettracer.tracing.forms.MaintainDeliveryCompanyForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BDOUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.audit.AuditDeliveryCompanyUtils;


/**
 * Implementation of <strong>Action </strong> that is responsible for adding,
 * modifying, and deleting delivery companies.
 * 
 * @author Byron Smith
 */
public final class ManageDeliveryCompanies extends Action {
	private static Logger logger = Logger.getLogger(ManageDeliveryCompanies.class);
	
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
		MaintainDeliveryCompanyForm mForm = (MaintainDeliveryCompanyForm) form;

		String companyCode = user.getStation().getCompany().getCompanyCode_ID();

		String sort = request.getParameter("sort");
		if (sort != null && sort.length() > 0) request.setAttribute("sort", sort);
		
		if (request.getParameter("addNewServiceLevel") != null) {
			String companyId = request.getParameter("delivercompany_ID");
			DeliverCompany company = AdminUtils.getDeliveryCompany(companyId);
						
			mForm.setDescription("");
			mForm.setDeliveryCompanyName(company.getName());
			return mapping.findForward(TracingConstants.EDIT_SERVICE_LEVEL);
		}
		
		if (request.getParameter("addDeliveryCompany") != null) {
			return mapping.findForward(TracingConstants.EDIT_DELIVERY_COMPANY);
		}
		
		if (request.getParameter("addNew") != null) {
			return mapping.findForward(TracingConstants.EDIT_DELIVERY_COMPANY);
		}
		
		/*
		 * Deletion Logic:
		 * - Delivery Companies
		 * - Station associations
		 * - Service Levels
		 */
		boolean deletedStation = false;
		boolean deletedServiceLevel = false;
		
		if (request.getParameter("delete1") != null && !request.getParameter("delete1").equals("")) {

			if (request.getParameter("deleteDeliveryCompanyId") != null && !request.getParameter("deleteDeliveryCompanyId").equals("")) {
				
				String companyId = request.getParameter("deleteDeliveryCompanyId");
				DeliverCompany company = AdminUtils.getDeliveryCompany(companyId);
				company.setActive(false);
				HibernateUtils.save(company);
				if (user.getStation().getCompany().getVariable().getAudit_delivery_companies() == 1) {
					Audit_DeliverCompany audit_dc = AuditDeliveryCompanyUtils.getAuditDeliverCompany(company, user);
					if (audit_dc != null) {
						audit_dc.setReason_modified("Deleted");
						HibernateUtils.saveNew(audit_dc);
					}
				}
			}
			
			if (request.getParameter("deleteDelivCoStation") != null && !request.getParameter("deleteDelivCoStation").equals("")) {
				
				String cid = request.getParameter("delivercompany_ID");
				DeliverCompany c = AdminUtils.getDeliveryCompany(cid);
			
				String deleteDelivCoStation = request.getParameter("deleteDelivCoStation");		
				DeliverCo_Station deliverCoStation = BDOUtils.getDeliverCo_Station(deleteDelivCoStation, c);
				
				
				ActionMessage error = null;
				
				if (!HibernateUtils.delete(deliverCoStation)) {
					error = new ActionMessage("error.deleting.station");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				} else {
					if (user.getStation().getCompany().getVariable().getAudit_delivery_companies() == 1) {
						Audit_DeliverCo_Station audit_dcs = AuditDeliveryCompanyUtils.getAuditDeliverCoStation(deliverCoStation, user);
						if (audit_dcs != null) {
							audit_dcs.setReason_modified("Deleted");
							HibernateUtils.saveNew(audit_dcs);
						}
					}
				}
				deletedStation = true;
			}
			
			if (request.getParameter("deleteServiceLevelID") != null && !request.getParameter("deleteServiceLevelID").equals("")) {
				String serviceLevelId = request.getParameter("deleteServiceLevelID");
				Deliver_ServiceLevel serviceLevel = BDOUtils.getServiceLevel(serviceLevelId);
								
				serviceLevel.setActive(false);
				HibernateUtils.save(serviceLevel);
				deletedServiceLevel = true;
				if (user.getStation().getCompany().getVariable().getAudit_delivery_companies() == 1) {
					Audit_Deliver_ServiceLevel audit_dsl = AuditDeliveryCompanyUtils.getAuditDeliver_ServiceLevel(serviceLevel, user);
					if (audit_dsl != null) {
						audit_dsl.setReason_modified("Deleted");
						HibernateUtils.saveNew(audit_dsl);
					}
				}
			}
			
		}
		
		DeliverCompany objRef = null;
		
		if (request.getParameter("saveDeliveryCompany") != null) {
			DeliverCompany c = new DeliverCompany();
			c.setDelivercompany_ID(mForm.getDelivercompany_ID());
			c.setAddress(mForm.getAddress());
			c.setPhone(mForm.getPhone());
			c.setName(mForm.getName());
			c.setCompany(user.getStation().getCompany());
			c.setActive(true);
			

			try {
				objRef = HibernateUtils.saveDeliveryCompany(c, user);
			} catch (Exception e) {
				ActionMessage error = new ActionMessage("error.creating.deliverycompany");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
			
		}
		
		if (deletedStation || 
				deletedServiceLevel || 
				request.getParameter("edit") != null || 
				request.getParameter("addNewStation") != null || 
				request.getParameter("saveServiceLevel") != null ||
				request.getParameter("saveDeliveryCompany") != null) {
			
			String companyId = null;
			DeliverCompany company = null;
			
			if (objRef != null) {
				company = objRef;
				companyId =  Integer.toString(objRef.getDelivercompany_ID());
			} else {
				companyId = request.getParameter("delivercompany_ID");
				company = AdminUtils.getDeliveryCompany(companyId);
					
			}
			
			if (request.getParameter("saveServiceLevel") != null) {
				
				String serviceLevelId = request.getParameter("serviceLevel_ID");
				Deliver_ServiceLevel c = new Deliver_ServiceLevel();
				c.setDelivercompany(company);
				c.setDescription(mForm.getDescription());
				c.setServicelevel_ID(new Integer(serviceLevelId).intValue());
				c.setActive(true);
				
				try {
					HibernateUtils.saveServiceLevel(c, user);
				} catch (Exception e) {
					logger.error(e.getMessage() + "\n" + e.getStackTrace());
					ActionMessage error = new ActionMessage("error.creating.servicelevel");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				}
			}

			
			if (request.getParameter("addNewStation") != null) {
				DeliverCo_Station a = new DeliverCo_Station();
				a.setStation_ID(new Integer(request.getParameter("station_ID")).intValue());
				a.setDelivercompany(company);
				
				boolean stationAdded = HibernateUtils.saveDeliverCo_Station(a, user);
				if (stationAdded == false) {
					ActionMessage error = null;				
					error = new ActionMessage("error.adding.station");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				}
			}

			mForm.setDelivercompany_ID(company.getDelivercompany_ID());
			mForm.setName(company.getName());
			mForm.setAddress(company.getAddress());
			mForm.setPhone(company.getPhone());
			
			List<Station> stationList = BDOUtils.getStationsByDeliveryCompany(company);
			request.setAttribute("stationList", stationList);
			
			List<Station> entireStationList = AdminUtils.getStations(null, companyCode, 0, 0);
						
			if (stationList != null) {
				for (int x=0; x<stationList.size(); ++x) {
					Station currentStation = stationList.get(x);
					for (int y=0; y<entireStationList.size(); ++y) {
						if (currentStation.getStationcode().equals(entireStationList.get(y).getStationcode())) {
							entireStationList.remove(entireStationList.get(y));
							break;
						}
					}
				}
			}
			request.setAttribute("entireStationList", entireStationList);
			
			List serviceLevelList = BDOUtils.getServiceLevels(company.getDelivercompany_ID());
			request.setAttribute("serviceLevelList", serviceLevelList);
			return mapping.findForward(TracingConstants.EDIT_DELIVERY_COMPANY);
		}
		

		if (request.getParameter("editServiceLevel") != null) {
			String companyId = request.getParameter("delivercompany_ID");
			DeliverCompany company = AdminUtils.getDeliveryCompany(companyId);			
			String serviceLevelId = request.getParameter("serviceLevelId");
			Deliver_ServiceLevel serviceLevel = BDOUtils.getServiceLevel(serviceLevelId);
			mForm.setServiceLevel_ID(serviceLevel.getServicelevel_ID());
			mForm.setDelivercompany_ID(company.getDelivercompany_ID());
			mForm.setDescription(serviceLevel.getDescription());
			mForm.setDeliveryCompanyName(company.getName());
			return mapping.findForward(TracingConstants.EDIT_SERVICE_LEVEL);
		} 
		
		List deliveryCompanies = AdminUtils.getDeliveryCompanies(mForm, user.getStation().getCompany(), 0, 0);
		
		if (deliveryCompanies != null && deliveryCompanies.size() > 0) {
			/** ************ pagination ************* */
			int rowcount = -1;
			int rowsperpage = request.getParameter("rowsperpage") != null ? Integer.parseInt(request
					.getParameter("rowsperpage")) : TracingConstants.ROWS_PER_PAGE;
			if (rowsperpage < 1) rowsperpage = TracingConstants.ROWS_PER_PAGE;
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			int totalpages = 0;

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1")) currpage++;
			if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1")) currpage--;
			request.setAttribute("currpage", Integer.toString(currpage));

			// get row count
			rowcount = deliveryCompanies.size();

			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}

			deliveryCompanies = AdminUtils.getDeliveryCompanies(mForm, user.getStation().getCompany(), rowsperpage, currpage);

			if (currpage + 1 == totalpages) request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList al = new ArrayList();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}

			/** ************ end of pagination ************* */

			request.setAttribute("deliveryList", deliveryCompanies);
		} else {
			int rowsperpage = request.getParameter("rowsperpage") != null ? Integer.parseInt(request
					.getParameter("rowsperpage")) : TracingConstants.ROWS_PER_PAGE;
			if (rowsperpage < 1) rowsperpage = TracingConstants.ROWS_PER_PAGE;
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			request.setAttribute("currpage", Integer.toString(currpage));
		}
		return mapping.findForward(TracingConstants.VIEW_DELIVERY_COMPANIES);
	}
}