package aero.nettracer.fs.utilities.tracing;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.Query;
import org.hibernate.Session;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;


public class TraceWrapper {
	private static ArrayBlockingQueue<MatchHistory> matchQueue;
	static int maxThreads = 10;
	
	private static ConcurrentHashMap<Long, FsIncident> incidentCache = new ConcurrentHashMap<Long, FsIncident>(3000);
	private static ConcurrentHashMap<Long, FsClaim> claimCache = new ConcurrentHashMap<Long, FsClaim>(3000);
	private static ConcurrentHashMap<Long, File> fileCache = new ConcurrentHashMap<Long, File>(3000);
	
	private static boolean LOAD_FROM_CACHE = false;
	private static int MAX_CACHE_SIZE = 15000;
	
	public static File loadFile(long fileId){
		String sql = "from aero.nettracer.fs.model.File f where f.id = :id";
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(sql.toString());
		q.setParameter("id", fileId);
		List<File> result = q.list();
		sess.close();
		if(result != null && result.size() > 0){
			return result.get(0);
		} else {
			return null;
		}
	}
	
	public static File loadFileFromCache(Long id){
		
		if(LOAD_FROM_CACHE && fileCache.containsKey(id)){
//			System.out.println("i cache");
			return fileCache.get(id);
		} else {
			 File file = loadFile(id);
			if(LOAD_FROM_CACHE && fileCache.size() < MAX_CACHE_SIZE){
				fileCache.put(id, file);
			}
			return file;
		}
	}
	
	
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
	
	public static FsIncident loadIncidentFromCache(Long id){
		if(LOAD_FROM_CACHE && incidentCache.containsKey(id)){
//			System.out.println("i cache");
			return incidentCache.get(id);
		} else {
			FsIncident incident = loadIncident(id);
			if(LOAD_FROM_CACHE && incidentCache.size() < MAX_CACHE_SIZE){
				incidentCache.put(id, incident);
			}
			return incident;
		}
	}
	
	public static FsClaim loadClaimFromCache(Long id){
		if(LOAD_FROM_CACHE && claimCache.containsKey(id)){
//			System.out.println("c cache");
			return claimCache.get(id);
		} else {
			FsClaim claim = loadClaim(id);
			if(LOAD_FROM_CACHE  && claimCache.size() < MAX_CACHE_SIZE){
				claimCache.put(id, claim);
			}
			return claim;
		}
	}
	
	public static void saveIncidentToCache(FsIncident inc){
		if(LOAD_FROM_CACHE){
			incidentCache.put(inc.getId(), inc);
		}
	}
	
	public static void saveClaimToCache(FsClaim claim){
		if(LOAD_FROM_CACHE){
			claimCache.put(claim.getId(), claim);
		}
	}
	
	public static void evictIncidentFromCache(long id){
		if(LOAD_FROM_CACHE){
			incidentCache.remove(id);
		}
	}
	
	public static void evictClaimFromCache(long id){
		if(LOAD_FROM_CACHE){
			claimCache.remove(id);
		}
	}
	
	public static void resetIncidentCache(){
		incidentCache = new ConcurrentHashMap<Long, FsIncident>(3000);
	}
	
	public static void resetClaimCache(){
		claimCache = new ConcurrentHashMap<Long, FsClaim>(3000);
	}

	public static int getIncidentCacheSize(){
		return incidentCache.size();
	}
	
	public static int getClaimCacheSize(){
		return claimCache.size();
	}
	

	public static ArrayBlockingQueue<MatchHistory> getMatchQueue(){
		if(matchQueue == null){
			matchQueue = new ArrayBlockingQueue<MatchHistory>(1000);
			for (int i=0; i<maxThreads; ++i) {
				try{
					Consumer consumer = new Consumer(matchQueue, Consumer.MATCH, i);
					new Thread(consumer).start();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return matchQueue;
	}

}
