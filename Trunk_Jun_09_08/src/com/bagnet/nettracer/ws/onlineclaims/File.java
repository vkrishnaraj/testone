package com.bagnet.nettracer.ws.onlineclaims;

public class File {

	private long id;
	private String filename;
	private String path;
	private boolean interim;
	
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
	
}
