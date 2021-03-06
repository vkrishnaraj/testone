package aero.nettracer.selfservice.fraud.client;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.healthmarketscience.rmiio.RemoteInputStream;

import aero.nettracer.fs.model.transport.v0.File;
import aero.nettracer.fs.model.transport.v0.FsAttachment;
import aero.nettracer.fs.model.transport.v0.FsClaim;
import aero.nettracer.fs.model.transport.v0.detection.AccessRequest;
import aero.nettracer.fs.model.transport.v0.detection.AccessRequestDTO;
import aero.nettracer.fs.model.transport.v0.detection.TraceResponse;
import aero.nettracer.fs.model.transport.v0.forum.FsForumThread;
import aero.nettracer.fs.model.transport.v0.forum.FsForumSearch;
import aero.nettracer.fs.model.transport.v0.forum.FsForumSearchResults;


public interface ClaimClientRemote {
	public String echoTest(String s);
	public long insertFile(File file);
	public TraceResponse traceFile(long fileId, int maxDelay, boolean isPrimary, boolean returnResults);
	public TraceResponse getFileMatches(long fileId);
	public int getIncidentCacheSize();
	public int getClaimCacheSize();
	public void requestAccess(long fileId, long matchId, String agent, String requestingAirline, String message);
	public void requestAccess(long fileId, long matchId, String agent, String requestingAirline, String message, String contactName, String contactEmail, String contactPhone);
	public List<AccessRequest> getOutstandingRequests(String airlineId, int begin, int perPage);
	public int getOutstandingRequetsCount(String airlineId);
	public void approveRequest(long requestId, String message, String agent);
	public void denyRequest(long requestId, String message, String agent);
	public File getFile(long fileId, String airline);
	public boolean deleteMatch(long matchId);
	public boolean deleteMatch(Set<Long>matchIds);
	public List<AccessRequest> getAccessRequests(AccessRequestDTO dto, int begin,int perPage);
	public int getAccessRequestsCount(AccessRequestDTO dto);
	public Map<String, Integer> getMatches(List<String> idList);
	public Map<String, Integer> getMatches(List<String> idList, String companycode);
	public FsAttachment uploadAttachment(FsAttachment attach);
	public FsAttachment getAttachment(int attachID, String airline);
	public boolean saveAttachments(List<Integer> attachIDs, long fileid, String airline, long claimId); 
	public boolean deleteAttachment(int attachID); 
	public long saveThread(FsForumThread thread);
	public FsForumThread getThread(long threadID);
	public FsForumSearchResults getTags(int page);
	public FsForumSearchResults getThreads(FsForumSearch criteria, int page);
	public FsClaim getClaim(long claimId, String airline);
}
