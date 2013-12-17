/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.bmo.LossCodeBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.db.Lz;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.WTCompany;
import com.bagnet.nettracer.tracing.db.audit.Audit_Company;
import com.bagnet.nettracer.tracing.db.audit.Audit_Company_Specific_Variable;
import com.bagnet.nettracer.tracing.forms.MaintainCompanyForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.LzUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.audit.AuditCompanyUtils;

/**
 * Implementation of <strong>Action </strong> that is responsible for adding,
 * modifying, and deleting company data.
 * @author Ankur Gupta
 */
public final class ManageCompany extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionMessages errors = new ActionMessages();
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		//Setup the email customer dropdown.

		MaintainCompanyForm dForm = (MaintainCompanyForm) form;
		
		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)) return (mapping
				.findForward(TracingConstants.NO_PERMISSION));
		
		// has permission but first check if superuser
		if (!user.getStation().getCompany().getCompanyCode_ID().equals(TracingConstants.OWENS_GROUP)) {
			// not superuser, check to see if edits is on, if not, redirect
			if ((request.getParameter("save") == null 
					&& request.getParameter("edit") == null
					&& request.getParameter("addNewLz") == null
					&& request.getParameter("saveLzList") == null
					&& request.getParameter("saveAssignments") == null
					&& request.getParameter("pagination") == null
					&& request.getParameter("addCarrier") == null
					&& request.getParameter("removeCarrier") == null
					) || request.getParameter("companyCode") == null || !request.getParameter("companyCode").equals(user.getStation().getCompany().getCompanyCode_ID())) {
				response.sendRedirect("companyAdmin.do?edit=1&companyCode=" + user.getStation().getCompany().getCompanyCode_ID());
				return null;
			}
		}
		List<Station> fullStationList = AdminUtils.getStations(null, (String) request.getParameter("companyCode"), 0, 0);
	  request.setAttribute("fullStationList", fullStationList);
		
		List<Company_specific_irregularity_code> codes = LossCodeBMO.getCompanyCodes(user.getStation().getCompany().getCompanyCode_ID(), TracingConstants.LOST_DELAY, false, user);
		//add to the loss codes
		request.setAttribute("losscodes", codes);
		
		List<Company_specific_irregularity_code> codes_dam = LossCodeBMO.getCompanyCodes(user.getStation().getCompany().getCompanyCode_ID(), TracingConstants.DAMAGED_BAG, false, user);
		//add to the loss codes
		request.setAttribute("losscodes_dam", codes_dam);
		
		List<Company_specific_irregularity_code> codes_pil = LossCodeBMO.getCompanyCodes(user.getStation().getCompany().getCompanyCode_ID(), TracingConstants.MISSING_ARTICLES, false, user);
		//add to the loss codes
		request.setAttribute("losscodes_pil", codes_pil);
		
		String pageState = null;
		if (dForm.getPageState() != null && !dForm.getPageState().equals("")) {
			pageState = (String)dForm.getPageState();
	 } else {
   	pageState =  TracingConstants.COMPANY_PAGESTATE_INFO;
   }
	 request.setAttribute("pageState", pageState);

		if (request.getParameter("edit") != null || (pageState.equals(TracingConstants.COMPANY_PAGESTATE_MOVETOLZ) &&
				((request.getParameter("pagination")!= null && !request.getParameter("pagination").equals("")) ||
				(request.getParameter("update_pagination")!= null && !request.getParameter("update_pagination").equals(""))))) {
			
			String companyCode = request.getParameter("companyCode");
			Company cmpny = AdminUtils.getCompany(companyCode);

			dForm.setCompanyCode(cmpny.getCompanyCode_ID());
			if (pageState == null || pageState.equals(TracingConstants.COMPANY_PAGESTATE_INFO)) {
				dForm.setCompanyDesc(cmpny.getCompanydesc());
				dForm.setAddr1(cmpny.getAddress1());
				dForm.setAddr2(cmpny.getAddress2());
				dForm.setCity(cmpny.getCity());
				dForm.setState_ID(cmpny.getState_ID());
				dForm.setCountrycode_ID(cmpny.getCountrycode_ID());
				dForm.setZip(cmpny.getZip());
				dForm.setPhone(cmpny.getPhone());
				dForm.setEmail_address(cmpny.getEmail_address());
			}
			
			if (cmpny.getVariable() != null) {
				if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_SETTINGS)) {
					dForm.setTotal_threads("" + cmpny.getVariable().getTotal_threads());
					dForm.setSeconds_wait("" + cmpny.getVariable().getSeconds_wait());
					dForm.setMin_match_percent("" + cmpny.getVariable().getMin_match_percent());
					dForm.setDefault_station_code("" + cmpny.getVariable().getDefault_station_code());
					dForm.setDefault_loss_code("" + cmpny.getVariable().getDefault_loss_code());
					dForm.setEmail_customer(cmpny.getVariable().isEmail_customer());
					dForm.setAutoCloseOhd(cmpny.getVariable().isAutoCloseOhd());
					dForm.setAuto_close_ohd_days_back(cmpny.getVariable().getAuto_close_ohd_days_back());
					dForm.setEmail_host("" + cmpny.getVariable().getEmail_host());
					dForm.setEmail_port("" + cmpny.getVariable().getEmail_port());
					dForm.setEmail_from("" + cmpny.getVariable().getEmail_from());
					dForm.setEmail_to("" + cmpny.getVariable().getEmail_to());
					dForm.setMin_interim_approval_check(""
							+ cmpny.getVariable().getMin_interim_approval_check());
					dForm.setMin_interim_approval_voucher(""
							+ cmpny.getVariable().getMin_interim_approval_voucher());
					dForm.setMin_interim_approval_miles(""
							+ cmpny.getVariable().getMin_interim_approval_miles());
					dForm.setMin_interim_approval_cc_refund(""
							+ cmpny.getVariable().getMin_interim_approval_cc_refund());
					dForm.setMin_interim_approval_incidental(""
							+ cmpny.getVariable().getMin_interim_approval_incidental());
					dForm.setMax_image_file_size("" + cmpny.getVariable().getMax_image_file_size());
					dForm.setScannerDefaultBack(new Integer(cmpny.getVariable().getScannerDefaultBack()));
					dForm.setScannerDefaultForward(new Integer(cmpny.getVariable().getScannerDefaultForward()));
					dForm.setBlindEmail(cmpny.getVariable().getBlindEmail());
					
					dForm.setAuto_close_days_back(cmpny.getVariable().getAuto_close_days_back());
					dForm.setAuto_close_ld_code(cmpny.getVariable().getAuto_close_ld_code());
					dForm.setAuto_close_dam_code(cmpny.getVariable().getAuto_close_dam_code());
					dForm.setAuto_close_pil_code(cmpny.getVariable().getAuto_close_pil_code());
					dForm.setAuto_close_ld_station(cmpny.getVariable().getAuto_close_ld_station());
					dForm.setAuto_close_dam_station(cmpny.getVariable().getAuto_close_dam_station());
					dForm.setAuto_close_pil_station(cmpny.getVariable().getAuto_close_pil_station());
					
					dForm.setPnrlastxdays(cmpny.getVariable().getPnr_last_x_days());
					
					dForm.setIssuanceitemxdaysback(cmpny.getVariable().getIssuance_edit_last_x_days());
					
					dForm.setIncident_lock_mins(cmpny.getVariable().getIncident_lock_mins());
					
					dForm.setBagdrop_autorefresh_mins(cmpny.getVariable().getBagdrop_autorefresh_mins());
				}
				
				if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_MOVETOLZ)) {
					dForm.setMbr_to_lz_days("" + cmpny.getVariable().getMbr_to_lz_days());
					dForm.setDamaged_to_lz_days("" + cmpny.getVariable().getDamaged_to_lz_days());
					dForm.setMiss_to_lz_days("" + cmpny.getVariable().getMiss_to_lz_days());
					dForm.setOhd_to_lz_days("" + cmpny.getVariable().getOhd_to_lz_days());
					dForm.setLz_mode("" + cmpny.getVariable().getLz_mode());
					dForm.setOhd_lz("" + cmpny.getVariable().getOhd_lz());
					populateLists(request, dForm);
				}
				
				if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_SECURITY)) {
					dForm.setPass_expire_days("" + cmpny.getVariable().getPass_expire_days());
					dForm.setAccount_lockout("" + cmpny.getVariable().getMax_failed_logins());
					dForm.setSecure_password("" + cmpny.getVariable().getSecure_password());
					dForm.setMin_pass_size(cmpny.getVariable().getMin_pass_size());
					dForm.setPass_x_history(cmpny.getVariable().getPass_x_history());
				}

				if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_AUDITING)) {
					dForm.setAudit_ohd("" + cmpny.getVariable().getAudit_ohd());
					dForm.setAudit_lost_found("" + cmpny.getVariable().getAudit_lost_found());
					dForm.setAudit_lost_delayed("" + cmpny.getVariable().getAudit_lost_delayed());
					dForm.setAudit_damaged("" + cmpny.getVariable().getAudit_damaged());
					dForm.setAudit_missing_articles("" + cmpny.getVariable().getAudit_missing_articles());
					dForm.setAudit_agent("" + cmpny.getVariable().getAudit_agent());
					dForm.setAudit_group("" + cmpny.getVariable().getAudit_group());
					dForm.setAudit_company("" + cmpny.getVariable().getAudit_company());
					dForm.setAudit_shift("" + cmpny.getVariable().getAudit_shift());
					dForm.setAudit_station("" + cmpny.getVariable().getAudit_station());
					dForm.setAudit_loss_codes("" + cmpny.getVariable().getAudit_loss_codes());
					dForm.setAudit_claims("" + cmpny.getVariable().getAudit_claims());
					dForm.setAudit_airport("" + cmpny.getVariable().getAudit_airport());
					dForm.setAudit_delivery_companies("" + cmpny.getVariable().getAudit_delivery_companies());
				}
				
				if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_WEB_SERVICES)) {
					dForm.setWebs_enabled("" + cmpny.getVariable().getWebs_enabled());
				}
				
				if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_WORLDTRACER)) {
					dForm.setMbr_to_wt_days("" + cmpny.getVariable().getMbr_to_wt_days());
					dForm.setOhd_to_wt_hours("" + cmpny.getVariable().getOhd_to_wt_hours());
					dForm.setOal_inc_hours("" + cmpny.getVariable().getOal_inc_hours());
					dForm.setOal_ohd_hours("" + cmpny.getVariable().getOal_ohd_hours());
					dForm.setWt_user("" + cmpny.getVariable().getWt_user());
					dForm.setWt_pass("" + cmpny.getVariable().getWt_pass());
					//dForm.setRetrieve_actionfile_interval("" + cmpny.getVariable().getRetrieve_actionfile_interval());
					dForm.setWt_url("" + cmpny.getVariable().getWt_url());
					dForm.setWt_airlinecode("" + cmpny.getVariable().getWt_airlinecode());
					dForm.setWt_enabled("" + cmpny.getVariable().getWt_enabled());
					dForm.setWt_write_enabled("" + cmpny.getVariable().getWt_write_enabled());
					dForm.setAuto_wt_amend(cmpny.getVariable().isAuto_wt_amend());
					
					
					/**
					 * Logic for loading up list of World Tracer Companies to add, delete, and save.
					 */
					List<WTCompany> wtlist=CompanyBMO.getWTCarriers((String) dForm.getCompanyCode());

					if (request.getParameter("index")!=null) {
						String index=request.getParameter("index");
						int indexToRemove = Integer.parseInt(index);
						if (wtlist != null && !wtlist.isEmpty() && wtlist.size() > indexToRemove) {
							int i = 0;
							Iterator<WTCompany> iterator = wtlist.iterator();
							while (iterator.hasNext()) {
								WTCompany comp = iterator.next();
								if (i == indexToRemove) {
									wtlist.remove(comp);
									if(comp.getId()!=0)
										HibernateUtils.delete(comp);
									break;
								}
								++i;
							}
						}
					}
					

					dForm.setWtCompanyList(wtlist);
					TracerUtils.populateCompanyLists(session);
				}
				
				if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_STATUSMESSAGE)) {
					dForm.setStatusMessage("" + cmpny.getVariable().getStatus_message());
				}
								
			}
			return mapping.findForward(TracingConstants.EDIT_COMPANY);
		}
		
		if (request.getParameter("addNew") != null) {
			request.setAttribute("newCompany", 1);
			return mapping.findForward(TracingConstants.EDIT_COMPANY);
		}

		if (request.getParameter("delete1") != null && !request.getParameter("delete1").equals("")) {
			String companyCode = request.getParameter("companyCode");
			Company cmpny = AdminUtils.getCompany(companyCode);

			ActionMessage error = null;
			if (TracerUtils.getStationList(companyCode) != null) error = new ActionMessage(
					"error.deleting.company.station");
			else if (AdminUtils.getGroups(companyCode) != null) error = new ActionMessage(
					"error.deleting.company.group");
			else if (cmpny.getIrregularity_codes() != null && cmpny.getIrregularity_codes().size() > 0) error = new ActionMessage(
					"error.deleting.company.codes");

			if (error != null) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				//Delete the specific variable object
				HibernateUtils.delete(cmpny);

				if (cmpny != null && user.getStation().getCompany().getVariable().getAudit_company() == 1) {
					Audit_Company_Specific_Variable csv = new Audit_Company_Specific_Variable();

					if (csv != null) {
						BeanUtils.copyProperties(csv, cmpny.getVariable());
						HibernateUtils.saveNew(csv);

						//saved the csv..Now save the actual company
						Audit_Company audit_company = AuditCompanyUtils.getAuditCompany(cmpny, user);
						if (audit_company != null) {
							audit_company.setVariable(csv);
							audit_company.setReason_modified("Deleted");
							HibernateUtils.saveNew(audit_company);
						}
					}
				}
				if (cmpny.getVariable() != null) HibernateUtils.delete(cmpny.getVariable());
			}
		}

		if (request.getParameter("addNewLz") != null) {
			LzUtils.addNewLz(new Integer(dForm.getNew_lz()).intValue());
			
			populateLists(request, dForm);
			return mapping.findForward(TracingConstants.EDIT_COMPANY);
		}
		
		boolean saveLzList = request.getParameter("saveLzList") != null;
		if (saveLzList) {
			
			if (!LzUtils.updateLzList(dForm, request, user)) {
				ActionMessage error = null;
				error = new ActionMessage("error.deleting.lz");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				populateLists(request, dForm);
				return mapping.findForward(TracingConstants.EDIT_COMPANY);
			}
			
			populateLists(request, dForm);
		}
		
		if (request.getParameter("saveAssignments") != null) {
			getStationList(request, dForm);
			LzUtils.updateStationAssignments(dForm, request, user);
			populateLists(request, dForm);
			return mapping.findForward(TracingConstants.EDIT_COMPANY);
		}
		
		if(request.getParameter("addCarrier")!=null){
			if(dForm.getWtCompanyList()!=null){
				List<WTCompany> wtlist=dForm.getWtCompanyList();
				HashMap<String,WTCompany> wtMap=new HashMap<String,WTCompany>();
				for(WTCompany wtc:wtlist){
					if(wtMap.get(wtc.getWtCompanyCode())==null){
						wtMap.put(wtc.getWtCompanyCode(), wtc);
					}
				}

				if(request.getParameter("addCarrier")!=null && dForm.getSelectedCarrier()!=null && dForm.getSelectedCarrier().length()>0){
					if(wtMap.get(dForm.getSelectedCarrier())==null && !dForm.getSelectedCarrier().isEmpty()){
						Company c=CompanyBMO.getCompany(dForm.getSelectedCarrier());
						WTCompany wtc=new WTCompany();
						wtc.setCompany_id(user.getCompanycode_ID());
						wtc.setWtCompanyCode(c.getCompanyCode_ID());
						wtc.setCompanyName(c.getCompanydesc());
						dForm.getWtCompanyList().add(wtc);
					} else {
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"error.duplicate.wt.carrier"));
						saveMessages(request, errors);
					}
				}
				return mapping.findForward(TracingConstants.EDIT_COMPANY);
			}
		}
		
		if (request.getParameter("save") != null || saveLzList) {
			Company c = new Company();
			c.setCompanyCode_ID((String) dForm.getCompanyCode());
			c.setCompanydesc((String) dForm.getCompanyDesc());
			c.setAddress1((String) dForm.getAddr1());
			c.setAddress2((String) dForm.getAddr2());
			c.setCity((String) dForm.getCity());
			c.setState_ID((String) dForm.getState_ID());
			c.setCountrycode_ID((String) dForm.getCountrycode_ID());
			c.setZip((String) dForm.getZip());
			c.setPhone((String) dForm.getPhone());
			c.setEmail_address((String) dForm.getEmail_address());
			
			Company_Specific_Variable var = new Company_Specific_Variable();
			var.setCompanyCode_ID(c.getCompanyCode_ID());
			try {
				if (request.getParameter("newCompany") != null) {
					var.setTotal_threads(10);
					var.setSeconds_wait(0);
					var.setMin_match_percent(50);
					var.setMbr_to_lz_days(5);
					var.setDamaged_to_lz_days(0);
					var.setMiss_to_lz_days(0);
					var.setOhd_to_lz_days(5);
				}
				
				if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_SETTINGS)) {
					var.setTotal_threads(Integer.parseInt((String) dForm.getTotal_threads()));
					var.setSeconds_wait(Integer.parseInt((String) dForm.getSeconds_wait()));
					var.setMin_match_percent(Double.parseDouble((String) dForm.getMin_match_percent()));
					if (((String) dForm.getDefault_station_code()) != null
							&& ((String) dForm.getDefault_station_code()).length() > 0) {
						var.setDefault_station_code(Integer.parseInt((String) dForm.getDefault_station_code()));
					}
					if (((String) dForm.getDefault_loss_code()) != null
							&& ((String) dForm.getDefault_loss_code()).length() > 0) {
						var.setDefault_loss_code(Integer.parseInt((String) dForm.getDefault_loss_code()));
					}
					var.setEmail_customer(dForm.isEmail_customer());
					var.setAutoCloseOhd(dForm.isAutoCloseOhd());
					var.setEmail_host((String) dForm.getEmail_host());
					var.setEmail_port(Integer.parseInt((String) dForm.getEmail_port()));
					var.setEmail_from((String) dForm.getEmail_from());
					var.setEmail_to((String) dForm.getEmail_to());
					
					var.setMin_interim_approval_check(Double.parseDouble((String) dForm
							.getMin_interim_approval_check()));
					var.setMin_interim_approval_voucher(Double.parseDouble((String) dForm
							.getMin_interim_approval_voucher()));
					var.setMin_interim_approval_miles(Double.parseDouble((String) dForm
							.getMin_interim_approval_miles()));
					var.setMin_interim_approval_cc_refund(Double.parseDouble((String) dForm
							.getMin_interim_approval_cc_refund()));
					var.setMin_interim_approval_incidental(Double.parseDouble((String) dForm
							.getMin_interim_approval_incidental()));
					var.setMax_image_file_size(Integer.parseInt((String) dForm.getMax_image_file_size()));
					var.setMax_image_file_size(Integer.parseInt((String) dForm.getMax_image_file_size()));
					var.setBlindEmail((String)dForm.getBlindEmail());
					if (dForm.getScannerDefaultBack() != null) {
						var.setScannerDefaultBack(dForm.getScannerDefaultBack().intValue());
					}
					if (dForm.getScannerDefaultForward() != null) {
						var.setScannerDefaultForward(dForm.getScannerDefaultForward().intValue());
					}

					if (dForm.getAuto_close_days_back() != null) {
						var.setAuto_close_days_back(dForm.getAuto_close_days_back().intValue());
					}

					if (dForm.getAuto_close_ohd_days_back() != null) {
						var.setAuto_close_ohd_days_back(dForm.getAuto_close_ohd_days_back().intValue());
					}
					if (dForm.getAuto_close_ld_code() != null) {
						var.setAuto_close_ld_code(dForm.getAuto_close_ld_code().intValue());
					}
					if (dForm.getAuto_close_dam_code() != null) {
						var.setAuto_close_dam_code(dForm.getAuto_close_dam_code().intValue());
					}
					if (dForm.getAuto_close_pil_code() != null) {
						var.setAuto_close_pil_code(dForm.getAuto_close_pil_code().intValue());
					}
					if (dForm.getAuto_close_ld_station() != null) {
					var.setAuto_close_ld_station(dForm.getAuto_close_ld_station().intValue());
					}
					if (dForm.getAuto_close_dam_station() != null) {
					var.setAuto_close_dam_station(dForm.getAuto_close_dam_station().intValue());
					}
					if (dForm.getAuto_close_pil_station() != null) {
					var.setAuto_close_pil_station(dForm.getAuto_close_pil_station().intValue());
					}
					
					if (dForm.getIncident_lock_mins() != null) {
					var.setIncident_lock_mins(dForm.getIncident_lock_mins().intValue());	
					}
					
					if (dForm.getBagdrop_autorefresh_mins() != null){
						var.setBagdrop_autorefresh_mins(dForm.getBagdrop_autorefresh_mins().intValue());
					}
					
					if(dForm.getPnrlastxdays()!=null){
						if(PropertyBMO.getValueAsInt(PropertyBMO.MAX_PNR_LAST_DAYS)!=0 && dForm.getPnrlastxdays()>PropertyBMO.getValueAsInt(PropertyBMO.MAX_PNR_LAST_DAYS)){
							String[] vars={PropertyBMO.getValue(PropertyBMO.MAX_PNR_LAST_DAYS)};
							ActionMessage error = new ActionMessage("error.max.pnr",vars);
							errors.add(ActionMessages.GLOBAL_MESSAGE, error);
							saveMessages(request, errors);
							
							dForm.setPnrlastxdays(PropertyBMO.getValueAsInt(PropertyBMO.MAX_PNR_LAST_DAYS));
							var.setPnr_last_x_days(PropertyBMO.getValueAsInt(PropertyBMO.MAX_PNR_LAST_DAYS));
						} else{
							var.setPnr_last_x_days(dForm.getPnrlastxdays());
						}
					}
					
					if (dForm.getIssuanceitemxdaysback()!=null) {
						var.setIssuance_edit_last_x_days(dForm.getIssuanceitemxdaysback().intValue());
					}
				}
				
				if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_MOVETOLZ)) {
					if (saveLzList) {
						var.setLz_mode(Integer.parseInt((String) dForm.getLz_mode()));
					} else {
						var.setMbr_to_lz_days(Integer.parseInt((String) dForm.getMbr_to_lz_days()));
						var.setDamaged_to_lz_days(Integer.parseInt((String) dForm.getDamaged_to_lz_days()));
						var.setMiss_to_lz_days(Integer.parseInt((String) dForm.getMiss_to_lz_days()));
						var.setOhd_to_lz_days(Integer.parseInt((String) dForm.getOhd_to_lz_days()));
						var.setOhd_lz(Integer.parseInt((String) dForm.getOhd_lz()));
					}
					populateLists(request, dForm);
				}
				
				
				if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_SECURITY)) {
					var.setPass_expire_days(Integer.parseInt((String) dForm.getPass_expire_days()));
					var.setMax_failed_logins(Integer.parseInt((String) dForm.getAccount_lockout()));
					var.setSecure_password(Integer.parseInt((String) dForm.getSecure_password()));
					var.setMin_pass_size(dForm.getMin_pass_size());
					var.setPass_x_history(dForm.getPass_x_history());
				}
				
				
				if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_AUDITING)) {
					var.setAudit_ohd(Integer.parseInt((String) dForm.getAudit_ohd()));
					var.setAudit_lost_found(Integer.parseInt((String) dForm.getAudit_lost_found()));
					var.setAudit_lost_delayed(Integer.parseInt((String) dForm.getAudit_lost_delayed()));
					var.setAudit_damaged(Integer.parseInt((String) dForm.getAudit_damaged()));
					var.setAudit_missing_articles(Integer
							.parseInt((String) dForm.getAudit_missing_articles()));
					var.setAudit_agent(Integer.parseInt((String) dForm.getAudit_agent()));
					var.setAudit_group(Integer.parseInt((String) dForm.getAudit_group()));
					var.setAudit_company(Integer.parseInt((String) dForm.getAudit_company()));
					var.setAudit_shift(Integer.parseInt((String) dForm.getAudit_shift()));
					var.setAudit_station(Integer.parseInt((String) dForm.getAudit_station()));
					var.setAudit_loss_codes(Integer.parseInt((String) dForm.getAudit_loss_codes()));
					var.setAudit_claims(Integer.parseInt((String) dForm.getAudit_claims()));
					var.setAudit_airport(Integer.parseInt((String) dForm.getAudit_airport()));
					var.setAudit_delivery_companies(Integer.parseInt((String) dForm.getAudit_delivery_companies()));
				}
				if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_WEB_SERVICES)){

					var.setWebs_enabled(Integer.parseInt((String) dForm.getWebs_enabled()));

					var.setWebs_enabled(Integer.parseInt(dForm.getWebs_enabled()));

				}
				if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_WORLDTRACER)) {
					var.setMbr_to_wt_days(Integer.parseInt((String) dForm.getMbr_to_wt_days()));
					var.setOhd_to_wt_hours(Integer.parseInt((String) dForm.getOhd_to_wt_hours()));
					var.setOal_inc_hours(Integer.parseInt((String)dForm.getOal_inc_hours()));
					var.setOal_ohd_hours(Integer.parseInt((String)dForm.getOal_ohd_hours()));
					var.setWt_user((String)dForm.getWt_user());
					var.setWt_pass((String)dForm.getWt_pass());
					//var.setRetrieve_actionfile_interval(Integer.parseInt((String)dForm.getRetrieve_actionfile_interval()));
					// let them save a blank url if wt is being disabled.
					// otherwise store the default instead of blank
					String wt_url = dForm.getWt_url();
					if (Integer.parseInt(dForm.getWt_enabled()) == 1 && (wt_url == null || wt_url.trim().length() == 0)) {
						wt_url = TracingConstants.DEFAULT_WT_URL;
					}
					var.setWt_url(wt_url);
					var.setWt_airlinecode((String)dForm.getWt_airlinecode());
					var.setWt_enabled(Integer.parseInt(dForm.getWt_enabled()));
					var.setWt_write_enabled(Integer.parseInt(dForm.getWt_write_enabled()));
					var.setAuto_wt_amend(dForm.isAuto_wt_amend());
					
				}
				if (pageState.equals(TracingConstants.COMPANY_PAGESTATE_STATUSMESSAGE)){

					var.setStatus_message((String) dForm.getStatusMessage());

				}				

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				c.setVariable(var);
				HibernateUtils.saveCompany(c, (String) request.getAttribute("pageState"), saveLzList);

				Company cmp = AdminUtils.getCompany(c.getCompanyCode_ID());

				if(dForm.getWtCompanyList()!=null && dForm.getWtCompanyList().size()>0){
					HibernateUtils.saveWTCarriers(dForm.getWtCompanyList());
				}
				
				if (cmp != null && user.getStation().getCompany().getVariable().getAudit_company() == 1) {
					Audit_Company_Specific_Variable csv = new Audit_Company_Specific_Variable();

					if (csv != null) {
						BeanUtils.copyProperties(csv, cmp.getVariable());
						HibernateUtils.saveNew(csv);

						//saved the csv..Now save the actual company
						Audit_Company audit_company = AuditCompanyUtils.getAuditCompany(cmp, user);
						if (audit_company != null) {
							audit_company.setVariable(csv);
							HibernateUtils.saveNew(audit_company);
						}
					}
				}
				
				request.setAttribute("saved","1");

			} catch (Exception e) {
				ActionMessage error = new ActionMessage("error.creating.company");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
			
			return mapping.findForward(TracingConstants.EDIT_COMPANY);
			//session.invalidate();
			//return mapping.findForward(TracingConstants.LOGON);
		}

		int companyCount = AdminUtils.getCompaniesCount(dForm);

		if (companyCount > 0) {
			/** ************ pagination ************* */
			int rowcount = -1;
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_ADMIN_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			int totalpages = 0;

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1")) currpage++;
			if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1")) currpage--;

			request.setAttribute("currpage", Integer.toString(currpage));

			// get row count
			rowcount = companyCount;

			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}

			List<Company> companyList = AdminUtils.getCompanies(dForm, rowsperpage, currpage);

			if (currpage + 1 == totalpages) request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList<String> al = new ArrayList<String>();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}

			/** ************ end of pagination ************* */

			request.setAttribute("companyList", companyList);
		} else {
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_ADMIN_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			request.setAttribute("currpage", Integer.toString(currpage));
		}

		//request.setAttribute("companyList",companyList);
		return mapping.findForward(TracingConstants.VIEW_COMPANIES);
	}
	
	public void populateLists(HttpServletRequest request, MaintainCompanyForm dForm){
		List<Station> stationList = getStationList(request, dForm);

		List<Lz> lzList = LzUtils.getIncidentLzStations(dForm.getCompanyCode());
		HashMap<Integer,String> usedMap = new HashMap<Integer,String>();
		
		for (Iterator<Station> i = stationList.iterator(); i.hasNext();) {
			usedMap.put(new Integer(i.next().getLz_ID()), "");
		}
		
		for (Iterator<Lz> j = lzList.iterator(); j.hasNext();) {
			Lz lz = j.next();
			int lzId = lz.getLz_ID();
			if (usedMap.containsKey(new Integer(lzId))) {
				lz.setIsUsed(true);	
			}
		}
		
		dForm.setLzStations(lzList);
		dForm.setDefaultLz(LzUtils.getDefaultLz(lzList));
	}
	
	public static List<Station> getStationList(HttpServletRequest request, MaintainCompanyForm dForm) {
		
		List<Station> stationList = AdminUtils.getStations(null, (String) request.getParameter("companyCode"),
				0, 0);

		
		/** ************ pagination ************* */
		int rowcount = -1;
		int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_ADMIN_PAGES, request.getSession());
		request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
		int totalpages = 0;

		int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
				.getParameter("currpage")) : 0;
		if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1")) currpage++;
		if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1")) currpage--;

		request.setAttribute("currpage", Integer.toString(currpage));

		// get row count
		rowcount = stationList.size();

		// find out total pages
		totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

		if (totalpages <= currpage) {
			currpage = 0;
			request.setAttribute("currpage", "0");
		}

		stationList = AdminUtils.getStations(null, (String) request.getParameter("companyCode"),
				rowsperpage, currpage);

		if (currpage + 1 == totalpages) request.setAttribute("end", "1");
		if (totalpages > 1) {
			ArrayList<String> al = new ArrayList<String>();
			for (int i = 0; i < totalpages; i++) {
				al.add(Integer.toString(i));
			}
			request.setAttribute("pages", al);
		}

		/** ************ end of pagination **************/
		request.setAttribute("stationList", stationList);
		return stationList;
	}
}



