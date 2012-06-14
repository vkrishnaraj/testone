package aero.nettracer.selfservice.fraud.client;

import javax.ejb.Remote;

import org.jboss.ejb3.annotation.RemoteBinding;
import org.jboss.ejb3.annotation.RemoteBindings;


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
public interface ClaimClientRemoteV1 extends ClaimClientRemote{

}
