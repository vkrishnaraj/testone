package aero.nettracer.fs.utilities.tracing.cache;

import java.io.IOException;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.lifecycle.ComponentStatus;


import aero.nettracer.fs.model.File;
import aero.nettracer.fs.utilities.tracing.TraceWrapper;

public class CacheManagerInfinispanImpl implements CacheManager {

	public static CacheManagerInfinispanImpl cache;
	public static Cache <Long,Object> myCache;

	public static CacheManagerInfinispanImpl getInstance() {
		if(cache == null){
			System.out.println("CacheMangerInfinispanImpl");
			cache = new CacheManagerInfinispanImpl();
		}
		return cache;
	}
	
	@Override
	public File loadFile(long fileId) {
		File file = null;
		try{
		Cache<Long, Object> myCache = this.getCache();
			file = (File)myCache.get(fileId);
			if(file == null){
				file = TraceWrapper.loadFile(fileId);
				this.updateCache(file);
			}
		} catch (Exception e){
			file = TraceWrapper.loadFile(fileId);
		}
		return file;
	}

	@Override
	public void updateCache(File file) {
		if(file != null && useCacheByCompany(file.getValidatingCompanycode())){
			try{
				Cache<Long,Object> myCache = this.getCache();
				myCache.put(file.getId(), file);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public void removeFromCache(long fileId) {
		try{
			Cache<Long, Object> myCache = this.getCache();
			myCache.remove(fileId);
		} catch (Exception e){
		}
	}
	
	
	private Cache<Long, Object> getCache(){
		if(myCache != null){
			return myCache;
		}
		DefaultCacheManager m;
		try {
			m = new DefaultCacheManager("/infinispan_config.xml");		
			Cache<Long, Object> cache = m.getCache("FsFileCache");
			if(cache.getStatus() != ComponentStatus.RUNNING){
				cache.start();
			}
			this.myCache = cache;
			return cache;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Since the cache is currently not distributed, the cache and only apply to hosted clients.
	 * Otherwise non-hosted files will enter the cache and never get updated.
	 * 
	 * @param companycode
	 * @return
	 */
	private boolean useCacheByCompany(String companycode){
		if(companycode != null 
				&& !"US".equals(companycode.toUpperCase()) 
				&& !"FL".equals(companycode.toUpperCase())){
			return true;
		} else {
			return false;
		}
	}
	
	
	
	/**
	 * Test method to file the cache and test the eviction policy
	 * 
	 * @param fileId
	 */
	public void fillCacheTest(long fileId){
		File file = this.loadFile(fileId);
		try{
			for(int i = 1; i < 140000; i++){
				Cache<Long,Object> myCache = this.getCache();
				myCache.put(new Long(i), TraceWrapper.loadFile(fileId));
				if(i%100 == 0){
					System.out.println(i + " | " + myCache.size());
				}
			}
		} catch (Exception e){
			
		}
		System.out.println("FILL CACHE DONE");
	}
	
}
