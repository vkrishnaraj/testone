package aero.nettracer.fs.model.detection;

import java.io.Serializable;
import java.util.Set;

public class TraceResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Set<MatchHistory> matchHistory;
	private Set<MetaWarning> metaWarning;
	private String metaSummary;
	public static final int THREAT_LEVEL_GREEN = -1;
	public static final int THREAT_LEVEL_UNKNOWN = 0;
	public static final int THREAT_LEVEL_YELLOW = 1;
	public static final int THREAT_LEVEL_ORANGE = 2;
	public static final int THREAT_LEVEL_RED = 2;

	public Set<MetaWarning> getMetaWarning() {
		return metaWarning;
	}

	public void setMetaWarning(Set<MetaWarning> metaWarning) {
		this.metaWarning = metaWarning;
	}

	public String getMetaSummary() {
		return metaSummary;
	}

	public void setMetaSummary(String metaSummary) {
		this.metaSummary = metaSummary;
	}

	public int getThreatLevel() {
		return threatLevel;
	}

	public void setThreatLevel(int threatLevel) {
		this.threatLevel = threatLevel;
	}

	private int threatLevel;

	public Set<MatchHistory> getMatchHistory() {
		return matchHistory;
	}

	public void setMatchHistory(Set<MatchHistory> matchHistory) {
		this.matchHistory = matchHistory;
	}

	public boolean isTraceComplete() {
		return traceComplete;
	}

	public void setTraceComplete(boolean traceComplete) {
		this.traceComplete = traceComplete;
	}

	public int getSecondsUntilReload() {
		return secondsUntilReload;
	}

	public void setSecondsUntilReload(int secondsUntilReload) {
		this.secondsUntilReload = secondsUntilReload;
	}

	boolean traceComplete;
	int secondsUntilReload;
}
