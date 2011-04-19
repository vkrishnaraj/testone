package aero.nettracer.fs.utilities.tracing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bagnet.nettracer.tracing.utils.DateUtils;

import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;
import aero.nettracer.fs.model.detection.MatchDetail;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

public class Producer {

	
	private static final int MAX_WAIT = 100;
	
	public static Set<MatchHistory> matchClaim(long claimId){
//		String sql = "from aero.nettracer.fs.model.FsClaim c where c.id = :id";
//		Query q = null;
//		Session sess = HibernateWrapper.getSession().openSession();
//		q = sess.createQuery(sql.toString());
//		q.setParameter("id", claimId);
//		List<FsClaim> result = q.list();
//		sess.close();
//		if(result != null && result.size() > 0){
//			return matchClaim(result.get(0));
//		} else {
//			return null;
//		}
		
		FsClaim claim = TraceWrapper.loadClaimFromCache(claimId);
		if(claim != null){
			return matchClaim(claim);
		} else {
			return null;
		}
	}

	//for now we are not to remove any match histories
	private static void removeMatchHistory(long claimId){
		String personSql = "delete from aero.nettracer.fs.model.detection.MatchHistory m where 1=1 " +
		"and (m.claim1.id = :id or m.claim2.id = :id)";

		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(personSql.toString());
		q.setParameter("id", claimId);
		q.executeUpdate();
		sess.close();
	}
	
	private static void queueClaim(Long id, HashSet<Long> queue, FsClaim claim, Vector v){
		if(queue.add(new Long(id))){
			MatchHistory match = new MatchHistory();
			match.setDetails(new LinkedHashSet<MatchDetail>());
			match.setClaim1(claim);
			match.setClaim2(new FsClaim(id));
			match.setTraceCount(v);
			match.setCreatedate(DateUtils.convertToGMTDate(new Date()));
			try{
				TraceWrapper.getMatchQueue().put(match);
				//System.out.println("Producer add claim: " + id);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private static void queueIncident(Long id, HashSet<Long> queue, FsClaim claim, Vector v){
		if(queue.add(new Long(id))){
			MatchHistory match = new MatchHistory();
			match.setDetails(new LinkedHashSet<MatchDetail>());
			match.setClaim1(claim);
			match.setIncident2(new FsIncident(id));
			match.setTraceCount(v);
			match.setCreatedate(DateUtils.convertToGMTDate(new Date()));
			try{
				TraceWrapper.getMatchQueue().put(match);
				//System.out.println("Producer add incident: " + id);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static Set<MatchHistory> matchClaim(FsClaim claim) {
		Date starttime = new Date();
		
		HashSet<Long> claimQueue = new HashSet<Long>();
		claimQueue.add(claim.getId());//adding search claim to queue so we don't trace against itself
		HashSet<Long> incidentQueue = new HashSet<Long>();
		Vector v = new Vector();
		
//		so we are to keep old match histories
//		removeMatchHistory(claim.getId());
		
		String sql = "select c1.id as c1_id, " // IS there a claim associated ot this person 
			+ "c2.id as c2_id, " // # Does the icident have claim
			+ "i2.id as i2_id, " // Is there a incident associated to this person
			+ "c3.id as c3_id, "
			+ "i3.id as i3_id, "
			+ "null as c4_id, "
			+ "null as i4_id, "
			+ "\'person\' as type "
			+ "from person p "
            + "left outer join fsclaim c1 on p.claim_id = c1.id "
            + "left outer join fsincident i2 on p.incident_id = i2.id "
            + "left outer join fsclaim c2 on i2.claim_id = c2.id "
            + "left outer join reservation r3 on p.reservation_id = r3.id "
            + "left outer join fsincident i3 on r3.incident_id = i3.id "
            + "left outer join fsclaim c3 on i3.claim_id = c3.id "
            
            + "where 1=0 ";

		Set<Person> persons = Consumer.getPersons(claim);
		
		for (Person person : persons) {
			
			if(person.getFirstName() != null && person.getFirstName().length() > 0 && person.getLastName() != null && person.getLastName().length()>0){
			sql += " or (lastNameSoundex = \'"+ person.getLastNameSoundex() + "\'"
				+ " and firstNameSoundex = \'" + person.getFirstNameSoundex() + "\')";
			
			sql += " or (lastNameDmp = \'"+ person.getLastNameDmp() + "\'"
			+ " and firstNameDmp = \'" + person.getFirstNameDmp() + "\')";
			}
			
			if(person.getPassportNumber() != null && person.getPassportNumber().length() > 0){
				sql += " or passportNumber = \'" + person.getPassportNumber() + "\' ";
			}
			
			if(person.getSocialSecurity() != null && person.getSocialSecurity().length() > 0){
				sql += " or socialSecurity = \'" + person.getSocialSecurity() + "\' ";
			}
			
			if(person.getEmailAddress() != null && person.getEmailAddress().length() > 0){
				sql += " or emailAddress = \'" + person.getEmailAddress() + "\' ";
			}
			
			if(person.getFfNumber() != null && person.getFfNumber().length() > 0){
				sql += " or ffNumber = \'" + person.getFfNumber() + "\' ";
			}
			
			if(person.getDriversLicenseNumber() != null && person.getDriversLicenseNumber().length() > 0){
				sql += " or driversLicenseNumber = \'" + person.getDriversLicenseNumber() + "\' ";
			}
		}
		
		Set<Phone> phones = Consumer.getPhones(claim);
		Set<String> phoneNumbers = new HashSet<String>();
		//we might have duplicate Phone objects with the same phone number, we want unique phone numbers
		for(Phone phone:phones){
			if(phone.getPhoneNumber() != null && phone.getPhoneNumber().length()>0){
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
//				if(phone.getPhoneNumber() != null && phone.getPhoneNumber().length() > 0){
//					sql += " or ph.phoneNumber = \'" + phone.getPhoneNumber() + "\' ";
//				}
//			}
			for(String phoneNumber:phoneNumbers){
				sql += " or ph.phoneNumber = \'" + phoneNumber + "\' ";
			}
		}
		
		if(claim.getIncident() != null && claim.getIncident().getReservation() != null
				&& claim.getIncident().getReservation().getCcNumLastFour() != null
				&& claim.getIncident().getReservation().getCcNumLastFour().length() > 0){
			sql += " union select null as c1_id, " +
					"c2.id as c2_id, " +
					"i2.id as i2_id, " +
					"null as c3_id, " +
					"null as i3_id, " +
					"null as c4_id, " +
					"null as i4_id, " +
					"'cc' as type " +
					"from reservation res " +
					"left outer join fsincident i2 on res.incident_id = i2.id " +
					"left outer join fsclaim c2 on i2.claim_id = c2.id " +
					"where ccNumber = \'" + claim.getIncident().getReservation().getCcNumLastFour() +"\' ";
		}

		//System.out.println(sql);
		
		SQLQuery pq = null;
		Session sess = HibernateWrapper.getSession().openSession();
		pq = sess.createSQLQuery(sql.toString());
		pq.addScalar("c1_id", Hibernate.LONG);
		pq.addScalar("c2_id", Hibernate.LONG);
		pq.addScalar("i2_id", Hibernate.LONG);
		pq.addScalar("c3_id", Hibernate.LONG);
		pq.addScalar("i3_id", Hibernate.LONG);
		pq.addScalar("c4_id", Hibernate.LONG);
		pq.addScalar("i4_id", Hibernate.LONG);
		pq.addScalar("type", Hibernate.STRING);
		List<Object[]> result = pq.list();

		sess.close();

		for (Object[] strs : result) {
			Long c1 = (Long) strs[0];
			Long c2 = (Long) strs[1];
			Long i2 = (Long) strs[2];
			Long c3 = (Long) strs[3];
			Long i3 = (Long) strs[4];
			Long c4 = (Long) strs[5];
			Long i4 = (Long) strs[6];
			String type = (String) strs[7];
			
			if(c1 != null){
				queueClaim(c1, claimQueue, claim, v);
			} else if (c2 != null){
				queueClaim(c2, claimQueue, claim, v);
			} else if (i2 != null){
				queueIncident(i2, incidentQueue, claim, v);
			} else if (c3 != null){
				queueClaim(c3, claimQueue, claim, v);
			} else if (i3 != null){
				queueIncident(i3, incidentQueue, claim, v);
			} else if (c4 != null){
				queueClaim(c4, claimQueue, claim, v);
			} else if (i4 != null){
				queueIncident(i4, incidentQueue, claim, v);
			}
		}		
		
		Date endtime = new Date();
		claimQueue.remove(new Long(claim.getId()));//removing dup from queue to get accurate count
		//System.out.println("Producer completed: " + (endtime.getTime() - starttime.getTime()));
		
		
		System.out.println((claimQueue.size() + incidentQueue.size()));
		try {
			for(int i = 0; v.size() < (claimQueue.size() + incidentQueue.size()) && i < MAX_WAIT; i++){
				System.out.println("waiting: " + i + ":" + v.size() + "/" + (claimQueue.size() + incidentQueue.size()) );
				Thread.sleep(40);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return getMatchHistoryResult(claim.getId());
	}
	
	public static Set<MatchHistory> getMatchHistoryResult(long claimId){
		String personSql = "from aero.nettracer.fs.model.detection.MatchHistory m where 1=1 " +
				"and (m.claim1.id = :id or m.claim2.id = :id)";

		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(personSql.toString());
		q.setParameter("id", claimId);
		List <MatchHistory>result = q.list();
		sess.close();
		for(MatchHistory match:result){
			//System.out.println("Match: " + match.getId() + "  " + match.getMatchPercentage());
		}
		return new LinkedHashSet<MatchHistory>(result);
	}
}
