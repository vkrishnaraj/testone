package aero.nettracer.serviceprovider.common.db;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import aero.nettracer.serviceprovider.common.db.PrivacyPermissions.AccessLevelType;



@Embeddable
public class PrivacyPermissionsKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5656496837397901463L;

	public PrivacyPermissionsKey(){
		this.companycode = null;
		this.level = null;
	}
	
	public PrivacyPermissionsKey(String companycode, AccessLevelType level){
		this.companycode = companycode;
		this.level = level;
	}
	
	public String getCompanycode() {
		return companycode;
	}
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	
	@Enumerated(EnumType.STRING)
	public AccessLevelType getLevel() {
		return level;
	}
	public void setLevel(AccessLevelType level) {
		this.level = level;
	}
	String companycode;
	AccessLevelType level;
	
	
	
}
