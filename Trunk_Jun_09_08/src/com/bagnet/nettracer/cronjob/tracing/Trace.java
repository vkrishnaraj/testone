package com.bagnet.nettracer.cronjob.tracing;

import java.util.ArrayList;

import com.bagnet.nettracer.cronjob.tracing.dto.MatchResult;
import com.bagnet.nettracer.cronjob.tracing.dto.Score;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;

public class Trace {
	
	public static Score trace (Incident incident, OHD ohd, RuleSet ruleSet) {
		
		ArrayList<MatchResult> matchResults = match(incident, ohd);

		if (ruleSet == null) {
			ruleSet = new RuleSet();
		}
		
		Score score = ScoringAlgorithm.score(matchResults, ruleSet); 
		assert(score != null);
		return score;
	}
	
	/**
	 * Method runs all of the matching methods to produce an ArrayList
	 * containing the sum total of all comparisons and cross products.
	 * 
	 * @param incident Incident being compared.
	 * @param ohd OHD being compared.
	 * @return ArrayList of MatchResults that are to be fed into the 
	 * preferred preprocessor and then scoring algorithm.
	 */
	private static ArrayList<MatchResult> match(Incident incident, OHD ohd) {
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
