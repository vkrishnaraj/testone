package aero.nettracer.fs.model.forum;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bagnet.nettracer.tracing.utils.DateUtils;

public class FsForumThread implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String title;
	private Date createDate;
	private Date lastEdited;
	private String createAgent;
	private String createAirline;
	private boolean locked;
	private int numPosts;
	private int numFiles;
	private int numAttachments;
	private List<FsForumPost> posts;
	private List<FsForumTag> tags;
	
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private java.util.TimeZone _TIMEZONE;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateAgent() {
		return createAgent;
	}

	public void setCreateAgent(String createAgent) {
		this.createAgent = createAgent;
	}

	public String getCreateAirline() {
		return createAirline;
	}

	public void setCreateAirline(String createAirline) {
		this.createAirline = createAirline;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public int getNumPosts() {
		return numPosts;
	}

	public void setNumPosts(int numPosts) {
		this.numPosts = numPosts;
	}

	public int getNumFiles() {
		return numFiles;
	}

	public void setNumFiles(int numFiles) {
		this.numFiles = numFiles;
	}

	public int getNumAttachments() {
		return numAttachments;
	}

	public void setNumAttachments(int numAttachments) {
		this.numAttachments = numAttachments;
	}

	public List<FsForumPost> getPosts() {
		return posts;
	}

	public void setPosts(List<FsForumPost> posts) {
		this.posts = posts;
	}

	public List<FsForumTag> getTags() {
		return tags;
	}

	public void setTags(List<FsForumTag> tags) {
		this.tags = tags;
	}
	
	public void incrementNumPosts() {
		numPosts++;
	}
	
	public void addNumAttachments(int num) {
		numAttachments += num;
	}
	
	public void addNumFiles(int num) {
		numFiles += num;
	}

	public Date getLastEdited() {
		return lastEdited;
	}

	public void setLastEdited(Date lastEdited) {
		this.lastEdited = lastEdited;
	}
	
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	public void set_DATEFORMAT(String _DATEFORMAT) {
		this._DATEFORMAT = _DATEFORMAT;
	}

	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}

	public void set_TIMEFORMAT(String _TIMEFORMAT) {
		this._TIMEFORMAT = _TIMEFORMAT;
	}

	public java.util.TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}

	public void set_TIMEZONE(java.util.TimeZone _TIMEZONE) {
		this._TIMEZONE = _TIMEZONE;
	}

	public String getCreateDateTimeDisp() {
		String createDateDisp = "";
		if (getCreateDate() != null) {
			createDateDisp = DateUtils.formatDate(getCreateDate(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
		}
		return createDateDisp;
	}

	public String getCreateDateDisp() {
		String createDateDisp = "";
		if (getCreateDate() != null) {
			createDateDisp = DateUtils.formatDate(getCreateDate(), _DATEFORMAT, null, _TIMEZONE);
		}
		return createDateDisp;
	}

	public String getEditedDateTimeDisp() {
		String createDateDisp = "";
		if (getLastEdited() != null) {
			createDateDisp = DateUtils.formatDate(getLastEdited(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
		}
		return createDateDisp;
	}

	public String getEditedDateDisp() {
		String createDateDisp = "";
		if (getLastEdited() != null) {
			createDateDisp = DateUtils.formatDate(getLastEdited(), _DATEFORMAT, null, _TIMEZONE);
		}
		return createDateDisp;
	}

}
