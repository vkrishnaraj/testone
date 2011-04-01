package aero.nettracer.fs.model.detection;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import aero.nettracer.fs.model.Claim;

@Entity
@Proxy(lazy = false)
public class MatchHistory {
	@Id
	@GeneratedValue
	private long id;
	
	@OneToMany(mappedBy = "match", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private Set<MatchDetail> details;
	
	@OneToOne(targetEntity = aero.nettracer.fs.model.Claim.class)
	private Claim claim1;
	
	@OneToOne(targetEntity = aero.nettracer.fs.model.Claim.class)
	private Claim claim2;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<MatchDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<MatchDetail> details) {
		this.details = details;
	}

	public Claim getClaim1() {
		return claim1;
	}

	public void setClaim1(Claim claim1) {
		this.claim1 = claim1;
	}

	public Claim getClaim2() {
		return claim2;
	}

	public void setClaim2(Claim claim2) {
		this.claim2 = claim2;
	}
}
