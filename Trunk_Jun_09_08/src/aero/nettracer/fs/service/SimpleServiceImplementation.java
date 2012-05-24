package aero.nettracer.fs.service;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.naming.Context;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.actions.FraudResultsAction;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.ClaimDAO;
import com.bagnet.nettracer.tracing.dao.FileDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.forms.ClaimForm;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.ntfs.ConnectionUtil;

import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.FsReceipt;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;
import aero.nettracer.fs.model.Segment;
import aero.nettracer.fs.model.detection.TraceResponse;
import aero.nettracer.fs.service.SubmitClaimDocument.SubmitClaim;
import aero.nettracer.fs.service.SubmitClaimResponseDocument.SubmitClaimResponse;
import aero.nettracer.fs.service.objects.xsd.Address;
import aero.nettracer.fs.service.objects.xsd.File;
import aero.nettracer.fs.service.objects.xsd.Receipt;
import aero.nettracer.fs.service.objects.xsd.Reservation;
import aero.nettracer.fs.service.objects.xsd.SimpleResponse;
import aero.nettracer.general.services.GeneralServiceBean;
import aero.nettracer.selfservice.fraud.ClaimRemote;

public class SimpleServiceImplementation extends SimpleServiceSkeleton {
    
    /**
     * Auto generated method signature
     * 
     * @param submitClaim
     */
	public aero.nettracer.fs.service.SubmitClaimResponseDocument submitClaim (aero.nettracer.fs.service.SubmitClaimDocument submitClaim) {
		SubmitClaim claim = submitClaim.getSubmitClaim();
		File file = claim.getData();
		
		SubmitClaimResponseDocument resDoc = SubmitClaimResponseDocument.Factory.newInstance();
		SubmitClaimResponse claimRes = resDoc.addNewSubmitClaimResponse();
		SimpleResponse res = claimRes.addNewReturn();
		
		createClaim(file, res);
		
		return resDoc;
    }
 
    /**
     * Auto generated method signature
     * 
     * @param updateClaimStatus
     */
    public aero.nettracer.fs.service.UpdateClaimStatusResponseDocument updateClaimStatus (aero.nettracer.fs.service.UpdateClaimStatusDocument updateClaimStatus) {
    	//TODO : fill this with the necessary business logic
    	throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#updateClaimStatus");
    }
    
    private void createClaim(File wsFile, SimpleResponse res) {

    	GeneralServiceBean bean = new GeneralServiceBean();
    	Agent user = bean.getAgent("webagent", TracerProperties.get("wt.company.code"));

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
		
		copyWStoNTFile(wsFile, file, claim);
		
		// end edit claim with WebService info
		
		// save the claim
		
////////// 1. save the claim locally
		boolean firstSave = claim.getId() == 0;
		boolean claimSaved = FileDAO.saveFile(claim.getFile(), firstSave);
		if (claimSaved) {
			claim = ClaimDAO.loadClaim(claim.getId());
		}
			
		ClaimUtils.enterAuditClaimEntry(user.getAgent_ID(), TracingConstants.FS_AUDIT_ITEM_TYPE_FILE, (claim.getFile()!= null?claim.getFile().getId():-1), TracingConstants.FS_ACTION_SAVE);
						
		if (ntfsUser) {
////////////// 2. save the claim on central services
			Context ctx = null;
			ClaimRemote remote = null;
			try {
				ctx = ConnectionUtil.getInitialContext();
				remote = (ClaimRemote) ConnectionUtil.getRemoteEjb(ctx, PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME));
			} catch (Exception e) {
				//logger.error(e);
			}
			
			long remoteFileId = 0;
			if (remote == null) {
				res.setSearchSummary("There was an error connecting to the fraud service.");
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
				remoteFileId = remote.insertFile(file);
				claim = ClaimDAO.loadClaim(claim.getId());
				if (remoteFileId > 0) {
					claim.getFile().setSwapId(remoteFileId);
					FileDAO.saveFile(claim.getFile(), false);
				}
				//logger.info("Claim saved to central services: " + remoteFileId);
					
////////////////// 3. submit the claim for tracing
				boolean hasViewFraudResultsPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_VIEW_FRAUD_RESULTS, user);
				TraceResponse results = ConnectionUtil.submitClaim(remoteFileId, firstSave, hasViewFraudResultsPermission);
				ClaimUtils.enterAuditClaimEntry(user.getAgent_ID(), TracingConstants.FS_AUDIT_ITEM_TYPE_FILE, (claim.getFile()!=null?claim.getFile().getId():-1), TracingConstants.FS_ACTION_SUBMIT);
				if (hasViewFraudResultsPermission && results != null) {
					res.setSearchSummary(results.getMetaSummary());
					res.setWarningColor(results.getThreatLevel());
					res.setWarningLevel(results.getThreatLevel());
				}
					
				if (ctx != null) {
					try {
						ctx.close();
					} catch (Exception e) {
						
					}
				}
			} // END REMOTE CALLS
		} // END NTFS_USER SECTION
    } // END METHOD createClaim
    
    private void copyWStoNTFile(File wsFile, aero.nettracer.fs.model.File file, FsClaim claim) {
    	aero.nettracer.fs.service.objects.xsd.Claim wsClaim = wsFile.getClaim();
    	aero.nettracer.fs.service.objects.xsd.Incident wsIncident = wsFile.getIncident();
    	Reservation wsRes = wsFile.getReservation();

    	// POPULATE CLAIM FROM WS
    	claim.setAirline(wsClaim.getAirline());
    	claim.setAirlineClaimId(wsClaim.getAirlineClaimId());
    	claim.setAmountClaimed(wsClaim.getAmountClaimed());
    	claim.setAmountClaimedCurrency(wsClaim.getAmountClaimedCurrency());
    	claim.setAmountPaid(wsClaim.getAmountPaid());
//    	claim.setAmountPaidCurrency(wsClaim.getAmountPaidCurrency());
    	claim.setClaimDate(wsClaim.getClaimDate().getTime());
    	claim.setClaimType(wsClaim.getClaimType());
    	claim.setTravelDate(wsClaim.getTravelDate().getTime());
    	claim.setClaimants(copyWStoNTPass(wsClaim.getClaimantsArray(), null, null, claim));
    	claim.setSegments(copyWStoNTSeg(wsClaim.getSegmentsArray(), null, null, claim));
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
    	// END POPULATE CLAIM FROM WS
    	
    	// POPULATE INCIDENT FROM WS
    	FsIncident incident = file.getIncident();
    	incident.setAirline(wsIncident.getAirline());
    	incident.setAirlineIncidentId(wsIncident.getAirlineIncidentId());
    	incident.setIncidentCreated(wsIncident.getIncidentCreated().getTime());
    	incident.setTimestampClosed(wsIncident.getIncidentClosed().getTime());
    	incident.setIncidentDescription(wsIncident.getIncidentDescription());
    	incident.setTimestampOpen(wsIncident.getIncidentOpened().getTime());
    	incident.setIncidentType(wsIncident.getIncidentType());
    	incident.setNumberDaysOpen(wsIncident.getNumberDaysOpen());
    	incident.setNumberOfBdos(wsIncident.getNumberOfBdosCreated());
    	incident.setPassengers(copyWStoNTPass(wsIncident.getClaimantsArray(), incident, null, null));
    	incident.setSegments(copyWStoNTSeg(wsIncident.getSegmentsArray(), incident, null, null));    	
    	// END POPULATE INCIDENT FROM WS
    	
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
    	// END POPULATE RESERVATION FROM WS
    	
    }
    
    private Set<Person> copyWStoNTPass(aero.nettracer.fs.service.objects.xsd.Person[] wsPasgrs, FsIncident inc, aero.nettracer.fs.model.Reservation res, FsClaim claim) {
    	Set<Person> pasgrs = new LinkedHashSet<Person>();
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
    		pass.setDateOfBirth(wsPass.getDateOfBirth().getTime());
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
    		
    		Set<Phone> phones = new LinkedHashSet<Phone>();
    		for (aero.nettracer.fs.service.objects.xsd.Phone wsPhone : wsPass.getPhoneNumbersArray()) {
    			Phone phone = new Phone();
    			phone.setPerson(pass);
    			phone.setPhoneNumber(wsPhone.getPhoneNumber());
    			phone.setType(wsPhone.getType());
    			phones.add(phone);
    		}
    		pass.setPhones(phones);
    		
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
    		pasgrs.add(pass);
    	}
    	return pasgrs;
    }
    
    private Set<Segment> copyWStoNTSeg(aero.nettracer.fs.service.objects.xsd.Segment[] wsSegs, FsIncident inc, aero.nettracer.fs.model.Reservation res, FsClaim claim) {
    	Set<Segment> segments = new LinkedHashSet<Segment>();
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
    	return segments;
    }
}
