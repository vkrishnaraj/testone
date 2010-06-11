package com.bagnet.clients.us;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.axis2.client.Options;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.CrmFile;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.CrmFile.CRMStatus;
import com.usairways.crm.webservices.ArrayOfInt;
import com.usairways.crm.webservices.CreateCRMFileDocument;
import com.usairways.crm.webservices.CreateCRMFileResponseDocument;
import com.usairways.crm.webservices.NetTracerStub;
import com.usairways.crm.webservices.CreateCRMFileDocument.CreateCRMFile;

public class CrmIntegration {
	private static final boolean ABORT = false;
	private static Logger logger = Logger.getLogger(CrmIntegration.class);

	public Incident sendIncident(String incidentId, Session sess) {
		Incident i = null;
		boolean sessionNull = (sess == null);
		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}

			i = (Incident) sess.load(Incident.class, incidentId);
			if (i != null && (i.getCrmFile() == null || i.getCrmFile().getStatus() != CRMStatus.SUCCESS)) {
				CrmFile f = i.getCrmFile();
				if (i.getCrmFile() == null) {
					f = new CrmFile();
				}
				CRMStatus status = CRMStatus.FAILED;
				String key = null;
				try {
					key = transmitToCrm(i, sess);
					if (key != null) {
						status = CRMStatus.SUCCESS;
					}
				} catch (RemoteException e) {
					logger.info("Web Service Failure: ", e);
				} finally {
					f.setIncident(i);
					f.setStatus(status);
					f.setCrm_key(key);
					Transaction t = null;
					t = sess.beginTransaction();
					sess.save(f);
					t.commit();
					logger.info("Updated incident: " + i.getIncident_ID() + "  Status: " + status.toString());

					// Evict Incident once finished to keep it from consuming
					// session
					// cache
					sess.evict(f);
				}

			} else {
				// Ignore because file has already been updated since query
			}
			sess.evict(i);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sess != null && sessionNull) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return i;
	}

	public String transmitToCrm(Incident i, Session sess) throws RemoteException {
		NetTracerStub stub = new NetTracerStub(PropertyBMO.getValue(PropertyBMO.CRM_INTEGRATION_ENDPOINT));

		Options x = stub._getServiceClient().getOptions();

		CreateCRMFileDocument d = CreateCRMFileDocument.Factory.newInstance();
		CreateCRMFile b = d.addNewCreateCRMFile();

		b.setSFileNumber(i.getIncident_ID());

		Calendar fileDateTime = new GregorianCalendar();
		logger.info("Incident create date: " + i.getFullCreateDate());

		int modifyTime = (fileDateTime.get(Calendar.ZONE_OFFSET) + fileDateTime.get(Calendar.DST_OFFSET)) / (1000 * 60 * 60);
		fileDateTime.setTimeZone(TimeZone.getTimeZone("GMT"));

		// PRODUCTION VALUE
		fileDateTime.setTime(i.getFullCreateDate());
		
		// TEST VALUE
		// fileDateTime.setTime(i.getCreatedate());
		fileDateTime.add(Calendar.HOUR, modifyTime);

		b.setFileDateTime(fileDateTime);

		Passenger p = i.getPassenger_list().get(0);
		Address a = p.getAddress(0);

		b.setSLastName(p.getLastname());
		b.setSFirstName(p.getFirstname());

		b.setSSalutation(p.getSalutationdesc());
		String memNum = p.getAirlinememnumber();
		if (memNum != null && memNum.length() > 0) {
			b.setSMembershipNumber(memNum);
		}

		// TODO: Update when we have dividend tiers.
		b.setIMembershipStatus(0);

		b.setSStreetAddress(a.getAddress1());
		b.setSAptSuiteNumber(a.getAddress2());
		b.setSCity(a.getCity());

		if (a.getCountrycode_ID().equals("US")) {
			b.setSStateProvince(a.getState_ID());
		} else {
			b.setSStateProvince(a.getProvince());
		}

		String country = lookupUsCrmCountryCode(a, sess);
		b.setSCountry(country);

		if (a.getZip() != null && a.getZip().length() > 0) {
			b.setSZip(a.getZip());
		}

		b.setSEmail(a.getEmail());
		b.setSHomePhone(a.getHomephone());
		b.setSBusinessPhone(a.getWorkphone());
		b.setSMobilePhone(a.getMobile());
		b.setSFax(a.getAltphone());
		b.setSPager(a.getPager());

		// <option value="2">Damage</option>
		// <option value="3">Pilferage</option>
		// <option value="4">Delay</option>
		int type = 4;

		if (i.getItemtype().getItemType_ID() == TracingConstants.DAMAGED_BAG) {
			type = 2;
		} else if (i.getItemtype().getItemType_ID() == TracingConstants.MISSING_ARTICLES) {
			type = 3;
		}
		b.setIFileType(type);

		// NETTRACER VALUES
		// <option value="1">Curb-side</option>
		// <option value="2">Ticket Counter</option>
		// <option value="3">Gate</option>
		// <option value="4">Remote</option>
		// <option value="5">Plane-side</option>
		// <option value="6">Unchecked</option>
		// <option value="7">Kiosk</option>

		// CRM VALUES
		// <option value="1" SELECTED>Ticket Counter</option>
		// <option value="2">Kiosk</option>
		// <option value="3">Curbside</option>
		// <option value="4">Gate Checked</option>
		// <option value="5">Force Gate Checked</option>
		// <option value="6">Carry-On</option>
		// <option value="8">Customs Recheck</option>
		// <option value="7">Other</option>

		int loc = 0;
		int setLoc = 0;
		try {
			loc = Integer.parseInt(i.getCheckedlocation());
		} catch (Exception e) {
			// Ignore
		}

		switch (loc) {
		case 0:
			setLoc = 7;
			break;
		case 1:
			setLoc = 3;
			break;
		case 2:
			setLoc = 1;
			break;
		case 3:
			setLoc = 4;
			break;
		case 4:
			setLoc = 7;
			break;
		case 5:
			setLoc = 7;
			break;
		case 6:
			setLoc = 6;
			break;
		case 7:
			setLoc = 7;
			break;
		}
		b.setIBagsCheckedLocation(setLoc);

		// TODO: Bag Types -- Document the addition of 59 to 69 as
		// "Sports Equipment"
		ArrayOfInt c = b.addNewIType();
		for (Item item : i.getItemlist()) {
			if (item != null) {

				int bt = 22;
				try {
					bt = Integer.parseInt(item.getBagtype());
				} catch (Exception e) {

				}

				c.addInt(bt);
			}
		}

		b.setSStationCreated(i.getStationcreated().getStationcode());

		HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();

		auth.setHost("crmtest.lcc.usairways.com");
		auth.setUsername("svc-ntracercrm");
		auth.setPassword("s$qDa)u91u6Y");
		auth.setDomain("lcc");
		auth.setPreemptiveAuthentication(false);

		x.setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE, auth);

		logger.info(d);

		if (!ABORT) {
			CreateCRMFileResponseDocument r = stub.create_CRMFile(d);
			logger.info("CRM Response Document: \n" + r);
			if (r != null && r.getCreateCRMFileResponse() != null && r.getCreateCRMFileResponse().getCreateCRMFileResult() != null) {
				return r.getCreateCRMFileResponse().getCreateCRMFileResult();
			}
			return null;
		} else {
			return "TEST KEY";
		}

	}

	@SuppressWarnings("unchecked")
	private String lookupUsCrmCountryCode(Address a, Session sess) {
		// TODO Auto-generated method stub

		ConcurrentHashMap<String, String> map = (ConcurrentHashMap<String, String>) PropertyBMO.getObject(PropertyBMO.US_CRM_COUNTRY_MAP);
		if (map == null || map.size() == 0) {
			map = new ConcurrentHashMap<String, String>(225, .95F);

			// Populate map

			String getTableSQL = "SELECT CountryCode_ID, country FROM z_crm_map";

			SQLQuery matchExistsQuery = sess.createSQLQuery(getTableSQL);
			List<Object[]> queryResults = matchExistsQuery.list();

			for (Object[] oArray : queryResults) {
				map.put((String) oArray[0], (String) oArray[1]);
			}

			PropertyBMO.setObject(PropertyBMO.US_CRM_COUNTRY_MAP, map);
		}

		if (map != null && map.get(a.getCountrycode_ID()) != null) {
			return map.get(a.getCountrycode_ID());
		} else {
			return "None";
			// return a.getCountry();
		}

	}
}
