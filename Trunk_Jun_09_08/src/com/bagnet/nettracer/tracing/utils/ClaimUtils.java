package com.bagnet.nettracer.tracing.utils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import aero.nettracer.fs.model.Bag;
import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;
import aero.nettracer.fs.model.PnrData;
import aero.nettracer.fs.model.Reservation;
import aero.nettracer.fs.model.Segment;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.forms.ClaimForm;

public class ClaimUtils {
	
	private static Logger logger = Logger.getLogger(TracerUtils.class);

	public static Claim createClaim(Agent user) {
		return createClaim(user, null);
	}
	
	public static Claim createClaim(Agent user, Incident ntIncident) {
		
		// create the claim
		Claim claim = new Claim();
		claim.setAirline(user.getCompanycode_ID());
		
		// create the person
		Person person = new Person();
		
		// create the claim status
		Status status = new Status();
		status.setStatus_ID(TracingConstants.CLAIM_STATUS_INPROCESS);
		status.setLocale(user);
		
		// create the claim currency
		String currency = user.getDefaultcurrency();
		
		// create the address
		FsAddress address = new FsAddress();
		address.setPerson(person);
		LinkedHashSet<FsAddress> addresses = new LinkedHashSet<FsAddress>();
		addresses.add(address);
		
		// create the phones
		LinkedHashSet<Phone> phones = new LinkedHashSet<Phone>();
		
		// create the person
		person.setAddresses(addresses);
		person.setPhones(phones);
		person.setClaim(claim);
		LinkedHashSet<Person> claimants = new LinkedHashSet<Person>();
		claimants.add(person);
		
		// create the pnr data
		PnrData pnrData = new PnrData();
		
		// create the reservation
		Reservation reservation = new Reservation();
		reservation.setPassengers(new LinkedHashSet<Person>());
		reservation.setPhones(new LinkedHashSet<Phone>());
		reservation.setPnrData(pnrData);
		reservation.setSegments(new LinkedHashSet<Segment>());
		pnrData.setReservation(reservation);
		
		// set the fraud incident
		FsIncident fsIncident = new FsIncident();
		fsIncident.setReservation(reservation);
		reservation.setIncident(fsIncident);
		person.setIncident(fsIncident);
		fsIncident.setClaim(claim);
		fsIncident.setPassengers(claimants);
		
		// create the claim
		claim.setStatus(status);
		claim.setAmountClaimedCurrency(currency);
		claim.setClaimants(claimants);
		claim.setIncident(fsIncident);
		
		// set the tracing incident if we have one
		if (ntIncident != null) {
			claim.setClaimType(ntIncident.getItemtype_ID());
			claim.setNtIncident(ntIncident);
			ntIncident.setClaim(claim);
		}
		
		return claim;
		
	}
	
	public static FsClaim createFsClaim(Claim claim) {
		FsClaim fsClaim = new FsClaim();
		
		fsClaim.setId(claim.getId());
		fsClaim.setAirline(claim.getAirline());
		fsClaim.setClaimType(claim.getClaimType());
		fsClaim.setClaimDate(claim.getClaimDate());
		fsClaim.setTravelDate(claim.getTravelDate());
		fsClaim.setAmountClaimed(claim.getAmountClaimed());
		fsClaim.setAmountClaimedCurrency(claim.getAmountClaimedCurrency());
		fsClaim.setAmountPaid(claim.getAmountPaid());
		fsClaim.setFraudStatus(claim.getFraudStatus());
		fsClaim.setDenied(claim.isDenied());
		fsClaim.setPrivateReasonForDenial(claim.getPrivateReasonForDenial());
		fsClaim.setPublicReasonForDenial(claim.getPublicReasonForDenial());
		fsClaim.setNtIncidentId(claim.getNtIncidentId());
		fsClaim.setClaimProrateId(claim.getClaimProrateId());
		fsClaim.setStatusId(claim.getStatusId());
		fsClaim.setBlacklist(claim.getBlacklist());
		
		FsIncident incident = cloneFsIncident(claim);
		incident.setClaim(fsClaim);
		fsClaim.setIncident(incident);

		LinkedHashSet<Person> claimants = new LinkedHashSet<Person>();
		if (claim.getClaimants() != null) {
			claimants.addAll(claim.getClaimants());
			for (Person p: claimants) {
				p.setClaim(fsClaim);
			}
		}
		fsClaim.setClaimants(claimants);		
		
		LinkedHashSet<Segment> segments = new LinkedHashSet<Segment>();
		if (claim.getSegments() != null) {
			segments.addAll(claim.getSegments());
			for (Segment s: segments) {
				s.setClaim(fsClaim);
			}
		}
		fsClaim.setSegments(segments);
		
		return fsClaim;
	}
	
	private static FsIncident cloneFsIncident(FsClaim claim) {
		FsIncident fsIncident = new FsIncident();
		FsIncident oldIncident = claim.getIncident();
		
		fsIncident.setId(oldIncident.getId());
		fsIncident.setAirlineIncidentId(oldIncident.getAirlineIncidentId());
		fsIncident.setIncidentCreated(oldIncident.getIncidentCreated());
		fsIncident.setIncidentType(oldIncident.getIncidentType());
		fsIncident.setNumberOfBdos(oldIncident.getNumberOfBdos());
		fsIncident.setNumberDaysOpen(oldIncident.getNumberDaysOpen());
		fsIncident.setTimestampOpen(oldIncident.getTimestampOpen());
		fsIncident.setTimestampClosed(oldIncident.getTimestampClosed());
		fsIncident.setItinComplexity(oldIncident.getItinComplexity());
		fsIncident.setIncidentDescription(oldIncident.getIncidentDescription());
		fsIncident.setRemarks(oldIncident.getRemarks());

		// bag
		Set<Bag> bags = new LinkedHashSet<Bag>();
		if (oldIncident.getBags() != null) {
			bags.addAll(oldIncident.getBags());
		}
		fsIncident.setBags(bags);
		
		// reservation
//		fsIncident.setReservation(oldIncident.getReservation());
//		fsIncident.getReservation().setIncident(fsIncident);
		
		// segments
		LinkedHashSet<Segment> segments = new LinkedHashSet<Segment>();
		if (oldIncident.getSegments() != null) {
			segments.addAll(oldIncident.getSegments());
			for (Segment s: segments) {
				s.setIncident(fsIncident);
			}
		}
		fsIncident.setSegments(segments);
		
		// passengers
		LinkedHashSet<Person> passengers = new LinkedHashSet<Person>();
		if (oldIncident.getPassengers() != null) {
			passengers.addAll(oldIncident.getPassengers());
			for (Person p: passengers) {
				p.setIncident(fsIncident);
			}
		}
		fsIncident.setPassengers(passengers);
		
		return fsIncident;
	}
	
//	public static ClaimForm createClaimForm(String incidentId, Claim claim, HttpServletRequest request) {
//		Incident incident = IncidentUtils.findIncidentByID(incidentId);
//		return createClaimForm(incident, claim, request);
//	}
	
	public static ClaimForm createClaimForm(HttpServletRequest request) {
		ClaimForm cform = null;
		try {
			HttpSession session = request.getSession();
			cform = (ClaimForm) session.getAttribute("claimForm");

			if (cform == null) {
				cform = new ClaimForm();
			} 
	
			Agent user = (Agent) session.getAttribute("user");

			session.setAttribute("claimstatuslist", session
					.getAttribute("claimstatuslist") != null ? session
					.getAttribute("claimstatuslist") : TracerUtils.getStatusList(TracingConstants.TABLE_CLAIM, user.getCurrentlocale()));

//			if (claim != null) {
//				cform.setClaim(claim);
//			} else {
//				cform.setClaim(ClaimUtils.createClaim(user, incident));
//			}

			// TODO: VERIFY THAT THIS IS NO LONGER NEEDED
//			Passenger pa = (Passenger) theform.getPassenger(0);
//			String passengername = pa.getFirstname() + " " + pa.getLastname();
//			cform.getClaim().setPassengername(passengername);

//			if (theform.getExpenselist() != null)
//				cform.getClaim().getNtIncident().setExpenselist(theform.getExpenselist());

			cform.set_DATEFORMAT(user.getDateformat().getFormat());
			cform.set_TIMEFORMAT(user.getTimeformat().getFormat());
			cform.set_TIMEZONE(TimeZone
					.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));

			session.setAttribute("claimForm", cform);

			return cform;

		} catch (Exception e) {
			logger.error("bean copy claim form error on populateClaim: " + e);
			e.printStackTrace();
			return cform;
		}
	}
	
}
