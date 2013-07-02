package aero.nettracer.fs.utilities.tracing.cache;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.utilities.tracing.TraceWrapper;

/**
 * @author Loupas
 *
 * The default implementation of CacheManager does not use any caching, just loads from the DB.
 * 
 * If you ever want to permanently disable the cache, instantiate this class
 *
 */
public class CacheManagerDefaultImpl implements CacheManager{

	public static CacheManagerDefaultImpl cache;

	public static CacheManagerDefaultImpl getInstance() {
		if(cache == null){
			System.out.println("CacheMangerDefaultImpl");
			cache = new CacheManagerDefaultImpl();
		}
		return cache;
	}
	
	@Override
	public File loadFile(long fileId) {
		return TraceWrapper.loadFile(fileId);
	}

	@Override
	public void updateCache(File file) {
		return;//no cache to add to
	}

	@Override
	public void removeFromCache(long fileId) {
		return;//no cache to remove from
	}


}
