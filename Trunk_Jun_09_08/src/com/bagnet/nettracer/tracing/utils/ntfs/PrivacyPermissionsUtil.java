package com.bagnet.nettracer.tracing.utils.ntfs;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import aero.nettracer.selfservice.fraud.PrivacyPermissionsRemote;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions.AccessLevelType;

public class PrivacyPermissionsUtil {
	  
	  public static PrivacyPermissions getPrivacyPermissions(String companycode,  AccessLevelType level) throws NamingException{
		  try{
			  Context ctx          = ConnectionUtil.getInitialContext();
			  PrivacyPermissionsRemote o = (PrivacyPermissionsRemote) ctx.lookup("NTServices_1_0/PrivacyPermissionsBean/remote");
			  return o.getPrivacyPermissions(companycode, level);
		  } catch (NamingException e){
			  e.printStackTrace();
			  throw e;
		  }
	  }
	  
	  public static void setPrivacyPermissions(PrivacyPermissions p) throws NamingException{
		  try{
			  Context ctx          = ConnectionUtil.getInitialContext();
			  PrivacyPermissionsRemote o = (PrivacyPermissionsRemote) ctx.lookup("NTServices_1_0/PrivacyPermissionsBean/remote");
			  o.setPrivacyPermissions(p);
		  } catch (NamingException e){
			  e.printStackTrace();
			  throw e;
		  }
	  }
	  
}
