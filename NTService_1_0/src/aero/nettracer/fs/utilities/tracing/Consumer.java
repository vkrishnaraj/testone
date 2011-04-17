package aero.nettracer.fs.utilities.tracing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.detection.MatchDetail;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

public class Consumer implements Runnable{

	public static final int INCIDENT = 1;
	public static final int CLAIM = 2;
	public static final int MATCH = 3;
	
	private int threadnumber;
	private int threadtype;
	private ArrayBlockingQueue<FsClaim> claimQueue;
	private ArrayBlockingQueue<FsIncident> incidentQueue;
	private ArrayBlockingQueue<MatchHistory> matchQueue;
	
	
	public static FsClaim loadClaim(long claimId){
		String sql = "from aero.nettracer.fs.model.FsClaim c where c.id = :id";
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(sql.toString());
		q.setParameter("id", claimId);
		List<FsClaim> result = q.list();
		sess.close();
		if(result != null && result.size() > 0){
			return result.get(0);
		} else {
			return null;
		}
	}
	
	public static FsIncident loadIncident(long incidentId){
		String sql = "from aero.nettracer.fs.model.FsIncident i where i.id = :id";
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(sql.toString());
		q.setParameter("id", incidentId);
		List<FsIncident> result = q.list();
		sess.close();
		if(result != null && result.size() > 0){
			return result.get(0);
		} else {
			return null;
		}
	}
	
	public Consumer(ArrayBlockingQueue queue, int type, int threadnumber) throws Exception{
		if(type == CLAIM){
			claimQueue = queue;
		} else if (type == INCIDENT){
			incidentQueue = queue;
		} else if (type == MATCH){
			matchQueue = queue;
		} else {
			throw new Exception("unable to create consumer");
		}
		this.threadnumber = threadnumber;
		this.threadtype = type;
	}
	
	
	@Override
	public void run() {
		while(true){
			try{
				if(this.threadtype == CLAIM){
					FsClaim claim = claimQueue.take();
					System.out.println("CLAIM " + threadnumber + ":" + claim.getId());
				} else if (this.threadtype == INCIDENT){
					FsIncident incident = incidentQueue.take();
					System.out.println("INCIDENT " + threadnumber + ":" + incident.getId());
				} else if (this.threadtype == MATCH){
					MatchHistory match = matchQueue.take();
					System.out.println("consumer " + threadnumber);
					processMatch(match);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	private static void processMatch(MatchHistory match){
		try{
			//process match
			if(match.getClaim2() != null){
				System.out.println("consumer claim: " + match.getClaim2().getId());
				match.setClaim2(loadClaim(match.getClaim2().getId()));
				processName(match);
			}
			if(match.getIncident2() != null){
				System.out.println("consume incident: " + match.getIncident2().getId());
				match.setIncident2(loadIncident(match.getIncident2().getId()));
				processName(match);
			}


			if(match.getDetails() == null || match.getDetails().size() == 0){
				//no match details so don't save
				//can expand to include not saving match if min match percent below certain threshold
				return;
			}
			//save match
			Transaction t = null;
			Session sess = null;
			try{
				sess = HibernateWrapper.getSession().openSession();
				t = sess.beginTransaction();
				sess.saveOrUpdate(match);
				t.commit();
			} catch (Exception e) {
				e.printStackTrace();
				if (t != null) {
					try {
						t.rollback();
					} catch (Exception ex) {
					}
				}
			} finally {

				if (sess != null) {
					try {
						sess.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			match.getTraceCount().add(null);
			System.out.println("consumer consumed count: " + match.getTraceCount().size());
		}
	}
	
	public static Set<Person> getPersons(FsIncident incident){
		HashSet<Person> ret = new HashSet<Person>();
			if(incident != null){
				if(incident.getPassengers() != null){
					for(Person p:incident.getPassengers()){
						ret.add(p);
					}
				}
				if(incident.getReservation() != null
						&& incident.getReservation().getPassengers() != null){
					for(Person p:incident.getReservation().getPassengers()){
						ret.add(p);
					}
				}
			}
		return ret;
	}
	
	public static Set<Person> getPersons(FsClaim claim){
		HashSet<Person> ret = new HashSet<Person>();
		
		if(claim != null){
			if(claim.getClaimants() != null){
				for(Person p:claim.getClaimants()){
					ret.add(p);
				}
			}
			if(claim.getIncident() != null){
				if(claim.getIncident().getPassengers() != null){
					for(Person p:claim.getIncident().getPassengers()){
						ret.add(p);
					}
				}
				if(claim.getIncident().getReservation() != null
						&& claim.getIncident().getReservation().getPassengers() != null){
					for(Person p:claim.getIncident().getReservation().getPassengers()){
						ret.add(p);
					}
				}
			}
			
		}
		
		return ret;
	}
	
	private static void processName(MatchHistory match){
		Set<Person> plist1 = getPersons(match.getClaim1());
		Set<Person> plist2 = null;
		if(match.getClaim2() != null){
		plist2 = getPersons(match.getClaim2());
		} else if (match.getIncident2() !=null){
			plist2 = getPersons(match.getIncident2());
		}
		
		Set <MatchDetail> details = match.getDetails();

		for(Person p1:plist1){
			for(Person p2:plist2){
				//for now just match on soundex
				//				if((p1.getFirstNameSoundex().equals(p2.getFirstNameSoundex()) && p1.getLastNameSoundex().equals(p2.getLastNameSoundex())
				//						|| (p1.getFirstNameDmp().equals(p2.getFirstNameDmp()) && p1.getLastNameDmp().equals(p2.getLastNameDmp())))){

				if(p1.getFirstNameSoundex().equals(p2.getFirstNameSoundex()) && p1.getLastNameSoundex().equals(p2.getLastNameSoundex())){
					MatchDetail detail = new MatchDetail();
					detail.setContent1(p1.getFirstName() + " " + p1.getLastName());
					detail.setContent2(p2.getFirstName() + " " + p2.getLastName());
					detail.setDescription("Name Match");
					detail.setMatch(match);
					details.add(detail);
				}
			}
		}
	}

}
