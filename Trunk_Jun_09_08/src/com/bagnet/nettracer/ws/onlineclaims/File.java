package com.bagnet.nettracer.ws.onlineclaims;

import java.util.Calendar;

public class File {

	private long id;
	private String filename;
	private String path;
	private boolean interim;
	private boolean publish;
	private int statusId;
	private Calendar dateUploaded;
	private Calendar dateViewed;
	
	public long getId() {
  	return id;
  }
	public void setId(long id) {
  	this.id = id;
  }
	public String getFilename() {
  	return filename;
  }
	public void setFilename(String filename) {
  	this.filename = filename;
  }
	public String getPath() {
  	return path;
  }
	public void setPath(String path) {
  	this.path = path;
  }
	public boolean isInterim() {
		return interim;
	}
	public void setInterim(boolean interim) {
		this.interim = interim;
	}
	public boolean isPublish() {
		return publish;
	}
	public void setPublish(boolean publish) {
		this.publish = publish;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public Calendar getDateUploaded() {
		return dateUploaded;
	}
	public void setDateUploaded(Calendar dateUploaded) {
		this.dateUploaded = dateUploaded;
	}
	public Calendar getDateViewed() {
		return dateViewed;
	}
	public void setDateViewed(Calendar dateViewed) {
		this.dateViewed = dateViewed;
	}
	
}
