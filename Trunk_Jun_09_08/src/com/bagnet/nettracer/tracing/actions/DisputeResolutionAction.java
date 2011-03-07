package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.LossCodeBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
//import com.bagnet.nettracer.tracing.db.TimeZone;
import com.bagnet.nettracer.tracing.db.dr.Dispute;
import com.bagnet.nettracer.tracing.db.dr.DisputeUtils;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.DisputeResolutionUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class DisputeResolutionAction extends CheckedAction {
	private static Logger logger = Logger.getLogger(DisputeResolutionAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		String forwardTarget = TracingConstants.NO_PERMISSION;
		String redirect = null;

		// check session
		TracerUtils.checkSession(session);

		if(session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		Agent user = (Agent) session.getAttribute("user");
		
		IncidentForm theform = (IncidentForm) form;
		session.setAttribute("incidentForm", theform);
		
		//get all the request parameters to determine the appropriate action
		String incident = request.getParameter("id");
		request.setAttribute("incident", incident);
		
		Incident myIncident = IncidentUtils.findIncidentByID(incident);
		if(theform.getFaultcompany_id() == null && myIncident != null && myIncident.getFaultstation() != null){
			theform.setFaultcompany_id(myIncident.getFaultstation().getCompany().getCompanyCode_ID());
		}
		
		//add new dispute
		String actionType = "" + request.getParameter("actionType");
		
		int incidentType = DisputeResolutionUtils.getIncidentType(incident);
		
		if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MANAGE_FAULT_DISPUTE, user)) {
			
			
			if (actionType.equalsIgnoreCase("start")) {
				forwardTarget = TracingConstants.DISPUTE_RESOLUTION;
			} else if (actionType.equalsIgnoreCase("reopen")){ 
				if(!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)
						&& !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MANAGE_FAULT_DISPUTE, user)) {
					forwardTarget = TracingConstants.NO_PERMISSION;
				} else {
					forwardTarget = viewToResolveDispute(incident, request);	
				}
			} else if (actionType.equalsIgnoreCase("new")) {
				forwardTarget = createNew(incident, theform, user);
			} else if (actionType.equalsIgnoreCase("viewToResolve")) {		//only authorized agent can update existing dispute
				if(!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)
						&& !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MANAGE_FAULT_DISPUTE, user)) {
					forwardTarget = TracingConstants.NO_PERMISSION;
				} else {
					forwardTarget = viewToResolveDispute(incident, request);	
				}
			} else if (actionType.equalsIgnoreCase("view")) {	// view only existing
				forwardTarget = viewExisting(incident, request);
			} else if (actionType.equalsIgnoreCase("manage")) {	// list all disputes limited access
				forwardTarget = TracingConstants.VIEW_DISPUTES;
			} else if (actionType.equalsIgnoreCase("update")) {
				ResourceBundle resourceBundle = ResourceBundle.getBundle(
						"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
				String myManuallyModifyDisputeBtn = "" + resourceBundle.getString("button.manually.modify.dispute");
				String myAcceptDisputeBtn = "" + resourceBundle.getString("button.accept.dispute");
				String myDenyDisputeBtn = "" + resourceBundle.getString("button.deny.dispute");
				
				boolean success = true;
				String whichButton = "";
				String myButton = "" + theform.getBtnUpdateDispute();
				if(myButton.equalsIgnoreCase(myAcceptDisputeBtn)) {
					whichButton += myAcceptDisputeBtn;
					//do accept dispute action here
					success = acceptDispute(incident, user);
				} else if (myButton.equalsIgnoreCase(myDenyDisputeBtn)) {
					whichButton += myDenyDisputeBtn;
					success = denyDispute(incident, user);
				} else if (myButton.equalsIgnoreCase(myManuallyModifyDisputeBtn)) {
					whichButton += myManuallyModifyDisputeBtn;
					success = manuallyModifyDispute(incident, theform, user);
				} 
				
				DisputeResolutionUtils.lockIncident(incident); 
				DisputeResolutionUtils.auditIncidentLockOrUnlock(incident, user);
				//DisputeResolutionUtils.lockIncidentWithAudit(incident, user);
				request.setAttribute("incident_id", incident);
				if(success){
					request.setAttribute("success", 1);
				}
				forwardTarget = TracingConstants.DISPUTE_RESOLUTION_UPDATE_SUCCESS;
				//forwardTarget = TracingConstants.VIEW_DISPUTES;
			}
		} else if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_DISPUTE_FAULT_CODE, user)) {
			if (actionType.equalsIgnoreCase("start")) {
				forwardTarget = TracingConstants.DISPUTE_RESOLUTION;
			} else if (actionType.equalsIgnoreCase("new")) {
				forwardTarget = createNew(incident, theform, user);
			} else if (actionType.equalsIgnoreCase("view")) {	// view only existing
				forwardTarget = viewExisting(incident, request);
			} 
		}
		
		//the company specific codes..
		//List<Company_specific_irregularity_code> codes = LossCodeBMO.getCompanyCodes(user.getStation().getCompany().getCompanyCode_ID(), TracingConstants.LOST_DELAY, true, user);
		List<Company_specific_irregularity_code> codes = LossCodeBMO.getCompanyCodes(user.getStation().getCompany().getCompanyCode_ID(), incidentType, false, user);
		//add to the loss codes
		request.setAttribute("losscodes", codes);
		
		List<Station> faultStationList = null;
		List<Company> faultCompanyList = null;
		
		// If the user has limited permission, 
		if (forwardTarget == TracingConstants.DISPUTE_RESOLUTION){
			//we do not restrict assigned fault stations during a dispute
			//the other cases are probably never used since ld, dam, pil uses their own actions
			faultStationList = TracerUtils.getStationList(theform.getFaultcompany_id());
			faultCompanyList = (List) request.getSession().getAttribute("companylistByName");
		} else if (UserPermissions.hasLimitedSavePermission(user, incident)) {
			faultStationList = UserPermissions.getLimitedSaveStations(user, incident);
			faultCompanyList = new ArrayList<Company>();
			faultCompanyList.add(user.getStation().getCompany());
		} else if (UserPermissions.hasLimitedFaultAirlines(user, incident)) {
			faultStationList = TracerUtils.getStationList(theform.getFaultcompany_id());
			faultCompanyList = new ArrayList<Company>();
			faultCompanyList.add(user.getStation().getCompany());
		} else {
			faultStationList = TracerUtils.getStationList(theform.getFaultcompany_id());
			faultCompanyList = (List) request.getSession().getAttribute("companylistByName");
		}
		request.setAttribute("faultstationlist", faultStationList);
		request.setAttribute("faultCompanyList", faultCompanyList);
		
		BagService bs = new BagService();
		try {
			bs.findIncidentByID(incident, theform, user, TracingConstants.MISSING_ARTICLES);
		}
		catch (Exception e) {
		}
		
		return mapping.findForward(forwardTarget);
	}
	private String createNew(String incident, IncidentForm theform, Agent user) {
		Dispute myDispute = new Dispute();
		
		myDispute.set_DATEFORMAT(user.getDateformat().getFormat());
		myDispute.set_TIMEFORMAT(user.getTimeformat().getFormat());
		myDispute.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
		
		myDispute.setCreated_timestamp(new Date());
		
		Status s = new Status();
		s.setStatus_ID(TracingConstants.DISPUTE_RESOLUTION_STATUS_OPEN);
		myDispute.setStatus(s);
		
		myDispute.setDisputeAgent(user);
		
		IncidentBMO iBMO = new IncidentBMO();
		Incident iDTO = iBMO.findIncidentByID(incident);
		int idtoFaultStationId = iDTO.getFaultstation().getStation_ID();
		int idtoLossCode = iDTO.getLoss_code();
		
//		logger.error("idtoFaultStationId=" + idtoFaultStationId + " and ");
//		logger.error("idtoLossCode=" + idtoLossCode + " and ");
//		logger.error("theform.getFaultstation_id()=" + theform.getFaultstation_id() + " and ");
//		logger.error("theform.getLoss_code()=" + theform.getLoss_code());		
		myDispute.setIncident(iDTO);
		
		myDispute.setBeforeDisputeFaultStation(iDTO.getFaultstation());
		myDispute.setBeforeDisputeLossCode(iDTO.getLoss_code());
		
		Station suggestedFaultStation = new Station();
		suggestedFaultStation.setStation_ID(theform.getFaultstation_id());
		myDispute.setSuggestedFaultStation(suggestedFaultStation);
	
		myDispute.setSuggestedLossCode(theform.getLoss_code());
		
		String typeOfChange = getTypeOfChangeInDispute(
				myDispute.getBeforeDisputeFaultStation(),
				suggestedFaultStation,
				myDispute.getBeforeDisputeLossCode(),
				myDispute.getSuggestedLossCode());
		myDispute.setTypeOfChange(typeOfChange);
		
		myDispute.setDisputeExplanation(theform.getDisputeRemark());
		
		myDispute.setResolutionAgent(user);		//got better idea?
		myDispute.setDeterminedFaultStation(iDTO.getFaultstation());
		myDispute.setDeterminedLossCode(iDTO.getLoss_code());
		
		DisputeUtils.saveDispute(myDispute);
		
		return TracingConstants.DISPUTE_RESOLUTION_INSERT_NEW_SUCCESS;
	}
	
	private String viewExisting(String incidentId, HttpServletRequest request) {
		Dispute dispute = DisputeUtils.getDisputeByIncidentId(incidentId);
		if (dispute != null) {
			request.setAttribute("dispute", dispute);
			
			Status myStatus = dispute.getStatus();
			
			String myStatusDesc = "";
			if (myStatus != null) {
				myStatusDesc += myStatus.getTextDescription(null);
			}
			request.setAttribute("statusDesc", myStatusDesc);
		}
		
		
		return TracingConstants.DISPUTE_RESOLUTION_READ_ONLY;
	}
	
	private String viewToResolveDispute(String incidentId, HttpServletRequest request) {
		Dispute dispute = DisputeUtils.getDisputeByIncidentId(incidentId);
		if (dispute != null) {
			request.setAttribute("dispute", dispute);
		}
		// if the status is open, then go to manage dispute page
		// otherwise, go to read only page
		
		return TracingConstants.MANAGE_DISPUTE;
	}
	
	private boolean acceptDispute(String incidentId, Agent user) {
		Dispute myDispute = DisputeUtils.getDisputeByIncidentId(incidentId);
		
		myDispute.setResolution_timestamp(DateUtils.convertToGMTDate(new Date()));
		Status status = new Status();
		status.setStatus_ID(TracingConstants.DISPUTE_RESOLUTION_STATUS_APPROVED);
		myDispute.setStatus(status);
		
		myDispute.setResolutionAgent(user);
		
		Station determinedFaultStation = myDispute.getSuggestedFaultStation();
		myDispute.setDeterminedFaultStation(determinedFaultStation);
		
		myDispute.setDeterminedLossCode(myDispute.getSuggestedLossCode());
		
//		DisputeUtils.saveDispute(myDispute);
		return DisputeUtils.saveDisputeAndUpdateIncident(myDispute, incidentId, user);
	}
	
	private boolean denyDispute(String incidentId, Agent user) {
		Dispute myDispute = DisputeUtils.getDisputeByIncidentId(incidentId);
		
		myDispute.setResolution_timestamp(DateUtils.convertToGMTDate(new Date()));
		Status status = new Status();
		status.setStatus_ID(TracingConstants.DISPUTE_RESOLUTION_STATUS_DENIED);
		myDispute.setStatus(status);
		
		myDispute.setResolutionAgent(user);
		
		Station determinedFaultStation = myDispute.getBeforeDisputeFaultStation();
		myDispute.setDeterminedFaultStation(determinedFaultStation);
		
		myDispute.setDeterminedLossCode(myDispute.getBeforeDisputeLossCode());
		
		return DisputeUtils.saveDispute(myDispute);
	}
	
	private boolean manuallyModifyDispute(String incidentId, IncidentForm theform, Agent user) {
		Dispute myDispute = DisputeUtils.getDisputeByIncidentId(incidentId);
		
		myDispute.setResolution_timestamp(DateUtils.convertToGMTDate(new Date()));
		Status status = new Status();
		status.setStatus_ID(TracingConstants.DISPUTE_RESOLUTION_STATUS_MANUAL_CHANGE);
		myDispute.setStatus(status);
		
		myDispute.setResolutionAgent(user);
		
		Station determinedFaultStation = new Station();
		determinedFaultStation.setStation_ID(theform.getFaultstation_id());
		myDispute.setDeterminedFaultStation(determinedFaultStation);
		
		myDispute.setDeterminedLossCode(theform.getLoss_code());
		
		myDispute.setResolutionRemarks(theform.getResolutionRemarks());
		
//		DisputeUtils.saveDispute(myDispute);
		return DisputeUtils.saveDisputeAndUpdateIncident(myDispute, incidentId, user);
	}
	
	private String getTypeOfChangeInDispute(
			Station beforeDisputeFaultStation,
			Station suggestedFaultStation,
			int beforeDisputeLossCode,
			int suggestedLossCode) {
		String result = "Fault & Code";
		
		if(beforeDisputeLossCode == suggestedLossCode) {
			result = "Fault";
		}
		if(beforeDisputeFaultStation.getStation_ID() == suggestedFaultStation.getStation_ID()) {
			result = "Code";
		}
		
		return result;
	}
}
