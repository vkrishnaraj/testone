package com.bagnet.nettracer.tracing.utils.ntfs;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;

import aero.nettracer.selfservice.fraud.PrivacyPermissionsRemote;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions.AccessLevelType;

public class PrivacyPermissionsUtil {
	  
	  
	
	  public static PrivacyPermissions getPrivacyPermissions(String companycode,  AccessLevelType level) throws Exception{
		  try{
			  Context ctx          = ConnectionUtil.getInitialContext();
			  PrivacyPermissionsRemote o = (PrivacyPermissionsRemote) ConnectionUtil.getRemoteEjb(ctx,PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_PERMISSION_SERVICE_NAME));
			  if(o != null){
			  return o.getPrivacyPermissions(companycode, level);
			  } else {
				  throw new 
				  Exception("Remote EJB connection failed");
			  }
		  } catch (NamingException e){
			  e.printStackTrace();
			  throw e;
		  }
	  }
	  
	  public static boolean setPrivacyPermissions(PrivacyPermissions p) throws NamingException{
		  try{
			  Context ctx          = ConnectionUtil.getInitialContext();
			  PrivacyPermissionsRemote o = (PrivacyPermissionsRemote)ConnectionUtil.getRemoteEjb(ctx,PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_PERMISSION_SERVICE_NAME));
			  if(o != null){
				  o.setPrivacyPermissions(p);
				  return true;
			  }
		  } catch (NamingException e){
			  e.printStackTrace();
			  throw e;
		  }
		  return false;
	  }
	  
}
