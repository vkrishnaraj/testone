package aero.nettracer.fs.utilities.tracing.cache;

import aero.nettracer.fs.model.File;

public interface CacheManager {
	public File loadFile(long fileId);
	public void updateCache(File file);
	public void removeFromCache(long fileId);
}
