package aero.nettracer.selfservice.fraud;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import org.jboss.ejb3.annotation.RemoteBinding;
import org.jboss.ejb3.annotation.RemoteBindings;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.detection.AccessRequest;
import aero.nettracer.fs.model.detection.TraceResponse;


@RemoteBindings({
	@RemoteBinding(clientBindUrl="sslsocket://184.172.24.144:3843", jndiBinding="NTServices_1_0/ClaimBean/testingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://184.172.24.144:3844", jndiBinding="NTServices_1_0/ClaimBean/trainingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://184.172.41.4:3845", jndiBinding="NTServices_1_0/ClaimBean/productionRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://10.8.185.136:3843", jndiBinding="NTServices_1_0/ClaimBean/testingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://10.8.185.136:3844", jndiBinding="NTServices_1_0/ClaimBean/trainingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://10.8.185.136:3845", jndiBinding="NTServices_1_0/ClaimBean/productionRemoteSSL"),
	@RemoteBinding(jndiBinding="NTServices_1_0/ClaimBean/remote")
})
//@RemoteBinding(jndiBinding="custom/remote/MySession")
//@RemoteBinding(jndiBinding="StatelessSSL")
@Remote
public interface ClaimRemote {
	public String echoTest(String s);
	public long insertFile(File File);
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
