package aero.nettracer.serviceprovider.common.db;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "permission")
@Proxy(lazy = true)
public class Permission {

	@Id
	@GeneratedValue
	private long id;

	@Basic
	private long permission_id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Profile profile;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPermission_id() {
		return permission_id;
	}

	public void setPermission_id(long permission_id) {
		this.permission_id = permission_id;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
