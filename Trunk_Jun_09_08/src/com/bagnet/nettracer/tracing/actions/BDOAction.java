/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
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

import com.bagnet.nettracer.integrations.delivery.DeliveryIntegrationResponse;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.LossCodeBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.db.DeliveryInstructions;
import com.bagnet.nettracer.tracing.db.DeliveryIntegrationType;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.ExpenseType;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.forms.BDOForm;
import com.bagnet.nettracer.tracing.history.BDOHistoryObject;
import com.bagnet.nettracer.tracing.utils.BDOUtils;
import com.bagnet.nettracer.tracing.utils.DeliveryIntegrationTypeUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.HistoryUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.web.StatusListDisp;

/**
 * @author Matt
 * 
 */
public class BDOAction extends Action {

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

		BDOForm theform = (BDOForm) form;

		ActionMessages messages = new ActionMessages();

		// ohd id or mbr id present in the request parameter
		String ohd = request.getParameter("ohd_id");
		String incident = request.getParameter("mbr_id");
		
		// if no ids, a input box will show to enter the mbr or ohd id
		if (request.getParameter("mbr") != null) {
			request.setAttribute("mbr", "1");
		} else if (request.getParameter("onhand") != null) {
			request.setAttribute("onhand", "1");
		} else {

			request.setAttribute("show_word_for", "1"); // show "BDO for" in
			
			String bdo_id2 = request.getParameter("bdo_id");
			int bdo_id = 0;

			if (bdo_id2 != null && !"".equals(bdo_id2)) {
				bdo_id = Integer.parseInt(bdo_id2);

				@SuppressWarnings("rawtypes")
				Iterator it = BDOUtils.findWt_id(bdo_id);

				while (it.hasNext()) {

					BDO bdo = (BDO) it.next();
					if (null != bdo.getIncident()
							&& !bdo.getIncident().toString().equals("")) {
						if (null != bdo.getIncident().getWtFile()
								&& !"".equals(bdo.getIncident().getWtFile())) {
							request.setAttribute("wt_id", bdo.getIncident().getWtFile());
						}
					} else {
						if (null != bdo.getOhd()
								&& !"".equals(bdo.getOhd().toString().trim())) {
							if (null != bdo.getOhd().getWt_id()
									&& !"".equals(bdo.getOhd().getWt_id()
											.trim())) {
								request.setAttribute("wt_id", bdo.getOhd()
										.getWt_id());
							}
						}
					}

				}
			} 
		}

		if (theform.getDelivercompany_ID() > 0) {

			@SuppressWarnings("rawtypes")
			List servicelevels = null;

			if (request.getParameter("changeservice") != null
					&& request.getParameter("changeservice").equals("1")
					&& request.getParameter("bdo_id") != null) {
				BDO bdo = BDOUtils.getBDOFromDB(new Integer(request
						.getParameter("bdo_id")));
				if (bdo.getDelivercompany().getDelivercompany_ID() == theform
						.getDelivercompany_ID()) {
					servicelevels = BDOUtils.getServiceLevels(theform
							.getDelivercompany_ID(), bdo.getServicelevel());
				} else {
					servicelevels = BDOUtils.getServiceLevels(theform
							.getDelivercompany_ID());
				}
			} else {
				servicelevels = BDOUtils.getServiceLevels(theform
						.getDelivercompany_ID());
			}
			// AJAX CALL
			request.setAttribute("servicelevels", servicelevels);
		}

		// change up service level
		if (request.getParameter("changeservice") != null
				&& request.getParameter("changeservice").equals("1")) {

			return (mapping.findForward(TracingConstants.AJAX_SERVICELEVEL));
		}

		request.setAttribute("losscodes", new ArrayList<Company_specific_irregularity_code>());
		request.setAttribute("faultstationlist",  new ArrayList<Station>());
		request.setAttribute("faultCompanyList",  new ArrayList<Company>());
		
		boolean checkLLC = false;
		try{
			if(request.getAttribute("currentstatus") != null) {
				checkLLC = Integer.parseInt((String)request.getAttribute("currentstatus")) == TracingConstants.MBR_STATUS_CLOSED;
			}
		} catch (NumberFormatException nfe){
			nfe.printStackTrace();
		}
		if(theform.getMbrType()==TracingConstants.NO_TYPE){
			if(theform.getIncident()!=null && theform.getIncident().getItemtype()!=null){
				theform.setMbrType(theform.getIncident().getItemtype_ID());
				request.setAttribute("mbrtype", theform.getIncident().getItemtype_ID());
			}
		}
		
		//Default to MBR Type 1
		int incidentType=theform.getMbrType()!=0?theform.getMbrType():TracingConstants.LOST_DELAY; 
		
		//the company specific codes..
		List<Company_specific_irregularity_code> codes = LossCodeBMO.getCompanyCodes(user.getStation().getCompany().getCompanyCode_ID(), incidentType, true, user, checkLLC);
		//add to the loss codes
		if(codes!=null)
			request.setAttribute("losscodes", codes);
		
		
		List<Station> faultstationlist = new ArrayList<Station>();
		List<Company> faultCompanyList = new ArrayList<Company>();
		
		faultstationlist = TracerUtils.getStationList(user.getStation().getCompany().getCompanyCode_ID());
		faultCompanyList = new ArrayList<Company>();
		faultCompanyList.add(user.getStation().getCompany());

		if(faultstationlist!=null){
			request.setAttribute("faultstationlist", faultstationlist);
		}
		if(faultCompanyList!=null){
			request.setAttribute("faultCompanyList", faultCompanyList);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		List list = new ArrayList(BDOUtils.getDeliveryCompanies(theform
				.getStation().getStation_ID(), true));
		if (list != null)
			request.setAttribute("delivercompanies", list);
		
		if (session.getAttribute("bdoCategoryList") == null) {
			list=new ArrayList(BDOUtils.getBDOCategories());
			if(list!=null)
				session.setAttribute("bdoCategoryList", list);
		}
		
		if (request.getParameter("receipt") != null
				&& request.getParameter("bdo_id") != null) {
			request.setAttribute("bdo_id", request.getParameter("bdo_id"));
			return (mapping.findForward(TracingConstants.RECEIPT_PARAMS));
		}

		/**
		 * Requesting the Delivery Cost if the BDO Delivery Company is of Integration Type SERV
		 */
		if (request.getParameter("requestDelivCost") != null) {
			//TODO: Find way to Persist Overweight, OverSize, Other, and NoAddFees values to carry to webcall
			CostServiceUtils.calculateDeliveryCost(theform, user,messages);
			saveMessages(request, messages);
			request.setAttribute("showbdo", "1");
			if(theform.getBDO_ID()>0){
				request.setAttribute("showprint", "1");
			}
			return (mapping.findForward(TracingConstants.BDO_MAIN));
		}
		
		// save bdo
		if (request.getParameter("save") != null) {

			request.setAttribute("showbdo", "1");
			/**If the form contains an incident id, then
			*get the incident information for purposes of checking for remark requirements
			*/
			if (theform.getIncident_ID()!=null && !theform.getIncident_ID().isEmpty()){
				BDOUtils.populateFormWithExistingData(theform.getIncident_ID(),theform);
			}

			// if there are no bags, then don't deliver, unless it is ohd
			if (theform.getItemlist().size() == 0
					&& theform.getIncident_ID() != null) {
				ActionMessage error = new ActionMessage("error.bdo_no_bag");
				messages.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, messages);
				return (mapping.findForward(TracingConstants.BDO_MAIN));
			}
			
			String[] bagchosen = null;

			// make sure user checked item to be inserted if choosebags is 1
			if (theform.getChoosebags() == 1) {
				// need to select
				bagchosen = request.getParameterValues("bagchosen");
				if (bagchosen == null) {
					ActionMessage error = new ActionMessage(
							"error.bdo_choose_bag");
					messages.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, messages);
					return (mapping.findForward(TracingConstants.BDO_MAIN));
				}
			}
			
			boolean lossCodesBagLevel=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LOSS_CODES_BAG_LEVEL, user);
			if(lossCodesBagLevel){
				if(!BDOUtils.checkFaultStationForPaxItin(theform)){
					ActionMessage error = new ActionMessage(
							"error.fault.pax.itin");
					messages.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, messages);
					return (mapping.findForward(TracingConstants.BDO_MAIN));
				}
			}

			

			// do insert
			BDO bdo = null;
			try {
				bdo = BDOUtils.createBdo(theform, bagchosen);
				bdo.setExpensePayout(createNewBdoPayout(theform, user, bdo));
				boolean success = BDOUtils.insertBDO(bdo, user);
				
				if (!success) {
					
					ActionMessage error =  new ActionMessage("error.unable_to_insert_bdo");
					messages.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, messages);
					return (mapping.findForward(TracingConstants.BDO_MAIN));
				} else if(PropertyBMO.isTrue(PropertyBMO.PROPERTY_DELIVERY_INSTRUCTIONS)) {
					try{
						Incident inc=IncidentBMO.getIncidentByID(incident,null);
						DeliveryInstructions DI=new DeliveryInstructions();
						if(inc.getDeliveryInstructions()!=null)
							DI=inc.getDeliveryInstructions();
						DI.setInstructions(bdo.getDelivery_comments());
						HibernateUtils.save(DI);
						if(inc.getDeliveryInstructions()==null){
							inc.setDeliveryInstructions(DI);
						}
						IncidentBMO iBMO = new IncidentBMO();
						iBMO.saveAndAuditIncident(false,inc, user,null);
					}
					catch(Exception e){}
				}
				//Create BDO
				BDOHistoryObject BHO=new BDOHistoryObject();
				BHO.setBDO(bdo);
				BHO.setObjectID(String.valueOf(bdo.getBDO_ID_ref()));
				BHO.setActualID( String.valueOf(bdo.getBDO_ID()));
				BHO.setLinkURL("bdo.do?bdo_id=");
				BHO.setObjectType(TracingConstants.HIST_DESCRIPTION_BDO);
				BHO.setStatusDesc(TracingConstants.HIST_DESCRIPTION_SAVE+" "+TracingConstants.HIST_DESCRIPTION_BDO);
				HistoryUtils.AddToHistoryContainer(session, BHO, String.valueOf(bdo.getBDO_ID()));
			} catch (Exception e) {
				ActionMessage error =  new ActionMessage("error.unable_to_insert_bdo");
				messages.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, messages);
				return (mapping.findForward(TracingConstants.BDO_MAIN));
			}
			
			ohd = theform.getOHD_ID(); // we will either have ohd or incident
			incident = theform.getIncident_ID();

			request.setAttribute("inserted", bdo.getBDO_ID());
			request.setAttribute("showprint", "1");
				
			// Perform delivery integration if necessary.
			DeliveryIntegrationResponse dir = DeliveryIntegrationTypeUtils.integrate(bdo, user);
			
			if (dir != null) {
				if (dir.isSuccess()) {
					if (dir.getResponse() != null) {
						String responseText = dir.getResponse();
						request.setAttribute("integrationResponse", responseText);
					}
				} else {
					String responseText = dir.getResponse();
					if (responseText != null) {
						request.setAttribute("integrationResponse", responseText);
					}
				}
			}
		}
		if (request.getParameter("savetowt") != null) {
			request.setAttribute("showbdo", "1");
			request.setAttribute("inserted", "1");
			request.setAttribute("showprint", "1");
			//TODO Is this important?
/*			WT_Queue wtq = new WT_Queue();
			wtq.setAgent(user);

			wtq.setCreatedate(TracerDateTime.getGMTDate());
			wtq.setType_id(String.valueOf(theform.getBDO_ID()));
			wtq.setWt_stationcode(user.getStation().getWt_stationcode());

			wtq.setType("bdo");

			wtq.setQueue_status(TracingConstants.LOG_NOT_RECEIVED);
			WorldTracerQueueUtils.saveBdoobj(theform, wtq, user);*/

			return (mapping.findForward(TracingConstants.BDO_MAIN));
		}
		// user wants to create a new bdo
		if (request.getParameter("createnewbdo") != null) {
			boolean showBdo=BDOUtils.createNewBDO(ohd, incident, theform, request);
			if(showBdo){
				request.setAttribute("showbdo", "1");
				return (mapping.findForward(TracingConstants.BDO_MAIN));
			}
			else{
				ActionMessage error = new ActionMessage("error.bdo_cant_create");
				messages.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, messages);
				request.setAttribute("showbdolist", "1");
			}
		}

		// user wants to update bdo
		if (request.getParameter("bdo_id") != null) {
			if (!BDOUtils.findBDO(Integer.parseInt(request
					.getParameter("bdo_id")), theform, request)) {
				ActionMessage error = new ActionMessage("error.bdo_not_found");
				messages.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, messages);
			} else {
				BDO bdo =BDOUtils.getBDOFromDB(Integer.parseInt(request.getParameter("bdo_id")));
				BDOHistoryObject BHO=new BDOHistoryObject();
				BHO.setBDO(bdo);
				BHO.setObjectID(String.valueOf(bdo.getBDO_ID_ref()));
				BHO.setActualID( String.valueOf(bdo.getBDO_ID()));
				BHO.setLinkURL("bdo.do?bdo_id=");
				BHO.setObjectType(TracingConstants.HIST_DESCRIPTION_BDO);
				BHO.setStatusDesc(TracingConstants.HIST_DESCRIPTION_LOAD+" "+TracingConstants.HIST_DESCRIPTION_BDO);
				HistoryUtils.AddToHistoryContainer(session, BHO,  String.valueOf(bdo.getBDO_ID()));
				request.setAttribute("showbdo", "1");
				request.setAttribute("showprint", "1");
				if(bdo.getIncident()!=null){
					BDOUtils.populateFormWithExistingData(bdo.getIncident(),theform);
				}	
				if(bdo.getDelivery_integration_type()==DeliveryIntegrationType.SERV && bdo.getDelivery_integration_id()!=null && !bdo.getDelivery_integration_id().isEmpty() && PropertyBMO.getValue(PropertyBMO.BDSI_ADDRESS_ENDPOINT)!=null){
					DecimalFormat myFormatter=new DecimalFormat("0000000000");
					String bdoNum=myFormatter.format(bdo.getBDO_ID());
					String stationCode="";
					if(bdo.getIncident()!=null){
						stationCode=bdo.getIncident().getStationcode();
					} else if (bdo.getOhd()!=null && bdo.getOhd().getHoldingStation()!=null){
						stationCode=bdo.getOhd().getHoldingStation().getStationcode();
					} else if(user.getStation()!=null) {
						stationCode=user.getStation().getStationcode();
					}
					List<StatusListDisp> statuslist=BagDelStatusInfoUtils.getStatusList(stationCode+bdo.getCompanycode_ID()+bdoNum,user);
					if(statuslist!=null) {
						request.setAttribute("statusList", statuslist);
					}	else { 
						request.setAttribute("webserviceError", new Integer("1"));
					}
				}
				return (mapping.findForward(TracingConstants.BDO_MAIN));
			}

		}

		// show list of bdo after entering bdo section
		if (ohd != null) {
			ohd = ohd.toUpperCase().trim();

			// retrieve all bdos in a list for this ohd
			if (!BDOUtils.findBDOList(ohd, null, theform, request)) {

				ActionMessage error = new ActionMessage("error.no.onhandreport");
				messages.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, messages);
				request.setAttribute("onhand", "1");
			} else {
				request.setAttribute("showbdolist", "1");
			}
		} else if (incident != null) {
			// user came in for mbr bdo
			incident = incident.toUpperCase().trim();
			Incident inc=IncidentBMO.getIncidentByID(incident, null);
			if(inc!=null){
				theform.setIncident(inc);
				theform.setMbrType(inc.getItemtype_ID());
			}
			// retrieve all bdos in a list for this incident
			if (!BDOUtils.findBDOList(null, incident, theform, request)) {
				ActionMessage error = new ActionMessage("error.noincident");
				messages.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, messages);
				request.setAttribute("mbr", "1");
			} else {
				request.setAttribute("showbdolist", "1");
			}
		}
		
		IncidentUtils.promptToCloseFile(incident, null, request);

		return (mapping.findForward(TracingConstants.BDO_MAIN));
	}
	
	protected ExpensePayout createNewBdoPayout(BDOForm theform, Agent user, BDO bdo) throws Exception {
		ExpensePayout ep = null;
		try {
			if (theform.getCost() != null && theform.getCost().length() > 0) {
				ep = new ExpensePayout();
				
				ep.setAgent(user);	
				ep.setCurrency(Currency.getInstance(theform.getCurrency()));
				ep.setCreatedate(new Date());
				
				Status s = new Status();
				s.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_PAID);
				ep.setStatus(s);
				
				ep.setApproval_date(new Date());
				
				ep.setCheckamt(Double.parseDouble(theform.getCost()));
				
				ep.setExpenselocation(user.getStation());
		
				ExpenseType et = new ExpenseType();
				et.setExpensetype_ID(TracingConstants.EXPENSEPAYOUT_DELIVERY);
				ep.setExpensetype(et);
				ep.setPaycode(TracingConstants.EXPENSEPAYOUT_DELIVERY_CODE);
						
				ep.setBdo(bdo);
				if (bdo.getIncident() != null) {
					ep.setIncident(bdo.getIncident());
				} else if (bdo.getOhd() != null) {
					ep.setOhd(bdo.getOhd());
				}
				
				ep.setStation(user.getStation());
			}
		} catch (NumberFormatException e) {
			return null;
		}

		return ep;
	}
}