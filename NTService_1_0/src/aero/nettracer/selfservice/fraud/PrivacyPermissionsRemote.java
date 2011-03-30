package aero.nettracer.selfservice.fraud;

import javax.ejb.Remote;

import org.jboss.ejb3.annotation.RemoteBinding;

import aero.nettracer.serviceprovider.common.db.PrivacyPermissions;


@RemoteBinding(clientBindUrl="sslsocket://192.168.2.145:4850", jndiBinding="permissionsSSL")
@Remote
public interface PrivacyPermissionsRemote {
	public PrivacyPermissions getPrivacyPermissions(String companycode, PrivacyPermissions.AccessLevelType level);
	public void setPrivacyPermissions(PrivacyPermissions ca);
}
