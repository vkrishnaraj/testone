package aero.nettracer.serviceprovider.common.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "sabre_connection")
@Proxy(lazy = true)
@NamedQueries({
	@NamedQuery(
	name = "loadByProfile",
	query = "from SabreConnection c where c.profile.id  = :profile"
	)
	})
public class SabreConnection {

	@Transient
	public static final String LOAD_BY_PERMISSION = "loadByProfile";
	
	@Id
	@GeneratedValue
	private long id;
	
	private Profile profile;
	
	@Column(length = 2)
	private String company;
	
	@Column(length = 20)
	private String username;
	
	@Column(length = 20)
	private String password;

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

	public String getCompany() {
  	return company;
  }

	public void setCompany(String company) {
  	this.company = company;
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
