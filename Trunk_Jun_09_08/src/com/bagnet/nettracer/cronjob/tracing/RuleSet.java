package com.bagnet.nettracer.cronjob.tracing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.cronjob.tracing.dto.MatchResult;
import com.bagnet.nettracer.cronjob.tracing.dto.Score;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Rule;
import com.bagnet.nettracer.tracing.db.Station;

public class RuleSet {

	HashMap<String, ArrayList<Rule>> rules = null;

	public RuleSet() {
		loadRuleSet();
	}

	public RuleSet(HashMap<String, ArrayList<Rule>> rules) {
		this.rules = rules;
	}

	public void loadRuleSet() {
		Session sess = null;

		List<Rule> ruleList = null;
		HashMap<String, ArrayList<Rule>> ruleSet = new HashMap<String, ArrayList<Rule>>();

		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Rule.class);
			ruleList = cri.list();

			for (Rule rule : ruleList) {
				if (ruleSet.containsKey(rule.getRuleName())) {
					// Add to existing lists
					ArrayList<Rule> list = ruleSet.get(rule.getRuleName());
					list.add(rule);
					// ruleSet.put(rule.getRuleName(), list);
				} else {
					// Create list
					ArrayList<Rule> list = new ArrayList<Rule>();
					list.add(rule);
					ruleSet.put(rule.getRuleName(), list);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Looks up the value of the first occurence of that element type. This will
	 * generally be used for additional rules that do not follow standard
	 * process, such as content category scoring.
	 * 
	 * @param element
	 * @param percentMatch
	 * @return
	 */
	public double getMultiplier(String element, double percentMatch) {
		Rule ruleToUse = getRules(element, percentMatch).get(0);
		return determineMultiplier(percentMatch, ruleToUse);
	}

	public double getMultiplier(MatchResult matchResult, Score score) {
		return getMultiplier(matchResult, score, -1);
	}

	public double getMultiplier(MatchResult matchResult, Score score, int length) {

		ArrayList<Rule> rules = getRules(matchResult.getMatchElement().name(),
				matchResult.getPercentMatch());
		Rule ruleToUse = null;

		if (rules.size() == 0) {
			// No matching rule
		} else if (rules.size() == 1) {
			ruleToUse = rules.get(0);
		} else {
			// Determine which rule to use.
			// Set a default in case no match, or in case there is only one
			// rule.
			ruleToUse = rules.get(0);
			if (length > -1) {
				for (Rule rule : rules) {
					if (length >= rule.getMinLength()
							&& length <= rule.getMaxLength()) {
						ruleToUse = rule;
					}
				}
			}
		}

		// Identify multiplier

		double percent = matchResult.getPercentMatch();
		double multiplier = determineMultiplier(percent, ruleToUse);

		// Determine if a GTSV value has been defined for this
		// field & set that value in the score object if necessary.
		if (ruleToUse.getGtsv() != 0) {
			if (percent >= ruleToUse.getGtsv()) {
				score.setGtsv(true);
				matchResult.setUsedInScoring(true);
			}
		}

		// Return appropriate percent
		return 0;
	}

	private double determineMultiplier(double percent, Rule ruleToUse) {
		double multiplier = 0;

		if (percent == 100) {
			multiplier = ruleToUse.getV10();
		} else if (percent >= 90) {
			multiplier = ruleToUse.getV9();
		} else if (percent >= 80) {
			multiplier = ruleToUse.getV8();
		} else if (percent >= 70) {
			multiplier = ruleToUse.getV7();
		} else if (percent >= 60) {
			multiplier = ruleToUse.getV6();
		} else if (percent >= 50) {
			multiplier = ruleToUse.getV5();
		} else if (percent >= 40) {
			multiplier = ruleToUse.getV4();
		} else if (percent >= 30) {
			multiplier = ruleToUse.getV3();
		} else if (percent >= 20) {
			multiplier = ruleToUse.getV2();
		} else if (percent >= 10) {
			multiplier = ruleToUse.getV1();
		} else if (percent >= 0) {
			multiplier = ruleToUse.getV0();
		}

		return multiplier;
	}

	private ArrayList<Rule> getRules(String element, double percentMatch) {
		ArrayList<Rule> potentialRules = rules.get(element);
		if (potentialRules.size() > 0) {
			return potentialRules;
		} else {
			return null;
		}
	}

}
