package aero.nettracer;

import java.util.Date;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Test;

import aero.nettracer.selfservice.fraud.ClaimRemote;
import aero.nettracer.selfservice.fraud.PrivacyPermissionsRemote;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions.AccessLevelType;

public class EJBTestConnection {
	  static String user     = null;
	  static String password = null;
//	  static String url      = "jnp://127.0.0.1:1199";
//	  static String url      = "jnp://184.172.41.4:1999";
//	  static String url      = "jnp://184.172.24.144:1899";
	  static String url      = "jnp://184.172.41.5:1099";
//	  static String url      = "jnp://192.168.2.145:3843";

	
	  static public Context getInitialContext() throws Exception {
		    Properties p = new Properties();
		    p.put("jnp.socketFactory", "org.jnp.interfaces.TimedSocketFactory");
		    p.put("jnp.timeout", "1000");
		    p.put("jnp.sotimeout", "5000");
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
	  public void hello(){
//
//			System.setProperty("javax.net.ssl.trustStore", "c:\\secure\\keystore.jks");
//		    System.setProperty("javax.net.ssl.trustStorePassword", "nettracer");
//		    System.setProperty("javax.net.ssl.keyStore", "c:\\secure\\keystore.jks");
//		    System.setProperty("javax.net.ssl.keyStorePassword", "nettracer");
		    Date start = new Date();


		  try{
			  
			  Context ctx          = getInitialContext();
			  Date end = new Date();
			  System.out.println(end.getTime() - start.getTime());
			  start = new Date();
			  ClaimRemote o = (ClaimRemote) ctx.lookup("NTServices_1_0/ClaimBean/remote");

			  System.out.println(o.echoTest("hello world"));
			  ctx.close();


			  
		  } catch (Exception e){
			  e.printStackTrace();
		  } finally {
			  Date end = new Date();
			  System.out.println(end.getTime() - start.getTime());
		  }
	  }
}
