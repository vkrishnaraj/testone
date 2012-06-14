package aero.nettracer.fs.utilities.tracing;

import java.util.Date;
import java.util.Vector;

public class TraceProgress {
	
	private long fileId;
	private long startTime;
	private Vector v;
	private int matches;
	
	private static final int MAX_SECONDS = 9999;
	
	public TraceProgress(long fileId, long startTime, Vector v, int matches){
		this.fileId = fileId;
		this.startTime = startTime;
		this.v = v;
		this.matches = matches;
	}
	
	public long getFileId() {
		return fileId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public Vector getV() {
		return v;
	}

	public void setV(Vector v) {
		this.v = v;
	}

	public int getMatches() {
		return matches;
	}

	public void setMatches(int matches) {
		this.matches = matches;
	}
	
	public boolean stillRunning(){
		return v.size() < matches;
	}
	
	public int getPercentComplete(){
		if(matches == 0){
			return 100;
		} else {
			return 100 * v.size() / matches;
		}
	}
	
	public int getSecondsUntilComplete(){
		long now = (new Date()).getTime();
		double percComplete = this.getPercentComplete();
		double percentRemaining = 100 - percComplete;
		if(percComplete == 0){
			return MAX_SECONDS;
		}
		if(percentRemaining == 0){
			return 0;
		}
		long timeElapsed = (now - startTime) / 1000;
		double secondsToComplete = (double) timeElapsed / percComplete * percentRemaining;
		return (int)(secondsToComplete*1.1);
	}
	
}
