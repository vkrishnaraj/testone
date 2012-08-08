package aero.nettracer.selfservice.fraud;

import java.util.List;
import java.util.Set;

import javax.jws.WebService;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.detection.AccessRequest;
import aero.nettracer.fs.model.detection.TraceResponse;

//@WebService
public class ClaimWebService{

	public String echoTest(String username, String password, String s) {
		return s;
	}

	public long insertFile(String username, String password, File File) {
		ClaimRemote remote = new ClaimBean();
		return remote.insertFile(File);
	}

	public TraceResponse traceFile(String username, String password, long fileId, int maxDelay, boolean isPrimary) {
		ClaimRemote remote = new ClaimBean();
		return remote.traceFile(fileId, maxDelay, isPrimary, true);
	}

	public TraceResponse getFileMatches(String username, String password, long fileId) {
		ClaimRemote remote = new ClaimBean();
		return remote.getFileMatches(fileId);
	}


	public void requestAccess(String username, String password, long fileId, long matchId, String agent,
			String requestingAirline, String message) { //, String contact
		ClaimRemote remote = new ClaimBean();
		remote.requestAccess(fileId, matchId, agent, requestingAirline, message); //contact

	}

	public List<AccessRequest> getOutstandingRequests(String username, String password, String airlineId,
			int begin, int perPage) {
		ClaimRemote remote = new ClaimBean();
		return remote.getOutstandingRequests(airlineId, begin, perPage);
	}

	public int getOutstandingRequetsCount(String username, String password, String airlineId) {
		ClaimRemote remote = new ClaimBean();
		return remote.getOutstandingRequetsCount(airlineId);
	}

	public void approveRequest(String username, String password, long requestId, String message, String agent) {
		ClaimRemote remote = new ClaimBean();
		remote.approveRequest(requestId, message, agent);

	}

	public void denyRequest(String username, String password, long requestId, String message, String agent) {
		ClaimRemote remote = new ClaimBean();
		remote.denyRequest(requestId, message, agent);

	}

	public File getFile(String username, String password, long fileId, String airline) {
		ClaimRemote remote = new ClaimBean();
		return remote.getFile(fileId, airline);
	}

	public boolean deleteMatch(String username, String password, long matchId) {
		ClaimRemote remote = new ClaimBean();
		return remote.deleteMatch(matchId);
	}

	public boolean deleteMatch(String username, String password, Set<Long> matchIds) {
		ClaimRemote remote = new ClaimBean();
		return remote.deleteMatch(matchIds);
	}

}
