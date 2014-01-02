/**
 * OnlineClaimsServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.bagnet.nettracer.ws.onlineclaims;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.integrations.reservation.ReservationIntegration;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.bmo.exception.StaleStateException;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.AuthorizationException;
import com.bagnet.nettracer.tracing.dao.OnlineClaimsDao;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.OnlineIncidentAuthorization;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.ProactiveNotification;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCFile;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCPassenger;
import com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim;
import com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim.ClaimStatus;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.ImageUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident;
import com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument.LoadClaimResponse;
import com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument.SaveClaimResponse;
import com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentResponseDocument.SaveNewIncidentResponse;
import com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.UploadFile;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Claim;
import com.bagnet.nettracer.ws.onlineclaims.xsd.File;
import com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress;
import com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag;
import com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary;
import com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone;
import com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth;
import com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView;
import com.bagnet.nettracer.ws.passengerview.PassengerViewUtil;

/**
 * OnlineClaimsServiceSkeleton java skeleton for the axisService
 */
public class OnlineClaimsServiceImplementation extends
		OnlineClaimsServiceSkeleton {
	private static final String UNABLE_TO_CREATE_DIRECTORY = "Unable to create directory";
	Logger logger = Logger.getLogger(OnlineClaimsServiceImplementation.class);

	public com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentResponseDocument saveNewIncident(
			com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument saveIncident) {
		logger.info("Save new incident request: \n" + saveIncident);
		NtAuth auth = saveIncident.getSaveNewIncident().getAuth();
		authenticate(auth);

		SaveNewIncidentResponseDocument res = SaveNewIncidentResponseDocument.Factory
				.newInstance();
		SaveNewIncidentResponse res2 = res.addNewSaveNewIncidentResponse();

		com.bagnet.nettracer.ws.onlineclaims.xsd.Incident incident = saveIncident
				.getSaveNewIncident().getIncident();

		String incidentID = saveIncident(incident); // REPLACE THIS WITH
													// GENERATED INCIDENT ID
													// FROM INCIDENT CREATION
		res2.setReturn(incidentID);

		logger.info("Response: \n" + res);
		return res;
	}

	private String saveIncident(com.bagnet.nettracer.ws.onlineclaims.xsd.Incident ws) {
 		IncidentBMO obmo = new IncidentBMO();
 		Incident inc = new Incident();
 		Date thedate = new Date();
 		String companyCode = TracerProperties.get("wt.company.code");
 		logger.error("COMP CODE: " + companyCode);
 		
 		// status
 		Status status = new Status();
 		status.setStatus_ID(TracingConstants.MBR_STATUS_OPEN);
 		status.setTable_ID(TracingConstants.TABLE_INCIDENT);
 		inc.setStatus(status);

 		// agent created
 		Agent agent = AdminUtils.getAgent(PropertyBMO.getValue(PropertyBMO.PROPERTY_OIA_AGENT));
 		if (agent == null) {
 			logger.error("NULL AGENT!!!!");
 		}
 		inc.setAgent(agent);

 		// create date time
		
		inc.setCreatedate(thedate);
		inc.setCreatetime(thedate);
		
		inc.setRecordlocator(ws.getPnr());
		inc.setNumpassengers(ws.getNumPassengers());
		inc.setItemtype(IncidentUtils.retrieveItemTypeWithDesc("Delayed"));		

 		// passengers
		HashSet<Passenger> pahash = new HashSet<Passenger>();
		
		Passenger pa = new Passenger();
		
		pa.setIncident(inc);
		pa.setFirstname(ws.getFirstName());
		pa.setIsprimary(1);
		pa.setLastname(ws.getLastName());
			
		if (ws.getMembershipNumber() != null && ws.getMembershipNumber().length() > 0) {
			 AirlineMembership am = new AirlineMembership();
			 am.setMembershipstatus(ws.getMembershipStatus());
			 am.setMembershipnum(ws.getMembershipNumber());
			 am.setCompanycode_ID(companyCode);
			 pa.setMembership(am);
		}
					

		HashSet<Address> addrhash = new HashSet<Address>();
		Address addr = new Address();
		addr.setPassenger(pa);
	
		IncidentAddress wp = ws.getDeliveryAddress();
		addr.setAddress1(wp.getAddress1());
		addr.setAddress2(wp.getAddress2());
		addr.setCity(wp.getCity());
		addr.setCountrycode_ID(wp.getCountry());
		addr.setEmail(ws.getEmail());
		addr.setProvince(wp.getProvince());
		addr.setState_ID(wp.getState());
		addr.setZip(wp.getPostalCode());
		
		for (IncidentPhone ph : ws.getPhoneArray()) {
			switch (ph.getType()) {
			case 0:
				addr.setHomephone(ph.getNumber());
				break;
			case 1:
				addr.setMobile(ph.getNumber());
				break;
			case 2:	
				addr.setWorkphone(ph.getNumber());
				break;
			default:
				addr.setAltphone(ph.getNumber());
				break;
			}
		}

					
		addrhash.add(addr);
		pa.setAddresses(addrhash);
		pahash.add(pa);
					
		inc.setPassengers(pahash);
		
		// incident claimcheck
		HashSet<Incident_Claimcheck> ic_set = new HashSet<Incident_Claimcheck>();
		
		Incident_Claimcheck ic = null;
		String wic;
		if (ws.getClaimCheckArray() != null) {
			for (int i = 0; i < ws.getClaimCheckArray().length; i++) {
				wic = ws.getClaimCheckArray(i);
				if (wic != null) {
					ic = new Incident_Claimcheck();
					ic.setIncident(inc);
					ic.setClaimchecknum(wic);

					ic_set.add(ic);
				}
			}
			inc.setClaimchecks(ic_set);
		}
		
		// item
		List<Item> ii_list = new ArrayList<Item>();
		Item item = null;
		IncidentBag wi = null;
		
		if (ws.getBagArray() != null) {
			for (int i = 0; i < ws.getBagArray().length; i++) {
				wi = ws.getBagArray(i);
				if (wi != null) {
					item = new Item();
					item.setIncident(inc);
					item.setBagnumber(i);
					item.setItemtype_ID(inc.getItemtype_ID());
					item.setBagtype(wi.getType() + "");
					item.setColor(wi.getColor());
			 		status = new Status();
			 		status.setStatus_ID(TracingConstants.ITEM_STATUS_OPEN);
			 		status.setTable_ID(TracingConstants.TABLE_ITEM);
					
			 		item.setStatus(status);
					ii_list.add(item);
				}
			}
			inc.setItemlist(ii_list);
		}

 		// itinerary
		HashSet<Itinerary> oi_set = new HashSet<Itinerary>();
		
		Itinerary oi = null;
		IncidentItinerary wit = null;
		IncidentItinerary[] itins = ws.getItineraryArray();
		if (itins != null) {
			for (int i = 0; i < itins.length; i++) {
				wit = itins[i];
				if (wit != null) {
					oi = new Itinerary();
					oi.setIncident(inc);
					
					oi.setItinerarytype(wit.getType());
					oi.setAirline(wit.getAirline());
					oi.setFlightnum(wit.getFlightNum());
					oi.setLegfrom(wit.getDepartureCity());
					oi.setLegto(wit.getArrivalCity());
					
					// arrive date
					if (wit.getArrivalDate() != null) {
						oi.setArrivedate(wit.getArrivalDate().getTime());
					}
					
					// depart date
					if (wit.getDepartureDate() != null) {
						oi.setDepartdate(wit.getDepartureDate().getTime());
					}
					
					oi_set.add(oi);
				}
			}
			inc.setItinerary(oi_set);
		}
	
 		
 		// create station
				String useStation = "ATL";
				if (itins != null && itins.length > 0) {
					String tempStation = itins[itins.length - 1].getArrivalCity();
					if (tempStation != null && tempStation.length() == 3) {
						useStation = tempStation;
					}
				}
				useStation = "JAX";
 		 		Station createandassigns = StationBMO.getStationByCode(useStation, companyCode);
 		 		if (createandassigns == null) {
 		 			logger.error("NULL STATION!!!!");
 		 		}
 		 		inc.setStationcreated(createandassigns);
 		 		
 		 		// assigned station
 		 		createandassigns = StationBMO.getStationByCode(useStation, companyCode);
 		 		inc.setStationassigned(createandassigns);
 		
		
 		 		inc.setOverall_weight(0D);
 		 		
 		 		
		
		// insert into our db
		int result = 0;
		try {
			result = obmo.insertIncident(false,inc, null, inc.getAgent());
		} catch (HibernateException e1) {
			e1.printStackTrace();
		} catch (StaleStateException e1) {
			//loupas - should never reach this
			e1.printStackTrace();
		}
		if (result < 1) {
			return null;
		}
		
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			OnlineIncidentAuthorization oia = (OnlineIncidentAuthorization) sess.load(OnlineIncidentAuthorization.class, ws.getAuthID());
			oia.setIncidentID(inc.getIncident_ID());
			t = sess.beginTransaction();
			sess.save(oia);
			t.commit();
		} catch (StaleObjectStateException e) {
			logger.error("unable to update onlineIncidentAuthorization table with new IncidentID because it has already been updated. " + e);
			if (t != null) {
				t.rollback();
			}
		} catch (Exception e) {
			logger.error("unable to update oia in database: " + e.toString());
			if (t != null) {
				t.rollback();
			}
		}  finally {
			if (sess != null) {
				sess.close();
			}
		}
		
 		return inc.getIncident_ID();
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param authIncident
	 */
	@SuppressWarnings("unchecked")
	public com.bagnet.nettracer.ws.onlineclaims.AuthIncidentResponseDocument authIncident(
			com.bagnet.nettracer.ws.onlineclaims.AuthIncidentDocument authIncident) {
		logger.info("Auth incident user: \n" + authIncident);
		NtAuth auth = authIncident.getAuthIncident().getAuth();
		authenticate(auth);

		AuthIncidentResponseDocument res = AuthIncidentResponseDocument.Factory
				.newInstance();
		com.bagnet.nettracer.ws.onlineclaims.xsd.Incident incident = res
				.addNewAuthIncidentResponse().addNewReturn();

		incident.setAuthStatus(com.bagnet.nettracer.ws.onlineclaims.Incident.AUTH_FAILURE);

		incident.setPnr(authIncident.getAuthIncident().getPnr());
		incident.setFirstName(authIncident.getAuthIncident().getFirstName());
		incident.setLastName(authIncident.getAuthIncident().getLastName());

		OnlineIncidentAuthorization oiAuth = null;
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			logger.info("Grabbing Authentication information from the DB!");
			Criteria criteria = sess
					.createCriteria(OnlineIncidentAuthorization.class);
			criteria.add(Restrictions.like("firstName", incident.getFirstName()
					.toUpperCase()));
			criteria.add(Restrictions.like("lastName", incident.getLastName()
					.toUpperCase()));
			criteria.add(Restrictions.eq("pnr", incident.getPnr().toUpperCase()));
			List<OnlineIncidentAuthorization> results = new ArrayList<OnlineIncidentAuthorization>(
					criteria.list());
			if (results != null && results.size() > 0) {
				oiAuth = results.get(0);
			}

		} finally {
			if (sess != null)
			sess.close();
		}

		if (oiAuth != null) { // MAKE SURE ROW EXISTS HERE

			incident.setAuthID(oiAuth.getId());

			if (oiAuth.getIncidentID() != null) { // CHECK FOR ALREADY COMPLETED
				incident.setAuthStatus(com.bagnet.nettracer.ws.onlineclaims.Incident.AUTH_COMPLETE);
				incident.setPnr(oiAuth.getIncidentID()); // SETTING PNR TO
															// INCIDENT ID
															// BECAUSE THAT IS
															// THE ONLY INFO WE
															// WILL NEED
			} else if (oiAuth.getExpireTime().before(new Date())) { // CHECK FOR
																	// EXPIRED
				incident.setAuthStatus(com.bagnet.nettracer.ws.onlineclaims.Incident.AUTH_EXPIRED);
			} else { // FULL SUCCESS
				incident.setAuthStatus(com.bagnet.nettracer.ws.onlineclaims.Incident.AUTH_SUCCESS);

				int passIndex = 0;
				ProactiveNotification pcn = oiAuth.getPcn();
				if (pcn != null) {

					for (OHD_Log ohdLog : pcn.getOhd_logs()) {
						OHD ohd = ohdLog.getOhd();
						if (ohd != null) {
							incident.addClaimCheck(ohd.getClaimnum());
						}
					}
					passIndex = oiAuth.getPcn().getPassIndex();
				}

				// Populates Address, Email, Phone, Membership info, and Itins
				// using pnr and passIndex.
				ReservationIntegration resInt = (ReservationIntegration) SpringUtils
						.getBean(SpringUtils.RESERVATION_INTEGRATION);
				incident = resInt.populateIncidentForWS(incident, passIndex);

			}
		}

		logger.info("Response: \n" + res);
		return res;

	}

	/**
	 * Auto generated method signature
	 * 
	 * @param saveClaim
	 * @throws AuthorizationException
	 */
	public com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument saveClaim(
			com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument saveClaim) {
		logger.info("Save claim request: \n" + saveClaim);
		NtAuth auth = saveClaim.getSaveClaim().getAuth();
		authenticate(auth);

		SaveClaimResponseDocument res = SaveClaimResponseDocument.Factory
				.newInstance();
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
				OnlineClaim claim = dao.convertClaimWsToDb(saveClaim
						.getSaveClaim().getClaim(), incidentId);

				OnlineClaim c = dao.saveOnlineClaimWsUseOnly(claim, incidentId,
						null);
				if (c != null) {
					res2.setReturn(true);
				}
			} catch (Exception e) {
				// Ignore Authorization Exception
				e.printStackTrace();
			} finally {
				if (sess != null) sess.close();
			}
		}
		logger.info("Response: \n" + res);
		return res;
	}

	private void authenticate(NtAuth auth) {
		System.out.println("Username: " + auth.getUsername());
		System.out.println("Password: " + auth.getPassword());

		if (auth == null || auth.getUsername() == null
				|| auth.getPassword() == null) {
			throw new RuntimeException("Unauthorized Access...");
		} else if (!auth.getUsername().equals("onlineclaims")
				|| !auth.getPassword().equals("B651kLN5")) {
			throw new RuntimeException("Unauthorized Access...");
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param authAdminUser
	 */
	public com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument authAdminUser(
			com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument authAdminUser) {
		logger.info("Auth admin user: \n" + authAdminUser);
		NtAuth auth = authAdminUser.getAuthAdminUser().getAuth();
		authenticate(auth);

		boolean value = false;
		try {
			SecurityUtils util = new SecurityUtils();
			Agent a = util.authAdminUser(authAdminUser.getAuthAdminUser()
					.getUsername(), authAdminUser.getAuthAdminUser()
					.getPassword());
			if (a != null) {
				value = true;
			} else {
				value = false;
			}

		} catch (Exception e) {
			// Do Nothing
		}

		AuthAdminUserResponseDocument res = AuthAdminUserResponseDocument.Factory
				.newInstance();
		res.addNewAuthAdminUserResponse().setReturn(value);
		logger.info("Response: \n" + res);
		return res;

	}

	/**
	 * Auto generated method signature
	 * 
	 * @param authPassenger
	 */
	public com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument authPassenger(
			com.bagnet.nettracer.ws.onlineclaims.AuthPassengerDocument authPassenger) {
		logger.info("Auth passenger user: \n" + authPassenger);
		NtAuth auth = authPassenger.getAuthPassenger().getAuth();
		authenticate(auth);

		AuthPassengerResponseDocument res = AuthPassengerResponseDocument.Factory
				.newInstance();
		PassengerView pv = res.addNewAuthPassengerResponse().addNewReturn();

		boolean authSuccess = false;
		WSPVAdvancedIncident pvData = null;
		long claimId = 0;

		String incidentId = authPassenger.getAuthPassenger().getIncidentId();
		String name = authPassenger.getAuthPassenger().getPassengerLastName();
		String fName = authPassenger.getAuthPassenger().getPassengerFirstName();

		PassengerViewUtil u = new PassengerViewUtil();

		incidentId = StringUtils.fillzero(incidentId).toUpperCase().trim();
		pvData = u.findAdvancedIncidentForPVO(incidentId, name, fName, true);

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
				if (sess != null) sess.close();
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
	 * 
	 * @param loadClaim
	 * @throws AuthorizationException
	 */
	public com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument loadClaim(
			com.bagnet.nettracer.ws.onlineclaims.LoadClaimDocument loadClaim) {
		logger.info("Load claim: \n" + loadClaim);
		NtAuth auth = loadClaim.getLoadClaim().getAuth();
		authenticate(auth);

		LoadClaimResponseDocument res = LoadClaimResponseDocument.Factory
				.newInstance();
		LoadClaimResponse res2 = res.addNewLoadClaimResponse();

		String incidentId = loadClaim.getLoadClaim().getIncidentId();
		incidentId = StringUtils.fillzero(incidentId).toUpperCase().trim();
		String name = loadClaim.getLoadClaim().getName();
		String fName = loadClaim.getLoadClaim().getFName();

		WSPVAdvancedIncident pvData = null;

		PassengerViewUtil u = new PassengerViewUtil();
		pvData = u.findAdvancedIncidentForPVO(incidentId, name, fName, true);

		if (pvData != null) {

			Session sess = null;

			try {
				sess = HibernateWrapper.getSession().openSession();

				OnlineClaimsDao dao = new OnlineClaimsDao();
				OnlineClaim c = null;
				c = dao.getOnlineClaim(incidentId);
				boolean diffPax = false;

				if (c == null) {
					c = new OnlineClaim();
					Incident i = IncidentBMO.getIncidentByID(incidentId, sess);
					c.setIncident(i);
					c.setStatus(ClaimStatus.NEW.toString());
					Set<OCPassenger> paxes = new HashSet<OCPassenger>();
					OCPassenger pass = new OCPassenger();
					pass.setFirstName(fName);
					pass.setLastName(name);
					paxes.add(pass);
					c.setPassenger(paxes);
					dao.saveOnlineClaimWsUseOnly(c, incidentId, null, sess);
				} else if (checkPrevSubmissionDiffPax(c, name, fName)){
					diffPax = true;
				}
				Claim ret = dao.convertClaimDbToWs(c);
				if (diffPax) {
					ret.setStatus("SECOND_CLAIM");
				}
				res2.setReturn(ret);

			} catch (AuthorizationException e) {
				// Ignore AuthorizationException
			} finally {
				if (sess != null) sess.close();
			}
		}
		logger.info("Response: \n" + res);
		return res;

	}
	
	private boolean checkPrevSubmissionDiffPax(OnlineClaim c, String lName, String fName) {
		if (c.getPassenger() != null && c.getPassenger().size() > 0) {
			for (OCPassenger pass : c.getPassenger()) {
				if (pass != null && pass.getLastName() != null && lName != null) {
					boolean fNameCheck = true;
					if (fName != null && pass.getFirstName() != null) {
						fName = fName.toUpperCase();
						String fNamePass = pass.getFirstName().toUpperCase();
						fNameCheck = fNamePass.contains(fName);
					}
					if (pass.getLastName().equalsIgnoreCase(lName) && fNameCheck) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param deleteFile
	 */
	public com.bagnet.nettracer.ws.onlineclaims.DeleteFileResponseDocument deleteFile(
			com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument deleteFile) {
		NtAuth auth = deleteFile.getDeleteFile().getAuth();
		authenticate(auth);

		// TODO : fill this with the necessary business logic
		throw new java.lang.UnsupportedOperationException("Please implement "
				+ this.getClass().getName() + "#deleteFile");
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param uploadFile
	 */
	public com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument uploadFile(
			com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument uploadFile) {
		NtAuth auth = uploadFile.getUploadFile().getAuth();
		authenticate(auth);

		UploadFileResponseDocument resDoc = UploadFileResponseDocument.Factory
				.newInstance();
		File resFile = resDoc.addNewUploadFileResponse().addNewReturn();

		Calendar cal = new GregorianCalendar();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);

		UploadFile u = uploadFile.getUploadFile();
		String lead = Long.toString(u.getClaimId());
		String st = Long.toString((new Date()).getTime());

		String fileName = "CLAIM_" + lead + "_" + st + "_" + u.getFilename();
		String filePath = "CLAIMS" + "/" + year + "/" + month + "/" + day + "/";
		;
		String image_store = TracerProperties.get(TracerProperties.get("wt.company.code"),"image_store");
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
		} finally {
			if (sess != null)
				sess.close();
		}
		return resDoc;
	}

}
