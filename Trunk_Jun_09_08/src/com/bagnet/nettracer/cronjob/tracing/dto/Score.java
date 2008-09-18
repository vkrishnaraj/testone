package com.bagnet.nettracer.cronjob.tracing.dto;

import java.util.ArrayList;

public class Score {
	private ArrayList<MatchResult> matchResults;
	private double overallScore;
	private boolean gtsv;
	private int bagNumber = -1;
	private String claimCheckNumber;

	public boolean isGtsv() {
		return gtsv;
	}

	public void setGtsv(boolean gtsv) {
		this.gtsv = gtsv;
	}

	public ArrayList<MatchResult> getMatchResults() {
		return matchResults;
	}

	public void setMatchResults(ArrayList<MatchResult> matchResults) {
		this.matchResults = matchResults;
	}

	public double getOverallScore() {
		return overallScore;
	}

	public void setOverallScore(double overallScore) {
		this.overallScore = overallScore;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Overall Score: " + overallScore + "\n\n");
		for (MatchResult result : matchResults) {
			sb.append(result.toString() + "\n\n");
		}
		return sb.toString();
	}

	public int getBagNumber() {
		return bagNumber;
	}

	public void setBagNumber(int bagNumber) {
		this.bagNumber = bagNumber;
	}

	public String getClaimCheckNumber() {
		return claimCheckNumber;
	}

	public void setClaimCheckNumber(String claimCheckNumber) {
		this.claimCheckNumber = claimCheckNumber;
	}

}
