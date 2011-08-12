package aero.nettracer.selfservice.fraud;

import javax.ejb.Remote;

import org.jboss.ejb3.annotation.RemoteBinding;
import org.jboss.ejb3.annotation.RemoteBindings;

import aero.nettracer.serviceprovider.common.db.PrivacyPermissions;


//@RemoteBinding(clientBindUrl="sslsocket://127.0.0.1:4850")
//	, jndiBinding="permissionsSSL")

@RemoteBindings({
	@RemoteBinding(clientBindUrl="sslsocket://184.172.24.144:3843", jndiBinding="services/PrivacyPermissionsBean/testingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://184.172.24.144:3844", jndiBinding="services/PrivacyPermissionsBean/trainingRemoteSSL"),
	@RemoteBinding(clientBindUrl="sslsocket://184.172.24.144:3845", jndiBinding="services/PrivacyPermissionsBean/productionRemoteSSL"),
	@RemoteBinding(jndiBinding="NTServices_1_0/PrivacyPermissionsBean/remote")
})
@Remote
public interface PrivacyPermissionsRemote {
	public PrivacyPermissions getPrivacyPermissions(String companycode, PrivacyPermissions.AccessLevelType level);
	public void setPrivacyPermissions(PrivacyPermissions ca);
}
