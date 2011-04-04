/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import aero.nettracer.fs.model.Claim;

import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.ClaimDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.forms.ClaimForm;
import com.bagnet.nettracer.tracing.forms.ClaimProrateForm;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ModifyClaimAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(ModifyClaimAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MODIFY_CLAIM, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		
		if(!manageToken(request)) {
			return (mapping.findForward(TracingConstants.INVALID_TOKEN));
		}
		

		boolean isNtUser = PropertyBMO.isTrue("nt.user");
		
		// for existing nt functionality
		ActionMessages errors = new ActionMessages();
		BagService bs = new BagService();
		IncidentForm theform = (IncidentForm) session.getAttribute("incidentForm");
		Incident ntIncident = null;
		request.setAttribute("CLAIM_PAYOUT_RPT", Integer.toString(ReportingConstants.CLAIM_PAYOUT_RPT));

		ClaimForm cform = (ClaimForm) form;
		Claim claim = null;
		
		// only do this if the customer is an NT user
		if (isNtUser) {
			
			// try to get the nt incident id
			String ntIncidentId;
			if (request.getParameter("incidentId") != null) {
				ntIncidentId = request.getParameter("incidentId");
			} else if (theform != null) {
				ntIncidentId = theform.getIncident_ID();
			} else {
				ntIncidentId = null;
			}
			
			request.setAttribute("incident", ntIncidentId);
			if (theform == null)
				theform = new IncidentForm();
			
			// try to get the nt incident if we have an id
			if (ntIncidentId != null && !ntIncidentId.isEmpty()) {
				
				ntIncident = bs.findIncidentByID(ntIncidentId, theform, user, TracingConstants.MISSING_ARTICLES);
				if (ntIncident == null) {

					ActionMessage error = new ActionMessage("error.noincident");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
					request.setAttribute("noincident", "1");
					return (mapping.findForward(TracingConstants.CLAIM_PAY_MAIN));
				} else {

					// if not the same company, then don't show claims
					if (!user.getStation().getCompany().getCompanyCode_ID().equals(theform.getStationassigned().getCompany().getCompanyCode_ID())) {
						ActionMessage error = new ActionMessage("error.noincident");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
						request.setAttribute("noincident", "1");
					} else {

						request.setAttribute("incident", theform.getIncident_ID());
						session.setAttribute("incidentForm", theform);
						session.removeAttribute("payout");
						if (session.getAttribute("prorate") != null) {
							session.removeAttribute("prorate");
							ClaimProrateForm cpform = new ClaimProrateForm();
							request.setAttribute("CLAIM_PRORATE_RPT", Integer.toString(ReportingConstants.CLAIM_PRORATE_RPT));
							TracerUtils.populateClaimProrate(cpform, theform, request);
							return (mapping.findForward(TracingConstants.CLAIM_PRORATE_MAIN));
						}
					}
				}
			}
		}
		
		if (request.getParameter("createNew") != null) {
			
			// create a new claim
			if (isNtUser && request.getParameter("populate") != null) {
				claim = ClaimUtils.createClaim(user, ntIncident);
			} else {
				claim = ClaimUtils.createClaim(user);
			}
		
		} else if (request.getParameter("claimId") != null) {
			
			// load an existing claim
			long id = Long.parseLong(request.getParameter("claimId"));
			if (id > 0) {
				claim = ClaimDAO.loadClaim(id);
			}
			
		} else {
			
			// edit the existing claim
			claim = cform.getClaim();
			if (isNtUser) {
				if (ntIncident != null && ntIncident.getClaim() != null) {
					claim = ntIncident.getClaim();
				}
			}
			
		}
		
		cform = ClaimUtils.createClaimForm(request);
		
		/* HANDLE REQUESTS */
		
		// save the claim
		if (request.getParameter("save") != null) {
			
			// 1. save the claim locally
			claim = cform.getClaim();
			boolean claimSaved = ClaimDAO.saveClaim(claim);
			
			// maintain existing nt functionality
			if (isNtUser) {
				if (claimSaved) {
					theform.setClaim(claim);
					request.setAttribute("success", "1");
					cform.setMod_claim_reason("");
				} else {
					request.setAttribute("fail", "1");
				}
			}
			
			// 2. save the claim on central services
			
			// 3. submit the claim for tracing
			
		} else if (request.getParameter("submit") != null) {
			// 1. submit the claim for tracing
			
		} else if (request.getParameter("error") != null) {
			if (request.getParameter("error").equals("print")) {
				// printing error
				ActionMessage error = new ActionMessage("error.print");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				request.setAttribute("closebtn", "1");
				return (mapping.findForward(TracingConstants.ERROR_MAIN));
			}
			if (request.getParameter("error").equals("nodata")) {
				// printing error
				ActionMessage error = new ActionMessage("error.nodata");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				request.setAttribute("closebtn", "1");
				return (mapping.findForward(TracingConstants.ERROR_MAIN));
			}
		} else if (isNtUser) {
			
			/**
			 * ***************** approve interim expense payout screen
			 * ********************
			 */
	
			if ((request.getParameter("approveinterim")) != null) {
				theform = new IncidentForm();
				if (ntIncident == null) {
					ActionMessage error = new ActionMessage("error.noincident");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
					request.setAttribute("noincident", "1");
					return (mapping.findForward(TracingConstants.CLAIM_PAY_MAIN));
				}
				request.setAttribute("incident", theform.getIncident_ID());
				session.setAttribute("incidentForm", theform);
				cform = ClaimUtils.createClaimForm(request);
				cform.setClaim(claim);
				
				request.setAttribute("edit", "1");
				request.setAttribute("editinterim", "1");
				request.setAttribute("approveinterim", "1");
				return (mapping.findForward(TracingConstants.CLAIM_PAY_MAIN));
	
			}
			
			/** **************** eof approve interim expense ******************* */
			if (theform == null || request.getParameter("clear") != null) {
				// came here from claim menu, need to show form to enter incident id
				session.setAttribute("payout", "1");
				request.setAttribute("noincident", "1");
				return (mapping.findForward(TracingConstants.CLAIM_PAY_MAIN));
			}
			
			// modify payouts
			if ((request.getParameter("modifyinterim")) != null) {
				request.setAttribute("editinterim", "1");			
			}
			
			// save interim
			if (request.getParameter("saveinterim") != null) {
				
				boolean savedClaim = ClaimDAO.saveClaim(claim);
				if (savedClaim) {
					theform.setClaim(claim);
					response.sendRedirect("searchIncident.do?incident=" + theform.getIncident_ID());
					return null;
				} else {
					request.setAttribute("edit", "1");
					request.setAttribute("editinterim", "1");
					request.setAttribute("fail", "1");
				}
			}
			
		}
		
		
		cform.setClaim(claim);
		return (mapping.findForward(TracingConstants.CLAIM_PAY_MAIN));
	}
}