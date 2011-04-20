package aero.nettracer.fs.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Proxy;


@Entity
@Proxy(lazy = false)
public class File implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	
	private long swapId;
	
	@OneToOne(targetEntity = aero.nettracer.fs.model.FsClaim.class, cascade = CascadeType.ALL)
	private FsClaim claim;
	
	@OneToOne(targetEntity = aero.nettracer.fs.model.FsIncident.class, cascade = CascadeType.ALL)
	private FsIncident incident;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public FsClaim getClaim() {
		return claim;
	}
	public void setClaim(FsClaim claim) {
		this.claim = claim;
	}
	public FsIncident getIncident() {
		return incident;
	}
	public void setIncident(FsIncident incident) {
		this.incident = incident;
	}
	public void setSwapId(long swapId) {
		this.swapId = swapId;
	}
	public long getSwapId() {
		return swapId;
	}
}
