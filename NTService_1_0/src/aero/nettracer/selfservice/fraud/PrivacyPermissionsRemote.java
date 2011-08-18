package aero.nettracer.selfservice.fraud;

import javax.ejb.Remote;

import org.jboss.ejb3.annotation.RemoteBinding;
import org.jboss.ejb3.annotation.RemoteBindings;

import aero.nettracer.serviceprovider.common.db.PrivacyPermissions;


//@RemoteBinding(clientBindUrl="sslsocket://127.0.0.1:4850")
//	, jndiBinding="permissionsSSL")

@RemoteBindings({
	@RemoteBinding(clientBindUrl="sslsocket://184.172.24.144:3843", jndiBinding="NTServices_1_0/PrivacyPermissionsBean/testingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://184.172.24.144:3844", jndiBinding="NTServices_1_0/PrivacyPermissionsBean/trainingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://184.172.24.144:3845", jndiBinding="NTServices_1_0/PrivacyPermissionsBean/productionRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://10.8.185.136:3843", jndiBinding="NTServices_1_0/PrivacyPermissionsBean/testingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://10.8.185.136:3844", jndiBinding="NTServices_1_0/PrivacyPermissionsBean/trainingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://10.8.185.136:3845", jndiBinding="NTServices_1_0/PrivacyPermissionsBean/productionRemoteSSL"),
	@RemoteBinding(jndiBinding="NTServices_1_0/PrivacyPermissionsBean/remote")
})
@Remote
public interface PrivacyPermissionsRemote {
	public PrivacyPermissions getPrivacyPermissions(String companycode, PrivacyPermissions.AccessLevelType level);
	public void setPrivacyPermissions(PrivacyPermissions ca);
}
