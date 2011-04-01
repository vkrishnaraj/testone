package com.bagnet.nettracer.tracing.utils.ntfs;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConnectionUtil {
	  static String user     = null;
	  static String password = null;
	  static String url      = "jnp://localhost:1199";

	
	  static public Context getInitialContext() throws NamingException {
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
}
