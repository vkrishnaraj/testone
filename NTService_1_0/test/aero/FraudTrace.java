package aero;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.junit.Test;

import com.bagnet.nettracer.tracing.utils.DateUtils;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;
import aero.nettracer.fs.model.PnrData;
import aero.nettracer.fs.model.Reservation;
import aero.nettracer.fs.model.Segment;
import aero.nettracer.fs.model.detection.MatchDetail;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.fs.model.detection.TraceResponse;
import aero.nettracer.fs.utilities.GeoCode;
import aero.nettracer.fs.utilities.GeoLocation;
import aero.nettracer.fs.utilities.InternationalException;
import aero.nettracer.fs.utilities.tracing.Producer;
import aero.nettracer.fs.utilities.tracing.TraceWrapper;
import aero.nettracer.selfservice.fraud.ClaimBean;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

public class FraudTrace {
	
//	@Test
	public void traceAll(){
		String sql = "select id from file";
//		String sql = "select id from file where id not in (select file1_id from matchhistory)";
		SQLQuery pq = null;
		Session sess = HibernateWrapper.getSession().openSession();
		pq = sess.createSQLQuery(sql.toString());
		pq.addScalar("id", Hibernate.LONG);
		List<Long> result = pq.list();
		sess.close();
		System.out.println(result.size());
		Date start = new Date();
		Date tick = new Date();
		int i = 0;
		for (Long strs : result) {
			i++;
			System.out.println("claim: " + strs);
			if(i%20 == 0){
				tick = new Date();
				double percentDone = i/result.size();
				long tpc = (long) ((tick.getTime() - start.getTime())/i);
				long timeLeft = tpc * (result.size() - i);
				System.out.println("" + (timeLeft/60000) + "   " + i + "/" + result.size());
				System.out.println(TraceWrapper.getIncidentCacheSize() + "   " + TraceWrapper.getClaimCacheSize());
			}
			
			Long c1 = (Long) strs;
			if(c1 != null){
				Producer.matchFile(c1, 10, true, true);
			}
		}
		
	}
	
//	@Test
	public void run() {
		ClaimBean bean = new ClaimBean();
		Date start = new Date();
		File f = createFile();
		long id = bean.insertFile(f);
		TraceResponse mh = Producer.matchFile(id,10,false,true);
		mh = Producer.matchFile(id,10,false,false);
		
		for(MatchHistory m:mh.getMatchHistory()){
			System.out.println("" + m.isPrimarymatch() + m.getCreatedate() + m.getOverallScore());
			for(MatchDetail d:m.getDetails()){
				System.out.println(d.getDescription() + ":" + d.getContent1() + ":" + d.getContent2());
			}
		}
		Date end = new Date();
		System.out.println("run1 " + (end.getTime() - start.getTime()));
//		start = new Date();
//		Producer.matchClaim(14537);
//		end = new Date();
//		System.out.println("run1 " + (end.getTime() - start.getTime()));
	}
	
	//3870
	
//	@Test
	public void letsSeeWhatWeGet(){
		String sql = "select id from fsclaim where id > 3500";
		SQLQuery pq = null;
		Session sess = HibernateWrapper.getSession().openSession();
		pq = sess.createSQLQuery(sql.toString());
		pq.addScalar("id", Hibernate.LONG);
		List<Long> result = pq.list();
		sess.close();
		Date start = new Date();
		Date tick = new Date();
		int i = 0;
		for (Long strs : result) {
			i++;
			System.out.println("claim: " + strs);
			if(i%20 == 0){
				tick = new Date();
				double percentDone = i/result.size();
				long tpc = (long) ((tick.getTime() - start.getTime())/i);
				long timeLeft = tpc * (result.size() - i);
				System.out.println("" + (timeLeft/60000) + "   " + i + "/" + result.size());
				System.out.println(TraceWrapper.getIncidentCacheSize() + "   " + TraceWrapper.getClaimCacheSize());
			}
			
			Long c1 = (Long) strs;
			if(c1 != null){
//				Producer.matchClaim(c1);
			}
		}
		
	}
	

	
//	@Test
	public void request(){
		ClaimBean bean = new ClaimBean();
		bean.requestAccess(312992, 9, "nettracer user", "B6", "Yo Mike", "555-555-5555");
	}
	
	  public static File createFile() {

		  	File file = new File();
		  
			// create the claim
			FsClaim claim = new FsClaim();
			file.setClaim(claim);
			claim.setFile(file);
			
			claim.setAirline("B6");

			// create the person
			Person person = new Person();


			// create the claim currency
			String currency = "USD";
			
			// create the address
			FsAddress address = new FsAddress();
			address.setPerson(person);
			address.setAddress1("2677 Paces Ferry");
			address.setCity("Atlanta");
			address.setState("GA");
			address.setZip("30339");
			LinkedHashSet<FsAddress> addresses = new LinkedHashSet<FsAddress>();
			addresses.add(address);
			
			// create the phones
			LinkedHashSet<Phone> phones = new LinkedHashSet<Phone>();

			// create the person
			person.setAddresses(addresses);
			person.setPhones(phones);
			person.setClaim(claim);
			person.setFirstName("John");
			person.setLastName("Smith");
			person.setSocialSecurity("444555666");
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
			file.setIncident(fsIncident);
			fsIncident.setFile(file);
			
			fsIncident.setAirline("B6");
			
			fsIncident.setReservation(reservation);
			reservation.setIncident(fsIncident);
			person.setIncident(fsIncident);
			fsIncident.setClaim(claim);
			fsIncident.setPassengers(claimants);
			
			// create the claim
			claim.setAmountClaimedCurrency(currency);
			claim.setClaimants(claimants);
			claim.setIncident(fsIncident);
			
			return file;
			
		}
	  
	  @Test public void geoCode2(){
		  Session sess = HibernateWrapper.getSession().openSession();
		  String sql = "select id, address1, city, state, zip, country from fsaddress where lattitude = -1";
//		  String sql = "select id, address1, city, state, zip, country from fsaddress where id=1690";
		  SQLQuery pq = null;
		  pq = sess.createSQLQuery(sql.toString());
//		  pq.setMaxResults(1000);
		  pq.addScalar("id", Hibernate.LONG);
		  pq.addScalar("address1", Hibernate.STRING);
		  pq.addScalar("city", Hibernate.STRING);
		  pq.addScalar("state", Hibernate.STRING);
		  pq.addScalar("zip", Hibernate.STRING);
		  pq.addScalar("country", Hibernate.STRING);

		  List<Object[]> result = pq.list();


		  double lat;
		  double lon;
		  long type;
		  long start = (new Date()).getTime();
		  long end = (new Date()).getTime();
		  int i = 0;
		  
		  for (Object[] strs : result) {
			  try{
			  i++;
			  if(i%100 == 0){
				  start = end;
				  end = (new Date()).getTime();
				  System.out.println((result.size() * ((end-start)/100))/60000 + ":" + (i*100/result.size()));
			  }
			  
			  long id = (Long)strs[0];
			  String address = (String) strs[1];
			  String city = (String) strs[2];
			  String state = (String) strs[3];
			  String zip = (String) strs[4];
			  String country = (String) strs[5];
			  GeoLocation g = null;
//			  System.out.println(id);
			  try {
				  g = GeoCode.locate(address, city, state, zip, null, country, null);


			  } catch (InternationalException e) {
				  g = null;
//				  e.printStackTrace();
			  }
			  if(g!=null){
//				  				System.out.println(g.getLatitude());
				  lat = g.getLatitude();
				  lon = g.getLongitude();
				  type = g.getType();
			  }else{
				  lat = 0;
				  lon = 0;
				  type = 0;
			  }
			  String update = "update fsaddress set lattitude=" + lat + ", longitude=" + lon + ",geocodeType=" + type + " where id=" + id;
			  //			System.out.println(update);
			  SQLQuery u = null;
			  u = sess.createSQLQuery(update.toString());
			  u.executeUpdate();
			  }catch(Exception e){
				  //continue
			  }
		  }
		  sess.close();
	  }		

	  
	  
//	  @Test
	  public void geoCode(){
		  long start = (new Date()).getTime();
		  long end = (new Date()).getTime();
		  int i = 0;
		  
		  Session sess = HibernateWrapper.getSession().openSession();
		  String sql = "from aero.nettracer.fs.model.FsAddress a where a.lattitude = -1";
		  
		  Query q = sess.createQuery(sql);
		  q.setMaxResults(1000);
		  List<FsAddress> results = q.list();
		  for(FsAddress a:results){
			  i++;
			  if(i%100 == 0){
				  start = end;
				  end = (new Date()).getTime();
				  System.out.println((results.size() * ((end-start)/100))/60000);
			  }
			  System.out.println(a.getId());
			  try {
				GeoLocation g = GeoCode.locate(a.getAddress1(), a.getCity(), a.getState(), a.getZip(), a.getProvince(), a.getCountry(), null);
				if(g==null){
					a.setLattitude(0);
					a.setLongitude(0);
					a.setGeocodeType(0);
				}else{
					a.setLattitude(g.getLatitude());
					a.setLongitude(g.getLongitude());
					a.setGeocodeType(g.getType());
				}
				sess.saveOrUpdate(a);
			} catch (InternationalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
	  }
	  
//	  @Test
	  public void geoTest(){
		  String address ="35 goldfish drive";
//		  String address = "6 sun island road";
//		  String address = "";
		  String city = "nantucket";
		  String state = "MA";
		  String zip = "02554";
//		  String zip = null;
		  String province = null;
		  String country = "US";
		  try{
		  GeoLocation gl = GeoCode.locate(address, city, state, zip, province, country, null);
		  if(gl!=null){
		  System.out.println(gl.getLatitude()+":"+gl.getLongitude());
		  }else{
			  System.out.println("null");
		  }
		  }catch(Exception e){
			  e.printStackTrace();
		  }
	  }
}
