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

import aero.nettracer.fs.model.File;

@Entity
@Proxy(lazy = false)
public class MatchHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;

	@OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private Set<MatchDetail> details;

	@Transient
	private boolean dataObscured;

	public boolean isDataObscured() {
		return dataObscured;
	}

	public void setDataObscured(boolean dataObscured) {
		this.dataObscured = dataObscured;
	}

	private boolean deleted;
	
	private boolean primarymatch;

	@OneToOne(targetEntity = aero.nettracer.fs.model.File.class)
	private File file1;

	@OneToOne(targetEntity = aero.nettracer.fs.model.File.class)
	private File file2;

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

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getMatchSummary() {
		String summary = "";
		if (details != null) {
			for (MatchDetail m : getDetails()) {
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

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getCreatedate() {
		return createdate;
	}

	@Transient
	public double calculatePercentage() {
		double ret = 0;
		if (this.details != null) {
			for (MatchDetail detail : details) {
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

	public void setFile1(File file1) {
		this.file1 = file1;
	}

	public File getFile1() {
		return file1;
	}

	public void setFile2(File file2) {
		this.file2 = file2;
	}

	public File getFile2() {
		return file2;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setPrimarymatch(boolean primarymatch) {
		this.primarymatch = primarymatch;
	}

	public boolean isPrimarymatch() {
		return primarymatch;
	}

}
