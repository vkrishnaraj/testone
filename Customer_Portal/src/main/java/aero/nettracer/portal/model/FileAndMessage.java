package aero.nettracer.portal.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FileAndMessage implements Comparable<FileAndMessage> {
	
	private String column1;
	private Calendar dateCreated;
	private String username;
	private boolean publish;
	private int statusId;
	private String path;
	private boolean message;

	public boolean isMessage() {
		return message;
	}

	public void setMessage(boolean message) {
		this.message = message;
	}

	public String getColumn1() {
		return column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public Calendar getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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
	
	public boolean isDownloadable() {
		return !isMessage() && getStatusId() != 3;
	}
	
	public String getDispDateCreated() {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		if (getDateCreated() != null) {
			return format.format(getDateCreated().getTime());
		}
		return "";
	}

	@Override
	public int compareTo(FileAndMessage o) {
		if (o.getDateCreated().before(getDateCreated())) {
			return 1;
		} else if (o.getDateCreated().after(getDateCreated())){
			return -1;
		}
		
		return 0;
	}
	
	
}
