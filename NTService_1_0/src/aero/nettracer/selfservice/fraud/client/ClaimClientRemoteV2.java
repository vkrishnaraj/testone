package aero.nettracer.selfservice.fraud.client;

import javax.ejb.Remote;

import org.jboss.ejb3.annotation.RemoteBinding;
import org.jboss.ejb3.annotation.RemoteBindings;


@RemoteBindings({
	@RemoteBinding(clientBindUrl="sslsocket://184.172.41.5:3843", jndiBinding="NTServices_1_0/ClaimClientBeanV2/publicTestingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://184.172.41.5:3844", jndiBinding="NTServices_1_0/ClaimClientBeanV2/publicTrainingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://184.172.41.6:3845", jndiBinding="NTServices_1_0/ClaimClientBeanV2/publicProductionRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://10.8.185.136:3843", jndiBinding="NTServices_1_0/ClaimClientBeanV2/privateTestingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://10.8.185.136:3844", jndiBinding="NTServices_1_0/ClaimClientBeanV2/privateTrainingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://10.8.185.136:3845", jndiBinding="NTServices_1_0/ClaimClientBeanV2/privateProductionRemoteSSL"),
	@RemoteBinding(jndiBinding="NTServices_1_0/ClaimClientBeanV2/remote")
})
@Remote
public interface ClaimClientRemoteV2 extends ClaimClientRemote{

}
