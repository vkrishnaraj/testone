package aero.nettracer.selfservice.fraud.client;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import org.jboss.ejb3.annotation.RemoteBinding;
import org.jboss.ejb3.annotation.RemoteBindings;

import aero.nettracer.fs.model.transport.v0.File;
import aero.nettracer.fs.model.transport.v0.detection.AccessRequest;
import aero.nettracer.fs.model.transport.v0.detection.TraceResponse;


@RemoteBindings({
	@RemoteBinding(clientBindUrl="sslsocket://184.172.41.5:3843", jndiBinding="NTServices_1_0/ClaimClientBeanV1/publicTestingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://184.172.41.5:3844", jndiBinding="NTServices_1_0/ClaimClientBeanV1/publicTrainingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://184.172.41.6:3845", jndiBinding="NTServices_1_0/ClaimClientBeanV1/publicProductionRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://10.8.185.136:3843", jndiBinding="NTServices_1_0/ClaimClientBeanV1/privateTestingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://10.8.185.136:3844", jndiBinding="NTServices_1_0/ClaimClientBeanV1/privateTrainingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://10.8.185.136:3845", jndiBinding="NTServices_1_0/ClaimClientBeanV1/privateProductionRemoteSSL"),
	@RemoteBinding(jndiBinding="NTServices_1_0/ClaimClientBeanV1/remote")
})
@Remote
public interface ClaimClientRemote {
	public String echoTest(String s);
	public long insertFile(File file);
	public TraceResponse traceFile(long fileId, int maxDelay, boolean isPrimary, boolean returnResults);
	public TraceResponse getFileMatches(long fileId);
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
