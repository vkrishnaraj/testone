/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.StringTokenizer;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.Query;
import org.hibernate.Session;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIPAddress;
import aero.nettracer.fs.model.FsReceipt;
import aero.nettracer.fs.model.Attachment;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;
import aero.nettracer.fs.model.Segment;
import aero.nettracer.fs.model.detection.TraceResponse;
import aero.nettracer.fs.utilities.TransportMapper;
import aero.nettracer.selfservice.fraud.client.ClaimClientRemote;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.claims.ClaimSettlementBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.ClaimDAO;
import com.bagnet.nettracer.tracing.dao.FileDAO;
import com.bagnet.nettracer.tracing.db.Agent;
//import com.bagnet.nettracer.tracing.db.Attachment;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Item_Photo;
import com.bagnet.nettracer.tracing.forms.ClaimForm;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.history.ClaimHistoryObject;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;
import com.bagnet.nettracer.tracing.utils.FileShareUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.HistoryUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.ImageUtils;
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
		
		//file save feature
		int itemindex = -1;
		int fileindex = -1;
		StringTokenizer stt = null;
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String parameter = (String) e.nextElement();
			try {
				if (parameter.indexOf("removeAttachment") > -1) {
					stt = new StringTokenizer(parameter, "_");
					if (stt.hasMoreElements())
						stt.nextToken();
					if (stt.hasMoreElements())
						itemindex = Integer.parseInt(stt.nextToken());
				}
			} catch (Exception removephotoe) {
				// tempering with data, should never happen
			}
			if (parameter.indexOf("uploadAttachment") > -1) {
				fileindex = 0;
			}
		}
		
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
			
			if (ntIncidentId != null) {
				if (ClaimSettlementBMO.getClaimSettlement(ntIncidentId, null) != null) {
					request.setAttribute("claimSettlementExists", "1");
				}
			}
			
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
				file = FileDAO.loadFile(ntIncident.getIncident_ID()); //airlineIncMark
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
			
			if(claim.getAttachments()==null){
				claim.setAttachments(new HashSet());
			}
			
		} else if (request.getParameter("claimId") != null) {
			
			// load an existing claim
			long id = Long.parseLong(request.getParameter("claimId"));
			if (id > 0) {
				claim = ClaimDAO.loadClaim(id);
				// insert log entry here
				ClaimHistoryObject CHO=new ClaimHistoryObject();
				CHO.setClaim(claim);
				CHO.setObjectID(String.valueOf(claim.getId()));
				CHO.setLinkURL("claim_resolution.do?claimId=");
				CHO.setStatusDesc(TracingConstants.HIST_DESCRIPTION_LOAD+" "+TracingConstants.HIST_DESCRIPTION_CLAIM);
				CHO.setObjectType(TracingConstants.HIST_DESCRIPTION_CLAIM);
				HistoryUtils.AddToHistoryContainer(session, CHO, null);
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
					} else if (request.getParameter("save") == null && request.getParameter("addNames") == null && request.getParameter("addReceipts") == null && fileindex == 0 && itemindex == 0) {
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
		
		//add attachments
		if (ntfsUser && (fileindex >= 0 || itemindex >= 0) ) {
			// 2. save the claim on central services
			Context ctx = null;
			ClaimClientRemote remote = null;
			try {
				ctx = ConnectionUtil.getInitialContext();
				remote = (ClaimClientRemote) ConnectionUtil.getRemoteEjb(ctx, PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME));
			} catch (Exception ex) {
				logger.error(ex);
			}
			
			long remoteFileId = 0;
			if (remote == null) {
				ActionMessage error = new ActionMessage("error.fs.could.not.communicate");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				if (itemindex >= 0) {
					// don't remove photo for now.
					Attachment theattachment= (Attachment) cform.getClaim().getAttachments().toArray()[itemindex];
					remote.deleteAttachment(theattachment.getAttachment_id());
					cform.getClaim().getAttachments().remove(theattachment);
					HibernateUtils.delete(theattachment);
					request.setAttribute("upload", Integer.toString(itemindex));
				} else if (fileindex >= 0) {
					// add photo
					request.setAttribute("upload", Integer.toString(fileindex));
					String lead = (cform.getClaim().getId() > 0 ? String.valueOf(cform.getClaim().getId()) : "");
					Attachment a=FileShareUtils.uploadFile(cform, lead, claim, user, errors, remote);
					if(a!=null){
						cform.getClaim().getAttachments().add(a);
					} else {
						ActionMessage error = new ActionMessage("error.fs.attachment.error");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
					}
					
				} 
			}
		}
		
		if (claim.getClaimants().size() > 1) {
			request.setAttribute("showNames", "true");
		}
		
		if (claim.getReceipts().size() > 0) {
			request.setAttribute("showReceipts", "true");
		}

		if (claim.getPhones().size() > 0) {
			request.setAttribute("showPhones", "true");
		}
		
		if (claim.getIpAddresses().size() > 0) {
			request.setAttribute("showIpAddresses", "true");
		}
		
		if (claim.getSegments().size() > 0) {
			request.setAttribute("showSegments", "true");
		}
		
		String showNames = request.getParameter("showNames");
		if (showNames != null) {
			request.setAttribute("showNames", showNames);
		}

		String showReceipts = request.getParameter("showReceipts");
		if (showReceipts != null) {
			request.setAttribute("showReceipts", showReceipts);
		}
		
		String showPhones = request.getParameter("showPhones");
		if (showPhones != null) {
			request.setAttribute("showPhones", showPhones);
		}
		
		String showIpAddresses = request.getParameter("showIpAddresses");
		if (showIpAddresses != null) {
			request.setAttribute("showIpAddresses", showIpAddresses);
		}
		
		String showSegments = request.getParameter("showSegments");
		if (showSegments != null) {
			request.setAttribute("showSegments", showSegments);
		}

		cform = ClaimUtils.createClaimForm(request);
		
		// save the claim
		if (request.getParameter("save") != null) {
			
			// 1. save the claim locally
			claim = cform.getClaim();
			deleteEmptyNames(claim);

			boolean firstSave = claim.getId() == 0;
			boolean claimSaved=false;
			if(validateAirlineIncidentId(claim.getIncident().getAirlineIncidentId(), errors, request, claim.getIncident().getId())) {//checks for fs_incident on local database, return turn is incidentid isn't in the table - airlineIncMark
				claimSaved = FileDAO.saveFile(claim.getFile(), firstSave);
			}
			if (claimSaved) {
				claim = ClaimDAO.loadClaim(claim.getId());
				ClaimHistoryObject CHO=new ClaimHistoryObject();
				CHO.setClaim(claim);
				CHO.setObjectID(String.valueOf(claim.getId()));
				CHO.setLinkURL("claim_resolution.do?claimId=");
				CHO.setStatusDesc(TracingConstants.HIST_DESCRIPTION_SAVE+" "+TracingConstants.HIST_DESCRIPTION_CLAIM);
				CHO.setObjectType(TracingConstants.HIST_DESCRIPTION_CLAIM);
				HistoryUtils.AddToHistoryContainer(session, CHO, null);
			}
			
			ClaimUtils.enterAuditClaimEntry(user.getAgent_ID(), TracingConstants.FS_AUDIT_ITEM_TYPE_FILE, (claim.getFile()!= null?claim.getFile().getId():-1), TracingConstants.FS_ACTION_SAVE);
			
			// maintain existing nt functionality
			if (isNtUser) {
				if (claimSaved) {
					if (theform.getClaims() == null) {
						theform.setClaims(new LinkedHashSet<Claim>());
					}
					
					if (theform.getClaims().contains(claim)) {
						theform.getClaims().remove(claim);
					}
					theform.getClaims().add(claim);
					request.setAttribute("success", "1");
					cform.setMod_claim_reason("");
				} else {
					request.setAttribute("fail", "1");
				}
			}
			
			
			if (ntfsUser && claimSaved ) {
				// 2. save the claim on central services
				Context ctx = null;
				ClaimClientRemote remote = null;
				try {
					ctx = ConnectionUtil.getInitialContext();
					remote = (ClaimClientRemote) ConnectionUtil.getRemoteEjb(ctx, PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME));
				} catch (Exception ex) {
					logger.error(ex);
				}
				
				long remoteFileId = 0;
				if (remote == null) {
					ActionMessage error = new ActionMessage("error.fs.could.not.communicate");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				} else {
					
					
					LinkedHashSet<FsClaim> fsClaims = new LinkedHashSet<FsClaim>();
					
					if (file == null) {
						file = claim.getFile();
					}
					
//					
					for (FsClaim current: file.getClaims()) {
						
						FsClaim newClaim = new FsClaim();
						BeanUtils.copyProperties(newClaim, current);
						LinkedHashSet<Segment> segs = new LinkedHashSet<Segment>();
						newClaim.setSegments(segs);
						newClaim.setCreateagent(current.getCreateagent());
						LinkedHashSet<Person> pers = new LinkedHashSet<Person>();
						newClaim.setClaimants(pers);

						LinkedHashSet<Attachment> attachs = new LinkedHashSet<Attachment>();
						newClaim.setAttachments(attachs);
						
						for (Person p: current.getClaimants()) {
							p.setClaim(newClaim);
							pers.add(p);
						}
						
						for(Attachment attach:claim.getAttachments()){
							attach.setClaim(newClaim);
							attachs.add(attach);
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
						
						LinkedHashSet<FsIPAddress> ipaddresses = new LinkedHashSet<FsIPAddress>();
						newClaim.setIpAddresses(ipaddresses);
						for(FsIPAddress ipaddress: current.getIpAddresses()){
							ipaddress.setClaim(newClaim);
							ipaddresses.add(ipaddress);
						}
						
						LinkedHashSet<Phone> phones = new LinkedHashSet<Phone>();
						newClaim.setPhones(phones);
						for(Phone phone: current.getPhones()){
							phone.setClaim(newClaim);
							phones.add(phone);
						}
						
						file.setStatusId(claim.getStatusId());
						newClaim.setFile(file);
						file.setIncident(newClaim.getIncident());
						newClaim.getIncident().setFile(file);
						fsClaims.add(newClaim);
					}
					
					file.setClaims(fsClaims);
					remoteFileId = remote.insertFile(TransportMapper.map(file));
					if(remoteFileId==-2){
						ActionMessage error = new ActionMessage("error.fs.id");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
					}
					claim = ClaimDAO.loadClaim(claim.getId());
					if (remoteFileId > 0) {
						List<Integer> attachids=new ArrayList();
						for(Attachment attach:claim.getAttachments()){
							attachids.add(attach.getAttachment_id());
						}
						remote.saveAttachments(attachids, file.getSwapId(), claim.getAirline(), claim.getId());
						
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
						response.sendRedirect("fraud_results.do?claimId=" + claim.getId());
						if (ctx != null) {
							ctx.close();
						}
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
		} 
		
		
		
		cform.setClaim(claim);
		session.setAttribute("incidentForm", theform);
		return (mapping.findForward(TracingConstants.CLAIM_PAY_MAIN));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void deleteAssociatedItems(FsClaim claim, HttpServletRequest request) {
		deleteAssociatedElements(new ArrayList(claim.getClaimants()), claim.getClaimants(), TracingConstants.JSP_DELETE_ASSOCIATED_NAME, request);
		deleteAssociatedElements(new ArrayList(claim.getReceipts()), claim.getReceipts(), TracingConstants.JSP_DELETE_ASSOCIATED_RECEIPT, request);	
		deleteAssociatedElements(new ArrayList(claim.getIpAddresses()), claim.getIpAddresses(), TracingConstants.JSP_DELETE_IP_ADDRESS, request);
		deleteAssociatedElements(new ArrayList(claim.getPhones()), claim.getPhones(), TracingConstants.JSP_DELETE_PHONE, request);	
		deleteAssociatedElements(new ArrayList(claim.getSegments()), claim.getSegments(), TracingConstants.JSP_DELETE_SEGMENT, request);
	}
	
	private void addAssociatedItems(FsClaim claim, HttpServletRequest request, Agent user) {
		addAssociatedNames(claim, request, user);
		addAssociatedReceipts(claim, request, user);
		addIPAddresses(claim, request, user);
		addPhones(claim, request, user);
		addSegments(claim, request, user);
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
	
	private void addAssociatedNames(FsClaim claim, HttpServletRequest request, Agent user) {
		if (request.getParameter("addNames") != null) {
			try {
				int numToAdd = Integer.parseInt(request.getParameter("addNameNum"));
				for (int i = 0; i < numToAdd; ++i) {
					Person p = new Person();
					p.setClaim(claim);
					
					FsAddress address = new FsAddress();
					address.setPerson(p);
					LinkedHashSet<FsAddress> addresses = new LinkedHashSet<FsAddress>();
					addresses.add(address);
					p.setAddresses(addresses);
					
					p.setPhones(new LinkedHashSet<Phone>());
					p.setDateFormat(user.getDateformat().getFormat());
					
					claim.getClaimants().add(p);
				}
				request.setAttribute("an", 1);
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
				request.setAttribute("rs", 1);
			} catch (NumberFormatException nfe) {
				logger.error(nfe);
			}
		}
	}
	
	private void addPhones(FsClaim claim, HttpServletRequest request, Agent user) {
		if (request.getParameter("addPhones") != null) {
			try {
				int numToAdd = Integer.parseInt(request.getParameter("addPhoneNum"));
				for (int i = 0; i < numToAdd; ++i) {
					Phone p = new Phone();
					p.setClaim(claim);
					claim.getPhones().add(p);
				}
				request.setAttribute("ph", 1);
			} catch (NumberFormatException nfe) {
				logger.error(nfe);
			}
		}
	}
	
	private void addIPAddresses(FsClaim claim, HttpServletRequest request, Agent user) {
		if (request.getParameter("addIPs") != null) {
			try {
				int numToAdd = Integer.parseInt(request.getParameter("addIPNum"));
				for (int i = 0; i < numToAdd; ++i) {
					FsIPAddress ip = new FsIPAddress();
					ip.setClaim(claim);
					claim.getIpAddresses().add(ip);
				}
				request.setAttribute("aip", 1);
			} catch (NumberFormatException nfe) {
				logger.error(nfe);
			}
		}
	}
	
	private void addSegments(FsClaim claim, HttpServletRequest request, Agent user) {
		if (request.getParameter("addSegs") != null) {
			try {
				int numToAdd = Integer.parseInt(request.getParameter("addSegNum"));
				for (int i = 0; i < numToAdd; ++i) {
					Segment seg = new Segment();
					seg.setClaim(claim);
					seg.setDateFormat(user.getDateformat().getFormat());
					claim.getSegments().add(seg);
				}
				request.setAttribute("aseg", 1);
			} catch (NumberFormatException nfe) {
				logger.error(nfe);
			}
		}
	}

	private void deleteEmptyNames(FsClaim claim) {
		ArrayList<Person> people = new ArrayList<Person>(claim.getClaimants());
		for (Person p: people) {
			if (p.isEmpty()) {
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
	
	private boolean validateAirlineIncidentId(String IncidentID, ActionMessages errors, HttpServletRequest request, Long FSID){
		if(IncidentID==null || IncidentID.isEmpty()){
			return true;
		}
		String sql = "select fsi.id from aero.nettracer.fs.model.FsIncident fsi where airlineIncidentId= :incident_id"; //airlineIncMark
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			q.setParameter("incident_id", IncidentID);
			List result = q.list();
			
			if(result.isEmpty()){
				return true;
			} else if(FSID==0 || !FSID.equals(((Long) result.get(0)))) {
				String errorsql = "select id from aero.nettracer.fs.model.FsClaim where incident_id= :incident_id"; //airlineIncMark
				
				Query q2 = sess.createQuery(sql);
				q2.setParameter("incident_id", result.get(0));
				List errorResult = q.list();
				request.setAttribute("validateAirline", (Long) errorResult.get(0));
				ActionMessage error = new ActionMessage("error.airlineincident");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				
			} else {
				return true;
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
			
		}
		
		return false;
	}
	
}