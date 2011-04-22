//package fs;
//
//// TODO: GET ARTICLES FOR PILF!!!
//// TODO: SOUNDEX CC NAMES
//// 
//
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.apache.commons.codec.language.DoubleMetaphone;
//import org.apache.commons.codec.language.Soundex;
//import org.hibernate.Criteria;
//import org.hibernate.HibernateException;
//import org.hibernate.SQLQuery;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.criterion.Expression;
//import org.hibernate.exception.ConstraintViolationException;
//import org.junit.Test;
//
//import aero.nettracer.fs.model.Address;
//import aero.nettracer.fs.model.Bag;
//import aero.nettracer.fs.model.Claim;
//import aero.nettracer.fs.model.FsClaim;
//import aero.nettracer.fs.model.FsIncident;
//import aero.nettracer.fs.model.Incident;
//import aero.nettracer.fs.model.Person;
//import aero.nettracer.fs.model.Phone;
//import aero.nettracer.fs.model.PnrData;
//import aero.nettracer.fs.model.Reservation;
//import aero.nettracer.fs.model.Segment;
//
//import com.bagnet.nettracer.hibernate.FraudWrapper;
//import com.bagnet.nettracer.tracing.bmo.OtherSystemInformationBMO;
//import com.bagnet.nettracer.tracing.constant.TracingConstants;
//import com.bagnet.nettracer.tracing.db.AirlineMembership;
//import com.bagnet.nettracer.tracing.db.ExpensePayout;
//import com.bagnet.nettracer.tracing.db.Item;
//import com.bagnet.nettracer.tracing.db.Itinerary;
//import com.bagnet.nettracer.tracing.db.Manufacturer;
//import com.bagnet.nettracer.tracing.db.OtherSystemInformation;
//import com.bagnet.nettracer.tracing.db.Passenger;
//
//public class ImportIncident {
//
//	private static Soundex sndx = new Soundex();
//	private static DoubleMetaphone dmp = new DoubleMetaphone();
//
//	// US AIRWAYS PARSING
//	static String tktRegEx = "^TKT-T/([A-Z]{3})/([A-Z0-9]+)/[0-9]{0,}/[0-9]{0,}[//(\\d+.\\d+)]{0,}";
//	static String fop = "^FOP-\\s{0,}\\$([A-Z]{2})X+([0-9]{4})/([0-9]{4})";
//	static String price = "\\d+\\.\\d+";
//	static String nm = "\\s+\\d\\.\\s{0,}\\d\\s{0,}([A-Z]+)/([A-Z]+)";
//	static String email = "\\d\\.WEB\\*EMAIL-([A-Z0-9\\.]+\\*[A-Z0-9]+\\.[A-Z]+)";
//	static String cc1 = "\\d\\.ADDR1\\*([A-Z]+)\\s([A-Z]+)";
//	static String cc2 = "\\d\\.ADDR2\\*([0-9A-Z\\s]+)";
//	static String cc3 = "\\d\\.ADDR3\\*([A-Z\\s]+)\\s([A-Z]{2})\\s([0-9]{5})";
//	static String ch = "\\d\\.CARDHOLDER:\\s([0-9A-Z\\s]+)";
//
//	static Pattern p = Pattern.compile(tktRegEx);
//	static Pattern p2 = Pattern.compile(fop);
//	static Pattern pp = Pattern.compile(price);
//	static Pattern np = Pattern.compile(nm);
//	static Pattern em = Pattern.compile(email);
//	static Pattern ccc1 = Pattern.compile(cc1);
//	static Pattern ccc2 = Pattern.compile(cc2);
//	static Pattern ccc3 = Pattern.compile(cc3);
//	static Pattern cardHolder = Pattern.compile(ch);
//	public static HashMap<String, Integer> alreadyImported = new HashMap<String, Integer>();
//
//	 @Test
//	public void importAll() {
//		ConcurrentHashMap<String, Integer> completed = new ConcurrentHashMap<String, Integer>();
//		// String sql = "SELECT i.incident_ID FROM Incident i WHERE
//		// (i.itemtype.itemType_ID = :itemType OR i.itemtype.itemType_ID =
//		// :itemType2) " + " ORDER BY i.createdate, i.createtime desc";
//		String sql = "SELECT distinct incident_ID from tempIncident";
//		SessionFactory sessFactory = null;
//		Session sess = null;
//		String hibernateAirline = null;
//		for (int iterator = 5; iterator < 6; ++iterator) {
//			switch (iterator) {
//			case 0:
//				sess = FraudWrapper.getJetblueSession().openSession();
//				sessFactory = FraudWrapper.getJetblueSession();
//
//				hibernateAirline = "JETBLUE";
//				break;
//			case 1:
//				hibernateAirline = "SPIRIT";
//				sess = FraudWrapper.getSpiritSession().openSession();
//				sessFactory = FraudWrapper.getSpiritSession();
//				break;
//			case 2:
//				hibernateAirline = "AZUL";
//				sess = FraudWrapper.getAzulSession().openSession();
//				sessFactory = FraudWrapper.getAzulSession();
//				break;
//			case 3:
//				hibernateAirline = "WESTJET";
//				sess = FraudWrapper.getWestJetSession().openSession();
//				sessFactory = FraudWrapper.getWestJetSession();
//				break;
//			case 4:
//				hibernateAirline = "USAIR";
//				sess = FraudWrapper.getUsairSession().openSession();
//				sessFactory = FraudWrapper.getUsairSession();
//				break;
//			case 5:
//				hibernateAirline = "AIRTRAN";
//				sess = FraudWrapper.getAirtranSession().openSession();
//				sessFactory = FraudWrapper.getAirtranSession();
//				break;
//			}
//			
//			
//
//			Session fraudSess = FraudWrapper.getFraudSession().openSession();
//			SQLQuery queryl = fraudSess.createSQLQuery("select distinct airlineIncidentId from incident where airlineIncidentId like '%FL%'");
//			List<String> alreadyImportedList = queryl.list();
//			
//			
//			Integer blank = new Integer(0);
//			for (String ai: alreadyImportedList) {
//				alreadyImported.put(ai, blank);
//			}
//			
//			
//			
//			SessionFactory fraudFactory = FraudWrapper.getFraudSession();
//			SQLQuery query = sess.createSQLQuery(sql);
//
//			List<String> incidentList = query.list();
//			
//			sess.close();
//			fraudSess.close();
//			
//			int totalSize = incidentList.size() - alreadyImported.size();
//			
//			ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10000);
//
//			Date startTime = new Date();
//
//			// Create producers and consumers
//			for (int i = 0; i < 12; ++i) {
//				ImportIncidentThread t = new ImportIncidentThread(queue, totalSize, hibernateAirline, sessFactory, fraudFactory, startTime, completed);
//				new Thread(t).start();
//			}
//
//			System.out.println("Total count: " + totalSize);
//			
//			
//
//			for (int i = 0; i < incidentList.size(); ++i) {
////			for (int i = 0; i < 1; ++i) {
//				while (queue.size() > 9000) {
//					try {
//						Thread.sleep(5 * 1000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//
//				String incidentId = incidentList.get(i);
//				if (!alreadyImported.containsKey(incidentId)) {
//					queue.add(incidentId);
//					
//				} else {
////					System.out.println("Skipped: " + incidentId);
////					System.err.println("Skipped: " + incidentId);
//				}
//				
//
//			}
//
//			while (queue.size() > 0) {
//				try {
//					Thread.sleep(5 * 1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//			try {
//				System.out.println("About to sleep for 20 seconds.");
//				Thread.sleep(1000 * 20);
//				System.out.println("Process complete...");
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//
//		}
//	}
//
//	/*
//	public void reprocessReservations() {
//
//		String sql = "SELECT id from reservation";
//		Session sess = FraudWrapper.getFraudSession().openSession();
//		SQLQuery query = sess.createSQLQuery(sql);
//		List<BigInteger> reservationList = query.list();
//
//		Date startTime = new Date();
//		System.out.println("Total count: " + reservationList.size());
//
//		for (int i = 0; i < reservationList.size(); ++i) {
//			if (i % 100 == 0) {
//				System.out.println("Current number: " + i + "/" + reservationList.size());
//			}
//
//			Reservation r = (Reservation) sess.load(Reservation.class, reservationList.get(i).longValue());
//			try {
//				reimportReservation(r, sess);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		}
//
//		// Reservation r = (Reservation) sess.load(Reservation.class, 5255L);
//		// try {
//		// reimportReservation(r, sess);
//		// } catch (Exception e) {
//		// // TODO Auto-generated catch block
//		// e.printStackTrace();
//		// }
//
//		sess.close();
//		System.out.println("Process complete...");
//	}
//*/
///*
//	public static void reimportReservation(Reservation r, Session sess) throws Exception {
//
//		try {
//
//			if (r.getPnrData() != null) {
//				String pnrData = r.getPnrData().getPnrData();
////				System.out.println(pnrData);
//				if (pnrData != null && pnrData.length() > 0) {
//					ParsedData pd = parseInterestingData(pnrData);
//					r.setCcNumber(pd.last4);
//					r.setFormOfPayment(pd.fop);
//					r.setCcType(pd.cardType);
//					r.setExpirationDate(pd.expiration);
//
//					//					
//					Set<Person> persons = r.getPassengers();
//					for (Person person : persons) {
//						person.setReservation(null);
//						persons.remove(person);
//					}
//
//					for (Name name : pd.names) {
//						Person p1 = new Person();
//						p1.setReservation(r);
//						persons.add(p1);
//
//						p1.setFirstName(name.firstName);
//						p1.setLastName(name.lastName);
//
//						if (name.cc == true) {
//							p1.setCcContact(true);
//							Set<Address> addresses = new LinkedHashSet<Address>();
//							p1.setAddresses(addresses);
//
//							Address a = new Address();
//							a.setPerson(p1);
//							addresses.add(a);
//
//							a.setAddress1(pd.add2);
//
//							a.setCity(pd.city);
//							a.setZip(pd.zip);
//							a.setState(pd.state);
//
//							// TODO: Latitude and Longitude
//							// a.setLattitude(0);
//							// a.setLongitude(0);
//
//						}
//					}
//
//					for (int j = 0; j < pd.emails.size(); ++j) {
//						String email = pd.emails.get(j);
//						Person p1 = null;
//						if (persons.size() >= 1 + j) {
//							int current = 0;
//							for (Person pp : persons) {
//								if (current == j) {
//									p1 = pp;
//									break;
//								}
//								++current;
//							}
//						} else {
//							p1 = new Person();
//							p1.setReservation(r);
//							persons.add(p1);
//						}
//						p1.setEmailAddress(email);
//					}
//
//					r.setTicketAmount(pd.ticketPrice);
//				}
//			}
//
//			// SAVE
//
//			Transaction t = sess.beginTransaction();
//			sess.update(r);
//			t.commit();
//
//			// EVICT
//			sess.evict(r);
//
//		} catch (ConstraintViolationException ex) {
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
//
//	}*/
//
//	public static void importIncident(String incidentId, Session sess, Session writeSess) throws Exception {
////		System.out.println("Importing: " + incidentId);
//		try {
//			
//			com.bagnet.nettracer.tracing.db.Incident inc = (com.bagnet.nettracer.tracing.db.Incident) sess.load(com.bagnet.nettracer.tracing.db.Incident.class, incidentId);
//
//			FsIncident i = new FsIncident();
//			FsClaim c = null;
//			Reservation r = null;
//
//			i.setAirlineIncidentId(inc.getIncident_ID());
//			Date createDate = new Date(inc.getCreatedate().getTime() + inc.getCreatetime().getTime());
//			i.setIncidentCreated(createDate);
//
//			i.setIncidentDescription(null);
//			i.setIncidentType(inc.getItemtype_ID());
//			Date travelDate = null;
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
//						if (travelDate == null) {
//							travelDate = it.getDepartdate();
//						}
//						itinComplexity += 1;
//						segments.add(seg);
//					}
//				}
//			}
//
//			i.setSegments(segments);
//			i.setItinComplexity(itinComplexity);
//
//			// TODO: CLAIM Contents, name, ph?
//			com.bagnet.nettracer.tracing.db.Claim icl = inc.getClaim();
//			if (icl != null) {
//				c = new FsClaim();
//				c.setIncident(i);
//				i.setClaim(c);
//				c.setAirlineClaimId(inc.getIncident_ID());
//				c.setAirline(inc.getStationcreated().getCompany().getCompanyCode_ID());
//				c.setTravelDate(travelDate);
//
//				if (icl.getClaimamount() > 0) {
//					c.setAmountClaimed(icl.getClaimamount());
//					c.setAmountClaimedCurrency(icl.getClaimcurrency_ID());
//				}
//
//				Set<Person> claimants = new LinkedHashSet<Person>();
//				Person p = new Person();
//				p.setClaim(c);
//				boolean populate = false;
//
//				if (icl.getSsn() != null && icl.getSsn().length() > 0) {
//					p.setSocialSecurity(icl.getSsn());
//					populate = true;
//				}
//
//				if (icl.getCommonnum() != null && icl.getCommonnum().length() > 0) {
//					p.setPassportNumber(icl.getCommonnum());
//					p.setPassportIssuer(icl.getCountryofissue());
//					populate = true;
//				}
//
//				if (icl.getDriverslicense() != null && icl.getDriverslicense().length() > 0) {
//					p.setDriversLicenseNumber(icl.getDriverslicense());
//					p.setDriversLicenseIssuer(icl.getDlstate());
//					populate = true;
//				}
//
//				if (populate) {
//					c.setClaimants(claimants);
//				}
//
//			}
//
//			// IGNORE DELIVERY PAYOUTS AS BDOS CAN CREATE THESE. LIMIT TO APPROVED
//			// PAYOUTS
//			double amount = 0;
//			boolean usePayoutAmount = false;
//			for (ExpensePayout ep : inc.getExpenselist()) {
//				if (!ep.getPaycode().equals("DEL")) {
//					if (ep.getStatus().getStatus_ID() == TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED) {
//						if (c == null) {
//							c = new Claim();
//							c.setIncident(i);
//							i.setClaim(c);
//							
//						}
//						amount += ep.getCheckamt();
//						amount += ep.getVoucheramt();
//						if (c.getAmountClaimed() == 0 || usePayoutAmount == true) {
//							usePayoutAmount = true;
//							c.setAmountClaimed(amount);
//							c.setAmountClaimedCurrency(ep.getCurrency_ID());
//						}
//						c.setAmountPaid(amount);
//						
//					}
//				}
//			}
//
//			i.setTimestampClosed(inc.getClosedate());
//			i.setTimestampOpen(createDate);
//
//			if (inc.getClosedate() != null) {
//				long timeOpenMillis = inc.getClosedate().getTime() - inc.getCreatedate().getTime();
//				long timeOpenDays = timeOpenMillis / 1000 / 60 / 60 / 24;
//				i.setNumberDaysOpen((int) timeOpenDays);
//			} else {
//
//			}
//
//			Set<Bag> bags = new LinkedHashSet<Bag>();
//
//			int numberOfBdos = 0;
//			if (inc.getItemlist() != null && inc.getItemlist().size() > 0) {
//				for (Item it : inc.getItemlist()) {
//					if (it != null) {
//						Bag bag = new Bag();
//						bag.setBagType(it.getBagtype());
//						bag.setBagColor(it.getColor());
//
//						String manu = null;
//						if (it.getManufacturer_ID() > 0) {
//							if (it.getManufacturer_ID() == TracingConstants.MANUFACTURER_OTHER_ID) {
//								if (it.getManufacturer_other() != null && it.getManufacturer_other().length() > 0) {
//									manu = it.getManufacturer_other();
//								}
//							} else {
//
//								Criteria cri = sess.createCriteria(Manufacturer.class).add(Expression.eq("manufacturer_ID", new Integer(it.getManufacturer_ID())));
//								Manufacturer manuf = (Manufacturer) cri.list().get(0);
//								if (manuf != null) {
//									manu = manuf.getDescription();
//								}
//							}
//						}
//						bag.setManufacturer(manu);
//						bag.setDescription(it.getDamage());
//						bag.setIncident(i);
//
//						if (it.getBdo() != null) {
//							numberOfBdos += 1;
//						}
//						bags.add(bag);
//
//					}
//
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
//						// TODO: Latitude and Longitude
//						// a.setLattitude(0);
//						// a.setLongitude(0);
//
//						a.setReservation(null);
//						a.setState(add.getState_ID());
//						a.setZip(add.getZip());
//						p.setEmailAddress(add.getEmail());
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
//						try {
//							p.setFirstNameDmp(dmp.encode(fn));
//							p.setFirstNameSoundex(sndx.encode(fn));
//						} catch (Exception e) {
//
//						}
//					}
//					p.setMiddleName(pax.getMiddlename());
//
//					String ln = pax.getLastname();
//					if (ln != null) {
//
//						p.setLastName(ln);
//						try {
//							p.setLastNameDmp(dmp.encode(ln));
//							p.setLastNameSoundex(sndx.encode(ln));
//						} catch (Exception e) {
//
//						}
//					}
//
//					if (pax.getMembership() != null) {
//						AirlineMembership m = pax.getMembership();
//						p.setFfAirline(m.getCompanycode_ID());
//						p.setFfNumber(m.getMembershipnum());
//					}
//				}
//			}
//
//			OtherSystemInformation osi = OtherSystemInformationBMO.getOsi(inc.getIncident_ID(), sess);
//			if (osi != null && osi.getInfo().length() > 0) {
//				r = new Reservation();
//				r.setIncident(i);
//				r.setAirline(inc.getStationcreated().getCompany().getCompanyCode_ID());
//
//				ParsedData pd = parseInterestingData(osi.getInfo());
//
//				r.setCcNumber(pd.last4);
//				r.setFormOfPayment(pd.fop);
//				r.setCcType(pd.cardType);
//				r.setExpirationDate(pd.expiration);
//
//				LinkedHashSet<Person> persons = new LinkedHashSet<Person>();
//				r.setPassengers(persons);
//
//				for (Name name : pd.names) {
//					Person p1 = new Person();
//					p1.setReservation(r);
//					persons.add(p1);
//
//					// TODO: SOUNDEX THESE
//					p1.setFirstName(name.firstName);
//					p1.setLastName(name.lastName);
//
//					if (name.cc == true) {
//						p1.setCcContact(true);
//						Set<Address> addresses = new LinkedHashSet<Address>();
//						p1.setAddresses(addresses);
//
//						Address a = new Address();
//						a.setPerson(p1);
//						addresses.add(a);
//
//						a.setAddress1(pd.add2);
//
//						a.setCity(pd.city);
//						a.setZip(pd.zip);
//						a.setState(pd.state);
//
//						// TODO: Latitude and Longitude
//						// a.setLattitude(0);
//						// a.setLongitude(0);
//
//					}
//				}
//
//				for (int j = 0; j < pd.emails.size(); ++j) {
//					String email = pd.emails.get(j);
//					Person p1 = null;
//					if (persons.size() >= 1 + j) {
//						int current = 0;
//						for (Person pp : persons) {
//							if (current == j) {
//								p1 = pp;
//								break;
//							}
//							++current;
//						}
//					} else {
//						p1 = new Person();
//						p1.setReservation(r);
//						persons.add(p1);
//					}
//					p1.setEmailAddress(email);
//				}
//
//				PnrData pnr = new PnrData();
//				pnr.setPnrData(osi.getInfo());
//				pnr.setReservation(r);
//				r.setPnrData(pnr);
//				r.setTicketAmount(pd.ticketPrice);
//				osi.getInfo();
//			}
//
//			i.setPassengers(passengers);
//
//			i.setRemarks(null);
//			i.setReservation(r);
//			i.setClaim(c);
//
//			// SAVE
//
//			Transaction t = writeSess.beginTransaction();
//			if (c != null) {
//				writeSess.save(c);
//			} else {
//				writeSess.save(i);
//			}
//			t.commit();
//
//			// EVICT
//			sess.evict(inc);
//			if (c != null) {
//				writeSess.evict(c);
//			} else {
//				writeSess.evict(i);
//			}
//
//		} catch (ConstraintViolationException ex) {
//		} catch (HibernateException he) {
//			System.out.println("CLAIMS:" + incidentId);
//		}catch (Exception e) {
//		
//			e.printStackTrace();
//			throw e;
//		}
//
//	}
//
//	private static void processAndAddPhone(Person p, Set<Phone> phones, String phone, int type) {
//		if (phone != null && phone.length() > 0) {
//			Phone ph = new Phone();
//			ph.setPerson(p);
//			ph.setType(type);
//			ph.setPhoneNumber(com.bagnet.nettracer.tracing.utils.StringUtils.removeNonNumeric(phone));
//			phones.add(ph);
//		}
//	}
//
//	public static ParsedData parseInterestingData(String test) {
//
//		String[] lines = test.split("\n");
//		int cardHolderCount = 0;
//		ParsedData pd = new ParsedData();
//		HashMap<String, String> hm = new HashMap<String, String>();
//		int lineCount = 0;
//		for (String line : lines) {
//			lineCount++;
//			if (lineCount < 30) {
//				if (lineCount < 5) {
//					// Attempt to parse names
//					try {
//						Matcher m = np.matcher(line);
//
//						while (m.find()) {
//							Name name = new Name();
//							name.firstName = m.group(2);
//							name.lastName = m.group(1);
//							pd.names.add(name);
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//
//				}
//
//				try {
//					Matcher m1 = ccc1.matcher(line);
//					Matcher m2 = ccc2.matcher(line);
//					Matcher m3 = ccc3.matcher(line);
//					Matcher chm = cardHolder.matcher(line);
//					if (chm.find()) {
//						cardHolderCount += 1;
//						if (cardHolderCount < 2) {
//							Name n = new Name();
//							pd.names.add(n);
//							n.cc = true;
//							String[] names = chm.group(1).split(" ");
//							try {
//								n.firstName = names[0];
//								n.lastName = names[names.length - 1];
//							} catch (Exception ex) {
//
//							}
//						} else if (cardHolderCount == 2) {
//
//							String[] adds = chm.group(1).split("\\*");
//							try {
//								pd.add2 = adds[0];
//								pd.city = adds[1];
//								pd.state = adds[3];
//								pd.zip = adds[4];
//							} catch (Exception ex) {
//
//							}
//						}
//					}
//					if (m1.find()) {
//						try {
//							Name n = new Name();
//							pd.names.add(n);
//							n.cc = true;
//							n.firstName = m1.group(1);
//							n.lastName = m1.group(2);
//
//						} catch (Exception nm) {
//
//						}
//					} else if (m2.find()) {
//						try {
//
//							pd.add2 = m2.group(1);
//
//						} catch (Exception nm) {
//
//						}
//
//					} else if (m3.find()) {
//						try {
//
//							pd.city = m3.group(1);
//							pd.state = m3.group(2);
//							pd.zip = m3.group(3);
//
//						} catch (Exception nm) {
//
//						}
//
//					}
//
//				} catch (Exception ex) {
//					ex.printStackTrace();
//				}
//
//			}
//			if (line.contains("TKT-T")) {
//
//				try {
//					Matcher m = p.matcher(line);
//					if (m.find()) {
//
//						if (m.groupCount() >= 2) {
//							String type = m.group(2);
//							if (type.equals("CASH") || type.equals("CHECK")) {
//								pd.fop = m.group(2);
//							} else {
//								if (type.startsWith("CC")) {
//									pd.fop = "CC";
//									try {
//										pd.cardType = type.substring(2, 4);
//										hm.put(pd.last4, pd.cardType);
//										pd.last4 = type.substring(type.length() - 4);
//									} catch (StringIndexOutOfBoundsException ec) {
//										// Ignore (example: "TKT-T/TBC/CC/21184499436/0001/$" -- no
//										// CC"
//									}
//								} else if (type.startsWith("XX")) {
//									pd.fop = "CC";
//									hm.put(pd.last4, null);
//									pd.last4 = type.substring(type.length() - 4);
//									try {
//										Matcher m2 = pp.matcher(line);
//										if (m2.find()) {
//											pd.ticketPrice = Double.parseDouble(m2.group());
//										}
//									} catch (Exception e) {
//										e.printStackTrace();
//									}
//
//								}
//							}
//						}
//					}
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//			} else if (line.contains("FOP")) {
//
//				try {
//					Matcher m = p2.matcher(line);
//					if (m.find()) {
//
//						String type = m.group(1);
//						if (type.equals("MC")) {
//							pd.cardType = "CA";
//						} else {
//							pd.cardType = m.group(1);
//						}
//
//						pd.last4 = m.group(2);
//						pd.expiration = m.group(3);
//
//					}
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			} else {
//				try {
//					Matcher emm = em.matcher(line);
//
//					if (emm.find()) {
//						String email = emm.group(1);
//						email = email.replace("*", "@");
//						pd.emails.add(email);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
////		pd.print();
//		if (test.contains("EDQP70")) {
//			try {
//				Thread.sleep(2 * 1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//		return pd;
//	}
//}
