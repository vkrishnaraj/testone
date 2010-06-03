/**
 * OnlineClaimsServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.bagnet.nettracer.ws.onlineclaims;

import org.apache.axis2.AxisFault;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.dao.AuthorizationException;
import com.bagnet.nettracer.tracing.dao.OnlineClaimsDao;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim;
import com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim.ClaimStatus;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident;
import com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument.LoadClaimResponse;
import com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument.SaveClaimResponse;
import com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth;
import com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView;
import com.bagnet.nettracer.ws.passengerview.PassengerViewUtil;

/**
 *  OnlineClaimsServiceSkeleton java skeleton for the axisService
 */
public class OnlineClaimsServiceImplementation extends OnlineClaimsServiceSkeleton {
	/**
	 * Auto generated method signature
	 * @param saveClaim
	 * @throws AuthorizationException 
	 */
	public com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument saveClaim(com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument saveClaim) {
		NtAuth auth = saveClaim.getSaveClaim().getAuth();
		authenticate(auth);
		
		
		SaveClaimResponseDocument res = SaveClaimResponseDocument.Factory.newInstance();
		SaveClaimResponse res2 = res.addNewSaveClaimResponse();
		res2.setReturn(false);
		
		String incidentId = saveClaim.getSaveClaim().getIncidentId();
		incidentId = StringUtils.fillzero(incidentId).toUpperCase().trim();
		String name = saveClaim.getSaveClaim().getName();
		
		WSPVAdvancedIncident pvData = null;

		PassengerViewUtil u = new PassengerViewUtil();
		pvData = u.findAdvancedIncidentForPVO(incidentId, name, true);

		if (pvData != null) {

			Session sess = null;

			try {
				sess = HibernateWrapper.getSession().openSession();

				OnlineClaimsDao dao = new OnlineClaimsDao();
				OnlineClaim claim = dao.convertClaimWsToDb(saveClaim.getSaveClaim().getClaim(), incidentId);
				
				OnlineClaim c = dao.saveOnlineClaimWsUseOnly(claim, incidentId, null);
				if (c != null) {
					res2.setReturn(true);
				} 
			} catch (Exception e) {
				// Ignore Authorization Exception
			} finally {
				sess.close();
			}
		}
		return res;
	}

	private void authenticate(NtAuth auth) {
		System.out.println("Username: " + auth.getUsername());
		System.out.println("Password: " + auth.getPassword());

		if (auth == null || auth.getUsername() == null || auth.getPassword() == null) { 
			throw new RuntimeException("Unauthorized Access...");
		} else if (!auth.getUsername().equals("onlineclaims") || !auth.getPassword().equals("B651kLN5")) {
			throw new RuntimeException("Unauthorized Access...");
		}
	}

	/**
	 * Auto generated method signature
	 * @param authAdminUser
	 */
	public com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument authAdminUser(com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument authAdminUser) {
		NtAuth auth = authAdminUser.getAuthAdminUser().getAuth();
		authenticate(auth);


		boolean value = false;
		try {
			OnlineClaimsService cs = new OnlineClaimsService();

			SecurityUtils util = new SecurityUtils();
			Agent a = util.authAdminUser(authAdminUser.getAuthAdminUser().getUsername(), authAdminUser.getAuthAdminUser().getPassword());
			if (a != null) {
				value = true;
			} else {
				value = false;
			}
			
		} catch (Exception e) {
			// Do Nothing
		}

		AuthAdminUserResponseDocument res = AuthAdminUserResponseDocument.Factory.newInstance();
		res.addNewAuthAdminUserResponse().setReturn(value);
		return res;

	}


	/**
	 * Auto generated method signature
	 * @param authPassenger
	 */
	public com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument authPassenger(com.bagnet.nettracer.ws.onlineclaims.AuthPassengerDocument authPassenger) {
		NtAuth auth = authPassenger.getAuthPassenger().getAuth();
		authenticate(auth);

		AuthPassengerResponseDocument res = AuthPassengerResponseDocument.Factory.newInstance();
		PassengerView pv = res.addNewAuthPassengerResponse().addNewReturn();

		boolean authSuccess = false;
		WSPVAdvancedIncident pvData = null;
		long claimId = 0;

		String incidentId = authPassenger.getAuthPassenger().getIncidentId();
		String name = authPassenger.getAuthPassenger().getPassengerLastName();

		PassengerViewUtil u = new PassengerViewUtil();

		incidentId = StringUtils.fillzero(incidentId).toUpperCase().trim();
		pvData = u.findAdvancedIncidentForPVO(incidentId, name, true);

		if (pvData != null) {
			authSuccess = true;

			Session sess = null;

			try {
				sess = HibernateWrapper.getSession().openSession();

				OnlineClaimsDao dao = new OnlineClaimsDao();
				OnlineClaim c = dao.getOnlineClaim(incidentId);
				if (c != null) {
					claimId = c.getClaimId();
				}
			} finally {
				sess.close();
			}
		}


		pv.setClaimId(claimId);
		pv.setData(pvData);
		pv.setAuthenticationSuccess(authSuccess);

		return res;

	}

	/**
	 * Auto generated method signature
	 * @param loadClaim
	 * @throws AuthorizationException 
	 */
	public com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument loadClaim(com.bagnet.nettracer.ws.onlineclaims.LoadClaimDocument loadClaim) {
		NtAuth auth = loadClaim.getLoadClaim().getAuth();
		authenticate(auth);

		LoadClaimResponseDocument res = LoadClaimResponseDocument.Factory.newInstance();
		LoadClaimResponse res2 = res.addNewLoadClaimResponse();
		
		String incidentId = loadClaim.getLoadClaim().getIncidentId();
		incidentId = StringUtils.fillzero(incidentId).toUpperCase().trim();
		String name = loadClaim.getLoadClaim().getName();
		
		WSPVAdvancedIncident pvData = null;

		PassengerViewUtil u = new PassengerViewUtil();
		pvData = u.findAdvancedIncidentForPVO(incidentId, name, true);

		if (pvData != null) {

			Session sess = null;

			try {
				sess = HibernateWrapper.getSession().openSession();

				OnlineClaimsDao dao = new OnlineClaimsDao();
				OnlineClaim c = null;
				c = dao.getOnlineClaim(incidentId);
				
				if (c == null) {
					c = new OnlineClaim();
					Incident i = new Incident();
					i.setIncident_ID(incidentId);
					c.setIncident(i);
					c.setStatus(ClaimStatus.NEW.toString());
					dao.saveOnlineClaimWsUseOnly(c, incidentId, null);
				}
				
				res2.setReturn(dao.convertClaimDbToWs(c));
				
			} catch (AuthorizationException e) { 
				// Ignore AuthorizationException
			} finally {
				sess.close();
			}
		}
		
		return res;
		
	}

	/**
	 * Auto generated method signature
	 * @param deleteFile
	 */
	public com.bagnet.nettracer.ws.onlineclaims.DeleteFileResponseDocument deleteFile(com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument deleteFile) {
		NtAuth auth = deleteFile.getDeleteFile().getAuth();
		authenticate(auth);

		
		//TODO : fill this with the necessary business logic
		throw new java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#deleteFile");
	}

	/**
	 * Auto generated method signature
	 * @param uploadFile
	 */
	public com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument uploadFile(com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument uploadFile) {
		NtAuth auth = uploadFile.getUploadFile().getAuth();
		authenticate(auth);

		
		//TODO : fill this with the necessary business logic
		throw new java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#uploadFile");
	}

}
