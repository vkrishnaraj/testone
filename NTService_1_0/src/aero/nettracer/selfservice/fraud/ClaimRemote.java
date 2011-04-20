package aero.nettracer.selfservice.fraud;

import java.util.Set;

import javax.ejb.Remote;

import org.jboss.ejb3.annotation.RemoteBinding;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.detection.MatchHistory;



//@RemoteBinding(clientBindUrl="sslsocket://127.0.0.1:4850")
@Remote
public interface ClaimRemote {
	public String echoTest(String s);
	public long insertFile(File File);
	public Set<MatchHistory> traceFile(long fileId);
	public Set<MatchHistory> getFileMatches(long fileId);
	public int getIncidentCacheSize();
	public int getClaimCacheSize();
}
