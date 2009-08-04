package aero.nettracer.serviceprovider.common.db;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "profile")
@Proxy(lazy = true)
public class Profile {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;

	//TODO: DEFINE HIBERNATE MAPPING
	private Map<ParameterType, String> parameters;

	//TODO: DEFINE HIBERNATE MAPPING
	private Map<PermissionType, Boolean> permissions;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Map<ParameterType, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<ParameterType, String> parameters) {
		this.parameters = parameters;
	}

	public Map<PermissionType, Boolean> getPermissions() {
		return permissions;
	}

	public void setPermissions(Map<PermissionType, Boolean> permissions) {
		this.permissions = permissions;
	}
}
