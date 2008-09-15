package com.bagnet.nettracer.cronjob.tracing.dto;

import java.util.ArrayList;


public class BagMatches {

	private ArrayList<MatchResult> matchResults = new ArrayList<MatchResult>();
	private double overallScore;
	
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
	
	public void addMatchResult(MatchResult result) {
		matchResults.add(result);
	}
	
}
