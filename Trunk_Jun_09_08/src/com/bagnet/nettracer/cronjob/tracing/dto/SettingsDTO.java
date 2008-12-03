package com.bagnet.nettracer.cronjob.tracing.dto;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.cronjob.tracing.PassiveTraceErrorHandler;
import com.bagnet.nettracer.cronjob.tracing.RuleSet;
import com.bagnet.nettracer.cronjob.tracing.TracingIncidentCache;
import com.bagnet.nettracer.cronjob.tracing.TracingOhdCache;

public class SettingsDTO {
	String companyCode;
	int daysBack;
	int daysForward;
	double minimumScore;
	RuleSet ruleSet;
	PassiveTraceErrorHandler errorHandler;
	TracingOhdCache ohdCache;
	TracingIncidentCache incidentCache;
	Logger logger;

	public SettingsDTO(String companyCode, int daysBack, int daysForward,
			double minimumScore, RuleSet ruleSet,
			PassiveTraceErrorHandler errorHandler, TracingOhdCache ohdCache,
			TracingIncidentCache incidentCache, Logger logger) {
		this.companyCode = companyCode;
		this.daysBack = daysBack;
		this.daysForward = daysForward;
		this.minimumScore = minimumScore;
		this.ruleSet = ruleSet;
		this.errorHandler = errorHandler;
		this.ohdCache = ohdCache;
		this.incidentCache = incidentCache;
		this.logger = logger;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public int getDaysBack() {
		return daysBack;
	}

	public void setDaysBack(int daysBack) {
		this.daysBack = daysBack;
	}

	public int getDaysForward() {
		return daysForward;
	}

	public void setDaysForward(int daysForward) {
		this.daysForward = daysForward;
	}

	public double getMinimumScore() {
		return minimumScore;
	}

	public void setMinimumScore(double minimumScore) {
		this.minimumScore = minimumScore;
	}

	public RuleSet getRuleSet() {
		return ruleSet;
	}

	public void setRuleSet(RuleSet ruleSet) {
		this.ruleSet = ruleSet;
	}

	public PassiveTraceErrorHandler getErrorHandler() {
		return errorHandler;
	}

	public void setErrorHandler(PassiveTraceErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	public TracingOhdCache getOhdCache() {
		return ohdCache;
	}

	public void setOhdCache(TracingOhdCache ohdCache) {
		this.ohdCache = ohdCache;
	}

	public TracingIncidentCache getIncidentCache() {
		return incidentCache;
	}

	public void setIncidentCache(TracingIncidentCache incidentCache) {
		this.incidentCache = incidentCache;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
