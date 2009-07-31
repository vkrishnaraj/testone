package aero.nettracer.serviceprovider.common.db;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import aero.nettracer.serviceprovider.common.pojo.UserProvidedParameters;

@Entity
@Table(name = "User")
@Proxy(lazy = true)
@NamedQueries({
	@NamedQuery(
	name = "loadByUsername",
	query = "from User where username = :username"
	)
	})
public class User {
	 
	public static final String LOAD_BY_PERMISSION = "loadByUsername";

	@Id
	@GeneratedValue
	private long id;

	@Column(length = 20)
	private String username;

	@Column(length = 20)
	private String password;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Profile profile;

	@Transient
	private Set<UserProvidedParameters> userProvidedParameters;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<UserProvidedParameters> getUserProvidedParameters() {
		return userProvidedParameters;
	}

	public void setUserProvidedParameters(Set<UserProvidedParameters> userProvidedParameters) {
		this.userProvidedParameters = userProvidedParameters;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
