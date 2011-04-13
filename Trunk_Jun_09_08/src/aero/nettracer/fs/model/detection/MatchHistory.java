package aero.nettracer.fs.model.detection;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import aero.nettracer.fs.model.FsClaim;

@Entity
@Proxy(lazy = false)
public class MatchHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	
	@OneToMany(mappedBy = "match", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private Set<MatchDetail> details;
	
	@OneToOne(targetEntity = aero.nettracer.fs.model.FsClaim.class)
	private FsClaim claim1;
	
	@OneToOne(targetEntity = aero.nettracer.fs.model.FsClaim.class)
	private FsClaim claim2;
	
	@Transient
	private boolean selected;

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

	public FsClaim getClaim1() {
		return claim1;
	}

	public void setClaim1(FsClaim claim1) {
		this.claim1 = claim1;
	}

	public FsClaim getClaim2() {
		return claim2;
	}

	public void setClaim2(FsClaim claim2) {
		this.claim2 = claim2;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public String getMatchSummary() {
		String summary = "";
		
		for (MatchDetail m: getDetails()) {
			summary += m.getDescription() + ",";
		}
		summary = summary.substring(0, summary.lastIndexOf(","));
		return summary;
	}
	
}
