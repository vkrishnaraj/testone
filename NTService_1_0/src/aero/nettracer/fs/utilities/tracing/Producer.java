package aero.nettracer.fs.utilities.tracing;

import java.util.Date;
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
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;
import aero.nettracer.fs.model.detection.MatchDetail;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.fs.utilities.GeoCode;
import aero.nettracer.fs.utilities.GeoLocation;
import aero.nettracer.fs.utilities.InternationalException;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

import com.bagnet.nettracer.tracing.utils.DateUtils;

public class Producer {

	private static boolean debug = false;
	private static final int MAX_WAIT = 200;
	public static final double MILE_SEARCH_RADIUS = 5;
	private static final long WAIT_TIME = 50;
	
	public static Set<MatchHistory> matchFile(long fileId){
		File file = TraceWrapper.loadFileFromCache(fileId);
		if(file != null){
			return matchFile(file);
		} else {
			return null;
		}
	}

	//for now we are not to remove any match histories
	@Deprecated
	private static void removeMatchHistory(long fileId){
		String personSql = "delete from aero.nettracer.fs.model.detection.MatchHistory m where 1=1 " +
		"and (m.file1.id = :id or m.file2.id = :id)";

		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(personSql.toString());
		q.setParameter("id", fileId);
		q.executeUpdate();
		sess.close();
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
				if(debug)System.out.println("Producer add file: " + id);
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
		
//		so we are to keep old match histories
//		removeMatchHistory(claim.getId());
		
		String sql = "select f1.id as f1_id, " // IS there a claim associated ot this person 
			+ "f2.id as f2_id, " // # Does the icident have claim
			+ "f3.id as f3_id, " // Is there a incident associated to this person
			+ "f4.id as f4_id, "
			+ "f5.id as f5_id, "
			+ "null as f6_id, "
			+ "null as f7_id, "
			+ "\'person\' as type "
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
			sql += "select c1.id as c1_id, " +
					"c2.id as c2_id, " +
					"i2.id as i2_id, " +
					"c3.id as c3_id, " +
					"i3.id as i3_id, " +
					"c4.id as c4_id, " +
					"i4.id as i4_id, " +
					"'phone' as type ";
			sql += "from phone ph "
				+  " left outer join person p on ph.person_id = p.id "
				+ " left outer join fsclaim c1 on p.claim_id = c1.id "
				+ "  left outer join fsincident i2 on p.incident_id = i2.id "
				+ " left outer join fsclaim c2 on i2.claim_id = c2.id "
				+ "   left outer join reservation r3 on p.reservation_id = r3.id "
				+ "     left outer join fsincident i3 on r3.incident_id = i3.id "
				+ "     left outer join fsclaim c3 on i3.claim_id = c3.id "
				+ "  left outer join reservation r4 on ph.reservation_id =	r4.id "
				+ "     left outer join fsincident i4 on r4.incident_id = i4.id "
				+ "    left outer join fsclaim c4 on i4.claim_id = c4.id "                   
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
					"where ccNumber = \'" + file.getIncident().getReservation().getCcNumLastFour() +"\' ";
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
			"from address ad " + 
      "left outer join person p on ad.person_id = p.id " +
            "left outer join claim c1 on p.claim_id = c1.id " +
            "left outer join file f1 on f1.id = c1.file_id " +
            
            "left outer join incident i2 on p.incident_id = i2.id " +
                  "left outer join claim c2 on i2.claim_id = c2.id " +
                  "left outer join file f2 on f2.id = c2.file_id " +
                  "left outer join file f3 on f3.id = i2.file_id " +
                  
            "left outer join reservation r3 on p.reservation_id = r3.id " +
                  "left outer join incident i3 on r3.incident_id = i3.id " +
                  "left outer join claim c3 on i3.claim_id = c3.id " +
                  "left outer join file f4 on f4.id = c3.file_id " +
                  "left outer join file f5 on f5.id = i3.file_id " +
                  
                  "where 1=0 ";

			for (FsAddress a: addresses) {
				
				// If it was geocoded, compare against other geocoded items.
				if (a.getLattitude() != 0) {
					// If the address has been geocoded we will calculate
					// the area (min/max latitude and longitude) in which to
					// search.
					double latRadius = GeoCode.getLatRadius(MILE_SEARCH_RADIUS);
					double longRadius = GeoCode.getLongRadius(a.getLongitude(), MILE_SEARCH_RADIUS);
					double y1 = a.getLattitude() - latRadius;
					double y2 = a.getLattitude() + latRadius;
					double x1 = a.getLongitude() - longRadius;
					double x2 = a.getLongitude() + longRadius;

					// If there is a latitude or longitude.
					sql += "or (lattitude >= " + y1 + " and lattitude <= " + y2 + " and longitude <= " + x2+ " and longitude >= " + x1 + ") ";

					// Compare against non-geocoded items.
					// Country / City
					if (a.getCity() != null && a.getCountry() != null) {
						sql += "or (lattitude = 0 and longitude = 0 and ad.country = \'" + a.getCountry() + "\' and ad.city = \'" + a.getCity() + "\' ) ";
					}
				} else {
					// This else is if the address wasn't geocoded.
					sql += "or (ad.country = \'" + a.getCountry() + "\' and ad.city = \'" + a.getCity() + "\' ) ";
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
		
		
//		System.out.println((claimQueue.size() + incidentQueue.size()));
		try {
			int i = 0;
			for(i= 0; v.size() < fileQueue.size() && i < MAX_WAIT; i++){
//				System.out.println("waiting: " + i + ":" + v.size() + "/" + (claimQueue.size() + incidentQueue.size()) );
				Thread.sleep(WAIT_TIME);
			}
			if (i >= MAX_WAIT) {
				System.out.println("***WARNING: Maximum Search Time Exceeded: " + (WAIT_TIME * MAX_WAIT) + "ms");
			} else {
				file.setPersonCache(null);
				file.setAddressCache(null);
				file.setPhoneCache(null);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		return getMatchHistoryResult(file.getId());
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
}
