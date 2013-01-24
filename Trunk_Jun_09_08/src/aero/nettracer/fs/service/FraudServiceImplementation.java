package aero.nettracer.fs.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.naming.Context;

import org.apache.commons.beanutils.BeanUtils;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.ClaimDAO;
import com.bagnet.nettracer.tracing.dao.FileDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.ntfs.ConnectionUtil;

import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIPAddress;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.FsReceipt;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;
import aero.nettracer.fs.model.Segment;
import aero.nettracer.fs.model.detection.MetaWarning;
import aero.nettracer.fs.model.detection.TraceResponse;
import aero.nettracer.fs.service.GetClaimResultsResponseDocument.GetClaimResultsResponse;
import aero.nettracer.fs.service.SubmitClaimDocument.SubmitClaim;
import aero.nettracer.fs.service.SubmitClaimResponseDocument.SubmitClaimResponse;
import aero.nettracer.fs.service.UpdateClaimStatusResponseDocument.UpdateClaimStatusResponse;
import aero.nettracer.fs.service.objects.xsd.Address;
import aero.nettracer.fs.service.objects.xsd.Authentication;
import aero.nettracer.fs.service.objects.xsd.File;
import aero.nettracer.fs.service.objects.xsd.IpAddress;
import aero.nettracer.fs.service.objects.xsd.Receipt;
import aero.nettracer.fs.service.objects.xsd.Reservation;
import aero.nettracer.fs.service.objects.xsd.ClaimResponse;
import aero.nettracer.fs.utilities.TransportMapper;
import aero.nettracer.selfservice.fraud.client.ClaimClientRemote;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;

public class FraudServiceImplementation extends FraudServiceSkeleton {
    
	private static Logger logger = Logger.getLogger(FraudServiceImplementation.class);
	
	private HashMap<Integer,String> statusMap = null;
	
	
	private boolean validStatusId(int status){
		if(statusMap == null){
			statusMap = new HashMap<Integer,String>();
			List<Status> list = StatusBMO.getStatuses(TracingConstants.STATUS_TABLE_ID_FS);
			if(list != null){
				for(Status s:list){
					statusMap.put(s.getStatus_ID(), s.getDescription());
				}
			}
		}
		return statusMap.containsKey(status);
	}
	
	private String getStatusDescriptions(){
		if(statusMap == null || statusMap.size() == 0){
			return "No valid status codes found, please contact NetTracer";
		}
		String ret = "";
		for(Integer i:statusMap.keySet()){
			ret += i + ":" + statusMap.get(i) + "||";
		}
		return ret;
	}
	
	public aero.nettracer.fs.service.GetClaimResultsResponseDocument getClaimResults (aero.nettracer.fs.service.GetClaimResultsDocument req){
		GetClaimResultsResponseDocument resDoc = GetClaimResultsResponseDocument.Factory.newInstance();
		GetClaimResultsResponse resultRes = resDoc.addNewGetClaimResultsResponse();
		ClaimResponse res = resultRes.addNewReturn();
		
		res.setSuccess(true);
		Agent agent = null;
		if(req != null && req.getGetClaimResults() != null && req.getGetClaimResults().getAuthentication() != null){
			Authentication auth = req.getGetClaimResults().getAuthentication();
			ActionMessages errors = new ActionMessages();
			agent = SecurityUtils.authUser(auth.getSystemName(), auth.getSystemPassword(), auth.getAirlineCode(), 1, errors);
			if(!errors.isEmpty()){
				res.addError("Incorrect username/password");
				res.setSuccess(false);
				logger.info(resDoc);
				return resDoc;
			}
		} else {
			res.addError("username/password not provided");
			res.setSuccess(false);
			logger.info(resDoc);
			return resDoc;
		}
		
		//Get File swapID
		FsClaim claim = ClaimUtils.loadClaim(req.getGetClaimResults().getFileId());
		if(claim == null){
			res.addError("File ID invalid");
			res.setSuccess(false);
			return resDoc;
		}
		if(claim.getFile() == null || claim.getFile().getSwapId() == 0){
			res.addError("File failed to submit to FS service, please contact NetTracer");
			res.setSuccess(false);
			return resDoc;
		}
		ClaimUtils.enterAuditClaimEntry(agent.getAgent_ID(), TracingConstants.FS_AUDIT_ITEM_TYPE_MATCH_HISTORY, (claim.getFile()!=null?claim.getFile().getId():-1), TracingConstants.FS_ACTION_LOAD);
		Context ctx = null;
		ClaimClientRemote remote = null;
		try {
			ctx = ConnectionUtil.getInitialContext();
			remote = (ClaimClientRemote) ConnectionUtil.getRemoteEjb(ctx, PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME));
		} catch (Exception e) {
			//logger.error(e);
		}
		
		if (remote == null) {
			res.addError("There was an error connecting to the fraud service.");
			res.setSuccess(false);
			return resDoc;
		} else {
			TraceResponse results = (TraceResponse) TransportMapper.map(remote.getFileMatches(claim.getFile().getSwapId()));
			boolean hasViewFraudResultsPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_VIEW_FRAUD_RESULTS, agent);
			if (hasViewFraudResultsPermission && results != null) {
				processTraceResponse(res,results);
			}
			return resDoc;
		}
	}
	
    /**
     * Auto generated method signature
     * 
     * @param submitClaim
     */
	public aero.nettracer.fs.service.SubmitClaimResponseDocument submitClaim (aero.nettracer.fs.service.SubmitClaimDocument submitClaim) {
		logger.info(submitClaim);
		
		SubmitClaimResponseDocument resDoc = SubmitClaimResponseDocument.Factory.newInstance();
		SubmitClaimResponse claimRes = resDoc.addNewSubmitClaimResponse();
		ClaimResponse res = claimRes.addNewReturn();
		res.setSuccess(true);
		Agent agent = null;
		
		if(submitClaim != null && submitClaim.getSubmitClaim() != null && submitClaim.getSubmitClaim().getAuthentication() != null){
			Authentication auth = submitClaim.getSubmitClaim().getAuthentication();
			ActionMessages errors = new ActionMessages();
			agent = SecurityUtils.authUser(auth.getSystemName(), auth.getSystemPassword(), auth.getAirlineCode(), 1, errors);
			if(!errors.isEmpty()){
				res.addError("Incorrect username/password");
				res.setSuccess(false);
				logger.info(resDoc);
				return resDoc;
			}
		} else {
			res.addError("username/password not provided");
			res.setSuccess(false);
			logger.info(resDoc);
			return resDoc;
		}

		
		SubmitClaim claim = submitClaim.getSubmitClaim();
		File file = claim.getData();
		
		createClaim(file, res, agent, submitClaim.getSubmitClaim().getMaxWaitTime());
		logger.info(resDoc);
		return resDoc;
    }
 
    /**
     * Auto generated method signature
     * 
     * @param updateClaimStatus
     */
    public aero.nettracer.fs.service.UpdateClaimStatusResponseDocument updateClaimStatus (aero.nettracer.fs.service.UpdateClaimStatusDocument updateClaimStatus) {
    	logger.info(updateClaimStatus);
    	UpdateClaimStatusResponseDocument resDoc = UpdateClaimStatusResponseDocument.Factory.newInstance();
    	UpdateClaimStatusResponse claimRes = resDoc.addNewUpdateClaimStatusResponse();
		ClaimResponse res = claimRes.addNewReturn();
		res.setSuccess(true);
    	
    	Agent agent = null;
    	if(updateClaimStatus != null && updateClaimStatus.getUpdateClaimStatus() != null && updateClaimStatus.getUpdateClaimStatus().getAuthentication() != null){
			Authentication auth = updateClaimStatus.getUpdateClaimStatus().getAuthentication();
			ActionMessages errors = new ActionMessages();
			agent = SecurityUtils.authUser(auth.getSystemName(), auth.getSystemPassword(), auth.getAirlineCode(), 1, errors);
			if(!errors.isEmpty()){
				res.addError("Incorrect username/password");
				res.setSuccess(false);
				logger.info(resDoc);
				return resDoc;
			}
		} else {
			res.addError("username/password not provided");
			res.setSuccess(false);
			logger.info(resDoc);
			return resDoc;
    	}
    	
    	long claimId = updateClaimStatus.getUpdateClaimStatus().getFileId();//Even though the wsdl states file id, it is really the claim id
    	Claim claim = ClaimDAO.loadClaim(claimId);
    	if(claim == null){
    		res.addError("file " + claimId + " not found");
			res.setSuccess(false);
    		logger.info(resDoc);
    		return resDoc;
    	}
    	
    	Double amountPaid = updateClaimStatus.getUpdateClaimStatus().getAmountPaid();
    	String amountPaidCurrency = updateClaimStatus.getUpdateClaimStatus().getAmountPaidCurrency();
    	int statusId = updateClaimStatus.getUpdateClaimStatus().getResolutionStatus();
    	
    	if(amountPaidCurrency == null || amountPaidCurrency.trim().length() == 0){
//    		res.setSearchSummary("must provide currency type");
//    		return resDoc;
    		
    		//The wsdl states that this is an optional field, so if no currency is provided, use previous
    		amountPaidCurrency = claim.getAmountPaidCurrency();
    	}
    	
    	if(!validStatusId(statusId)){
    		res.addError("" + statusId +" is not a valid status ID.  Please use one of the following: " + getStatusDescriptions());
			res.setSuccess(false);
    		logger.info(resDoc);
    		return resDoc;
    	}
    	
    	claim.setAmountPaid(amountPaid);
    	claim.setAmountPaidCurrency(amountPaidCurrency);
    	claim.setStatusId(statusId);
    	
		boolean firstSave = claim.getId() == 0;
		boolean claimSaved = FileDAO.saveFile(claim.getFile(), firstSave);
		if (claimSaved) {
			claim = ClaimDAO.loadClaim(claim.getId());
		} else {
			//TODO handle error
			res.addError("There was an error saving this claim");
			res.setSuccess(false);
			logger.info(resDoc);
			return resDoc;
		}
		
		res.setFileId(claim.getId());
		ClaimUtils.enterAuditClaimEntry(agent.getAgent_ID(), TracingConstants.FS_AUDIT_ITEM_TYPE_FILE, (claim.getFile()!= null?claim.getFile().getId():-1), TracingConstants.FS_ACTION_SAVE);
		
		boolean ntfsUser = PropertyBMO.isTrue("ntfs.user");
		if (claimSaved && ntfsUser) {
			submitClaimToFs(claim, firstSave, res, agent, updateClaimStatus.getUpdateClaimStatus().getMaxWaitTime());
		}
		logger.info(resDoc);
    	return resDoc;
    }
    
    private void createClaim(File wsFile, ClaimResponse res, Agent user, int maxWait) {
    	
    	//TODO required field validation
 
		boolean ntfsUser = PropertyBMO.isTrue("ntfs.user");

		// create a new claim
		FsClaim claim = ClaimUtils.createClaim(user);
			
		aero.nettracer.fs.model.File file = new aero.nettracer.fs.model.File();
		file.setValidatingCompanycode(user.getCompanycode_ID());
		file.setClaims(new LinkedHashSet<FsClaim>());
			
		file.getClaims().add(claim);
		claim.setFile(file);
			
		file.setIncident(claim.getIncident());
		claim.getIncident().setFile(file);
		// end create new claim
		
		// edit claim with WebService info
		
		if (!copyWStoNTFile(wsFile, file, claim, res)) {
			return;
		}
		
		// end edit claim with WebService info
		
		// save the claim
		
////////// 1. save the claim locally
		boolean firstSave = claim.getId() == 0;
		boolean claimSaved = FileDAO.saveFile(claim.getFile(), firstSave);
		if (claimSaved) {
			claim = ClaimDAO.loadClaim(claim.getId());
		} else {
			//TODO handle error
			res.addError("There was an error saving this claim");
			res.setSuccess(false);
			return;
		}
		
		res.setFileId(claim.getId());
		ClaimUtils.enterAuditClaimEntry(user.getAgent_ID(), TracingConstants.FS_AUDIT_ITEM_TYPE_FILE, (claim.getFile()!= null?claim.getFile().getId():-1), TracingConstants.FS_ACTION_SAVE);
		
		if (claimSaved && ntfsUser) {
			submitClaimToFs(claim, firstSave, res, user, maxWait);
		}
    } // END METHOD createClaim
    
    
    private void submitClaimToFs(FsClaim claim, boolean firstSave, ClaimResponse res, Agent user, int maxWait){
		Context ctx = null;
		ClaimClientRemote remote = null;
		aero.nettracer.fs.model.File file = claim.getFile();
		try {
			ctx = ConnectionUtil.getInitialContext();
			remote = (ClaimClientRemote) ConnectionUtil.getRemoteEjb(ctx, PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME));
		} catch (Exception e) {
			//logger.error(e);
		}
		
		long remoteFileId = 0;
		if (remote == null) {
			res.addError("There was an error connecting to the fraud service.");
			res.setSuccess(false);
			return;
		} else {
			LinkedHashSet<FsClaim> fsClaims = new LinkedHashSet<FsClaim>();
			
			for (FsClaim current: file.getClaims()) {
				FsClaim newClaim = new FsClaim();
				try {
					BeanUtils.copyProperties(newClaim, current);
				} catch (Exception e) {
						
				}
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
			remoteFileId = remote.insertFile(TransportMapper.map(file));
			claim = ClaimDAO.loadClaim(claim.getId());
			if (remoteFileId > 0) {
				claim.getFile().setSwapId(remoteFileId);
				FileDAO.saveFile(claim.getFile(), false);
			}
			//logger.info("Claim saved to central services: " + remoteFileId);
				
//////////////////3. submit the claim for tracing
			//sync 0 - default, 1-async, 2-sync
			boolean hasViewFraudResultsPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_VIEW_FRAUD_RESULTS, user);

			TraceResponse results = ConnectionUtil.submitClaim(remoteFileId, firstSave, hasViewFraudResultsPermission, maxWait);
			
			ClaimUtils.enterAuditClaimEntry(user.getAgent_ID(), TracingConstants.FS_AUDIT_ITEM_TYPE_FILE, (claim.getFile()!=null?claim.getFile().getId():-1), TracingConstants.FS_ACTION_SUBMIT);
			if (hasViewFraudResultsPermission && results != null) {
				processTraceResponse(res,results);
			}
				
			if (ctx != null) {
				try {
					ctx.close();
				} catch (Exception e) {
					
				}
			}
		} // END REMOTE CALLS
    }
    
    private void processTraceResponse(ClaimResponse res, TraceResponse results){
		ArrayList<String> s = new ArrayList<String>();
		for(MetaWarning mw:results.getMetaWarning()){
			String toAdd = mw.getDescription();
			toAdd = toAdd.replace("<ul>", " ");
			toAdd = toAdd.replace("</ul>", " ");
			toAdd = toAdd.replace("<li>", " ");
			toAdd = toAdd.replace("</li>", " ");
			s.add(toAdd);
		}
		res.setSearchSummary(StringUtils.join(s.toArray(), "||"));
		res.setWarningColor(results.getThreatLevel());
		res.setWarningLevel(results.getThreatLevel());
		String directAccessUrl = PropertyBMO.getValue(PropertyBMO.DIRECT_ACCESS_URL);
		if(res.getFileId() != 0 && directAccessUrl != null){
			res.setDirectAccessUrl(directAccessUrl + "fraud_results.do?claimId=" + res.getFileId());
		}
		res.setTraceComplete(results.isTraceComplete());
		if(!results.isTraceComplete()){
			res.setSecondsUntilComplete(results.getSecondsUntilReload());
		} else {
			res.setSecondsUntilComplete(0);
		}
    }
    
    private boolean copyWStoNTFile(File wsFile, aero.nettracer.fs.model.File file, FsClaim claim, ClaimResponse res) {
    	aero.nettracer.fs.service.objects.xsd.Claim wsClaim = wsFile.getClaim();
    	aero.nettracer.fs.service.objects.xsd.Incident wsIncident = wsFile.getIncident();
    	Reservation wsRes = wsFile.getReservation();

    	// POPULATE CLAIM FROM WS
    	if (wsClaim.getIpaddressArray() != null){
    		Set<FsIPAddress> ips = new LinkedHashSet<FsIPAddress>();
    		for (IpAddress wsIp: wsClaim.getIpaddressArray()) {
    			if (wsIp.getIpaddress() != null && wsIp.getIpaddress().matches("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")) {
    				FsIPAddress ip = new FsIPAddress();
    				ip.setClaim(claim);
    				ip.setIpAddress(wsIp.getIpaddress());
    				ip.setAssociation(wsIp.getAssociation());
    				ips.add(ip);
    			} else {
    	    		res.addError("" + wsIp.getIpaddress() +" is not a valid IP Address.");
    				res.setSuccess(false);
    	    		return false;
    			}
    		}
    		claim.setIpAddresses(ips);
    	}
    	
    	if(wsClaim.getPhonesArray() != null){
    		Set<Phone> phones = new LinkedHashSet<Phone>();
    		for(aero.nettracer.fs.service.objects.xsd.Phone wsphone:wsClaim.getPhonesArray()){
    			Phone fsphone = new Phone();
    			fsphone.setPhoneNumber(wsphone.getPhoneNumber());
    			fsphone.setType(wsphone.getType());
    			fsphone.setAssociation(wsphone.getAssociation());
    			fsphone.setClaim(claim);
    			phones.add(fsphone);
    		}
    		claim.setPhones(phones);
    	}
    	
    	claim.setAirline(wsClaim.getAirline());
    	claim.setAirlineClaimId(wsClaim.getAirlineClaimId());
    	claim.setAmountClaimed(wsClaim.getAmountClaimed());
    	claim.setAmountClaimedCurrency(wsClaim.getAmountClaimedCurrency());
    	claim.setAmountPaid(wsClaim.getAmountPaid());
    	claim.setAmountPaidCurrency(wsClaim.getAmountPaidCurrency());
    	Calendar c = Calendar.getInstance();
    	if(wsClaim.getClaimDate() != null){
    		c = wsClaim.getClaimDate();
    	}
    	c.setTimeZone(TimeZone.getTimeZone("GMT"));
		Calendar time = Calendar.getInstance();
		time.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 
				c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
    	claim.setClaimDate(time.getTime());

    	claim.setClaimType(wsClaim.getClaimType());
    	claim.setTravelDate(wsClaim.getTravelDate()!=null?wsClaim.getTravelDate().getTime():null);
    	claim.setClaimants(copyWStoNTPass(wsClaim.getClaimantsArray(), null, null, claim));
    	claim.setSegments(copyWStoNTSeg(wsClaim.getSegmentsArray(), null, null, claim));
    	
    	claim.setClaimRemark(wsClaim.getRemark());
    	
    	if(wsClaim.getReceiptsArray() != null){
    		Set<FsReceipt> receipts = new LinkedHashSet<FsReceipt>();
    		for (Receipt wsReceipt : wsClaim.getReceiptsArray()) {
    			FsReceipt receipt = new FsReceipt();
    			receipt.setClaim(claim);
    			receipt.setCcExpMonth(wsReceipt.getCcExpMonth());
    			receipt.setCcExpYear(wsReceipt.getCcExpYear());
    			receipt.setCcLastFour(wsReceipt.getCcLastFour());
    			//receipt.setCcLastFour(wsReceipt.getCcNumber()); There is no ccNumber in FsReceipt
    			receipt.setCcType(wsReceipt.getCcType());
    			receipt.setCompany(wsReceipt.getCompany());

    			aero.nettracer.fs.service.objects.xsd.Phone wsPhone = wsReceipt.getPhone();
    			Phone phone = new Phone();
    			phone.setReceipt(receipt);
    			phone.setPhoneNumber(wsPhone.getPhoneNumber());
    			phone.setType(wsPhone.getType());
    			receipt.setPhone(phone);

    			receipts.add(receipt);
    		}
    		claim.setReceipts(receipts);
    	}
    	// END POPULATE CLAIM FROM WS
    	
    	// POPULATE INCIDENT FROM WS
    	FsIncident incident = file.getIncident();
    	if(wsIncident!=null){
    		incident.setAirline(wsIncident.getAirline());
    		incident.setAirlineIncidentId(wsIncident.getAirlineIncidentId());
    		incident.setIncidentCreated(wsIncident.getIncidentCreated()!=null?wsIncident.getIncidentCreated().getTime():null);
    		incident.setTimestampClosed(wsIncident.getIncidentClosed()!=null?wsIncident.getIncidentClosed().getTime():null);
    		incident.setIncidentDescription(wsIncident.getIncidentDescription());
    		incident.setTimestampOpen(wsIncident.getIncidentOpened()!=null?wsIncident.getIncidentOpened().getTime():null);
    		incident.setIncidentType(wsIncident.getIncidentType());
    		incident.setNumberDaysOpen(wsIncident.getNumberDaysOpen());
    		incident.setNumberOfBdos(wsIncident.getNumberOfBdosCreated());
    		incident.setPassengers(copyWStoNTPass(wsIncident.getClaimantsArray(), incident, null, null));
    		incident.setSegments(copyWStoNTSeg(wsIncident.getSegmentsArray(), incident, null, null));    	
    		// END POPULATE INCIDENT FROM WS
    	}
    	if(wsRes != null){
    		// BEGIN POPULATE RESERVATION FROM WS
    		aero.nettracer.fs.model.Reservation reservation = new aero.nettracer.fs.model.Reservation();
    		reservation.setIncident(incident);
    		reservation.setCcExpMonth(wsRes.getCcExpMonth());
    		reservation.setCcExpYear(wsRes.getCcExpYear());
    		reservation.setCcNumber(wsRes.getCcNumber());
    		reservation.setCcNumLastFour(wsRes.getCcNumLastFour());
    		reservation.setCcType(wsRes.getCcType());
    		reservation.setFormOfPayment(wsRes.getFormOfPayment());
    		reservation.setRecordLocator(wsRes.getRecordLocator());
    		reservation.setPassengers(copyWStoNTPass(wsRes.getClaimantsArray(), null, reservation, null));
    		reservation.setSegments(copyWStoNTSeg(wsRes.getSegmentsArray(), null, reservation, null));
    		incident.setReservation(reservation);
    	}
    	// END POPULATE RESERVATION FROM WS
    	return true;
    }
    
    private Set<Person> copyWStoNTPass(aero.nettracer.fs.service.objects.xsd.Person[] wsPasgrs, FsIncident inc, aero.nettracer.fs.model.Reservation res, FsClaim claim) {
    	Set<Person> pasgrs = new LinkedHashSet<Person>();
    	if(wsPasgrs != null){
    		for (aero.nettracer.fs.service.objects.xsd.Person wsPass : wsPasgrs) {
    			Person pass = new Person();
    			if (inc != null) {
    				pass.setIncident(inc);
    			}
    			if (res != null) {
    				pass.setReservation(res);
    			}
    			if (claim != null) {
    				pass.setClaim(claim);
    			}
    			pass.setDateOfBirth(wsPass.getDateOfBirth()!=null?wsPass.getDateOfBirth().getTime():null);
    			pass.setDriversLicenseCountry(wsPass.getDriversLicenseCountry());
    			pass.setDriversLicenseNumber(wsPass.getDriversLicenseNumber());
    			pass.setDriversLicenseProvince(wsPass.getDriversLicenseProvince());
    			pass.setDriversLicenseState(wsPass.getDriversLicenseState());
    			pass.setEmailAddress(wsPass.getEmailAddress());
    			pass.setFfAirline(wsPass.getFfAirline());
    			pass.setFfNumber(wsPass.getFfNumber());
    			pass.setFirstName(wsPass.getFirstName());
    			pass.setLastName(wsPass.getLastName());
    			pass.setMiddleName(wsPass.getMiddleName());
    			pass.setPassportIssuer(wsPass.getPassportIssuer());
    			pass.setPassportNumber(wsPass.getPassportNumber());
    			pass.setSocialSecurity(wsPass.getSocialSecurity());

    			if(wsPass.getPhoneNumbersArray() != null){
    				Set<Phone> phones = new LinkedHashSet<Phone>();
    				for (aero.nettracer.fs.service.objects.xsd.Phone wsPhone : wsPass.getPhoneNumbersArray()) {
    					Phone phone = new Phone();
    					phone.setPerson(pass);
    					phone.setPhoneNumber(wsPhone.getPhoneNumber());
    					phone.setType(wsPhone.getType());
    					phones.add(phone);
    				}
    				pass.setPhones(phones);
    			}

    			if(wsPass.getAddressesArray() != null){
    				Set<FsAddress> addresses = new LinkedHashSet<FsAddress>();
    				for (Address wsAddr : wsPass.getAddressesArray()) {
    					FsAddress addr = new FsAddress();
    					addr.setPerson(pass);
    					addr.setAddress1(wsAddr.getAddress1());
    					addr.setAddress2(wsAddr.getAddress2());
    					addr.setCity(wsAddr.getCity());
    					addr.setCountry(wsAddr.getCountry());
    					addr.setProvince(wsAddr.getProvince());
    					addr.setState(wsAddr.getState());
    					addr.setZip(wsAddr.getZip());
    					addresses.add(addr);
    				}
    				pass.setAddresses(addresses);
    			}

    			pasgrs.add(pass);
    		}
    	}
    	return pasgrs;
    }
    
    private Set<Segment> copyWStoNTSeg(aero.nettracer.fs.service.objects.xsd.Segment[] wsSegs, FsIncident inc, aero.nettracer.fs.model.Reservation res, FsClaim claim) {
    	Set<Segment> segments = new LinkedHashSet<Segment>();
    	if(wsSegs != null){
    		for (aero.nettracer.fs.service.objects.xsd.Segment wsSeg : wsSegs) {
    			Segment seg = new Segment();
    			if (inc != null) {
    				seg.setIncident(inc);
    			}
    			if (res != null) {
    				seg.setReservation(res);
    			}
    			if (claim != null) {
    				seg.setClaim(claim);
    			}
    			seg.setAirline(wsSeg.getAirlineCode());
    			seg.setArrival(wsSeg.getArrivalCityCode());
    			seg.setDate(wsSeg.getDate().getTime());
    			seg.setDeparture(wsSeg.getDepartureCityCode());
    			seg.setFlight(wsSeg.getFlightNumber());
    			segments.add(seg);
    		}
    	}
    	return segments;
    }
    
}
