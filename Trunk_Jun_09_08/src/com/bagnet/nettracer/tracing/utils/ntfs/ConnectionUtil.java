package com.bagnet.nettracer.tracing.utils.ntfs;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import aero.nettracer.selfservice.fraud.ClaimRemote;

public class ConnectionUtil {
	  static String user     = null;
	  static String password = null;
	  static String url      = "jnp://192.168.2.145:1199";

	
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
	  
	  public static ClaimRemote getClaimRemote() {
		  try {
			  Context ctx = ConnectionUtil.getInitialContext();
			  ClaimRemote o = (ClaimRemote) ctx.lookup("NTServices_1_0/ClaimBean/remote");
			  return o;
		  } catch (NamingException ne) {
			  ne.printStackTrace();
			  return null;
		  }
	  }
}
