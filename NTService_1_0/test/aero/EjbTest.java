package aero;

import java.util.LinkedHashSet;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Test;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;
import aero.nettracer.fs.model.PnrData;
import aero.nettracer.fs.model.Reservation;
import aero.nettracer.fs.model.Segment;
import aero.nettracer.selfservice.fraud.ClaimBean;
import aero.nettracer.selfservice.fraud.ClaimRemote;


public class EjbTest {

	  static String user     = null;
	  static String password = null;
//	  static String url      = "jnp://127.0.0.1:1199";
//	  static String url      = "jnp://192.168.2.145:1199";
//	  static String url      = "jnp://localhost:1199";
	  static String url      = "jnp://10.8.185.136:1599";
	
	  static public Context getInitialContext() throws Exception {
		    Properties p = new Properties();
		    p.put(Context.INITIAL_CONTEXT_FACTORY,
		          "org.jnp.interfaces.NamingContextFactory");
		    p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces"); 
		    p.put(Context.PROVIDER_URL, url);
		    if (user != null) {
		      System.out.println ("user: " + user);
		      p.put(Context.SECURITY_PRINCIPAL, user);
		      if (password == null) 
		        password = "";
		      p.put(Context.SECURITY_CREDENTIALS, password);
		    } 
		    return new InitialContext(p);
		  }

//	  //@Test
//	  public void connectionTest(){
//		  try{
//		  Context ctx          = getInitialContext();
//		  System.out.println("got connection...");
//	      Calculator dhome = (Calculator) ctx.lookup("Calculator/remote");
//
//	      System.out.println("Creating Demo\n");
//	     
//	      
//	      // Here is the call that executes the method on the 
//	      // server side object
//	      System.out.println("The result is " + dhome.add(2, 3));
//		  }catch(Exception e){
//			  e.printStackTrace();
//		  }
//
//	  }
	  
	  @Test
	  public void hello(){
//			System.setProperty("javax.net.ssl.trustStore", "c:\\secure\\keystore.jks");
//		    System.setProperty("javax.net.ssl.trustStorePassword", "nettracer");
//		    System.setProperty("javax.net.ssl.keyStore", "c:\\secure\\keystore.jks");
//		    System.setProperty("javax.net.ssl.keyStorePassword", "nettracer");
//		  
		  try{
			  
			  Context ctx          = getInitialContext();
			  ClaimRemote o = (ClaimRemote) ctx.lookup("NTServices_1_0/ClaimBean/remote");
			  System.out.println(o.echoTest("hello world"));
//			  
//			  for (long i=0; i<312989; ++i) {
//				  if (i%100 == 0) {
//					  System.out.println(i);
//				  }
//				  try {
//				  o.traceFile(19286);
//				  o.traceFile(56786);
//				  o.traceFile(167063);
//				  } catch (Exception e) {
//					  // Ignore
//				  }
//			  }
			  
		  } catch (Exception e){
			  e.printStackTrace();
		  }
	  }
	  
	  
//	  @Test
//	  public void echo(){
//			System.setProperty("javax.net.ssl.trustStore", "c:\\secure\\keystore.jks");
//		    System.setProperty("javax.net.ssl.trustStorePassword", "nettracer");
//		    System.setProperty("javax.net.ssl.keyStore", "c:\\secure\\keystore.jks");
//		    System.setProperty("javax.net.ssl.keyStorePassword", "nettracer");
//		  try{
//			  Context ctx          = getInitialContext();
//			  ClaimRemote o = (ClaimRemote) ctx.lookup("NTServices_1_0/ClaimBean/remote");
////			  PrivacyPermissionsRemote o = (PrivacyPermissionsRemote) ctx.lookup("permissionsSSL");
////			  System.out.println(o.hello());
//			  System.out.println(o.echoTest("hello World"));
//			  FsClaim claim = createFsClaim();
//			  long id = o.insertClaim(createFsClaim());
//			  claim.setSwapId(id);
//			  System.out.println(id);
//			  System.out.println(o.insertClaim(claim));
//		  } catch (Exception e){
//			  e.printStackTrace();
//		  }
//	  }
	  /*
//	  @Test
	  public void updateTest(){
		  try{
			  ClaimBean bean = new ClaimBean();
			  File file = createFile();
			  file.getIncident().setAirline("B6");
			  long id = bean.insertFile(createFile());
			  bean.traceFile(id);
			  
//			  file.setSwapId(id);
//			  System.out.println("file:"+id);
//			  System.out.println(bean.insertFile(file));
		  } catch (Exception e){
			  e.printStackTrace();
		  }
	  }
	  */
//	  @Test
//		public void letsSeeWhatWeGet(){
//			System.setProperty("javax.net.ssl.trustStore", "c:\\secure\\keystore.jks");
//		    System.setProperty("javax.net.ssl.trustStorePassword", "nettracer");
//		    System.setProperty("javax.net.ssl.keyStore", "c:\\secure\\keystore.jks");
//		    System.setProperty("javax.net.ssl.keyStorePassword", "nettracer");
//		  try{
//			  Context ctx          = getInitialContext();
//			  ClaimRemote o = (ClaimRemote) ctx.lookup("NTServices_1_0/ClaimBean/remote");
//			  
//		  
//			String sql = "select id from fsclaim";
//			SQLQuery pq = null;
//			Session sess = HibernateWrapper.getSession().openSession();
//			pq = sess.createSQLQuery(sql.toString());
//			pq.addScalar("id", Hibernate.LONG);
//			List<Long> result = pq.list();
//			sess.close();
//			Date start = new Date();
//			Date tick = new Date();
//			int i = 0;
//			for (Long strs : result) {
//				i++;
////				System.out.println("claim: " + strs);
//				if(i%20 == 0){
//					tick = new Date();
//					double percentDone = i/result.size();
//					long tpc = (long) ((tick.getTime() - start.getTime())/i);
//					long timeLeft = tpc * (result.size() - i);
//					System.out.println("" + (timeLeft/60000) + "   " + i + "/" + result.size());
//					System.out.println(o.getIncidentCacheSize() + "   " + o.getClaimCacheSize());
//				}
//				
//				Long c1 = (Long) strs;
//				if(c1 != null){
////					Producer.matchClaim(c1);
//					o.traceClaim(c1);
//				}
//			}
//		  }catch (Exception e){
//			  e.printStackTrace();
//		  }
//			
//		}
	  
//	  @Test
//	  public void objectReset(){
//		  FsClaim c = new FsClaim();
//		  c.setAirline("WS");
//		  c.setIncident(new FsIncident());
//		  ClaimBean.resetIdAndgeocode(c);	  
//	  }
	  
	  public static File createFile() {

		  	File file = new File();
		  
			// create the claim
			FsClaim claim = new FsClaim();
			file.setClaim(claim);
			claim.setFile(file);
			
			claim.setAirline("WS");

			// create the person
			Person person = new Person();


			// create the claim currency
			String currency = "USD";
			
			// create the address
			FsAddress address = new FsAddress();
			address.setPerson(person);
			address.setAddress1("2675 Paces Ferry");
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
			
			fsIncident.setAirline("WS");
			
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
	  
}
