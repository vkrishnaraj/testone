import static org.junit.Assert.assertTrue;


import java.util.Date;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Test;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.selfservice.fraud.ClaimRemote;


public class FraudTest {
	  static String user     = null;
	  static String password = null;
//	  static String url      = "jnp://127.0.0.1:1199";
	  static String url      = "jnp://192.168.2.145:1199";

	
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
	  
	  @Test
	  public void echo(){
			System.setProperty("javax.net.ssl.trustStore", "c:\\secure\\keystore.jks");
		    System.setProperty("javax.net.ssl.trustStorePassword", "nettracer");
		    System.setProperty("javax.net.ssl.keyStore", "c:\\secure\\keystore.jks");
		    System.setProperty("javax.net.ssl.keyStorePassword", "nettracer");
		  try{
			  Context ctx          = getInitialContext();
//			  ClaimRemote o = (ClaimRemote) ctx.lookup("NTServices_1_0/ClaimBean/remote");
			  ClaimRemote o = (ClaimRemote) ctx.lookup("ClaimRemoteSSL3843");
//			  PrivacyPermissionsRemote o = (PrivacyPermissionsRemote) ctx.lookup("permissionsSSL");
//			  System.out.println(o.hello());
			  System.out.println(o.echoTest("hello World"));
			  
			  
//			  Agent user = new Agent();
//			  user.setAgent_ID(1755);
//			  user.setCompanycode_ID("WS");
//			  Incident i = new Incident();
//			  i.setAgent(user);
//			  i.setCreatedate(new Date());
//			  i.setIncident_ID("FLLWS123456");
//			  
//				FsIncident fsinc = ClaimUtils.getFsIncident(i, user);
//				File f = new File();
//				f.setValidatingCompanycode("WS");
//				f.setIncident(fsinc);
//				fsinc.setFile(f);
//				
//				
//				f.getIncident().setAirline("WS");
////				ClaimBean bean = new ClaimBean();
//				long id = o.insertFile(f);
//				f.setSwapId(id);
//				assertTrue(id>0);
//				
//				File loadFile = o.getFile(id, f.getValidatingCompanycode());
//				assertTrue(loadFile.getId() == id);
//				assertTrue(loadFile.getIncident().getAirline().equals("WS"));
//				loadFile.setSwapId(loadFile.getId());
//				
//				loadFile.getIncident().setAirline("B6");
//				o.insertFile(loadFile);
//				
//				File updatedFile = o.getFile(loadFile.getSwapId(), f.getValidatingCompanycode());
//				assertTrue(updatedFile.getIncident().getAirline().equals("B6"));
			  
		  } catch (Exception e){
			  e.printStackTrace();
		  }
	  }
}
