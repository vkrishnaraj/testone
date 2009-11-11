package aero.nettracer.serviceprovider.common.db;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.MapKey;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

@Entity
@Table(name = "profile")
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
	

	@org.hibernate.annotations.CollectionOfElements(targetElement = String.class, fetch = FetchType.EAGER)
	@JoinTable(name = "profile_parameters", joinColumns = @JoinColumn(name = "profile_id"))
	@MapKey(columns = @Column(name = "parameter_type", length = 30), type = @Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "aero.nettracer.serviceprovider.common.db.ParameterType"),
			@Parameter(name = EnumType.TYPE, value = "12") }))
	private Map<ParameterType, String> parameters;

	@org.hibernate.annotations.CollectionOfElements(targetElement = Boolean.class, fetch = FetchType.EAGER)
	@JoinTable(name = "profile_permission", joinColumns = @JoinColumn(name = "permission_id"))
	@MapKey(columns = @Column(name = "permission_type", length = 30), type = @Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "aero.nettracer.serviceprovider.common.db.PermissionType"),
			@Parameter(name = EnumType.TYPE, value = "12") }))
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
