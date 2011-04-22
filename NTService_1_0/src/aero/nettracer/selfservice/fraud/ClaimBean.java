package aero.nettracer.selfservice.fraud;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.BeanUtils;

import com.bagnet.nettracer.tracing.utils.DateUtils;

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
import aero.nettracer.fs.model.detection.Blacklist;
import aero.nettracer.fs.model.detection.MatchDetail;
import aero.nettracer.fs.model.detection.MatchDetail.MatchType;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.fs.model.detection.Whitelist;
import aero.nettracer.fs.utilities.GeoCode;
import aero.nettracer.fs.utilities.GeoLocation;
import aero.nettracer.fs.utilities.InternationalException;
import aero.nettracer.fs.utilities.tracing.Consumer;
import aero.nettracer.fs.utilities.tracing.Producer;
import aero.nettracer.fs.utilities.tracing.TraceWrapper;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions.AccessLevelType;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;






@Stateless
public class ClaimBean implements ClaimRemote, ClaimHome{

	public static boolean debug = false;
	
	public String echoTest(String s){
		return "echo: " + s;
	}
	
	public Set<MatchHistory> traceFile(long fileId){
		return Producer.matchFile(fileId);
	}
	
	public long insertFile(File file){
		Transaction t = null;
		Session sess = null;
		try{
			File toSubmit = resetIdAndgeocode(file);
			
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			if(toSubmit.getId() > 0){		
				if(debug)System.out.println("delete and save");
				File toDelete = (File)sess.load(File.class,toSubmit.getId());
				if(toDelete.getClaim() != null){
					FsClaim claim = toDelete.getClaim();
					claim.setFile(null);
					if(claim.getIncident() != null){
						FsIncident inc = toDelete.getClaim().getIncident();
						claim.getIncident().setFile(null);
					}
					sess.delete(claim);
					toDelete.setClaim(toSubmit.getClaim());
					if(toDelete.getClaim() != null){
					toDelete.getClaim().setFile(toDelete);
					}
					toDelete.setIncident(toSubmit.getIncident());
					if(toDelete.getIncident() != null){
					toDelete.getIncident().setFile(toDelete);
					}
					
					
				} else if (toDelete.getIncident() != null){
					FsIncident inc = toDelete.getIncident();
					inc.setFile(null);
					sess.delete(inc);
					toDelete.setIncident(toSubmit.getIncident());
					if(toDelete.getIncident() != null){
					toDelete.getIncident().setFile(toDelete);
					}
				}
				
//				toDelete.setClaim(null);
//				toDelete.setIncident(null);
//				sess.saveOrUpdate(toDelete);
//				t.commit();
//				sess.evict(toDelete);
//				sess.close();
//				sess = HibernateWrapper.getSession().openSession();
//				t = sess.beginTransaction();
				
				
//				File toAdd = (File)sess.load(File.class,toSubmit.getId());
//				toAdd.setClaim(toSubmit.getClaim());
//				if(toAdd.getClaim() != null){
//				toAdd.getClaim().setFile(toAdd);
//				}
//				toAdd.setIncident(toSubmit.getIncident());
//				if(toAdd.getIncident() != null){
//				toAdd.getIncident().setFile(toAdd);
//				}
//				sess.saveOrUpdate(toAdd);
//				t.commit();
				
				sess.saveOrUpdate(toDelete);
				t.commit();
				
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

	
	public static File resetIdAndgeocode(File file) throws Exception{
		if(file != null){
			long temp = file.getSwapId();
			file.setSwapId(file.getId());
			file.setId(temp);

			if(file.getClaim() != null && file.getIncident() != null && file.getClaim().getIncident().equals(file.getIncident()) == false){
				throw new Exception("file incident does not match claim incident");
			}
			
			if(file.getClaim() != null){
				file.setClaim(resetClaim(file.getClaim()));
			} else if(file.getIncident() != null){
				file.setIncident(resetIncident(file.getIncident()));
			}

			if(debug)System.out.println(file.toString());
		}
		return file;
	}
	
	public static FsClaim resetClaim(FsClaim claim){
		if(claim != null){
			claim.setSwapId(claim.getId());
			claim.setId(0);
			
			claim.setIncident(resetIncident(claim.getIncident()));
			claim.setClaimants(resetPersonId(claim.getClaimants()));
			claim.setSegments(resetSegmentId(claim.getSegments()));
			claim.setBlacklist(resetBlackListId(claim.getBlacklist()));
		}
		return claim;
	}

	public static FsIncident resetIncident(FsIncident inc){
		if(inc != null){
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
			if(res.getPurchaser() != null){
				res.getPurchaser().setId(0);
				res.getPurchaser().setAddresses(resetAddressIdAndGeocode(res.getPurchaser().getAddresses()));
				res.getPurchaser().setPhones(resetPhoneId(res.getPurchaser().getPhones()));
			}

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
				person.setAddresses(resetAddressIdAndGeocode(person.getAddresses()));
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
	
	public static Set<FsAddress> resetAddressIdAndGeocode(Set<FsAddress> addresses){
		if(addresses != null){
			for(FsAddress address:addresses){
				
				address.setId(0);
				// GEOCODING ALL ADDRESSES POSSIBLE ON SAVE
				// Note: Because this is not persisted to the client, 
				// it must happen every time.
				try {	
					GeoLocation loc = null;
					loc = GeoCode.locate(address.getAddress1(), address.getCity(), address.getState(), address.getZip(), address.getProvince(), address.getCountry(), null);
					
					if (loc != null) {
						address.setLattitude(loc.getLatitude());
						address.setLongitude(loc.getLongitude());
					}
				} catch (InternationalException e) {
					// Ignore; not pertinent at this time.
				} catch (Exception e) {
					// Log error only
					e.printStackTrace();
				}

				
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
	public Set<MatchHistory> getFileMatches(long fileId) {
		return Producer.getMatchHistoryResult(fileId);
	}
	
	public int getIncidentCacheSize(){
		return TraceWrapper.getIncidentCacheSize();
	}
	
	public int getClaimCacheSize(){
		return TraceWrapper.getClaimCacheSize();
	}
	
	
	
	public void censor(MatchHistory match, AccessLevelType level){
		String s = "Not for your eyes";
		PrivacyPermissions p1 = null;
		PrivacyPermissions p2 = null;
		String company1 = "";
		String company2 = "";
		
		if(match.getFile1() != null){
			if(match.getFile1().getClaim() != null && match.getFile1().getClaim().getAirline() != null){
				company1 = match.getFile1().getClaim().getAirline();
			} else if (match.getFile1().getIncident() != null && match.getFile1().getIncident().getAirline() != null){
				company1 = match.getFile1().getIncident().getAirline();
			}
		}
		if(match.getFile2() != null){
			if(match.getFile2().getClaim() != null && match.getFile2().getClaim().getAirline() != null){
				company2 = match.getFile2().getClaim().getAirline();
			} else if (match.getFile2().getIncident() != null && match.getFile2().getIncident().getAirline() != null){
				company2 = match.getFile2().getIncident().getAirline();
			}
		}
		
		for(PrivacyPermissions p: PrivacyPermissionsBean.getPrivacyPermissions()){
			if(p.getKey().getCompanycode().equals(company1) && p.getKey().getLevel().equals(level)){
				p1 = p;
			}
			if(p.getKey().getCompanycode().equals(company2) && p.getKey().getLevel().equals(level)){
				p2 = p;
			}
		}
		
		censorFile(match.getFile1(), p1);
		censorFile(match.getFile2(), p2);
		
		for(MatchDetail d:match.getDetails()){
			if(d.getType().equals(MatchType.name)){
				if(!p1.isName())d.setContent1(s);
				if(!p2.isName())d.setContent2(s);
			} else if (d.getType().equals(MatchType.address)){
				if(!p1.isAddress())d.setContent1(s);
				if(!p2.isAddress())d.setContent2(s);
			} else if (d.getType().equals(MatchType.phone)){
				if(!p1.isPhonenumber())d.setContent1(s);
				if(!p2.isPhonenumber())d.setContent2(s);
			} else if (d.getType().equals(MatchType.email)){
				if(!p1.isEmail())d.setContent1(s);
				if(!p2.isEmail())d.setContent2(s);
			} else if (d.getType().equals(MatchType.cc)){
				if(!p1.isCc())d.setContent1(s);
				if(!p2.isCc())d.setContent2(s);
			} else if (d.getType().equals(MatchType.drivers)){
				if(!p1.isDrivers())d.setContent1(s);
				if(!p2.isDrivers())d.setContent2(s);
			} else if (d.getType().equals(MatchType.ffn)){
				if(!p1.isFfn())d.setContent1(s);
				if(!p2.isFfn())d.setContent2(s);
			} else if (d.getType().equals(MatchType.ssn)){
				if(!p1.isSsn())d.setContent1(s);
				if(!p2.isSsn())d.setContent2(s);
			} else if (d.getType().equals(MatchType.passport)){
				if(!p1.isPassport())d.setContent1(s);
				if(!p2.isPassport())d.setContent2(s);
			} else if (d.getType().equals(MatchType.pnrloc)){
				if(!p1.isPnrloc())d.setContent1(s);
				if(!p2.isPnrloc())d.setContent2(s);
			} else if (d.getType().equals(MatchType.pnrdata)){
				if(!p1.isPnrdata())d.setContent1(s);
				if(!p2.isPnrdata())d.setContent2(s);
			} else if (d.getType().equals(MatchType.traveldate)){
				if(!p1.isTraveldate())d.setContent1(s);
				if(!p2.isTraveldate())d.setContent2(s);
			} else if (d.getType().equals(MatchType.ticketamount)){
				if(!p1.isTicketamount())d.setContent1(s);
				if(!p2.isTicketamount())d.setContent2(s);
			} else if (d.getType().equals(MatchType.dob)){
				if(!p1.isDob())d.setContent1(s);
				if(!p2.isDob())d.setContent2(s);
			} else if (d.getType().equals(MatchType.itin)){
				if(!p1.isItin())d.setContent1(s);
				if(!p2.isItin())d.setContent2(s);
			}
			
			
		}
	}
	
	public void censorFile(File f, AccessLevelType level){
		
	}
	
	public void censorFile(File f, PrivacyPermissions p){
		String s = "!!!CENSOR!!!";
		Set<Person> persons = Consumer.getPersons(f);
		for(Person person:persons){
			if(!p.isName()){
				person.setFirstName(s);
				person.setMiddleName(s);
				person.setLastName(s);
			}
			if(!p.isEmail()){
				person.setEmailAddress(s);
			}
			if(!p.isDrivers()){
				person.setDriversLicenseIssuer(s);
				person.setDriversLicenseNumber(s);
			}
			if(!p.isFfn()){
				person.setFfAirline(s);
				person.setFfNumber(s);
			}
			if(!p.isPassport()){
				person.setPassportIssuer(s);
				person.setPassportNumber(s);
			}
			if(!p.isSsn()){
				person.setSocialSecurity(s);
			}
			if(!p.isDob()){
				person.setDateOfBirth(null);
			}
		}
		
		Set<FsAddress> addresses = Consumer.getAddresses(f);
		for(FsAddress address:addresses){
			if(!p.isAddress()){
				address.setAddress1(s);
				address.setAddress2(s);
				address.setCity(s);
				address.setCountry(s);
				address.setLattitude(0);
				address.setLongitude(0);
				address.setState(s);
				address.setZip(s);
				address.setProvince(s);
			}
		}
		
		Set<Phone> phones = Consumer.getPhones(f);
		for(Phone phone:phones){
			if(!p.isPhonenumber()){
				phone.setPhoneNumber(s);
			}
		}
		
		if(f.getClaim() != null){
			if(!p.isAmountclaimed()){
				f.getClaim().setAmountClaimed(0);
			}
			if(!p.isAmountpaid()){
				
			}
		}
		
		//incident
		
		//pnrdata
		
		//itin
		
		//bag
		
		Reservation reservation = f.getIncident().getReservation();
		
		
	}
	
	
	
}
