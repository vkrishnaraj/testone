package aero.nettracer.fs.utilities.tracing;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

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
					System.out.println("consumer " + threadnumber + ":" + match.getClaim2().getId());
					processMatch(match);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	private static void processMatch(MatchHistory match){
		//process match
		processName(match);
		
		
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
		Set<Person> plist2 = getPersons(match.getClaim2());
		
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
