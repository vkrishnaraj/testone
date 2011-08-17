/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.naming.Context;
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
import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsReceipt;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;
import aero.nettracer.fs.model.Segment;
import aero.nettracer.fs.model.detection.TraceResponse;
import aero.nettracer.selfservice.fraud.ClaimRemote;

import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.ClaimDAO;
import com.bagnet.nettracer.tracing.dao.FileDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.forms.ClaimForm;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.ntfs.ConnectionUtil;

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
		Set<FsClaim> claims = null;
		Claim claim = null;
		File file = null;
		
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
					} 
				}
				claims = new LinkedHashSet<FsClaim>(ntIncident.getClaims());
			}
			
			if (claims == null) {
				claims = new LinkedHashSet<FsClaim>();
			}
		}
		
		if (request.getParameter("createNew") != null) {
			
			// create a new claim
			if (isNtUser && request.getParameter("populate") != null) {
				if (claims.isEmpty() || PropertyBMO.isTrue("ntfs.support.multiple.claims")) {
					claim = ClaimUtils.createClaim(user, ntIncident);
					claims.add(claim);
					ntIncident.setClaims(new LinkedHashSet<Claim>());
				} else {
					claim = ntIncident.getClaims().iterator().next();
				}
				file = FileDAO.loadFile(ntIncident.getIncident_ID());
			} else {
				claim = ClaimUtils.createClaim(user);
			}
			
			if (file == null) {
				file = new File();
				file.setValidatingCompanycode(user.getCompanycode_ID());
				file.setClaims(new LinkedHashSet<FsClaim>());
			}
			
			file.getClaims().add(claim);
			claim.setFile(file);
			
			file.setIncident(claim.getIncident());
			claim.getIncident().setFile(file);
			
		} else if (request.getParameter("claimId") != null) {
			
			// load an existing claim
			long id = Long.parseLong(request.getParameter("claimId"));
			if (id > 0) {
				claim = ClaimDAO.loadClaim(id);
				// insert log entry here
				ClaimUtils.enterAuditClaimEntry(user.getAgent_ID(), TracingConstants.FS_AUDIT_ITEM_TYPE_FILE, (claim.getFile()!=null?claim.getFile().getId():-1), TracingConstants.FS_ACTION_LOAD);
				if (claim.getNtIncident() != null) {
					ntIncident = claim.getNtIncident();
					bs.populateIncidentFormFromIncidentObj(ntIncident.getIncident_ID(), theform, user, ntIncident.getItemtype_ID(), new IncidentBMO(), ntIncident, false);
				}
				file = claim.getFile();
			}
			
		} else {
			// edit the existing claim
			claim = cform.getClaim();
			if (isNtUser && ntIncident != null) {
				if (claim == null) {
					claim = ClaimUtils.createClaim(user, ntIncident);
					claim.setNtIncident(ntIncident);
					ntIncident.getClaims().add(claim);
				} else {
					// here we should have a claim and ntIncident
					if (PropertyBMO.isTrue("ntfs.support.multiple.claims") || ntIncident.getClaims().isEmpty()) {
						claim.setNtIncident(ntIncident);
						ntIncident.getClaims().add(claim);
					} else if (request.getParameter("save") == null) {
						// allow single claim only
						ActionMessage error = new ActionMessage("error.claim.exists");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
					}
				}
			}
		}
		
		if (isNtUser) {
			theform.setIncident_ID(claim.getNtIncidentId());
		}

		// delete associated items
		deleteAssociatedItems(claim, request);
		
		// add new items
		addAssociatedItems(claim, request, user);
		
		String showNames = request.getParameter("showNames");
		if (showNames != null) {
			request.setAttribute("showNames", showNames);
		}

		String showReceipts = request.getParameter("showReceipts");
		if (showReceipts != null) {
			request.setAttribute("showReceipts", showReceipts);
		}

		cform = ClaimUtils.createClaimForm(request);

		/* HANDLE REQUESTS */
		
		// save the claim
		if (request.getParameter("save") != null) {
			
			// 1. save the claim locally
			claim = cform.getClaim();
			deleteEmptyNames(claim);

			boolean firstSave = claim.getId() == 0;
			boolean claimSaved = FileDAO.saveFile(claim.getFile(), firstSave);	
			
			ClaimUtils.enterAuditClaimEntry(user.getAgent_ID(), TracingConstants.FS_AUDIT_ITEM_TYPE_FILE, (claim.getFile()!=null?claim.getFile().getId():-1), TracingConstants.FS_ACTION_SAVE);
			
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
				Context ctx = null;
				ClaimRemote remote = null;
				try {
					ctx = ConnectionUtil.getInitialContext();
					remote = (ClaimRemote) ConnectionUtil.getRemoteEjb(ctx, PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME));
				} catch (Exception e) {
					logger.error(e);
				}
				
				long remoteFileId = 0;
				if (remote != null) {
					LinkedHashSet<FsClaim> fsClaims = new LinkedHashSet<FsClaim>();
					
					if (file == null) {
						file = claim.getFile();
					}
					
					for (FsClaim current: file.getClaims()) {
						FsClaim newClaim = new FsClaim();
						BeanUtils.copyProperties(newClaim, current);
						LinkedHashSet<Segment> segs = new LinkedHashSet<Segment>();
						newClaim.setSegments(segs);
						
						LinkedHashSet<Person> pers = new LinkedHashSet<Person>();
						newClaim.setClaimants(pers);
						
						for (Person p: current.getClaimants()) {
							p.setClaim(newClaim);
							pers.add(p);
						}
						
						for (Segment s: current.getSegments()) {
							s.setClaim(newClaim);
							segs.add(s);
						}
						
						LinkedHashSet<FsReceipt> receipts = new LinkedHashSet<FsReceipt>();
						newClaim.setReceipts(receipts);
						for (FsReceipt r: current.getReceipts()) {
							r.setClaim(newClaim);
							receipts.add(r);
						}
						
						file.setStatusId(claim.getStatusId());
						newClaim.setFile(file);
						file.setIncident(newClaim.getIncident());
						newClaim.getIncident().setFile(file);
						fsClaims.add(newClaim);
					}
					
					file.setClaims(fsClaims);
					remoteFileId = remote.insertFile(file);
					claim = ClaimDAO.loadClaim(claim.getId());
					if (remoteFileId > 0) {
						claim.getFile().setSwapId(remoteFileId);
						FileDAO.saveFile(claim.getFile(), false);
					}
					logger.info("Claim saved to central services: " + remoteFileId);
					
					// 3. submit the claim for tracing
					boolean hasViewFraudResultsPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_VIEW_FRAUD_RESULTS, user);
					TraceResponse results = ConnectionUtil.submitClaim(remoteFileId, firstSave, hasViewFraudResultsPermission);
					ClaimUtils.enterAuditClaimEntry(user.getAgent_ID(), TracingConstants.FS_AUDIT_ITEM_TYPE_FILE, (claim.getFile()!=null?claim.getFile().getId():-1), TracingConstants.FS_ACTION_SUBMIT);
					if (hasViewFraudResultsPermission && results != null) {
						
						// TODO: SET RELOAD TIME HERE
						session.setAttribute("traceResults", results);
						session.setAttribute("results", results.getMatchHistory());
						response.sendRedirect("fraud_results.do?results=1&claimId=" + claim.getId());
						return null;
					}
					
					if (ctx != null) {
						ctx.close();
					}
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
			
			
		}
		
		cform.setClaim(claim);
		session.setAttribute("incidentForm", theform);
		return (mapping.findForward(TracingConstants.CLAIM_PAY_MAIN));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void deleteAssociatedItems(FsClaim claim, HttpServletRequest request) {
		deleteAssociatedElements(new ArrayList(claim.getClaimants()), claim.getClaimants(), TracingConstants.JSP_DELETE_ASSOCIATED_NAME, request);
		deleteAssociatedElements(new ArrayList(claim.getReceipts()), claim.getReceipts(), TracingConstants.JSP_DELETE_ASSOCIATED_RECEIPT, request);	
	}
	
	private void addAssociatedItems(FsClaim claim, HttpServletRequest request, Agent user) {
		addAssociatedNames(claim, request);
		addAssociatedReceipts(claim, request, user);
	}
	
	@SuppressWarnings("rawtypes")
	private void deleteAssociatedElements(ArrayList source, Set destination, String key, HttpServletRequest request) {
		if (source == null || destination == null) {
			return;
		}
		
		String toDelete = request.getParameter("delete_these_elements");
		if (toDelete != null && !toDelete.isEmpty()) {
			String[] tokens = toDelete.split(",");
			for (int i = 0; i < tokens.length; ++i) {
				if (tokens[i].contains(key)) {
					int index = Integer.parseInt(tokens[i].split("_")[1]);
					destination.remove(source.get(index));
				}
			}
		}
	}
	
	private void addAssociatedNames(FsClaim claim, HttpServletRequest request) {
		if (request.getParameter("addNames") != null) {
			try {
				int numToAdd = Integer.parseInt(request.getParameter("addNameNum"));
				for (int i = 0; i < numToAdd; ++i) {
					Person p = new Person();
					p.setClaim(claim);
					claim.getClaimants().add(p);
				}
			} catch (NumberFormatException nfe) {
				logger.error(nfe);
			}
		}
	}
	
	private void addAssociatedReceipts(FsClaim claim, HttpServletRequest request, Agent user) {
		if (request.getParameter("addReceipts") != null) {
			try {
				String country = CompanyBMO.getCompany(user.getCompanycode_ID()).getCountrycode_ID();
				int numToAdd = Integer.parseInt(request.getParameter("addReceiptNum"));
				for (int i = 0; i < numToAdd; ++i) {
					FsReceipt r = createReceipt(country);
					r.setClaim(claim);
					claim.getReceipts().add(r);
				}
			} catch (NumberFormatException nfe) {
				logger.error(nfe);
			}
		}
	}
	
	private void deleteEmptyNames(FsClaim claim) {
		ArrayList<Person> people = new ArrayList<Person>(claim.getClaimants());
		for (Person p: people) {
			String last = p.getLastName();
			String first = p.getFirstName();
			if ((last == null || last.isEmpty()) && (first == null || first.isEmpty())) {
				claim.getClaimants().remove(p);
			}
		}
	}
	
	private FsReceipt createReceipt(String country) {
		FsReceipt receipt = new FsReceipt();
		receipt.setCcType("");
		
		FsAddress address = new FsAddress();
		address.setCountry(country);
		address.setReceipt(receipt);
		receipt.setAddress(address);
		
		Phone phone = new Phone();
		phone.setType(Phone.WORK);
		phone.setReceipt(receipt);
		receipt.setPhone(phone);
		
		return receipt;
	}
	
}