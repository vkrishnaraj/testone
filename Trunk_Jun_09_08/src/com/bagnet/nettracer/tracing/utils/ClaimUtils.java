package com.bagnet.nettracer.tracing.utils;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

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
		claim.setClaimDate(new Date());
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
		Phone phone = new Phone();
		phone.setPerson(person);
		phone.setType(0);
		phones.add(phone);
		
		// create the person
		person.setAddresses(addresses);
		person.setPhones(phones);
		person.setClaim(claim);
		LinkedHashSet<Person> claimants = new LinkedHashSet<Person>();
		claimants.add(person);
		
		// create the purchaser
		Person purchaser = new Person();
		FsAddress pAddress = new FsAddress();
		pAddress.setPerson(purchaser);
		LinkedHashSet<FsAddress> pAddresses = new LinkedHashSet<FsAddress>();
		pAddresses.add(pAddress);
		purchaser.setAddresses(pAddresses);
		
		// create the pnr data
		PnrData pnrData = new PnrData();
		
		// create the reservation
		Reservation reservation = new Reservation();
		reservation.setPurchaser(purchaser);
		purchaser.setReservation(reservation);
		reservation.setPassengers(new LinkedHashSet<Person>());
		reservation.setPhones(new LinkedHashSet<Phone>());
		reservation.setPnrData(pnrData);
		reservation.setSegments(new LinkedHashSet<Segment>());
		pnrData.setReservation(reservation);
		
		// set the fraud incident
		FsIncident fsIncident = new FsIncident();
		fsIncident.setAirline(user.getCompanycode_ID());
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
		
		Segment s = new Segment();
		s.setClaim(claim);
		LinkedHashSet<Segment> segments = new LinkedHashSet<Segment>();
		segments.add(s);
		claim.setSegments(segments);
		
		// set the tracing incident if we have one
		if (ntIncident != null) {
			claim.setClaimType(ntIncident.getItemtype_ID());
			claim.setNtIncident(ntIncident);
			ntIncident.setClaim(claim);
		}
		
		return claim;
		
	}
	
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
	
	public static Set<FsClaim> getPaginatedList(Set<FsClaim> list, int rowsperpage, int currpage) {
		Set<FsClaim> paginatedList = new LinkedHashSet<FsClaim>();
		int startIndex = currpage * rowsperpage;
		int endIndex = startIndex + rowsperpage;
		
		if (endIndex > list.size()) {
			endIndex = list.size();
		}
		
		FsClaim[] temp = list.toArray(new FsClaim[0]);
		for (int i = startIndex; i < endIndex; ++i) {
			paginatedList.add(temp[i]);
		}
		
		return paginatedList;
	}
	
}
