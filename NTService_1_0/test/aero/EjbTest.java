package aero;

import javax.ejb.*;
import javax.naming.*;

import org.junit.Test;

import java.rmi.*;
import java.util.Properties;

import aero.nettracer.selfservice.fraud.HelloRemote;
import aero.nettracer.selfservice.fraud.PrivacyPermissionsRemote;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions.AccessLevelType;


public class EjbTest {

	  static String user     = null;
	  static String password = null;
	  static String url      = "jnp://192.168.2.145:4850";
//	  static String url      = "jnp://localhost:1199";

	
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
		  try{
			  Context ctx          = getInitialContext();
//			  PrivacyPermissionsRemote o = (PrivacyPermissionsRemote) ctx.lookup("NTServices_1_0/PrivacyPermissionsBean/remote");
			  PrivacyPermissionsRemote o = (PrivacyPermissionsRemote) ctx.lookup("permissionsSSL");
//			  System.out.println(o.hello());
			  PrivacyPermissions p = o.getPrivacyPermissions("WS", AccessLevelType.def);
			  System.out.println(p.isName());
			  p.setName(false);
			  o.setPrivacyPermissions(p);
		  } catch (Exception e){
			  e.printStackTrace();
		  }
	  }
	  
}
