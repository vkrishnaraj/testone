package com.bagnet.nettracer.cronjob.tracing;

import java.util.ArrayList;
import java.util.HashMap;

import com.bagnet.nettracer.cronjob.tracing.dto.BagMatches;
import com.bagnet.nettracer.cronjob.tracing.dto.MatchResult;
import com.bagnet.nettracer.cronjob.tracing.dto.Score;

public class ScoringAlgorithm {

	public static Score score(ArrayList<MatchResult> matchResults,
			RuleSet ruleSet) {
		assert (ruleSet != null);
		assert (matchResults != null);

		Score score = new Score();
		score.setMatchResults(matchResults);

		double overallScore = 0;
		HashMap<Integer, BagMatches> bags = new HashMap<Integer, BagMatches>();
		
		// Begin actual scoring
		for (MatchElement element: MatchElement.values()) {
			MatchResult topElement = null;
			
			for (MatchResult matchResult : matchResults) {
				if (matchResult.getMatchElement().name().equals(element.name())) {
					
					// We only want the top 1 match; not bag-specific
					if (	matchResult.getMatchElement().name().equals("RECORD_LOCATOR") ||
							matchResult.getMatchElement().name().equals("MEM_NUMBER") || 
							matchResult.getMatchElement().name().equals("NAME") ||
							matchResult.getMatchElement().name().equals("ADDRESS") ||
							matchResult.getMatchElement().name().equals("ITINERARY") ||
							matchResult.getMatchElement().name().equals("EMAIL") ||
							matchResult.getMatchElement().name().equals("PHONE") ||
							matchResult.getMatchElement().name().equals("CLAIM_CHECK")) {
						if (topElement != null) {
							if (topElement.getPercentMatch() < matchResult.getPercentMatch()) {
								topElement = matchResult;
							}
						} else {
							topElement = matchResult;
						}
					} // End of top 1 match for non-bag specific elements
					
					// For bag specific matches we do not add them to the score immediately but
					// put them in "BagMatches" so that we can determine the highest scoring bag.
					
					if (	matchResult.getMatchElement().name().equals("MANUFACTURER") ||
							matchResult.getMatchElement().name().equals("COLOR") ||
							matchResult.getMatchElement().name().equals("TYPE") ||
							matchResult.getMatchElement().name().equals("ELEMENTS") ||
							matchResult.getMatchElement().name().equals("CONTENTS")) {
						
						// Get or create the necessary bag.
						BagMatches bag = null;
						if (bags.containsKey(new Integer(matchResult.getBagNumber()))) {
							bag = bags.get(new Integer(matchResult.getBagNumber()));
						} else {
							bag = new BagMatches();
							bags.put(new Integer(matchResult.getBagNumber()), bag);
						}
						
						bag.setOverallScore(bag.getOverallScore() + check(matchResult, ruleSet, score, true));
						
					} // End of bag-specific matches
				}
			}
			if (topElement != null) {
				overallScore += check(topElement, ruleSet, score, false);
			}
		}
		
		// Pick bag with biggest score
		BagMatches bestBag = null;
		for (BagMatches bag: bags.values()) {
			if (bestBag == null) {
				bestBag = bag;
			} else {
				if (bag.getOverallScore() > bestBag.getOverallScore()) {
					bestBag = bag;
				}
			}
		}
		
		// Add the best bag's score to the overall score and set its usedInScoring flags to true.
		if (bestBag != null) {
			overallScore += bestBag.getOverallScore();
			for (MatchResult result: bestBag.getMatchResults()) {
				result.setUsedInScoring(true);
			}
		}
		
		score.setOverallScore(overallScore);
		return score;
	}
	
	private static double check (MatchResult matchResult, RuleSet ruleSet, Score score, boolean bagSpecific) {
		double addToScore = matchResult.getPercentMatch() * ruleSet.getMultiplier(matchResult, score);
		if (matchResult.getMatchElement().name().equals("CONTENTS")) {
			if (matchResult.isCategoryMatched()) {
				addToScore += ruleSet.getMultiplier("CONTENTS_CATEGORY", 100);
			}
		}
		if (addToScore > 0 && bagSpecific == false) { 
			matchResult.setUsedInScoring(true);
		}
		return addToScore;
	}
}
