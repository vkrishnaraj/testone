package aero.nettracer.fs.model.forum;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.bagnet.nettracer.tracing.utils.DateUtils;

import aero.nettracer.fs.model.FsAttachment;
import aero.nettracer.fs.model.FsClaim;

public class FsForumPost implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String title;
	private String text;
	private Date createDate;
	private String createAgent;
	private String createAirline;
	private Date lastEdited;
	private FsForumPost parent;
	private FsForumThread thread;
	private Set<FsClaim> claims;
	private Set<FsAttachment> attachments;
	
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public Date getLastEdited() {
		return lastEdited;
	}

	public void setLastEdited(Date lastEdited) {
		this.lastEdited = lastEdited;
	}

	public FsForumPost getParent() {
		return parent;
	}

	public void setParent(FsForumPost parent) {
		this.parent = parent;
	}

	public FsForumThread getThread() {
		return thread;
	}

	public void setThread(FsForumThread thread) {
		this.thread = thread;
	}

	public Set<FsClaim> getClaims() {
		return claims;
	}

	public void setClaims(Set<FsClaim> claims) {
		this.claims = claims;
	}

	public Set<FsAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<FsAttachment> attachments) {
		this.attachments = attachments;
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

	public String getCreateDateDisp() {
		String createDateDisp = "";
		if (thread != null) {
			createDateDisp = DateUtils.formatDate(getCreateDate(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
		}
		return createDateDisp;
	}

}
