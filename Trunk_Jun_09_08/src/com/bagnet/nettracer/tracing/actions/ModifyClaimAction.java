/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Segment;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.fs.model.detection.TraceResponse;
import aero.nettracer.selfservice.fraud.ClaimRemote;

import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.ClaimDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.forms.ClaimForm;
import com.bagnet.nettracer.tracing.forms.ClaimProrateForm;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.ntfs.ConnectionUtil;

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
		boolean ntfsUser = PropertyBMO.isTrue("ntfs.user");
		
		if (request.getParameter("back") != null) {
			request.setAttribute("back", "1");
		}
		
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
					if (request.getParameter("populate") != null) {
						return (mapping.findForward(TracingConstants.CLAIM_CREATE_NEW));
					} else {
						if (cform.getClaim() == null) {
							cform.setClaim(new Claim());
						}
						return mapping.findForward(TracingConstants.CLAIM_PAY_MAIN);
					}
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
				claim = ntIncident.getClaim();
				if (claim == null) {
					claim = ClaimUtils.createClaim(user, ntIncident);
				}
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
			if (isNtUser && ntIncident != null) {
				claim = ntIncident.getClaim();
				if (claim == null) {
					claim = ClaimUtils.createClaim(user, ntIncident);
					claim.setNtIncident(ntIncident);
					ntIncident.setClaim(claim);
				}
			}
		}
		
		cform = ClaimUtils.createClaimForm(request);
		
		/* HANDLE REQUESTS */
		
		// save the claim
		if (request.getParameter("save") != null) {
			
			// 1. save the claim locally
			claim = cform.getClaim();
			boolean firstSave = claim.getId() == 0;
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
			
			if (ntfsUser) {
				// 2. save the claim on central services
				Context ctx = ConnectionUtil.getInitialContext();
				ClaimRemote remote = (ClaimRemote) ctx
						.lookup("NTServices_1_0/ClaimBean/remote");
				long remoteFileId = 0;
				if (remote != null) {
					FsClaim newClaim = new FsClaim();
					BeanUtils.copyProperties(newClaim, claim);
					LinkedHashSet<Segment> segs = new LinkedHashSet<Segment>();
					newClaim.setSegments(segs);
					
					LinkedHashSet<Person> pers = new LinkedHashSet<Person>();
					newClaim.setClaimants(pers);
					
					newClaim.getIncident().setClaim(newClaim);
					for (Person p: claim.getClaimants()) {
						p.setClaim(newClaim);
						pers.add(p);
					}
					
					for (Segment s: claim.getSegments()) {
						s.setClaim(newClaim);
						segs.add(s);
					}
					
					File file = claim.getFile();
					if (file == null) {
						file = new File();
					}
					file.setClaim(newClaim);
					file.setStatusId(claim.getStatus().getStatus_ID());
					newClaim.setFile(file);
					file.setIncident(newClaim.getIncident());
					newClaim.getIncident().setFile(file);
					
					remoteFileId = remote.insertFile(file);
					if (remoteFileId > 0) {
						file.setSwapId(remoteFileId);
					}
					
					claim = ClaimDAO.loadClaim(claim.getId());
					claim.setFile(file);
					file.setClaim(claim);
					file.setIncident(claim.getIncident());
					claim.getIncident().setFile(file);
					
					ClaimDAO.saveClaim(claim);
					logger.info("Claim saved to central services: " + remoteFileId);
	
					// 3. submit the claim for tracing
					TraceResponse results = submitClaim(remoteFileId, firstSave);
					if (results != null) {
						
						// TODO: SET RELOAD TIME HERE
						session.setAttribute("results", results.getMatchHistory());
						response.sendRedirect("fraud_results.do?results=1&claimId=" + claim.getId());
						return null;
					}
					ctx.close();
				}
			}
			
		} else if (request.getParameter("submit") != null) {
			
			// 1. submit the claim for tracing
			if (claim.getFile().getSwapId() > 0) {
				TraceResponse results = submitClaim(claim.getFile().getSwapId(), false);
				if (results != null) {
					session.setAttribute("results", results.getMatchHistory());
					response.sendRedirect("fraud_results.do?results=1&claimId=" + claim.getId());
					return null;
				}
			}
			
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
	
	private TraceResponse submitClaim(long fileId, boolean primary) {
		if (fileId <= 0) {
			return null;
		}
		TraceResponse results = null;
		try {
			Context ctx = ConnectionUtil.getInitialContext();
			ClaimRemote remote = (ClaimRemote) ctx.lookup("NTServices_1_0/ClaimBean/remote");
			
			
			if (remote != null) {
				int wait = 6;
				try {
					wait = PropertyBMO.getValueAsInt(PropertyBMO.CENTRAL_FRAUD_CHECK_TIMEOUT);
				} catch (Exception e) {
					//
				}
				results = remote.traceFile(fileId, wait, primary);
				
			}
			ctx.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return results;
	}
	
}