package aero.nettracer.general.services;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Test;

import com.bagnet.nettracer.tracing.db.CountryCode;

import aero.nettracer.lf.services.LFServiceRemote;

public class GeneralServiceRemoteTest {
	  static String user     = null;
	  static String password = null;
//	  static String url      = "jnp://127.0.0.1:1199";
//	  static String url      = "jnp://192.168.2.145:1199";
	  static String url      = "jnp://localhost:1399";
	
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
	  public void test(){
		  Context ctx;
		try {
			ctx = getInitialContext();
			GeneralServiceRemote go = (GeneralServiceRemote) ctx.lookup("tracer/GeneralServiceBean/remote");
			System.out.println(go.echo("hello world"));
			List<CountryCode> countries = go.getCountries();
			for(CountryCode country:countries){
				System.out.println(country.getCountry());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
}
