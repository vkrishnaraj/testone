package aero.nettracer.selfservice.fraud;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.ejb.Stateless;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.BeanUtils;

import com.bagnet.nettracer.tracing.utils.DateUtils;

import aero.nettracer.fs.model.Bag;
import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;
import aero.nettracer.fs.model.PnrData;
import aero.nettracer.fs.model.Reservation;
import aero.nettracer.fs.model.Segment;
import aero.nettracer.fs.model.detection.Blacklist;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.fs.model.detection.Whitelist;
import aero.nettracer.fs.utilities.tracing.Producer;
import aero.nettracer.fs.utilities.tracing.TraceWrapper;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;






@Stateless
public class ClaimBean implements ClaimRemote, ClaimHome{

	public static boolean debug = false;
	
	public String echoTest(String s){
		return "echo: " + s;
	}
	
	public Set<MatchHistory> traceClaim(long claimId){
		return Producer.matchClaim(claimId);
	}
	
	public Set<MatchHistory> traceIncident(FsIncident incident){
		return null;
	}
	
	public long insertIncident(FsIncident incident){
		return -1;
	}
	
	public synchronized long insertClaim(FsClaim claim){
		FsClaim toSubmit = resetId(claim);
		
		Transaction t = null;
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			if(toSubmit.getId() > 0){		
				if(debug)System.out.println("delete and save");
				FsClaim toDelete = (FsClaim)sess.load(FsClaim.class,toSubmit.getId());
//				sess.lock(toDelete, LockMode.NONE);
				if(toSubmit.getIncident() != null){
					if(toDelete.getIncident() != null){
						toSubmit.getIncident().setId(toDelete.getIncident().getId());
////						sess.delete(toDelete.getIncident());
////						toDelete.setIncident(null);
////						sess.save(toSubmit.getIncident());
					}
				}
				

				sess.delete(toDelete);
				// t.commit();
				// t = sess.beginTransaction();
				BeanUtils.copyProperties(toSubmit, toDelete);

				// 2. save the claim on central services
				if (toDelete.getClaimants() != null)
					for (Person p : toDelete.getClaimants()) {
						p.setClaim(toDelete);
					}

				if (toDelete.getSegments() != null)
					for (Segment s : toDelete.getSegments()) {
						s.setClaim(toDelete);
					}

				if (toDelete.getIncident() != null)
					toDelete.getIncident().setClaim(toDelete);

				sess.saveOrUpdate(toDelete);
				// sess.merge(toSubmit);
				// t.commit();
//				sess.evict(toDelete);
//				sess.flush();
				t.commit();
				t = sess.beginTransaction();
//				sess.close();
//				sess = HibernateWrapper.getSession().openSession();
			} else {
			if(debug)System.out.println("saving:" + toSubmit.getId());
			sess.saveOrUpdate(toSubmit);


			t.commit();
			}
			return toSubmit.getId();
		} catch (Exception e) {
//			logger.error("Error Saving: ", e);
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
//					logger.error("Error Saving: ", ex);
					ex.printStackTrace();
				}
			}
			return -1;
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

	
	public static FsClaim resetId(FsClaim claim){
		if(claim != null){
			long temp = claim.getSwapId();
			claim.setSwapId(claim.getId());
			claim.setId(temp);

			claim.setIncident(resetIncident(claim.getIncident()));
			claim.setClaimants(resetPersonId(claim.getClaimants()));
			claim.setSegments(resetSegmentId(claim.getSegments()));
			claim.setBlacklist(resetBlackListId(claim.getBlacklist()));

			if(debug)System.out.println(claim.toString());
		}
		return claim;
	}

	public static FsIncident resetIncident(FsIncident inc){
		if(inc != null){
			long temp = inc.getSwapId();
//			inc.setSwapId(inc.getId());
//			inc.setId(temp);
			inc.setId(0);
			
			inc.setReservation(resetReservation(inc.getReservation()));
			inc.setSegments(resetSegmentId(inc.getSegments()));
			inc.setBags(resetBagId(inc.getBags()));
			inc.setPassengers(resetPersonId(inc.getPassengers()));
		}
		return inc;
	}

	public static Reservation resetReservation(Reservation res){
		if(res != null){
			res.setId(0);

			res.setSegments(resetSegmentId(res.getSegments()));
			res.setCcWhitelist(resetWhiteListId(res.getCcWhitelist()));
			res.setPnrData(resetPnrDataId(res.getPnrData()));
			res.setPassengers(resetPersonId(res.getPassengers()));
			res.setPhones(resetPhoneId(res.getPhones()));
		}
		return res;
	}

	public static Set<Segment> resetSegmentId(Set<Segment> segments){
		if(segments != null){

			for(Segment segment:segments){
				segment.setId(0);
			}
		}
		return segments;
	}

	public static Set<Bag> resetBagId(Set<Bag> bags){
		if(bags != null){
			for(Bag bag:bags){
				bag.setId(0);
			}
		}
		return bags;
	}

	public static Set<Person> resetPersonId(Set<Person> persons){
		if(persons != null){
			for(Person person:persons){
				person.setId(0);
				person.setAddresses(resetAddressId(person.getAddresses()));
				person.setPhones(resetPhoneId(person.getPhones()));
			}
		}
		return persons;
	}

	public static Whitelist resetWhiteListId(Whitelist wlist){
		if(wlist != null){
			wlist.setId(0);
		}
		return wlist;
	}

	public static Blacklist resetBlackListId(Blacklist blist){
		if(blist != null){
			blist.setId(0);
		}
		return blist;
	}

	public static PnrData resetPnrDataId(PnrData pnrData){
		if(pnrData != null){
			pnrData.setId(0);
		}
		return pnrData;
	}

	public static Set<Phone> resetPhoneId(Set<Phone> phones){
		if(phones != null){
			for(Phone phone:phones){
				phone.setId(0);
				phone.setPhoneNumber(aero.nettracer.fs.utilities.Util.removeNonNumeric(phone.getPhoneNumber()));
			}
		}
		return phones;
	}
	
	public static Set<FsAddress> resetAddressId(Set<FsAddress> addresses){
		if(addresses != null){
			for(FsAddress address:addresses){
				address.setId(0);
			}
		}
		return addresses;
	}
	
	  public static FsClaim createFsClaim() {

			// create the claim
			FsClaim claim = new FsClaim();
			claim.setAirline("WS");

			// create the person
			Person person = new Person();


			// create the claim currency
			String currency = "USD";
			
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
			claim.setAmountClaimedCurrency(currency);
			claim.setClaimants(claimants);
			claim.setIncident(fsIncident);
			
			return claim;
			
		}

	@Override
	public Set<MatchHistory> getClaimMatches(long claimId) {
		return Producer.getMatchHistoryResult(claimId);
	}
	
	public int getIncidentCacheSize(){
		return TraceWrapper.getIncidentCacheSize();
	}
	
	public int getClaimCacheSize(){
		return TraceWrapper.getClaimCacheSize();
	}
	
}
