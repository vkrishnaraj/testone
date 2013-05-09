package aero.nettracer.selfservice.fraud;

import javax.ejb.Remote;

import aero.nettracer.serviceprovider.common.db.PrivacyPermissions;


@Remote
public interface PrivacyPermissionsRemote {
	public PrivacyPermissions getPrivacyPermissions(String companycode, PrivacyPermissions.AccessLevelType level);
	public void setPrivacyPermissions(PrivacyPermissions ca);
}
