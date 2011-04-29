package aero.nettracer.selfservice.fraud;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.detection.AccessRequest;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.fs.model.detection.TraceResponse;



//@RemoteBinding(clientBindUrl="sslsocket://127.0.0.1:4850")
@Remote
public interface ClaimRemote {
	public String echoTest(String s);
	public long insertFile(File File);
	public TraceResponse traceFile(long fileId, int maxDelay, boolean isPrimary);
	public TraceResponse traceFile(File file, int maxDelay, boolean persistResults, boolean isPrimary);
	public Set<MatchHistory> getFileMatches(long fileId);
	public int getIncidentCacheSize();
	public int getClaimCacheSize();
	public void requestAccess(long fileId, long matchId, String agent, String requestingAirline, String message);
	public List<AccessRequest> getOutstandingRequests(String airlineId, int begin, int perPage);
	public int getOutstandingRequetsCount(String airlineId);
	public void approveRequest(long requestId, String message, String agent);
	public void denyRequest(long requestId, String message, String agent);
	public File getFile(long fileId, String airline);
	public boolean deleteMatch(long matchId);
	public boolean deleteMatch(Set<Long>matchIds);
}
