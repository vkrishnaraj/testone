package aero.nettracer.fs.utilities.tracing;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;
import aero.nettracer.fs.model.Reservation;
import aero.nettracer.fs.model.detection.MatchDetail;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.fs.model.detection.MatchDetail.MatchType;
import aero.nettracer.fs.utilities.GeoCode;
import aero.nettracer.selfservice.fraud.PrivacyPermissionsBean;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions.AccessLevelType;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

import com.bagnet.nettracer.tracing.utils.DateUtils;

public class Producer {

	private static boolean debug = true;
	private static final int MAX_WAIT = 2000;
	//TODO review mile radius
	public static final double MILE_SEARCH_RADIUS = 2;
	private static final long WAIT_TIME = 100;
	private static final double MILE_SEARCH_ZIP = 4;
	private static final double MILE_SEARCH_CITY = 10;
	
	public static Set<MatchHistory> matchFile(long fileId){
		File file = TraceWrapper.loadFileFromCache(fileId);
		if(file != null){
			return matchFile(file);
		} else {
			return null;
		}
	}

	
	
	private static void queueFile(Long id, HashSet<Long> queue, File file, Vector v){
		if(queue.add(new Long(id))){
			MatchHistory match = new MatchHistory();
			match.setDetails(new LinkedHashSet<MatchDetail>());
			match.setFile1(file);
			match.setFile2(new File(id));
			match.setTraceCount(v);
			match.setCreatedate(DateUtils.convertToGMTDate(new Date()));
			try{
				TraceWrapper.getMatchQueue().put(match);
//				if(debug)System.out.println("Producer add file: " + id);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static Set<MatchHistory> matchFile(File file) {
		Date starttime = new Date();
		
		HashSet<Long> fileQueue = new HashSet<Long>();
		fileQueue.add(file.getId());//adding search claim to queue so we don't trace against itself

		Vector v = new Vector();
		
		// so we are to keep old match histories

		if (file.getId() != 0) {
			String ms = "select file2_id, overallScore from matchhistory where file1_id = " + file.getId();
			SQLQuery pq = null;
			Session sess = HibernateWrapper.getSession().openSession();
			pq = sess.createSQLQuery(ms.toString());
			pq.addScalar("file2_id", Hibernate.LONG);
			pq.addScalar("overallScore", Hibernate.DOUBLE);
			List<Object[]> listMatchingFiles = pq.list();
			
			HashMap<Long, Double> matchingMap = new HashMap<Long, Double>();
			for (Object[] strs : listMatchingFiles) {
				Long f1 = (Long) strs[0];
				Double f2 = (Double) strs[1];
				matchingMap.put(f1, f2);
			}
			file.setMatchingFiles(matchingMap);
		}

		
		String sql = "select f1.id as f1_id, " // IS there a claim associated ot this person 
			+ "f2.id as f2_id, " // # Does the icident have claim
			+ "f3.id as f3_id, " // Is there a incident associated to this person
			+ "f4.id as f4_id, "
			+ "f5.id as f5_id, "
			+ "null as f6_id, "
			+ "null as f7_id, "
			+ "\'person\' as type "
			+ "from person p "
			+ "left outer join fsclaim c1 on p.claim_id = c1.id "
			+ "left outer join file f1 on f1.id = c1.file_id "
			+ "left outer join fsincident i2 on p.incident_id = i2.id "
			+ "left outer join fsclaim c2 on i2.claim_id = c2.id "
			+ "left outer join file f2 on f2.id = c2.file_id "
			+ "left outer join file f3 on f3.id = i2.file_id "
			+ "left outer join reservation r3 on p.reservation_id = r3.id "
			+ "left outer join fsincident i3 on r3.incident_id = i3.id "
			+ "left outer join fsclaim c3 on i3.claim_id = c3.id "
			+ "left outer join file f4 on f4.id = c3.file_id "
			+ "left outer join file f5 on f5.id = i3.file_id "
            + "where 1=0 ";

		Set<Person> persons = Consumer.getPersons(file);
		file.setPersonCache(persons);
		for (Person person : persons) {
			
			if(person.getFirstName() != null && person.getFirstName().trim().length() > 0 && person.getLastName() != null && person.getLastName().trim().length()>0){
			sql += " or (lastNameSoundex = \'"+ person.getLastNameSoundex() + "\'"
				+ " and firstNameSoundex = \'" + person.getFirstNameSoundex() + "\')";
			
			sql += " or (lastNameDmp = \'"+ person.getLastNameDmp() + "\'"
			+ " and firstNameDmp = \'" + person.getFirstNameDmp() + "\')";
			}
			
			if(person.getPassportNumber() != null && person.getPassportNumber().trim().length() > 0){
				sql += " or passportNumber = \'" + person.getPassportNumber() + "\' ";
			}
			
			if(person.getSocialSecurity() != null && person.getSocialSecurity().trim().length() > 0){
				sql += " or socialSecurity = \'" + person.getSocialSecurity() + "\' ";
			}
			
			if(person.getEmailAddress() != null && person.getEmailAddress().trim().length() > 0){
				sql += " or emailAddress = \'" + person.getEmailAddress() + "\' ";
			}
			
			if(person.getFfNumber() != null && person.getFfNumber().trim().length() > 0){
				sql += " or ffNumber = \'" + person.getFfNumber() + "\' ";
			}
			
			if(person.getDriversLicenseNumber() != null && person.getDriversLicenseNumber().trim().length() > 0){
				sql += " or driversLicenseNumber = \'" + person.getDriversLicenseNumber() + "\' ";
			}
		}
		
		Set<Phone> phones = Consumer.getPhones(file);
		file.setPhoneCache(phones);
		Set<String> phoneNumbers = new HashSet<String>();
		//we might have duplicate Phone objects with the same phone number, we want unique phone numbers
		for(Phone phone:phones){
			if(phone.getPhoneNumber() != null && phone.getPhoneNumber().trim().length()>0){
				phoneNumbers.add(phone.getPhoneNumber());
			}
		}
		if(phoneNumbers.size() > 0){

			sql += " union ";
			sql += "select f1.id as f1_id, " +
					"f2.id as f2_id, " +
					"f3.id as f3_id, " +
					"f4.id as f4_id, " +
					"f5.id as f5_id, " +
					"f6.id as f6_id, " +
					"f7.id as f7_id, " +
					"'phone' as type ";
			sql += "from phone ph "
				+  " left outer join person p on ph.person_id = p.id "
				+ " left outer join fsclaim c1 on p.claim_id = c1.id "
				+ " left outer join file f1 on f1.id = c1.file_id "
				+ "  left outer join fsincident i2 on p.incident_id = i2.id "
				+ " left outer join fsclaim c2 on i2.claim_id = c2.id "
				+ " left outer join file f2 on f2.id = c2.file_id "
				+ " left outer join file f3 on f3.id = i2.file_id "
				+ "   left outer join reservation r3 on p.reservation_id = r3.id "
				+ "     left outer join fsincident i3 on r3.incident_id = i3.id "
				+ "     left outer join fsclaim c3 on i3.claim_id = c3.id "
				+ " left outer join file f4 on f4.id = c3.file_id "
				+ " left outer join file f5 on f5.id = i3.file_id "
				+ "  left outer join reservation r4 on ph.reservation_id =	r4.id "
				+ "     left outer join fsincident i4 on r4.incident_id = i4.id "
				+ "    left outer join fsclaim c4 on i4.claim_id = c4.id "
				+ " left outer join file f6 on f6.id = c4.file_id "
				+ " left outer join file f7 on f7.id = i4.file_id "
				+ " where 1=0 ";

//			for(Phone phone:phones){
//				if(phone.getPhoneNumber() != null && phone.getPhoneNumber().trim().length() > 0){
//					sql += " or ph.phoneNumber = \'" + phone.getPhoneNumber() + "\' ";
//				}
//			}
			for(String phoneNumber:phoneNumbers){
				sql += " or ph.phoneNumber = \'" + phoneNumber + "\' ";
			}
		}
		
		if(file.getIncident() != null && file.getIncident().getReservation() != null
				&& file.getIncident().getReservation().getCcNumLastFour() != null
				&& file.getIncident().getReservation().getCcNumLastFour().trim().length() > 0){
			sql += " union select null as f1_id, " +
					"f2.id as f2_id, " +
					"f3.id as f3_id, " +
					"null as f4_id, " +
					"null as f5_id, " +
					"null as f6_id, " +
					"null as f7_id, " +
					"'cc' as type " +
					"from reservation res " +
					"left outer join fsincident i2 on res.incident_id = i2.id " +
					"left outer join fsclaim c2 on i2.claim_id = c2.id " +
					"left outer join file f2 on f2.id = c2.file_id " +
					"left outer join file f3 on f3.id = i2.file_id " +
					"where ccNumLastFour = \'" + file.getIncident().getReservation().getCcNumLastFour() +"\' ";
		}

		Set<FsAddress> addresses = Consumer.getAddresses(file);
		file.setAddressCache(addresses);
		if (addresses != null && addresses.size() > 0) {
			sql += " union select f1.id as f1_id, " +
			"f2.id as f2_id, " +
			"f3.id as f3_id, " +
			"f4.id as f4_id, " +
			"f5.id as f5_id, " +
			"null as f6_id, " +
			"null as f7_id, " +
			"'geo' as type " +
			"from fsaddress ad " + 
      "left outer join person p on ad.person_id = p.id " +
            "left outer join fsclaim c1 on p.claim_id = c1.id " +
            "left outer join file f1 on f1.id = c1.file_id " +
            
            "left outer join fsincident i2 on p.incident_id = i2.id " +
                  "left outer join fsclaim c2 on i2.claim_id = c2.id " +
                  "left outer join file f2 on f2.id = c2.file_id " +
                  "left outer join file f3 on f3.id = i2.file_id " +
                  
            "left outer join reservation r3 on p.reservation_id = r3.id " +
                  "left outer join fsincident i3 on r3.incident_id = i3.id " +
                  "left outer join fsclaim c3 on i3.claim_id = c3.id " +
                  "left outer join file f4 on f4.id = c3.file_id " +
                  "left outer join file f5 on f5.id = i3.file_id " +
                  
                  "where 1=0 ";

			for (FsAddress a: addresses) {
				
				// If it was geocoded, compare against other geocoded items.
				if (a.getLattitude() != 0) {
					// If the address has been geocoded we will calculate
					// the area (min/max latitude and longitude) in which to
					// search.
					double mileSearch = MILE_SEARCH_RADIUS;
					if (a.getGeocodeType() == GeoCode.ACCURACY_STREET) {
						a.setGeocodeType(GeoCode.ACCURACY_STREET);
					} else if (a.getGeocodeType() == GeoCode.ACCURACY_CITY) {
						a.setGeocodeType(GeoCode.ACCURACY_CITY);
						mileSearch = MILE_SEARCH_CITY;
					} else if (a.getGeocodeType() == GeoCode.ACCURACY_ZIP) {
						a.setGeocodeType(GeoCode.ACCURACY_ZIP);
						mileSearch = MILE_SEARCH_ZIP;
					}
					double latRadius = GeoCode.getLatRadius(mileSearch);
					double longRadius = GeoCode.getLongRadius(a.getLongitude(), mileSearch);
					double y1 = a.getLattitude() - latRadius;
					double y2 = a.getLattitude() + latRadius;
					double x1 = a.getLongitude() - longRadius;
					double x2 = a.getLongitude() + longRadius;

					// If there is a latitude or longitude.
					sql += "or (lattitude >= " + y1 + " and lattitude <= " + y2 + " and longitude >= " + x2+ " and longitude <= " + x1 + ") ";

					// Compare against non-geocoded items.
					// Country / City
					if (a.getCity() != null && a.getCity().trim().length() > 0 && a.getCountry() != null && a.getCountry().trim().length() > 0) {
						sql += "or (lattitude = 0 and longitude = 0 and ad.country = \'" + a.getCountry() + "\' and ad.city = \'" + a.getCity() + "\' ) ";
					}
				} else {
					// This else is if the address wasn't geocoded.
					if (a.getCity() != null && a.getCity().trim().length() > 0 && a.getCountry() != null && a.getCountry().trim().length() > 0) {
					sql += "or (ad.country = \'" + a.getCountry() + "\' and ad.city = \'" + a.getCity() + "\' ) ";
					}
				}
			}		
		}
		
		
		
		
		if(debug)System.out.println(sql);
		
		SQLQuery pq = null;
		Session sess = HibernateWrapper.getSession().openSession();
		pq = sess.createSQLQuery(sql.toString());
		pq.addScalar("f1_id", Hibernate.LONG);
		pq.addScalar("f2_id", Hibernate.LONG);
		pq.addScalar("f3_id", Hibernate.LONG);
		pq.addScalar("f4_id", Hibernate.LONG);
		pq.addScalar("f5_id", Hibernate.LONG);
		pq.addScalar("f6_id", Hibernate.LONG);
		pq.addScalar("f7_id", Hibernate.LONG);
		pq.addScalar("type", Hibernate.STRING);
		List<Object[]> result = pq.list();

		sess.close();
		

		for (Object[] strs : result) {
			Long f1 = (Long) strs[0];
			Long f2 = (Long) strs[1];
			Long f3 = (Long) strs[2];
			Long f4 = (Long) strs[3];
			Long f5 = (Long) strs[4];
			Long f6 = (Long) strs[5];
			Long f7 = (Long) strs[6];
			String type = (String) strs[7];
			
			if(f1 != null){
				queueFile(f1, fileQueue, file, v);
			} else if (f2 != null){
				queueFile(f2, fileQueue, file, v);
			} else if (f3 != null){
				queueFile(f3, fileQueue, file, v);
			} else if (f4 != null){
				queueFile(f4, fileQueue, file, v);
			} else if (f5 != null){
				queueFile(f5, fileQueue, file, v);
			} else if (f6 != null){
				queueFile(f6, fileQueue, file, v);
			} else if (f7 != null){
				queueFile(f7, fileQueue, file, v);
			}
		}		
		
		Date endtime = new Date();
		fileQueue.remove(new Long(file.getId()));//removing dup from queue to get accurate count
		if(debug)System.out.println("Producer completed: " + (endtime.getTime() - starttime.getTime()));
		
		
		System.out.println(fileQueue.size());
		try {
			int i = 0;
			for(i= 0; v.size() < fileQueue.size() && i < MAX_WAIT; i++){
//				System.out.println("waiting: " + i + ":" + v.size() + "/" + (fileQueue.size()) );
				Thread.sleep(WAIT_TIME);
			}
			if (i >= MAX_WAIT) {
				System.out.println("***WARNING: Maximum Search Time Exceeded: " + (WAIT_TIME * MAX_WAIT) + "ms");
			} else {
//				file.setPersonCache(null);
//				file.setAddressCache(null);
//				file.setPhoneCache(null);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
//		return getMatchHistoryResult(file.getId());
		return getCensoredFileMatches(file.getId());
	}
	

	public static Set<MatchHistory> getMatchHistoryResult(long fileId){
		String personSql = "from aero.nettracer.fs.model.detection.MatchHistory m where 1=1 " +
				"and (m.file1.id = :id or m.file2.id = :id) order by m.overallScore desc";

		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(personSql.toString());
		q.setParameter("id", fileId);
		List <MatchHistory>result = q.list();
		sess.close();
		for(MatchHistory match:result){
			if(debug)System.out.println("Match: " + match.getId() + "  " + match.getOverallScore());
		}
		return new LinkedHashSet<MatchHistory>(result);
	}
	
	public static Set<MatchHistory> getCensoredFileMatches(long fileId) {
		File f = TraceWrapper.loadFileFromCache(fileId);
		String company = null;
		if(f.getClaim() != null && f.getClaim().getAirline() != null){
			company = f.getClaim().getAirline();
		} else if (f.getIncident() != null && f.getIncident().getAirline() != null){
			company = f.getIncident().getAirline();
		}
		Set<MatchHistory> histories = Producer.getMatchHistoryResult(fileId);
		
		for(MatchHistory history:histories){
			censor(history, AccessLevelType.def, company);
		}
		
		return histories;
	}
	
	public static void censor(MatchHistory match, AccessLevelType level, String userCompany){
		String s = "Not for your eyes";
		PrivacyPermissions p1 = new PrivacyPermissions();
		PrivacyPermissions p2 = new PrivacyPermissions();
		String company1 = null;
		String company2 = null;
		
		if(match.getFile1() != null){
			if(match.getFile1().getClaim() != null && match.getFile1().getClaim().getAirline() != null){
				company1 = match.getFile1().getClaim().getAirline();
			} else if (match.getFile1().getIncident() != null && match.getFile1().getIncident().getAirline() != null){
				company1 = match.getFile1().getIncident().getAirline();
			}
		}
		if(match.getFile2() != null){
			if(match.getFile2().getClaim() != null && match.getFile2().getClaim().getAirline() != null){
				company2 = match.getFile2().getClaim().getAirline();
			} else if (match.getFile2().getIncident() != null && match.getFile2().getIncident().getAirline() != null){
				company2 = match.getFile2().getIncident().getAirline();
			}
		}
		
		for(PrivacyPermissions p: PrivacyPermissionsBean.getPrivacyPermissions()){
			if(p.getKey().getCompanycode().equals(company1) && p.getKey().getLevel().equals(level)){
				p1 = p;
			}
			if(p.getKey().getCompanycode().equals(company2) && p.getKey().getLevel().equals(level)){
				p2 = p;
			}
		}
		
		if(company1 == null || company1.trim().length() == 0 || !company1.equals(userCompany)){
			censorFile(match.getFile1(), p1);
		} else {
			p1.setAllEnabled(true);
		}
		if(company2 == null || company2.trim().length() == 0 || !company2.equals(userCompany)){
			censorFile(match.getFile2(), p2);
		} else {
			p2.setAllEnabled(true);
		}
		
		for(MatchDetail d:match.getDetails()){
			if(d.getMatchtype() == null){
				System.out.println("ERROR:" + d.getDescription() + ":" + d.getContent1() + ":" + d.getContent2());
			}
			if(d.getMatchtype().equals(MatchType.name)){
				if(!p1.isAllEnabled() && !p1.isName())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isName())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.address)){
				if(!p1.isAllEnabled() && !p1.isAddress())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isAddress())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.phone)){
				if(!p1.isAllEnabled() && !p1.isPhonenumber())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isPhonenumber())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.email)){
				if(!p1.isAllEnabled() && !p1.isEmail())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isEmail())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.cc)){
				if(!p1.isAllEnabled() && !p1.isCc())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isCc())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.drivers)){
				if(!p1.isAllEnabled() && !p1.isDrivers())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isDrivers())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.ffn)){
				if(!p1.isAllEnabled() && !p1.isFfn())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isFfn())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.ssn)){
				if(!p1.isAllEnabled() && !p1.isSsn())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isSsn())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.passport)){
				if(!p1.isAllEnabled() && !p1.isPassport())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isPassport())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.pnrloc)){
				if(!p1.isAllEnabled() && !p1.isPnrloc())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isPnrloc())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.pnrdata)){
				if(!p1.isAllEnabled() && !p1.isPnrdata())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isPnrdata())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.traveldate)){
				if(!p1.isAllEnabled() && !p1.isTraveldate())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isTraveldate())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.ticketamount)){
				if(!p1.isAllEnabled() && !p1.isTicketamount())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isTicketamount())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.dob)){
				if(!p1.isAllEnabled() && !p1.isDob())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isDob())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.itin)){
				if(!p1.isAllEnabled() && !p1.isItin())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isItin())d.setContent2(s);
			}
			
			
		}
	}
	
	public void censorFile(File f, AccessLevelType level){
		
	}
	
	public static void censorFile(File f, PrivacyPermissions p){
		String s = "!!!CENSOR!!!";
		Set<Person> persons = Consumer.getPersons(f);
		for(Person person:persons){
			if(!p.isName()){
				person.setFirstName(s);
				person.setMiddleName(s);
				person.setLastName(s);
			}
			if(!p.isEmail()){
				person.setEmailAddress(s);
			}
			if(!p.isDrivers()){
				person.setDriversLicenseIssuer(s);
				person.setDriversLicenseNumber(s);
			}
			if(!p.isFfn()){
				person.setFfAirline(s);
				person.setFfNumber(s);
			}
			if(!p.isPassport()){
				person.setPassportIssuer(s);
				person.setPassportNumber(s);
			}
			if(!p.isSsn()){
				person.setSocialSecurity(s);
			}
			if(!p.isDob()){
				person.setDateOfBirth(null);
			}
		}
		
		Set<FsAddress> addresses = Consumer.getAddresses(f);
		for(FsAddress address:addresses){
			if(!p.isAddress()){
				address.setAddress1(s);
				address.setAddress2(s);
				address.setCity(s);
				address.setCountry(s);
				address.setLattitude(0);
				address.setLongitude(0);
				address.setState(s);
				address.setZip(s);
				address.setProvince(s);
			}
		}
		
		Set<Phone> phones = Consumer.getPhones(f);
		for(Phone phone:phones){
			if(!p.isPhonenumber()){
				phone.setPhoneNumber(s);
			}
		}
		
		if(f.getClaim() != null){
			if(!p.isAmountclaimed()){
				f.getClaim().setAmountClaimed(0);
			}
			if(!p.isAmountpaid()){
				f.getClaim().setAmountPaid(0);
			}
			if(!p.isDenied()){
//				f.getClaim().setDenied(null);
			}
		}
		
		
		//incident
		
		//pnrdata
		
		//itin
		
		//bag
		
		Reservation reservation = f.getIncident().getReservation();
		
		
	}
	
}
