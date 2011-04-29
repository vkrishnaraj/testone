package aero.nettracer.fs.utilities.tracing;

import java.util.Date;

public class ThreadContainer {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Thread getConsumer() {
		return consumer;
	}
	public void setConsumer(Thread consumer) {
		this.consumer = consumer;
	}
	public  Date getStartTime() {
		return startTime;
	}

	public  void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public  boolean isWaiting() {
		return isWaiting;
	}
	public  void setWaiting(boolean isWaiting) {
		this.isWaiting = isWaiting;
	}
	private int id;
	private Thread consumer;
	private Date startTime;
	private boolean isWaiting; 
}
