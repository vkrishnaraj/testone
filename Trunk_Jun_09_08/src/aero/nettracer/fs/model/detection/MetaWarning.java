package aero.nettracer.fs.model.detection;

import java.io.Serializable;

import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class MetaWarning implements Serializable {
	private static final long serialVersionUID = 1L;

	private double percentageMatch;
	private int threatLevel;
	private String description;

	public double getPercentageMatch() {
		return percentageMatch;
	}

	public void setPercentageMatch(double percentageMatch) {
		this.percentageMatch = percentageMatch;
	}

	public int getThreatLevel() {
		return threatLevel;
	}

	public void setThreatLevel(int threatLevel) {
		this.threatLevel = threatLevel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
