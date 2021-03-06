package aero.nettracer.fs.model.forum;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import aero.nettracer.fs.model.FsAttachment;
import aero.nettracer.fs.model.FsClaim;

import com.bagnet.nettracer.tracing.utils.DateUtils;

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
	private List<FsForumPost_Claim> claims;
	private List<FsAttachment> attachments;
	
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
	
	public String getTextReadonly() {
		if(text != null)
		{
			return text.replaceAll("\r\n", "<br>");
		}
		else
		{
			return "";
		}
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

	public List<FsForumPost_Claim> getClaims() {
		return claims;
	}

	public void setClaims(List<FsForumPost_Claim> claims) {
		this.claims = claims;
	}

	public List<FsAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<FsAttachment> attachments) {
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

	public String getCreateDateTimeDisp() {
		String createDateDisp = "";
		if (thread != null) {
			createDateDisp = DateUtils.formatDate(getCreateDate(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
		}
		return createDateDisp;
	}

	public String getCreateDateDisp() {
		String createDateDisp = "";
		if (thread != null) {
			createDateDisp = DateUtils.formatDate(getCreateDate(), _DATEFORMAT, null, _TIMEZONE);
		}
		return createDateDisp;
	}

	public String getEditedDateTimeDisp() {
		String createDateDisp = "";
		if (thread != null) {
			createDateDisp = DateUtils.formatDate(getLastEdited(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
		}
		return createDateDisp;
	}

	public String getEditedDateDisp() {
		String createDateDisp = "";
		if (thread != null) {
			createDateDisp = DateUtils.formatDate(getLastEdited(), _DATEFORMAT, null, _TIMEZONE);
		}
		return createDateDisp;
	}

}
