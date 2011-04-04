//package fs;
//
//import java.util.Date;
//import java.util.LinkedHashSet;
//import java.util.Set;
//
//import org.apache.commons.codec.language.DoubleMetaphone;
//import org.apache.commons.codec.language.Soundex;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//
//import aero.nettracer.fs.model.Address;
//import aero.nettracer.fs.model.Bag;
//import aero.nettracer.fs.model.Claim;
//import aero.nettracer.fs.model.Incident;
//import aero.nettracer.fs.model.Person;
//import aero.nettracer.fs.model.Phone;
//import aero.nettracer.fs.model.Reservation;
//import aero.nettracer.fs.model.Segment;
//
//import com.bagnet.nettracer.tracing.bmo.claims.ClaimSettlementBMO;
//import com.bagnet.nettracer.tracing.constant.TracingConstants;
//import com.bagnet.nettracer.tracing.db.AirlineMembership;
//import com.bagnet.nettracer.tracing.db.Item;
//import com.bagnet.nettracer.tracing.db.Itinerary;
//import com.bagnet.nettracer.tracing.db.Passenger;
//import com.bagnet.nettracer.tracing.db.claims.ClaimSettlement;
//
//public class ImportIncident {
//
//	private static Soundex sndx = new Soundex();
//	private static DoubleMetaphone dmp = new DoubleMetaphone();
//
//	public void importIncident(String incidentId, Session sess, Session writeSess) throws Exception {
//
//		try {
//			com.bagnet.nettracer.tracing.db.Incident inc = (com.bagnet.nettracer.tracing.db.Incident) sess.load(com.bagnet.nettracer.tracing.db.Incident.class, incidentId);
//
//			Incident i = new Incident();
//			Claim c = new Claim();
//			Reservation r = new Reservation();
//
//			i.setAirlineIncidentId(inc.getIncident_ID());
//			Date createDate = new Date(inc.getCreatedate().getTime() + inc.getCreatetime().getTime());
//			i.setIncidentCreated(createDate);
//
//			i.setIncidentDescription(null);
//			i.setIncidentType(inc.getItemtype_ID());
//
//			int itinComplexity = 0;
//			Set<Itinerary> itin = inc.getItinerary();
//			Set<Segment> segments = new LinkedHashSet<Segment>();
//			if (itin != null) {
//				for (Itinerary it : itin) {
//					if (it.getItinerarytype() == TracingConstants.PASSENGER_ROUTING) {
//						Segment seg = new Segment();
//						seg.setAirline(it.getAirline());
//						seg.setArrival(it.getLegto());
//						seg.setClaim(null);
//						seg.setIncident(i);
//						seg.setDeparture(it.getLegfrom());
//						seg.setFlight(it.getFlightnum());
//						seg.setReservation(null);
//						seg.setDate(it.getDepartdate());
//						itinComplexity += 1;
//						segments.add(seg);
//					}
//				}
//			}
//
//			i.setSegments(segments);
//			i.setItinComplexity(itinComplexity);
//
//			i.setTimestampClosed(inc.getClosedate());
//			i.setTimestampOpen(createDate);
//
//			long timeOpenMillis = inc.getClosedate().getTime() - inc.getCreatedate().getTime();
//			long timeOpenDays = timeOpenMillis / 1000 / 60 / 60 / 24;
//
//			i.setNumberDaysOpen((int) timeOpenDays);
//
//			Set<Bag> bags = new LinkedHashSet<Bag>();
//
//			int numberOfBdos = 0;
//			if (inc.getItemlist() != null && inc.getItemlist().size() > 0) {
//				for (Item it : inc.getItemlist()) {
//					Bag bag = new Bag();
//					bag.setBagType(it.getBagtype());
//					bag.setBagColor(it.getColor());
//					
//					bag.setManufacturer(it.getManufacturer());
//					bag.setDescription(it.getDamage());
//					bag.setIncident(i);
//
//					if (it.getBdo() != null) {
//						numberOfBdos += 1;
//					}
//					bags.add(bag);
//				}
//			}
//
//			i.setBags(bags);
//			i.setNumberOfBdos(numberOfBdos);
//
//			Set<Person> passengers = new LinkedHashSet<Person>();
//
//			Set<Passenger> paxSet = inc.getPassengers();
//			
//			if (paxSet != null) {
//				for (Passenger pax : paxSet) {
//					Person p = new Person();
//					p.setIncident(i);
//					passengers.add(p);
//					
//					Set<Address> addresses = new LinkedHashSet<Address>();
//					Set<Phone> phones = new LinkedHashSet<Phone>();
//					
//					if (pax.getAddresses() != null) {
//						Address a = new Address();
//						a.setPerson(p);
//						addresses.add(a);
//						
//						com.bagnet.nettracer.tracing.db.Address add = pax.getAddress(0);
//						a.setAddress1(add.getAddress1());
//						a.setAddress2(add.getAddress2());
//						a.setCity(add.getCity());
//						a.setCountry(add.getCountrycode_ID());
//						
//						//TODO: Latitude and Longitude
//						// a.setLattitude(0);
//						// a.setLongitude(0);
//											
//						
//						a.setReservation(null);
//						a.setState(add.getState_ID());
//						a.setZip(add.getZip());
//						p.setEmailAddress(add.getEmail());
//						
//						
//						processAndAddPhone(p, phones, add.getHomephone(), Phone.HOME);
//						processAndAddPhone(p, phones, add.getMobile(), Phone.MOBILE);
//						processAndAddPhone(p, phones, add.getWorkphone(), Phone.WORK);
//						processAndAddPhone(p, phones, add.getPager(), Phone.PAGER);
//						processAndAddPhone(p, phones, add.getAltphone(), Phone.ALTERNATE);
//						
//					}
//					
//					p.setPhones(phones);
//					p.setAddresses(addresses);
//
//					p.setDescription(null);
//					String fn = pax.getFirstname();
//					if (fn != null) {
//						p.setFirstName(fn);
//						p.setFirstNameDmp(dmp.encode(fn));
//						p.setFirstNameSoundex(sndx.encode(fn));
//					}
//					p.setMiddleName(pax.getMiddlename());
//
//					String ln = pax.getLastname();
//					if (ln != null) {
//						p.setLastName(ln);
//						p.setLastNameDmp(dmp.encode(ln));
//						p.setLastNameSoundex(sndx.encode(ln));
//					}
//
//					if (pax.getMembership() != null) {
//						AirlineMembership m = pax.getMembership();
//						p.setFfAirline(m.getCompanycode_ID());
//						p.setFfNumber(m.getMembershipnum());	
//					}
//					
//
//					p.setSocialSecurity(null);
//					p.setDriversLicenseIssuer(null);
//					p.setDriversLicenseNumber(null);
//					p.setPassportIssuer(null);
//					p.setPassportNumber(null);
//
//					p.setClaim(null);
//					p.setReservation(null);
//				}
//			}
//
//			i.setPassengers(passengers);
//
//			i.setRemarks(null);
//			i.setReservation(r);
//			i.setClaim(c);
//			// SAVE
//
//			Transaction t = writeSess.beginTransaction();
//			sess.save(i);
//			t.commit();
//
//			// EVICT
//			sess.evict(inc);
//			writeSess.evict(i);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
//
//	}
//
//	public void importClaimSettlement(String incidentId, Session sess, Session writeSess) throws Exception {
//
//		try {
//			ClaimSettlement cs = ClaimSettlementBMO.getClaimSettlement(incidentId, sess);
//			
//			cs.getAddress1();
//			cs.getAddress2();
//			cs.getBagList();
//			cs.getBusinessPhone();
//			cs.getCity();
//			cs.getClaimAgent();
//			cs.getCountryCode_ID();
//			
//			
//			com.bagnet.nettracer.tracing.db.Incident inc = (com.bagnet.nettracer.tracing.db.Incident) sess.load(com.bagnet.nettracer.tracing.db.Incident.class, incidentId);
//
//			Incident i = new Incident();
//			Claim c = new Claim();
//			Reservation r = new Reservation();
//
//			i.setAirlineIncidentId(inc.getIncident_ID());
//			Date createDate = new Date(inc.getCreatedate().getTime() + inc.getCreatetime().getTime());
//			i.setIncidentCreated(createDate);
//
//			i.setIncidentDescription(null);
//			i.setIncidentType(inc.getItemtype_ID());
//
//			int itinComplexity = 0;
//			Set<Itinerary> itin = inc.getItinerary();
//			Set<Segment> segments = new LinkedHashSet<Segment>();
//			if (itin != null) {
//				for (Itinerary it : itin) {
//					if (it.getItinerarytype() == TracingConstants.PASSENGER_ROUTING) {
//						Segment seg = new Segment();
//						seg.setAirline(it.getAirline());
//						seg.setArrival(it.getLegto());
//						seg.setClaim(null);
//						seg.setIncident(i);
//						seg.setDeparture(it.getLegfrom());
//						seg.setFlight(it.getFlightnum());
//						seg.setReservation(null);
//						seg.setDate(it.getDepartdate());
//						itinComplexity += 1;
//						segments.add(seg);
//					}
//				}
//			}
//
//			i.setSegments(segments);
//			i.setItinComplexity(itinComplexity);
//
//			i.setTimestampClosed(inc.getClosedate());
//			i.setTimestampOpen(createDate);
//
//			long timeOpenMillis = inc.getClosedate().getTime() - inc.getCreatedate().getTime();
//			long timeOpenDays = timeOpenMillis / 1000 / 60 / 60 / 24;
//
//			i.setNumberDaysOpen((int) timeOpenDays);
//
//			Set<Bag> bags = new LinkedHashSet<Bag>();
//
//			int numberOfBdos = 0;
//			if (inc.getItemlist() != null && inc.getItemlist().size() > 0) {
//				for (Item it : inc.getItemlist()) {
//					Bag bag = new Bag();
//					bag.setBagType(it.getBagtype());
//					bag.setBagColor(it.getColor());
//					
//					bag.setManufacturer(it.getManufacturer());
//					bag.setDescription(it.getDamage());
//					bag.setIncident(i);
//
//					if (it.getBdo() != null) {
//						numberOfBdos += 1;
//					}
//					bags.add(bag);
//				}
//			}
//
//			i.setBags(bags);
//			i.setNumberOfBdos(numberOfBdos);
//
//			Set<Person> passengers = new LinkedHashSet<Person>();
//
//			Set<Passenger> paxSet = inc.getPassengers();
//			
//			if (paxSet != null) {
//				for (Passenger pax : paxSet) {
//					Person p = new Person();
//					p.setIncident(i);
//					passengers.add(p);
//					
//					Set<Address> addresses = new LinkedHashSet<Address>();
//					Set<Phone> phones = new LinkedHashSet<Phone>();
//					
//					if (pax.getAddresses() != null) {
//						Address a = new Address();
//						a.setPerson(p);
//						addresses.add(a);
//						
//						com.bagnet.nettracer.tracing.db.Address add = pax.getAddress(0);
//						a.setAddress1(add.getAddress1());
//						a.setAddress2(add.getAddress2());
//						a.setCity(add.getCity());
//						a.setCountry(add.getCountrycode_ID());
//						
//						//TODO: Latitude and Longitude
//						// a.setLattitude(0);
//						// a.setLongitude(0);
//											
//						
//						a.setReservation(null);
//						a.setState(add.getState_ID());
//						a.setZip(add.getZip());
//						p.setEmailAddress(add.getEmail());
//						
//						
//						processAndAddPhone(p, phones, add.getHomephone(), Phone.HOME);
//						processAndAddPhone(p, phones, add.getMobile(), Phone.MOBILE);
//						processAndAddPhone(p, phones, add.getWorkphone(), Phone.WORK);
//						processAndAddPhone(p, phones, add.getPager(), Phone.PAGER);
//						processAndAddPhone(p, phones, add.getAltphone(), Phone.ALTERNATE);
//						
//					}
//					
//					p.setPhones(phones);
//					p.setAddresses(addresses);
//
//					p.setDescription(null);
//					String fn = pax.getFirstname();
//					if (fn != null) {
//						p.setFirstName(fn);
//						p.setFirstNameDmp(dmp.encode(fn));
//						p.setFirstNameSoundex(sndx.encode(fn));
//					}
//					p.setMiddleName(pax.getMiddlename());
//
//					String ln = pax.getLastname();
//					if (ln != null) {
//						p.setLastName(ln);
//						p.setLastNameDmp(dmp.encode(ln));
//						p.setLastNameSoundex(sndx.encode(ln));
//					}
//
//					if (pax.getMembership() != null) {
//						AirlineMembership m = pax.getMembership();
//						p.setFfAirline(m.getCompanycode_ID());
//						p.setFfNumber(m.getMembershipnum());	
//					}
//					
//
//					p.setSocialSecurity(null);
//					p.setDriversLicenseIssuer(null);
//					p.setDriversLicenseNumber(null);
//					p.setPassportIssuer(null);
//					p.setPassportNumber(null);
//
//					p.setClaim(null);
//					p.setReservation(null);
//				}
//			}
//
//			i.setPassengers(passengers);
//
//			i.setRemarks(null);
//			i.setReservation(r);
//			i.setClaim(c);
//			// SAVE
//
//			Transaction t = writeSess.beginTransaction();
//			sess.save(i);
//			t.commit();
//
//			// EVICT
//			sess.evict(inc);
//			writeSess.evict(i);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//			
//		}
//	}
//
//	
//	private void processAndAddPhone(Person p, Set<Phone> phones, String phone, int type) {
//	  if (phone != null && phone.length() > 0) {
//	  	Phone ph = new Phone();
//	  	ph.setPerson(p);
//	  	ph.setType(type);
//	  	ph.setPhoneNumber(com.bagnet.nettracer.tracing.utils.StringUtils.removeNonNumeric(phone));
//	  	phones.add(ph);
//	  }
//  }
//}
