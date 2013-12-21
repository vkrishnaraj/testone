package com.bagnet.nettracer.ws.onlineclaims;

import java.util.Calendar;
import java.util.Date;

public class Message {

	long id;	
	private String message;
	private Calendar dateCreated;
	private Calendar dateReviewed;
	private String username;
	private boolean publish;
	private int statusId;

	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id=id;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Calendar getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Calendar getDateReviewed() {
		return dateReviewed;
	}

	public void setDateReviewed(Calendar dateReviewed) {
		this.dateReviewed = dateReviewed;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

}
