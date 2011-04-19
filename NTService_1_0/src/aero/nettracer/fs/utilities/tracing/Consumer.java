package aero.nettracer.fs.utilities.tracing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;
import aero.nettracer.fs.model.Reservation;
import aero.nettracer.fs.model.detection.MatchDetail;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

public class Consumer implements Runnable{

	public static final int INCIDENT = 1;
	public static final int CLAIM = 2;
	public static final int MATCH = 3;
	
	public static final double MIN_MATCH_PERCENTAGE = 50;
	public static final double P_SOUNDEX = 5;
	public static final double P_METAPHONE = 5;
	public static final double P_NAME = 5;
	public static final double P_PHONE = 10;
	public static final double P_CC_TYPE = 5;
	public static final double P_CC_EXP = 5;
	public static final double P_CC_4 = 5;
	public static final double P_CC_4_TYPE_EXP = 20;
	public static final double P_CC_16 = 30;
	public static final double P_SSN = 30;
	public static final double P_DRIVERS = 30;
	public static final double P_FFN = 10;
	public static final double P_PASSPORT = 30;
	public static final double P_EMAIL = 20;
	
	
	
	private int threadnumber;
	private int threadtype;
	private ArrayBlockingQueue<FsClaim> claimQueue;
	private ArrayBlockingQueue<FsIncident> incidentQueue;
	private ArrayBlockingQueue<MatchHistory> matchQueue;
	
	public Consumer(ArrayBlockingQueue queue, int type, int threadnumber) throws Exception{
		if(type == CLAIM){
			claimQueue = queue;
		} else if (type == INCIDENT){
			incidentQueue = queue;
		} else if (type == MATCH){
			matchQueue = queue;
		} else {
			throw new Exception("unable to create consumer");
		}
		this.threadnumber = threadnumber;
		this.threadtype = type;
	}
	
	
	@Override
	public void run() {
		while(true){
			try{
				if(this.threadtype == CLAIM){
					FsClaim claim = claimQueue.take();
					
					//System.out.println("CLAIM " + threadnumber + ":" + claim.getId());
				} else if (this.threadtype == INCIDENT){
					FsIncident incident = incidentQueue.take();
					//System.out.println("INCIDENT " + threadnumber + ":" + incident.getId());
				} else if (this.threadtype == MATCH){
					MatchHistory match = matchQueue.take();
//					System.out.println(matchQueue.size());
					//System.out.println("consumer " + threadnumber);
					processMatch(match);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	private static void processMatch(MatchHistory match){
		try{
			//process match
			if(match.getClaim2() != null){
				//System.out.println("consumer claim: " + match.getClaim2().getId());
				match.setClaim2(TraceWrapper.loadClaimFromCache(match.getClaim2().getId()));
			}
			if(match.getIncident2() != null){
				//System.out.println("consume incident: " + match.getIncident2().getId());
				match.setIncident2(TraceWrapper.loadIncidentFromCache(match.getIncident2().getId()));
			}
			processPerson(match);
			processAddress(match);
			processReservation(match);

			if(match.getDetails() == null || match.getMatchPercentage() < MIN_MATCH_PERCENTAGE){
				//no match details so don't save
				//can expand to include not saving match if min match percent below certain threshold
				return;
			}
			//save match
			Transaction t = null;
			Session sess = null;
			try{
				sess = HibernateWrapper.getSession().openSession();
				t = sess.beginTransaction();
				sess.saveOrUpdate(match);
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
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			match.getTraceCount().add(null);
			//System.out.println("consumer consumed count: " + match.getTraceCount().size());
		}
	}
	
	public static Set<Person> getPersons(FsIncident incident){
		HashSet<Person> ret = new HashSet<Person>();
			if(incident != null){
				if(incident.getPassengers() != null){
					for(Person p:incident.getPassengers()){
						ret.add(p);
					}
				}
				if(incident.getReservation() != null
						&& incident.getReservation().getPassengers() != null){
					for(Person p:incident.getReservation().getPassengers()){
						ret.add(p);
					}
				}
			}
		return ret;
	}
	
	public static Set<Person> getPersons(FsClaim claim){
		HashSet<Person> ret = new HashSet<Person>();
		
		if(claim != null){
			if(claim.getClaimants() != null){
				for(Person p:claim.getClaimants()){
					ret.add(p);
				}
			}
			if(claim.getIncident() != null){
				if(claim.getIncident().getPassengers() != null){
					for(Person p:claim.getIncident().getPassengers()){
						ret.add(p);
					}
				}
				if(claim.getIncident().getReservation() != null
						&& claim.getIncident().getReservation().getPassengers() != null){
					for(Person p:claim.getIncident().getReservation().getPassengers()){
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
			if(claim.getClaimants() != null){
				for(Person person:claim.getClaimants()){
					if(person.getPhones() != null){
						for(Phone phone:person.getPhones()){
							ret.add(phone);
						}
					}
				}
			}
			if(claim.getIncident() != null){
				if(claim.getIncident().getPassengers() != null){
					for(Person p:claim.getIncident().getPassengers()){
						if(p.getPhones() != null){
							for(Phone phone:p.getPhones()){
								ret.add(phone);
							}
						}
					}
				}
				if(claim.getIncident().getReservation() != null
						&& claim.getIncident().getReservation().getPassengers() != null){
					for(Person p:claim.getIncident().getReservation().getPassengers()){
						if(p.getPhones() != null){
							for(Phone phone:p.getPhones()){
								ret.add(phone);
							}
						}
					}
				}
			}
		}
		return ret;
	}
	
	private static void processAddress(MatchHistory match){
		
	}
	
	private static void processReservation(MatchHistory match){
		Reservation r1 = null;
		Reservation r2 = null;
		if(match.getClaim1() != null && match.getClaim1().getIncident() != null){
			r1 = match.getClaim1().getIncident().getReservation();
		}
		if(match.getClaim2() != null && match.getClaim2().getIncident() != null){
			r2 = match.getClaim2().getIncident().getReservation();
		}
		if(match.getIncident2() != null){
			r2 = match.getIncident2().getReservation();
		}
		
		if(r1 != null && r2 != null){
			boolean num4 = (r1.getCcNumLastFour() != null && r1.getCcNumLastFour().length() > 0 && r1.getCcNumLastFour().equals(r2.getCcNumLastFour())) ? true:false;
			boolean num16 = (r1.getCcNumber() != null && r1.getCcNumber().length() > 0 && r1.getCcNumber().equals(r2.getCcNumber())) ? true:false;
			boolean type = (r1.getCcType() != null && r1.getCcType().length() > 0 && r1.getCcType().equals(r2.getCcType())) ? true:false;
			boolean exp = (r1.getCcExpMonth() != 0 && r1.getCcExpMonth() == r2.getCcExpMonth() && r1.getCcExpYear() == r2.getCcExpYear() ) ? true:false;
			boolean typeEmpty = (r1.getCcType() != null && r1.getCcType().length() > 0) ? false:true;
			boolean expEmpty = (r1.getCcExpMonth() != 0) ? false:true;
			
			if(num16){						
				MatchDetail detail = new MatchDetail();
				detail.setContent1("");
				detail.setContent2("");
				detail.setMatch(match);
				match.getDetails().add(detail);
				if(type && exp){
					detail.setDescription("Credit Card Full Match");
					detail.setPercent(P_CC_16 + P_CC_EXP + P_CC_TYPE);
				} else if(type && expEmpty) {
					detail.setDescription("Credit Card Number and Type Match");
					detail.setPercent(P_CC_16 + P_CC_TYPE);
				} else if(exp && typeEmpty){
					detail.setDescription("Credit Card Number and Expiration Match");
					detail.setPercent(P_CC_16 + P_CC_EXP);
				} else if(expEmpty && typeEmpty){
					detail.setDescription("Credit Card Number Match");
					detail.setPercent(P_CC_16);
				}
			} else if (num4){
				MatchDetail detail = new MatchDetail();
				detail.setContent1("");
				detail.setContent2("");
				detail.setMatch(match);
				match.getDetails().add(detail);
				if(type && exp){
					detail.setDescription("Credit Card 4 digit, type and expiration Match");
					detail.setPercent(P_CC_4_TYPE_EXP);
				} else if(type && expEmpty) {
					detail.setDescription("Credit Card 4 digit and Type Match");
					detail.setPercent(P_CC_4 + P_CC_TYPE);
				} else if(exp && typeEmpty){
					detail.setDescription("Credit Card 4 digit and Expiration Match");
					detail.setPercent(P_CC_4 + P_CC_EXP);
				} else if(expEmpty && typeEmpty){
					detail.setDescription("Credit Card 4 digit Match");
					detail.setPercent(P_CC_4);
				}
			}
			
		}
	}
	
	

	
	private static void processPerson(MatchHistory match){
		Set<Person> plist1 = getPersons(match.getClaim1());
		Set<Person> plist2 = null;
		if(match.getClaim2() != null){
			plist2 = getPersons(match.getClaim2());
		} else if (match.getIncident2() !=null){
			plist2 = getPersons(match.getIncident2());
		}
		
		Set <MatchDetail> details = match.getDetails();

		for(Person p1:plist1){
			for(Person p2:plist2){
				if(p1.getFirstName() != null && p1.getFirstName().length() > 0 
						&& p1.getLastName() != null && p1.getLastName().length() > 0){
					if(p1.getFirstName().equals(p2.getFirstName()) && p1.getLastName().equals(p2.getLastName())){
						MatchDetail detail = new MatchDetail();
						detail.setContent1(p1.getFirstName() + " " + p1.getLastName());
						detail.setContent2(p2.getFirstName() + " " + p2.getLastName());
						detail.setDescription("Direct Name Match");
						detail.setMatch(match);
						detail.setPercent(P_NAME);
						details.add(detail);
					} else {
						if(p1.getFirstNameSoundex().equals(p2.getFirstNameSoundex()) && p1.getLastNameSoundex().equals(p2.getLastNameSoundex())){
							MatchDetail detail = new MatchDetail();
							detail.setContent1(p1.getFirstName() + " " + p1.getLastName());
							detail.setContent2(p2.getFirstName() + " " + p2.getLastName());
							detail.setDescription("Soundex Match");
							detail.setMatch(match);
							detail.setPercent(P_SOUNDEX);
							details.add(detail);
						}
						if(p1.getFirstNameDmp().equals(p2.getFirstNameDmp()) && p1.getLastNameDmp().equals(p2.getLastNameDmp())){
							MatchDetail detail = new MatchDetail();
							detail.setContent1(p1.getFirstName() + " " + p1.getLastName());
							detail.setContent2(p2.getFirstName() + " " + p2.getLastName());
							detail.setDescription("Double Metaphone Match");
							detail.setMatch(match);
							detail.setPercent(P_METAPHONE);
							details.add(detail);
						}
					}
				}//end name
				if(p1.getDriversLicenseNumber() != null && p1.getDriversLicenseNumber().length() > 0
						&& p1.getDriversLicenseIssuer() != null && p1.getDriversLicenseIssuer().length() > 0){
					if(p1.getDriversLicenseNumber().equals(p2.getDriversLicenseNumber())
							&& p1.getDriversLicenseIssuer().equals(p2.getDriversLicenseIssuer())){
						MatchDetail detail = new MatchDetail();
						detail.setContent1(p1.getFirstName() + " " + p1.getLastName());
						detail.setContent2(p2.getFirstName() + " " + p2.getLastName());
						detail.setDescription("Driver's License Match");
						detail.setMatch(match);
						detail.setPercent(P_DRIVERS);
						details.add(detail);
					}
				}//end drivers
				if(p1.getEmailAddress() != null && p1.getEmailAddress().length() > 0){
					if(p1.getEmailAddress().equals(p2.getEmailAddress())){
						MatchDetail detail = new MatchDetail();
						detail.setContent1(p1.getFirstName() + " " + p1.getLastName());
						detail.setContent2(p2.getFirstName() + " " + p2.getLastName());
						detail.setDescription("Email Address Match");
						detail.setMatch(match);
						detail.setPercent(P_EMAIL);
						details.add(detail);
					}
				}//end email
				if(p1.getFfNumber() != null && p1.getFfNumber().length() > 0){
					if(p1.getFfNumber().equals(p2.getFfNumber())){
						MatchDetail detail = new MatchDetail();
						detail.setContent1(p1.getFirstName() + " " + p1.getLastName());
						detail.setContent2(p2.getFirstName() + " " + p2.getLastName());
						detail.setDescription("Frequent Flyer Number Match");
						detail.setMatch(match);
						detail.setPercent(P_FFN);
						details.add(detail);
					}
				}//end FFN
				if(p1.getPassportNumber() != null && p1.getPassportNumber().length() > 0){
					if(p1.getPassportNumber().equals(p2.getPassportNumber())){
						MatchDetail detail = new MatchDetail();
						detail.setContent1(p1.getFirstName() + " " + p1.getLastName());
						detail.setContent2(p2.getFirstName() + " " + p2.getLastName());
						detail.setDescription("Passport Match");
						detail.setMatch(match);
						detail.setPercent(P_PASSPORT);
						details.add(detail);
					}
				}//end passport
				if(p1.getSocialSecurity() != null && p1.getSocialSecurity().length() > 0){
					if(p1.getSocialSecurity().equals(p2.getSocialSecurity())){
						MatchDetail detail = new MatchDetail();
						detail.setContent1(p1.getFirstName() + " " + p1.getLastName());
						detail.setContent2(p2.getFirstName() + " " + p2.getLastName());
						detail.setDescription("SSN Match");
						detail.setMatch(match);
						detail.setPercent(P_SSN);
						details.add(detail);
					}
				}
			}
		}
	}

}
