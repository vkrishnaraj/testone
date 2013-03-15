package aero.nettracer.serviceprovider.common.db;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

//import org.hibernate.annotations.MapKey;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

@Entity
@Table(name = "svc_profile")
@Proxy(lazy = true)
public class Profile {

	
	
	@Id
	@GeneratedValue
	private long id;

	@Column(length = 20)
	private String name;
	
	@Column(length = 2)
	private String airline;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	@JoinTable(name = "svc_profile_parameters", joinColumns = @JoinColumn(name = "profile_id"))
	@MapKeyEnumerated(javax.persistence.EnumType.STRING)
	@MapKeyColumn(name = "parameter_type")
	@Column(name="element")
	private Map<ParameterType, String> parameters;

	@ElementCollection(targetClass = Boolean.class, fetch = FetchType.EAGER)
	@JoinTable(name = "svc_profile_permission", joinColumns = @JoinColumn(name = "permission_id"))
	@MapKeyEnumerated(javax.persistence.EnumType.STRING)
	@MapKeyColumn(name = "permission_type")
	@Column(name="element")
	private Map<PermissionType, Boolean> permissions;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getAirline() {
  	return airline;
  }

	public void setAirline(String airline) {
  	this.airline = airline;
  }
}
