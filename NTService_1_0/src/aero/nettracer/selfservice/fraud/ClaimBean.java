package aero.nettracer.selfservice.fraud;

import java.util.Set;

import javax.ejb.Stateless;

import org.hibernate.Session;
import org.hibernate.Transaction;

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
import aero.nettracer.fs.model.detection.Whitelist;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;





@Stateless
public class ClaimBean implements ClaimRemote, ClaimHome{

	public String echoTest(String s){
		return "echo: " + s;
	}
	
	public Object submitClaim(FsClaim claim){
		return null;
	}
	
	public Object submitIncident(FsIncident incident){
		return null;
	}
	
	public boolean insertClaim(FsClaim claim){
		FsClaim toSubmit = resetId(claim);
		Transaction t = null;
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			sess.saveOrUpdate(toSubmit);
			t.commit();
			return true;
		} catch (Exception e) {
//			logger.error("Error Saving: ", e);
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
//					logger.error("Error Saving: ", ex);
				}
			}
			return false;
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
			//swap claim id
			claim.setAirlineClaimId((new Long(claim.getId())).toString());
			claim.setId(0);

			claim.setIncident(resetIncident(claim.getIncident()));
			claim.setClaimants(resetPersonId(claim.getClaimants()));
			claim.setSegments(resetSegmentId(claim.getSegments()));
			claim.setBlacklist(resetBlackListId(claim.getBlacklist()));

			System.out.println(claim.toString());
		}
		return claim;
	}

	public static FsIncident resetIncident(FsIncident inc){
		if(inc != null){
			inc.setAirlineIncidentId((new Long(inc.getId())).toString());
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
	
}
