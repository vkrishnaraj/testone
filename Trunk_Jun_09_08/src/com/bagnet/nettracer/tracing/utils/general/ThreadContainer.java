package com.bagnet.nettracer.tracing.utils.general;

import java.util.Date;

public class ThreadContainer {
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Thread getThread() {
		return thread;
	}
	public void setThread(Thread thread) {
		this.thread = thread;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public boolean isWaiting() {
		return isWaiting;
	}
	public void setWaiting(boolean isWaiting) {
		this.isWaiting = isWaiting;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	public boolean isDead() {
		return dead;
	}
	public void setSentNotification(boolean sentNotification) {
		this.sentNotification = sentNotification;
	}
	public boolean isSentNotification() {
		return sentNotification;
	}
	private String id;
	private Thread thread;
	private Date startTime;
	private boolean isWaiting; 
	private boolean dead = false;
	private boolean sentNotification = false;
}
