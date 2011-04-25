package com.bagnet.nettracer.tracing.utils.ntfs;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.commons.codec.language.Soundex;
import org.hibernate.Session;

import aero.nettracer.fs.model.Bag;
import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;
import aero.nettracer.fs.model.PnrData;
import aero.nettracer.fs.model.Reservation;
import aero.nettracer.fs.model.Segment;
import aero.nettracer.fs.model.detection.TraceResponse;
import aero.nettracer.selfservice.fraud.ClaimRemote;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.OtherSystemInformationBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.OtherSystemInformation;
import com.bagnet.nettracer.tracing.db.Passenger;

public class ClaimUtil {

	private static Soundex sndx = new Soundex();
	private static DoubleMetaphone dmp = new DoubleMetaphone();

	// US AIRWAYS PARSING
	static String tktRegEx = "^TKT-T/([A-Z]{3})/([A-Z0-9]+)/[0-9]{0,}/[0-9]{0,}[//(\\d+.\\d+)]{0,}";
	static String fop = "^FOP-\\s{0,}\\$([A-Z]{2})X+([0-9]{4})/([0-9]{4})";
	static String price = "\\d+\\.\\d+";
	static String nm = "\\s+\\d\\.\\s{0,}\\d\\s{0,}([A-Z]+)/([A-Z]+)";
	static String email = "\\d\\.WEB\\*EMAIL-([A-Z0-9\\.]+\\*[A-Z0-9]+\\.[A-Z]+)";
	static String cc1 = "\\d\\.ADDR1\\*([A-Z]+)\\s([A-Z]+)";
	static String cc2 = "\\d\\.ADDR2\\*([0-9A-Z\\s]+)";
	static String cc3 = "\\d\\.ADDR3\\*([A-Z\\s]+)\\s([A-Z]{2})\\s([0-9]{5})";
	static String ch = "\\d\\.CARDHOLDER:\\s([0-9A-Z\\s]+)";

	static Pattern p = Pattern.compile(tktRegEx);
	static Pattern p2 = Pattern.compile(fop);
	static Pattern pp = Pattern.compile(price);
	static Pattern np = Pattern.compile(nm);
	static Pattern em = Pattern.compile(email);
	static Pattern ccc1 = Pattern.compile(cc1);
	static Pattern ccc2 = Pattern.compile(cc2);
	static Pattern ccc3 = Pattern.compile(cc3);
	static Pattern cardHolder = Pattern.compile(ch);
	public static HashMap<String, Integer> alreadyImported = new HashMap<String, Integer>();

	public static String echoTest(String s) throws NamingException {
		try {
			Context ctx = ConnectionUtil.getInitialContext();
			ClaimRemote o = (ClaimRemote) ctx.lookup("NTServices_1_0/ClaimBean/remote");
			return o.echoTest(s);
		} catch (NamingException e) {
			e.printStackTrace();
			throw e;
		}
	}

	// public static boolean submitClaim(FSClaim claim) throws NamingException{
	// try{
	// Context ctx = ConnectionUtil.getInitialContext();
	// ClaimRemote o = (ClaimRemote)
	// ctx.lookup("NTServices_1_0/ClaimBean/remote");
	// return o.insertClaim(claim);
	// } catch (NamingException e){
	// e.printStackTrace();
	// throw e;
	// }
	// }

	public static File createFsIncident(Incident inc) throws Exception {
		try {

			// Get Incident as inc
			File file = new File();
			
			FsIncident i = new FsIncident();
			i.setFile(file);
			file.setIncident(i);
			FsClaim c = null;
			Reservation r = null;

			i.setAirlineIncidentId(inc.getIncident_ID());
			Date createDate = new Date(inc.getCreatedate().getTime() + inc.getCreatetime().getTime());
			i.setIncidentCreated(createDate);

			i.setIncidentDescription(null);
			i.setIncidentType(inc.getItemtype_ID());
			Date travelDate = null;
			int itinComplexity = 0;
			Set<Itinerary> itin = inc.getItinerary();
			Set<Segment> segments = new LinkedHashSet<Segment>();
			if (itin != null) {
				for (Itinerary it : itin) {
					if (it.getItinerarytype() == TracingConstants.PASSENGER_ROUTING) {
						Segment seg = new Segment();
						seg.setAirline(it.getAirline());
						seg.setArrival(it.getLegto());
						seg.setClaim(null);
						seg.setIncident(i);
						seg.setDeparture(it.getLegfrom());
						seg.setFlight(it.getFlightnum());
						seg.setReservation(null);
						seg.setDate(it.getDepartdate());
						if (travelDate == null) {
							travelDate = it.getDepartdate();
						}
						itinComplexity += 1;
						segments.add(seg);
					}
				}
			}

			i.setSegments(segments);
			i.setItinComplexity(itinComplexity);

			// IGNORE DELIVERY PAYOUTS AS BDOS CAN CREATE THESE. LIMIT TO
			// APPROVED
			// PAYOUTS
			double amount = 0;
			boolean usePayoutAmount = false;
			for (ExpensePayout ep : inc.getExpenselist()) {
				if (!ep.getPaycode().equals("DEL")) {
					if (ep.getStatus().getStatus_ID() == TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED) {
						if (c == null) {
							c = new Claim();
							c.setIncident(i);
							i.setClaim(c);

						}
						amount += ep.getCheckamt();
						amount += ep.getVoucheramt();
						if (c.getAmountClaimed() == 0 || usePayoutAmount == true) {
							usePayoutAmount = true;
							c.setAmountClaimed(amount);
							c.setAmountClaimedCurrency(ep.getCurrency_ID());
						}
						c.setAmountPaid(amount);

					}
				}
			}

			i.setTimestampClosed(inc.getClosedate());
			i.setTimestampOpen(createDate);

			if (inc.getClosedate() != null) {
				long timeOpenMillis = inc.getClosedate().getTime() - inc.getCreatedate().getTime();
				long timeOpenDays = timeOpenMillis / 1000 / 60 / 60 / 24;
				i.setNumberDaysOpen((int) timeOpenDays);
			} else {

			}

			Set<Bag> bags = new LinkedHashSet<Bag>();

			int numberOfBdos = 0;
			if (inc.getItemlist() != null && inc.getItemlist().size() > 0) {
				for (Item it : inc.getItemlist()) {
					if (it != null) {
						Bag bag = new Bag();
						bag.setBagType(it.getBagtype());
						bag.setBagColor(it.getColor());

						String manu = null;
						if (it.getManufacturer_ID() > 0) {
							if (it.getManufacturer_ID() == TracingConstants.MANUFACTURER_OTHER_ID) {
								if (it.getManufacturer_other() != null && it.getManufacturer_other().length() > 0) {
									manu = it.getManufacturer_other();
								}
							} else {
								manu = it.getCachedManufacturerDescription();
							}
						}
						bag.setManufacturer(manu);
						bag.setDescription(it.getDamage());
						bag.setIncident(i);

						if (it.getBdo() != null) {
							numberOfBdos += 1;
						}
						bags.add(bag);

					}

				}
			}

			i.setBags(bags);
			i.setNumberOfBdos(numberOfBdos);

			Set<Person> passengers = new LinkedHashSet<Person>();

			Set<Passenger> paxSet = inc.getPassengers();

			if (paxSet != null) {
				for (Passenger pax : paxSet) {
					Person p = new Person();
					p.setIncident(i);
					passengers.add(p);

					Set<FsAddress> addresses = new LinkedHashSet<FsAddress>();
					Set<Phone> phones = new LinkedHashSet<Phone>();

					if (pax.getAddresses() != null) {
						FsAddress a = new FsAddress();
						a.setPerson(p);
						addresses.add(a);

						com.bagnet.nettracer.tracing.db.Address add = pax.getAddress(0);
						a.setAddress1(add.getAddress1());
						a.setAddress2(add.getAddress2());
						a.setCity(add.getCity());
						a.setCountry(add.getCountrycode_ID());

						a.setReservation(null);
						a.setState(add.getState_ID());
						a.setZip(add.getZip());
						p.setEmailAddress(add.getEmail());

						processAndAddPhone(p, phones, add.getHomephone(), Phone.HOME);
						processAndAddPhone(p, phones, add.getMobile(), Phone.MOBILE);
						processAndAddPhone(p, phones, add.getWorkphone(), Phone.WORK);
						processAndAddPhone(p, phones, add.getPager(), Phone.PAGER);
						processAndAddPhone(p, phones, add.getAltphone(), Phone.ALTERNATE);

					}

					p.setPhones(phones);
					p.setAddresses(addresses);

					p.setDescription(null);
					String fn = pax.getFirstname();
					if (fn != null) {
						p.setFirstName(fn);
						try {
							p.setFirstNameDmp(dmp.encode(fn));
							p.setFirstNameSoundex(sndx.encode(fn));
						} catch (Exception e) {

						}
					}
					p.setMiddleName(pax.getMiddlename());

					String ln = pax.getLastname();
					if (ln != null) {

						p.setLastName(ln);
						try {
							p.setLastNameDmp(dmp.encode(ln));
							p.setLastNameSoundex(sndx.encode(ln));
						} catch (Exception e) {

						}
					}

					if (pax.getMembership() != null) {
						AirlineMembership m = pax.getMembership();
						p.setFfAirline(m.getCompanycode_ID());
						p.setFfNumber(m.getMembershipnum());
					}
				}
			}

			OtherSystemInformation osi = OtherSystemInformationBMO.getOsi(inc.getIncident_ID(), null);
			if (osi != null && osi.getInfo().length() > 0) {
				r = new Reservation();
				r.setIncident(i);
				r.setAirline(inc.getStationcreated().getCompany().getCompanyCode_ID());

				ParsedData pd = parseInterestingData(osi.getInfo());

				r.setFormOfPayment(pd.fop);
				r.setCcType(pd.cardType);
				r.setCcNumLastFour(pd.last4);
				if (pd.expiration != null && pd.expiration.length() == 4) {
					r.setCcExpYear(Integer.parseInt(pd.expiration.substring(2)));
					r.setCcExpMonth(Integer.parseInt(pd.expiration.substring(0, 2)));
				}

				LinkedHashSet<Person> persons = new LinkedHashSet<Person>();
				r.setPassengers(persons);

				for (Name name : pd.names) {
					Person p1 = new Person();
					p1.setReservation(r);
					persons.add(p1);

					p1.setFirstName(name.firstName);
					p1.setLastName(name.lastName);
					
					if (name.firstName != null) {
						p1.setFirstNameDmp(dmp.encode(name.firstName));
						p1.setFirstNameSoundex(sndx.encode(name.firstName));
					}
					
					if (name.lastName != null) {
						p1.setLastNameDmp(dmp.encode(name.lastName));
						p1.setLastNameSoundex(sndx.encode(name.lastName));
					}

					if (name.cc == true) {
						p1.setCcContact(true);
						Set<FsAddress> addresses = new LinkedHashSet<FsAddress>();
						p1.setAddresses(addresses);

						FsAddress a = new FsAddress();
						a.setPerson(p1);
						addresses.add(a);

						a.setAddress1(pd.add2);

						a.setCity(pd.city);
						a.setZip(pd.zip);
						a.setState(pd.state);

					}
				}

				for (int j = 0; j < pd.emails.size(); ++j) {
					String email = pd.emails.get(j);
					Person p1 = null;
					if (persons.size() >= 1 + j) {
						int current = 0;
						for (Person pp : persons) {
							if (current == j) {
								p1 = pp;
								break;
							}
							++current;
						}
					} else {
						p1 = new Person();
						p1.setReservation(r);
						persons.add(p1);
					}
					p1.setEmailAddress(email);
				}

				PnrData pnr = new PnrData();
				pnr.setPnrData(osi.getInfo());
				pnr.setReservation(r);
				r.setPnrData(pnr);
				r.setTicketAmount(pd.ticketPrice);
				osi.getInfo();
			}

			i.setPassengers(passengers);

			i.setRemarks(null);
			i.setReservation(r);
			i.setClaim(c);
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	private static void processAndAddPhone(Person p, Set<Phone> phones, String phone, int type) {
		if (phone != null && phone.length() > 0) {
			Phone ph = new Phone();
			ph.setPerson(p);
			ph.setType(type);
			ph.setPhoneNumber(com.bagnet.nettracer.tracing.utils.StringUtils.removeNonNumeric(phone));
			phones.add(ph);
		}
	}

	public static ParsedData parseInterestingData(String test) {

		String[] lines = test.split("\n");
		int cardHolderCount = 0;
		ParsedData pd = new ParsedData();
		HashMap<String, String> hm = new HashMap<String, String>();
		int lineCount = 0;
		for (String line : lines) {
			lineCount++;
			if (lineCount < 30) {
				if (lineCount < 5) {
					// Attempt to parse names
					try {
						Matcher m = np.matcher(line);

						while (m.find()) {
							Name name = new Name();
							name.firstName = m.group(2);
							name.lastName = m.group(1);
							pd.names.add(name);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				try {
					Matcher m1 = ccc1.matcher(line);
					Matcher m2 = ccc2.matcher(line);
					Matcher m3 = ccc3.matcher(line);
					Matcher chm = cardHolder.matcher(line);
					if (chm.find()) {
						cardHolderCount += 1;
						if (cardHolderCount < 2) {
							Name n = new Name();
							pd.names.add(n);
							n.cc = true;
							String[] names = chm.group(1).split(" ");
							try {
								n.firstName = names[0];
								n.lastName = names[names.length - 1];
							} catch (Exception ex) {

							}
						} else if (cardHolderCount == 2) {

							String[] adds = chm.group(1).split("\\*");
							try {
								pd.add2 = adds[0];
								pd.city = adds[1];
								pd.state = adds[3];
								pd.zip = adds[4];
							} catch (Exception ex) {

							}
						}
					}
					if (m1.find()) {
						try {
							Name n = new Name();
							pd.names.add(n);
							n.cc = true;
							n.firstName = m1.group(1);
							n.lastName = m1.group(2);

						} catch (Exception nm) {

						}
					} else if (m2.find()) {
						try {

							pd.add2 = m2.group(1);

						} catch (Exception nm) {

						}

					} else if (m3.find()) {
						try {

							pd.city = m3.group(1);
							pd.state = m3.group(2);
							pd.zip = m3.group(3);

						} catch (Exception nm) {

						}

					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
			if (line.contains("TKT-T")) {

				try {
					Matcher m = p.matcher(line);
					if (m.find()) {

						if (m.groupCount() >= 2) {
							String type = m.group(2);
							if (type.equals("CASH") || type.equals("CHECK")) {
								pd.fop = m.group(2);
							} else {
								if (type.startsWith("CC")) {
									pd.fop = "CC";
									try {
										pd.cardType = type.substring(2, 4);
										hm.put(pd.last4, pd.cardType);
										pd.last4 = type.substring(type.length() - 4);
									} catch (StringIndexOutOfBoundsException ec) {
										// Ignore (example:
										// "TKT-T/TBC/CC/21184499436/0001/$" --
										// no
										// CC"
									}
								} else if (type.startsWith("XX")) {
									pd.fop = "CC";
									hm.put(pd.last4, null);
									pd.last4 = type.substring(type.length() - 4);
									try {
										Matcher m2 = pp.matcher(line);
										if (m2.find()) {
											pd.ticketPrice = Double.parseDouble(m2.group());
										}
									} catch (Exception e) {
										e.printStackTrace();
									}

								}
							}
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (line.contains("FOP")) {

				try {
					Matcher m = p2.matcher(line);
					if (m.find()) {

						String type = m.group(1);
						if (type.equals("MC")) {
							pd.cardType = "CA";
						} else {
							pd.cardType = m.group(1);
						}

						pd.last4 = m.group(2);
						pd.expiration = m.group(3);

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					Matcher emm = em.matcher(line);

					if (emm.find()) {
						String email = emm.group(1);
						email = email.replace("*", "@");
						pd.emails.add(email);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return pd;
	}

	public static void saveAndTraceForFraud(HttpSession session, Incident iDTO, boolean isNew) throws Exception {
		if (isNew && PropertyBMO.isTrue(PropertyBMO.CENTRAL_FRAUD_CHECK_ENABLED)) {
			
			File file = createFsIncident(iDTO);
			ClaimRemote remote = ConnectionUtil.getClaimRemote();
			long remoteFileId = remote.insertFile(file);
			file.setSwapId(remoteFileId);
			Session sess = HibernateWrapper.getSession().openSession();
			sess.save(file);
			sess.close();
			TraceResponse results = remote.traceFile(file.getSwapId(), PropertyBMO.getValueAsInt(PropertyBMO.CENTRAL_FRAUD_DAM_MISSING_TIMEOUT));
			session.setAttribute("results", results.getMatchHistory());
		}
	}

}
