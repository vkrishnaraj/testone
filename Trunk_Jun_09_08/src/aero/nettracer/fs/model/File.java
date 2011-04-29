package aero.nettracer.fs.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.constant.TracingConstants;

@Entity
@Proxy(lazy = false)
public class File implements Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	HashMap<Long, Double> matchingFiles = null;

	public HashMap<Long, Double> getMatchingFiles() {
		return matchingFiles;
	}

	public void setMatchingFiles(HashMap<Long, Double> matchingFiles) {
		this.matchingFiles = matchingFiles;
	}

	private int statusId;
	
	@Transient
	Set<Person> personCache = null;
	@Transient
	Set<FsAddress> addressCache = null;
	@Transient
	Set<Phone> phoneCache = null;

	@Id
	@GeneratedValue
	private long id;

	private long swapId;

	@OneToOne(targetEntity = aero.nettracer.fs.model.FsClaim.class, cascade = CascadeType.ALL, mappedBy = "file")
	private FsClaim claim;

	@OneToOne(targetEntity = aero.nettracer.fs.model.FsIncident.class, cascade = CascadeType.ALL, mappedBy = "file")
	private FsIncident incident;

	public File() {
	}

	public File(long id) {
		this.id = id;
	}

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

	public Set<Person> getPersonCache() {
		return personCache;
	}

	public void setPersonCache(Set<Person> personCache) {
		this.personCache = personCache;
	}

	public Set<FsAddress> getAddressCache() {
		return addressCache;
	}

	public void setAddressCache(Set<FsAddress> addressCache) {
		this.addressCache = addressCache;
	}

	public Set<Phone> getPhoneCache() {
		return phoneCache;
	}

	public void setPhoneCache(Set<Phone> phoneCache) {
		this.phoneCache = phoneCache;
	}

	public void resetCache() {
		this.addressCache = null;
		this.phoneCache = null;
		this.personCache = null;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
	public String getDisStatusText() {
		String toReturn;
		if (statusId == TracingConstants.STATUS_SUSPECTED_FRAUD) {
			toReturn = "<p style=\"padding:0px;margin:0px;font-weight:bold;\">Suspected Fraud</p><br/>";
		} else if (statusId == TracingConstants.STATUS_KNOWN_FRAUD) {
			toReturn = "<p style=\"padding:0px;margin:0px;font-weight:bold;\">Known Fraud</p><br/>";
		} else {
			toReturn = "";
		}
		return toReturn;		
	}

	public String getDisStatus() {
		String toReturn;
		if (statusId == TracingConstants.STATUS_SUSPECTED_FRAUD) {
			toReturn = "class=\"suspected_fraud\"";
		} else if (statusId == TracingConstants.STATUS_KNOWN_FRAUD) {
//			toReturn = "style=\"background-color:#CC1B0B;\"";
			toReturn = "class=\"known_fraud\"";
		} else {
			toReturn = "";
		}
		return toReturn;		
	}
	
}
