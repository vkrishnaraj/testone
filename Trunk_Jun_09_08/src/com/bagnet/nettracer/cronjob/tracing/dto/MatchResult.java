package com.bagnet.nettracer.cronjob.tracing.dto;

import com.bagnet.nettracer.cronjob.tracing.MatchElement;
import com.bagnet.nettracer.tracing.utils.StringUtils;

public class MatchResult {
	private MatchElement matchElement;
	private double percentMatch;
	private String incidentContents;
	private String ohdContents;
	private int itineraryType;
	private int bagNumber;
	private boolean categoryMatched;
	private String contentCategory;
	private boolean usedInScoring;
	private boolean usableInScoring;
	private double scoredValue; // Value used primarily for manually validating scores.

	public double getScoredValue() {
		return scoredValue;
	}

	public void setScoredValue(double scoredValue) {
		this.scoredValue = scoredValue;
	}

	public MatchResult(MatchElement matchElement, double percentMatch,
			String incidentContents, String ohdContents) {
		setMatchElement(matchElement);
		setPercentMatch(percentMatch);
		setIncidentContents(incidentContents);
		setOhdContents(ohdContents);
	}

	public MatchElement getMatchElement() {
		return matchElement;
	}

	public void setMatchElement(MatchElement matchElement) {
		this.matchElement = matchElement;
	}

	public double getPercentMatch() {
		return percentMatch;
	}

	public void setPercentMatch(double percentMatch) {
		this.percentMatch = percentMatch;
	}

	public String getIncidentContents() {
		return incidentContents;
	}

	public void setIncidentContents(String incidentContents) {
		this.incidentContents = incidentContents;
	}

	public String getOhdContents() {
		return ohdContents;
	}

	public void setOhdContents(String ohdContents) {
		this.ohdContents = ohdContents;
	}

	public boolean isUsedInScoring() {
		return usedInScoring;
	}

	public void setUsedInScoring(boolean usedInScoring) {
		this.usedInScoring = usedInScoring;
	}

	public int getBagNumber() {
		return bagNumber;
	}

	public void setBagNumber(int bagNumber) {
		this.bagNumber = bagNumber;
	}

	public int getItineraryType() {
		return itineraryType;
	}

	public void setItineraryType(int itineraryType) {
		this.itineraryType = itineraryType;
	}

	public boolean isCategoryMatched() {
		return categoryMatched;
	}

	public void setCategoryMatched(boolean categoryMatched) {
		this.categoryMatched = categoryMatched;
	}

	public String toString() {
		String header = "\n    ";
		return StringUtils.join(header, "Element: " + matchElement.name(),
				"Percent Match: " + Double.toString(percentMatch), "Incident: "
						+ incidentContents, "OHD: " + ohdContents, "Itin: "
						+ itineraryType, "Bag #: " + bagNumber, "Cat Matched: "
						+ categoryMatched, "Used in Scoring: " + usedInScoring,
				"Usable in Scoring: " + usableInScoring, "Scored Value: "
						+ scoredValue);
	}

	public String getContentCategory() {
		return contentCategory;
	}

	public void setContentCategory(String contentCategory) {
		this.contentCategory = contentCategory;
	}

	public boolean isUsableInScoring() {
		return usableInScoring;
	}

	public void setUsableInScoring(boolean usableInScoring) {
		this.usableInScoring = usableInScoring;
	}

}
