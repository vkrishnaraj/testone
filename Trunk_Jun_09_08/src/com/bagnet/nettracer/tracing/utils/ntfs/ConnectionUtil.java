package com.bagnet.nettracer.tracing.utils.ntfs;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.detection.TraceResponse;
import aero.nettracer.selfservice.fraud.ClaimRemote;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;

public class ConnectionUtil {
	  static String user     = null;
	  static String password = null;
	  static String url      = PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVER_LOCATION);

	
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
	  
	  public static long insertFile(File file) throws NamingException {
		  Context ctx = getInitialContext();
		  long id = -1;
		  if (ctx != null) {
			  ClaimRemote remote = (ClaimRemote) ctx.lookup("NTServices_1_0/ClaimBean/remote");
			  id = remote.insertFile(file);
			  ctx.close();
		  }
		  return id;
	  }
	  
	  public static TraceResponse submitClaim(long fileId, boolean primary) {
			if (fileId <= 0) {
				return null;
			}
			TraceResponse results = null;
			try {
				Context ctx = ConnectionUtil.getInitialContext();
				ClaimRemote remote = (ClaimRemote) ctx.lookup("NTServices_1_0/ClaimBean/remote");
				
				if (remote != null) {
					int wait = 6;
					try {
						wait = PropertyBMO.getValueAsInt(PropertyBMO.CENTRAL_FRAUD_CHECK_TIMEOUT);
					} catch (Exception e) {
						//
					}
					results = remote.traceFile(fileId, wait, primary);
				}
				ctx.close();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			return results;
		}
	  
	  // DO NOT USE - NEED TO CLOSE CONTEXT
//	  public static ClaimRemote getClaimRemote() {
//		  try {
//			  Context ctx = ConnectionUtil.getInitialContext();
//			  ClaimRemote o = (ClaimRemote) ctx.lookup("NTServices_1_0/ClaimBean/remote");
//			  return o;
//		  } catch (NamingException ne) {
//			  ne.printStackTrace();
//			  return null;
//		  }
//	  }
}
