package com.bagnet.nettracer.tracing.utils.ntfs;

import javax.naming.Context;
import javax.naming.NamingException;

import aero.nettracer.fs.model.Claim;
import aero.nettracer.selfservice.fraud.ClaimRemote;


public class ClaimUtil {
	  public static String echoTest(String s) throws NamingException{
		  try{
			  Context ctx          = ConnectionUtil.getInitialContext();
			  ClaimRemote o = (ClaimRemote) ctx.lookup("NTServices_1_0/ClaimBean/remote");
			  return o.echoTest(s);
		  } catch (NamingException e){
			  e.printStackTrace();
			  throw e;
		  }
	  }
	  
	  public static boolean submitClaim(Claim claim) throws NamingException{
		  try{
			  Context ctx          = ConnectionUtil.getInitialContext();
			  ClaimRemote o = (ClaimRemote) ctx.lookup("NTServices_1_0/ClaimBean/remote");
			  return o.insertClaim(claim);
		  } catch (NamingException e){
			  e.printStackTrace();
			  throw e;
		  }
	  }
}
