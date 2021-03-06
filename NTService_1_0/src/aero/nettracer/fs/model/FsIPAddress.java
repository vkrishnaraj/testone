package aero.nettracer.fs.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import aero.nettracer.fs.model.detection.IPWhiteList;
import aero.nettracer.fs.model.detection.PhoneWhiteList;

@Entity
@Proxy(lazy = false)
public class FsIPAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	private String ipAddress;
	private String association;

	@ManyToOne(targetEntity = aero.nettracer.fs.model.detection.IPWhiteList.class)
	@Fetch(FetchMode.SELECT)
	private IPWhiteList whitelist;
	
	@ManyToOne(targetEntity = aero.nettracer.fs.model.FsClaim.class)
	@Fetch(FetchMode.SELECT)
	private FsClaim claim;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public FsClaim getClaim() {
		return claim;
	}

	public void setClaim(FsClaim claim) {
		this.claim = claim;
	}

	public IPWhiteList getWhitelist() {
		return whitelist;
	}

	public void setWhitelist(IPWhiteList whitelist) {
		this.whitelist = whitelist;
	}

	public void setAssociation(String association) {
		this.association = association;
	}

	public String getAssociation() {
		return association;
	}
	
}
