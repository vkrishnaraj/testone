package com.bagnet.nettracer.cronjob.tracing.dto;

import java.util.ArrayList;


public class Score {
	private ArrayList<MatchResult> matchResults;
	private double overallScore;
	private boolean gtsv;
	
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
	
}
