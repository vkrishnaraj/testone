package com.bagnet.nettracer.tracing.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.commons.codec.language.Soundex;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.DozerBeanMapperSingletonWrapper;

import aero.nettracer.fs.model.Bag;
import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;
import aero.nettracer.fs.model.PnrData;
import aero.nettracer.fs.model.Reservation;
import aero.nettracer.fs.model.Segment;
import aero.nettracer.serviceprovider.wt_1_0.common.Ohd;

import com.bagnet.nettracer.tracing.bmo.OtherSystemInformationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.OtherSystemInformation;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.forms.ClaimForm;
import com.bagnet.nettracer.tracing.utils.ntfs.Name;
import com.bagnet.nettracer.tracing.utils.ntfs.ParsedData;



public class ClaimUtils {

	private static Soundex sndx = new Soundex();
	private static DoubleMetaphone dmp = new DoubleMetaphone();

	private static String tktRegEx = "^TKT-T/([A-Z]{3})/([A-Z0-9]+)/[0-9]{0,}/[0-9]{0,}[//(\\d+.\\d+)]{0,}";
	private static String fop = "^FOP-\\s{0,}\\$([A-Z]{2})X+([0-9]{4})/([0-9]{4})";
	private static String price = "\\d+\\.\\d+";
	private static String nm = "\\s+\\d\\.\\s{0,}\\d\\s{0,}([A-Z]+)/([A-Z]+)";
	private static String email = "\\d\\.WEB\\*EMAIL-([A-Z0-9\\.]+\\*[A-Z0-9]+\\.[A-Z]+)";
	private static String cc1 = "\\d\\.ADDR1\\*([A-Z]+)\\s([A-Z]+)";
	private static String cc2 = "\\d\\.ADDR2\\*([0-9A-Z\\s]+)";
	private static String cc3 = "\\d\\.ADDR3\\*([A-Z\\s]+)\\s([A-Z]{2})\\s([0-9]{5})";
	private static String ch = "\\d\\.CARDHOLDER:\\s([0-9A-Z\\s\\*]+)";

	private static Pattern p = Pattern.compile(tktRegEx);
	private static Pattern p2 = Pattern.compile(fop);
	private static Pattern pp = Pattern.compile(price);
	private static Pattern np = Pattern.compile(nm);
	private static Pattern em = Pattern.compile(email);
	private static Pattern ccc1 = Pattern.compile(cc1);
	private static Pattern ccc2 = Pattern.compile(cc2);
	private static Pattern ccc3 = Pattern.compile(cc3);
	private static Pattern cardHolder = Pattern.compile(ch);

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
		LinkedHashSet<Person> persons = new LinkedHashSet<Person>();
		reservation.setPassengers(persons);
		reservation.setPhones(new LinkedHashSet<Phone>());
		reservation.setPnrData(pnrData);
		
		if (ntIncident != null) {
			OtherSystemInformation osi = OtherSystemInformationBMO.getOsi(ntIncident.getIncident_ID(), null);
			if (osi != null && osi.getInfo().length() > 0) {
				pnrData.setPnrData(osi.getInfo());
				ParsedData pd = parseInterestingData(osi.getInfo());

				reservation.setCcNumber(pd.last4);
				reservation.setTicketAmount(pd.ticketPrice);
				reservation.setFormOfPayment(pd.fop);
				reservation.setCcType(pd.cardType);
				
				try {
					reservation.setCcExpMonth(Integer.parseInt(pd.expiration.substring(0, 2)));
					reservation.setCcExpYear(Integer.parseInt(pd.expiration.substring(2)));
				} catch (Exception e ) {
					// Ignore
				}
				
				for (Name name : pd.names) {
					Person p1 = new Person();
					p1.setReservation(reservation);
					persons.add(p1);


					p1.setFirstName(name.firstName);
					p1.setLastName(name.lastName);
					
					String fn = p1.getFirstName();
					if (fn != null) {
						p1.setFirstName(fn);
						try {
							p1.setFirstNameDmp(dmp.encode(fn));
							p1.setFirstNameSoundex(sndx.encode(fn));
						} catch (Exception e) {
	
						}
					}
					
	
					String ln = p1.getLastName();
					if (ln != null) {
	
						p1.setLastName(ln);
						try {
							p1.setLastNameDmp(dmp.encode(ln));
							p1.setLastNameSoundex(sndx.encode(ln));
						} catch (Exception e) {
	
						}
					}


					if (name.cc == true) {
						reservation.setPurchaser(p1);
						p1.setReservation(reservation);
						p1.setCcContact(true);
						Set<FsAddress> ccaddresses = new LinkedHashSet<FsAddress>();
						p1.setAddresses(ccaddresses);

						FsAddress a = new FsAddress();
						a.setPerson(p1);
						ccaddresses.add(a);

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
						p1.setReservation(reservation);
						persons.add(p1);
					}
					p1.setEmailAddress(email);
				}


			}	
		}
		

		
		
		reservation.setSegments(new LinkedHashSet<Segment>());
		pnrData.setReservation(reservation);
		
		// set the fraud incident
		FsIncident fsIncident = new FsIncident();
		fsIncident.setAirline(user.getCompanycode_ID());
		fsIncident.setReservation(reservation);
		reservation.setIncident(fsIncident);
		person.setIncident(fsIncident);
		fsIncident.setClaim(claim);
		
		Set<Person> passengers = new LinkedHashSet<Person>();

		if (ntIncident == null) {
			
		} else {		
			Set<Passenger> paxSet = ntIncident.getPassengers();
	
			if (paxSet != null) {
				boolean firstPax = true;
				for (Passenger pax : paxSet) {
					Person p = getPersonFromPax(pax);
					passengers.add(p);
					p.setIncident(fsIncident);
					
					if (firstPax) {
						claimants.clear();
						person = getPersonFromPax(pax);
						person.setClaim(claim);
						claimants.add(person);
						firstPax = false;
					}
			
				}
			}
		}
		
		fsIncident.setPassengers(passengers);
		if (ntIncident != null) {
			fsIncident.setAirlineIncidentId(ntIncident.getIncident_ID());
			fsIncident.setTimestampClosed(ntIncident.getClosedate());
			Date createDate = new Date(ntIncident.getCreatedate().getTime() + ntIncident.getCreatetime().getTime());
			fsIncident.setTimestampOpen(createDate);
			Set<Bag> bags = new LinkedHashSet<Bag>();
	
			int numberOfBdos = 0;
			if (ntIncident.getItemlist() != null && ntIncident.getItemlist().size() > 0) {
				for (Item it : ntIncident.getItemlist()) {
					if (it != null) {
						Bag bag = new Bag();
						bag.setBagType(it.getBagtype());
						bag.setBagColor(it.getColor());
	
						String manu = null;
						manu = it.getManufacturer();
						bag.setManufacturer(manu);
						bag.setDescription(it.getDamage());
						bag.setIncident(fsIncident);
	
						if (it.getBdo() != null) {
							numberOfBdos += 1;
						}
						bags.add(bag);
					}
				}
			}
	
			fsIncident.setBags(bags);
			fsIncident.setNumberOfBdos(numberOfBdos);
		
			
			// collect amount from incident payouts (interim or temporary)
			double amount = 0;
			double amountPaid = 0;
			if (ntIncident.getExpenselist() != null) {
				for (ExpensePayout ep : ntIncident.getExpenselist()) {
					if (ep != null && !ep.getPaycode().equals("DEL")) {
						if (ep.getStatus().getStatus_ID() == TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED) {
							amountPaid += ep.getCheckamt();
							amountPaid += ep.getVoucheramt();
							amountPaid += ep.getCreditCardRefund();
						}
						amount += ep.getCheckamt();
						amount += ep.getVoucheramt();
						amount += ep.getCreditCardRefund();
						currency = ep.getCurrency_ID();
					}
				}
			}
			
			claim.setAmountPaid(amountPaid);
			claim.setAmountClaimed(amount);
			claim.setClaimType(ntIncident.getItemtype_ID());
			claim.setNtIncident(ntIncident);
			ntIncident.setClaim(claim);
		}
		claim.setAmountClaimedCurrency(currency);
		claim.setClaimants(claimants);
		claim.setIncident(fsIncident);
		// create the claim
		claim.setStatus(status);
		
		Segment s = new Segment();
		s.setClaim(claim);
		LinkedHashSet<Segment> segments = new LinkedHashSet<Segment>();
		segments.add(s);
		claim.setSegments(segments);
		
		return claim;
		
	}

	private static Person getPersonFromPax(Passenger pax) {
		Person p = new Person();
		Set<FsAddress> paxAddresses = new LinkedHashSet<FsAddress>();
		Set<Phone> paxPhones = new LinkedHashSet<Phone>();
		
		if (pax.getAddresses() != null) {
			FsAddress a = new FsAddress();
			a.setPerson(p);
			paxAddresses.add(a);

			com.bagnet.nettracer.tracing.db.Address add = pax.getAddress(0);
			a.setAddress1(add.getAddress1());
			a.setAddress2(add.getAddress2());
			a.setCity(add.getCity());
			a.setCountry(add.getCountrycode_ID());

			a.setReservation(null);
			a.setState(add.getState_ID());
			a.setZip(add.getZip());
			p.setEmailAddress(add.getEmail());

			processAndAddPhone(p, paxPhones, add.getHomephone(), Phone.HOME);
			processAndAddPhone(p, paxPhones, add.getMobile(), Phone.MOBILE);
			processAndAddPhone(p, paxPhones, add.getWorkphone(), Phone.WORK);
			processAndAddPhone(p, paxPhones, add.getPager(), Phone.PAGER);
			processAndAddPhone(p, paxPhones, add.getAltphone(), Phone.ALTERNATE);

		}

		p.setPhones(paxPhones);
		p.setAddresses(paxAddresses);

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
		
		return p;
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

	private static void processAndAddPhone(Person p, Set<Phone> phones,
			String phone, int type) {
		if (phone != null && phone.length() > 0) {
			Phone ph = new Phone();
			ph.setPerson(p);
			ph.setType(type);
			ph.setPhoneNumber(com.bagnet.nettracer.tracing.utils.StringUtils
					.removeNonNumeric(phone));
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
							String[] names = chm.group(1).split("[\\s\\*]+");
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
										pd.last4 = type
												.substring(type.length() - 4);
									} catch (StringIndexOutOfBoundsException ec) {
										// Ignore (example:
										// "TKT-T/TBC/CC/21184499436/0001/$" --
										// no
										// CC"
									}
								} else if (type.startsWith("XX")) {
									pd.fop = "CC";
									hm.put(pd.last4, null);
									pd.last4 = type
											.substring(type.length() - 4);
									try {
										Matcher m2 = pp.matcher(line);
										if (m2.find()) {
											pd.ticketPrice = Double
													.parseDouble(m2.group());
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

}
