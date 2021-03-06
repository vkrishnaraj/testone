package aero.nettracer.fs.utilities.tracing;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.Query;
import org.hibernate.Session;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.fs.utilities.tracing.cache.CacheManager;
import aero.nettracer.fs.utilities.tracing.cache.CacheManagerDefaultImpl;
import aero.nettracer.fs.utilities.tracing.cache.CacheManagerInfinispanImpl;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;


public class TraceWrapper {
	private static ArrayBlockingQueue<MatchHistory> matchQueue;
	static final int DEFAULT_MAX_THREADS = 20;
	
	private static ConcurrentHashMap<Long, FsIncident> incidentCache = new ConcurrentHashMap<Long, FsIncident>(3000);
	private static ConcurrentHashMap<Long, FsClaim> claimCache = new ConcurrentHashMap<Long, FsClaim>(3000);
	private static ConcurrentHashMap<Long, File> fileCache = new ConcurrentHashMap<Long, File>(3000);
	
	private static boolean LOAD_FROM_CACHE = false;
	private static int MAX_CACHE_SIZE = 5000;
	
	private static String FS_CACHE_MANAGER_IMPL = System.getProperty("fs.cachemanager.impl");
	
	public static File loadFile(long fileId){
		Session sess = HibernateWrapper.getSession().openSession();
		File f = null;
		try{
			f = (File) sess.load(File.class, fileId);

		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		return f;
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
	
	@Deprecated
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
	
	@Deprecated
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
	
	@Deprecated
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
	
	@Deprecated
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
	
	public static void startConsumerThreads(){
		int maxThreads = DEFAULT_MAX_THREADS;
		try{
			maxThreads = new Integer(System.getProperty("fs.consumer.max.threads"));
		} catch (Exception e){
			maxThreads = DEFAULT_MAX_THREADS;//unable to parse system property, use default
		}
		System.out.println("Consumer Thread Count: " + maxThreads);
		
		Vector <ThreadContainer>v = new Vector<ThreadContainer>();
		for (int i=0; i<maxThreads; ++i) {
			try{
				ThreadContainer tc =  new ThreadContainer();
				tc.setStartTime(new Date());
				Consumer consumer = new Consumer(matchQueue, Consumer.MATCH, tc);
				Thread t = new Thread(consumer, "CosumerThread " + i);
				tc.setConsumer(t);
				tc.setId(i);
				v.add(tc);
				t.setPriority(Thread.MIN_PRIORITY);
				t.start();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		ThreadMonitor tm = new ThreadMonitor(v, matchQueue);
		Thread t = new Thread(tm, "TraceWrapperMonitorThread");
		t.start();
	}
	
	public static Thread startProducerGeoThread(ConsumerQueueElement element, File file, boolean isPrimary, Date createDate){
		ProducerGeo pg = new ProducerGeo(element,file,isPrimary,createDate);
		Thread t = new Thread(pg, "ProducerGeo " + file.getId());
		t.setPriority(Thread.MIN_PRIORITY);
		t.start();
		return t;
	}
	
	
	
	/**
	 * Using the Wrapper property to set the cache implementation.  This also provides a kill switch, if we ever need to disable the cache,
	 * update the wrapper conf to use the DefaultImpl and bounce.
	 * 
	 * If we ever updatet this project with Spring, consider refactoring to use Spring injection instead
	 * 
	 * @return
	 */
	public static CacheManager getCacheManager(){
		if("CacheManagerInfinispanImpl".equals(FS_CACHE_MANAGER_IMPL)){
			return CacheManagerInfinispanImpl.getInstance();
		} else if ("CacheManagerDefaultImpl".equals(FS_CACHE_MANAGER_IMPL)){
			return CacheManagerDefaultImpl.getInstance();
		} else {
			return CacheManagerDefaultImpl.getInstance();
		}
	}
	
}
