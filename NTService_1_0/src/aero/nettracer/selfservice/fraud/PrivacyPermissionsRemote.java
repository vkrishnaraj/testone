package aero.nettracer.selfservice.fraud;

import javax.ejb.Remote;

import org.jboss.ejb3.annotation.RemoteBinding;
import org.jboss.ejb3.annotation.RemoteBindings;

import aero.nettracer.serviceprovider.common.db.PrivacyPermissions;


//@RemoteBinding(clientBindUrl="sslsocket://127.0.0.1:4850")
//	, jndiBinding="permissionsSSL")

@RemoteBindings({
	@RemoteBinding(clientBindUrl="sslsocket://184.172.41.5:3843", jndiBinding="NTServices_1_0/PrivacyPermissionsBean/publicTestingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://184.172.41.5:3844", jndiBinding="NTServices_1_0/PrivacyPermissionsBean/publicTrainingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://184.172.41.6:3845", jndiBinding="NTServices_1_0/PrivacyPermissionsBean/publicProductionRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://10.8.185.136:3843", jndiBinding="NTServices_1_0/PrivacyPermissionsBean/privateTestingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://10.8.185.136:3844", jndiBinding="NTServices_1_0/PrivacyPermissionsBean/privateTrainingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://10.8.185.136:3845", jndiBinding="NTServices_1_0/PrivacyPermissionsBean/privateProductionRemoteSSL"),
	@RemoteBinding(jndiBinding="NTServices_1_0/PrivacyPermissionsBean/remote")
})
@Remote
public interface PrivacyPermissionsRemote {
	public PrivacyPermissions getPrivacyPermissions(String companycode, PrivacyPermissions.AccessLevelType level);
	public void setPrivacyPermissions(PrivacyPermissions ca);
}
