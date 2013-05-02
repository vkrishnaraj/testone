package aero.nettracer.fs.utilities.tracing;

import java.util.Date;
import java.util.Vector;

public class TraceProgress {
	
	private long fileId;
	private long startTime;
	private ConsumerQueueElement element;
	
	private static final int MAX_SECONDS = 9999;
	
	public TraceProgress(long startTime, ConsumerQueueElement element){
		this.fileId = element.getFile().getId();
		this.startTime = startTime;
		this.element = element;
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
	
	public ConsumerQueueElement getElement() {
		return element;
	}

	public void setElement(ConsumerQueueElement element) {
		this.element = element;
	}
	
	public boolean stillRunning(){
		return !element.isTraceFinished();
	}
	
	public int getPercentComplete(){
		if(element.getProducerTotalCount() == 0){
			return 100;
		} else {
			return 100 * (1 - (element.getQueue().size() / element.getProducerTotalCount()));
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
