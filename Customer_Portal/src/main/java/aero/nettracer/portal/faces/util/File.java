/**
 * 
 */
package aero.nettracer.portal.faces.util;

import java.util.Calendar;

public class File {
	private String name;
	private String mime;
	private String path;
	private long id;
	private Long length;
	private byte[] data;
	private boolean interim;
	private boolean publish;
	private int status;
	private Calendar dateUploaded;
	private Calendar dateViewed;

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		int extDot = name.lastIndexOf('.');
		if (extDot > 0) {
			String extension = name.substring(extDot + 1);
			if ("jpg".equals(extension)) {
				mime = "image/jpeg";
			} else if ("gif".equals(extension)) {
				mime = "image/gif";
			} else if ("pdf".equals(extension)) {
				mime = "image/pdf";
			} else if ("png".equals(extension)) {
				mime = "image/png";
			} else {
				mime = "image/unknown";
			}
		}
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public String getMime() {
		return mime;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isInterim() {
		return interim;
	}

	public void setInterim(boolean interim) {
		this.interim = interim;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isPublish() {
		return publish;
	}

	public void setPublish(boolean publish) {
		this.publish = publish;
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
	
	public boolean isDownloadable() {
		return getStatus() != 3;
	}
}
