package aero.nettracer.fs.model.transport.v2;

import java.io.Serializable;

public class FsIPAddress implements Serializable {
	private static final long serialVersionUID = 5504963603280831328L;
	
	private long id;
	private String ipAddress;
	private String association;

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

	public void setAssociation(String association) {
		this.association = association;
	}

	public String getAssociation() {
		return association;
	}
	
}
