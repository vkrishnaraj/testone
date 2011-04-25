package aero.nettracer.fs.model.detection;

import java.util.Set;

public class TraceResponse {
	Set<MatchHistory> matchHistory;

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
