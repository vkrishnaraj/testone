package aero.nettracer.serviceprovider.common.db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = true)
@Table(name = "svc_world_tracer_ishares_account")
@NamedQueries({
	@NamedQuery(
	name = "loadWTISharesAccountByProfile",
	query = "from WorldTracerISharesAccount where profile.id = :profile"
	)
	})
public class WorldTracerISharesAccount implements Serializable {

	@Transient
	public static final String LOAD_BY_PROFILE = "loadWTISharesAccountByProfile";

	@Column(length = 2)
	private String companyCode;

	@Id
	@GeneratedValue
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastReset;

	@Column(length = 20)
	private String password;

	@ManyToOne
	private Profile profile;

	@Column(length = 20)
	private String username;
	
	@Column(length = 20)
	private String sine;
	
	
	@Column(length = 128)
	private String host;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public long getId() {
		return id;
	}

	public Date getLastReset() {
		return lastReset;
	}

	public String getPassword() {
		return password;
	}

	public Profile getProfile() {
		return profile;
	}

	public String getUsername() {
		return username;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLastReset(Date lastReset) {
		this.lastReset = lastReset;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSine() {
		return sine;
	}

	public void setSine(String sine) {
		this.sine = sine;
	}
}
