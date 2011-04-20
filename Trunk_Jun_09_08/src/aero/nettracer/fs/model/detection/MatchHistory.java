package aero.nettracer.fs.model.detection;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.Vector;

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
import aero.nettracer.fs.model.FsIncident;

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
	
	@OneToOne(targetEntity = aero.nettracer.fs.model.FsIncident.class)
	private FsIncident incident1;
	
	@OneToOne(targetEntity = aero.nettracer.fs.model.FsIncident.class)
	private FsIncident incident2;
	
	@Transient
	private boolean selected;
	
	@Transient
	private Vector traceCount;
	
	private Date createdate;
	
	private double overallScore;

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
		if (details != null) {
			for (MatchDetail m: getDetails()) {
				summary += m.getDescription() + "<br>";
			}
			summary = summary.substring(0, summary.lastIndexOf("<br>"));
		}
		return summary;
	}

	public void setTraceCount(Vector traceCount) {
		this.traceCount = traceCount;
	}

	public Vector getTraceCount() {
		return traceCount;
	}

	public void setIncident1(FsIncident incident1) {
		this.incident1 = incident1;
	}

	public FsIncident getIncident1() {
		return incident1;
	}

	public void setIncident2(FsIncident incident2) {
		this.incident2 = incident2;
	}

	public FsIncident getIncident2() {
		return incident2;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getCreatedate() {
		return createdate;
	}
	
	@Transient
	public double calculatePercentage(){
		double ret = 0;
		if(this.details != null){
			for(MatchDetail detail:details){
				ret += detail.getPercent();
			}
		}
		this.overallScore = ret;
		return ret;
	}

	public double getOverallScore() {
  	if (overallScore == 0) {
  		return calculatePercentage();
  	} else {
  		return overallScore;
  	}
  }

	public void setOverallScore(double overallScore) {
  	this.overallScore = overallScore;
  }
	
}
