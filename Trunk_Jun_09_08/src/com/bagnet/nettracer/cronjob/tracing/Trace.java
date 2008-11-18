package com.bagnet.nettracer.cronjob.tracing;

import java.util.ArrayList;

import com.bagnet.nettracer.cronjob.tracing.dto.MatchResult;
import com.bagnet.nettracer.cronjob.tracing.dto.Score;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.db.TraceIncident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.TraceOHD;

public class Trace {
	
	public static void main(String[] args) {
		String incident_ID = args[0];
		String ohd_ID = args[1];
		Score score = Trace.trace(incident_ID, ohd_ID, null);
		System.out.print("Comparing TraceIncident: " + incident_ID + " to OHD: " + ohd_ID +"\n\n");
		System.out.print(score.toString());
	}
	
	public static Score trace (String incident_ID, String ohd_ID, RuleSet ruleSet) {
		IncidentBMO ibmo = new IncidentBMO();
		TraceIncident inc = ibmo.findTraceIncidentByID(incident_ID);
		OhdBMO obmo = new OhdBMO();
		TraceOHD ohd = (TraceOHD) obmo.findTraceOHDByID(ohd_ID);
		return Trace.trace(inc, ohd, null);
	}
	
	public static Score trace (TraceIncident incident, TraceOHD ohd, RuleSet ruleSet) {
		
		ArrayList<MatchResult> matchResults = match(incident, ohd);

		if (ruleSet == null) {
			ruleSet = new RuleSet();
		}
		
		Score score = ScoringAlgorithm.score(matchResults, ruleSet); 
		assert(score != null);
		/*
		 if (score.getOverallScore() > 100) {
			score.setOverallScore(100);
		}
		*/
		
		if (score.getOverallScore() < 0) {
			score.setOverallScore(0);
		}
		
		
		
		return score;
	}
	
	/**
	 * Method runs all of the matching methods to produce an ArrayList
	 * containing the sum total of all comparisons and cross products.
	 * 
	 * @param incident TraceIncident being compared.
	 * @param ohd OHD being compared.
	 * @return ArrayList of MatchResults that are to be fed into the 
	 * preferred preprocessor and then scoring algorithm.
	 */
	private static ArrayList<MatchResult> match(TraceIncident incident, TraceOHD ohd) {
		ArrayList<MatchResult> returnList = new ArrayList<MatchResult>();
		for(MatchElement element: MatchElement.values()) {
			ArrayList<MatchResult> result = element.match(incident, ohd);
			if (result != null) {
				returnList.addAll(result);
			}
		}
		return returnList;
	}
}
