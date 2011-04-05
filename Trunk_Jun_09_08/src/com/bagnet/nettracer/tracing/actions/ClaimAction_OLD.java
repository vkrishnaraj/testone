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

import aero.nettracer.fs.model.FsClaim;

import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.claims.ClaimSettlementBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.forms.ClaimForm;
import com.bagnet.nettracer.tracing.forms.ClaimProrateForm;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ClaimAction_OLD extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(ModifyClaimAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		String incident_id = null;

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

		BagService bs = new BagService();
		ClaimForm cform = (ClaimForm) form;
		IncidentForm theform = (IncidentForm) session.getAttribute("incidentForm");
		if (theform != null) {
			request.setAttribute("incident", theform.getIncident_ID());
			if (ClaimSettlementBMO.getClaimSettlement(theform.getIncident_ID(), null) != null) {
				request.setAttribute("claimSettlementExists", "1");
			}
		} else {
			//TODO get incident ID here.
		}
		
		request.setAttribute("CLAIM_PAYOUT_RPT", Integer.toString(ReportingConstants.CLAIM_PAYOUT_RPT));

		/** ****************** handle requests ******************** */
		ActionMessages errors = new ActionMessages();
		if (request.getParameter("error") != null) {
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
		}

		if (request.getParameter("getclaim") != null || request.getParameter("getclaimfa") != null || session.getAttribute("getclaimfa") != null) {

			if (request.getParameter("getclaim") != null)
				incident_id = cform.getClaim().getNtIncident().getIncident_ID();
			if (request.getParameter("getclaimfa") != null)
				incident_id = request.getParameter("incidentid");
			if (request.getSession().getAttribute("getclaimfa") != null) {
				incident_id = (String) session.getAttribute("incidentid");
				request.getSession().removeAttribute("getclaimfa");
			}

			if (incident_id != null && incident_id.length() > 0) {
				if (theform == null)
					theform = new IncidentForm();
				if (bs.findIncidentByID(incident_id, theform, user, TracingConstants.MISSING_ARTICLES) == null) {

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

		ExpensePayout ep = null;

		/**
		 * ***************** approve interim expense payout screen
		 * ********************
		 */

		if ((request.getParameter("approveinterim")) != null) {
			incident_id = request.getParameter("incidentid");
			if (incident_id != null && incident_id.length() > 0) {
				theform = new IncidentForm();
				if (bs.findIncidentByID(incident_id, theform, user, TracingConstants.MISSING_ARTICLES) == null) {
					ActionMessage error = new ActionMessage("error.noincident");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
					request.setAttribute("noincident", "1");
					return (mapping.findForward(TracingConstants.CLAIM_PAY_MAIN));
				}
				request.setAttribute("incident", theform.getIncident_ID());
				session.setAttribute("incidentForm", theform);
			}
//			cform = TracerUtils.populateClaim(cform, theform, request);
//			cform = ClaimUtils.createClaimForm(incident_id, request);
			
			int index = -1;

			if (index == -1) {
				// unable to find the payout
				ActionMessage error = new ActionMessage("error.nopayout");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				request.setAttribute("nopayout", "1");
				return (mapping.findForward(TracingConstants.CLAIM_PAY_MAIN));
			}

			// Commented out because there is no more status description
			/*
			Status st = ep.getStatus();
			if (st.getDescription() == null || st.getDescription().length() == 0)
				ep.setStatus(StatusBMO.getStatus(st.getStatus_ID()));
			*/

			// TODO: FIX THE LINE BELOW TO RESTORE AUDIT_CLAIM FUNCTIONALITY
//			cform.setMod_claim_reason("");
//			cform.setMod_exp_reason("");
			request.setAttribute("edit", "1");
			if (index >= 0) {
				request.setAttribute("index", Integer.toString(index));
			}
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
		if ((request.getParameter("modifyinterim")) != null)
			request.setAttribute("editinterim", "1");


		// save claim amount
		if (request.getParameter("saveclaim") != null) {
			Claim cDTO = new Claim();

			if (bs.insertClaim(cDTO, cform, session, false, theform.getIncident_ID())) {
				theform.setClaim(cDTO);
//				cform.setClaim_ID(cDTO.getId());
				request.setAttribute("success", "1");
//				cform.setMod_claim_reason("");
			} else {
//				TracerUtils.populateClaim(cform, theform, request);
//				cform = ClaimUtils.createClaimForm(incident_id, request);
				request.setAttribute("fail", "1");
			}
			return (mapping.findForward(TracingConstants.CLAIM_PAY_MAIN));
		}

		// save claim expense payout
		boolean savedclaim = false;
		if (request.getParameter("save") != null) {
			Claim cDTO = new Claim();
			if (bs.insertClaim(cDTO, cform, session, false, theform.getIncident_ID()))
				savedclaim = true;

			
			if (savedclaim) {
				bs.findClaimByID(cform.getClaim().getId(), cform, theform);
//				cform = TracerUtils.populateClaim(cform, theform, request);
//				cform = ClaimUtils.createClaimForm(incident_id, request);
				request.setAttribute("success", "1");
			} else {
				request.setAttribute("fail", "1");
			}
			return (mapping.findForward(TracingConstants.CLAIM_PAY_MAIN));
		}

		// save interim
		if (request.getParameter("saveinterim") != null) {
			
			Claim cDTO = new Claim();
			if (bs.insertClaim(cDTO, cform, session, true, theform.getIncident_ID()))
				savedclaim = true;

			if (savedclaim) {
				bs.findClaimByID(cDTO.getId(), cform, theform);
				response.sendRedirect("searchIncident.do?incident=" + theform.getIncident_ID());
				return null;
			} else {
				request.setAttribute("edit", "1");
				request.setAttribute("editinterim", "1");
				request.setAttribute("fail", "1");
			}
		}

		/**
		 * retrieve to modify claim payout
		 */
//		cform = TracerUtils.populateClaim(cform, theform, request);
//		cform = ClaimUtils.createClaimForm(incident_id, request);
		request.setAttribute("incident", theform.getIncident_ID());

		return (mapping.findForward(TracingConstants.CLAIM_PAY_MAIN));
	}
}