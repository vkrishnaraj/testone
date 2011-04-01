package aero.nettracer.fs.model.detection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Proxy;

import aero.nettracer.fs.model.Claim;

@Entity
@Proxy(lazy = false)
public class InternalSummary {
	@Id
	@GeneratedValue
	private long id;
	
	@OneToOne(targetEntity = aero.nettracer.fs.model.Claim.class)
	private Claim claim;
	private String description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Claim getClaim() {
		return claim;
	}

	public void setClaim(Claim claim) {
		this.claim = claim;
	}
}
