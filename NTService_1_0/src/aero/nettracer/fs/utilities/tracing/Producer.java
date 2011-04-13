package aero.nettracer.fs.utilities.tracing;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import org.hibernate.Query;
import org.hibernate.Session;

import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.detection.MatchDetail;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

public class Producer {

	public static Set<MatchHistory> matchClaim(long claimId){
		String sql = "from aero.nettracer.fs.model.FsClaim c where c.id = :id";
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(sql.toString());
		q.setParameter("id", claimId);
		List<FsClaim> result = q.list();
		sess.close();
		if(result != null && result.size() > 0){
			return matchClaim(result.get(0));
		} else {
			return null;
		}
	}

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
	
	public static Set<MatchHistory> matchClaim(FsClaim claim) {
		Date starttime = new Date();
		
		HashSet<FsClaim> claims = new HashSet<FsClaim>();
		
//		ArrayBlockingQueue<FsClaim> claimQueue = TraceWrapper.getClaimQueue();
//		ArrayBlockingQueue<FsIncident> incidentQueue = TraceWrapper.getIncidentQueue();
		ArrayBlockingQueue<MatchHistory> matchQueue = TraceWrapper.getMatchQueue();
		
		removeMatchHistory(claim.getId());
		
		String personSql = "from aero.nettracer.fs.model.Person p where 1=0";

		Set<Person> persons = Consumer.getPersons(claim);
		
		for (Person person : persons) {
//			personSql += " and (lastNameSoundex = soundex(\'"
//					+ person.getLastName() + "\')"
//					+ " and firstNameSoundex = soundex(\'"
//					+ person.getFirstName() + "\'))";
			
			if(person.getFirstName() != null && person.getFirstName().length() > 0 && person.getLastName() != null && person.getLastName().length()>0){
			personSql += " or (lastNameSoundex = \'"+ person.getLastNameSoundex() + "\'"
				+ " and firstNameSoundex = \'" + person.getFirstNameSoundex() + "\')";
			
			personSql += " or (lastNameDmp = \'"+ person.getLastNameDmp() + "\'"
			+ " and firstNameDmp = \'" + person.getFirstNameDmp() + "\')";
			}
			
			if(person.getPassportNumber() != null && person.getPassportNumber().length() > 0){
				personSql += " or passportNumber = \'" + person.getPassportNumber() + "\' ";
			}
			
			if(person.getSocialSecurity() != null && person.getSocialSecurity().length() > 0){
				personSql += " or socialSecurity = \'" + person.getSocialSecurity() + "\' ";
			}
		}

		System.out.println(personSql);
		
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(personSql.toString());
		q.setMaxResults(1000);
		List<Person> result = q.list();
		sess.close();

		for (Person p : result) {
//			if(p.getClaim() != null){
//				System.out.println("produce claim " + p.getLastName() + ":" + p.getFirstName());
//				try{
//					claimQueue.put(p.getClaim());
//				}catch (Exception e){
//					e.printStackTrace();
//				}
//			}
//			if(p.getIncident() != null){
//				System.out.println("produce incident " + p.getLastName() + ":" + p.getFirstName());
//				try{
//					incidentQueue.put(p.getIncident());
//				}catch (Exception e){
//					e.printStackTrace();
//				}
//			}
			
			if(p.getIncident() != null && p.getIncident().getClaim() != null){
				claims.add(p.getIncident().getClaim());
			}	
			
		}//end person
		
		System.out.println("removing claim: " + claim.getId());
		System.out.println(claims.remove(claim));
		for(FsClaim toAdd:claims){
			MatchHistory match = new MatchHistory();
			match.setDetails(new LinkedHashSet<MatchDetail>());
			match.setClaim1(claim);
			match.setClaim2(toAdd);
			try{
				matchQueue.put(match);
				System.out.println("Producer: " + toAdd.getId());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		Date endtime = new Date();
		System.out.println("Producer completed: " + (endtime.getTime() - starttime.getTime()));
		
		try {
			Thread.sleep(2000);
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
			System.out.println(match);
		}
		return new LinkedHashSet<MatchHistory>(result);
	}
}
