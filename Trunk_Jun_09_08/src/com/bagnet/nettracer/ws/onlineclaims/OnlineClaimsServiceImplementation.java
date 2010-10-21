/**
 * OnlineClaimsServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.bagnet.nettracer.ws.onlineclaims;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.dao.AuthorizationException;
import com.bagnet.nettracer.tracing.dao.OnlineClaimsDao;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCFile;
import com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim;
import com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim.ClaimStatus;
import com.bagnet.nettracer.tracing.utils.ImageUtils;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident;
import com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument.LoadClaimResponse;
import com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument.SaveClaimResponse;
import com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.UploadFile;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Claim;
import com.bagnet.nettracer.ws.onlineclaims.xsd.File;
import com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth;
import com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView;
import com.bagnet.nettracer.ws.passengerview.PassengerViewUtil;

/**
 *  OnlineClaimsServiceSkeleton java skeleton for the axisService
 */
public class OnlineClaimsServiceImplementation extends OnlineClaimsServiceSkeleton {
	private static final String UNABLE_TO_CREATE_DIRECTORY = "Unable to create directory";
	/**
	 * Auto generated method signature
	 * @param saveClaim
	 * @throws AuthorizationException 
	 */
	Logger logger = Logger.getLogger(OnlineClaimsServiceImplementation.class);
	
	public com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument saveClaim(com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument saveClaim) {
		logger.info("Save claim request: \n" + saveClaim);
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
				e.printStackTrace();
			} finally {
				sess.close();
			}
		}
		logger.info("Response: \n" + res);
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
		logger.info("Auth admin user: \n" + authAdminUser);
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
		logger.info("Response: \n" + res);
		return res;

	}


	/**
	 * Auto generated method signature
	 * @param authPassenger
	 */
	public com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument authPassenger(com.bagnet.nettracer.ws.onlineclaims.AuthPassengerDocument authPassenger) {
		logger.info("Auth passenger user: \n" + authPassenger);
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
		logger.info("Response: \n" + res);
		return res;

	}

	/**
	 * Auto generated method signature
	 * @param loadClaim
	 * @throws AuthorizationException 
	 */
	public com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument loadClaim(com.bagnet.nettracer.ws.onlineclaims.LoadClaimDocument loadClaim) {
		logger.info("Load claim: \n" + loadClaim);
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
				Incident inc = (Incident) sess.load(Incident.class, incidentId);
				sess.close();
				
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
				Claim ret = dao.convertClaimDbToWs(c);
				ret.setClaimType(inc.getItemtype().getItemType_ID());
				res2.setReturn(ret);
				
			} catch (AuthorizationException e) { 
				// Ignore AuthorizationException
			} finally {
				sess.close();
			}
		}
		logger.info("Response: \n" + res);
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

		UploadFileResponseDocument resDoc = UploadFileResponseDocument.Factory.newInstance();
		File resFile = resDoc.addNewUploadFileResponse().addNewReturn();
		
		
		Calendar cal = new GregorianCalendar();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);

		UploadFile u = uploadFile.getUploadFile();
		String lead = Long.toString(u.getClaimId());
		String st = Long.toString((new Date()).getTime());

		String fileName = "CLAIM_" + lead + "_" + st + "_" + u.getFilename();
		String filePath = "CLAIMS" + "/" + year + "/" + month + "/" + day + "/";;
		String image_store = TracerProperties.get("image_store");
		String picpath = image_store + filePath + fileName;
		
		if (!ImageUtils.makeFolder(image_store + filePath)) {
			logger.error(UNABLE_TO_CREATE_DIRECTORY);
			throw new RuntimeException(UNABLE_TO_CREATE_DIRECTORY);
		}
		
		Session sess = HibernateWrapper.getSession().openSession();
		org.hibernate.Transaction t = null;
		
		try {
			FileOutputStream fos = new FileOutputStream(picpath);
			byte[] f = u.getFile();
			fos.write(f);
			fos.flush();
	    fos.close();
	    
	    
	    OnlineClaimsDao d = new OnlineClaimsDao();
	    OnlineClaim claim = d.getOnlineClaim(u.getClaimId());
	    
	    OCFile thefile = new OCFile();
	    thefile.setClaim(claim);
	    thefile.setDateUploaded(TracerDateTime.getGMTDate());
	    thefile.setFilename(fileName);
	    
	    t = sess.beginTransaction();
	    sess.save(thefile);
	    t.commit();
	    sess.close();
	    resFile.setFilename(fileName);
	    resFile.setId(thefile.getId());
	    claim.getFile();
	    
    } catch (IOException e) {
	    e.printStackTrace();
    } catch (Exception e) {
    	if (t != null) {
    		t.rollback();
    	}
    }	finally {    
    	if (sess != null)	sess.close();
    }
		return resDoc;
	}

}
