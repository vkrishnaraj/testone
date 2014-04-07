package aero.nettracer.fs.utilities.tracing;

// TODO: MASS TRIMMING

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import aero.nettracer.fs.bmo.FsNameAliasBMO;
import aero.nettracer.fs.model.CreditCard;
import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIPAddress;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.FsNameAlias;
import aero.nettracer.fs.model.FsReceipt;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;
import aero.nettracer.fs.model.Reservation;
import aero.nettracer.fs.model.detection.MatchDetail;
import aero.nettracer.fs.model.detection.MatchDetail.MatchType;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.fs.utilities.GeoCode;
import aero.nettracer.fs.utilities.WhiteListUtil;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

public class Consumer implements Runnable{

	private static final Logger logger = Logger.getLogger(Consumer.class);
	protected static final String SIMILAR_NAME = "Name Match (Similar)";
	protected static final String DOUBLE_METAPHONE_MATCH = "Name Match (Double Metaphone)";
	protected static final String EXACT_NAME_MATCH = "Name Match (Exact)";
	protected static final String SOUNDEX_MATCH = "Name Match (Soundex)";
	
	protected static final String DOUBLE_METAPHONE_NICKNAME_MATCH = "Nickname Match (Double Metaphone)";
	protected static final String SIMILAR_NICKNAME_MATCH = "Nickname Match (Similar)";
	protected static final String EXACT_NICKNAME_MATCH = "Nickname Match (Exact)";
	protected static final String SOUNDEX_NICKNAME_MATCH = "Nickname Match (Soundex)";
	
	public static final int MATCH = 3;
	public static final Integer integerZero = new Integer(0);
	public static final double MIN_MATCH_SCORE = 10;
	public static final double P_SOUNDEX = 4;
	public static final double P_METAPHONE = 4;
	public static final double P_NAME = 25;
	public static final double P_PHONE = 10;
	public static final double P_CC_TYPE = 5;
	public static final double P_CC_EXP = 5;
	public static final double P_CC_4 = 5;
	public static final double P_CC_4_TYPE_EXP = 20;
	public static final double P_CC_6 = 5;
	public static final double P_SSN = 30;
	public static final double P_DRIVERS = 30;
	public static final double P_FFN = 15;
	public static final double P_PASSPORT = 30;
	public static final double P_EMAIL = 20;
	
	public static final double P_NICK_NAME_MULTIPLIER = 0.5;

	private static final double ADDRESS_FAR_PROXIMITY = 5;
	private static final double ADDRESS_CLOSE_PROXIMITY = 20;

	private static final double ADDRESS_CLOSE_PROXIMITY_LIMIT = .15;

	private static final double ADDRESS_SIMILAR = 40;

	private static final double PHONE_MATCH = 30;
	private static final double PARTIAL_PHONE_MATCH = 15;
	private static final double WHITELIST_MATCH = 0;
	private static final double P_DOB = 6;	
	private static final double P_IP = 30;	
	
	private static final Pattern invalidPhone = Pattern.compile("^(0+|1+|3+|4+|5+|6+|7+|8+|9+)$");
	
	private static final long MAX_THREAD_WAIT_TIME = 300000;//5min
	
	
	
	private int threadnumber;
	private int threadtype;
	private ArrayBlockingQueue<MatchHistory> matchQueue;
	private ThreadContainer tc;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Consumer(ArrayBlockingQueue queue, int type, ThreadContainer tc) throws Exception{
		if (type == MATCH){
			setMatchQueue(queue);
		} else {
			throw new Exception("unable to create consumer");
		}
		this.threadnumber = tc.getId();
		this.threadtype = type;
		this.tc = tc;
	}
	
	
	@Override
	public void run() {
		while(true){
			tc.setWaiting(true);
			try{
				if (this.threadtype == MATCH){
					MatchHistory match = ConsumerQueueManager.getInstance().take();
					if(match != null){
						tc.setStartTime(new Date());
						tc.setWaiting(false);
						logger.debug("consumer " + threadnumber);
						processMatch(match);
					} else {
						try{
							ConsumerQueueManager manager = ConsumerQueueManager.getInstance();
							synchronized(manager){
								manager.wait(MAX_THREAD_WAIT_TIME);
							}
						} catch (InterruptedException e){
							e.printStackTrace();
							//ignore
						}
					}
				}
			}catch (Exception e){
				e.printStackTrace();
			}finally{

			}
		}
	}
	
	private static void processMatch(MatchHistory match){
		try{
			long timeout = 1000;
			Date sdate = new Date();
			Date edate;
			//process match
			if(match.getFile2() != null){
				logger.debug("consumer claim: " + match.getFile2().getId());
				match.setFile2(TraceWrapper.getCacheManager().loadFile(match.getFile2().getId()));
				edate = new Date();
				if(edate.getTime() - sdate.getTime()>timeout){
					System.out.println("load file " + match.getFile2().getId() + ": " + (edate.getTime() - sdate.getTime()));
				}
				sdate = edate;
			} else {
				throw new Exception("file2 is null");
			}

			processPerson(match);
			edate = new Date();
			if(edate.getTime() - sdate.getTime()>timeout)System.out.println("person: " + (edate.getTime() - sdate.getTime()));
			sdate = edate;
			processAddress(match);
			edate = new Date();
			if(edate.getTime() - sdate.getTime()>timeout)System.out.println("address: " + (edate.getTime() - sdate.getTime()));
			sdate = edate;
			processReservation(match);
			edate = new Date();
			if(edate.getTime() - sdate.getTime()>timeout)System.out.println("reservation: " + (edate.getTime() - sdate.getTime()));
			sdate = edate;
			processPhone(match);
			edate = new Date();
			if(edate.getTime() - sdate.getTime()>timeout)System.out.println("phone: " + (edate.getTime() - sdate.getTime()));
			sdate = edate;
			processReceipts(match);
			edate = new Date();
			if(edate.getTime() - sdate.getTime()>timeout)System.out.println("phone: " + (edate.getTime() - sdate.getTime()));
			sdate = edate;
			proccessCreditCards(match);
			edate = new Date();
			if(edate.getTime() - sdate.getTime()>timeout)System.out.println("phone: " + (edate.getTime() - sdate.getTime()));
			sdate = edate;
			processIPs(match);
			edate = new Date();
			if(edate.getTime() - sdate.getTime()>timeout)System.out.println("phone: " + (edate.getTime() - sdate.getTime()));
			
			
			
			if(match.getDetails() == null || match.getOverallScore() <= MIN_MATCH_SCORE){
				//no match details so don't save
				//can expand to include not saving match if min match percent below certain threshold
				return;
			}
			
			// Filter Existing Matches
			filterAndSaveMatch(match);
			MatchHistory mirror = createMirroredMatch(match);
			filterAndSaveMatch(mirror);
			
		}catch (Exception e){
			e.printStackTrace();
		}finally{

		}
	}
	
	private static MatchHistory createMirroredMatch(MatchHistory match) {
		MatchHistory toReturn = new MatchHistory();
		Set<MatchDetail> newDetails = new LinkedHashSet<MatchDetail>();
		if (match.getDetails() != null) {
			for (MatchDetail detail : match.getDetails()) {
				MatchDetail temp = new MatchDetail();
				temp.setDescription(detail.getDescription());
				temp.setMatchtype(detail.getMatchtype());
				temp.setPercent(detail.getPercent());
				temp.setMatch(toReturn);
				temp.setContent1(detail.getContent2());
				temp.setContent2(detail.getContent1());
				newDetails.add(temp);
			}
		}
		toReturn.setCreatedate(match.getCreatedate());
		toReturn.setDeleted(false);
		toReturn.setPrimarymatch(false);
		toReturn.setOverallScore(match.getOverallScore());
		toReturn.setDetails(newDetails);
		toReturn.setFile1(match.getFile2());
		toReturn.setFile2(match.getFile1());
		return toReturn;
	}
	
	private static void filterAndSaveMatch(MatchHistory match) {
		if (match == null) {
			return;
		}
		if (match.getFile1().getMatchingFiles() != null) {
			
			HashMap<Long, List<Double>>matchingMap = match.getFile1().getMatchingFiles();
			List<Double> existingScores = new ArrayList<Double>();
			if (matchingMap.get(new Long(match.getFile2().getId()))!=null){
				existingScores=matchingMap.get(new Long(match.getFile2().getId()));
			}
			
			
			for(Double existingScore:existingScores){
				if (existingScore != null) {
	
					String score1 = existingScore.toString();
					score1 = score1.substring(0, Math.min(5, score1.length()));
					
					String score2 = match.getOverallScore() + "";
					score2 = score2.substring(0, Math.min(5, score2.length()));
	
					
					if (score1.equals(score2)) { 
						logger.debug("Returning because match exists...");
						return;
					}
				}
			}
		}
		//save match
		Transaction t = null;
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			// TODO: Check to see if match already exists???
			t = sess.beginTransaction();
			sess.saveOrUpdate(match);
			logger.debug("Saving match... " + match.getId());
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
				}
			}
		} finally {

			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static Set<CreditCard> getCreditCards(File file){
		Set<CreditCard> ret = new HashSet<CreditCard>();
		if(file != null && file.getIncident() != null && file.getIncident().getReservation() != null){
			Reservation r1 = file.getIncident().getReservation();
			CreditCard c1 = new CreditCard();
			c1.setCcExpMonth(r1.getCcExpMonth());
			c1.setCcExpYear(r1.getCcExpYear());
			c1.setCcNumber(r1.getCcNumber());
			c1.setCcNumLastFour(r1.getCcNumLastFour());
			c1.setCcType(r1.getCcType());
			ret.add(c1); //Does it already map the unique numbers?
		} 
		if(file != null && file.getClaims() != null){
			for(FsClaim claim:file.getClaims()){
				if(claim.getReceipts() != null){
					for(FsReceipt receipt:claim.getReceipts()){
						CreditCard c1 = new CreditCard();
						c1.setCcExpMonth(receipt.getCcExpMonth());
						c1.setCcExpYear(receipt.getCcExpYear());
						c1.setCcNumber(null);
						c1.setCcNumLastFour(receipt.getCcLastFour());
						c1.setCcType(receipt.getCcType());
						ret.add(c1);
					}
				}
			}
		}
		return ret;
	}
	
	private static void processReceipts(MatchHistory match) {
		//Created new processCreditCard method
	}


	private static void processPhone(MatchHistory match) {

		Set<Phone> plist1 = match.getFile1().getPhoneCache();
		if(plist1 == null){
			logger.debug("phone cache is null");
			plist1 = getPhones(match.getFile1());
		}	

		HashSet<String> phoneNumberMatches = new HashSet<String>();
		Set <MatchDetail> details = match.getDetails();
		
		Set<Phone> plist2 = null;
		if(match.getFile2() != null){
			plist2 = getPhones(match.getFile2());
		} 
		
		String hashKey; 
		for (Phone p1: plist1) {
			for (Phone p2: plist2) {
				hashKey = p1.getPhoneNumber() + p2.getPhoneNumber();
				if (validPhone(p1.getPhoneNumber()) && validPhone(p2.getPhoneNumber()) && phoneNumberMatches.add(hashKey)) {
					boolean fullMatch = isFullPhoneMatch(p1, p2);
					boolean partialMatch = false;
					if (!fullMatch) {
						partialMatch = isPartialPhoneMatch(p1, p2); 
					}
					
					if (fullMatch || partialMatch) {
						
						MatchDetail detail = getMatchDetail(match, MatchType.phone);
						detail.setContent1(p1.getPhoneNumber());
						detail.setContent2(p2.getPhoneNumber());
						
						if (p1.getWhitelist() != null || p2.getWhitelist() != null) {
							String description = null;
							/*
							 * MJS: the white-listed phone number was left off due to the fact that 
							 * the airline must give explicit permission to show the phone number.
							 * Even if the number is white-listed, if the airline's policy is to 
							 * not display phone numbers, then we can't display the white-listed 
							 * number along with the description.
							 */
							if (p1.getWhitelist() != null) {
								description = p1.getWhitelist().getDescription();
							} else {
								description = p2.getWhitelist().getDescription();
							}
							detail.setDescription("Phone Number Match (Whitelisted - " + description +" )");
							detail.setPercent(WHITELIST_MATCH);								
						} else if (fullMatch) {
							detail.setDescription("Phone Number Match");
							detail.setPercent(PHONE_MATCH);
						} else {
							detail.setDescription("Partial Phone Number Match");
							detail.setPercent(PARTIAL_PHONE_MATCH);
						}
						
						details.add(detail);
					}
				}
			}
		}
	}
	
	private static MatchDetail getMatchDetail(MatchHistory match, MatchType type) {
		MatchDetail detail = new MatchDetail();
		detail.setMatch(match);
		detail.setMatchtype(type);
		return detail;
	}
	
	private static boolean isFullPhoneMatch(Phone p1, Phone p2) {
		if (p1.getPhoneNumber() == null || p2.getPhoneNumber() == null) {
			return false;
		}
		return p1.getPhoneNumber().equals(p2.getPhoneNumber());
	}
	
	private static boolean isPartialPhoneMatch(Phone p1, Phone p2) {
		String pn1 = p1.getPhoneNumber();
		String pn2 = p2.getPhoneNumber();
		if (pn1 == null || pn2 == null) {
			return false;
		}
		
		boolean partialMatch = false;
		partialMatch |= pn1.contains(pn2);
		partialMatch |= pn2.contains(pn1);
		
		if (!partialMatch && pn1.length() > 7 && pn2.length() > 7) {
			String partialPn1 = pn1.substring(pn1.length() - 7);
			String partialPn2 = pn2.substring(pn2.length() - 7);
			partialMatch |= partialPn1.equals(partialPn2);
		}
		
		return partialMatch;
	}

	private static boolean validPhone(String p){
		if(p == null || p.trim().length() < 5){
			return false;
		}
		Matcher m = invalidPhone.matcher(p.trim());
		return !m.matches();		
	}
	

	public static Set<Person> getPersons(File file, boolean alias){
		LinkedHashSet<Person> ret = new LinkedHashSet<Person>();
		Set<Person> persons =  new LinkedHashSet<Person>();
		if(file.getClaims() != null){
			for(FsClaim claim:file.getClaims()){
				persons.addAll(getPersons(claim));
			}
		}
		if (file.getIncident() != null){
			persons.addAll(getPersons(file.getIncident()));
		}
		
		ret.addAll(persons);
		if(alias && persons != null){
			for(Person person:persons){
				List<FsNameAlias> names = FsNameAliasBMO.getAlias(person.getFirstName());
				if(names!=null)for(FsNameAlias name:names){
					Person toAdd = new Person();
					toAdd.setFirstName(name.getAlias());
					toAdd.setLastName(person.getLastName());
					toAdd.setParent(person);
					ret.add(toAdd);
				}
			}
		}
		
		return ret;
	}
	
	public static Set<Person> getPersons(File file){
		return getPersons(file, false);
	}
	
	public static Set<Person> getPersons(FsIncident incident) {
		LinkedHashSet<Person> ret = new LinkedHashSet<Person>();
		if (incident != null) {
			if (incident.getPassengers() != null) {
				for (Person p : incident.getPassengers()) {
					ret.add(p);
				}
			}
			if (incident.getReservation() != null) {
				if (incident.getReservation().getPurchaser() != null) {
					Person p = incident.getReservation().getPurchaser();
					ret.add(p);
				}

				if (incident.getReservation().getPassengers() != null) {
					for (Person p : incident.getReservation().getPassengers()) {
						ret.add(p);
					}
				}
			}
		}
		return ret;
	}
	
	public static Set<Person> getPersons(FsClaim claim){
		LinkedHashSet<Person> ret = new LinkedHashSet<Person>();
		
		if(claim != null){
			if(claim.getClaimants() != null){
				for(Person p:claim.getClaimants()){
					ret.add(p);
				}
			}
		}
		return ret;
	}

	
	public static Set<FsAddress> getAddresses(File file){
		Set<FsAddress>addresses = new HashSet<FsAddress>();
		if(file.getClaims() != null){
			for(FsClaim claim:file.getClaims()){
				addresses.addAll(getAddresses(claim));
			}
		} 
		if (file.getIncident() != null){
			addresses.addAll(getAddresses(file.getIncident()));
		} 
		return addresses;
	}
	
	// TODO: Opportunity to optimize primary claim's data sets.  We will re-run this over and over.
	// Note: now referenced in producer as well for geocoding.
	public static Set<FsAddress> getAddresses(FsClaim claim){
		HashSet<FsAddress> ret = new LinkedHashSet<FsAddress>();
		
		if(claim != null){
			if(claim.getClaimants() != null){
				for(Person p:claim.getClaimants()){
					for (FsAddress a: p.getAddresses())
						ret.add(a);
				}
			}
			if(claim.getReceipts() != null){
				for(FsReceipt receipt:claim.getReceipts()){
					if(receipt.getAddress() != null){
						ret.add(receipt.getAddress());
					}
				}
			}
		}
		return ret;
	}

	public static Set<FsAddress> getAddresses(FsIncident incident) {
		HashSet<FsAddress> ret = new HashSet<FsAddress>();

		if (incident != null) {
			if (incident.getPassengers() != null) {
				for (Person p : incident.getPassengers()) {
					for (FsAddress a : p.getAddresses()) {
						ret.add(a);
					}
				}
			}
			if (incident.getReservation() != null) {
				if (incident.getReservation().getPurchaser() != null) {
					Person p = incident.getReservation().getPurchaser();
					for (FsAddress a : p.getAddresses()) {
						ret.add(a);
					}
				}
				if (incident.getReservation().getPassengers() != null) {
					for (Person p : incident.getReservation().getPassengers()) {
						for (FsAddress a : p.getAddresses()) {
							ret.add(a);
						}
					}
				}
			}
		}

		return ret;
	}

	
	public static Set<Phone> getPhones(File file){
		Set<Phone> phones = new HashSet<Phone>();
		if(file.getClaims() != null){
			for(FsClaim claim:file.getClaims()){
			phones.addAll(getPhones(claim));
			}
		} 
		if (file.getIncident() != null){
			phones.addAll(getPhones(file.getIncident()));
		}
		return phones;
	}

	private static Set<Phone> getPhones(FsIncident incident) {
		HashSet<Phone> ret = new HashSet<Phone>();
		if(incident != null){
			if(incident.getPassengers() != null){
				for(Person p:incident.getPassengers()){
					if(p.getPhones() != null){
						for(Phone phone:p.getPhones()){
							ret.add(phone);
						}
					}
				}
			}
			if(incident.getReservation() != null){
				if(incident.getReservation().getPassengers() != null){
					for(Person p:incident.getReservation().getPassengers()){
						if(p.getPhones() != null){
							for(Phone phone:p.getPhones()){
								ret.add(phone);
							}
						}
					}
				}
				if(incident.getReservation().getPhones() != null){
					for(Phone p:incident.getReservation().getPhones()){
						ret.add(p);
					}
				}
			}
		}
		return ret;
	}


	public static Set<Phone> getPhones(FsClaim claim){
		HashSet<Phone> ret = new HashSet<Phone>();
		if(claim != null){
			if(claim.getPhones() != null){
				for(Phone phone:claim.getPhones()){
					ret.add(phone);
				}
			}
			if(claim.getClaimants() != null){
				for(Person person:claim.getClaimants()){
					if(person.getPhones() != null){
						for(Phone phone:person.getPhones()){
							ret.add(phone);
						}
					}
				}
			}
			if(claim.getReceipts() != null){
				for(FsReceipt receipt:claim.getReceipts()){
					if(receipt.getPhone() != null){
						ret.add(receipt.getPhone());
					}
				}
			}
		}
		return ret;
	}
	
	protected static void processAddress(MatchHistory match){
		Set<FsAddress> plist1 = match.getFile1().getAddressCache();
		if(plist1 == null){
			plist1 = getAddresses(match.getFile1());
		}	

		
		Set<FsAddress> plist2 = null;
		if(match.getFile2() != null){
			plist2 = getAddresses(match.getFile2());
		} 
		
		Set <MatchDetail> details = match.getDetails();
		HashSet<String> addressHashSet = new HashSet<String>();
		
		boolean hasProxMatch = false;
		boolean hasCloseProxMatch = false;
		for(FsAddress a1:plist1){
			String tas1 ="";
			if(a1.getNormAddress()!=null && !a1.getNormAddress().isEmpty()){
				tas1=a1.getNormAddress();
			} else {
				tas1=WhiteListUtil.normalizeAddress(a1);
			}
			
			for(FsAddress a2:plist2){
				String tas2 ="";
				if(a2.getNormAddress()!=null && !a2.getNormAddress().isEmpty()){
					tas2=a2.getNormAddress();
				} else {
					tas2=WhiteListUtil.normalizeAddress(a2);
				}
				
				String hKey1 = tas1 + "/" + tas2;
				String hKey2 = tas2 + "/" + tas1;
				if (addressHashSet.contains(hKey1) || addressHashSet.contains(hKey2) || a1.getAddress1() == null) {
					continue;
				} else {
					addressHashSet.add(hKey1);
					addressHashSet.add(hKey2);
				}
				
				// If geocoded => US based location
				if (a1.getLattitude() != 0 && a2.getLattitude() != 0) {
					
					double distance = 100;
					
					distance = GeoCode.distanceBetweenPoints(a1.getLattitude(), a1.getLongitude(), a2.getLattitude(), a2.getLongitude());

					
					if (distance < Producer.MILE_SEARCH_RADIUS ) {
						if (a1.getGeocodeType() == GeoCode.ACCURACY_STREET
								&& a2.getGeocodeType() == GeoCode.ACCURACY_STREET) {
							if (distance > ADDRESS_CLOSE_PROXIMITY_LIMIT) {

								String distanceStr = Double.toString(distance);
								distanceStr = distanceStr.substring(0, Math.min(5, distanceStr.length()));	

								MatchDetail detail = new MatchDetail();
								detail.setContent1(a1.getAddress1() + (a1.getAddress2() == null || a1.getAddress2().isEmpty() ? "" : ", " + a1.getAddress2()) + ", " + a1.getCity() + ", " + a1.getState() + " " + a1.getZip());
								detail.setContent2(a2.getAddress1() + (a2.getAddress2() == null || a2.getAddress2().isEmpty() ? "" : ", " + a2.getAddress2()) + ", " + a2.getCity() + ", " + a2.getState() + " " + a2.getZip());
								detail.setDescription("Proximity Match: " + distanceStr + " miles.");
								detail.setMatch(match);
								if(a1.getWhitelist() != null || a2.getWhitelist() != null){
									detail.setPercent(WHITELIST_MATCH);
									detail.setDescription(detail.getDescription() + " (Whitelisted - " + (a1.getWhitelist()!=null?a1.getWhitelist().getDescription():a2.getWhitelist().getDescription()) + ")");
								} else {
									if(hasProxMatch){
										//only counting prox match once
										detail.setPercent(0.0);
									} else {
										detail.setPercent(ADDRESS_FAR_PROXIMITY);
										hasProxMatch = true;
									}
								}
								detail.setMatchtype(MatchType.address);
								details.add(detail);
							} else {
								String distanceStr = Double.toString(distance);
								distanceStr = distanceStr.substring(0, Math.min(5, distanceStr.length()));

								MatchDetail detail = new MatchDetail();
								detail.setContent1(a1.getAddress1() + (a1.getAddress2() == null || a1.getAddress2().isEmpty() ? "" : ", " + a1.getAddress2()) + ", " + a1.getCity() + ", " + a1.getState() + " " + a1.getZip());
								detail.setContent2(a2.getAddress1() + (a2.getAddress2() == null || a2.getAddress2().isEmpty() ? "" : ", " + a2.getAddress2()) + ", " + a2.getCity() + ", " + a2.getState() + " " + a2.getZip());
								detail.setDescription("Close Proximity Match: " + distanceStr + " miles.");
								detail.setMatch(match);
								if(a1.getWhitelist() != null  || a2.getWhitelist() != null ){
									detail.setPercent(WHITELIST_MATCH);
									detail.setDescription(detail.getDescription() + " (Whitelisted - " + (a1.getWhitelist()!=null?a1.getWhitelist().getDescription():a2.getWhitelist().getDescription()) + ")");
								} else {
									if(hasCloseProxMatch){
										detail.setPercent(0.0);
									} else {
										detail.setPercent(ADDRESS_CLOSE_PROXIMITY);
										hasCloseProxMatch = true;
									}
								}
								detail.setMatchtype(MatchType.address);
								details.add(detail);

							}
						}
							
						if((a1.getAddress1()!=null && a1.getAddress1().length()>0)&&(a2.getAddress1()!=null && a2.getAddress1().length()>0)){
							String str1 = a1.getAddress1() + " " + replaceNull(a1.getAddress2());
							String str2 = a2.getAddress1() + " " + replaceNull(a2.getAddress2());
							if (a1.getProvince() != null && a1.getProvince().trim().length() > 0 && a2.getProvince() != null && a2.getProvince().trim().length() > 0 ) {
								str1 = a1.getAddress1() + " " + replaceNull(a1.getAddress2()) + " " + replaceNull(a1.getCity()) + ", " + replaceNull(a1.getProvince());
								str2 = a2.getAddress1() + " " + replaceNull(a2.getAddress2()) + " " + replaceNull(a2.getCity()) + ", " + replaceNull(a2.getProvince());	
							} else {
								str1 = a1.getAddress1() + " " + replaceNull(a1.getAddress2()) + " " + replaceNull(a1.getCity()) + ", " + replaceNull(a1.getState());
								str2 = a2.getAddress1() + " " + replaceNull(a2.getAddress2()) + " " + replaceNull(a2.getCity()) + ", " + replaceNull(a2.getState());
							}
							
							String description = "Similar Address";
							double percent = 0;
							boolean isWhitelisted =  false;
							if(a1.getWhitelist() != null  || a2.getWhitelist() != null ){
								percent = WHITELIST_MATCH;
								isWhitelisted = true;
								description = "Similar Address (Whitelisted - " + (a1.getWhitelist()!=null?a1.getWhitelist().getDescription():a2.getWhitelist().getDescription()) + ")";
							} else {
								percent = ADDRESS_SIMILAR;
							}
							generateStringCompareDetail(match, details, str1, str2, description, percent, 75, .15, MatchType.address, isWhitelisted);
						}
					}
				} else {
					// If country available
					if((a1.getAddress1()!=null && a1.getAddress1().length()>0)&&(a2.getAddress1()!=null && a2.getAddress1().length()>0)){
						
						if (a1.getCountry() != null && a2.getCountry() != null && a1.getCountry().equalsIgnoreCase(a2.getCountry()) && a1.getCountry().trim().length() > 0) { 
							String str1 = null;
							String str2 = null;
							if (a1.getProvince() != null && a1.getProvince().trim().length() > 0 && a2.getProvince() != null && a2.getProvince().trim().length() > 0 ) {
								str1 = a1.getAddress1() + " " + replaceNull(a1.getAddress2()) + " " + replaceNull(a1.getCity()) + ", " + replaceNull(a1.getProvince()) + " "+replaceNull(a1.getCountry());
								str2 = a2.getAddress1() + " " + replaceNull(a2.getAddress2()) + " " + replaceNull(a2.getCity()) + ", " + replaceNull(a2.getProvince()) + " "+replaceNull(a2.getCountry());	
							} else {
								str1 = a1.getAddress1() + " " + replaceNull(a1.getAddress2()) + " " + replaceNull(a1.getCity()) + " " + replaceNull(a1.getState()) + " "+replaceNull(a1.getCountry());
								str2 = a2.getAddress1() + " " + replaceNull(a2.getAddress2()) + " " + replaceNull(a2.getCity()) + " " + replaceNull(a2.getState()) + " "+replaceNull(a2.getCountry());
							}
							
							String description = "Similar Address";
							double percent = 0;
							boolean isWhitelisted =  false;
							if(a1.getWhitelist() != null  || a2.getWhitelist() != null ){
								percent = WHITELIST_MATCH;
								isWhitelisted = true;
								description = "Similar Address (Whitelisted - " + (a1.getWhitelist()!=null?a1.getWhitelist().getDescription():a2.getWhitelist().getDescription()) + ")";
							} else {
								percent = ADDRESS_SIMILAR;
							}
							generateStringCompareDetail(match, details, str1, str2, description, percent, 75, .15, MatchType.address, isWhitelisted);
							
						} else {
							// Country not available
							String str1 = null;
							String str2 = null;
							if (a1.getProvince() != null && a1.getProvince().trim().length() > 0 && a2.getProvince() != null && a2.getProvince().trim().length() > 0 ) {
								str1 = a1.getAddress1() + " " + replaceNull(a1.getAddress2()) + " " + replaceNull(a1.getCity()) + " " + replaceNull(a1.getProvince());
								str2 = a2.getAddress1() + " " + replaceNull(a2.getAddress2()) + " " + replaceNull(a2.getCity()) + " " + replaceNull(a2.getProvince());	
							} else {
								str1 = a1.getAddress1() + " " + replaceNull(a1.getAddress2()) + " " + replaceNull(a1.getCity()) + " " + replaceNull(a1.getState());
								str2 = a2.getAddress1() + " " + replaceNull(a2.getAddress2()) + " " + replaceNull(a2.getCity()) + " " + replaceNull(a2.getState());
							}
							String description = "Similar Address";
							double percent = 0;
							boolean isWhitelisted =  false;
							if(a1.getWhitelist() != null  || a2.getWhitelist() != null ){
								percent = WHITELIST_MATCH;
								description = "Similar Address (Whitelisted - " + (a1.getWhitelist()!=null?a1.getWhitelist().getDescription():a2.getWhitelist().getDescription()) + ")";
								isWhitelisted = true;
							} else {
								percent = ADDRESS_SIMILAR;
							}
							generateStringCompareDetail(match, details, str1, str2, description, percent, 75, .15, MatchType.address, isWhitelisted);
						}
					}
				}
			}
		}
		
	}

	private static void generateStringCompareDetail(MatchHistory match, Set<MatchDetail> details, String str1, String str2, String description, double percent, double minimumScore, double multiplier, MatchType type, boolean isWhitelisted) {
	 
	double score = StringCompare.compareStrings(str1, str2);
	  if (score > minimumScore) {
	  	MatchDetail detail = new MatchDetail();
	  	detail.setContent1(str1);
	  	detail.setContent2(str2);
	  	detail.setDescription(description);
	  	detail.setMatch(match);
	  	if(isWhitelisted){
	  		detail.setPercent(WHITELIST_MATCH);
	  		
	  	} else {
	  		detail.setPercent(score*multiplier);
	  	}
	  	detail.setMatchtype(type);
	  	details.add(detail);
	  }
  }
	
	private static String replaceNull(String string) {
	  if (string == null) return "";
	  return string.trim();
  }

	private static void proccessCreditCards(MatchHistory match){
		Set<CreditCard> ccs1 = getCreditCards(match.getFile1());
		Set<CreditCard> ccs2 = getCreditCards(match.getFile2());

		HashSet<String> creditMatches = new HashSet<String>();
		String hashKey;
		for(CreditCard c1: ccs1){
			for(CreditCard c2: ccs2){
				hashKey=c1.getCcType()+c1.getCcNumLastFour()+c2.getCcType()+c2.getCcNumLastFour();
						
				if(creditMatches.add(hashKey))		
				{
					proccessCreditCard(c1, c2, match);
				
				}
			}
		}
	}

	protected static void proccessCreditCard(CreditCard r1, CreditCard r2, MatchHistory match){
		if(r1 != null && r2 != null){
			boolean num4 = (r1.getCcNumLastFour() != null && r1.getCcNumLastFour().trim().length() > 0 && r1.getCcNumLastFour().equals(r2.getCcNumLastFour())) ? true:false;
			boolean num6 = (r1.getCcNumber() != null && r1.getCcNumber().trim().length() > 0 && r1.getCcNumber().equals(r2.getCcNumber())) ? true:false;
			boolean type = (r1.getCcType() != null && r1.getCcType().trim().length() > 0 && r1.getCcType().equals(r2.getCcType())) ? true:false;
			boolean exp = (r1.getCcExpMonth() != 0 && r1.getCcExpMonth() == r2.getCcExpMonth() && r1.getCcExpYear() != 0 && r1.getCcExpYear() == r2.getCcExpYear() ) ? true:false;
			boolean num4Empty = (r1.getCcNumLastFour() != null && r1.getCcNumLastFour().trim().length() > 0 && r2.getCcNumLastFour() != null && r2.getCcNumLastFour().trim().length() > 0) ? false:true;
			boolean num6Empty = (r1.getCcNumber() != null && r1.getCcNumber().trim().length() > 0 && r2.getCcNumber() != null && r2.getCcNumber().trim().length() > 0) ? false:true;
			boolean typeEmpty = (r1.getCcType() != null && r1.getCcType().trim().length() > 0 && r2.getCcType() != null && r2.getCcType().trim().length() > 0) ? false:true;
			@SuppressWarnings("unused")
			boolean expEmpty = (r1.getCcExpMonth() != 0 && r2.getCcExpMonth() !=0 && r1.getCcExpYear() != 0 && r2.getCcExpYear() != 0) ? false:true;
			
			if(num6){						
				if(num4 && type && exp){
					MatchDetail detail = new MatchDetail();
					detail.setMatch(match);
					match.getDetails().add(detail);
					detail.setContent1(r1.getCcType() + " " + r1.getCcNumber() + "******" + r1.getCcNumLastFour() + " exp:" + r1.getCcExpMonth()  + "/" +r1.getCcExpYear());
					detail.setContent2(r2.getCcType() + " " + r2.getCcNumber() + "******" + r2.getCcNumLastFour() + " exp:" + r2.getCcExpMonth()  + "/" +r2.getCcExpYear());
					detail.setDescription("Credit Card Full Match");
					detail.setPercent(P_CC_6 + P_CC_4 + P_CC_EXP + P_CC_TYPE);
					detail.setMatchtype(MatchType.cc);
				} else if(typeEmpty && exp && num4) {
					MatchDetail detail = new MatchDetail();
					detail.setMatch(match);
					match.getDetails().add(detail);
					detail.setContent1(r1.getCcNumber() + "******" + r1.getCcNumLastFour() + " exp:" + r1.getCcExpMonth()  + "/" +r1.getCcExpYear());
					detail.setContent2(r2.getCcNumber() + "******" + r2.getCcNumLastFour() + " exp:" + r2.getCcExpMonth()  + "/" +r2.getCcExpYear());
					detail.setDescription("Credit Card 6 digit, 4 digit, and Expiration Match");
					detail.setPercent(P_CC_6 + P_CC_4 + P_CC_EXP);
					detail.setMatchtype(MatchType.cc);
				} else if(type && num4) {
					MatchDetail detail = new MatchDetail();
					detail.setMatch(match);
					match.getDetails().add(detail);
					detail.setContent1(r1.getCcType() + " " + r1.getCcNumber() + "******" + r1.getCcNumLastFour());
					detail.setContent2(r2.getCcType() + " " + r2.getCcNumber() + "******" + r2.getCcNumLastFour());
					detail.setDescription("Credit Card 6 digit, 4 digit, and Type Match");
					detail.setPercent(P_CC_6 + P_CC_TYPE + P_CC_4);
					detail.setMatchtype(MatchType.cc);
				} else if(type && exp && num4Empty) {
					MatchDetail detail = new MatchDetail();
					detail.setMatch(match);
					match.getDetails().add(detail);
					detail.setContent1(r1.getCcType() + " " + r1.getCcNumber() + "********** exp:" + r1.getCcExpMonth()  + "/" +r1.getCcExpYear());
					detail.setContent2(r2.getCcType() + " " + r2.getCcNumber() + "********** exp:" + r2.getCcExpMonth()  + "/" +r2.getCcExpYear());
					detail.setDescription("Credit Card 6 digit, Type, and Expiration Match");
					detail.setPercent(P_CC_6 + P_CC_TYPE + P_CC_EXP);
					detail.setMatchtype(MatchType.cc);
				} else if(typeEmpty && num4){
					MatchDetail detail = new MatchDetail();
					detail.setMatch(match);
					match.getDetails().add(detail);
					detail.setContent1(r1.getCcNumber() + "******" + r1.getCcNumLastFour());
					detail.setContent2(r2.getCcNumber() + "******" + r2.getCcNumLastFour());
					detail.setDescription("Credit Card 6 digit and 4 digit Match");
					detail.setPercent(P_CC_6 + P_CC_4);
					detail.setMatchtype(MatchType.cc);
				} else if(type && num4Empty){
					MatchDetail detail = new MatchDetail();
					detail.setMatch(match);
					match.getDetails().add(detail);
					detail.setContent1(r1.getCcType() + " " + r1.getCcNumber() + "**********");
					detail.setContent2(r2.getCcType() + " " + r2.getCcNumber() + "**********");
					detail.setDescription("Credit Card 6 digit and Type Match");
					detail.setPercent(P_CC_6 + P_CC_TYPE);
					detail.setMatchtype(MatchType.cc);
				} else if(exp && typeEmpty && num4Empty){
					MatchDetail detail = new MatchDetail();
					detail.setMatch(match);
					match.getDetails().add(detail);
					detail.setContent1(r1.getCcNumber() + "********** exp:" + r1.getCcExpMonth()  + "/" +r1.getCcExpYear());
					detail.setContent2(r2.getCcNumber() + "********** exp:" + r2.getCcExpMonth()  + "/" +r2.getCcExpYear());
					detail.setDescription("Credit Card 6 digit and Expiration Match");
					detail.setPercent(P_CC_6 + P_CC_EXP);
					detail.setMatchtype(MatchType.cc);
				} else if (typeEmpty && num4Empty){
					MatchDetail detail = new MatchDetail();
					detail.setMatch(match);
					match.getDetails().add(detail);
					detail.setContent1(r1.getCcNumber() + "**********");
					detail.setContent2(r2.getCcNumber() + "**********");
					detail.setDescription("Credit Card 6 digit Match");
					detail.setPercent(P_CC_6);
					detail.setMatchtype(MatchType.cc);
				}
			} else if (num4 && num6Empty){
				if(type && exp){
					MatchDetail detail = new MatchDetail();
					detail.setMatch(match);
					match.getDetails().add(detail);
					detail.setContent1(r1.getCcType() + " ************" + r1.getCcNumLastFour() + " exp:" + r1.getCcExpMonth()  + "/" +r1.getCcExpYear());
					detail.setContent2(r2.getCcType() + " ************" + r2.getCcNumLastFour() + " exp:" + r2.getCcExpMonth()  + "/" +r2.getCcExpYear());
					detail.setDescription("Credit Card 4 digit, type and expiration Match");
					detail.setPercent(P_CC_4_TYPE_EXP);
					detail.setMatchtype(MatchType.cc);
				} else if(type) {
					MatchDetail detail = new MatchDetail();
					detail.setMatch(match);
					match.getDetails().add(detail);
					detail.setContent1(r1.getCcType() + " ************" + r1.getCcNumLastFour());
					detail.setContent2(r2.getCcType() + " ************" + r2.getCcNumLastFour());
					detail.setDescription("Credit Card 4 digit and Type Match");
					detail.setPercent(P_CC_4 + P_CC_TYPE);
					detail.setMatchtype(MatchType.cc);
				} else if(exp && typeEmpty){
					MatchDetail detail = new MatchDetail();
					detail.setMatch(match);
					match.getDetails().add(detail);
					detail.setContent1("************" + r1.getCcNumLastFour() + " exp:" + r1.getCcExpMonth()  + "/" +r1.getCcExpYear());
					detail.setContent2("************" + r2.getCcNumLastFour() + " exp:" + r2.getCcExpMonth()  + "/" +r2.getCcExpYear());
					detail.setDescription("Credit Card 4 digit and Expiration Match");
					detail.setPercent(P_CC_4 + P_CC_EXP);
					detail.setMatchtype(MatchType.cc);
				} else if(typeEmpty){
					MatchDetail detail = new MatchDetail();
					detail.setMatch(match);
					match.getDetails().add(detail);
					detail.setContent1("************" + r1.getCcNumLastFour());
					detail.setContent2("************" + r2.getCcNumLastFour());
					detail.setDescription("Credit Card 4 digit Match");
					detail.setPercent(P_CC_4);
					detail.setMatchtype(MatchType.cc);
				}
			}
			
		}
	}
	
	private static void processReservation(MatchHistory match){
		//previously all we processed was credit cards of which there is a new processCreditCard method
	}
	
	

	protected static boolean processName(MatchHistory match, Person p1, Person p2){
		return processName(match,p1,p2,true);
	}
	
	protected static boolean processName(MatchHistory match, Person p1, Person p2, boolean searchNicknames){
		boolean hasMatch = false;
		Set<MatchDetail> details = match.getDetails();
		String content1 = null;
		if(p1.getParent() != null){
			content1 = p1.getParent().getFirstName().trim() + " " + p1.getParent().getLastName().trim();
		} else {
			content1 = p1.getFirstName().trim() + " " + p1.getLastName().trim();
		}
		String content2 = p2.getFirstName().trim() + " " + p2.getLastName().trim();
		double stringCompareValue = StringCompare.compareStrings(p1.getFirstName().toUpperCase() + " " + p1.getLastName().toUpperCase(),
				p2.getFirstName().toUpperCase() + " " + p2.getLastName().toUpperCase())/100;
		
		if (p1.getFirstName().equalsIgnoreCase(p2.getFirstName())
				&& p1.getLastName().equalsIgnoreCase(p2.getLastName())) {
			
			MatchDetail detail = new MatchDetail();
			detail.setContent1(content1);
			detail.setContent2(content2);
			if(p1.getParent() != null){
				detail.setDescription(EXACT_NICKNAME_MATCH);
				detail.setPercent(P_NAME * P_NICK_NAME_MULTIPLIER);
			} else {
				detail.setDescription(EXACT_NAME_MATCH);
				detail.setPercent(P_NAME);
			}
			detail.setMatch(match);
			detail.setMatchtype(MatchType.name);
			details.add(detail);
			hasMatch = true;


		} else if (stringCompareValue > 0.9){
			MatchDetail detail = new MatchDetail();
			detail.setContent1(content1);
			detail.setContent2(content2);
			detail.setDescription(SIMILAR_NAME);
			detail.setMatch(match);
			detail.setPercent(stringCompareValue * .1);
			detail.setMatchtype(MatchType.name);
			if(p1.getParent() != null){
				detail.setDescription(SIMILAR_NICKNAME_MATCH);
				detail.setPercent(stringCompareValue * P_NAME * P_NICK_NAME_MULTIPLIER);
			} else {
				detail.setDescription(SIMILAR_NAME);
				detail.setPercent(stringCompareValue * P_NAME);
			}
			details.add(detail);
			hasMatch = true;
			
		} else if(searchNicknames) {
			

			//process nick names
			List<FsNameAlias> names = FsNameAliasBMO.getAlias(p1.getFirstName());
			for(FsNameAlias name:names){
				Person nickname = new Person();
				nickname.setFirstName(name.getAlias());
				nickname.setLastName(p1.getLastName());
				nickname.setParent(p1);
				if(processName(match,nickname,p2,false)){
					return true;
				}
			}

			if (p1.getFirstNameSoundex() != null && p2.getFirstNameSoundex() != null
					&& p1.getFirstNameSoundex().length() > 0 
					&& p1.getFirstNameSoundex().equals(p2.getFirstNameSoundex())
					&& p1.getLastNameSoundex().length() > 0 
					&& p1.getLastNameSoundex().equals(p2.getLastNameSoundex())) {
				MatchDetail detail = new MatchDetail();
				detail.setContent1(p1.getFirstName() + " " + p1.getLastName());
				detail.setContent2(p2.getFirstName() + " " + p2.getLastName());
				detail.setMatch(match);
				detail.setMatchtype(MatchType.name);
				if(p1.getParent() != null){
					detail.setDescription(SOUNDEX_NICKNAME_MATCH);
					detail.setPercent(P_SOUNDEX * P_NICK_NAME_MULTIPLIER);
				} else {
					detail.setDescription(SOUNDEX_MATCH);
					detail.setPercent(P_SOUNDEX);
				}
				details.add(detail);
				hasMatch = true;
			}
			if (p1.getFirstNameDmp() != null && p2.getFirstNameDmp() != null
					&& p1.getFirstNameDmp().length() > 0 && p1.getLastNameDmp().length() > 0
					&& p1.getFirstNameDmp().equals(p2.getFirstNameDmp())
					&& p1.getLastNameDmp().equals(p2.getLastNameDmp())) {
				MatchDetail detail = new MatchDetail();
				detail.setContent1(p1.getFirstName() + " " + p1.getLastName());
				detail.setContent2(p2.getFirstName() + " " + p2.getLastName());
				detail.setMatch(match);
				detail.setMatchtype(MatchType.name);
				if(p1.getParent() != null){
					detail.setDescription(DOUBLE_METAPHONE_NICKNAME_MATCH);
					detail.setPercent(P_METAPHONE * P_NICK_NAME_MULTIPLIER);
				} else {
					detail.setDescription(DOUBLE_METAPHONE_MATCH);
					detail.setPercent(P_METAPHONE);
				}
				details.add(detail);
				hasMatch = true;
			}
		}
		return hasMatch;
	}
	
	protected static void processPerson(MatchHistory match){

		Set<Person> plist1 = match.getFile1().getPersonCache(false);
		if(plist1 == null){
			plist1 = getPersons(match.getFile1());
			match.getFile1().setPersonCache(plist1, false);
		}	
		Set<Person> plist2 = null;
		if(match.getFile2() != null){
			plist2 = getPersons(match.getFile2());
		} 
		
		Set <MatchDetail> details = match.getDetails();
		HashSet<String> nameHashSet = new HashSet<String>();
		HashSet<String> emailHashSet = new HashSet<String>();;
		
		for(Person p1:plist1){
			for(Person p2:plist2){

				if(p1.getFirstName() != null && p1.getFirstName().trim().length() > 0 
						&& p1.getLastName() != null && p1.getLastName().trim().length() > 0 && p2.getFirstName() != null && p2.getLastName() != null){

					String content2 = p2.getFirstName().trim() + " " + p2.getLastName().trim();
					String comparator = p1.getFirstName().trim() + " " + p1.getLastName().trim() + "/" + content2;
					comparator = comparator.toUpperCase();
					if (!nameHashSet.contains(comparator)) {
						nameHashSet.add(comparator);
						processName(match,p1,p2);
					}
				}//end name
				// TODO: Update contents appropriately
				// TODO: Need to split up license number from issuer (they should build).
				
				boolean driverNumberNull = p1.getDriversLicenseNumber() == null || p1.getDriversLicenseNumber().trim().length() == 0 
										|| p2.getDriversLicenseNumber() == null || p2.getDriversLicenseNumber().trim().length() == 0 ? true:false;
				boolean driverNumber = !driverNumberNull && p1.getDriversLicenseNumber().equalsIgnoreCase(p2.getDriversLicenseNumber()) ? true:false;
				boolean driverIssueNull = p1.getDriversLicenseIssuer() == null || p1.getDriversLicenseIssuer().trim().length() == 0 
											|| p2.getDriversLicenseIssuer() == null || p2.getDriversLicenseIssuer().trim().length() == 0 ? true:false;
				boolean driverIssue = !driverIssueNull && p1.getDriversLicenseIssuer().equalsIgnoreCase(p2.getDriversLicenseIssuer()) ? true:false;
				
				if(driverNumber && driverIssue){
					MatchDetail detail = new MatchDetail();
					detail.setContent1(p1.getDriversLicenseIssuer() + "********");
					detail.setContent2(p2.getDriversLicenseIssuer() + "********");
					detail.setDescription("Driver's License Match");
					detail.setMatch(match);
					detail.setPercent(P_DRIVERS);
					detail.setMatchtype(MatchType.drivers);
					details.add(detail);
				} else if (driverNumber && driverIssueNull){
					MatchDetail detail = new MatchDetail();
					detail.setContent1("********");
					detail.setContent2("********");
					detail.setDescription("Driver's License Match");
					detail.setMatch(match);
					detail.setPercent(P_DRIVERS);
					detail.setMatchtype(MatchType.drivers);
					details.add(detail);
				}//end drivers
				
				if (p1.getEmailAddress() != null && p1.getEmailAddress().trim().length() > 0) {
					if (p2.getEmailAddress() != null && p2.getEmailAddress().trim().length() > 0) {
					
						String content1 = p1.getEmailAddress().trim();
						String content2 = p2.getEmailAddress().trim();
						String comparator = content1 + "/" + content2;
						comparator = comparator.toUpperCase();
						if (!emailHashSet.contains(comparator)) {
	
							if (content1.equalsIgnoreCase(content2)) {
								MatchDetail detail = new MatchDetail();
								detail.setContent1(content1);
								detail.setContent2(content2);
								detail.setDescription("Email Address Match");
								detail.setMatch(match);
								detail.setPercent(P_EMAIL);
								detail.setMatchtype(MatchType.email);
								details.add(detail);
								emailHashSet.add(comparator);
							}
						}
					}
				}//end email
				if (p1.getFfNumber() != null && p1.getFfNumber().trim().length() > 0) {
					if (p2.getFfNumber() != null) {

						String content1 = p1.getFfNumber().trim();
						String content2 = p2.getFfNumber().trim();
						String comparator = content1 + "/" + content2;
						comparator = comparator.toUpperCase();
						if (!emailHashSet.contains(comparator)) {

							if (p1.getFfNumber().trim().equalsIgnoreCase(p2.getFfNumber().trim())) {
								MatchDetail detail = new MatchDetail();
								detail.setContent1(content1);
								detail.setContent2(content2);
								detail.setDescription("Frequent Flyer Number Match");
								detail.setMatch(match);
								detail.setPercent(P_FFN);
								detail.setMatchtype(MatchType.ffn);
								details.add(detail);
							}
						}
					}
				}// end FFN
				if(p1.getPassportNumber() != null && p1.getPassportNumber().trim().length() > 0){
					if(p1.getPassportNumber().equalsIgnoreCase(p2.getPassportNumber())){
						// TODO: Update contents appropriately
						MatchDetail detail = new MatchDetail();
						detail.setContent1("*********");
						detail.setContent2("*********");
						detail.setDescription("Passport Match");
						detail.setMatch(match);
						detail.setPercent(P_PASSPORT);
						detail.setMatchtype(MatchType.passport);
						details.add(detail);
					}
				}//end passport
				// TODO: Update contents appropriately
				if(p1.getSocialSecurity() != null && p1.getSocialSecurity().trim().length() > 0){
					if(p1.getSocialSecurity().equalsIgnoreCase(p2.getSocialSecurity())){
						MatchDetail detail = new MatchDetail();
						detail.setContent1("*********");
						detail.setContent2("*********");
						detail.setDescription("SSN Match");
						detail.setMatch(match);
						detail.setPercent(P_SSN);
						detail.setMatchtype(MatchType.ssn);
						details.add(detail);
					}
				}
				
				if(p1.getDateOfBirth() != null){
					if(p1.getDateOfBirth().equals(p2.getDateOfBirth())){
						MatchDetail detail = new MatchDetail();
						detail.setContent1("*********");
						detail.setContent2("*********");
						detail.setDescription("Date of Birth Match");
						detail.setMatch(match);
						detail.setPercent(P_DOB);
						detail.setMatchtype(MatchType.dob);
						details.add(detail);
					}
				}
			}
		}
	}
	
	protected static void processIPs(MatchHistory match){

		Set<FsIPAddress> iplist1 = null;
		for (FsClaim claim:match.getFile1().getClaims()) {
			if (claim.getIpAddresses() != null) {
				if (iplist1 == null) {
					iplist1 = new LinkedHashSet<FsIPAddress>();
				}
				iplist1.addAll(claim.getIpAddresses());
			}
		}
		
		if (iplist1 != null) {
			Set<FsIPAddress> iplist2 = null;
			for (FsClaim claim:match.getFile2().getClaims()) {
				if (claim.getIpAddresses() != null) {
					if (iplist2 == null) {
						iplist2 = new LinkedHashSet<FsIPAddress>();
					}
					iplist2.addAll(claim.getIpAddresses());
				}
			}
			if (iplist2 != null) {
				Set <MatchDetail> details = match.getDetails();
				
				for(FsIPAddress ip1:iplist1){
					for(FsIPAddress ip2:iplist2){
						if(ip1.getIpAddress() != null && ip2.getIpAddress() != null && ip1.getIpAddress().equals(ip2.getIpAddress())){
							MatchDetail detail = new MatchDetail();
							detail.setContent1(ip1.getIpAddress());
							detail.setContent2(ip2.getIpAddress());
							detail.setMatch(match);
							detail.setMatchtype(MatchType.ipAddress);
							if (ip1.getWhitelist() != null) {
								detail.setDescription("IP Address Match (Whitelisted - " + ip1.getWhitelist().getDescription()+")");
								detail.setPercent(0);
							} else {
								detail.setDescription("IP Address Match");
								detail.setPercent(P_IP);
							}
							details.add(detail);
						}
					}
				} // END FOR LOOP iplist1
			}
		}
	} // END processIP method


	public ArrayBlockingQueue<MatchHistory> getMatchQueue() {
		return matchQueue;
	}


	public void setMatchQueue(ArrayBlockingQueue<MatchHistory> matchQueue) {
		this.matchQueue = matchQueue;
	}
}
